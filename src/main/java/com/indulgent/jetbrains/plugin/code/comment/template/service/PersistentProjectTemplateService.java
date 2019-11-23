package com.indulgent.jetbrains.plugin.code.comment.template.service;

import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Save state in project settings file
 *
 * @author Indulgent
 *         03.06.2016.
 */
@State(
		name = "CodeCommentSettings", storages = {
		@Storage(file = "code-comments-settings.xml")
})
public class PersistentProjectTemplateService implements TemplateService, PersistentStateComponent<Element> {
	private static final String ROOT_TAG_NAME = "root";
	private static final String TEMPLATES_TAG_NAME = "templates";
	private static final String TEMPLATE_TAG_NAME = "template";
	private static final String KEY_ATTRIBUTE_NAME = "key";
	private final Map<String, Template> templates = new HashMap<>();


	public Collection<Template> getAll() {
		return templates.values();
	}

	public Template getByKey(String key) {
		return templates.get(key);
	}

	public void save(@NotNull Template template) {
		templates.put(template.getKey(), template);
	}

	private void clear() {
		templates.clear();
	}

	@Nullable
	@Override
	public Element getState() {
		Element templatesElement = new Element(TEMPLATES_TAG_NAME);

		for (Template template : templates.values()) {
			Element templateElement = new Element(TEMPLATE_TAG_NAME);
			templateElement.setAttribute(KEY_ATTRIBUTE_NAME, template.getKey());
			templateElement.setText(template.getBody());
			templatesElement.addContent(templateElement);
		}

		Element root = new Element(ROOT_TAG_NAME);
		root.addContent(templatesElement);
		return root;
	}

	@Override
	public void loadState(Element state) {

		clear();

		Element templatesElement = state.getChild(TEMPLATES_TAG_NAME);
		for (Element templateElement : templatesElement.getChildren()) {
			String templateKey = templateElement.getAttribute(KEY_ATTRIBUTE_NAME).getValue();
			String templateBody = templateElement.getText();
			save(new Template(templateKey, templateBody));
		}
	}
}
