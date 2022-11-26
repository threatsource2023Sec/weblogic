package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

public final class DOMEnvelopedTransform extends ApacheTransform {
   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
      if (params != null) {
         throw new InvalidAlgorithmParameterException("params must be null");
      }
   }
}
