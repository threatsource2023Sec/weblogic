package weblogic.messaging.path;

import weblogic.common.CompletionRequest;

public interface AsyncMap {
   void putIfAbsent(Object var1, Object var2, CompletionRequest var3);

   void put(Object var1, Object var2, CompletionRequest var3);

   void get(Object var1, CompletionRequest var2);

   void remove(Object var1, Object var2, CompletionRequest var3);
}
