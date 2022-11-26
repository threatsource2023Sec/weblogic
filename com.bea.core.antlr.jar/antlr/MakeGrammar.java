package antlr;

import antlr.collections.Stack;
import antlr.collections.impl.LList;
import antlr.collections.impl.Vector;

public class MakeGrammar extends DefineGrammarSymbols {
   protected Stack blocks = new LList();
   protected RuleRefElement lastRuleRef;
   protected RuleEndElement ruleEnd;
   protected RuleBlock ruleBlock;
   protected int nested = 0;
   protected boolean grammarError = false;
   ExceptionSpec currentExceptionSpec = null;

   public MakeGrammar(Tool var1, String[] var2, LLkAnalyzer var3) {
      super(var1, var2, var3);
   }

   public void abortGrammar() {
      String var1 = "unknown grammar";
      if (this.grammar != null) {
         var1 = this.grammar.getClassName();
      }

      this.tool.error("aborting grammar '" + var1 + "' due to errors");
      super.abortGrammar();
   }

   protected void addElementToCurrentAlt(AlternativeElement var1) {
      var1.enclosingRuleName = this.ruleBlock.ruleName;
      this.context().addAlternativeElement(var1);
   }

   public void beginAlt(boolean var1) {
      super.beginAlt(var1);
      Alternative var2 = new Alternative();
      var2.setAutoGen(var1);
      this.context().block.addAlternative(var2);
   }

   public void beginChildList() {
      super.beginChildList();
      this.context().block.addAlternative(new Alternative());
   }

   public void beginExceptionGroup() {
      super.beginExceptionGroup();
      if (!(this.context().block instanceof RuleBlock)) {
         this.tool.panic("beginExceptionGroup called outside of rule block");
      }

   }

   public void beginExceptionSpec(Token var1) {
      if (var1 != null) {
         var1.setText(StringUtils.stripFront(StringUtils.stripBack(var1.getText(), " \n\r\t"), " \n\r\t"));
      }

      super.beginExceptionSpec(var1);
      this.currentExceptionSpec = new ExceptionSpec(var1);
   }

   public void beginSubRule(Token var1, Token var2, boolean var3) {
      super.beginSubRule(var1, var2, var3);
      this.blocks.push(new BlockContext());
      this.context().block = new AlternativeBlock(this.grammar, var2, var3);
      this.context().altNum = 0;
      ++this.nested;
      this.context().blockEnd = new BlockEndElement(this.grammar);
      this.context().blockEnd.block = this.context().block;
      this.labelElement(this.context().block, var1);
   }

