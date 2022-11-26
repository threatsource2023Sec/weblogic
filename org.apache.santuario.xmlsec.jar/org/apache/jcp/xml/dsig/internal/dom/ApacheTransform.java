package org.apache.jcp.xml.dsig.internal.dom;

import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Set;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NodeSetData;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ApacheTransform extends TransformService {
   private static final Logger LOG;
   private Transform apacheTransform;
   protected Document ownerDoc;
   protected Element transformElem;
   protected TransformParameterSpec params;

   public final AlgorithmParameterSpec getParameterSpec() {
      return this.params;
   }

   public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
      if (context != null && !(context instanceof DOMCryptoContext)) {
         throw new ClassCastException("context must be of type DOMCryptoContext");
      } else if (parent == null) {
         throw new NullPointerException();
      } else if (!(parent instanceof javax.xml.crypto.dom.DOMStructure)) {
         throw new ClassCastException("parent must be of type DOMStructure");
      } else {
         this.transformElem = (Element)((javax.xml.crypto.dom.DOMStructure)parent).getNode();
         this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
      }
   }

   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
      if (context != null && !(context instanceof DOMCryptoContext)) {
         throw new ClassCastException("context must be of type DOMCryptoContext");
      } else if (parent == null) {
         throw new NullPointerException();
      } else if (!(parent instanceof javax.xml.crypto.dom.DOMStructure)) {
         throw new ClassCastException("parent must be of type DOMStructure");
      } else {
         this.transformElem = (Element)((javax.xml.crypto.dom.DOMStructure)parent).getNode();
         this.ownerDoc = DOMUtils.getOwnerDocument(this.transformElem);
      }
   }

   public Data transform(Data data, XMLCryptoContext xc) throws TransformException {
      if (data == null) {
         throw new NullPointerException("data must not be null");
      } else {
         return this.transformIt(data, xc, (OutputStream)null);
      }
   }

   public Data transform(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
      if (data == null) {
         throw new NullPointerException("data must not be null");
      } else if (os == null) {
         throw new NullPointerException("output stream must not be null");
      } else {
         return this.transformIt(data, xc, os);
      }
   }

   private Data transformIt(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
      if (this.ownerDoc == null) {
         throw new TransformException("transform must be marshalled");
      } else {
         if (this.apacheTransform == null) {
            try {
               this.apacheTransform = new Transform(this.ownerDoc, this.getAlgorithm(), this.transformElem.getChildNodes());
               this.apacheTransform.setElement(this.transformElem, xc.getBaseURI());
               boolean secVal = Utils.secureValidation(xc);
               this.apacheTransform.setSecureValidation(secVal);
               LOG.debug("Created transform for algorithm: {}", this.getAlgorithm());
            } catch (Exception var9) {
               throw new TransformException("Couldn't find Transform for: " + this.getAlgorithm(), var9);
            }
         }

         if (Utils.secureValidation(xc)) {
            String algorithm = this.getAlgorithm();
            if ("http://www.w3.org/TR/1999/REC-xslt-19991116".equals(algorithm)) {
               throw new TransformException("Transform " + algorithm + " is forbidden when secure validation is enabled");
            }
         }

         XMLSignatureInput in;
         if (data instanceof ApacheData) {
            LOG.debug("ApacheData = true");
            in = ((ApacheData)data).getXMLSignatureInput();
         } else if (data instanceof NodeSetData) {
            LOG.debug("isNodeSet() = true");
            if (data instanceof DOMSubTreeData) {
               LOG.debug("DOMSubTreeData = true");
               DOMSubTreeData subTree = (DOMSubTreeData)data;
               in = new XMLSignatureInput(subTree.getRoot());
               in.setExcludeComments(subTree.excludeComments());
            } else {
               Set nodeSet = Utils.toNodeSet(((NodeSetData)data).iterator());
               in = new XMLSignatureInput(nodeSet);
            }
         } else {
            LOG.debug("isNodeSet() = false");

            try {
               in = new XMLSignatureInput(((OctetStreamData)data).getOctetStream());
            } catch (Exception var8) {
               throw new TransformException(var8);
            }
         }

         boolean secVal = Utils.secureValidation(xc);
         in.setSecureValidation(secVal);

         try {
            if (os != null) {
               in = this.apacheTransform.performTransform(in, os);
               if (!in.isNodeSet() && !in.isElement()) {
                  return null;
               }
            } else {
               in = this.apacheTransform.performTransform(in);
            }

            return (Data)(in.isOctetStream() ? new ApacheOctetStreamData(in) : new ApacheNodeSetData(in));
         } catch (Exception var7) {
            throw new TransformException(var7);
         }
      }
   }

   public final boolean isFeatureSupported(String feature) {
      if (feature == null) {
         throw new NullPointerException();
      } else {
         return false;
      }
   }

   static {
      Init.init();
      LOG = LoggerFactory.getLogger(ApacheTransform.class);
   }
}
