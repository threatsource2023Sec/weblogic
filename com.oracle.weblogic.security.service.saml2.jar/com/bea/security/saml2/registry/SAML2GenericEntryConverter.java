package com.bea.security.saml2.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.store.bootstrap.Entry;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import weblogic.security.providers.utils.GenericEntryConverter;

public abstract class SAML2GenericEntryConverter extends GenericEntryConverter {
   public static final String BEA_SAML2_ENDPOINT = "beaSAML2Endpoint";
   public static final String BEA_SAML2_PARTNER = "beaSAML2Partner";
   private HashMap partnerMetaData;

   public SAML2GenericEntryConverter(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      super(logger, encryptor);
   }

   public HashMap getPartnerMetaData() {
      return this.partnerMetaData;
   }

   public void setPartnerMetaData(HashMap partnerMetaData) {
      this.partnerMetaData = partnerMetaData;
   }

   protected void addAttribute(String attributeName, Object attributeValueObject, Map attributes, Map binaryAttributes) {
      ArrayList attributeValues = new ArrayList();
      if (attributeValueObject instanceof Boolean) {
         Boolean booleanValue = (Boolean)attributeValueObject;
         attributeValues.add(booleanValue.toString());
         attributes.put(attributeName, attributeValues);
      } else if (attributeValueObject instanceof Integer) {
         Integer integerValue = (Integer)attributeValueObject;
         attributeValues.add(integerValue.toString());
         attributes.put(attributeName, attributeValues);
      } else {
         super.addAttribute(attributeName, attributeValueObject, attributes, binaryAttributes);
      }

   }

   public Object[] determineParameterObjects(Method method, String attributeName, Collection attrValues) {
      if (attrValues == null) {
         return null;
      } else {
         Class[] parameterTypes = method.getParameterTypes();
         Object[] attributes = attrValues.toArray();
         Object[] params;
         if (parameterTypes[0].isAssignableFrom(Integer.TYPE)) {
            Integer param = new Integer((String)attributes[0]);
            params = new Object[]{param};
            return params;
         } else if (parameterTypes[0].isAssignableFrom(Boolean.TYPE)) {
            Boolean param = new Boolean((String)attributes[0]);
            params = new Object[]{param};
            return params;
         } else {
            return super.determineParameterObjects(method, attributeName, attrValues);
         }
      }
   }

   protected boolean isExcluded(String methodName) {
      boolean result = false;
      result = super.isExcluded(methodName);
      if (!result && (methodName.equals("getTypedServices") || methodName.equals("getArtifactResolutionServices") || methodName.equals("getSingleSignOnServices") || methodName.equals("getAssertionConsumerServices"))) {
         result = true;
      }

      return result;
   }

   protected boolean isEndpointEntry(Entry ldifEntry) {
      Map attributes = ldifEntry.getAttributes();
      String dnAttribute = getDNAttribute(attributes);
      if (dnAttribute != null && dnAttribute.contains("beaSAML2EndpointBindingLocation")) {
         this.logDebug("This is an Endpoint entry: " + dnAttribute);
         return true;
      } else {
         return false;
      }
   }
}
