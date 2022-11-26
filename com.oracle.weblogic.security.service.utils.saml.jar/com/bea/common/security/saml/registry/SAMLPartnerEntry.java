package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.jdkutils.WeaverUtil.Boolean;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.saml.registry.SAMLPartner;

public abstract class SAMLPartnerEntry implements SAMLPartner, Serializable {
   private static final long serialVersionUID = 4487531081937668253L;
   private transient LoggerSpi log = null;
   private static final String[] BASE_OBJECT_CLASSES = new String[]{"top"};
   private static final String PARTNER_ATTR_ID = "cn";
   private static final String PARTNER_ATTR_ENABLED = "beaSAMLPartnerEnabled";
   private static final String PARTNER_ATTR_DESC = "beaSAMLPartnerDescription";
   private static final String[] BASE_ATTRIBUTES = new String[]{"cn", "beaSAMLPartnerEnabled", "beaSAMLPartnerDescription"};
   private static final int PARTNER_ID_PADDING_LENGTH = 5;
   protected transient LegacyEncryptorSpi encryptionService = null;
   private HashMap attrMap = new HashMap();
   private boolean isV1Config = false;

   protected boolean isDebugEnabled() {
      return this.log != null ? this.log.isDebugEnabled() : false;
   }

   protected void logDebug(String method, String msg) {
      if (this.log != null && this.log.isDebugEnabled()) {
         this.log.debug("SAMLPartnerEntry: " + method + ": " + msg);
      }

   }

   protected SAMLPartnerEntry(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      this.log = logger;
      this.encryptionService = encryptor;
      this.setEnabled(false);
   }

   protected static String[] getBaseLDAPObjectClasses() {
      return BASE_OBJECT_CLASSES;
   }

   protected static String[] getBaseLDAPAttributes() {
      return BASE_ATTRIBUTES;
   }

   protected static String getPartnerIdAttribute() {
      return "cn";
   }

   protected static String getPartnerEnabledAttribute() {
      return "beaSAMLPartnerEnabled";
   }

   protected static String makeNewPartnerId(String prefix, int num) {
      String numString;
      for(numString = Integer.toString(num); numString.length() < 5; numString = "0" + numString) {
      }

      return prefix + "_" + numString;
   }

   private void clearMap() {
      this.attrMap.clear();
   }

   private void setMap(Map map) {
      this.attrMap.putAll(map);
   }

   public void setAttributesFromMap(Map map) {
      this.setMap(map);
   }

