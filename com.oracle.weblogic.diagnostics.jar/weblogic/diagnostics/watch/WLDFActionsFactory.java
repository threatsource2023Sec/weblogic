package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFArrayPropertyBean;
import weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBean;
import weblogic.diagnostics.descriptor.WLDFConfigurationPropertyBean;
import weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBean;
import weblogic.diagnostics.descriptor.WLDFPropertyBean;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;

public class WLDFActionsFactory {
   static final String ESCAPE_CHAR = "\\";
   static final String VALUE_SEPARATOR = ",";
   static final String CSV_ESCAPE_SEQUENCE = Pattern.quote("\\") + ",";
   static final String CSV_SPLITTER = "(?<!\\Q\\\\E),";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final DiagnosticsTextWatchTextFormatter txtFormatter = DiagnosticsTextWatchTextFormatter.getInstance();

   public static ActionConfigBean convertToActionConfig(WLDFActionBean actionBean) throws IntrospectionException, InstantiationException, IllegalAccessException, ClassNotFoundException {
      ActionConfigBean configBean = null;
      ServiceLocator serviceLocator = WatchUtils.getServiceLocator();
      if (serviceLocator != null) {
         configBean = (ActionConfigBean)serviceLocator.getService(ActionConfigBean.class, actionBean.getType(), new Annotation[0]);
         if (configBean != null) {
            configBean.setEnabled(actionBean.isEnabled());
            configBean.setName(actionBean.getName());
            configBean.setTimeout(actionBean.getTimeout());
            WLDFConfigurationPropertyBean[] configurationProperties = actionBean.getConfigurationProperties();
            if (configurationProperties != null && configurationProperties.length > 0) {
               List invalidProperties = new ArrayList();
               BeanInfo beanInfo = Introspector.getBeanInfo(configBean.getClass());
               PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
               WLDFConfigurationPropertyBean[] var7 = configurationProperties;
               int var8 = configurationProperties.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  WLDFConfigurationPropertyBean property = var7[var9];
                  String propertyName = property.getName();
                  PropertyDescriptor descriptor = findProperty(propertyName, propertyDescriptors);
                  if (descriptor == null) {
                     invalidProperties.add(propertyName);
                  } else {
                     Method writeMethod = descriptor.getWriteMethod();
                     Class[] parameters = writeMethod.getParameterTypes();
                     if (parameters.length != 1) {
                        throw new IllegalArgumentException(txtFormatter.getInvalidActionConfigPropertyWriteMethodText(propertyName, parameters.length));
                     }

                     Class fieldType = parameters[0];
                     Object convertedValue = null;
                     if (property instanceof WLDFPropertyBean) {
                        convertedValue = convertProperty((WLDFPropertyBean)property, fieldType);
                     } else if (property instanceof WLDFEncryptedPropertyBean) {
                        convertedValue = convertEncryptedProperty((WLDFEncryptedPropertyBean)property, fieldType);
                     } else if (property instanceof WLDFArrayPropertyBean) {
                        if (fieldType.isArray()) {
                           convertedValue = convertArray((WLDFArrayPropertyBean)property, fieldType);
                        } else if (List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType)) {
                           convertedValue = convertToListOrSet((WLDFArrayPropertyBean)property, writeMethod, fieldType);
                        }
                     } else {
                        if (!(property instanceof WLDFConfigurationPropertiesBean)) {
                           throw new IllegalStateException("Unexpected property type: " + property.getClass().getName());
                        }

                        if (Map.class.isAssignableFrom(fieldType)) {
                           convertedValue = convertMap((WLDFConfigurationPropertiesBean)property, writeMethod, fieldType);
                        }
                     }

                     try {
                        writeMethod.invoke(configBean, convertedValue);
                     } catch (InvocationTargetException | IllegalAccessException var18) {
                        throw new IllegalArgumentException(var18);
                     }
                  }
               }

               if (invalidProperties.size() > 0) {
                  throw new IllegalArgumentException(txtFormatter.getIllegalActionPropertiesText(actionBean.getName(), actionBean.getType(), invalidProperties.toString(), listPropertyNames(propertyDescriptors).toString()));
               }
            }
         }
      }

      return configBean;
   }

   private static List listPropertyNames(PropertyDescriptor[] propertyDescriptors) {
      List namesList = new ArrayList(propertyDescriptors.length);
      if (propertyDescriptors != null) {
         PropertyDescriptor[] var2 = propertyDescriptors;
         int var3 = propertyDescriptors.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor descriptor = var2[var4];
            String propertyName = descriptor.getName();
            if (!isReservedProperty(propertyName)) {
               namesList.add(propertyName);
            }
         }
      }

      return namesList;
   }

   private static boolean isReservedProperty(String propertyName) {
      switch (propertyName) {
         case "enabled":
         case "type":
         case "class":
         case "timeout":
         case "name":
            return true;
         default:
            return false;
      }
   }

   private static PropertyDescriptor findProperty(String propertyName, PropertyDescriptor[] propertyDescriptors) {
      if (propertyName != null && !isReservedProperty(propertyName)) {
         PropertyDescriptor[] var2 = propertyDescriptors;
         int var3 = propertyDescriptors.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor p = var2[var4];
            if (p.getName().equalsIgnoreCase(propertyName)) {
               return p;
            }
         }
      }

      return null;
   }

   private static Object convertMap(WLDFConfigurationPropertiesBean mapProperties, Method writeMethod, Class fieldType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      Map valuesMap = fieldType.isInterface() ? new HashMap() : (Map)fieldType.newInstance();
      Class mapValueFieldType = getActualGenericParameterType(writeMethod, 1);
      WLDFConfigurationPropertyBean[] var5 = mapProperties.getConfigurationProperties();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WLDFConfigurationPropertyBean property = var5[var7];
         String key = property.getName();
         Object value = null;
         if (property instanceof WLDFPropertyBean) {
            value = convertProperty((WLDFPropertyBean)property, mapValueFieldType);
         } else if (property instanceof WLDFEncryptedPropertyBean) {
            value = convertEncryptedProperty((WLDFEncryptedPropertyBean)property, mapValueFieldType);
         }

         ((Map)valuesMap).put(key, value);
      }

      return valuesMap;
   }

   private static Object convertArray(WLDFArrayPropertyBean property, Class fieldType) {
      Class componentType = fieldType.getComponentType();
      String[] values = property.getValue();
      Object valueArray = fieldType.cast(Array.newInstance(componentType, values.length));

      for(int i = 0; i < values.length; ++i) {
         Object value = convertValueToTargetType(componentType, values[i]);
         Array.set(valueArray, i, value);
      }

      return valueArray;
   }

   private static Object convertToListOrSet(WLDFArrayPropertyBean property, Method writeMethod, Class fieldType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      Collection valuesList = newInstance(fieldType);
      convertCollection(property, writeMethod, valuesList);
      return valuesList;
   }

   private static Collection newInstance(Class fieldType) throws InstantiationException, IllegalAccessException {
      return Set.class.isAssignableFrom(fieldType) ? (Collection)((Collection)(fieldType.isInterface() ? new HashSet() : fieldType.newInstance())) : (Collection)((Collection)(fieldType.isInterface() ? new ArrayList() : fieldType.newInstance()));
   }

   private static void convertCollection(WLDFArrayPropertyBean property, Method writeMethod, Collection valuesList) throws ClassNotFoundException {
      Class elementType = getActualGenericParameterType(writeMethod, 0);
      String[] values = property.getValue();
      String[] var5 = values;
      int var6 = values.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String value = var5[var7];
         Object convertedValue = convertValueToTargetType(elementType, value);
         valuesList.add(convertedValue);
      }

   }

   private static Class getActualGenericParameterType(Method writeMethod, int position) throws ClassNotFoundException {
      Type[] genericParameterTypes = writeMethod.getGenericParameterTypes();
      ParameterizedType genericType = (ParameterizedType)genericParameterTypes[0];
      Type[] actualTypeArguments = genericType.getActualTypeArguments();
      if (actualTypeArguments != null && actualTypeArguments.length - 1 >= position) {
         String typeName = actualTypeArguments[position].toString();
         typeName = typeName.replaceAll("class ", "");
         Class elementType = Class.forName(typeName);
         return elementType;
      } else {
         return String.class;
      }
   }

   private static Object convertProperty(WLDFPropertyBean propertyBean, Class fieldType) {
      String property = propertyBean.getValue();
      return convertValueToTargetType(fieldType, property);
   }

   private static Object convertEncryptedProperty(WLDFEncryptedPropertyBean propertyBean, Class fieldType) {
      String property = propertyBean.getEncryptedValue();
      return convertValueToTargetType(fieldType, property);
   }

   private static Object convertValueToTargetType(Class fieldType, String property) {
      if (!Long.class.isAssignableFrom(fieldType) && !Long.TYPE.isAssignableFrom(fieldType)) {
         if (!Integer.class.isAssignableFrom(fieldType) && !Integer.TYPE.isAssignableFrom(fieldType)) {
            if (!Short.class.isAssignableFrom(fieldType) && !Short.class.isAssignableFrom(fieldType)) {
               if (!Double.class.isAssignableFrom(fieldType) && !Double.TYPE.isAssignableFrom(fieldType)) {
                  if (!Float.class.isAssignableFrom(fieldType) && !Float.TYPE.isAssignableFrom(fieldType)) {
                     if (!Boolean.class.isAssignableFrom(fieldType) && !Boolean.TYPE.isAssignableFrom(fieldType)) {
                        return !Byte.class.isAssignableFrom(fieldType) && !Byte.TYPE.isAssignableFrom(fieldType) ? property : Byte.parseByte(property);
                     } else {
                        return Boolean.parseBoolean(property);
                     }
                  } else {
                     return Float.parseFloat(property);
                  }
               } else {
                  return Double.parseDouble(property);
               }
            } else {
               return Short.parseShort(property);
            }
         } else {
            return Integer.parseInt(property);
         }
      } else {
         return Long.parseLong(property);
      }
   }
}
