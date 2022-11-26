package org.hibernate.validator.internal.xml.mapping;

class DefaultPackageStaxBuilder extends AbstractOneLineStringStaxBuilder {
   private static final String DEFAULT_PACKAGE_QNAME = "default-package";

   protected String getAcceptableQName() {
      return "default-package";
   }
}
