package antlr;

class DefaultToolErrorHandler implements ToolErrorHandler {
   private final Tool antlrTool;
   CharFormatter javaCharFormatter = new JavaCharFormatter();

   DefaultToolErrorHandler(Tool var1) {
      this.antlrTool = var1;
   }

   private void dumpSets(String[] var1, int var2, Grammar var3, boolean var4, int var5, Lookahead[] var6) {
      StringBuffer var7 = new StringBuffer(100);

      for(int var8 = 1; var8 <= var5; ++var8) {
         var7.append("k==").append(var8).append(':');
         if (var4) {
            String var9 = var6[var8].fset.toStringWithRanges(",", this.javaCharFormatter);
            if (var6[var8].containsEpsilon()) {
               var7.append("<end-of-token>");
               if (var9.length() > 0) {
                  var7.append(',');
               }
            }

            var7.append(var9);
         } else {
            var7.append(var6[var8].fset.toString(",", var3.tokenManager.getVocabulary()));
         }

         var1[var2++] = var7.toString();
         var7.setLength(0);
      }

   }

   public void warnAltAmbiguity(Grammar var1, AlternativeBlock var2, boolean var3, int var4, Lookahead[] var5, int var6, int var7) {
      StringBuffer var8 = new StringBuffer(100);
      if (var2 instanceof RuleBlock && ((RuleBlock)var2).isLexerAutoGenRule()) {
         Alternative var9 = var2.getAlternativeAt(var6);
         Alternative var10 = var2.getAlternativeAt(var7);
         RuleRefElement var11 = (RuleRefElement)var9.head;
         RuleRefElement var12 = (RuleRefElement)var10.head;
         String var13 = CodeGenerator.reverseLexerRuleName(var11.targetRule);
         String var14 = CodeGenerator.reverseLexerRuleName(var12.targetRule);
         var8.append("lexical nondeterminism between rules ");
         var8.append(var13).append(" and ").append(var14).append(" upon");
      } else {
         if (var3) {
            var8.append("lexical ");
         }

         var8.append("nondeterminism between alts ");
         var8.append(var6 + 1).append(" and ");
         var8.append(var7 + 1).append(" of block upon");
      }

      String[] var15 = new String[var4 + 1];
      var15[0] = var8.toString();
      this.dumpSets(var15, 1, var1, var3, var4, var5);
      this.antlrTool.warning(var15, var1.getFilename(), var2.getLine(), var2.getColumn());
   }

   public void warnAltExitAmbiguity(Grammar var1, BlockWithImpliedExitPath var2, boolean var3, int var4, Lookahead[] var5, int var6) {
      String[] var7 = new String[var4 + 2];
      var7[0] = (var3 ? "lexical " : "") + "nondeterminism upon";
      this.dumpSets(var7, 1, var1, var3, var4, var5);
      var7[var4 + 1] = "between alt " + (var6 + 1) + " and exit branch of block";
      this.antlrTool.warning(var7, var1.getFilename(), var2.getLine(), var2.getColumn());
   }
}
