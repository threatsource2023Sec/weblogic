package org.apache.xml.security.transforms;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.xml.sax.SAXException;

public abstract class TransformSpi {
   protected boolean secureValidation;

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
      throw new UnsupportedOperationException();
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, Transform transformObject) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
      return this.enginePerformTransform(input, (OutputStream)null, transformObject);
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
      return this.enginePerformTransform(input, (Transform)null);
   }

   protected abstract String engineGetURI();
}
