package antlr.preprocessor;

import antlr.ANTLRException;
import antlr.TokenStreamException;
import antlr.collections.impl.IndexedVector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class Hierarchy {
   protected Grammar LexerRoot = null;
   protected Grammar ParserRoot = null;
   protected Grammar TreeParserRoot = null;
   protected Hashtable symbols;
   protected Hashtable files;
   protected antlr.Tool antlrTool;

   public Hierarchy(antlr.Tool var1) {
      this.antlrTool = var1;
      this.LexerRoot = new Grammar(var1, "Lexer", (String)null, (IndexedVector)null);
      this.ParserRoot = new Grammar(var1, "Parser", (String)null, (IndexedVector)null);
      this.TreeParserRoot = new Grammar(var1, "TreeParser", (String)null, (IndexedVector)null);
      this.symbols = new Hashtable(10);
      this.files = new Hashtable(10);
      this.LexerRoot.setPredefined(true);
      this.ParserRoot.setPredefined(true);
      this.TreeParserRoot.setPredefined(true);
      this.symbols.put(this.LexerRoot.getName(), this.LexerRoot);
      this.symbols.put(this.ParserRoot.getName(), this.ParserRoot);
      this.symbols.put(this.TreeParserRoot.getName(), this.TreeParserRoot);
   }

   public void addGrammar(Grammar var1) {
      var1.setHierarchy(this);
      this.symbols.put(var1.getName(), var1);
      GrammarFile var2 = this.getFile(var1.getFileName());
      var2.addGrammar(var1);
   }

   public void addGrammarFile(GrammarFile var1) {
      this.files.put(var1.getName(), var1);
   }

   public void expandGrammarsInFile(String var1) {
      GrammarFile var2 = this.getFile(var1);
      Enumeration var3 = var2.getGrammars().elements();

      while(var3.hasMoreElements()) {
         Grammar var4 = (Grammar)var3.nextElement();
         var4.expandInPlace();
      }

   }

   public Grammar findRoot(Grammar var1) {
      if (var1.getSuperGrammarName() == null) {
         return var1;
      } else {
         Grammar var2 = var1.getSuperGrammar();
         return var2 == null ? var1 : this.findRoot(var2);
      }
   }

   public GrammarFile getFile(String var1) {
      return (GrammarFile)this.files.get(var1);
   }

   public Grammar getGrammar(String var1) {
      return (Grammar)this.symbols.get(var1);
   }

   public static String optionsToString(IndexedVector var0) {
      String var1 = "options {" + System.getProperty("line.separator");

      for(Enumeration var2 = var0.elements(); var2.hasMoreElements(); var1 = var1 + (Option)var2.nextElement() + System.getProperty("line.separator")) {
      }

      var1 = var1 + "}" + System.getProperty("line.separator") + System.getProperty("line.separator");
      return var1;
   }

   public void readGrammarFile(String var1) throws FileNotFoundException {
      BufferedReader var2 = new BufferedReader(new FileReader(var1));
      this.addGrammarFile(new GrammarFile(this.antlrTool, var1));
      PreprocessorLexer var3 = new PreprocessorLexer(var2);
      var3.setFilename(var1);
      Preprocessor var4 = new Preprocessor(var3);
      var4.setTool(this.antlrTool);
      var4.setFilename(var1);

      try {
         var4.grammarFile(this, var1);
      } catch (TokenStreamException var6) {
         this.antlrTool.toolError("Token stream error reading grammar(s):\n" + var6);
      } catch (ANTLRException var7) {
         this.antlrTool.toolError("error reading grammar(s):\n" + var7);
      }

   }

   public boolean verifyThatHierarchyIsComplete() {
      boolean var1 = true;
      Enumeration var2 = this.symbols.elements();

      Grammar var3;
      while(var2.hasMoreElements()) {
         var3 = (Grammar)var2.nextElement();
         if (var3.getSuperGrammarName() != null) {
            Grammar var4 = var3.getSuperGrammar();
            if (var4 == null) {
               this.antlrTool.toolError("grammar " + var3.getSuperGrammarName() + " not defined");
               var1 = false;
               this.symbols.remove(var3.getName());
            }
         }
      }

      if (!var1) {
         return false;
      } else {
         var2 = this.symbols.elements();

         while(var2.hasMoreElements()) {
            var3 = (Grammar)var2.nextElement();
            if (var3.getSuperGrammarName() != null) {
               var3.setType(this.findRoot(var3).getName());
            }
         }

         return true;
      }
   }

   public antlr.Tool getTool() {
      return this.antlrTool;
   }

   public void setTool(antlr.Tool var1) {
      this.antlrTool = var1;
   }
}
