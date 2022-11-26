package org.python.icu.text;

import java.text.CharacterIterator;
import org.python.icu.util.ICUCloneNotSupportedException;

/** @deprecated */
@Deprecated
public final class StringCharacterIterator implements CharacterIterator {
   private String text;
   private int begin;
   private int end;
   private int pos;

   /** @deprecated */
   @Deprecated
   public StringCharacterIterator(String text) {
      this(text, 0);
   }

   /** @deprecated */
   @Deprecated
   public StringCharacterIterator(String text, int pos) {
      this(text, 0, text.length(), pos);
   }

   /** @deprecated */
   @Deprecated
   public StringCharacterIterator(String text, int begin, int end, int pos) {
      if (text == null) {
         throw new NullPointerException();
      } else {
         this.text = text;
         if (begin >= 0 && begin <= end && end <= text.length()) {
            if (pos >= begin && pos <= end) {
               this.begin = begin;
               this.end = end;
               this.pos = pos;
            } else {
               throw new IllegalArgumentException("Invalid position");
            }
         } else {
            throw new IllegalArgumentException("Invalid substring range");
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public void setText(String text) {
      if (text == null) {
         throw new NullPointerException();
      } else {
         this.text = text;
         this.begin = 0;
         this.end = text.length();
         this.pos = 0;
      }
   }

   /** @deprecated */
   @Deprecated
   public char first() {
      this.pos = this.begin;
      return this.current();
   }

   /** @deprecated */
   @Deprecated
   public char last() {
      if (this.end != this.begin) {
         this.pos = this.end - 1;
      } else {
         this.pos = this.end;
      }

      return this.current();
   }

   /** @deprecated */
   @Deprecated
   public char setIndex(int p) {
      if (p >= this.begin && p <= this.end) {
         this.pos = p;
         return this.current();
      } else {
         throw new IllegalArgumentException("Invalid index");
      }
   }

   /** @deprecated */
   @Deprecated
   public char current() {
      return this.pos >= this.begin && this.pos < this.end ? this.text.charAt(this.pos) : '\uffff';
   }

   /** @deprecated */
   @Deprecated
   public char next() {
      if (this.pos < this.end - 1) {
         ++this.pos;
         return this.text.charAt(this.pos);
      } else {
         this.pos = this.end;
         return '\uffff';
      }
   }

   /** @deprecated */
   @Deprecated
   public char previous() {
      if (this.pos > this.begin) {
         --this.pos;
         return this.text.charAt(this.pos);
      } else {
         return '\uffff';
      }
   }

   /** @deprecated */
   @Deprecated
   public int getBeginIndex() {
      return this.begin;
   }

   /** @deprecated */
   @Deprecated
   public int getEndIndex() {
      return this.end;
   }

   /** @deprecated */
   @Deprecated
   public int getIndex() {
      return this.pos;
   }

   /** @deprecated */
   @Deprecated
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof StringCharacterIterator)) {
         return false;
      } else {
         StringCharacterIterator that = (StringCharacterIterator)obj;
         if (this.hashCode() != that.hashCode()) {
            return false;
         } else if (!this.text.equals(that.text)) {
            return false;
         } else {
            return this.pos == that.pos && this.begin == that.begin && this.end == that.end;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public int hashCode() {
      return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
   }

   /** @deprecated */
   @Deprecated
   public Object clone() {
      try {
         StringCharacterIterator other = (StringCharacterIterator)super.clone();
         return other;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }
}
