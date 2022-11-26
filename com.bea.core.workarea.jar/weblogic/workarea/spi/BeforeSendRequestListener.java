package weblogic.workarea.spi;

import weblogic.workarea.WorkContextMap;

public interface BeforeSendRequestListener {
   void sendRequest(int var1, WorkContextMap var2);
}
