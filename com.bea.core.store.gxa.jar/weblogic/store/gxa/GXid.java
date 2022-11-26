package weblogic.store.gxa;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.transaction.xa.Xid;
import weblogic.store.gxa.internal.GXidImpl;

public abstract class GXid {
   public abstract Xid getXAXid();

   public abstract String toString();

   public static GXid read(ObjectInput oi) throws IOException {
      GXidImpl gxidImpl = new GXidImpl();
      gxidImpl.readExternal(oi);
      return gxidImpl;
   }

   public void write(ObjectOutput oo) throws IOException {
      ((Externalizable)this).writeExternal(oo);
   }
}
