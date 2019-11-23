package com.indulgent.jetbrains.plugin.code.comment.model.user.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Information about user
 *
 * @author Indulgent
 *         07.06.2016.
 */
class UserInfoImpl implements UserInfo {
	private final String name;

	/**
	 * Constructor
	 *
	 * @param name name of user
	 */
	UserInfoImpl(String name) {
		this.name = name;
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}
}
