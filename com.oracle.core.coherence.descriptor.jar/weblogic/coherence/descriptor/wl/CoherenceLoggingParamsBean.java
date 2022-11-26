package weblogic.coherence.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface CoherenceLoggingParamsBean extends SettableBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   String getLoggerName();

   void setLoggerName(String var1);

   String getMessageFormat();

   void setMessageFormat(String var1);
}
