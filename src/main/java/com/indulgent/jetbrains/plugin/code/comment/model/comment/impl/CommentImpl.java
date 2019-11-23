package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CodeInformation;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentHistory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileInformation;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Comment data
 *
 * @author Indulgent
 *         04.06.2016.
 */
class CommentImpl implements Comment {
	private final FileInformation fileInformation;
	private final CodeInformation codeInformation;
	private final GroupInfo groupInfo;
	private final CommentHistory commentHistory;

	/**
	 * Constructor
	 *
	 * @param fileInformation information about file
	 * @param codeInformation information about place in file
	 * @param groupInfo       information about group
	 */
	CommentImpl(@NotNull FileInformation fileInformation, @NotNull CodeInformation codeInformation, @NotNull GroupInfo groupInfo) {
		this.fileInformation = fileInformation;
		this.codeInformation = codeInformation;
		this.groupInfo = groupInfo;
		this.commentHistory = new CommentHistoryImpl();
	}

	@Override
	@NotNull
	public FileInformation getFileInformation() {
		return fileInformation;
	}

	@Override
	@NotNull
	public CodeInformation getCodeInformation() {
		return codeInformation;
	}

	@Override
	@NotNull
	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	@Override
	@NotNull
	public CommentHistory getCommentHistory() {
		return commentHistory;
	}
}
