package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class DefaultTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      HTTPLogger.logGlassfishDescriptorIgnored(reader.getLocalName());
   }
}
