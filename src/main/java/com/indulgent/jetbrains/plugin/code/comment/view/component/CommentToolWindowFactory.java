package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.messaging.MessagingProvider;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.view.UIBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Comments tool window factory
 *
 * @author Indulgent
 *         03.05.2016.
 */
public class CommentToolWindowFactory implements ToolWindowFactory {

	private static final String TOOL_WINDOW_ID = "tool-window.title";
	private static final String ALL_FILE_COMMENTS_TAB = "tool-window.mode.file.all.title";
	private static final String CURRENT_FILE_COMMENTS_TAB = "tool-window.mode.file.current.title";

	/**
	 * Get tool window state
	 *
	 * @param project target project
	 * @return tool window state
	 */
	public static boolean isActiveToolWindow(@NotNull Project project) {
		return getToolWindow(project).isActive();
	}

	/**
	 * Get selected comments in tool window
	 *
	 * @param project target project
	 * @return selected comments
	 */
	public static Collection<Comment> getToolWindowComments(Project project) {
		ToolWindow toolWindow = getToolWindow(project);
		ContentManager contentManager = toolWindow.getContentManager();
		Content selectedContent = contentManager.getSelectedContent();
		if (selectedContent == null) {
			Messages.showInfoMessage("Comments not selected.", "Attention");
			return Collections.emptyList();
		}
		CommentToolWindowPanelBase panel = (CommentToolWindowPanelBase) selectedContent.getComponent();
		return panel.getSelectedComments();
	}

	private static ToolWindow getToolWindow(Project project) {
		return ToolWindowManager.getInstance(project).getToolWindow(UIBundle.message(TOOL_WINDOW_ID));
	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		ContentManager contentManager = toolWindow.getContentManager();
		List<SelectCommentPanel.ChooseCommentListener> listeners = Collections.singletonList(new GoToCommentListener(project));

		addTab(project, new AllFileCommentToolWindowPanel(project, listeners), ALL_FILE_COMMENTS_TAB, contentFactory, contentManager);

		CurrentFileCommentToolWindowPanel currentFileCommentToolWindowPanel = new CurrentFileCommentToolWindowPanel(project, listeners);
		addTab(project, currentFileCommentToolWindowPanel, CURRENT_FILE_COMMENTS_TAB, contentFactory, contentManager);
		MessagingProvider.getInstance(project).subscribeOpenFile(currentFileCommentToolWindowPanel.getChangeFileListener());
	}

	private void addTab(@NotNull Project project, @NotNull CommentToolWindowPanelBase panel, String titleKey, @NotNull ContentFactory contentFactory, @NotNull ContentManager contentManager) {
		Content allFileContent = contentFactory.createContent(panel, UIBundle.message(titleKey), false);
		allFileContent.setCloseable(false);
		contentManager.addContent(allFileContent);
		MessagingProvider.getInstance(project).subscribeAddComments(comments -> panel.refresh());
		MessagingProvider.getInstance(project).subscribeRemoveComments(comments -> panel.refresh());
		MessagingProvider.getInstance(project).subscribeRefreshComments(panel::refresh);
	}
}
