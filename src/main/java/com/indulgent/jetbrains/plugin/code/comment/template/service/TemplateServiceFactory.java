package com.indulgent.jetbrains.plugin.code.comment.template.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of TemplateService
 *
 * @author Indulgent
 *         03.06.2016.
 */
public class TemplateServiceFactory {

	/**
	 * Get actual instance of TemplateService
	 *
	 * @param project current project
	 * @return instance of TemplateService
	 */
	@NotNull
	public static TemplateService getService(@NotNull Project project) {
		return ServiceManager.getService(project, TemplateService.class);
	}
}
