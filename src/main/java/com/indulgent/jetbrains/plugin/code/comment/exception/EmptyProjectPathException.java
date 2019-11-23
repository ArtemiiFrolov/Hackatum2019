package com.indulgent.jetbrains.plugin.code.comment.exception;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Exception of empty path to project
 *
 * @author Indulgent
 *         05.06.2016.
 */
public class EmptyProjectPathException extends Exception {
	private final Project project;

	/**
	 * Constructor
	 *
	 * @param project problem project
	 */
	public EmptyProjectPathException(@NotNull Project project) {
		this.project = project;
	}

	/**
	 * Get problem project
	 *
	 * @return project
	 */
	public Project getProject() {
		return project;
	}
}
