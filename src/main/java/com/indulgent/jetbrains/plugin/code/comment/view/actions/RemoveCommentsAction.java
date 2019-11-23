package com.indulgent.jetbrains.plugin.code.comment.view.actions;

import com.indulgent.jetbrains.plugin.code.comment.messaging.MessagingProvider;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.view.component.CommentToolWindowFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Action for remove selected comments
 *
 * @author Indulgent
 *         15.05.2016.
 */
public class RemoveCommentsAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		Project project = event.getProject();
		if (project == null) {
			Messages.showInfoMessage("Comments not selected.", "Attention");
			return;
		}

		Collection<Comment> commentsForRemove = CommentToolWindowFactory.isActiveToolWindow(project) ? CommentToolWindowFactory.getToolWindowComments(project) : getEditorComments(project);

		if (commentsForRemove.isEmpty()) {
			return;
		}

		CommentServiceFactory.getService(project).remove(commentsForRemove);
		MessagingProvider.getInstance(project).sendRemoveComments(commentsForRemove);
	}

	private List<Comment> getEditorComments(Project project) {
		Messages.showInfoMessage("Comments not selected.", "Attention");
		return Collections.emptyList();
	}
}
