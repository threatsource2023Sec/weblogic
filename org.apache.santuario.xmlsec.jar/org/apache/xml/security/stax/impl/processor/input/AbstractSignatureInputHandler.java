package org.apache.xml.security.stax.impl.processor.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xml.security.binding.excc14n.InclusiveNamespaces;
import org.apache.xml.security.binding.xmldsig.CanonicalizationMethodType;
import org.apache.xml.security.binding.xmldsig.SignatureType;
import org.apache.xml.security.binding.xmldsig.SignedInfoType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithm;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithmFactory;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.impl.util.SignerOutputStream;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public abstract class AbstractSignatureInputHandler extends AbstractInputSecurityHeaderHandler {
   public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque eventQueue, Integer index) throws XMLSecurityException {
      SignatureType signatureType = (SignatureType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
      if (signatureType.getSignedInfo() == null) {
         throw new XMLSecurityException("stax.signature.signedInfoMissing");
      } else if (signatureType.getSignedInfo().getSignatureMethod() == null) {
         throw new XMLSecurityException("stax.signature.signatureMethodMissing");
      } else if (signatureType.getSignedInfo().getCanonicalizationMethod() == null) {
         throw new XMLSecurityException("stax.signature.canonicalizationMethodMissing");
      } else if (signatureType.getSignatureValue() == null) {
         throw new XMLSecurityException("stax.signature.signatureValueMissing");
      } else {
         if (signatureType.getId() == null) {
            signatureType.setId(IDGenerator.generateID((String)null));
         }

         InboundSecurityToken inboundSecurityToken = this.verifySignedInfo(inputProcessorChain, securityProperties, signatureType, eventQueue, index);
         this.addSignatureReferenceInputProcessorToChain(inputProcessorChain, securityProperties, signatureType, inboundSecurityToken);
      }
   }

   protected abstract void addSignatureReferenceInputProcessorToChain(InputProcessorChain var1, XMLSecurityProperties var2, SignatureType var3, InboundSecurityToken var4) throws XMLSecurityException;

   protected InboundSecurityToken verifySignedInfo(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType, Deque eventDeque, int index) throws XMLSecurityException {
      String c14NMethod = signatureType.getSignedInfo().getCanonicalizationMethod().getAlgorithm();
      Iterator iterator;
      if (!"http://www.w3.org/TR/2001/REC-xml-c14n-20010315".equals(c14NMethod) && !"http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments".equals(c14NMethod) && !"http://www.w3.org/2001/10/xml-exc-c14n#".equals(c14NMethod) && !"http://www.w3.org/2001/10/xml-exc-c14n#WithComments".equals(c14NMethod) && !"http://www.w3.org/2006/12/xml-c14n11".equals(c14NMethod) && !"http://www.w3.org/2006/12/xml-c14n11#WithComments".equals(c14NMethod)) {
         iterator = this.reparseSignedInfo(inputProcessorChain, securityProperties, signatureType, eventDeque, index).descendingIterator();
         int index = false;
      } else {
         iterator = eventDeque.descendingIterator();

         for(int i = 0; i < index; ++i) {
            iterator.next();
         }
      }

      SignatureVerifier signatureVerifier = this.newSignatureVerifier(inputProcessorChain, securityProperties, signatureType);

      try {
         XMLSecEvent xmlSecEvent;
         label47:
         while(iterator.hasNext()) {
            xmlSecEvent = (XMLSecEvent)iterator.next();
            switch (xmlSecEvent.getEventType()) {
               case 1:
                  if (xmlSecEvent.asStartElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                     signatureVerifier.processEvent(xmlSecEvent);
                     break label47;
                  }
            }
         }

         label38:
         while(iterator.hasNext()) {
            xmlSecEvent = (XMLSecEvent)iterator.next();
            signatureVerifier.processEvent(xmlSecEvent);
            switch (xmlSecEvent.getEventType()) {
               case 2:
                  if (xmlSecEvent.asEndElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                     break label38;
                  }
            }
         }
      } catch (XMLStreamException var10) {
         throw new XMLSecurityException(var10);
      }

      signatureVerifier.doFinal();
      return signatureVerifier.getInboundSecurityToken();
   }

   protected Deque reparseSignedInfo(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, SignatureType signatureType, Deque eventDeque, int index) throws XMLSecurityException {
      Deque signedInfoDeque = new ArrayDeque();

      try {
         UnsyncByteArrayOutputStream unsynchronizedByteArrayOutputStream = new UnsyncByteArrayOutputStream();
         Throwable var8 = null;

         try {
            Transformer transformer = XMLSecurityUtils.getTransformer((Transformer)null, unsynchronizedByteArrayOutputStream, (Map)null, signatureType.getSignedInfo().getCanonicalizationMethod().getAlgorithm(), XMLSecurityConstants.DIRECTION.IN);
            Iterator iterator = eventDeque.descendingIterator();

            for(int i = 0; i < index; ++i) {
               iterator.next();
            }

            XMLSecEvent xmlSecEvent;
            label201:
            while(iterator.hasNext()) {
               xmlSecEvent = (XMLSecEvent)iterator.next();
               switch (xmlSecEvent.getEventType()) {
                  case 1:
                     if (xmlSecEvent.asStartElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                        transformer.transform(xmlSecEvent);
                        break label201;
                     }
               }
            }

            label210:
            while(iterator.hasNext()) {
               xmlSecEvent = (XMLSecEvent)iterator.next();
               transformer.transform(xmlSecEvent);
               switch (xmlSecEvent.getEventType()) {
                  case 2:
                     if (xmlSecEvent.asEndElement().getName().equals(XMLSecurityConstants.TAG_dsig_SignedInfo)) {
                        break label210;
                     }
               }
            }

            transformer.doFinal();
            InputStream is = new UnsyncByteArrayInputStream(unsynchronizedByteArrayOutputStream.toByteArray());
            Throwable var13 = null;

            try {
               XMLStreamReader xmlStreamReader = ((XMLInputFactory)inputProcessorChain.getSecurityContext().get("XMLInputFactory")).createXMLStreamReader(is);

               while(xmlStreamReader.hasNext()) {
                  XMLSecEvent xmlSecEvent = XMLSecEventFactory.allocate(xmlStreamReader, (XMLSecStartElement)null);
                  signedInfoDeque.push(xmlSecEvent);
                  xmlStreamReader.next();
               }

               SignedInfoType signedInfoType = (SignedInfoType)((JAXBElement)this.parseStructure(signedInfoDeque, 0, securityProperties)).getValue();
               signatureType.setSignedInfo(signedInfoType);
               ArrayDeque var16 = signedInfoDeque;
               return var16;
            } catch (Throwable var29) {
               var13 = var29;
               throw var29;
            } finally {
               $closeResource(var13, is);
            }
         } catch (Throwable var31) {
            var8 = var31;
            throw var31;
         } finally {
            $closeResource(var8, unsynchronizedByteArrayOutputStream);
         }
      } catch (IOException | XMLStreamException var33) {
         throw new XMLSecurityException(var33);
      }
   }

   protected abstract SignatureVerifier newSignatureVerifier(InputProcessorChain var1, XMLSecurityProperties var2, SignatureType var3) throws XMLSecurityException;

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

   public abstract class SignatureVerifier {
      private final SignatureType signatureType;
      private final InboundSecurityToken inboundSecurityToken;
      private SignerOutputStream signerOutputStream;
      private OutputStream bufferedSignerOutputStream;
      private Transformer transformer;

      public SignatureVerifier(SignatureType signatureType, InboundSecurityContext inboundSecurityContext, XMLSecurityProperties securityProperties) throws XMLSecurityException {
         this.signatureType = signatureType;
         InboundSecurityToken inboundSecurityToken = this.retrieveSecurityToken(signatureType, securityProperties, inboundSecurityContext);
         this.inboundSecurityToken = inboundSecurityToken;
         this.createSignatureAlgorithm(inboundSecurityToken, signatureType);
      }

      protected abstract InboundSecurityToken retrieveSecurityToken(SignatureType var1, XMLSecurityProperties var2, InboundSecurityContext var3) throws XMLSecurityException;

      public InboundSecurityToken getInboundSecurityToken() {
         return this.inboundSecurityToken;
      }

      protected void createSignatureAlgorithm(InboundSecurityToken inboundSecurityToken, SignatureType signatureType) throws XMLSecurityException {
         String algorithmURI = signatureType.getSignedInfo().getSignatureMethod().getAlgorithm();
         Object verifyKey;
         if (inboundSecurityToken.isAsymmetric()) {
            verifyKey = inboundSecurityToken.getPublicKey(algorithmURI, XMLSecurityConstants.Asym_Sig, signatureType.getId());
         } else {
            verifyKey = inboundSecurityToken.getSecretKey(algorithmURI, XMLSecurityConstants.Sym_Sig, signatureType.getId());
            if (verifyKey != null) {
               verifyKey = XMLSecurityUtils.prepareSecretKey(algorithmURI, ((Key)verifyKey).getEncoded());
            }
         }

         if (verifyKey == null) {
            throw new XMLSecurityException("KeyInfo.nokey", new Object[]{"the inbound security token"});
         } else {
            try {
               SignatureAlgorithm signatureAlgorithm = SignatureAlgorithmFactory.getInstance().getSignatureAlgorithm(algorithmURI);
               signatureAlgorithm.engineInitVerify((Key)verifyKey);
               this.signerOutputStream = new SignerOutputStream(signatureAlgorithm);
               this.bufferedSignerOutputStream = new UnsyncBufferedOutputStream(this.signerOutputStream);
               CanonicalizationMethodType canonicalizationMethodType = signatureType.getSignedInfo().getCanonicalizationMethod();
               InclusiveNamespaces inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(canonicalizationMethodType.getContent(), XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
               Map transformerProperties = null;
               if (inclusiveNamespacesType != null) {
                  transformerProperties = new HashMap();
                  transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacesType.getPrefixList());
               }

               this.transformer = XMLSecurityUtils.getTransformer((Transformer)null, this.bufferedSignerOutputStream, transformerProperties, canonicalizationMethodType.getAlgorithm(), XMLSecurityConstants.DIRECTION.IN);
            } catch (NoSuchAlgorithmException var9) {
               throw new XMLSecurityException(var9);
            } catch (NoSuchProviderException var10) {
               throw new XMLSecurityException(var10);
            }
         }
      }

      protected void processEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException {
         this.transformer.transform(xmlSecEvent);
      }

      protected void doFinal() throws XMLSecurityException {
         try {
            this.transformer.doFinal();
            this.bufferedSignerOutputStream.close();
         } catch (IOException var2) {
            throw new XMLSecurityException(var2);
         } catch (XMLStreamException var3) {
            throw new XMLSecurityException(var3);
         }

         if (!this.signerOutputStream.verify(this.signatureType.getSignatureValue().getValue())) {
            throw new XMLSecurityException("errorMessages.InvalidSignatureValueException");
         }
      }
   }
}
