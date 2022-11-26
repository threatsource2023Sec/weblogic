package org.apache.xml.security.transforms.implementations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class TransformXSLT extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/TR/1999/REC-xslt-19991116";
   static final String XSLTSpecNS = "http://www.w3.org/1999/XSL/Transform";
   static final String defaultXSLTSpecNSprefix = "xslt";
   static final String XSLTSTYLESHEET = "stylesheet";
   private static final Logger LOG = LoggerFactory.getLogger(TransformXSLT.class);

   protected String engineGetURI() {
      return "http://www.w3.org/TR/1999/REC-xslt-19991116";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream baos, Transform transformObject) throws IOException, TransformationException {
      try {
         Element transformElement = transformObject.getElement();
         Element xsltElement = XMLUtils.selectNode(transformElement.getFirstChild(), "http://www.w3.org/1999/XSL/Transform", "stylesheet", 0);
         if (xsltElement == null) {
            xsltElement = XMLUtils.selectNode(transformElement.getFirstChild(), "http://www.w3.org/1999/XSL/Transform", "transform", 0);
         }

         if (xsltElement == null) {
            Object[] exArgs = new Object[]{"xslt:stylesheet", "Transform"};
            throw new TransformationException("xml.WrongContent", exArgs);
         } else {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Throwable var9 = null;

            StreamSource stylesheet;
            StreamResult outputTarget;
            try {
               Transformer transformer = tFactory.newTransformer();
               DOMSource source = new DOMSource(xsltElement);
               outputTarget = new StreamResult(os);
               transformer.transform(source, outputTarget);
               stylesheet = new StreamSource(new ByteArrayInputStream(os.toByteArray()));
            } catch (Throwable var52) {
               var9 = var52;
               throw var52;
            } finally {
               $closeResource(var9, os);
            }

            Transformer transformer = tFactory.newTransformer(stylesheet);

            try {
               transformer.setOutputProperty("{http://xml.apache.org/xalan}line-separator", "\n");
            } catch (Exception var51) {
               LOG.warn("Unable to set Xalan line-separator property: " + var51.getMessage());
            }

            InputStream is = new ByteArrayInputStream(input.getBytes());
            Throwable var62 = null;

            label245: {
               XMLSignatureInput var16;
               try {
                  Source xmlSource = new StreamSource(is);
                  if (baos != null) {
                     outputTarget = new StreamResult(baos);
                     transformer.transform(xmlSource, outputTarget);
                     break label245;
                  }

                  ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                  Throwable var13 = null;

                  try {
                     StreamResult outputTarget = new StreamResult(baos1);
                     transformer.transform(xmlSource, outputTarget);
                     XMLSignatureInput output = new XMLSignatureInput(baos1.toByteArray());
                     output.setSecureValidation(this.secureValidation);
                     var16 = output;
                  } catch (Throwable var49) {
                     var13 = var49;
                     throw var49;
                  } finally {
                     $closeResource(var13, baos1);
                  }
               } catch (Throwable var54) {
                  var62 = var54;
                  throw var54;
               } finally {
                  $closeResource(var62, is);
               }

               return var16;
            }

            XMLSignatureInput output = new XMLSignatureInput((byte[])null);
            output.setSecureValidation(this.secureValidation);
            output.setOutputStream(baos);
            return output;
         }
      } catch (XMLSecurityException var56) {
         throw new TransformationException(var56);
      } catch (TransformerConfigurationException var57) {
         throw new TransformationException(var57);
      } catch (TransformerException var58) {
         throw new TransformationException(var58);
      }
   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
