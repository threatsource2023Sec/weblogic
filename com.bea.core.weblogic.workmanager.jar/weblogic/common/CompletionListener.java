package weblogic.common;

public interface CompletionListener {
   void onCompletion(CompletionRequest var1, Object var2);

   void onException(CompletionRequest var1, Throwable var2);
}
