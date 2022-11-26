package org.apache.xml.security.stax.impl.transformer;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.utils.UnsyncByteArrayInputStream;
import org.apache.xml.security.utils.UnsyncByteArrayOutputStream;

public class TransformBase64Decode extends TransformIdentity {
   private TransformIdentity.ChildOutputMethod childOutputMethod;

   public void setOutputStream(OutputStream outputStream) throws XMLSecurityException {
      super.setOutputStream(new Base64OutputStream(new FilterOutputStream(outputStream) {
         public void close() throws IOException {
            super.flush();
         }
      }, false));
   }

   public XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod forInput) {
      switch (forInput) {
         case XMLSecEvent:
            return XMLSecurityConstants.TransformMethod.InputStream;
         case InputStream:
            return XMLSecurityConstants.TransformMethod.InputStream;
         default:
            throw new IllegalArgumentException("Unsupported class " + forInput.name());
      }
   }

   public void transform(XMLSecEvent xmlSecEvent) throws XMLStreamException {
      int eventType = xmlSecEvent.getEventType();
      switch (eventType) {
         case 4:
            if (this.getOutputStream() != null) {
               try {
                  this.getOutputStream().write(xmlSecEvent.asCharacters().getData().getBytes());
               } catch (IOException var4) {
                  throw new XMLStreamException(var4);
               }
            } else {
               if (this.childOutputMethod == null) {
                  XMLSecurityConstants.TransformMethod preferredChildTransformMethod = this.getTransformer().getPreferredTransformMethod(XMLSecurityConstants.TransformMethod.XMLSecEvent);
                  switch (preferredChildTransformMethod) {
                     case XMLSecEvent:
                        this.childOutputMethod = new TransformIdentity.ChildOutputMethod() {
                           private UnsyncByteArrayOutputStream byteArrayOutputStream;
                           private Base64OutputStream base64OutputStream;

                           public void transform(Object object) throws XMLStreamException {
                              if (this.base64OutputStream == null) {
                                 this.byteArrayOutputStream = new UnsyncByteArrayOutputStream();
                                 this.base64OutputStream = new Base64OutputStream(this.byteArrayOutputStream, false);
                              }

                              try {
                                 this.base64OutputStream.write((byte[])object);
                              } catch (IOException var3) {
                                 throw new XMLStreamException(var3);
                              }
                           }

                           public void doFinal() throws XMLStreamException {
                              try {
                                 this.base64OutputStream.close();
                              } catch (IOException var14) {
                                 throw new XMLStreamException(var14);
                              }

                              try {
                                 InputStream is = new UnsyncByteArrayInputStream(this.byteArrayOutputStream.toByteArray());
                                 Throwable var2 = null;

                                 try {
                                    XMLEventReaderInputProcessor xmlEventReaderInputProcessor = new XMLEventReaderInputProcessor((XMLSecurityProperties)null, TransformIdentity.getXmlInputFactory().createXMLStreamReader(is));

                                    XMLSecEvent xmlSecEvent;
                                    do {
                                       xmlSecEvent = xmlEventReaderInputProcessor.processNextEvent((InputProcessorChain)null);
                                       TransformBase64Decode.this.getTransformer().transform(xmlSecEvent);
                                    } while(xmlSecEvent.getEventType() != 8);
                                 } catch (Throwable var15) {
                                    var2 = var15;
                                    throw var15;
                                 } finally {
                                    if (var2 != null) {
                                       try {
                                          is.close();
                                       } catch (Throwable var13) {
                                          var2.addSuppressed(var13);
                                       }
                                    } else {
                                       is.close();
                                    }

                                 }
                              } catch (IOException | XMLSecurityException var17) {
                                 throw new XMLStreamException(var17);
                              }

                              TransformBase64Decode.this.getTransformer().doFinal();
                           }
                        };
                        break;
                     case InputStream:
                        this.childOutputMethod = new TransformIdentity.ChildOutputMethod() {
                           private UnsyncByteArrayOutputStream byteArrayOutputStream;
                           private Base64OutputStream base64OutputStream;

                           public void transform(Object object) throws XMLStreamException {
                              if (this.base64OutputStream == null) {
                                 this.byteArrayOutputStream = new UnsyncByteArrayOutputStream();
                                 this.base64OutputStream = new Base64OutputStream(this.byteArrayOutputStream, false);
                              }

                              try {
                                 this.base64OutputStream.write((byte[])object);
                              } catch (IOException var3) {
                                 throw new XMLStreamException(var3);
                              }
                           }

                           public void doFinal() throws XMLStreamException {
                              try {
                                 this.base64OutputStream.close();
                              } catch (IOException var14) {
                                 throw new XMLStreamException(var14);
                              }

                              try {
                                 InputStream is = new UnsyncByteArrayInputStream(this.byteArrayOutputStream.toByteArray());
                                 Throwable var2 = null;

                                 try {
                                    TransformBase64Decode.this.getTransformer().transform((InputStream)is);
                                    TransformBase64Decode.this.getTransformer().doFinal();
                                 } catch (Throwable var13) {
                                    var2 = var13;
                                    throw var13;
                                 } finally {
                                    if (var2 != null) {
                                       try {
                                          is.close();
                                       } catch (Throwable var12) {
                                          var2.addSuppressed(var12);
                                       }
                                    } else {
                                       is.close();
                                    }

                                 }

                              } catch (IOException var16) {
                                 throw new XMLStreamException(var16);
                              }
                           }
                        };
                  }
               }

               this.childOutputMethod.transform(xmlSecEvent.asCharacters().getData().getBytes());
            }
         default:
      }
   }

   public void transform(InputStream inputStream) throws XMLStreamException {
      if (this.getOutputStream() != null) {
         super.transform(inputStream);
      } else {
         super.transform((InputStream)(new Base64InputStream(inputStream, false)));
      }

   }

   public void doFinal() throws XMLStreamException {
      if (this.getOutputStream() != null) {
         try {
            this.getOutputStream().close();
         } catch (IOException var2) {
            throw new XMLStreamException(var2);
         }
      }

      if (this.childOutputMethod != null) {
         this.childOutputMethod.doFinal();
      } else if (this.getTransformer() != null) {
         this.getTransformer().doFinal();
      }

   }
}
