package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ThresholdParamsBean extends SettableBean {
   long getBytesHigh();

   void setBytesHigh(long var1) throws IllegalArgumentException;

   long getBytesLow();

   void setBytesLow(long var1) throws IllegalArgumentException;

   long getMessagesHigh();

   void setMessagesHigh(long var1) throws IllegalArgumentException;

   long getMessagesLow();

   void setMessagesLow(long var1) throws IllegalArgumentException;

   TemplateBean getTemplateBean();
}
