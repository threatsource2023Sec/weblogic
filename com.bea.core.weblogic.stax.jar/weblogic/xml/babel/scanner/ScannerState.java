package weblogic.xml.babel.scanner;

import java.io.IOException;
import java.io.Reader;
import org.xml.sax.InputSource;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.xml.babel.baseparser.BaseEntityResolver;
import weblogic.xml.babel.baseparser.ParserConstraintException;
import weblogic.xml.babel.dtd.EntityTable;
import weblogic.xml.babel.reader.XmlChars;

public final class ScannerState {
   private Reader input;
   private UnsyncCircularQueue tokenQ = new UnsyncCircularQueue();
   private final IntegerQueue lineQ = new IntegerQueue();
   private final IntegerQueue columnQ = new IntegerQueue();
   private boolean reachedEOF;
   private boolean counting = false;
   private char[] inputBuffer;
   private int inputBufferPosition;
   private int inputBufferSize;
   private static final int MAXBUFSIZE = 16384;
   private int bufSize = 1024;
   private static final int initialBufSize = 1024;
   private int numAlloc = 1;
   private final PEReference perReference = new PEReference(this);
   private int mark;
   private boolean markSet;
   private int insertionPoint;
   private boolean insertionPointSet;
   private int tokenState;
   private boolean tokenStateSet;
   final TokenFactory tokenFactory = new TokenFactory();
   char currentChar;
   char lookAhead;
   int currentLine;
   int currentColumn;
   private int charsSinceMark;
   Token currentToken;
   private BaseEntityResolver entityResolver;
   private EntityTable parameterEntityTable = new EntityTable();
   private EntityTable internalEntityTable = new EntityTable();
   private EntityTable externalEntityTable = new EntityTable();

   public ScannerState(Reader reader) throws IOException, ScannerException {
      this.lineQ.add(0);
      this.columnQ.add(0);
      this.input = reader;
      this.currentLine = 1;
      this.currentColumn = 1;
      this.reachedEOF = false;
      this.inputBufferPosition = 0;
      this.inputBufferSize = 0;
      this.inputBuffer = new char[this.bufSize];
      this.read();
      this.mark = 0;
      this.charsSinceMark = 0;
      this.counting = false;
      this.markSet = false;
      this.tokenStateSet = false;
      this.insertionPoint = 0;
      this.insertionPointSet = false;
   }

   public void clear() {
      this.lineQ.clear();
      this.columnQ.clear();
      this.lineQ.add(0);
      this.columnQ.add(0);
      this.tokenFactory.init();
      if (this.inputBuffer.length > 16384) {
         this.inputBuffer = new char[1024];
      }

   }

   public ScannerState recycle(Reader reader) throws IOException, ScannerException {
      this.tokenQ = new UnsyncCircularQueue();
      this.clear();
      this.input = reader;
      this.currentLine = 1;
      this.currentColumn = 1;
      this.reachedEOF = false;
      this.inputBufferPosition = 0;
      this.inputBufferSize = 0;
      this.mark = 0;
      this.counting = false;
      this.charsSinceMark = 0;
      this.markSet = false;
      this.tokenStateSet = false;
      this.numAlloc = 1;
      this.insertionPoint = 0;
      this.insertionPointSet = false;
      this.read();
      return this;
   }

   public Scanner createScanner(InputSource inputSource) throws IOException, ScannerException {
      Scanner scanner = Scanner.createScanner(inputSource);
      scanner.init(this);
      return scanner;
   }

   Token returnToken() {
      return this.tokenQ.empty() ? this.currentToken : this.returnTokenUnchecked();
   }

   final Token returnTokenUnchecked() {
      this.lineQ.remove();
      this.columnQ.remove();
      return (Token)this.tokenQ.get();
   }

   final void setToken(Token token) {
      if (this.tokenQ.size() > 0) {
         this.pushToken(token);
      } else {
         this.currentToken = token;
      }

   }

   final void pushToken(Token token) {
      this.tokenQ.put(token);
      this.lineQ.add(this.currentLine);
      this.columnQ.add(this.currentColumn);
      this.currentToken = token;
   }

   final boolean hasQueuedTokens() {
      return this.tokenQ.size() > 0;
   }

   final int currentIndex() {
      return this.tokenQ.size() - 1;
   }

   final void assign(int position, Token token) {
      for(int i = 0; i < this.tokenQ.size(); ++i) {
         Token currentToken = (Token)this.tokenQ.get();
         if (i == position) {
            this.tokenQ.put(token);
         } else {
            this.tokenQ.put(currentToken);
         }
      }

   }

   final boolean hasReachedEOF() {
      return this.reachedEOF;
   }

