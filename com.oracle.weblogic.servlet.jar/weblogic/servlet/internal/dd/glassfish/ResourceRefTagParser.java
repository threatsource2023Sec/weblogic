package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class ResourceRefTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      ResourceDescriptionBean resourceDescription = weblogicWebAppBean.createResourceDescription();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("res-ref-name".equals(tagName)) {
               resourceDescription.setResRefName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("res-ref-name");
            } else if ("jndi-name".equals(tagName)) {
               resourceDescription.setJNDIName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("jndi-name");
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "resource-ref"));

   }
}
