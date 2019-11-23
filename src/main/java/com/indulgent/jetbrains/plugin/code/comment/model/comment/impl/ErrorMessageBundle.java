package com.indulgent.jetbrains.plugin.code.comment.model.comment.impl;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Message bundle for error message
 *
 * @author Indulgent
 *         06.06.2016.
 */
public class ErrorMessageBundle {
	private static final Map<String, String> messages = new HashMap<>();

	static {
		messages.put("com.indulgent.jetbrains.plugin.code.comment.exception.EmptyFileCanonicalPathException", "Get current file information error.");
		messages.put("com.indulgent.jetbrains.plugin.code.comment.exception.EmptyProjectPathException", "Get current file information error.");
		messages.put("com.indulgent.jetbrains.plugin.code.comment.exception.FileNotInProjectException", "Only project file.");
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
