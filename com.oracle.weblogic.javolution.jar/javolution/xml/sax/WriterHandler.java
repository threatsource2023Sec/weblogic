package javolution.xml.sax;

import java.io.IOException;
import java.io.Writer;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.util.FastTable;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class WriterHandler implements ContentHandler, Reusable {
   private static final int BUFFER_LENGTH = 2048;
   private Writer _writer;
   private CharSequence _prolog;
   private CharSequence _indent;
   private FastTable _prefixMappings;
   private int _nesting;
   private boolean _isTagOpen;
   private final char[] _buffer;
   private int _index;

   public WriterHandler() {
      this._prolog = Text.EMPTY;
      this._indent = Text.EMPTY;
      this._prefixMappings = new FastTable();
      this._nesting = -1;
      this._buffer = new char[2048];
   }

   public WriterHandler setWriter(Writer var1) {
      this._writer = var1;
      return this;
   }

   public void setIndent(CharSequence var1) {
      this._indent = var1;
   }

   public void setProlog(CharSequence var1) {
      this._prolog = var1;
   }

   public void reset() {
      this._writer = null;
      this._indent = Text.EMPTY;
      this._prolog = Text.EMPTY;
      this._prefixMappings.clear();
      this._nesting = -1;
      this._isTagOpen = false;
      this._index = 0;
   }

   public void setDocumentLocator(Locator var1) {
   }

   public void startDocument() throws SAXException {
      if (this._writer == null) {
         throw new SAXException("Writer not set");
      } else {
         try {
            this.writeNoEscape((Object)this._prolog);
         } catch (IOException var2) {
            throw new SAXException(var2);
         }
      }
   }

   public void endDocument() throws SAXException {
      try {
         this.flushBuffer();
         this._writer.close();
      } catch (IOException var2) {
         throw new SAXException(var2);
      }
   }

   public void startPrefixMapping(CharSequence var1, CharSequence var2) throws SAXException {
      this._prefixMappings.addLast(var1);
      this._prefixMappings.addLast(var2);
   }

   public void endPrefixMapping(CharSequence var1) throws SAXException {
   }

   public void startElement(CharSequence var1, CharSequence var2, CharSequence var3, Attributes var4) throws SAXException {
      try {
         if (this._isTagOpen) {
            this.writeNoEscape(">\n");
            this._isTagOpen = false;
         }

         ++this._nesting;
         this.indent();
         this.writeNoEscape('<');
         this.writeNoEscape((Object)var3);
         if (this._prefixMappings.size() > 0) {
            this.writeNamespaces();
         }

         int var5 = var4.getLength();

         for(int var6 = 0; var6 < var5; ++var6) {
            CharSequence var7 = var4.getQName(var6);
            CharSequence var8 = var4.getValue(var6);
            this.writeNoEscape(' ');
            this.writeNoEscape((Object)var7);
            this.writeNoEscape('=');
            this.writeNoEscape('"');
            this.write((Object)var8);
            this.writeNoEscape('"');
         }

         this._isTagOpen = true;
      } catch (IOException var9) {
         throw new SAXException(var9);
      }
   }

   private void indent() throws IOException {
      int var1 = this._indent.length();
      if (var1 > 0) {
         for(int var2 = 0; var2 < this._nesting; ++var2) {
            this.writeNoEscape((Object)this._indent);
         }
      }

   }

   private void writeNamespaces() throws IOException {
      int var1 = 0;
      int var2 = this._prefixMappings.size();

      while(var1 < var2) {
         CharSequence var3 = (CharSequence)this._prefixMappings.get(var1++);
         CharSequence var4 = (CharSequence)this._prefixMappings.get(var1++);
         if (var3.length() == 0) {
            this.writeNoEscape(" xmlns=\"");
            this.write((Object)var4);
            this.writeNoEscape('"');
         } else {
            this.writeNoEscape(" xmlns:");
            this.writeNoEscape((Object)var3);
            this.writeNoEscape('=');
            this.writeNoEscape('"');
            this.write((Object)var4);
            this.writeNoEscape('"');
         }
      }

      this._prefixMappings.clear();
   }

   public void endElement(CharSequence var1, CharSequence var2, CharSequence var3) throws SAXException {
      try {
         if (this._isTagOpen) {
            this.writeNoEscape("/>\n");
            this._isTagOpen = false;
         } else {
            this.indent();
            this.writeNoEscape('<');
            this.writeNoEscape('/');
            this.writeNoEscape((Object)var3);
            this.writeNoEscape('>');
            this.writeNoEscape('\n');
         }

         --this._nesting;
      } catch (IOException var5) {
         throw new SAXException(var5);
      }
   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      try {
         if (this._isTagOpen) {
            this.writeNoEscape('>');
            this._isTagOpen = false;
         }

         this.writeNoEscape("<![CDATA[");
         this.flushBuffer();
         this._writer.write(var1, var2, var3);
         this.writeNoEscape("]]>\n");
      } catch (IOException var5) {
         throw new SAXException(var5);
      }
   }

   public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
   }

   public void processingInstruction(CharSequence var1, CharSequence var2) throws SAXException {
   }

   public void skippedEntity(CharSequence var1) throws SAXException {
   }

   private void write(Object var1) throws IOException {
      if (var1 instanceof String) {
         this.write((String)var1);
      } else {
         this.writeNonString((CharSequence)var1);
      }

   }

   private void write(String var1) throws IOException {
      int var2 = var1.length();
      int var3 = 0;

      while(true) {
         while(var3 < var2) {
            char var4 = var1.charAt(var3++);
            if (var4 < '@' && var4 != ' ') {
               switch (var4) {
                  case '"':
                     this.writeNoEscape("&quot;");
                     break;
                  case '&':
                     this.writeNoEscape("&amp;");
                     break;
                  case '\'':
                     this.writeNoEscape("&apos;");
                     break;
                  case '<':
                     this.writeNoEscape("&lt;");
                     break;
                  case '>':
                     this.writeNoEscape("&gt;");
                     break;
                  default:
                     if (var4 >= ' ') {
                        this.writeNoEscape(var4);
                     } else {
                        this.writeNoEscape("&#");
                        this.writeNoEscape((char)(48 + var4 / 10));
                        this.writeNoEscape((char)(48 + var4 % 10));
                        this.writeNoEscape(';');
                     }
               }
            } else {
               this._buffer[this._index] = var4;
               if (++this._index == 2048) {
                  this.flushBuffer();
               }
            }
         }

         return;
      }
   }

   private void writeNonString(CharSequence var1) throws IOException {
      int var2 = var1.length();
      int var3 = 0;

      while(true) {
         while(var3 < var2) {
            char var4 = var1.charAt(var3++);
            if (var4 < '@' && var4 != ' ') {
               switch (var4) {
                  case '"':
                     this.writeNoEscape("&quot;");
                     break;
                  case '&':
                     this.writeNoEscape("&amp;");
                     break;
                  case '\'':
                     this.writeNoEscape("&apos;");
                     break;
                  case '<':
                     this.writeNoEscape("&lt;");
                     break;
                  case '>':
                     this.writeNoEscape("&gt;");
                     break;
                  default:
                     if (var4 >= ' ') {
                        this.writeNoEscape(var4);
                     } else {
                        this.writeNoEscape("&#");
                        this.writeNoEscape((char)(48 + var4 / 10));
                        this.writeNoEscape((char)(48 + var4 % 10));
                        this.writeNoEscape(';');
                     }
               }
            } else {
               this._buffer[this._index] = var4;
               if (++this._index == 2048) {
                  this.flushBuffer();
               }
            }
         }

         return;
      }
   }

   private void writeNoEscape(Object var1) throws IOException {
      if (var1 instanceof String) {
         this.writeNoEscape((String)var1);
      } else {
         this.writeNoEscapeNonString((CharSequence)var1);
      }

   }

   private void writeNoEscape(String var1) throws IOException {
      int var2 = 0;
      int var3 = var1.length();

      while(var2 < var3) {
         this._buffer[this._index] = var1.charAt(var2++);
         if (++this._index == 2048) {
            this.flushBuffer();
         }
      }

   }

   private void writeNoEscapeNonString(CharSequence var1) throws IOException {
      int var2 = 0;
      int var3 = var1.length();

      while(var2 < var3) {
         this._buffer[this._index] = var1.charAt(var2++);
         if (++this._index == 2048) {
            this.flushBuffer();
         }
      }

   }

   private final void writeNoEscape(char var1) throws IOException {
      this._buffer[this._index] = var1;
      if (++this._index == 2048) {
         this.flushBuffer();
      }

   }

   private void flushBuffer() throws IOException {
      this._writer.write(this._buffer, 0, this._index);
      this._index = 0;
   }
}
