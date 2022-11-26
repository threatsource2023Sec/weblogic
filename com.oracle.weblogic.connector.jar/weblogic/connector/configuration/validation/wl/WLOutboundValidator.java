package weblogic.connector.configuration.validation.wl;

import java.util.Iterator;
import java.util.List;
import weblogic.connector.common.Debug;
import weblogic.connector.configuration.validation.ValidatingMessageImpl;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAInfo;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionInstanceBean;
import weblogic.j2ee.descriptor.wl.OutboundResourceAdapterBean;

public class WLOutboundValidator extends DefaultWLRAValidator {
   private static final Object CLASS_NAME = "WLOutboundValidator";
   private final OutboundResourceAdapterBean wlOutboundResourceAdapter;
   private final WLValidationUtils wlValidationUtils;

   public WLOutboundValidator(ValidationContext context, OutboundResourceAdapterBean wlOutboundResourceAdapter, WLValidationUtils wlValidationUtils) {
      super(context);
      this.wlOutboundResourceAdapter = wlOutboundResourceAdapter;
      this.wlValidationUtils = wlValidationUtils;
   }

   public int order() {
      return 140;
   }

   public void doValidate() {
      if (this.wlOutboundResourceAdapter.isDefaultConnectionPropertiesSet()) {
         ConnectionDefinitionPropertiesBean connDefnProperties = this.wlOutboundResourceAdapter.getDefaultConnectionProperties();
         ConfigPropertiesBean defaultProperties = connDefnProperties.getProperties();
         this.validateGlobalDefaultProps("Connection Pools Group", getComposedKeyOfAllBeans(this.wlOutboundResourceAdapter), "<outbound-resource-adapter><default-connection-properties><properties>", defaultProperties);
      }

      ConnectionDefinitionBean[] wlConnGroups = this.wlOutboundResourceAdapter.getConnectionDefinitionGroups();

      for(int i = 0; i < wlConnGroups.length; ++i) {
         ConnectionDefinitionBean group = wlConnGroups[i];
         String wlConnFactoryInterface = group.getConnectionFactoryInterface();
         weblogic.j2ee.descriptor.ConnectionDefinitionBean connDef = this.wlValidationUtils.findMatchingConnectionDefinitionInRA(this.raConnectorBean, wlConnFactoryInterface);
         if (connDef == null) {
            this.errorInGroup(group, fmt.NO_MATCHING_CONN_FACTORY_INTERFACE(wlConnFactoryInterface));
         } else {
            PropSetterTable connFactoryPropSetterTable = this.raValidationInfo.getConnectionFactoryPropSetterTable(wlConnFactoryInterface);
            if (group.isDefaultConnectionPropertiesSet()) {
               ConnectionDefinitionPropertiesBean connDefnProps = group.getDefaultConnectionProperties();
               ConfigPropertiesBean groupProperties = connDefnProps.getProperties();
               this.validateWLProps("Connection Pools Group", getComposedKeyOfGroup(group), "<outbound-resource-adapter><connection-definition-group>[connection-factory-interface = " + wlConnFactoryInterface + "]<default-connection-properties>", groupProperties, connFactoryPropSetterTable, connDef.getManagedConnectionFactoryClass());
            }

            ConnectionInstanceBean[] connInstances = group.getConnectionInstances();
            int numInstances = connInstances.length;

            for(int j = 0; j < numInstances; ++j) {
               ConnectionInstanceBean connInstance = connInstances[j];
               ConfigPropertiesBean configProps = null;
               if (connInstance.isConnectionPropertiesSet()) {
                  ConnectionDefinitionPropertiesBean connDefnPropsBean = connInstance.getConnectionProperties();
                  configProps = connDefnPropsBean.getProperties();
                  this.validateWLProps("Connection Pools Group", getComposedKeyOfGroup(group), "<outbound-resource-adapter><connection-definition-group>[connection-factory-interface = " + wlConnFactoryInterface + "]<connection-instance>[ jndi-name = " + connInstance.getJNDIName() + "]", configProps, connFactoryPropSetterTable, connDef.getManagedConnectionFactoryClass());
               }

               if (connInstance.getJNDIName() == null || connInstance.getJNDIName().trim().length() == 0) {
                  this.error("Connection Pool", connInstance.getJNDIName(), fmt.noJNDINameForConnectionInstance(wlConnFactoryInterface));
               }
            }
         }
      }

   }

   void errorInGroup(ConnectionDefinitionBean group, String message) {
      this.error("Connection Pools Group", getComposedKeyOfGroup(group), message);
   }

   static String getComposedKeyOfGroup(ConnectionDefinitionBean group) {
      String key = "";
      ConnectionInstanceBean[] instances = group.getConnectionInstances();
      if (instances != null) {
         ConnectionInstanceBean[] var3 = instances;
         int var4 = instances.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionInstanceBean instance = var3[var5];
            key = key + instance.getJNDIName() + ";;;;";
         }
      }

      return key;
   }

   static String getComposedKeyOfAllBeans(OutboundResourceAdapterBean wlOutboundResourceAdapter) {
      String key = "";
      ConnectionDefinitionBean[] groups = wlOutboundResourceAdapter.getConnectionDefinitionGroups();
      if (groups != null) {
         ConnectionDefinitionBean[] var3 = groups;
         int var4 = groups.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConnectionDefinitionBean group = var3[var5];
            key = key + getComposedKeyOfGroup(group);
         }
      }

      return key;
   }

   protected void validateProp4AllSetterTable(String elementName, ConfigPropertyBean propBean) {
      ConnectionDefinitionBean[] wlConnGroups = this.wlOutboundResourceAdapter.getConnectionDefinitionGroups();
      if (wlConnGroups != null) {
         ConnectionDefinitionBean[] var4 = wlConnGroups;
         int var5 = wlConnGroups.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ConnectionDefinitionBean gb = var4[var6];
            String wlConnFactoryInterface = gb.getConnectionFactoryInterface();
            weblogic.j2ee.descriptor.ConnectionDefinitionBean connDef = this.wlValidationUtils.findMatchingConnectionDefinitionInRA(this.raConnectorBean, wlConnFactoryInterface);
            if (connDef != null) {
               PropSetterTable table = this.raValidationInfo.getConnectionFactoryPropSetterTable(wlConnFactoryInterface);
               if (table != null) {
                  this.validateWLConfigProperty("Connection Pools Group", getComposedKeyOfGroup(gb), elementName, table, propBean, connDef.getManagedConnectionFactoryClass());
               }
            }
         }
      }

   }

   public static boolean validateRAInfo(RAInfo raInfo, ValidatingMessageImpl validationResult) {
      Debug.enter(CLASS_NAME, "validateRAInfo(...)");

      boolean var9;
      try {
         boolean validationErrorFound = false;
         List outboundList = raInfo.getOutboundInfos();
         if (outboundList != null && outboundList.size() > 0) {
            Iterator outboundIterator = outboundList.iterator();

            while(outboundIterator.hasNext()) {
               OutboundInfo outboundInfo = (OutboundInfo)outboundIterator.next();
               if (outboundInfo != null && outboundInfo.getInitialCapacity() > outboundInfo.getMaxCapacity()) {
                  validationErrorFound = true;
                  validationResult.error("Connection Pool", outboundInfo.getJndiName(), Debug.getExceptionMaxCapacityLessThanInitialCapacity(outboundInfo.getJndiName()), 9999);
               }
            }
         }

         if (!validationErrorFound) {
            var9 = true;
            return var9;
         }

         var9 = false;
      } finally {
         Debug.exit(CLASS_NAME, "validateRAInfo(...)");
      }

      return var9;
   }
}
