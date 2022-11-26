package com.googlecode.cqengine.query.option;

public class QueryLog {
   final Appendable sink;
   final String lineSeparator;

   public QueryLog(Appendable sink) {
      this(sink, System.getProperty("line.separator"));
   }

   public QueryLog(Appendable sink, String lineSeparator) {
      this.sink = sink;
      this.lineSeparator = lineSeparator;
   }

   public Appendable getSink() {
      return this.sink;
   }

   public void log(String message) {
      try {
         this.sink.append(message);
         if (this.lineSeparator != null) {
            this.sink.append(this.lineSeparator);
         }

      } catch (Exception var3) {
         throw new IllegalStateException("Exception appending to query log", var3);
      }
   }
}
