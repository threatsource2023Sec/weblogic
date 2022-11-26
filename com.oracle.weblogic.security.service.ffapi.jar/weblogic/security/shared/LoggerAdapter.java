package weblogic.security.shared;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface LoggerAdapter {
   Object getLogger(String var1);

   boolean isDebugEnabled(Object var1);

   void debug(Object var1, Object var2);

   void debug(Object var1, Object var2, Throwable var3);

   void info(Object var1, Object var2);

   void info(Object var1, Object var2, Throwable var3);

   void warn(Object var1, Object var2);

   void warn(Object var1, Object var2, Throwable var3);

   void error(Object var1, Object var2);

   void error(Object var1, Object var2, Throwable var3);

   void severe(Object var1, Object var2);

   void severe(Object var1, Object var2, Throwable var3);
}
