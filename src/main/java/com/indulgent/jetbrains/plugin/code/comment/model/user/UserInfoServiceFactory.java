package com.indulgent.jetbrains.plugin.code.comment.model.user;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of UserInfoService
 *
 * @author Indulgent
 *         07.06.2016.
 */
public class UserInfoServiceFactory {
	/**
	 * Get actual instance of UserInfoService
	 *
	 * @param project current project
	 * @return instance of UserInfoService
	 */
	@NotNull
	public static UserInfoService getService(@NotNull Project project) {
		return ServiceManager.getService(project, UserInfoService.class);
	}
}
