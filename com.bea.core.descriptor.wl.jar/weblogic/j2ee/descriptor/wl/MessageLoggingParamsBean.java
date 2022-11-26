package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface MessageLoggingParamsBean extends SettableBean {
   boolean isMessageLoggingEnabled();

   void setMessageLoggingEnabled(boolean var1) throws IllegalArgumentException;

   String getMessageLoggingFormat();

   void setMessageLoggingFormat(String var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();
}
