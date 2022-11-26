package org.apache.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMXSLTTransform extends ApacheTransform {
   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
      if (params == null) {
         throw new InvalidAlgorithmParameterException("params are required");
      } else if (!(params instanceof XSLTTransformParameterSpec)) {
         throw new InvalidAlgorithmParameterException("unrecognized params");
      } else {
         this.params = params;
      }
   }

   public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
      super.init(parent, context);
      this.unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
   }

   private void unmarshalParams(Element sheet) {
      this.params = new XSLTTransformParameterSpec(new javax.xml.crypto.dom.DOMStructure(sheet));
   }

   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
      super.marshalParams(parent, context);
      XSLTTransformParameterSpec xp = (XSLTTransformParameterSpec)this.getParameterSpec();
      Node xsltElem = ((javax.xml.crypto.dom.DOMStructure)xp.getStylesheet()).getNode();
      DOMUtils.appendChild(this.transformElem, xsltElem);
   }
}
