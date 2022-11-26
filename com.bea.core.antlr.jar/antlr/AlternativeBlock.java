package antlr;

import antlr.collections.impl.Vector;

class AlternativeBlock extends AlternativeElement {
   protected String initAction = null;
   protected Vector alternatives = new Vector(5);
   protected String label;
   protected int alti;
   protected int altj;
   protected int analysisAlt;
   protected boolean hasAnAction = false;
   protected boolean hasASynPred = false;
   protected int ID = 0;
   protected static int nblks;
   boolean not = false;
   boolean greedy = true;
   boolean greedySet = false;
   protected boolean doAutoGen = true;
   protected boolean warnWhenFollowAmbig = true;
   protected boolean generateAmbigWarnings = true;

   public AlternativeBlock(Grammar var1) {
      super(var1);
      this.not = false;
      ++nblks;
      this.ID = nblks;
   }

   public AlternativeBlock(Grammar var1, Token var2, boolean var3) {
      super(var1, var2);
      this.not = var3;
      ++nblks;
      this.ID = nblks;
   }

   public void addAlternative(Alternative var1) {
      this.alternatives.appendElement(var1);
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Alternative getAlternativeAt(int var1) {
      return (Alternative)this.alternatives.elementAt(var1);
   }

   public Vector getAlternatives() {
      return this.alternatives;
   }

   public boolean getAutoGen() {
      return this.doAutoGen;
   }

   public String getInitAction() {
      return this.initAction;
   }

   public String getLabel() {
      return this.label;
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public void prepareForAnalysis() {
      for(int var1 = 0; var1 < this.alternatives.size(); ++var1) {
         Alternative var2 = (Alternative)this.alternatives.elementAt(var1);
         var2.cache = new Lookahead[this.grammar.maxk + 1];
         var2.lookaheadDepth = -1;
      }

   }

   public void removeTrackingOfRuleRefs(Grammar var1) {
      for(int var2 = 0; var2 < this.alternatives.size(); ++var2) {
         Alternative var3 = this.getAlternativeAt(var2);

         for(AlternativeElement var4 = var3.head; var4 != null; var4 = var4.next) {
            if (var4 instanceof RuleRefElement) {
               RuleRefElement var5 = (RuleRefElement)var4;
               RuleSymbol var6 = (RuleSymbol)var1.getSymbol(var5.targetRule);
               if (var6 == null) {
                  this.grammar.antlrTool.error("rule " + var5.targetRule + " referenced in (...)=>, but not defined");
               } else {
                  var6.references.removeElement(var5);
               }
            } else if (var4 instanceof AlternativeBlock) {
               ((AlternativeBlock)var4).removeTrackingOfRuleRefs(var1);
            }
         }
      }

   }

   public void setAlternatives(Vector var1) {
      this.alternatives = var1;
   }

   public void setAutoGen(boolean var1) {
      this.doAutoGen = var1;
   }

   public void setInitAction(String var1) {
      this.initAction = var1;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public void setOption(Token var1, Token var2) {
      if (var1.getText().equals("warnWhenFollowAmbig")) {
         if (var2.getText().equals("true")) {
            this.warnWhenFollowAmbig = true;
         } else if (var2.getText().equals("false")) {
            this.warnWhenFollowAmbig = false;
         } else {
            this.grammar.antlrTool.error("Value for warnWhenFollowAmbig must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else if (var1.getText().equals("generateAmbigWarnings")) {
         if (var2.getText().equals("true")) {
            this.generateAmbigWarnings = true;
         } else if (var2.getText().equals("false")) {
            this.generateAmbigWarnings = false;
         } else {
            this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else if (var1.getText().equals("greedy")) {
         if (var2.getText().equals("true")) {
            this.greedy = true;
            this.greedySet = true;
         } else if (var2.getText().equals("false")) {
            this.greedy = false;
            this.greedySet = true;
         } else {
            this.grammar.antlrTool.error("Value for greedy must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else {
         this.grammar.antlrTool.error("Invalid subrule option: " + var1.getText(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

   }

   public String toString() {
      String var1 = " (";
      if (this.initAction != null) {
         var1 = var1 + this.initAction;
      }

      for(int var2 = 0; var2 < this.alternatives.size(); ++var2) {
         Alternative var3 = this.getAlternativeAt(var2);
         Lookahead[] var4 = var3.cache;
         int var5 = var3.lookaheadDepth;
         if (var5 != -1) {
            if (var5 == Integer.MAX_VALUE) {
               var1 = var1 + "{?}:";
            } else {
               var1 = var1 + " {";

               for(int var6 = 1; var6 <= var5; ++var6) {
                  var1 = var1 + var4[var6].toString(",", this.grammar.tokenManager.getVocabulary());
                  if (var6 < var5 && var4[var6 + 1] != null) {
                     var1 = var1 + ";";
                  }
               }

               var1 = var1 + "}:";
            }
         }

         AlternativeElement var8 = var3.head;
         String var7 = var3.semPred;
         if (var7 != null) {
            var1 = var1 + var7;
         }

         while(var8 != null) {
            var1 = var1 + var8;
            var8 = var8.next;
         }

         if (var2 < this.alternatives.size() - 1) {
            var1 = var1 + " |";
         }
      }

      var1 = var1 + " )";
      return var1;
   }
}
