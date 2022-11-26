package weblogic.security.providers.utils;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.store.bootstrap.Entry;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.common.store.bootstrap.LDIFTParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class GenericEntryConverter implements EntryConverter {
   protected static final String DN = "dn";
   protected static final String OU = "ou";
   protected static final String OBJECTCLASS = "objectClass";
   protected LoggerSpi LOGGER;
   protected LegacyEncryptorSpi legacyEncryptor;
   private boolean isClearTextExport = false;

   public GenericEntryConverter(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      this.LOGGER = logger;
      this.legacyEncryptor = encryptor;
   }

   protected void logDebug(String message) {
      if (this.LOGGER != null && this.LOGGER.isDebugEnabled()) {
         this.LOGGER.debug(message);
      }

   }

   public void setClearTextExport(boolean isClearText) {
      this.isClearTextExport = isClearText;
   }

   protected boolean isClearTextExport() {
      return this.isClearTextExport;
   }

   protected abstract BusinessObjectInfo getBusinessObjectInfo(String var1);

   protected abstract boolean isEncryptAttribute(String var1);

   protected String getAttributeName(String methodName) {
      String fieldName = null;
      if (methodName.startsWith("is")) {
         fieldName = methodName.substring(2);
      } else {
         fieldName = methodName.substring(3);
      }

      return fieldName;
   }

   public Object convert(Entry ldifEntry) {
      Map attributes = ldifEntry.getAttributes();
      Map binaryAttributes = ldifEntry.getBinaryAttributes();
      String dnAttribute = getDNAttribute(attributes);
      if (dnAttribute == null) {
         this.logDebug("DN attribute is not available in the LDIF entry");
         return null;
      } else {
         BusinessObjectInfo businessObjectInfo = this.getBusinessObjectInfo(dnAttribute);
         if (businessObjectInfo == null) {
            this.logDebug("No business object info determined for : " + dnAttribute);
            return null;
         } else {
            Object businessObject = null;

            try {
               businessObject = businessObjectInfo.getBusinessObjectClass().newInstance();
               this.reflectPrimaryKeyFields(businessObject, businessObjectInfo);
            } catch (Exception var12) {
               this.logDebug("reflection of business object and primary key fields failed: " + var12.getMessage());
               return null;
            }

            Iterator entries = attributes.entrySet().iterator();

            Map.Entry mapEntry;
            while(entries.hasNext()) {
               mapEntry = (Map.Entry)entries.next();
               if (!"dn".equals(mapEntry.getKey())) {
                  try {
                     this.reflectAttribute(businessObject, businessObjectInfo, mapEntry);
                  } catch (Exception var11) {
                     this.logDebug("reflection of attribute " + mapEntry.getKey() + " failed: " + var11.getMessage());
                  }
               }
            }

            if (binaryAttributes != null) {
               entries = binaryAttributes.entrySet().iterator();

               while(entries.hasNext()) {
                  mapEntry = (Map.Entry)entries.next();

                  try {
                     this.reflectBinaryAttribute(businessObject, businessObjectInfo, mapEntry);
                  } catch (Exception var10) {
                     this.logDebug("reflection of attribute " + mapEntry.getKey() + " failed: " + var10.getMessage());
                  }
               }
            }

            this.logDebug("returning business object: " + businessObject.toString());
            return businessObject;
         }
      }
   }

   protected void reflectPrimaryKeyFields(Object businessObject, BusinessObjectInfo businessObjectInfo) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Object businessObjectIdObject = businessObjectInfo.getBusinessObjectIdObject();
      Class businessObjectIdClass = businessObjectInfo.getBusinessObjectIdClass();
      Class businessObjectClass = businessObjectInfo.getBusinessObjectClass();
      Method[] methods = businessObjectIdClass.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         String methodName = method.getName();
         if (methodName.startsWith("get") && !methodName.equalsIgnoreCase("getClass") && !methodName.equalsIgnoreCase("getObject")) {
            String setMethodName = "set" + methodName.substring(3);
            Method setMethod = null;

            try {
               setMethod = businessObjectClass.getMethod(setMethodName, String.class);
               setMethod.invoke(businessObject, method.invoke(businessObjectIdObject));
            } catch (NoSuchMethodException var13) {
               this.logDebug("reflectPrimaryKeyFields, no such method: " + setMethodName);
               throw var13;
            }
         }
      }

   }

   public String getMethodName(String attributeName) {
      return attributeName;
   }

   private void reflectAttribute(Object businessObject, BusinessObjectInfo businessObjectInfo, Map.Entry attribute) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Class businessObjectClass = businessObjectInfo.getBusinessObjectClass();
      Method setMethod = determineMethod(businessObjectClass, "set" + this.getMethodName((String)attribute.getKey()));
      if (setMethod == null) {
         this.logDebug("Can not determine setMethod for attribute: " + attribute.getKey());
      } else {
         Collection attrValues = (Collection)attribute.getValue();
         Object[] parameterObjects = this.determineParameterObjects(setMethod, (String)attribute.getKey(), attrValues);
         if (parameterObjects == null) {
            this.logDebug("Empty value from the attribute: " + attribute.getKey());
         } else {
            setMethod.invoke(businessObject, parameterObjects);
         }
      }
   }

   private void reflectBinaryAttribute(Object businessObject, BusinessObjectInfo businessObjectInfo, Map.Entry attribute) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Class businessObjectClass = businessObjectInfo.getBusinessObjectClass();
      Method setMethod = determineMethod(businessObjectClass, "set" + this.getMethodName((String)attribute.getKey()));
      if (setMethod == null) {
         this.logDebug("Can not determine setMethod for attribute: " + attribute.getKey());
      } else {
         Collection attrValues = (Collection)attribute.getValue();
         Object[] parameterObjects = this.determineBinaryParameterObjects(setMethod, (String)attribute.getKey(), attrValues);
         if (parameterObjects == null) {
            this.logDebug("Empty value from the attribute: " + attribute.getKey());
         } else {
            setMethod.invoke(businessObject, parameterObjects);
         }
      }
   }

   public Object[] determineParameterObjects(Method method, String attributeName, Collection attrValues) {
      if (attrValues == null) {
         return null;
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         if (parameterTypes[0].isAssignableFrom(String.class)) {
            String[] attrValueStrings = (String[])((String[])attrValues.toArray(new String[0]));
            if (attrValueStrings.length > 0 && attrValueStrings[0] != null) {
               String value = attrValueStrings[0];
               if (this.isEncryptAttribute(attributeName)) {
                  this.logDebug("process encrypt attribute: " + attributeName);
                  if (this.legacyEncryptor != null) {
                     try {
                        value = this.legacyEncryptor.encryptString(value);
                        this.logDebug("Attribute value encrypted: " + value);
                     } catch (Exception var8) {
                        this.logDebug("Exception in encrypting attribute: " + var8.getMessage());
                     }
                  }
               }

               return new Object[]{value};
            } else {
               return null;
            }
         } else {
            return new Object[]{attrValues};
         }
      }
   }

   private Object[] determineBinaryParameterObjects(Method method, String attributeName, Collection attrValues) {
      if (attrValues == null) {
         return null;
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         if (parameterTypes[0].isAssignableFrom(Collection.class)) {
            return new Object[]{attrValues};
         } else {
            byte[][] attrValueBytes = (byte[][])((byte[][])attrValues.toArray(new byte[0][]));
            if (attrValueBytes.length > 0 && attrValueBytes[0] != null) {
               byte[] byteArray = attrValueBytes[0];
               if (this.isEncryptAttribute(attributeName)) {
                  this.logDebug("process encrypt attribute: " + attributeName);
                  if (this.legacyEncryptor != null) {
                     try {
                        attrValueBytes[0] = this.legacyEncryptor.encryptBytes(byteArray);
                        this.logDebug("Binary Attribute value encrypted");
                     } catch (Exception var8) {
                        this.logDebug("Exception in encrypting binary attribute: " + var8.getMessage());
                     }
                  }
               }

               return new Object[]{byteArray};
            } else {
               return null;
            }
         }
      }
   }

   protected abstract Class determineIdObjectClass(Object var1);

   public abstract Map buildBaseAttributes(Object var1);

   public List convertToLDIFEntries(List businessObjects) {
      ArrayList ldifEntries = new ArrayList();

      for(int i = 0; i < businessObjects.size(); ++i) {
         Object businessObject = businessObjects.get(i);
         Class businessObjectIdClass = this.determineIdObjectClass(businessObject);
         if (businessObjectIdClass == null) {
            this.logDebug("Can not determine business object id class");
         } else {
            Map attributes = new HashMap();
            Map binaryAttributes = new HashMap();
            Map baseAttributes = this.buildBaseAttributes(businessObject, businessObjectIdClass);
            if (baseAttributes != null && baseAttributes.size() != 0) {
               attributes.putAll(baseAttributes);

               try {
                  this.reflectBusinessObject(businessObject, businessObject.getClass(), businessObjectIdClass, attributes, binaryAttributes);
               } catch (Exception var10) {
                  this.logDebug("failed to reflect business object into attributes: " + var10.getMessage());
               }

               Entry entry = new LDIFTParser.EntryImpl(attributes, binaryAttributes);
               ldifEntries.add(entry);
            } else {
               this.logDebug("Can not determine the base attributes for the business object, drop it.");
            }
         }
      }

      return ldifEntries;
   }

   public Map buildBaseAttributes(Object businessObject, Class businessObjectIdClass) {
      Object idObject = null;

      try {
         idObject = this.reflectIdObject(businessObject, businessObject.getClass(), businessObjectIdClass);
      } catch (Exception var5) {
         this.logDebug("failed to construct id object from business object: " + var5.getMessage());
      }

      if (idObject == null) {
         this.logDebug("Fail to build the Id object from the business object");
         return null;
      } else {
         return this.buildBaseAttributes(idObject);
      }
   }

   protected Object reflectIdObject(Object businessObject, Class businessObjectClass, Class businessObjectIdClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Object businessObjectIdObject = null;

      try {
         businessObjectIdObject = businessObjectIdClass.newInstance();
      } catch (InstantiationException var11) {
         return null;
      } catch (IllegalAccessException var12) {
         return null;
      }

      Method[] methods = businessObjectIdClass.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         String methodName = method.getName();
         if (methodName.startsWith("set")) {
            String getMethodName = "get" + methodName.substring(3);
            Method getMethod = businessObjectClass.getMethod(getMethodName);
            method.invoke(businessObjectIdObject, getMethod.invoke(businessObject));
         }
      }

      return businessObjectIdObject;
   }

   protected void reflectBusinessObject(Object businessObject, Class businessObjectClass, Class businessObjectIdClass, Map attributes, Map binaryAttributes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Method[] methods = businessObjectClass.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         String methodName = method.getName();
         if (!this.isExcluded(methodName)) {
            Object returnObject = method.invoke(businessObject);
            this.logDebug("method " + methodName + " returns object: " + returnObject);
            if (returnObject != null) {
               String attributeName = this.getAttributeName(methodName);
               if (attributeName != null) {
                  attributeName = attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1);
                  this.addAttribute(attributeName, returnObject, attributes, binaryAttributes);
               } else {
                  this.LOGGER.debug(methodName + " related attributeName is null");
               }
            }
         }
      }

   }

   protected boolean isExcluded(String methodName) {
      boolean result = false;
      if (!methodName.startsWith("is") && !methodName.startsWith("get") || methodName.equalsIgnoreCase("getRealmName") || methodName.equalsIgnoreCase("getDomainName") || methodName.equalsIgnoreCase("getClass")) {
         result = true;
      }

      return result;
   }

   protected void addAttribute(String attributeName, Object attributeValueObject, Map attributes, Map binaryAttributes) {
      ArrayList attributeValues = new ArrayList();
      if (attributeValueObject instanceof String) {
         String stringValue = (String)attributeValueObject;
         if (this.isEncryptAttribute(attributeName) && this.isClearTextExport) {
            this.logDebug("Encrypt attribute need to do clear text export");

            try {
               stringValue = this.legacyEncryptor.decryptString(stringValue);
            } catch (Exception var9) {
               this.logDebug("Decryption exception when decrypt attribute: " + var9.getMessage());
            }
         }

         attributeValues.add(stringValue);
         attributes.put(attributeName, attributeValues);
      } else if (attributeValueObject instanceof byte[]) {
         byte[] byteArray = (byte[])((byte[])attributeValueObject);
         if (this.isEncryptAttribute(attributeName) && this.isClearTextExport) {
            this.logDebug("Encrypt binary attribute need to do clear text export");

            try {
               byteArray = this.legacyEncryptor.decryptBytes(byteArray);
            } catch (Exception var8) {
               this.logDebug("Decryption exception when decrypt binary attribute: " + var8.getMessage());
            }
         }

         attributeValues.add(byteArray);
         binaryAttributes.put(attributeName, attributeValues);
      } else {
         Collection attributeValueCollection = (Collection)attributeValueObject;
         Object[] objects = attributeValueCollection.toArray();
         if (objects.length > 0) {
            if (objects[0] instanceof String) {
               attributes.put(attributeName, attributeValueObject);
            } else {
               binaryAttributes.put(attributeName, attributeValueObject);
            }
         }
      }

   }

   public static String getDNAttribute(Map attributes) {
      if (attributes == null) {
         return null;
      } else {
         Collection dnAttributes = (Collection)attributes.get("dn");
         return dnAttributes != null && dnAttributes.size() != 0 ? (String)dnAttributes.iterator().next() : null;
      }
   }

   private static Method determineMethod(Class boClass, String methodName) {
      Method[] methods = boClass.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equalsIgnoreCase(methodName)) {
            return methods[i];
         }
      }

      return null;
   }

   public String[] determinePrimaryKeyFields(String dnString) {
      if (dnString != null && !dnString.equals("")) {
         ArrayList list = new ArrayList();
         StringTokenizer st = new StringTokenizer(dnString, ",");

         while(st.hasMoreElements()) {
            String token = st.nextToken();
            if (!this.inFilterList(token)) {
               int indexOfEqual = token.indexOf("=");
               if (indexOfEqual == -1) {
                  throw new RuntimeException("Bad Fromat dn string: " + dnString);
               }

               list.add(token.substring(indexOfEqual + 1));
            }
         }

         return (String[])((String[])list.toArray(new String[list.size()]));
      } else {
         return new String[0];
      }
   }

   public boolean inFilterList(String token) {
      return false;
   }

   protected static final boolean notEmpty(String s) {
      return s != null && !s.equals("");
   }

   public static class BusinessObjectInfo {
      private Class businessObjectClass;
      private Class businessObjectIdClass;
      private Object businessObjectIdObject;

      public BusinessObjectInfo(Class boClass, Class boIdClass, Object idObject) {
         this.businessObjectClass = boClass;
         this.businessObjectIdClass = boIdClass;
         this.businessObjectIdObject = idObject;
      }

      public Class getBusinessObjectClass() {
         return this.businessObjectClass;
      }

      public Class getBusinessObjectIdClass() {
         return this.businessObjectIdClass;
      }

      public Object getBusinessObjectIdObject() {
         return this.businessObjectIdObject;
      }
   }
}
