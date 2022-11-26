package org.apache.jcp.xml.dsig.internal.dom;

import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import org.w3c.dom.Element;

public class DOMCanonicalizationMethod extends DOMTransform implements CanonicalizationMethod {
   public DOMCanonicalizationMethod(TransformService spi) throws InvalidAlgorithmParameterException {
      super(spi);
      if (!(spi instanceof ApacheCanonicalizer) && !isC14Nalg(spi.getAlgorithm())) {
         throw new InvalidAlgorithmParameterException("Illegal CanonicalizationMethod");
      }
   }

   public DOMCanonicalizationMethod(Element cmElem, XMLCryptoContext context, Provider provider) throws MarshalException {
      super(cmElem, context, provider);
      if (!(this.spi instanceof ApacheCanonicalizer) && !isC14Nalg(this.spi.getAlgorithm())) {
         throw new MarshalException("Illegal CanonicalizationMethod");
      }
   }

   public Data canonicalize(Data data, XMLCryptoContext xc) throws TransformException {
      return this.transform(data, xc);
   }

   public Data canonicalize(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
      return this.transform(data, xc, os);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CanonicalizationMethod)) {
         return false;
      } else {
         CanonicalizationMethod ocm = (CanonicalizationMethod)o;
         return this.getAlgorithm().equals(ocm.getAlgorithm()) && DOMUtils.paramsEqual(this.getParameterSpec(), ocm.getParameterSpec());
      }
   }

   public int hashCode() {
      int result = 17;
      result = 31 * result + this.getAlgorithm().hashCode();
      AlgorithmParameterSpec spec = this.getParameterSpec();
      if (spec != null) {
         result = 31 * result + spec.hashCode();
      }

      return result;
   }

   private static boolean isC14Nalg(String alg) {
      return isInclusiveC14Nalg(alg) || isExclusiveC14Nalg(alg) || isC14N11alg(alg);
   }

   private static boolean isInclusiveC14Nalg(String alg) {
      return alg.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") || alg.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
   }

   private static boolean isExclusiveC14Nalg(String alg) {
      return alg.equals("http://www.w3.org/2001/10/xml-exc-c14n#") || alg.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments");
   }

   private static boolean isC14N11alg(String alg) {
      return alg.equals("http://www.w3.org/2006/12/xml-c14n11") || alg.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments");
   }
}
