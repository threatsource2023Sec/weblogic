package com.bea.httppubsub.json;

import org.apache.commons.lang.math.NumberUtils;

public class JSONTokener {
   private int myIndex = 0;
   private String mySource;

   public JSONTokener(String s) {
      this.mySource = s;
   }

   public void back() {
      if (this.myIndex > 0) {
         --this.myIndex;
      }

   }

   public static int dehexchar(char c) {
      if (c >= '0' && c <= '9') {
         return c - 48;
      } else if (c >= 'A' && c <= 'F') {
         return c - 55;
      } else {
         return c >= 'a' && c <= 'f' ? c - 87 : -1;
      }
   }

   public boolean more() {
      return this.myIndex < this.mySource.length();
   }

   public char next() {
      if (this.more()) {
         char c = this.mySource.charAt(this.myIndex);
         ++this.myIndex;
         return c;
      } else {
         return '\u0000';
      }
   }

   public char next(char c) throws JSONException {
      char n = this.next();
      if (n != c) {
         throw this.syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
      } else {
         return n;
      }
   }

   public String next(int n) throws JSONException {
      int i = this.myIndex;
      int j = i + n;
      if (j >= this.mySource.length()) {
         throw this.syntaxError("Substring bounds error");
      } else {
         this.myIndex += n;
         return this.mySource.substring(i, j);
      }
   }

   public char nextClean() throws JSONException {
      label51:
      while(true) {
         char c = this.next();
         if (c == '/') {
            switch (this.next()) {
               case '*':
                  while(true) {
                     c = this.next();
                     if (c == 0) {
                        throw this.syntaxError("Unclosed comment");
                     }

                     if (c == '*') {
                        if (this.next() == '/') {
                           continue label51;
                        }

                        this.back();
                     }
                  }
               case '/':
                  while(true) {
                     c = this.next();
                     if (c == '\n' || c == '\r' || c == 0) {
                        continue label51;
                     }
                  }
               default:
                  this.back();
                  return '/';
            }
         } else if (c == '#') {
            while(true) {
               c = this.next();
               if (c == '\n' || c == '\r' || c == 0) {
                  break;
               }
            }
         } else if (c == 0 || c > ' ') {
            return c;
         }
      }
   }

   public String nextString(char quote) throws JSONException {
      StringBuffer sb = new StringBuffer();

      while(true) {
         char c = this.next();
         switch (c) {
            case '\u0000':
            case '\n':
            case '\r':
               throw this.syntaxError("Unterminated string");
            case '\\':
               c = this.next();
               switch (c) {
                  case 'b':
                     sb.append('\b');
                     continue;
                  case 'c':
                  case 'd':
                  case 'e':
                  case 'g':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'l':
                  case 'm':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 's':
                  case 'v':
                  case 'w':
                  default:
                     sb.append(c);
                     continue;
                  case 'f':
                     sb.append('\f');
                     continue;
                  case 'n':
                     sb.append('\n');
                     continue;
                  case 'r':
                     sb.append('\r');
                     continue;
                  case 't':
                     sb.append('\t');
                     continue;
                  case 'u':
                     sb.append((char)Integer.parseInt(this.next((int)4), 16));
                     continue;
                  case 'x':
                     sb.append((char)Integer.parseInt(this.next((int)2), 16));
                     continue;
               }
            default:
               if (c == quote) {
                  return sb.toString();
               }

               sb.append(c);
         }
      }
   }

   public String nextTo(char d) {
      StringBuffer sb = new StringBuffer();

      while(true) {
         char c = this.next();
         if (c == d || c == 0 || c == '\n' || c == '\r') {
            if (c != 0) {
               this.back();
            }

            return sb.toString().trim();
         }

         sb.append(c);
      }
   }

   public String nextTo(String delimiters) {
      StringBuffer sb = new StringBuffer();

      while(true) {
         char c = this.next();
         if (delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r') {
            if (c != 0) {
               this.back();
            }

            return sb.toString().trim();
         }

         sb.append(c);
      }
   }

   public Object nextValue() throws JSONException {
      char c = this.nextClean();
      switch (c) {
         case '"':
         case '\'':
            return this.nextString(c);
         case '[':
            this.back();
            return new JSONArray(this);
         case '{':
            this.back();
            return new JSONObject(this);
         default:
            StringBuffer sb;
            for(sb = new StringBuffer(); c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0; c = this.next()) {
               sb.append(c);
            }

            this.back();
            String s = sb.toString().trim();
            if (s.equals("")) {
               throw this.syntaxError("Missing value");
            } else if (s.equalsIgnoreCase("true")) {
               return Boolean.TRUE;
            } else if (s.equalsIgnoreCase("false")) {
               return Boolean.FALSE;
            } else if (s.equalsIgnoreCase("null")) {
               return JSONObject.NULL;
            } else {
               return NumberUtils.isNumber(s) ? NumberUtils.createNumber(s) : s;
            }
      }
   }

   public char skipTo(char to) {
      int index = this.myIndex;

      char c;
      do {
         c = this.next();
         if (c == 0) {
            this.myIndex = index;
            return c;
         }
      } while(c != to);

      this.back();
      return c;
   }

   public boolean skipPast(String to) {
      this.myIndex = this.mySource.indexOf(to, this.myIndex);
      if (this.myIndex < 0) {
         this.myIndex = this.mySource.length();
         return false;
      } else {
         this.myIndex += to.length();
         return true;
      }
   }

   public JSONException syntaxError(String message) {
      return new JSONException(message + this.toString());
   }

   public String toString() {
      return " at character " + this.myIndex + " of " + this.mySource;
   }
}
