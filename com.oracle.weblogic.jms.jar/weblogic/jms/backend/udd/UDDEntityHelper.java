package weblogic.jms.backend.udd;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.jms.common.JMSDebug;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.JMSServerMBean;

public class UDDEntityHelper {
   private Set uddEntityInstances = new HashSet();
   private Set localJMSServers = new HashSet();
   private String partitionName = null;

   public UDDEntityHelper(String partitionName) {
      this.partitionName = partitionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void activateLocalJMSServer(String instanceName, JMSServerMBean jmsServer) throws DeploymentException {
      try {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("UDDEntityHelper.activateLocalJMSServer: " + instanceName + " partitionName:" + this.partitionName);
         }

         this.notifyUDD(JMSLegalHelper.getDomain(jmsServer), jmsServer, instanceName, UDDEntity.EntityState.ACTIVATE);
      } catch (RuntimeException var4) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("UDDEntityHelper.Unexpected Exception: ", var4);
         }

         throw var4;
      }
   }

   public void prepareLocalJMSServer(String instanceName, JMSServerMBean jmsServer) throws DeploymentException {
      synchronized(this.localJMSServers) {
         this.localJMSServers.add(instanceName);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("UDDEntityHelper.prepareLocalJMSServer: " + instanceName + " partitionName:" + this.partitionName);
      }

      this.notifyUDD(JMSLegalHelper.getDomain(jmsServer), jmsServer, instanceName, UDDEntity.EntityState.PREPARE);
   }

   public void deactivateLocalJMSServer(String instanceName, JMSServerMBean jmsServer) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("UDDEntityHelper.deactivateLocalJMSServer: " + instanceName + " partitionName:" + this.partitionName);
      }

      try {
         this.notifyUDD(JMSLegalHelper.getDomain(jmsServer), jmsServer, instanceName, UDDEntity.EntityState.DEACTIVATE);
         this.notifyUDD(JMSLegalHelper.getDomain(jmsServer), jmsServer, instanceName, UDDEntity.EntityState.UNPREPARE);
      } catch (DeploymentException var4) {
      }

   }

   public void unprepareLocalJMSServer(String instanceName, JMSServerMBean jmsServer) {
      synchronized(this.localJMSServers) {
         this.localJMSServers.remove(instanceName);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("UDDEntityHelper.unprepareLocalJMSServer: " + instanceName);
      }

      try {
         this.notifyUDD(JMSLegalHelper.getDomain(jmsServer), jmsServer, instanceName, UDDEntity.EntityState.UNPREPARE);
      } catch (DeploymentException var5) {
      }

   }

   public boolean isJMSServerLocal(String jmsServerInstanceName) {
      synchronized(this.localJMSServers) {
         return this.localJMSServers.contains(jmsServerInstanceName);
      }
   }

   private void notifyUDD(DomainMBean domain, JMSServerMBean server, String instanceName, UDDEntity.EntityState newState) throws DeploymentException {
      Set cloneInstances = new HashSet();
      synchronized(this.uddEntityInstances) {
         cloneInstances.addAll(this.uddEntityInstances);
      }

      Iterator var6 = cloneInstances.iterator();

      while(var6.hasNext()) {
         UDDEntity entity = (UDDEntity)var6.next();
         UUID uid = UUID.randomUUID();

         try {
            entity.updateState(uid, domain, instanceName, newState, server);
         } catch (DeploymentException var16) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("UDDEntityHelper.nodifyUDD failure: " + instanceName, var16);
            }

            entity.rollBackState(uid, true);
            throw var16;
         } catch (BeanUpdateRejectedException var17) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("UDDEntityHelper.nodifyUDD failure: " + instanceName, var17);
            }

            entity.rollBackState(uid, true);
            throw new DeploymentException("Unable to deploy", var17);
         } finally {
            entity.complete(uid);
         }
      }

   }

   void addUDDEntity(UDDEntity instance) {
      synchronized(this.uddEntityInstances) {
         if (!this.uddEntityInstances.contains(instance)) {
            this.uddEntityInstances.add(instance);
         }

      }
   }

   void removeUDDEntity(UDDEntity instance) {
      synchronized(this.uddEntityInstances) {
         this.uddEntityInstances.remove(instance);
      }
   }
}
