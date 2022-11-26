package org.glassfish.tyrus.core.uri.internal;

import java.util.NoSuchElementException;

final class CharacterIterator {
   private int pos;
   private String s;

   public CharacterIterator(String s) {
      this.s = s;
      this.pos = -1;
   }

   public boolean hasNext() {
      return this.pos < this.s.length() - 1;
   }

   public char next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.s.charAt(++this.pos);
      }
   }

   public char peek() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.s.charAt(this.pos + 1);
      }
   }

   public int pos() {
      return this.pos;
   }

   public String getInput() {
      return this.s;
   }

   public void setPosition(int newPosition) {
      if (newPosition > this.s.length() - 1) {
         throw new IndexOutOfBoundsException("Given position " + newPosition + " is outside the input string range.");
      } else {
         this.pos = newPosition;
      }
   }

   public char current() {
      if (this.pos == -1) {
         throw new IllegalStateException("Iterator not used yet.");
      } else {
         return this.s.charAt(this.pos);
      }
   }
}
