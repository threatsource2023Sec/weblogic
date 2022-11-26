package org.antlr.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.antlr.Tool;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.tree.ParseTree;

public class Interp {
   public static void main(String[] args) throws Exception {
      if (args.length != 4) {
         System.err.println("java Interp file.g tokens-to-ignore start-rule input-file");
      } else {
         String grammarFileName = args[0];
         String ignoreTokens = args[1];
         String startRule = args[2];
         String inputFileName = args[3];
         Tool tool = new Tool();
         CompositeGrammar composite = new CompositeGrammar();
         Grammar parser = new Grammar(tool, grammarFileName, composite);
         composite.setDelegationRoot(parser);
         FileReader fr = new FileReader(grammarFileName);
         BufferedReader br = new BufferedReader(fr);
         parser.parseAndBuildAST(br);
         br.close();
         parser.composite.assignTokenTypes();
         parser.composite.defineGrammarSymbols();
         parser.composite.createNFAs();
         List leftRecursiveRules = parser.checkAllRulesForLeftRecursion();
         if (leftRecursiveRules.size() <= 0) {
            if (parser.getRule(startRule) == null) {
               System.out.println("undefined start rule " + startRule);
            } else {
               String lexerGrammarText = parser.getLexerGrammar();
               Grammar lexer = new Grammar(tool);
               lexer.importTokenVocabulary(parser);
               lexer.fileName = grammarFileName;
               lexer.setTool(tool);
               if (lexerGrammarText != null) {
                  lexer.setGrammarContent(lexerGrammarText);
               } else {
                  System.err.println("no lexer grammar found in " + grammarFileName);
               }

               lexer.composite.createNFAs();
               CharStream input = new ANTLRFileStream(inputFileName);
               Interpreter lexEngine = new Interpreter(lexer, input);
               FilteringTokenStream tokens = new FilteringTokenStream(lexEngine);
               StringTokenizer tk = new StringTokenizer(ignoreTokens, " ");

               while(tk.hasMoreTokens()) {
                  String tokenName = tk.nextToken();
                  tokens.setTokenTypeChannel(lexer.getTokenType(tokenName), 99);
               }

               if (parser.getRule(startRule) == null) {
                  System.err.println("Rule " + startRule + " does not exist in " + grammarFileName);
               } else {
                  Interpreter parseEngine = new Interpreter(parser, tokens);
                  ParseTree t = parseEngine.parse(startRule);
                  System.out.println(t.toStringTree());
               }
            }
         }
      }
   }

   public static class FilteringTokenStream extends CommonTokenStream {
      Set hide = new HashSet();

      public FilteringTokenStream(TokenSource src) {
         super(src);
      }

      protected void sync(int i) {
         super.sync(i);
         if (this.hide.contains(this.get(i).getType())) {
            this.get(i).setChannel(99);
         }

      }

      public void setTokenTypeChannel(int ttype, int channel) {
         this.hide.add(ttype);
      }
   }
}
