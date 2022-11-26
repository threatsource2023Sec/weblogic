package weblogic.connector.configuration.validation.wl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.configuration.validation.DefaultValidator;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.utils.TypeUtils;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;

abstract class DefaultWLRAValidator extends DefaultValidator {
   protected final RAValidationInfo raValidationInfo;
   protected final ConnectorBean raConnectorBean;

   DefaultWLRAValidator(ValidationContext context) {
      super(context);
      this.raValidationInfo = context.getRaValidationInfo();
      this.raConnectorBean = context.getConnector();
   }

   protected final void validateWLProps(String subComponent, String key, String elementName, ConfigPropertiesBean wlProps, PropSetterTable propSetterTable, String interfaceName, String className) {
      if (wlProps != null) {
         this.checkForDuplicateProperty(subComponent, key, elementName, wlProps);
         ConfigPropertyBean[] propBeans = wlProps.getProperties();

         for(int i = 0; propBeans != null && i < propBeans.length; ++i) {
            ConfigPropertyBean propBean = propBeans[i];
            this.validateWLConfigProperty(subComponent, key, elementName, propSetterTable, propBean, interfaceName, className);
         }
      }

   }

   protected final void validateWLProps(String subComponent, String key, String elementName, ConfigPropertiesBean wlProps, PropSetterTable propSetterTable, String interfaceName) {
      this.validateWLProps(subComponent, key, elementName, wlProps, propSetterTable, interfaceName, (String)null);
   }

   protected final void validateWLProps(String subComponent, String key, String elementName, ConfigPropertiesBean wlProps, PropSetterTable propSetterTable) {
      this.validateWLProps(subComponent, key, elementName, wlProps, propSetterTable, (String)null, (String)null);
   }

   protected final void validateGlobalDefaultProps(String subComponent, String key, String elementName, ConfigPropertiesBean wlProps) {
      if (wlProps != null && wlProps.getProperties() != null) {
         this.checkForDuplicateProperty(subComponent, key, elementName, wlProps);
         ConfigPropertyBean[] propBeans = wlProps.getProperties();
         ConfigPropertyBean[] var6 = propBeans;
         int var7 = propBeans.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ConfigPropertyBean propBean = var6[var8];
            this.validateProp4AllSetterTable(elementName, propBean);
         }

      }
   }

   protected void validateWLConfigProperty(String subComponent, String key, String elementName, PropSetterTable propSetterTable, ConfigPropertyBean propBean, String interfaceName) {
      this.validateWLConfigProperty(subComponent, key, elementName, propSetterTable, propBean, interfaceName, (String)null);
   }

   protected void validateWLConfigProperty(String subComponent, String key, String elementName, PropSetterTable propSetterTable, ConfigPropertyBean propBean, String interfaceName, String className) {
      PropSetterTable.PropertyInjector injector = propSetterTable.getInjectorByName(propBean.getName());
      String msg;
      if (injector != null) {
         msg = injector.getType();
         String wlValue = propBean.getValue();

         try {
            TypeUtils.getValueByType(wlValue, msg);
         } catch (NumberFormatException var12) {
            this.warning(fmt.PROPERTY_TYPE_VALUE_MISMATCH("weblogic-ra.xml", elementName, propBean.getName(), msg, wlValue, var12.toString()));
         }
      } else if (className == null) {
         if (interfaceName == null) {
            msg = fmt.MISSING_RA_PROPERTY(elementName, propBean.getName(), propBean.getValue());
            this.reportPropertyIssue(subComponent, key, msg);
         } else {
            msg = fmt.MISSING_CF_PROPERTY(elementName, propBean.getName(), propBean.getValue(), interfaceName);
            this.reportPropertyIssue(subComponent, key, msg);
         }
      } else {
         msg = fmt.MISSING_AO_PROPERTY(elementName, propBean.getName(), propBean.getValue(), interfaceName, className);
         this.reportPropertyIssue(subComponent, key, msg);
      }

   }

   protected abstract void validateProp4AllSetterTable(String var1, ConfigPropertyBean var2);

   public void checkForDuplicateProperty(String subComponent, String key, String elementName, ConfigPropertiesBean configPropertiesBean) {
      if (configPropertiesBean != null && configPropertiesBean.getProperties() != null) {
         ConfigPropertyBean[] properties = configPropertiesBean.getProperties();
         Comparator comparator = new ConfigPropertyBeanComparator(this.getPropertyNameNormalizer());
         List duplicatedProperties = this.getDuplicateProperties(subComponent, key, elementName, properties, comparator);
         Iterator var8 = duplicatedProperties.iterator();

         while(var8.hasNext()) {
            ConfigPropertyBean bean = (ConfigPropertyBean)var8.next();
            configPropertiesBean.destroyProperty(bean);
         }

      }
   }

   protected void reportDuplicateProperties(String subComponent, String key, String elementName, List duplicatedProperties) {
      StringBuilder sb = new StringBuilder();
      Iterator var6 = duplicatedProperties.iterator();

      while(var6.hasNext()) {
         Object item = var6.next();
         ConfigPropertyBean prop = (ConfigPropertyBean)item;
         sb.append(prop.getName() + ",");
      }

      sb.deleteCharAt(sb.length() - 1);
      String msg = fmt.DUPLICATE_WLRA_PROPERTY(elementName, sb.toString());
      this.reportPropertyIssue(subComponent, key, msg);
   }
}
