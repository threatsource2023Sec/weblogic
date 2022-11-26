package org.python.netty.util.internal.logging;

import org.python.apache.commons.logging.LogFactory;

/** @deprecated */
@Deprecated
public class CommonsLoggerFactory extends InternalLoggerFactory {
   public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

   public InternalLogger newInstance(String name) {
      return new CommonsLogger(LogFactory.getLog(name), name);
   }
}
