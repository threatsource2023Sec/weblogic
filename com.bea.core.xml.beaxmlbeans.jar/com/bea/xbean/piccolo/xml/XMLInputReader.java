package com.bea.xbean.piccolo.xml;

import java.io.IOException;
import java.io.Reader;

public abstract class XMLInputReader extends Reader {
   private String xmlVersion = null;
   private boolean xmlStandaloneDeclared = false;
   private boolean xmlStandalone = false;
   private String xmlDeclaredEncoding = null;
   private XMLDeclParser parser = new XMLDeclParser();

   protected void resetInput() {
      this.xmlVersion = this.xmlDeclaredEncoding = null;
      this.xmlStandaloneDeclared = this.xmlStandalone = false;
   }

   protected int parseXMLDeclaration(char[] cbuf, int offset, int length) throws IOException {
      this.parser.reset(cbuf, offset, length);
      int var10000 = this.parser.parse();
      XMLDeclParser var10001 = this.parser;
      if (var10000 == 1) {
         this.xmlVersion = this.parser.getXMLVersion();
         this.xmlStandalone = this.parser.isXMLStandalone();
         this.xmlStandaloneDeclared = this.parser.isXMLStandaloneDeclared();
         this.xmlDeclaredEncoding = this.parser.getXMLEncoding();
         return this.parser.getCharsRead();
      } else {
         return 0;
      }
   }

   public String getXMLVersion() {
      return this.xmlVersion;
   }

   public boolean isXMLStandalone() {
      return this.xmlStandalone;
   }

   public boolean isXMLStandaloneDeclared() {
      return this.xmlStandaloneDeclared;
   }

   public String getXMLDeclaredEncoding() {
      return this.xmlDeclaredEncoding;
   }
}
