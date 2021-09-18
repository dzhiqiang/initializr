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

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectGlobalModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateCodeTests {

	@Test
	void write(@TempDir Path path) throws IOException {
		MutableProjectDescription description = new MutableProjectDescription();
		description.setPackageName("com.dzq");
		description.setLanguage(new JavaLanguage());
		MustacheTemplateRenderer templateRenderer = new MustacheTemplateRenderer("classpath:/templates");
		TemplateCode templateCode = new TemplateCode(
				((projectRoot) -> projectRoot.resolve("src/main/java/789Mapper.java")),
				"mybatis/demos/mybatis/UserMapper.java", templateRenderer,
				() -> new ProjectGlobalModel(description).getModel());
		templateCode.write(path);
		Path code = path.resolve("src/main/java/789Mapper.java");
		assertThat(code).exists();
	}

}
