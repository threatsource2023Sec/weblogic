package antlr;

import antlr.collections.impl.BitSet;

public interface ANTLRGrammarParseBehavior {
   void abortGrammar();

   void beginAlt(boolean var1);

   void beginChildList();

   void beginExceptionGroup();

   void beginExceptionSpec(Token var1);

   void beginSubRule(Token var1, Token var2, boolean var3);

   void beginTree(Token var1) throws SemanticException;

   void defineRuleName(Token var1, String var2, boolean var3, String var4) throws SemanticException;

   void defineToken(Token var1, Token var2);

   void endAlt();

   void endChildList();

   void endExceptionGroup();

   void endExceptionSpec();

   void endGrammar();

   void endOptions();

   void endRule(String var1);

   void endSubRule();

   void endTree();

   void hasError();

   void noASTSubRule();

   void oneOrMoreSubRule();

   void optionalSubRule();

   void refAction(Token var1);

   void refArgAction(Token var1);

   void setUserExceptions(String var1);

   void refCharLiteral(Token var1, Token var2, boolean var3, int var4, boolean var5);

   void refCharRange(Token var1, Token var2, Token var3, int var4, boolean var5);

   void refElementOption(Token var1, Token var2);

   void refTokensSpecElementOption(Token var1, Token var2, Token var3);

   void refExceptionHandler(Token var1, Token var2);

   void refHeaderAction(Token var1, Token var2);

   void refInitAction(Token var1);

   void refMemberAction(Token var1);

   void refPreambleAction(Token var1);

   void refReturnAction(Token var1);

   void refRule(Token var1, Token var2, Token var3, Token var4, int var5);

   void refSemPred(Token var1);

   void refStringLiteral(Token var1, Token var2, int var3, boolean var4);

   void refToken(Token var1, Token var2, Token var3, Token var4, boolean var5, int var6, boolean var7);

   void refTokenRange(Token var1, Token var2, Token var3, int var4, boolean var5);

   void refTreeSpecifier(Token var1);

   void refWildcard(Token var1, Token var2, int var3);

   void setArgOfRuleRef(Token var1);

   void setCharVocabulary(BitSet var1);

   void setFileOption(Token var1, Token var2, String var3);

   void setGrammarOption(Token var1, Token var2);

   void setRuleOption(Token var1, Token var2);

   void setSubruleOption(Token var1, Token var2);

   void startLexer(String var1, Token var2, String var3, String var4);

   void startParser(String var1, Token var2, String var3, String var4);

   void startTreeWalker(String var1, Token var2, String var3, String var4);

   void synPred();

   void zeroOrMoreSubRule();
}
