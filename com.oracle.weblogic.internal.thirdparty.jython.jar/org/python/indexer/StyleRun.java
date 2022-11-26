package org.python.indexer;

public class StyleRun implements Comparable {
   public Type type;
   private int offset;
   private int length;
   public String message;
   public String url;

   public StyleRun(Type type, int offset, int length) {
      this.type = type;
      this.offset = offset;
      this.length = length;
   }

   public StyleRun(Type type, int offset, int length, String msg, String url) {
      this.type = type;
      this.offset = offset;
      this.length = length;
      this.message = msg;
      this.url = url;
   }

   public int start() {
      return this.offset;
   }

   public int end() {
      return this.offset + this.length;
   }

   public int length() {
      return this.length;
   }

   public boolean equals(Object o) {
      if (!(o instanceof StyleRun)) {
         return false;
      } else {
         StyleRun other = (StyleRun)o;
         return other.type == this.type && other.offset == this.offset && other.length == this.length && this.equalFields(other.message, this.message) && this.equalFields(other.url, this.url);
      }
   }

   private boolean equalFields(Object o1, Object o2) {
      if (o1 == null) {
         return o2 == null;
      } else {
         return o1.equals(o2);
      }
   }

   public int compareTo(StyleRun other) {
      if (this.equals(other)) {
         return 0;
      } else if (this.offset < other.offset) {
         return -1;
      } else {
         return other.offset < this.offset ? 1 : this.hashCode() - other.hashCode();
      }
   }

   public String toString() {
      return "[" + this.type + " beg=" + this.offset + " len=" + this.length + "]";
   }

   public static enum Type {
      KEYWORD,
      COMMENT,
      STRING,
      DOC_STRING,
      IDENTIFIER,
      BUILTIN,
      NUMBER,
      CONSTANT,
      FUNCTION,
      PARAMETER,
      LOCAL,
      DECORATOR,
      CLASS,
      ATTRIBUTE,
      LINK,
      ANCHOR,
      DELIMITER,
      TYPE_NAME,
      ERROR,
      WARNING,
      INFO;
   }
}
