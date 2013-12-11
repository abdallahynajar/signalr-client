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

/**
 * Represents an URI builder.
 */
public final class URIBuilder {

    private String _schema;

    private String _userInfo;

    private String _host;

    private int _port;

    private String _path;

    private String _query;

    private String _fragment;

    public URIBuilder(final String uri) {
        this(toURI(uri));
    }

    public URIBuilder(final URI uri) {
        if (uri == null)
            throw new InvalidParameterException("URI must not be null");

        init(uri);
    }

    public URIBuilder(final String uri, final String path) {
        this(toURI(uri), path);
    }

    public URIBuilder(final URI uri, final String path) {
        if (uri == null)
            throw new InvalidParameterException("URI must not be null");

        init(uri.resolve(path));
    }

    private static URI toURI(final String uri) {
        if (uri == null)
            throw new InvalidParameterException("URI must not be null");

        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new URIException(e);
        }
    }

    private void init(final URI uri) {
        _schema = uri.getScheme();
        _userInfo = uri.getUserInfo();
        _host = uri.getHost();
        _port = uri.getPort();
        _path = uri.getPath();
        _query = uri.getQuery();
        _fragment = uri.getFragment();
    }

    public String getSchema() {
        return _schema;
    }

    public void setSchema(final String schema) {
        _schema = schema;
    }

    public String getHost() {
        return _host;
    }

    public void setHost(final String host) {
        _host = host;
    }

    public int getPort() {
        return _port;
    }

    public void setPort(final int port) {
        _port = port;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(final String path) {
        _path = path;
    }

    public String getQuery() {
        return _query;
    }

    public void setQuery(final String query) {
        _query = query;
    }

    public String getFragment() {
        return _fragment;
    }

    public void setFragment(final String fragment) {
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
