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

package io.spring.initializr.metadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Template {

	/**
	 * TemplateType code.
	 */
	public static final String TEMPLATE_TYPE_CODE = "code";

	/**
	 * TemplateType static.
	 */
	public static final String TEMPLATE_TYPE_STATIC = "static";

	private String name;

	private String templateType;

	private String type;

	/**
	 * All TemplateType.
	 */
	public static final List<String> TEMPLATE_TYPE_ALL = Collections
			.unmodifiableList(Arrays.asList(TEMPLATE_TYPE_CODE, TEMPLATE_TYPE_STATIC));

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		if (!TEMPLATE_TYPE_ALL.contains(templateType)) {
			throw new InvalidInitializrMetadataException(
					"Invalid templateType " + templateType + " must be one of " + TEMPLATE_TYPE_ALL);
		}
		this.templateType = templateType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Template{" + "name='" + this.name + '\'' + ", templateType='" + this.templateType + '\'' + ", type='"
				+ this.type + '\'' + '}';
	}

	public static Template create(String name, String templateType) {
		return create(name, templateType, null);
	}

	public static Template create(String name, String templateType, String type) {
		Template template = new Template();
		template.setName(name);
		template.setTemplateType(templateType);
		template.setType(type);
		return template;
	}

}
