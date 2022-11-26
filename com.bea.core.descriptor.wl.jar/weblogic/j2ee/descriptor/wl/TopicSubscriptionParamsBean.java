package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface TopicSubscriptionParamsBean extends SettableBean {
   long getMessagesLimitOverride();

   void setMessagesLimitOverride(long var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();
}
