package com.indulgent.jetbrains.plugin.code.comment.exception;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Exception of work with non-project file
 *
 * @author Indulgent
 *         05.06.2016.
 */
public class FileNotInProjectException extends Exception {
	private final Project project;
	private final VirtualFile file;

	/**
	 * Constructor
	 *
	 * @param project problem project
	 * @param file    problem file
	 */
	public FileNotInProjectException(@NotNull Project project, @NotNull VirtualFile file) {
		this.project = project;
		this.file = file;
	}

	/**
	 * Get problem project
	 *
	 * @return project
	 */
	public Project getProject() {
		return project;
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
