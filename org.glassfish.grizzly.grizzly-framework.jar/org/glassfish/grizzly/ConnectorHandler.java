package org.glassfish.grizzly;

import java.util.concurrent.Future;

public interface ConnectorHandler {
   Future connect(Object var1);

   void connect(Object var1, CompletionHandler var2);

   Future connect(Object var1, Object var2);

   void connect(Object var1, Object var2, CompletionHandler var3);
}
