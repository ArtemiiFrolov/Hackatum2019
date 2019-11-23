package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.view.actions.AddCommentAction;
import com.indulgent.jetbrains.plugin.code.comment.view.actions.DownloadCommentsAction;
import com.indulgent.jetbrains.plugin.code.comment.view.actions.RefreshCommentsAction;
import com.indulgent.jetbrains.plugin.code.comment.view.actions.RemoveCommentsAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Tool bar for comment tool window
 *
 * @author Indulgent
 *         24.05.2016.
 */
class ToolBar {

	private static final String CODE_COMMENTS_VIEW_TOOLBAR_KEY = "codeCommentsViewToolbar";

	/**
	 * Get tool bar component
	 *
	 * @return tool bar component
	 */
	@NotNull
	JComponent getComponent() {
		JPanel toolBarPanel = new JPanel(new GridLayout());
		DefaultActionGroup actionGroup = new DefaultActionGroup();
		actionGroup.add(ActionManager.getInstance().getAction(AddCommentAction.class.getName()));
		actionGroup.add(ActionManager.getInstance().getAction(RemoveCommentsAction.class.getName()));
		actionGroup.add(ActionManager.getInstance().getAction(RefreshCommentsAction.class.getName()));
		actionGroup.add(ActionManager.getInstance().getAction(DownloadCommentsAction.class.getName()));
		ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(CODE_COMMENTS_VIEW_TOOLBAR_KEY, actionGroup, true);
		toolBarPanel.add(actionToolbar.getComponent());
		return toolBarPanel;
	}
}
