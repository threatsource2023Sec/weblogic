package weblogic.connector.configuration.meta;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.resource.spi.ConfigProperty;
import weblogic.connector.exception.RAException;
import weblogic.connector.utils.ArrayUtils;
import weblogic.connector.utils.IntrospectorManager;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.ConfigPropertyBean;

abstract class AbstractConfigPropertyProcessor {
   private final ConnectorAPContextImpl apcontext;

   AbstractConfigPropertyProcessor(ConnectorAPContextImpl apcontext) {
      this.apcontext = apcontext;
   }

   protected Map readProperties(Class clz, Object bean) throws RAException {
      Map properties = instropectJavaBeanProperties(clz);
      Field[] fields = this.getFieldsForScan(clz);
      this.scanFieldAnnotation(clz, properties, fields);
      Method[] methods = clz.getMethods();
      this.scanMethodAnnotation(clz, properties, methods);
      this.flushDescriptorBean(properties, bean);
      return properties;
   }

   private Field[] getFieldsForScan(Class clz) {
      HashSet fields;
      for(fields = new HashSet(); clz != Object.class; clz = clz.getSuperclass()) {
         Field[] var3 = clz.getDeclaredFields();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            fields.add(field);
         }
      }

      return (Field[])fields.toArray(new Field[fields.size()]);
   }

   private void scanFieldAnnotation(Class clz, Map properties, Field[] fields) throws RAException {
      Field[] var4 = fields;
      int var5 = fields.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field field = var4[var6];
         ConfigProperty configProperty = (ConfigProperty)field.getAnnotation(ConfigProperty.class);
         if (configProperty != null) {
            if (properties.get(field.getName()) != null) {
               this.processAnnotation(clz, field.getName(), field.getName(), field.getType(), configProperty, properties);
            } else {
               this.apcontext.error(ValidationMessage.RAComplianceTextMsg.CONFIGPROPERTY_FEILD_IS_NOT_PROPERTY(clz.getName(), field.toString()));
            }
         }
      }

   }

   private void scanMethodAnnotation(Class clz, Map properties, Method[] methods) throws RAException {
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         ConfigProperty configProperty = (ConfigProperty)method.getAnnotation(ConfigProperty.class);
         if (configProperty != null) {
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
               String propertyName = Introspector.decapitalize(method.getName().substring(3));
               if (!properties.containsKey(propertyName)) {
                  this.apcontext.error(ValidationMessage.RAComplianceTextMsg.CONFIGPROPERTY_NOT_VALID_SETTER_NO_PROPERTY_FOUND(clz.getName(), method.toString(), propertyName));
               } else {
                  this.processAnnotation(clz, method.getName(), propertyName, method.getParameterTypes()[0], configProperty, properties);
               }
            } else {
               this.apcontext.error(ValidationMessage.RAComplianceTextMsg.CONFIGPROPERTY_NOT_VALID_SETTER_METHOD(clz.getName(), method.toGenericString()));
            }
         }
      }

   }

   protected void flushDescriptorBean(Map properties, Object bean) {
      Iterator var3 = properties.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         ConfigPropertyModel configPropertyModel = (ConfigPropertyModel)entry.getValue();
         String propertyName = configPropertyModel.getName();
         ConfigPropertyBean configPropertyBean = this.getOrCreateConfigPropertyBean(propertyName, bean);
         this.mergeDDandModel(configPropertyModel, configPropertyBean);
      }

   }

   void mergeDDandModel(ConfigPropertyModel configPropertyModel, ConfigPropertyBean configPropertyBean) {
      if (!MetaUtils.isPropertySet(configPropertyBean, "ConfigPropertyValue") && !"".equals(configPropertyModel.getDefaultValue())) {
         configPropertyBean.setConfigPropertyValue(configPropertyModel.getDefaultValue());
      }

      if (!MetaUtils.isPropertySet(configPropertyBean, "Descriptions") && configPropertyModel.getDiscriptions() != null && configPropertyModel.getDiscriptions().length > 0) {
         String[] var3 = configPropertyModel.getDiscriptions();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String description = var3[var5];
            if (!"".equals(description)) {
               configPropertyBean.addDescription(description);
            }
         }
      }

      if (!MetaUtils.isPropertySet(configPropertyBean, "ConfigPropertyType") && !"".equals(configPropertyModel.getType())) {
         configPropertyBean.setConfigPropertyType(configPropertyModel.getType());
      }

      if (!MetaUtils.isPropertySet(configPropertyBean, "ConfigPropertyConfidential")) {
         configPropertyBean.setConfigPropertyConfidential(configPropertyModel.isConfidential());
      }

      if (!MetaUtils.isPropertySet(configPropertyBean, "ConfigPropertySupportsDynamicUpdates")) {
         configPropertyBean.setConfigPropertySupportsDynamicUpdates(configPropertyModel.isSupportsDynamicUpdates());
      }

      if (!MetaUtils.isPropertySet(configPropertyBean, "ConfigPropertyIgnore")) {
         configPropertyBean.setConfigPropertyIgnore(configPropertyModel.isIgore());
      }

   }

   public static Map instropectJavaBeanProperties(Class clz) throws RAException {
      try {
         BeanInfo beanInfo = IntrospectorManager.getBeanInfo(clz);
         PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
         Map properties = new HashMap();
         PropertyDescriptor[] var4 = propertyDescriptors;
         int var5 = propertyDescriptors.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor propertyDescriptor = var4[var6];
            if (propertyDescriptor.getWriteMethod() != null && MetaUtils.isValidPropertyType(propertyDescriptor.getPropertyType())) {
               ConfigPropertyModel cpd = new ConfigPropertyModel(propertyDescriptor.getName());
               cpd.setType(MetaUtils.canonicalizeConfigPropertyType(propertyDescriptor.getPropertyType()));
               cpd.setDiscriptions(new String[]{propertyDescriptor.getShortDescription()});
               properties.put(cpd.getName(), cpd);
            }
         }

         return properties;
      } catch (IntrospectionException var9) {
         throw new RAException(var9);
      }
   }

   ConfigPropertyBean getOrCreateConfigPropertyBean(String propertyName, Object parentBean) {
      ConfigPropertyBean configPropertyBean = this.findConfigPropertyBeanInDD(propertyName, parentBean);
      if (configPropertyBean == null) {
         configPropertyBean = this.createConfigPropertyBean(parentBean);
         configPropertyBean.setConfigPropertyName(propertyName);
      }

      return configPropertyBean;
   }

   private ConfigPropertyBean findConfigPropertyBeanInDD(String propertyName, Object parentBean) {
      ConfigPropertyBean[] configPropertyBeansInDD = this.getConfigPropertyBeansInDD(parentBean);
      return (ConfigPropertyBean)ArrayUtils.search(configPropertyBeansInDD, Introspector.decapitalize(propertyName), new ArrayUtils.KeyLocator() {
         public String getKey(ConfigPropertyBean property) {
            return Introspector.decapitalize(property.getConfigPropertyName());
         }
      });
   }

   private void processAnnotation(Class bean, String annotatedElementName, String propertyName, Class type, ConfigProperty propertyAnnotation, Map p) throws RAException {
      if (!Object.class.equals(propertyAnnotation.type()) || propertyAnnotation.type().equals(type)) {
         this.apcontext.warning(ValidationMessage.RAComplianceTextMsg.CONFIGPROPERTY_NOT_VALID_TYPE_MATCH(bean.getClass().getName(), annotatedElementName));
      }

      readConfigPropertyAnnotation(propertyAnnotation, type, (ConfigPropertyModel)p.get(propertyName));
   }

   static void readConfigPropertyAnnotation(ConfigProperty configProperty, Class type, ConfigPropertyModel configPropertyModel) {
      assert configPropertyModel != null : "not an valid JavaBeanProperty";

      configPropertyModel.setDiscriptions(configProperty.description());
      configPropertyModel.setDefaultValue(configProperty.defaultValue());
      configPropertyModel.setConfidential(configProperty.confidential());
      configPropertyModel.setSupportsDynamicUpdates(configProperty.supportsDynamicUpdates());
      configPropertyModel.setIgore(configProperty.ignore());
   }

   public abstract ConfigPropertyBean[] getConfigPropertyBeansInDD(Object var1);

   public abstract ConfigPropertyBean createConfigPropertyBean(Object var1);
}
