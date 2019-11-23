package com.indulgent.jetbrains.plugin.code.comment.template.processor;

import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

/**
 * Template processor on freemarker engine
 *
 * @author Indulgent
 *         03.06.2016.
 */
class FreemarkerTemplateProcessor implements TemplateProcessor {

	public static final String TEMPLATE_NAME = FreemarkerTemplateProcessor.class.getName();

	public <DT> void process(@NotNull Template template, @NotNull DT data, @NotNull Writer destination) {
		try {
			freemarker.template.Template freemarkerTemplate = getFreemarkerTemplate(template);
			freemarkerTemplate.process(data, destination);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	@NotNull
	private freemarker.template.Template getFreemarkerTemplate(@NotNull Template template) {
		try {
			return new freemarker.template.Template(TEMPLATE_NAME, new StringReader(template.getBody()), new Configuration());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
