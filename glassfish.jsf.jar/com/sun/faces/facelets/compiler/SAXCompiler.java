package com.sun.faces.facelets.compiler;

import com.sun.faces.config.FaceletsConfiguration;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.tag.TagAttributeImpl;
import com.sun.faces.facelets.tag.TagAttributesImpl;
import com.sun.faces.util.Util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.Location;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttributes;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public final class SAXCompiler extends Compiler {
   private static final Pattern XmlDeclaration = Pattern.compile("^<\\?xml.+?version=['\"](.+?)['\"](.+?encoding=['\"]((.+?))['\"])?.*?\\?>");

   public FaceletHandler doCompile(URL src, String alias) throws IOException {
      CompilationManager mgr = new CompilationManager(alias, this);
      CompilationHandler handler = new CompilationHandler(mgr, alias);
      return this.doCompile(mgr, handler, src, alias);
   }

   public FaceletHandler doMetadataCompile(URL src, String alias) throws IOException {
      CompilationManager mgr = new CompilationManager("metadata/" + alias, this);
      CompilationHandler handler = new MetadataCompilationHandler(mgr, alias);
      return this.doCompile(mgr, handler, src, alias);
   }

   protected FaceletHandler doCompile(CompilationManager mngr, CompilationHandler handler, URL src, String alias) throws IOException {
      String encoding = this.getEncoding();

      try {
         InputStream is = new BufferedInputStream(src.openStream(), 1024);
         Throwable var7 = null;

         try {
            writeXmlDecl(is, encoding, mngr);
            SAXParser parser = this.createSAXParser(handler);
            parser.parse(is, handler);
         } catch (Throwable var19) {
            var7 = var19;
            throw var19;
         } finally {
            if (is != null) {
               if (var7 != null) {
                  try {
                     is.close();
                  } catch (Throwable var18) {
                     var7.addSuppressed(var18);
                  }
               } else {
                  is.close();
               }
            }

         }
      } catch (SAXException var21) {
         throw new FaceletException("Error Parsing " + alias + ": " + var21.getMessage(), var21.getCause());
      } catch (ParserConfigurationException var22) {
         throw new FaceletException("Error Configuring Parser " + alias + ": " + var22.getMessage(), var22.getCause());
      } catch (FaceletException var23) {
         throw var23;
      }

      FaceletHandler result = new EncodingHandler(mngr.createFaceletHandler(), encoding, mngr.getCompilationMessageHolder());
      mngr.setCompilationMessageHolder((CompilationMessageHolder)null);
      return result;
   }

   private String getEncoding() {
      String encodingFromRequest = null;
      FacesContext context = FacesContext.getCurrentInstance();
      if (null != context) {
         ExternalContext extContext = context.getExternalContext();
         encodingFromRequest = extContext.getRequestCharacterEncoding();
      }

      String result = null != encodingFromRequest ? encodingFromRequest : "UTF-8";
      return result;
   }

   protected static void writeXmlDecl(InputStream is, String encoding, CompilationManager mngr) throws IOException {
      is.mark(128);

      try {
         byte[] b = new byte[128];
         if (is.read(b) > 0) {
            String r = new String(b, encoding);
            Matcher m = XmlDeclaration.matcher(r);
            if (m.find()) {
               WebConfiguration config = mngr.getWebConfiguration();
               FaceletsConfiguration faceletsConfig = config.getFaceletsConfiguration();
               boolean currentModeIsXhtml = faceletsConfig.isProcessCurrentDocumentAsFaceletsXhtml(mngr.getAlias());
               if (currentModeIsXhtml) {
                  Util.saveXMLDECLToFacesContextAttributes(m.group(0) + "\n");
               }
            }
         }
      } finally {
         is.reset();
      }

   }

   private SAXParser createSAXParser(CompilationHandler handler) throws SAXException, ParserConfigurationException {
      SAXParserFactory factory = Util.createSAXParserFactory();
      factory.setNamespaceAware(true);
      factory.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
      factory.setFeature("http://xml.org/sax/features/validation", this.isValidating());
      factory.setValidating(this.isValidating());
      if (handler.isDisallowDoctypeDeclSet()) {
         factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", handler.isDisallowDoctypeDecl());
      }

      SAXParser parser = factory.newSAXParser();
      XMLReader reader = parser.getXMLReader();
      reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
      reader.setErrorHandler(handler);
      reader.setEntityResolver(handler);
      return parser;
   }

   private static class MetadataCompilationHandler extends CompilationHandler {
      private static final String METADATA_HANDLER = "metadata";
      private boolean processingMetadata = false;
      private boolean metadataProcessed = false;

      public MetadataCompilationHandler(CompilationManager unit, String alias) {
         super(unit, alias);
      }

      public void characters(char[] ch, int start, int length) throws SAXException {
         if (!this.metadataProcessed && this.processingMetadata) {
            this.unit.writeText(new String(ch, start, length));
         }

      }

      public void comment(char[] ch, int start, int length) throws SAXException {
      }

      public void endCDATA() throws SAXException {
      }

      public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      }

      public void startCDATA() throws SAXException {
      }

      public void startDTD(String name, String publicId, String systemId) throws SAXException {
      }

      public void startEntity(String name) throws SAXException {
      }

      public void processingInstruction(String target, String data) throws SAXException {
      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if (!this.metadataProcessed) {
            if (!this.processingMetadata && ("http://java.sun.com/jsf/core".equals(uri) || "http://xmlns.jcp.org/jsf/core".equals(uri)) && "metadata".equals(localName)) {
               this.processingMetadata = true;
            }

            if (this.processingMetadata) {
               super.startElement(uri, localName, qName, attributes);
            }
         }

         if (localName.equals("view") && ("http://java.sun.com/jsf/core".equals(uri) || "http://xmlns.jcp.org/jsf/core".equals(uri))) {
            super.startElement(uri, localName, qName, attributes);
         }

      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         if (!this.metadataProcessed) {
            if (this.processingMetadata) {
               super.endElement(uri, localName, qName);
            }

            if (this.processingMetadata && ("http://java.sun.com/jsf/core".equals(uri) || "http://xmlns.jcp.org/jsf/core".equals(uri)) && "metadata".equals(localName)) {
               this.processingMetadata = false;
               this.metadataProcessed = true;
            }
         }

         if (localName.equals("view") && ("http://java.sun.com/jsf/core".equals(uri) || "http://xmlns.jcp.org/jsf/core".equals(uri))) {
            super.endElement(uri, localName, qName);
         }

      }
   }

   private static class CompilationHandler extends DefaultHandler implements LexicalHandler {
      protected final String alias;
      protected boolean inDocument = false;
      protected Locator locator;
      protected final CompilationManager unit;

      public CompilationHandler(CompilationManager unit, String alias) {
         this.unit = unit;
         this.alias = alias;
      }

      public void characters(char[] ch, int start, int length) throws SAXException {
         if (this.inDocument) {
            this.unit.writeText(new String(ch, start, length));
         }

      }

      public void comment(char[] ch, int start, int length) throws SAXException {
         if (this.inDocument && !this.unit.getWebConfiguration().getFaceletsConfiguration().isConsumeComments(this.alias)) {
            this.unit.writeComment(new String(ch, start, length));
         }

      }

      protected TagAttributesImpl createAttributes(Attributes attrs) {
         int len = attrs.getLength();
         TagAttributeImpl[] ta = new TagAttributeImpl[len];

         for(int i = 0; i < len; ++i) {
            ta[i] = new TagAttributeImpl(this.createLocation(), attrs.getURI(i), attrs.getLocalName(i), attrs.getQName(i), attrs.getValue(i));
         }

         return new TagAttributesImpl(ta);
      }

      protected Location createLocation() {
         Location result = null;
         if (null != this.locator) {
            result = new Location(this.alias, this.locator.getLineNumber(), this.locator.getColumnNumber());
         } else if (Compiler.log.isLoggable(Level.SEVERE)) {
            Compiler.log.log(Level.SEVERE, "Unable to create Location due to null locator instance variable.");
         }

         return result;
      }

      public void endCDATA() throws SAXException {
         if (this.inDocument && !this.unit.getWebConfiguration().getFaceletsConfiguration().isConsumeCDATA(this.alias)) {
            this.unit.writeInstruction("]]>");
         }

      }

      public void endDocument() throws SAXException {
         super.endDocument();
      }

      public void endDTD() throws SAXException {
         this.inDocument = true;
      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         this.unit.popTag();
      }

      public void endEntity(String name) throws SAXException {
      }

      public void endPrefixMapping(String prefix) throws SAXException {
         this.unit.popNamespace(prefix);
      }

      public void fatalError(SAXParseException e) throws SAXException {
         if (this.locator != null) {
            throw new SAXException("Error Traced[line: " + this.locator.getLineNumber() + "] " + e.getMessage());
         } else {
            throw e;
         }
      }

      public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
         if (this.inDocument) {
            this.unit.writeWhitespace(new String(ch, start, length));
         }

      }

      public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
         String dtd = "com/sun/faces/xhtml/default.dtd";
         URL url = this.getClass().getClassLoader().getResource(dtd);
         return new InputSource(url.toString());
      }

      public void setDocumentLocator(Locator locator) {
         this.locator = locator;
      }

      public void startCDATA() throws SAXException {
         if (this.inDocument && !this.unit.getWebConfiguration().getFaceletsConfiguration().isConsumeCDATA(this.alias)) {
            this.unit.writeInstruction("<![CDATA[");
         }

      }

      public void startDocument() throws SAXException {
         this.inDocument = true;
      }

      public void startDTD(String name, String publicId, String systemId) throws SAXException {
         FaceletsConfiguration facelets = this.unit.getWebConfiguration().getFaceletsConfiguration();
         boolean processAsXhtml = facelets.isProcessCurrentDocumentAsFaceletsXhtml(this.alias);
         if (this.inDocument && (processAsXhtml || facelets.isOutputHtml5Doctype(this.alias))) {
            boolean isHtml5 = facelets.isOutputHtml5Doctype(this.alias);
            StringBuffer sb = new StringBuffer(64);
            sb.append("<!DOCTYPE ").append(name);
            if (!isHtml5 && publicId != null) {
               sb.append(" PUBLIC \"").append(publicId).append("\"");
               if (systemId != null) {
                  sb.append(" \"").append(systemId).append("\"");
               }
            } else if (!isHtml5 && systemId != null) {
               sb.append(" SYSTEM \"").append(systemId).append("\"");
            }

            sb.append(">\n");
            Util.saveDOCTYPEToFacesContextAttributes(sb.toString());
         }

         this.inDocument = false;
      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         TagAttributes tagAttrs = this.createAttributes(attributes);
         Tag tag = new Tag(this.createLocation(), uri, localName, qName, tagAttrs);
         tagAttrs.setTag(tag);
         this.unit.pushTag(tag);
      }

      public void startEntity(String name) throws SAXException {
      }

      public void startPrefixMapping(String prefix, String uri) throws SAXException {
         this.unit.pushNamespace(prefix, uri);
      }

      public void processingInstruction(String target, String data) throws SAXException {
         if (this.inDocument) {
            boolean processAsXhtml = this.unit.getWebConfiguration().getFaceletsConfiguration().isProcessCurrentDocumentAsFaceletsXhtml(this.alias);
            if (processAsXhtml) {
               StringBuffer sb = new StringBuffer(64);
               sb.append("<?").append(target).append(' ').append(data).append("?>\n");
               this.unit.writeInstruction(sb.toString());
            }
         }

      }

      protected boolean isDisallowDoctypeDeclSet() {
         return this.unit.getWebConfiguration().isSet(WebConfiguration.BooleanWebContextInitParameter.DisallowDoctypeDecl);
      }

      protected boolean isDisallowDoctypeDecl() {
         return this.unit.getWebConfiguration().isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisallowDoctypeDecl);
      }
   }
}
