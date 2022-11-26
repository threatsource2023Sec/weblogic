package org.antlr.codegen;

import java.io.IOException;
import org.antlr.Tool;
import org.antlr.misc.Utils;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.ST;

public class ObjCTarget extends Target {
   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, ST headerFileST, String extName) throws IOException {
      generator.write(headerFileST, grammar.name + Grammar.grammarTypeToFileNameSuffix[grammar.type] + extName);
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      if (literal.startsWith("'\\u")) {
         literal = "0x" + literal.substring(3, 7);
      } else {
         int c = literal.charAt(1);
         if (c < ' ' || c > 127) {
            literal = "0x" + Integer.toHexString(c);
         }
      }

      return literal;
   }

   public String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator generator, String literal) {
      literal = Utils.replace(literal, "\"", "\\\"");
      StringBuilder buf = new StringBuilder(literal);
      buf.setCharAt(0, '"');
      buf.setCharAt(literal.length() - 1, '"');
      buf.insert(0, '@');
      return buf.toString();
   }

   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype) {
      String name = generator.grammar.getTokenDisplayName(ttype);
      return name.charAt(0) == '\'' ? String.valueOf(ttype) : name;
   }

   public String getTokenTextAndTypeAsTargetLabel(CodeGenerator generator, String text, int tokenType) {
      String name = generator.grammar.getTokenDisplayName(tokenType);
      if (name.charAt(0) == '\'') {
         return String.valueOf(tokenType);
      } else {
         String textEquivalent = text == null ? name : text;
         return textEquivalent.charAt(0) >= '0' && textEquivalent.charAt(0) <= '9' ? textEquivalent : generator.grammar.name + Grammar.grammarTypeToFileNameSuffix[generator.grammar.type] + "_" + textEquivalent;
      }
   }
}
