package org.apache.xmlbeans.impl.regex;

import java.text.CharacterIterator;

public final class REUtil {
   static final int CACHESIZE = 20;
   static final RegularExpression[] regexCache = new RegularExpression[20];

   private REUtil() {
   }

   static final int composeFromSurrogates(int high, int low) {
      return 65536 + (high - '\ud800' << 10) + low - '\udc00';
   }

   static final boolean isLowSurrogate(int ch) {
      return (ch & 'ﰀ') == 56320;
   }

   static final boolean isHighSurrogate(int ch) {
      return (ch & 'ﰀ') == 55296;
   }

   static final String decomposeToSurrogates(int ch) {
      char[] chs = new char[2];
      ch -= 65536;
      chs[0] = (char)((ch >> 10) + '\ud800');
      chs[1] = (char)((ch & 1023) + '\udc00');
      return new String(chs);
   }

   static final String substring(CharacterIterator iterator, int begin, int end) {
      char[] src = new char[end - begin];

      for(int i = 0; i < src.length; ++i) {
         src[i] = iterator.setIndex(i + begin);
      }

      return new String(src);
   }

   static final int getOptionValue(int ch) {
      int ret = 0;
      switch (ch) {
         case 44:
            ret = 1024;
            break;
         case 70:
            ret = 256;
            break;
         case 72:
            ret = 128;
            break;
         case 88:
            ret = 512;
            break;
         case 105:
            ret = 2;
            break;
         case 109:
            ret = 8;
            break;
         case 115:
            ret = 4;
            break;
         case 117:
            ret = 32;
            break;
         case 119:
            ret = 64;
            break;
         case 120:
            ret = 16;
      }

      return ret;
   }

   static final int parseOptions(String opts) throws ParseException {
      if (opts == null) {
         return 0;
      } else {
         int options = 0;

         for(int i = 0; i < opts.length(); ++i) {
            int v = getOptionValue(opts.charAt(i));
            if (v == 0) {
               throw new ParseException("Unknown Option: " + opts.substring(i), -1);
            }

            options |= v;
         }

         return options;
      }
   }

   static final String createOptionString(int options) {
      StringBuffer sb = new StringBuffer(9);
      if ((options & 256) != 0) {
         sb.append('F');
      }

      if ((options & 128) != 0) {
         sb.append('H');
      }

      if ((options & 512) != 0) {
         sb.append('X');
      }

      if ((options & 2) != 0) {
         sb.append('i');
      }

      if ((options & 8) != 0) {
         sb.append('m');
      }

      if ((options & 4) != 0) {
         sb.append('s');
      }

      if ((options & 32) != 0) {
         sb.append('u');
      }

      if ((options & 64) != 0) {
         sb.append('w');
      }

      if ((options & 16) != 0) {
         sb.append('x');
      }

      if ((options & 1024) != 0) {
         sb.append(',');
      }

      return sb.toString().intern();
   }

   static String stripExtendedComment(String regex) {
      int len = regex.length();
      StringBuffer buffer = new StringBuffer(len);
      int offset = 0;

      while(true) {
         while(true) {
            char ch;
            do {
               do {
                  do {
                     do {
                        do {
                           if (offset >= len) {
                              return buffer.toString();
                           }

                           ch = regex.charAt(offset++);
                        } while(ch == '\t');
                     } while(ch == '\n');
                  } while(ch == '\f');
               } while(ch == '\r');
            } while(ch == ' ');

            if (ch == '#') {
               while(offset < len) {
                  ch = regex.charAt(offset++);
                  if (ch == '\r' || ch == '\n') {
                     break;
                  }
               }
            } else if (ch == '\\' && offset < len) {
               char next;
               if ((next = regex.charAt(offset)) != '#' && next != '\t' && next != '\n' && next != '\f' && next != '\r' && next != ' ') {
                  buffer.append('\\');
                  buffer.append((char)next);
                  ++offset;
               } else {
                  buffer.append((char)next);
                  ++offset;
               }
            } else {
               buffer.append((char)ch);
            }
         }
      }
   }

