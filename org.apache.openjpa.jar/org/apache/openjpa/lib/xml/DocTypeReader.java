package org.apache.openjpa.lib.xml;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class DocTypeReader extends Reader {
   private Reader _xml = null;
   private Reader _docType = null;
   private char[] _header = null;
   private int _headerPos = 0;

   public DocTypeReader(Reader xml, Reader docType) throws IOException {
      this._docType = docType;
      this._xml = this.bufferHeader(xml);
   }

   public int read() throws IOException {
      int ch = this.readHeader();
      if (ch != -1) {
         return ch;
      } else {
         ch = this.readDocType();
         return ch != -1 ? ch : this._xml.read();
      }
   }

   public int read(char[] buf) throws IOException {
      return this.read(buf, 0, buf.length);
   }

   public int read(char[] buf, int off, int len) throws IOException {
      int headerRead = this.readHeader(buf, off, len);
      off += headerRead;
      len -= headerRead;
      int docRead = this.readDocType(buf, off, len);
      off += docRead;
      len -= docRead;
      return headerRead + docRead + this._xml.read(buf, off, len);
   }

   public long skip(long len) throws IOException {
      return this._xml.skip(len);
   }

   public boolean ready() throws IOException {
      return this._xml.ready();
   }

   public boolean markSupported() {
      return this._xml.markSupported();
   }

   public void mark(int readAheadLimit) throws IOException {
      this._xml.mark(readAheadLimit);
   }

   public void reset() throws IOException {
      this._xml.reset();
   }

   public void close() throws IOException {
      this._xml.close();
      if (this._docType != null) {
         this._docType.close();
      }

   }

   private Reader bufferHeader(Reader origXML) throws IOException {
      if (this._docType == null) {
         this._header = new char[0];
         return origXML;
      } else {
         StringWriter writer = new StringWriter();
         PushbackReader xml = new PushbackReader(origXML, 3);

         while(true) {
            int ch;
            for(ch = xml.read(); ch != -1 && Character.isWhitespace((char)ch); ch = xml.read()) {
               writer.write(ch);
            }

            if (ch == -1) {
               return this.headerOnly(writer.toString());
            }

            if (ch != 60) {
               xml.unread(ch);
               this._header = writer.toString().toCharArray();
               return xml;
            }

            ch = xml.read();
            if (ch != 63 && ch != 33) {
               xml.unread(ch);
               xml.unread(60);
               this._header = writer.toString().toCharArray();
               return xml;
            }

            int ch2 = xml.read();
            if (ch == 33 && ch2 == 68) {
               xml.unread(ch2);
               xml.unread(ch);
               xml.unread(60);
               this._header = writer.toString().toCharArray();
               this._docType = null;
               return xml;
            }

            int ch3 = xml.read();
            boolean comment = ch == 33 && ch2 == 45 && ch3 == 45;
            writer.write(60);
            writer.write(ch);
            writer.write(ch2);
            writer.write(ch3);
            ch2 = 0;

            for(ch3 = 0; (ch = xml.read()) != -1; ch2 = ch) {
               writer.write(ch);
               if (!comment && ch == 62 || comment && ch == 62 && ch2 == 45 && ch3 == 45) {
                  break;
               }

               ch3 = ch2;
            }

            if (ch == -1) {
               return this.headerOnly(writer.toString());
            }

            for(ch = xml.read(); ch != -1 && Character.isWhitespace((char)ch); ch = xml.read()) {
               writer.write(ch);
            }

            if (ch == -1) {
               return this.headerOnly(writer.toString());
            }

            xml.unread(ch);
         }
      }
   }

   private Reader headerOnly(String header) {
      this._header = new char[0];
      this._docType = null;
      return new StringReader(header);
   }

   private int readHeader() {
      return this._headerPos == this._header.length ? -1 : this._header[this._headerPos++];
   }

   private int readHeader(char[] buf, int off, int len) {
      int read;
      for(read = 0; len > 0 && this._headerPos < this._header.length; --len) {
         buf[off] = this._header[this._headerPos++];
         ++read;
         ++off;
      }

      return read;
   }

   private int readDocType() throws IOException {
      if (this._docType == null) {
         return -1;
      } else {
         int ch = this._docType.read();
         if (ch == -1) {
            this._docType = null;
         }

         return ch;
      }
   }

   private int readDocType(char[] buf, int off, int len) throws IOException {
      if (this._docType == null) {
         return 0;
      } else {
         int read = this._docType.read(buf, off, len);
         if (read < len) {
            this._docType = null;
         }

         if (read == -1) {
            read = 0;
         }

         return read;
      }
   }
}
