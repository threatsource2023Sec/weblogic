package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface FlowControlParamsBean extends SettableBean {
   int getFlowMinimum();

   void setFlowMinimum(int var1) throws IllegalArgumentException;

   int getFlowMaximum();

   void setFlowMaximum(int var1) throws IllegalArgumentException;

   int getFlowInterval();

   void setFlowInterval(int var1) throws IllegalArgumentException;

   int getFlowSteps();

   void setFlowSteps(int var1) throws IllegalArgumentException;

   boolean isFlowControlEnabled();

   void setFlowControlEnabled(boolean var1) throws IllegalArgumentException;

   String getOneWaySendMode();

   void setOneWaySendMode(String var1) throws IllegalArgumentException;

   int getOneWaySendWindowSize();

   void setOneWaySendWindowSize(int var1) throws IllegalArgumentException;
}
