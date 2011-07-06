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
package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.mongo.MongoLoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Compile time (static) log framework binder.
 * 
 * @author <a href="mailto:christian.trutz@gmail.com">Christian Trutz</a>
 * @see #getLoggerFactory()
 * @see LoggerFactory
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

	/**
	 * Only <b>one</b> instance of this class should exist.
	 */
	private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

	/**
	 * To avoid constant folding by the compiler, this field must <b>not</b> be
	 * final.
	 * 
	 * @see LoggerFactory#versionSanityCheck
	 */
	public static String REQUESTED_API_VERSION = "1.6";

	/**
	 * {@link MongoLoggerFactory} instance.
	 */
	private final ILoggerFactory loggerFactory;

	/**
	 * @see #SINGLETON
	 */
	private StaticLoggerBinder() {
		loggerFactory = new MongoLoggerFactory();
	}

	/**
	 * Return the singleton instance of this class.
	 * 
	 * @return {@link #SINGLETON}
	 */
	public static final StaticLoggerBinder getSingleton() {
		return SINGLETON;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link MongoLoggerFactory} instance
	 */
	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link MongoLoggerFactory} class name
	 */
	public String getLoggerFactoryClassStr() {
		return MongoLoggerFactory.class.getName();
	}

}
