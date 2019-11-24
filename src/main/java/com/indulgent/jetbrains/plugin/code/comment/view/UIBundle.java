package com.indulgent.jetbrains.plugin.code.comment.view;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Message bundle
 *
 * @author HackaTUM team
 *
 */
public class UIBundle {

	private static final Map<String, String> messages = new HashMap<>();

	static {
		messages.put("tool-window.title", "Code comment");
		messages.put("tool-window.mode.file.all.title", "Project");
		messages.put("tool-window.mode.file.current.title", "Current File");
	}

	/**
	 * Get message by key
	 *
	 * @param key key of message
	 * @return message by key
	 */
	public static String message(@NotNull String key) {
		return messages.get(key);
	}
}
