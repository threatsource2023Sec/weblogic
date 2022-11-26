package weblogic.ejb.container.internal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import weblogic.ejb.container.EJBLogger;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.AggregatableInternal;
import weblogic.jndi.ClassTypeOpaqueReference;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.internal.NamingNode;
import weblogic.logging.Loggable;
import weblogic.protocol.LocalServerIdentity;

public class AggregatableOpaqueReference implements ClassTypeOpaqueReference, AggregatableInternal {
   private static final long serialVersionUID = 464050940789719368L;
   private final String uniqueEJBIdentifier;
   private final String interfaceName;
   private final Object referent;
   private final Set serverIDs = new HashSet();
   private Object serverID;
   private transient Map serverIdToReferent;
   private static final Object localServerID = LocalServerIdentity.getIdentity();

   public AggregatableOpaqueReference(String uniqueEJBIdentifier, String interfaceName, Object referent) {
      this.uniqueEJBIdentifier = uniqueEJBIdentifier;
      this.interfaceName = interfaceName;
      this.referent = referent;
      this.serverID = localServerID;
   }

   public void onBind(NamingNode store, String name, Aggregatable other) throws NamingException {
      if (other != null) {
         this.validateAdditionalBinding(name, other);
      }

      AggregatableOpaqueReference aor = (AggregatableOpaqueReference)other;
      if (aor == null) {
         aor = this;
      }

      synchronized(this) {
         if (this.serverIdToReferent == null) {
            this.serverIdToReferent = new HashMap();
         }

         this.serverIdToReferent.put(aor.serverID, aor.referent);
         this.serverIDs.add(aor.serverID);
      }
   }

   public void onRebind(NamingNode store, String name, Aggregatable other) throws NamingException {
      this.onBind(store, name, other);
   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      Object id = null;
      if (other == null) {
         id = localServerID;
      } else {
         id = ((AggregatableOpaqueReference)other).serverID;
      }

      synchronized(this) {
         this.serverIdToReferent.remove(id);
         this.serverIDs.remove(id);
      }

      return this.serverIDs.isEmpty();
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      if (this.serverIdToReferent == null) {
         this.serverIdToReferent = new HashMap();
      }

      Object referent = this.serverIdToReferent.get(localServerID);
      if (referent == null) {
         Iterator var4 = this.serverIdToReferent.values().iterator();
         if (var4.hasNext()) {
            Object obj = var4.next();
            referent = obj;
         }

         if (referent == null) {
            referent = this.referent;
         }
      }

      return referent instanceof OpaqueReference ? ((OpaqueReference)referent).getReferent(name, ctx) : referent;
   }

   public String toString() {
      return "AggregatableOpaqueReference for interface " + this.interfaceName + " of EJB " + this.uniqueEJBIdentifier;
   }

   public boolean isBindable(AggregatableInternal object) {
      if (!(object instanceof AggregatableOpaqueReference)) {
         return false;
      } else {
         AggregatableOpaqueReference aor = (AggregatableOpaqueReference)object;
         if (!this.uniqueEJBIdentifier.equals(aor.uniqueEJBIdentifier)) {
            return false;
         } else {
            return this.interfaceName.equals(aor.interfaceName);
         }
      }
   }

   private void validateAdditionalBinding(String name, Object o) throws NameAlreadyBoundException {
      if (!(o instanceof AggregatableOpaqueReference)) {
         throw new NameAlreadyBoundException();
      } else {
         AggregatableOpaqueReference aor = (AggregatableOpaqueReference)o;
         Loggable log;
         if (!this.uniqueEJBIdentifier.equals(aor.uniqueEJBIdentifier)) {
            log = EJBLogger.logAlreadyBindInterfaceWithNameLoggable(aor.interfaceName, name);
            throw new NameAlreadyBoundException(log.getMessage());
         } else if (!this.interfaceName.equals(aor.interfaceName)) {
            log = EJBLogger.logAnotherInterfaceBindWithNameLoggable(aor.interfaceName, name);
            throw new NameAlreadyBoundException(log.getMessage());
         }
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      synchronized(this) {
         s.defaultWriteObject();
      }
   }

   public Class getObjectClass() {
      return this.referent instanceof ClassTypeOpaqueReference ? ((ClassTypeOpaqueReference)this.referent).getObjectClass() : this.referent.getClass();
   }
}
