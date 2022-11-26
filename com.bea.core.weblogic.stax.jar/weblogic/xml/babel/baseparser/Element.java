package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public abstract class Element {
   protected String prefix;
   protected String name;
   protected String uri;
   protected int line = 0;
   protected int column = 0;
   public int type;
   public static final int STAG = 0;
   public static final int EMPTYELEMTAG = 1;
   public static final int ELEMENT = 2;
   public static final int ETAG = 3;
   public static final int PI = 4;
   public static final int CHARDATA = 5;
   public static final int COMMENT = 6;
   public static final int SPACE = 7;
   public static final int NULLELEMENT = 8;
   public static final int EOF = 9;
   static final String[] nameArray = new String[]{"STAG", "EMPTYELEMTAG", "ELEMENT", "ETAG", "PI", "CHARDATA", "COMMENT", "SPACE", "NULLELEMENT", "EOF"};

   public abstract void parse(BaseParser var1) throws IOException, ScannerException, ParseException;

   public static final String getString(int name) {
      return nameArray[name];
   }

   public String getName() {
      if (this.uri != null && this.prefix != null) {
         return "['" + this.uri + "']:" + this.prefix + ":" + this.name;
      } else if (this.uri != null) {
         return "['" + this.uri + "']:" + this.name;
      } else {
         return this.prefix != null ? this.prefix + ":" + this.name : this.name;
      }
   }

   public String getRawName() {
      return this.prefix != null ? this.prefix + ":" + this.name : this.name;
   }

   public String getNSName() {
      return this.uri != null ? this.uri + ":" + this.name : this.name;
   }

   protected void init() {
      this.uri = null;
      this.prefix = null;
      this.name = null;
      this.type = 2;
   }

   protected boolean setNameSpace(BaseParser parser) throws IOException, ScannerException, ParseException {
      if (this.prefix != null) {
         this.uri = parser.getNameSpace(this.prefix);
         if (this.uri == null) {
            throw new ParseException("Prefix [" + this.prefix + "] used without binding it to a namespace URI");
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected boolean setDefaultNameSpace(BaseParser parser) throws IOException, ScannerException, ParseException {
      if (parser.isDefaultNameSpaceSet()) {
         this.uri = parser.getDefaultNameSpace();
         return true;
      } else {
         return false;
      }
   }

   public void setLocalName(String n) {
      this.name = n;
   }

   public void setPrefix(String p) {
      this.prefix = p;
   }

   public void setURI(String u) {
      this.uri = u;
   }

   public String getLocalName() {
      return this.name;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getURI() {
      return this.uri;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public void setColumn(int column) {
      this.column = column;
   }

   public void setPosition(BaseParser parser) {
      this.line = parser.getLine();
      this.column = parser.getColumn();
   }
}
