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
                  case ' ':
                     toAppend = "&nbsp;";
                     break;
                  case '¡':
                     toAppend = "&iexcl;";
                     break;
                  case '¢':
                     toAppend = "&cent;";
                     break;
                  case '£':
                     toAppend = "&pound;";
                     break;
                  case '¤':
                     toAppend = "&curren;";
                     break;
                  case '¥':
                     toAppend = "&yen;";
                     break;
                  case '¦':
                     toAppend = "&brvbar;";
                     break;
                  case '§':
                     toAppend = "&sect;";
                     break;
                  case '¨':
                     toAppend = "&uml;";
                     break;
                  case '©':
                     toAppend = "&copy;";
                     break;
                  case 'ª':
                     toAppend = "&ordf;";
                     break;
                  case '«':
                     toAppend = "&laquo;";
                     break;
                  case '¬':
                     toAppend = "&not;";
                     break;
                  case '\u00ad':
                     toAppend = "&shy;";
                     break;
                  case '®':
                     toAppend = "&reg;";
                     break;
                  case '¯':
                     toAppend = "&macr;";
                     break;
                  case '°':
                     toAppend = "&deg;";
                     break;
                  case '±':
                     toAppend = "&plusmn;";
                     break;
                  case '²':
                     toAppend = "&sup2;";
                     break;
                  case '³':
                     toAppend = "&sup3;";
                     break;
                  case '´':
                     toAppend = "&acute;";
                     break;
                  case 'µ':
                     toAppend = "&micro;";
                     break;
                  case '¶':
                     toAppend = "&para;";
                     break;
                  case '·':
                     toAppend = "&middot;";
                     break;
                  case '¸':
                     toAppend = "&cedil;";
                     break;
                  case '¹':
                     toAppend = "&sup1;";
                     break;
                  case 'º':
                     toAppend = "&ordm;";
                     break;
                  case '»':
                     toAppend = "&raquo;";
                     break;
                  case '¼':
                     toAppend = "&frac14;";
                     break;
                  case '½':
                     toAppend = "&frac12;";
                     break;
                  case '¾':
                     toAppend = "&frac34;";
                     break;
                  case '¿':
                     toAppend = "&iquest;";
                     break;
                  case 'À':
                     toAppend = "&Agrave;";
                     break;
                  case 'Á':
                     toAppend = "&Aacute;";
                     break;
                  case 'Â':
                     toAppend = "&Acirc;";
                     break;
                  case 'Ã':
                     toAppend = "&Atilde;";
                     break;
                  case 'Ä':
                     toAppend = "&Auml;";
                     break;
                  case 'Å':
                     toAppend = "&Aring;";
                     break;
                  case 'Æ':
                     toAppend = "&AElig;";
                     break;
                  case 'Ç':
                     toAppend = "&Ccedil;";
                     break;
                  case 'È':
                     toAppend = "&Egrave;";
                     break;
                  case 'É':
                     toAppend = "&Eacute;";
                     break;
                  case 'Ê':
                     toAppend = "&Ecirc;";
                     break;
                  case 'Ë':
                     toAppend = "&Euml;";
                     break;
                  case 'Ì':
                     toAppend = "&Igrave;";
                     break;
                  case 'Í':
                     toAppend = "&Iacute;";
                     break;
                  case 'Î':
                     toAppend = "&Icirc;";
                     break;
                  case 'Ï':
                     toAppend = "&Iuml;";
                     break;
                  case 'Ð':
                     toAppend = "&ETH;";
                     break;
                  case 'Ñ':
                     toAppend = "&Ntilde;";
                     break;
                  case 'Ò':
                     toAppend = "&Ograve;";
                     break;
                  case 'Ó':
                     toAppend = "&Oacute;";
                     break;
                  case 'Ô':
                     toAppend = "&Ocirc;";
                     break;
                  case 'Õ':
                     toAppend = "&Otilde;";
                     break;
                  case 'Ö':
                     toAppend = "&Ouml;";
                     break;
                  case '×':
                     toAppend = "&times;";
                     break;
                  case 'Ø':
                     toAppend = "&Oslash;";
                     break;
                  case 'Ù':
                     toAppend = "&Ugrave;";
                     break;
                  case 'Ú':
                     toAppend = "&Uacute;";
                     break;
                  case 'Û':
                     toAppend = "&Ucirc;";
                     break;
                  case 'Ü':
                     toAppend = "&Uuml;";
                     break;
                  case 'Ý':
                     toAppend = "&Yacute;";
                     break;
                  case 'Þ':
                     toAppend = "&THORN;";
                     break;
                  case 'ß':
                     toAppend = "&szlig;";
                     break;
                  case 'à':
                     toAppend = "&agrave;";
                     break;
                  case 'á':
                     toAppend = "&aacute;";
                     break;
                  case 'â':
                     toAppend = "&acirc;";
                     break;
                  case 'ã':
                     toAppend = "&atilde;";
                     break;
                  case 'ä':
                     toAppend = "&auml;";
                     break;
                  case 'å':
                     toAppend = "&aring;";
                     break;
                  case 'æ':
                     toAppend = "&aelig;";
                     break;
                  case 'ç':
                     toAppend = "&ccedil;";
                     break;
                  case 'è':
                     toAppend = "&egrave;";
                     break;
                  case 'é':
                     toAppend = "&eacute;";
                     break;
                  case 'ê':
                     toAppend = "&ecirc;";
                     break;
                  case 'ë':
                     toAppend = "&euml;";
                     break;
                  case 'ì':
                     toAppend = "&igrave;";
                     break;
                  case 'í':
                     toAppend = "&iacute;";
                     break;
                  case 'î':
                     toAppend = "&icirc;";
                     break;
                  case 'ï':
                     toAppend = "&iuml;";
                     break;
                  case 'ð':
                     toAppend = "&eth;";
                     break;
                  case 'ñ':
                     toAppend = "&ntilde;";
                     break;
                  case 'ò':
                     toAppend = "&ograve;";
                     break;
                  case 'ó':
                     toAppend = "&oacute;";
                     break;
                  case 'ô':
                     toAppend = "&ocirc;";
                     break;
                  case 'õ':
                     toAppend = "&otilde;";
                     break;
                  case 'ö':
                     toAppend = "&ouml;";
                     break;
                  case '÷':
                     toAppend = "&divide;";
                     break;
                  case 'ø':
                     toAppend = "&oslash;";
                     break;
                  case 'ù':
                     toAppend = "&ugrave;";
                     break;
                  case 'ú':
                     toAppend = "&uacute;";
                     break;
                  case 'û':
                     toAppend = "&ucirc;";
                     break;
                  case 'ü':
                     toAppend = "&uuml;";
                     break;
                  case 'ý':
                     toAppend = "&yacute;";
                     break;
                  case 'þ':
                     toAppend = "&thorn;";
                     break;
                  case 'ÿ':
                     toAppend = "&yuml;";
               }
         }

         sb.append(toAppend);
      }

   }
}
