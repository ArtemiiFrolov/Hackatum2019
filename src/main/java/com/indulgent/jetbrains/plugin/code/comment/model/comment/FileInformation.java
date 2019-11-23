package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import org.jetbrains.annotations.NotNull;

/**
 * Information about file
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface FileInformation {
	/**
	 * Get relative file path
	 *
	 * @return path
	 */
	@NotNull
	String getPath();
}
