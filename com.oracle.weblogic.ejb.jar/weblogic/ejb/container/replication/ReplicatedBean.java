package weblogic.ejb.container.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.cluster.replication.ApplicationUnavailableException;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ReplicatableExt;
import weblogic.cluster.replication.ResourceGroupKey;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;

public class ReplicatedBean implements ReplicatableExt, Externalizable {
   private static final long serialVersionUID = 3890747734666769086L;
   private static final DebugCategory DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
   private static final boolean debug;
   protected String jndiName;
   private transient ReplicatedHome replicatedHome;

   public ReplicatedBean() {
   }

   public ReplicatedBean(String n) {
      this.jndiName = n;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.jndiName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.jndiName = (String)in.readObject();
   }

   protected ReplicatedHome getHome() {
      if (this.replicatedHome == null) {
         try {
            Context ctx = new InitialContext();
            Object obj = PortableRemoteObject.narrow(ctx.lookup(this.jndiName), ReplicatedHome.class);
            this.replicatedHome = (ReplicatedHome)obj;
         } catch (NamingException var3) {
            this.logClustersNotHomogeneous(this.jndiName);
            throw new EJBException(var3);
         }
      }

      return this.replicatedHome;
   }

   public void secondaryCreatedForFullState(ROID id, ResourceGroupKey resourceGroupKey) {
      if (debug) {
         this.debug("** secondaryCreatedForFullState for :" + id + ", resourceGroupKey=" + resourceGroupKey);
      }

      this.getHome().secondaryCreatedForFullState(id, resourceGroupKey);
   }

   public void becomePrimary(ROID id) {
      if (debug) {
         this.debug("** becomePrimary on :" + id);
      }

      try {
         this.getHome().becomePrimary(id);
      } catch (RemoteException var3) {
         EJBLogger.logFailureInReplication(StackTraceUtils.throwable2StackTrace(var3));
      }

   }

   public Object becomeSecondary(ROID key) throws ApplicationUnavailableException {
      if (debug) {
         this.debug("** becomeSecondary " + key);
      }

      try {
         ReplicatedHome replicatedHome = this.getHome();
         if (!ServerHelper.isLocal(replicatedHome)) {
            this.logClustersNotHomogeneous(this.jndiName);
            throw new ApplicationUnavailableException(this.jndiName.toString() + " cannot be found at local instance.");
         } else {
            return replicatedHome.createSecondary(key);
         }
      } catch (RemoteException var3) {
         this.logClustersNotHomogeneous(this.jndiName);
         throw new ApplicationUnavailableException(var3.toString());
      } catch (EJBException var4) {
         throw new ApplicationUnavailableException(var4.toString());
      }
   }

   public void becomeUnregistered(ROID id) {
      try {
         this.getHome().removeSecondary(id);
      } catch (RemoteException var3) {
      }

   }

   public void update(ROID key, Serializable change) {
      if (debug) {
         this.debug("*** Received update for key: " + key);
      }

      try {
         this.getHome().updateSecondary(key, change);
      } catch (RemoteException var4) {
         EJBLogger.logFailedToUpdateSecondaryDuringReplication(this.jndiName, StackTraceUtils.throwable2StackTrace(var4));
      }

   }

   public Object getKey() {
      return DEFAULT_KEY;
   }

   public RemoteReference getPrimaryRemoteRef(ROID id) throws RemoteException {
      if (debug) {
         this.debug("** getPrimaryRemoteRef for key=" + id);
      }

      return this.getHome().getPrimaryRemoteRef(id);
   }

   public RemoteReference getSecondaryRemoteRef(ROID id, boolean fromPrimary) throws RemoteException {
      if (debug) {
         this.debug("** getSecondaryRemoteRef for key=" + id + ", fromPrimary=" + fromPrimary);
      }

      return this.getHome().getSecondaryRemoteRef(id, fromPrimary);
   }

   private void debug(String str) {
      Object workCtxs = ApplicationVersionUtils.getDebugWorkContexts();
      Debug.say(str + (workCtxs == null ? "" : ", workCtxs=" + workCtxs));
   }

   protected void logClustersNotHomogeneous(String jndiName) {
      if (debug) {
         Loggable l = EJBLogger.logClustersNotHomogeneousLoggable(jndiName);
         this.debug(l.getMessageText());
      }

   }

   static {
      debug = DEBUG_APP_VERSION.isEnabled();
   }
}
