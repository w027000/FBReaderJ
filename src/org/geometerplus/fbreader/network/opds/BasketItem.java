/*
 * Copyright (C) 2010-2011 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.network.opds;

import org.geometerplus.zlibrary.core.util.ZLNetworkUtil;

import org.geometerplus.fbreader.network.NetworkLibrary;
import org.geometerplus.fbreader.network.urlInfo.UrlInfo;
import org.geometerplus.fbreader.network.urlInfo.UrlInfoCollection;

public class BasketItem extends OPDSCatalogItem {
	BasketItem(OPDSNetworkLink link, UrlInfoCollection<?> urls) {
		super(
			link,
			NetworkLibrary.resource().getResource("basket").getValue(),
			NetworkLibrary.resource().getResource("basketSummaryEmpty").getValue(),
			urls,
			Accessibility.ALWAYS,
			FLAGS_DEFAULT & ~FLAGS_GROUP
		);
		link.setSupportsBasket();
	}

	@Override
	public CharSequence getSummary() {
		final int size = Link.basket().bookIds().size();
		if (size == 0) {
			return super.getSummary();
		}
		return NetworkLibrary.resource().getResource("basketSummary").getValue()
			.replace("%0", String.valueOf(size)).replace("%1", "XXX");
	}

	@Override
	protected String getCatalogUrl() {
		final StringBuilder builder = new StringBuilder();
		boolean flag = false;
		for (String bookId : Link.basket().bookIds()) {
			if (flag) {
				builder.append(',');
			} else {
				flag = true;
			}
			builder.append(bookId);
		}

		return ZLNetworkUtil.appendParameter(getUrl(UrlInfo.Type.Catalog), "ids", builder.toString());
	}
}
