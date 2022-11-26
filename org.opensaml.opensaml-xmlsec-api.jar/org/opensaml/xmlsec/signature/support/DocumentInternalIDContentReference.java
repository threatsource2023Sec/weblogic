package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;

public class DocumentInternalIDContentReference extends URIContentReference {
   public DocumentInternalIDContentReference(@Nonnull String referenceID) {
      super("#" + referenceID);
   }
}
