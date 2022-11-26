package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.store.data.BEASAMLAssertingParty;
import com.bea.common.security.store.data.BEASAMLAssertingPartyId;
import com.bea.common.security.store.data.WLSCertRegEntry;
import com.bea.common.security.store.data.WLSCertRegEntryId;
import com.bea.common.store.bootstrap.LDIFTUtils;
import java.util.HashMap;
import java.util.Map;
import weblogic.security.providers.utils.GenericEntryConverter;

public class SAMLAssertingPartyEntryConverter extends GenericEntryConverter {
   public SAMLAssertingPartyEntryConverter(LoggerSpi logger, LegacyEncryptorSpi encryptor) {
      super(logger, encryptor);
   }

   public GenericEntryConverter.BusinessObjectInfo getBusinessObjectInfo(String dnString) {
      String[] fieldValues = this.determinePrimaryKeyFields(dnString);
      if (fieldValues.length != 4) {
         return null;
      } else {
         try {
            BEASAMLAssertingPartyId idObject = new BEASAMLAssertingPartyId(fieldValues[3], fieldValues[2], fieldValues[1], fieldValues[0]);
            this.logDebug("checking id object: " + idObject.toString());
            if (notEmpty(idObject.getCn()) && notEmpty(idObject.getDomainName()) && notEmpty(idObject.getRealmName()) && notEmpty(idObject.getRegistryName()) && idObject.getRegistryName().equalsIgnoreCase("SAMLAssertingPartyRegistry")) {
               return new GenericEntryConverter.BusinessObjectInfo(BEASAMLAssertingParty.class, BEASAMLAssertingPartyId.class, idObject);
            }

            this.logDebug("Not a SAMLAssertingPartyRegistry binding");
         } catch (Exception var5) {
            this.logDebug("Exception in checking dn attribute: " + dnString);
         }

         try {
            WLSCertRegEntryId idObject = new WLSCertRegEntryId(fieldValues[3], fieldValues[2], fieldValues[1], fieldValues[0]);
            if (notEmpty(idObject.getCn()) && notEmpty(idObject.getDomainName()) && notEmpty(idObject.getRealmName()) && notEmpty(idObject.getRegistryName()) && idObject.getRegistryName().equalsIgnoreCase("SAMLCertificateRegistry")) {
               return new GenericEntryConverter.BusinessObjectInfo(WLSCertRegEntry.class, WLSCertRegEntryId.class, idObject);
            }
         } catch (Exception var4) {
            this.logDebug("Exception in checking dn attribute: " + dnString);
         }

         return null;
      }
   }

   protected boolean isEncryptAttribute(String attributeName) {
      return attributeName.equalsIgnoreCase("beaSAMLAuthPassword");
   }

   protected Class determineIdObjectClass(Object businessObject) {
      if (businessObject instanceof BEASAMLAssertingParty) {
         return BEASAMLAssertingPartyId.class;
      } else {
         return businessObject instanceof WLSCertRegEntry ? WLSCertRegEntryId.class : null;
      }
   }

   public Map buildBaseAttributes(Object businessObjectIdObject) {
      HashMap baseAttributes = new HashMap();
      String dnString;
      String ouString;
      String[] objectClassStrings;
      if (businessObjectIdObject instanceof BEASAMLAssertingPartyId) {
         BEASAMLAssertingPartyId idObject = (BEASAMLAssertingPartyId)businessObjectIdObject;
         dnString = "cn=" + idObject.getCn() + ", ou=" + idObject.getRegistryName() + ", ou=" + idObject.getRealmName() + ", dc=" + idObject.getDomainName();
         ouString = idObject.getRegistryName();
         objectClassStrings = new String[]{"top", "beaSAMLAssertingParty", "beaSAMLPartner"};
         LDIFTUtils.buildBaseAttributes(baseAttributes, dnString, ouString, objectClassStrings);
      } else if (businessObjectIdObject instanceof WLSCertRegEntryId) {
         WLSCertRegEntryId idObject = (WLSCertRegEntryId)businessObjectIdObject;
         dnString = "cn=" + idObject.getCn() + ", ou=" + idObject.getRegistryName() + ", ou=" + idObject.getRealmName() + ", dc=" + idObject.getDomainName();
         ouString = idObject.getRegistryName();
         objectClassStrings = new String[]{"top"};
         LDIFTUtils.buildBaseAttributes(baseAttributes, dnString, ouString, objectClassStrings);
      }

      return baseAttributes;
   }
}
