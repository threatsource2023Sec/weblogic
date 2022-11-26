package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractContentHandler implements ContentHandler {
   public void endMultipart() {
   }

   public void startMultipart(BodyDescriptor bd) {
   }

   public boolean body(BodyDescriptor bd, InputStream is) throws IOException {
      return true;
   }

   public void endBodyPart() {
   }

   public void endHeader() {
   }

   public void endMessage() {
   }

   public void epilogue(InputStream is) throws IOException {
   }

   public void field(String fieldData) {
   }

   public void preamble(InputStream is) throws IOException {
   }

   public void startBodyPart() {
   }

   public void startHeader() {
   }

   public void startMessage() {
   }

   public void raw(InputStream is) throws IOException {
   }
}
