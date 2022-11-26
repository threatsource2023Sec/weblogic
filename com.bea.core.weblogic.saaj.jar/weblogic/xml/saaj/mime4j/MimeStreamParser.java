package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.LinkedList;
import weblogic.xml.saaj.mime4j.decoder.Base64InputStream;
import weblogic.xml.saaj.mime4j.decoder.QuotedPrintableInputStream;

public class MimeStreamParser {
   private static BitSet fieldChars = null;
   private RootInputStream rootStream = null;
   private LinkedList bodyDescriptors = new LinkedList();
   private ContentHandler handler = null;
   private boolean raw = false;
   private boolean keepGoing = true;
   private int depth;

   public void parse(InputStream is) throws IOException {
      this.rootStream = new RootInputStream(is);
      this.parseMessage(this.rootStream);
   }

   public boolean isRaw() {
      return this.raw;
   }

   public void setRaw(boolean raw) {
      this.raw = raw;
   }

   public void stop() {
      this.rootStream.truncate();
   }

   private void parseEntity(InputStream is) throws IOException {
      BodyDescriptor bd = this.parseHeader(is);
      this.parseEntity(bd, is);
   }

   public void parseEntity(BodyDescriptor bd, InputStream is) throws IOException {
      if (this.rootStream == null) {
         this.rootStream = new RootInputStream((InputStream)is);
      }

      if (this.checkDepth() && bd.isMultipart()) {
         ++this.depth;
         this.bodyDescriptors.addFirst(bd);
         this.handler.startMultipart(bd);
         MimeBoundaryInputStream tempIs = new MimeBoundaryInputStream((InputStream)is, bd.getBoundary());
         this.handler.preamble(new CloseShieldInputStream(tempIs));
         tempIs.consume();

         while(tempIs.hasMoreParts()) {
            tempIs = new MimeBoundaryInputStream((InputStream)is, bd.getBoundary());
            this.parseBodyPart(tempIs);
            if (!this.keepGoing) {
               return;
            }

            tempIs.consume();
            if (tempIs.parentEOF()) {
               break;
            }
         }

         this.handler.epilogue(new CloseShieldInputStream((InputStream)is));
         this.handler.endMultipart();
         this.bodyDescriptors.removeFirst();
         --this.depth;
      } else if (bd.isMessage()) {
         if (bd.isBase64Encoded()) {
            is = new EOLConvertingInputStream(new Base64InputStream((InputStream)is));
         } else if (bd.isQuotedPrintableEncoded()) {
            is = new EOLConvertingInputStream(new QuotedPrintableInputStream((InputStream)is));
         }

         this.bodyDescriptors.addFirst(bd);
         this.parseMessage((InputStream)is);
         this.bodyDescriptors.removeFirst();
      } else {
         this.keepGoing = this.handler.body(bd, new CloseShieldInputStream((InputStream)is));
         if (!this.keepGoing) {
            return;
         }
      }

      while(((InputStream)is).read() != -1) {
      }

   }

   private boolean checkDepth() {
      return this.depth < 1;
   }

   private void parseMessage(InputStream is) throws IOException {
      if (this.raw) {
         this.handler.raw(new CloseShieldInputStream(is));
      } else {
         this.handler.startMessage();
         this.parseEntity(is);
         this.handler.endMessage();
      }

   }

   private void parseBodyPart(InputStream is) throws IOException {
      if (this.raw) {
         this.handler.raw(new CloseShieldInputStream(is));
      } else {
         this.handler.startBodyPart();
         this.parseEntity(is);
         this.handler.endBodyPart();
      }

   }

   private BodyDescriptor parseHeader(InputStream is) throws IOException {
      BodyDescriptor bd = new BodyDescriptor(this.bodyDescriptors.isEmpty() ? null : (BodyDescriptor)this.bodyDescriptors.getFirst());
      this.handler.startHeader();
      int lineNumber = this.rootStream.getLineNumber();
      StringBuffer sb = new StringBuffer();
      int curr = false;

      int curr;
      for(int prev = 0; (curr = is.read()) != -1; prev = curr == 13 ? prev : curr) {
         if (curr == 10 && (prev == 10 || prev == 0)) {
            sb.deleteCharAt(sb.length() - 1);
            break;
         }

         sb.append((char)curr);
      }

      int start = 0;
      int pos = 0;

      while(true) {
         while(pos < sb.length()) {
            while(pos < sb.length() && sb.charAt(pos) != '\r') {
               ++pos;
            }

            if (pos < sb.length() - 1 && sb.charAt(pos + 1) != '\n') {
               ++pos;
            } else {
               if (pos >= sb.length() - 2 || fieldChars.get(sb.charAt(pos + 2))) {
                  String field = sb.substring(start, pos);
                  start = pos + 2;
                  int index = field.indexOf(58);
                  boolean valid = false;
                  if (index != -1 && fieldChars.get(field.charAt(0))) {
                     valid = true;
                     String fieldName = field.substring(0, index).trim();

                     for(int i = 0; i < fieldName.length(); ++i) {
                        if (!fieldChars.get(fieldName.charAt(i))) {
                           valid = false;
                           break;
                        }
                     }

                     if (valid) {
                        this.handler.field(field);
                        bd.addField(fieldName, field.substring(index + 1));
                     }
                  }
               }

               pos += 2;
               ++lineNumber;
            }
         }

         this.handler.endHeader();
         return bd;
      }
   }

   public void setContentHandler(ContentHandler h) {
      this.handler = h;
   }

   static {
      fieldChars = new BitSet();

      int i;
      for(i = 33; i <= 57; ++i) {
         fieldChars.set(i);
      }

      for(i = 59; i <= 126; ++i) {
         fieldChars.set(i);
      }

   }
}
