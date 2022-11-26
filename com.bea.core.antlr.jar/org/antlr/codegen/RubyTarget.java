package org.antlr.codegen;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.antlr.Tool;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class RubyTarget extends Target {
   public static final Set rubyKeywords = new HashSet() {
      {
         this.add("alias");
         this.add("END");
         this.add("retry");
         this.add("and");
         this.add("ensure");
         this.add("return");
         this.add("BEGIN");
         this.add("false");
         this.add("self");
         this.add("begin");
         this.add("for");
         this.add("super");
         this.add("break");
         this.add("if");
         this.add("then");
         this.add("case");
         this.add("in");
         this.add("true");
         this.add("class");
         this.add("module");
         this.add("undef");
         this.add("def");
         this.add("next");
         this.add("unless");
         this.add("defined?");
         this.add("nil");
         this.add("until");
         this.add("do");
         this.add("not");
         this.add("when");
         this.add("else");
         this.add("or");
         this.add("while");
         this.add("elsif");
         this.add("redo");
         this.add("yield");
         this.add("end");
         this.add("rescue");
      }
   };
   public static Map sharedActionBlocks = new HashMap();

   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, ST outputFileST) throws IOException {
      Map actions;
      if (grammar.type == 4) {
         actions = grammar.getActions();
         if (actions.containsKey("all")) {
            sharedActionBlocks.put(grammar.name, actions.get("all"));
         }
      } else if (grammar.implicitLexer && sharedActionBlocks.containsKey(grammar.name)) {
         actions = grammar.getActions();
         actions.put("all", sharedActionBlocks.get(grammar.name));
      }

      STGroup group = generator.getTemplates();
      RubyRenderer renderer = new RubyRenderer();

      try {
         group.registerRenderer(Class.forName("java.lang.String"), renderer);
      } catch (ClassNotFoundException var8) {
         System.err.println("ClassNotFoundException: " + var8.getMessage());
         var8.printStackTrace(System.err);
      }

      String fileName = generator.getRecognizerFileName(grammar.name, grammar.type);
      generator.write(outputFileST, fileName);
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      int code_point = 0;
      literal = literal.substring(1, literal.length() - 1);
      if (literal.charAt(0) == '\\') {
         switch (literal.charAt(1)) {
            case '"':
            case '\'':
            case '\\':
               code_point = literal.codePointAt(1);
               break;
            case 'b':
               code_point = 8;
               break;
            case 'f':
               code_point = 12;
               break;
            case 'n':
               code_point = 10;
               break;
            case 'r':
               code_point = 13;
               break;
            case 't':
               code_point = 9;
               break;
            case 'u':
               code_point = Integer.parseInt(literal.substring(2), 16);
               break;
            default:
               System.out.println("1: hey you didn't account for this: \"" + literal + "\"");
         }
      } else if (literal.length() == 1) {
         code_point = literal.codePointAt(0);
      } else {
         System.out.println("2: hey you didn't account for this: \"" + literal + "\"");
      }

      return "0x" + Integer.toHexString(code_point);
   }

   public int getMaxCharValue(CodeGenerator generator) {
      return 255;
   }

   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype) {
      String name = generator.grammar.getTokenDisplayName(ttype);
      return name.charAt(0) == '\'' ? generator.grammar.computeTokenNameFromLiteral(ttype, name) : name;
   }

   public boolean isValidActionScope(int grammarType, String scope) {
      if (scope.equals("all")) {
         return true;
      } else if (scope.equals("token")) {
         return true;
      } else if (scope.equals("module")) {
         return true;
      } else if (scope.equals("overrides")) {
         return true;
      } else {
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
   }

   public String encodeIntAsCharEscape(int v) {
      int intValue;
      if (v == 65535) {
         intValue = -1;
      } else {
         intValue = v;
      }

      return String.valueOf(intValue);
   }

   public class RubyRenderer implements AttributeRenderer {
      protected String[] rubyCharValueEscape = new String[256];

      public RubyRenderer() {
         int ix;
         for(ix = 0; ix < 16; ++ix) {
            this.rubyCharValueEscape[ix] = "\\x0" + Integer.toHexString(ix);
         }

         for(ix = 16; ix < 32; ++ix) {
            this.rubyCharValueEscape[ix] = "\\x" + Integer.toHexString(ix);
         }

         for(char i = ' '; i < 127; ++i) {
            this.rubyCharValueEscape[i] = Character.toString(i);
         }

         for(ix = 127; ix < 256; ++ix) {
            this.rubyCharValueEscape[ix] = "\\x" + Integer.toHexString(ix);
         }

         this.rubyCharValueEscape[10] = "\\n";
         this.rubyCharValueEscape[13] = "\\r";
         this.rubyCharValueEscape[9] = "\\t";
         this.rubyCharValueEscape[8] = "\\b";
         this.rubyCharValueEscape[12] = "\\f";
         this.rubyCharValueEscape[92] = "\\\\";
         this.rubyCharValueEscape[34] = "\\\"";
      }

      public String toString(Object o, String formatName, Locale locale) {
         if (formatName == null) {
            return o.toString();
         } else {
            String idString = o.toString();
            if (idString.length() == 0) {
               return idString;
            } else if (formatName.equals("snakecase")) {
               return this.snakecase(idString);
            } else if (formatName.equals("camelcase")) {
               return this.camelcase(idString);
            } else if (formatName.equals("subcamelcase")) {
               return this.subcamelcase(idString);
            } else if (formatName.equals("constant")) {
               return this.constantcase(idString);
            } else if (formatName.equals("platform")) {
               return this.platform(idString);
            } else if (formatName.equals("lexerRule")) {
               return this.lexerRule(idString);
            } else if (formatName.equals("constantPath")) {
               return this.constantPath(idString);
            } else if (formatName.equals("rubyString")) {
               return this.rubyString(idString);
            } else if (formatName.equals("label")) {
               return this.label(idString);
            } else if (formatName.equals("symbol")) {
               return this.symbol(idString);
            } else {
               throw new IllegalArgumentException("Unsupported format name");
            }
         }
      }

      private String snakecase(String value) {
         StringBuilder output_buffer = new StringBuilder();
         int l = value.length();
         int cliff = l - 1;
         if (value.length() == 0) {
            return value;
         } else if (l == 1) {
            return value.toLowerCase();
         } else {
            char cur;
            for(int i = 0; i < cliff; ++i) {
               cur = value.charAt(i);
               char next = value.charAt(i + 1);
               if (Character.isLetter(cur)) {
                  output_buffer.append(Character.toLowerCase(cur));
                  if (!Character.isDigit(next) && !Character.isWhitespace(next)) {
                     if (Character.isLowerCase(cur) && Character.isUpperCase(next)) {
                        output_buffer.append('_');
                     } else if (i < cliff - 1 && Character.isUpperCase(cur) && Character.isUpperCase(next)) {
                        char peek = value.charAt(i + 2);
                        if (Character.isLowerCase(peek)) {
                           output_buffer.append('_');
                        }
                     }
                  } else {
                     output_buffer.append('_');
                  }
               } else if (Character.isDigit(cur)) {
                  output_buffer.append(cur);
                  if (Character.isLetter(next)) {
                     output_buffer.append('_');
                  }
               } else if (!Character.isWhitespace(cur)) {
                  output_buffer.append(cur);
               }
            }

            cur = value.charAt(cliff);
            if (!Character.isWhitespace(cur)) {
               output_buffer.append(Character.toLowerCase(cur));
            }

            return output_buffer.toString();
         }
      }

      private String constantcase(String value) {
         return this.snakecase(value).toUpperCase();
      }

      private String platform(String value) {
         return "__" + value + "__";
      }

      private String symbol(String value) {
         return value.matches("[a-zA-Z_]\\w*[\\?\\!\\=]?") ? ":" + value : "%s(" + value + ")";
      }

      private String lexerRule(String value) {
         return value.equals("Tokens") ? "token!" : this.snakecase(value) + "!";
      }

      private String constantPath(String value) {
         return value.replaceAll("\\.", "::");
      }

      private String rubyString(String value) {
         StringBuilder output_buffer = new StringBuilder();
         int len = value.length();
         output_buffer.append('"');

         for(int i = 0; i < len; ++i) {
            output_buffer.append(this.rubyCharValueEscape[value.charAt(i)]);
         }

         output_buffer.append('"');
         return output_buffer.toString();
      }

      private String camelcase(String value) {
         StringBuilder output_buffer = new StringBuilder();
         int cliff = value.length();
         boolean at_edge = true;
         if (value.length() == 0) {
            return value;
         } else if (cliff == 1) {
            return value.toUpperCase();
         } else {
            for(int i = 0; i < cliff; ++i) {
               char cur = value.charAt(i);
               if (Character.isWhitespace(cur)) {
                  at_edge = true;
               } else if (cur == '_') {
                  at_edge = true;
               } else if (Character.isDigit(cur)) {
                  output_buffer.append(cur);
                  at_edge = true;
               } else if (at_edge) {
                  output_buffer.append(Character.toUpperCase(cur));
                  if (Character.isLetter(cur)) {
                     at_edge = false;
                  }
               } else {
                  output_buffer.append(cur);
               }
            }

            return output_buffer.toString();
         }
      }

      private String label(String value) {
         if (RubyTarget.rubyKeywords.contains(value)) {
            return this.platform(value);
         } else if (Character.isUpperCase(value.charAt(0)) && !value.equals("FILE") && !value.equals("LINE")) {
            return this.platform(value);
         } else if (value.equals("FILE")) {
            return "_FILE_";
         } else {
            return value.equals("LINE") ? "_LINE_" : value;
         }
      }

      private String subcamelcase(String value) {
         value = this.camelcase(value);
         if (value.length() == 0) {
            return value;
         } else {
            Character head = Character.toLowerCase(value.charAt(0));
            String tail = value.substring(1);
            return head.toString().concat(tail);
         }
      }
   }
}
