package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;

public interface KeyLengthSpecifiedAlgorithm extends AlgorithmDescriptor {
   @Nonnull
   Integer getKeyLength();
}
