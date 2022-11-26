package org.antlr.codegen;

import org.antlr.Tool;
import org.antlr.misc.Utils;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.ST;

public class DelphiTarget extends Target {
   public DelphiTarget() {
      this.targetCharValueEscape[10] = "'#10'";
      this.targetCharValueEscape[13] = "'#13'";
      this.targetCharValueEscape[9] = "'#9'";
      this.targetCharValueEscape[8] = "\\b";
      this.targetCharValueEscape[12] = "\\f";
      this.targetCharValueEscape[92] = "\\";
      this.targetCharValueEscape[39] = "''";
      this.targetCharValueEscape[34] = "'";
   }

   protected ST chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, ST recognizerST, ST cyclicDFAST) {
      return recognizerST;
   }

   public String encodeIntAsCharEscape(int v) {
      String hex;
      if (v <= 127) {
         hex = Integer.toHexString(v | 65536).substring(3, 5);
         return "'#$" + hex + "'";
      } else {
         hex = Integer.toHexString(v | 65536).substring(1, 5);
         return "'#$" + hex + "'";
      }
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      StringBuilder buf = new StringBuilder();
      int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
      if (c < 0) {
         return "0";
      } else {
         buf.append(c);
         return buf.toString();
      }
   }

   public String getTargetStringLiteralFromString(String s, boolean quoted) {
      if (s == null) {
         return null;
      } else {
         StringBuilder buf = new StringBuilder();
         if (quoted) {
            buf.append('\'');
         }

         for(int i = 0; i < s.length(); ++i) {
            int c = s.charAt(i);
            if (c != '"' && c < this.targetCharValueEscape.length && this.targetCharValueEscape[c] != null) {
               buf.append(this.targetCharValueEscape[c]);
            } else {
               buf.append((char)c);
            }

            if ((i & 127) == 127) {
               buf.append("' + \r\n  '");
            }
         }

         if (quoted) {
            buf.append('\'');
         }

         return buf.toString();
      }
   }

   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal) {
      literal = Utils.replace(literal, "\\'", "''");
      literal = Utils.replace(literal, "\\r\\n", "'#13#10'");
      literal = Utils.replace(literal, "\\r", "'#13'");
      literal = Utils.replace(literal, "\\n", "'#10'");
      StringBuilder buf = new StringBuilder(literal);
      buf.setCharAt(0, '\'');
      buf.setCharAt(literal.length() - 1, '\'');
      return buf.toString();
   }

   public String getTarget64BitStringFromValue(long word) {
      int numHexDigits = 16;
      StringBuilder buf = new StringBuilder(numHexDigits + 2);
      buf.append("$");
      String digits = Long.toHexString(word);
      digits = digits.toUpperCase();
      int padding = numHexDigits - digits.length();

      for(int i = 1; i <= padding; ++i) {
         buf.append('0');
      }

      buf.append(digits);
      return buf.toString();
   }
}
