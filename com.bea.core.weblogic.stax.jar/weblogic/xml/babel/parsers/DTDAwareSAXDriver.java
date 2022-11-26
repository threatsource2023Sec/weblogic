package weblogic.xml.babel.parsers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import org.xml.sax.ContentHandler;
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
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.EndElement;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.baseparser.SAXAdapter;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.babel.baseparser.ValidatingBaseParser;
import weblogic.xml.babel.helpers.Outputter;
import weblogic.xml.babel.helpers.SAXContentHandlerImpl;
import weblogic.xml.babel.helpers.SAXErrorHandlerImpl;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.scanner.ScannerException;

public class DTDAwareSAXDriver extends ValidatingBaseParser implements XMLReader {
   private static final boolean debug = false;
   SAXAdapter saxAdapter = new SAXAdapter();
   SAXElementFactory saxElementFactory = new SAXElementFactory();
   ContentHandler contentHandler;
   ErrorHandler errorHandler = new SAXErrorHandlerImpl();
   DTDHandler dtdHandler;
   EntityResolver entityResolver;
   SAXFeatureManager saxFeatureManager = new SAXFeatureManager();

   protected void putNamespaceURI(String key, String value) throws SAXException {
      super.putNamespaceURI(key, value);
      this.saxAdapter.startPrefixMapping(key, value, this.contentHandler);
   }

   protected void removeNamespaceURI(List keys) throws SAXException {
      super.removeNamespaceURI(keys);
      int i = 0;

      for(int len = keys.size(); i < len; ++i) {
         this.saxAdapter.endPrefixMapping(((PrefixMapping)keys.get(i)).getPrefix(), this.contentHandler);
      }

   }

   public Locator getLocator() {
      SAXElementFactory var10000 = this.saxElementFactory;
      return SAXElementFactory.createLocator((BaseParser)this);
   }

