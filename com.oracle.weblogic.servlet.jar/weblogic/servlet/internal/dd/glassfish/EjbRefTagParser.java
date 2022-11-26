package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class EjbRefTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      EjbReferenceDescriptionBean EjbRefDescriptor = weblogicWebAppBean.createEjbReferenceDescription();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("ejb-ref-name".equals(tagName)) {
               EjbRefDescriptor.setEjbRefName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("ejb-ref-name");
            } else if ("jndi-name".equals(tagName)) {
               EjbRefDescriptor.setJNDIName(this.parseTagData(reader));
               HTTPLogger.logGlassfishDescriptorParsed("jndi-name");
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "ejb-ref"));

   }
}
