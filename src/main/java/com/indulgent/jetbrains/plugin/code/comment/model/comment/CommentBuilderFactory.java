package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.impl.CommentBuilderImpl;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Factory of CommentBuilder
 *
 * @author Indulgent
 *         05.06.2016.
 */
public final class CommentBuilderFactory {
	public static final CommentBuilderFactory instance = new CommentBuilderFactory();

	private CommentBuilderFactory() {
	}

	/**
	 * Get instance of factory
	 *
	 * @return factory instance
	 */
	public static CommentBuilderFactory getInstance() {
		return instance;
	}

	/**
	 * Get builder instance
	 *
	 * @param project current project
	 * @return builder
	 */
	public CommentBuilder getBuilder(@NotNull Project project) {
		return new CommentBuilderImpl(project);
	}
}
