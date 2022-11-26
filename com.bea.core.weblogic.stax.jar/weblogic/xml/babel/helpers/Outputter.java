package weblogic.xml.babel.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class Outputter implements ContentHandler, DTDHandler {
   private boolean isValidating;
   private String compareURI;
   private StringBuffer utf16 = new StringBuffer();
   private ByteArrayOutputStream utf8 = new ByteArrayOutputStream();
   private Sorter notations;
   private Sorter entities;

   public Outputter(boolean validating, String uri) {
      this.isValidating = validating;
      this.compareURI = uri;
   }

   public void getOutputXML(String outputFileName) throws IOException, FileNotFoundException {
      File file = new File(outputFileName);
      FileOutputStream out = new FileOutputStream(file);
      this.utf8.writeTo(out);
      this.utf8.reset();
   }

   public String getDiagnostic(boolean debug) {
      byte[] actual = this.utf8.toByteArray();
      String retval = null;

      byte[] canonical;
      try {
         URL uri = new URL(this.compareURI);
         InputStream in = uri.openStream();
         byte[] buf = new byte[1024];
         if (debug) {
            File temp = File.createTempFile("Outputter", "xml");
            FileOutputStream actualOut = new FileOutputStream(temp);
            this.utf8.writeTo(actualOut);
            System.out.println("UTF8:" + this.utf8 + ":END");
         }

         this.utf8.reset();

         while(true) {
            int len;
            if ((len = in.read(buf)) < 0) {
               in.close();
               canonical = this.utf8.toByteArray();
               if (debug) {
                  System.out.println("CANO:" + canonical + ":END");
               }
               break;
            }

            this.utf8.write(buf, 0, len);
         }
      } catch (Exception var11) {
         return "[I/O problem checking output: " + var11.getMessage() + "]";
      }

      for(int i = 0; retval == null && i < canonical.length; ++i) {
         if (i >= actual.length) {
            retval = "Output ends at byte " + i + " but should continue to byte " + canonical.length;
         } else if (actual[i] != canonical[i]) {
            retval = "Output byte " + i + " has the wrong value; actual 0x" + Integer.toHexString(255 & actual[i]) + " should instead be 0x" + Integer.toHexString(255 & canonical[i]);
         }
      }

      if (retval == null && actual.length != canonical.length) {
         retval = "Too much output; length is " + actual.length + " but should only be " + canonical.length;
      }

      return retval;
   }

   private void writeUTF8(char[] buf, int offset, int len) throws IOException {
      for(int i = 0; i < len; ++i) {
         char c = buf[offset + i];
         if (c < 128) {
            this.utf8.write(c);
         } else {
            switch (c & 63488) {
               case 0:
                  this.utf8.write(c >> 6 & 31 | 192);
                  this.utf8.write(c & 63 | 128);
                  break;
               case 55296:
                  if (i + 1 < len) {
                     char c2 = buf[offset + i + 1];
                     if ((c & 'ﰀ') == 55296 && (c2 & 'ﰀ') == 56320) {
                        ++i;
                        int n = (c & 1023) << 10 | c2 & 1023;
                        n += 65536;
                        this.utf8.write(n >> 18 & 7 | 240);
                        this.utf8.write(n >> 12 & 63 | 128);
                        this.utf8.write(n >> 6 & 63 | 128);
                        this.utf8.write(n & 63 | 128);
                        break;
                     }
                  }
               default:
                  this.utf8.write(c >> 12 & 15 | 224);
                  this.utf8.write(c >> 6 & 63 | 128);
                  this.utf8.write(c & 63 | 128);
            }
         }
      }

   }

   private void escapeUTF16(char c) {
      switch (c) {
         case '\t':
            this.utf16.append("&#9;");
            break;
         case '\n':
            this.utf16.append("&#10;");
            break;
         case '\r':
            this.utf16.append("&#13;");
            break;
         case '"':
            this.utf16.append("&quot;");
            break;
         case '&':
            this.utf16.append("&amp;");
            break;
         case '<':
            this.utf16.append("&lt;");
            break;
         case '>':
            this.utf16.append("&gt;");
            break;
         default:
            this.utf16.append(c);
      }

   }

   public void escapeUTF16(char[] buf, int off, int len) {
      while(len-- > 0) {
         this.escapeUTF16(buf[off++]);
      }

   }

   private void escapeUTF16(String s) {
      char[] buf = s.toCharArray();
      this.escapeUTF16(buf, 0, buf.length);
   }

   private void writeUTF16(String s) {
      this.utf16.append(s);
   }

   private void flushUTF16() throws SAXException {
      try {
         char[] buf = this.utf16.toString().toCharArray();
         this.utf16.setLength(0);
         this.writeUTF8(buf, 0, buf.length);
      } catch (IOException var2) {
         throw new SAXException("I/O error", var2);
      }
   }

   public void setDocumentLocator(Locator l) {
   }

   public void startDocument() throws SAXException {
   }

   public void processingInstruction(String target, String params) throws SAXException {
      this.writeUTF16("<?");
      this.writeUTF16(target);
      this.writeUTF16(" ");
      this.writeUTF16(params);
      this.writeUTF16("?>");
      this.flushUTF16();
   }

   public void notationDecl(String name, String publicID, String systemID) throws SAXException {
      String decl = "<!NOTATION " + name;
      if (publicID != null) {
         decl = decl + " PUBLIC '" + publicID;
         if (systemID != null) {
            decl = decl + "' '" + systemID;
         }
      } else {
         decl = decl + " SYSTEM '" + systemID;
      }

      decl = decl + "'>\n";
      if (this.notations == null) {
         this.notations = new Sorter();
      }

      this.notations.put(name, decl);
   }

   public void unparsedEntityDecl(String name, String publicID, String systemID, String notation) throws SAXException {
      if (this.isValidating) {
         String decl = "<!ENTITY " + name;
         if (publicID != null) {
            decl = decl + "PUBLIC '" + publicID + "' '";
         } else {
            decl = decl + "SYSTEM '";
         }

         decl = decl + systemID + "' NDATA " + notation + ">\n";
         if (this.entities == null) {
            this.entities = new Sorter();
         }

         this.entities.put(name, decl);
      }
   }

   public void startPrefixMapping(String prefix, String uri) {
   }

   public void endPrefixMapping(String prefix) {
   }

   public void skippedEntity(String name) {
   }

   public void startElement(String namespace, String local, String name, Attributes attrs) throws SAXException {
      if (this.notations != null) {
         this.writeUTF16("<!DOCTYPE ");
         this.writeUTF16(name);
         this.writeUTF16(" [\n");
         this.flushUTF16();
         Enumeration iter = this.notations.elements();

         while(iter.hasMoreElements()) {
            this.writeUTF16((String)iter.nextElement());
         }

         this.flushUTF16();
         if (this.entities != null) {
            iter = this.entities.elements();

            while(iter.hasMoreElements()) {
               this.writeUTF16((String)iter.nextElement());
            }
         }

         this.writeUTF16("]>\n");
         this.flushUTF16();
         this.notations = null;
         this.entities = null;
      }

      this.writeUTF16("<");
      this.writeUTF16(name);
      if (attrs != null && attrs.getLength() != 0) {
         int len = attrs.getLength();
         int[] v = new int[len];

         int i;
         for(i = 0; i < len; v[i] = i++) {
         }

         for(i = 1; i < len; ++i) {
            int n = v[i];
            String s = attrs.getQName(n);

            int j;
            for(j = i - 1; j >= 0 && s.compareTo(attrs.getQName(v[j])) < 0; --j) {
               v[j + 1] = v[j];
            }

            v[j + 1] = n;
         }

         for(i = 0; i < len; ++i) {
            this.writeUTF16(" ");
            this.writeUTF16(attrs.getQName(v[i]));
            this.writeUTF16("=\"");
            this.escapeUTF16(attrs.getValue(v[i]));
            this.writeUTF16("\"");
            this.flushUTF16();
         }
      }

      this.writeUTF16(">");
      this.flushUTF16();
   }

   public void endElement(String namespace, String local, String name) throws SAXException {
      this.writeUTF16("</");
      this.writeUTF16(name);
      this.writeUTF16(">");
      this.flushUTF16();
   }

   public void characters(char[] buf, int offset, int len) throws SAXException {
      this.escapeUTF16(buf, offset, len);
      this.flushUTF16();
   }

   public void ignorableWhitespace(char[] buf, int offset, int len) throws SAXException {
      if (!this.isValidating) {
         this.characters(buf, offset, len);
      }

   }

   public void endDocument() throws SAXException {
   }
}
