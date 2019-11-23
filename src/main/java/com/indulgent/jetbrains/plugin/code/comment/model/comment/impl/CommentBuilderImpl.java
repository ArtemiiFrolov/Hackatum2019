package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentBuilder;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * Builder of comments
 *
 * @author Indulgent
 *         04.06.2016.
 */
public class CommentBuilderImpl implements CommentBuilder {
	private final Project project;

	private VirtualFile file;
	private CodeInformationBuilder codeInformationBuilder;
	private GroupInfo groupInfo;
	private UserInfo userInfo;
	private String text;

	/**
	 * Constructor
	 *
	 * @param project current project
	 */
	public CommentBuilderImpl(@NotNull Project project) {
		this.project = project;
	}

	@Override
	public void setFile(@NotNull VirtualFile file) {
		this.file = file;
	}

	@Override
	public void setSelectionInformation(@NotNull SelectionModel selectionInformation) {
		codeInformationBuilder = new CodeInformationBuilder(selectionInformation);
	}

	@Override
	public void setUserInformation(@NotNull UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void setGroupInformation(@NotNull GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	@Override
	public void setComment(@NotNull String text) {
		this.text = text;
	}

	@Override
	@NotNull
	public Comment build() throws FileNotInProjectException, EmptyFileCanonicalPathException, EmptyProjectPathException {
		FileInformationBuilder fileInformationBuilder = FileInformationBuilderFactory.getInstance().getBuilder();
		Comment comment = new CommentImpl(fileInformationBuilder.build(project, file), codeInformationBuilder.build(), groupInfo);
		comment.getCommentHistory().add(userInfo, text, Calendar.getInstance());
		return comment;
	}
}
