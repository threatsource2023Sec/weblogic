package weblogic.cluster;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.NamingException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.Environment;
import weblogic.jndi.WLContext;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.rmi.cluster.ClusterableRemoteObject;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;

public final class BasicServiceOffer implements ServiceOffer, Externalizable {
   private static final long serialVersionUID = 2651365883729651106L;
   private int id;
   private String name;
   private Object service;
   private HostID memID;
   private long creationTime;
   private int oldID;
   private String appId;
   private String partitionName;

   BasicServiceOffer(int id, String name, String appId, String partitionName, Object service) {
      this.oldID = -1;
      this.id = id;
      this.name = name;
      this.appId = appId;
      this.partitionName = partitionName;
      this.service = service;
      this.creationTime = System.currentTimeMillis();
   }

   BasicServiceOffer(int id, String name, String appId, String partitionName, Object service, int oldID) {
      this(id, name, appId, partitionName, service);
      this.oldID = oldID;
   }

   public int id() {
      return this.id;
   }

   public String name() {
      return this.name;
   }

   public String appID() {
      return this.appId;
   }

   public String serviceName() {
      return " from " + this.memID.toString();
   }

   public boolean isClusterable() {
      return this.service instanceof ClusterableRemoteObject;
   }

   public long approximateAge() {
      return System.currentTimeMillis() - this.creationTime;
   }

   public int getOldID() {
      return this.oldID;
   }

   public void setServer(HostID memID) {
      this.memID = memID;
   }

   public HostID getServerID() {
      return this.memID;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String toString() {
      return this.name + "@" + this.appId + ":" + (this.partitionName != null ? this.partitionName : "") + ":" + (this.service != null ? this.service.getClass().getName() : "Subcontext") + " (from " + this.memID + ")";
   }

   public void install(Context context) throws NamingException {
      try {
         ManagedInvocationContext mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext(this.partitionName);
         Throwable var3 = null;

         try {
            if (this.service != null) {
               ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);

               try {
                  if (this.appId != null) {
                     avus.setBindApplicationId(this.appId);
                  }

                  context.bind(this.name, this.service);
               } finally {
                  if (this.appId != null) {
                     avus.unsetBindApplicationId();
                  }

               }
            } else {
               context.createSubcontext(this.name);
            }
         } catch (Throwable var21) {
            var3 = var21;
            throw var21;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var19) {
                     var3.addSuppressed(var19);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (IllegalArgumentException var23) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug(var23 + " when installing " + this.name + " in " + this.partitionName);
         }
      }

   }

   public void retract(Context context) throws NamingException {
      try {
         ManagedInvocationContext mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext(this.partitionName);
         Throwable var3 = null;

         try {
            if (this.service != null) {
               ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);

               try {
                  if (this.appId != null) {
                     avus.setBindApplicationId(this.appId);
                  }

                  Hashtable env = (Hashtable)context.getEnvironment().clone();
                  env.remove("weblogic.jndi.partitionInformation");
                  WLContext ctx = (WLContext)(new Environment(env)).getContext(context.getNameInNamespace());
                  ctx.unbind(this.name, this.service);
               } finally {
                  if (this.appId != null) {
                     avus.unsetBindApplicationId();
                  }

               }
            } else {
               try {
                  context.destroySubcontext(this.name);
               } catch (ContextNotEmptyException var24) {
               }
            }
         } catch (Throwable var26) {
            var3 = var26;
            throw var26;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var23) {
                     var3.addSuppressed(var23);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (IllegalArgumentException var28) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug(var28 + " when retracting " + this.name + " in " + this.partitionName);
         }
      }

   }

   public void update(Context context) throws NamingException {
      try {
         ManagedInvocationContext mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext(this.partitionName);
         Throwable var3 = null;

         try {
            if (this.service != null) {
               ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);

               try {
                  if (this.appId != null) {
                     avus.setBindApplicationId(this.appId);
                  }

                  context.rebind(this.name, this.service);
               } finally {
                  if (this.appId != null) {
                     avus.unsetBindApplicationId();
                  }

               }
            } else {
               try {
                  context.destroySubcontext(this.name);
               } catch (ContextNotEmptyException var22) {
               }
            }
         } catch (Throwable var24) {
            var3 = var24;
            throw var24;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var21) {
                     var3.addSuppressed(var21);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (IllegalArgumentException var26) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug(var26 + " when updating " + this.name + " in " + this.partitionName);
         }
      }

   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      WLObjectOutput out = (WLObjectOutput)oo;
      out.writeInt(this.id);
      out.writeString(this.name);
      if (this.partitionName == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         out.writeString(this.partitionName);
      }

      out.writeObjectWL(this.service);
      out.writeLong(System.currentTimeMillis() - this.creationTime);
      out.writeInt(this.oldID);
      out.writeString(this.appId);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      WLObjectInput in = (WLObjectInput)oi;
      this.id = in.readInt();
      this.name = in.readString();
      boolean hasPartitionName = in.readBoolean();
      if (hasPartitionName) {
         this.partitionName = in.readString();
      }

      this.service = in.readObjectWL();
      this.creationTime = System.currentTimeMillis() - in.readLong();
      this.oldID = in.readInt();

      try {
         this.appId = in.readString();
      } catch (EOFException var5) {
      }

   }

   public BasicServiceOffer() {
      this.oldID = -1;
   }
}
