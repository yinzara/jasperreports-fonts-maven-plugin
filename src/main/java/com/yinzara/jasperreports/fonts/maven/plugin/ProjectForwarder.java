/*
 * The MIT License
 *
 * Copyright 2013 Matthew.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
	
	public File getParentFile() {
		return delegate().getParentFile();
	}

	public void setParentFile(File parentFile) {
		delegate().setParentFile(parentFile);
	}

	public Set<Artifact> createArtifacts(ArtifactFactory artifactFactory, String inheritedScope, ArtifactFilter filter) throws InvalidDependencyVersionException {
		return delegate().createArtifacts(artifactFactory, inheritedScope, filter);
	}

	public String getModulePathAdjustment(MavenProject moduleProject) throws IOException {
		return delegate().getModulePathAdjustment(moduleProject);
	}

	public Artifact getArtifact() {
		return delegate().getArtifact();
	}

	public void setArtifact(Artifact artifact) {
		delegate().setArtifact(artifact);
	}

	public Model getModel() {
		return delegate().getModel();
	}

	public MavenProject getParent() {
		return delegate().getParent();
	}

	public void setParent(MavenProject parent) {
		delegate().setParent(parent);
	}

	public boolean hasParent() {
		return delegate().hasParent();
	}

	public File getFile() {
		return delegate().getFile();
	}

	public void setFile(File file) {
		delegate().setFile(file);
	}

	public File getBasedir() {
		return delegate().getBasedir();
	}

	public void setDependencies(List<Dependency> dependencies) {
		delegate().setDependencies(dependencies);
	}

	public List<Dependency> getDependencies() {
		return delegate().getDependencies();
	}

	public DependencyManagement getDependencyManagement() {
		return delegate().getDependencyManagement();
	}

	public void addCompileSourceRoot(String path) {
		delegate().addCompileSourceRoot(path);
	}

	public void addScriptSourceRoot(String path) {
		delegate().addScriptSourceRoot(path);
	}

	public void addTestCompileSourceRoot(String path) {
		delegate().addTestCompileSourceRoot(path);
	}

	public List<String> getCompileSourceRoots() {
		return delegate().getCompileSourceRoots();
	}

	public List<String> getScriptSourceRoots() {
		return delegate().getScriptSourceRoots();
	}

	public List<String> getTestCompileSourceRoots() {
		return delegate().getTestCompileSourceRoots();
	}

	public List<String> getCompileClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getCompileClasspathElements();
	}

	public List<Artifact> getCompileArtifacts() {
		return delegate().getCompileArtifacts();
	}

	public List<Dependency> getCompileDependencies() {
		return delegate().getCompileDependencies();
	}

	public List<String> getTestClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getTestClasspathElements();
	}

	public List<Artifact> getTestArtifacts() {
		return delegate().getTestArtifacts();
	}

	public List<Dependency> getTestDependencies() {
		return delegate().getTestDependencies();
	}

	public List<String> getRuntimeClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getRuntimeClasspathElements();
	}

	public List<Artifact> getRuntimeArtifacts() {
		return delegate().getRuntimeArtifacts();
	}

	public List<Dependency> getRuntimeDependencies() {
		return delegate().getRuntimeDependencies();
	}

	public List<String> getSystemClasspathElements() throws DependencyResolutionRequiredException {
		return delegate().getSystemClasspathElements();
	}

	public List<Artifact> getSystemArtifacts() {
		return delegate().getSystemArtifacts();
	}

	public List<Dependency> getSystemDependencies() {
		return delegate().getSystemDependencies();
	}

	public void setModelVersion(String pomVersion) {
		delegate().setModelVersion(pomVersion);
	}

	public String getModelVersion() {
		return delegate().getModelVersion();
	}

	public String getId() {
		return delegate().getId();
	}

	public void setGroupId(String groupId) {
		delegate().setGroupId(groupId);
	}

	public String getGroupId() {
		return delegate().getGroupId();
	}

	public void setArtifactId(String artifactId) {
		delegate().setArtifactId(artifactId);
	}

	public String getArtifactId() {
		return delegate().getArtifactId();
	}

	public void setName(String name) {
		delegate().setName(name);
	}

	public String getName() {
		return delegate().getName();
	}

	public void setVersion(String version) {
		delegate().setVersion(version);
	}

	public String getVersion() {
		return delegate().getVersion();
	}

	public String getPackaging() {
		return delegate().getPackaging();
	}

	public void setPackaging(String packaging) {
		delegate().setPackaging(packaging);
	}

	public void setInceptionYear(String inceptionYear) {
		delegate().setInceptionYear(inceptionYear);
	}

	public String getInceptionYear() {
		return delegate().getInceptionYear();
	}

	public void setUrl(String url) {
		delegate().setUrl(url);
	}

	public String getUrl() {
		return delegate().getUrl();
	}

	public Prerequisites getPrerequisites() {
		return delegate().getPrerequisites();
	}

	public void setIssueManagement(IssueManagement issueManagement) {
		delegate().setIssueManagement(issueManagement);
	}

	public CiManagement getCiManagement() {
		return delegate().getCiManagement();
	}

	public void setCiManagement(CiManagement ciManagement) {
		delegate().setCiManagement(ciManagement);
	}

	public IssueManagement getIssueManagement() {
		return delegate().getIssueManagement();
	}

	public void setDistributionManagement(DistributionManagement distributionManagement) {
		delegate().setDistributionManagement(distributionManagement);
	}

	public DistributionManagement getDistributionManagement() {
		return delegate().getDistributionManagement();
	}

	public void setDescription(String description) {
		delegate().setDescription(description);
	}

	public String getDescription() {
		return delegate().getDescription();
	}

	public void setOrganization(Organization organization) {
		delegate().setOrganization(organization);
	}

	public Organization getOrganization() {
		return delegate().getOrganization();
	}

	public void setScm(Scm scm) {
		delegate().setScm(scm);
	}

	public Scm getScm() {
		return delegate().getScm();
	}

	public void setMailingLists(List<MailingList> mailingLists) {
		delegate().setMailingLists(mailingLists);
	}

	public List<MailingList> getMailingLists() {
		return delegate().getMailingLists();
	}

	public void addMailingList(MailingList mailingList) {
		delegate().addMailingList(mailingList);
	}

	public void setDevelopers(List<Developer> developers) {
		delegate().setDevelopers(developers);
	}

	public List<Developer> getDevelopers() {
		return delegate().getDevelopers();
	}

	public void addDeveloper(Developer developer) {
		delegate().addDeveloper(developer);
	}

	public void setContributors(List<Contributor> contributors) {
		delegate().setContributors(contributors);
	}

	public List<Contributor> getContributors() {
		return delegate().getContributors();
	}

	public void addContributor(Contributor contributor) {
		delegate().addContributor(contributor);
	}

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
