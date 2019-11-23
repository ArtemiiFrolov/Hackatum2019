package com.indulgent.jetbrains.plugin.code.comment.template.processor;

import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of template processors
 *
 * @author Indulgent
 *         03.06.2016.
 */
public class TemplateProcessorFactory {

	public static final FreemarkerTemplateProcessor freemarkerTemplateProcessor = new FreemarkerTemplateProcessor();

	/**
	 * Get processor instance
	 *
	 * @param template template
	 * @return processor instance
	 */
	@NotNull
	public static TemplateProcessor getProcessor(@NotNull Template template) {
		return freemarkerTemplateProcessor;
	}
}
