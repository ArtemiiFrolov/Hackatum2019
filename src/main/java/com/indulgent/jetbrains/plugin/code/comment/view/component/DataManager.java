package com.indulgent.jetbrains.plugin.code.comment.view.component;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.FileComments;

import java.util.Collection;

/**
 * Manager of comments data
 *
 * @author Indulgent
 *         24.05.2016.
 */
interface DataManager {

	/**
	 * Get actual comments data
	 *
	 * @return comments data
	 */
	Collection<FileComments> getData();
}
