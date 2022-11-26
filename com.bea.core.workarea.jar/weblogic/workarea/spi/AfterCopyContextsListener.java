package weblogic.workarea.spi;

import weblogic.workarea.WorkContextMap;

public interface AfterCopyContextsListener {
   void contextsCopied(int var1, WorkContextMap var2, WorkContextMap var3);
}
