package com.indulgent.jetbrains.plugin.code.comment.view.actions;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.logging.Log;
import com.indulgent.jetbrains.plugin.code.comment.messaging.MessagingProvider;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentBuilder;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentBuilderFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentService;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.impl.ErrorMessageBundle;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfoServiceFactory;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfoServiceFactory;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Collections;

/**
 * Action for add comment to selected code
 *
 * @author Indulgent
 *         15.05.2016.
 */
public class AddCommentAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		Project project = event.getProject();
		if (project == null) {
			Messages.showInfoMessage("File not found.", "Attention");
			return;
		}
		FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
		Editor selectedTextEditor = fileEditorManager.getSelectedTextEditor();
		if (selectedTextEditor == null) {
			Messages.showInfoMessage("File not found.", "Attention");
			return;
		}
		VirtualFile file = FileDocumentManager.getInstance().getFile(selectedTextEditor.getDocument());
		if (file == null) {
			Messages.showInfoMessage("File not found.", "Attention");
			return;
		}

		SelectionModel selectionModel = selectedTextEditor.getSelectionModel();
		String selectedText = selectionModel.getSelectedText();
		String commentText = Messages.showMultilineInputDialog(project, "Write comment", "Comment", null, AllIcons.General.Balloon, null);
		if (commentText == null || commentText.length() == 0) {
			return;
		}
		CommentBuilder builder = CommentBuilderFactory.getInstance().getBuilder(project);
		builder.setComment(commentText);
		builder.setSelectionInformation(selectionModel);
		builder.setFile(file);
		builder.setUserInformation(UserInfoServiceFactory.getService(project).getCurrentUser());
		builder.setGroupInformation(GroupInfoServiceFactory.getService(project).getAll().iterator().next());
		CommentService service = ServiceManager.getService(project, CommentService.class);
		try {
			Comment comment = builder.build();
			service.save(comment);

			MessagingProvider.getInstance(project).sendAddComments(Collections.singletonList(comment));
		} catch (FileNotInProjectException | EmptyFileCanonicalPathException | EmptyProjectPathException e) {
			Log.getInstance(project).error(ErrorMessageBundle.message(e.getClass().getName()), e);
		}
	}
}
