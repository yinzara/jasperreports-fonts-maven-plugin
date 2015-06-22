/**
 The MIT License (MIT)

 Copyright (c) 2013 yinzara

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.yinzara.jasperreports.fonts.maven.plugin;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.util.FileUtils;

/**
 * Scans the source directories for fonts, writes a Jasper fonts XML file, writes a jasperreports_extensions.properties
 * file then packages as a JAR.
 *
 * @author Matthew Morrissette
 */
@Mojo(name = "process-fonts",
        threadSafe = true,
        defaultPhase = LifecyclePhase.PROCESS_RESOURCES,
        requiresProject = false)
public class FontsMojo extends org.apache.maven.plugin.AbstractMojo {

    private static final String ITALIC_TYPE = "italic";

    private static final String BOLD_TYPE = "bold";

    private static final String BOLD_ITALIC_TYPE = "boldItalic";

    private static final String NORMAL_TYPE = "normal";

    private static final String DEFAULT_INCLUDES = "**/*.ttf,**/*.TTF";

    /**
     * <p>
     * Should the fonts processed by the plugin be packaged in the resulting jar file?</p>
     * <p>
     * If true all font files will be copied into the resultant jar file and this can be installed directly in the root
     * of the classpath on the system running Jasper. However, if you do not have rights to distribute these fonts, be
     * careful about violating licenses.</p>
     */
    @Parameter(required = false, defaultValue = "false", property = "packageFonts")
    protected boolean packageFonts;

    /**
     * <p>
     * This is the location where fonts will be searched from and all font paths will be relative to if
     * {@link #packageFonts} is false (the default value).</p>
     * <p>
     * In standalone mode this defaults to the current directory</p>
     * <p>
     * In a project this defaults to "${basedir}/src/fonts</p>
     */
    @Parameter(required = false, property = "srcPath")
    protected String srcPath;

    /**
     * <p>
     * The name of the output jar file.</p>
     * <p>
     * In standalone, this defaults to "fonts.jar"</p>
     * <p>
     * In a project, this defaults to ${project.build.finalName}-${classifier}.jar where ${classifier} is the
     * {@link #classifier} configuration property of this plugin</p>
     */
    @Parameter(required = false, property = "jarName")
    protected String jarName;

    /**
     * <p>
     * The directory to output the artifacts to.</p>
     * <p>
     * In standalone mode this defaults to a subfolder "target" from the current directory</p>
     * <p>
     * In a project this defaults to ${project.build.directory} </p>
     */
    @Parameter(required = false, property = "outputPath")
    protected String outputPath;

    /**
     * <p>
     * This is the name of the XML file used to store the font information for jasper.</p>
     * <p>
     * In a standalone mode, this defaults to "jasper_fonts.xml"</p>
     * <p>
     * In a project this defaults to ${project.build.finalName}-${classifier}.xml where classifier is the
     * {@link #classifier} parameter</p>
     * <p>
     * The default value is almost always appropriate unless you have other font libraries on the same running Jasper
     * application</p>
     */
    @Parameter(required = false, property = "jasperreports.fonts.xmlFileName")
    protected String xmlFileName;

    /**
     * <p>
     * This parameter is only used if {@link #packageFonts} is false (the default value).</p>
     * <p>
     * This path will be included in the outputted fonts configuration as the location of the font files (i.e. the
     * directory) on the system running Jasper.</p>
     * <p>
     * If unspecified in standalone mode this will default to the absolute path resolved from the {@link #srcPath}
     * parameter</p>
     * <p>
     * If unspecified in a project, no path will be prepended to the fonts and it is assumed that all fonts in
     * {@link #srcPath} will be installed at the root of the classpath on the system running Jasper</p>
     */
    @Parameter(required = false, property = "jasperreports.fonts.deploymentPath")
    protected String deploymentPath;

    /**
     * <p>
     * This parameter controls where the .properties and .xml files needed for deployment are created.</p>
     * <p>
     * In standalone mode this defaults to the resolved {@link #deploymentPath}
     * <p>
     * <p>
     * In in a project this defaults to ${project.build.outputDirectory}. This will cause the .properties and .xml files
     * to be in the classpath of your project (either jar or war). If set to an empty element, it will default to
     * ${project.build.directory}/fonts.</p>
     */
    @Parameter(required = false, property = "jasperreports.fonts.outputDirectory")
    protected String workPath;