   public void saxParseSome() throws SAXParseException, SAXException {
      SAXElementFactory var10000;
      SAXParseException spe;
      try {
         Element element = super.parseSome();
         this.contentHandler.setDocumentLocator(this.getLocator());
         switch (element.type) {
            case 0:
               this.saxAdapter.startElement((StartElement)element, this.contentHandler);
            case 1:
            case 2:
            case 6:
            default:
               break;
            case 3:
               this.saxAdapter.endElement((EndElement)element, this.contentHandler);
               break;
            case 4:
               if (!element.getRawName().equalsIgnoreCase("xml")) {
                  this.saxAdapter.processingInstruction((ProcessingInstruction)element, this.contentHandler);
               }
               break;
            case 5:
               if (this.inDocument()) {
                  this.saxAdapter.characters((CharDataElement)element, this.contentHandler);
               }
               break;
            case 7:
               if (this.inDocument()) {
                  this.saxAdapter.characters((Space)element, this.contentHandler);
               }
         }
      } catch (ScannerException var3) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException(var3, this.getLocator());
         this.errorHandler.fatalError(spe);
      } catch (ParseException var4) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException(var4, this.getLocator());
         this.errorHandler.fatalError(spe);
      } catch (IOException var5) {
         var10000 = this.saxElementFactory;
         spe = SAXElementFactory.createSAXParseException((Exception)var5, this.getLocator());
         this.errorHandler.fatalError(spe);
      }

   }

   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      SAXFeatureManager var10000 = this.saxFeatureManager;
      return SAXFeatureManager.getFeature(name);
   }

   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      SAXFeatureManager var10000 = this.saxFeatureManager;
      SAXFeatureManager.setFeature(name, value);
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotSupportedException("Do not support property: " + name);
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      throw new SAXNotSupportedException("Do not support property: " + name);
   }

   public void setEntityResolver(EntityResolver resolver) {
      this.entityResolver = resolver;
   }

   public EntityResolver getEntityResolver() {
      return this.entityResolver;
   }

   public void setDTDHandler(DTDHandler handler) {
      this.dtdHandler = handler;
   }

   public DTDHandler getDTDHandler() {
      return this.dtdHandler;
   }

   public void setErrorHandler(ErrorHandler handler) {
      this.errorHandler = handler;
   }

   public ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public void setContentHandler(ContentHandler handler) {
      this.contentHandler = handler;
   }

   public ContentHandler getContentHandler() {
      return this.contentHandler;
   }

   void subinit(InputSource input) throws IOException, SAXException {
      try {
         super.init(input);
      } catch (ScannerException var4) {
         SAXElementFactory var10000 = this.saxElementFactory;
         SAXParseException spe = SAXElementFactory.createSAXParseException(var4, this.getLocator());
         this.errorHandler.fatalError(spe);
      }

   }

   public void parse(InputSource input) throws IOException, SAXException {
      this.subinit(input);
      this.contentHandler.startDocument();

      while(this.hasNext()) {
         this.saxParseSome();
      }

      this.contentHandler.endDocument();
   }

   public void parse(String systemId) throws IOException, SAXException {
      this.parse(new InputSource(systemId));
   }

   public static void timeTest(String fname, int numTimes) {
      long duration = 0L;

      for(int i = 0; i < numTimes; ++i) {
         try {
            InputStream stream = new BufferedInputStream(new FileInputStream(fname));
            Reader reader = XmlReader.createReader(stream);
            InputSource inputSource = new InputSource(reader);
            SAXDriver parser = new SAXDriver();
            SAXErrorHandlerImpl errorHandler = new SAXErrorHandlerImpl();
            SAXContentHandlerImpl contentHandler = new SAXContentHandlerImpl();
            contentHandler.setQuiet();
            parser.setContentHandler(contentHandler);
            parser.setErrorHandler(errorHandler);
            long ctime = System.currentTimeMillis();
            parser.parse(inputSource);
            long ftime = System.currentTimeMillis();
            duration += ftime - ctime;
         } catch (SAXParseException var15) {
            System.out.println("----- SAXParseException ----");
            System.out.println(var15);
            var15.printStackTrace();
         } catch (Exception var16) {
            System.out.println("-----JAVA   ----");
            var16.printStackTrace();
         }
      }

      System.out.println("Parser took:" + duration + " milliseconds for " + numTimes + " iterations " + (float)duration / (float)numTimes + " milliseconds per iteration");
      System.out.println("Parser:" + (double)((float)duration) / 1000.0 + " [s] (" + (double)numTimes / ((double)duration / 1000.0) + " [iteration/s], " + (float)duration / (float)numTimes + " [ms/iteration])");
   }

   public static void outputTest(String fname, String ofname, String validName) {
      try {
         InputStream stream = new BufferedInputStream(new FileInputStream(fname));
         Reader reader = XmlReader.createReader(stream);
         InputSource inputSource = new InputSource(reader);
         SAXDriver parser = new SAXDriver();
         SAXErrorHandlerImpl errorHandler = new SAXErrorHandlerImpl();
         Outputter contentHandler = new Outputter(false, validName);
         parser.setContentHandler(contentHandler);
         parser.setErrorHandler(errorHandler);
         parser.parse(inputSource);
         System.out.println(contentHandler.getDiagnostic(true));
         contentHandler.getOutputXML(ofname);
      } catch (SAXParseException var9) {
         System.out.println("----- SAXParseException ----");
         System.out.println(var9);
         var9.printStackTrace();
      } catch (Exception var10) {
         System.out.println("-----JAVA   ----");
         var10.printStackTrace();
      }

   }

   public static void main(String[] args) {
      boolean timeTest = false;
      boolean outputTest = false;
      if (args.length == 2) {
         timeTest = true;
      }

      if (args.length == 3) {
         outputTest = true;
      }

      if (outputTest) {
         outputTest(args[0], args[1], args[2]);
      } else if (timeTest) {
         timeTest(args[0], Integer.parseInt(args[1]));
      } else {
         try {
            InputSource inputSource = SAXElementFactory.createInputSource(args[0]);
            DTDAwareSAXDriver parser = new DTDAwareSAXDriver();
            parser.setFeature("http://xml.org/sax/features/validation", false);
            parser.setFeature("http://xml.org/sax/features/namespaces", true);
            System.out.println("------ Features --------");
            System.out.println("validation set to " + parser.getFeature("http://xml.org/sax/features/validation"));
            System.out.println("namespace set to " + parser.getFeature("http://xml.org/sax/features/namespaces"));
            SAXErrorHandlerImpl errorHandler = new SAXErrorHandlerImpl();
            SAXContentHandlerImpl contentHandler = new SAXContentHandlerImpl();
            contentHandler.setVerbose();
            parser.setContentHandler(contentHandler);
            parser.setErrorHandler(errorHandler);
            parser.parse(inputSource);
         } catch (SAXParseException var7) {
            System.out.println("----- SAXParseException ----");
            System.out.println(var7);
            var7.printStackTrace();
         } catch (SAXException var8) {
            System.out.println("----- SAXException ----");
            System.out.println(var8);
            var8.printStackTrace();
         } catch (Exception var9) {
            System.out.println("-----JAVA   ----");
            var9.printStackTrace();
         }
      }

   }
}
