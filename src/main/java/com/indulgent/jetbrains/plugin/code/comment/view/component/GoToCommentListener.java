package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author Indulgent
 *         25.05.2016.
 */
class GoToCommentListener implements SelectCommentPanel.ChooseCommentListener {

	private final Project project;

	/**
	 * Constructor
	 *
	 * @param project current project
	 */
	GoToCommentListener(@NotNull Project project) {
		this.project = project;
	}

	@Override
	public void choose(@NotNull Comment selectedComment) {
		String filePath = project.getBasePath() + "/" + selectedComment.getFileInformation().getPath();
		VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl("file://" + filePath);
		if (virtualFile == null) {
			Messages.showInfoMessage("File not found.", "Attention");
			return;
		}
		FileEditorManager editorManager = FileEditorManager.getInstance(project);
		editorManager.openFile(virtualFile, true);
		Editor selectedTextEditor = editorManager.getSelectedTextEditor();
		if (selectedTextEditor == null) {
			Messages.showInfoMessage("SelectedTextEditor not found.", "Attention");
			return;
		}
		SelectionModel selectionModel = selectedTextEditor.getSelectionModel();

		selectedTextEditor.getCaretModel().moveToOffset(selectedComment.getCodeInformation().getEnd());
		selectionModel.setSelection(selectedComment.getCodeInformation().getStart(), selectedComment.getCodeInformation().getEnd());
		selectedTextEditor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
	}
}
