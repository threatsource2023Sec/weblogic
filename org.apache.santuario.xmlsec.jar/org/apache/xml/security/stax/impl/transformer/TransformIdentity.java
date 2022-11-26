package org.apache.xml.security.stax.impl.transformer;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public class TransformIdentity implements Transformer {
   private static XMLOutputFactory xmlOutputFactory;
   private static XMLInputFactory xmlInputFactory;
   private OutputStream outputStream;
   private XMLEventWriter xmlEventWriterForOutputStream;
   private Transformer transformer;
   private ChildOutputMethod childOutputMethod;

   protected static XMLOutputFactory getXmlOutputFactory() {
      Class var0 = TransformIdentity.class;
      synchronized(TransformIdentity.class) {
         if (xmlOutputFactory == null) {
            xmlOutputFactory = XMLOutputFactory.newInstance();
         }
      }

      return xmlOutputFactory;
   }

   public static XMLInputFactory getXmlInputFactory() {
      Class var0 = TransformIdentity.class;
      synchronized(TransformIdentity.class) {
         if (xmlInputFactory == null) {
            xmlInputFactory = XMLInputFactory.newInstance();
         }
      }

      return xmlInputFactory;
   }

   public void setOutputStream(OutputStream outputStream) throws XMLSecurityException {
      this.outputStream = outputStream;
   }

   protected OutputStream getOutputStream() {
      return this.outputStream;
   }

   protected XMLEventWriter getXmlEventWriterForOutputStream() throws XMLStreamException {
      if (this.xmlEventWriterForOutputStream != null) {
         return this.xmlEventWriterForOutputStream;
      } else {
         return this.outputStream != null ? (this.xmlEventWriterForOutputStream = getXmlOutputFactory().createXMLEventWriter(new FilterOutputStream(this.outputStream) {
            public void close() throws IOException {
               super.flush();
            }
         })) : null;
      }
   }

   public void setTransformer(Transformer transformer) throws XMLSecurityException {
      this.transformer = transformer;
   }

   protected Transformer getTransformer() {
      return this.transformer;
   }

   public void setProperties(Map properties) throws XMLSecurityException {
      throw new UnsupportedOperationException("no properties supported");
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
      if (this.getXmlEventWriterForOutputStream() != null) {
         this.getXmlEventWriterForOutputStream().add(xmlSecEvent);
      } else {
         if (this.childOutputMethod == null) {
            XMLSecurityConstants.TransformMethod preferredChildTransformMethod = this.getTransformer().getPreferredTransformMethod(XMLSecurityConstants.TransformMethod.XMLSecEvent);
            switch (preferredChildTransformMethod) {
               case XMLSecEvent:
                  this.childOutputMethod = new ChildOutputMethod() {
                     public void transform(Object object) throws XMLStreamException {
                        TransformIdentity.this.getTransformer().transform((XMLSecEvent)object);
                     }

                     public void doFinal() throws XMLStreamException {
                        TransformIdentity.this.getTransformer().doFinal();
                     }
                  };
                  break;
               case InputStream:
                  this.childOutputMethod = new ChildOutputMethod() {
                     private UnsyncByteArrayOutputStream baos;
                     private XMLEventWriter xmlEventWriter;

                     public void transform(Object object) throws XMLStreamException {
                        if (this.xmlEventWriter == null) {
                           this.baos = new UnsyncByteArrayOutputStream();
                           this.xmlEventWriter = TransformIdentity.getXmlOutputFactory().createXMLEventWriter(this.baos);
                        }

                        this.xmlEventWriter.add((XMLSecEvent)object);
                     }

                     public void doFinal() throws XMLStreamException {
                        this.xmlEventWriter.close();

                        try {
                           InputStream is = new UnsyncByteArrayInputStream(this.baos.toByteArray());
                           Throwable var2 = null;

                           try {
                              TransformIdentity.this.getTransformer().transform((InputStream)is);
                              TransformIdentity.this.getTransformer().doFinal();
                           } catch (Throwable var12) {
                              var2 = var12;
                              throw var12;
                           } finally {
                              if (var2 != null) {
                                 try {
                                    is.close();
                                 } catch (Throwable var11) {
                                    var2.addSuppressed(var11);
                                 }
                              } else {
                                 is.close();
                              }

                           }

                        } catch (IOException var14) {
                           throw new XMLStreamException(var14);
                        }
                     }
                  };
            }
         }

         if (this.childOutputMethod != null) {
            this.childOutputMethod.transform(xmlSecEvent);
         }
      }

   }

   public void transform(final InputStream inputStream) throws XMLStreamException {
      if (this.getOutputStream() != null) {
         try {
            XMLSecurityUtils.copy(inputStream, this.getOutputStream());
         } catch (IOException var3) {
            throw new XMLStreamException(var3);
         }
      } else {
         if (this.childOutputMethod == null) {
            XMLSecurityConstants.TransformMethod preferredChildTransformMethod = this.getTransformer().getPreferredTransformMethod(XMLSecurityConstants.TransformMethod.InputStream);
            switch (preferredChildTransformMethod) {
               case XMLSecEvent:
                  this.childOutputMethod = new ChildOutputMethod() {
                     private XMLEventReaderInputProcessor xmlEventReaderInputProcessor;

                     public void transform(Object object) throws XMLStreamException {
                        if (this.xmlEventReaderInputProcessor == null) {
                           this.xmlEventReaderInputProcessor = new XMLEventReaderInputProcessor((XMLSecurityProperties)null, TransformIdentity.getXmlInputFactory().createXMLStreamReader(inputStream));
                        }

                        try {
                           XMLSecEvent xmlSecEvent;
                           do {
                              xmlSecEvent = this.xmlEventReaderInputProcessor.processNextEvent((InputProcessorChain)null);
                              TransformIdentity.this.getTransformer().transform(xmlSecEvent);
                           } while(xmlSecEvent.getEventType() != 8);

                        } catch (XMLSecurityException var3) {
                           throw new XMLStreamException(var3);
                        }
                     }

                     public void doFinal() throws XMLStreamException {
                        TransformIdentity.this.getTransformer().doFinal();
                     }
                  };
                  break;
               case InputStream:
                  this.childOutputMethod = new ChildOutputMethod() {
                     public void transform(Object object) throws XMLStreamException {
                        TransformIdentity.this.getTransformer().transform(inputStream);
                     }

                     public void doFinal() throws XMLStreamException {
                        TransformIdentity.this.getTransformer().doFinal();
                     }
                  };
            }
         }

         if (this.childOutputMethod != null) {
            this.childOutputMethod.transform(inputStream);
         }
      }

   }

   public void doFinal() throws XMLStreamException {
      if (this.xmlEventWriterForOutputStream != null) {
         this.xmlEventWriterForOutputStream.close();
      }

      if (this.childOutputMethod != null) {
         this.childOutputMethod.doFinal();
      } else if (this.transformer != null) {
         this.transformer.doFinal();
      }

   }

   interface ChildOutputMethod {
      void transform(Object var1) throws XMLStreamException;

      void doFinal() throws XMLStreamException;
   }
}
