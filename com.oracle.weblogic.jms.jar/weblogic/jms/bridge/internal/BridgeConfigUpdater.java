package weblogic.jms.bridge.internal;

import java.util.Enumeration;
import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.jms.JMSLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.BridgeDestinationCommonMBean;
import weblogic.management.configuration.BridgeDestinationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class BridgeConfigUpdater implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean root) throws UpdateException {
      try {
         MessagingBridgeMBean[] bridges = root.getMessagingBridges();

         int i;
         for(i = 0; bridges != null && i < bridges.length; ++i) {
            this.updateMessagingBridge(root, bridges[i]);
         }

         BridgeDestinationMBean[] destinations = root.getBridgeDestinations();

         for(i = 0; destinations != null && i < destinations.length; ++i) {
            this.updateBridgeDestination(root, destinations[i]);
         }

      } catch (InvalidAttributeValueException var5) {
         throw new UpdateException(var5);
      } catch (ManagementException var6) {
         throw new UpdateException(var6);
      }
   }

   private void updateMessagingBridge(DomainMBean root, MessagingBridgeMBean bridge) throws InvalidAttributeValueException, ManagementException {
      BridgeDestinationCommonMBean source = bridge.getSourceDestination();
      if (source != null && source instanceof BridgeDestinationMBean) {
         bridge.setSourceDestination((BridgeDestinationCommonMBean)null);
         this.updateBridgeDestination(root, (BridgeDestinationMBean)source);
         bridge.setSourceDestination(this.updateBridgeDestination(root, (BridgeDestinationMBean)source));
      }

      BridgeDestinationCommonMBean target = bridge.getTargetDestination();
      if (target != null && target instanceof BridgeDestinationMBean) {
         bridge.setTargetDestination((BridgeDestinationCommonMBean)null);
         this.updateBridgeDestination(root, (BridgeDestinationMBean)target);
         bridge.setTargetDestination(this.updateBridgeDestination(root, (BridgeDestinationMBean)target));
      }

   }

   private BridgeDestinationCommonMBean updateBridgeDestination(DomainMBean root, BridgeDestinationMBean oldBean) throws InvalidAttributeValueException, ManagementException {
      JMSBridgeDestinationMBean newBean = root.lookupJMSBridgeDestination(oldBean.getName());
      if (newBean != null) {
         this.destroyOldBean(root, oldBean);
         return newBean;
      } else {
         JMSLogger.logReplacingBridgeDestinationMBean(oldBean.getName());
         newBean = root.createJMSBridgeDestination(oldBean.getName());
         if (oldBean.getAdapterJNDIName() != null) {
            newBean.setAdapterJNDIName(oldBean.getAdapterJNDIName());
         }

         if (oldBean.getUserName() != null) {
            newBean.setUserName(oldBean.getUserName());
         }

         if (oldBean.getUserPassword() != null) {
            newBean.setUserPassword(oldBean.getUserPassword());
         }

         if (oldBean.getClasspath() != null) {
            newBean.setClasspath(oldBean.getClasspath());
         }

         Properties props = oldBean.getProperties();
         int count = 0;
         if (props != null && props.size() != 0) {
            Enumeration enum_ = props.propertyNames();

            label59:
            while(true) {
               while(true) {
                  while(true) {
                     if (!enum_.hasMoreElements()) {
                        break label59;
                     }

                     String key = (String)enum_.nextElement();
                     String value = props.getProperty(key);
                     if (!key.equalsIgnoreCase("ConnectionFactoryJNDI") && !key.equalsIgnoreCase("ConnectionFactoryJNDIName")) {
                        if (key.equalsIgnoreCase("ConnectionURL")) {
                           newBean.setConnectionURL(value);
                        } else if (key.equalsIgnoreCase("InitialContextFactory")) {
                           newBean.setInitialContextFactory(value);
                        } else if (!key.equalsIgnoreCase("DestinationJNDI") && !key.equalsIgnoreCase("DestinationJNDIName")) {
                           if (key.equalsIgnoreCase("DestinationType")) {
                              newBean.setDestinationType(value);
                           }
                        } else {
                           newBean.setDestinationJNDIName(value);
                           ++count;
                        }
                     } else {
                        newBean.setConnectionFactoryJNDIName(value);
                        ++count;
                     }
                  }
               }
            }
         }

         if (count < 2) {
            root.destroyJMSBridgeDestination(newBean);
            return oldBean;
         } else {
            this.destroyOldBean(root, oldBean);
            return newBean;
         }
      }
   }

   private void destroyOldBean(DomainMBean root, BridgeDestinationMBean oldBean) {
      try {
         root.destroyBridgeDestination(oldBean);
      } catch (BeanRemoveRejectedException var4) {
      }

   }
}
