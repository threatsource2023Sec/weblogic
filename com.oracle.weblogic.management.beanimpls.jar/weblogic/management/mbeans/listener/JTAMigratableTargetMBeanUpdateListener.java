package weblogic.management.mbeans.listener;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;

public class JTAMigratableTargetMBeanUpdateListener implements BeanUpdateListener {
   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      try {
         JTAMigratableTargetMBean proposedJMTonST = (JTAMigratableTargetMBean)event.getProposedBean();
         JTAMigratableTargetMBean originalJMTonST = (JTAMigratableTargetMBean)event.getSourceBean();
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            String propertyName = propertyUpdate.getPropertyName();
            ServerTemplateMBean serverTemplateMBean;
            DomainMBean domain;
            ServerMBean[] var10;
            int var11;
            int var12;
            ServerMBean server;
            ServerTemplateMBean srvtemp;
            JTAMigratableTargetMBean jmtOnServer;
            if ("MigrationPolicy".equals(propertyName)) {
               if (!(proposedJMTonST.getParent() instanceof ServerMBean)) {
                  serverTemplateMBean = (ServerTemplateMBean)((ServerTemplateMBean)proposedJMTonST.getParent());
                  domain = (DomainMBean)serverTemplateMBean.getParent();
                  var10 = domain.getServers();
                  var11 = var10.length;

                  for(var12 = 0; var12 < var11; ++var12) {
                     server = var10[var12];
                     srvtemp = server.getServerTemplate();
                     if (srvtemp != null && srvtemp.getName().equals(serverTemplateMBean.getName())) {
                        jmtOnServer = server.getJTAMigratableTarget();
                        if (jmtOnServer != null) {
                           if (((AbstractDescriptorBean)server)._isTransient()) {
                              jmtOnServer.setMigrationPolicy(proposedJMTonST.getMigrationPolicy());
                           } else {
                              String origMigrationPolicy = originalJMTonST.getMigrationPolicy();
                              String serverMigrationPolicy = jmtOnServer.getMigrationPolicy();
                              if (origMigrationPolicy.equalsIgnoreCase(serverMigrationPolicy)) {
                                 jmtOnServer.setMigrationPolicy(proposedJMTonST.getMigrationPolicy());
                              }
                           }
                        }
                     }
                  }
               }
            } else if ("StrictOwnershipCheck".equals(propertyName) && !(proposedJMTonST.getParent() instanceof ServerMBean)) {
               serverTemplateMBean = (ServerTemplateMBean)((ServerTemplateMBean)proposedJMTonST.getParent());
               domain = (DomainMBean)serverTemplateMBean.getParent();
               var10 = domain.getServers();
               var11 = var10.length;

               for(var12 = 0; var12 < var11; ++var12) {
                  server = var10[var12];
                  srvtemp = server.getServerTemplate();
                  if (srvtemp != null && srvtemp.getName().equals(serverTemplateMBean.getName())) {
                     jmtOnServer = server.getJTAMigratableTarget();
                     if (jmtOnServer != null) {
                        if (((AbstractDescriptorBean)server)._isTransient()) {
                           jmtOnServer.setStrictOwnershipCheck(proposedJMTonST.isStrictOwnershipCheck());
                        } else {
                           boolean origStrictOwnershipCheck = originalJMTonST.isStrictOwnershipCheck();
                           boolean serverStrictOwnershipCheck = jmtOnServer.isStrictOwnershipCheck();
                           if (origStrictOwnershipCheck == serverStrictOwnershipCheck) {
                              jmtOnServer.setStrictOwnershipCheck(proposedJMTonST.isStrictOwnershipCheck());
                           }
                        }
                     }
                  }
               }
            }
         }

      } catch (Exception var18) {
         throw new BeanUpdateRejectedException(var18.getMessage());
      }
   }
}
