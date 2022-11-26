package weblogic.apache.xerces.parsers;

import java.io.IOException;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLInputSource;
import weblogic.apache.xerces.xni.parser.XMLParserConfiguration;

public abstract class XMLParser {
   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   private static final String[] RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-handler"};
   protected final XMLParserConfiguration fConfiguration;

   protected XMLParser(XMLParserConfiguration var1) {
      this.fConfiguration = var1;
      this.fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
   }

   public void parse(XMLInputSource var1) throws XNIException, IOException {
      this.reset();
      this.fConfiguration.parse(var1);
   }

   protected void reset() throws XNIException {
   }
}
