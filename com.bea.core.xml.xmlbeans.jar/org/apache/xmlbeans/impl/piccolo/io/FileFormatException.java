package org.apache.xmlbeans.impl.piccolo.io;

import java.io.IOException;

public class FileFormatException extends IOException {
   protected int line;
   protected int column;

   public FileFormatException() {
      this((String)null);
   }

   public FileFormatException(String msg) {
      this(msg, -1, -1);
   }

   public FileFormatException(String msg, int line, int column) {
      super(msg);
      this.line = line;
      this.column = column;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }
}
