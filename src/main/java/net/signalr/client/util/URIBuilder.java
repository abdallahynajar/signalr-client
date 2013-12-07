/*
 * Copyright © Martin Tamme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.signalr.client.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;

public final class URIBuilder {

	private String _schema;

	private String _userInfo;

	private String _host;

	private int _port;

	private String _path;

	private String _query;

	private String _fragment;

	public URIBuilder(String uri) {
		this(toURI(uri));
	}

	public URIBuilder(URI uri) {
		if (uri == null)
			throw new InvalidParameterException("URI must not be null");

		init(uri);
	}

	public URIBuilder(String uri, String path) {
		this(toURI(uri), path);
	}

	public URIBuilder(URI uri, String path) {
		if (uri == null)
			throw new InvalidParameterException("URI must not be null");

		init(uri.resolve(path));
	}

	private void init(URI uri) {
		_schema = uri.getScheme();
		_userInfo = uri.getUserInfo();
		_host = uri.getHost();
		_port = uri.getPort();
		_path = uri.getPath();
		_query = uri.getQuery();
		_fragment = uri.getFragment();
	}

	private static URI toURI(String uri) {
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			throw new URIException(e);
		}
	}

	public String getSchema() {
		return _schema;
	}

	public void setSchema(String schema) {
		_schema = schema;
	}

	public String getHost() {
		return _host;
	}

	public void setHost(String host) {
		_host = host;
	}

	public int getPort() {
		return _port;
	}

	public void setPort(int port) {
		_port = port;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	public String getQuery() {
		return _query;
	}

	public void setQuery(String query) {
		_query = query;
	}

	public String getFragment() {
		return _fragment;
	}

	public void setFragment(String fragment) {
		_fragment = fragment;
	}

	public URI toURI() {
		try {
			return new URI(_schema, _userInfo, _host, _port, _path, _query, _fragment);
		} catch (URISyntaxException e) {
			throw new URIException(e);
		}
	}

	@Override
	public String toString() {
		URI uri = toURI();

		return uri.toString();
	}
}
