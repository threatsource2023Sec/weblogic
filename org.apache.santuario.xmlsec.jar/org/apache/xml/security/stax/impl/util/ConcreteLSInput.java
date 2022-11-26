package org.apache.xml.security.stax.impl.util;

import java.io.InputStream;
import java.io.Reader;
import org.w3c.dom.ls.LSInput;

public class ConcreteLSInput implements LSInput {
   private Reader reader;
   private InputStream inputStream;
   private String stringData;
   private String systemId;
   private String publicId;
   private String baseURI;
   private String encoding;
   private boolean certifiedText;

   public Reader getCharacterStream() {
      return this.reader;
   }

   public void setCharacterStream(Reader characterStream) {
      this.reader = characterStream;
   }

   public InputStream getByteStream() {
      return this.inputStream;
   }

   public void setByteStream(InputStream byteStream) {
      this.inputStream = byteStream;
   }

   public String getStringData() {
      return this.stringData;
   }

   public void setStringData(String stringData) {
      this.stringData = stringData;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public void setSystemId(String systemId) {
      this.systemId = systemId;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public void setPublicId(String publicId) {
      this.publicId = publicId;
   }

   public String getBaseURI() {
      return this.baseURI;
   }

   public void setBaseURI(String baseURI) {
      this.baseURI = baseURI;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public boolean getCertifiedText() {
      return this.certifiedText;
   }

   public void setCertifiedText(boolean certifiedText) {
      this.certifiedText = certifiedText;
   }
}
