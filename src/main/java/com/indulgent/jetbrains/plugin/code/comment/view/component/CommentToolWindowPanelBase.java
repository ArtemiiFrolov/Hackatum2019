package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Tool panel
 *
 * @author Indulgent
 *         15.05.2016.
 */
abstract class CommentToolWindowPanelBase extends SimpleToolWindowPanel {

	private final DataPanel dataPanel;

	/**
	 * Constructor
	 *
	 * @param project   current project
	 * @param listeners collections of choose comment listener
	 */
	CommentToolWindowPanelBase(@NotNull Project project, @NotNull Collection<SelectCommentPanel.ChooseCommentListener> listeners) {
		super(true, true);
		dataPanel = new DataPanel(getDataManager(project), listeners);
		setToolbar(new ToolBar().getComponent());
		add(dataPanel.getComponent());
	}

	/**
	 * Get selected comments
	 *
	 * @return selected comments
	 */
	Collection<Comment> getSelectedComments() {
		return dataPanel.getSelectedComments();
	}

	/**
	 * Refresh data in panel
	 */
	void refresh() {
		dataPanel.refresh();
	}

	@NotNull
	protected abstract DataManager getDataManager(Project project);
}
