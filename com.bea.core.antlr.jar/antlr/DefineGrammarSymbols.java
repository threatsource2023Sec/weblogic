package antlr;

import antlr.collections.impl.BitSet;
import java.util.Hashtable;

public class DefineGrammarSymbols implements ANTLRGrammarParseBehavior {
   protected Hashtable grammars = new Hashtable();
   protected Hashtable tokenManagers = new Hashtable();
   protected Grammar grammar;
   protected Tool tool;
   LLkAnalyzer analyzer;
   String[] args;
   static final String DEFAULT_TOKENMANAGER_NAME = "*default";
   protected Hashtable headerActions = new Hashtable();
   Token thePreambleAction = new CommonToken(0, "");
   String language = "Java";
   protected int numLexers = 0;
   protected int numParsers = 0;
   protected int numTreeParsers = 0;

   public DefineGrammarSymbols(Tool var1, String[] var2, LLkAnalyzer var3) {
      this.tool = var1;
      this.args = var2;
      this.analyzer = var3;
   }

   public void _refStringLiteral(Token var1, Token var2, int var3, boolean var4) {
      if (!(this.grammar instanceof LexerGrammar)) {
         String var5 = var1.getText();
         if (this.grammar.tokenManager.getTokenSymbol(var5) != null) {
            return;
         }

         StringLiteralSymbol var6 = new StringLiteralSymbol(var5);
         int var7 = this.grammar.tokenManager.nextTokenType();
         var6.setTokenType(var7);
         this.grammar.tokenManager.define(var6);
      }

   }

   public void _refToken(Token var1, Token var2, Token var3, Token var4, boolean var5, int var6, boolean var7) {
      String var8 = var2.getText();
      if (!this.grammar.tokenManager.tokenDefined(var8)) {
         int var9 = this.grammar.tokenManager.nextTokenType();
         TokenSymbol var10 = new TokenSymbol(var8);
         var10.setTokenType(var9);
         this.grammar.tokenManager.define(var10);
      }

   }

   public void abortGrammar() {
      if (this.grammar != null && this.grammar.getClassName() != null) {
         this.grammars.remove(this.grammar.getClassName());
      }

      this.grammar = null;
   }

   public void beginAlt(boolean var1) {
   }

   public void beginChildList() {
   }

   public void beginExceptionGroup() {
   }

   public void beginExceptionSpec(Token var1) {
   }

   public void beginSubRule(Token var1, Token var2, boolean var3) {
   }

   public void beginTree(Token var1) throws SemanticException {
   }

