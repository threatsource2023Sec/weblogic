package weblogic.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.XMLLogger;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.util.DocumentType;

/** @deprecated */
@Deprecated
public class XMLInputSource extends InputSource {
   private InputSource inputSource;
   private OpenBufferedInputStream byteStream;
   private OpenBufferedReader characterStream;
   private String localRootTag;
   private String qRootTag;
   private String rootTagInternal;
   private String publicId;
   private String publicIdInternal;
   private String doctypeSystemId;
   private String doctypeSystemIdInternal;
   private String namespaceURI;
   private String DTDName;
   private boolean startElementPassed = false;
   private boolean parsed = false;
   private Map externalEntitiesSystemIds;
   private Map externalEntitiesPublicIds;
   final int BYTE_LIMIT = 16384;

   public XMLInputSource() {
   }

   public XMLInputSource(String systemId) {
      if (systemId == null) {
         throw new IllegalArgumentException("Failed to create weblogic.xml.sax.XMLInputSource with systemId parameter. InputStream cannot be null.");
      } else {
         this.setSystemId(systemId);
      }
   }

   public XMLInputSource(InputStream byteStream) {
      super(byteStream);
      if (byteStream == null) {
         throw new IllegalArgumentException("Failed to create weblogic.xml.sax.XMLInputSource with java.io.InputStream parameter. InputStream cannot be null.");
      } else {
         this.setByteStream(byteStream);
      }
   }

   public XMLInputSource(Reader characterStream) {
      super(characterStream);
      if (characterStream == null) {
         throw new IllegalArgumentException("Failed to create weblogic.xml.sax.XMLInputSource with java.io.Reader parameter. Reader cannot be null.");
      } else {
         this.setCharacterStream(characterStream);
      }
   }

   public XMLInputSource(InputSource source) throws IOException {
      if (source == null) {
         throw new IllegalArgumentException("InputSource is missing.");
      } else {
         this.inputSource = source;
         Reader ioCharReader = source.getCharacterStream();
         if (ioCharReader != null) {
            this.setCharacterStream(ioCharReader);
         }

         InputStream ioByteStream = source.getByteStream();
         if (ioByteStream != null) {
            this.setByteStream(ioByteStream);
         }

         String id = source.getSystemId();
         if (id != null) {
            super.setSystemId(id);
            if (this.inputSource == null) {
               this.inputSource = new InputSource(id);
            } else {
               this.inputSource.setSystemId(id);
            }

            if (ioCharReader == null && ioByteStream == null) {
               try {
                  URL url = new URL(id);
                  this.setByteStream(url.openStream());
               } catch (MalformedURLException var6) {
               } catch (IOException var7) {
               }
            }
         }

      }
   }

   public String getPublicId() {
      return this.getPublicIdInternal();
   }

