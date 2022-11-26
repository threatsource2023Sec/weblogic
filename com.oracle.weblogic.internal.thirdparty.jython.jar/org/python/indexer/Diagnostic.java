package org.python.indexer;

public class Diagnostic {
   public String file;
   public Type type;
   public int start;
   public int end;
   public int line;
   public int column;
   public String msg;

   public Diagnostic(String file, Type type, int start, int end, String msg) {
      this.type = type;
      this.file = file;
      this.start = start;
      this.end = end;
      this.msg = msg;
   }

   public String toString() {
      return "<Diagnostic:" + this.file + ":" + this.type + ":" + this.msg + ">";
   }

   public static enum Type {
      INFO,
      WARNING,
      ERROR;
   }
}
