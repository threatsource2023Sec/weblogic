package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.management.Attribute;
import javax.management.DynamicMBean;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
import org.codehaus.jettison.mapped.MappedXMLInputFactory;
import org.codehaus.jettison.mapped.MappedXMLOutputFactory;
import org.xml.sax.SAXException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ExportCustomizeableValues;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.ImportExportLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.security.RealmMBean;
import weblogic.utils.XXEUtils;

public class ExportCustomizeableValuesHelper {
   private static Object annotatedObj;
   private static JSONObject attributesJson;
   private static String annotatedObjType;
   private static String systemResourceEncryptedData;
   private static final BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("PortablePartitionHelper");

   public static String processExportCustomizeableValuesAnnotation(Object obj, String objName, String sysResourceEncryptedData) throws Exception {
      JSONObject topLevelJson = new JSONObject();

      try {
         systemResourceEncryptedData = sysResourceEncryptedData;
         annotatedObj = obj;
         createNewJsonObject(objName);
         processAnnotation(obj);
         addToTopLevelJson(topLevelJson);
      } finally {
         systemResourceEncryptedData = null;
      }

      return topLevelJson.toString(4);
   }

   private static void addToTopLevelJson(JSONObject topLevelJson) throws JSONException {
      if (topLevelJson.has(annotatedObjType)) {
         topLevelJson.accumulate(annotatedObjType, attributesJson);
      } else {
         topLevelJson.put(annotatedObjType, attributesJson);
      }

   }

   private static void createNewJsonObject(String objName) throws JSONException {
      attributesJson = new JSONObject();
      String objType = annotatedObj.getClass().getName();
      if (annotatedObj instanceof AbstractDescriptorBean) {
         String xmlName = ((AbstractDescriptorBean)annotatedObj)._getBeanElementName();
         objType = xmlName;
      }

      attributesJson.put("name", objName);
      annotatedObjType = objType;
   }

