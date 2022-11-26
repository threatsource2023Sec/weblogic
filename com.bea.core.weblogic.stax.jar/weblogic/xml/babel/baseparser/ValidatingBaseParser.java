package weblogic.xml.babel.baseparser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.dtd.DocumentTypeDefinition;
import weblogic.xml.babel.dtd.EntityTable;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.scanner.Scanner;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.ScannerState;

public class ValidatingBaseParser extends BaseParser {
   protected boolean scanningDTD = false;
   protected DocumentTypeDefinition dtd;

   public ValidatingBaseParser() {
   }

   public ValidatingBaseParser(BaseParser parser) {
      this.setParent(parser);
   }

   public ValidatingBaseParser(Reader reader) throws IOException, ScannerException {
      super.init(reader);
   }

   public ValidatingBaseParser(InputStream stream) throws IOException, ScannerException {
      super.init(XmlReader.createReader(stream));
   }

   public ValidatingBaseParser(InputSource input) throws IOException, ScannerException {
      super.init(input);
   }

   public void initDTD(InputSource input) throws IOException, ScannerException {
      super.init(input);
   }

   public void setEntityTables(Scanner scanner, BaseParser parser) {
      ScannerState state = scanner.getState();
      state.setParameterEntityTable(parser.getParameterEntityTable());
      state.setExternalEntityTable(parser.getExternalEntityTable());
      state.setInternalEntityTable(parser.getInternalEntityTable());
   }

   protected void initScanner(Scanner scanner) throws IOException, ScannerException {
      if (this.dtd == null) {
         throw new ScannerException("SanityCheck: no dtd!");
      } else {
         if (this.isChild) {
            this.setEntityTables(scanner, this.parent);
         } else {
            this.setEntityTables(scanner, this);
         }

         scanner.setReadDTD();
         if (this.scanningDTD) {
            scanner.setExternalDTD(true);
         }

         this.prime();
      }
   }

   public void setExternalDTD() {
      this.scanningDTD = true;
   }

   public DocumentTypeDefinition getDTD() {
      return this.dtd;
   }

   protected void init(Reader reader) throws IOException, ScannerException {
      this.dtd = new DocumentTypeDefinition();
      if (this.isChild) {
         this.dtd.setTables(this.parent.getParameterEntityTable(), this.parent.getExternalEntityTable(), this.parent.getInternalEntityTable());
      }

      super.init(reader);
   }

   protected Element parseProlog() throws IOException, ScannerException, ParseException {
      switch (this.currentToken.tokenType) {
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
            if (this.cde.isSpace()) {
               return this.cde;
            }
         case -1:
            this.eofElement.parse(this);
            return this.eofElement;
         case 19:
            this.space.parse(this);
            return this.space;
         case 24:
            this.dtd.parse(this);
            return this.parseElement();
         default:
            System.out.println("Did not get a DOCYTPE");
            throw new ParseException("Expected a PI, SPACE, OPENTAG, or COMMENT ", this.scanner.getLine(), this.currentToken);
      }
   }

   protected void processAttributes(StartElement se) {
      this.dtd.processAttributes(se);

      try {
         Iterator i = se.getAttributes().iterator();

         while(i.hasNext()) {
            Attribute a = (Attribute)i.next();
            String name = a.getLocalName();
            if (name.startsWith("xml:")) {
               this.putNamespaceURI(name, a.getValue());
            }
         }
      } catch (SAXException var5) {
      }

   }

   public EntityTable getParameterEntityTable() {
      return this.dtd.getParameterEntityTable();
   }

   public EntityTable getInternalEntityTable() {
      return this.dtd.getInternalEntityTable();
   }

   public EntityTable getExternalEntityTable() {
      return this.dtd.getExternalEntityTable();
   }

   public static void main(String[] args) {
      try {
         BaseParser parser = new ValidatingBaseParser(new InputSource(new BufferedInputStream(new FileInputStream(args[0]))));

         while(!parser.isEOF()) {
            Element e = parser.parseSome();
            System.out.println("[" + e.getLine() + "][" + Element.getString(e.type) + "][" + e + "]");
         }
      } catch (ScannerException var3) {
         System.out.println("-----SCANNER----");
         System.out.println(var3);
         var3.printStackTrace();
         var3.printTokenStack();
         var3.printErrorLine();
      } catch (ParseException var4) {
         System.out.println("-----PARSER ----");
         System.out.println(var4);
         var4.printStackTrace();
      } catch (Exception var5) {
         System.out.println("-----JAVA   ----");
         var5.printStackTrace();
      }

   }
}
