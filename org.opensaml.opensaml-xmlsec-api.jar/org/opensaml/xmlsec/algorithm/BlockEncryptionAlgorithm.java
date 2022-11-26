package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface BlockEncryptionAlgorithm extends KeySpecifiedAlgorithm, KeyLengthSpecifiedAlgorithm {
   @Nonnull
   @NotEmpty
   String getCipherMode();

   @Nonnull
   @NotEmpty
   String getPadding();
}
