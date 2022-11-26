package org.python.icu.text;

import java.text.ParsePosition;

public interface SymbolTable {
   char SYMBOL_REF = '$';

   char[] lookup(String var1);

   UnicodeMatcher lookupMatcher(int var1);

   String parseReference(String var1, ParsePosition var2, int var3);
}
