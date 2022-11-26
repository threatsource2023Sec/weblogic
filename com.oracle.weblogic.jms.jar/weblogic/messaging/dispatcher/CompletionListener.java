package weblogic.messaging.dispatcher;

public interface CompletionListener {
   void onCompletion(Object var1);

   void onException(Throwable var1);
}
