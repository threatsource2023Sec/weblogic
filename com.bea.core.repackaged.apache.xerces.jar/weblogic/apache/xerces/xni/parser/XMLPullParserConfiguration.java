package weblogic.apache.xerces.xni.parser;

import java.io.IOException;
import weblogic.apache.xerces.xni.XNIException;

public interface XMLPullParserConfiguration extends XMLParserConfiguration {
   void setInputSource(XMLInputSource var1) throws XMLConfigurationException, IOException;

   boolean parse(boolean var1) throws XNIException, IOException;

   void cleanup();
}
