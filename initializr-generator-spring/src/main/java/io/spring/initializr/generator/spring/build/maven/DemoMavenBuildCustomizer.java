/*
 * Copyright 2012-2021 the original author or authors.
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

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenModule;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

import org.springframework.core.Ordered;

public class DemoMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {

	private ProjectDescription description;

	public DemoMavenBuildCustomizer(ProjectDescription description) {
		this.description = description;
	}

	@Override
	public void customize(MavenBuild build) {
		this.description.getDemos().values().forEach((demo) -> demo.getTemplate().values().forEach((template) -> {
			MavenModule mavenModule = build.modules().getMavenModule(template.getModule());
			if (mavenModule != null && mavenModule.getModuleMavenBuild() != null) {
				template.getDependencies().forEach(
						(id, dependency) -> mavenModule.getModuleMavenBuild().dependencies().add(id, dependency));
			}
			else {
				template.getDependencies().forEach((id, dependency) -> build.dependencies().add(id, dependency));
			}
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 2;
	}

}
