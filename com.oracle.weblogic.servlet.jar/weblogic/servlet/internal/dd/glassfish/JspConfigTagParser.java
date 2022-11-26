package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.JspDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class JspConfigTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      JspDescriptorBean jspDescriptor = weblogicWebAppBean.createJspDescriptor();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("property".equals(tagName)) {
               BaseGlassfishTagParser.Property property = this.getProperty(reader);
               if ("checkInterval".equals(property.getName())) {
                  jspDescriptor.setPageCheckSeconds(Integer.parseInt(property.getValue()));
                  HTTPLogger.logGlassfishDescriptorParsed("checkInterval");
               } else if ("keepgenerated".equals(property.getName())) {
                  jspDescriptor.setKeepgenerated(this.convertToBoolean(property.getValue()));
                  HTTPLogger.logGlassfishDescriptorParsed("keepgenerated");
               } else if ("scratchdir".equals(property.getName())) {
                  jspDescriptor.setWorkingDir(property.getValue());
                  HTTPLogger.logGlassfishDescriptorParsed("scratchdir");
               }
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "jsp-config"));

   }
}
