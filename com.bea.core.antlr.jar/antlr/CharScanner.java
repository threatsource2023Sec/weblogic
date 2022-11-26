package antlr;

import antlr.collections.impl.BitSet;
import java.util.Hashtable;

public abstract class CharScanner implements TokenStream {
   static final char NO_CHAR = '\u0000';
   public static final char EOF_CHAR = '\uffff';
   protected ANTLRStringBuffer text;
   protected boolean saveConsumedInput;
   protected Class tokenObjectClass;
   protected boolean caseSensitive;
   protected boolean caseSensitiveLiterals;
   protected Hashtable literals;
   protected int tabsize;
   protected Token _returnToken;
   protected ANTLRHashString hashString;
   protected LexerSharedInputState inputState;
   protected boolean commitToPath;
   protected int traceDepth;

   public CharScanner() {
      this.saveConsumedInput = true;
      this.caseSensitive = true;
      this.caseSensitiveLiterals = true;
      this.tabsize = 8;
      this._returnToken = null;
      this.commitToPath = false;
      this.traceDepth = 0;
      this.text = new ANTLRStringBuffer();
      this.hashString = new ANTLRHashString(this);
      this.setTokenObjectClass("antlr.CommonToken");
   }

   public CharScanner(InputBuffer var1) {
      this();
      this.inputState = new LexerSharedInputState(var1);
   }

   public CharScanner(LexerSharedInputState var1) {
      this();
      this.inputState = var1;
   }

   public void append(char var1) {
      if (this.saveConsumedInput) {
         this.text.append(var1);
      }

   }

   public void append(String var1) {
      if (this.saveConsumedInput) {
         this.text.append(var1);
      }

   }

   public void commit() {
      this.inputState.input.commit();
   }

   public void consume() throws CharStreamException {
      if (this.inputState.guessing == 0) {
         char var1 = this.LA(1);
         if (this.caseSensitive) {
            this.append(var1);
         } else {
            this.append(this.inputState.input.LA(1));
         }

         if (var1 == '\t') {
            this.tab();
         } else {
            ++this.inputState.column;
         }
      }

      this.inputState.input.consume();
   }

   public void consumeUntil(int var1) throws CharStreamException {
      while(this.LA(1) != '\uffff' && this.LA(1) != var1) {
         this.consume();
      }

   }

   public void consumeUntil(BitSet var1) throws CharStreamException {
      while(this.LA(1) != '\uffff' && !var1.member(this.LA(1))) {
         this.consume();
      }

   }

   public boolean getCaseSensitive() {
      return this.caseSensitive;
   }

   public final boolean getCaseSensitiveLiterals() {
      return this.caseSensitiveLiterals;
   }

   public int getColumn() {
      return this.inputState.column;
   }

   public void setColumn(int var1) {
      this.inputState.column = var1;
   }

   public boolean getCommitToPath() {
      return this.commitToPath;
   }

   public String getFilename() {
      return this.inputState.filename;
   }

   public InputBuffer getInputBuffer() {
      return this.inputState.input;
   }

   public LexerSharedInputState getInputState() {
      return this.inputState;
   }

   public void setInputState(LexerSharedInputState var1) {
      this.inputState = var1;
   }

   public int getLine() {
      return this.inputState.line;
   }

   public String getText() {
      return this.text.toString();
   }

   public Token getTokenObject() {
      return this._returnToken;
   }

   public char LA(int var1) throws CharStreamException {
      return this.caseSensitive ? this.inputState.input.LA(var1) : this.toLower(this.inputState.input.LA(var1));
   }

   protected Token makeToken(int var1) {
      try {
         Token var2 = (Token)this.tokenObjectClass.newInstance();
         var2.setType(var1);
         var2.setColumn(this.inputState.tokenStartColumn);
         var2.setLine(this.inputState.tokenStartLine);
         return var2;
      } catch (InstantiationException var3) {
         this.panic("can't instantiate token: " + this.tokenObjectClass);
      } catch (IllegalAccessException var4) {
         this.panic("Token class is not accessible" + this.tokenObjectClass);
      }

      return Token.badToken;
   }

   public int mark() {
      return this.inputState.input.mark();
   }

   public void match(char var1) throws MismatchedCharException, CharStreamException {
      if (this.LA(1) != var1) {
         throw new MismatchedCharException(this.LA(1), var1, false, this);
      } else {
         this.consume();
      }
   }

