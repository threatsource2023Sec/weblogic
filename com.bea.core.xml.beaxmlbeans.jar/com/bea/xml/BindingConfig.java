package com.bea.xml;

import javax.xml.namespace.QName;

public class BindingConfig {
   private static final InterfaceExtension[] EMPTY_INTERFACE_EXT_ARRAY = new InterfaceExtension[0];
   private static final PrePostExtension[] EMPTY_PREPOST_EXT_ARRAY = new PrePostExtension[0];
   private static final UserType[] EMPTY_USER_TYPE_ARRY = new UserType[0];
   public static final int QNAME_TYPE = 1;
   public static final int QNAME_DOCUMENT_TYPE = 2;
   public static final int QNAME_ACCESSOR_ELEMENT = 3;
   public static final int QNAME_ACCESSOR_ATTRIBUTE = 4;

   public String lookupPackageForNamespace(String uri) {
      return null;
   }

   public String lookupPrefixForNamespace(String uri) {
      return null;
   }

   public String lookupSuffixForNamespace(String uri) {
      return null;
   }

   /** @deprecated */
   public String lookupJavanameForQName(QName qname) {
      return null;
   }

   public String lookupJavanameForQName(QName qname, int kind) {
      return null;
   }

   public InterfaceExtension[] getInterfaceExtensions() {
      return EMPTY_INTERFACE_EXT_ARRAY;
   }

   public InterfaceExtension[] getInterfaceExtensions(String fullJavaName) {
      return EMPTY_INTERFACE_EXT_ARRAY;
   }

   public PrePostExtension[] getPrePostExtensions() {
      return EMPTY_PREPOST_EXT_ARRAY;
   }

   public PrePostExtension getPrePostExtension(String fullJavaName) {
      return null;
   }

   public UserType[] getUserTypes() {
      return EMPTY_USER_TYPE_ARRY;
   }

   public UserType lookupUserTypeForQName(QName qname) {
      return null;
   }
}
