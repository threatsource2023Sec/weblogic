package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.apache.commons.logging.LogFactory;

public class CommonsTraceFactory extends TraceFactory {
   private LogFactory logFactory = LogFactory.getFactory();

   public Trace getTrace(Class clazz) {
      return new CommonsTrace(clazz);
   }
}