   protected void setAttributesFromBusinessObject(Object businessObject) {
      Class businessObjectClass = businessObject.getClass();
      Method[] methods = businessObjectClass.getMethods();

      label61:
      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         String methodName = method.getName();
         if (methodName.startsWith("get") && !methodName.equalsIgnoreCase("getRealmName") && !methodName.equalsIgnoreCase("getDomainName")) {
            Object returnObject = null;

            try {
               returnObject = method.invoke(businessObject);
            } catch (Exception var12) {
            }

            this.logDebug("setAttributesFromBusinessObject", "method " + methodName + "returns object: " + returnObject);
            if (returnObject != null) {
               String attributeName = methodName.substring(3);
               attributeName = attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1);
               ArrayList attributeValues = new ArrayList();
               if (returnObject instanceof String) {
                  attributeValues.add(returnObject);
               } else if (returnObject instanceof Collection) {
                  attributeValues.addAll((Collection)returnObject);
               }

               Iterator it = attributeValues.iterator();

               while(true) {
                  String value;
                  do {
                     if (!it.hasNext()) {
                        if (attributeValues.size() > 0) {
                           String[] values = (String[])((String[])attributeValues.toArray(new String[0]));
                           this.setMultiValuedAttribute(attributeName, values);
                        }
                        continue label61;
                     }

                     value = (String)it.next();
                  } while(value != null && value.trim().length() != 0);

                  it.remove();
               }
            }
         }
      }

   }

   protected Object constructBusinessObject(Class businessObjectClass, String partnerId, String registryName, String realmName, String domainName) throws Exception {
      Object businessObject = null;
      businessObject = businessObjectClass.newInstance();
      return this.constructBusinessObject(businessObjectClass, businessObject, partnerId, registryName, realmName, domainName);
   }

   protected Object constructBusinessObject(Class businessObjectClass, Object businessObject, String partnerId, String registryName, String realmName, String domainName) throws Exception {
      Method[] methods = businessObjectClass.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         Method method = methods[i];
         String methodName = method.getName();
         if (methodName.startsWith("set")) {
            String attributeName = methodName.substring(3);
            attributeName = attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1);
            Class[] parameterTypes = method.getParameterTypes();
            if (attributeName.equalsIgnoreCase("cn")) {
               method.invoke(businessObject, partnerId);
            } else if (attributeName.equalsIgnoreCase("realmName")) {
               method.invoke(businessObject, realmName);
            } else if (attributeName.equalsIgnoreCase("domainName")) {
               method.invoke(businessObject, domainName);
            } else if (attributeName.equalsIgnoreCase("registryName")) {
               method.invoke(businessObject, registryName);
            } else if (parameterTypes[0].isAssignableFrom(String.class)) {
               String attributeValue = this.getAttribute(attributeName);
               if (attributeValue == null) {
                  attributeValue = "";
               }

               method.invoke(businessObject, attributeValue);
            } else {
               String[] attributeValues = this.getMultiValuedAttribute(attributeName);
               ArrayList list = new ArrayList();
               if (attributeValues != null && attributeValues.length > 0) {
                  for(int j = 0; j < attributeValues.length; ++j) {
                     list.add(attributeValues[j]);
                  }
               }

               method.invoke(businessObject, list);
            }
         }
      }

      return businessObject;
   }

   protected boolean containsAttribute(String attr) {
      return attr == null ? false : this.attrMap.containsKey(attr);
   }

   protected String getAttribute(String attr) {
      String attributeValue = null;
      if (attr != null) {
         Object value = this.attrMap.get(attr);
         if (value != null) {
            if (value instanceof String) {
               attributeValue = (String)value;
            } else {
               attributeValue = ((String[])((String[])value))[0];
            }
         }
      }

      return attributeValue != null && attributeValue.length() != 0 ? attributeValue : null;
   }

   protected void setAttribute(String attr, String value) {
      if (attr != null) {
         this.attrMap.put(attr, value == null ? null : value.trim());
      }

   }

   protected String[] getMultiValuedAttribute(String attr) {
      if (attr != null) {
         Object value = this.attrMap.get(attr);
         if (value != null) {
            if (value instanceof String[]) {
               return (String[])((String[])value);
            }

            return new String[]{(String)value};
         }
      }

      return null;
   }

   protected void setMultiValuedAttribute(String attr, String[] values) {
      if (attr != null) {
         for(int i = 0; values != null && i < values.length; ++i) {
            if (values[i] != null) {
               values[i] = values[i].trim();
            }
         }

         this.attrMap.put(attr, values);
      }

   }

   protected int getIntegerAttribute(String attr) {
      String value = this.getAttribute(attr);
      if (value == null) {
         return 0;
      } else {
         try {
            return Integer.parseInt(value);
         } catch (NumberFormatException var4) {
            return 0;
         }
      }
   }

   protected void setIntegerAttribute(String attr, int value) {
      this.setAttribute(attr, Integer.toString(value));
   }

   protected boolean getBooleanAttribute(String attr) {
      String value = this.getAttribute(attr);
      return value == null ? false : Boolean.parseBoolean(value);
   }

   protected void setBooleanAttribute(String attr, boolean value) {
      this.setAttribute(attr, java.lang.Boolean.toString(value));
   }

   public void setEncryptionService(LegacyEncryptorSpi encryption) {
      this.encryptionService = encryption;
   }

   protected String getEncryptedAttribute(String attr) {
      String method = "getEncryptedAttribute";
      String value = this.getAttribute(attr);
      if (value != null) {
         try {
            return this.encryptionService.decryptString(value);
         } catch (BadPaddingException var5) {
            this.logDebug(method, "Encryption exception: " + var5.getMessage());
         } catch (IllegalBlockSizeException var6) {
            this.logDebug(method, "Encryption exception: " + var6.getMessage());
         }
      }

      return null;
   }

   protected void setEncryptedAttribute(String attr, String value) {
      String method = "setEncryptedAttribute";
      if (value != null) {
         try {
            String encryptedValue = null;
            if (this.encryptionService != null) {
               encryptedValue = this.encryptionService.encryptString(value);
            } else {
               encryptedValue = value;
            }

            this.setAttribute(attr, encryptedValue);
         } catch (BadPaddingException var5) {
            this.logDebug(method, "Encryption exception: " + var5.getMessage());
         } catch (IllegalBlockSizeException var6) {
            this.logDebug(method, "Encryption exception: " + var6.getMessage());
         }
      } else {
         this.setAttribute(attr, (String)null);
      }

   }

   protected boolean isValueEncrypted(String value) {
      return this.encryptionService == null ? false : this.encryptionService.isEncrypted(value);
   }

   protected String decrypt(String value) {
      String method = "decrypt";

      try {
         if (this.encryptionService != null) {
            return this.encryptionService.decryptString(value);
         }

         return null;
      } catch (BadPaddingException var4) {
         this.logDebug(method, "Encryption exception: " + var4.getMessage());
      } catch (IllegalBlockSizeException var5) {
         this.logDebug(method, "Encryption exception: " + var5.getMessage());
      }

      return null;
   }

   public String getPartnerId() {
      return this.getAttribute("cn");
   }

   public boolean isEnabled() {
      return this.getBooleanAttribute("beaSAMLPartnerEnabled");
   }

   public void setEnabled(boolean enabled) {
      this.setBooleanAttribute("beaSAMLPartnerEnabled", enabled);
   }

   public String getDescription() {
      return this.getAttribute("beaSAMLPartnerDescription");
   }

   public void setDescription(String desc) {
      this.setAttribute("beaSAMLPartnerDescription", desc);
   }

   protected boolean isV1Config() {
      return this.isV1Config;
   }

   public void setV1Config(boolean isV1Config) {
      this.isV1Config = isV1Config;
   }

   public void validate() throws InvalidParameterException {
   }

   protected void handleEncryption(boolean isInbound) {
   }

   public void construct() throws InvalidParameterException {
      String partnerId = this.getPartnerId();
      if (partnerId == null || partnerId.length() == 0) {
         throw new InvalidParameterException("No Partner ID");
      }
   }

   protected boolean notNull(String s) {
      return s != null && s.length() > 0;
   }

   protected Element getAttributesAsDOMElement(Document dom, String partnerElementStr, String[] attributeNames, boolean ctPasswords) throws DOMException {
      Element partnerElement = dom.createElement(partnerElementStr);
      String partnerId = this.getAttribute("cn");
      partnerElement.setAttribute("Id", partnerId);
      String profile = this.getAttribute("beaSAMLProfile");
      partnerElement.setAttribute("Profile", profile);
      String enabled = this.getAttribute("beaSAMLPartnerEnabled");
      partnerElement.setAttribute("Enabled", enabled);
      String description = this.getAttribute("beaSAMLPartnerDescription");
      Element descriptionElement = SAMLXMLUtil.generateNormalElement(dom, "urn:bea:security:saml:1.1:partner-registry", "spr:Description", description);
      partnerElement.appendChild(descriptionElement);

      for(int i = 0; i < attributeNames.length; ++i) {
         PartnerAttributeSchemaInfo attributeInfo = new PartnerAttributeSchemaInfo(attributeNames[i]);
         if (attributeInfo.processThisAttribute()) {
            Element element = null;
            if (attributeInfo.singleValued()) {
               String value = null;
               if (attributeInfo.isEncrypted()) {
                  value = this.getAttribute(attributeNames[i]);
                  if (ctPasswords) {
                     if (this.isValueEncrypted(value)) {
                        value = this.decrypt(value);
                     }
                  } else if (!this.isValueEncrypted(value)) {
                     try {
                        if (this.encryptionService != null) {
                           value = this.encryptionService.encryptString(value);
                        }
                     } catch (Exception var16) {
                        this.logDebug("getAttributesAsDOMElement", "Encryption exception: " + var16.getMessage());
                        value = null;
                     }
                  }
               } else {
                  value = this.getAttribute(attributeNames[i]);
               }

               element = SAMLXMLUtil.generateNormalElement(dom, "urn:bea:security:saml:1.1:partner-registry", "spr:" + attributeInfo.getXMLTagName(), value);
            } else {
               element = SAMLXMLUtil.generateTwoLevelElement(dom, "urn:bea:security:saml:1.1:partner-registry", "spr:" + attributeInfo.getXMLTagName(), "spr:" + attributeInfo.getXMLSubTagName(), this.getMultiValuedAttribute(attributeNames[i]));
            }

            partnerElement.appendChild(element);
         }
      }

      return partnerElement;
   }

   protected void setAttributesFromDOMElement(Element partnerElement, String[] attributeNames) throws InvalidParameterException {
      this.setAttribute("cn", SAMLXMLUtil.getPartnerId(partnerElement));
      this.setAttribute("beaSAMLProfile", partnerElement.getAttribute("Profile"));
      this.setAttribute("beaSAMLPartnerEnabled", partnerElement.getAttribute("Enabled"));
      Element descriptionElement = SAMLXMLUtil.getChildElement(partnerElement, "Description");
      if (descriptionElement != null) {
         String description = SAMLXMLUtil.getSingleValueFromElement(descriptionElement);
         if (description != null) {
            this.setAttribute("beaSAMLPartnerDescription", description);
         }
      }

      for(int i = 0; i < attributeNames.length; ++i) {
         PartnerAttributeSchemaInfo attributeInfo = new PartnerAttributeSchemaInfo(attributeNames[i]);
         if (attributeInfo.processThisAttribute()) {
            Element subElement = SAMLXMLUtil.getChildElement(partnerElement, attributeInfo.getXMLTagName());
            if (subElement != null) {
               if (attributeInfo.singleValued()) {
                  String value = SAMLXMLUtil.getSingleValueFromElement(subElement);
                  if (value != null) {
                     if (attributeInfo.isEncrypted()) {
                        try {
                           if (this.isValueEncrypted(value)) {
                              if (this.encryptionService != null) {
                                 this.encryptionService.decryptString(value);
                              }
                           } else {
                              try {
                                 this.setEncryptedAttribute(attributeNames[i], value);
                              } catch (Exception var9) {
                                 this.setAttribute(attributeNames[i], (String)null);
                              }
                           }
                        } catch (Exception var10) {
                           throw new InvalidParameterException(ProvidersLogger.getCouldNotDecryptPassword(value));
                        }
                     } else {
                        this.setAttribute(attributeNames[i], value);
                     }
                  }
               } else {
                  String[] values = SAMLXMLUtil.getMultiValuesFromElement(subElement);
                  if (values != null) {
                     this.setMultiValuedAttribute(attributeNames[i], values);
                  }
               }
            }
         }
      }

   }

   private static class PartnerAttributeSchemaInfo {
      private boolean processThisAttribute = true;
      private boolean singleValued = true;
      private boolean isEncrypted = false;
      private String xmlTagName;
      private String xmlSubTagName;
      private static final String ATTR_NAME_PREFIX = "beaSAML";

      public PartnerAttributeSchemaInfo(String attributeName) {
         if (!attributeName.equals("cn") && !attributeName.equals("beaSAMLProfile") && !attributeName.equals("beaSAMLPartnerEnabled") && !attributeName.equals("beaSAMLPartnerDescription")) {
            if (attributeName.equals("beaSAMLAudienceURI")) {
               this.singleValued = false;
               this.xmlTagName = "AudienceURIs";
               this.xmlSubTagName = "AudienceURI";
            } else if (!attributeName.equals("beaSAMLRedirectURIs") && !attributeName.equals("beaSAMLIntersiteTransferParams") && !attributeName.equals("beaSAMLAssertionConsumerParams")) {
               if (attributeName.equals("beaSAMLAuthPassword")) {
                  this.isEncrypted = true;
               }

               this.xmlTagName = attributeName.substring("beaSAML".length());
            } else {
               this.singleValued = false;
               this.xmlTagName = attributeName.substring("beaSAML".length());
               this.xmlSubTagName = this.xmlTagName.substring(0, this.xmlTagName.length() - 1);
            }

         } else {
            this.processThisAttribute = false;
         }
      }

      public boolean processThisAttribute() {
         return this.processThisAttribute;
      }

      public boolean singleValued() {
         return this.singleValued;
      }

      public String getXMLTagName() {
         return this.xmlTagName;
      }

      public String getXMLSubTagName() {
         return this.xmlSubTagName;
      }

      public boolean isEncrypted() {
         return this.isEncrypted;
      }
   }
}
