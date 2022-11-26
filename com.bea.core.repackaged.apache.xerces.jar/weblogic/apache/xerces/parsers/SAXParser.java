package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLParserConfiguration;

public class SAXParser extends AbstractSAXParser {
   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
   private static final String[] RECOGNIZED_FEATURES = new String[]{"http://apache.org/xml/features/scanner/notify-builtin-refs"};
   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   private static final String[] RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/grammar-pool"};

   public SAXParser(XMLParserConfiguration var1) {
      super(var1);
   }

   public SAXParser() {
      this((SymbolTable)null, (XMLGrammarPool)null);
   }

   public SAXParser(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null);
   }

   public SAXParser(SymbolTable var1, XMLGrammarPool var2) {
      super((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
      this.fConfiguration.setFeature("http://apache.org/xml/features/scanner/notify-builtin-refs", true);
      this.fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
      if (var1 != null) {
         this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
      }

      if (var2 != null) {
         this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", var2);
      }

   }
}
