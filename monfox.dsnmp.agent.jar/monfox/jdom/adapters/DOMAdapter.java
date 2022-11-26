package monfox.jdom.adapters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Document;

public interface DOMAdapter {
   Document getDocument(File var1, boolean var2) throws IOException;

   Document getDocument(InputStream var1, boolean var2) throws IOException;

   Document createDocument() throws IOException;
}
