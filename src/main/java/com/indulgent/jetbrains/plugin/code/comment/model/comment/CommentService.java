package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Service for work with comments
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface CommentService {
	/**
	 * Get all comments
	 *
	 * @return collection of comments
	 */
	@NotNull
	Collection<FileComments> getAll();

	/**
	 * Get comments for file
	 *
	 * @return collection of comments
	 */
	FileComments getForFile(@NotNull VirtualFile file);

	/**
	 * Save comment in storage
	 *
	 * @param comment saving comment
	 */
	void save(@NotNull Comment comment);

	/**
	 * Remove comments from storage
	 *
	 * @param comments removing comments
	 */
	void remove(@NotNull Collection<Comment> comments);
}