   public void defineRuleName(Token var1, String var2, boolean var3, String var4) throws SemanticException {
      String var5 = var1.getText();
      if (var1.type == 24) {
         var5 = CodeGenerator.encodeLexerRuleName(var5);
         if (!this.grammar.tokenManager.tokenDefined(var1.getText())) {
            int var6 = this.grammar.tokenManager.nextTokenType();
            TokenSymbol var7 = new TokenSymbol(var1.getText());
            var7.setTokenType(var6);
            this.grammar.tokenManager.define(var7);
         }
      }

      RuleSymbol var8;
      if (this.grammar.isDefined(var5)) {
         var8 = (RuleSymbol)this.grammar.getSymbol(var5);
         if (var8.isDefined()) {
            this.tool.error("redefinition of rule " + var5, this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else {
         var8 = new RuleSymbol(var5);
         this.grammar.define(var8);
      }

      var8.setDefined();
      var8.access = var2;
      var8.comment = var4;
   }

   public void defineToken(Token var1, Token var2) {
      String var3 = null;
      String var4 = null;
      if (var1 != null) {
         var3 = var1.getText();
      }

      if (var2 != null) {
         var4 = var2.getText();
      }

      TokenSymbol var6;
      if (var4 == null) {
         if (this.grammar.tokenManager.tokenDefined(var3)) {
            this.tool.warning("Redefinition of token in tokens {...}: " + var3, this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            return;
         }

         int var8 = this.grammar.tokenManager.nextTokenType();
         var6 = new TokenSymbol(var3);
         var6.setTokenType(var8);
         this.grammar.tokenManager.define(var6);
      } else {
         StringLiteralSymbol var5 = (StringLiteralSymbol)this.grammar.tokenManager.getTokenSymbol(var4);
         if (var5 != null) {
            if (var3 == null || var5.getLabel() != null) {
               this.tool.warning("Redefinition of literal in tokens {...}: " + var4, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
               return;
            }

            if (var3 != null) {
               var5.setLabel(var3);
               this.grammar.tokenManager.mapToTokenSymbol(var3, var5);
            }
         }

         if (var3 != null) {
            var6 = this.grammar.tokenManager.getTokenSymbol(var3);
            if (var6 != null) {
               if (var6 instanceof StringLiteralSymbol) {
                  this.tool.warning("Redefinition of token in tokens {...}: " + var3, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
                  return;
               }

               int var7 = var6.getTokenType();
               var5 = new StringLiteralSymbol(var4);
               var5.setTokenType(var7);
               var5.setLabel(var3);
               this.grammar.tokenManager.define(var5);
               this.grammar.tokenManager.mapToTokenSymbol(var3, var5);
               return;
            }
         }

         var5 = new StringLiteralSymbol(var4);
         int var9 = this.grammar.tokenManager.nextTokenType();
         var5.setTokenType(var9);
         var5.setLabel(var3);
         this.grammar.tokenManager.define(var5);
         if (var3 != null) {
            this.grammar.tokenManager.mapToTokenSymbol(var3, var5);
         }
      }

   }

   public void endAlt() {
   }

   public void endChildList() {
   }

   public void endExceptionGroup() {
   }

   public void endExceptionSpec() {
   }

   public void endGrammar() {
   }

   public void endOptions() {
      TokenManager var3;
      SimpleTokenManager var4;
      if (this.grammar.exportVocab == null && this.grammar.importVocab == null) {
         this.grammar.exportVocab = this.grammar.getClassName();
         if (this.tokenManagers.containsKey("*default")) {
            this.grammar.exportVocab = "*default";
            var3 = (TokenManager)this.tokenManagers.get("*default");
            this.grammar.setTokenManager(var3);
         } else {
            var4 = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
            this.grammar.setTokenManager(var4);
            this.tokenManagers.put(this.grammar.exportVocab, var4);
            this.tokenManagers.put("*default", var4);
         }
      } else {
         ImportVocabTokenManager var1;
         TokenManager var2;
         if (this.grammar.exportVocab == null && this.grammar.importVocab != null) {
            this.grammar.exportVocab = this.grammar.getClassName();
            if (this.grammar.importVocab.equals(this.grammar.exportVocab)) {
               this.tool.warning("Grammar " + this.grammar.getClassName() + " cannot have importVocab same as default output vocab (grammar name); ignored.");
               this.grammar.importVocab = null;
               this.endOptions();
            } else if (this.tokenManagers.containsKey(this.grammar.importVocab)) {
               var3 = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
               var2 = (TokenManager)var3.clone();
               var2.setName(this.grammar.exportVocab);
               var2.setReadOnly(false);
               this.grammar.setTokenManager(var2);
               this.tokenManagers.put(this.grammar.exportVocab, var2);
            } else {
               var1 = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
               var1.setReadOnly(false);
               this.tokenManagers.put(this.grammar.exportVocab, var1);
               this.grammar.setTokenManager(var1);
               if (!this.tokenManagers.containsKey("*default")) {
                  this.tokenManagers.put("*default", var1);
               }

            }
         } else if (this.grammar.exportVocab != null && this.grammar.importVocab == null) {
            if (this.tokenManagers.containsKey(this.grammar.exportVocab)) {
               var3 = (TokenManager)this.tokenManagers.get(this.grammar.exportVocab);
               this.grammar.setTokenManager(var3);
            } else {
               var4 = new SimpleTokenManager(this.grammar.exportVocab, this.tool);
               this.grammar.setTokenManager(var4);
               this.tokenManagers.put(this.grammar.exportVocab, var4);
               if (!this.tokenManagers.containsKey("*default")) {
                  this.tokenManagers.put("*default", var4);
               }

            }
         } else if (this.grammar.exportVocab != null && this.grammar.importVocab != null) {
            if (this.grammar.importVocab.equals(this.grammar.exportVocab)) {
               this.tool.error("exportVocab of " + this.grammar.exportVocab + " same as importVocab; probably not what you want");
            }

            if (this.tokenManagers.containsKey(this.grammar.importVocab)) {
               var3 = (TokenManager)this.tokenManagers.get(this.grammar.importVocab);
               var2 = (TokenManager)var3.clone();
               var2.setName(this.grammar.exportVocab);
               var2.setReadOnly(false);
               this.grammar.setTokenManager(var2);
               this.tokenManagers.put(this.grammar.exportVocab, var2);
            } else {
               var1 = new ImportVocabTokenManager(this.grammar, this.grammar.importVocab + CodeGenerator.TokenTypesFileSuffix + CodeGenerator.TokenTypesFileExt, this.grammar.exportVocab, this.tool);
               var1.setReadOnly(false);
               this.tokenManagers.put(this.grammar.exportVocab, var1);
               this.grammar.setTokenManager(var1);
               if (!this.tokenManagers.containsKey("*default")) {
                  this.tokenManagers.put("*default", var1);
               }

            }
         }
      }
   }

   public void endRule(String var1) {
   }

   public void endSubRule() {
   }

   public void endTree() {
   }

   public void hasError() {
   }

   public void noASTSubRule() {
   }

   public void oneOrMoreSubRule() {
   }

   public void optionalSubRule() {
   }

   public void setUserExceptions(String var1) {
   }

   public void refAction(Token var1) {
   }

   public void refArgAction(Token var1) {
   }

   public void refCharLiteral(Token var1, Token var2, boolean var3, int var4, boolean var5) {
   }

   public void refCharRange(Token var1, Token var2, Token var3, int var4, boolean var5) {
   }

   public void refElementOption(Token var1, Token var2) {
   }

   public void refTokensSpecElementOption(Token var1, Token var2, Token var3) {
   }

   public void refExceptionHandler(Token var1, Token var2) {
   }

   public void refHeaderAction(Token var1, Token var2) {
      String var3;
      if (var1 == null) {
         var3 = "";
      } else {
         var3 = StringUtils.stripFrontBack(var1.getText(), "\"", "\"");
      }

      if (this.headerActions.containsKey(var3)) {
         if (var3.equals("")) {
            this.tool.error(var2.getLine() + ": header action already defined");
         } else {
            this.tool.error(var2.getLine() + ": header action '" + var3 + "' already defined");
         }
      }

      this.headerActions.put(var3, var2);
   }

   public String getHeaderAction(String var1) {
      Token var2 = (Token)this.headerActions.get(var1);
      return var2 == null ? "" : var2.getText();
   }

   public int getHeaderActionLine(String var1) {
      Token var2 = (Token)this.headerActions.get(var1);
      return var2 == null ? 0 : var2.getLine();
   }

   public void refInitAction(Token var1) {
   }

   public void refMemberAction(Token var1) {
   }

   public void refPreambleAction(Token var1) {
      this.thePreambleAction = var1;
   }

   public void refReturnAction(Token var1) {
   }

   public void refRule(Token var1, Token var2, Token var3, Token var4, int var5) {
      String var6 = var2.getText();
      if (var2.type == 24) {
         var6 = CodeGenerator.encodeLexerRuleName(var6);
      }

      if (!this.grammar.isDefined(var6)) {
         this.grammar.define(new RuleSymbol(var6));
      }

   }

   public void refSemPred(Token var1) {
   }

   public void refStringLiteral(Token var1, Token var2, int var3, boolean var4) {
      this._refStringLiteral(var1, var2, var3, var4);
   }

   public void refToken(Token var1, Token var2, Token var3, Token var4, boolean var5, int var6, boolean var7) {
      this._refToken(var1, var2, var3, var4, var5, var6, var7);
   }

   public void refTokenRange(Token var1, Token var2, Token var3, int var4, boolean var5) {
      if (var1.getText().charAt(0) == '"') {
         this.refStringLiteral(var1, (Token)null, 1, var5);
      } else {
         this._refToken((Token)null, var1, (Token)null, (Token)null, false, 1, var5);
      }

      if (var2.getText().charAt(0) == '"') {
         this._refStringLiteral(var2, (Token)null, 1, var5);
      } else {
         this._refToken((Token)null, var2, (Token)null, (Token)null, false, 1, var5);
      }

   }

   public void refTreeSpecifier(Token var1) {
   }

   public void refWildcard(Token var1, Token var2, int var3) {
   }

   public void reset() {
      this.grammar = null;
   }

   public void setArgOfRuleRef(Token var1) {
   }

   public void setCharVocabulary(BitSet var1) {
      ((LexerGrammar)this.grammar).setCharVocabulary(var1);
   }

   public void setFileOption(Token var1, Token var2, String var3) {
      if (var1.getText().equals("language")) {
         if (var2.getType() == 6) {
            this.language = StringUtils.stripBack(StringUtils.stripFront(var2.getText(), '"'), '"');
         } else if (var2.getType() != 24 && var2.getType() != 41) {
            this.tool.error("language option must be string or identifier", var3, var2.getLine(), var2.getColumn());
         } else {
            this.language = var2.getText();
         }
      } else if (var1.getText().equals("mangleLiteralPrefix")) {
         if (var2.getType() == 6) {
            this.tool.literalsPrefix = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
         } else {
            this.tool.error("mangleLiteralPrefix option must be string", var3, var2.getLine(), var2.getColumn());
         }
      } else if (var1.getText().equals("upperCaseMangledLiterals")) {
         if (var2.getText().equals("true")) {
            this.tool.upperCaseMangledLiterals = true;
         } else if (var2.getText().equals("false")) {
            this.tool.upperCaseMangledLiterals = false;
         } else {
            this.grammar.antlrTool.error("Value for upperCaseMangledLiterals must be true or false", var3, var1.getLine(), var1.getColumn());
         }
      } else if (!var1.getText().equals("namespaceStd") && !var1.getText().equals("namespaceAntlr") && !var1.getText().equals("genHashLines")) {
         if (var1.getText().equals("namespace")) {
            if (!this.language.equals("Cpp") && !this.language.equals("CSharp")) {
               this.tool.error(var1.getText() + " option only valid for C++ and C# (a.k.a CSharp)", var3, var1.getLine(), var1.getColumn());
            } else if (var2.getType() != 6) {
               this.tool.error(var1.getText() + " option must be a string", var3, var2.getLine(), var2.getColumn());
            } else if (var1.getText().equals("namespace")) {
               this.tool.setNameSpace(var2.getText());
            }
         } else {
            this.tool.error("Invalid file-level option: " + var1.getText(), var3, var1.getLine(), var2.getColumn());
         }
      } else if (!this.language.equals("Cpp")) {
         this.tool.error(var1.getText() + " option only valid for C++", var3, var1.getLine(), var1.getColumn());
      } else if (var1.getText().equals("noConstructors")) {
         if (!var2.getText().equals("true") && !var2.getText().equals("false")) {
            this.tool.error("noConstructors option must be true or false", var3, var2.getLine(), var2.getColumn());
         }

         this.tool.noConstructors = var2.getText().equals("true");
      } else if (var1.getText().equals("genHashLines")) {
         if (!var2.getText().equals("true") && !var2.getText().equals("false")) {
            this.tool.error("genHashLines option must be true or false", var3, var2.getLine(), var2.getColumn());
         }

         this.tool.genHashLines = var2.getText().equals("true");
      } else if (var2.getType() != 6) {
         this.tool.error(var1.getText() + " option must be a string", var3, var2.getLine(), var2.getColumn());
      } else if (var1.getText().equals("namespaceStd")) {
         this.tool.namespaceStd = var2.getText();
      } else if (var1.getText().equals("namespaceAntlr")) {
         this.tool.namespaceAntlr = var2.getText();
      }

   }

   public void setGrammarOption(Token var1, Token var2) {
      if (!var1.getText().equals("tokdef") && !var1.getText().equals("tokenVocabulary")) {
         if (var1.getText().equals("literal") && this.grammar instanceof LexerGrammar) {
            this.tool.error("the literal option is invalid >= ANTLR 2.6.0.\n  Use the \"tokens {...}\" mechanism instead.", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         } else if (var1.getText().equals("exportVocab")) {
            if (var2.getType() != 41 && var2.getType() != 24) {
               this.tool.error("exportVocab must be an identifier", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
            } else {
               this.grammar.exportVocab = var2.getText();
            }
         } else if (var1.getText().equals("importVocab")) {
            if (var2.getType() != 41 && var2.getType() != 24) {
               this.tool.error("importVocab must be an identifier", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
            } else {
               this.grammar.importVocab = var2.getText();
            }
         } else if (var1.getText().equals("k")) {
            if (this.grammar instanceof TreeWalkerGrammar && !var2.getText().equals("1")) {
               this.tool.error("Treewalkers only support k=1", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
            } else {
               this.grammar.setOption(var1.getText(), var2);
            }
         } else {
            this.grammar.setOption(var1.getText(), var2);
         }
      } else {
         this.tool.error("tokdef/tokenVocabulary options are invalid >= ANTLR 2.6.0.\n  Use importVocab/exportVocab instead.  Please see the documentation.\n  The previous options were so heinous that Terence changed the whole\n  vocabulary mechanism; it was better to change the names rather than\n  subtly change the functionality of the known options.  Sorry!", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
      }

   }

   public void setRuleOption(Token var1, Token var2) {
   }

   public void setSubruleOption(Token var1, Token var2) {
   }

   public void startLexer(String var1, Token var2, String var3, String var4) {
      if (this.numLexers > 0) {
         this.tool.panic("You may only have one lexer per grammar file: class " + var2.getText());
      }

      ++this.numLexers;
      this.reset();
      Grammar var5 = (Grammar)this.grammars.get(var2);
      if (var5 != null) {
         if (!(var5 instanceof LexerGrammar)) {
            this.tool.panic("'" + var2.getText() + "' is already defined as a non-lexer");
         } else {
            this.tool.panic("Lexer '" + var2.getText() + "' is already defined");
         }
      } else {
         LexerGrammar var6 = new LexerGrammar(var2.getText(), this.tool, var3);
         var6.comment = var4;
         var6.processArguments(this.args);
         var6.setFilename(var1);
         this.grammars.put(var6.getClassName(), var6);
         var6.preambleAction = this.thePreambleAction;
         this.thePreambleAction = new CommonToken(0, "");
         this.grammar = var6;
      }

   }

   public void startParser(String var1, Token var2, String var3, String var4) {
      if (this.numParsers > 0) {
         this.tool.panic("You may only have one parser per grammar file: class " + var2.getText());
      }

      ++this.numParsers;
      this.reset();
      Grammar var5 = (Grammar)this.grammars.get(var2);
      if (var5 != null) {
         if (!(var5 instanceof ParserGrammar)) {
            this.tool.panic("'" + var2.getText() + "' is already defined as a non-parser");
         } else {
            this.tool.panic("Parser '" + var2.getText() + "' is already defined");
         }
      } else {
         this.grammar = new ParserGrammar(var2.getText(), this.tool, var3);
         this.grammar.comment = var4;
         this.grammar.processArguments(this.args);
         this.grammar.setFilename(var1);
         this.grammars.put(this.grammar.getClassName(), this.grammar);
         this.grammar.preambleAction = this.thePreambleAction;
         this.thePreambleAction = new CommonToken(0, "");
      }

   }

   public void startTreeWalker(String var1, Token var2, String var3, String var4) {
      if (this.numTreeParsers > 0) {
         this.tool.panic("You may only have one tree parser per grammar file: class " + var2.getText());
      }

      ++this.numTreeParsers;
      this.reset();
      Grammar var5 = (Grammar)this.grammars.get(var2);
      if (var5 != null) {
         if (!(var5 instanceof TreeWalkerGrammar)) {
            this.tool.panic("'" + var2.getText() + "' is already defined as a non-tree-walker");
         } else {
            this.tool.panic("Tree-walker '" + var2.getText() + "' is already defined");
         }
      } else {
         this.grammar = new TreeWalkerGrammar(var2.getText(), this.tool, var3);
         this.grammar.comment = var4;
         this.grammar.processArguments(this.args);
         this.grammar.setFilename(var1);
         this.grammars.put(this.grammar.getClassName(), this.grammar);
         this.grammar.preambleAction = this.thePreambleAction;
         this.thePreambleAction = new CommonToken(0, "");
      }

   }

   public void synPred() {
   }

   public void zeroOrMoreSubRule() {
   }
}
