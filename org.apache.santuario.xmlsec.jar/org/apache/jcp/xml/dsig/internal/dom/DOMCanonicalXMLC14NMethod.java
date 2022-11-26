package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import javax.xml.crypto.Data;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;

public final class DOMCanonicalXMLC14NMethod extends ApacheCanonicalizer {
   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
      if (params != null) {
         throw new InvalidAlgorithmParameterException("no parameters should be specified for Canonical XML C14N algorithm");
      }
   }

   public Data transform(Data data, XMLCryptoContext xc) throws TransformException {
      if (data instanceof DOMSubTreeData) {
         DOMSubTreeData subTree = (DOMSubTreeData)data;
         if (subTree.excludeComments()) {
            try {
               this.apacheCanonicalizer = Canonicalizer.getInstance("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
               boolean secVal = Utils.secureValidation(xc);
               this.apacheCanonicalizer.setSecureValidation(secVal);
            } catch (InvalidCanonicalizerException var5) {
               throw new TransformException("Couldn't find Canonicalizer for: http://www.w3.org/TR/2001/REC-xml-c14n-20010315: " + var5.getMessage(), var5);
            }
         }
      }

      return this.canonicalize(data, xc);
   }
}
