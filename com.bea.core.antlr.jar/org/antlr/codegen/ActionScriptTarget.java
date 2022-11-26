package org.antlr.codegen;

import org.antlr.Tool;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.ST;

public class ActionScriptTarget extends Target {
   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
      return String.valueOf(c);
   }

   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype) {
      if (ttype >= 0 && ttype <= 3) {
         return String.valueOf(ttype);
      } else {
         String name = generator.grammar.getTokenDisplayName(ttype);
         return name.charAt(0) == '\'' ? String.valueOf(ttype) : name;
      }
   }

   public String encodeIntAsCharEscape(int v) {
      if (v <= 255) {
         return "\\x" + Integer.toHexString(v | 256).substring(1, 3);
      } else if (v <= 32767) {
         String hex = Integer.toHexString(v | 65536).substring(1, 5);
         return "\\u" + hex;
      } else if (v > 65535) {
         System.err.println("Warning: character literal out of range for ActionScript target " + v);
         return "";
      } else {
         StringBuilder buf = new StringBuilder("\\u80");
         buf.append(Integer.toHexString(v >> 8 | 256).substring(1, 3));
         buf.append("\\x");
         buf.append(Integer.toHexString(v & 255 | 256).substring(1, 3));
         return buf.toString();
      }
   }

   public String getTarget64BitStringFromValue(long word) {
      StringBuffer buf = new StringBuffer(22);
      buf.append("0x");
      this.writeHexWithPadding(buf, Integer.toHexString((int)(word & 4294967295L)));
      buf.append(", 0x");
      this.writeHexWithPadding(buf, Integer.toHexString((int)(word >> 32)));
      return buf.toString();
   }

   private void writeHexWithPadding(StringBuffer buf, String digits) {
      digits = digits.toUpperCase();
      int padding = 8 - digits.length();

      for(int i = 1; i <= padding; ++i) {
         buf.append('0');
      }

      buf.append(digits);
   }

   protected ST chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, ST recognizerST, ST cyclicDFAST) {
      return recognizerST;
   }
}
