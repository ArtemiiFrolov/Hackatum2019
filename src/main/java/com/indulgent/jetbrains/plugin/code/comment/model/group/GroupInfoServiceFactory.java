package com.indulgent.jetbrains.plugin.code.comment.model.group;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of GroupInfoService
 *
 * @author Indulgent
 *         07.06.2016.
 */
public class GroupInfoServiceFactory {
	/**
	 * Get actual instance of GroupInfoService
	 *
	 * @param project current project
	 * @return instance of GroupInfoService
	 */
	@NotNull
	public static GroupInfoService getService(@NotNull Project project) {
		return ServiceManager.getService(project, GroupInfoService.class);
	}
}
