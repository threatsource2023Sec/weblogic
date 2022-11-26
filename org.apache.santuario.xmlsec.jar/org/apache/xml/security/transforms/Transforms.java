package org.apache.xml.security.transforms;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Transforms extends SignatureElementProxy {
   public static final String TRANSFORM_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   public static final String TRANSFORM_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
   public static final String TRANSFORM_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
   public static final String TRANSFORM_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
   public static final String TRANSFORM_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
   public static final String TRANSFORM_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
   public static final String TRANSFORM_XSLT = "http://www.w3.org/TR/1999/REC-xslt-19991116";
   public static final String TRANSFORM_BASE64_DECODE = "http://www.w3.org/2000/09/xmldsig#base64";
   public static final String TRANSFORM_XPATH = "http://www.w3.org/TR/1999/REC-xpath-19991116";
   public static final String TRANSFORM_ENVELOPED_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
   public static final String TRANSFORM_XPOINTER = "http://www.w3.org/TR/2001/WD-xptr-20010108";
   public static final String TRANSFORM_XPATH2FILTER = "http://www.w3.org/2002/06/xmldsig-filter2";
   private static final Logger LOG = LoggerFactory.getLogger(Transforms.class);
   private Element[] transforms;
   private boolean secureValidation;

   protected Transforms() {
   }

   public Transforms(Document doc) {
      super(doc);
      this.addReturnToSelf();
   }

   public Transforms(Element element, String baseURI) throws DOMException, XMLSignatureException, InvalidTransformException, TransformationException, XMLSecurityException {
      super(element, baseURI);
      int numberOfTransformElems = this.getLength();
      if (numberOfTransformElems == 0) {
         Object[] exArgs = new Object[]{"Transform", "Transforms"};
         throw new TransformationException("xml.WrongContent", exArgs);
      }
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   public void addTransform(String transformURI) throws TransformationException {
      try {
         LOG.debug("Transforms.addTransform({})", transformURI);
         Transform transform = new Transform(this.getDocument(), transformURI);
         this.addTransform(transform);
      } catch (InvalidTransformException var3) {
         throw new TransformationException(var3);
      }
   }

   public void addTransform(String transformURI, Element contextElement) throws TransformationException {
      try {
         LOG.debug("Transforms.addTransform({})", transformURI);
         Transform transform = new Transform(this.getDocument(), transformURI, contextElement);
         this.addTransform(transform);
      } catch (InvalidTransformException var4) {
         throw new TransformationException(var4);
      }
   }

   public void addTransform(String transformURI, NodeList contextNodes) throws TransformationException {
      try {
         Transform transform = new Transform(this.getDocument(), transformURI, contextNodes);
         this.addTransform(transform);
      } catch (InvalidTransformException var4) {
         throw new TransformationException(var4);
      }
   }

   private void addTransform(Transform transform) {
      LOG.debug("Transforms.addTransform({})", transform.getURI());
      Element transformElement = transform.getElement();
      this.appendSelf(transformElement);
      this.addReturnToSelf();
   }

   public XMLSignatureInput performTransforms(XMLSignatureInput xmlSignatureInput) throws TransformationException {
      return this.performTransforms(xmlSignatureInput, (OutputStream)null);
   }

   public XMLSignatureInput performTransforms(XMLSignatureInput xmlSignatureInput, OutputStream os) throws TransformationException {
      try {
         int last = this.getLength() - 1;

         for(int i = 0; i < last; ++i) {
            Transform t = this.item(i);
            LOG.debug("Perform the ({})th {} transform", i, t.getURI());
            this.checkSecureValidation(t);
            xmlSignatureInput = t.performTransform(xmlSignatureInput);
         }

         if (last >= 0) {
            Transform t = this.item(last);
            LOG.debug("Perform the ({})th {} transform", last, t.getURI());
            this.checkSecureValidation(t);
            xmlSignatureInput = t.performTransform(xmlSignatureInput, os);
         }

         return xmlSignatureInput;
      } catch (IOException var6) {
         throw new TransformationException(var6);
      } catch (CanonicalizationException var7) {
         throw new TransformationException(var7);
      } catch (InvalidCanonicalizerException var8) {
         throw new TransformationException(var8);
      }
   }

   private void checkSecureValidation(Transform transform) throws TransformationException {
      String uri = transform.getURI();
      if (this.secureValidation && "http://www.w3.org/TR/1999/REC-xslt-19991116".equals(uri)) {
         Object[] exArgs = new Object[]{uri};
         throw new TransformationException("signature.Transform.ForbiddenTransform", exArgs);
      } else {
         transform.setSecureValidation(this.secureValidation);
      }
   }

   public int getLength() {
      this.initTransforms();
      return this.transforms.length;
   }

   public Transform item(int i) throws TransformationException {
      try {
         this.initTransforms();
         return new Transform(this.transforms[i], this.baseURI);
      } catch (XMLSecurityException var3) {
         throw new TransformationException(var3);
      }
   }

   private void initTransforms() {
      if (this.transforms == null) {
         this.transforms = XMLUtils.selectDsNodes(this.getFirstChild(), "Transform");
      }

   }

   public String getBaseLocalName() {
      return "Transforms";
   }
}
