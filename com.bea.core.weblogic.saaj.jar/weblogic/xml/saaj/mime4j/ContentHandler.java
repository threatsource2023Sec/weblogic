package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;

public interface ContentHandler {
   void startMessage();

   void endMessage();

   void startBodyPart();

   void endBodyPart();

   void startHeader();

   void field(String var1);

   void endHeader();

   void preamble(InputStream var1) throws IOException;

   void epilogue(InputStream var1) throws IOException;

   void startMultipart(BodyDescriptor var1);

   void endMultipart();

   boolean body(BodyDescriptor var1, InputStream var2) throws IOException;

   void raw(InputStream var1) throws IOException;
}
