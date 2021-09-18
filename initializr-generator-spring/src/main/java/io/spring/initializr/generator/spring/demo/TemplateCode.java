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

package io.spring.initializr.generator.spring.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;

public class TemplateCode {

	private String templateName;

	private Function<Path, Path> projectRootFactory;

	private MustacheTemplateRenderer mustacheTemplateRenderer;

	private Supplier<Map<String, Object>> modelFactory;

	public TemplateCode(Function<Path, Path> projectRootFactory, String templateName,
			MustacheTemplateRenderer mustacheTemplateRenderer, Supplier<Map<String, Object>> modelFactory) {
		this.projectRootFactory = projectRootFactory;
		this.templateName = templateName;
		this.mustacheTemplateRenderer = mustacheTemplateRenderer;
		this.modelFactory = modelFactory;
	}

	public void write(Path projectRoot) throws IOException {
		Path output = this.projectRootFactory.apply(projectRoot);
		Files.createDirectories(output.getParent());
		Files.createFile(output);
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(output))) {
			writer.println(this.mustacheTemplateRenderer.render(this.templateName, this.modelFactory.get()));
		}
	}

}
