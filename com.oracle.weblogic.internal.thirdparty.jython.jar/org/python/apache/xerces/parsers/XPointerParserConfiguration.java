package org.python.apache.xerces.parsers;

import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.xinclude.XIncludeHandler;
import org.python.apache.xerces.xinclude.XIncludeNamespaceSupport;
import org.python.apache.xerces.xni.XMLDocumentHandler;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;
import org.python.apache.xerces.xni.parser.XMLComponentManager;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xni.parser.XMLDocumentSource;
import org.python.apache.xerces.xpointer.XPointerHandler;

public class XPointerParserConfiguration extends XML11Configuration {
   private XPointerHandler fXPointerHandler;
   private XIncludeHandler fXIncludeHandler;
   protected static final String ALLOW_UE_AND_NOTATION_EVENTS = "http://xml.org/sax/features/allow-dtd-events-after-endDTD";
   protected static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
   protected static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
   protected static final String XPOINTER_HANDLER = "http://apache.org/xml/properties/internal/xpointer-handler";
   protected static final String XINCLUDE_HANDLER = "http://apache.org/xml/properties/internal/xinclude-handler";
   protected static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";

   public XPointerParserConfiguration() {
      this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public XPointerParserConfiguration(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public XPointerParserConfiguration(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public XPointerParserConfiguration(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var2, var3);
      this.fXIncludeHandler = new XIncludeHandler();
      this.addCommonComponent(this.fXIncludeHandler);
      this.fXPointerHandler = new XPointerHandler();
      this.addCommonComponent(this.fXPointerHandler);
      String[] var4 = new String[]{"http://xml.org/sax/features/allow-dtd-events-after-endDTD", "http://apache.org/xml/features/xinclude/fixup-base-uris", "http://apache.org/xml/features/xinclude/fixup-language"};
      this.addRecognizedFeatures(var4);
      String[] var5 = new String[]{"http://apache.org/xml/properties/internal/xinclude-handler", "http://apache.org/xml/properties/internal/xpointer-handler", "http://apache.org/xml/properties/internal/namespace-context"};
      this.addRecognizedProperties(var5);
      this.setFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD", true);
      this.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);
      this.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
      this.setProperty("http://apache.org/xml/properties/internal/xinclude-handler", this.fXIncludeHandler);
      this.setProperty("http://apache.org/xml/properties/internal/xpointer-handler", this.fXPointerHandler);
      this.setProperty("http://apache.org/xml/properties/internal/namespace-context", new XIncludeNamespaceSupport());
   }

   protected void configurePipeline() {
      super.configurePipeline();
      this.fDTDScanner.setDTDHandler(this.fDTDProcessor);
      this.fDTDProcessor.setDTDSource(this.fDTDScanner);
      this.fDTDProcessor.setDTDHandler(this.fXIncludeHandler);
      this.fXIncludeHandler.setDTDSource(this.fDTDProcessor);
      this.fXIncludeHandler.setDTDHandler(this.fXPointerHandler);
      this.fXPointerHandler.setDTDSource(this.fXIncludeHandler);
      this.fXPointerHandler.setDTDHandler(this.fDTDHandler);
      if (this.fDTDHandler != null) {
         this.fDTDHandler.setDTDSource(this.fXPointerHandler);
      }

      XMLDocumentSource var1 = null;
      if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE) {
         var1 = this.fSchemaValidator.getDocumentSource();
      } else {
         var1 = this.fLastComponent;
         this.fLastComponent = this.fXPointerHandler;
      }

      XMLDocumentHandler var2 = var1.getDocumentHandler();
      var1.setDocumentHandler(this.fXIncludeHandler);
      this.fXIncludeHandler.setDocumentSource(var1);
      if (var2 != null) {
         this.fXIncludeHandler.setDocumentHandler(var2);
         var2.setDocumentSource(this.fXIncludeHandler);
      }

      this.fXIncludeHandler.setDocumentHandler(this.fXPointerHandler);
      this.fXPointerHandler.setDocumentSource(this.fXIncludeHandler);
   }

   protected void configureXML11Pipeline() {
      super.configureXML11Pipeline();
      this.fXML11DTDScanner.setDTDHandler(this.fXML11DTDProcessor);
      this.fXML11DTDProcessor.setDTDSource(this.fXML11DTDScanner);
      this.fDTDProcessor.setDTDHandler(this.fXIncludeHandler);
      this.fXIncludeHandler.setDTDSource(this.fXML11DTDProcessor);
      this.fXIncludeHandler.setDTDHandler(this.fXPointerHandler);
      this.fXPointerHandler.setDTDSource(this.fXIncludeHandler);
      this.fXPointerHandler.setDTDHandler(this.fDTDHandler);
      if (this.fDTDHandler != null) {
         this.fDTDHandler.setDTDSource(this.fXPointerHandler);
      }

      XMLDocumentSource var1 = null;
      if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE) {
         var1 = this.fSchemaValidator.getDocumentSource();
      } else {
         var1 = this.fLastComponent;
         this.fLastComponent = this.fXPointerHandler;
      }

      XMLDocumentHandler var2 = var1.getDocumentHandler();
      var1.setDocumentHandler(this.fXIncludeHandler);
      this.fXIncludeHandler.setDocumentSource(var1);
      if (var2 != null) {
         this.fXIncludeHandler.setDocumentHandler(var2);
         var2.setDocumentSource(this.fXIncludeHandler);
      }

      this.fXIncludeHandler.setDocumentHandler(this.fXPointerHandler);
      this.fXPointerHandler.setDocumentSource(this.fXIncludeHandler);
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      super.setProperty(var1, var2);
   }
}
