package com.indulgent.jetbrains.plugin.code.comment.exception;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Exception of empty canonical path to file
 *
 * @author Indulgent
 *         05.06.2016.
 */
public class EmptyFileCanonicalPathException extends Exception {
	private final VirtualFile file;

	/**
	 * Constructor
	 *
	 * @param file problem file
	 */
	public EmptyFileCanonicalPathException(@NotNull VirtualFile file) {
		this.file = file;
	}

	/**
	 * Get problem file
	 *
	 * @return file
	 */
	public VirtualFile getFile() {
		return file;
	}
}
