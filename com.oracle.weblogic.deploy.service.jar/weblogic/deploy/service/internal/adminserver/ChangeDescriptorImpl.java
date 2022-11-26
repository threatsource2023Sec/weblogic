package weblogic.deploy.service.internal.adminserver;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.deploy.service.ChangeDescriptor;

class ChangeDescriptorImpl implements ChangeDescriptor {
   private static final long serialVersionUID = 4628863147014401090L;
   private String changeOperation;
   private String changeTarget;
   private String changeSource;
   private Serializable data;
   private Serializable version;
   private String identity;

   ChangeDescriptorImpl(String operation, String targetURI, String sourceURI, Serializable version, String identity) {
      this.changeOperation = operation;
      this.changeTarget = targetURI.replace('\\', '/');
      this.changeSource = sourceURI.replace('\\', '/');
      this.version = version;
      this.identity = identity;
   }

   ChangeDescriptorImpl(Serializable input, Serializable version) {
      this.data = input;
      this.version = version;
   }

   public ChangeDescriptorImpl() {
   }

   public String getChangeOperation() {
      return this.changeOperation;
   }

   public String getChangeTarget() {
      return this.changeTarget;
   }

   public String getChangeSource() {
      return this.changeSource;
   }

   public Serializable getVersion() {
      return this.version;
   }

   public Serializable getData() {
      return this.data;
   }

   public String getIdentity() {
      return this.identity;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.data != null) {
         sb.append("Serializable data and version");
      } else {
         sb.append("Change operation: ");
         sb.append(this.changeOperation);
         sb.append(", target: ");
         sb.append(this.changeTarget);
         sb.append(", source: ");
         sb.append(this.changeSource);
         sb.append(", version: ");
         sb.append(this.version);
         sb.append(", id: ");
         sb.append(this.identity);
      }

      return sb.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.getChangeOperation());
      out.writeObject(this.getChangeTarget());
      out.writeObject(this.getChangeSource());
      out.writeObject(this.getData());
      out.writeObject(this.getVersion());
      out.writeObject(this.getIdentity());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.changeOperation = (String)in.readObject();
      this.changeTarget = (String)in.readObject();
      this.changeSource = (String)in.readObject();
      this.data = (Serializable)in.readObject();
      this.version = (Serializable)in.readObject();
      this.identity = (String)in.readObject();
   }
}
