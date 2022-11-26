package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.apache.xml.security.signature.XMLSignature;

public interface ContentReference {
   void createReference(@Nonnull XMLSignature var1);
}
