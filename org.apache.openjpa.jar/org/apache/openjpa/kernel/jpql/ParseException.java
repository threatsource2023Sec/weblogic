package org.apache.openjpa.kernel.jpql;

import java.util.TreeSet;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class ParseException extends UserException {
   private static final Localizer _loc = Localizer.forPackage(ParseException.class);

   public ParseException(Token currentTokenVal, int[][] expectedTokenSequencesVal, String[] tokenImageVal) {
      super(initMessage(currentTokenVal, expectedTokenSequencesVal, tokenImageVal));
   }

   public ParseException() {
   }

   public ParseException(String message) {
      super(message);
   }

   private static Localizer.Message initMessage(Token currentToken, int[][] expectedTokenSequences, String[] tokenImage) {
      TreeSet expected = new TreeSet();
      int maxSize = 0;

      for(int i = 0; i < expectedTokenSequences.length; ++i) {
         if (maxSize < expectedTokenSequences[i].length) {
            maxSize = expectedTokenSequences[i].length;
         }

         for(int j = 0; j < expectedTokenSequences[i].length; ++j) {
            expected.add(tokenImage[expectedTokenSequences[i][j]]);
         }
      }

      Token tok = currentToken.next;
      String curtok = "";

      for(int i = 0; i < maxSize; ++i) {
         if (i != 0) {
            curtok = curtok + " ";
         }

         if (tok.kind == 0) {
            curtok = curtok + tokenImage[0];
            break;
         }

         curtok = curtok + escape(tok.image);
         tok = tok.next;
      }

      return _loc.get("bad-parse", new Object[]{curtok, new Integer(currentToken.next.beginColumn), expected});
   }

   private static String escape(String str) {
      StringBuffer retval = new StringBuffer();

      for(int i = 0; i < str.length(); ++i) {
         switch (str.charAt(i)) {
            case '\u0000':
               break;
            case '\b':
               retval.append("\\b");
               break;
            case '\t':
               retval.append("\\t");
               break;
            case '\n':
               retval.append("\\n");
               break;
            case '\f':
               retval.append("\\f");
               break;
            case '\r':
               retval.append("\\r");
               break;
            case '"':
               retval.append("\\\"");
               break;
            case '\'':
               retval.append("\\'");
               break;
            case '\\':
               retval.append("\\\\");
               break;
            default:
               char ch;
               if ((ch = str.charAt(i)) >= ' ' && ch <= '~') {
                  retval.append(ch);
               } else {
                  String s = "0000" + Integer.toString(ch, 16);
                  retval.append("\\u" + s.substring(s.length() - 4, s.length()));
               }
         }
      }

      return retval.toString();
   }
}
