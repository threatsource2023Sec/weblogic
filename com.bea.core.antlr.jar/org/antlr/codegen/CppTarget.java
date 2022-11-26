package org.antlr.codegen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.Tool;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.Aggregate;

public class CppTarget extends Target {
   ArrayList strings = new ArrayList();

   public boolean useBaseTemplatesForSynPredFragments() {
      return false;
   }

   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, ST outputFileST) throws IOException {
      outputFileST.add("literals", this.strings);
      String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
      generator.write(outputFileST, fileName);
   }

   protected void genRecognizerHeaderFile(Tool tool, CodeGenerator generator, Grammar grammar, ST headerFileST, String extName) throws IOException {
      List tokens = (List)headerFileST.getAttribute("tokens");

      for(int i = 0; i < tokens.size(); ++i) {
         boolean can_break = false;
         Object tok = tokens.get(i);
         if (tok instanceof Aggregate) {
            Aggregate atok = (Aggregate)tok;
            Iterator i$ = atok.properties.entrySet().iterator();

            while(i$.hasNext()) {
               Map.Entry pairs = (Map.Entry)i$.next();
               if (pairs.getValue().equals("EOF")) {
                  tokens.remove(i);
                  can_break = true;
                  break;
               }
            }
         }

         if (can_break) {
            break;
         }
      }

      String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
      fileName = fileName.substring(0, fileName.length() - 4) + extName;
      generator.write(headerFileST, fileName);
   }

   protected ST chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, ST recognizerST, ST cyclicDFAST) {
      return recognizerST;
   }

   public boolean isValidActionScope(int grammarType, String scope) {
      switch (grammarType) {
         case 1:
            if (scope.equals("lexer")) {
               return true;
            }

            if (scope.equals("header")) {
               return true;
            }

            if (scope.equals("includes")) {
               return true;
            }

            if (scope.equals("preincludes")) {
               return true;
            }

            if (scope.equals("overrides")) {
               return true;
            }

            if (scope.equals("namespace")) {
               return true;
            }
            break;
         case 2:
            if (scope.equals("parser")) {
               return true;
            }

            if (scope.equals("header")) {
               return true;
            }

            if (scope.equals("includes")) {
               return true;
            }

            if (scope.equals("preincludes")) {
               return true;
            }

            if (scope.equals("overrides")) {
               return true;
            }

            if (scope.equals("namespace")) {
               return true;
            }
            break;
         case 3:
            if (scope.equals("treeparser")) {
               return true;
            }

            if (scope.equals("header")) {
               return true;
            }

            if (scope.equals("includes")) {
               return true;
            }

            if (scope.equals("preincludes")) {
               return true;
            }

            if (scope.equals("overrides")) {
               return true;
            }

            if (scope.equals("namespace")) {
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

            if (scope.equals("header")) {
               return true;
            }

            if (scope.equals("includes")) {
               return true;
            }

            if (scope.equals("preincludes")) {
               return true;
            }

            if (scope.equals("overrides")) {
               return true;
            }

            if (scope.equals("namespace")) {
               return true;
            }
      }

      return false;
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
      StringBuffer buf = new StringBuffer();
      buf.append("{ ");

      for(int i = 1; i < literal.length() - 1; ++i) {
         buf.append("0x");
         if (literal.charAt(i) == '\\') {
            ++i;
            switch (literal.charAt(i)) {
               case 'B':
               case 'b':
                  buf.append("08");
                  break;
               case 'F':
               case 'f':
                  buf.append("0C");
                  break;
               case 'N':
               case 'n':
                  buf.append("0A");
                  break;
               case 'R':
               case 'r':
                  buf.append("0D");
                  break;
               case 'T':
               case 't':
                  buf.append("09");
                  break;
               case 'U':
               case 'u':
                  buf.append(literal.substring(i + 1, i + 5));
                  i += 5;
                  break;
               default:
                  buf.append(Integer.toHexString(literal.charAt(i)).toUpperCase());
            }
         } else {
            buf.append(Integer.toHexString(literal.charAt(i)).toUpperCase());
         }

         buf.append(", ");
      }

      buf.append(" antlr3::ANTLR_STRING_TERMINATOR}");
      String bytes = buf.toString();
      int index = this.strings.indexOf(bytes);
      if (index == -1) {
         this.strings.add(bytes);
         index = this.strings.indexOf(bytes);
      }

      String strref = "lit_" + String.valueOf(index + 1);
      return strref;
   }

   protected void performGrammarAnalysis(CodeGenerator generator, Grammar grammar) {
      if (CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE == 60) {
         CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE = 65535;
      }

      if (CodeGenerator.MAX_SWITCH_CASE_LABELS == 300) {
         CodeGenerator.MAX_SWITCH_CASE_LABELS = 3000;
      }

      if (CodeGenerator.MIN_SWITCH_ALTS == 3) {
         CodeGenerator.MIN_SWITCH_ALTS = 1;
      }

      super.performGrammarAnalysis(generator, grammar);
   }
}
