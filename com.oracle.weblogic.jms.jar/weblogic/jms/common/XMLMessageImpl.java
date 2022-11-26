package weblogic.jms.common;

import java.io.CharArrayWriter;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.extensions.XMLMessage;
import weblogic.jms.utils.xml.dom.DOMDeserializer;
import weblogic.jms.utils.xml.dom.DOMSerializer;
import weblogic.jms.utils.xml.stax.bin.BinaryXMLStreamReader;
import weblogic.jms.utils.xml.stax.bin.BinaryXMLStreamWriter;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.XXEUtils;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.StringInput;
import weblogic.utils.io.StringOutput;

public final class XMLMessageImpl extends MessageImpl implements XMLMessage, Externalizable {
   private static final long serialVersionUID = -7021112875012439613L;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte VERSIONMASK = 127;
   private static final boolean mydebug = false;
   private String text;
   private PayloadStream payloadToken;
   private PayloadText payloadText;
   private byte subflag = 0;
   private WeakReference selectedDocument;

   public XMLMessageImpl() {
   }

   public XMLMessageImpl(String xmlText) {
      this.text = xmlText;
   }

   public XMLMessageImpl(Document document) throws javax.jms.JMSException {
      this.setDocumentInternal(document);
   }

   public void setText(String text) throws javax.jms.JMSException {
      this.writeMode();
      this.text = text;
      this.payloadToken = null;
      this.payloadText = null;
   }

   public String getText() throws javax.jms.JMSException {
      this.decompressMessageBody();
      if (this.text != null) {
         return this.text;
      } else if (this.payloadText != null) {
         try {
            this.text = this.payloadText.readUTF8();
            this.payloadText = null;
            return this.text;
         } catch (IOException var3) {
            throw new JMSException(var3);
         }
      } else {
         Document document = null;
         if (this.payloadToken != null) {
            try {
               document = detokenize(this.payloadToken);
            } catch (ParserConfigurationException var5) {
               throw serializationJMSException(var5);
            } catch (XMLStreamException var6) {
               throw serializationJMSException(var6);
            } catch (IOException var7) {
               throw new JMSException(var7);
            }
         }

         if (document != null) {
            try {
               this.text = serialize(document);
            } catch (TransformerException var4) {
               throw serializationJMSException(var4);
            }

            return this.text;
         } else {
            return null;
         }
      }
   }

   public synchronized void setDocument(Document document) throws javax.jms.JMSException {
      this.writeMode();
      this.text = null;
      this.payloadText = null;
      this.setDocumentInternal(document);
   }

   private void setDocumentInternal(Document document) throws javax.jms.JMSException {
      try {
         if (document == null) {
            this.payloadToken = null;
         } else {
            this.payloadToken = tokenize(document);
         }

      } catch (XMLStreamException var3) {
         throw serializationJMSException(var3);
      } catch (IOException var4) {
         throw new JMSException(var4);
      }
   }

   public Document getDocument() throws javax.jms.JMSException {
      this.decompressMessageBody();
      if (this.payloadToken != null) {
         try {
            return detokenize(this.payloadToken);
         } catch (ParserConfigurationException var2) {
            throw deserializationJMSException(var2);
         } catch (XMLStreamException var3) {
            throw deserializationJMSException(var3);
         } catch (IOException var4) {
            throw new JMSException(var4);
         }
      } else {
         if (this.payloadText != null) {
            try {
               this.text = this.payloadText.readUTF8();
            } catch (IOException var8) {
               throw new JMSException(var8);
            }

            this.payloadText = null;
         }

         if (this.text != null) {
            try {
               return deserialize(this.text);
            } catch (ParserConfigurationException var5) {
               throw deserializationJMSException(var5);
            } catch (SAXException var6) {
               throw deserializationJMSException(var6);
            } catch (IOException var7) {
               throw deserializationJMSException(var7);
            }
         } else {
            return null;
         }
      }
   }

   public byte getType() {
      return 7;
   }

   public MessageImpl copy() throws javax.jms.JMSException {
      XMLMessageImpl xmi = new XMLMessageImpl();
      this.copy(xmi);
      xmi.text = this.text;
      if (this.payloadToken != null) {
         xmi.payloadToken = this.payloadToken.copyPayloadWithoutSharedStream();
      }

      if (this.payloadText != null) {
         xmi.payloadText = this.payloadText.copyPayloadWithoutSharedText();
      }

      xmi.subflag = this.subflag;
      return xmi;
   }

