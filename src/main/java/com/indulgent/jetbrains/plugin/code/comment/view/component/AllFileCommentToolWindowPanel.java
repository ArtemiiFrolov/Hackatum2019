package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentServiceFactory;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Tool panel for all file
 *
 * @author Indulgent
 *         22.05.2016.
 */
class AllFileCommentToolWindowPanel extends CommentToolWindowPanelBase {

	/**
	 * Constructor
	 *
	 * @param project   current project
	 * @param listeners collections of choose comment listener
	 */
	AllFileCommentToolWindowPanel(@NotNull Project project, @NotNull Collection<SelectCommentPanel.ChooseCommentListener> listeners) {
		super(project, listeners);
	}

	@NotNull
	@Override
	protected DataManager getDataManager(Project project) {
		return () -> CommentServiceFactory.getService(project).getAll();
	}
}
