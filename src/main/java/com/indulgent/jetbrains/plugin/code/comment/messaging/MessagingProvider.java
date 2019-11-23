package com.indulgent.jetbrains.plugin.code.comment.messaging;

import com.indulgent.jetbrains.plugin.code.comment.model.comment.Comment;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Internal messaging provider
 *
 * @author Indulgent
 *         22.05.2016.
 */
public final class MessagingProvider {

	private static final Map<Project, MessagingProvider> messagingProviders = new HashMap<>();
	private static final Object LOCK = new Object();

	private final Topic<CommentsActionListener> addCommentsTopic = new Topic<>("AddCommentsTopic", CommentsActionListener.class, Topic.BroadcastDirection.TO_CHILDREN);
	private final Topic<CommentsActionListener> removeCommentsTopic = new Topic<>("RemoveCommentsTopic", CommentsActionListener.class, Topic.BroadcastDirection.TO_CHILDREN);
	private final Topic<RefreshCommentsActionListener> refreshCommentsTopic = new Topic<>("RefreshCommentsTopic", RefreshCommentsActionListener.class, Topic.BroadcastDirection.TO_CHILDREN);

	private final Project project;

	private MessagingProvider(Project project) {
		this.project = project;
	}

	/**
	 * Get instance for project
	 *
	 * @param project target project
	 * @return instance of message provider
	 */
	public static MessagingProvider getInstance(Project project) {
		MessagingProvider messagingProvider = messagingProviders.get(project);
		if (messagingProvider != null) {
			return messagingProvider;
		}
		synchronized (LOCK) {
			messagingProvider = messagingProviders.get(project);
			if (messagingProvider != null) {
				return messagingProvider;
			}
			messagingProvider = new MessagingProvider(project);
			messagingProviders.put(project, messagingProvider);
		}
		return messagingProvider;
	}

	/**
	 * Subscribe to add comments notification
	 *
	 * @param listener listener of add comments notification
	 */
	public void subscribeOpenFile(FileEditorManagerListener listener) {
		project.getMessageBus().connect().subscribe(FileEditorManagerAdapter.FILE_EDITOR_MANAGER, listener);
	}

	/**
	 * Send add comments notification
	 *
	 * @param comments added commentaries
	 */
	public void sendAddComments(Collection<Comment> comments) {
		project.getMessageBus().syncPublisher(addCommentsTopic).afterAction(comments);
	}

	/**
	 * Subscribe to add comments notification
	 *
	 * @param listener listener of add comments notification
	 */
	public void subscribeAddComments(CommentsActionListener listener) {
		project.getMessageBus().connect().subscribe(addCommentsTopic, listener);
	}

	/**
	 * Send remove comments notification
	 *
	 * @param comments removed commentaries
	 */
	public void sendRemoveComments(Collection<Comment> comments) {
		project.getMessageBus().syncPublisher(removeCommentsTopic).afterAction(comments);
	}

	/**
	 * Subscribe to remove comments notification
	 *
	 * @param listener listener of add comments notification
	 */
	public void subscribeRemoveComments(CommentsActionListener listener) {
		project.getMessageBus().connect().subscribe(removeCommentsTopic, listener);
	}

	/**
	 * Send refresh comments information notification
	 */
	public void sendRefreshComments() {
		project.getMessageBus().syncPublisher(refreshCommentsTopic).afterRefresh();
	}

	/**
	 * Subscribe to refresh comments information notification
	 *
	 * @param listener listener of add comments notification
	 */
	public void subscribeRefreshComments(RefreshCommentsActionListener listener) {
		project.getMessageBus().connect().subscribe(refreshCommentsTopic, listener);
	}
}
