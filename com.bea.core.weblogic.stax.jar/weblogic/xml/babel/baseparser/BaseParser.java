package weblogic.xml.babel.baseparser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.collections.Stack;
import weblogic.xml.babel.dtd.DocumentTypeDefinition;
import weblogic.xml.babel.dtd.EntityTable;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.scanner.Scanner;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;
import weblogic.xml.stream.XMLStreamException;

public class BaseParser {
   Token currentToken;
   public static final boolean debug = false;
   protected static final boolean debugNS = false;
   protected static final boolean verbose = false;
   protected Scanner scanner;
   protected Stack stack;
   protected Stack prefixStack;
   protected SymbolTable nameSpaceTable;
   protected Map nameSpaceMap;
   protected static final String DEFAULTNSNAME = "";
   protected static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
   protected boolean dnsSet;
   protected boolean readOneStartTag;
   protected StartElement startElement;
   protected EndElement endElement;
   protected ProcessingInstruction pi;
   protected Space space;
   protected CharDataElement cde;
   protected CommentElement comment;
   protected NullElement nullElement;
   protected EOFElement eofElement;
   protected List outOfScopeNameSpace;
   protected BaseEntityResolver entityResolver;
   protected BaseParser parent;
   protected boolean isChild = false;
   protected String systemID = "";
   protected String publicID = "";
   private boolean isFragmentParser = false;
   private int maxAttrsPerElement = -1;

   protected BaseParser() {
   }

   public BaseParser(Reader reader) throws IOException, ScannerException {
      this.init(reader);
   }

   public BaseParser(InputStream stream) throws IOException, ScannerException {
      this.init(XmlReader.createReader(stream));
   }

   public BaseParser(InputSource input) throws IOException, ScannerException {
      this.init(input);
   }

   protected void init(String systemId) throws IOException, ScannerException {
      this.init(this.resolveSystemID(systemId));
   }

   protected void init(InputSource source) throws IOException, ScannerException {
      this.init(this.resolveInputSource(source));
   }

   protected void initScanner(Scanner scanner) throws IOException, ScannerException {
      this.prime();
   }

   public Reader resolveSystemID(String systemID) throws IOException {
      this.systemID = systemID;

      try {
         InputStream stream = new FileInputStream(systemID);
         return XmlReader.createReader(stream);
      } catch (IOException var4) {
         URL url = new URL(systemID);
         InputStream stream = new BufferedInputStream(url.openStream());
         return XmlReader.createReader(stream);
      }
   }

   public Reader resolveInputSource(InputSource source) throws IOException {
      if (source.getCharacterStream() != null) {
         return source.getCharacterStream();
      } else if (source.getByteStream() != null) {
         return XmlReader.createReader(source.getByteStream());
      } else if (source.getSystemId() != null) {
         return this.resolveSystemID(source.getSystemId());
      } else {
         throw new IOException("Unable to resolve input source.");
      }
   }

   public Scanner createScanner(InputSource source) throws IOException, ScannerException {
      return this.createScanner(this.resolveInputSource(source));
   }

   public Scanner createScanner(Reader reader) throws IOException, ScannerException {
      Scanner scanner;
      if (this.isChild) {
         scanner = this.subScanner(reader);
      } else {
         scanner = this.baseScanner(reader);
      }

      return scanner;
   }

   public Scanner baseScanner(Reader reader) throws IOException, ScannerException {
      Scanner scanner = new Scanner(reader);
      this.setEntityTables(scanner, this);
      scanner.setEntityResolver(this.entityResolver);
      return scanner;
   }

   public Scanner subScanner(Reader reader) throws IOException, ScannerException {
      Scanner scanner = this.baseScanner(reader);
      this.setEntityTables(scanner, this.parent);
      return scanner;
   }

   public void setParent(BaseParser parent) {
      this.parent = parent;
      this.isChild = true;
   }

   public void clear() {
      this.stack.clear();
      this.prefixStack.clear();
      this.nameSpaceTable.clear();
      this.nameSpaceMap.clear();
      this.scanner.clear();
      if (this.cde != null) {
         this.cde.init();
      }

      if (this.comment != null) {
         this.comment.init();
      }

      if (this.startElement != null) {
         this.startElement.init();
      }

      if (this.endElement != null) {
         this.endElement.init();
      }

      if (this.space != null) {
         this.space.init();
      }

      if (this.pi != null) {
         this.pi.init();
      }

      if (this.nullElement != null) {
         this.nullElement.init();
      }

      if (this.eofElement != null) {
         this.eofElement.init();
      }

      this.currentToken = null;
      this.outOfScopeNameSpace = null;
   }

