package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.core.xml.AbstractXMLObject;

public abstract class AbstractSAMLObject extends AbstractXMLObject {
   protected AbstractSAMLObject(@Nullable String namespaceURI, @Nonnull String elementLocalName, @Nullable String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public final boolean equals(Object obj) {
      return obj == this ? true : super.equals(obj);
   }

   public int hashCode() {
      return super.hashCode();
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
