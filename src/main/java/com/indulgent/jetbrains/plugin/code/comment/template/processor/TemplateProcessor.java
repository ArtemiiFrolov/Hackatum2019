package com.indulgent.jetbrains.plugin.code.comment.template.processor;

import com.indulgent.jetbrains.plugin.code.comment.template.Template;
import org.jetbrains.annotations.NotNull;

import java.io.Writer;

/**
 * Processor of template
 *
 * @author Indulgent
 *         03.06.2016.
 */
public interface TemplateProcessor {

	/**
	 * Process template transformation from {@code template} in {@code destination} using information from {@code data}
	 *
	 * @param template    template information
	 * @param data        data for template processing
	 * @param destination destination of result
	 * @param <DT>        type of data
	 */
	<DT> void process(@NotNull Template template, @NotNull DT data, @NotNull Writer destination);
}
