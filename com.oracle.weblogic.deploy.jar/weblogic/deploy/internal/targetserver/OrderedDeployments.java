package weblogic.deploy.internal.targetserver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.Collection;
import java.util.TreeMap;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentOrder;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.ArrayUtils;

public class OrderedDeployments {
   private static final TreeMap deployments;
   private static final TreeMap deferredDeployments;
   private static PropertyChangeListener changeListener;
   private static final AuthenticatedSubject kernelId;

   public static synchronized Collection getDeployments() {
      return deployments.values();
   }

   public static synchronized BasicDeployment getOrCreateBasicDeployment(BasicDeploymentMBean mbean) {
      if (mbean == null) {
         return null;
      } else {
         BasicDeployment basicDep = (BasicDeployment)deployments.get(mbean);
         if (basicDep == null) {
            basicDep = DeployHelper.createDeployment(mbean);
            addDeployment(mbean, basicDep);
            registerChangeListenerIfNeeded();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("OrderedDeployments cannot find " + mbean + ", created AppDeployment=" + basicDep);
               if (mbean instanceof AppDeploymentMBean) {
                  Debug.deploymentDebug(mbean + " isActive=" + ApplicationUtils.isActiveVersion((AppDeploymentMBean)mbean));
               }
            }
         } else if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("OrderedDeployments found " + mbean + ", AppDeployment=" + basicDep);
            if (mbean instanceof AppDeploymentMBean) {
               Debug.deploymentDebug(mbean + " isActive=" + ApplicationUtils.isActiveVersion((AppDeploymentMBean)mbean));
            }
         }

         return basicDep;
      }
   }

   public static synchronized BasicDeployment getOrDeferredBasicDeployment(BasicDeploymentMBean mbean) {
      return mbean == null ? null : (BasicDeployment)((BasicDeployment)(deployments.containsKey(mbean) ? deployments.get(mbean) : deferredDeployments.get(mbean)));
   }

   public static void removeBasicDeployment(BasicDeploymentMBean mbean) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("OrderedDeployments remove basic deployment" + mbean);
         if (mbean instanceof AppDeploymentMBean) {
            Debug.deploymentDebug(mbean + " isActive=" + ApplicationUtils.isActiveVersion((AppDeploymentMBean)mbean));
         }
      }

      removeDeployment(mbean);
   }

   public static synchronized void addDeployment(Object mbean, Object dep) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("OrderedDeployments add mbean: " + mbean + ", dep: " + dep);
      }

      deployments.put(mbean, dep);
      if (mbean instanceof BasicDeploymentMBean) {
         BasicDeploymentMBean depMBean = (BasicDeploymentMBean)mbean;
         int deploymentOrder = depMBean.getDeploymentOrder();
         DeploymentOrder.addToDeploymentOrderCache((BasicDeploymentMBean)mbean, deploymentOrder);
      }

   }

   public static synchronized void removeDeployment(Object mbean) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("OrderedDeployments remove deployment mbean: " + mbean);
      }

      deployments.remove(mbean);
      if (mbean instanceof BasicDeploymentMBean) {
         DeploymentOrder.removeFromDeploymentOrderCache((BasicDeploymentMBean)mbean);
      }

      removeDeferredBasicDeployment(mbean);
   }

   static synchronized void removeDeferredBasicDeployment(Object mbean) {
      deferredDeployments.remove(mbean);
   }

   static synchronized void deferRemoveBasicDeployment(Object mbean) {
      Object dep = deployments.get(mbean);
      removeDeployment(mbean);
      if (dep != null) {
         deferredDeployments.put(mbean, dep);
      }

   }

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private static synchronized void refreshBasicDeployment(BasicDeploymentMBean rtBasicDep) {
      if (rtBasicDep != null) {
         BasicDeployment dep = (BasicDeployment)deployments.get(rtBasicDep);
         if (dep == null) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("OrderedDeployments refresh rtTreeBean=" + rtBasicDep + " cannot find BasicDeployment");
            }

         } else {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("OrderedDeployments refresh rtTreeBean=" + rtBasicDep + ", basicDep=" + dep);
            }

            BasicDeploymentMBean proposedDeploymentMBean = dep.getDeploymentMBean();
            removeDeployment(proposedDeploymentMBean);
            dep.resetMBean(rtBasicDep);
            addDeployment(rtBasicDep, dep);
         }
      }
   }

   private static void registerChangeListenerIfNeeded() {
      if (changeListener == null) {
         changeListener = new BasicDeploymentChangeListener();
         getDomain().addPropertyChangeListener(changeListener);
      }

   }

   static {
      deployments = new TreeMap(DeploymentOrder.COMPARATOR);
      deferredDeployments = new TreeMap(DeploymentOrder.COMPARATOR);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static class BasicDeploymentChangeListener implements PropertyChangeListener {
      private BasicDeploymentChangeListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt != null && evt.getNewValue() instanceof BasicDeploymentMBean[]) {
            ArrayUtils.computeDiff((Object[])((Object[])evt.getOldValue()), (Object[])((Object[])evt.getNewValue()), new ArrayUtils.DiffHandler() {
               public void addObject(Object addedObject) {
                  OrderedDeployments.refreshBasicDeployment((BasicDeploymentMBean)addedObject);
               }

               public void removeObject(Object removedObject) {
               }
            });
         }

      }

      // $FF: synthetic method
      BasicDeploymentChangeListener(Object x0) {
         this();
      }
   }
}
