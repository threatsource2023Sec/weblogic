package weblogic.wtc.jatmi;

public interface Conversation {
   void tpsend(TypedBuffer var1, int var2) throws TPException;

   Reply tprecv(int var1) throws TPException, TPReplyException;

   void tpdiscon() throws TPException;
}