   public void match(BitSet var1) throws MismatchedCharException, CharStreamException {
      if (!var1.member(this.LA(1))) {
         throw new MismatchedCharException(this.LA(1), var1, false, this);
      } else {
         this.consume();
      }
   }

   public void match(String var1) throws MismatchedCharException, CharStreamException {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (this.LA(1) != var1.charAt(var3)) {
            throw new MismatchedCharException(this.LA(1), var1.charAt(var3), false, this);
         }

         this.consume();
      }

   }

   public void matchNot(char var1) throws MismatchedCharException, CharStreamException {
      if (this.LA(1) == var1) {
         throw new MismatchedCharException(this.LA(1), var1, true, this);
      } else {
         this.consume();
      }
   }

   public void matchRange(char var1, char var2) throws MismatchedCharException, CharStreamException {
      if (this.LA(1) >= var1 && this.LA(1) <= var2) {
         this.consume();
      } else {
         throw new MismatchedCharException(this.LA(1), var1, var2, false, this);
      }
   }

   public void newline() {
      ++this.inputState.line;
      this.inputState.column = 1;
   }

   public void tab() {
      int var1 = this.getColumn();
      int var2 = ((var1 - 1) / this.tabsize + 1) * this.tabsize + 1;
      this.setColumn(var2);
   }

   public void setTabSize(int var1) {
      this.tabsize = var1;
   }

   public int getTabSize() {
      return this.tabsize;
   }

   public void panic() {
      System.err.println("CharScanner: panic");
      Utils.error("");
   }

   public void panic(String var1) {
      System.err.println("CharScanner; panic: " + var1);
      Utils.error(var1);
   }

   public void reportError(RecognitionException var1) {
      System.err.println(var1);
   }

   public void reportError(String var1) {
      if (this.getFilename() == null) {
         System.err.println("error: " + var1);
      } else {
         System.err.println(this.getFilename() + ": error: " + var1);
      }

   }

   public void reportWarning(String var1) {
      if (this.getFilename() == null) {
         System.err.println("warning: " + var1);
      } else {
         System.err.println(this.getFilename() + ": warning: " + var1);
      }

   }

   public void resetText() {
      this.text.setLength(0);
      this.inputState.tokenStartColumn = this.inputState.column;
      this.inputState.tokenStartLine = this.inputState.line;
   }

   public void rewind(int var1) {
      this.inputState.input.rewind(var1);
   }

   public void setCaseSensitive(boolean var1) {
      this.caseSensitive = var1;
   }

   public void setCommitToPath(boolean var1) {
      this.commitToPath = var1;
   }

   public void setFilename(String var1) {
      this.inputState.filename = var1;
   }

   public void setLine(int var1) {
      this.inputState.line = var1;
   }

   public void setText(String var1) {
      this.resetText();
      this.text.append(var1);
   }

   public void setTokenObjectClass(String var1) {
      try {
         this.tokenObjectClass = Utils.loadClass(var1);
      } catch (ClassNotFoundException var3) {
         this.panic("ClassNotFoundException: " + var1);
      }

   }

   public int testLiteralsTable(int var1) {
      this.hashString.setBuffer(this.text.getBuffer(), this.text.length());
      Integer var2 = (Integer)this.literals.get(this.hashString);
      if (var2 != null) {
         var1 = var2;
      }

      return var1;
   }

   public int testLiteralsTable(String var1, int var2) {
      ANTLRHashString var3 = new ANTLRHashString(var1, this);
      Integer var4 = (Integer)this.literals.get(var3);
      if (var4 != null) {
         var2 = var4;
      }

      return var2;
   }

   public char toLower(char var1) {
      return Character.toLowerCase(var1);
   }

   public void traceIndent() {
      for(int var1 = 0; var1 < this.traceDepth; ++var1) {
         System.out.print(" ");
      }

   }

   public void traceIn(String var1) throws CharStreamException {
      ++this.traceDepth;
      this.traceIndent();
      System.out.println("> lexer " + var1 + "; c==" + this.LA(1));
   }

   public void traceOut(String var1) throws CharStreamException {
      this.traceIndent();
      System.out.println("< lexer " + var1 + "; c==" + this.LA(1));
      --this.traceDepth;
   }

   public void uponEOF() throws TokenStreamException, CharStreamException {
   }
}