   public BaseParser recycle(Reader reader) throws IOException, ScannerException {
      this.scanner.recycle(reader);
      this.clear();
      this.readOneStartTag = false;
      this.init();
      this.initScanner(this.scanner);
      return this;
   }

   protected void init(Reader reader) throws IOException, ScannerException {
      this.scanner = this.createScanner(reader);
      this.stack = new Stack();
      this.prefixStack = new Stack();
      if (this.isChild) {
         this.nameSpaceTable = this.parent.nameSpaceTable;
         this.nameSpaceMap = this.parent.nameSpaceMap;
      } else {
         this.nameSpaceTable = new SymbolTable();
         this.nameSpaceMap = new HashMap();
      }

      this.startElement = new StartElement();
      this.endElement = new EndElement();
      this.pi = new ProcessingInstruction();
      this.space = new Space();
      this.cde = new CharDataElement();
      this.comment = new CommentElement();
      this.nullElement = new NullElement();
      this.eofElement = new EOFElement();
      this.readOneStartTag = false;
      if (this.isChild) {
         this.setBaseEntityResolver(this.parent.getBaseEntityResolver());
         this.setEntityTables(this.scanner, this.parent);
      } else if (this.entityResolver == null) {
         this.setBaseEntityResolver(new BaseEntityResolver());
      }

      this.init();
      this.initScanner(this.scanner);
   }

   public void setEntityTables(Scanner scanner, BaseParser parser) {
   }

   public void setBaseEntityResolver(BaseEntityResolver entityResolver) {
      this.entityResolver = entityResolver;
      this.scanner.setEntityResolver(entityResolver);
   }

   public InputSource resolveEntity(String publicID, String systemID) throws SAXException {
      return this.entityResolver.resolveEntity(publicID, systemID);
   }

   public void prime() throws IOException, ScannerException {
      for(this.currentToken = null; this.currentToken == null; this.currentToken = this.scanner.scan()) {
      }

   }

   protected void init() {
      this.nameSpaceTable.put("", (String)null);
      this.nameSpaceTable.put("xml", "http://www.w3.org/XML/1998/namespace");
      this.nameSpaceMap.put("xml", "http://www.w3.org/XML/1998/namespace");
      this.dnsSet = false;
      this.cde.init();
   }

   protected Scanner getScanner() {
      return this.scanner;
   }

   public boolean hasNext() throws XMLStreamException {
      return !this.currentToken.isEOF();
   }

   public void setFragmentParser(boolean value) {
      this.isFragmentParser = value;
   }

