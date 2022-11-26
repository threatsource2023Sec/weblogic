package org.python.jline.console;

import org.python.jline.internal.Preconditions;

public class CursorBuffer {
   private boolean overTyping = false;
   public int cursor = 0;
   public final StringBuilder buffer = new StringBuilder();

   public CursorBuffer copy() {
      CursorBuffer that = new CursorBuffer();
      that.overTyping = this.overTyping;
      that.cursor = this.cursor;
      that.buffer.append(this.toString());
      return that;
   }

   public boolean isOverTyping() {
      return this.overTyping;
   }

   public void setOverTyping(boolean b) {
      this.overTyping = b;
   }

   public int length() {
      return this.buffer.length();
   }

   public char nextChar() {
      return this.cursor == this.buffer.length() ? '\u0000' : this.buffer.charAt(this.cursor);
   }

   public char current() {
      return this.cursor <= 0 ? '\u0000' : this.buffer.charAt(this.cursor - 1);
   }

   public void write(char c) {
      this.buffer.insert(this.cursor++, c);
      if (this.isOverTyping() && this.cursor < this.buffer.length()) {
         this.buffer.deleteCharAt(this.cursor);
      }

   }

   public void write(CharSequence str) {
      Preconditions.checkNotNull(str);
      if (this.buffer.length() == 0) {
         this.buffer.append(str);
      } else {
         this.buffer.insert(this.cursor, str);
      }

      this.cursor += str.length();
      if (this.isOverTyping() && this.cursor < this.buffer.length()) {
         this.buffer.delete(this.cursor, this.cursor + str.length());
      }

   }

   public boolean clear() {
      if (this.buffer.length() == 0) {
         return false;
      } else {
         this.buffer.delete(0, this.buffer.length());
         this.cursor = 0;
         return true;
      }
   }

   public String upToCursor() {
      return this.cursor <= 0 ? "" : this.buffer.substring(0, this.cursor);
   }

   public String toString() {
      return this.buffer.toString();
   }
}
