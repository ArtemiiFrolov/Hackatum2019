package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of CommentService
 *
 * @author Indulgent
 *         07.06.2016.
 */
public final class CommentServiceFactory {
	/**
	 * Get actual instance of CommentService
	 *
	 * @param project current project
	 * @return instance of CommentService
	 */
	@NotNull
	public static CommentService getService(@NotNull Project project) {
		return ServiceManager.getService(project, CommentService.class);
	}
}
