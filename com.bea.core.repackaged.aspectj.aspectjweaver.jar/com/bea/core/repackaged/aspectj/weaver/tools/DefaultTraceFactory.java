package com.bea.core.repackaged.aspectj.weaver.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DefaultTraceFactory extends TraceFactory {
   public static final String ENABLED_PROPERTY = "com.bea.core.repackaged.aspectj.tracing.enabled";
   public static final String FILE_PROPERTY = "com.bea.core.repackaged.aspectj.tracing.file";
   private boolean tracingEnabled = getBoolean("com.bea.core.repackaged.aspectj.tracing.enabled", false);
   private PrintStream print;

   public DefaultTraceFactory() {
      String filename = System.getProperty("com.bea.core.repackaged.aspectj.tracing.file");
      if (filename != null) {
         File file = new File(filename);

         try {
            this.print = new PrintStream(new FileOutputStream(file));
         } catch (IOException var4) {
            if (debug) {
               var4.printStackTrace();
            }
         }
      }

   }

   public boolean isEnabled() {
      return this.tracingEnabled;
   }

   public Trace getTrace(Class clazz) {
      DefaultTrace trace = new DefaultTrace(clazz);
      trace.setTraceEnabled(this.tracingEnabled);
      if (this.print != null) {
         trace.setPrintStream(this.print);
      }

      return trace;
   }
}
