package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public interface DebugEventListener {
   String PROTOCOL_VERSION = "2";
   int TRUE = 1;
   int FALSE = 0;

   void enterRule(String var1, String var2);

   void enterAlt(int var1);

   void exitRule(String var1, String var2);

   void enterSubRule(int var1);

   void exitSubRule(int var1);

   void enterDecision(int var1, boolean var2);

   void exitDecision(int var1);

   void consumeToken(Token var1);

   void consumeHiddenToken(Token var1);

   void LT(int var1, Token var2);

   void mark(int var1);

   void rewind(int var1);

   void rewind();

   void beginBacktrack(int var1);

   void endBacktrack(int var1, boolean var2);

   void location(int var1, int var2);

   void recognitionException(RecognitionException var1);

   void beginResync();

   void endResync();

   void semanticPredicate(boolean var1, String var2);

   void commence();

   void terminate();

   void consumeNode(Object var1);

   void LT(int var1, Object var2);

   void nilNode(Object var1);

   void errorNode(Object var1);

   void createNode(Object var1);

   void createNode(Object var1, Token var2);

   void becomeRoot(Object var1, Object var2);

   void addChild(Object var1, Object var2);

   void setTokenBoundaries(Object var1, int var2, int var3);
}
