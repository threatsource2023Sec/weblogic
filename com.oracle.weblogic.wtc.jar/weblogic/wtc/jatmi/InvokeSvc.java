package weblogic.wtc.jatmi;

public interface InvokeSvc {
   void invoke(InvokeInfo var1, gwatmi var2) throws TPException;

   void advertise(String var1, TuxedoService var2) throws TPException;

   void unadvertise(String var1) throws TPException;

   void shutdown();
}
