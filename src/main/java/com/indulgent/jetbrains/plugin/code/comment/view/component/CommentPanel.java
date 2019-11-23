package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.util.CommentHistoryUtil;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Panel of single comment information
 *
 * @author Indulgent
 *         24.05.2016.
 */
class CommentPanel {

	private static final String SPLITTER_KEY = "code_comments.window.tool.splitter.detail";
	private static final float PROPORTION = 0.5f;

	private final Collection<SelectCommentPanel.SelectCommentsListener> selectListeners = new ArrayList<>();

	private final Object lock = new Object();
	private volatile JComponent component = null;

	private JTextArea commentedText;
	private JTextArea commentText;

	/**
	 * Get comments information component
	 *
	 * @return comments information component
	 */
	@NotNull
	JComponent getComponent() {
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
	private JComponent buildComponent() {
		JBSplitter detailSplitter = new JBSplitter(SPLITTER_KEY, PROPORTION);
		detailSplitter.setOrientation(false);
		commentedText = new JTextArea();
		commentedText.setEditable(false);
		commentText = new JTextArea();
		commentText.setEditable(false);
		detailSplitter.setFirstComponent(new JBScrollPane(commentedText));
		detailSplitter.setSecondComponent(new JBScrollPane(commentText));

		selectListeners.add(selectedComments -> {
			commentedText.setText("");
			commentText.setText("");
			if (selectedComments.size() != 1) {
				return;
			}
			Comment comment = selectedComments.iterator().next();
			commentedText.setText(comment.getCodeInformation().getCodeText());
			commentText.setText(CommentHistoryUtil.getComment(comment));
		});

		return detailSplitter;
	}

	/**
	 * Get listeners of selection comments notification
	 *
	 * @return listeners of selection comments notification
	 */
	Collection<SelectCommentPanel.SelectCommentsListener> getSelectListeners() {
		return selectListeners;
	}

	/**
	 * Refresh data in panel
	 */
	public void refresh() {
		synchronized (lock) {
			if (commentedText != null) {
				commentedText.setText("");
			}
			if (commentText != null) {
				commentText.setText("");
			}
		}
	}
}
