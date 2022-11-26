package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLParserConfiguration;

public class XMLDocumentParser extends AbstractXMLDocumentParser {
   public XMLDocumentParser() {
      super((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
   }

   public XMLDocumentParser(XMLParserConfiguration var1) {
      super(var1);
   }

   public XMLDocumentParser(SymbolTable var1) {
      super((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
   }

   public XMLDocumentParser(SymbolTable var1, XMLGrammarPool var2) {
      super((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", var2);
   }
}
