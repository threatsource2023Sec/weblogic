package weblogic.work.concurrent.partition;

import java.lang.annotation.Annotation;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;

public class ConcurrentManagedObjectDeploymentHandler implements DeploymentHandler {
   private PartitionObjectAndRuntimeManager concurrentObjectAndRuntimeManager = PartitionObjectAndRuntimeManager.getInstance();

   public void activateDeployment(DeploymentMBean bean, DeploymentHandlerContext arg1) throws DeploymentException {
      if (bean instanceof ManagedScheduledExecutorServiceMBean) {
         ManagedScheduledExecutorServiceMBean msesBean = (ManagedScheduledExecutorServiceMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.createManagedScheduledExecutorServiceReference(msesBean);
      } else if (bean instanceof ManagedExecutorServiceMBean) {
         ManagedExecutorServiceMBean mesBean = (ManagedExecutorServiceMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.createManagedExecutorServiceReference(mesBean);
      } else {
         if (!(bean instanceof ManagedThreadFactoryMBean)) {
            return;
         }

         ManagedThreadFactoryMBean mtfBean = (ManagedThreadFactoryMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.createManagedThreadFactoryReference(mtfBean);
      }

   }

   public void deactivateDeployment(DeploymentMBean bean, DeploymentHandlerContext arg1) throws UndeploymentException {
      if (bean instanceof ManagedScheduledExecutorServiceMBean) {
         ManagedScheduledExecutorServiceMBean msesbean = (ManagedScheduledExecutorServiceMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.removeObject(ManagedScheduledExecutorService.class.getName(), msesbean.getName(), RuntimeAccessUtils.getCurrentPartitionName());
      } else if (bean instanceof ManagedExecutorServiceMBean) {
         ManagedExecutorServiceMBean mesbean = (ManagedExecutorServiceMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.removeObject(ManagedExecutorService.class.getName(), mesbean.getName(), RuntimeAccessUtils.getCurrentPartitionName());
      } else {
         if (!(bean instanceof ManagedThreadFactoryMBean)) {
            return;
         }

         ManagedThreadFactoryMBean mtfBean = (ManagedThreadFactoryMBean)getOriginalMBean(bean);
         this.concurrentObjectAndRuntimeManager.removeObject(ManagedThreadFactory.class.getName(), mtfBean.getName(), RuntimeAccessUtils.getCurrentPartitionName());
      }

   }

   private static DeploymentMBean getOriginalMBean(DeploymentMBean mbean) {
      return (DeploymentMBean)((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(mbean);
   }

   public void prepareDeployment(DeploymentMBean bean, DeploymentHandlerContext arg1) throws DeploymentException {
   }

   public void unprepareDeployment(DeploymentMBean bean, DeploymentHandlerContext arg1) throws UndeploymentException {
   }
}
