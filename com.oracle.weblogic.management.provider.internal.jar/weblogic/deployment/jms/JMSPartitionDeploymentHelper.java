package weblogic.deployment.jms;

import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.PropertyBean;
import weblogic.management.configuration.ForeignConnectionFactoryOverrideMBean;
import weblogic.management.configuration.ForeignDestinationOverrideMBean;
import weblogic.management.configuration.ForeignServerOverrideMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.JMSSystemResourceOverrideMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PartitionPropertyMBean;

public class JMSPartitionDeploymentHelper {
   public static void processPartition(PartitionMBean partition, JMSSystemResourceMBean bean, JMSSystemResourceMBean clone) {
      processResource(partition, bean, clone, clone.getJMSResource());
   }

   public static void processResource(PartitionMBean partition, JMSSystemResourceMBean bean, JMSSystemResourceMBean clone, JMSBean resource) {
      if (resource == null) {
         resource = clone.getJMSResource();
      }

      JMSSystemResourceOverrideMBean override = partition == null ? null : partition.lookupJMSSystemResourceOverride(bean.getName());
      if (override != null) {
         ForeignServerOverrideMBean[] foreignServerOverrideMBeans = override.getForeignServers();
         ForeignServerOverrideMBean[] var6 = foreignServerOverrideMBeans;
         int var7 = foreignServerOverrideMBeans.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ForeignServerOverrideMBean foreignServerOverrideMBean = var6[var8];
            ForeignServerBean foreignServersBean = resource.lookupForeignServer(foreignServerOverrideMBean.getName());
            if (foreignServersBean != null) {
               overrideForeignServer(partition, foreignServersBean, foreignServerOverrideMBean);
            }
         }
      }

   }

   private static void overrideForeignServer(PartitionMBean partition, ForeignServerBean foreignServersBean, ForeignServerOverrideMBean foreignServerOverrideMBean) {
      String initialContextFactory = foreignServerOverrideMBean.getInitialContextFactory();
      if (initialContextFactory != null && !initialContextFactory.equals("No-Override")) {
         foreignServersBean.setInitialContextFactory(initialContextFactory);
      }

      String url = foreignServerOverrideMBean.getConnectionURL();
      if (url != null && !url.equals("No-Override")) {
         foreignServersBean.setConnectionURL(url);
      }

      String jndiPropertiesCredential = foreignServerOverrideMBean.getJNDIPropertiesCredential();
      if (jndiPropertiesCredential != null && !jndiPropertiesCredential.equals("No-Override")) {
         foreignServersBean.setJNDIPropertiesCredential(jndiPropertiesCredential);
      }

      byte[] jndiPropertiesCredentialEncrypted = foreignServerOverrideMBean.getJNDIPropertiesCredentialEncrypted();
      if (jndiPropertiesCredentialEncrypted != null) {
         foreignServersBean.setJNDIPropertiesCredentialEncrypted(jndiPropertiesCredentialEncrypted);
      }

      PartitionPropertyMBean[] jndiProperties = foreignServerOverrideMBean.getJNDIProperties();
      int var10;
      if (jndiProperties != null) {
         PartitionPropertyMBean[] var8 = jndiProperties;
         int var9 = jndiProperties.length;

         for(var10 = 0; var10 < var9; ++var10) {
            PartitionPropertyMBean jndiProperty = var8[var10];
            PropertyBean propertyBean = foreignServersBean.lookupJNDIProperty(jndiProperty.getName());
            if (propertyBean == null) {
               propertyBean = foreignServersBean.createJNDIProperty(jndiProperty.getName());
               propertyBean.setValue(jndiProperty.getValue());
            } else {
               propertyBean.setValue(jndiProperty.getValue());
            }
         }
      }

      ForeignConnectionFactoryOverrideMBean[] foreignConnectionFactoryOverrideMBeans = foreignServerOverrideMBean.getForeignConnectionFactories();
      ForeignConnectionFactoryOverrideMBean[] var16 = foreignConnectionFactoryOverrideMBeans;
      var10 = foreignConnectionFactoryOverrideMBeans.length;

      int var19;
      for(var19 = 0; var19 < var10; ++var19) {
         ForeignConnectionFactoryOverrideMBean foreignConnectionFactoryOverrideMBean = var16[var19];
         ForeignConnectionFactoryBean foreignConnectionFactoryBean = foreignServersBean.lookupForeignConnectionFactory(foreignConnectionFactoryOverrideMBean.getName());
         if (foreignConnectionFactoryBean != null) {
            overrideForeignConnectionFactory(partition, foreignConnectionFactoryBean, foreignConnectionFactoryOverrideMBean);
         }
      }

      ForeignDestinationOverrideMBean[] foreignDestinationOverrideMBeans = foreignServerOverrideMBean.getForeignDestinations();
      ForeignDestinationOverrideMBean[] var18 = foreignDestinationOverrideMBeans;
      var19 = foreignDestinationOverrideMBeans.length;

      for(int var21 = 0; var21 < var19; ++var21) {
         ForeignDestinationOverrideMBean foreignDestinationOverrideMBean = var18[var21];
         ForeignDestinationBean foreignDestinationBean = foreignServersBean.lookupForeignDestination(foreignDestinationOverrideMBean.getName());
         if (foreignDestinationBean != null) {
            overrideForeignDestination(partition, foreignDestinationBean, foreignDestinationOverrideMBean);
         }
      }

   }

   private static void overrideForeignDestination(PartitionMBean partition, ForeignDestinationBean foreignDestinationBean, ForeignDestinationOverrideMBean foreignDestinationOverrideMBean) {
      String remoteJNDIName = foreignDestinationOverrideMBean.getRemoteJNDIName();
      if (remoteJNDIName != null && !remoteJNDIName.equals("No-Override")) {
         foreignDestinationBean.setRemoteJNDIName(remoteJNDIName);
      }

   }

   private static void overrideForeignConnectionFactory(PartitionMBean partition, ForeignConnectionFactoryBean foreignConnectionFactoryBean, ForeignConnectionFactoryOverrideMBean foreignConnectionFactoryOverrideMBean) {
      String remoteJNDIName = foreignConnectionFactoryOverrideMBean.getRemoteJNDIName();
      if (remoteJNDIName != null && !remoteJNDIName.equals("No-Override")) {
         foreignConnectionFactoryBean.setRemoteJNDIName(remoteJNDIName);
      }

      String userName = foreignConnectionFactoryOverrideMBean.getUsername();
      if (userName != null && !userName.equals("No-Override")) {
         foreignConnectionFactoryBean.setUsername(userName);
      }

      String pwd = foreignConnectionFactoryOverrideMBean.getPassword();
      if (pwd != null && !pwd.equals("No-Override")) {
         foreignConnectionFactoryBean.setPassword(pwd);
      }

      byte[] pwdEncrypted = foreignConnectionFactoryOverrideMBean.getPasswordEncrypted();
      if (pwdEncrypted != null) {
         foreignConnectionFactoryBean.setPasswordEncrypted(pwdEncrypted);
      }

   }
}
