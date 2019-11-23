package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.intellij.ui.JBSplitter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;

/**
 * Panel of comments information
 *
 * @author Indulgent
 *         24.05.2016.
 */
class DataPanel {

	private static final String SPLITTER_KEY = "code_comments.window.tool.splitter.main";
	private static final float PROPORTION = 0.33f;

	private final DataManager dataManager;
	private final Collection<SelectCommentPanel.ChooseCommentListener> listeners;

	private final Object lock = new Object();
	private volatile Component component = null;

	private SelectCommentPanel selectCommentPanel;
	private CommentPanel commentPanel;

	/**
	 * Constructor
	 *
	 * @param dataManager data manager for comments
	 * @param listeners   collections of choose comment listener
	 */
	DataPanel(@NotNull DataManager dataManager, @NotNull Collection<SelectCommentPanel.ChooseCommentListener> listeners) {
		this.dataManager = dataManager;
		this.listeners = listeners;
	}

	/**
	 * Get comments information component
	 *
	 * @return comments information component
	 */
	@NotNull
	Component getComponent() {
		if (component != null) {
			return component;
		}
		synchronized (lock) {
			if (component != null) {
				return component;
			}

			component = buildComponent();
		}
		return component;
	}

	@NotNull
	private Component buildComponent() {
		JBSplitter mainSplitter = new JBSplitter(SPLITTER_KEY, PROPORTION);
		mainSplitter.setOrientation(false);

		selectCommentPanel = new SelectCommentPanel(dataManager, listeners);
		mainSplitter.setFirstComponent(selectCommentPanel.getComponent());

		commentPanel = new CommentPanel();
		mainSplitter.setSecondComponent(commentPanel.getComponent());

		selectCommentPanel.addSelectListeners(commentPanel.getSelectListeners());

		return mainSplitter;
	}

	/**
	 * Get selected comments
	 *
	 * @return selected comments
	 */
	@NotNull
	Collection<Comment> getSelectedComments() {
		synchronized (lock) {
			return selectCommentPanel.getSelectedComments();
		}
	}

	/**
	 * Refresh data in panel
	 */
	void refresh() {
		synchronized (lock) {
			if (selectCommentPanel != null) {
				selectCommentPanel.refresh();
			}
			if (commentPanel != null) {
				commentPanel.refresh();
			}
		}
	}
}
