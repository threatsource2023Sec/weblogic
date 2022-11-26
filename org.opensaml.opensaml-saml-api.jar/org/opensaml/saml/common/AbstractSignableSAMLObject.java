package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.xmlsec.signature.AbstractSignableXMLObject;
import org.opensaml.xmlsec.signature.Signature;

public abstract class AbstractSignableSAMLObject extends AbstractSignableXMLObject implements SignableSAMLObject {
   protected AbstractSignableSAMLObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public final boolean equals(Object obj) {
      return obj == this ? true : super.equals(obj);
   }

   public int hashCode() {
      return super.hashCode();
   }

   public void setSignature(@Nullable Signature newSignature) {
      if (newSignature != null && newSignature.getContentReferences().isEmpty()) {
         newSignature.getContentReferences().add(new SAMLObjectContentReference(this));
      }

      super.setSignature(newSignature);
   }

   @Nullable
   protected DateTime prepareForAssignment(@Nullable DateTime oldValue, @Nullable DateTime newValue) {
      DateTime utcValue = null;
      if (newValue != null) {
         utcValue = newValue.withZone(DateTimeZone.UTC);
      }

      return (DateTime)super.prepareForAssignment(oldValue, utcValue);
   }
}
