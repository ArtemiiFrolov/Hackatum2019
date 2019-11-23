package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Comment data
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface Comment {
	/**
	 * Get commented file information
	 *
	 * @return file information
	 */
	@NotNull
	FileInformation getFileInformation();

	/**
	 * Get commented code information
	 *
	 * @return code information
	 */
	@NotNull
	CodeInformation getCodeInformation();

	/**
	 * Get information about group
	 *
	 * @return group information
	 */
	@NotNull
	GroupInfo getGroupInfo();

	/**
	 * Get history of comments
	 *
	 * @return history of comments
	 */
	@NotNull
	CommentHistory getCommentHistory();
}
