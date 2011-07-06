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

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * A {@link ILoggerFactory} implementation returning {@link MongoLogger}s. Log
 * messages for different log levels (trace, debug, info, warn, error) are
 * stored into different mongoDB collections.
 * 
 * @author <a href="mailto:christian.trutz@gmail.com">Christian Trutz</a>
 */
public class MongoLoggerFactory implements ILoggerFactory {

	/**
	 * Logger cache.
	 */
	private final Map<String, Logger> loggerCache;

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
	 * Initialize {@link #loggerCache} and mongoDB collections.
	 * 
	 * @see #traceCollection
	 * @see #debugCollection
	 * @see #infoCollection
	 * @see #warnCollection
	 * @see #errorCollection
	 */
	public MongoLoggerFactory() {
		loggerCache = new HashMap<String, Logger>();
		try {
			Mongo mongo = new Mongo();

			// TODO make db and collection names configurable
			DB db = mongo.getDB("logger");
			traceCollection = db.getCollection("trace");
			debugCollection = db.getCollection("debug");
			infoCollection = db.getCollection("info");
			warnCollection = db.getCollection("warn");
			errorCollection = db.getCollection("error");

		} catch (UnknownHostException unknownHostException) {
			throw new IllegalStateException(unknownHostException);
		} catch (MongoException mongoException) {
			throw new IllegalStateException(mongoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Logger getLogger(final String name) {
		Logger logger = null;
		// protect against concurrent access of the loggerMap
		synchronized (this) {
			logger = loggerCache.get(name);
			if (logger == null) {
				logger = new MongoLogger(name, traceCollection,
						debugCollection, infoCollection, warnCollection,
						errorCollection);
				loggerCache.put(name, logger);
			}
		}
		return logger;
	}

}
