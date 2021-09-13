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

package io.spring.initializr.generator.project.contributor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

/**
 * {@link ProjectContributor} :所有生成在main子项目确认子项目目录.
 *
 * @author Duan zhiqiang
 */
public class ModuleProjectContributor implements ProjectContributor {

	private ProjectContributor projectContributor;

	private Supplier<String> moduleRootFactory;

	public ModuleProjectContributor(ProjectContributor projectContributor, Supplier<String> moduleRootFactory) {
		this.projectContributor = projectContributor;
		this.moduleRootFactory = moduleRootFactory;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		String moduleRoot = this.moduleRootFactory.get();
		if (StringUtils.hasText(moduleRoot)) {
			projectRoot = projectRoot.resolve(moduleRoot);
		}
		this.projectContributor.contribute(projectRoot);
	}

}
