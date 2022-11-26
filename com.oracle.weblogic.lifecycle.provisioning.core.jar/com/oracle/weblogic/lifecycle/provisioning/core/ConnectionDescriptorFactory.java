package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConnectionDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.WebLogicPartitionProvisioningContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Self;
import org.jvnet.hk2.annotations.Optional;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.ActiveBeanUtilBase;

public class ConnectionDescriptorFactory implements Factory {
   private final ActiveDescriptor myDescriptor;
   private final WebLogicPartitionProvisioningContext provisioningContext;
   private final Provider activeBeanUtilBaseProvider;
   private final RuntimeAccess runtimeAccess;

   @Inject
   public ConnectionDescriptorFactory(@Self ActiveDescriptor myDescriptor, WebLogicPartitionProvisioningContext provisioningContext, Provider activeBeanUtilBaseProvider, @Optional RuntimeAccess runtimeAccess) {
      Objects.requireNonNull(myDescriptor);
      Objects.requireNonNull(provisioningContext);
      Objects.requireNonNull(activeBeanUtilBaseProvider);
      if (myDescriptor.getName() == null) {
         throw new IllegalArgumentException("myDescriptor", new IllegalStateException("myDescriptor.getName() == null"));
      } else {
         this.myDescriptor = myDescriptor;
         this.provisioningContext = provisioningContext;
         this.activeBeanUtilBaseProvider = activeBeanUtilBaseProvider;
         this.runtimeAccess = runtimeAccess;
      }
   }

   public final ConnectionDescriptor provide() {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "provide";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provide");
      }

