package weblogic.diagnostics.harvester.internal;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HarvesterDefaultAttributeNormalizerParser extends LLkParser implements HarvesterDefaultAttributeNormalizerParserTokenTypes {
   private static final String REGEX_METACHARS = ".#^$\\?+*|[]()";
   private static final String INVALID_IDENT_CHARS = ".#^$\\?+*|[]()%";
   private static final char WILD_CARD_CHAR = '%';
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "WS", "LSQPAREN", "RSQPAREN", "COMMA", "PERIOD", "SEMICOLON", "DIGIT", "LETTER", "INTEGER", "IDENTIFIER", "STAR_WILDCARD", "KEYSPEC"};

   private static void validateCharacters(String s) throws RecognitionException {
      int len = s.length();

      for(int i = 0; i < len; ++i) {
         char c = s.charAt(i);
         if (".#^$\\?+*|[]()%".indexOf(c) >= 0) {
            throw new RecognitionException("Unexpected character '" + c + "' in IDENTIFIER " + s);
         }
      }

   }

   private static String normalizeKey(String keySpec) {
      int size = keySpec.length();
      List keyList = new ArrayList();
      StringBuilder buf = new StringBuilder();
      boolean hasWildCard = false;
      boolean hasStar = false;

      for(int i = 0; i < size; ++i) {
         char ch = keySpec.charAt(i);
         if (ch == '\\') {
            ++i;
            if (i < size) {
               ch = keySpec.charAt(i);
               if (ch == '%') {
                  buf.append('%');
               }

               buf.append(ch);
            }
         } else if (ch == ',') {
            hasStar |= addKeySpec(keyList, buf);
            buf = new StringBuilder();
         } else {
            if (ch == '%') {
               hasWildCard = true;
            }

            buf.append(ch);
         }
      }

      hasStar |= addKeySpec(keyList, buf);
      if (hasStar) {
         return "(*)";
      } else {
         return normalizeKey(keyList, hasWildCard);
      }
   }

   private static boolean addKeySpec(List list, StringBuilder buf) {
      boolean hasStar = false;
      if (buf.length() > 0) {
         String tok = buf.toString().trim();
         list.add(tok);
         if ("*".equals(tok)) {
            hasStar = true;
         }
      }

      return hasStar;
   }

   private static String normalizeKey(List keyList, boolean hasWildCard) {
      int size = keyList.size();
      StringBuilder sb = new StringBuilder();
      int i;
      if (hasWildCard) {
         sb.append("{");

         for(i = 0; i < size; ++i) {
            if (i > 0) {
               sb.append("|");
            }

            String str = (String)keyList.get(i);
            escapeRegexMetaChars(str, sb);
         }

         sb.append("}");
      } else {
         sb.append("(");

         for(i = 0; i < size; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            unescapeWildcard((String)keyList.get(i), sb);
         }

         sb.append(")");
      }

      return sb.toString();
   }

   private static void unescapeWildcard(String s, StringBuilder sb) {
      int len = s.length();

      for(int i = 0; i < len; ++i) {
         char ch = s.charAt(i);
         if (ch == '%') {
            char nextChar = i < len - 1 ? s.charAt(i + 1) : 0;
            if (nextChar == '%') {
               ++i;
            }
         } else if (ch == ',') {
            sb.append('\\');
         }

         sb.append(ch);
      }

   }

   private static void escapeRegexMetaChars(String a, StringBuilder sb) {
      int len = a.length();
      boolean startsWithWildCard = len > 0 && a.charAt(0) == '%';
      boolean endsWithWildCard = len > 0 && a.charAt(len - 1) == '%';
      if (!startsWithWildCard) {
         sb.append("^");
      }

      for(int i = 0; i < len; ++i) {
         char c = a.charAt(i);
         if (c == '%') {
            char nextChar = i < len - 1 ? a.charAt(i + 1) : 0;
            if (nextChar == '%') {
               sb.append('%');
               ++i;
            } else {
               sb.append(".*?");
            }
         } else if (".#^$\\?+*|[]()".indexOf(c) >= 0) {
            sb.append("\\");
            sb.append(c);
         } else {
            sb.append(c);
         }
      }

      if (!endsWithWildCard) {
         sb.append("$");
      }

   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.err.println("Invalid number of arguments");
         System.exit(1);
      }

      System.out.println("Normalizing: " + args[0]);
      HarvesterDefaultAttributeNormalizerLexer l = new HarvesterDefaultAttributeNormalizerLexer(new StringReader(args[0]));
      HarvesterDefaultAttributeNormalizerParser p = new HarvesterDefaultAttributeNormalizerParser(l);
      String s = p.normalizeAttributeSpec();
      System.out.println("Normalized attribute spec: " + s);
   }

   protected HarvesterDefaultAttributeNormalizerParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public HarvesterDefaultAttributeNormalizerParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected HarvesterDefaultAttributeNormalizerParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public HarvesterDefaultAttributeNormalizerParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public HarvesterDefaultAttributeNormalizerParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final String normalizeAttributeSpec() throws RecognitionException, TokenStreamException {
      String r = null;
      String a = this.attributeNameSpec();
      String b = this.remainderSpec();
      r = a + b;
      return r;
   }

   public final String attributeNameSpec() throws RecognitionException, TokenStreamException {
      String r = null;
      Token id = null;
      id = this.LT(1);
      this.match(13);
      r = id.getText();
      validateCharacters(r);
      return r;
   }

   public final String remainderSpec() throws RecognitionException, TokenStreamException {
      Token ks = null;
      String a = null;
      String b = null;
      String c = null;
      String r = "";
      switch (this.LA(1)) {
         case 1:
            this.match(1);
            r = "";
            break;
         case 5:
            this.match(5);
            a = this.indexSpec();
            this.match(6);
            b = this.remainderSpec();
            r = "[" + a + "]" + b;
            break;
         case 8:
            this.match(8);
            r = this.normalizeAttributeSpec();
            r = "." + r;
            break;
         case 15:
            ks = this.LT(1);
            this.match(15);
            b = this.remainderSpec();
            r = normalizeKey(ks.getText()) + b;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return r;
   }

   public final String indexSpec() throws RecognitionException, TokenStreamException {
      Token id = null;
      String r;
      switch (this.LA(1)) {
         case 12:
            id = this.LT(1);
            this.match(12);
            r = id.getText();
            break;
         case 14:
            this.match(14);
            r = "*";
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return r;
   }
}
