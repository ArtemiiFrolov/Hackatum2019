package com.indulgent.jetbrains.plugin.code.comment.model.comment;

import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Collection;

/**
 * History of comments
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface CommentHistory {
	/**
	 * Add comment
	 *
	 * @param userInfo author of comment
	 * @param text     comment text
	 * @param date     date of comment
	 */
	void add(@NotNull UserInfo userInfo, @NotNull String text, Calendar date);

	/**
	 * Get comments
	 *
	 * @return comments
	 */
	@NotNull
	Collection<CommentHistoryRecord> getRecords();
}