      JDBCSystemResourceMBean jdbcSystemResource = this.findJDBCSystemResource();
      if (jdbcSystemResource == null) {
         throw new NoSuchDataSourceException(this.getName());
      } else {
         ConnectionDescriptor returnValue = this.createConnectionDescriptor(jdbcSystemResource);
         if (returnValue == null) {
            throw new IllegalStateException("this.createConnectionDescriptor(dataSource) == null");
         } else {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.exiting(className, "provide", returnValue);
            }

            return returnValue;
         }
      }
   }

   protected JDBCSystemResourceMBean findJDBCSystemResource() {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "findJDBCSystemResource";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "findJDBCSystemResource");
      }

      JDBCSystemResourceMBean returnValue = null;
      String name = this.getName();

      assert name != null;

      PartitionMBean partition = this.getPartition();
      if (partition == null) {
         throw new NoSuchDataSourceException(name + "; the data source might exist but no partition was returned from getPartition() so no search could occur");
      } else {
         ActiveBeanUtilBase activeBeanUtilBase;
         if (this.activeBeanUtilBaseProvider != null) {
            activeBeanUtilBase = (ActiveBeanUtilBase)this.activeBeanUtilBaseProvider.get();
         } else {
            activeBeanUtilBase = null;
         }

         List candidates = new ArrayList();
         int size = candidates.size();
         switch (size) {
            case 0:
               throw new NoSuchDataSourceException(name);
            case 1:
               returnValue = (JDBCSystemResourceMBean)candidates.get(0);
               if (returnValue == null) {
                  throw new NoSuchDataSourceException(name);
               }

               if (logger != null && logger.isLoggable(Level.FINER)) {
                  logger.exiting(className, "findJDBCSystemResource", returnValue);
               }

               return returnValue;
            default:
               Collection dataSources = this.extractOriginalDataSources(candidates);
               throw new NonUniqueDataSourceException(name, dataSources);
         }
      }
   }

   private final Collection extractOriginalDataSources(Iterable jdbcSystemResources) {
      Collection returnValue = new ArrayList();
      if (jdbcSystemResources != null) {
         ActiveBeanUtilBase activeBeanUtilBase;
         if (this.activeBeanUtilBaseProvider != null) {
            activeBeanUtilBase = (ActiveBeanUtilBase)this.activeBeanUtilBaseProvider.get();
         } else {
            activeBeanUtilBase = null;
         }

         Iterator var4;
         JDBCSystemResourceMBean jdbcSystemResource;
         if (activeBeanUtilBase == null) {
            var4 = jdbcSystemResources.iterator();

            while(var4.hasNext()) {
               jdbcSystemResource = (JDBCSystemResourceMBean)var4.next();
               if (jdbcSystemResource != null) {
                  returnValue.add(jdbcSystemResource.getJDBCResource());
               }
            }
         } else {
            var4 = jdbcSystemResources.iterator();

            while(var4.hasNext()) {
               jdbcSystemResource = (JDBCSystemResourceMBean)var4.next();
               if (jdbcSystemResource != null) {
                  returnValue.add(((JDBCSystemResourceMBean)activeBeanUtilBase.toOriginalBean(jdbcSystemResource)).getJDBCResource());
               }
            }
         }
      }

      return returnValue;
   }

   protected final PartitionMBean getPartition() {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "getPartition";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getPartition");
      }

      PartitionMBean returnValue = null;
      String partitionName = this.provisioningContext.getPartitionName();
      if (partitionName == null) {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getPartition", "Cannot look up any PartitionMBean because partitionName is null.");
         }
      } else {
         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getPartition", "Attempting to find a PartitionMBean corresponding to a partition named \"{0}\"", partitionName);
         }

         if (this.runtimeAccess == null) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "getPartition", "Cannot look up a PartitionMBean corresponding to a partition named \"{0}\" because runtimeAccess is null.", partitionName);
            }
         } else {
            DomainMBean domain = this.runtimeAccess.getDomain();

            assert domain != null;

            returnValue = domain.lookupPartition(partitionName);
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "getPartition", "Retrieved a PartitionMBean corresponding to a partition named \"{0}\": {1}", new Object[]{partitionName, returnValue});
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getPartition", returnValue);
      }

      return returnValue;
   }

   protected ConnectionDescriptor createConnectionDescriptor(JDBCSystemResourceMBean jdbcSystemResource) {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "createConnectionDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "createConnectionDescriptor", jdbcSystemResource);
      }

      Objects.requireNonNull(jdbcSystemResource);
      JDBCDataSourceBean dataSource = jdbcSystemResource.getJDBCResource();
      if (dataSource == null) {
         throw new IllegalArgumentException("jdbcSystemResource", new IllegalStateException("jdbcSystemResource.getJDBCResource() == null"));
      } else {
         DataSourceConnectionDescriptor returnValue = new DataSourceConnectionDescriptor();
         JDBCDriverParamsBean jdbcDriverParameters = dataSource.getJDBCDriverParams();
         String url;
         if (jdbcDriverParameters == null) {
            url = null;
         } else {
            url = jdbcDriverParameters.getUrl();
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.logp(Level.FINER, className, "createConnectionDescriptor", "Building DriverManagerDataSource with {0} as the url", url);
         }

         DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(url, returnValue);
         returnValue.setDataSource(driverManagerDataSource);
         this.configureConnectionDescriptor(returnValue, jdbcSystemResource);
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "createConnectionDescriptor", returnValue);
         }

         return returnValue;
      }
   }

   protected void configureConnectionDescriptor(ConnectionDescriptor connectionDescriptor, JDBCSystemResourceMBean jdbcSystemResource) {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "configureConnectionDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "configureConnectionDescriptor", new Object[]{connectionDescriptor, jdbcSystemResource});
      }

      Objects.requireNonNull(connectionDescriptor);
      Objects.requireNonNull(jdbcSystemResource);
      if (jdbcSystemResource.getJDBCResource() == null) {
         throw new IllegalArgumentException("jdbcSystemResource", new IllegalStateException("jdbcSystemResource.getJDBCResource() == null"));
      } else {
         ActiveBeanUtilBase activeBeanUtilBase;
         if (this.activeBeanUtilBaseProvider == null) {
            activeBeanUtilBase = null;
         } else {
            activeBeanUtilBase = (ActiveBeanUtilBase)this.activeBeanUtilBaseProvider.get();
         }

         JDBCSystemResourceMBean originalBean;
         JDBCSystemResourceMBean activeBean;
         if (activeBeanUtilBase == null) {
            originalBean = jdbcSystemResource;
            activeBean = jdbcSystemResource;
         } else {
            originalBean = (JDBCSystemResourceMBean)activeBeanUtilBase.toOriginalBean(jdbcSystemResource);

            assert originalBean != null;

            activeBean = (JDBCSystemResourceMBean)activeBeanUtilBase.toActiveBean(jdbcSystemResource);

            assert activeBean != null;
         }

         JDBCDataSourceBean originalDataSource = originalBean.getJDBCResource();

         assert originalDataSource != null;

         String dataSourceName = originalDataSource.getName();
         if (dataSourceName == null) {
            throw new IllegalArgumentException("jdbcSystemResource", new IllegalStateException("jdbcSystemResource.getJDBCResource().getName() == null"));
         } else {
            connectionDescriptor.setDataSourceName(dataSourceName);
            JDBCDataSourceBean dataSource = activeBean.getJDBCResource();

            assert dataSource != null;

            JDBCDataSourceParamsBean dataSourceParameters = dataSource.getJDBCDataSourceParams();
            boolean jndiNamesLength;
            if (dataSourceParameters == null) {
               jndiNamesLength = false;
               connectionDescriptor.setProperty("jndiNames.length", "0");
            } else {
               String[] jndiNames = dataSourceParameters.getJNDINames();
               if (jndiNames != null && jndiNames.length > 0) {
                  int jndiNamesLength = jndiNames.length;
                  connectionDescriptor.setProperty("jndiNames.length", String.valueOf(jndiNamesLength));
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "configureConnectionDescriptor", "JNDI names for data source {0}: {1}", new Object[]{dataSourceName, Arrays.asList(jndiNames)});
                  }

                  for(int i = 0; i < jndiNamesLength; ++i) {
                     String jndiName = jndiNames[i];
                     if (jndiName != null) {
                        connectionDescriptor.setProperty("jndiNames." + i, jndiName);
                     }
                  }
               } else {
                  jndiNamesLength = false;
                  connectionDescriptor.setProperty("jndiNames.length", "0");
               }
            }

            JDBCDriverParamsBean jdbcDriverParameters = dataSource.getJDBCDriverParams();
            if (jdbcDriverParameters != null) {
               connectionDescriptor.setURL(jdbcDriverParameters.getUrl());
               connectionDescriptor.setDriverClassName(jdbcDriverParameters.getDriverName());
               String password = jdbcDriverParameters.getPassword();
               if (password != null) {
                  connectionDescriptor.setPassword(password.toCharArray());
               }

               JDBCPropertiesBean jdbcProperties = jdbcDriverParameters.getProperties();
               if (jdbcProperties != null) {
                  JDBCPropertyBean[] properties = jdbcProperties.getProperties();
                  if (properties != null && properties.length > 0) {
                     JDBCPropertyBean[] var18 = properties;
                     int var19 = properties.length;

                     for(int var20 = 0; var20 < var19; ++var20) {
                        JDBCPropertyBean propertyBean = var18[var20];
                        if (propertyBean != null) {
                           String propertyName = propertyBean.getName();
                           if (propertyName != null) {
                              connectionDescriptor.setProperty(propertyName, propertyBean.getValue());
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   public final String getName() {
      assert this.myDescriptor != null;

      String name = this.myDescriptor.getName();
      if (name == null) {
         throw new IllegalStateException("this.myDescriptor.getName() == null");
      } else {
         return name;
      }
   }

   public void dispose(ConnectionDescriptor connectionDescriptor) {
      String className = ConnectionDescriptorFactory.class.getName();
      String methodName = "dispose";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "dispose", connectionDescriptor);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "dispose");
      }

   }
}
