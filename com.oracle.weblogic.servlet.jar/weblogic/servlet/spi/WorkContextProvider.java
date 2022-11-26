package weblogic.servlet.spi;

import java.io.IOException;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletWorkContext;

public interface WorkContextProvider {
   void initOrRestoreThreadContexts(ServletWorkContext var1, ServletRequestImpl var2) throws IOException;

   void copyThreadContexts(ServletWorkContext var1, ServletRequestImpl var2);

   void updateWorkContexts(String var1, byte[] var2);

   void removeWorkContext(String var1);
}
