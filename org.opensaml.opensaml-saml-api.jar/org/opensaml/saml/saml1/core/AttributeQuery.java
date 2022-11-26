package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface AttributeQuery extends SubjectQuery {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeQuery";
   @Nonnull
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery", "saml1p");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "AttributeQueryType";
   @Nonnull
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQueryType", "saml1p");
   @Nonnull
   @NotEmpty
   String RESOURCE_ATTRIB_NAME = "Resource";

   @Nonnull
   @NonnullElements
   List getAttributeDesignators();

   @Nullable
   String getResource();

   void setResource(@Nullable String var1);
}
