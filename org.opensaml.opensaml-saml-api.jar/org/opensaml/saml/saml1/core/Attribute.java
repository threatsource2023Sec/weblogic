package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface Attribute extends AttributeDesignator {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
   @Nonnull
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute", "saml1");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "AttributeType";
   @Nonnull
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeType", "saml1");

   @Nonnull
   @NonnullElements
   List getAttributeValues();
}