    /**
     * <p>
     * Since specifying a {@link #fileSet} in command line mode is impossible, this parameter allows you to specify a
     * comma separated list of "includes".
     * </p>
     * <p>
     * @see FileUtils#getFiles(java.io.File, java.lang.String, java.lang.String)
     */
    @Parameter(required = false, defaultValue = DEFAULT_INCLUDES)
    protected String includes;

    /**
     * <p>
     * Since specifying a {@link #fileSet} in command line mode is impossible, this parameter allows you to specify a
     * comma separated list of "excludes".
     * </p>
     * <p>
     * @see FileUtils#getFiles(java.io.File, java.lang.String, java.lang.String)
     */
    @Parameter(required = false)
    protected String excludes;

    /**
     * <p>
     * If required, you can rename families read from the font files to other families. This allows you to say use
     * "Helvetica" (the default Jasper font) to a font file you have that isn't Helvetica.</p>
     * <p>
     * Overrides anything specified in the {@link #renames} parameter</p>
     */
    @Parameter(required = false)
    protected FamilyRename[] familyRenames;

    /**
     * <p>
     * Related to the {@link #familyRenames} parameter, this can be used in standalone mode to rename certain font
     * families to another.</p>
     * <p>
     * Ignored if {@link #familyRenames} is specified</p>
     * <p>
     * Of the syntax:
     * <pre>
     * <code>
     * oldFamilyName1:newFamilyName1,oldFamilyName2:newFamilyName2
     * </code>
     * </pre>
     */
    @Parameter(required = false, property = "jasperreports.fonts.renames")
    protected String renames;

    /**
     * <p>
     * When renaming font families, should the original name be mapped too?</p>
     */
    @Parameter(required = false, defaultValue = "true")
    protected boolean copyOnRename;

    /**
     * <p>
     * If a given font family does not have a normal type face font (i.e. not bold or italic), should one of the other
     * type faces be mapped as the normal font as well.</p>
     * <p>
     * This will ensure a &lt;normal&gt; element appears for every fontFamily in the output xml</p>
     */
    @Parameter(required = false, defaultValue = "true", property = "jasperreports.fonts.requireNormalFonts")
    protected boolean requireNormalFonts;

    /**
     * <p>
     * If true and a font is unable to be read, the build will fail.</p>
     * <p>
     * If false (the default value), fonts that are unable to be read will be skipped</p>
     */
    @Parameter(required = false, defaultValue = "false", property = "jasperreports.fonts.failOnBadFont")
    protected boolean failOnBadFont;

    /**
     * <p>
     * The value of the "pdfEmbedded" XML element in the fonts XML file.</p>
     * <p>
     * The default is almost always appropriate</p>
     */
    @Parameter(required = false, defaultValue = "true")
    protected boolean pdfEmbedded;

    /**
     * <p>
     * The value of the "pdfEncoding" XML element in the fonts XML file.</p>
     * <p>
     * The default value is almost always appropriate</p>
     */
    @Parameter(required = false, defaultValue = "Identity-H")
    protected String pdfEncoding;

    /**
     * <p>
     * Should the output jar be attached as an artifact to the current project (has no effect in standalone).</p>
     */
    @Parameter(required = false, defaultValue = "false")
    protected boolean attachArtifact;

    /**
     * <p>
     * If {@link #attachArtifact} is true, this is the classifier of the attached artifact.</p>
     */
    @Parameter(required = false, defaultValue = "fonts", property = "jasperreports.fonts.classifier")
    protected String classifier;

    @Component
    private MavenProject project;

    @Component
    private MavenProjectHelper projectHelper;

    private Map<String, String> renameMap;

    private File srcDir;

    private String srcFilePath;

    private File jarOutputDir;

    private File workDirectory;

    private File extensionPropertiesFile;

    private File xmlFile;

    private boolean standalone;

    private final Map<String, List<Font>> families = new HashMap<String, List<Font>>(25);

