package com.indulgent.jetbrains.plugin.code.comment.messaging;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;

import java.util.Collection;
import java.util.EventListener;

/**
 * Listener of action with comments
 *
 * @author Indulgent
 *         23.05.2016.
 */
public interface CommentsActionListener extends EventListener {

	/**
	 * Do action after action with comments
	 *
	 * @param comments target comments
	 */
	void afterAction(Collection<Comment> comments);
}
