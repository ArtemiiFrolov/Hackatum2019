package com.indulgent.jetbrains.plugin.code.comment.model.group.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Information about group
 *
 * @author Indulgent
 *         07.06.2016.
 */
class GroupInfoImpl implements GroupInfo {
	private final String name;

	/**
	 * Constructor
	 *
	 * @param name name of group
	 */
	GroupInfoImpl(String name) {
		this.name = name;
	}

	@NotNull
	@Override
	public String getName() {
		return name;
	}
}
