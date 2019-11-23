package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.CommentHistoryRecord;
import com.indulgent.jetbrains.plugin.code.comment.model.user.UserInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

/**
 * Record of commentaries history
 *
 * @author Indulgent
 *         04.06.2016.
 */
class CommentHistoryRecordImpl implements CommentHistoryRecord {
	private final Calendar date;
	private final String text;
	private final UserInfo userInfo;

	/**
	 * Constructor
	 *
	 * @param date     date of record
	 * @param text     comment text
	 * @param userInfo author
	 */
	CommentHistoryRecordImpl(@NotNull Calendar date, @NotNull String text, @NotNull UserInfo userInfo) {
		this.date = date;
		this.text = text;
		this.userInfo = userInfo;
	}

	@Override
	@NotNull
	public Calendar getDate() {
		return date;
	}

	@Override
	@NotNull
	public String getText() {
		return text;
	}

	@Override
	@NotNull
	public UserInfo getUserInfo() {
		return userInfo;
	}
}
