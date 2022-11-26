package weblogic.xml.util;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.net.URL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.process.XMLParsingException;

public final class DocumentType implements XMLConstants {
   private static final boolean debug = System.getProperty("weblogic.xml.debug") != null;
   private static final String DOCTYPE_DECL_START = "<!DOCTYPE";
   private static final String[] ROOT_NAME_STOP = new String[]{"[", "\t", "\r", " ", "\n", ">"};
   private static final String[] ELEMENT_NAME_STOP = new String[]{"/", "\t", "\r", " ", "\n", ">"};
   private static final String[] EXTERNAL_ID_START = new String[]{"PUBLIC", "SYSTEM", ">"};
   private static final String[] QUOTES = new String[]{"\"", "'"};
   private static final String[] WHITE_SPACE = new String[]{"\t", "\r", " ", "\n"};
   private String rootTag;
   private String publicId;
   private String systemId;

   public DocumentType(String pubId, String sysId, String root) {
      this.publicId = pubId;
      this.systemId = sysId;
      this.rootTag = root;
   }

   public DocumentType(InputSource source, boolean reset) throws IOException, SAXException, XMLParsingException {
      this(getReader(source), reset);
   }

   public DocumentType(Reader xmlIn, boolean reset) throws IOException, SAXException, XMLParsingException {
      if (xmlIn == null) {
         throw new SAXException("Document input stream was null");
      } else if (reset && !xmlIn.markSupported()) {
         throw new XMLParsingException("Cannot read stream with reset=true because it does not support mark");
      } else {
         if (debug) {
            System.out.println("DocumentType: reset = " + reset);
         }

         int BYTE_LIMIT = true;
         if (reset) {
            xmlIn.mark(1000);
         }

         PushbackReader r = new PushbackReader(xmlIn, 1000);

         try {
            while(true) {
               if (debug) {
                  System.out.println("advancing to '<'");
               }

               String foo = ParsingUtils.read(r, "<", false);
               if (debug) {
                  System.out.println("\"" + foo + "\"");
               }

               if (startDocTypeDecl(r)) {
                  DocTypeDecl d = docTypeDecl(r);
                  this.rootTag = d.rootTag;
                  this.publicId = d.publicId;
                  this.systemId = d.systemId;
                  break;
               }

               if (startElementTag(r)) {
                  this.rootTag = elementTag(r);
                  this.systemId = retrieveSchemaSystemId(r);
                  break;
               }

               r.read();
               String nextChar = ParsingUtils.peek(r, 1);
               if ("?!/".indexOf(nextChar) < 0) {
                  throw new XMLParsingException("Cannot locate DOCTYPE header or root element");
               }
            }
         } catch (EOFException var8) {
            throw new XMLParsingException("Cannot locate DOCTYPE header or root element");
         }

         if (reset) {
            try {
               xmlIn.reset();
            } catch (IOException var7) {
               throw new IOException("Could not reset mark because read too far");
            }
         }

      }
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public String getRootElementTag() {
      return this.rootTag;
   }

   public boolean equals(Object obj) {
      DocumentType other = null;
      if (obj == null) {
         return false;
      } else {
         try {
            other = (DocumentType)obj;
         } catch (ClassCastException var4) {
            return false;
         }

         return nullableStringEquals(this.publicId, other.publicId) && nullableStringEquals(this.systemId, other.systemId) && nullableStringEquals(this.rootTag, other.rootTag);
      }
   }

   public int hashCode() {
      int hash = 0;
      if (this.publicId != null) {
         hash ^= this.publicId.hashCode();
      }

      if (this.systemId != null) {
         hash ^= this.systemId.hashCode();
      }

      if (this.rootTag != null) {
         hash ^= this.rootTag.hashCode();
      }

      return hash;
   }

   private static boolean nullableStringEquals(String s1, String s2) {
      if (s1 == null) {
         return s2 == null;
      } else {
         return s1.equals(s2);
      }
   }

   private static String readQuotedLiteral(PushbackReader r) throws IOException {
      if (debug) {
         System.out.println("reading quoted literal:");
      }

      if (debug) {
         System.out.println("reading through first quote");
      }

      String result = ParsingUtils.read(r, QUOTES, true);
      char quoteType = result.charAt(result.length() - 1);
      if (debug) {
         System.out.println("quote type is " + quoteType);
      }

      String quoteTypeStr = new String(new char[]{quoteType});
      if (debug) {
         System.out.println("reading until close quote");
      }

      String quotedText = ParsingUtils.read(r, quoteTypeStr, false);
      ParsingUtils.read(r, quoteTypeStr, true);
      return quotedText;
   }

   private static Reader getReader(InputSource source) throws XMLParsingException, SAXException {
      Reader charStream = source.getCharacterStream();
      if (charStream == null) {
         InputStream byteStream = source.getByteStream();
         if (byteStream != null) {
            charStream = new InputStreamReader(byteStream);
         } else {
            String systemId = source.getSystemId();

            try {
               if (debug) {
                  System.out.println("Trying to open file " + systemId);
               }

               if (systemId != null) {
                  charStream = new FileReader(systemId);
               }
            } catch (FileNotFoundException var7) {
               try {
                  if (debug) {
                     System.out.println("Not found. Trying to open URL " + systemId);
                  }

                  charStream = new InputStreamReader((new URL(systemId)).openStream());
               } catch (Exception var6) {
               }
            }
         }
      }

      if (charStream == null) {
         if (debug) {
            System.out.println("SystemID = " + source.getSystemId());
         }

         throw new SAXException("Could not open or read input source");
      } else {
         return (Reader)charStream;
      }
   }

   public static void dumpReader(Reader r) throws IOException {
      char[] buf = new char[1000];

      while(r.read(buf) > 0) {
         System.out.print(buf);
      }

      System.out.println("");
   }

   private static boolean startElementTag(PushbackReader r) throws IOException {
      String nextTwoChars = ParsingUtils.peek(r, 2);
      return nextTwoChars.charAt(0) == '<' && Character.isLetter(nextTwoChars.charAt(1));
   }

   private static boolean startDocTypeDecl(PushbackReader r) throws IOException {
      return "<!DOCTYPE".equals(ParsingUtils.peek(r, "<!DOCTYPE".length()));
   }

   private static boolean startExternalId(PushbackReader r) throws IOException {
      return !"[".equals(ParsingUtils.peek(r, 1));
   }

   private static ExternalId externalId(PushbackReader r) throws IOException {
      ExternalId result = new ExternalId();
      if (debug) {
         System.out.println("reading external id start");
      }

      String extIdDecl = ParsingUtils.read(r, EXTERNAL_ID_START, true);
      if (debug) {
         System.out.println("external id start = " + extIdDecl);
      }

      if (debug) {
         System.out.println("reading through WS");
      }

      ParsingUtils.readWS(r);
      if ("SYSTEM".equals(extIdDecl)) {
         result.systemId = readQuotedLiteral(r);
         if (debug) {
            System.out.println("reading system ID = \"" + result.systemId + "\"");
         }
      } else if ("PUBLIC".equals(extIdDecl)) {
         result.publicId = readQuotedLiteral(r);
         if (debug) {
            System.out.println("reading public ID = \"" + result.publicId + "\"");
         }

         if (debug) {
            System.out.println("reading through WS");
         }

         ParsingUtils.readWS(r);
         result.systemId = readQuotedLiteral(r);
         if (debug) {
            System.out.println("reading system ID = \"" + result.systemId + "\"");
         }
      }

      return result;
   }

   private static DocTypeDecl docTypeDecl(PushbackReader r) throws IOException {
      DocTypeDecl result = new DocTypeDecl();
      if (debug) {
         System.out.println("reading through <!DOCTYPE");
      }

      ParsingUtils.read(r, "<!DOCTYPE", true);
      if (debug) {
         System.out.println("reading through WS");
      }

      ParsingUtils.readWS(r);
      if (debug) {
         System.out.println("reading through root tag name");
      }

      result.rootTag = ParsingUtils.read(r, ROOT_NAME_STOP, false);
      ParsingUtils.readWS(r);
      if (debug) {
         System.out.println("read root tag name = " + result.rootTag);
      }

      if (startExternalId(r)) {
         ExternalId ext = externalId(r);
         result.publicId = ext.publicId;
         result.systemId = ext.systemId;
      }

      return result;
   }

   private static String elementTag(PushbackReader r) throws IOException {
      if (debug) {
         System.out.println("reading element tag start");
      }

      ParsingUtils.read(r, "<", true);
      String name = ParsingUtils.read(r, ELEMENT_NAME_STOP, false);
      return name;
   }

   private static String retrieveSchemaSystemId(PushbackReader r) throws IOException {
      String read = ParsingUtils.read(r, new String[]{"schemaLocation", ">"}, true);
      if (read != null && !"".equals(read) && read.charAt(read.length() - 1) == '>') {
         return null;
      } else {
         if (debug) {
            System.out.println("reading schemaLocation:");
         }

         if (debug) {
            System.out.println("reading through first quote");
         }

         String result = ParsingUtils.read(r, QUOTES, true);
         char quoteType = result.charAt(result.length() - 1);
         if (debug) {
            System.out.println("quote type is " + quoteType);
         }

         String quoteTypeStr = new String(new char[]{quoteType});
         if (debug) {
            System.out.println("reading until close quote or white space");
         }

         String[] stops = new String[WHITE_SPACE.length + 1];
         System.arraycopy(WHITE_SPACE, 0, stops, 0, WHITE_SPACE.length);
         stops[stops.length - 1] = quoteTypeStr;
         String text = ParsingUtils.read(r, stops, false);
         String peek = ParsingUtils.peek(r, 1);
         if (peek.equals(quoteTypeStr)) {
            return text;
         } else {
            ParsingUtils.readWS(r);
            text = ParsingUtils.read(r, stops, false);
            return text;
         }
      }
   }

   private static class DocTypeDecl {
      public String publicId;
      public String systemId;
      public String rootTag;

      private DocTypeDecl() {
      }

      // $FF: synthetic method
      DocTypeDecl(Object x0) {
         this();
      }
   }

   private static class ExternalId {
      public String publicId;
      public String systemId;

      private ExternalId() {
      }

      // $FF: synthetic method
      ExternalId(Object x0) {
         this();
      }
   }
}
