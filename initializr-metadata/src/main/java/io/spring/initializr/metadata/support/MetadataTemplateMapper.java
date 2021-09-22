/*
 * Copyright 2012-2020 the original author or authors.
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

package io.spring.initializr.metadata.support;

import java.util.function.Function;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.metadata.Template;

public final class MetadataTemplateMapper {

	private MetadataTemplateMapper() {

	}

	public static io.spring.initializr.generator.demo.Template getTemplate(String id, Template template,
			Function<String, Dependency> function) {
		io.spring.initializr.generator.demo.Template.TemplateBuilder templateBuilder = new io.spring.initializr.generator.demo.Template.TemplateBuilder(
				id, template.getName(), template.getTemplateType(), template.getType());
		template.getDependencies()
				.forEach((dependency) -> templateBuilder.addDependency(dependency, function.apply(dependency)));
		return templateBuilder.build();
	}

}
