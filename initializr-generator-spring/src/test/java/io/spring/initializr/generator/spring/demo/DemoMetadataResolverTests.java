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

import java.util.Arrays;

import io.spring.initializr.metadata.Demo;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.Template;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DemoMetadataResolverTests {

	@Test
	void resolveDemo() {
		InitializrMetadata metadata = createSampleMetadata();
		DemoMetadataResolver resolver = new DemoMetadataResolver(metadata);
		Assertions.assertThat(resolver.resolveDemo("mybatis")).isNotNull();
	}

	@Test
	void resolveTemplate() {
		InitializrMetadata metadata = createTemplateMetadata();
		DemoMetadataResolver resolver = new DemoMetadataResolver(metadata);
		Assertions.assertThat(resolver.resolveTemplate("user")).isNotNull();
	}

	private InitializrMetadata createTemplateMetadata() {
		InitializrMetadata metadata = new InitializrMetadata();
		Template template = Template.create("user", "code", "api");
		metadata.getConfiguration().getEnv().getTemplates().put(template.getName(), template);
		metadata.validate();
		return metadata;
	}

	private InitializrMetadata createSampleMetadata() {
		InitializrMetadata metadata = new InitializrMetadata();
		metadata.getDemos().merge(Arrays.asList(Demo.withId("mybatis", "mybatis", "mybatis示例")));
		metadata.validate();
		return metadata;
	}

}
