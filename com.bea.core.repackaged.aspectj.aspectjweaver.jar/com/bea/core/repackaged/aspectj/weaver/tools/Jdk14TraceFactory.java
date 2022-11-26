package com.bea.core.repackaged.aspectj.weaver.tools;

public class Jdk14TraceFactory extends TraceFactory {
   public Trace getTrace(Class clazz) {
      return new Jdk14Trace(clazz);
   }
}
