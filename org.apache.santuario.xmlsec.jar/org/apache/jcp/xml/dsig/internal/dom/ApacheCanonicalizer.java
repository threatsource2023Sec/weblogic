package org.apache.jcp.xml.dsig.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ApacheCanonicalizer extends TransformService {
   private static final Logger LOG;
   protected Canonicalizer apacheCanonicalizer;
   private Transform apacheTransform;
   protected String inclusiveNamespaces;
   protected C14NMethodParameterSpec params;
   protected Document ownerDoc;
   protected Element transformElem;

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

   public Data canonicalize(Data data, XMLCryptoContext xc) throws TransformException {
      return this.canonicalize(data, xc, (OutputStream)null);
   }

   public Data canonicalize(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
      if (this.apacheCanonicalizer == null) {
         try {
            this.apacheCanonicalizer = Canonicalizer.getInstance(this.getAlgorithm());
            boolean secVal = Utils.secureValidation(xc);
            this.apacheCanonicalizer.setSecureValidation(secVal);
            LOG.debug("Created canonicalizer for algorithm: {}", this.getAlgorithm());
         } catch (InvalidCanonicalizerException var8) {
            throw new TransformException("Couldn't find Canonicalizer for: " + this.getAlgorithm() + ": " + var8.getMessage(), var8);
         }
      }

      if (os != null) {
         this.apacheCanonicalizer.setWriter(os);
      } else {
         this.apacheCanonicalizer.setWriter(new ByteArrayOutputStream());
      }

      try {
         Set nodeSet = null;
         if (data instanceof ApacheData) {
            XMLSignatureInput in = ((ApacheData)data).getXMLSignatureInput();
            if (in.isElement()) {
               if (this.inclusiveNamespaces != null) {
                  return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeSubtree(in.getSubNode(), this.inclusiveNamespaces)));
               }

               return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeSubtree(in.getSubNode())));
            }

            if (!in.isNodeSet()) {
               return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalize(Utils.readBytesFromStream(in.getOctetStream()))));
            }

            nodeSet = in.getNodeSet();
         } else {
            if (data instanceof DOMSubTreeData) {
               DOMSubTreeData subTree = (DOMSubTreeData)data;
               if (this.inclusiveNamespaces != null) {
                  return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeSubtree(subTree.getRoot(), this.inclusiveNamespaces)));
               }

               return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeSubtree(subTree.getRoot())));
            }

            if (!(data instanceof NodeSetData)) {
               return new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalize(Utils.readBytesFromStream(((OctetStreamData)data).getOctetStream()))));
            }

            NodeSetData nsd = (NodeSetData)data;
            Set ns = Utils.toNodeSet(nsd.iterator());
            nodeSet = ns;
            LOG.debug("Canonicalizing {} nodes", ns.size());
         }

         return this.inclusiveNamespaces != null ? new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeXPathNodeSet(nodeSet, this.inclusiveNamespaces))) : new OctetStreamData(new ByteArrayInputStream(this.apacheCanonicalizer.canonicalizeXPathNodeSet(nodeSet)));
      } catch (Exception var7) {
         throw new TransformException(var7);
      }
   }

   public Data transform(Data data, XMLCryptoContext xc, OutputStream os) throws TransformException {
      if (data == null) {
         throw new NullPointerException("data must not be null");
      } else if (os == null) {
         throw new NullPointerException("output stream must not be null");
      } else if (this.ownerDoc == null) {
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

         XMLSignatureInput in;
         if (data instanceof ApacheData) {
            LOG.debug("ApacheData = true");
            in = ((ApacheData)data).getXMLSignatureInput();
         } else if (data instanceof NodeSetData) {
            LOG.debug("isNodeSet() = true");
            if (data instanceof DOMSubTreeData) {
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
            in = this.apacheTransform.performTransform(in, os);
            if (!in.isNodeSet() && !in.isElement()) {
               return null;
            } else {
               return (Data)(in.isOctetStream() ? new ApacheOctetStreamData(in) : new ApacheNodeSetData(in));
            }
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
      LOG = LoggerFactory.getLogger(ApacheCanonicalizer.class);
   }
}
