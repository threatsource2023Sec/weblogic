package weblogic.xml.babel.baseparser;

import weblogic.xml.babel.reader.XmlChars;

public class XMLDeclaration {
   protected String version = null;
   protected String encoding = null;
   protected String standalone = null;
   protected boolean isValid = false;
   protected int position = 0;
   protected char currentChar;
   protected String input;
   protected static final boolean debug = false;

   public XMLDeclaration(String source) {
      this.input = source;
      if (this.input != null && this.hasNext()) {
         this.accept();
      }

   }

   protected boolean hasNext() {
      return this.position < this.input.length();
   }

   protected void accept() {
      if (this.hasNext()) {
         this.currentChar = this.input.charAt(this.position);
         ++this.position;
      }
   }

   protected void accept(char c) throws ParseException {
      if (this.currentChar == c) {
         this.accept();
      } else {
         throw new ParseException("The XMLDeclaration was not well formed");
      }
   }

   protected void accept(String s) throws ParseException {
      for(int i = 0; i < s.length(); ++i) {
         this.accept(s.charAt(i));
      }

   }

   protected void skipSpace() {
      while(XmlChars.isSpace(this.currentChar) && this.hasNext()) {
         this.accept();
      }

   }

   protected String readName(char c) {
      StringBuffer buf = new StringBuffer();

      while(this.currentChar != c && this.hasNext()) {
         buf.append(this.currentChar);
         this.accept();
      }

      return buf.toString();
   }

   protected String readAttribute() throws ParseException {
      this.skipSpace();
      String name;
      if (this.currentChar == '\'') {
         this.accept();
         name = this.readName('\'');
         this.accept('\'');
         return name;
      } else if (this.currentChar == '"') {
         this.accept();
         name = this.readName('"');
         this.accept('"');
         return name;
      } else {
         throw new ParseException("Unterminated value");
      }
   }

   protected void readEquals() throws ParseException {
      this.skipSpace();
      this.accept('=');
      this.skipSpace();
   }

   protected String readParam(String type) throws ParseException {
      this.skipSpace();
      this.accept(type);
      this.readEquals();
      String value = this.readAttribute();
      return value;
   }

   public void parse() throws ParseException {
      if (this.input != null) {
         this.version = this.readParam("version");
         if (this.hasNext()) {
            this.skipSpace();
            if (this.currentChar == 'e') {
               this.encoding = this.readParam("encoding");
            }

            this.skipSpace();
            if (this.currentChar == 's') {
               this.standalone = this.readParam("standalone");
               if (!this.standalone.equals("yes") && !this.standalone.equals("no")) {
                  throw new ParseException("Standalone declaration must be yes or no [" + this.standalone + "]");
               }
            }

            this.skipSpace();
            if (this.hasNext()) {
               throw new ParseException("The XML Encoding Declaration must consist of a valid version plus optional encoding and standalone declarations");
            }
         }
      }
   }

   public String getVersion() {
      return this.version;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public String getStandalone() {
      return this.standalone;
   }

   public String toString() {
      String value = "<?xml ";
      if (this.version != null) {
         value = value + "version='" + this.version + "'";
      }

      if (this.encoding != null) {
         value = value + " encoding='" + this.encoding + "'";
      }

      if (this.standalone != null) {
         value = value + " standalone='" + this.standalone + "'";
      }

      value = value + "?>";
      return value;
   }

   public static void main(String[] args) throws Exception {
      XMLDeclaration decl;
      if (args.length != 1) {
         decl = new XMLDeclaration("version = '1.0' encoding = 'foo' standalone='no'");
         decl.parse();
         System.out.println(decl);
         decl = new XMLDeclaration("version = '1.0' encoding = 'foo'");
         decl.parse();
         System.out.println(decl);
         decl = new XMLDeclaration("version = '1.0' ");
         decl.parse();
         System.out.println(decl);

         try {
            decl = new XMLDeclaration("version = '1.0 ");
            decl.parse();
         } catch (ParseException var4) {
            System.out.println("Caught a bad decl [1]" + var4);
         }

         System.out.println(decl);

         try {
            decl = new XMLDeclaration("version = '1.0' standalone='nope' ");
            decl.parse();
         } catch (ParseException var3) {
            System.out.println("Caught a bad decl [2]" + var3);
         }

         System.out.println(decl);
      } else {
         decl = new XMLDeclaration(args[0]);
         decl.parse();
         System.out.println(decl);
      }

   }
}
