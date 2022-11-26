package com.bea.common.security.saml2.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PartnerImportExportHelperFactory {
   public static PartnerImportExportHelperInt getInstance(SAML2SecurityHelperInt helper) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
      if (helper == null) {
         return null;
      } else {
         Class shClass = Class.forName("com.bea.security.saml2.providers.PartnerImportExportHelper", true, helper.getClass().getClassLoader());
         Constructor ctor = shClass.getConstructor(SAML2SecurityHelperInt.class);
         return (PartnerImportExportHelperInt)ctor.newInstance(helper);
      }
   }
}
