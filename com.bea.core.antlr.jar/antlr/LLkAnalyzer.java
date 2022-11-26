package antlr;

import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;

public class LLkAnalyzer implements LLkGrammarAnalyzer {
   public boolean DEBUG_ANALYZER = false;
   private AlternativeBlock currentBlock;
   protected Tool tool = null;
   protected Grammar grammar = null;
   protected boolean lexicalAnalysis = false;
   CharFormatter charFormatter = new JavaCharFormatter();

   public LLkAnalyzer(Tool var1) {
      this.tool = var1;
   }

   protected boolean altUsesWildcardDefault(Alternative var1) {
      AlternativeElement var2 = var1.head;
      if (var2 instanceof TreeElement && ((TreeElement)var2).root instanceof WildcardElement) {
         return true;
      } else {
         return var2 instanceof WildcardElement && var2.next instanceof BlockEndElement;
      }
   }

   public boolean deterministic(AlternativeBlock var1) {
      boolean var2 = true;
      if (this.DEBUG_ANALYZER) {
         System.out.println("deterministic(" + var1 + ")");
      }

      boolean var3 = true;
      int var4 = var1.alternatives.size();
      AlternativeBlock var5 = this.currentBlock;
      Object var6 = null;
      this.currentBlock = var1;
      if (!var1.greedy && !(var1 instanceof OneOrMoreBlock) && !(var1 instanceof ZeroOrMoreBlock)) {
         this.tool.warning("Being nongreedy only makes sense for (...)+ and (...)*", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

      if (var4 == 1) {
         AlternativeElement var14 = var1.getAlternativeAt(0).head;
         this.currentBlock.alti = 0;
         var1.getAlternativeAt(0).cache[1] = var14.look(1);
         var1.getAlternativeAt(0).lookaheadDepth = 1;
         this.currentBlock = var5;
         return true;
      } else {
         for(int var7 = 0; var7 < var4 - 1; ++var7) {
            this.currentBlock.alti = var7;
            this.currentBlock.analysisAlt = var7;
            this.currentBlock.altj = var7 + 1;

            for(int var8 = var7 + 1; var8 < var4; ++var8) {
               this.currentBlock.altj = var8;
               if (this.DEBUG_ANALYZER) {
                  System.out.println("comparing " + var7 + " against alt " + var8);
               }

               this.currentBlock.analysisAlt = var8;
               int var13 = 1;
               Lookahead[] var9 = new Lookahead[this.grammar.maxk + 1];

               boolean var10;
               do {
                  var10 = false;
                  if (this.DEBUG_ANALYZER) {
                     System.out.println("checking depth " + var13 + "<=" + this.grammar.maxk);
                  }

                  Lookahead var11 = this.getAltLookahead(var1, var7, var13);
                  Lookahead var12 = this.getAltLookahead(var1, var8, var13);
                  if (this.DEBUG_ANALYZER) {
                     System.out.println("p is " + var11.toString(",", this.charFormatter, this.grammar));
                  }

                  if (this.DEBUG_ANALYZER) {
                     System.out.println("q is " + var12.toString(",", this.charFormatter, this.grammar));
                  }

                  var9[var13] = var11.intersection(var12);
                  if (this.DEBUG_ANALYZER) {
                     System.out.println("intersection at depth " + var13 + " is " + var9[var13].toString());
                  }

                  if (!var9[var13].nil()) {
                     var10 = true;
                     ++var13;
                  }
               } while(var10 && var13 <= this.grammar.maxk);

               Alternative var15 = var1.getAlternativeAt(var7);
               Alternative var16 = var1.getAlternativeAt(var8);
               if (var10) {
                  var3 = false;
                  var15.lookaheadDepth = Integer.MAX_VALUE;
                  var16.lookaheadDepth = Integer.MAX_VALUE;
                  if (var15.synPred != null) {
                     if (this.DEBUG_ANALYZER) {
                        System.out.println("alt " + var7 + " has a syn pred");
                     }
                  } else if (var15.semPred != null) {
                     if (this.DEBUG_ANALYZER) {
                        System.out.println("alt " + var7 + " has a sem pred");
                     }
                  } else if (!this.altUsesWildcardDefault(var16)) {
                     if ((var1.warnWhenFollowAmbig || !(var15.head instanceof BlockEndElement) && !(var16.head instanceof BlockEndElement)) && var1.generateAmbigWarnings && (!var1.greedySet || !var1.greedy || (!(var15.head instanceof BlockEndElement) || var16.head instanceof BlockEndElement) && (!(var16.head instanceof BlockEndElement) || var15.head instanceof BlockEndElement))) {
                        this.tool.errorHandler.warnAltAmbiguity(this.grammar, var1, this.lexicalAnalysis, this.grammar.maxk, var9, var7, var8);
                     }
                  }
               } else {
                  var15.lookaheadDepth = Math.max(var15.lookaheadDepth, var13);
                  var16.lookaheadDepth = Math.max(var16.lookaheadDepth, var13);
               }
            }
         }

         this.currentBlock = var5;
         return var3;
      }
   }

   public boolean deterministic(OneOrMoreBlock var1) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("deterministic(...)+(" + var1 + ")");
      }

      AlternativeBlock var2 = this.currentBlock;
      this.currentBlock = var1;
      boolean var3 = this.deterministic((AlternativeBlock)var1);
      boolean var4 = this.deterministicImpliedPath(var1);
      this.currentBlock = var2;
      return var4 && var3;
   }

