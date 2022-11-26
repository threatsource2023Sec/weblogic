package weblogic.xml.babel.scanner;

public final class Token {
   public int tokenType;
   public int subType;
   public String text;
   public char[] textArray;
   public int start;
   public int length;
   public static final int EOF = -1;
   public static final int NAME = 0;
   public static final int OPENTAGBEGIN = 1;
   public static final int TAGEND = 2;
   public static final int CHAR = 3;
   public static final int CLOSETAGBEGIN = 4;
   public static final int EMPTYTAGEND = 5;
   public static final int EQUALS = 6;
   public static final int COMMENT = 7;
   public static final int PIBEGIN = 8;
   public static final int PIEND = 9;
   public static final int CDBEGIN = 10;
   public static final int CDATA = 11;
   public static final int CDEND = 12;
   public static final int CHARDATA = 13;
   public static final int ENTITYREF = 14;
   public static final int CHARREF = 15;
   public static final int ATTVALUE = 16;
   public static final int PIDATA = 17;
   public static final int NAMEPREFIX = 18;
   public static final int SPACE = 19;
   public static final int UNDEFINED = 20;
   public static final int HEXADECIMAL = 21;
   public static final int DECIMAL = 22;
   public static final int XMLNS = 23;
   public static final int DOCTYPE = 24;
   public static final int SYSTEM = 25;
   public static final int PUBLIC = 26;
   public static final int PEREFERENCE = 27;
   public static final int EMPTY = 28;
   public static final int ANY = 29;
   public static final int OPENPAREN = 30;
   public static final int CLOSEPAREN = 31;
   public static final int OPTION = 32;
   public static final int STAR = 33;
   public static final int PLUS = 34;
   public static final int OR = 35;
   public static final int AND = 36;
   public static final int PCDATA = 37;
   public static final int ELEMENTDECL = 38;
   public static final int DECLEND = 39;
   public static final int ATTLISTDECL = 40;
   public static final int ID = 41;
   public static final int IDREF = 42;
   public static final int IDREFS = 43;
   public static final int ENTITY = 44;
   public static final int ENTITIES = 45;
   public static final int NMTOKEN = 46;
   public static final int NMTOKENS = 47;
   public static final int NOTATION = 48;
   public static final int REQUIRED = 49;
   public static final int IMPLIED = 50;
   public static final int FIXED = 51;
   public static final int GEDECL = 52;
   public static final int PEDECL = 53;
   public static final int NDATA = 54;
   public static final int NOTATIONDECL = 55;
   public static final int OPENCHOICE = 56;
   public static final int OPENSEQ = 57;
   public static final int DOCTYPEEND = 58;
   public static final int OPENMIXED = 59;
   public static final int MAX = 60;
   static final String[] nameArray = new String[]{"NAME", "OPENTAGBEGIN", "TAGEND", "CHAR", "CLOSETAGBEGIN", "EMPTYTAGEND", "EQUALS", "COMMENT", "PIBEGIN", "PIEND", "CDBEGIN", "CDATA", "CDEND", "CHARDATA", "ENTITYREF", "CHARREF", "ATTVALUE", "PIDATA", "NAMEPREFIX", "SPACE", "UNDEFINED", "HEXADECIMAL", "DECIMAL", "XMLNS", "DOCTYPE", "SYSTEM", "PUBLIC", "PEREFERENCE", "EMPTY", "ANY", "OPENPAREN", "CLOSEPAREN", "OPTION", "STAR", "PLUS", "OR", "AND", "PCDATA", "ELEMENTDECL", "DECLEND", "ATTLISTDECL", "ID", "IDREF", "IDREFS", "ENTITY", "ENTITIES", "NMTOKEN", "NMTOKENS", "NOTATION", "REQUIRED", "IMPLIED", "FIXED", "GEDECL", "PEDECL", "NDATA", "NOTATIONDECL", "OPENCHOICE", "OPENSEQ", "DOCTYPEEND", "OPENMIXED"};

   public static final String getString(int type, int subType) {
      if (type == -1) {
         return "EOF";
      } else {
         return subType == 20 ? nameArray[type] : nameArray[type] + ":" + nameArray[subType];
      }
   }

   public static final String getString(int tok) {
      return tok == -1 ? "EOF" : nameArray[tok];
   }

   Token(int tokenType) {
      this.tokenType = tokenType;
      this.subType = 20;
      this.text = null;
      this.start = 0;
      this.length = 0;
   }

   Token(int tokenType, String text) {
      this.tokenType = tokenType;
      this.subType = 20;
      this.text = text;
      this.start = 0;
      this.length = 0;
   }

   Token(int tokenType, char[] ch, int start, int length) {
      this.tokenType = tokenType;
      this.subType = 20;
      this.textArray = ch;
      this.start = start;
      this.length = length;
      this.text = null;
   }

   public String getArrayAsString() {
      return this.text != null ? this.text : new String(this.textArray, this.start, this.length);
   }

   public boolean isEOF() {
      return this.tokenType == -1;
   }

   public boolean isEODTD() {
      return this.tokenType == 58;
   }

   public String toString() {
      try {
         if (this.text != null) {
            return "[" + getString(this.tokenType, this.subType) + "]\t(" + this.text + ")";
         } else {
            return this.length > 0 ? "[" + getString(this.tokenType, this.subType) + "]\t(" + new String(this.textArray, this.start, this.length) + ")" : "[" + getString(this.tokenType, this.subType) + "]";
         }
      } catch (Exception var2) {
         return "[" + getString(this.tokenType, this.subType) + "]";
      }
   }

   public Token duplicate() {
      Token token = new Token(this.tokenType, this.text);
      token.subType = this.subType;
      if (this.length != 0) {
         token.textArray = new char[this.length];

         for(int i = 0; i < this.length; ++i) {
            token.textArray[i] = this.textArray[this.start + i];
         }

         token.length = this.length;
         token.start = 0;
      }

      return token;
   }

   public boolean compareType(Token token) {
      return this.tokenType == token.tokenType && this.subType == token.subType;
   }

   public boolean compareType(int type) {
      return this.tokenType == type;
   }
}
