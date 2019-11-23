package com.indulgent.jetbrains.plugin.code.comment.model.user.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfoService;

/**
 * Service for work with user information
 *
 * @author Indulgent
 *         07.06.2016.
 */
class UserInfoServiceImpl implements UserInfoService {
	private static final UserInfoImpl UNKNOWN = new UserInfoImpl("unknown");

	@Override
	public UserInfo getCurrentUser() {
		return UNKNOWN;
	}
}
