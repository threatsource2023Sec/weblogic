package weblogic.diagnostics.instrumentation;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class HarvesterAttributeNormalizerParser extends LLkParser implements HarvesterAttributeNormalizerParserTokenTypes {
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "WS", "LPAREN", "RPAREN", "COMMA", "TYPE_NAME", "STAR_WILDCARD", "POSITIONAL_WILDCARD"};

   private static Set createValidStatsKeySet() {
      Set r = new HashSet();
      r.add("min");
      r.add("max");
      r.add("avg");
      r.add("count");
      r.add("sum");
      r.add("sum_of_squares");
      r.add("std_deviation");
      return r;
   }

   private static String normalizeKey(String a) {
      boolean regex = isRegex(a);
      int len = a.length();
      String r;
      if (!regex) {
         r = "(" + a + ")";
      } else {
         r = "{" + escapeRegexMetaChars(a) + "}";
      }

      return r;
   }

   private static boolean isRegex(String a) {
      if (a.equals("*")) {
         return false;
      } else {
         int len = a.length();
         if (len == 0) {
            return false;
         } else {
            boolean regex = a.indexOf("*") >= 0;
            regex = regex || a.indexOf("?") >= 0;
            regex = regex || a.indexOf("%") >= 0;
            return regex;
         }
      }
   }

   private static String escapeRegexMetaChars(String a) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < a.length(); ++i) {
         char c = a.charAt(i);
         switch (c) {
            case '$':
               sb.append("\\$");
               break;
            case '%':
            case '*':
               sb.append(".*?");
               break;
            case '.':
               sb.append("\\.");
               break;
            case '?':
               sb.append("[^,]*?");
               break;
            default:
               sb.append(c);
         }
      }

      return sb.toString();
   }

   private static void validateStatsKey(String a) {
      if (!HarvesterAttributeNormalizerParser.ValidStatsKeySetInitializer.VALID_STATS_KEYS.contains(a)) {
         throw new IllegalArgumentException("Invalid statistics key " + a);
      }
   }

   private static String getFullyQualifiedTypeName(String type) {
      int index = type.indexOf(".");
      if (index == -1) {
         try {
            return Class.forName("java.lang." + type).getName();
         } catch (Exception var3) {
            return type;
         }
      } else {
         return type;
      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.err.println("Invalid number of arguments");
         System.exit(1);
      }

      HarvesterAttributeNormalizerLexer l = new HarvesterAttributeNormalizerLexer(new StringReader(args[0]));
      HarvesterAttributeNormalizerParser p = new HarvesterAttributeNormalizerParser(l);
      String s = p.normalizeAttributeSpec();
      System.out.println("Normalized attribute spec: " + s);
   }

   protected HarvesterAttributeNormalizerParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public HarvesterAttributeNormalizerParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected HarvesterAttributeNormalizerParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public HarvesterAttributeNormalizerParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public HarvesterAttributeNormalizerParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final String normalizeAttributeSpec() throws RecognitionException, TokenStreamException {
      String r = null;
      String a = "";
      String b = "";
      String c = "";
      String d = "";
      a = this.classNameKey();
      b = this.methodNameKey();
      c = this.methodParamsKey();
      d = this.methodStatsKey();
      r = a + b + c + d;
      return r;
   }

   public final String classNameKey() throws RecognitionException, TokenStreamException {
      String r = null;
      Token t1 = null;
      Token t2 = null;
      this.match(5);
      String a;
      switch (this.LA(1)) {
         case 8:
            t1 = this.LT(1);
            this.match(8);
            a = t1.getText();
            break;
         case 9:
            t2 = this.LT(1);
            this.match(9);
            a = t2.getText();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.match(6);
      r = normalizeKey(a);
      return r;
   }

   public final String methodNameKey() throws RecognitionException, TokenStreamException {
      String r = null;
      Token t1 = null;
      Token t2 = null;
      this.match(5);
      String a;
      switch (this.LA(1)) {
         case 8:
            t1 = this.LT(1);
            this.match(8);
            a = t1.getText();
            break;
         case 9:
            t2 = this.LT(1);
            this.match(9);
            a = t2.getText();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.match(6);
      r = normalizeKey(a);
      return r;
   }

   public final String methodParamsKey() throws RecognitionException, TokenStreamException {
      String r = "";
      Token t1 = null;
      Token t2 = null;
      this.match(5);
      switch (this.LA(1)) {
         case 7:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 8:
         case 9:
         case 10:
            switch (this.LA(1)) {
               case 8:
                  t1 = this.LT(1);
                  this.match(8);
                  r = getFullyQualifiedTypeName(t1.getText());
                  break;
               case 9:
                  this.match(9);
                  r = "*";
                  break;
               case 10:
                  this.match(10);
                  r = "?";
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            while(this.LA(1) == 7) {
               this.match(7);
               switch (this.LA(1)) {
                  case 8:
                     t2 = this.LT(1);
                     this.match(8);
                     r = r + "," + getFullyQualifiedTypeName(t2.getText());
                     break;
                  case 9:
                     this.match(9);
                     r = r + ",*";
                     break;
                  case 10:
                     this.match(10);
                     r = r + ",?";
                     break;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            }
         case 6:
            this.match(6);
            if (!isRegex(r)) {
               r = r.replace(",", "\\,");
            }

            r = normalizeKey(r);
            return r;
      }
   }

   public final String methodStatsKey() throws RecognitionException, TokenStreamException {
      String r;
      r = "";
      Token t1 = null;
      Token t2 = null;
      Token t3 = null;
      this.match(5);
      label16:
      switch (this.LA(1)) {
         case 8:
            t1 = this.LT(1);
            this.match(8);
            r = t1.getText();
            validateStatsKey(r);

            while(true) {
               if (this.LA(1) != 7) {
                  break label16;
               }

               this.match(7);
               t2 = this.LT(1);
               this.match(8);
               String a = t2.getText();
               validateStatsKey(a);
               r = r + "," + a;
            }
         case 9:
            t3 = this.LT(1);
            this.match(9);
            r = t3.getText();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.match(6);
      r = normalizeKey(r);
      return r;
   }

   private static class ValidStatsKeySetInitializer {
      private static final Set VALID_STATS_KEYS = HarvesterAttributeNormalizerParser.createValidStatsKeySet();
   }
}
