package com.indulgent.jetbrains.plugin.code.comment.model.user;

/**
 * Service for work with user information
 *
 * @author Indulgent
 *         07.06.2016.
 */
public interface UserInfoService {
	/**
	 * Get current user information
	 *
	 * @return user information
	 */
	UserInfo getCurrentUser();
}
