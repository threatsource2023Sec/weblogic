package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface Action extends SAMLObject {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "Action";
   @Nullable
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Action", "saml1");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "ActionType";
   @Nullable
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ActionType", "saml1");
   @Nonnull
   @NotEmpty
   String NAMESPACE_ATTRIB_NAME = "Namespace";

   @Nullable
   String getNamespace();

   void setNamespace(@Nullable String var1);

   @Nullable
   String getContents();

   void setContents(@Nullable String var1);
}
