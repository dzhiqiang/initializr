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

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchitectureStartMavenBuildCustomizerTests {

	@Test
	void testAddStart() {
		MavenBuild mavenBuild = createMavenBuild();
		mavenBuild.dependencies().add("test", "com.dzq", "test", DependencyScope.COMPILE);
		ArchitectureStartMavenBuildCustomizer customizer = new ArchitectureStartMavenBuildCustomizer();
		customizer.customize(mavenBuild);
		assertThat(mavenBuild.dependencies().isEmpty()).isTrue();
		assertThat(mavenBuild.modules().getMavenModule("start").getModuleMavenBuild().dependencies().items())
				.hasSize(1);
	}

	private MavenBuild createMavenBuild() {
		MavenBuild mavenBuild = new MavenBuild();
		MavenModule.MavenModuleBuilder mavenModule = new MavenModule.MavenModuleBuilder("start", mavenBuild);
		mavenBuild.modules().addModuleMavenBuild(mavenModule.build());
		return mavenBuild;
	}

}
