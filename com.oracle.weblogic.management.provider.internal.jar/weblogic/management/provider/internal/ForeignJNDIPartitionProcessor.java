package weblogic.management.provider.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJNDILinkMBean;
import weblogic.management.configuration.ForeignJNDILinkOverrideMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.configuration.ForeignJNDIProviderOverrideMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@Service
public class ForeignJNDIPartitionProcessor extends AbstractComponentPartitionProcessor {
   public boolean isTargetableVirtually(ConfigurationMBean bean) {
      return false;
   }

   public void processForeignJNDIProvider(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ForeignJNDIProviderMBean bean, ForeignJNDIProviderMBean clone) throws InvalidAttributeValueException, ManagementException {
      if (partition != null) {
         ForeignJNDILinkMBean[] linkMBeans = bean.getForeignJNDILinks();
         if (linkMBeans != null) {
            ForeignJNDILinkMBean[] var7 = linkMBeans;
            int var8 = linkMBeans.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ForeignJNDILinkMBean linkMBean = var7[var9];
               cloneForeignJNDILink(domain, partition, resourceGroup, clone, linkMBean);
            }
         }

         ForeignJNDIProviderOverrideMBean override = partition.lookupForeignJNDIProviderOverride(bean.getName());
         if (clone != null && override != null) {
            this.overrideAttributes(partition, clone, override);
         }

      }
   }

   static ForeignJNDILinkMBean cloneForeignJNDILink(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ForeignJNDIProviderMBean parent, ForeignJNDILinkMBean bean) throws InvalidAttributeValueException, ManagementException {
      ForeignJNDILinkMBean clone = parent.lookupForeignJNDILink(PartitionProcessor.addSuffix(partition, bean.getName()));
      if (clone != null) {
         return clone;
      } else {
         clone = (ForeignJNDILinkMBean)PartitionProcessor.newClone(domain, partition, resourceGroup, bean);
         parent.addForeignJNDILink(clone);
         return clone;
      }
   }

   private void overrideAttributes(PartitionMBean partition, ForeignJNDIProviderMBean clone, ForeignJNDIProviderOverrideMBean override) throws InvalidAttributeValueException {
      String icf = override.getInitialContextFactory();
      if (icf != null) {
         clone.setInitialContextFactory(icf);
      }

      String url = override.getProviderURL();
      if (url != null) {
         clone.setProviderURL(url);
      }

      String pwd = override.getPassword();
      if (pwd != null) {
         clone.setPassword(pwd);
      }

      String usr = override.getUser();
      if (usr != null) {
         clone.setUser(usr);
      }

      Properties cloneProps = clone.getProperties();
      Properties overrideProps = override.getProperties();
      if (cloneProps != null && overrideProps != null) {
         Iterator var10 = overrideProps.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry oEntry = (Map.Entry)var10.next();
            if (cloneProps.contains(oEntry.getKey())) {
               cloneProps.put(oEntry.getKey(), oEntry.getValue());
            }
         }
      }

      ForeignJNDILinkOverrideMBean[] oLinkMBeans = override.getForeignJNDILinks();
      if (oLinkMBeans != null) {
         ForeignJNDILinkOverrideMBean[] var19 = oLinkMBeans;
         int var12 = oLinkMBeans.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            ForeignJNDILinkOverrideMBean oLinkMBean = var19[var13];
            ForeignJNDILinkMBean linkMBean = clone.lookupForeignJNDILink(PartitionProcessor.addSuffix(partition, oLinkMBean.getName()));
            if (linkMBean != null) {
               String localName = oLinkMBean.getLocalJNDIName();
               if (localName != null) {
                  linkMBean.setLocalJNDIName(localName);
               }

               String remoteName = oLinkMBean.getRemoteJNDIName();
               if (remoteName != null) {
                  linkMBean.setRemoteJNDIName(remoteName);
               }
            }
         }
      }

   }
}
