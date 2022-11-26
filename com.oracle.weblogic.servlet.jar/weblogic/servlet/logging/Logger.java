package weblogic.servlet.logging;

import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;

public interface Logger {
   int log(ServletRequestImpl var1, ServletResponseImpl var2);

   void markRotated();

   void needToWriteELFHeaders();
}
