package weblogic.apache.xerces.xni.parser;

import java.io.IOException;
import weblogic.apache.xerces.xni.XNIException;

public interface XMLDocumentScanner extends XMLDocumentSource {
   void setInputSource(XMLInputSource var1) throws IOException;

   boolean scanDocument(boolean var1) throws IOException, XNIException;
}
