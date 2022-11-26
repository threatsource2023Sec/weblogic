package javolution.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import javolution.lang.Reusable;
import javolution.xml.pull.XmlPullParserException;
import javolution.xml.pull.XmlPullParserImpl;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

public class XmlSaxParserImpl implements Reusable {
   private static DefaultHandler DEFAULT_HANDLER = new DefaultHandler();
   private ContentHandler _contentHandler;
   private ErrorHandler _errorHandler;
   private final XmlPullParserImpl _pullParser = new XmlPullParserImpl();
   private final LocatorImpl _locator = new LocatorImpl();
   private EntityResolver _entityResolver;
   private DTDHandler _dtdHandler;
   int[] _startLength = new int[2];
   private static final CharSequence NO_CHAR = new CharSequence() {
      public int length() {
         return 0;
      }

      public char charAt(int var1) {
         throw new IndexOutOfBoundsException();
      }

      public CharSequence subSequence(int var1, int var2) {
         throw new IndexOutOfBoundsException();
      }
   };

   public XmlSaxParserImpl() {
      this.setContentHandler(DEFAULT_HANDLER);
      this.setErrorHandler(DEFAULT_HANDLER);
   }

   public void setContentHandler(ContentHandler var1) {
      if (var1 != null) {
         this._contentHandler = var1;
      } else {
         throw new NullPointerException();
      }
   }

   public ContentHandler getContentHandler() {
      return this._contentHandler == DEFAULT_HANDLER ? null : this._contentHandler;
   }

   public void setErrorHandler(ErrorHandler var1) {
      if (var1 != null) {
         this._errorHandler = var1;
      } else {
         throw new NullPointerException();
      }
   }

   public ErrorHandler getErrorHandler() {
      return this._errorHandler == DEFAULT_HANDLER ? null : this._errorHandler;
   }

   public void parse(InputStream var1) throws IOException, SAXException {
      this._pullParser.setInput(var1);
      this.parseAll();
   }

   public void parse(ByteBuffer var1) throws IOException, SAXException {
      this._pullParser.setInput(var1);
      this.parseAll();
   }

   public void parse(Reader var1) throws IOException, SAXException {
      this._pullParser.setInput(var1);
      this.parseAll();
   }

   public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1.equals("http://xml.org/sax/features/namespaces")) {
         return true;
      } else if (var1.equals("http://xml.org/sax/features/namespace-prefixes")) {
         return true;
      } else {
         throw new SAXNotRecognizedException("Feature " + var1 + " not recognized");
      }
   }

   public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (!var1.equals("http://xml.org/sax/features/namespaces") && !var1.equals("http://xml.org/sax/features/namespace-prefixes")) {
         throw new SAXNotRecognizedException("Feature " + var1 + " not recognized");
      }
   }

   public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Property " + var1 + " not recognized");
   }

   public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotRecognizedException("Property " + var1 + " not recognized");
   }

   public void setEntityResolver(EntityResolver var1) {
      this._entityResolver = var1;
   }

   public EntityResolver getEntityResolver() {
      return this._entityResolver;
   }

   public void setDTDHandler(DTDHandler var1) {
      this._dtdHandler = var1;
   }

   public DTDHandler getDTDHandler() {
      return this._dtdHandler;
   }

   public void reset() {
      this.setContentHandler(DEFAULT_HANDLER);
      this.setErrorHandler(DEFAULT_HANDLER);
      this._pullParser.reset();
   }

   private void parseAll() throws IOException, SAXException {
      try {
         int var1 = this._pullParser.getEventType();
         if (var1 != 0) {
            throw new SAXException("Currently parsing");
         }

         this._contentHandler.startDocument();

         while(true) {
            while(true) {
               var1 = this._pullParser.nextToken();
               int var5;
               if (var1 == 2) {
                  int var17 = this._pullParser.getDepth();
                  int var18 = this._pullParser.getNamespaceCount(var17 - 1);
                  int var19 = this._pullParser.getNamespaceCount(var17);

                  CharSequence var21;
                  CharSequence var22;
                  for(var5 = var18; var5 < var19; ++var5) {
                     var21 = this._pullParser.getNamespacePrefix(var5);
                     var21 = var21 == null ? NO_CHAR : var21;
                     var22 = this._pullParser.getNamespaceUri(var5);
                     this._contentHandler.startPrefixMapping(var21, var22);
                  }

                  CharSequence var20 = this._pullParser.getName();
                  var21 = this._pullParser.getNamespace();
                  var22 = this._pullParser.getQName();
                  Attributes var23 = this._pullParser.getSaxAttributes();
                  this._contentHandler.startElement(var21, var20, var22, var23);
               } else if (var1 == 3) {
                  CharSequence var16 = this._pullParser.getName();
                  CharSequence var3 = this._pullParser.getNamespace();
                  CharSequence var4 = this._pullParser.getQName();
                  this._contentHandler.endElement(var3, var16, var4);
                  var5 = this._pullParser.getDepth();
                  int var6 = this._pullParser.getNamespaceCount(var5);
                  int var7 = this._pullParser.getNamespaceCount(var5 + 1);

                  for(int var8 = var6; var8 < var7; ++var8) {
                     CharSequence var9 = this._pullParser.getNamespacePrefix(var8);
                     var9 = var9 == null ? NO_CHAR : var9;
                     this._contentHandler.endPrefixMapping(var9);
                  }
               } else if (var1 != 4 && var1 != 5) {
                  if (var1 == 1) {
                     return;
                  }
               } else {
                  char[] var15;
                  if (this._pullParser.isWhitespace()) {
                     var15 = this._pullParser.getTextCharacters(this._startLength);
                     this._contentHandler.ignorableWhitespace(var15, this._startLength[0], this._startLength[1]);
                  } else {
                     var15 = this._pullParser.getTextCharacters(this._startLength);
                     this._contentHandler.characters(var15, this._startLength[0], this._startLength[1]);
                  }
               }
            }
         }
      } catch (XmlPullParserException var13) {
         SAXParseException var2 = new SAXParseException(var13.getMessage(), this._locator);
         this._errorHandler.fatalError(var2);
      } finally {
         this._contentHandler.endDocument();
         this.reset();
      }

   }

   static XmlPullParserImpl access$100(XmlSaxParserImpl var0) {
      return var0._pullParser;
   }

   private class LocatorImpl implements Locator {
      private LocatorImpl() {
      }

      public String getPublicId() {
         return null;
      }

      public String getSystemId() {
         return null;
      }

      public int getLineNumber() {
         return XmlSaxParserImpl.access$100(XmlSaxParserImpl.this).getLineNumber();
      }

      public int getColumnNumber() {
         return XmlSaxParserImpl.access$100(XmlSaxParserImpl.this).getColumnNumber();
      }

      LocatorImpl(Object var2) {
         this();
      }
   }
}
