package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException;
import com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Indulgent
 *         05.06.2016.
 */
public interface CommentBuilder {
	/**
	 * Add commented file information
	 *
	 * @param file file information
	 */
	void setFile(@NotNull VirtualFile file);

	/**
	 * Add commented code in file information
	 *
	 * @param selectionInformation code information
	 */
	void setSelectionInformation(@NotNull SelectionModel selectionInformation);

	/**
	 * Add author of comment information
	 *
	 * @param userInfo author information
	 */
	void setUserInformation(@NotNull UserInfo userInfo);

	/**
	 * Add information about group
	 *
	 * @param groupInfo group information
	 */
	void setGroupInformation(@NotNull GroupInfo groupInfo);

	/**
	 * Add comment text information
	 *
	 * @param text comment text
	 */
	void setComment(@NotNull String text);

	/**
	 * Build comment
	 *
	 * @return comment
	 */
	@NotNull
	Comment build() throws FileNotInProjectException, EmptyFileCanonicalPathException, EmptyProjectPathException;
}