   public String getPublicIdInternal() {
      String id = super.getPublicId();
      if (id != null) {
         return id;
      } else if (this.parsed) {
         return this.publicIdInternal;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var3) {
            XMLLogger.logSAXException("Failed to retrieve PUBLIC id. " + var3.getMessage());
            return null;
         } catch (IOException var4) {
            XMLLogger.logIOException("Failed to retrieve PUBLIC id. " + var4.getMessage());
            return null;
         }

         return this.publicIdInternal;
      }
   }

   public String getDoctypeSystemId() {
      if (this.startElementPassed) {
         return this.doctypeSystemId;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve SYSTEM id. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve SYSTEM id. " + var3.getMessage());
            return null;
         }

         return this.doctypeSystemId;
      }
   }

   public String getDoctypeSystemIdInternal() {
      if (this.parsed) {
         return this.doctypeSystemIdInternal;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve SYSTEM id. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve SYSTEM id. " + var3.getMessage());
            return null;
         }

         return this.doctypeSystemIdInternal;
      }
   }

   public void setSystemId(String id) {
      super.setSystemId(id);
      if (this.inputSource == null) {
         this.inputSource = new InputSource(id);
      } else {
         this.inputSource.setSystemId(id);
      }

      if (this.getCharacterStream() == null && this.getByteStream() == null) {
         try {
            URL url = new URL(id);
            this.setByteStream(url.openStream());
         } catch (MalformedURLException var3) {
            XMLLogger.logIOException("Failed to set SYSTEM id. " + var3.getMessage());
         } catch (IOException var4) {
            XMLLogger.logIOException("Failed to set SYSTEM id. " + var4.getMessage());
         }
      }

   }

   public String getRootTag() {
      if (this.inputSource == null) {
         return null;
      } else {
         if (!this.startElementPassed) {
            try {
               this.parseDocumentTypeInternal();
            } catch (SAXException var2) {
               XMLLogger.logSAXException("Failed to retrieve root tag. " + var2.getMessage());
               return null;
            } catch (IOException var3) {
               XMLLogger.logIOException("Failed to retrieve root tag. " + var3.getMessage());
               return null;
            }
         }

         return this.namespaceURI != null && this.namespaceURI.equals("") ? this.localRootTag : this.qRootTag;
      }
   }

   public String getRootTagInternal() {
      if (this.inputSource == null) {
         return null;
      } else {
         if (!this.parsed) {
            try {
               this.parseDocumentTypeInternal();
            } catch (SAXException var2) {
               XMLLogger.logSAXException("Failed to retrieve root tag. " + var2.getMessage());
               return null;
            } catch (IOException var3) {
               XMLLogger.logIOException("Failed to retrieve root tag. " + var3.getMessage());
               return null;
            }
         }

         return this.rootTagInternal;
      }
   }

   public String getQualifiedRootTag() {
      if (this.startElementPassed) {
         return this.qRootTag;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve qualified root tag. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve qualified root tag. " + var3.getMessage());
            return null;
         }

         return this.qRootTag;
      }
   }

   public String getDTDName() {
      if (this.startElementPassed) {
         return this.DTDName;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve DTD name. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve DTD name. " + var3.getMessage());
            return null;
         }

         return this.DTDName;
      }
   }

   public String getLocalRootTag() {
      if (this.startElementPassed) {
         return this.localRootTag;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve local root tag. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve local root tag. " + var3.getMessage());
            return null;
         }

         return this.localRootTag;
      }
   }

   public String getNamespaceURI() {
      if (this.startElementPassed) {
         return this.namespaceURI;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve namespace URI. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve namespace URI. " + var3.getMessage());
            return null;
         }

         return this.namespaceURI;
      }
   }

   private void parseDocumentTypeInternal() throws SAXException, IOException {
      if (!this.parsed) {
         try {
            DocumentType dt = null;
            if (this.characterStream != null) {
               dt = new DocumentType(this.characterStream, false);
            } else if (this.byteStream != null) {
               dt = new DocumentType(new OpenBufferedReader(new InputStreamReader(this.byteStream)), false);
            }

            if (dt != null) {
               this.publicIdInternal = dt.getPublicId();
               this.doctypeSystemIdInternal = dt.getSystemId();
               this.rootTagInternal = dt.getRootElementTag();
            }
         } catch (XMLParsingException var5) {
         } finally {
            this.reset();
            this.parsed = true;
         }

      }
   }

   private void reset() throws IOException {
      try {
         if (this.characterStream != null) {
            this.characterStream.reset();
            this.characterStream.allowClose(true);
         } else if (this.byteStream != null) {
            this.byteStream.reset();
            this.byteStream.allowClose(true);
         }
      } catch (IOException var2) {
         XMLLogger.logIOException("Failed to retrieve PUBLIC id or SYSTEM id from the document. Decrease the number of char between the beginning of the document and its root element.");
      }

   }

   public void setCharacterStream(Reader characterStream) {
      if (characterStream == null) {
         throw new IllegalArgumentException("Character stream is missing.");
      } else {
         try {
            this.characterStream = new OpenBufferedReader(characterStream, 16384);
            this.characterStream.mark(16384);
            if (this.inputSource == null) {
               this.inputSource = new InputSource(this.characterStream);
            } else {
               this.inputSource.setCharacterStream(this.characterStream);
            }
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to set character stream. " + var3.getMessage());
         }

      }
   }

   public Reader getCharacterStream() {
      return this.characterStream;
   }

   public void setByteStream(InputStream ioByteStream) {
      if (ioByteStream == null) {
         throw new IllegalArgumentException("Byte stream is missing.");
      } else {
         this.byteStream = new OpenBufferedInputStream(ioByteStream, 16384);
         this.byteStream.mark(16384);
         if (this.inputSource == null) {
            this.inputSource = new InputSource(this.byteStream);
         } else {
            this.inputSource.setByteStream(this.byteStream);
         }

      }
   }

   public InputStream getByteStream() {
      return this.byteStream;
   }

   public String toString() {
      String inputSourceFields = "\nPublic Id = " + this.publicId + "\nSystem Id = " + this.doctypeSystemId + "\nNamespace URI = " + this.namespaceURI + "\nLocal Root Tag = " + this.localRootTag + "\nQualified Root Tag = " + this.qRootTag;
      return inputSourceFields;
   }

   public InputSource getInputSource() {
      return this.inputSource;
   }

   public Map getExternalEntitiesSystemIds() {
      if (this.startElementPassed) {
         return this.externalEntitiesSystemIds;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve external entities system ids. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve external entities system ids. " + var3.getMessage());
            return null;
         }

         return this.externalEntitiesSystemIds;
      }
   }

   public Map getExternalEntitiesPublicIds() {
      if (this.startElementPassed) {
         return this.externalEntitiesPublicIds;
      } else if (this.inputSource == null) {
         return null;
      } else {
         try {
            this.parseDocumentTypeInternal();
         } catch (SAXException var2) {
            XMLLogger.logSAXException("Failed to retrieve external entities public ids. " + var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logIOException("Failed to retrieve external entities public ids. " + var3.getMessage());
            return null;
         }

         return this.externalEntitiesPublicIds;
      }
   }
}
