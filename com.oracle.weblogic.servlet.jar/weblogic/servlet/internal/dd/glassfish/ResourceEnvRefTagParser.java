package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class ResourceEnvRefTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      ResourceEnvDescriptionBean resEnvDescription = weblogicWebAppBean.createResourceEnvDescription();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("resource-env-ref-name".equals(tagName)) {
               resEnvDescription.setResourceEnvRefName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("resource-env-ref-name");
            } else if ("jndi-name".equals(tagName)) {
               resEnvDescription.setJNDIName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("jndi-name");
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "resource-env-ref"));

   }
}