   public void beginTree(Token var1) throws SemanticException {
      if (!(this.grammar instanceof TreeWalkerGrammar)) {
         this.tool.error("Trees only allowed in TreeParser", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         throw new SemanticException("Trees only allowed in TreeParser");
      } else {
         super.beginTree(var1);
         this.blocks.push(new TreeBlockContext());
         this.context().block = new TreeElement(this.grammar, var1);
         this.context().altNum = 0;
      }
   }

   public BlockContext context() {
      return this.blocks.height() == 0 ? null : (BlockContext)this.blocks.top();
   }

   public static RuleBlock createNextTokenRule(Grammar var0, Vector var1, String var2) {
      RuleBlock var3 = new RuleBlock(var0, var2);
      var3.setDefaultErrorHandler(var0.getDefaultErrorHandler());
      RuleEndElement var4 = new RuleEndElement(var0);
      var3.setEndElement(var4);
      var4.block = var3;

      for(int var5 = 0; var5 < var1.size(); ++var5) {
         RuleSymbol var6 = (RuleSymbol)var1.elementAt(var5);
         if (!var6.isDefined()) {
            var0.antlrTool.error("Lexer rule " + var6.id.substring(1) + " is not defined");
         } else if (var6.access.equals("public")) {
            Alternative var7 = new Alternative();
            RuleBlock var8 = var6.getBlock();
            Vector var9 = var8.getAlternatives();
            if (var9 != null && var9.size() == 1) {
               Alternative var10 = (Alternative)var9.elementAt(0);
               if (var10.semPred != null) {
                  var7.semPred = var10.semPred;
               }
            }

            RuleRefElement var11 = new RuleRefElement(var0, new CommonToken(41, var6.getId()), 1);
            var11.setLabel("theRetToken");
            var11.enclosingRuleName = "nextToken";
            var11.next = var4;
            var7.addElement(var11);
            var7.setAutoGen(true);
            var3.addAlternative(var7);
            var6.addReference(var11);
         }
      }

      var3.setAutoGen(true);
      var3.prepareForAnalysis();
      return var3;
   }

   private AlternativeBlock createOptionalRuleRef(String var1, Token var2) {
      AlternativeBlock var3 = new AlternativeBlock(this.grammar, var2, false);
      String var4 = CodeGenerator.encodeLexerRuleName(var1);
      if (!this.grammar.isDefined(var4)) {
         this.grammar.define(new RuleSymbol(var4));
      }

      CommonToken var5 = new CommonToken(24, var1);
      var5.setLine(var2.getLine());
      var5.setLine(var2.getColumn());
      RuleRefElement var6 = new RuleRefElement(this.grammar, var5, 1);
      var6.enclosingRuleName = this.ruleBlock.ruleName;
      BlockEndElement var7 = new BlockEndElement(this.grammar);
      var7.block = var3;
      Alternative var8 = new Alternative(var6);
      var8.addElement(var7);
      var3.addAlternative(var8);
      Alternative var9 = new Alternative();
      var9.addElement(var7);
      var3.addAlternative(var9);
      var3.prepareForAnalysis();
      return var3;
   }

   public void defineRuleName(Token var1, String var2, boolean var3, String var4) throws SemanticException {
      if (var1.type == 24) {
         if (!(this.grammar instanceof LexerGrammar)) {
            this.tool.error("Lexical rule " + var1.getText() + " defined outside of lexer", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            var1.setText(var1.getText().toLowerCase());
         }
      } else if (this.grammar instanceof LexerGrammar) {
         this.tool.error("Lexical rule names must be upper case, '" + var1.getText() + "' is not", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         var1.setText(var1.getText().toUpperCase());
      }

      super.defineRuleName(var1, var2, var3, var4);
      String var5 = var1.getText();
      if (var1.type == 24) {
         var5 = CodeGenerator.encodeLexerRuleName(var5);
      }

      RuleSymbol var6 = (RuleSymbol)this.grammar.getSymbol(var5);
      RuleBlock var7 = new RuleBlock(this.grammar, var1.getText(), var1.getLine(), var3);
      var7.setDefaultErrorHandler(this.grammar.getDefaultErrorHandler());
      this.ruleBlock = var7;
      this.blocks.push(new BlockContext());
      this.context().block = var7;
      var6.setBlock(var7);
      this.ruleEnd = new RuleEndElement(this.grammar);
      var7.setEndElement(this.ruleEnd);
      this.nested = 0;
   }

   public void endAlt() {
      super.endAlt();
      if (this.nested == 0) {
         this.addElementToCurrentAlt(this.ruleEnd);
      } else {
         this.addElementToCurrentAlt(this.context().blockEnd);
      }

      ++this.context().altNum;
   }

   public void endChildList() {
      super.endChildList();
      BlockEndElement var1 = new BlockEndElement(this.grammar);
      var1.block = this.context().block;
      this.addElementToCurrentAlt(var1);
   }

   public void endExceptionGroup() {
      super.endExceptionGroup();
   }

   public void endExceptionSpec() {
      super.endExceptionSpec();
      if (this.currentExceptionSpec == null) {
         this.tool.panic("exception processing internal error -- no active exception spec");
      }

      if (this.context().block instanceof RuleBlock) {
         ((RuleBlock)this.context().block).addExceptionSpec(this.currentExceptionSpec);
      } else if (this.context().currentAlt().exceptionSpec != null) {
         this.tool.error("Alternative already has an exception specification", this.grammar.getFilename(), this.context().block.getLine(), this.context().block.getColumn());
      } else {
         this.context().currentAlt().exceptionSpec = this.currentExceptionSpec;
      }

      this.currentExceptionSpec = null;
   }

   public void endGrammar() {
      if (this.grammarError) {
         this.abortGrammar();
      } else {
         super.endGrammar();
      }

   }

   public void endRule(String var1) {
      super.endRule(var1);
      BlockContext var2 = (BlockContext)this.blocks.pop();
      this.ruleEnd.block = var2.block;
      this.ruleEnd.block.prepareForAnalysis();
   }

   public void endSubRule() {
      super.endSubRule();
      --this.nested;
      BlockContext var1 = (BlockContext)this.blocks.pop();
      AlternativeBlock var2 = var1.block;
      if (var2.not && !(var2 instanceof SynPredBlock) && !(var2 instanceof ZeroOrMoreBlock) && !(var2 instanceof OneOrMoreBlock) && !this.analyzer.subruleCanBeInverted(var2, this.grammar instanceof LexerGrammar)) {
         String var3 = System.getProperty("line.separator");
         this.tool.error("This subrule cannot be inverted.  Only subrules of the form:" + var3 + "    (T1|T2|T3...) or" + var3 + "    ('c1'|'c2'|'c3'...)" + var3 + "may be inverted (ranges are also allowed).", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
      }

      if (var2 instanceof SynPredBlock) {
         SynPredBlock var4 = (SynPredBlock)var2;
         this.context().block.hasASynPred = true;
         this.context().currentAlt().synPred = var4;
         this.grammar.hasSyntacticPredicate = true;
         var4.removeTrackingOfRuleRefs(this.grammar);
      } else {
         this.addElementToCurrentAlt(var2);
      }

      var1.blockEnd.block.prepareForAnalysis();
   }

   public void endTree() {
      super.endTree();
      BlockContext var1 = (BlockContext)this.blocks.pop();
      this.addElementToCurrentAlt(var1.block);
   }

   public void hasError() {
      this.grammarError = true;
   }

   private void labelElement(AlternativeElement var1, Token var2) {
      if (var2 != null) {
         for(int var3 = 0; var3 < this.ruleBlock.labeledElements.size(); ++var3) {
            AlternativeElement var4 = (AlternativeElement)this.ruleBlock.labeledElements.elementAt(var3);
            String var5 = var4.getLabel();
            if (var5 != null && var5.equals(var2.getText())) {
               this.tool.error("Label '" + var2.getText() + "' has already been defined", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
               return;
            }
         }

         var1.setLabel(var2.getText());
         this.ruleBlock.labeledElements.appendElement(var1);
      }

   }

   public void noAutoGenSubRule() {
      this.context().block.setAutoGen(false);
   }

   public void oneOrMoreSubRule() {
      if (this.context().block.not) {
         this.tool.error("'~' cannot be applied to (...)* subrule", this.grammar.getFilename(), this.context().block.getLine(), this.context().block.getColumn());
      }

      OneOrMoreBlock var1 = new OneOrMoreBlock(this.grammar);
      setBlock(var1, this.context().block);
      BlockContext var2 = (BlockContext)this.blocks.pop();
      this.blocks.push(new BlockContext());
      this.context().block = var1;
      this.context().blockEnd = var2.blockEnd;
      this.context().blockEnd.block = var1;
   }

   public void optionalSubRule() {
      if (this.context().block.not) {
         this.tool.error("'~' cannot be applied to (...)? subrule", this.grammar.getFilename(), this.context().block.getLine(), this.context().block.getColumn());
      }

      this.beginAlt(false);
      this.endAlt();
   }

   public void refAction(Token var1) {
      super.refAction(var1);
      this.context().block.hasAnAction = true;
      this.addElementToCurrentAlt(new ActionElement(this.grammar, var1));
   }

   public void setUserExceptions(String var1) {
      ((RuleBlock)this.context().block).throwsSpec = var1;
   }

   public void refArgAction(Token var1) {
      ((RuleBlock)this.context().block).argAction = var1.getText();
   }

   public void refCharLiteral(Token var1, Token var2, boolean var3, int var4, boolean var5) {
      if (!(this.grammar instanceof LexerGrammar)) {
         this.tool.error("Character literal only valid in lexer", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      } else {
         super.refCharLiteral(var1, var2, var3, var4, var5);
         CharLiteralElement var6 = new CharLiteralElement((LexerGrammar)this.grammar, var1, var3, var4);
         if (!((LexerGrammar)this.grammar).caseSensitive && var6.getType() < 128 && Character.toLowerCase((char)var6.getType()) != (char)var6.getType()) {
            this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }

         this.addElementToCurrentAlt(var6);
         this.labelElement(var6, var2);
         String var7 = this.ruleBlock.getIgnoreRule();
         if (!var5 && var7 != null) {
            this.addElementToCurrentAlt(this.createOptionalRuleRef(var7, var1));
         }

      }
   }

   public void refCharRange(Token var1, Token var2, Token var3, int var4, boolean var5) {
      if (!(this.grammar instanceof LexerGrammar)) {
         this.tool.error("Character range only valid in lexer", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      } else {
         int var6 = ANTLRLexer.tokenTypeForCharLiteral(var1.getText());
         int var7 = ANTLRLexer.tokenTypeForCharLiteral(var2.getText());
         if (var7 < var6) {
            this.tool.error("Malformed range.", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            if (!((LexerGrammar)this.grammar).caseSensitive) {
               if (var6 < 128 && Character.toLowerCase((char)var6) != (char)var6) {
                  this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
               }

               if (var7 < 128 && Character.toLowerCase((char)var7) != (char)var7) {
                  this.tool.warning("Character literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
               }
            }

            super.refCharRange(var1, var2, var3, var4, var5);
            CharRangeElement var8 = new CharRangeElement((LexerGrammar)this.grammar, var1, var2, var4);
            this.addElementToCurrentAlt(var8);
            this.labelElement(var8, var3);
            String var9 = this.ruleBlock.getIgnoreRule();
            if (!var5 && var9 != null) {
               this.addElementToCurrentAlt(this.createOptionalRuleRef(var9, var1));
            }

         }
      }
   }

   public void refTokensSpecElementOption(Token var1, Token var2, Token var3) {
      TokenSymbol var4 = this.grammar.tokenManager.getTokenSymbol(var1.getText());
      if (var4 == null) {
         this.tool.panic("cannot find " + var1.getText() + "in tokens {...}");
      }

      if (var2.getText().equals("AST")) {
         var4.setASTNodeType(var3.getText());
      } else {
         this.grammar.antlrTool.error("invalid tokens {...} element option:" + var2.getText(), this.grammar.getFilename(), var2.getLine(), var2.getColumn());
      }

   }

   public void refElementOption(Token var1, Token var2) {
      AlternativeElement var3 = this.context().currentElement();
      if (!(var3 instanceof StringLiteralElement) && !(var3 instanceof TokenRefElement) && !(var3 instanceof WildcardElement)) {
         this.tool.error("cannot use element option (" + var1.getText() + ") for this kind of element", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      } else {
         ((GrammarAtom)var3).setOption(var1, var2);
      }

   }

   public void refExceptionHandler(Token var1, Token var2) {
      super.refExceptionHandler(var1, var2);
      if (this.currentExceptionSpec == null) {
         this.tool.panic("exception handler processing internal error");
      }

      this.currentExceptionSpec.addHandler(new ExceptionHandler(var1, var2));
   }

   public void refInitAction(Token var1) {
      super.refAction(var1);
      this.context().block.setInitAction(var1.getText());
   }

   public void refMemberAction(Token var1) {
      this.grammar.classMemberAction = var1;
   }

   public void refPreambleAction(Token var1) {
      super.refPreambleAction(var1);
   }

   public void refReturnAction(Token var1) {
      if (this.grammar instanceof LexerGrammar) {
         String var2 = CodeGenerator.encodeLexerRuleName(((RuleBlock)this.context().block).getRuleName());
         RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var2);
         if (var3.access.equals("public")) {
            this.tool.warning("public Lexical rules cannot specify return type", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            return;
         }
      }

      ((RuleBlock)this.context().block).returnAction = var1.getText();
   }

   public void refRule(Token var1, Token var2, Token var3, Token var4, int var5) {
      if (this.grammar instanceof LexerGrammar) {
         if (var2.type != 24) {
            this.tool.error("Parser rule " + var2.getText() + " referenced in lexer");
            return;
         }

         if (var5 == 2) {
            this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }
      }

      super.refRule(var1, var2, var3, var4, var5);
      this.lastRuleRef = new RuleRefElement(this.grammar, var2, var5);
      if (var4 != null) {
         this.lastRuleRef.setArgs(var4.getText());
      }

      if (var1 != null) {
         this.lastRuleRef.setIdAssign(var1.getText());
      }

      this.addElementToCurrentAlt(this.lastRuleRef);
      String var6 = var2.getText();
      if (var2.type == 24) {
         var6 = CodeGenerator.encodeLexerRuleName(var6);
      }

      RuleSymbol var7 = (RuleSymbol)this.grammar.getSymbol(var6);
      var7.addReference(this.lastRuleRef);
      this.labelElement(this.lastRuleRef, var3);
   }

   public void refSemPred(Token var1) {
      super.refSemPred(var1);
      if (this.context().currentAlt().atStart()) {
         this.context().currentAlt().semPred = var1.getText();
      } else {
         ActionElement var2 = new ActionElement(this.grammar, var1);
         var2.isSemPred = true;
         this.addElementToCurrentAlt(var2);
      }

   }

   public void refStringLiteral(Token var1, Token var2, int var3, boolean var4) {
      super.refStringLiteral(var1, var2, var3, var4);
      if (this.grammar instanceof TreeWalkerGrammar && var3 == 2) {
         this.tool.error("^ not allowed in here for tree-walker", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

      StringLiteralElement var5 = new StringLiteralElement(this.grammar, var1, var3);
      if (this.grammar instanceof LexerGrammar && !((LexerGrammar)this.grammar).caseSensitive) {
         for(int var6 = 1; var6 < var1.getText().length() - 1; ++var6) {
            char var7 = var1.getText().charAt(var6);
            if (var7 < 128 && Character.toLowerCase(var7) != var7) {
               this.tool.warning("Characters of string literal must be lowercase when caseSensitive=false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
               break;
            }
         }
      }

      this.addElementToCurrentAlt(var5);
      this.labelElement(var5, var2);
      String var8 = this.ruleBlock.getIgnoreRule();
      if (!var4 && var8 != null) {
         this.addElementToCurrentAlt(this.createOptionalRuleRef(var8, var1));
      }

   }

   public void refToken(Token var1, Token var2, Token var3, Token var4, boolean var5, int var6, boolean var7) {
      if (this.grammar instanceof LexerGrammar) {
         if (var6 == 2) {
            this.tool.error("AST specification ^ not allowed in lexer", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }

         if (var5) {
            this.tool.error("~TOKEN is not allowed in lexer", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }

         this.refRule(var1, var2, var3, var4, var6);
         String var8 = this.ruleBlock.getIgnoreRule();
         if (!var7 && var8 != null) {
            this.addElementToCurrentAlt(this.createOptionalRuleRef(var8, var2));
         }
      } else {
         if (var1 != null) {
            this.tool.error("Assignment from token reference only allowed in lexer", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }

         if (var4 != null) {
            this.tool.error("Token reference arguments only allowed in lexer", this.grammar.getFilename(), var4.getLine(), var4.getColumn());
         }

         super.refToken(var1, var2, var3, var4, var5, var6, var7);
         TokenRefElement var9 = new TokenRefElement(this.grammar, var2, var5, var6);
         this.addElementToCurrentAlt(var9);
         this.labelElement(var9, var3);
      }

   }

   public void refTokenRange(Token var1, Token var2, Token var3, int var4, boolean var5) {
      if (this.grammar instanceof LexerGrammar) {
         this.tool.error("Token range not allowed in lexer", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      } else {
         super.refTokenRange(var1, var2, var3, var4, var5);
         TokenRangeElement var6 = new TokenRangeElement(this.grammar, var1, var2, var4);
         if (var6.end < var6.begin) {
            this.tool.error("Malformed range.", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            this.addElementToCurrentAlt(var6);
            this.labelElement(var6, var3);
         }
      }
   }

   public void refTreeSpecifier(Token var1) {
      this.context().currentAlt().treeSpecifier = var1;
   }

   public void refWildcard(Token var1, Token var2, int var3) {
      super.refWildcard(var1, var2, var3);
      WildcardElement var4 = new WildcardElement(this.grammar, var1, var3);
      this.addElementToCurrentAlt(var4);
      this.labelElement(var4, var2);
   }

   public void reset() {
      super.reset();
      this.blocks = new LList();
      this.lastRuleRef = null;
      this.ruleEnd = null;
      this.ruleBlock = null;
      this.nested = 0;
      this.currentExceptionSpec = null;
      this.grammarError = false;
   }

   public void setArgOfRuleRef(Token var1) {
      super.setArgOfRuleRef(var1);
      this.lastRuleRef.setArgs(var1.getText());
   }

   public static void setBlock(AlternativeBlock var0, AlternativeBlock var1) {
      var0.setAlternatives(var1.getAlternatives());
      var0.initAction = var1.initAction;
      var0.label = var1.label;
      var0.hasASynPred = var1.hasASynPred;
      var0.hasAnAction = var1.hasAnAction;
      var0.warnWhenFollowAmbig = var1.warnWhenFollowAmbig;
      var0.generateAmbigWarnings = var1.generateAmbigWarnings;
      var0.line = var1.line;
      var0.greedy = var1.greedy;
      var0.greedySet = var1.greedySet;
   }

   public void setRuleOption(Token var1, Token var2) {
      this.ruleBlock.setOption(var1, var2);
   }

   public void setSubruleOption(Token var1, Token var2) {
      this.context().block.setOption(var1, var2);
   }

   public void synPred() {
      if (this.context().block.not) {
         this.tool.error("'~' cannot be applied to syntactic predicate", this.grammar.getFilename(), this.context().block.getLine(), this.context().block.getColumn());
      }

      SynPredBlock var1 = new SynPredBlock(this.grammar);
      setBlock(var1, this.context().block);
      BlockContext var2 = (BlockContext)this.blocks.pop();
      this.blocks.push(new BlockContext());
      this.context().block = var1;
      this.context().blockEnd = var2.blockEnd;
      this.context().blockEnd.block = var1;
   }

   public void zeroOrMoreSubRule() {
      if (this.context().block.not) {
         this.tool.error("'~' cannot be applied to (...)+ subrule", this.grammar.getFilename(), this.context().block.getLine(), this.context().block.getColumn());
      }

      ZeroOrMoreBlock var1 = new ZeroOrMoreBlock(this.grammar);
      setBlock(var1, this.context().block);
      BlockContext var2 = (BlockContext)this.blocks.pop();
      this.blocks.push(new BlockContext());
      this.context().block = var1;
      this.context().blockEnd = var2.blockEnd;
      this.context().blockEnd.block = var1;
   }
}
