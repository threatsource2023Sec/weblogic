package weblogic.workarea.spi;

import weblogic.workarea.WorkContextMap;

public interface AfterSendRequestListener {
   void requestSent(int var1, WorkContextMap var2);
}
