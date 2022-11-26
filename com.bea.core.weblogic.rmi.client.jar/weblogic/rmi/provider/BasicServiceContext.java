package weblogic.rmi.provider;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.spi.ServiceContext;

public class BasicServiceContext implements ServiceContext, Externalizable {
   private int id;
   protected Object data;
   private boolean user;

   public BasicServiceContext() {
   }

   public BasicServiceContext(int id, Object data) {
      this(id, data, false);
   }

   public BasicServiceContext(int id, Object data, boolean user) {
      this.id = id;
      this.data = data;
      this.user = user;
   }

   public int getContextId() {
      return this.id;
   }

   public Object getContextData() {
      return this.data;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.write(this.id);
      out.writeBoolean(this.user);
      out.writeObject(this.data);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.id = in.read();
      this.user = in.readBoolean();
      this.data = in.readObject();
   }

   public boolean isUser() {
      return this.user;
   }

   public String toString() {
      return "Context #" + this.id + " " + this.data;
   }
}
