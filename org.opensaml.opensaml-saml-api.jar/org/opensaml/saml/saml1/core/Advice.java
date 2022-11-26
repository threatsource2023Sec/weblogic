package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Advice extends SAMLObject, ElementExtensibleXMLObject {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "Advice";
   @Nonnull
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Advice", "saml1");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "AdviceType";
   @Nonnull
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AdviceType", "saml1");

   @Nonnull
   @NonnullElements
   List getAssertionIDReferences();

   @Nonnull
   @NonnullElements
   List getAssertions();
}
