package monfox.toolkit.snmp.metadata.gen;

import monfox.java_cup.runtime.Symbol;

public class XSNMPSymbol extends Symbol {
   public int lineNum = -1;
   public int charNum = -1;

   public XSNMPSymbol(int var1, int var2, int var3, String var4) {
      super(var1, var2, var3, var4);
   }
}
