package weblogic.management.mbeans.custom;

import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class Server extends ServerTemplate {
   ServerTemplateMBean template;

   public Server(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public ServerTemplateMBean getServerTemplate() {
      return this.template;
   }

   public void setServerTemplate(ServerTemplateMBean template) {
      ClusterMBean oldCluster = ((ServerMBean)this.getMbean()).getCluster();
      this.template = template;
      ClusterMBean cluster = template != null ? template.getCluster() : null;
      if (((AbstractDescriptorBean)this.getMbean())._isTransient()) {
         if (cluster != null && oldCluster != null) {
            if (cluster.getName().equals(oldCluster.getName())) {
               return;
            }

            this.deleteDefaultMigratableTarget((ServerMBean)this.getMbean());
            this.createDefaultMigratableTarget((ServerMBean)this.getMbean(), cluster);
         } else if (cluster == null && oldCluster != null) {
            this.deleteDefaultMigratableTarget((ServerMBean)this.getMbean());
            this.getMbean().unSet("Cluster");
         } else if (cluster != null && oldCluster == null) {
            this.createDefaultMigratableTarget((ServerMBean)this.getMbean(), cluster);
         }
      } else if (!((ServerMBean)this.getMbean()).isSet("Cluster")) {
         if (cluster != null && oldCluster != null) {
            if (cluster.getName().equals(oldCluster.getName())) {
               return;
            }

            this.deleteDefaultMigratableTarget((ServerMBean)this.getMbean());
            this.createDefaultMigratableTarget((ServerMBean)this.getMbean(), cluster);
         } else if (cluster == null && oldCluster != null) {
            this.deleteDefaultMigratableTarget((ServerMBean)this.getMbean());
            this.getMbean().unSet("Cluster");
         } else if (cluster != null && oldCluster == null) {
            this.createDefaultMigratableTarget((ServerMBean)this.getMbean(), cluster);
         }
      }

   }
}
