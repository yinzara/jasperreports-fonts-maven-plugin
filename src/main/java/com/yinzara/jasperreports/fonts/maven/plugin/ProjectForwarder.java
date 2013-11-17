/*
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

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.model.Build;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.Contributor;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Developer;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.Extension;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.License;
import org.apache.maven.model.MailingList;
import org.apache.maven.model.Model;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Prerequisites;
import org.apache.maven.model.Profile;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.Reporting;
import org.apache.maven.model.Repository;
import org.apache.maven.model.Resource;
import org.apache.maven.model.Scm;
import org.apache.maven.project.DuplicateArtifactAttachmentException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.repository.RemoteRepository;

@SuppressWarnings("all")
public abstract class ProjectForwarder extends MavenProject {

	protected abstract MavenProject delegate(); 
	
        @Override
	public File getParentFile() {
		return delegate().getParentFile();
	}

        @Override
	public void setParentFile(File parentFile) {
		delegate().setParentFile(parentFile);
	}

        @Override
	public Set<Artifact> createArtifacts(ArtifactFactory artifactFactory, String inheritedScope, ArtifactFilter filter) throws InvalidDependencyVersionException {
		return delegate().createArtifacts(artifactFactory, inheritedScope, filter);
	}

        @Override
	public String getModulePathAdjustment(MavenProject moduleProject) throws IOException {
		return delegate().getModulePathAdjustment(moduleProject);
	}

        @Override
	public Artifact getArtifact() {
		return delegate().getArtifact();
	}

        @Override
	public void setArtifact(Artifact artifact) {
		delegate().setArtifact(artifact);
	}

        @Override
	public Model getModel() {
		return delegate().getModel();
	}

        @Override
	public MavenProject getParent() {
		return delegate().getParent();
	}

        @Override
	public void setParent(MavenProject parent) {
		delegate().setParent(parent);
	}

        @Override
	public boolean hasParent() {
		return delegate().hasParent();
	}

        @Override
	public File getFile() {
		return delegate().getFile();
	}

        @Override
	public void setFile(File file) {
		delegate().setFile(file);
	}

        @Override
	public File getBasedir() {
		return delegate().getBasedir();
	}

        @Override
	public void setDependencies(List<Dependency> dependencies) {
		delegate().setDependencies(dependencies);
	}

        @Override
	public List<Dependency> getDependencies() {
		return delegate().getDependencies();
	}

        @Override
	public DependencyManagement getDependencyManagement() {
		return delegate().getDependencyManagement();
	}

        @Override
	public void addCompileSourceRoot(String path) {
		delegate().addCompileSourceRoot(path);
	}

        @Override
	public void addScriptSourceRoot(String path) {
		delegate().addScriptSourceRoot(path);
	}

        @Override
	public void addTestCompileSourceRoot(String path) {
		delegate().addTestCompileSourceRoot(path);
	}

        @Override
	public List<String> getCompileSourceRoots() {
		return delegate().getCompileSourceRoots();
	}

        @Override
	public List<String> getScriptSourceRoots() {
		return delegate().getScriptSourceRoots();
	}

        @Override
	public List<String> getTestCompileSourceRoots() {
		return delegate().getTestCompileSourceRoots();
	}

        @Override
	public List<String> getCompileClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getCompileClasspathElements();
	}

        @Override
	public List<Artifact> getCompileArtifacts() {
		return delegate().getCompileArtifacts();
	}

        @Override
	public List<Dependency> getCompileDependencies() {
		return delegate().getCompileDependencies();
	}

        @Override
	public List<String> getTestClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getTestClasspathElements();
	}

        @Override
	public List<Artifact> getTestArtifacts() {
		return delegate().getTestArtifacts();
	}

        @Override
	public List<Dependency> getTestDependencies() {
		return delegate().getTestDependencies();
	}

        @Override
	public List<String> getRuntimeClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getRuntimeClasspathElements();
	}

        @Override
	public List<Artifact> getRuntimeArtifacts() {
		return delegate().getRuntimeArtifacts();
	}

        @Override
	public List<Dependency> getRuntimeDependencies() {
		return delegate().getRuntimeDependencies();
	}

        @Override
	public List<String> getSystemClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getSystemClasspathElements();
	}

        @Override
	public List<Artifact> getSystemArtifacts() {
		return delegate().getSystemArtifacts();
	}

        @Override
	public List<Dependency> getSystemDependencies() {
		return delegate().getSystemDependencies();
	}

        @Override
	public void setModelVersion(String pomVersion) {
		delegate().setModelVersion(pomVersion);
	}

        @Override
	public String getModelVersion() {
		return delegate().getModelVersion();
	}
        
        @Override
	public String getId() {
		return delegate().getId();
	}

        @Override
	public void setGroupId(String groupId) {
		delegate().setGroupId(groupId);
	}

        @Override
	public String getGroupId() {
		return delegate().getGroupId();
	}

        @Override
	public void setArtifactId(String artifactId) {
		delegate().setArtifactId(artifactId);
	}

        @Override
	public String getArtifactId() {
		return delegate().getArtifactId();
	}

        @Override
	public void setName(String name) {
		delegate().setName(name);
	}

        @Override
	public String getName() {
		return delegate().getName();
	}

        @Override
	public void setVersion(String version) {
		delegate().setVersion(version);
	}
        
        @Override
	public String getVersion() {
		return delegate().getVersion();
	}

        @Override
	public String getPackaging() {
		return delegate().getPackaging();
	}

        @Override
	public void setPackaging(String packaging) {
		delegate().setPackaging(packaging);
	}

        @Override
	public void setInceptionYear(String inceptionYear) {
		delegate().setInceptionYear(inceptionYear);
	}

        @Override
	public String getInceptionYear() {
		return delegate().getInceptionYear();
	}

        @Override
	public void setUrl(String url) {
		delegate().setUrl(url);
	}

        @Override
	public String getUrl() {
		return delegate().getUrl();
	}

        @Override
	public Prerequisites getPrerequisites() {
		return delegate().getPrerequisites();
	}

        @Override
	public void setIssueManagement(IssueManagement issueManagement) {
		delegate().setIssueManagement(issueManagement);
	}

        @Override
	public CiManagement getCiManagement() {
		return delegate().getCiManagement();
	}

        @Override
	public void setCiManagement(CiManagement ciManagement) {
		delegate().setCiManagement(ciManagement);
	}

        @Override
	public IssueManagement getIssueManagement() {
		return delegate().getIssueManagement();
	}

        @Override
	public void setDistributionManagement(DistributionManagement distributionManagement) {
		delegate().setDistributionManagement(distributionManagement);
	}

        @Override
	public DistributionManagement getDistributionManagement() {
		return delegate().getDistributionManagement();
	}

        @Override
	public void setDescription(String description) {
		delegate().setDescription(description);
	}

        @Override
	public String getDescription() {
		return delegate().getDescription();
	}

        @Override
	public void setOrganization(Organization organization) {
		delegate().setOrganization(organization);
	}

        @Override
	public Organization getOrganization() {
		return delegate().getOrganization();
	}

        @Override
	public void setScm(Scm scm) {
		delegate().setScm(scm);
	}

        @Override
	public Scm getScm() {
		return delegate().getScm();
	}

        @Override
	public void setMailingLists(List<MailingList> mailingLists) {
		delegate().setMailingLists(mailingLists);
	}

        @Override
	public List<MailingList> getMailingLists() {
		return delegate().getMailingLists();
	}

        @Override
	public void addMailingList(MailingList mailingList) {
		delegate().addMailingList(mailingList);
	}

        @Override
	public void setDevelopers(List<Developer> developers) {
		delegate().setDevelopers(developers);
	}

        @Override
	public List<Developer> getDevelopers() {
		return delegate().getDevelopers();
	}

        @Override
	public void addDeveloper(Developer developer) {
		delegate().addDeveloper(developer);
	}

        @Override
	public void setContributors(List<Contributor> contributors) {
		delegate().setContributors(contributors);
	}

        @Override
	public List<Contributor> getContributors() {
		return delegate().getContributors();
	}

        @Override
	public void addContributor(Contributor contributor) {
		delegate().addContributor(contributor);
	}

        @Override
	public void setBuild(Build build) {
		delegate().setBuild(build);
	}

	public Build getBuild() {
		return delegate().getBuild();
	}

	public List<Resource> getResources() {
		return delegate().getResources();
	}

	public List<Resource> getTestResources() {
		return delegate().getTestResources();
	}

	public void addResource(Resource resource) {
		delegate().addResource(resource);
	}

	public void addTestResource(Resource testResource) {
		delegate().addTestResource(testResource);
	}

	public void setReporting(Reporting reporting) {
		delegate().setReporting(reporting);
	}

	public Reporting getReporting() {
		return delegate().getReporting();
	}

	public void setLicenses(List<License> licenses) {
		delegate().setLicenses(licenses);
	}

	public List<License> getLicenses() {
		return delegate().getLicenses();
	}

	public void addLicense(License license) {
		delegate().addLicense(license);
	}

	public void setArtifacts(Set<Artifact> artifacts) {
		delegate().setArtifacts(artifacts);
	}

	public Set<Artifact> getArtifacts() {
		return delegate().getArtifacts();
	}

	public Map<String, Artifact> getArtifactMap() {
		return delegate().getArtifactMap();
	}

	public void setPluginArtifacts(Set<Artifact> pluginArtifacts) {
		delegate().setPluginArtifacts(pluginArtifacts);
	}

	public Set<Artifact> getPluginArtifacts() {
		return delegate().getPluginArtifacts();
	}

	public Map<String, Artifact> getPluginArtifactMap() {
		return delegate().getPluginArtifactMap();
	}

	public void setReportArtifacts(Set<Artifact> reportArtifacts) {
		delegate().setReportArtifacts(reportArtifacts);
	}

	public Set<Artifact> getReportArtifacts() {
		return delegate().getReportArtifacts();
	}

	public Map<String, Artifact> getReportArtifactMap() {
		return delegate().getReportArtifactMap();
	}

	public void setExtensionArtifacts(Set<Artifact> extensionArtifacts) {
		delegate().setExtensionArtifacts(extensionArtifacts);
	}

	public Set<Artifact> getExtensionArtifacts() {
		return delegate().getExtensionArtifacts();
	}

	public Map<String, Artifact> getExtensionArtifactMap() {
		return delegate().getExtensionArtifactMap();
	}

	public void setParentArtifact(Artifact parentArtifact) {
		delegate().setParentArtifact(parentArtifact);
	}

	public Artifact getParentArtifact() {
		return delegate().getParentArtifact();
	}

	public List<Repository> getRepositories() {
		return delegate().getRepositories();
	}

	public List<ReportPlugin> getReportPlugins() {
		return delegate().getReportPlugins();
	}

	public List<Plugin> getBuildPlugins() {
		return delegate().getBuildPlugins();
	}

	public List<String> getModules() {
		return delegate().getModules();
	}

	public PluginManagement getPluginManagement() {
		return delegate().getPluginManagement();
	}

	public void setRemoteArtifactRepositories(List<ArtifactRepository> remoteArtifactRepositories) {
		delegate().setRemoteArtifactRepositories(remoteArtifactRepositories);
	}

	public List<ArtifactRepository> getRemoteArtifactRepositories() {
		return delegate().getRemoteArtifactRepositories();
	}

	public void setPluginArtifactRepositories(List<ArtifactRepository> pluginArtifactRepositories) {
		delegate().setPluginArtifactRepositories(pluginArtifactRepositories);
	}

	public List<ArtifactRepository> getPluginArtifactRepositories() {
		return delegate().getPluginArtifactRepositories();
	}

	public ArtifactRepository getDistributionManagementArtifactRepository() {
		return delegate().getDistributionManagementArtifactRepository();
	}

	public List<Repository> getPluginRepositories() {
		return delegate().getPluginRepositories();
	}

        @Override
	public List<RemoteRepository> getRemoteProjectRepositories() {
		return delegate().getRemoteProjectRepositories();
	}

	public List<RemoteRepository> getRemotePluginRepositories() {
		return delegate().getRemotePluginRepositories();
	}

        @Override
	public void setActiveProfiles(List<Profile> activeProfiles) {
		delegate().setActiveProfiles(activeProfiles);
	}

	public List<Profile> getActiveProfiles() {
		return delegate().getActiveProfiles();
	}

	public void setInjectedProfileIds(String source, List<String> injectedProfileIds) {
		delegate().setInjectedProfileIds(source, injectedProfileIds);
	}

	public Map<String, List<String>> getInjectedProfileIds() {
		return delegate().getInjectedProfileIds();
	}

	public void addAttachedArtifact(Artifact artifact) throws DuplicateArtifactAttachmentException {
		delegate().addAttachedArtifact(artifact);
	}

	public List<Artifact> getAttachedArtifacts() {
		return delegate().getAttachedArtifacts();
	}

	public Xpp3Dom getGoalConfiguration(String pluginGroupId, String pluginArtifactId, String executionId, String goalId) {
		return delegate().getGoalConfiguration(pluginGroupId, pluginArtifactId, executionId, goalId);
	}

	public Xpp3Dom getReportConfiguration(String pluginGroupId, String pluginArtifactId, String reportSetId) {
		return delegate().getReportConfiguration(pluginGroupId, pluginArtifactId, reportSetId);
	}

	public MavenProject getExecutionProject() {
		return delegate().getExecutionProject();
	}

	public void setExecutionProject(MavenProject executionProject) {
		delegate().setExecutionProject(executionProject);
	}

	public List<MavenProject> getCollectedProjects() {
		return delegate().getCollectedProjects();
	}

	public void setCollectedProjects(List<MavenProject> collectedProjects) {
		delegate().setCollectedProjects(collectedProjects);
	}

	public Set<Artifact> getDependencyArtifacts() {
		return delegate().getDependencyArtifacts();
	}

	public void setDependencyArtifacts(Set<Artifact> dependencyArtifacts) {
		delegate().setDependencyArtifacts(dependencyArtifacts);
	}

	public void setReleaseArtifactRepository(ArtifactRepository releaseArtifactRepository) {
		delegate().setReleaseArtifactRepository(releaseArtifactRepository);
	}

	public void setSnapshotArtifactRepository(ArtifactRepository snapshotArtifactRepository) {
		delegate().setSnapshotArtifactRepository(snapshotArtifactRepository);
	}

	public void setOriginalModel(Model originalModel) {
		delegate().setOriginalModel(originalModel);
	}

	public Model getOriginalModel() {
		return delegate().getOriginalModel();
	}

	public void setManagedVersionMap(Map<String, Artifact> map) {
		delegate().setManagedVersionMap(map);
	}

	public Map<String, Artifact> getManagedVersionMap() {
		return delegate().getManagedVersionMap();
	}

	public boolean equals(Object other) {
		return delegate().equals(other);
	}

	public int hashCode() {
		return delegate().hashCode();
	}

	public List<Extension> getBuildExtensions() {
		return delegate().getBuildExtensions();
	}

	public void addProjectReference(MavenProject project) {
		delegate().addProjectReference(project);
	}

	public void attachArtifact(String type, String classifier, File file) {
		delegate().attachArtifact(type, classifier, file);
	}

	public Properties getProperties() {
		return delegate().getProperties();
	}

	public List<String> getFilters() {
		return delegate().getFilters();
	}

	public Map<String, MavenProject> getProjectReferences() {
		return delegate().getProjectReferences();
	}

	public boolean isExecutionRoot() {
		return delegate().isExecutionRoot();
	}

	public void setExecutionRoot(boolean executionRoot) {
		delegate().setExecutionRoot(executionRoot);
	}

	public String getDefaultGoal() {
		return delegate().getDefaultGoal();
	}

	public Plugin getPlugin(String pluginKey) {
		return delegate().getPlugin(pluginKey);
	}

	public String toString() {
		return delegate().toString();
	}

	public void writeModel(Writer writer) throws IOException {
		delegate().writeModel(writer);
	}

	public void writeOriginalModel(Writer writer) throws IOException {
		delegate().writeOriginalModel(writer);
	}

	public MavenProject clone() {
		return delegate().clone();
	}

	public Artifact replaceWithActiveArtifact(Artifact pluginArtifact) {
		return delegate().replaceWithActiveArtifact(pluginArtifact);
	}

	public void setContextValue(String key, Object value) {
		delegate().setContextValue(key, value);
	}

	public Object getContextValue(String key) {
		return delegate().getContextValue(key);
	}

	public void setClassRealm(ClassRealm classRealm) {
		delegate().setClassRealm(classRealm);
	}

	public ClassRealm getClassRealm() {
		return delegate().getClassRealm();
	}

	public void setExtensionDependencyFilter(DependencyFilter extensionDependencyFilter) {
		delegate().setExtensionDependencyFilter(extensionDependencyFilter);
	}

	public DependencyFilter getExtensionDependencyFilter() {
		return delegate().getExtensionDependencyFilter();
	}

	public void setResolvedArtifacts(Set<Artifact> artifacts) {
		delegate().setResolvedArtifacts(artifacts);
	}

	public void setArtifactFilter(ArtifactFilter artifactFilter) {
		delegate().setArtifactFilter(artifactFilter);
	}

	public boolean hasLifecyclePhase(String phase) {
		return delegate().hasLifecyclePhase(phase);
	}

	public void addLifecyclePhase(String lifecyclePhase) {
		delegate().addLifecyclePhase(lifecyclePhase);
	}

	public ProjectBuildingRequest getProjectBuildingRequest() {
		return delegate().getProjectBuildingRequest();
	}

	public void setProjectBuildingRequest(ProjectBuildingRequest projectBuildingRequest) {
		delegate().setProjectBuildingRequest(projectBuildingRequest);
	}

}
