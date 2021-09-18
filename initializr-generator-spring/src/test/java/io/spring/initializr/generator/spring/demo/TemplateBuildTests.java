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

package io.spring.initializr.generator.spring.demo;

import java.io.IOException;
import java.nio.file.Path;

import io.spring.initializr.generator.demo.Template;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectGlobalModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TemplateBuildTests {

	@Test
	void write(@TempDir Path projectRoot) throws IOException {
		MutableProjectDescription projectDescription = new MutableProjectDescription();
		projectDescription.setPackageName("com.dzq");
		projectDescription.setLanguage(new JavaLanguage());
		TemplateBuild templateBuild = new TemplateBuild("classpath:/templates",
				() -> new ProjectGlobalModel(projectDescription).getModel());
		templateBuild.addTemplateCode(projectDescription, Template.create("mybatis", "mybatis", "code", "dao"));
		templateBuild.write(projectRoot);
	}

}
