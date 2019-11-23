package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.indulgent.jetbrains.plugin.code.comment.util.CommentHistoryUtil;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * Panel for selection comments
 *
 * @author Indulgent
 *         24.05.2016.
 */
class SelectCommentPanel {

	private final Object lock = new Object();
	private final DataManager dataManager;
	private final Collection<ChooseCommentListener> chooseCommentListeners;
	private volatile JComponent component = null;
	private DefaultTreeModel treeModel;
	private Tree tree;

	/**
	 * Constructor
	 *
	 * @param dataManager            manager of comments data
	 * @param chooseCommentListeners collections of choose comment listener
	 */
	SelectCommentPanel(@NotNull DataManager dataManager, @NotNull Collection<ChooseCommentListener> chooseCommentListeners) {
		this.dataManager = dataManager;
		this.chooseCommentListeners = chooseCommentListeners;
	}

	/**
	 * Get component for selection comments
	 *
	 * @return component for selection comments
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
		return new JBScrollPane(getTree());
	}

	/**
	 * Add listeners for select comments notification
	 *
	 * @param listeners listeners for select comments notification
	 */
	void addSelectListeners(@NotNull Collection<SelectCommentsListener> listeners) {
		synchronized (lock) {
			for (SelectCommentsListener listener : listeners) {
				tree.getSelectionModel().addTreeSelectionListener(event -> listener.onSelect(getSelectedComments()));
			}
		}
	}

	/**
	 * Get selected comments
	 *
	 * @return selected comments
	 */
	Collection<Comment> getSelectedComments() {
		List<Comment> selectedComments = new ArrayList<>();
		for (TreePath treePath : tree.getSelectionModel().getSelectionPaths()) {
			DefaultMutableTreeNode pathComponent = (DefaultMutableTreeNode) treePath.getLastPathComponent();
			Object userObject = pathComponent.getUserObject();
			if (userObject instanceof Comment) {
				selectedComments.add((Comment) userObject);
			}
			if (userObject instanceof PathData) {
				Enumeration commentElements = pathComponent.children();
				while (commentElements.hasMoreElements()) {
					DefaultMutableTreeNode commentNode = (DefaultMutableTreeNode) commentElements.nextElement();
					selectedComments.add((Comment) commentNode.getUserObject());
				}
			}
			if (userObject instanceof SumData) {
				Enumeration fileElements = pathComponent.children();
				while (fileElements.hasMoreElements()) {
					DefaultMutableTreeNode fileNode = (DefaultMutableTreeNode) fileElements.nextElement();
					Enumeration commentElements = fileNode.children();
					while (commentElements.hasMoreElements()) {
						DefaultMutableTreeNode commentNode = (DefaultMutableTreeNode) commentElements.nextElement();
						selectedComments.add((Comment) commentNode.getUserObject());
					}
				}
			}
		}
		return selectedComments;
	}

	/**
	 * Refresh data in panel
	 */
	void refresh() {
		synchronized (lock) {
			if (treeModel != null) {
				treeModel.setRoot(getTreeRoot());
			}
		}
	}

	@NotNull
	private Tree getTree() {
		treeModel = new DefaultTreeModel(getTreeRoot());
		tree = new Tree(treeModel);
		tree.setCellRenderer(new CellRenderer());
		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 2) {
						DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
						if (lastPathComponent.getUserObject() instanceof Comment) {
							for (ChooseCommentListener chooseCommentListener : chooseCommentListeners) {
								chooseCommentListener.choose((Comment) lastPathComponent.getUserObject());
							}
						}
					}
				}
			}
		};
		tree.addMouseListener(ml);
		return tree;
	}

	@NotNull
	private DefaultMutableTreeNode getTreeRoot() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		int filesCount = 0;
		int commentsCount = 0;

		Collection<FileComments> commentsByFileData = dataManager.getData();
		for (FileComments commentsByFile : commentsByFileData) {
			DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(new PathData(commentsByFile.getFileInformation().getPath()));
			filesCount++;
			for (Comment comment : commentsByFile.getComments()) {
				fileNode.add(new DefaultMutableTreeNode(comment));
				commentsCount++;
			}
			root.add(fileNode);
		}
		root.setUserObject(new SumData(filesCount, commentsCount));
		return root;
	}

	interface SelectCommentsListener {
		void onSelect(@NotNull Collection<Comment> selectedComments);
	}

	interface ChooseCommentListener {
		void choose(Comment comment);
	}

	private static class CellRenderer extends DefaultTreeCellRenderer {
		private static final int MAX_LENGTH = 60;
		private static final String MORE_TEXT = "...";
		private static final int MORE_TEXT_LENGTH = MORE_TEXT.length();

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof SumData) {
				return new JLabel("Files " + ((SumData) userObject).filesCount + " comments " + ((SumData) userObject).commentsCount);
			}
			if (userObject instanceof PathData) {
				PathData pathData = (PathData) userObject;
				return new JLabel(getLabelText(pathData.path));
			}
			if (userObject instanceof Comment) {
				Comment comment = (Comment) userObject;
				return new JLabel(CommentHistoryUtil.getComment(comment));
			}

			return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}

		@NotNull
		private String getLabelText(String source) {
			boolean needMore = false;
			String labelText = source;
			int eolIndex = labelText.indexOf('\n');
			if (eolIndex > 0) {
				needMore = true;
				labelText = labelText.substring(0, eolIndex);
			}

			if (labelText.length() > MAX_LENGTH) {
				needMore = true;
				labelText = labelText.substring(0, MAX_LENGTH - MORE_TEXT_LENGTH);
			}

			if (needMore) {
				labelText += MORE_TEXT;
			}
			return labelText;
		}
	}

	private class SumData {
		int filesCount;
		int commentsCount;

		public SumData(int filesCount, int commentsCount) {
			this.filesCount = filesCount;
			this.commentsCount = commentsCount;
		}
	}

	private class PathData {
		String path;

		public PathData(String path) {
			this.path = path;
		}
	}
}
