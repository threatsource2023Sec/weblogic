package weblogic.jms.saf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.jms.common.JMSDebug;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.utils.GenericManagedDeployment;

public class IDEntityHelper {
   private Map localSAFAgents = Collections.synchronizedMap(new HashMap());
   private LinkedList idInstances = new LinkedList();
   private String partitionName = null;

   public IDEntityHelper(String partitionName) {
      this.partitionName = partitionName;
   }

   String getPartitionName() {
      return this.partitionName;
   }

   public void prepareLocalSAFAgent(GenericManagedDeployment safAgentDeployment) throws DeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDEntityHelper.prepareLocalSAFAgent: " + safAgentDeployment.getName());
      }

      this.localSAFAgents.put(safAgentDeployment.getName(), safAgentDeployment.getMBean().getName());
      this.notifyIDs(JMSLegalHelper.getDomain(safAgentDeployment.getMBean()), true);
   }

   public void activateLocalSAFAgent(GenericManagedDeployment safAgentDeployment) throws DeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDEntityHelper.activateLocalSAFAgent: " + safAgentDeployment.getName());
      }

      this.localSAFAgents.put(safAgentDeployment.getName(), safAgentDeployment.getMBean().getName());
      this.notifyIDs(JMSLegalHelper.getDomain(safAgentDeployment.getMBean()), true);
      this.notifyIDs(JMSLegalHelper.getDomain(safAgentDeployment.getMBean()), false);
   }

   public void deactivateLocalSAFAgent(GenericManagedDeployment safAgentDeployment) throws UndeploymentException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDEntityHelper.deactivateLocalSAFAgent: " + safAgentDeployment.getName());
      }

      this.localSAFAgents.remove(safAgentDeployment.getName());

      try {
         this.notifyIDs(JMSLegalHelper.getDomain(safAgentDeployment.getMBean()), true);
         this.notifyIDs(JMSLegalHelper.getDomain(safAgentDeployment.getMBean()), false);
      } catch (DeploymentException var3) {
         throw new UndeploymentException(var3);
      }
   }

   public void unprepareLocalSAFAgent(GenericManagedDeployment safAgentDeployment) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("IDEntityHelper.unprepareLocalSAFAgent: " + safAgentDeployment.getName());
      }

   }

   public Map getLocalSAFAgents() {
      return this.localSAFAgents;
   }

   void notifyIDs(DomainMBean domain, boolean prepare) throws DeploymentException {
      boolean done = false;
      IDBeanHandler instance = null;
      synchronized(this.idInstances) {
         boolean var19 = false;

         try {
            var19 = true;
            Iterator itr = this.idInstances.iterator();

            while(itr.hasNext()) {
               instance = (IDBeanHandler)itr.next();

               try {
                  if (prepare) {
                     instance.reconcileTargets(domain);
                  } else {
                     instance.activateTargetUpdates(true);
                  }
               } catch (BeanUpdateRejectedException var22) {
                  throw new DeploymentException("Rejected bean update", var22);
               }
            }

            done = true;
            var19 = false;
         } finally {
            if (var19) {
               if (!done && prepare) {
                  Iterator itr = this.idInstances.listIterator();

                  while(itr.hasNext()) {
                     IDBeanHandler innerInstance = (IDBeanHandler)itr.next();
                     if (innerInstance == instance) {
                        break;
                     }

                     try {
                        innerInstance.activateTargetUpdates(false);
                     } catch (BeanUpdateRejectedException var20) {
                     }
                  }
               }

            }
         }

         if (!done && prepare) {
            Iterator itr = this.idInstances.listIterator();

            while(itr.hasNext()) {
               IDBeanHandler innerInstance = (IDBeanHandler)itr.next();
               if (innerInstance == instance) {
                  break;
               }

               try {
                  innerInstance.activateTargetUpdates(false);
               } catch (BeanUpdateRejectedException var21) {
               }
            }
         }

      }
   }

   void addIDEntity(IDBeanHandler instance) {
      synchronized(this.idInstances) {
         this.idInstances.add(instance);
      }
   }

   void removeIDEntity(IDBeanHandler instance) {
      synchronized(this.idInstances) {
         this.idInstances.remove(instance);
      }
   }

   void removeAllIDEntities() {
      synchronized(this.idInstances) {
         Iterator itr = this.idInstances.iterator();

         while(itr.hasNext()) {
            itr.next();
            itr.remove();
         }

      }
   }
}
