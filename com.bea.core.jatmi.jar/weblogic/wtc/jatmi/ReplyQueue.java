package weblogic.wtc.jatmi;

public interface ReplyQueue {
   void add_reply(gwatmi var1, CallDescriptor var2, tfmh var3);

   ReqMsg get_reply(boolean var1);

   tfmh get_specific_reply(ReqOid var1, boolean var2);
}
