package weblogic.security.spi;

import java.util.List;
import java.util.Set;
import weblogic.security.service.ContextHandler;

public interface BulkAdjudicator {
   void initialize(String[] var1);

   Set adjudicate(List var1, List var2, ContextHandler var3);
}
