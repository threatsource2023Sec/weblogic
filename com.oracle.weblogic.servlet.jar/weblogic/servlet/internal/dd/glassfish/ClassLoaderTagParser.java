package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class ClassLoaderTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      ContainerDescriptorBean containerDescriptor = weblogicWebAppBean.createContainerDescriptor();
      String delegate = reader.getAttributeValue((String)null, "delegate");
      containerDescriptor.setPreferWebInfClasses(this.convertToBoolean(delegate));
      HTTPLogger.logGlassfishDescriptorParsed("delegate");

      int event;
      do {
         event = reader.next();
      } while(reader.hasNext() && !this.isEndTag(event, reader, "class-loader"));

   }
}