   private static void processAnnotation(Object obj) throws IOException, JSONException {
      assert obj != null;

      ExportCustomizeableValues classPortable = (ExportCustomizeableValues)obj.getClass().getAnnotation(ExportCustomizeableValues.class);
      PropertyDescriptor[] var2 = beanInfoAccess.getBeanInfoForInstance(obj, false, (String)null).getPropertyDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyDescriptor propDesc = var2[var4];
         ExportCustomizeableValues portable = null;
         Method readMethod = propDesc.getReadMethod();
         if (readMethod != null) {
            if (classPortable == null) {
               portable = (ExportCustomizeableValues)readMethod.getAnnotation(ExportCustomizeableValues.class);
            }

            if (classPortable != null || portable != null) {
               dumpMethods(propDesc, readMethod, portable.saveDefault(), obj);
            }
         }
      }

   }

   private static String getXmlNameFromProp(PropertyDescriptor pDesc) {
      int propIndex = ((AbstractDescriptorBean)annotatedObj)._getPropertyIndex(pDesc.getName());
      return ((AbstractDescriptorBean)annotatedObj)._getSchemaHelper2().getElementName(propIndex);
   }

   private static void addStringAttributesToJson(String objectName, String objectVal) throws JSONException {
      attributesJson.accumulate(objectName, objectVal);
   }

   private static void dumpMethods(PropertyDescriptor pDesc, Method method, boolean saveDefault, Object obj) throws JSONException {
      Class retType = method.getReturnType();
      Object retObject = null;
      String arrayObjectName = getXmlNameFromProp(pDesc);

      try {
         if (retType.isArray()) {
            Object array = method.invoke(annotatedObj);
            if (arrayObjectName == null) {
               arrayObjectName = method.getName().replaceFirst("get", "");
            }

            int length = Array.getLength(array);

            for(int i = 0; i < length; ++i) {
               retObject = Array.get(array, i);
               if (!((AbstractDescriptorBean)retObject)._isTransient()) {
                  if (retObject instanceof DescriptorBean) {
                     getAttributesFromXml(retObject, saveDefault, arrayObjectName, false);
                  } else {
                     String objectName = getXmlNameFromProp(pDesc);
                     addStringAttributesToJson(objectName, (String)retObject);
                  }
               }
            }
         } else {
            if (Iterable.class.isAssignableFrom(retType)) {
               throw new RuntimeException("right now returning a collection is not supported for exporting a partition");
            }

            retObject = method.invoke(annotatedObj);
            if (retObject instanceof DescriptorBean) {
               if (obj instanceof SystemResourceMBean) {
                  DescriptorBean sysResource = ((SystemResourceMBean)obj).getResource();
                  if (sysResource.equals(retObject)) {
                     getAttributesFromXml(retObject, saveDefault, (String)null, true);
                  } else {
                     getAttributesFromXml(retObject, saveDefault);
                  }
               } else {
                  getAttributesFromXml(retObject, saveDefault);
               }
            } else {
               String objectName = getXmlNameFromProp(pDesc);
               addStringAttributesToJson(objectName, (String)retObject);
            }
         }
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var11) {
         var11.printStackTrace();
      } catch (SecurityException var12) {
         var12.printStackTrace();
      }

   }

   private static boolean isReferenceRelationship(Object obj1, Object obj2) {
      if (obj1 instanceof SystemResourceMBean) {
         return false;
      } else {
         List partitionReferences = ImportExportHelper.getAllPartitionReferences(obj1);
         if (obj2 instanceof AbstractDescriptorBean) {
            String xmlName = ((AbstractDescriptorBean)obj2)._getBeanElementName();
            String objName = getPojoFromXml(annotatedObj, xmlName);
            return partitionReferences.contains(objName);
         } else {
            return partitionReferences.contains(obj2);
         }
      }
   }

   private static String getJSONFromXML(String xmlString) throws XMLStreamException {
      ByteArrayOutputStream bOAS = new ByteArrayOutputStream();
      XMLInputFactory xmlFactory = XXEUtils.createXMLInputFactoryInstance();
      xmlFactory.setProperty("javax.xml.stream.isNamespaceAware", false);
      xmlFactory.setProperty("javax.xml.stream.isNamespaceAware", false);
      XMLEventReader reader = xmlFactory.createXMLEventReader(new StringReader(xmlString));
      XMLEventWriter writer = (new MappedXMLOutputFactory(new HashMap())).createXMLEventWriter(bOAS);
      writer.add(reader);
      writer.close();
      reader.close();
      return bOAS.toString();
   }

   private static void getAttributesFromXml(Object obj, boolean saveDefault, String arrayObjectName, boolean isSystemResource) throws JSONException {
      if (obj != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("gettingAttributes from " + obj.getClass().getName() + " whose class loader is " + obj.getClass().getClassLoader());
         }

         String objXml1;
         if (isSystemResource && systemResourceEncryptedData != null && systemResourceEncryptedData.length() > 0) {
            objXml1 = systemResourceEncryptedData;
         } else {
            DescriptorManager dm = DescriptorManagerHelper.getDescriptorManager(true);

            try {
               objXml1 = DescriptorUtils.toString(dm, (DescriptorBean)obj);
            } catch (IOException var13) {
               throw new JSONException(var13.getCause());
            }
         }

         BufferedReader br = new BufferedReader(new StringReader(objXml1));
         StringBuilder sb = new StringBuilder();

         try {
            String line;
            while((line = br.readLine()) != null) {
               sb.append(line.trim());
            }

            JSONObject objJson = new JSONObject(getJSONFromXML(sb.toString()));
            Iterator jsonIter = objJson.keys();

            while(jsonIter.hasNext()) {
               String jsonElement = jsonIter.next().toString();
               if (arrayObjectName != null) {
                  String arrObjName = getPojoFromXml(annotatedObj, arrayObjectName);
                  if (arrObjName == null) {
                     arrObjName = arrayObjectName;
                  }

                  if (isReferenceRelationship(annotatedObj, arrObjName)) {
                     setNameOnJSON(objJson.getJSONObject(jsonElement));
                  }

                  if (arrayObjectName.compareTo(jsonElement) == 0) {
                     addElementToJSONTree(attributesJson, objJson, jsonElement);
                  } else if (attributesJson.has(arrayObjectName)) {
                     attributesJson.getJSONArray(arrayObjectName).put(objJson);
                  } else {
                     JSONArray arrayObject = new JSONArray();
                     arrayObject.put(objJson);
                     attributesJson.put(arrayObjectName, arrayObject);
                  }
               } else {
                  if (isReferenceRelationship(annotatedObj, obj)) {
                     setNameOnJSON(objJson.getJSONObject(jsonElement));
                  }

                  if (!saveDefault) {
                     emptyValueOfObj(objJson.getJSONObject(jsonElement));
                  }

                  addElementToJSONTree(attributesJson, objJson, jsonElement);
               }
            }

         } catch (XMLStreamException | FactoryConfigurationError | IOException var14) {
            var14.printStackTrace();
            throw new JSONException(var14.getLocalizedMessage());
         }
      }
   }

   private static void getAttributesFromXml(Object obj, boolean saveDefault) throws JSONException {
      getAttributesFromXml(obj, saveDefault, (String)null, false);
   }

   private static void addElementToJSONTree(JSONObject parent, JSONObject child, String jsonElement) throws JSONException {
      if (parent.has(jsonElement)) {
         parent.accumulate(jsonElement, child.get(jsonElement));
      } else {
         parent.put(jsonElement, child.get(jsonElement));
      }

   }

   private static void setNameOnJSON(JSONObject obj) throws JSONException {
      Iterator keys = obj.keys();

      while(keys.hasNext()) {
         Object key = keys.next();
         if (key.toString().compareTo("name") != 0) {
            keys.remove();
         }
      }

   }

   private static void emptyValueOfObj(JSONObject obj) throws JSONException {
      Iterator keys = obj.keys();

      while(keys.hasNext()) {
         Object key = keys.next();
         Object jsonObject = obj.get(key.toString());
         if (jsonObject instanceof JSONObject) {
            emptyValueOfObj((JSONObject)jsonObject);
         } else if (jsonObject instanceof JSONArray) {
            emptyValueOfArray((JSONArray)jsonObject);
         } else {
            obj.put(key.toString(), "");
         }
      }

   }

   private static void emptyValueOfArray(JSONArray jsonArray) throws JSONException {
      for(int i = 0; i < jsonArray.length(); ++i) {
         Object jsonObj = jsonArray.get(i);
         if (jsonObj instanceof JSONObject) {
            emptyValueOfObj((JSONObject)jsonObj);
         } else if (jsonObj instanceof JSONArray) {
            emptyValueOfArray((JSONArray)jsonObj);
         }
      }

   }

   public static String getPojoFromXml(Object beanObj, String xmlName) {
      int idx = ((AbstractDescriptorBean)beanObj)._getSchemaHelper2().getPropertyIndex(xmlName);
      return idx == -1 ? null : ((AbstractDescriptorBean)beanObj)._getPropertyName(idx);
   }

   private static String getNameFromJsonObj(Object jsonObj) throws JSONException {
      String fromJsonName = null;
      if (jsonObj instanceof JSONArray) {
         JSONObject tmp1 = ((JSONArray)jsonObj).getJSONObject(0);
         fromJsonName = tmp1.getString("name");
      } else {
         if (!(jsonObj instanceof JSONObject)) {
            throw new IllegalArgumentException("attribProperties parameter should either be of type JSONObject or JSONArray");
         }

         fromJsonName = ((JSONObject)jsonObj).getString("name");
      }

      return fromJsonName;
   }

   private static Object getReferenceAttribute(DomainMBean domain, Class partitionPropType, String fromJsonName, String attribXmlName) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException, JSONException {
      BeanInfo domainBeanInfo = beanInfoAccess.getBeanInfoForInstance(domain, true, (String)null);
      PropertyDescriptor[] var5 = domainBeanInfo.getPropertyDescriptors();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         PropertyDescriptor pDomainPDescriptor = var5[var7];
         Class domainPropType = pDomainPDescriptor.getPropertyType();
         if (domainPropType.isArray()) {
            domainPropType = domainPropType.getComponentType();
         }

         if (domainPropType.getName().compareTo(partitionPropType.getName()) == 0) {
            Method dmtgo = pDomainPDescriptor.getReadMethod();
            if (dmtgo == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Domain does not have any readmethod for the property" + pDomainPDescriptor.getName());
               }
               break;
            }

            Object domainAttributeObject = dmtgo.invoke(domain);
            if (!domainAttributeObject.getClass().isArray()) {
               return domainAttributeObject;
            }

            for(int i = 0; i < Array.getLength(domainAttributeObject); ++i) {
               Object domAttribute = Array.get(domainAttributeObject, i);
               PropertyDescriptor[] var14 = beanInfoAccess.getBeanInfoForInstance(domAttribute, true, (String)null).getPropertyDescriptors();
               int var15 = var14.length;

               for(int var16 = 0; var16 < var15; ++var16) {
                  PropertyDescriptor pDomAttributePDesc = var14[var16];
                  if (pDomAttributePDesc.getName().compareTo("Name") == 0) {
                     String pDomAttributeValue = (String)pDomAttributePDesc.getReadMethod().invoke(domAttribute);
                     if (pDomAttributeValue != null && fromJsonName != null && pDomAttributeValue.compareTo(fromJsonName) == 0) {
                        return domAttribute;
                     }
                  }
               }
            }
         }
      }

      Loggable l = ImportExportLogger.logIllegalReferenceAttributeLoggable(attribXmlName, fromJsonName);
      l.log();
      throw new IllegalArgumentException(l.getMessage());
   }

   private static Attribute getAttributesFromPropDescriptor(PropertyDescriptor pDescriptor, String pName, Object propValue) {
      if (pDescriptor.getPropertyType().getName().equals("[B")) {
         Object propValue = (byte[])((String)propValue).getBytes();
         return new Attribute(pName, propValue);
      } else if (pDescriptor.getPropertyType().isArray() && propValue.getClass().isArray()) {
         String[] values = (String[])((String[])propValue);
         StringBuilder sb = new StringBuilder();
         String[] var5 = values;
         int var6 = values.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String value = var5[var7];
            sb.append(value + ",");
         }

         sb.deleteCharAt(sb.length());
         Object propValue = sb.toString();
         return new Attribute(pName, propValue);
      } else if (!pDescriptor.getPropertyType().isAssignableFrom(String.class)) {
         return null;
      } else if (propValue instanceof JSONObject) {
         JSONObject pValue = (JSONObject)propValue;
         boolean isNull = false;

         try {
            isNull = pValue.getBoolean("@xsi:nil");
         } catch (JSONException var9) {
            isNull = false;
         }

         return isNull ? null : new Attribute(pName, "");
      } else {
         return new Attribute(pName, propValue);
      }
   }

   private static JSONObject getObjJson(Object obj, String objName, JSONObject readFromStringJson) throws JSONException {
      String xmlName = ((AbstractDescriptorBean)obj)._getBeanElementName();
      Object jsonObjForobj = readFromStringJson.opt(xmlName);
      if (jsonObjForobj == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("the Json read from input string does not contain elements for obj represented by" + xmlName);
         }

         return null;
      } else if (jsonObjForobj instanceof JSONObject) {
         String jsonName = ((JSONObject)jsonObjForobj).optString("name");
         if (jsonName == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("the Json read from input string for object represented by" + xmlName + " does not have a name attribute");
            }

            return null;
         } else {
            return jsonName.compareTo(objName) == 0 ? (JSONObject)jsonObjForobj : null;
         }
      } else {
         if (jsonObjForobj instanceof JSONArray) {
            for(int i = 0; i < ((JSONArray)jsonObjForobj).length(); ++i) {
               JSONObject jsonObj = ((JSONArray)jsonObjForobj).getJSONObject(i);
               String jsonName = jsonObj.optString("name");
               if (jsonName != null && jsonName.compareTo(objName) == 0) {
                  return jsonObj;
               }
            }
         }

         return null;
      }
   }

   private static JSONObject getObjJsonForSystemResource(SystemResourceMBean obj, String objName, JSONObject readFromStringJson) throws JSONException {
      String xmlName = ((AbstractDescriptorBean)obj)._getBeanElementName();
      JSONObject objToReturn = new JSONObject();
      Object jsonObjForobj = readFromStringJson.opt(xmlName);
      if (jsonObjForobj == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("the Json read from input string does not contain elements for obj represented by" + xmlName);
         }

         return null;
      } else {
         String jsonKey;
         if (jsonObjForobj instanceof JSONObject) {
            String jsonName = ((JSONObject)jsonObjForobj).optString("name");
            if (jsonName == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("the Json read from input string for object represented by" + xmlName + " does not have a name attribute");
               }

               return null;
            } else if (jsonName.compareTo(objName) == 0) {
               Iterator readAttJsonKeys = ((JSONObject)jsonObjForobj).keys();

               while(readAttJsonKeys.hasNext()) {
                  jsonKey = readAttJsonKeys.next().toString();
                  if (jsonKey.compareTo("name") != 0) {
                     objToReturn.put(jsonKey, ((JSONObject)jsonObjForobj).get(jsonKey));
                  }
               }

               return objToReturn;
            } else {
               return null;
            }
         } else {
            if (jsonObjForobj instanceof JSONArray) {
               for(int i = 0; i < ((JSONArray)jsonObjForobj).length(); ++i) {
                  JSONObject jsonObj = ((JSONArray)jsonObjForobj).getJSONObject(i);
                  jsonKey = jsonObj.optString("name");
                  if (jsonKey != null && jsonKey.compareTo(objName) == 0) {
                     Iterator readAttJsonKeys = jsonObj.keys();

                     while(readAttJsonKeys.hasNext()) {
                        String jsonKey = readAttJsonKeys.next().toString();
                        if (jsonKey.compareTo("name") != 0) {
                           objToReturn.put(jsonKey, jsonObj.get(jsonKey));
                        }
                     }

                     return objToReturn;
                  }
               }
            }

            return null;
         }
      }
   }

   public static Map createXmlToPojoMap(Object obj, JSONObject objectToRead) {
      Map pojoToXmlTable = new HashMap();
      Iterator readAttJsonKeys = objectToRead.keys();

      while(readAttJsonKeys.hasNext()) {
         String jsonKey = readAttJsonKeys.next().toString();
         String propertyName = getPojoFromXml(obj, jsonKey);
         if (propertyName != null) {
            pojoToXmlTable.put(propertyName, jsonKey);
         } else {
            pojoToXmlTable.put(jsonKey, jsonKey);
         }
      }

      return pojoToXmlTable;
   }

   private static String applySystemResourceJsonOverrides(String xmlFromJsonStr, DescriptorBean origDescriptorBean) throws IOException, XMLStreamException, IllegalStateException, DescriptorUpdateFailedException, ParserConfigurationException, SAXException, TransformerException {
      EditableDescriptorManager descMgr = (EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true);
      ArrayList errs = new ArrayList();
      InputStream changedIs = new ByteArrayInputStream(xmlFromJsonStr.getBytes());
      Descriptor origDescriptor = origDescriptorBean.getDescriptor();
      Descriptor changedDescriptor = descMgr.createDescriptor(new ConfigReader(changedIs), errs, false);
      DescriptorDiff descDiff = origDescriptor.computeDiff(changedDescriptor);
      origDescriptor.applyDiff(descDiff);
      ByteArrayOutputStream boAs = new ByteArrayOutputStream();
      descMgr.writeDescriptorAsXML(origDescriptor, boAs);
      return boAs.toString();
   }

   public static String setAttributesOnSystemResourceObject(DomainMBean domain, SystemResourceMBean sr, String objName, String inputString, String origXmlStr) throws Exception {
      if (inputString == null) {
         return null;
      } else {
         JSONObject readJson = new JSONObject(inputString);
         if (readJson.length() <= 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("The file attribute.json is empty, so no attributes to update on MBeans");
            }

            return null;
         } else {
            if (sr instanceof SystemResourceMBean) {
               JSONObject readAttJson = getObjJsonForSystemResource(sr, objName, readJson);
               if (readAttJson != null) {
                  ByteArrayOutputStream bOAS = new ByteArrayOutputStream();
                  XMLOutputFactory xmlFactory = XMLOutputFactory.newInstance();
                  xmlFactory.setProperty("javax.xml.stream.isNamespaceAware", false);
                  xmlFactory.setProperty("javax.xml.stream.isNamespaceAware", false);
                  XMLStreamWriter xmlStreamWriter = xmlFactory.createXMLStreamWriter(bOAS);
                  serialize(xmlStreamWriter, readAttJson);
                  String xmlString = bOAS.toString();
                  String retString = applySystemResourceJsonOverrides(xmlString, sr.getResource());
                  return retString;
               }
            }

            return null;
         }
      }
   }

   public static void setAttributesOnObject(DomainMBean domain, Object obj, String objName, String inputString) throws Exception {
      if (inputString != null) {
         JSONObject readJson = new JSONObject(inputString);
         if (readJson.length() <= 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("The file attribute.json is empty, so no attributes to update on MBeans");
            }

         } else {
            JSONObject readAttJson = getObjJson(obj, objName, readJson);
            if (readAttJson == null) {
               Loggable l = ImportExportLogger.logIllegalNameChangeLoggable(obj.toString(), objName, "-attributes.json");
               l.log();
            } else {
               Map pojoToXmlTable = createXmlToPojoMap(obj, readAttJson);
               if (!pojoToXmlTable.isEmpty()) {
                  PropertyDescriptor[] var7 = beanInfoAccess.getBeanInfoForInstance(obj, false, (String)null).getPropertyDescriptors();
                  int var8 = var7.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     PropertyDescriptor pDescriptor = var7[var9];
                     setPerAttribute(pDescriptor, obj, pojoToXmlTable, readAttJson, domain);
                  }

               }
            }
         }
      }
   }

   private static void setPerAttribute(PropertyDescriptor pDescriptor, Object obj, Map pojoToXmlTable, JSONObject readAttJson, DomainMBean domain) throws Exception {
      String attName = pDescriptor.getName();
      if (attName.compareToIgnoreCase("Name") != 0) {
         if (pojoToXmlTable.containsKey(attName)) {
            Method methodToGetObj = pDescriptor.getReadMethod();
            if (methodToGetObj != null) {
               try {
                  Object attributeObject = methodToGetObj.invoke(obj);
                  if (attributeObject == null) {
                     setEmptyAttributeObject(pDescriptor, readAttJson, attributeObject, attName, pojoToXmlTable, domain, obj);
                  } else if (attributeObject.getClass().isArray() && Array.getLength(attributeObject) == 0) {
                     addNewArrayAttributetoEmptyObject(pDescriptor, readAttJson, attributeObject, attName, pojoToXmlTable, domain, obj);
                  } else if (attributeObject.getClass().isArray() && Array.getLength(attributeObject) > 0) {
                     updateExistingArrayAttributeObject(pDescriptor, readAttJson, attributeObject, attName, pojoToXmlTable, domain, obj);
                  } else if (attributeObject instanceof DynamicMBean) {
                     setAttributesVal(attributeObject, readAttJson);
                  } else {
                     invokeSetMethod(pDescriptor, attributeObject, obj);
                  }
               } catch (IllegalArgumentException | NoSuchMethodException | SecurityException | IllegalAccessException var8) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(var8.getLocalizedMessage(), var8);
                  }

                  throw var8;
               } catch (InvocationTargetException var9) {
                  var9.getTargetException().printStackTrace();
                  throw var9;
               }
            }
         }

      }
   }

   private static void addNewArrayAttributetoEmptyObject(PropertyDescriptor pDescriptor, JSONObject readAttJson, Object attributeObject, String attName, Map pojoToXmlTable, DomainMBean domain, Object obj) throws JSONException, InvocationTargetException, IllegalAccessException {
      String xmlName = (String)pojoToXmlTable.get(attName);
      Object fromjsonProp = readAttJson.get(xmlName);
      if (fromjsonProp instanceof JSONObject) {
         readAttribJsonForEmptyArray((JSONObject)fromjsonProp, pDescriptor, attName, xmlName, obj, domain);
      } else if (fromjsonProp instanceof JSONArray) {
         for(int i = 0; i < ((JSONArray)fromjsonProp).length(); ++i) {
            readAttribJsonForEmptyArray(((JSONArray)fromjsonProp).getJSONObject(i), pDescriptor, attName, xmlName, obj, domain);
         }
      }

   }

   private static void readAttribJsonForEmptyArray(JSONObject objJson, PropertyDescriptor pDescriptor, String attName, String xmlName, Object obj, DomainMBean domain) throws JSONException, IllegalAccessException, InvocationTargetException {
      Iterator objJsonKeys = objJson.keys();
      if (objJsonKeys == null) {
         findReferencedAndSet(pDescriptor, attName, xmlName, obj, domain, objJson);
      } else {
         while(true) {
            while(objJsonKeys.hasNext()) {
               Object nestedObject = objJson.get(objJsonKeys.next().toString());
               if (nestedObject instanceof JSONObject) {
                  findReferencedAndSet(pDescriptor, attName, xmlName, obj, domain, nestedObject);
               } else if (nestedObject instanceof JSONArray) {
                  for(int i = 0; i < ((JSONArray)nestedObject).length(); ++i) {
                     findReferencedAndSet(pDescriptor, attName, xmlName, obj, domain, ((JSONArray)nestedObject).getJSONObject(i));
                  }
               }
            }

            return;
         }
      }
   }

   private static void findReferencedAndSet(PropertyDescriptor pDescriptor, String attName, String xmlName, Object obj, DomainMBean domain, Object nestedObject) throws IllegalAccessException, JSONException, InvocationTargetException {
      Object attributeObject = null;
      Class partitionPropType = pDescriptor.getPropertyType();
      if (partitionPropType.isArray()) {
         partitionPropType = partitionPropType.getComponentType();
      }

      String fromJsonName = getNameFromJsonObj(nestedObject);
      if (ImportExportHelper.getAllPartitionReferences(obj).contains(attName)) {
         if (fromJsonName.isEmpty()) {
            return;
         }

         attributeObject = getReferenceAttribute(domain, partitionPropType, fromJsonName, xmlName);
         if (attributeObject == null) {
            return;
         }
      }

      invokeSetMethod(pDescriptor, attributeObject, obj);
   }

   private static void invokeSetMethod(PropertyDescriptor pDescriptor, Object attributeObject, Object obj) throws InvocationTargetException, IllegalAccessException {
      Method methodToSetObj;
      if (pDescriptor.getValue("adder") != null) {
         try {
            methodToSetObj = obj.getClass().getMethod(pDescriptor.getValue("adder").toString(), pDescriptor.getPropertyType().getComponentType());
            methodToSetObj.invoke(obj, attributeObject);
         } catch (NoSuchMethodException var4) {
            var4.printStackTrace();
         }
      } else {
         methodToSetObj = pDescriptor.getWriteMethod();
         if (methodToSetObj != null) {
            methodToSetObj.invoke(obj, attributeObject);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("There is no set method for the attribute" + pDescriptor.getName() + "on" + obj.toString());
         }
      }

   }

   private static void updateExistingArrayAttributeObject(PropertyDescriptor pDescriptor, JSONObject readAttJson, Object attributeObject, String attName, Map pojoToXmlTable, DomainMBean domain, Object obj) throws InvocationTargetException, IllegalAccessException, JSONException, NoSuchMethodException {
      Object objAttribute = null;

      for(int i = 0; i < Array.getLength(attributeObject); ++i) {
         objAttribute = Array.get(attributeObject, i);
         PropertyDescriptor[] var9 = beanInfoAccess.getBeanInfoForInstance(objAttribute, false, (String)null).getPropertyDescriptors();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            PropertyDescriptor pAttributePDesc = var9[var11];
            if (pAttributePDesc.getName().compareTo("Name") == 0) {
               String pAttributeValue = (String)pAttributePDesc.getReadMethod().invoke(objAttribute);
               JSONArray matchJsonArray = new JSONArray();
               if (findMatchingAttributesToChange(pAttributeValue, readAttJson.get((String)pojoToXmlTable.get(attName)), matchJsonArray)) {
                  setAttributesVal(objAttribute, matchJsonArray.getJSONObject(0));
                  break;
               }
            }
         }
      }

   }

   private static Object getSecurityRealm(DomainMBean domain, String fromJsonName) throws JSONException {
      Object retObject = null;
      RealmMBean[] var3 = domain.getSecurityConfiguration().getRealms();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         RealmMBean realm = var3[var5];
         if (realm.getName().compareTo(fromJsonName) == 0) {
            retObject = realm;
            break;
         }
      }

      if (retObject == null) {
         throw new IllegalArgumentException("Specified Realm in attributes.json \"" + fromJsonName + " \" not found in Domain's security configuration.");
      } else {
         return retObject;
      }
   }

   private static void setEmptyAttributeObject(PropertyDescriptor pDescriptor, JSONObject readAttJson, Object attributeObject, String attName, Map pojoToXmlTable, DomainMBean domain, Object obj) throws InvocationTargetException, IllegalAccessException, JSONException, NoSuchMethodException {
      Class partitionPropType = pDescriptor.getPropertyType();
      if (partitionPropType.isArray()) {
         partitionPropType = partitionPropType.getComponentType();
      }

      String attribXmlName = (String)pojoToXmlTable.get(attName);
      Object fromjsonProp = readAttJson.get(attribXmlName);
      String fromJsonName = getNameFromJsonObj(fromjsonProp);
      if (ImportExportHelper.getAllPartitionReferences(obj).contains(attName)) {
         if (fromJsonName.isEmpty()) {
            return;
         }

         if (attName.compareTo("Realm") == 0) {
            attributeObject = getSecurityRealm(domain, fromJsonName);
         } else {
            attributeObject = getReferenceAttribute(domain, partitionPropType, fromJsonName, attribXmlName);
         }

         if (attributeObject == null) {
            return;
         }

         invokeSetMethod(pDescriptor, attributeObject, obj);
      } else {
         createNewAttributes(pDescriptor, obj, fromjsonProp);
      }

   }

   private static boolean findMatchingAttributesToChange(String attrObjectName, Object attributeJsonObject, JSONArray matchJson) throws JSONException {
      String attrJsonName = null;
      if (attributeJsonObject instanceof JSONObject) {
         attrJsonName = ((JSONObject)attributeJsonObject).getString("name");
         if (attrObjectName.compareTo(attrJsonName) == 0) {
            matchJson.put(0, (JSONObject)attributeJsonObject);
            return true;
         }
      } else if (attributeJsonObject instanceof JSONArray) {
         for(int i = 0; i < ((JSONArray)attributeJsonObject).length(); ++i) {
            attrJsonName = ((JSONArray)attributeJsonObject).getJSONObject(i).getString("name");
            if (attrObjectName.compareTo(attrJsonName) == 0) {
               matchJson.put(0, ((JSONArray)attributeJsonObject).getJSONObject(i));
               return true;
            }
         }
      }

      return false;
   }

   private static void createNewAttributes(PropertyDescriptor propDesc, Object obj, Object fromjsonProp) throws JSONException {
      JSONArray jsonPropArray = new JSONArray();

      try {
         if (fromjsonProp instanceof JSONObject) {
            jsonPropArray.put(fromjsonProp);
         } else {
            if (!(fromjsonProp instanceof JSONArray)) {
               throw new IllegalArgumentException("fromjsonProp should either be a JSONObject or JSONArray");
            }

            jsonPropArray = (JSONArray)fromjsonProp;
         }

         Method methodToSetObj = propDesc.getWriteMethod();
         Class propertyType = propDesc.getPropertyType();
         if (propertyType.isArray()) {
            propertyType = propertyType.getComponentType();
         }

         for(int i = 0; i <= jsonPropArray.length(); ++i) {
            Object newObject = propertyType.newInstance();
            JSONObject propJsonObj = jsonPropArray.getJSONObject(i);
            setAttributesVal(newObject, propJsonObj);
            methodToSetObj.invoke(obj, newObject);
         }
      } catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | InstantiationException var9) {
         var9.printStackTrace();
      }

   }

   private static void setAttributesVal(Object attributeObject, JSONObject jsonPropObject) throws JSONException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Method setAttribute = attributeObject.getClass().getMethod("setAttribute", Attribute.class);
      if (jsonPropObject.length() > 0) {
         Map attribPojoToXMLTable = createXmlToPojoMap(attributeObject, jsonPropObject);
         PropertyDescriptor[] var4 = beanInfoAccess.getBeanInfoForInstance(attributeObject, false, (String)null).getPropertyDescriptors();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor attPropDescriptor = var4[var6];
            String attAttName = attPropDescriptor.getName();
            if (attribPojoToXMLTable.containsKey(attAttName)) {
               Object propVal = jsonPropObject.get((String)attribPojoToXMLTable.get(attAttName));
               Attribute newAttribute = getAttributesFromPropDescriptor(attPropDescriptor, attAttName, propVal);
               if (newAttribute != null) {
                  setAttribute.invoke(attributeObject, newAttribute);
               }
            }
         }

      }
   }

   private static void serialize(XMLStreamWriter xmlStreamWriter, JSONObject jsonToConvert) throws XMLStreamException, ManagementException, JSONException {
      XMLStreamReader reader = getReader(jsonToConvert);
      xmlStreamWriter.writeStartDocument();

      while(reader.hasNext()) {
         int x = reader.next();
         switch (x) {
            case 1:
               xmlStreamWriter.writeStartElement(reader.getPrefix(), reader.getLocalName(), reader.getNamespaceURI());
               int namespaceCount = reader.getNamespaceCount();

               int attributeCount;
               for(attributeCount = namespaceCount - 1; attributeCount >= 0; --attributeCount) {
                  xmlStreamWriter.writeNamespace(reader.getNamespacePrefix(attributeCount), reader.getNamespaceURI(attributeCount));
               }

               attributeCount = reader.getAttributeCount();

               for(int i = 0; i < attributeCount; ++i) {
                  xmlStreamWriter.writeAttribute(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i));
               }
               break;
            case 2:
               xmlStreamWriter.writeEndElement();
               break;
            case 3:
               xmlStreamWriter.writeProcessingInstruction(reader.getPITarget(), reader.getPIData());
               break;
            case 4:
               xmlStreamWriter.writeCharacters(reader.getText());
               break;
            case 5:
               xmlStreamWriter.writeComment(reader.getText());
            case 6:
            case 7:
               break;
            case 8:
               xmlStreamWriter.writeEndDocument();
               break;
            case 9:
               xmlStreamWriter.writeEntityRef(reader.getLocalName());
               break;
            case 10:
            default:
               throw new ManagementException("Cannot find the xml attribute");
            case 11:
               xmlStreamWriter.writeDTD(reader.getText());
               break;
            case 12:
               xmlStreamWriter.writeCData(reader.getText());
         }
      }

      xmlStreamWriter.writeEndDocument();
   }

   private static XMLStreamReader getReader(JSONObject jsonToConvert) throws XMLStreamException, JSONException {
      Map nsMap = new HashMap();
      MappedXMLInputFactory inputFactory = new MappedXMLInputFactory(nsMap);
      String jsonString = jsonToConvert.toString();
      return inputFactory.createXMLStreamReader(new JSONTokener(jsonString));
   }
}
