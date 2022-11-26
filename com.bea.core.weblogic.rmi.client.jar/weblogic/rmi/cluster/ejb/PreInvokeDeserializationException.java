package weblogic.rmi.cluster.ejb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.RemoteEJBPreInvokeException;
import weblogic.rmi.extensions.server.RemoteReference;

public final class PreInvokeDeserializationException extends RemoteEJBPreInvokeException implements Externalizable {
   private static final long serialVersionUID = 1716485207394054084L;
   private RemoteReference failoverRef = null;
   private String failoverURL = null;
   private String message = null;
   private Throwable cause = null;

   public PreInvokeDeserializationException() {
   }

   public PreInvokeDeserializationException(String s) {
      super(s);
      this.message = s;
   }

   public PreInvokeDeserializationException(String s, Throwable th) {
      super(s, th);
      this.message = s;
      this.cause = th;
   }

   public void setFailoverRemoteRef(RemoteReference ref) {
      this.failoverRef = ref;
   }

   public RemoteReference getFailoverRemoteRef() {
      return this.failoverRef;
   }

   public void setFailoverURL(String url) {
      this.failoverURL = url;
   }

   public String getFailoverURL() {
      return this.failoverURL;
   }

   public String getMessage() {
      return this.message;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.failoverRef);
      out.writeObject(this.failoverURL);
      out.writeObject(this.getMessage());
      out.writeObject(this.getCause());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.failoverRef = (RemoteReference)in.readObject();

      try {
         this.failoverURL = (String)in.readObject();
         this.message = (String)in.readObject();
         this.cause = (Throwable)in.readObject();
      } catch (IOException var3) {
      }

   }
}
