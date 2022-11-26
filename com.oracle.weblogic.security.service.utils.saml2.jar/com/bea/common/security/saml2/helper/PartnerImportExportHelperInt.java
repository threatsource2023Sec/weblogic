package com.bea.common.security.saml2.helper;

import com.bea.security.saml2.providers.registry.Partner;
import java.util.Properties;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public interface PartnerImportExportHelperInt {
   String PARTNER_CONSTRAINT_NAME = "Partner";
   String PARTNER_CONSTRAINT_TYPE_ALL = "all";
   String PARTNER_CONSTRAINT_TYPE_ENABLED = "enabled";
   String PARTNER_CONSTRAINT_TYPE_DISABLED = "disabled";
   String CONSTRAINT_PASSWORD = "Passwords";
   String CONSTRAINT_PASSWORD_CLEARTEXT = "cleartext";
   String FORMAT_SAML2 = "SAML2";
   String FORMAT_LDIFTEMPLATE = "LDIF Template";

   void importData(String var1, String var2, Properties var3, boolean var4) throws InvalidParameterException, ErrorCollectionException;

   void exportData(String var1, String var2, Properties var3, boolean var4) throws InvalidParameterException, ErrorCollectionException;

   void setPartners(Partner[] var1);

   void clear();

   void addPartners(Partner[] var1);
}
