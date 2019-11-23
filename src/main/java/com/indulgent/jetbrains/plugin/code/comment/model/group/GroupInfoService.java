package com.indulgent.jetbrains.plugin.code.comment.model.group;

import java.util.Collection;

/**
 * Service for work with groups
 *
 * @author Indulgent
 *         07.06.2016.
 */
public interface GroupInfoService {
	/**
	 * Get all groups
	 *
	 * @return groups
	 */
	Collection<GroupInfo> getAll();
}
