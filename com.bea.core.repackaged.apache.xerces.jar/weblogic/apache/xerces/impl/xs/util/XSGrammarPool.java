package weblogic.apache.xerces.impl.xs.util;

import java.util.ArrayList;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.XSModelImpl;
import weblogic.apache.xerces.util.XMLGrammarPoolImpl;
import weblogic.apache.xerces.xs.XSModel;

public class XSGrammarPool extends XMLGrammarPoolImpl {
   public XSModel toXSModel() {
      return this.toXSModel((short)1);
   }

   public XSModel toXSModel(short var1) {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < this.fGrammars.length; ++var3) {
         for(XMLGrammarPoolImpl.Entry var4 = this.fGrammars[var3]; var4 != null; var4 = var4.next) {
            if (var4.desc.getGrammarType().equals("http://www.w3.org/2001/XMLSchema")) {
               var2.add(var4.grammar);
            }
         }
      }

      int var6 = var2.size();
      if (var6 == 0) {
         return this.toXSModel(new SchemaGrammar[0], var1);
      } else {
         SchemaGrammar[] var5 = (SchemaGrammar[])var2.toArray(new SchemaGrammar[var6]);
         return this.toXSModel(var5, var1);
      }
   }

   protected XSModel toXSModel(SchemaGrammar[] var1, short var2) {
      return new XSModelImpl(var1, var2);
   }
}
