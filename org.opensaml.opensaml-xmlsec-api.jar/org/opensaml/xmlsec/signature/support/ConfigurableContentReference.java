package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nullable;

public interface ConfigurableContentReference extends ContentReference {
   @Nullable
   String getDigestAlgorithm();

   void setDigestAlgorithm(@Nullable String var1);
}
