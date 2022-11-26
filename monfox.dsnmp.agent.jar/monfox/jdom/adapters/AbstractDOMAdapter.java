package monfox.jdom.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Document;

public abstract class AbstractDOMAdapter implements DOMAdapter {
   public static boolean a;

   public Document getDocument(File var1, boolean var2) throws IOException {
      return this.getDocument((InputStream)(new FileInputStream(var1)), var2);
   }

   public abstract Document getDocument(InputStream var1, boolean var2) throws IOException;

   public abstract Document createDocument() throws IOException;
}
