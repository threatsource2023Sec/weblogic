package weblogic.management.deploy.internal;

import java.io.IOException;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.stream.XMLStreamException;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.descriptor.DeploymentPlanException;
import weblogic.application.descriptor.DeploymentPlanProcessor;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.internal.ActiveBeanUtilImpl;
import weblogic.management.provider.internal.OrderedOrganizer;
import weblogic.management.provider.internal.OverridePartitionDepPlan;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.utils.ActiveBeanUtil;

@Service
@Named
public class OverridePartitionDepPlanService implements OverridePartitionDepPlan {
   private ResourceDeploymentPlanBean resourceDeploymentPlanBean;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   @Inject
   private ActiveBeanUtil util = new ActiveBeanUtilImpl();

   public void setPartition(PartitionMBean partition) throws IOException, XMLStreamException {
      this.resourceDeploymentPlanBean = partition.getResourceDeploymentPlanDescriptor();
   }

   public void applyResourceOverride(ResourceGroupMBean resource, OrderedOrganizer cloneOrganizer) throws Exception {
      if (this.resourceDeploymentPlanBean != null && resource != null) {
         debugLogger.debug("applyResourceOverride processing RG " + resource.getParent().getName() + "/" + resource.getName());
         WebLogicMBean parent = resource.getParent();
         PartitionMBean partition = parent != null && parent instanceof PartitionMBean ? (PartitionMBean)parent : null;
         Iterator var5 = cloneOrganizer.elements(partition).iterator();

         while(true) {
            while(var5.hasNext()) {
               PartitionProcessor.BeanPair bp = (PartitionProcessor.BeanPair)var5.next();
               if (bp.getOrig() instanceof DeploymentMBean) {
                  this.applyResourceOverride(bp.getClone(), bp.getOrig().getName());
               } else {
                  Object loadedResource = bp.getResource();
                  DescriptorBean loadedDescriptor = null;
                  if (loadedResource != null && loadedResource instanceof DescriptorBean) {
                     loadedDescriptor = (DescriptorBean)loadedResource;
                  } else if (bp.getClone() instanceof SystemResourceMBean) {
                     loadedDescriptor = ((SystemResourceMBean)bp.getClone()).getResource();
                  }

                  this.applyResourceOverride(bp.getClone(), bp.getOrig().getName(), loadedDescriptor);
               }
            }

            return;
         }
      }
   }

   public void applyResourceOverride(ConfigurationMBean activeBean, String originalResourceName) throws Exception {
      DescriptorBean loadedSystemResourceDescriptor = null;
      if (activeBean instanceof SystemResourceMBean) {
         loadedSystemResourceDescriptor = ((SystemResourceMBean)activeBean).getResource();
      }

      this.applyResourceOverride(activeBean, originalResourceName, loadedSystemResourceDescriptor);
   }

   private void applyResourceOverride(ConfigurationMBean activeBean, String originalResourceName, DescriptorBean loadedDescriptor) throws Exception {
      DeploymentPlanProcessor externalDeploymentPlanProcessor;
      if (loadedDescriptor != null) {
         debugLogger.debug("applyResourceOverride processing SystemResource activeBean descriptor: originalName = " + originalResourceName + activeBean.getClass().getName() + ":" + activeBean.getName() + " and pre-loaded descriptor is " + loadedDescriptor.getClass().getName() + "=" + loadedDescriptor.getDescriptor());
         externalDeploymentPlanProcessor = new DeploymentPlanProcessor(this.resourceDeploymentPlanBean, originalResourceName, loadedDescriptor, true);

         try {
            externalDeploymentPlanProcessor.applyPlanOverrides();
         } catch (DeploymentPlanException var8) {
         }

         debugLogger.debug("applyResourceOverride processing SystemResource activeBean: originalName =  " + originalResourceName + " and activeBean is " + activeBean.getClass().getName() + ":" + activeBean);
         DeploymentPlanProcessor configDeploymentPlanProcessor = new DeploymentPlanProcessor(this.resourceDeploymentPlanBean, originalResourceName, activeBean, true);

         try {
            configDeploymentPlanProcessor.applyPlanOverrides();
         } catch (DeploymentPlanException var7) {
         }
      } else {
         debugLogger.debug("applyResourceOverride processing non-SystemResource activeBean: originalName =  " + originalResourceName + " and activeBean is " + activeBean.getClass().getName() + ":" + activeBean);
         externalDeploymentPlanProcessor = new DeploymentPlanProcessor(this.resourceDeploymentPlanBean, originalResourceName, activeBean, true);
         externalDeploymentPlanProcessor.applyPlanOverrides();
      }

   }

   private ConfigurationMBean findClonedResource(PartitionMBean partition, ConfigurationMBean bean, Class tClass, OrderedOrganizer cloneOrganizer) throws ManagementException {
      PartitionProcessor.BeanPair pair = this.findBeanPair(partition, bean, tClass, cloneOrganizer);
      if (pair != null) {
         if (!tClass.isAssignableFrom(pair.getClone().getClass())) {
            throw new ManagementException("Clone of resource " + bean.getName() + " is of type " + pair.getClone().getClass().getName() + " but " + bean.getClass().getName() + " was expected");
         } else {
            return pair.getClone();
         }
      } else {
         return this.util.toActiveBean(bean);
      }
   }

   private PartitionProcessor.BeanPair findBeanPair(PartitionMBean partition, ConfigurationMBean bean, Class tClass, OrderedOrganizer cloneOrganizer) throws ManagementException {
      PartitionProcessor.BeanPair result = null;
      Iterator var6 = cloneOrganizer.elements(partition).iterator();

      PartitionProcessor.BeanPair pair;
      do {
         if (!var6.hasNext()) {
            return null;
         }

         pair = (PartitionProcessor.BeanPair)var6.next();
      } while(!pair.getOrig().getName().equals(bean.getName()));

      if (!tClass.isAssignableFrom(pair.getClone().getClass())) {
         throw new ManagementException("Clone of resource " + bean.getName() + " is of type " + pair.getClone().getClass().getName() + " but " + bean.getClass().getName() + " was expected");
      } else {
         return pair;
      }
   }
}
