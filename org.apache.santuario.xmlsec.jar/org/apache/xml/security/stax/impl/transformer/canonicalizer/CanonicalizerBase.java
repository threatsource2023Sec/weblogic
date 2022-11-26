package org.apache.xml.security.stax.impl.transformer.canonicalizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.c14n.implementations.UtfHelpper;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecComment;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecProcessingInstruction;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.stax.impl.transformer.TransformIdentity;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public abstract class CanonicalizerBase extends TransformIdentity {
   static final byte[] _END_PI = new byte[]{63, 62};
   static final byte[] _BEGIN_PI = new byte[]{60, 63};
   static final byte[] _END_COMM = new byte[]{45, 45, 62};
   static final byte[] _BEGIN_COMM = new byte[]{60, 33, 45, 45};
   static final byte[] __XA_ = new byte[]{38, 35, 120, 65, 59};
   static final byte[] __X9_ = new byte[]{38, 35, 120, 57, 59};
   static final byte[] _QUOT_ = new byte[]{38, 113, 117, 111, 116, 59};
   static final byte[] __XD_ = new byte[]{38, 35, 120, 68, 59};
   static final byte[] _GT_ = new byte[]{38, 103, 116, 59};
   static final byte[] _LT_ = new byte[]{38, 108, 116, 59};
   static final byte[] _END_TAG = new byte[]{60, 47};
   static final byte[] _AMP_ = new byte[]{38, 97, 109, 112, 59};
   static final byte[] EQUAL_STRING = new byte[]{61, 34};
   static final byte[] NEWLINE = new byte[]{10};
   protected static final String XML = "xml";
   protected static final String XMLNS = "xmlns";
   protected static final char DOUBLEPOINT = ':';
   private static final Map cache = new WeakHashMap();
   private final C14NStack outputStack = new C14NStack();
   private boolean includeComments = false;
   private DocumentLevel currentDocumentLevel;
   protected boolean firstCall;

   public CanonicalizerBase(boolean includeComments) {
      this.currentDocumentLevel = CanonicalizerBase.DocumentLevel.NODE_BEFORE_DOCUMENT_ELEMENT;
      this.firstCall = true;
      this.includeComments = includeComments;
   }

   public void setProperties(Map properties) throws XMLSecurityException {
      throw new UnsupportedOperationException("InclusiveNamespace-PrefixList not supported");
   }

   public void setTransformer(Transformer transformer) throws XMLSecurityException {
      this.setOutputStream(new UnsyncByteArrayOutputStream());
      super.setTransformer(transformer);
   }

   protected List getCurrentUtilizedNamespaces(XMLSecStartElement xmlSecStartElement, C14NStack outputStack) {
      List utilizedNamespaces = Collections.emptyList();
      XMLSecNamespace elementNamespace = xmlSecStartElement.getElementNamespace();
      XMLSecNamespace found = (XMLSecNamespace)outputStack.containsOnStack(elementNamespace);
      if (found == null || found.getNamespaceURI() == null || !found.getNamespaceURI().equals(elementNamespace.getNamespaceURI())) {
         utilizedNamespaces = new ArrayList(2);
         ((List)utilizedNamespaces).add(elementNamespace);
         outputStack.peek().add(elementNamespace);
      }

      List declaredNamespaces = xmlSecStartElement.getOnElementDeclaredNamespaces();

      for(int i = 0; i < declaredNamespaces.size(); ++i) {
         XMLSecNamespace comparableNamespace = (XMLSecNamespace)declaredNamespaces.get(i);
         XMLSecNamespace resultNamespace = (XMLSecNamespace)outputStack.containsOnStack(comparableNamespace);
         if (resultNamespace == null || resultNamespace.getNamespaceURI() == null || !resultNamespace.getNamespaceURI().equals(comparableNamespace.getNamespaceURI())) {
            if (utilizedNamespaces == Collections.emptyList()) {
               utilizedNamespaces = new ArrayList(2);
            }

            ((List)utilizedNamespaces).add(comparableNamespace);
            outputStack.peek().add(comparableNamespace);
         }
      }

      List comparableAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

      for(int i = 0; i < comparableAttributes.size(); ++i) {
         XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)comparableAttributes.get(i);
         XMLSecNamespace attributeNamespace = xmlSecAttribute.getAttributeNamespace();
         if (!"xml".equals(attributeNamespace.getPrefix()) && attributeNamespace.getNamespaceURI() != null && !attributeNamespace.getNamespaceURI().isEmpty()) {
            XMLSecNamespace resultNamespace = (XMLSecNamespace)outputStack.containsOnStack(attributeNamespace);
            if (resultNamespace == null || resultNamespace.getNamespaceURI() == null || !resultNamespace.getNamespaceURI().equals(attributeNamespace.getNamespaceURI())) {
               if (utilizedNamespaces == Collections.emptyList()) {
                  utilizedNamespaces = new ArrayList(2);
               }

               ((List)utilizedNamespaces).add(attributeNamespace);
               outputStack.peek().add(attributeNamespace);
            }
         }
      }

      return (List)utilizedNamespaces;
   }

   protected List getCurrentUtilizedAttributes(XMLSecStartElement xmlSecStartElement, C14NStack outputStack) {
      List comparableAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();
      return (List)(comparableAttributes.isEmpty() ? Collections.emptyList() : new ArrayList(comparableAttributes));
   }

   protected List getInitialUtilizedNamespaces(XMLSecStartElement xmlSecStartElement, C14NStack outputStack) {
      List utilizedNamespaces = new ArrayList();
      List visibleNamespaces = new ArrayList();
      xmlSecStartElement.getNamespacesFromCurrentScope(visibleNamespaces);

      for(int i = 0; i < visibleNamespaces.size(); ++i) {
         XMLSecNamespace comparableNamespace = (XMLSecNamespace)visibleNamespaces.get(i);
         XMLSecNamespace found = (XMLSecNamespace)outputStack.containsOnStack(comparableNamespace);
         if (found != null) {
            utilizedNamespaces.remove(comparableNamespace);
         }

         outputStack.peek().add(comparableNamespace);
         if (!comparableNamespace.getNamespaceURI().isEmpty() || !comparableNamespace.getPrefix().isEmpty()) {
            utilizedNamespaces.add(comparableNamespace);
         }
      }

      return utilizedNamespaces;
   }

   protected List getInitialUtilizedAttributes(XMLSecStartElement xmlSecStartElement, C14NStack outputStack) {
      List utilizedAttributes = Collections.emptyList();
      List comparableAttributes = new ArrayList();
      xmlSecStartElement.getAttributesFromCurrentScope(comparableAttributes);

      for(int i = 0; i < comparableAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)comparableAttributes.get(i);
         if ("xml".equals(comparableAttribute.getName().getPrefix()) && outputStack.containsOnStack(comparableAttribute) == null) {
            if (utilizedAttributes == Collections.emptyList()) {
               utilizedAttributes = new ArrayList(2);
            }

            ((List)utilizedAttributes).add(comparableAttribute);
            outputStack.peek().add(comparableAttribute);
         }
      }

      List elementAttributes = xmlSecStartElement.getOnElementDeclaredAttributes();

      for(int i = 0; i < elementAttributes.size(); ++i) {
         XMLSecAttribute comparableAttribute = (XMLSecAttribute)elementAttributes.get(i);
         QName attributeName = comparableAttribute.getName();
         if (!"xml".equals(attributeName.getPrefix())) {
            if (utilizedAttributes == Collections.emptyList()) {
               utilizedAttributes = new ArrayList(2);
            }

            ((List)utilizedAttributes).add(comparableAttribute);
         }
      }

      return (List)utilizedAttributes;
   }

   public XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod forInput) {
      switch (forInput) {
         case XMLSecEvent:
            return XMLSecurityConstants.TransformMethod.XMLSecEvent;
         case InputStream:
            return XMLSecurityConstants.TransformMethod.InputStream;
         default:
            throw new IllegalArgumentException("Unsupported class " + forInput.name());
      }
   }

   public void transform(XMLSecEvent xmlSecEvent) throws XMLStreamException {
      try {
         OutputStream outputStream = this.getOutputStream();
         switch (xmlSecEvent.getEventType()) {
            case 1:
               XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
               this.currentDocumentLevel = CanonicalizerBase.DocumentLevel.NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT;
               this.outputStack.push(Collections.emptyList());
               Object utilizedNamespaces;
               Object utilizedAttributes;
               if (this.firstCall) {
                  utilizedNamespaces = new ArrayList();
                  utilizedAttributes = new ArrayList();
                  this.outputStack.peek().add(XMLSecEventFactory.createXMLSecNamespace((String)null, ""));
                  this.outputStack.push(Collections.emptyList());
                  ((List)utilizedNamespaces).addAll(this.getInitialUtilizedNamespaces(xmlSecStartElement, this.outputStack));
                  ((List)utilizedAttributes).addAll(this.getInitialUtilizedAttributes(xmlSecStartElement, this.outputStack));
                  this.firstCall = false;
               } else {
                  utilizedNamespaces = this.getCurrentUtilizedNamespaces(xmlSecStartElement, this.outputStack);
                  utilizedAttributes = this.getCurrentUtilizedAttributes(xmlSecStartElement, this.outputStack);
               }

               outputStream.write(60);
               String prefix = xmlSecStartElement.getName().getPrefix();
               if (prefix != null && !prefix.isEmpty()) {
                  UtfHelpper.writeByte(prefix, outputStream, cache);
                  outputStream.write(58);
               }

               String name = xmlSecStartElement.getName().getLocalPart();
               UtfHelpper.writeByte(name, outputStream, cache);
               int i;
               if (!((List)utilizedNamespaces).isEmpty()) {
                  Collections.sort((List)utilizedNamespaces);

                  for(i = 0; i < ((List)utilizedNamespaces).size(); ++i) {
                     XMLSecNamespace xmlSecNamespace = (XMLSecNamespace)((List)utilizedNamespaces).get(i);
                     if (!this.namespaceIsAbsolute(xmlSecNamespace.getNamespaceURI())) {
                        throw new XMLStreamException("namespace is relative encountered: " + xmlSecNamespace.getNamespaceURI());
                     }

                     if (xmlSecNamespace.isDefaultNamespaceDeclaration()) {
                        outputAttrToWriter((String)null, "xmlns", xmlSecNamespace.getNamespaceURI(), outputStream, cache);
                     } else {
                        outputAttrToWriter("xmlns", xmlSecNamespace.getPrefix(), xmlSecNamespace.getNamespaceURI(), outputStream, cache);
                     }
                  }
               }

               if (!((List)utilizedAttributes).isEmpty()) {
                  Collections.sort((List)utilizedAttributes);

                  for(i = 0; i < ((List)utilizedAttributes).size(); ++i) {
                     XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)((List)utilizedAttributes).get(i);
                     QName attributeName = xmlSecAttribute.getName();
                     String attributeNamePrefix = attributeName.getPrefix();
                     if (attributeNamePrefix != null && !attributeNamePrefix.isEmpty()) {
                        outputAttrToWriter(attributeNamePrefix, attributeName.getLocalPart(), xmlSecAttribute.getValue(), outputStream, cache);
                     } else {
                        outputAttrToWriter((String)null, attributeName.getLocalPart(), xmlSecAttribute.getValue(), outputStream, cache);
                     }
                  }
               }

               outputStream.write(62);
               break;
            case 2:
               XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
               String localPrefix = xmlSecEndElement.getName().getPrefix();
               outputStream.write(_END_TAG);
               if (localPrefix != null && !localPrefix.isEmpty()) {
                  UtfHelpper.writeByte(localPrefix, outputStream, cache);
                  outputStream.write(58);
               }

               UtfHelpper.writeByte(xmlSecEndElement.getName().getLocalPart(), outputStream, cache);
               outputStream.write(62);
               this.outputStack.pop();
               if (this.outputStack.size() == 1) {
                  this.currentDocumentLevel = CanonicalizerBase.DocumentLevel.NODE_AFTER_DOCUMENT_ELEMENT;
               }
               break;
            case 3:
               outputPItoWriter((XMLSecProcessingInstruction)xmlSecEvent, outputStream, this.currentDocumentLevel);
               break;
            case 4:
               if (this.currentDocumentLevel == CanonicalizerBase.DocumentLevel.NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT) {
                  outputTextToWriter(xmlSecEvent.asCharacters().getText(), outputStream);
               }
               break;
            case 5:
               if (this.includeComments) {
                  outputCommentToWriter((XMLSecComment)xmlSecEvent, outputStream, this.currentDocumentLevel);
               }
               break;
            case 6:
               if (this.currentDocumentLevel == CanonicalizerBase.DocumentLevel.NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT) {
                  outputTextToWriter(xmlSecEvent.asCharacters().getText(), outputStream);
               }
               break;
            case 7:
               this.currentDocumentLevel = CanonicalizerBase.DocumentLevel.NODE_BEFORE_DOCUMENT_ELEMENT;
            case 8:
            case 11:
            default:
               break;
            case 9:
               throw new XMLStreamException("illegal event :" + XMLSecurityUtils.getXMLEventAsString(xmlSecEvent));
            case 10:
               throw new XMLStreamException("illegal event :" + XMLSecurityUtils.getXMLEventAsString(xmlSecEvent));
            case 12:
               outputTextToWriter(xmlSecEvent.asCharacters().getData(), outputStream);
               break;
            case 13:
               throw new XMLStreamException("illegal event :" + XMLSecurityUtils.getXMLEventAsString(xmlSecEvent));
            case 14:
               throw new XMLStreamException("illegal event :" + XMLSecurityUtils.getXMLEventAsString(xmlSecEvent));
            case 15:
               throw new XMLStreamException("illegal event :" + XMLSecurityUtils.getXMLEventAsString(xmlSecEvent));
         }

      } catch (IOException var12) {
         throw new XMLStreamException(var12);
      }
   }

   public void transform(InputStream inputStream) throws XMLStreamException {
      XMLEventReaderInputProcessor xmlEventReaderInputProcessor = new XMLEventReaderInputProcessor((XMLSecurityProperties)null, getXmlInputFactory().createXMLStreamReader(inputStream));

      try {
         XMLSecEvent xmlSecEvent;
         do {
            xmlSecEvent = xmlEventReaderInputProcessor.processNextEvent((InputProcessorChain)null);
            this.transform(xmlSecEvent);
         } while(xmlSecEvent.getEventType() != 8);

      } catch (XMLSecurityException var4) {
         throw new XMLStreamException(var4);
      }
   }

   public void doFinal() throws XMLStreamException {
      if (this.getTransformer() != null) {
         UnsyncByteArrayOutputStream baos = (UnsyncByteArrayOutputStream)this.getOutputStream();

         try {
            InputStream is = new UnsyncByteArrayInputStream(baos.toByteArray());
            Throwable var3 = null;

            try {
               this.getTransformer().transform((InputStream)is);
               this.getTransformer().doFinal();
            } catch (Throwable var13) {
               var3 = var13;
               throw var13;
            } finally {
               if (var3 != null) {
                  try {
                     is.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  is.close();
               }

            }
         } catch (IOException var15) {
            throw new XMLStreamException(var15);
         }
      }

   }

   protected static void outputAttrToWriter(String prefix, String name, String value, OutputStream writer, Map cache) throws IOException {
      writer.write(32);
      if (prefix != null) {
         UtfHelpper.writeByte(prefix, writer, cache);
         UtfHelpper.writeCodePointToUtf8(58, writer);
      }

      UtfHelpper.writeByte(name, writer, cache);
      writer.write(EQUAL_STRING);
      int length = value.length();
      int i = 0;

      while(true) {
         byte[] toWrite;
         label27:
         while(true) {
            if (i >= length) {
               writer.write(34);
               return;
            }

            int c = value.codePointAt(i);
            i += Character.charCount(c);
            switch (c) {
               case 9:
                  toWrite = __X9_;
                  break label27;
               case 10:
                  toWrite = __XA_;
                  break label27;
               case 13:
                  toWrite = __XD_;
                  break label27;
               case 34:
                  toWrite = _QUOT_;
                  break label27;
               case 38:
                  toWrite = _AMP_;
                  break label27;
               case 60:
                  toWrite = _LT_;
                  break label27;
               default:
                  if (c < 128) {
                     writer.write(c);
                  } else {
                     UtfHelpper.writeCodePointToUtf8(c, writer);
                  }
            }
         }

         writer.write(toWrite);
      }
   }

   protected static void outputTextToWriter(String text, OutputStream writer) throws IOException {
      int length = text.length();
      int i = 0;

      while(true) {
         byte[] toWrite;
         label21:
         while(true) {
            if (i >= length) {
               return;
            }

            int c = text.codePointAt(i);
            i += Character.charCount(c);
            switch (c) {
               case 13:
                  toWrite = __XD_;
                  break label21;
               case 38:
                  toWrite = _AMP_;
                  break label21;
               case 60:
                  toWrite = _LT_;
                  break label21;
               case 62:
                  toWrite = _GT_;
                  break label21;
               default:
                  if (c < 128) {
                     writer.write(c);
                  } else {
                     UtfHelpper.writeCodePointToUtf8(c, writer);
                  }
            }
         }

         writer.write(toWrite);
      }
   }

   protected static void outputTextToWriter(char[] text, OutputStream writer) throws IOException {
      int length = text.length;

      for(int i = 0; i < length; ++i) {
         int c;
         if (Character.isHighSurrogate(text[i]) && i + 1 != length && Character.isLowSurrogate(text[i + 1])) {
            char var10000 = text[i];
            ++i;
            c = Character.toCodePoint(var10000, text[i]);
         } else {
            c = text[i];
         }

         byte[] toWrite;
         switch (c) {
            case 13:
               toWrite = __XD_;
               break;
            case 38:
               toWrite = _AMP_;
               break;
            case 60:
               toWrite = _LT_;
               break;
            case 62:
               toWrite = _GT_;
               break;
            default:
               if (c < 128) {
                  writer.write(c);
               } else {
                  UtfHelpper.writeCodePointToUtf8(c, writer);
               }
               continue;
         }

         writer.write(toWrite);
      }

   }

   protected static void outputPItoWriter(XMLSecProcessingInstruction currentPI, OutputStream writer, DocumentLevel position) throws IOException {
      if (position == CanonicalizerBase.DocumentLevel.NODE_AFTER_DOCUMENT_ELEMENT) {
         writer.write(NEWLINE);
      }

      writer.write(_BEGIN_PI);
      String target = currentPI.getTarget();
      int length = target.length();
      int i = 0;

      int i;
      while(i < length) {
         i = target.codePointAt(i);
         i += Character.charCount(i);
         if (i == 13) {
            writer.write(__XD_);
         } else if (i < 128) {
            writer.write(i);
         } else {
            UtfHelpper.writeCodePointToUtf8(i, writer);
         }
      }

      String data = currentPI.getData();
      length = data.length();
      if (length > 0) {
         writer.write(32);
         i = 0;

         while(i < length) {
            int c = data.codePointAt(i);
            i += Character.charCount(c);
            if (c == 13) {
               writer.write(__XD_);
            } else {
               UtfHelpper.writeCodePointToUtf8(c, writer);
            }
         }
      }

      writer.write(_END_PI);
      if (position == CanonicalizerBase.DocumentLevel.NODE_BEFORE_DOCUMENT_ELEMENT) {
         writer.write(NEWLINE);
      }

   }

   protected static void outputCommentToWriter(XMLSecComment currentComment, OutputStream writer, DocumentLevel position) throws IOException {
      if (position == CanonicalizerBase.DocumentLevel.NODE_AFTER_DOCUMENT_ELEMENT) {
         writer.write(NEWLINE);
      }

      writer.write(_BEGIN_COMM);
      String data = currentComment.getText();
      int length = data.length();
      int i = 0;

      while(i < length) {
         int c = data.codePointAt(i);
         i += Character.charCount(c);
         if (c == 13) {
            writer.write(__XD_);
         } else if (c < 128) {
            writer.write(c);
         } else {
            UtfHelpper.writeCodePointToUtf8(c, writer);
         }
      }

      writer.write(_END_COMM);
      if (position == CanonicalizerBase.DocumentLevel.NODE_BEFORE_DOCUMENT_ELEMENT) {
         writer.write(NEWLINE);
      }

   }

   private boolean namespaceIsAbsolute(String namespaceValue) {
      if (namespaceValue.isEmpty()) {
         return true;
      } else {
         return namespaceValue.indexOf(58) > 0;
      }
   }

   public static class C14NStack extends ArrayDeque {
      public Object containsOnStack(Object o) {
         Iterator elementIterator = super.iterator();

         while(elementIterator.hasNext()) {
            List list = (List)elementIterator.next();
            if (!list.isEmpty()) {
               int idx = list.lastIndexOf(o);
               if (idx != -1) {
                  return list.get(idx);
               }
            }
         }

         return null;
      }

      public List peek() {
         List list = (List)super.peekFirst();
         if (list == Collections.emptyList()) {
            super.removeFirst();
            list = new ArrayList();
            super.addFirst(list);
         }

         return (List)list;
      }

      public List peekFirst() {
         throw new UnsupportedOperationException("Use peek()");
      }
   }

   private static enum DocumentLevel {
      NODE_BEFORE_DOCUMENT_ELEMENT,
      NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT,
      NODE_AFTER_DOCUMENT_ELEMENT;
   }
}
