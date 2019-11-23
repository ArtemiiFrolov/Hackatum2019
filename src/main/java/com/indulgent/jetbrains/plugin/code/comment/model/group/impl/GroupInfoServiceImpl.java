package com.indulgent.jetbrains.plugin.code.comment.model.group.impl;

import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfo;
import com.indulgent.jetbrains.plugin.code.comment.model.group.GroupInfoService;

import java.util.Collection;
import java.util.Collections;

/**
 * Service for work with groups
 *
 * @author Indulgent
 *         07.06.2016.
 */
class GroupInfoServiceImpl implements GroupInfoService {
	private static final Collection<GroupInfo> GROUP_INFO_COLLECTION = Collections.singletonList(new GroupInfoImpl("other"));

	@Override
	public Collection<GroupInfo> getAll() {
		return GROUP_INFO_COLLECTION;
	}
}
