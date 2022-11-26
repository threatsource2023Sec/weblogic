package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.Endpoint;
import com.bea.common.security.store.data.EndpointId;
import com.bea.common.store.bootstrap.LDIFTUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.security.providers.utils.GenericEntryConverter;

public class SAML2EndpointEntryConverter extends SAML2GenericEntryConverter {
   public SAML2EndpointEntryConverter(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      super(logger, encryptor);
   }

   protected String getAttributeName(String methodName) {
      String fieldName = super.getAttributeName(methodName);
      HashMap map = (HashMap)this.getPartnerMetaData().get(Endpoint.class.getName());
      return (String)map.get(fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1));
   }

   public String getMethodName(String attributeName) {
      HashMap map = (HashMap)this.getPartnerMetaData().get(Endpoint.class.getName());
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
      if (businessObjectIdObject instanceof EndpointId) {
         EndpointId idObject = (EndpointId)businessObjectIdObject;
         String dnString = "beaSAML2EndpointBindingLocation=" + idObject.getBindingLocation() + "+" + "beaSAML2Endpoint" + "BindingType=" + idObject.getBindingType() + "+" + "beaSAML2Endpoint" + "ServiceType=" + idObject.getServiceType() + "+" + "beaSAML2Partner" + "Name=" + idObject.getPartnerName() + ", ou=Endpoint, ou=" + idObject.getRealmName() + ", dc=" + idObject.getDomainName();
         String ouString = "";
         String[] objectClassStrings = new String[]{"top", "beaSAML2Endpoint"};
         LDIFTUtils.buildBaseAttributes(baseAttributes, dnString, ouString, objectClassStrings);
      }

      return baseAttributes;
   }

   protected Class determineIdObjectClass(Object businessObject) {
      return businessObject instanceof Endpoint ? EndpointId.class : null;
   }

   public String[] determinePrimaryKeyFields(String dnString) {
      if (dnString != null && !dnString.equals("")) {
         ArrayList list = new ArrayList();
         StringTokenizer st = new StringTokenizer(dnString, ",");
         boolean firstToken = true;

         while(true) {
            while(st.hasMoreElements()) {
               String token = st.nextToken();
               if (firstToken) {
                  StringTokenizer st2 = new StringTokenizer(token, "+");

                  while(st2.hasMoreElements()) {
                     String token2 = st2.nextToken();
                     if (!this.parseToken(list, token2)) {
                        throw new RuntimeException("Bad Fromat dn string: " + dnString);
                     }
                  }

                  firstToken = false;
               } else if (!this.inFilterList(token) && !this.parseToken(list, token)) {
                  throw new RuntimeException("Bad Fromat dn string: " + dnString);
               }
            }

            return (String[])((String[])list.toArray(new String[list.size()]));
         }
      } else {
         return new String[0];
      }
   }

   private boolean parseToken(ArrayList list, String token) {
      int indexOfEqual = token.indexOf("=");
      if (indexOfEqual == -1) {
         return false;
      } else {
         list.add(token.substring(indexOfEqual + 1));
         return true;
      }
   }

   protected GenericEntryConverter.BusinessObjectInfo getBusinessObjectInfo(String dnString) {
      String[] fieldValues = this.determinePrimaryKeyFields(dnString);
      if (fieldValues.length != 6) {
         return null;
      } else {
         try {
            EndpointId idObject = new EndpointId(fieldValues[5], fieldValues[4], fieldValues[0], fieldValues[1], fieldValues[2], fieldValues[3]);
            this.logDebug("checking id object: " + idObject.toString());
            if (notEmpty(idObject.getPartnerName()) && notEmpty(idObject.getBindingType()) && notEmpty(idObject.getBindingLocation()) && notEmpty(idObject.getServiceType()) && notEmpty(idObject.getDomainName()) && notEmpty(idObject.getRealmName())) {
               return new GenericEntryConverter.BusinessObjectInfo(Endpoint.class, EndpointId.class, idObject);
            }

            this.logDebug("Not an Endpoint binding");
         } catch (Exception var4) {
            this.logDebug("Exception in checking dn attribute: " + dnString);
         }

         return null;
      }
   }

   public boolean inFilterList(String token) {
      boolean result = false;
      if (token.trim().equals("ou=Endpoint")) {
         result = true;
      }

      return result;
   }

   protected boolean isEncryptAttribute(String attributeName) {
      return false;
   }

   public Endpoint constructBusinessObject(String dnString, Collection endpoints) {
      GenericEntryConverter.BusinessObjectInfo businessObjectInfo = this.getBusinessObjectInfo(dnString);
      if (businessObjectInfo == null) {
         this.logDebug("No business object info determined for : " + dnString);
         return null;
      } else {
         Endpoint businessObject = null;

         try {
            businessObject = (Endpoint)businessObjectInfo.getBusinessObjectClass().newInstance();
            this.reflectPrimaryKeyFields(businessObject, businessObjectInfo);
            Iterator iter = endpoints.iterator();

            Endpoint endpoint;
            do {
               if (!iter.hasNext()) {
                  return null;
               }

               endpoint = (Endpoint)iter.next();
            } while(!endpoint.equals(businessObject));

            return endpoint;
         } catch (Exception var7) {
            this.logDebug("reflection of business object and primary key fields failed: " + var7.getMessage());
            return null;
         }
      }
   }
}
