package com.indulgent.jetbrains.plugin.code.comment.view.actions;

import com.indulgent.jetbrains.plugin.code.comment.messaging.MessagingProvider;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Action for refresh comments information
 *
 * @author Indulgent
 *         22.05.2016.
 */
public class RefreshCommentsAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		Project project = event.getProject();
		if (project == null) {
			Messages.showInfoMessage("Project not found.", "Attention");
			return;
		}

		MessagingProvider.getInstance(project).sendRefreshComments();
	}
}
