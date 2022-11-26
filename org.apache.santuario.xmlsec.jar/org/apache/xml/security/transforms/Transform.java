package org.apache.xml.security.transforms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.implementations.TransformBase64Decode;
import org.apache.xml.security.transforms.implementations.TransformC14N;
import org.apache.xml.security.transforms.implementations.TransformC14N11;
import org.apache.xml.security.transforms.implementations.TransformC14N11_WithComments;
import org.apache.xml.security.transforms.implementations.TransformC14NExclusive;
import org.apache.xml.security.transforms.implementations.TransformC14NExclusiveWithComments;
import org.apache.xml.security.transforms.implementations.TransformC14NWithComments;
import org.apache.xml.security.transforms.implementations.TransformEnvelopedSignature;
import org.apache.xml.security.transforms.implementations.TransformXPath;
import org.apache.xml.security.transforms.implementations.TransformXPath2Filter;
import org.apache.xml.security.transforms.implementations.TransformXSLT;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.HelperNodeList;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class Transform extends SignatureElementProxy {
   private static final Logger LOG = LoggerFactory.getLogger(Transform.class);
   private static Map transformSpiHash = new ConcurrentHashMap();
   private final TransformSpi transformSpi;
   private boolean secureValidation;

   public Transform(Document doc, String algorithmURI) throws InvalidTransformException {
      this(doc, algorithmURI, (NodeList)null);
   }

   public Transform(Document doc, String algorithmURI, Element contextChild) throws InvalidTransformException {
      super(doc);
      HelperNodeList contextNodes = null;
      if (contextChild != null) {
         contextNodes = new HelperNodeList();
         XMLUtils.addReturnToElement(doc, contextNodes);
         contextNodes.appendChild(contextChild);
         XMLUtils.addReturnToElement(doc, contextNodes);
      }

      this.transformSpi = this.initializeTransform(algorithmURI, contextNodes);
   }

   public Transform(Document doc, String algorithmURI, NodeList contextNodes) throws InvalidTransformException {
      super(doc);
      this.transformSpi = this.initializeTransform(algorithmURI, contextNodes);
   }

   public Transform(Element element, String baseURI) throws InvalidTransformException, TransformationException, XMLSecurityException {
      super(element, baseURI);
      String algorithmURI = element.getAttributeNS((String)null, "Algorithm");
      if (algorithmURI != null && algorithmURI.length() != 0) {
         Class transformSpiClass = (Class)transformSpiHash.get(algorithmURI);
         if (transformSpiClass == null) {
            Object[] exArgs = new Object[]{algorithmURI};
            throw new InvalidTransformException("signature.Transform.UnknownTransform", exArgs);
         } else {
            Object[] exArgs;
            try {
               this.transformSpi = (TransformSpi)transformSpiClass.newInstance();
            } catch (InstantiationException var7) {
               exArgs = new Object[]{algorithmURI};
               throw new InvalidTransformException(var7, "signature.Transform.UnknownTransform", exArgs);
            } catch (IllegalAccessException var8) {
               exArgs = new Object[]{algorithmURI};
               throw new InvalidTransformException(var8, "signature.Transform.UnknownTransform", exArgs);
            }
         }
      } else {
         Object[] exArgs = new Object[]{"Algorithm", "Transform"};
         throw new TransformationException("xml.WrongContent", exArgs);
      }
   }

   public static void register(String algorithmURI, String implementingClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException, InvalidTransformException {
      JavaUtils.checkRegisterPermission();
      Class transformSpi = (Class)transformSpiHash.get(algorithmURI);
      if (transformSpi != null) {
         Object[] exArgs = new Object[]{algorithmURI, transformSpi};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         Class transformSpiClass = ClassLoaderUtils.loadClass(implementingClass, Transform.class);
         transformSpiHash.put(algorithmURI, transformSpiClass);
      }
   }

   public static void register(String algorithmURI, Class implementingClass) throws AlgorithmAlreadyRegisteredException {
      JavaUtils.checkRegisterPermission();
      Class transformSpi = (Class)transformSpiHash.get(algorithmURI);
      if (transformSpi != null) {
         Object[] exArgs = new Object[]{algorithmURI, transformSpi};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         transformSpiHash.put(algorithmURI, implementingClass);
      }
   }

   public static void registerDefaultAlgorithms() {
      transformSpiHash.put("http://www.w3.org/2000/09/xmldsig#base64", TransformBase64Decode.class);
      transformSpiHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", TransformC14N.class);
      transformSpiHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", TransformC14NWithComments.class);
      transformSpiHash.put("http://www.w3.org/2006/12/xml-c14n11", TransformC14N11.class);
      transformSpiHash.put("http://www.w3.org/2006/12/xml-c14n11#WithComments", TransformC14N11_WithComments.class);
      transformSpiHash.put("http://www.w3.org/2001/10/xml-exc-c14n#", TransformC14NExclusive.class);
      transformSpiHash.put("http://www.w3.org/2001/10/xml-exc-c14n#WithComments", TransformC14NExclusiveWithComments.class);
      transformSpiHash.put("http://www.w3.org/TR/1999/REC-xpath-19991116", TransformXPath.class);
      transformSpiHash.put("http://www.w3.org/2000/09/xmldsig#enveloped-signature", TransformEnvelopedSignature.class);
      transformSpiHash.put("http://www.w3.org/TR/1999/REC-xslt-19991116", TransformXSLT.class);
      transformSpiHash.put("http://www.w3.org/2002/06/xmldsig-filter2", TransformXPath2Filter.class);
   }

   public String getURI() {
      return this.getLocalAttribute("Algorithm");
   }

   public XMLSignatureInput performTransform(XMLSignatureInput input) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
      return this.performTransform(input, (OutputStream)null);
   }

   public XMLSignatureInput performTransform(XMLSignatureInput input, OutputStream os) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException {
      XMLSignatureInput result = null;

      Object[] exArgs;
      try {
         this.transformSpi.secureValidation = this.secureValidation;
         result = this.transformSpi.enginePerformTransform(input, os, this);
         return result;
      } catch (ParserConfigurationException var6) {
         exArgs = new Object[]{this.getURI(), "ParserConfigurationException"};
         throw new CanonicalizationException(var6, "signature.Transform.ErrorDuringTransform", exArgs);
      } catch (SAXException var7) {
         exArgs = new Object[]{this.getURI(), "SAXException"};
         throw new CanonicalizationException(var7, "signature.Transform.ErrorDuringTransform", exArgs);
      }
   }

   public String getBaseLocalName() {
      return "Transform";
   }

   private TransformSpi initializeTransform(String algorithmURI, NodeList contextNodes) throws InvalidTransformException {
      this.setLocalAttribute("Algorithm", algorithmURI);
      Class transformSpiClass = (Class)transformSpiHash.get(algorithmURI);
      if (transformSpiClass == null) {
         Object[] exArgs = new Object[]{algorithmURI};
         throw new InvalidTransformException("signature.Transform.UnknownTransform", exArgs);
      } else {
         TransformSpi newTransformSpi = null;

         Object[] exArgs;
         try {
            newTransformSpi = (TransformSpi)transformSpiClass.newInstance();
         } catch (InstantiationException var7) {
            exArgs = new Object[]{algorithmURI};
            throw new InvalidTransformException(var7, "signature.Transform.UnknownTransform", exArgs);
         } catch (IllegalAccessException var8) {
            exArgs = new Object[]{algorithmURI};
            throw new InvalidTransformException(var8, "signature.Transform.UnknownTransform", exArgs);
         }

         LOG.debug("Create URI \"{}\" class \"{}\"", algorithmURI, newTransformSpi.getClass());
         LOG.debug("The NodeList is {}", contextNodes);
         if (contextNodes != null) {
            int length = contextNodes.getLength();

            for(int i = 0; i < length; ++i) {
               this.appendSelf(contextNodes.item(i).cloneNode(true));
            }
         }

         return newTransformSpi;
      }
   }

   public boolean isSecureValidation() {
      return this.secureValidation;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }
}
