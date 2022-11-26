package weblogic.common.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import weblogic.common.T3ServicesDef;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.time.common.TimeServicesDef;
import weblogic.utils.AssertionError;

public final class T3BindableServices implements T3ServicesDef, Externalizable {
   private static final long serialVersionUID = -9045561245277800956L;
   private JVMID hostID;
   private T3ServicesDef services;

   public T3BindableServices(JVMID hostID) {
      try {
         this.setHostID(hostID);
      } catch (RemoteException var3) {
         throw new AssertionError("Unexpected exception: " + var3);
      }
   }

   public T3BindableServices() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         ((WLObjectOutput)out).writeObjectWL(this.hostID);
      } else {
         out.writeObject(this.hostID);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      JVMID id;
      if (in instanceof WLObjectInput) {
         id = (JVMID)((WLObjectInput)in).readObjectWL();
      } else {
         id = (JVMID)in.readObject();
      }

      this.setHostID(id);
   }

   private void setHostID(JVMID hostID) throws RemoteException {
      if (hostID.equals(JVMID.localID())) {
         this.services = (T3ServicesDef)GlobalServiceLocator.getServiceLocator().getService(T3ServicesDef.class, new Annotation[0]);
      } else {
         RJVM rjvm = RJVMManager.getRJVMManager().findOrCreate(hostID);
         this.services = (T3ServicesDef)rjvm.getColocatedServices();
      }

      this.hostID = hostID;
   }

   public TimeServicesDef time() {
      return this.services.time();
   }

   public static void initialize() {
      try {
         ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
         env.setCreateIntermediateContexts(true);
         env.setReplicateBindings(false);
         env.setProperty("weblogic.jndi.createUnderSharable", "true");
         env.getInitialContext().bind("weblogic.common.T3Services", new T3BindableServices(JVMID.localID()));
      } catch (NamingException var1) {
         throw new AssertionError(var1);
      }
   }
}
