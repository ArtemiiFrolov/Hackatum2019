package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;

import java.util.Collection;

/**
 * Builder of comments data
 *
 * @author Indulgent
 *         07.06.2016.
 */
interface CommentsDataBuilder {
	/**
	 * Build comments data
	 *
	 * @return comments
	 */
	Collection<FileComments> build();
}
