package weblogic.management.patching.agent;

public interface ZdtAgentOutputHandler {
   void error(String var1);

   void error(String var1, Exception var2);

   void info(String var1);

   void warning(String var1);

   boolean isDebugEnabled();

   void debug(String var1);
}
