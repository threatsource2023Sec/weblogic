package weblogic.xml.dtdc;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Locale;
import org.xml.sax.AttributeList;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class BaseParser implements Parser, AttributeList, Locator {
   protected DocumentHandler dh;
   protected int currentLine = 1;
   protected int lastLinePosition = 0;
   protected int current = 0;
   protected int end = 0;
   protected int startCharacterData = -1;
   protected Hashtable ids = new Hashtable();
   protected String[] names;
   protected String[] types;
   protected String[] values;
   protected int numAttributes = 0;
   protected Hashtable htypes = new Hashtable();
   protected Hashtable hvalues = new Hashtable();

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[ " + this.getClass().getName() + ", currentLine=" + this.currentLine + ", currentColumn=" + (this.current - this.lastLinePosition) + ", attributes=[");

      for(int i = 0; i < this.numAttributes; ++i) {
         sb.append(this.names[i] + "=(" + this.types[i] + ")" + this.values[i]);
         if (i != this.numAttributes - 1) {
            sb.append(", ");
         }
      }

      sb.append("]]");
      return sb.toString();
   }

   public int getLineNumber() {
      return this.currentLine;
   }

   public int getColumnNumber() {
      return this.current - this.lastLinePosition;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return null;
   }

   public void setLocale(Locale locale) {
      if (!Locale.getDefault().equals(locale)) {
         throw new Error("Operation unsupported, locale cannot differ from the default");
      }
   }

   public void setEntityResolver(EntityResolver er) {
   }

   public void setDTDHandler(DTDHandler dh) {
   }

   public void setErrorHandler(ErrorHandler eh) {
   }

   public void setDocumentHandler(DocumentHandler dh) {
      this.dh = dh;
   }

   public void parse(String string) throws IOException, SAXException {
      this.parse(new File(string));
   }

   public void parse(InputSource is) throws IOException, SAXException {
      CharArrayWriter caw = new CharArrayWriter();
      BufferedReader br = getBufferedReader(is);

      try {
         char[] buffer = new char[2048];

         int read;
         while((read = br.read(buffer)) != -1) {
            caw.write(buffer, 0, read);
         }

         this.parse(caw.toCharArray());
      } finally {
         br.close();
      }
   }

   public abstract void parse(char[] var1) throws SAXException;

   public void parse(File file) throws IOException, SAXException {
      FileReader fis = new FileReader(file);

      try {
         char[] chars = new char[(int)file.length()];
         fis.read(chars, 0, chars.length);
         this.parse(chars);
      } finally {
         fis.close();
      }

   }

   public void parse(InputStream in) throws IOException, SAXException {
      CharArrayWriter caw = new CharArrayWriter();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));

      try {
         char[] buffer = new char[2048];

         int read;
         while((read = br.read(buffer)) != -1) {
            caw.write(buffer, 0, read);
         }

         this.parse(caw.toCharArray());
      } finally {
         br.close();
      }
   }

   public void parse(InputStream in, String encodingType) throws IOException, SAXException {
      CharArrayWriter caw = new CharArrayWriter();
      BufferedReader br = new BufferedReader(new InputStreamReader(in, encodingType));

      try {
         char[] buffer = new char[2048];

         int read;
         while((read = br.read(buffer)) != -1) {
            caw.write(buffer, 0, read);
         }

         this.parse(caw.toCharArray());
      } finally {
         br.close();
      }
   }

   protected final void nextLine() {
      ++this.currentLine;
      this.lastLinePosition = this.current;
   }

   protected final void eatComment(char[] chars) throws SAXException {
      this.sendCharacters(chars, 4);

      while(chars[this.current++] != '-' || chars[this.current] != '-' || chars[this.current + 1] != '>') {
         if (chars[this.current] == '\n') {
            this.nextLine();
         }
      }

      this.current += 2;
      this.startCharacterData = -1;
   }

   protected final void sendCharacters(char[] chars, int length) throws SAXException {
      if (this.startCharacterData != -1) {
         this.dh.characters(chars, this.startCharacterData, this.current - length - this.startCharacterData);
         this.startCharacterData = -1;
      }

   }

   protected final void match(char[] chars, String string, String name, int startLine) throws SAXParseException {
      for(int index = string.length() - 1; index >= 0; --index) {
         if (chars[this.current + index] != string.charAt(index)) {
            throw new SAXParseException("Could not parse " + name + ", starting at line " + startLine, this);
         }
      }

      this.current += string.length();
   }

   protected void reset() {
      for(int i = this.numAttributes - 1; i >= 0; --i) {
         this.names[i] = null;
         this.types[i] = null;
         this.values[i] = null;
      }

      this.numAttributes = 0;
      this.htypes.clear();
      this.hvalues.clear();
   }

   public int getLength() {
      return this.numAttributes;
   }

   public String getName(int n) {
      return this.names[n];
   }

   public String getType(int n) {
      return this.types[n];
   }

   public String getValue(int n) {
      return this.values[n];
   }

   public String getType(String name) {
      return (String)this.htypes.get(name);
   }

   public String getValue(String name) {
      return (String)this.hvalues.get(name);
   }

   public String _readAttribute(char[] chars) throws SAXException {
      StringBuffer sb = null;
      ++this.current;

      char quote;
      do {
         do {
            quote = chars[this.current++];
         } while(quote == ' ');
      } while(quote == '\t' || quote == '\n' || quote == '\r');

      if (quote != '"' && quote != '\'') {
         throw new SAXParseException("Invalid attribute", this);
      } else {
         int start = this.current;

         while(true) {
            char c;
            char[] stringBytes;
            while((c = chars[this.current++]) != quote) {
               switch (c) {
                  case '&':
                     stringBytes = new char[this.current - start];
                     int tempCurrent = this.current;
                     this.handleEscapes(chars);
                     if (sb == null) {
                        sb = new StringBuffer();
                     }

                     System.arraycopy(chars, start, stringBytes, 0, tempCurrent - start);
                     sb.append(new String(stringBytes));
                     start = this.current;
                     break;
                  case '<':
                     if (chars[this.current] != '!' || chars[this.current + 1] != '[' || chars[this.current + 2] != 'C' || chars[this.current + 3] != 'D' || chars[this.current + 4] != 'A' || chars[this.current + 5] != 'T' || chars[this.current + 6] != 'A' || chars[this.current + 7] != '[') {
                        throw new SAXParseException("Illegal character in input: <", this);
                     }

                     stringBytes = new char[this.current - start - 1];
                     if (sb == null) {
                        sb = new StringBuffer();
                     }

                     System.arraycopy(chars, start, stringBytes, 0, this.current - start - 1);
                     sb.append(new String(stringBytes));
                     this.current += 8;
                     start = this.current;
                     int end = false;

                     label88:
                     do {
                        while(true) {
                           switch (chars[this.current++]) {
                              case ']':
                                 continue label88;
                           }
                        }
                     } while(chars[this.current] != ']' || chars[this.current + 1] != '>');

                     this.current += 2;
                     int end = this.current - 3;
                     stringBytes = new char[end - start];
                     System.arraycopy(chars, start, stringBytes, 0, end - start);
                     sb.append(new String(stringBytes));
                     start = this.current;
               }
            }

            stringBytes = new char[this.current - start - 1];
            System.arraycopy(chars, start, stringBytes, 0, this.current - start - 1);
            String value = new String(stringBytes);
            if (sb != null) {
               return sb.append(value).toString();
            }

            return value;
         }
      }
   }

   protected void putAttribute(String name, String type, String value) {
      this.hvalues.put(name, value);
      this.names[this.numAttributes] = name;
      this.types[this.numAttributes] = type;
      this.values[this.numAttributes++] = value;
   }

   protected void handleEscapes(char[] chars) throws SAXException {
      int tempCurrent;
      tempCurrent = this.current;
      label56:
      switch (chars[this.current]) {
         case 'a':
            switch (chars[this.current + 1]) {
               case 'm':
                  if (chars[this.current + 2] == 'p' && chars[this.current + 3] == ';') {
                     this.compress(chars, '&', 4);
                     break label56;
                  }

                  throw new SAXParseException("Invalid character sequence", this);
               case 'p':
                  if (chars[this.current + 2] != 'o' || chars[this.current + 3] != 's' || chars[this.current + 4] != ';') {
                     throw new SAXParseException("Invalid character sequence", this);
                  }

                  this.compress(chars, '\'', 5);
               default:
                  break label56;
            }
         case 'g':
            if (chars[this.current + 1] == 't' && chars[this.current + 2] == ';') {
               this.compress(chars, '>', 3);
               break;
            }

            throw new SAXParseException("Invalid character sequence", this);
         case 'l':
            if (chars[this.current + 1] == 't' && chars[this.current + 2] == ';') {
               this.compress(chars, '<', 3);
               break;
            }

            throw new SAXParseException("Invalid character sequence", this);
         case 'q':
            if (chars[this.current + 1] != 'u' || chars[this.current + 2] != 'o' || chars[this.current + 3] != 't' || chars[this.current + 4] != ';') {
               throw new SAXParseException("Invalid character sequence", this);
            }

            this.compress(chars, '"', 5);
      }

      if (this.startCharacterData != -1) {
         this.dh.characters(chars, this.startCharacterData, tempCurrent - this.startCharacterData);
         this.startCharacterData = this.current;
      }

   }

   protected void handleCDATA(char[] chars) throws SAXException {
      if (this.startCharacterData != -1) {
         this.dh.characters(chars, this.startCharacterData, this.current - 9 - this.startCharacterData);
      }

      int start = this.current;

      label23:
      do {
         while(true) {
            switch (chars[this.current++]) {
               case ']':
                  continue label23;
            }
         }
      } while(chars[this.current] != ']' || chars[this.current + 1] != '>');

      char[] stringBytes = new char[this.current - start - 1];
      System.arraycopy(chars, start, stringBytes, 0, this.current - start - 1);
      this.current += 2;
      this.dh.characters(chars, start, this.current - 3 - start);
      this.startCharacterData = this.current;
   }

   private void compress(char[] chars, char replace, int num) {
      chars[this.current - 1] = replace;
      this.current += num;
   }

   private static BufferedReader getBufferedReader(InputSource source) {
      Reader charStream = source.getCharacterStream();
      if (charStream == null) {
         String systemId = source.getSystemId();

         try {
            charStream = new FileReader(systemId);
         } catch (FileNotFoundException var6) {
            try {
               URL url = new URL(source.getSystemId());
               charStream = new InputStreamReader(url.openStream());
            } catch (Exception var5) {
            }
         }
      }

      return charStream == null ? null : new BufferedReader((Reader)charStream, 1000);
   }
}
