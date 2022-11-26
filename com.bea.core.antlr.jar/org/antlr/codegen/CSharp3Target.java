package org.antlr.codegen;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import org.antlr.Tool;
import org.antlr.tool.Grammar;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;

public class CSharp3Target extends Target {
   private static final HashSet _languageKeywords = new HashSet() {
      {
         this.add("abstract");
         this.add("event");
         this.add("new");
         this.add("struct");
         this.add("as");
         this.add("explicit");
         this.add("null");
         this.add("switch");
         this.add("base");
         this.add("extern");
         this.add("object");
         this.add("this");
         this.add("bool");
         this.add("false");
         this.add("operator");
         this.add("throw");
         this.add("break");
         this.add("finally");
         this.add("out");
         this.add("true");
         this.add("byte");
         this.add("fixed");
         this.add("override");
         this.add("try");
         this.add("case");
         this.add("float");
         this.add("params");
         this.add("typeof");
         this.add("catch");
         this.add("for");
         this.add("private");
         this.add("uint");
         this.add("char");
         this.add("foreach");
         this.add("protected");
         this.add("ulong");
         this.add("checked");
         this.add("goto");
         this.add("public");
         this.add("unchecked");
         this.add("class");
         this.add("if");
         this.add("readonly");
         this.add("unsafe");
         this.add("const");
         this.add("implicit");
         this.add("ref");
         this.add("ushort");
         this.add("continue");
         this.add("in");
         this.add("return");
         this.add("using");
         this.add("decimal");
         this.add("int");
         this.add("sbyte");
         this.add("virtual");
         this.add("default");
         this.add("interface");
         this.add("sealed");
         this.add("volatile");
         this.add("delegate");
         this.add("internal");
         this.add("short");
         this.add("void");
         this.add("do");
         this.add("is");
         this.add("sizeof");
         this.add("while");
         this.add("double");
         this.add("lock");
         this.add("stackalloc");
         this.add("else");
         this.add("long");
         this.add("static");
         this.add("enum");
         this.add("namespace");
         this.add("string");
      }
   };

   public boolean useBaseTemplatesForSynPredFragments() {
      return false;
   }

   public String encodeIntAsCharEscape(int v) {
      return "\\x" + Integer.toHexString(v).toUpperCase();
   }

   public String getTarget64BitStringFromValue(long word) {
      return "0x" + Long.toHexString(word).toUpperCase();
   }

   protected void genRecognizerFile(Tool tool, CodeGenerator generator, Grammar grammar, ST outputFileST) throws IOException {
      if (!grammar.getGrammarIsRoot()) {
         Grammar rootGrammar = grammar.composite.getRootGrammar();
         String actionScope = grammar.getDefaultActionScope(grammar.type);
         Map actions = (Map)rootGrammar.getActions().get(actionScope);
         Object rootNamespace = actions != null ? actions.get("namespace") : null;
         if (actions != null && rootNamespace != null) {
            Map actions = (Map)grammar.getActions().get(actionScope);
            if (actions == null) {
               actions = new HashMap();
               grammar.getActions().put(actionScope, actions);
            }

            ((Map)actions).put("namespace", rootNamespace);
         }
      }

      generator.getTemplates().registerRenderer(String.class, new StringRenderer(generator, this));
      super.genRecognizerFile(tool, generator, grammar, outputFileST);
   }

   public static class StringRenderer implements AttributeRenderer {
      private final CodeGenerator _generator;
      private final CSharp3Target _target;

      public StringRenderer(CodeGenerator generator, CSharp3Target target) {
         this._generator = generator;
         this._target = target;
      }

      public String toString(Object obj, String formatName, Locale locale) {
         String value = (String)obj;
         if (value != null && formatName != null) {
            if (formatName.equals("id")) {
               return CSharp3Target._languageKeywords.contains(value) ? "@" + value : value;
            } else if (formatName.equals("cap")) {
               return Character.toUpperCase(value.charAt(0)) + value.substring(1);
            } else if (formatName.equals("string")) {
               return this._target.getTargetStringLiteralFromString(value, true);
            } else {
               throw new IllegalArgumentException("Unsupported format name: '" + formatName + "'");
            }
         } else {
            return value;
         }
      }
   }
}
