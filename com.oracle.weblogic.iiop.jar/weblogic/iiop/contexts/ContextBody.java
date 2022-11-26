package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaOutputStream;

public abstract class ContextBody {
   public abstract long getClientContextId();

   public boolean shouldEstablishContext() {
      return false;
   }

   public boolean shouldDiscardContext() {
      return false;
   }

   public abstract void write(CorbaOutputStream var1);
}
