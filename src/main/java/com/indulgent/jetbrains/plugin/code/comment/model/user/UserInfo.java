package com.indulgent.jetbrains.plugin.code.comment.model.user;

import org.jetbrains.annotations.NotNull;

/**
 * Information about user
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface UserInfo {
	/**
	 * Get name of group
	 *
	 * @return name
	 */
	@NotNull
	String getName();
}