   final boolean expect(String s) throws IOException, ScannerException {
      int i;
      for(i = 0; i < s.length(); ++i) {
         this.expect(s.charAt(i));
      }

      if (i == s.length()) {
         return true;
      } else {
         throw new ScannerException(" '" + s + "' expected, got char[" + this.currentChar + "] ", this);
      }
   }

   final boolean expect(char c) throws IOException, ScannerException {
      if (this.currentChar == c) {
         this.read();
         return true;
      } else {
         throw new ScannerException("Line:" + this.currentLine + " '" + c + "' expected, got char[" + this.currentChar + "]", this);
      }
   }

   final boolean expect(char c, String msg) throws IOException, ScannerException {
      if (this.currentChar == c) {
         this.read();
         return true;
      } else {
         throw new ScannerException("Line:" + this.currentLine + " A '" + c + "' was expected, " + msg);
      }
   }

   final void insertData(char[] data) throws IOException, ScannerException {
      if (!this.insertionPointSet) {
         throw new ScannerException("Attempt to rewrite data buffer without setting the insertion point");
      } else {
         this.insertionPointSet = false;
         char[] newbuf = new char[this.inputBuffer.length + data.length];

         int i;
         for(i = 0; i < this.insertionPoint; ++i) {
            newbuf[i] = this.inputBuffer[i];
         }

         for(i = 0; i < data.length; ++i) {
            newbuf[this.insertionPoint + i] = data[i];
         }

         for(i = 0; i < this.inputBuffer.length - this.insertionPoint; ++i) {
            newbuf[this.insertionPoint + data.length + i] = this.inputBuffer[this.insertionPoint + i];
         }

         this.inputBuffer = newbuf;
         this.inputBufferSize += data.length;
         this.currentChar = this.inputBuffer[this.insertionPoint];
         this.inputBufferPosition = this.insertionPoint + 1;
      }
   }

   final char[] addToBuffer(char[] buf, int amount) {
      char[] newbuf = new char[buf.length + amount];
      System.arraycopy(buf, 0, newbuf, 0, buf.length);
      return newbuf;
   }

   private final void set(char c) {
      this.inputBuffer[this.inputBufferPosition] = c;
   }

   private final void set(int position, char c) {
      this.inputBuffer[position] = c;
   }

   final void deleteFromLastInsertionPoint() {
      int numChar = this.inputBufferPosition - 1 - this.insertionPoint;
      this.deleteFromLastInsertionPoint(numChar);
   }

   private void deleteFromLastInsertionPoint(int numChar) {
      if (this.insertionPointSet) {
         for(int i = this.insertionPoint; i < this.inputBufferSize - numChar; ++i) {
            this.inputBuffer[i] = this.inputBuffer[i + numChar];
         }

         this.inputBufferSize -= numChar;
         this.inputBufferPosition -= numChar;
      }

   }

   private final void delete(int position) {
      for(int i = position; i < this.inputBufferSize - 1; ++i) {
         this.inputBuffer[i] = this.inputBuffer[i + 1];
      }

      --this.inputBufferSize;
   }

   final void read() throws IOException, ScannerException {
      try {
         if (this.inputBufferPosition == this.inputBufferSize && !this.reachedEOF) {
            int amountRead = 0;
            int readAmount = 512;
            int overflowAmount = 256;
            if (!this.markSet && !this.hasQueuedTokens()) {
               amountRead = this.input.read(this.inputBuffer, 0, readAmount);
               this.inputBufferSize = amountRead;
               this.inputBufferPosition = 0;
               this.numAlloc = 1;
            } else {
               try {
                  if (this.inputBufferSize + overflowAmount >= this.inputBuffer.length) {
                     this.inputBuffer = this.addToBuffer(this.inputBuffer, this.inputBuffer.length / 2 + this.numAlloc * overflowAmount);
                     this.bufSize = this.inputBuffer.length;
                     ++this.numAlloc;
                  }

                  amountRead = this.input.read(this.inputBuffer, this.inputBufferSize, overflowAmount);
                  this.inputBufferSize += amountRead;
               } catch (ParserConstraintException var5) {
                  throw var5;
               } catch (Exception var6) {
                  var6.printStackTrace();
                  System.out.println("amount read:" + amountRead + "inputBufferSize" + this.inputBufferSize + "overflow" + overflowAmount);
               }
            }

            if (amountRead == -1) {
               this.reachedEOF = true;
            }
         }

         this.currentChar = this.inputBuffer[this.inputBufferPosition];
         if (this.counting) {
            ++this.charsSinceMark;
         }

         ++this.inputBufferPosition;
         ++this.currentColumn;
         if (this.currentChar == '\n') {
            this.currentColumn = 1;
            ++this.currentLine;
         }

      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new ScannerException("Unterminated element", this);
      }
   }

