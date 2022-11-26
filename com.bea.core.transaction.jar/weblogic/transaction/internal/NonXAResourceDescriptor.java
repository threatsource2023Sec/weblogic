package weblogic.transaction.internal;

import java.util.ArrayList;
import java.util.Map;
import javax.naming.NameAlreadyBoundException;
import javax.transaction.SystemException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.nonxa.NonXAException;
import weblogic.transaction.nonxa.NonXAResource;

final class NonXAResourceDescriptor extends ResourceDescriptor {
   private NonXAResource nonXAResource;
   private NonXAResourceRuntime runtimeMBean;
   private NonXAResourceRuntime partitionRuntimeMBean;
   private boolean loggingResource;

   private NonXAResourceDescriptor(String aName, NonXAResource nxar, int aResourceType) {
      super(aName);
   }

   private void initNonXAResource(NonXAResource nxar, int resourceType) {
      boolean newResource = false;
      synchronized(this) {
         if (nxar != null && !this.isRegistered()) {
            this.setNonXAResource(nxar);
            this.setResourceType(resourceType);
            this.setRegistered(true);
            newResource = true;
         }
      }

      if (newResource) {
         if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTANonXA.isDebugEnabled()) {
            debug("initNonXAResource(aXar=" + nxar + ")");
         }

         try {
            JNDIAdvertiser.advertiseNonXAResource(this.getName());
         } catch (NameAlreadyBoundException var6) {
         } catch (Exception var7) {
            TXLogger.logAdvertiseNonXAResourceError(this.getName(), var7);
         }

         if (PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext() != null) {
            this.setComponentInvocationContext(PlatformHelper.getPlatformHelper().getCurrentComponentInvocationContext());
         }

         if (PlatformHelper.getPlatformHelper().getPartitionName() != null) {
            this.setPartitionName(PlatformHelper.getPlatformHelper().getPartitionName());
         }

         this.registerMBean();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.toString()).append("nonXAResource = ").append(this.nonXAResource).append("\n");
      return sb.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof NonXAResourceDescriptor)) {
         return false;
      } else {
         NonXAResourceDescriptor that = (NonXAResourceDescriptor)o;
         return this.getName().equals(that.getName());
      }
   }

   static void registerResource(String name, NonXAResource nxar, int resourceType) {
      ResourceDescriptor rd = get(name);
      if (rd != null && !(rd instanceof NonXAResourceDescriptor)) {
         TXLogger.logLookingForResourceDescriptorFailure(name, rd.getSCsToString());
         String msg = "Registration of resource " + name + " failed";
         String cause = "This resource is trying to be registered as a NonXAResource and was already registered as a XAResource, there is a naming conflict.";
         SystemException se = new SystemException(msg);
         se.initCause(new ClassCastException(cause));
         rd.setResourceDescriptorNamingConflict(se);
      } else {
         NonXAResourceDescriptor nxard = (NonXAResourceDescriptor)rd;
         if (nxard != null) {
            nxard.initNonXAResource(nxar, resourceType);
         } else {
            nxard = (NonXAResourceDescriptor)create(name, nxar, resourceType);
         }

         if (nxar != null) {
            nxard.addSC(getTM().getLocalCoordinatorDescriptor());
         }

         if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
            debugStack(nxard, "register");
         }

      }
   }

   static ResourceDescriptor getOrCreate(NonXAResource nxar) {
      NonXAResourceDescriptor rd = (NonXAResourceDescriptor)get(nxar);
      if (rd != null) {
         if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
            debug(rd, "getOrCreate gets rd: " + rd);
         }

         return rd;
      } else if (nxar == null) {
         return null;
      } else {
         String nxarName;
         if (nxar instanceof LoggingResource) {
            nxarName = ((LoggingResource)nxar).getName();
         } else {
            nxarName = nxar.getClass().getName();
         }

         rd = (NonXAResourceDescriptor)get(nxarName);
         if (rd != null) {
            if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
               debug(rd, "getOrCreate gets rd: " + rd);
            }

            rd.initNonXAResource(nxar, 2);
            return rd;
         } else {
            rd = (NonXAResourceDescriptor)create(nxarName, nxar, 2);
            if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
               debug(rd, "getOrCreate creates rd: " + rd);
            }

            return rd;
         }
      }
   }

   static ResourceDescriptor getOrCreate(String name) {
      ResourceDescriptor rd = get(name);
      if (rd != null) {
         return rd;
      } else {
         rd = create(name, (NonXAResource)null, 2);
         return rd;
      }
   }

   NonXAResource getNonXAResource() {
      return this.nonXAResource;
   }

   NonXAResourceRuntime getRuntimeMBean() {
      return this.runtimeMBean;
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc) {
      return this.isAccessibleAt(aCoDesc, false);
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc, boolean isResourceNotFound) {
      if (aCoDesc.equals(getTM().getLocalCoordinatorDescriptor())) {
         if (this.getNonXAResource() == null) {
            return false;
         } else {
            return this.isRegistered();
         }
      } else {
         return this.isAvailableAtSC(aCoDesc);
      }
   }

   void unregister() throws SystemException {
      if (TxDebug.JTANonXA.isDebugEnabled() || TxDebug.JTALLR.isDebugEnabled()) {
         debugStack("unregister");
      }

      JNDIAdvertiser.unAdvertiseNonXAResource(this.getName());
      this.unregisterMBean();
   }

   protected boolean includeInCheckpoint() {
      this.checkpointed = PlatformHelper.getPlatformHelper().isCheckpointLLR() && this.isLoggingResource() && this.getTxRefCount() > 0;
      return this.checkpointed;
   }

   private boolean isLoggingResource() {
      return this.loggingResource;
   }

   private static ResourceDescriptor get(NonXAResource nxar) {
      if (nxar == null) {
         return null;
      } else {
         ArrayList rdList = getResourceDescriptorList();
         if (rdList == null) {
            return null;
         } else {
            int len = rdList.size();

            for(int i = 0; i < len; ++i) {
               ResourceDescriptor rd = (ResourceDescriptor)rdList.get(i);
               if (rd instanceof NonXAResourceDescriptor) {
                  NonXAResourceDescriptor nxard = (NonXAResourceDescriptor)rd;
                  NonXAResource rdnxar = nxard.nonXAResource;

                  try {
                     if (rdnxar != null && (rdnxar.equals(nxar) || rdnxar.isSameRM(nxar))) {
                        return nxard;
                     }
                  } catch (NonXAException var8) {
                  }
               }
            }

            return null;
         }
      }
   }

   static ResourceDescriptor get(String name) {
      ArrayList rdList = getResourceDescriptorList();
      if (rdList == null) {
         return null;
      } else {
         ResourceDescriptor rd = null;
         int len = rdList.size();

         for(int i = 0; i < len; ++i) {
            rd = (ResourceDescriptor)rdList.get(i);
            if (rd.name.equals(name)) {
               return rd;
            }
         }

         return null;
      }
   }

   boolean needsStaticEnlistment(boolean enlistOOAlso) {
      return false;
   }

   void tallyCompletion(ServerResourceInfo aResourceInfo, Exception aReason) {
      if (this.runtimeMBean != null) {
         this.runtimeMBean.tallyCompletion(aResourceInfo, (NonXAException)aReason);
      }

      String partitionName = this.getPartitionName();
      if (partitionName != null && this.partitionRuntimeMBean != null) {
         this.partitionRuntimeMBean.tallyCompletion(aResourceInfo, (NonXAException)aReason);
      }

   }

   void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("NonXAResource");
      super.dump(imageSource, xsw);
      xsw.writeEndElement();
   }

   private static ResourceDescriptor create(String name, NonXAResource nxar, int resourceType) {
      NonXAResourceDescriptor rdnew = new NonXAResourceDescriptor(name, nxar, resourceType);
      synchronized(resourceDescriptorLock) {
         NonXAResourceDescriptor rd = (NonXAResourceDescriptor)get(name);
         if (rd != null) {
            rd.initNonXAResource(nxar, resourceType);
            return rd;
         } else {
            ArrayList clone = (ArrayList)resourceDescriptorList.clone();
            clone.add(rdnew);
            resourceDescriptorList = clone;
            rdnew.initNonXAResource(nxar, resourceType);
            return rdnew;
         }
      }
   }

   private void setNonXAResource(NonXAResource nxar) {
      this.nonXAResource = nxar;
      if (this.nonXAResource instanceof LoggingResource) {
         this.loggingResource = true;
      } else {
         this.loggingResource = false;
      }

   }

   private void registerMBean() {
      try {
         if (this.runtimeMBean != null) {
            return;
         }

         this.runtimeMBean = getTM().getRuntime().registerNonXAResource(this.getName());
         String partitionName = this.getPartitionName();
         if (partitionName != null) {
            JTAPartitionRuntime jtaPartitionRuntime = getTM().getPartitionRuntime(partitionName);
            if (jtaPartitionRuntime != null) {
               this.partitionRuntimeMBean = jtaPartitionRuntime.registerNonXAResource(this.getName());
            }
         }
      } catch (Exception var3) {
         TXLogger.logResourceMBeanCreateFailed(this.getName(), var3);
      }

   }

   private void unregisterMBean() {
      try {
         if (this.runtimeMBean != null) {
            getTM().getRuntime().unregisterNonXAResource(this.runtimeMBean);
            this.runtimeMBean = null;
            if (this.partitionRuntimeMBean != null) {
               String partitionName = this.getPartitionName();
               if (partitionName != null) {
                  JTAPartitionRuntime jtaPartitionRuntime = getTM().getPartitionRuntime(partitionName);
                  if (jtaPartitionRuntime != null) {
                     jtaPartitionRuntime.unregisterNonXAResource(this.partitionRuntimeMBean);
                     this.partitionRuntimeMBean = null;
                  }
               }
            }
         }
      } catch (Exception var3) {
         TXLogger.logUnregisterResMBeanError(var3);
      }

   }

   Map getProperties() {
      return null;
   }

   void setProperties(Map properties) {
   }

   private void addToLocalCoordinatorDescriptor() {
      ServerCoordinatorDescriptor local = (ServerCoordinatorDescriptor)getTM().getLocalCoordinatorDescriptor();
      if (local != null) {
         local.addNonXAResourceDescriptor(this);
      }

   }

   private void removeFromLocalCoordinatorDescriptor() {
      ServerCoordinatorDescriptor local = (ServerCoordinatorDescriptor)getTM().getLocalCoordinatorDescriptor();
      if (local != null) {
         local.removeNonXAResourceDescriptor(this);
      }

   }

   private static void debug(String s) {
      if (TxDebug.JTANonXA.isDebugEnabled()) {
         TxDebug.JTANonXA.debug(s);
      } else if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.JTALLR.debug(s);
      }

   }

   private static void debug(ResourceDescriptor rd, String s) {
      if (rd != null) {
         s = "ResourceDescriptor[" + rd.getName() + "]" + s;
      }

      debug(s);
   }

   private static void debugStack(String s) {
      if (TxDebug.JTANonXA.isDebugEnabled()) {
         TxDebug.debugStack(TxDebug.JTANonXA, s);
      } else if (TxDebug.JTALLR.isDebugEnabled()) {
         TxDebug.debugStack(TxDebug.JTALLR, s);
      }

   }

   private static void debugStack(ResourceDescriptor rd, String s) {
      if (rd != null) {
         s = "ResourceDescriptor[" + rd.getName() + "]" + s;
      }

      debugStack(s);
   }
}
