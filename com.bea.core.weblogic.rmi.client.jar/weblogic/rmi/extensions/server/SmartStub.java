package weblogic.rmi.extensions.server;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/** @deprecated */
@Deprecated
public abstract class SmartStub implements StubDelegateInfo, Externalizable {
   private Object delegate;

   public SmartStub() {
   }

   public SmartStub(Object delegate) {
      this.delegate = delegate;
   }

   public final Object getStubDelegate() {
      return this.delegate;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.delegate);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.delegate = in.readObject();
   }
}
