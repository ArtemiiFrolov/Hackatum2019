package com.indulgent.jetbrains.plugin.code.comment.template;

/**
 * Template information
 *
 * @author Indulgent
 *         03.06.2016.
 */
public class Template {

	private final String key;
	private final String body;

	/**
	 * Constructor
	 *
	 * @param key  key of template
	 * @param body template body
	 */
	public Template(String key, String body) {
		this.key = key;
		this.body = body;
	}

	/**
	 * Get key of template
	 *
	 * @return key of template
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get template body
	 *
	 * @return template body
	 */
	public String getBody() {
		return body;
	}
}
