package weblogic.security.spi;

import weblogic.security.service.ContextHandler;

public interface AdjudicatorV2 {
   void initialize(String[] var1);

   boolean adjudicate(Result[] var1, Resource var2, ContextHandler var3);
}
