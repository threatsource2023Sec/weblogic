package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.Endpoint;
import com.bea.common.security.store.data.EndpointId;
import com.bea.common.security.store.data.SPPartner;
import com.bea.common.security.store.data.SPPartnerId;
import com.bea.common.store.bootstrap.Entry;
import com.bea.common.store.bootstrap.LDIFTUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.security.providers.utils.GenericEntryConverter;

public class SAML2SPPartnerEntryConverter extends SAML2GenericEntryConverter {
   private SAML2EndpointEntryConverter endpointEntryConverter;
   private Collection endpoints = new ArrayList();

   public SAML2SPPartnerEntryConverter(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      super(logger, encryptor);
   }

   protected String getAttributeName(String methodName) {
      String fieldName = super.getAttributeName(methodName);
      HashMap map = (HashMap)this.getPartnerMetaData().get(SPPartner.class.getName());
      return (String)map.get(fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1));
   }

   public String getMethodName(String attributeName) {
      HashMap map = (HashMap)this.getPartnerMetaData().get(SPPartner.class.getName());
      Set set = map.entrySet();
      Iterator iter = set.iterator();

      Map.Entry entry;
      String key;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         entry = (Map.Entry)iter.next();
         key = (String)entry.getKey();
      } while(key == null || !key.equalsIgnoreCase(attributeName));

      return (String)entry.getValue();
   }

   public Map buildBaseAttributes(Object businessObjectIdObject) {
      HashMap baseAttributes = new HashMap();
      SPPartnerId idObject = (SPPartnerId)businessObjectIdObject;
      String dnString = "beaSAML2PartnerName=" + idObject.getName() + ", ou=SPPartner, ou=" + idObject.getRealmName() + ", dc=" + idObject.getDomainName();
      String ouString = "";
      String[] objectClassStrings = new String[]{"top", "beaSAMLRelyingParty", "beaSAMLPartner"};
      LDIFTUtils.buildBaseAttributes(baseAttributes, dnString, ouString, objectClassStrings);
      return baseAttributes;
   }

   protected void addAttribute(String attributeName, Object attributeValueObject, Map attributes, Map binaryAttributes) {
      if (attributeValueObject instanceof Collection) {
         Collection attributeValueCollection = (Collection)attributeValueObject;
         Object[] objects = attributeValueCollection.toArray();
         if (objects.length > 0 && objects[0] instanceof Endpoint) {
            Collection endPointCollection = new ArrayList();
            Iterator iter = attributeValueCollection.iterator();

            while(iter.hasNext()) {
               Endpoint endPoint = (Endpoint)iter.next();
               Map map = this.endpointEntryConverter.buildBaseAttributes(endPoint, EndpointId.class);
               ArrayList dn = (ArrayList)map.get("dn");
               endPointCollection.add((String)dn.get(0));
            }

            attributes.put(attributeName, endPointCollection);
            return;
         }
      }

      super.addAttribute(attributeName, attributeValueObject, attributes, binaryAttributes);
   }

   public Object convert(Entry ldifEntry) {
      if (this.isEndpointEntry(ldifEntry)) {
         Object businessObject = this.endpointEntryConverter.convert(ldifEntry);
         if (businessObject != null) {
            this.LOGGER.debug("Converted endpoint successfully: " + ldifEntry);
            this.endpoints.add(businessObject);
         }

         return businessObject;
      } else {
         return super.convert(ldifEntry);
      }
   }

   protected Class determineIdObjectClass(Object businessObject) {
      return businessObject instanceof SPPartner ? SPPartnerId.class : null;
   }

   protected GenericEntryConverter.BusinessObjectInfo getBusinessObjectInfo(String dnString) {
      String[] fieldValues = this.determinePrimaryKeyFields(dnString);
      if (fieldValues.length != 3) {
         return null;
      } else {
         try {
            SPPartnerId idObject = new SPPartnerId(fieldValues[2], fieldValues[1], fieldValues[0]);
            this.logDebug("checking id object: " + idObject.toString());
            if (notEmpty(idObject.getName()) && notEmpty(idObject.getDomainName()) && notEmpty(idObject.getRealmName())) {
               return new GenericEntryConverter.BusinessObjectInfo(SPPartner.class, SPPartnerId.class, idObject);
            }

            this.logDebug("Not a SPPartner binding");
         } catch (Exception var4) {
            this.logDebug("Exception in checking dn attribute: " + dnString);
         }

         return null;
      }
   }

   public Object[] determineParameterObjects(Method method, String attributeName, Collection attrValues) {
      if (attrValues == null) {
         return null;
      } else if (method.getName().equals("setServices")) {
         Collection endpoints = new ArrayList();
         Object[] objects = attrValues.toArray();

         for(int i = 0; i < objects.length; ++i) {
            String dnString = (String)objects[i];
            Endpoint endpoint = this.endpointEntryConverter.constructBusinessObject(dnString, this.getEndpoints());
            if (endpoint != null) {
               endpoints.add(endpoint);
            }
         }

         Object[] params = new Object[]{endpoints};
         return params;
      } else {
         return super.determineParameterObjects(method, attributeName, attrValues);
      }
   }

   public boolean inFilterList(String token) {
      boolean result = false;
      if (token.trim().equals("ou=SPPartner")) {
         result = true;
      }

      return result;
   }

   protected boolean isEncryptAttribute(String attributeName) {
      return attributeName.equalsIgnoreCase("beaSAML2PartnerClientPasswordEncrypt");
   }

   public Collection getEndpoints() {
      return this.endpoints;
   }

   public void setEndpoints(Collection endpoints) {
      this.endpoints = endpoints;
   }

   public SAML2EndpointEntryConverter getSAML2EndpointEntryConverter() {
      return this.endpointEntryConverter;
   }

   public void setSAML2EndpointEntryConverter(SAML2EndpointEntryConverter endpointEntryConverter) {
      this.endpointEntryConverter = endpointEntryConverter;
   }
}
