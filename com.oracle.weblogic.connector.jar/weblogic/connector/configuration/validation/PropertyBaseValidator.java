package weblogic.connector.configuration.validation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.common.Debug;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.utils.IntrospectorManager;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.TypeUtils;
import weblogic.j2ee.descriptor.ConfigPropertyBean;

public abstract class PropertyBaseValidator extends DefaultRAValidator {
   private final RAValidationInfo raValidationInfo;

   public PropertyBaseValidator(ValidationContext context) {
      super(context);
      this.raValidationInfo = context.getRaValidationInfo();
   }

   protected final RAValidationInfo getRAValidationInfo() {
      return this.raValidationInfo;
   }

   protected final void validateProperties(String subComponent, String key, Class clz, ConfigPropertyBean[] configProperties, PropSetterTable propSetterTable, String element, String descriptor) {
      Debug.enter(this.getClass().getCanonicalName(), ".validateProperties()");

      try {
         Debug.println("Get all the methods & properties of the class");
         PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clz);
         Debug.println("Iterate through all the config properties and (optionally) set them in the obj");
         ConfigPropertyBean[] var18 = configProperties;
         int var10 = configProperties.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ConfigPropertyBean configPropBean = var18[var11];
            if (this.validateConfigPropertyNameType(element, configPropBean)) {
               this.validateConfigPropertyDefaultType(element, configPropBean);
               this.findAndRegisterConfigPropertyInjector(subComponent, key, element, clz, propertyDescriptors, configPropBean.getConfigPropertyName(), configPropBean.getConfigPropertyType(), propSetterTable);
            }
         }
      } catch (IntrospectionException var16) {
         String exMsg = Debug.getExceptionIntrospectProperties(clz.getName());
         this.error(subComponent, key, exMsg);
      } finally {
         Debug.println("Clear the BeanInfo cache for RA Classes");
         Debug.exit(this.getClass().getCanonicalName(), "validateProperties() ");
      }

   }

   public static final PropertyDescriptor[] getPropertyDescriptors(Class clz) throws IntrospectionException {
      return IntrospectorManager.getBeanInfo(clz).getPropertyDescriptors();
   }

   private void findAndRegisterConfigPropertyInjector(String subComponent, String key, String elementName, Class clz, PropertyDescriptor[] pds, String name, String type, PropSetterTable propSetterTable) {
      if (!findAndRegisterConfigPropertyInjector(pds, name, type, propSetterTable, this.getPropertyNameNormalizer())) {
         String msg = fmt.NO_SET_METHOD_FOR_PROPERTY("META-INF/ra.xml", elementName, name, type, clz.getName());
         this.reportPropertyIssue(subComponent, key, msg);
      }

   }

   public static boolean findAndRegisterConfigPropertyInjector(PropertyDescriptor[] pds, String name, String type, PropSetterTable propSetterTable, PropertyNameNormalizer propertyNameNormalizer) {
      Method setter = ValidationUtils.findSetterProperty(pds, name, type, propertyNameNormalizer);
      if (setter != null) {
         propSetterTable.registerSetterProperty(name, type, setter);
         return true;
      } else {
         return false;
      }
   }

   private boolean validateConfigPropertyNameType(String elementName, ConfigPropertyBean configProp) {
      boolean validProperty = true;
      String keyName = configProp.getConfigPropertyName();
      String keyValue = configProp.getConfigPropertyValue();
      String keyType = configProp.getConfigPropertyType();
      Debug.println("Validate the config property Name = '" + keyName + "' Type = '" + keyType + "' value = '" + keyValue + "'");
      if (keyName == null || keyName.length() == 0) {
         this.warning(fmt.NULL_PROPERTY_NAME(elementName, keyType, keyValue));
         validProperty = false;
      }

      if (!TypeUtils.isSupportedType(keyType)) {
         this.warning(fmt.INVALID_PROPERTY_TYPE("META-INF/ra.xml", elementName, keyName, keyType));
         validProperty = false;
      }

      return validProperty;
   }

   private void validateConfigPropertyDefaultType(String elementName, ConfigPropertyBean configProp) {
      String keyValue = configProp.getConfigPropertyValue();
      String keyType = configProp.getConfigPropertyType();
      if (keyValue != null && !"".equals(keyValue)) {
         try {
            TypeUtils.getValueByType(keyValue, keyType);
         } catch (NumberFormatException var6) {
            this.warning(fmt.PROPERTY_TYPE_VALUE_MISMATCH("META-INF/ra.xml", elementName, configProp.getConfigPropertyName(), keyType, keyValue, var6.toString()));
         }
      }

   }

   public List checkForDuplicateProperty(String subComponent, String key, String elementName, ConfigPropertyBean[] props) {
      Comparator comparator = new ConfigPropertyBeanComparator(this.getPropertyNameNormalizer());
      List duplicatedProperties = this.getDuplicateProperties(subComponent, key, elementName, props, comparator);
      return duplicatedProperties;
   }

   protected void reportDuplicateProperties(String subComponent, String key, String elementName, List duplicatedProperties) {
      StringBuilder sb = new StringBuilder();
      Iterator var6 = duplicatedProperties.iterator();

      while(var6.hasNext()) {
         Object item = var6.next();
         ConfigPropertyBean prop = (ConfigPropertyBean)item;
         sb.append(prop.getConfigPropertyName() + ",");
      }

      sb.deleteCharAt(sb.length() - 1);
      String message = fmt.DUPLICATE_RA_PROPERTY(elementName, sb.toString());
      this.reportPropertyIssue(subComponent, key, message);
   }
}
