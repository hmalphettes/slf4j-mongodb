/* 
   Copyright 2011 Christian Trutz

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.slf4j.impl.mongo;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.helpers.MarkerIgnoringBase;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * {@link Logger} writing log messages into mongoDB.
 * 
 * @author <a href="mailto:christian.trutz@gmail.com">Christian Trutz</a>
 */
public class MongoLogger extends MarkerIgnoringBase {

	/**
	 * @see Serializable
	 */
	private static final long serialVersionUID = -5313857708266728847L;

	/**
	 * Logger name.
	 */
	private final String name;

	/**
	 * mongoDB trace collection.
	 */
	private final DBCollection traceCollection;

	/**
	 * mongoDB debug collection.
	 */
	private final DBCollection debugCollection;

	/**
	 * mongoDB info collection.
	 */
	private final DBCollection infoCollection;

	/**
	 * mongoDB warn collection.
	 */
	private final DBCollection warnCollection;

	/**
	 * mongoDB error collection.
	 */
	private final DBCollection errorCollection;

	/**
	 * 
	 * @param name
	 * @param traceCollection
	 * @param debugCollection
	 * @param infoCollection
	 * @param warnCollection
	 * @param errorCollection
	 */
	protected MongoLogger(final String name,
			final DBCollection traceCollection,
			final DBCollection debugCollection,
			final DBCollection infoCollection,
			final DBCollection warnCollection,
			final DBCollection errorCollection) {
		this.name = name;
		this.traceCollection = traceCollection;
		this.debugCollection = debugCollection;
		this.infoCollection = infoCollection;
		this.warnCollection = warnCollection;
		this.errorCollection = errorCollection;
	}

	/**
	 * 
	 * @param collection
	 * @param throwable
	 * @param message
	 * @param args
	 */
	private void log(final DBCollection collection, final Throwable throwable,
			final String message, final Object... args) {

		Map<String, Object> attributes = new HashMap<String, Object>();

		// logger name
		attributes.put("name", name);

		// throwable
		if (throwable != null) {
			StringWriter writer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(writer));
			attributes.put("throwable", writer.toString());
		}

		// message
		attributes.put("message", message);

		// args
		if (args != null)
			if (args.length > 0)
				attributes.put("args", args);

		DBObject dbObject = new BasicDBObject(attributes);
		collection.insert(dbObject);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTraceEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(String msg) {
		log(traceCollection, null, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(String format, Object arg) {
		log(traceCollection, null, format, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(String format, Object arg1, Object arg2) {
		log(traceCollection, null, format, arg1, arg2);
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(String format, Object[] argArray) {
		log(traceCollection, null, format, argArray);
	}

	/**
	 * {@inheritDoc}
	 */
	public void trace(String msg, Throwable throwable) {
		log(traceCollection, throwable, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDebugEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(String msg) {
		log(debugCollection, null, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(String format, Object arg) {
		log(debugCollection, null, format);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(String format, Object arg1, Object arg2) {
		log(debugCollection, null, format, arg1, arg2);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(String format, Object[] argArray) {
		log(debugCollection, null, format, argArray);
	}

	/**
	 * {@inheritDoc}
	 */
	public void debug(String msg, Throwable throwable) {
		log(debugCollection, throwable, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isInfoEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(String msg) {
		log(infoCollection, null, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(String format, Object arg) {
		log(infoCollection, null, format, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(String format, Object arg1, Object arg2) {
		log(infoCollection, null, format, arg1, arg2);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(String format, Object[] argArray) {
		log(infoCollection, null, format, argArray);
	}

	/**
	 * {@inheritDoc}
	 */
	public void info(String msg, Throwable throwable) {
		log(infoCollection, throwable, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isWarnEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(String msg) {
		log(warnCollection, null, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(String format, Object arg) {
		log(warnCollection, null, format, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(String format, Object arg1, Object arg2) {
		log(warnCollection, null, format, arg1, arg2);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(String format, Object[] argArray) {
		log(warnCollection, null, format, argArray);
	}

	/**
	 * {@inheritDoc}
	 */
	public void warn(String msg, Throwable throwable) {
		log(warnCollection, throwable, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isErrorEnabled() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(String msg) {
		log(errorCollection, null, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(String format, Object arg) {
		log(errorCollection, null, format, arg);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(String format, Object arg1, Object arg2) {
		log(errorCollection, null, format, arg1, arg2);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(String format, Object[] argArray) {
		log(errorCollection, null, format, argArray);
	}

	/**
	 * {@inheritDoc}
	 */
	public void error(String msg, Throwable throwable) {
		log(errorCollection, throwable, msg);
	}

}
