package org.python.netty.util.internal.logging;

import org.python.apache.log4j.Logger;

public class Log4JLoggerFactory extends InternalLoggerFactory {
   public static final InternalLoggerFactory INSTANCE = new Log4JLoggerFactory();

   public InternalLogger newInstance(String name) {
      return new Log4JLogger(Logger.getLogger(name));
   }
}
