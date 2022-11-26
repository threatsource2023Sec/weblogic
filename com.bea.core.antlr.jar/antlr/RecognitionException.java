package antlr;

public class RecognitionException extends ANTLRException {
   public String fileName;
   public int line;
   public int column;

   public RecognitionException() {
      super("parsing error");
      this.fileName = null;
      this.line = -1;
      this.column = -1;
   }

   public RecognitionException(String var1) {
      super(var1);
      this.fileName = null;
      this.line = -1;
      this.column = -1;
   }

   /** @deprecated */
   public RecognitionException(String var1, String var2, int var3) {
      this(var1, var2, var3, -1);
   }

   public RecognitionException(String var1, String var2, int var3, int var4) {
      super(var1);
      this.fileName = var2;
      this.line = var3;
      this.column = var4;
   }

   public String getFilename() {
      return this.fileName;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }

   /** @deprecated */
   public String getErrorMessage() {
      return this.getMessage();
   }

   public String toString() {
      return FileLineFormatter.getFormatter().getFormatString(this.fileName, this.line, this.column) + this.getMessage();
   }
}
