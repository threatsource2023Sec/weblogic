package antlr;

interface ToolErrorHandler {
   void warnAltAmbiguity(Grammar var1, AlternativeBlock var2, boolean var3, int var4, Lookahead[] var5, int var6, int var7);

   void warnAltExitAmbiguity(Grammar var1, BlockWithImpliedExitPath var2, boolean var3, int var4, Lookahead[] var5, int var6);
}
