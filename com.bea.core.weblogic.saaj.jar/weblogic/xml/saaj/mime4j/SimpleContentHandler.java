package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;
import weblogic.xml.saaj.mime4j.decoder.Base64InputStream;
import weblogic.xml.saaj.mime4j.decoder.QuotedPrintableInputStream;
import weblogic.xml.saaj.mime4j.field.Field;
import weblogic.xml.saaj.mime4j.message.Header;

public abstract class SimpleContentHandler implements ContentHandler {
   private Header currHeader;

   public abstract void headers(Header var1);

   public abstract boolean bodyDecoded(BodyDescriptor var1, InputStream var2) throws IOException;

   public void startMessage() {
   }

   public void endMessage() {
   }

   public void startBodyPart() {
   }

   public void endBodyPart() {
   }

   public void preamble(InputStream is) throws IOException {
   }

   public void epilogue(InputStream is) throws IOException {
   }

   public void startMultipart(BodyDescriptor bd) {
   }

   public void endMultipart() {
   }

   public void raw(InputStream is) throws IOException {
   }

   public final void startHeader() {
      this.currHeader = new Header();
   }

   public final void field(String fieldData) {
      this.currHeader.addField(Field.parse(fieldData));
   }

   public final void endHeader() {
      Header tmp = this.currHeader;
      this.currHeader = null;
      this.headers(tmp);
   }

   public final boolean body(BodyDescriptor bd, InputStream is) throws IOException {
      if (bd.isBase64Encoded()) {
         return this.bodyDecoded(bd, new Base64InputStream(is));
      } else {
         return bd.isQuotedPrintableEncoded() ? this.bodyDecoded(bd, new QuotedPrintableInputStream(is)) : this.bodyDecoded(bd, is);
      }
   }
}
