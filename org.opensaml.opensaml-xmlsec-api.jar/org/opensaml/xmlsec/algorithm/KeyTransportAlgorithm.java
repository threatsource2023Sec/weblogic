package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface KeyTransportAlgorithm extends KeySpecifiedAlgorithm {
   @Nonnull
   @NotEmpty
   String getCipherMode();

   @Nonnull
   @NotEmpty
   String getPadding();
}
