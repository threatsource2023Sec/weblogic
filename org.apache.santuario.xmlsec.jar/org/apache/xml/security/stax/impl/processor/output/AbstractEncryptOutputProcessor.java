package org.apache.xml.security.stax.impl.processor.output;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.XMLCipherUtil;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecCharacters;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.EncryptionPartDef;
import org.apache.xml.security.stax.impl.XMLSecurityEventWriter;
import org.apache.xml.security.stax.impl.util.TrimmerOutputStream;
import org.apache.xml.security.utils.XMLUtils;

public abstract class AbstractEncryptOutputProcessor extends AbstractOutputProcessor {
   private static final XMLSecStartElement wrapperStartElement = XMLSecEventFactory.createXmlSecStartElement(new QName("a"), (List)null, (List)null);
   private static final XMLSecEndElement wrapperEndElement = XMLSecEventFactory.createXmlSecEndElement(new QName("a"));
   private AbstractInternalEncryptionOutputProcessor activeInternalEncryptionOutputProcessor;

   public AbstractEncryptOutputProcessor() throws XMLSecurityException {
   }

   public abstract void processEvent(XMLSecEvent var1, OutputProcessorChain var2) throws XMLStreamException, XMLSecurityException;

   public void doFinal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      this.doFinalInternal(outputProcessorChain);
      super.doFinal(outputProcessorChain);
   }

   protected void doFinalInternal(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      this.verifyEncryptionParts(outputProcessorChain);
   }

   protected void verifyEncryptionParts(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      List encryptionPartDefs = outputProcessorChain.getSecurityContext().getAsList(EncryptionPartDef.class);
      Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap("encryptionParts");
      Iterator securePartsMapIterator = dynamicSecureParts.entrySet().iterator();

      label31:
      while(true) {
         SecurePart securePart;
         do {
            if (!securePartsMapIterator.hasNext()) {
               return;
            }

            Map.Entry securePartEntry = (Map.Entry)securePartsMapIterator.next();
            securePart = (SecurePart)securePartEntry.getValue();
         } while(!securePart.isRequired());

         for(int i = 0; encryptionPartDefs != null && i < encryptionPartDefs.size(); ++i) {
            EncryptionPartDef encryptionPartDef = (EncryptionPartDef)encryptionPartDefs.get(i);
            if (encryptionPartDef.getSecurePart() == securePart) {
               continue label31;
            }
         }

         throw new XMLSecurityException("stax.encryption.securePartNotFound", new Object[]{securePart.getName()});
      }
   }

   protected AbstractInternalEncryptionOutputProcessor getActiveInternalEncryptionOutputProcessor() {
      return this.activeInternalEncryptionOutputProcessor;
   }

   protected void setActiveInternalEncryptionOutputProcessor(AbstractInternalEncryptionOutputProcessor activeInternalEncryptionOutputProcessor) {
      this.activeInternalEncryptionOutputProcessor = activeInternalEncryptionOutputProcessor;
   }

   private char[] byteToCharArray(byte[] bytes, int off, int len) {
      char[] chars = new char[len - off];

      for(int i = off; i < len; ++i) {
         chars[i] = (char)bytes[i];
      }

      return chars;
   }

   public class CharacterEventGeneratorOutputStream extends OutputStream {
      private final Deque charactersBuffer = new ArrayDeque();

      public Deque getCharactersBuffer() {
         return this.charactersBuffer;
      }

      public void write(int b) throws IOException {
         this.charactersBuffer.offer(AbstractEncryptOutputProcessor.this.createCharacters(new char[]{(char)b}));
      }

      public void write(byte[] b) throws IOException {
         this.charactersBuffer.offer(AbstractEncryptOutputProcessor.this.createCharacters(AbstractEncryptOutputProcessor.this.byteToCharArray(b, 0, b.length)));
      }

      public void write(byte[] b, int off, int len) throws IOException {
         this.charactersBuffer.offer(AbstractEncryptOutputProcessor.this.createCharacters(AbstractEncryptOutputProcessor.this.byteToCharArray(b, off, len)));
      }
   }

   public abstract class AbstractInternalEncryptionOutputProcessor extends AbstractOutputProcessor {
      private EncryptionPartDef encryptionPartDef;
      private CharacterEventGeneratorOutputStream characterEventGeneratorOutputStream;
      private XMLEventWriter xmlEventWriter;
      private OutputStream cipherOutputStream;
      private String encoding;
      private XMLSecStartElement xmlSecStartElement;
      private int elementCounter;

      public AbstractInternalEncryptionOutputProcessor(EncryptionPartDef encryptionPartDef, XMLSecStartElement xmlSecStartElement, String encoding) throws XMLSecurityException {
         this.addBeforeProcessor(AbstractEncryptEndingOutputProcessor.class.getName());
         this.addBeforeProcessor(AbstractInternalEncryptionOutputProcessor.class.getName());
         this.addAfterProcessor(AbstractEncryptOutputProcessor.class.getName());
         this.setEncryptionPartDef(encryptionPartDef);
         this.setXmlSecStartElement(xmlSecStartElement);
         this.setEncoding(encoding);
      }

      public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
         String encryptionSymAlgorithm = this.securityProperties.getEncryptionSymAlgorithm();

         try {
            String jceAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(encryptionSymAlgorithm);
            if (jceAlgorithm == null) {
               throw new XMLSecurityException("algorithms.NoSuchMap", new Object[]{encryptionSymAlgorithm});
            }

            Cipher symmetricCipher = Cipher.getInstance(jceAlgorithm);
            int ivLen = JCEMapper.getIVLengthFromURI(encryptionSymAlgorithm) / 8;
            byte[] iv = XMLSecurityConstants.generateBytes(ivLen);
            AlgorithmParameterSpec parameterSpec = XMLCipherUtil.constructBlockCipherParameters(encryptionSymAlgorithm, iv, this.getClass());
            symmetricCipher.init(1, this.encryptionPartDef.getSymmetricKey(), parameterSpec);
            this.characterEventGeneratorOutputStream = AbstractEncryptOutputProcessor.this.new CharacterEventGeneratorOutputStream();
            Base64OutputStream base64EncoderStream = null;
            if (XMLUtils.isIgnoreLineBreaks()) {
               base64EncoderStream = new Base64OutputStream(this.characterEventGeneratorOutputStream, true, 0, (byte[])null);
            } else {
               base64EncoderStream = new Base64OutputStream(this.characterEventGeneratorOutputStream, true);
            }

            base64EncoderStream.write(iv);
            OutputStream outputStreamx = new CipherOutputStream(base64EncoderStream, symmetricCipher);
            OutputStream outputStream = this.applyTransforms(outputStreamx);
            this.cipherOutputStream = new TrimmerOutputStream(outputStream, 81920, 3, 4);
            this.xmlEventWriter = new XMLSecurityEventWriter(XMLSecurityConstants.xmlOutputFactoryNonRepairingNs.createXMLStreamWriter(this.cipherOutputStream, StandardCharsets.UTF_8.name()));
            this.xmlEventWriter.add(AbstractEncryptOutputProcessor.wrapperStartElement);
         } catch (NoSuchPaddingException var10) {
            throw new XMLSecurityException(var10);
         } catch (NoSuchAlgorithmException var11) {
            throw new XMLSecurityException(var11);
         } catch (IOException var12) {
            throw new XMLSecurityException(var12);
         } catch (XMLStreamException var13) {
            throw new XMLSecurityException(var13);
         } catch (InvalidKeyException var14) {
            throw new XMLSecurityException(var14);
         } catch (InvalidAlgorithmParameterException var15) {
            throw new XMLSecurityException(var15);
         }

         super.init(outputProcessorChain);
      }

      protected OutputStream applyTransforms(OutputStream outputStream) throws XMLSecurityException {
         return outputStream;
      }

      public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
         OutputProcessorChain subOutputProcessorChain;
         switch (xmlSecEvent.getEventType()) {
            case 1:
               XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
               if (this.elementCounter == 0 && xmlSecStartElement.getName().equals(this.getXmlSecStartElement().getName())) {
                  switch (this.getEncryptionPartDef().getModifier()) {
                     case Element:
                        subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                        this.processEventInternal(xmlSecStartElement, subOutputProcessorChain);
                        this.encryptEvent(xmlSecEvent);
                        break;
                     case Content:
                        outputProcessorChain.processEvent(xmlSecEvent);
                        subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                        this.processEventInternal(xmlSecStartElement, subOutputProcessorChain);
                  }
               } else {
                  this.encryptEvent(xmlSecEvent);
               }

               ++this.elementCounter;
               break;
            case 2:
               --this.elementCounter;
               if (this.elementCounter == 0 && xmlSecEvent.asEndElement().getName().equals(this.getXmlSecStartElement().getName())) {
                  subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                  switch (this.getEncryptionPartDef().getModifier()) {
                     case Element:
                        this.encryptEvent(xmlSecEvent);
                        this.doFinalInternal(subOutputProcessorChain);
                        break;
                     case Content:
                        this.doFinalInternal(subOutputProcessorChain);
                        this.outputAsEvent(subOutputProcessorChain, xmlSecEvent);
                  }

                  subOutputProcessorChain.removeProcessor(this);
                  AbstractEncryptOutputProcessor.this.setActiveInternalEncryptionOutputProcessor((AbstractInternalEncryptionOutputProcessor)null);
               } else {
                  this.encryptEvent(xmlSecEvent);
               }
               break;
            default:
               this.encryptEvent(xmlSecEvent);
               Deque charactersBuffer = this.characterEventGeneratorOutputStream.getCharactersBuffer();
               if (charactersBuffer.size() > 5) {
                  OutputProcessorChain subOutputProcessorChainx = outputProcessorChain.createSubChain(this);
                  Iterator charactersIterator = charactersBuffer.iterator();

                  while(charactersIterator.hasNext()) {
                     XMLSecCharacters characters = (XMLSecCharacters)charactersIterator.next();
                     this.outputAsEvent(subOutputProcessorChainx, characters);
                     charactersIterator.remove();
                  }
               }
         }

      }

      private void encryptEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException {
         this.xmlEventWriter.add(xmlSecEvent);
      }

      protected void processEventInternal(XMLSecStartElement xmlSecStartElement, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
         List attributes = new ArrayList(2);
         attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Id, this.getEncryptionPartDef().getEncRefId()));
         attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Type, this.getEncryptionPartDef().getModifier().getModifier()));
         this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_EncryptedData, true, attributes);
         attributes = new ArrayList(1);
         attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, this.securityProperties.getEncryptionSymAlgorithm()));
         this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_EncryptionMethod, false, attributes);
         this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_EncryptionMethod);
         this.createKeyInfoStructure(outputProcessorChain);
         this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_CipherData, false, (List)null);
         this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_CipherValue, false, (List)null);
      }

      protected abstract void createKeyInfoStructure(OutputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

      protected void doFinalInternal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
         try {
            this.xmlEventWriter.add(AbstractEncryptOutputProcessor.wrapperEndElement);
            this.xmlEventWriter.close();
            this.cipherOutputStream.close();
         } catch (IOException var5) {
            throw new XMLStreamException(var5);
         }

         Deque charactersBuffer = this.characterEventGeneratorOutputStream.getCharactersBuffer();
         if (!charactersBuffer.isEmpty()) {
            Iterator charactersIterator = charactersBuffer.iterator();

            while(charactersIterator.hasNext()) {
               XMLSecCharacters characters = (XMLSecCharacters)charactersIterator.next();
               this.outputAsEvent(outputProcessorChain, characters);
               charactersIterator.remove();
            }
         }

         this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_CipherValue);
         this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_CipherData);
         this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_xenc_EncryptedData);
      }

      protected EncryptionPartDef getEncryptionPartDef() {
         return this.encryptionPartDef;
      }

      protected void setEncryptionPartDef(EncryptionPartDef encryptionPartDef) {
         this.encryptionPartDef = encryptionPartDef;
      }

      protected XMLSecStartElement getXmlSecStartElement() {
         return this.xmlSecStartElement;
      }

      protected void setXmlSecStartElement(XMLSecStartElement xmlSecStartElement) {
         this.xmlSecStartElement = xmlSecStartElement;
      }

      public String getEncoding() {
         return this.encoding;
      }

      public void setEncoding(String encoding) {
         this.encoding = encoding;
      }
   }
}
