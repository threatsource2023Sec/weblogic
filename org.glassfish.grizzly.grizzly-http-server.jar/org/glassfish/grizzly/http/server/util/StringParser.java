package org.glassfish.grizzly.http.server.util;

public final class StringParser {
   private char[] chars;
   private int index;
   private int length;
   private String string;

   public StringParser() {
      this((String)null);
   }

   public StringParser(String string) {
      this.chars = null;
      this.index = 0;
      this.length = 0;
      this.string = null;
      this.setString(string);
   }

   public int getIndex() {
      return this.index;
   }

   public int getLength() {
      return this.length;
   }

   public String getString() {
      return this.string;
   }

   public void setString(String string) {
      this.string = string;
      if (string != null) {
         this.length = string.length();
         this.chars = this.string.toCharArray();
      } else {
         this.length = 0;
         this.chars = new char[0];
      }

      this.reset();
   }

   public void advance() {
      if (this.index < this.length) {
         ++this.index;
      }

   }

   public String extract(int start) {
      return start >= 0 && start < this.length ? this.string.substring(start) : "";
   }

   public String extract(int start, int end) {
      return start >= 0 && start < end && end <= this.length ? this.string.substring(start, end) : "";
   }

   public int findChar(char ch) {
      while(this.index < this.length && ch != this.chars[this.index]) {
         ++this.index;
      }

      return this.index;
   }

   public int findText() {
      while(this.index < this.length && this.isWhite(this.chars[this.index])) {
         ++this.index;
      }

      return this.index;
   }

   public int findWhite() {
      while(this.index < this.length && !this.isWhite(this.chars[this.index])) {
         ++this.index;
      }

      return this.index;
   }

   public void reset() {
      this.index = 0;
   }

   public int skipChar(char ch) {
      while(this.index < this.length && ch == this.chars[this.index]) {
         ++this.index;
      }

      return this.index;
   }

   public int skipText() {
      while(this.index < this.length && !this.isWhite(this.chars[this.index])) {
         ++this.index;
      }

      return this.index;
   }

   public int skipWhite() {
      while(this.index < this.length && this.isWhite(this.chars[this.index])) {
         ++this.index;
      }

      return this.index;
   }

   protected boolean isWhite(char ch) {
      return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
   }
}