   public void addNamespaceDeclarations(Map map) {
      if (map != null) {
         try {
            Iterator iter = map.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               String uri = (String)entry.getValue();
               String prefix = (String)entry.getKey();
               if (prefix.length() == 0) {
                  this.setDefaultNameSpace(uri);
               } else {
                  this.putNamespaceURI(prefix, uri);
               }
            }
         } catch (SAXException var6) {
         }

      }
   }

   public Element parseSome() throws IOException, ScannerException, ParseException {
      if (this.isEOF()) {
         return null;
      } else {
         Element element;
         if (!this.isFragmentParser && !this.nameSpaceTable.withinElement()) {
            element = this.parseProlog();
         } else {
            element = this.parseElement();
         }

         switch (element.type) {
            case 0:
               this.stack.push(element.getLocalName());
               String prefix = element.getPrefix();
               if (prefix == null) {
                  prefix = "";
               }

               this.prefixStack.push(prefix);
               break;
            case 3:
               if (this.stack.isEmpty()) {
                  throw new ParseException("Unexpected ELEMENT", this.scanner.getLine(), this.currentToken);
               }

               String ename = (String)this.stack.pop();
               String ePrefix = (String)this.prefixStack.pop();
               String tagName = element.getLocalName();
               String tagPrefix = element.getPrefix();
               if (tagPrefix == null) {
                  tagPrefix = "";
               }

               if (!ename.equals(tagName) || !ePrefix.equals(tagPrefix)) {
                  throw new ParseException("Unbalanced ELEMENT got:" + tagName + " expected:" + ename, this.scanner.getLine(), this.currentToken);
               }
         }

         return element;
      }
   }

   public BaseEntityResolver getBaseEntityResolver() {
      return this.entityResolver;
   }

   public void parse() throws IOException, ScannerException, ParseException {
      this.parseSome();
   }

   protected Element parseProlog() throws IOException, ScannerException, ParseException {
      switch (this.currentToken.tokenType) {
         case -1:
            this.eofElement.parse(this);
            this.processEOF();
            return this.eofElement;
         case 1:
            if (!this.readOneStartTag) {
               this.readOneStartTag = true;
               return this.parseElement();
            }

            throw new ParseException("All tags must be contained within a single element", this.scanner.getLine(), this.currentToken);
         case 7:
         case 8:
            return this.parseElement();
         case 13:
            this.cde.parse(this);
            return this.cde;
         case 19:
            this.space.parse(this);
            return this.space;
         case 24:
            this.accept();
            return this.parseProlog();
         default:
            System.out.println("Did not get a DOCYTPE");
            throw new ParseException("Expected a PI, SPACE, OPENTAG, or COMMENT ", this.scanner.getLine(), this.currentToken);
      }
   }

   protected Element parseElement() throws IOException, ScannerException, ParseException {
      switch (this.currentToken.tokenType) {
         case 1:
            this.nameSpaceTable.openScope();
            this.startElement.parse(this, this.maxAttrsPerElement);
            this.processAttributes(this.startElement);
            return this.startElement;
         case 2:
         case 3:
         case 5:
         case 6:
         case 9:
         case 12:
         case 16:
         case 17:
         case 18:
         default:
            throw new ParseException("Expected an ELEMENT", this.scanner.getLine(), this.currentToken);
         case 4:
            this.endElement.parse(this);
            this.outOfScopeNameSpace = this.nameSpaceTable.closeScope();

            try {
               this.removeNamespaceURI(this.outOfScopeNameSpace);
            } catch (SAXException var2) {
               throw new ParseException(var2.getMessage());
            }

            return this.endElement;
         case 7:
            this.comment.parse(this);
            return this.comment;
         case 8:
            this.pi.parse(this);
            return this.pi;
         case 10:
         case 11:
         case 13:
         case 14:
         case 15:
            this.cde.parse(this);
            return this.cde;
         case 19:
            this.space.parse(this);
            return this.space;
      }
   }

   protected void processAttributes(StartElement se) {
   }

   protected void parsePrint() throws IOException, ScannerException, ParseException {
      Element element = this.parseSome();
      System.out.print("[" + this.nameSpaceTable.getDepth() + "]" + element);
   }

   public void accept(int expectedIndex) throws IOException, ScannerException, ParseException {
      if (this.currentToken.tokenType == expectedIndex) {
         this.accept();
      } else {
         throw new ParseException("Unexpected token", this.scanner.getLine(), this.currentToken);
      }
   }

   public boolean ignore(int expectedIndex) throws IOException, ScannerException, ParseException {
      if (this.currentToken.tokenType == expectedIndex) {
         this.accept();
         return true;
      } else {
         return false;
      }
   }

   public void accept() throws IOException, ScannerException, ParseException {
      this.currentToken = this.scanner.scan();
   }

   void processEOF() throws ParseException {
      if (!this.isFragmentParser) {
         if (this.inDocument()) {
            throw new ParseException("Unbalanced root element in document, expected to see " + this.stack.pop() + " end tag", this.scanner.getLine(), this.currentToken);
         }
      }
   }

   public int getLine() {
      return this.scanner.getLine();
   }

   public int getColumn() {
      return this.scanner.getColumn();
   }

   public String getSystemId() {
      return this.systemID;
   }

   public String getPublicId() {
      return this.publicID;
   }

   public void printTokenStack() {
      this.scanner.printTokenStack();
   }

   public boolean compare(int tok) {
      return this.currentToken.tokenType == tok;
   }

   boolean isEOF() throws ParseException {
      if (this.currentToken.isEOF()) {
         this.processEOF();
         return true;
      } else {
         return false;
      }
   }

   public boolean inDocument() {
      return !this.stack.isEmpty();
   }

   void tab() {
      for(int i = 0; i < this.stack.size(); ++i) {
         System.out.print("\t");
      }

   }

   protected void putNamespaceURI(String key, String value) throws SAXException {
      this.nameSpaceTable.put(key, value);
      this.nameSpaceMap.put(key, value);
   }

   protected void removeNamespaceURI(List keys) throws SAXException {
      int i = 0;

      for(int len = keys.size(); i < len; ++i) {
         PrefixMapping prefixMapping = (PrefixMapping)keys.get(i);
         if (prefixMapping.getUri() == null) {
            this.nameSpaceMap.remove(prefixMapping.getPrefix());
         } else {
            this.nameSpaceMap.put(prefixMapping.getPrefix(), prefixMapping.getUri());
         }
      }

   }

   public Token getCurrentToken() {
      return this.currentToken;
   }

   protected void setDefaultNameSpace(String nameSpace) throws SAXException {
      this.putNamespaceURI("", nameSpace);
      this.dnsSet = true;
   }

   public List getOutOfScopeNamespaces() {
      return this.outOfScopeNameSpace;
   }

   public Map getNameSpaceMap() {
      return this.nameSpaceMap;
   }

   public boolean isDefaultNameSpaceSet() {
      return this.dnsSet;
   }

   public String getDefaultNameSpace() {
      return this.nameSpaceTable.get("");
   }

   public String getNameSpace(String prefix) {
      return this.nameSpaceTable.get(prefix);
   }

   public EntityTable getParameterEntityTable() {
      return null;
   }

   public EntityTable getInternalEntityTable() {
      return null;
   }

   public EntityTable getExternalEntityTable() {
      return null;
   }

   public DocumentTypeDefinition getDTD() {
      return null;
   }

   public String getDTDStringValue() {
      return this.scanner.getDTDStringValue();
   }

   public int getMaxAttrsPerElement() {
      return this.maxAttrsPerElement;
   }

   public void setMaxAttrsPerElement(int maxAttrsPerElement) {
      this.maxAttrsPerElement = maxAttrsPerElement;
   }

   public static void timeTest(String fname, int numTimes) {
      long duration = 0L;

      for(int i = 0; i < numTimes; ++i) {
         try {
            InputStream stream = new BufferedInputStream(new FileInputStream(fname));
            BaseParser parser = new BaseParser(stream);
            long ctime = System.currentTimeMillis();

            while(!parser.isEOF()) {
               parser.parse();
            }

            long ftime = System.currentTimeMillis();
            duration += ftime - ctime;
         } catch (ScannerException var11) {
            System.out.println("-----SCANNER----");
            System.out.println(var11);
            var11.printStackTrace();
            var11.printTokenStack();
            var11.printErrorLine();
         } catch (ParseException var12) {
            System.out.println("-----PARSER ----");
            System.out.println(var12);
            var12.printStackTrace();
         } catch (Exception var13) {
            System.out.println("-----JAVA   ----");
            var13.printStackTrace();
         }
      }

      System.out.println("Parser took:" + duration + " milliseconds for " + numTimes + " iterations " + (float)duration / (float)numTimes + " milliseconds per iteration");
      System.out.println("Parser:" + (double)((float)duration) / 1000.0 + " [s] (" + (double)numTimes / ((double)duration / 1000.0) + " [iteration/s], " + (float)duration / (float)numTimes + " [ms/iteration])");
   }

   public static void main(String[] args) {
      try {
         boolean timeTest = false;
         if (args.length == 2) {
            timeTest = true;
         }

         if (!timeTest) {
            Map map = new HashMap();
            map.put("a", "http://a");
            BaseParser parser = new BaseParser(new BufferedInputStream(new FileInputStream(args[0])));
            parser.setFragmentParser(false);
            parser.addNamespaceDeclarations(map);

            while(!parser.isEOF()) {
               parser.parsePrint();
            }
         } else {
            timeTest(args[0], Integer.parseInt(args[1]));
         }
      } catch (ScannerException var4) {
         System.out.println("-----SCANNER----");
         System.out.println(var4);
         var4.printStackTrace();
         var4.printTokenStack();
         var4.printErrorLine();
      } catch (ParseException var5) {
         System.out.println("-----PARSER ----");
         System.out.println(var5);
         var5.printStackTrace();
      } catch (Exception var6) {
         System.out.println("-----JAVA   ----");
         var6.printStackTrace();
      }

   }
}
