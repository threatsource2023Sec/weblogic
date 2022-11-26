package org.antlr.codegen;

import java.io.IOException;
import java.lang.Character.UnicodeBlock;
import java.util.List;
import org.antlr.Tool;
import org.antlr.runtime.Token;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.ST;

public class Target {
   protected String[] targetCharValueEscape = new String[255];

   public Target() {
      this.targetCharValueEscape[10] = "\\n";
      this.targetCharValueEscape[13] = "\\r";
      this.targetCharValueEscape[9] = "\\t";
      this.targetCharValueEscape[8] = "\\b";
      this.targetCharValueEscape[12] = "\\f";
      this.targetCharValueEscape[92] = "\\\\";
      this.targetCharValueEscape[39] = "\\'";
      this.targetCharValueEscape[34] = "\\\"";
   }

   public boolean useBaseTemplatesForSynPredFragments() {
      return true;
   }

   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, ST outputFileST) throws IOException {
      String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
      generator.write(outputFileST, fileName);
   }

   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, ST headerFileST, String extName) throws IOException {
   }

   protected void performGrammarAnalysis(CodeGenerator generator, Grammar grammar) {
      grammar.buildNFA();
      grammar.createLookaheadDFAs();
   }

   public boolean isValidActionScope(int grammarType, String scope) {
      switch (grammarType) {
         case 1:
            if (scope.equals("lexer")) {
               return true;
            }
            break;
         case 2:
            if (scope.equals("parser")) {
               return true;
            }
            break;
         case 3:
            if (scope.equals("treeparser")) {
               return true;
            }
            break;
         case 4:
            if (scope.equals("parser")) {
               return true;
            }

            if (scope.equals("lexer")) {
               return true;
            }
      }

      return false;
   }

   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype) {
      String name = generator.grammar.getTokenDisplayName(ttype);
      return name.charAt(0) == '\'' ? String.valueOf(ttype) : name;
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      StringBuilder buf = new StringBuilder();
      buf.append('\'');
      int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
      if (c < 0) {
         return "'\u0000'";
      } else {
         if (c < this.targetCharValueEscape.length && this.targetCharValueEscape[c] != null) {
            buf.append(this.targetCharValueEscape[c]);
         } else if (UnicodeBlock.of((char)c) == UnicodeBlock.BASIC_LATIN && !Character.isISOControl((char)c)) {
            buf.append((char)c);
         } else {
            String hex = Integer.toHexString(c | 65536).toUpperCase().substring(1, 5);
            buf.append("\\u");
            buf.append(hex);
         }

         buf.append('\'');
         return buf.toString();
      }
   }

   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal) {
      StringBuilder sb = new StringBuilder();
      StringBuilder is = new StringBuilder(literal);
      sb.append('"');

      for(int i = 1; i < is.length() - 1; ++i) {
         if (is.charAt(i) == '\\') {
            switch (is.charAt(i + 1)) {
               case '"':
               case '\\':
               case 'b':
               case 'f':
               case 'n':
               case 'r':
               case 't':
               case 'u':
                  sb.append('\\');
               default:
                  ++i;
            }
         } else if (is.charAt(i) == '"') {
            sb.append('\\');
         }

         sb.append(is.charAt(i));
      }

      sb.append('"');
      return sb.toString();
   }

   public String getTargetStringLiteralFromString(String s, boolean quoted) {
      if (s == null) {
         return null;
      } else {
         StringBuilder buf = new StringBuilder();
         if (quoted) {
            buf.append('"');
         }

         for(int i = 0; i < s.length(); ++i) {
            int c = s.charAt(i);
            if (c != '\'' && c < this.targetCharValueEscape.length && this.targetCharValueEscape[c] != null) {
               buf.append(this.targetCharValueEscape[c]);
            } else {
               buf.append((char)c);
            }
         }

         if (quoted) {
            buf.append('"');
         }

         return buf.toString();
      }
   }

   public String getTargetStringLiteralFromString(String s) {
      return this.getTargetStringLiteralFromString(s, false);
   }

   public String getTarget64BitStringFromValue(long word) {
      int numHexDigits = 16;
      StringBuilder buf = new StringBuilder(numHexDigits + 2);
      buf.append("0x");
      String digits = Long.toHexString(word);
      digits = digits.toUpperCase();
      int padding = numHexDigits - digits.length();

      for(int i = 1; i <= padding; ++i) {
         buf.append('0');
      }

      buf.append(digits);
      return buf.toString();
   }

   public String encodeIntAsCharEscape(int v) {
      if (v <= 127) {
         return "\\" + Integer.toOctalString(v);
      } else {
         String hex = Integer.toHexString(v | 65536).substring(1, 5);
         return "\\u" + hex;
      }
   }

   public int getMaxCharValue(CodeGenerator generator) {
      return 65535;
   }

   public List postProcessAction(List chunks, Token actionToken) {
      return chunks;
   }
}
