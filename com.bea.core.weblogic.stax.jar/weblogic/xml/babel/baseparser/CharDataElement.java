package weblogic.xml.babel.baseparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weblogic.xml.babel.reader.XmlChars;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;

public class CharDataElement extends Element {
   protected int length;
   private final List arrays = new ArrayList();
   private static final char[] blankArray = new char[0];

   CharDataElement() {
      this.init();
   }

   protected void init() {
      super.init();
      this.length = 0;
      this.arrays.clear();
   }

   final void addArray(char[] newbuf) {
      this.arrays.add(newbuf);
      this.length += newbuf.length;
   }

   void addToTextArray(char c) {
      this.addArray(new char[c]);
   }

   void addToTextArray(String s) {
      this.addArray(s.toCharArray());
   }

   void addToTextArray(Token token) {
      if (token.length != 0) {
         char[] newbuf = new char[token.length];
         System.arraycopy(token.textArray, token.start, newbuf, 0, token.length);
         this.addArray(newbuf);
      }
   }

   void addToTextArrayNonAlloc(Token token) {
      if (token.length != 0) {
         this.addArray(token.textArray);
      }
   }

   boolean isSpace(char[] textArray) {
      int i = 0;

      for(int length = textArray.length; i < length; ++i) {
         if (!XmlChars.isSpace(textArray[i])) {
            return false;
         }
      }

      return true;
   }

   public boolean isSpace() {
      int i = 0;

      for(int length = this.arrays.size(); i < length; ++i) {
         if (!this.isSpace((char[])this.arrays.get(i))) {
            return false;
         }
      }

      return true;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);

      while(true) {
         while(parser.compare(19) || parser.compare(13) || parser.compare(14) || parser.compare(15) || parser.compare(10)) {
            if (parser.compare(10)) {
               parser.accept();

               while(parser.compare(11) || parser.compare(19)) {
                  this.addToTextArray(parser.currentToken);
                  parser.accept();
               }

               parser.accept(12);
            } else if (parser.compare(14)) {
               this.addToTextArray(parser.currentToken.text);
               parser.accept();
            } else if (parser.compare(15)) {
               this.addToTextArray(parser.currentToken.text);
               parser.accept();
            } else {
               this.addToTextArray(parser.currentToken);
               parser.accept();
            }
         }

         this.type = 5;
         return;
      }
   }

   protected char[] allocateArray() {
      if (this.arrays.size() == 1) {
         return (char[])this.arrays.get(0);
      } else {
         char[] newbuf = new char[this.length];
         int pos = 0;

         for(int i = 0; i < this.arrays.size(); ++i) {
            char[] currentbuf = (char[])this.arrays.get(i);
            if (currentbuf.length > 0) {
               System.arraycopy(currentbuf, 0, newbuf, pos, currentbuf.length);
            }

            pos += currentbuf.length;
         }

         return newbuf;
      }
   }

   public char[] getArray() {
      return this.arrays.size() == 0 ? blankArray : this.allocateArray();
   }

   public String getContent() {
      int size = this.arrays.size();
      if (size == 0) {
         return "";
      } else if (size == 1) {
         return new String((char[])this.arrays.get(0));
      } else {
         StringBuilder sb = new StringBuilder(this.length);

         for(int i = 0; i < size; ++i) {
            sb.append((char[])this.arrays.get(i));
         }

         return sb.toString();
      }
   }

   public String toString() {
      return this.getContent();
   }
}
