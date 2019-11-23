package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * Tool panel for current file
 *
 * @author Indulgent
 *         22.05.2016.
 */
class CurrentFileCommentToolWindowPanel extends CommentToolWindowPanelBase {

	private static final Collection<FileComments> EMPTY_RESULT = Collections.emptyList();

	/**
	 * Constructor
	 *
	 * @param project   current project
	 * @param listeners collections of choose comment listener
	 */
	CurrentFileCommentToolWindowPanel(@NotNull Project project, @NotNull Collection<SelectCommentPanel.ChooseCommentListener> listeners) {
		super(project, listeners);
	}

	@NotNull
	@Override
	protected DataManager getDataManager(Project project) {
		return () -> {
			FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
			Editor selectedTextEditor = fileEditorManager.getSelectedTextEditor();
			if (selectedTextEditor == null) {
				return EMPTY_RESULT;
			}
			VirtualFile file = FileDocumentManager.getInstance().getFile(selectedTextEditor.getDocument());
			if (file == null) {
				return EMPTY_RESULT;
			}
			FileComments comments = CommentServiceFactory.getService(project).getForFile(file);
			if (comments == null) {
				return EMPTY_RESULT;
			}
			return Collections.singleton(comments);
		};
	}

	/**
	 * Get change file listener
	 *
	 * @return listener
	 */
	FileEditorManagerListener getChangeFileListener() {
		return new ChangeFileListener(this);
	}

	private static class ChangeFileListener implements FileEditorManagerListener {

		private final CurrentFileCommentToolWindowPanel panel;

		private ChangeFileListener(CurrentFileCommentToolWindowPanel panel) {
			this.panel = panel;
		}

		@Override
		public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
			panel.refresh();
		}

		@Override
		public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
			panel.refresh();
		}

		@Override
		public void selectionChanged(@NotNull FileEditorManagerEvent event) {
			panel.refresh();
		}
	}
}
