package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.util.SoftReferenceSymbolTable;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;

public class SoftReferenceSymbolTableConfiguration extends XIncludeAwareParserConfiguration {
   public SoftReferenceSymbolTableConfiguration() {
      this(new SoftReferenceSymbolTable(), (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SoftReferenceSymbolTableConfiguration(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SoftReferenceSymbolTableConfiguration(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public SoftReferenceSymbolTableConfiguration(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var2, var3);
   }
}
