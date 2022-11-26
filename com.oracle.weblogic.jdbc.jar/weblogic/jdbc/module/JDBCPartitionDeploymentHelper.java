package weblogic.jdbc.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.management.configuration.JDBCPropertyOverrideMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JDBCSystemResourceOverrideMBean;
import weblogic.management.configuration.PartitionMBean;

public class JDBCPartitionDeploymentHelper {
   private static final Collection jdbcOverrideAffectedTypes = new ArrayList();

   public static void processPartition(PartitionMBean partition, JDBCSystemResourceMBean bean, JDBCSystemResourceMBean clone) {
      processJDBCResource(partition, bean, clone, clone.getJDBCResource());
   }

   public static Collection affectedTypesForOverridingConfigBean(Class candidateOverridingConfigBeanType) {
      return (Collection)(JDBCSystemResourceOverrideMBean.class.isAssignableFrom(candidateOverridingConfigBeanType) ? jdbcOverrideAffectedTypes : Collections.EMPTY_SET);
   }

   public static JDBCDataSourceBean processJDBCResource(PartitionMBean partition, JDBCSystemResourceMBean bean, JDBCSystemResourceMBean clone, JDBCDataSourceBean dsBeanClone) {
      String decoratedName = clone.getName();
      if (dsBeanClone == null) {
         dsBeanClone = clone.getJDBCResource();
      }

      dsBeanClone.setName(decoratedName);
      JDBCSystemResourceOverrideMBean[] jdbcSystemResourceOverrides = partition == null ? new JDBCSystemResourceOverrideMBean[0] : partition.getJDBCSystemResourceOverrides();
      JDBCSystemResourceOverrideMBean processedOverride = null;
      JDBCSystemResourceOverrideMBean[] var7 = jdbcSystemResourceOverrides;
      int var8 = jdbcSystemResourceOverrides.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         JDBCSystemResourceOverrideMBean override = var7[var9];
         String dsname = override.getDataSourceName();
         JDBCPropertyOverrideMBean[] jdbcPropertyOverrides = override.getJDBCPropertyOverrides();
         if (dsname != null && dsname.equals(bean.getJDBCResource().getName())) {
            if (processedOverride != null) {
               throw new RuntimeException("Multiple overrides defined for datasource " + clone.getName() + ": " + processedOverride.getName() + ", " + override.getName());
            }

            overrideAttributes(partition, dsBeanClone, override.getURL(), override.getUser(), override.getPasswordEncrypted(), jdbcPropertyOverrides, override.getInitialCapacity(), override.getMinCapacity(), override.getMaxCapacity());
            processedOverride = override;
         }
      }

      if (partition != null) {
         JDBCPropertiesBean internalProps = dsBeanClone.getInternalProperties();
         createPropIfAbsent(internalProps, "PartitionName", partition.getName());
         createPropIfAbsent(internalProps, "UnqualifiedName", bean.getName());
         JDBCDataSourceParamsBean dsParams = dsBeanClone.getJDBCDataSourceParams();
         String dslist = dsParams.getDataSourceList();
         if (dslist != null && dslist.length() > 0) {
            String suffix = "$" + partition.getName();
            List memberlist = JDBCHelper.getHelper().dsToList(dslist);
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < memberlist.size(); ++i) {
               String member = (String)memberlist.get(i);
               if (member != null && member.endsWith(suffix)) {
                  sb.append(member);
               } else {
                  sb.append(member + suffix);
               }

               if (i < memberlist.size() - 1) {
                  sb.append(",");
               }
            }

            dsParams.setDataSourceList(sb.toString());
         }
      }

      return dsBeanClone;
   }

   private static void createPropIfAbsent(JDBCPropertiesBean internalProps, String propName, String propValue) {
      if (internalProps.lookupProperty(propName) == null) {
         internalProps.createProperty(propName, propValue);
      }

   }

   private static void overrideAttributes(PartitionMBean partition, JDBCDataSourceBean bean, String url, String user, byte[] passwordEncrypted, JDBCPropertyOverrideMBean[] jdbcPropertyOverrides, int initialCapacity, int minCapacity, int maxCapacity) {
      JDBCDriverParamsBean driverParams = bean.getJDBCDriverParams();
      JDBCConnectionPoolParamsBean poolParams = bean.getJDBCConnectionPoolParams();
      JDBCPropertiesBean propertiesBean = driverParams.getProperties();
      if (url != null) {
         driverParams.setUrl(url);
      }

      if (initialCapacity != -1) {
         poolParams.setInitialCapacity(initialCapacity);
      }

      if (minCapacity != -1) {
         poolParams.setMinCapacity(minCapacity);
      }

      if (maxCapacity != -1) {
         poolParams.setMaxCapacity(maxCapacity);
      }

      JDBCPropertyBean userProperty = propertiesBean.lookupProperty("user");
      if (user != null) {
         if (userProperty == null) {
            propertiesBean.createProperty("user", user);
         } else {
            userProperty.setValue(user);
         }
      }

      if (passwordEncrypted != null) {
         driverParams.setPasswordEncrypted(passwordEncrypted);
         JDBCPropertyBean passwordProperty = propertiesBean.lookupProperty("password");
         if (passwordProperty != null) {
            propertiesBean.destroyProperty(passwordProperty);
         }
      }

      if (jdbcPropertyOverrides != null && jdbcPropertyOverrides.length > 0) {
         JDBCPropertyOverrideMBean[] var19 = jdbcPropertyOverrides;
         int var14 = jdbcPropertyOverrides.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            JDBCPropertyOverrideMBean propertyOverride = var19[var15];
            String propertyName = propertyOverride.getName();
            JDBCPropertyBean propertyProp = propertiesBean.lookupProperty(propertyName);
            if (propertyProp == null) {
               propertyProp = propertiesBean.createProperty(propertyName);
            }

            if (propertyOverride.getEncryptedValueEncrypted() != null) {
               propertyProp.setEncryptedValueEncrypted(propertyOverride.getEncryptedValueEncrypted());
            } else if (propertyOverride.getValue() != null) {
               propertyProp.setValue(propertyOverride.getValue());
            } else if (propertyOverride.getSysPropValue() != null) {
               propertyProp.setSysPropValue(propertyOverride.getSysPropValue());
            }
         }
      }

      JDBCLogger.logConfiguringPartitionDataSource(bean.getName(), partition != null ? partition.getName() : "", "");
   }

   static {
      jdbcOverrideAffectedTypes.add(JDBCSystemResourceMBean.class);
   }
}
