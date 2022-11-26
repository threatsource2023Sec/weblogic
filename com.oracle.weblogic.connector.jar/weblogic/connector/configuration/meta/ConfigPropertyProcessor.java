package weblogic.connector.configuration.meta;

import weblogic.connector.exception.RAException;
import weblogic.j2ee.descriptor.ActivationSpecBean;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class ConfigPropertyProcessor {
   private final ConnectorBeanNavigator connectorBeanNavigator;

   public ConfigPropertyProcessor(ConnectorBeanNavigator connectorBeanNavigator) {
      this.connectorBeanNavigator = connectorBeanNavigator;
   }

   private Class getClass(String clz, GenericClassLoader classLoader) throws RAException {
      try {
         return Class.forName(clz, false, classLoader);
      } catch (ClassNotFoundException var4) {
         throw new RAException(var4);
      }
   }

   private void readResourceAdapterProperties(GenericClassLoader classLoader) throws RAException {
      final ConnectorBean connectorBean = this.connectorBeanNavigator.getConnectorBean();
      String resourceAdapterClass = this.connectorBeanNavigator.getOrCreateResourceAdapter().getResourceAdapterClass();
      if (resourceAdapterClass != null) {
         AbstractConfigPropertyProcessor propertyProcessor = new AbstractConfigPropertyProcessor(this.connectorBeanNavigator.context) {
            public ConfigPropertyBean createConfigPropertyBean(Object bean) {
               ResourceAdapterBean adapterBean = connectorBean.getResourceAdapter();
               return adapterBean.createConfigProperty();
            }

            public ConfigPropertyBean[] getConfigPropertyBeansInDD(Object bean) {
               ResourceAdapterBean adapterBean = connectorBean.getResourceAdapter();
               return adapterBean.getConfigProperties() == null ? new ConfigPropertyBean[0] : adapterBean.getConfigProperties();
            }
         };
         Class raClass = this.getClass(resourceAdapterClass, classLoader);
         propertyProcessor.readProperties(raClass, connectorBean);
      }
   }

   private void readActivationSpecProperties(GenericClassLoader classLoader) throws RAException {
      MessageAdapterBean messageAdapter = this.connectorBeanNavigator.getMessageAdapterBean();
      if (messageAdapter != null) {
         MessageListenerBean[] messageListeners = messageAdapter.getMessageListeners();
         if (messageListeners != null) {
            MessageListenerBean[] var4 = messageListeners;
            int var5 = messageListeners.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               MessageListenerBean messageListenerBean = var4[var6];
               final ActivationSpecBean activationSpec = messageListenerBean.getActivationSpec();
               if (activationSpec != null) {
                  AbstractConfigPropertyProcessor propertyProcessor = new AbstractConfigPropertyProcessor(this.connectorBeanNavigator.context) {
                     public ConfigPropertyBean createConfigPropertyBean(Object bean) {
                        return activationSpec.createConfigProperty();
                     }

                     public ConfigPropertyBean[] getConfigPropertyBeansInDD(Object bean) {
                        return activationSpec.getConfigProperties() == null ? new ConfigPropertyBean[0] : activationSpec.getConfigProperties();
                     }
                  };
                  Class clz = this.getClass(activationSpec.getActivationSpecClass(), classLoader);
                  propertyProcessor.readProperties(clz, activationSpec);
               }
            }

         }
      }
   }

   private void readAdminObjectProperties(GenericClassLoader classLoader) throws RAException {
      AdminObjectBean[] adminObjects = this.connectorBeanNavigator.getOrCreateResourceAdapter().getAdminObjects();
      if (adminObjects != null) {
         AdminObjectBean[] var3 = adminObjects;
         int var4 = adminObjects.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            final AdminObjectBean adminObject = var3[var5];
            AbstractConfigPropertyProcessor processor = new AbstractConfigPropertyProcessor(this.connectorBeanNavigator.context) {
               public ConfigPropertyBean createConfigPropertyBean(Object bean) {
                  return adminObject.createConfigProperty();
               }

               public ConfigPropertyBean[] getConfigPropertyBeansInDD(Object bean) {
                  return adminObject.getConfigProperties();
               }
            };
            Class clz = this.getClass(adminObject.getAdminObjectClass(), classLoader);
            processor.readProperties(clz, adminObject);
         }

      }
   }

   private void readConnectionDefinitionProperties(GenericClassLoader classLoader) throws RAException {
      ConnectionDefinitionBean[] connectionDefinitions = this.connectorBeanNavigator.getConnections();
      if (connectionDefinitions != null) {
         ConnectionDefinitionBean[] var3 = connectionDefinitions;
         int var4 = connectionDefinitions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            final ConnectionDefinitionBean connectionBean = var3[var5];
            AbstractConfigPropertyProcessor propertyProcessor = new AbstractConfigPropertyProcessor(this.connectorBeanNavigator.context) {
               public ConfigPropertyBean createConfigPropertyBean(Object bean) {
                  return connectionBean.createConfigProperty();
               }

               public ConfigPropertyBean[] getConfigPropertyBeansInDD(Object bean) {
                  return connectionBean.getConfigProperties() == null ? new ConfigPropertyBean[0] : connectionBean.getConfigProperties();
               }
            };
            Class clz = this.getClass(connectionBean.getManagedConnectionFactoryClass(), classLoader);
            propertyProcessor.readProperties(clz, connectionBean);
         }

      }
   }

   public void readConfigProperties(GenericClassLoader classLoader) throws RAException {
      this.readResourceAdapterProperties(classLoader);
      this.readAdminObjectProperties(classLoader);
      this.readActivationSpecProperties(classLoader);
      this.readConnectionDefinitionProperties(classLoader);
   }
}