   final void skipSpace() throws IOException, ScannerException {
      while(XmlChars.isSpace(this.currentChar)) {
         this.read();
      }

   }

   final void skipDTDSpace() throws IOException, ScannerException {
      this.skipSpace();

      while(this.currentChar == '%') {
         this.perReference.read();
      }

      this.skipSpace();
   }

   final void markInsert() {
      this.insertionPoint = this.inputBufferPosition - 1;
      this.insertionPointSet = true;
   }

   final void unMarkInsert() {
      this.insertionPoint = 0;
      this.insertionPointSet = false;
   }

   final void mark() {
      this.mark = this.inputBufferPosition - 1;
      if (this.mark < 0) {
         this.mark = 0;
      }

      this.markSet = true;
   }

   final void countedMark() {
      this.mark();
      this.counting = true;
      this.charsSinceMark = 0;
   }

   public int getCharsSinceMark() {
      return this.charsSinceMark;
   }

   void unMark() {
      this.markSet = false;
      this.charsSinceMark = 0;
      this.counting = false;
   }

   final String getString(int stringLength) {
      this.markSet = false;
      return stringLength > 0 ? new String(this.inputBuffer, this.mark, stringLength) : null;
   }

   private String getInputBuffer(int start, int stop) {
      if (start < 0) {
         start = 0;
      }

      if (stop < 0) {
         return null;
      } else {
         if (start + stop > this.inputBufferSize) {
            stop = this.inputBufferSize - start;
         }

         return stop < 0 ? null : new String(this.inputBuffer, start, stop);
      }
   }

   final String getInputBufferContext(int offset) {
      String txt = this.getInputBuffer(this.inputBufferPosition - offset, offset);
      txt = "{" + txt + "} <-- bad character";
      txt = txt + ", currentChar is {" + this.currentChar + "}";
      return txt;
   }

   final Token createToken(int tokenType, int length) {
      return length > 0 && this.markSet ? this.tokenFactory.createToken(tokenType, this.inputBuffer, this.mark, length) : this.tokenFactory.createToken(tokenType);
   }

   final Token createStoredToken(int tokenType, int length) {
      if (length > 0 && this.markSet) {
         this.markSet = false;
         return this.tokenFactory.createStoredToken(tokenType, this.inputBuffer, this.mark, length);
      } else {
         return this.tokenFactory.createToken(tokenType);
      }
   }

   final int getLine() {
      return this.lineQ.get();
   }

   final int getColumn() {
      return this.columnQ.get();
   }

   static final boolean isEOL(char c) {
      return c == '\r' || c == '\n' || c == '\t';
   }

   public static final boolean checkSize(int i) {
      return i >= 1024;
   }

   final void setState(int tokenState) {
      this.tokenStateSet = true;
      this.tokenState = tokenState;
   }

   final int getState() {
      this.tokenStateSet = false;
      return this.tokenState;
   }

   final boolean isStateSet() {
      return this.tokenStateSet;
   }

   public final void printTokenStack() {
      if (this.hasQueuedTokens()) {
         System.out.println("Scanner has tokens:");
      } else {
         System.out.println("Scanner has no tokens:");
      }

      while(this.hasQueuedTokens()) {
         System.out.println("\t" + this.returnToken());
      }

   }

   public void setBaseEntityResolver(BaseEntityResolver entityResolver) {
      this.entityResolver = entityResolver;
   }

   public BaseEntityResolver getBaseEntityResolver() {
      return this.entityResolver;
   }

   public void setParameterEntityTable(EntityTable tb) {
      this.parameterEntityTable = tb;
   }

   public void setExternalEntityTable(EntityTable tb) {
      this.externalEntityTable = tb;
   }

   public void setInternalEntityTable(EntityTable tb) {
      this.internalEntityTable = tb;
   }

   public EntityTable getParameterEntityTable() {
      return this.parameterEntityTable;
   }

   public EntityTable getInternalEntityTable() {
      return this.internalEntityTable;
   }

   public EntityTable getExternalEntityTable() {
      return this.externalEntityTable;
   }

   public final void checkedRead() throws IOException, ScannerException {
      if (!XmlChars.isChar(this.currentChar) && !XmlChars.isLowSurrogate(this.currentChar) && !XmlChars.isHighSurrogate(this.currentChar)) {
         throw new ScannerException(" Got character[" + this.currentChar + "] expected a valid XML character", this);
      } else {
         this.read();
      }
   }
}
