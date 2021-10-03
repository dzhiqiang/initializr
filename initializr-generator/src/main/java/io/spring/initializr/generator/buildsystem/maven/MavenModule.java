/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.buildsystem.maven;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.version.VersionReference;

public class MavenModule {

	private String name;

	private String moduleName;

	private String artifactId;

	private MavenBuild moduleMavenBuild;

	public MavenModule(String name, String moduleName, String artifactId, MavenBuild moduleMavenBuild) {
		this.name = name;
		this.moduleName = moduleName;
		this.artifactId = artifactId;
		this.moduleMavenBuild = moduleMavenBuild;
	}

	public String getName() {
		return this.name;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getArtifactId() {
		return this.artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public MavenBuild getModuleMavenBuild() {
		return this.moduleMavenBuild;
	}

	public void setModuleMavenBuild(MavenBuild moduleMavenBuild) {
		this.moduleMavenBuild = moduleMavenBuild;
	}

	public static class MavenModuleBuilder {

		private String name;

		private String moduleName;

		private String artifactId;

		private MavenBuild parent;

		private MavenModule mavenModule;

		public MavenModuleBuilder(String name, MavenBuild parent) {
			this.name = name;
			this.parent = parent;
			this.moduleName = moduleName(name);
			this.artifactId = artifactId(name);
			MavenBuild moduleMavenBuild = new MavenBuild();
			moduleMavenBuild.settings().parent(this.parent.getSettings().getGroup(),
					this.parent.getSettings().getArtifact(), this.parent.getSettings().getVersion());
			moduleMavenBuild.settings().coordinates(this.parent.getSettings().getGroup(), this.artifactId);
			this.mavenModule = new MavenModule(this.name, this.moduleName, this.artifactId, moduleMavenBuild);
		}

		public void addDependency(String dependency) {
			String id = artifactId(dependency);
			this.mavenModule.getModuleMavenBuild().dependencies().add(id,
					Dependency.withCoordinates(this.parent.getSettings().getGroup(), id)
							.version(VersionReference.ofValue("${project.version}")));
		}

		public MavenModule build() {
			return this.mavenModule;
		}

		private String moduleName(String name) {
			return this.parent.getSettings().getName() + "-" + name;
		}

		private String artifactId(String name) {
			return this.parent.getSettings().getArtifact() + "-" + name;
		}

	}

}
