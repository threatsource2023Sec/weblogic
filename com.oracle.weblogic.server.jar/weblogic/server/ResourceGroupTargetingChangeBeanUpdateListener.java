package weblogic.server;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ResourceGroupMultitargetingUtils;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.management.utils.TargetingAnalyzer;

public class ResourceGroupTargetingChangeBeanUpdateListener implements BeanUpdateListener {
   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (event.getSourceBean() instanceof DomainMBean && event.getProposedBean() instanceof DomainMBean) {
         validateIfRGCanBeRetargeted((DomainMBean)event.getSourceBean(), (DomainMBean)event.getProposedBean());
      } else {
         throw new BeanUpdateRejectedException("Current bean or proposed bean is not a Domain MBean.");
      }
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private static void validateIfRGCanBeRetargeted(DomainMBean curDomain, DomainMBean proposedDomain) throws BeanUpdateRejectedException {
      TargetingAnalyzer targetingAnalyzer = (TargetingAnalyzer)GlobalServiceLocator.getServiceLocator().getService(TargetingAnalyzer.class, new Annotation[0]);
      targetingAnalyzer.init(curDomain, proposedDomain);
      StringBuilder error = new StringBuilder();
      ResourceGroupMBean rgMbean = null;
      Collection resourceGroupNames = targetingAnalyzer.getResourceGroupNamesLeavingAndJoiningServers();
      Iterator var6 = resourceGroupNames.iterator();

      while(var6.hasNext()) {
         String rg = (String)var6.next();
         if (rg.indexOf("/") > 0) {
            String rgName = rg.substring(rg.indexOf("/") + 1);
            String pName = rg.substring(0, rg.indexOf("/"));
            rgMbean = proposedDomain.lookupPartition(pName).lookupResourceGroup(rgName);
         } else {
            rgMbean = proposedDomain.lookupResourceGroup(rg);
         }

         if (rgMbean != null && PartitionUtils.getServers(rgMbean).size() > 1) {
            try {
               List blResources = ResourceGroupMultitargetingUtils.findBlacklistedResources(rgMbean);
               if (blResources.size() > 0) {
                  if (error.length() > 0) {
                     error.append(" ; ").append(System.lineSeparator());
                  }

                  error.append(rg).append(" : ").append(blResources);
               }
            } catch (InvocationTargetException | IllegalAccessException | IOException var10) {
               throw new BeanUpdateRejectedException(var10.getMessage());
            }
         }
      }

      if (error.length() > 0) {
         error.insert(0, "The following resource groups cannot be retargeted as requested. A retargeted resource group can run temporarily on both the old and new target concurrently, and the resource groups below contain resources which cannot run on multiple servers at the same time. \nPlease remove the old target for the resource group or partition in one edit session, then add the new target in a later edit session.\n ");
         throw new BeanUpdateRejectedException(error.toString());
      }
   }
}
