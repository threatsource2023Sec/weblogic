package weblogic.xml.babel.scanner;

import java.io.IOException;
import java.util.Arrays;
import weblogic.xml.babel.reader.XmlChars;
import weblogic.xml.util.TernarySearchTree;

final class Space {
   private ScannerState state;
   private char[] spaceInput;
   private int bufSize;
   private boolean prevLargeBuffer = false;
   private boolean hasShrunk = false;
   private boolean ignore = true;
   private TernarySearchTree values;

   Space(ScannerState state) {
      this.state = state;
      this.bufSize = 512;
      this.spaceInput = new char[this.bufSize];
      this.values = new TernarySearchTree();
   }

   public void init() {
      this.spaceInput = new char[this.bufSize];
      this.values = new TernarySearchTree();
   }

   char[] alloc(char[] oldArray, int size) {
      char[] value = (char[])((char[])this.values.get(oldArray, 0, size));
      if (value == null) {
         char[] newArray = new char[size];
         System.arraycopy(oldArray, 0, newArray, 0, size);
         this.values.put(new String(oldArray, 0, size), newArray);
         return newArray;
      } else {
         return value;
      }
   }

   void read() throws IOException, ScannerException {
      int i = 0;

      while(XmlChars.isSpace(this.state.currentChar)) {
         if (i >= this.spaceInput.length) {
            this.spaceInput = this.state.addToBuffer(this.spaceInput, this.bufSize);
         }

         if (this.state.currentChar == '\r') {
            this.spaceInput[i] = '\n';
            ++i;
            this.state.read();
            if (!XmlChars.isSpace(this.state.currentChar)) {
               this.state.pushToken(this.state.tokenFactory.createStoredToken(19, this.alloc(this.spaceInput, i), 0, i));
               if (i > this.bufSize) {
                  this.prevLargeBuffer = true;
               } else if (this.prevLargeBuffer) {
                  this.prevLargeBuffer = false;
                  this.hasShrunk = false;
               } else if (!this.hasShrunk) {
                  this.spaceInput = new char[this.bufSize];
                  this.hasShrunk = true;
                  return;
               }

               Arrays.fill(this.spaceInput, ' ');
               return;
            }

            if (this.state.currentChar == '\n') {
               this.state.read();
            }
         } else {
            this.spaceInput[i] = this.state.currentChar;
            this.state.read();
            ++i;
         }
      }

      this.state.pushToken(this.state.tokenFactory.createStoredToken(19, this.alloc(this.spaceInput, i), 0, i));
      if (i > this.bufSize) {
         this.prevLargeBuffer = true;
      } else if (this.prevLargeBuffer) {
         this.prevLargeBuffer = false;
         this.hasShrunk = false;
      } else if (!this.hasShrunk) {
         this.spaceInput = new char[this.bufSize];
         this.hasShrunk = true;
         return;
      }

      Arrays.fill(this.spaceInput, ' ');
   }

   void skipRead() throws IOException, ScannerException {
      while(XmlChars.isSpace(this.state.currentChar)) {
         this.state.read();
      }

      this.state.pushToken(this.state.tokenFactory.createToken(19, " "));
   }
}
