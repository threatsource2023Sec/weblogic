package org.apache.log;

import org.apache.log.format.PatternFormatter;
import org.apache.log.output.io.StreamTarget;
import org.apache.log.util.DefaultErrorHandler;

public class Hierarchy {
   private static final String FORMAT = "%7.7{priority} %5.5{time}   [%8.8{category}] (%{context}): %{message}\\n%{throwable}";
   private static final Hierarchy c_hierarchy = new Hierarchy();
   private ErrorHandler m_errorHandler = new DefaultErrorHandler();
   private Logger m_rootLogger = new Logger(new InnerErrorHandler(), "", (LogTarget[])null, (Logger)null);

   public static Hierarchy getDefaultHierarchy() {
      return c_hierarchy;
   }

   public Hierarchy() {
      PatternFormatter formatter = new PatternFormatter("%7.7{priority} %5.5{time}   [%8.8{category}] (%{context}): %{message}\\n%{throwable}");
      StreamTarget target = new StreamTarget(System.out, formatter);
      this.setDefaultLogTarget(target);
   }

   public void setDefaultLogTarget(LogTarget target) {
      if (null == target) {
         throw new IllegalArgumentException("Can not set DefaultLogTarget to null");
      } else {
         LogTarget[] targets = new LogTarget[]{target};
         this.getRootLogger().setLogTargets(targets);
      }
   }

   public void setDefaultLogTargets(LogTarget[] targets) {
      if (null != targets && 0 != targets.length) {
         for(int i = 0; i < targets.length; ++i) {
            if (null == targets[i]) {
               throw new IllegalArgumentException("Can not set DefaultLogTarget element to null");
            }
         }

         this.getRootLogger().setLogTargets(targets);
      } else {
         throw new IllegalArgumentException("Can not set DefaultLogTargets to null");
      }
   }

   public void setDefaultPriority(Priority priority) {
      if (null == priority) {
         throw new IllegalArgumentException("Can not set default Hierarchy Priority to null");
      } else {
         this.getRootLogger().setPriority(priority);
      }
   }

   public void setErrorHandler(ErrorHandler errorHandler) {
      if (null == errorHandler) {
         throw new IllegalArgumentException("Can not set default Hierarchy ErrorHandler to null");
      } else {
         this.m_errorHandler = errorHandler;
      }
   }

   public Logger getLoggerFor(String category) {
      return this.getRootLogger().getChildLogger(category);
   }

   /** @deprecated */
   public void log(String message, Throwable throwable) {
      this.m_errorHandler.error(message, throwable, (LogEvent)null);
   }

   /** @deprecated */
   public void log(String message) {
      this.log(message, (Throwable)null);
   }

   protected final Logger getRootLogger() {
      return this.m_rootLogger;
   }

   private class InnerErrorHandler implements ErrorHandler {
      private InnerErrorHandler() {
      }

      public void error(String message, Throwable throwable, LogEvent event) {
         Hierarchy.this.m_errorHandler.error(message, throwable, event);
      }

      // $FF: synthetic method
      InnerErrorHandler(Object x1) {
         this();
      }
   }
}
