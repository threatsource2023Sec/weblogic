package antlr;

import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class Grammar {
   protected Tool antlrTool;
   protected CodeGenerator generator;
   protected LLkGrammarAnalyzer theLLkAnalyzer;
   protected Hashtable symbols;
   protected boolean buildAST = false;
   protected boolean analyzerDebug = false;
   protected boolean interactive = false;
   protected String superClass = null;
   protected TokenManager tokenManager;
   protected String exportVocab = null;
   protected String importVocab = null;
   protected Hashtable options;
   protected Vector rules;
   protected Token preambleAction = new CommonToken(0, "");
   protected String className = null;
   protected String fileName = null;
   protected Token classMemberAction = new CommonToken(0, "");
   protected boolean hasSyntacticPredicate = false;
   protected boolean hasUserErrorHandling = false;
   protected int maxk = 1;
   protected boolean traceRules = false;
   protected boolean debuggingOutput = false;
   protected boolean defaultErrorHandler = true;
   protected String comment = null;

   public Grammar(String var1, Tool var2, String var3) {
      this.className = var1;
      this.antlrTool = var2;
      this.symbols = new Hashtable();
      this.options = new Hashtable();
      this.rules = new Vector(100);
      this.superClass = var3;
   }

   public void define(RuleSymbol var1) {
      this.rules.appendElement(var1);
      this.symbols.put(var1.getId(), var1);
   }

   public abstract void generate() throws IOException;

   protected String getClassName() {
      return this.className;
   }

   public boolean getDefaultErrorHandler() {
      return this.defaultErrorHandler;
   }

   public String getFilename() {
      return this.fileName;
   }

   public int getIntegerOption(String var1) throws NumberFormatException {
      Token var2 = (Token)this.options.get(var1);
      if (var2 != null && var2.getType() == 20) {
         return Integer.parseInt(var2.getText());
      } else {
         throw new NumberFormatException();
      }
   }

   public Token getOption(String var1) {
      return (Token)this.options.get(var1);
   }

   protected abstract String getSuperClass();

   public GrammarSymbol getSymbol(String var1) {
      return (GrammarSymbol)this.symbols.get(var1);
   }

   public Enumeration getSymbols() {
      return this.symbols.elements();
   }

   public boolean hasOption(String var1) {
      return this.options.containsKey(var1);
   }

   public boolean isDefined(String var1) {
      return this.symbols.containsKey(var1);
   }

   public abstract void processArguments(String[] var1);

   public void setCodeGenerator(CodeGenerator var1) {
      this.generator = var1;
   }

   public void setFilename(String var1) {
      this.fileName = var1;
   }

   public void setGrammarAnalyzer(LLkGrammarAnalyzer var1) {
      this.theLLkAnalyzer = var1;
   }

   public boolean setOption(String var1, Token var2) {
      this.options.put(var1, var2);
      String var3 = var2.getText();
      if (var1.equals("k")) {
         try {
            this.maxk = this.getIntegerOption("k");
            if (this.maxk <= 0) {
               this.antlrTool.error("option 'k' must be greater than 0 (was " + var2.getText() + ")", this.getFilename(), var2.getLine(), var2.getColumn());
               this.maxk = 1;
            }
         } catch (NumberFormatException var6) {
            this.antlrTool.error("option 'k' must be an integer (was " + var2.getText() + ")", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else {
         int var4;
         if (var1.equals("codeGenMakeSwitchThreshold")) {
            try {
               var4 = this.getIntegerOption("codeGenMakeSwitchThreshold");
            } catch (NumberFormatException var7) {
               this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", this.getFilename(), var2.getLine(), var2.getColumn());
            }

            return true;
         } else if (var1.equals("codeGenBitsetTestThreshold")) {
            try {
               var4 = this.getIntegerOption("codeGenBitsetTestThreshold");
            } catch (NumberFormatException var8) {
               this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", this.getFilename(), var2.getLine(), var2.getColumn());
            }

            return true;
         } else if (var1.equals("defaultErrorHandler")) {
            if (var3.equals("true")) {
               this.defaultErrorHandler = true;
            } else if (var3.equals("false")) {
               this.defaultErrorHandler = false;
            } else {
               this.antlrTool.error("Value for defaultErrorHandler must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
            }

            return true;
         } else if (var1.equals("analyzerDebug")) {
            if (var3.equals("true")) {
               this.analyzerDebug = true;
            } else if (var3.equals("false")) {
               this.analyzerDebug = false;
            } else {
               this.antlrTool.error("option 'analyzerDebug' must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
            }

            return true;
         } else if (var1.equals("codeGenDebug")) {
            if (var3.equals("true")) {
               this.analyzerDebug = true;
            } else if (var3.equals("false")) {
               this.analyzerDebug = false;
            } else {
               this.antlrTool.error("option 'codeGenDebug' must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
            }

            return true;
         } else if (var1.equals("classHeaderSuffix")) {
            return true;
         } else if (var1.equals("classHeaderPrefix")) {
            return true;
         } else if (var1.equals("namespaceAntlr")) {
            return true;
         } else if (var1.equals("namespaceStd")) {
            return true;
         } else if (var1.equals("genHashLines")) {
            return true;
         } else {
            return var1.equals("noConstructors");
         }
      }
   }

   public void setTokenManager(TokenManager var1) {
      this.tokenManager = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer(20000);
      Enumeration var2 = this.rules.elements();

      while(var2.hasMoreElements()) {
         RuleSymbol var3 = (RuleSymbol)var2.nextElement();
         if (!var3.id.equals("mnextToken")) {
            var1.append(var3.getBlock().toString());
            var1.append("\n\n");
         }
      }

      return var1.toString();
   }
}
