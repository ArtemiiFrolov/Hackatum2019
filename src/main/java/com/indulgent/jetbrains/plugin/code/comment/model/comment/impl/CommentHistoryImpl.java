package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentHistory;
import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentHistoryRecord;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * History of comments
 *
 * @author Indulgent
 *         04.06.2016.
 */
class CommentHistoryImpl implements CommentHistory {
	private final Collection<CommentHistoryRecord> records = new ArrayList<>();

	@Override
	public void add(@NotNull UserInfo userInfo, @NotNull String text, Calendar date) {
		records.add(new CommentHistoryRecordImpl(date, text, userInfo));
	}

	@Override
	@NotNull
	public Collection<CommentHistoryRecord> getRecords() {
		return records;
	}
}
