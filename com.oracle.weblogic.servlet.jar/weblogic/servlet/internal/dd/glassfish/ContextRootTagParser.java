package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class ContextRootTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      String contextRoot = this.parseTagData(reader);
      weblogicWebAppBean.setContextRoots(new String[]{contextRoot});
      HTTPLogger.logGlassfishDescriptorParsed("context-root");
   }
}