    private final Map<String, File> files = new HashMap<String, File>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);

        try {
            parseConfiguration();

            try {
                for (final File fontFile : FileUtils.getFiles(srcDir, includes, excludes)) {
                    processFontFile(fontFile);
                }
            }
            catch (final IOException exp) {
                throw new MojoFailureException("Unable to read source directory: " + srcDir.getAbsolutePath(), exp);
            }

            writeXMLFile();

            writeExtensionProperties();

            final File outputFile = new File(jarOutputDir, jarName);
            final JarArchiver jarArchiver = new JarArchiver();
            jarArchiver.setDestFile(outputFile);

            jarArchiver.addFile(xmlFile, xmlFile.getName());
            jarArchiver.addFile(extensionPropertiesFile, extensionPropertiesFile.getName());

            if (packageFonts) {
                for (final File font : files.values()) {
                    jarArchiver.addFile(font, font.getName());
                }
            }

            try {
                jarArchiver.createArchive();
            }
            catch (final IOException exp) {
                throw new MojoFailureException("IOException while creating archive", exp);
            }

            if (!standalone && attachArtifact) {
                projectHelper.attachArtifact(project, "jar", classifier, outputFile);
            }
        }
        finally {
            Locale.setDefault(originalLocale);
        }
    }

    private void processFontFile(final File fontFile) throws MojoExecutionException, MojoFailureException {
        if (fontFile.isFile()) {
            final boolean loaded = loadFont(fontFile, Font.TRUETYPE_FONT, false);
            if (!loaded) {
                loadFont(fontFile, Font.TYPE1_FONT, true);
            }
        }
    }

    private boolean loadFont(
            final File fontFile,
            final int type,
            final boolean failIfBad) throws MojoFailureException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fontFile);
            final Font font = Font.createFont(type, fis);

            final String fontFamily;
            if (font.getFamily() == null || font.getFamily().trim().isEmpty()) {
                fontFamily = font.getName();
            }
            else {
                fontFamily = font.getFamily();
            }
            List<Font> fonts = families.get(fontFamily);
            if (fonts == null) {
                fonts = new LinkedList<Font>();
                families.put(fontFamily, fonts);
            }
            files.put(font.getName(), fontFile);
            fonts.add(font);
            return true;
        }
        catch (final IOException exp) {
            if (failIfBad) {
                if (failOnBadFont) {
                    throw new MojoFailureException("Exception while opening font file:"
                            + fontFile.getAbsolutePath(), exp);
                }
                else {
                    getLog().warn("Exception while opening font file:"
                            + fontFile.getAbsolutePath());
                }
            }
            return false;
        }
        catch (final FontFormatException exp) {
            if (failIfBad) {
                if (failOnBadFont) {
                    throw new MojoFailureException("Exception while reading font file:"
                            + fontFile.getAbsolutePath(), exp);
                }
                else {
                    getLog().warn("Exception while reading font file:"
                            + fontFile.getAbsolutePath());
                }
            }
            return false;
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (final IOException exp) {
                    getLog().warn("Unable to close font files", exp);
                }
            }
        }
    }

    private File writeXMLFile() throws MojoExecutionException {
        xmlFile = new File(workDirectory, xmlFileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(xmlFile);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<fontFamilies>\n");
            for (final Map.Entry<String, List<Font>> entry : families.entrySet()) {
                if (renameMap.containsKey(entry.getKey())) {
                    writeFontFamily(renameMap.get(entry.getKey()), entry.getValue(), writer, true);
                    if (copyOnRename) {
                        writeFontFamily(entry.getKey(), entry.getValue(), writer, false);
                    }
                }
                else {
                    writeFontFamily(entry.getKey(), entry.getValue(), writer, true);
                }
            }
            writer.write("</fontFamilies>\n");
        }
        catch (final IOException exp) {
            throw new MojoExecutionException("Exception while writing fonts list xml file", exp);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (final IOException exp) {
                    getLog().warn("Exception while closing XML file:" + xmlFile, exp);
                }
            }
        }
        return xmlFile;
    }

    private String getRelativeToSourcePath(final File file) throws IOException {
        final String normalizedFile = FileUtils.normalize(file.getAbsolutePath());
        if (normalizedFile.startsWith(srcFilePath)) {
            return normalizedFile.substring(srcFilePath.length() + 1);
        }
        else {
            return file.getName();
        }
    }

    private void writeExtensionProperties() throws MojoFailureException {
        extensionPropertiesFile = new File(workDirectory, "jasperreports_extension.properties");
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(extensionPropertiesFile));
            writer.write("net.sf.jasperreports.extension.registry.factory.font="
                    + "net.sf.jasperreports.engine.fonts.SimpleFontExtensionsRegistryFactory\n");
            writer.write("net.sf.jasperreports.extension.simple.font.families.fonts=");
            writer.write(xmlFileName);
            writer.write("\n");
            writer.flush();
        }
        catch (final IOException exp) {
            throw new MojoFailureException("Exception while writing jasperreports_extension.properties", exp);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (final IOException exp) {
                    getLog().warn("Exception while closing extension properties file", exp);
                }
            }
        }
    }

    private void writeFontFamily(
            final String familyName,
            final List<Font> fonts,
            final FileWriter writer,
            final boolean log) throws IOException {
        writer.write("\t<fontFamily name=\"");
        writer.write(familyName);
        writer.write("\">\n");
        boolean hasBold = false;
        boolean hasBoldItalic = false;
        boolean hasNormal = false;
        boolean hasItalic = false;
        Font lastFont = null;
        for (final Font font : fonts) {
            lastFont = font;
            if (font.isBold() || font.getName().toLowerCase().contains("bold")) {
                if (font.isItalic() || font.getName().toLowerCase().contains("italic")) {
                    writeFont(writer, font, BOLD_ITALIC_TYPE, hasBoldItalic, log);
                    hasBoldItalic = true;
                }
                else {
                    writeFont(writer, font, BOLD_TYPE, hasBold, log);
                    hasBold = true;
                }
            }
            else if (font.isItalic() || font.getName().toLowerCase().contains("italic")) {
                writeFont(writer, font, ITALIC_TYPE, hasItalic, log);
                hasItalic = true;
            }
            else {
                writeFont(writer, font, NORMAL_TYPE, hasNormal, log);
                hasNormal = true;
            }
        }
        if (requireNormalFonts && !hasNormal && lastFont != null) {
            if (log) {
                getLog().info("Font Family: '"
                        + familyName
                        + "' did not have a normal font. Mapping font: "
                        + lastFont.getName()
                        + " to the normal font.");
            }
            writer.write("\t\t<normal>");
            writer.write(deploymentPath);
            writer.write(files.get(lastFont.getName()).getName().trim());
            writer.write("</normal>\n");
        }

        writer.write("\t\t<pdfEmbedded>");
        writer.write(Boolean.toString(pdfEmbedded));
        writer.write("</pdfEmbedded>\n");
        writer.write("\t\t<pdfEncoding>");
        writer.write(pdfEncoding);
        writer.write("</pdfEncoding>\n");
        writer.write("\t</fontFamily>\n");
    }

    private void writeFont(final Writer writer,
            final Font font,
            final String type,
            final boolean alreadyPresent,
            final boolean log) throws IOException {

        if (!alreadyPresent) {
            writer.write("\t\t<");
            writer.write(type);
            writer.write(">");
            writer.write(deploymentPath);
            final File fontFile = files.get(font.getName());
            writer.write(packageFonts ? fontFile.getName().trim() : getRelativeToSourcePath(fontFile));
            writer.write("</");
            writer.write(type);
            writer.write(">\n");
        }
        else if (log) {
            getLog().info("Font Family: '"
                    + font.getFamily() != null && !font.getFamily().trim().isEmpty() ? font.getFamily() : font.getName()
                    + ", Font: '"
                    + font.getName()
                    + "' already has a normal font"
            );
        }
    }

    protected void parseConfiguration() throws MojoExecutionException, MojoFailureException {
        if (project != null && project.getBasedir() == null) {
            //We are running standalone
            parseStandaloneConfiguration();
        }
        else {
            parseProjectConfiguration();
        }

        if (jarName.indexOf('.') < 0) {
            jarName += ".jar";
        }

        if (xmlFileName.indexOf('.') < 0) {
            xmlFileName += ".xml";
        }

        if (!srcDir.exists()) {
            throw new MojoFailureException("Unable to find source directory to scan fonts: " 
                    + srcDir.getAbsolutePath());
        }

        if (familyRenames != null) {
            renameMap = new HashMap<String, String>(familyRenames.length * 3 / 2);
            for (final FamilyRename rename : familyRenames) {
                renameMap.put(rename.getFromFamily(), rename.getToFamily());
            }
        }
        else if (renames != null) {
            final String[] values = renames.split(",");
            renameMap = new HashMap<String, String>(values.length * 3 / 2);
            for (final String value : values) {
                final String[] renameValues = value.split(":");
                if (renameValues.length == 1 || renameValues.length > 2) {
                    throw new MojoFailureException("'renames' parameter is of incorrect syntax. "
                            + "See plugin documentation for more information.");
                }
                renameMap.put(renameValues[0], renameValues[1]);
            }
        }
        else {
            renameMap = Collections.emptyMap();
        }
    }

    protected void parseProjectConfiguration() throws MojoExecutionException {
        //We're running in a project
        standalone = false;

        if (outputPath == null) {
            outputPath = project.getBuild().getDirectory();
        }
        if (workPath == null) {
            if (project.getBuild().getOutputDirectory() == null) {
                project.getBuild().setOutputDirectory(project.getBuild().getDirectory());
            }
            workDirectory = new File(project.getBuild().getOutputDirectory());
        }
        else if (workPath.isEmpty()) {
            workDirectory = new File(project.getBuild().getDirectory(), "fonts");
            workDirectory.mkdirs();
        }
        else {
            workDirectory = new File(project.getBasedir(), workPath);
        }
        jarOutputDir = new File(outputPath);
        jarOutputDir.mkdirs();

        if (srcPath == null) {
            srcDir = new File(project.getBasedir(), "src/fonts");
        }
        else {
            srcDir = new File(project.getBasedir(), srcPath);
        }
        srcFilePath = FileUtils.normalize(srcDir.getAbsolutePath());

        if (deploymentPath != null && !deploymentPath.endsWith(File.separator)) {
            deploymentPath += File.separator;
        }
        if (packageFonts || deploymentPath == null) {
            deploymentPath = "";
        }
        if (jarName == null) {
            jarName = project.getBuild().getFinalName() + "-" + classifier + ".jar";
        }
        if (xmlFileName == null) {
            xmlFileName = project.getBuild().getFinalName() + "-" + classifier + ".xml";
        }
    }

    protected void parseStandaloneConfiguration() throws MojoExecutionException {
        standalone = true;

        if (srcPath == null) {
            srcDir = new File(".");
        }
        else {
            srcDir = new File(srcPath);
        }
        srcFilePath = FileUtils.normalize(srcDir.getAbsolutePath());

        try {
            if (!packageFonts && deploymentPath == null) {
                deploymentPath = srcDir.getCanonicalPath();
                if (!deploymentPath.endsWith(File.separator)) {
                    deploymentPath += File.separator;
                }
            }
            else {
                deploymentPath = "";
                srcDir.getCanonicalPath();
            }
        }
        catch (final IOException exp) {
            throw new MojoExecutionException("Unable to find path: " + srcDir.getAbsolutePath(), exp);
        }

        final File basedir;
        try {
            basedir = new File(".").getCanonicalFile();
        }
        catch (final IOException exp) {
            throw new MojoExecutionException("Unable to get current directory", exp);
        }

        if (outputPath == null) {
            workDirectory = new File(basedir, "target");
            outputPath = workDirectory.getAbsolutePath();
            workDirectory.mkdirs();
            project.getBuild().setOutputDirectory(outputPath);
            project.getBuild().setDirectory(outputPath);
            jarOutputDir = workDirectory;
        }
        if (jarName == null) {
            jarName = "fonts.jar";
        }

        if (xmlFileName == null) {
            xmlFileName = "jasper_fonts.xml";
        }
    }
}

