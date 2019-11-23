package com.indulgent.jetbrains.plugin.code.comment.template.service;

import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Service for work with templates
 *
 * @author Indulgent
 *         03.06.2016.
 */
public interface TemplateService {
	Collection<Template> getAll();

	Template getByKey(String key);

	void save(@NotNull Template template);
}
