package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.util.SecurityManager;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;

public class SecurityConfiguration extends XIncludeAwareParserConfiguration {
   protected static final String SECURITY_MANAGER_PROPERTY = "http://apache.org/xml/properties/security-manager";

   public SecurityConfiguration() {
      this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SecurityConfiguration(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SecurityConfiguration(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public SecurityConfiguration(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var2, var3);
      this.setProperty("http://apache.org/xml/properties/security-manager", new SecurityManager());
   }
}
