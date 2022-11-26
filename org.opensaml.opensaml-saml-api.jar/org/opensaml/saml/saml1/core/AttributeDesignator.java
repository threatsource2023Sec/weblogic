package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeDesignator extends SAMLObject {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeDesignator";
   @Nonnull
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator", "saml1");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "AttributeDesignatorType";
   @Nonnull
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignatorType", "saml1");
   @Nonnull
   @NotEmpty
   String ATTRIBUTENAME_ATTRIB_NAME = "AttributeName";
   @Nonnull
   @NotEmpty
   String ATTRIBUTENAMESPACE_ATTRIB_NAME = "AttributeNamespace";

   @Nullable
   String getAttributeName();

   void setAttributeName(@Nullable String var1);

   @Nullable
   String getAttributeNamespace();

   void setAttributeNamespace(@Nullable String var1);
}
