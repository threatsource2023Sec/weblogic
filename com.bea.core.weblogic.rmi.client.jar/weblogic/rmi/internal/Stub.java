package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.Replacer;

public class Stub implements Serializable, weblogic.rmi.extensions.server.Stub {
   private static final long serialVersionUID = -6029212487682289828L;
   private RemoteReference ror;

   public Stub(StubReference info) {
      this(info.getRemoteRef());
   }

   public Stub(RemoteReference reference) {
      this.ror = reference;
   }

   public RemoteReference getRemoteRef() {
      return this.ror;
   }

   public String toString() {
      return this.ror.toString();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof Stub) {
         Stub other = (Stub)obj;
         return this.ror.equals(other.ror);
      } else if (obj instanceof StubInfoIntf) {
         StubInfoIntf intf = (StubInfoIntf)obj;
         return this.ror.equals(intf.getStubInfo().getRemoteRef());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.ror.hashCode();
   }

   protected final Object writeReplace() throws ObjectStreamException, IOException {
      Replacer replacer = RemoteObjectReplacer.getReplacer();
      Object temp = replacer.replaceObject(this);
      Object writeObject = replacer.replaceObject(temp);
      return writeObject;
   }

   static {
      try {
         RJVMEnvironment.getEnvironment().ensureInitialized();
      } catch (Exception var1) {
         throw new AssertionError("Unexpected exception", var1);
      }
   }
}
