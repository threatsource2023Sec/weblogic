package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.impl.dv.DTDDVFactory;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.parser.XMLParserConfiguration;

public abstract class XMLGrammarParser extends XMLParser {
   protected DTDDVFactory fDatatypeValidatorFactory;

   protected XMLGrammarParser(SymbolTable var1) {
      super((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
   }
}
