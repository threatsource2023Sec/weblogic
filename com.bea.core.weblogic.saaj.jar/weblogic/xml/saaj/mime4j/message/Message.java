package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;
import weblogic.xml.saaj.mime4j.BodyDescriptor;
import weblogic.xml.saaj.mime4j.ContentHandler;
import weblogic.xml.saaj.mime4j.MimeStreamParser;
import weblogic.xml.saaj.mime4j.decoder.Base64InputStream;
import weblogic.xml.saaj.mime4j.decoder.QuotedPrintableInputStream;
import weblogic.xml.saaj.mime4j.field.Field;
import weblogic.xml.saaj.mime4j.field.UnstructuredField;

public class Message extends Entity implements Body {
   public Message() {
   }

   public Message(InputStream is) throws IOException {
      MimeStreamParser parser = new MimeStreamParser();
      parser.setContentHandler(new MessageBuilder());
      parser.parse(is);
   }

   public UnstructuredField getSubject() {
      return (UnstructuredField)this.getHeader().getField("Subject");
   }

   public void writeTo(OutputStream out) throws IOException {
   }

   private class MessageBuilder implements ContentHandler {
      private Stack stack = new Stack();
      private Message root = null;

      public MessageBuilder() {
      }

      private void expect(Class c) {
         if (!c.isInstance(this.stack.peek())) {
            throw new IllegalStateException("Internal stack error: Expected '" + c.getName() + "' found '" + this.stack.peek().getClass().getName() + "'");
         }
      }

      public void startMessage() {
         if (this.stack.isEmpty()) {
            this.stack.push(Message.this);
         } else {
            this.expect(Entity.class);
            Message m = new Message();
            ((Entity)this.stack.peek()).setBody(m);
            this.stack.push(m);
         }

      }

      public void endMessage() {
         this.expect(Message.class);
         this.stack.pop();
      }

      public void startHeader() {
         this.stack.push(new Header());
      }

      public void field(String fieldData) {
         this.expect(Header.class);
         ((Header)this.stack.peek()).addField(Field.parse(fieldData));
      }

      public void endHeader() {
         this.expect(Header.class);
         Header h = (Header)this.stack.pop();
         this.expect(Entity.class);
         ((Entity)this.stack.peek()).setHeader(h);
      }

      public void startMultipart(BodyDescriptor bd) {
         this.expect(Entity.class);
         Entity e = (Entity)this.stack.peek();
         Multipart multiPart = new Multipart();
         e.setBody(multiPart);
         this.stack.push(multiPart);
      }

      public boolean body(BodyDescriptor bd, InputStream is) throws IOException {
         this.expect(Entity.class);
         String enc = bd.getTransferEncoding();
         if ("base64".equals(enc)) {
            is = new Base64InputStream((InputStream)is);
         } else if ("quoted-printable".equals(enc)) {
            is = new QuotedPrintableInputStream((InputStream)is);
         }

         Body body = null;
         if (bd.getMimeType().startsWith("text/")) {
            body = new TempFileTextBody((InputStream)is, bd.getCharset());
         } else {
            body = new TempFileBinaryBody((InputStream)is);
         }

         ((Entity)this.stack.peek()).setBody((Body)body);
         return true;
      }

      public void endMultipart() {
         this.stack.pop();
      }

      public void startBodyPart() {
         this.expect(Multipart.class);
         BodyPart bodyPart = new BodyPart();
         ((Multipart)this.stack.peek()).addBodyPart(bodyPart);
         this.stack.push(bodyPart);
      }

      public void endBodyPart() {
         this.expect(BodyPart.class);
         this.stack.pop();
      }

      public void epilogue(InputStream is) throws IOException {
         this.expect(Multipart.class);
         StringBuffer sb = new StringBuffer();

         int b;
         while((b = is.read()) != -1) {
            sb.append((char)b);
         }

         ((Multipart)this.stack.peek()).setEpilogue(sb.toString());
      }

      public void preamble(InputStream is) throws IOException {
         this.expect(Multipart.class);
         StringBuffer sb = new StringBuffer();

         int b;
         while((b = is.read()) != -1) {
            sb.append((char)b);
         }

         ((Multipart)this.stack.peek()).setPreamble(sb.toString());
      }

      public void raw(InputStream is) throws IOException {
      }
   }
}
