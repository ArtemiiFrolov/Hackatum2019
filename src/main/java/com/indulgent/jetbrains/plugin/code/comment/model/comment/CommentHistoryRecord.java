package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * Record of commentaries history
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface CommentHistoryRecord {
	/**
	 * Get date of record
	 *
	 * @return date of record
	 */
	@NotNull
	Calendar getDate();

	/**
	 * Get comment text
	 *
	 * @return comment text
	 */
	@NotNull
	String getText();

	/**
	 * Get author
	 *
	 * @return author
	 */
	@NotNull
	UserInfo getUserInfo();
}
