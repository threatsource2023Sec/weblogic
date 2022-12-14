package weblogic.html;

/** @deprecated */
@Deprecated
public final class EntityEscape {
   public static String escapeString(String s) {
      if (s == null) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         int len = s.length();
         char[] chars = new char[len];
         s.getChars(0, len, chars, 0);
         escapeChars(chars, sb);
         return sb.toString();
      }
   }

   public static void escapeString(String s, StringBuffer sb) {
      if (s != null) {
         int len = s.length();
         char[] chars = new char[len];
         s.getChars(0, len, chars, 0);
         escapeChars(chars, sb);
      }
   }

   public static void escapeChars(char[] chars, StringBuffer sb) {
      for(int i = 0; i < chars.length; ++i) {
         String toAppend;
         switch (chars[i]) {
            case '"':
               toAppend = "&quot;";
               break;
            case '&':
               toAppend = "&amp;";
               break;
            case '<':
               toAppend = "&lt;";
               break;
            case '>':
               toAppend = "&gt;";
               break;
            default:
               switch (chars[i]) {
                  case '\'':
                     toAppend = "&#39;";
                     break;
                  case '(':
                  case ')':
                  case '*':
                  case '+':
                  case ',':
                  case '-':
                  case '.':
                  case '/':
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                  case '8':
                  case '9':
                  case ':':
                  case ';':
                  case '<':
                  case '=':
                  case '>':
                  case '?':
                  case '@':
                  case 'A':
                  case 'B':
                  case 'C':
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'G':
                  case 'H':
                  case 'I':
                  case 'J':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'S':
                  case 'T':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  case 'a':
                  case 'b':
                  case 'c':
                  case 'd':
                  case 'e':
                  case 'f':
                  case 'g':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'l':
                  case 'm':
                  case 'n':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 'r':
                  case 's':
                  case 't':
                  case 'u':
                  case 'v':
                  case 'w':
                  case 'x':
                  case 'y':
                  case 'z':
                  case '{':
                  case '|':
                  case '}':
                  case '~':
                  case '\u007f':
                  case '\u0080':
                  case '\u0081':
                  case '\u0082':
                  case '\u0083':
                  case '\u0084':
                  case '\u0085':
                  case '\u0086':
                  case '\u0087':
                  case '\u0088':
                  case '\u0089':
                  case '\u008a':
                  case '\u008b':
                  case '\u008c':
                  case '\u008d':
                  case '\u008e':
                  case '\u008f':
                  case '\u0090':
                  case '\u0091':
                  case '\u0092':
                  case '\u0093':
                  case '\u0094':
                  case '\u0095':
                  case '\u0096':
                  case '\u0097':
                  case '\u0098':
                  case '\u0099':
                  case '\u009a':
                  case '\u009b':
                  case '\u009c':
                  case '\u009d':
                  case '\u009e':
                  case '\u009f':
                  default:
                     toAppend = String.valueOf(chars[i]);
                     break;
                  case '??':
                     toAppend = "&nbsp;";
                     break;
                  case '??':
                     toAppend = "&iexcl;";
                     break;
                  case '??':
                     toAppend = "&cent;";
                     break;
                  case '??':
                     toAppend = "&pound;";
                     break;
                  case '??':
                     toAppend = "&curren;";
                     break;
                  case '??':
                     toAppend = "&yen;";
                     break;
                  case '??':
                     toAppend = "&brvbar;";
                     break;
                  case '??':
                     toAppend = "&sect;";
                     break;
                  case '??':
                     toAppend = "&uml;";
                     break;
                  case '??':
                     toAppend = "&copy;";
                     break;
                  case '??':
                     toAppend = "&ordf;";
                     break;
                  case '??':
                     toAppend = "&laquo;";
                     break;
                  case '??':
                     toAppend = "&not;";
                     break;
                  case '\u00ad':
                     toAppend = "&shy;";
                     break;
                  case '??':
                     toAppend = "&reg;";
                     break;
                  case '??':
                     toAppend = "&macr;";
                     break;
                  case '??':
                     toAppend = "&deg;";
                     break;
                  case '??':
                     toAppend = "&plusmn;";
                     break;
                  case '??':
                     toAppend = "&sup2;";
                     break;
                  case '??':
                     toAppend = "&sup3;";
                     break;
                  case '??':
                     toAppend = "&acute;";
                     break;
                  case '??':
                     toAppend = "&micro;";
                     break;
                  case '??':
                     toAppend = "&para;";
                     break;
                  case '??':
                     toAppend = "&middot;";
                     break;
                  case '??':
                     toAppend = "&cedil;";
                     break;
                  case '??':
                     toAppend = "&sup1;";
                     break;
                  case '??':
                     toAppend = "&ordm;";
                     break;
                  case '??':
                     toAppend = "&raquo;";
                     break;
                  case '??':
                     toAppend = "&frac14;";
                     break;
                  case '??':
                     toAppend = "&frac12;";
                     break;
                  case '??':
                     toAppend = "&frac34;";
                     break;
                  case '??':
                     toAppend = "&iquest;";
                     break;
                  case '??':
                     toAppend = "&Agrave;";
                     break;
                  case '??':
                     toAppend = "&Aacute;";
                     break;
                  case '??':
                     toAppend = "&Acirc;";
                     break;
                  case '??':
                     toAppend = "&Atilde;";
                     break;
                  case '??':
                     toAppend = "&Auml;";
                     break;
                  case '??':
                     toAppend = "&Aring;";
                     break;
                  case '??':
                     toAppend = "&AElig;";
                     break;
                  case '??':
                     toAppend = "&Ccedil;";
                     break;
                  case '??':
                     toAppend = "&Egrave;";
                     break;
                  case '??':
                     toAppend = "&Eacute;";
                     break;
                  case '??':
                     toAppend = "&Ecirc;";
                     break;
                  case '??':
                     toAppend = "&Euml;";
                     break;
                  case '??':
                     toAppend = "&Igrave;";
                     break;
                  case '??':
                     toAppend = "&Iacute;";
                     break;
                  case '??':
                     toAppend = "&Icirc;";
                     break;
                  case '??':
                     toAppend = "&Iuml;";
                     break;
                  case '??':
                     toAppend = "&ETH;";
                     break;
                  case '??':
                     toAppend = "&Ntilde;";
                     break;
                  case '??':
                     toAppend = "&Ograve;";
                     break;
                  case '??':
                     toAppend = "&Oacute;";
                     break;
                  case '??':
                     toAppend = "&Ocirc;";
                     break;
                  case '??':
                     toAppend = "&Otilde;";
                     break;
                  case '??':
                     toAppend = "&Ouml;";
                     break;
                  case '??':
                     toAppend = "&times;";
                     break;
                  case '??':
                     toAppend = "&Oslash;";
                     break;
                  case '??':
                     toAppend = "&Ugrave;";
                     break;
                  case '??':
                     toAppend = "&Uacute;";
                     break;
                  case '??':
                     toAppend = "&Ucirc;";
                     break;
                  case '??':
                     toAppend = "&Uuml;";
                     break;
                  case '??':
                     toAppend = "&Yacute;";
                     break;
                  case '??':
                     toAppend = "&THORN;";
                     break;
                  case '??':
                     toAppend = "&szlig;";
                     break;
                  case '??':
                     toAppend = "&agrave;";
                     break;
                  case '??':
                     toAppend = "&aacute;";
                     break;
                  case '??':
                     toAppend = "&acirc;";
                     break;
                  case '??':
                     toAppend = "&atilde;";
                     break;
                  case '??':
                     toAppend = "&auml;";
                     break;
                  case '??':
                     toAppend = "&aring;";
                     break;
                  case '??':
                     toAppend = "&aelig;";
                     break;
                  case '??':
                     toAppend = "&ccedil;";
                     break;
                  case '??':
                     toAppend = "&egrave;";
                     break;
                  case '??':
                     toAppend = "&eacute;";
                     break;
                  case '??':
                     toAppend = "&ecirc;";
                     break;
                  case '??':
                     toAppend = "&euml;";
                     break;
                  case '??':
                     toAppend = "&igrave;";
                     break;
                  case '??':
                     toAppend = "&iacute;";
                     break;
                  case '??':
                     toAppend = "&icirc;";
                     break;
                  case '??':
                     toAppend = "&iuml;";
                     break;
                  case '??':
                     toAppend = "&eth;";
                     break;
                  case '??':
                     toAppend = "&ntilde;";
                     break;
                  case '??':
                     toAppend = "&ograve;";
                     break;
                  case '??':
                     toAppend = "&oacute;";
                     break;
                  case '??':
                     toAppend = "&ocirc;";
                     break;
                  case '??':
                     toAppend = "&otilde;";
                     break;
                  case '??':
                     toAppend = "&ouml;";
                     break;
                  case '??':
                     toAppend = "&divide;";
                     break;
                  case '??':
                     toAppend = "&oslash;";
                     break;
                  case '??':
                     toAppend = "&ugrave;";
                     break;
                  case '??':
                     toAppend = "&uacute;";
                     break;
                  case '??':
                     toAppend = "&ucirc;";
                     break;
                  case '??':
                     toAppend = "&uuml;";
                     break;
                  case '??':
                     toAppend = "&yacute;";
                     break;
                  case '??':
                     toAppend = "&thorn;";
                     break;
                  case '??':
                     toAppend = "&yuml;";
               }
         }

         sb.append(toAppend);
      }

   }
}
