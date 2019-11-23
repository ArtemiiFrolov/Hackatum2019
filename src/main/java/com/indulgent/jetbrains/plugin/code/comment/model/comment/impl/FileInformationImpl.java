package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileInformation;
import org.jetbrains.annotations.NotNull;

/**
 * Information about file
 *
 * @author Indulgent
 *         04.06.2016.
 */
class FileInformationImpl implements FileInformation {
	private final String path;

	/**
	 * Constructor
	 *
	 * @param path relative file path
	 */
	FileInformationImpl(@NotNull String path) {
		this.path = path;
	}

	@Override
	@NotNull
	public String getPath() {
		return path;
	}
}
