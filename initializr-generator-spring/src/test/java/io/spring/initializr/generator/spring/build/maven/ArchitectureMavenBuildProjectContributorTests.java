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

package io.spring.initializr.generator.spring.build.maven;

import java.io.IOException;
import java.nio.file.Path;

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenModule;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchitectureMavenBuildProjectContributorTests {

	@Test
	void mavenBuildIsContributedInProjectStructure(@TempDir Path projectDir) throws IOException {
		MavenBuild build = new MavenBuild();
		build.settings().name("demo");
		MavenModule mavenModule = new MavenModule.MavenModuleBuilder("test", build).build();
		build.modules().addModuleMavenBuild(mavenModule);
		new ArchitectureMavenBuildProjectContributor(build, IndentingWriterFactory.withDefaultSettings())
				.contribute(projectDir);
		Path pomFile = projectDir.resolve("demo-test/pom.xml");
		assertThat(pomFile).isRegularFile();
	}

	@Test
	void mulitMavenBuildInProjectStructure(@TempDir Path projectDir) throws IOException {
		MavenBuild build = new MavenBuild();
		build.settings().name("demo");
		MavenModule testMavenModule = new MavenModule.MavenModuleBuilder("test", build).build();
		build.modules().addModuleMavenBuild(testMavenModule);

		MavenModule apiMavenModule = new MavenModule.MavenModuleBuilder("api", build).build();
		build.modules().addModuleMavenBuild(apiMavenModule);

		new ArchitectureMavenBuildProjectContributor(build, IndentingWriterFactory.withDefaultSettings())
				.contribute(projectDir);
		Path testPomFile = projectDir.resolve("demo-test/pom.xml");
		assertThat(testPomFile).isRegularFile();
		Path apiPomFile = projectDir.resolve("demo-api/pom.xml");
		assertThat(apiPomFile).isRegularFile();
	}

	@Test
	void mulitPomInProjectStructure(@TempDir Path projectDir) throws IOException {
		MavenBuild build = new MavenBuild();
		build.settings().name("demo");
		build.settings().artifact("test");
		MavenModule testMavenModule = new MavenModule.MavenModuleBuilder("test", build).build();
		build.modules().addModuleMavenBuild(testMavenModule);
		testMavenModule.getModuleMavenBuild().dependencies().add("test", "com.dzq", "test", DependencyScope.COMPILE);
		MavenModule apiMavenModule = new MavenModule.MavenModuleBuilder("api", build).build();
		build.modules().addModuleMavenBuild(apiMavenModule);
		apiMavenModule.getModuleMavenBuild().dependencies().add("api", "com.dzq", "api", DependencyScope.COMPILE);
		new ArchitectureMavenBuildProjectContributor(build, IndentingWriterFactory.withDefaultSettings())
				.contribute(projectDir);
		Path testPomFile = projectDir.resolve("demo-test/pom.xml");
		assertThat(testPomFile).isRegularFile();
		Path apiPomFile = projectDir.resolve("demo-api/pom.xml");
		assertThat(apiPomFile).isRegularFile();
	}

}
