package com.indulgent.jetbrains.plugin.code.comment.logging;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Log for code comments plugin
 *
 * @author Indulgent
 *         06.06.2016.
 */
public final class Log {
	private static final String LOG_GROUP_DISPLAY_ID = "Code Comments";
	private static final String LOG_TITLE = "Code Comments";

	private final Project project;

	private Log(@NotNull Project project) {
		this.project = project;
	}

	/**
	 * Get instance of logger
	 *
	 * @return instance
	 */
	public static Log getInstance(@NotNull Project project) {
		return ServiceManager.getService(project, Log.class);
	}

	/**
	 * Log a information message
	 *
	 * @param message information message
	 */
	public void info(String message) {
		addToLog(message, NotificationType.INFORMATION);
	}

	/**
	 * Log a warning message, with associated exception information
	 *
	 * @param message   warning message
	 * @param exception exception information
	 */
	public void warn(String message, Exception exception) {
		addToLog(message, NotificationType.WARNING);
	}

	/**
	 * Log a error message
	 *
	 * @param message error message
	 */
	public void error(String message) {
		addToLog(message, NotificationType.ERROR);
	}

	/**
	 * Log a error message, with associated exception information
	 *
	 * @param message   error message
	 * @param exception exception information
	 */
	public void error(String message, Exception exception) {
		addToLog(message, NotificationType.ERROR);
	}

	private void addToLog(String message, NotificationType notificationType) {
		project.getMessageBus().syncPublisher(Notifications.TOPIC).notify(new Notification(LOG_GROUP_DISPLAY_ID, LOG_TITLE, message, notificationType));
	}
}
