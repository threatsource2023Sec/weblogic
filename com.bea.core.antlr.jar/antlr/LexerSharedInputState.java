package antlr;

import java.io.InputStream;
import java.io.Reader;

public class LexerSharedInputState {
   protected int column;
   protected int line;
   protected int tokenStartColumn;
   protected int tokenStartLine;
   protected InputBuffer input;
   protected String filename;
   public int guessing;

   public LexerSharedInputState(InputBuffer var1) {
      this.column = 1;
      this.line = 1;
      this.tokenStartColumn = 1;
      this.tokenStartLine = 1;
      this.guessing = 0;
      this.input = var1;
   }

   public LexerSharedInputState(InputStream var1) {
      this((InputBuffer)(new ByteBuffer(var1)));
   }

   public LexerSharedInputState(Reader var1) {
      this((InputBuffer)(new CharBuffer(var1)));
   }

   public String getFilename() {
      return this.filename;
   }

   public InputBuffer getInput() {
      return this.input;
   }

   public int getLine() {
      return this.line;
   }

   public int getTokenStartColumn() {
      return this.tokenStartColumn;
   }

   public int getTokenStartLine() {
      return this.tokenStartLine;
   }

   public int getColumn() {
      return this.column;
   }

   public void reset() {
      this.column = 1;
      this.line = 1;
      this.tokenStartColumn = 1;
      this.tokenStartLine = 1;
      this.guessing = 0;
      this.filename = null;
      this.input.reset();
   }
}
