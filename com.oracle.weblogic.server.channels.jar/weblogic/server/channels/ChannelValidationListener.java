package weblogic.server.channels;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.server.ServerLogger;

public class ChannelValidationListener implements BeanUpdateListener {
   private final String localServerName;
   private final boolean isAdminServer;

   public ChannelValidationListener(String localServerName, boolean isAdminServer) {
      this.localServerName = localServerName;
      this.isAdminServer = isAdminServer;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelValidationListener.prepareUpdate() [" + this.localServerName + "] " + event.getSourceBean() + ", event dump:");
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();

         for(int i = 0; i < changes.length; ++i) {
            ChannelHelper.p("    " + changes[i]);
         }
      }

      this.checkChannelConsistencyForUpdatedConfig(event);
      this.checkConsistencyForNewAddedServers(event);
      this.preventEnableAdminstrationPortDynamically(event);
      ChannelHelper.p("ChannelValidationListener.prepareUpdate() [" + this.localServerName + "] validation passed successfully.");
   }

   private void preventEnableAdminstrationPortDynamically(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (!this.isAdminServer) {
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();

         for(int i = 0; i < changes.length; ++i) {
            if (event.getSourceBean() instanceof ServerMBean) {
               if (changes[i].getAddedObject() instanceof NetworkAccessPointMBean) {
                  NetworkAccessPointMBean nap = (NetworkAccessPointMBean)changes[i].getAddedObject();
                  if (nap.getProtocol().equalsIgnoreCase("ADMIN") && !ChannelHelper.isLocalAdminChannelEnabled()) {
                     throw new BeanUpdateRejectedException(ServerLogger.getCannotDynamicallyCreateNewChannelLoggable(this.localServerName).getMessage());
                  }
               }
            } else if (event.getSourceBean() instanceof DomainMBean && changes[i].getPropertyName().equals("AdministrationPortEnabled") && ((DomainMBean)event.getProposedBean()).isAdministrationPortEnabled() != ChannelHelper.isLocalAdminChannelEnabled()) {
               throw new BeanUpdateRejectedException(ServerLogger.getCannotDynamicallyEnableDisableAdminChannelLoggable(this.localServerName).getMessage());
            }
         }

      }
   }

   private void checkConsistencyForNewAddedServers(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (this.isAdminServer && event.getProposedBean() instanceof DomainMBean) {
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var3 = changes;
         int var4 = changes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate change = var3[var5];
            if (change.getAddedObject() instanceof ServerMBean) {
               ServerMBean smb = (ServerMBean)change.getAddedObject();
               if (smb != null) {
                  try {
                     ChannelHelper.checkConsistency(smb, this.localServerName);
                  } catch (ConfigurationException var9) {
                     ChannelHelper.p("[" + this.localServerName + "] find error for new server: " + var9.getMessage());
                     throw new BeanUpdateRejectedException(var9.getMessage(), var9);
                  }
               }
            }
         }
      }

   }

   private void checkChannelConsistencyForUpdatedConfig(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      ServerMBean proposed = null;
      if (event.getProposedBean() instanceof ServerMBean) {
         proposed = (ServerMBean)event.getProposedBean();
      } else if (event.getProposedBean() instanceof NetworkAccessPointMBean && ((NetworkAccessPointMBean)event.getProposedBean()).getParent() instanceof ServerMBean) {
         proposed = (ServerMBean)((NetworkAccessPointMBean)event.getProposedBean()).getParent();
      } else if (event.getProposedBean() instanceof SSLMBean && ((SSLMBean)event.getProposedBean()).getParent() instanceof ServerMBean) {
         proposed = (ServerMBean)((SSLMBean)event.getProposedBean()).getParent();
      }

      if (proposed != null) {
         try {
            ChannelHelper.checkConsistency(proposed, this.localServerName);
         } catch (ConfigurationException var4) {
            ChannelHelper.p("[" + this.localServerName + "] find error for updated server: " + var4.getMessage());
            throw new BeanUpdateRejectedException(var4.getMessage(), var4);
         }
      }

   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("ChannelValidationListener.activateUpdate() [" + this.localServerName + "] " + event.getSourceBean() + ", event dump:");
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();

         for(int i = 0; i < changes.length; ++i) {
            ChannelHelper.p("    " + changes[i]);
         }
      }

      ChannelValidationListener cvl = new ChannelValidationListener(this.localServerName, this.isAdminServer);
      BeanUpdateEvent.PropertyUpdate[] changes;
      if (this.isAdminServer && event.getProposedBean() instanceof DomainMBean) {
         changes = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var11 = changes;
         int var12 = changes.length;

         for(int var6 = 0; var6 < var12; ++var6) {
            BeanUpdateEvent.PropertyUpdate change = var11[var6];
            if (change.getAddedObject() instanceof ServerMBean) {
               ServerMBean smb = (ServerMBean)change.getAddedObject();
               ChannelHelper.p("[" + this.localServerName + "] register ChannelValidationListener for new added server " + smb);
               registeChannelValidationListerForOneServer(cvl, smb);
            }
         }
      } else if (event.getSourceBean() instanceof ServerMBean) {
         changes = event.getUpdateList();

         for(int i = 0; i < changes.length; ++i) {
            if (changes[i].getAddedObject() instanceof NetworkAccessPointMBean) {
               NetworkAccessPointMBean nap = (NetworkAccessPointMBean)changes[i].getAddedObject();
               ChannelHelper.p("[" + this.localServerName + "] register ChannelValidationListener for new added channel " + nap);
               nap.addBeanUpdateListener(cvl);
            }
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private boolean isUpdatingLocalServer(ServerMBean proposed) {
      return proposed.getName().equals(this.localServerName);
   }

   static void registerChannelValidationLister(RuntimeAccess runtimeAccess, String localServerName, boolean isAdminServer) {
      ChannelValidationListener cvl = new ChannelValidationListener(localServerName, isAdminServer);
      if (runtimeAccess.isAdminServer()) {
         ChannelHelper.p("[" + localServerName + "] local server is AdminServer. register ChannelValidationListener for all servers");
         ServerMBean[] servers = runtimeAccess.getDomain().getServers();
         ServerMBean[] var5 = servers;
         int var6 = servers.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ServerMBean smb = var5[var7];
            registeChannelValidationListerForOneServer(cvl, smb);
         }
      } else {
         ChannelHelper.p("[" + localServerName + "] local server is not AdminServer. register listener for local server only");
         ServerMBean smb = runtimeAccess.getServer();
         registeChannelValidationListerForOneServer(cvl, smb);
      }

      runtimeAccess.getDomain().addBeanUpdateListener(cvl);
   }

   private static void registeChannelValidationListerForOneServer(ChannelValidationListener cvl, ServerMBean smb) {
      smb.addBeanUpdateListener(cvl);
      smb.getSSL().addBeanUpdateListener(cvl);
      NetworkAccessPointMBean[] naps = smb.getNetworkAccessPoints();
      NetworkAccessPointMBean[] var3 = naps;
      int var4 = naps.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         NetworkAccessPointMBean nap = var3[var5];
         nap.addBeanUpdateListener(cvl);
      }

   }
}
