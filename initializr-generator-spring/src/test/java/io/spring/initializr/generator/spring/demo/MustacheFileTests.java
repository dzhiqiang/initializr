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

import java.util.HashMap;
import java.util.Map;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MustacheFileTests {

	private final MustacheTemplateRenderer templateRenderer = new MustacheTemplateRenderer("classpath:/templates");

	@Test
	void userMapper() {
		Map<String, Object> model = new HashMap<>();
		model.put("packageName", "com.dzq");
		String text = this.templateRenderer.render("mybatis/demos/mybatis/UserMapper.java", model);
		assertThat(text).contains("package com.dzq.dao.dataobject");
	}

	@Test
	void userXml() {
		Map<String, Object> model = new HashMap<>();
		model.put("packageName", "com.dzq");
		String text = this.templateRenderer.render("mybatisXml/user.xml", model);
		assertThat(text).contains("<mapper namespace=\"com.dzq.dao.mapper.UserMapper\">");
	}

}
