package com.bea.httppubsub.util;

import java.text.ParseException;

public class JSONPCallbackFunctionNameValidator {
   public static final String VALID_CALLBACK_NAME_PATTERN = "functionName, obj.functionName, obj[\"function-name\"], and combination of those patterns";
   private String name;
   private int length;
   private int index = 0;
   private int lastIndex = 0;

   public JSONPCallbackFunctionNameValidator(String name) {
      this.name = name.trim();
      this.length = this.name.length();
   }

   public boolean validate() {
      if (this.name.isEmpty()) {
         return false;
      } else {
         try {
            this.parse();
            return true;
         } catch (ParseException var2) {
            return false;
         }
      }
   }

   private void parse() throws ParseException {
      this.parseIdentifer();

      while(this.index < this.length) {
         this.skipWhitespace();
         if (this.index == this.length) {
            return;
         }

         switch (this.name.charAt(this.index++)) {
            case '.':
               this.parseIdentifer();
               break;
            case '[':
               this.parseMemberExpression();
               break;
            default:
               throw new ParseException("incorrect format", this.index);
         }
      }

   }

   private void parseIdentifer() throws ParseException {
      this.skipWhitespace();
      int cp = this.getNextCodePoint();
      if (!isJsIdentifierStart(cp)) {
         throw new ParseException("incorrect format", this.index);
      } else {
         while(this.index < this.length) {
            cp = this.getNextCodePoint();
            if (!isJsIdentifierPart(cp)) {
               this.index = this.lastIndex;
               break;
            }
         }

      }
   }

   private void parseMemberExpression() throws ParseException {
      this.skipWhitespace();
      if (this.index == this.length) {
         throw new ParseException("incorrect format", this.index);
      } else {
         char ch = this.name.charAt(this.index);
         if (ch != '"' && ch != '\'') {
            throw new ParseException("incorrect format", this.index);
         } else {
            while(++this.index < this.length && (this.name.charAt(this.index) != ch || this.name.charAt(this.index - 1) == '\\')) {
            }

            if (this.index == this.length) {
               throw new ParseException("incorrect format", this.index);
            } else {
               ++this.index;
               this.skipWhitespace();
               if (this.index == this.length) {
                  throw new ParseException("incorrect format", this.index);
               } else if (this.name.charAt(this.index++) != ']') {
                  throw new ParseException("incorrect format", this.index);
               }
            }
         }
      }
   }

   private int getNextCodePoint() throws ParseException {
      this.lastIndex = this.index;
      int codePoint;
      if (this.name.charAt(this.index) == '\\') {
         if (this.length - this.index < 6 || this.name.charAt(this.index + 1) != 'u') {
            throw new ParseException("incorrect format", this.index);
         }

         try {
            codePoint = Integer.parseInt(this.name.substring(this.index + 2, this.index + 6), 16);
         } catch (NumberFormatException var3) {
            throw new ParseException("incorret format", this.index);
         }

         this.index += 6;
      } else {
         codePoint = this.name.codePointAt(this.index);
         this.index += Character.charCount(codePoint);
      }

      return codePoint;
   }

   private void skipWhitespace() {
      while(this.index < this.length && this.name.charAt(this.index) <= ' ') {
         ++this.index;
      }

   }

   public static boolean isJsIdentifier(String id) {
      if (id.length() == 0) {
         return false;
      } else {
         try {
            id = escapeUnicodeSequence(id);
         } catch (ParseException var3) {
            return false;
         }

         int cp = id.codePointAt(0);
         if (!isJsIdentifierStart(cp)) {
            return false;
         } else {
            for(int i = Character.charCount(cp); i < id.length(); i += Character.charCount(cp)) {
               cp = id.codePointAt(i);
               if (!isJsIdentifierPart(cp)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static boolean isJsIdentifierStart(int codePoint) {
      if (codePoint != 36 && codePoint != 95) {
         return Character.isLetter(codePoint) || Character.getType(codePoint) == 10;
      } else {
         return true;
      }
   }

   public static boolean isJsIdentifierPart(int codePoint) {
      if (isJsIdentifierStart(codePoint)) {
         return true;
      } else {
         int type = Character.getType(codePoint);
         if (type != 6 && type != 8) {
            if (type == 9) {
               return true;
            } else if (type == 23) {
               return true;
            } else {
               return codePoint == 8204 || codePoint == 8205;
            }
         } else {
            return true;
         }
      }
   }

   private static String escapeUnicodeSequence(String str) throws ParseException {
      StringBuilder sb = null;
      int i = 0;

      while(true) {
         while(i < str.length()) {
            char ch = str.charAt(i);
            if (ch == '\\' && str.charAt(i + 1) == 'u') {
               char[] chars = null;

               char[] chars;
               try {
                  chars = Character.toChars(Integer.parseInt(str.substring(i + 2, i + 6), 16));
               } catch (NumberFormatException var6) {
                  throw new ParseException("incorret format", i);
               }

               if (sb == null) {
                  sb = new StringBuilder();
                  sb.append(str.substring(0, i));
               }

               sb.append(chars);
               i += 6;
            } else {
               if (sb != null) {
                  sb.append(ch);
               }

               ++i;
            }
         }

         return sb == null ? str : sb.toString();
      }
   }
}
