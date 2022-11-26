package weblogic.buzzmessagebus;

public interface BuzzHTTPSupport {
   BuzzHTTP register(BuzzHTTP var1);

   void registerForRead();

   void messageInitiated();

   void messageCompleted();

   void requeue();
}
