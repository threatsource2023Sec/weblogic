package javolution.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javolution.lang.Reflection;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public final class XMLReaderImpl implements XMLReader {
   private static Sax2DefaultHandler DEFAULT_HANDLER = new Sax2DefaultHandler();
   private final XmlSaxParserImpl _parser = new XmlSaxParserImpl();
   private final Proxy _proxy = new Proxy();
   private static final Reflection.Constructor NEW_URL = Reflection.getConstructor("java.net.URL(java.lang.String)");
   private static final Reflection.Method OPEN_STREAM = Reflection.getMethod("java.net.URL.openStream()");
   private static final Reflection.Constructor NEW_FILE_INPUT_STREAM = Reflection.getConstructor("java.io.FileInputStream(java.lang.String)");

   public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this._parser.getFeature(var1);
   }

   public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      this._parser.setFeature(var1, var2);
   }

   public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      return this._parser.getProperty(var1);
   }

   public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      this._parser.setProperty(var1, var2);
   }

   public void setEntityResolver(EntityResolver var1) {
      this._parser.setEntityResolver(var1);
   }

   public EntityResolver getEntityResolver() {
      return this._parser.getEntityResolver();
   }

   public void setDTDHandler(DTDHandler var1) {
      this._parser.setDTDHandler(var1);
   }

   public DTDHandler getDTDHandler() {
      return this._parser.getDTDHandler();
   }

   public void setContentHandler(org.xml.sax.ContentHandler var1) {
      if (var1 != null) {
         XMLReaderImpl.Proxy.access$102(this._proxy, var1);
         this._parser.setContentHandler(this._proxy);
      } else {
         throw new NullPointerException();
      }
   }

   public org.xml.sax.ContentHandler getContentHandler() {
      return XMLReaderImpl.Proxy.access$100(this._proxy) == DEFAULT_HANDLER ? null : XMLReaderImpl.Proxy.access$100(this._proxy);
   }

   public void setErrorHandler(ErrorHandler var1) {
      this._parser.setErrorHandler(var1);
   }

   public ErrorHandler getErrorHandler() {
      return this._parser.getErrorHandler();
   }

   public void parse(InputSource var1) throws IOException, SAXException {
      Reader var2 = var1.getCharacterStream();
      if (var2 != null) {
         this._parser.parse(var2);
      } else {
         InputStream var3 = var1.getByteStream();
         if (var3 != null) {
            String var4 = var1.getEncoding();
            if (var4 != null && !var4.equals("UTF-8") && !var4.equals("utf-8")) {
               InputStreamReader var5 = new InputStreamReader(var3, var4);
               this._parser.parse((Reader)var5);
            } else {
               this._parser.parse(var3);
            }
         } else {
            this.parse(var1.getSystemId());
         }
      }

   }

   public void parse(String var1) throws IOException, SAXException {
      InputStream var2;
      try {
         Object var3 = NEW_URL.newInstance(var1);
         var2 = (InputStream)OPEN_STREAM.invoke(var3);
      } catch (Exception var6) {
         try {
            var2 = (InputStream)NEW_FILE_INPUT_STREAM.newInstance(var1);
         } catch (Exception var5) {
            throw new UnsupportedOperationException("Cannot parse " + var1);
         }
      }

      this._parser.parse(var2);
   }

   static Sax2DefaultHandler access$200() {
      return DEFAULT_HANDLER;
   }

   private static final class Sax2DefaultHandler implements EntityResolver, DTDHandler, org.xml.sax.ContentHandler, ErrorHandler {
      private Sax2DefaultHandler() {
      }

      public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
         return null;
      }

      public void notationDecl(String var1, String var2, String var3) throws SAXException {
      }

      public void unparsedEntityDecl(String var1, String var2, String var3, String var4) throws SAXException {
      }

      public void setDocumentLocator(Locator var1) {
      }

      public void startDocument() throws SAXException {
      }

      public void endDocument() throws SAXException {
      }

      public void startPrefixMapping(String var1, String var2) throws SAXException {
      }

      public void endPrefixMapping(String var1) throws SAXException {
      }

      public void startElement(String var1, String var2, String var3, org.xml.sax.Attributes var4) throws SAXException {
      }

      public void endElement(String var1, String var2, String var3) throws SAXException {
      }

      public void characters(char[] var1, int var2, int var3) throws SAXException {
      }

      public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
      }

      public void processingInstruction(String var1, String var2) throws SAXException {
      }

      public void skippedEntity(String var1) throws SAXException {
      }

      public void warning(SAXParseException var1) throws SAXException {
      }

      public void error(SAXParseException var1) throws SAXException {
      }

      public void fatalError(SAXParseException var1) throws SAXException {
         throw var1;
      }

      Sax2DefaultHandler(Object var1) {
         this();
      }
   }

   private static final class Proxy implements ContentHandler, org.xml.sax.Attributes {
      private org.xml.sax.ContentHandler _sax2Handler = XMLReaderImpl.access$200();
      private Attributes _attributes;

      public Proxy() {
      }

      public void setDocumentLocator(Locator var1) {
         this._sax2Handler.setDocumentLocator(var1);
      }

      public void startDocument() throws SAXException {
         this._sax2Handler.startDocument();
      }

      public void endDocument() throws SAXException {
         this._sax2Handler.endDocument();
         this._sax2Handler = XMLReaderImpl.access$200();
      }

      public void startPrefixMapping(CharSequence var1, CharSequence var2) throws SAXException {
         this._sax2Handler.startPrefixMapping(var1.toString(), var2.toString());
      }

      public void endPrefixMapping(CharSequence var1) throws SAXException {
         this._sax2Handler.endPrefixMapping(var1.toString());
      }

      public void startElement(CharSequence var1, CharSequence var2, CharSequence var3, Attributes var4) throws SAXException {
         this._attributes = var4;
         this._sax2Handler.startElement(var1.toString(), var2.toString(), var3.toString(), this);
      }

      public void endElement(CharSequence var1, CharSequence var2, CharSequence var3) throws SAXException {
         this._sax2Handler.endElement(var1.toString(), var2.toString(), var3.toString());
      }

      public void characters(char[] var1, int var2, int var3) throws SAXException {
         this._sax2Handler.characters(var1, var2, var3);
      }

      public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
         this._sax2Handler.ignorableWhitespace(var1, var2, var3);
      }

      public void processingInstruction(CharSequence var1, CharSequence var2) throws SAXException {
         this._sax2Handler.processingInstruction(var1.toString(), var2.toString());
      }

      public void skippedEntity(CharSequence var1) throws SAXException {
         this._sax2Handler.skippedEntity(var1.toString());
      }

      public int getLength() {
         return this._attributes.getLength();
      }

      public String getURI(int var1) {
         CharSequence var2 = this._attributes.getURI(var1);
         return var2 != null ? var2.toString() : null;
      }

      public String getLocalName(int var1) {
         CharSequence var2 = this._attributes.getLocalName(var1);
         return var2 != null ? var2.toString() : null;
      }

      public String getQName(int var1) {
         CharSequence var2 = this._attributes.getQName(var1);
         return var2 != null ? var2.toString() : null;
      }

      public String getType(int var1) {
         return this._attributes.getType(var1);
      }

      public String getValue(int var1) {
         CharSequence var2 = this._attributes.getValue(var1);
         return var2 != null ? var2.toString() : null;
      }

      public int getIndex(String var1, String var2) {
         return this._attributes.getIndex(var1, var2);
      }

      public int getIndex(String var1) {
         return this._attributes.getIndex(var1);
      }

      public String getType(String var1, String var2) {
         return this._attributes.getType(var1, var2);
      }

      public String getType(String var1) {
         return this._attributes.getType(var1);
      }

      public String getValue(String var1, String var2) {
         return this._attributes.getValue(var1, var2).toString();
      }

      public String getValue(String var1) {
         return this._attributes.getValue(var1).toString();
      }

      static org.xml.sax.ContentHandler access$102(Proxy var0, org.xml.sax.ContentHandler var1) {
         return var0._sax2Handler = var1;
      }

      static org.xml.sax.ContentHandler access$100(Proxy var0) {
         return var0._sax2Handler;
      }
   }
}
