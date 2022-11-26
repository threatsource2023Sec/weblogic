package org.antlr.codegen;

import java.lang.Character.UnicodeBlock;
import org.antlr.tool.AttributeScope;
import org.antlr.tool.Grammar;
import org.antlr.tool.RuleLabelScope;

public class Perl5Target extends Target {
   public Perl5Target() {
      this.targetCharValueEscape[36] = "\\$";
      this.targetCharValueEscape[64] = "\\@";
      this.targetCharValueEscape[37] = "\\%";
      AttributeScope.tokenScope.addAttribute("self", (String)null);
      RuleLabelScope.predefinedLexerRulePropertiesScope.addAttribute("self", (String)null);
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      StringBuffer buf = new StringBuffer(10);
      int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
      if (c < 0) {
         buf.append("\\x{0000}");
      } else if (c < this.targetCharValueEscape.length && this.targetCharValueEscape[c] != null) {
         buf.append(this.targetCharValueEscape[c]);
      } else if (UnicodeBlock.of((char)c) == UnicodeBlock.BASIC_LATIN && !Character.isISOControl((char)c)) {
         buf.append((char)c);
      } else {
         String hex = Integer.toHexString(c | 65536).toUpperCase().substring(1, 5);
         buf.append("\\x{");
         buf.append(hex);
         buf.append("}");
      }

      if (buf.indexOf("\\") == -1) {
         buf.insert(0, '\'');
         buf.append('\'');
      } else {
         buf.insert(0, '"');
         buf.append('"');
      }

      return buf.toString();
   }

   public String encodeIntAsCharEscape(int v) {
      int intValue;
      if ((v & '耀') == 0) {
         intValue = v;
      } else {
         intValue = -(65536 - v);
      }

      return String.valueOf(intValue);
   }
}
