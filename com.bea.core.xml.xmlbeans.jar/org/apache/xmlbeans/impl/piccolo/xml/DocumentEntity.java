package org.apache.xmlbeans.impl.piccolo.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.xmlbeans.impl.piccolo.util.RecursionException;
import org.xml.sax.InputSource;

public class DocumentEntity implements Entity {
   private boolean isOpen = false;
   private URL url = null;
   private String sysID = null;
   private InputSource source = null;
   private static URL defaultContext;
   private boolean isStandalone = false;
   private XMLStreamReader streamReader = null;
   private XMLReaderReader readerReader = null;
   private XMLInputReader activeReader = null;

   public DocumentEntity() {
   }

   public DocumentEntity(String sysID) throws IOException {
      this.reset(sysID);
   }

   public DocumentEntity(InputSource source) throws IOException {
      this.reset(source);
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   public void open() throws IOException, RecursionException {
      String encoding = null;
      if (this.source != null) {
         Reader sourceReader = this.source.getCharacterStream();
         if (sourceReader != null) {
            if (this.readerReader == null) {
               this.readerReader = new XMLReaderReader();
            }

            this.readerReader.reset(sourceReader, true);
            this.isStandalone = this.readerReader.isXMLStandalone();
            this.activeReader = this.readerReader;
            this.isOpen = true;
            return;
         }

         InputStream in = this.source.getByteStream();
         if (in != null) {
            if (this.streamReader == null) {
               this.streamReader = new XMLStreamReader();
            }

            this.streamReader.reset(in, this.source.getEncoding(), true);
            this.isOpen = true;
            this.isStandalone = this.streamReader.isXMLStandalone();
            this.activeReader = this.streamReader;
            return;
         }

         this.url = new URL(defaultContext, this.source.getSystemId());
         this.sysID = this.url.toString();
         encoding = this.source.getEncoding();
      }

      if (this.streamReader == null) {
         this.streamReader = new XMLStreamReader();
      }

      this.streamReader.reset(this.url.openStream(), encoding, true);
      this.isStandalone = this.streamReader.isXMLStandalone();
      this.activeReader = this.streamReader;
      this.isOpen = true;
   }

   public String getDeclaredEncoding() {
      return this.activeReader.getXMLDeclaredEncoding();
   }

   public boolean isStandaloneDeclared() {
      return this.activeReader.isXMLStandaloneDeclared();
   }

   public String getXMLVersion() {
      return this.activeReader.getXMLVersion();
   }

   public void reset(String sysID) throws IOException {
      this.close();
      this.isStandalone = false;
      this.source = null;

      try {
         this.url = new URL(defaultContext, sysID);
      } catch (MalformedURLException var3) {
         this.url = (new File(sysID)).toURL();
      }

      this.sysID = this.url.toString();
   }

   public void reset(InputSource source) throws IOException {
      this.close();
      this.isStandalone = false;
      this.source = source;
      this.sysID = source.getSystemId();
      if (this.sysID != null) {
         try {
            this.url = new URL(defaultContext, this.sysID);
         } catch (MalformedURLException var3) {
            this.url = (new File(this.sysID)).toURL();
         }

         this.sysID = this.url.toString();
      }

   }

   public void close() throws IOException {
      if (this.isOpen) {
         this.source = null;
         this.activeReader.close();
         this.activeReader = null;
         this.isOpen = false;
      }
   }

   public String getPublicID() {
      return null;
   }

   public String getSystemID() {
      return this.sysID;
   }

   public boolean isStandalone() {
      return this.isStandalone;
   }

   public void setStandalone(boolean standalone) {
      this.isStandalone = standalone;
   }

   public boolean isInternal() {
      return false;
   }

   public boolean isParsed() {
      return true;
   }

   public Reader getReader() {
      return this.activeReader;
   }

   public String stringValue() {
      throw new UnsupportedOperationException();
   }

   public char[] charArrayValue() {
      throw new UnsupportedOperationException();
   }

   static {
      try {
         defaultContext = new URL("file", (String)null, ".");
      } catch (IOException var1) {
         defaultContext = null;
      }

   }
}
