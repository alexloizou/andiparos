/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.parosproxy.paros.core.spider;

import java.util.Vector;

import org.apache.commons.httpclient.URI;

public class Base extends org.parosproxy.paros.core.spider.Tag {

	// parser for "A" tag
	private static final ParserTag parser = new ParserTag("BASE",
			ParserTag.CLOSING_TAG_NO);

	// parser for "href" attribute in tag
	private static final ParserAttr parserAttrHref = new ParserAttr(Attr.HREF);

	private String href = "";

	/**
	 * Get an array of "BASE" tags.
	 * 
	 * @param doc
	 *            The html document to be parsed.
	 * @return array of "A"
	 */
	public static Base[] getBases(String doc) {

		Vector<Base> bases = new Vector<Base>();
		parser.parse(doc);
		while (parser.nextTag()) {
			String content = parser.getContent();
			String attrs = parser.getAttrs();
			Base base = new Base();
			base.buildAttrs(attrs);
			base.build(content);
			bases.addElement(base);
		}

		Base[] result = new Base[bases.size()];
		result = (Base[]) bases.toArray(result);
		return result;
	}

	protected void buildAttrs(String attrs) {
		super.buildAttrs(attrs);

		String tmp = parserAttrHref.getValue(attrs);
		try {
			URI uri = new URI(tmp, false);
			if (uri.isAbsoluteURI()) {
				href = tmp;
			}
		} catch (Exception e) {
		}

	}

	/**
	 * @return Returns the href.
	 */
	public String getHref() {
		return href;
	}
}