   public static Document deserialize(String text) throws ParserConfigurationException, SAXException, IOException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("XMLMessageImpl.parse(): message: " + text);
      }

      DocumentBuilderFactory dbf = XXEUtils.createDocumentBuilderFactoryInstance();
      DocumentBuilder docBuilder = dbf.newDocumentBuilder();
      return docBuilder.parse(new InputSource(new StringReader(text)));
   }

   private static String serialize(Document document) throws TransformerException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("XMLMessageImpl.serialize(): " + document);
      }

      CharArrayWriter caw = new CharArrayWriter();
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer idTransform = tf.newTransformer();
      Source input = new DOMSource(document);
      Result output = new StreamResult(caw);
      idTransform.transform(input, output);
      return caw.toString();
   }

   private static PayloadStream tokenize(Document document) throws XMLStreamException, IOException {
      BufferOutputStream bos = PayloadFactoryImpl.createOutputStream();
      BinaryXMLStreamWriter xsw = new BinaryXMLStreamWriter(bos);
      DOMSerializer.serialize(document, xsw);
      return (PayloadStream)bos.moveToPayload();
   }

   private static Document detokenize(PayloadStream payloadToken) throws XMLStreamException, ParserConfigurationException, IOException {
      InputStream is = payloadToken.getInputStream();
      BinaryXMLStreamReader xsr = new BinaryXMLStreamReader(is);
      return (Document)DOMDeserializer.deserialize(xsr);
   }

   public Object parse() throws Exception {
      Document doc = null;
      if (this.selectedDocument != null) {
         doc = (Document)this.selectedDocument.get();
      }

      if (doc != null) {
         return doc;
      } else {
         this.selectedDocument = null;
         doc = this.getDocumentForSelection();
         if (doc != null) {
            this.selectedDocument = new WeakReference(doc);
         }

         return doc;
      }
   }

   public void nullBody() {
      this.text = null;
      this.payloadText = null;
      this.payloadToken = null;
   }

   private static javax.jms.JMSException serializationJMSException(Exception cause) {
      javax.jms.JMSException ret = new javax.jms.JMSException("failed to serialize message");
      ret.initCause(cause);
      return ret;
   }

   private static javax.jms.JMSException deserializationJMSException(Exception cause) {
      javax.jms.JMSException ret = new javax.jms.JMSException("failed to deserialize message");
      ret.initCause(cause);
      return ret;
   }

   public String toString() {
      StringBuffer ret = new StringBuffer();
      ret.append("XMLMessage[id=" + this.getJMSMessageID() + ",text=\n");

      try {
         ret.append(this.getText());
      } catch (javax.jms.JMSException var3) {
         ret.append(StackTraceUtilsClient.throwable2StackTrace(var3));
      }

      return ret.toString();
   }

   public void writeExternal(ObjectOutput tOut) throws IOException {
      super.writeExternal(tOut);
      int compressionThreshold = Integer.MAX_VALUE;
      byte flag = false;
      boolean readStringAsObject = true;
      ObjectOutput out;
      if (tOut instanceof MessageImpl.JMSObjectOutputWrapper) {
         out = ((MessageImpl.JMSObjectOutputWrapper)tOut).getInnerObjectOutput();
         compressionThreshold = ((MessageImpl.JMSObjectOutputWrapper)tOut).getCompressionThreshold();
         readStringAsObject = ((MessageImpl.JMSObjectOutputWrapper)tOut).getReadStringAsObject();
      } else {
         out = tOut;
      }

      byte flag;
      if (out instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)out).getPeerInfo();
         if (pi.getMajor() >= 9) {
            if (this.needToDecompressDueToInterop(out)) {
               flag = 3;
            } else {
               flag = (byte)(3 | (this.shouldCompress(out, compressionThreshold) ? -128 : 0));
            }
         } else {
            flag = 2;
         }
      } else {
         flag = (byte)(3 | (this.shouldCompress(out, compressionThreshold) ? -128 : 0));
      }

      out.writeByte(flag);
      if ((flag & 127) == 3) {
         if (this.isCompressed()) {
            if (this.needToDecompressDueToInterop(out)) {
               Payload payload = this.decompress();
               String text = null;
               if ((this.subflag & 2) != 0) {
                  text = ((PayloadText)payload).readUTF8();
               } else if ((this.subflag & 4) != 0) {
                  ObjectInputStream ouinput = new FilteringObjectInputStream(payload.getInputStream());

                  try {
                     text = (String)ouinput.readObject();
                  } catch (ClassNotFoundException var17) {
                     throw new IOException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var17);
                  }
               }

               if (text != null && text.length() > 0) {
                  out.writeBoolean(true);
                  if (out instanceof StringOutput) {
                     out.writeByte(2 | (readStringAsObject ? 128 : 0));
                     ((StringOutput)out).writeUTF8(text);
                  } else {
                     out.writeByte(4);
                     out.writeObject(text);
                  }
               }

               if ((this.subflag & 1) != 0) {
                  out.writeBoolean(true);
                  out.writeByte(1);
                  ((PayloadStream)payload).writeLengthAndData(out);
               }
            } else {
               out.writeByte(this.subflag);
               this.flushCompressedMessageBody(out);
            }
         } else if ((flag & -128) != 0) {
            if (this.payloadToken != null) {
               out.writeByte(1);
               this.writeExternalCompressPayload(out, this.payloadToken);
            } else if (this.payloadText != null) {
               out.writeByte(2);
               this.writeExternalCompressPayload(out, this.payloadText);
            } else {
               out.writeByte(4);
               this.writeExternalCompressPayload(out, PayloadFactoryImpl.convertObjectToPayload(this.text));
            }
         } else if (this.payloadToken != null) {
            out.writeBoolean(true);
            out.writeByte(1);
            this.payloadToken.writeLengthAndData(out);
         } else if (this.text != null && this.text.length() > 0) {
            out.writeBoolean(true);
            if (out instanceof StringOutput) {
               out.writeByte(2 | (readStringAsObject ? 128 : 0));
               ((StringOutput)out).writeUTF8(this.text);
            } else {
               out.writeByte(4);
               out.writeObject(this.text);
            }
         } else if (this.payloadText != null) {
            out.writeBoolean(true);
            out.writeByte(2 | (readStringAsObject ? 128 : 0));
            this.payloadText.writeLengthAndData(out);
         } else {
            out.writeBoolean(false);
         }
      } else {
         String interopStr = null;
         PayloadText payloadTextInterop = null;
         PayloadStream payloadStreamInterop = null;
         byte interopflag = 0;
         if (this.isCompressed()) {
            interopflag = this.subflag;
         } else if (this.text != null) {
            interopStr = this.text;
         } else if (this.payloadText != null) {
            payloadTextInterop = this.payloadText;
            interopflag = 2;
         } else if (this.payloadToken != null) {
            payloadStreamInterop = this.payloadToken;
            interopflag = 1;
         }

         if (interopStr == null && (payloadTextInterop != null || payloadStreamInterop != null)) {
            if ((interopflag & 2) != 0) {
               interopStr = this.payloadText.readUTF8();
            } else {
               BufferInputStream document;
               if ((interopflag & 4) != 0) {
                  try {
                     document = payloadTextInterop.getInputStream();
                     ObjectInputStream ouinput = new FilteringObjectInputStream(document);
                     interopStr = (String)ouinput.readObject();
                  } catch (ClassNotFoundException var16) {
                     IOException ioe = new IOException(JMSClientExceptionLogger.logErrorInteropXMLMessageLoggable().getMessage());
                     ioe.initCause(var16);
                     throw ioe;
                  }
               } else if ((interopflag & 1) != 0) {
                  document = null;

                  IOException ioe;
                  Document document;
                  try {
                     document = detokenize(payloadStreamInterop);
                  } catch (ParserConfigurationException var14) {
                     ioe = new IOException(JMSClientExceptionLogger.logErrorInteropXMLMessageLoggable().getMessage());
                     ioe.initCause(var14);
                     throw ioe;
                  } catch (XMLStreamException var15) {
                     ioe = new IOException(JMSClientExceptionLogger.logErrorInteropXMLMessageLoggable().getMessage());
                     ioe.initCause(var15);
                     throw ioe;
                  }

                  try {
                     interopStr = serialize(document);
                  } catch (TransformerException var13) {
                     ioe = new IOException(JMSClientExceptionLogger.logErrorInteropXMLMessageLoggable().getMessage());
                     ioe.initCause(var13);
                     throw ioe;
                  }
               }
            }
         }

         if (interopStr != null && interopStr.length() > 0) {
            out.writeBoolean(true);
            out.writeObject(interopStr);
         } else {
            out.writeBoolean(false);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      byte vrsn = in.readByte();
      switch (vrsn & 127) {
         case 1:
            this.text = in.readUTF();
            break;
         case 2:
            if (in.readBoolean()) {
               this.text = (String)in.readObject();
            }
            break;
         case 3:
            if ((vrsn & -128) != 0) {
               this.subflag = in.readByte();
               this.readExternalCompressedMessageBody(in);
            } else {
               boolean hasData = in.readBoolean();
               if (hasData) {
                  byte mysubflag = in.readByte();
                  if ((mysubflag & 1) != 0) {
                     this.payloadToken = (PayloadStream)PayloadFactoryImpl.createPayload((InputStream)in);
                  } else if ((mysubflag & 2) != 0) {
                     if (in instanceof StringInput && (mysubflag & 128) != 0) {
                        this.text = ((StringInput)in).readUTF8();
                     } else {
                        this.payloadText = (PayloadText)PayloadFactoryImpl.createPayload((InputStream)in);
                     }
                  } else if ((mysubflag & 4) != 0) {
                     this.text = (String)in.readObject();
                  }
               }
            }
            break;
         default:
            throw JMSUtilities.versionIOException(vrsn, 1, 3);
      }

   }

   public long getPayloadSize() {
      if (super.bodySize != -1L) {
         return super.bodySize;
      } else if (this.isCompressed()) {
         return super.bodySize = (long)this.getCompressedMessageBodySize();
      } else if (this.text != null) {
         return super.bodySize = (long)this.text.length();
      } else if (this.payloadToken != null) {
         return super.bodySize = (long)this.payloadToken.getLength();
      } else {
         return this.payloadText != null ? (super.bodySize = (long)this.payloadText.getLength()) : (super.bodySize = 0L);
      }
   }

   public void decompressMessageBody() throws javax.jms.JMSException {
      if (this.isCompressed()) {
         try {
            Payload payload = this.decompress();
            if ((this.subflag & 2) != 0) {
               this.text = ((PayloadText)payload).readUTF8();
            } else if ((this.subflag & 4) != 0) {
               ObjectInputStream ouinput = new FilteringObjectInputStream(payload.getInputStream());
               this.text = (String)ouinput.readObject();
            } else if ((this.subflag & 1) != 0) {
               this.payloadToken = (PayloadStream)payload;
            }

            this.cleanupCompressedMessageBody();
         } catch (IOException var3) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var3);
         } catch (ClassNotFoundException var4) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var4);
         }
      }
   }

   private Document getDocumentForSelection() throws javax.jms.JMSException {
      Document doc = null;
      String text = this.text;
      PayloadStream localPayloadToken = this.payloadToken;
      PayloadText localPayloadText = this.payloadText;
      if (this.isCompressed()) {
         try {
            Payload payload = this.decompress();
            if ((this.subflag & 2) != 0) {
               text = ((PayloadText)payload).readUTF8();
            } else if ((this.subflag & 4) != 0) {
               ObjectInputStream ouinput = new FilteringObjectInputStream(payload.getInputStream());
               text = (String)ouinput.readObject();
            } else if ((this.subflag & 1) != 0) {
               localPayloadToken = (PayloadStream)payload;
            }
         } catch (IOException var14) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var14);
         } catch (ClassNotFoundException var15) {
            throw new JMSException(JMSClientExceptionLogger.logErrorDeserializeMessageBodyLoggable().getMessage(), var15);
         }
      }

      if (localPayloadToken != null) {
         try {
            doc = detokenize(localPayloadToken);
         } catch (ParserConfigurationException var11) {
            throw deserializationJMSException(var11);
         } catch (XMLStreamException var12) {
            throw deserializationJMSException(var12);
         } catch (IOException var13) {
            throw deserializationJMSException(var13);
         }
      }

      if (localPayloadText != null) {
         try {
            text = localPayloadText.readUTF8();
         } catch (IOException var10) {
            throw new JMSException(var10);
         }
      }

      if (text != null) {
         try {
            doc = deserialize(text);
         } catch (ParserConfigurationException var7) {
            throw deserializationJMSException(var7);
         } catch (SAXException var8) {
            throw deserializationJMSException(var8);
         } catch (IOException var9) {
            throw deserializationJMSException(var9);
         }
      }

      return doc;
   }
}