   public static void main(String[] argv) {
      String pattern = null;

      String target;
      int i;
      try {
         String options = "";
         target = null;
         if (argv.length == 0) {
            System.out.println("Error:Usage: java REUtil -i|-m|-s|-u|-w|-X regularExpression String");
            System.exit(0);
         }

         for(i = 0; i < argv.length; ++i) {
            if (argv[i].length() != 0 && argv[i].charAt(0) == '-') {
               if (argv[i].equals("-i")) {
                  options = options + "i";
               } else if (argv[i].equals("-m")) {
                  options = options + "m";
               } else if (argv[i].equals("-s")) {
                  options = options + "s";
               } else if (argv[i].equals("-u")) {
                  options = options + "u";
               } else if (argv[i].equals("-w")) {
                  options = options + "w";
               } else if (argv[i].equals("-X")) {
                  options = options + "X";
               } else {
                  System.err.println("Unknown option: " + argv[i]);
               }
            } else if (pattern == null) {
               pattern = argv[i];
            } else if (target == null) {
               target = argv[i];
            } else {
               System.err.println("Unnecessary: " + argv[i]);
            }
         }

         RegularExpression reg = new RegularExpression(pattern, options);
         System.out.println("RegularExpression: " + reg);
         Match match = new Match();
         reg.matches(target, match);

         for(int i = 0; i < match.getNumberOfGroups(); ++i) {
            if (i == 0) {
               System.out.print("Matched range for the whole pattern: ");
            } else {
               System.out.print("[" + i + "]: ");
            }

            if (match.getBeginning(i) < 0) {
               System.out.println("-1");
            } else {
               System.out.print(match.getBeginning(i) + ", " + match.getEnd(i) + ", ");
               System.out.println("\"" + match.getCapturedText(i) + "\"");
            }
         }
      } catch (ParseException var7) {
         if (pattern == null) {
            var7.printStackTrace();
         } else {
            System.err.println("org.apache.xerces.utils.regex.ParseException: " + var7.getMessage());
            target = "        ";
            System.err.println(target + pattern);
            i = var7.getLocation();
            if (i >= 0) {
               System.err.print(target);

               for(int i = 0; i < i; ++i) {
                  System.err.print("-");
               }

               System.err.println("^");
            }
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public static RegularExpression createRegex(String pattern, String options) throws ParseException {
      RegularExpression re = null;
      int intOptions = parseOptions(options);
      synchronized(regexCache) {
         int i;
         for(i = 0; i < 20; ++i) {
            RegularExpression cached = regexCache[i];
            if (cached == null) {
               i = -1;
               break;
            }

            if (cached.equals(pattern, intOptions)) {
               re = cached;
               break;
            }
         }

         if (re != null) {
            if (i != 0) {
               System.arraycopy(regexCache, 0, regexCache, 1, i);
               regexCache[0] = re;
            }
         } else {
            re = new RegularExpression(pattern, options);
            System.arraycopy(regexCache, 0, regexCache, 1, 19);
            regexCache[0] = re;
         }

         return re;
      }
   }

   public static boolean matches(String regex, String target) throws ParseException {
      return createRegex(regex, (String)null).matches(target);
   }

   public static boolean matches(String regex, String options, String target) throws ParseException {
      return createRegex(regex, options).matches(target);
   }

   public static String quoteMeta(String literal) {
      int len = literal.length();
      StringBuffer buffer = null;

      for(int i = 0; i < len; ++i) {
         int ch = literal.charAt(i);
         if (".*+?{[()|\\^$".indexOf(ch) >= 0) {
            if (buffer == null) {
               buffer = new StringBuffer(i + (len - i) * 2);
               if (i > 0) {
                  buffer.append(literal.substring(0, i));
               }
            }

            buffer.append('\\');
            buffer.append((char)ch);
         } else if (buffer != null) {
            buffer.append((char)ch);
         }
      }

      return buffer != null ? buffer.toString() : literal;
   }

   static void dumpString(String v) {
      for(int i = 0; i < v.length(); ++i) {
         System.out.print(Integer.toHexString(v.charAt(i)));
         System.out.print(" ");
      }

      System.out.println();
   }
}
