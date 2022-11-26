package weblogic.xml.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.parsers.SAXParser;
import org.xml.sax.AttributeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.AssertionError;
import weblogic.xml.dom.ResourceEntityResolver;
import weblogic.xml.jaxp.WebLogicSAXParserFactory;

public final class ProcessorDriver extends HandlerBase {
   private static final boolean debug = false;
   private static final boolean verboseSAX = false;
   private static final boolean verboseProcessor = false;
   private InProcessor processor;
   private ProcessingContext procTree;
   private ProcessingContext currentNode;
   private Parser parser;
   int level;

   public ProcessorDriver(InProcessor proc, String publicId, String localDTDResourceName, boolean validate) {
      this.level = 0;
      this.processor = proc;
      this.setupSAXParser(publicId, localDTDResourceName, validate);
   }

   public ProcessorDriver(InProcessor proc, boolean validate) {
      this(proc, (String)null, (String)null, validate);
   }

   public void setCustomErrorHandler(ErrorHandler err) {
      if (err != null && this.parser != null) {
         this.parser.setErrorHandler(err);
      }

   }

   public void startElement(String name, AttributeList attrs) throws SAXException {
      try {
         ProcessingContext currNode = this.currentNode();
         if (currNode != null && currNode.isText()) {
            this.postProc();
            currNode = this.popNode();
         }

         currNode = this.pushNewNode(name);
         this.setAttributes(currNode, attrs);
         this.preProc();
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   public void endElement(String name) throws SAXException {
      ProcessingContext currNode = this.currentNode();
      if (currNode.isText()) {
         try {
            this.postProc();
         } finally {
            currNode = this.popNode();
         }
      }

      try {
         this.postProc();
      } finally {
         currNode = this.popNode();
      }

   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      ProcessingContext currNode = this.currentNode();
      if (!currNode.isText()) {
         currNode = this.pushNewTextNode();
         this.preProc();
      }

      currNode.appendValue(ch, start, length);
   }

   public void process(String xmlFilePath) throws IOException, XMLProcessingException, XMLParsingException {
      this.process(new File(xmlFilePath));
   }

   public void process(File xmlFile) throws IOException, XMLProcessingException, XMLParsingException {
      this.process((InputStream)(new FileInputStream(xmlFile)));
   }

   public void process(InputStream xmlInputStream) throws IOException, XMLProcessingException, XMLParsingException {
      try {
         InputSource in = new InputSource(xmlInputStream);
         this.parser.parse(in);
      } catch (SAXProcessorException var3) {
         throw new XMLProcessingException(var3);
      } catch (SAXException var4) {
         throw new XMLParsingException(var4);
      }
   }

   public void process(Reader xmlReader) throws IOException, XMLProcessingException, XMLParsingException {
      try {
         InputSource in = new InputSource(xmlReader);
         this.parser.parse(in);
      } catch (SAXProcessorException var3) {
         throw new XMLProcessingException(var3);
      } catch (SAXException var4) {
         throw new XMLParsingException(var4);
      }
   }

   public void process(InputSource in) throws IOException, XMLProcessingException, XMLParsingException {
      try {
         this.parser.parse(in);
      } catch (SAXProcessorException var3) {
         throw new XMLProcessingException(var3);
      } catch (SAXException var4) {
         throw new XMLParsingException(var4);
      }
   }

   private void preProc() throws SAXException {
      ProcessingContext currNode = this.currentNode();

      try {
         this.processor.preProc(currNode);
      } catch (SAXException var3) {
         this.popNode();
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      }
   }

   private void postProc() throws SAXException {
      ProcessingContext currNode = this.currentNode();

      try {
         this.processor.postProc(currNode);
      } catch (SAXException var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      }
   }

   private ProcessingContext popNode() throws SAXException {
      ProcessingContext n = this.currentNode();
      ProcessingContext parentNode = (ProcessingContext)n.getParent();

      try {
         if (!n.referenced()) {
            n.release();
         }
      } catch (XMLProcessingException var4) {
         throw new AssertionError(var4);
      }

      if (parentNode != null) {
         this.setCurrentNode(parentNode);
      }

      return parentNode;
   }

   private ProcessingContext pushNewTextNode() {
      ProcessingContext prevNode = this.currentNode();
      ProcessingContext newNode = prevNode.newTextNode();
      this.setCurrentNode(newNode);
      return newNode;
   }

   private ProcessingContext pushNewNode(String tagName) {
      ProcessingContext prevNode = this.currentNode();
      ProcessingContext newNode = null;

      try {
         if (prevNode != null) {
            newNode = prevNode.newElementNode(tagName);
         } else {
            newNode = new ProcessingContext(tagName);
         }
      } catch (XMLProcessingException var5) {
         throw new AssertionError(var5);
      }

      this.setCurrentNode(newNode);
      return newNode;
   }

   public ProcessingContext currentNode() {
      return this.currentNode;
   }

   private void setCurrentNode(ProcessingContext ctx) {
      this.currentNode = ctx;
   }

   private void setAttributes(ProcessingContext p, AttributeList attrs) {
      for(int i = 0; i < attrs.getLength(); ++i) {
         p.setAttribute(attrs.getName(i), attrs.getValue(i));
      }

   }

   private void setupSAXParser(String publicId, String localDTD, boolean validate) {
      try {
         WebLogicSAXParserFactory fact = new WebLogicSAXParserFactory();
         fact.setFeature("http://apache.org/xml/features/allow-java-encodings", true);
         fact.setValidating(validate);
         SAXParser saxParser = fact.newSAXParser();
         this.parser = saxParser.getParser();
      } catch (Exception var6) {
         throw new AssertionError(var6);
      }

      this.parser.setErrorHandler(new HandlerBase() {
         public void error(SAXParseException x) throws SAXException {
            throw x;
         }
      });
      if (localDTD != null) {
         ResourceEntityResolver er = new ResourceEntityResolver();
         er.setUnresolvedIsFatal(true);
         er.addEntityResource(publicId, localDTD);
         this.parser.setEntityResolver(er);
      }

      this.parser.setDocumentHandler(this);
   }
}
