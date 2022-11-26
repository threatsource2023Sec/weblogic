package weblogic.xml.babel.scanner;

public class ScannerException extends Exception {
   private static final long serialVersionUID = 4623744204588273702L;
   int line;
   int column;
   transient ScannerState state;

   public ScannerException() {
      this.line = 0;
      this.column = 0;
      this.state = null;
   }

   public ScannerException(String s) {
      super(s);
      this.line = 0;
      this.column = 0;
      this.state = null;
   }

   public ScannerException(String s, int line, int column) {
      super(s);
      this.line = line;
      this.column = column;
   }

   public ScannerException(String s, int line, int column, ScannerState state) {
      super(s);
      this.line = line;
      this.column = column;
      this.state = state;
   }

   public ScannerException(String s, ScannerState state) {
      super(s);
      this.line = state.currentLine;
      this.column = state.currentColumn;
      this.state = state;
   }

   public String toString() {
      return "Error at line:" + this.line + " col:" + this.column + " " + this.getMessage();
   }

   public void printTokenStack() {
      if (this.state != null) {
         if (this.state.hasQueuedTokens()) {
            System.out.println("Scanner has tokens:");
         } else {
            System.out.println("Scanner has no tokens:");
         }

         while(this.state.hasQueuedTokens()) {
            System.out.println("\t" + this.state.returnToken());
         }

      }
   }

   public void printErrorLine() {
      if (this.state != null) {
         int offset = 20;
         System.out.println(this.state.getInputBufferContext(offset));
      }
   }
}
