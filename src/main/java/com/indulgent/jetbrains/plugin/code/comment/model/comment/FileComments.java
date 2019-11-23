package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Comments for file
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface FileComments {
	/**
	 * Get commented file information
	 *
	 * @return file information
	 */
	@NotNull
	FileInformation getFileInformation();

	/**
	 * Get commentaries for file
	 *
	 * @return commentaries
	 */
	@NotNull
	Collection<Comment> getComments();

	/**
	 * Add comment to file
	 *
	 * @param comment new comment
	 */
	void save(Comment comment);

	/**
	 * Remove comment from file
	 *
	 * @param comment removing comment
	 */
	void remove(@NotNull Comment comment);

	/**
	 * Returns true if this file contains no comments
	 *
	 * @return true if this file contains no comments
	 */
	boolean isEmpty();
}
