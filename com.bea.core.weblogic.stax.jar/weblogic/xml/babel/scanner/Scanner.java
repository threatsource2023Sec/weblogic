package weblogic.xml.babel.scanner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import org.xml.sax.InputSource;
import weblogic.xml.babel.baseparser.BaseEntityResolver;
import weblogic.xml.babel.reader.XmlReader;

public final class Scanner {
   private ScannerState state;
   private OpenTag openTag;
   private CloseTag closeTag;
   private Comment comment;
   private ProcessingInstruction processingInstruction;
   private CData cData;
   private CharData datachar;
   private Reference reference;
   private Name name;
   private Space space;
   private DTDProcessor dtdScanner;
   private int readAhead;
   private BaseEntityResolver entityResolver;
   private String dtdStringValue;
   static final boolean debug = false;
   static final boolean verbose = false;

   public Scanner(Reader reader) throws IOException, ScannerException {
      this.state = new ScannerState(reader);
      this.openTag = new OpenTag(this.state);
      this.closeTag = new CloseTag(this.state);
      this.comment = new Comment(this.state, false);
      this.processingInstruction = new ProcessingInstruction(this.state);
      this.cData = new CData(this.state);
      this.datachar = new CharData(this.state, "&<\t\n\r");
      this.reference = new Reference(this.state);
      this.name = new Name(this.state);
      this.space = new Space(this.state);
      this.dtdScanner = new DTDHandler(this.state);
      this.readAhead = 10;
      this.dtdStringValue = null;
   }

   public Scanner recycle(Reader reader) throws IOException, ScannerException {
      this.clear();
      this.state = this.state.recycle(reader);
      this.dtdStringValue = null;
      return this;
   }

   public void clear() {
      this.state.clear();
      this.space.init();
      this.cData.clear();
      this.processingInstruction.clear();
   }

   public static Scanner createScanner(InputSource source) throws IOException, ScannerException {
      if (source.getCharacterStream() != null) {
         return new Scanner(source.getCharacterStream());
      } else if (source.getByteStream() != null) {
         return new Scanner(XmlReader.createReader(source.getByteStream()));
      } else if (source.getSystemId() != null) {
         URL url = new URL(source.getSystemId());
         InputStream stream = new BufferedInputStream(url.openStream());
         return new Scanner(XmlReader.createReader(stream));
      } else {
         throw new ScannerException("Unable to resolve input source.");
      }
   }

   public void setEntityResolver(BaseEntityResolver entityResolver) {
      this.entityResolver = entityResolver;
      this.state.setBaseEntityResolver(entityResolver);
   }

   public void init(ScannerState state) {
      this.state.setBaseEntityResolver(state.getBaseEntityResolver());
      this.state.setParameterEntityTable(state.getParameterEntityTable());
      this.state.setInternalEntityTable(state.getInternalEntityTable());
      this.state.setExternalEntityTable(state.getExternalEntityTable());
   }

   public BaseEntityResolver getEntityResolver() {
      return this.entityResolver;
   }

   public ScannerState getState() {
      return this.state;
   }

   public void setReadDTD() throws IOException, ScannerException {
      this.dtdScanner = new DTDScanner(this.state);
   }

   public void setExternalDTD(boolean val) {
      this.dtdScanner.setExternalDTD(val);
      this.state.setState(24);
   }

   public void setSkipDTD() throws IOException, ScannerException {
      this.dtdScanner = new DTDHandler(this.state);
   }

   public final Token scan() throws IOException, ScannerException {
      if (this.state.hasQueuedTokens()) {
         return this.state.returnTokenUnchecked();
      } else {
         for(int i = 0; i < this.readAhead; ++i) {
            if (this.state.hasReachedEOF()) {
               this.state.setToken(this.state.tokenFactory.createEOF());
               return this.state.returnToken();
            }

            this.startState();
            if (this.state.currentToken != null && this.state.currentToken.isEODTD()) {
               break;
            }
         }

         return this.state.returnToken();
      }
   }

   public final int getLine() {
      return this.state.getLine();
   }

   public final int getColumn() {
      return this.state.getColumn();
   }

   public final void printTokenStack() {
      this.state.printTokenStack();
   }

   public final void setReadAhead(int i) {
      this.readAhead = i;
   }

   public final int getReadAhead() {
      return this.readAhead;
   }

   public final String getDTDStringValue() {
      return this.dtdStringValue;
   }

   private final void startState() throws IOException, ScannerException {
      if (this.state.isStateSet()) {
         int currentState = this.state.getState();
         switch (currentState) {
            case 11:
               this.cData.read(false);
               return;
            case 24:
               this.dtdScanner.read();
               return;
         }
      } else {
         switch (this.state.currentChar) {
            case '\t':
            case '\n':
            case '\r':
               this.space.read();
               break;
            case '&':
               this.reference.read();
               break;
            case '<':
               this.state.read();
               if (Name.isNameBegin(this.state.currentChar)) {
                  this.openTag.read();
               } else if (this.state.currentChar == '/') {
                  this.state.read();
                  this.closeTag.read();
               } else if (this.state.currentChar == '!') {
                  this.state.read();
                  if (this.state.currentChar == '-') {
                     this.state.read();
                     this.state.expect('-');
                     this.comment.read();
                  } else if (this.state.currentChar == '[') {
                     this.state.read();
                     this.state.expect("CDATA[");
                     this.cData.read();
                  } else {
                     String command = this.name.stringRead();
                     if (command.equals("DOCTYPE")) {
                        this.dtdScanner.read();
                        this.dtdStringValue = this.dtdScanner.getStringValue();
                        return;
                     }
                  }
               } else {
                  if (this.state.currentChar != '?') {
                     throw new ScannerException(" '" + this.state.currentChar + "' expected [/?! -- [CDATA[ ]", this.state);
                  }

                  this.state.read();
                  this.processingInstruction.read();
               }
               break;
            default:
               if (!this.datachar.read()) {
                  throw new ScannerException(" '" + this.state.currentChar + "' expected a valid XML character", this.state);
               }
         }
      }

   }

   public static final void main(String[] args) {
      try {
         for(int i = 0; i < 10; ++i) {
            boolean timeTest = false;
            if (args.length == 2) {
               timeTest = true;
            }

            InputStream stream = new BufferedInputStream(new FileInputStream(args[0]));
            Reader reader = XmlReader.createReader(stream);
            Scanner scanner = new Scanner(reader);
            scanner.setReadDTD();
            long ctime = System.currentTimeMillis();
            Token token;
            if (!timeTest) {
               do {
                  for(token = scanner.scan(); token == null; token = scanner.scan()) {
                  }

                  System.out.print("[" + scanner.getLine());
                  System.out.print("," + scanner.getColumn() + "] ");
                  System.out.println(token);
               } while(!token.isEOF());

               return;
            }

            do {
               for(token = scanner.scan(); token == null; token = scanner.scan()) {
               }
            } while(!token.isEOF());

            long ftime = System.currentTimeMillis();
            long duration = ftime - ctime;
            System.out.println("Scanner took:" + duration + " milliseconds");
         }
      } catch (ScannerException var13) {
         System.out.println(var13);
         var13.printStackTrace();
         var13.printTokenStack();
         var13.printErrorLine();
      } catch (Exception var14) {
         var14.printStackTrace();
      }

   }
}
