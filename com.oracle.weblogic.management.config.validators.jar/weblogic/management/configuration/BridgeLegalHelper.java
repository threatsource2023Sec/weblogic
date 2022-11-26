package weblogic.management.configuration;

import java.util.Enumeration;
import java.util.Properties;
import weblogic.management.internal.ManagementTextTextFormatter;

public final class BridgeLegalHelper {
   private static final boolean debug = false;

   public static void validateBridge(MessagingBridgeMBean bean) throws IllegalArgumentException {
      TargetMBean[] targets = bean.getTargets();
      if (targets.length != 0) {
         JMSLegalHelper.validateHAPolicies(bean, targets[0]);
      }

   }

   public static void validateBridgeDestinations(MessagingBridgeMBean bridge) throws IllegalArgumentException {
      BridgeDestinationCommonMBean source = bridge.getSourceDestination();
      BridgeDestinationCommonMBean target = bridge.getTargetDestination();
      if (source != null && target != null) {
         String rgGroupInfo = "";
         String referringRGTMsg = "";
         ResourceGroupTemplateMBean srcProps;
         if (bridge.getParent() instanceof ResourceGroupTemplateMBean) {
            rgGroupInfo = " Resource Group Template " + bridge.getParent().getName();
            if (bridge.getParent() instanceof ResourceGroupMBean) {
               rgGroupInfo = " Resource Group " + bridge.getParent().getName();
               srcProps = ((ResourceGroupMBean)bridge.getParent()).getResourceGroupTemplate();
               if (srcProps != null) {
                  referringRGTMsg = " Or Under Resource Group Template " + srcProps.getName();
               }
            }
         } else {
            rgGroupInfo = " Domain Scope ";
         }

         if (!JMSLegalHelper.isAssociatedMBeanScopeLegal(bridge, source)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getBridgeScopeMismatch(bridge.getName(), source.getName(), rgGroupInfo, referringRGTMsg));
         } else if (!JMSLegalHelper.isAssociatedMBeanScopeLegal(bridge, target)) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getBridgeScopeMismatch(bridge.getName(), target.getName(), rgGroupInfo, referringRGTMsg));
         } else if (source.getName().equals(target.getName())) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSameSourceTargetException(bridge.getName()));
         } else {
            srcProps = null;
            Properties srcProps;
            if (source instanceof JMSBridgeDestinationMBean) {
               srcProps = createProperties(((JMSBridgeDestinationMBean)source).getConnectionURL(), ((JMSBridgeDestinationMBean)source).getInitialContextFactory(), ((JMSBridgeDestinationMBean)source).getConnectionFactoryJNDIName(), ((JMSBridgeDestinationMBean)source).getDestinationJNDIName(), ((JMSBridgeDestinationMBean)source).getDestinationType());
            } else {
               srcProps = ((BridgeDestinationMBean)source).getProperties();
            }

            Properties tgtProps = null;
            if (target instanceof JMSBridgeDestinationMBean) {
               tgtProps = createProperties(((JMSBridgeDestinationMBean)target).getConnectionURL(), ((JMSBridgeDestinationMBean)target).getInitialContextFactory(), ((JMSBridgeDestinationMBean)target).getConnectionFactoryJNDIName(), ((JMSBridgeDestinationMBean)target).getDestinationJNDIName(), ((JMSBridgeDestinationMBean)target).getDestinationType());
            } else {
               tgtProps = ((BridgeDestinationMBean)target).getProperties();
            }

            if (!notSameDestinations(srcProps, tgtProps)) {
               throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getSameSourceTargetException(bridge.getName()));
            }
         }
      }
   }

   public static boolean notSameDestinations(Properties srcProps, Properties tgtProps) {
      if (srcProps != null && srcProps.size() != 0) {
         if (tgtProps != null && tgtProps.size() != 0) {
            Enumeration enmt = srcProps.propertyNames();
            Properties tgtClone = (Properties)tgtProps.clone();

            String key;
            while(enmt.hasMoreElements()) {
               key = (String)enmt.nextElement();
               String srcProp = srcProps.getProperty(key);
               String tgtProp = tgtProps.getProperty(key);
               if (srcProp == null) {
                  if (tgtProp != null) {
                     return true;
                  }
               } else {
                  if (tgtProp == null) {
                     return true;
                  }

                  if (!srcProp.equals(tgtProp)) {
                     return true;
                  }

                  tgtClone.remove(key);
               }
            }

            enmt = tgtClone.propertyNames();

            do {
               if (!enmt.hasMoreElements()) {
                  return false;
               }

               key = (String)enmt.nextElement();
            } while(tgtClone.getProperty(key) == null);

            return true;
         } else {
            return true;
         }
      } else {
         return tgtProps != null && tgtProps.size() != 0;
      }
   }

   public static Properties createProperties(String conUrl, String icf, String cfJndi, String destJndi, String destType) {
      Properties copyer = new Properties();
      if (conUrl != null && conUrl.length() > 0) {
         copyer.put("ConnectionURL", conUrl);
      }

      if (icf != null) {
         copyer.put("InitialContextFactory", icf);
      } else {
         copyer.put("InitialContextFactory", "weblogic.jndi.WLInitialContextFactory");
      }

      if (cfJndi != null) {
         copyer.put("ConnectionFactoryJNDIName", cfJndi);
      }

      if (destJndi != null) {
         copyer.put("DestinationJNDIName", destJndi);
      }

      if (destType != null) {
         copyer.put("DestinationType", destType);
      }

      return copyer;
   }
}
