package javax.enterprise.context;

public interface Conversation {
   void begin();

   void begin(String var1);

   void end();

   String getId();

   long getTimeout();

   void setTimeout(long var1);

   boolean isTransient();
}
