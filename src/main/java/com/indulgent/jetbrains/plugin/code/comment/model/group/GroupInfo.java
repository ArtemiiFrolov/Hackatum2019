package com.indulgent.jetbrains.plugin.code.comment.model.group;

import org.jetbrains.annotations.NotNull;

/**
 * Information about group
 *
 * @author Indulgent
 *         05.06.2016.
 */
public interface GroupInfo {
	/**
	 * Get name of group
	 *
	 * @return name
	 */
	@NotNull
	String getName();
}
