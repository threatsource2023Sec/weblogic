package weblogic.apache.org.apache.log.format;

import weblogic.apache.org.apache.log.ContextMap;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.util.StackIntrospector;

public class ExtendedPatternFormatter extends PatternFormatter {
   private static final int TYPE_METHOD = 9;
   private static final int TYPE_THREAD = 10;
   private static final String TYPE_METHOD_STR = "method";
   private static final String TYPE_THREAD_STR = "thread";
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$log$Logger;

   public ExtendedPatternFormatter(String format) {
      super(format);
   }

   protected int getTypeIdFor(String type) {
      if (type.equalsIgnoreCase("method")) {
         return 9;
      } else {
         return type.equalsIgnoreCase("thread") ? 10 : super.getTypeIdFor(type);
      }
   }

   protected String formatPatternRun(LogEvent event, PatternFormatter.PatternRun run) {
      switch (run.m_type) {
         case 9:
            return this.getMethod(event, run.m_format);
         case 10:
            return this.getThread(event, run.m_format);
         default:
            return super.formatPatternRun(event, run);
      }
   }

   private String getMethod(LogEvent event, String format) {
      ContextMap map = event.getContextMap();
      if (null != map) {
         Object object = map.get("method");
         if (null != object) {
            return object.toString();
         }
      }

      String result = StackIntrospector.getCallerMethod(class$weblogic$apache$org$apache$log$Logger == null ? (class$weblogic$apache$org$apache$log$Logger = class$("weblogic.apache.org.apache.log.Logger")) : class$weblogic$apache$org$apache$log$Logger);
      return null == result ? "UnknownMethod" : result;
   }

   private String getThread(LogEvent event, String format) {
      ContextMap map = event.getContextMap();
      if (null != map) {
         Object object = map.get("thread");
         if (null != object) {
            return object.toString();
         }
      }

      return Thread.currentThread().getName();
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