   public boolean deterministic(ZeroOrMoreBlock var1) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("deterministic(...)*(" + var1 + ")");
      }

      AlternativeBlock var2 = this.currentBlock;
      this.currentBlock = var1;
      boolean var3 = this.deterministic((AlternativeBlock)var1);
      boolean var4 = this.deterministicImpliedPath(var1);
      this.currentBlock = var2;
      return var4 && var3;
   }

   public boolean deterministicImpliedPath(BlockWithImpliedExitPath var1) {
      boolean var3 = true;
      Vector var4 = var1.getAlternatives();
      int var5 = var4.size();
      this.currentBlock.altj = -1;
      if (this.DEBUG_ANALYZER) {
         System.out.println("deterministicImpliedPath");
      }

      for(int var6 = 0; var6 < var5; ++var6) {
         Alternative var7 = var1.getAlternativeAt(var6);
         if (var7.head instanceof BlockEndElement) {
            this.tool.warning("empty alternative makes no sense in (...)* or (...)+", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }

         int var2 = 1;
         Lookahead[] var8 = new Lookahead[this.grammar.maxk + 1];

         boolean var9;
         do {
            var9 = false;
            if (this.DEBUG_ANALYZER) {
               System.out.println("checking depth " + var2 + "<=" + this.grammar.maxk);
            }

            Lookahead var11 = var1.next.look(var2);
            var1.exitCache[var2] = var11;
            this.currentBlock.alti = var6;
            Lookahead var10 = this.getAltLookahead(var1, var6, var2);
            if (this.DEBUG_ANALYZER) {
               System.out.println("follow is " + var11.toString(",", this.charFormatter, this.grammar));
            }

            if (this.DEBUG_ANALYZER) {
               System.out.println("p is " + var10.toString(",", this.charFormatter, this.grammar));
            }

            var8[var2] = var11.intersection(var10);
            if (this.DEBUG_ANALYZER) {
               System.out.println("intersection at depth " + var2 + " is " + var8[var2]);
            }

            if (!var8[var2].nil()) {
               var9 = true;
               ++var2;
            }
         } while(var9 && var2 <= this.grammar.maxk);

         if (var9) {
            var3 = false;
            var7.lookaheadDepth = Integer.MAX_VALUE;
            var1.exitLookaheadDepth = Integer.MAX_VALUE;
            Alternative var12 = var1.getAlternativeAt(this.currentBlock.alti);
            if (var1.warnWhenFollowAmbig && var1.generateAmbigWarnings) {
               if (var1.greedy && var1.greedySet && !(var12.head instanceof BlockEndElement)) {
                  if (this.DEBUG_ANALYZER) {
                     System.out.println("greedy loop");
                  }
               } else if (!var1.greedy && !(var12.head instanceof BlockEndElement)) {
                  if (this.DEBUG_ANALYZER) {
                     System.out.println("nongreedy loop");
                  }

                  if (!lookaheadEquivForApproxAndFullAnalysis(var1.exitCache, this.grammar.maxk)) {
                     this.tool.warning(new String[]{"nongreedy block may exit incorrectly due", "\tto limitations of linear approximate lookahead (first k-1 sets", "\tin lookahead not singleton)."}, this.grammar.getFilename(), var1.getLine(), var1.getColumn());
                  }
               } else {
                  this.tool.errorHandler.warnAltExitAmbiguity(this.grammar, var1, this.lexicalAnalysis, this.grammar.maxk, var8, var6);
               }
            }
         } else {
            var7.lookaheadDepth = Math.max(var7.lookaheadDepth, var2);
            var1.exitLookaheadDepth = Math.max(var1.exitLookaheadDepth, var2);
         }
      }

      return var3;
   }

   public Lookahead FOLLOW(int var1, RuleEndElement var2) {
      RuleBlock var3 = (RuleBlock)var2.block;
      String var4;
      if (this.lexicalAnalysis) {
         var4 = CodeGenerator.encodeLexerRuleName(var3.getRuleName());
      } else {
         var4 = var3.getRuleName();
      }

      if (this.DEBUG_ANALYZER) {
         System.out.println("FOLLOW(" + var1 + "," + var4 + ")");
      }

      if (var2.lock[var1]) {
         if (this.DEBUG_ANALYZER) {
            System.out.println("FOLLOW cycle to " + var4);
         }

         return new Lookahead(var4);
      } else if (var2.cache[var1] != null) {
         if (this.DEBUG_ANALYZER) {
            System.out.println("cache entry FOLLOW(" + var1 + ") for " + var4 + ": " + var2.cache[var1].toString(",", this.charFormatter, this.grammar));
         }

         if (var2.cache[var1].cycle == null) {
            return (Lookahead)var2.cache[var1].clone();
         } else {
            RuleSymbol var10 = (RuleSymbol)this.grammar.getSymbol(var2.cache[var1].cycle);
            RuleEndElement var11 = var10.getBlock().endNode;
            if (var11.cache[var1] == null) {
               return (Lookahead)var2.cache[var1].clone();
            } else {
               if (this.DEBUG_ANALYZER) {
                  System.out.println("combining FOLLOW(" + var1 + ") for " + var4 + ": from " + var2.cache[var1].toString(",", this.charFormatter, this.grammar) + " with FOLLOW for " + ((RuleBlock)var11.block).getRuleName() + ": " + var11.cache[var1].toString(",", this.charFormatter, this.grammar));
               }

               if (var11.cache[var1].cycle == null) {
                  var2.cache[var1].combineWith(var11.cache[var1]);
                  var2.cache[var1].cycle = null;
               } else {
                  Lookahead var12 = this.FOLLOW(var1, var11);
                  var2.cache[var1].combineWith(var12);
                  var2.cache[var1].cycle = var12.cycle;
               }

               if (this.DEBUG_ANALYZER) {
                  System.out.println("saving FOLLOW(" + var1 + ") for " + var4 + ": from " + var2.cache[var1].toString(",", this.charFormatter, this.grammar));
               }

               return (Lookahead)var2.cache[var1].clone();
            }
         }
      } else {
         var2.lock[var1] = true;
         Lookahead var5 = new Lookahead();
         RuleSymbol var6 = (RuleSymbol)this.grammar.getSymbol(var4);

         for(int var7 = 0; var7 < var6.numReferences(); ++var7) {
            RuleRefElement var8 = var6.getReference(var7);
            if (this.DEBUG_ANALYZER) {
               System.out.println("next[" + var4 + "] is " + var8.next.toString());
            }

            Lookahead var9 = var8.next.look(var1);
            if (this.DEBUG_ANALYZER) {
               System.out.println("FIRST of next[" + var4 + "] ptr is " + var9.toString());
            }

            if (var9.cycle != null && var9.cycle.equals(var4)) {
               var9.cycle = null;
            }

            var5.combineWith(var9);
            if (this.DEBUG_ANALYZER) {
               System.out.println("combined FOLLOW[" + var4 + "] is " + var5.toString());
            }
         }

         var2.lock[var1] = false;
         if (var5.fset.nil() && var5.cycle == null) {
            if (this.grammar instanceof TreeWalkerGrammar) {
               var5.fset.add(3);
            } else if (this.grammar instanceof LexerGrammar) {
               var5.setEpsilon();
            } else {
               var5.fset.add(1);
            }
         }

         if (this.DEBUG_ANALYZER) {
            System.out.println("saving FOLLOW(" + var1 + ") for " + var4 + ": " + var5.toString(",", this.charFormatter, this.grammar));
         }

         var2.cache[var1] = (Lookahead)var5.clone();
         return var5;
      }
   }

   private Lookahead getAltLookahead(AlternativeBlock var1, int var2, int var3) {
      Alternative var5 = var1.getAlternativeAt(var2);
      AlternativeElement var6 = var5.head;
      Lookahead var4;
      if (var5.cache[var3] == null) {
         var4 = var6.look(var3);
         var5.cache[var3] = var4;
      } else {
         var4 = var5.cache[var3];
      }

      return var4;
   }

   public Lookahead look(int var1, ActionElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookAction(" + var1 + "," + var2 + ")");
      }

      return var2.next.look(var1);
   }

   public Lookahead look(int var1, AlternativeBlock var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookAltBlk(" + var1 + "," + var2 + ")");
      }

      AlternativeBlock var3 = this.currentBlock;
      this.currentBlock = var2;
      Lookahead var4 = new Lookahead();

      for(int var5 = 0; var5 < var2.alternatives.size(); ++var5) {
         if (this.DEBUG_ANALYZER) {
            System.out.println("alt " + var5 + " of " + var2);
         }

         this.currentBlock.analysisAlt = var5;
         Alternative var6 = var2.getAlternativeAt(var5);
         AlternativeElement var7 = var6.head;
         if (this.DEBUG_ANALYZER && var6.head == var6.tail) {
            System.out.println("alt " + var5 + " is empty");
         }

         Lookahead var8 = var7.look(var1);
         var4.combineWith(var8);
      }

      if (var1 == 1 && var2.not && this.subruleCanBeInverted(var2, this.lexicalAnalysis)) {
         if (this.lexicalAnalysis) {
            BitSet var9 = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
            int[] var10 = var4.fset.toArray();

            for(int var11 = 0; var11 < var10.length; ++var11) {
               var9.remove(var10[var11]);
            }

            var4.fset = var9;
         } else {
            var4.fset.notInPlace(4, this.grammar.tokenManager.maxTokenType());
         }
      }

      this.currentBlock = var3;
      return var4;
   }

   public Lookahead look(int var1, BlockEndElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookBlockEnd(" + var1 + ", " + var2.block + "); lock is " + var2.lock[var1]);
      }

      if (var2.lock[var1]) {
         return new Lookahead();
      } else {
         Lookahead var3;
         if (!(var2.block instanceof ZeroOrMoreBlock) && !(var2.block instanceof OneOrMoreBlock)) {
            var3 = new Lookahead();
         } else {
            var2.lock[var1] = true;
            var3 = this.look(var1, var2.block);
            var2.lock[var1] = false;
         }

         if (var2.block instanceof TreeElement) {
            var3.combineWith(Lookahead.of(3));
         } else if (var2.block instanceof SynPredBlock) {
            var3.setEpsilon();
         } else {
            Lookahead var4 = var2.block.next.look(var1);
            var3.combineWith(var4);
         }

         return var3;
      }
   }

   public Lookahead look(int var1, CharLiteralElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookCharLiteral(" + var1 + "," + var2 + ")");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else if (this.lexicalAnalysis) {
         if (var2.not) {
            BitSet var3 = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
            if (this.DEBUG_ANALYZER) {
               System.out.println("charVocab is " + var3.toString());
            }

            this.removeCompetingPredictionSets(var3, var2);
            if (this.DEBUG_ANALYZER) {
               System.out.println("charVocab after removal of prior alt lookahead " + var3.toString());
            }

            var3.clear(var2.getType());
            return new Lookahead(var3);
         } else {
            return Lookahead.of(var2.getType());
         }
      } else {
         this.tool.panic("Character literal reference found in parser");
         return Lookahead.of(var2.getType());
      }
   }

   public Lookahead look(int var1, CharRangeElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookCharRange(" + var1 + "," + var2 + ")");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         BitSet var3 = BitSet.of(var2.begin);

         for(int var4 = var2.begin + 1; var4 <= var2.end; ++var4) {
            var3.add(var4);
         }

         return new Lookahead(var3);
      }
   }

   public Lookahead look(int var1, GrammarAtom var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look(" + var1 + "," + var2 + "[" + var2.getType() + "])");
      }

      if (this.lexicalAnalysis) {
         this.tool.panic("token reference found in lexer");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         Lookahead var3 = Lookahead.of(var2.getType());
         if (var2.not) {
            int var4 = this.grammar.tokenManager.maxTokenType();
            var3.fset.notInPlace(4, var4);
            this.removeCompetingPredictionSets(var3.fset, var2);
         }

         return var3;
      }
   }

   public Lookahead look(int var1, OneOrMoreBlock var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look+" + var1 + "," + var2 + ")");
      }

      Lookahead var3 = this.look(var1, (AlternativeBlock)var2);
      return var3;
   }

   public Lookahead look(int var1, RuleBlock var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookRuleBlk(" + var1 + "," + var2 + ")");
      }

      Lookahead var3 = this.look(var1, (AlternativeBlock)var2);
      return var3;
   }

   public Lookahead look(int var1, RuleEndElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookRuleBlockEnd(" + var1 + "); noFOLLOW=" + var2.noFOLLOW + "; lock is " + var2.lock[var1]);
      }

      Lookahead var3;
      if (var2.noFOLLOW) {
         var3 = new Lookahead();
         var3.setEpsilon();
         var3.epsilonDepth = BitSet.of(var1);
         return var3;
      } else {
         var3 = this.FOLLOW(var1, var2);
         return var3;
      }
   }

   public Lookahead look(int var1, RuleRefElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookRuleRef(" + var1 + "," + var2 + ")");
      }

      RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var2.targetRule);
      if (var3 != null && var3.defined) {
         RuleBlock var4 = var3.getBlock();
         RuleEndElement var5 = var4.endNode;
         boolean var6 = var5.noFOLLOW;
         var5.noFOLLOW = true;
         Lookahead var7 = this.look(var1, var2.targetRule);
         if (this.DEBUG_ANALYZER) {
            System.out.println("back from rule ref to " + var2.targetRule);
         }

         var5.noFOLLOW = var6;
         if (var7.cycle != null) {
            this.tool.error("infinite recursion to rule " + var7.cycle + " from rule " + var2.enclosingRuleName, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }

         if (var7.containsEpsilon()) {
            if (this.DEBUG_ANALYZER) {
               System.out.println("rule ref to " + var2.targetRule + " has eps, depth: " + var7.epsilonDepth);
            }

            var7.resetEpsilon();
            int[] var8 = var7.epsilonDepth.toArray();
            var7.epsilonDepth = null;

            for(int var9 = 0; var9 < var8.length; ++var9) {
               int var10 = var1 - (var1 - var8[var9]);
               Lookahead var11 = var2.next.look(var10);
               var7.combineWith(var11);
            }
         }

         return var7;
      } else {
         this.tool.error("no definition of rule " + var2.targetRule, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         return new Lookahead();
      }
   }

   public Lookahead look(int var1, StringLiteralElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookStringLiteral(" + var1 + "," + var2 + ")");
      }

      if (this.lexicalAnalysis) {
         return var1 > var2.processedAtomText.length() ? var2.next.look(var1 - var2.processedAtomText.length()) : Lookahead.of(var2.processedAtomText.charAt(var1 - 1));
      } else if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         Lookahead var3 = Lookahead.of(var2.getType());
         if (var2.not) {
            int var4 = this.grammar.tokenManager.maxTokenType();
            var3.fset.notInPlace(4, var4);
         }

         return var3;
      }
   }

   public Lookahead look(int var1, SynPredBlock var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look=>(" + var1 + "," + var2 + ")");
      }

      return var2.next.look(var1);
   }

   public Lookahead look(int var1, TokenRangeElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookTokenRange(" + var1 + "," + var2 + ")");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         BitSet var3 = BitSet.of(var2.begin);

         for(int var4 = var2.begin + 1; var4 <= var2.end; ++var4) {
            var3.add(var4);
         }

         return new Lookahead(var3);
      }
   }

   public Lookahead look(int var1, TreeElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look(" + var1 + "," + var2.root + "[" + var2.root.getType() + "])");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         Lookahead var3 = null;
         if (var2.root instanceof WildcardElement) {
            var3 = var2.root.look(1);
         } else {
            var3 = Lookahead.of(var2.root.getType());
            if (var2.root.not) {
               int var4 = this.grammar.tokenManager.maxTokenType();
               var3.fset.notInPlace(4, var4);
            }
         }

         return var3;
      }
   }

   public Lookahead look(int var1, WildcardElement var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look(" + var1 + "," + var2 + ")");
      }

      if (var1 > 1) {
         return var2.next.look(var1 - 1);
      } else {
         BitSet var3;
         if (this.lexicalAnalysis) {
            var3 = (BitSet)((LexerGrammar)this.grammar).charVocabulary.clone();
         } else {
            var3 = new BitSet(1);
            int var4 = this.grammar.tokenManager.maxTokenType();
            var3.notInPlace(4, var4);
            if (this.DEBUG_ANALYZER) {
               System.out.println("look(" + var1 + "," + var2 + ") after not: " + var3);
            }
         }

         return new Lookahead(var3);
      }
   }

   public Lookahead look(int var1, ZeroOrMoreBlock var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("look*(" + var1 + "," + var2 + ")");
      }

      Lookahead var3 = this.look(var1, (AlternativeBlock)var2);
      Lookahead var4 = var2.next.look(var1);
      var3.combineWith(var4);
      return var3;
   }

   public Lookahead look(int var1, String var2) {
      if (this.DEBUG_ANALYZER) {
         System.out.println("lookRuleName(" + var1 + "," + var2 + ")");
      }

      RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var2);
      RuleBlock var4 = var3.getBlock();
      if (var4.lock[var1]) {
         if (this.DEBUG_ANALYZER) {
            System.out.println("infinite recursion to rule " + var4.getRuleName());
         }

         return new Lookahead(var2);
      } else if (var4.cache[var1] != null) {
         if (this.DEBUG_ANALYZER) {
            System.out.println("found depth " + var1 + " result in FIRST " + var2 + " cache: " + var4.cache[var1].toString(",", this.charFormatter, this.grammar));
         }

         return (Lookahead)var4.cache[var1].clone();
      } else {
         var4.lock[var1] = true;
         Lookahead var5 = this.look(var1, var4);
         var4.lock[var1] = false;
         var4.cache[var1] = (Lookahead)var5.clone();
         if (this.DEBUG_ANALYZER) {
            System.out.println("saving depth " + var1 + " result in FIRST " + var2 + " cache: " + var4.cache[var1].toString(",", this.charFormatter, this.grammar));
         }

         return var5;
      }
   }

   public static boolean lookaheadEquivForApproxAndFullAnalysis(Lookahead[] var0, int var1) {
      for(int var2 = 1; var2 <= var1 - 1; ++var2) {
         BitSet var3 = var0[var2].fset;
         if (var3.degree() > 1) {
            return false;
         }
      }

      return true;
   }

   private void removeCompetingPredictionSets(BitSet var1, AlternativeElement var2) {
      AlternativeElement var3 = this.currentBlock.getAlternativeAt(this.currentBlock.analysisAlt).head;
      if (var3 instanceof TreeElement) {
         if (((TreeElement)var3).root != var2) {
            return;
         }
      } else if (var2 != var3) {
         return;
      }

      for(int var4 = 0; var4 < this.currentBlock.analysisAlt; ++var4) {
         AlternativeElement var5 = this.currentBlock.getAlternativeAt(var4).head;
         var1.subtractInPlace(var5.look(1).fset);
      }

   }

   private void removeCompetingPredictionSetsFromWildcard(Lookahead[] var1, AlternativeElement var2, int var3) {
      for(int var4 = 1; var4 <= var3; ++var4) {
         for(int var5 = 0; var5 < this.currentBlock.analysisAlt; ++var5) {
            AlternativeElement var6 = this.currentBlock.getAlternativeAt(var5).head;
            var1[var4].fset.subtractInPlace(var6.look(var4).fset);
         }
      }

   }

   private void reset() {
      this.grammar = null;
      this.DEBUG_ANALYZER = false;
      this.currentBlock = null;
      this.lexicalAnalysis = false;
   }

   public void setGrammar(Grammar var1) {
      if (this.grammar != null) {
         this.reset();
      }

      this.grammar = var1;
      this.lexicalAnalysis = this.grammar instanceof LexerGrammar;
      this.DEBUG_ANALYZER = this.grammar.analyzerDebug;
   }

   public boolean subruleCanBeInverted(AlternativeBlock var1, boolean var2) {
      if (!(var1 instanceof ZeroOrMoreBlock) && !(var1 instanceof OneOrMoreBlock) && !(var1 instanceof SynPredBlock)) {
         if (var1.alternatives.size() == 0) {
            return false;
         } else {
            int var3 = 0;

            while(var3 < var1.alternatives.size()) {
               Alternative var4 = var1.getAlternativeAt(var3);
               if (var4.synPred == null && var4.semPred == null && var4.exceptionSpec == null) {
                  AlternativeElement var5 = var4.head;
                  if ((var5 instanceof CharLiteralElement || var5 instanceof TokenRefElement || var5 instanceof CharRangeElement || var5 instanceof TokenRangeElement || var5 instanceof StringLiteralElement && !var2) && var5.next instanceof BlockEndElement && var5.getAutoGenType() == 1) {
                     ++var3;
                     continue;
                  }

                  return false;
               }

               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
