package antlr;

import antlr.collections.impl.Vector;
import java.util.Enumeration;

interface TokenManager {
   Object clone();

   void define(TokenSymbol var1);

   String getName();

   String getTokenStringAt(int var1);

   TokenSymbol getTokenSymbol(String var1);

   TokenSymbol getTokenSymbolAt(int var1);

   Enumeration getTokenSymbolElements();

   Enumeration getTokenSymbolKeys();

   Vector getVocabulary();

   boolean isReadOnly();

   void mapToTokenSymbol(String var1, TokenSymbol var2);

   int maxTokenType();

   int nextTokenType();

   void setName(String var1);

   void setReadOnly(boolean var1);

   boolean tokenDefined(String var1);
}
