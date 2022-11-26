package weblogic.entitlement.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextFilter {
   public static final char ALL_CHAR = '*';
   public static final char ESCAPE_CHAR = '\\';
   public static final Token ALL_STRINGS_TOKEN = new SpecialToken('*');
   private List mTokens = null;

   public TextFilter() {
      this.mTokens = new ArrayList();
   }

   public TextFilter(String stringFilter) {
      char[] filter = stringFilter.toCharArray();
      this.mTokens = new ArrayList(countTokens(filter));
      StringBuffer buf = new StringBuffer(filter.length);

      for(int i = 0; i < filter.length; ++i) {
         if (filter[i] == '*') {
            if (buf.length() > 0) {
               this.mTokens.add(new Token(buf.toString()));
            }

            this.mTokens.add(ALL_STRINGS_TOKEN);
            buf.setLength(0);
         } else if (filter[i] == '\\') {
            ++i;
            if (i < filter.length) {
               buf.append(filter[i]);
            }
         } else {
            buf.append(filter[i]);
         }
      }

      if (buf.length() > 0) {
         this.mTokens.add(new Token(buf.toString()));
      }

   }

   public int getTokenCount() {
      return this.mTokens.size();
   }

   public Token getToken(int index) {
      return (Token)this.mTokens.get(index);
   }

   public void add(Token token) {
      this.mTokens.add(token);
   }

   public void add(String text) {
      this.add(new Token(text));
   }

   public Iterator tokens() {
      return this.mTokens.iterator();
   }

   public Token remove(int index) {
      return (Token)this.mTokens.remove(index);
   }

   public String toString() {
      StringBuffer filter = new StringBuffer();
      int i = 0;

      for(int n = this.getTokenCount(); i < n; ++i) {
         filter.append(this.getToken(i));
      }

      return filter.toString();
   }

   public String toString(Escaping e, String all) {
      StringBuffer filter = new StringBuffer();
      int i = 0;

      for(int n = this.getTokenCount(); i < n; ++i) {
         Token t = this.getToken(i);
         if (t == ALL_STRINGS_TOKEN) {
            filter.append(all);
         } else {
            filter.append(e.escapeString(t.getText()));
         }
      }

      return filter.toString();
   }

   public boolean accept(String text) {
      return this.accept(text, 0, this.getTokenCount());
   }

   private boolean accept(String text, int start, int end) {
      for(int i = start; i < end; ++i) {
         Token token = this.getToken(i);
         if (token.isSpecial()) {
            if (token == ALL_STRINGS_TOKEN) {
               int nextToken = i + 1;
               int j = 0;

               for(int textLength = text.length(); j < textLength; ++j) {
                  if (this.accept(text.substring(j), nextToken, end)) {
                     return true;
                  }
               }

               return this.accept("", nextToken, end);
            }

            throw new RuntimeException("Unknown token");
         }

         String t = token.getText();
         if (!text.startsWith(t)) {
            return false;
         }

         text = text.substring(t.length());
      }

      return text.length() == 0;
   }

   private static int countTokens(char[] filter) {
      int count = 0;
      boolean inTextToken = false;

      for(int i = 0; i < filter.length; ++i) {
         if (filter[i] == '*') {
            ++count;
            inTextToken = false;
         } else {
            if (filter[i] == '\\') {
               ++i;
               if (filter.length >= i) {
                  break;
               }
            }

            if (!inTextToken) {
               ++count;
               inTextToken = true;
            }
         }
      }

      return count;
   }

   private static class SpecialToken extends Token {
      private SpecialToken(char c) {
         super(String.valueOf(c));
      }

      public boolean isSpecial() {
         return true;
      }

      public boolean equals(Object obj) {
         return this == obj;
      }

      public String toString() {
         return this.getText();
      }

      // $FF: synthetic method
      SpecialToken(char x0, Object x1) {
         this(x0);
      }
   }

   public static class Token {
      private final String text;

      public Token(String text) {
         if (text.length() == 0) {
            throw new IllegalArgumentException("no text");
         } else {
            this.text = text;
         }
      }

      public String getText() {
         return this.text;
      }

      public boolean isSpecial() {
         return false;
      }

      public int hashCode() {
         int retVal = this.isSpecial() ? 0 : -1;
         return retVal ^ this.text.hashCode();
      }

      public boolean equals(Object object) {
         if (!(object instanceof Token)) {
            return false;
         } else {
            Token token = (Token)object;
            return !token.isSpecial() && this.text.equals(token.getText());
         }
      }

      public String toString() {
         StringBuffer bfr = null;
         char[] textChars = this.text.toCharArray();

         for(int i = 0; i < textChars.length; ++i) {
            if (textChars[i] == '*' || textChars[i] == '\\') {
               if (bfr == null) {
                  bfr = new StringBuffer(textChars.length * 2 - i);
                  bfr.append(this.text.substring(0, i));
               }

               bfr.append('\\');
            }

            if (bfr != null) {
               bfr.append(textChars[i]);
            }
         }

         return bfr != null ? bfr.toString() : this.text;
      }
   }
}
