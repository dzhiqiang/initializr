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

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenModule;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

import org.springframework.core.Ordered;

/**
 * Customize the {@link MavenBuild} 用于生成多pom文件.
 *
 * @author Duan Zhiqiang
 */
public class ArchitectureStartMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {

	/**
	 * 默认的开始模块.
	 */
	public static final String DEFAULT_START = "start";

	@Override
	public void customize(MavenBuild build) {

		MavenModule startMavenBuild = build.getMavenModuleByName(DEFAULT_START);
		build.dependencies().ids().forEach(
				(key) -> startMavenBuild.getModuleMavenBuild().dependencies().add(key, build.dependencies().get(key)));
		build.dependencies().clear();
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
