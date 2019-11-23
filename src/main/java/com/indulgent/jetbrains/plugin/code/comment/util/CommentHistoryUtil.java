package com.indulgent.jetbrains.plugin.code.comment.util;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentHistoryRecord;

import java.util.Collection;

/**
 * Utility class for work with comment history
 *
 * @author Indulgent
 *         07.06.2016.
 */
public final class CommentHistoryUtil {
	private CommentHistoryUtil() {
	}

	/**
	 * Get text of comment
	 *
	 * @param comment source
	 * @return text
	 */
	public static String getComment(Comment comment) {
		Collection<CommentHistoryRecord> records = comment.getCommentHistory().getRecords();
		if (records.isEmpty()) {
			return "";
		}
		return records.iterator().next().getText();
	}
}
