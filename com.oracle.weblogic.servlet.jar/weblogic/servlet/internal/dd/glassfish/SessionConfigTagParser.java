package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.j2ee.descriptor.wl.SessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;

public class SessionConfigTagParser extends BaseGlassfishTagParser {
   void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      SessionDescriptorBean sessionDescriptor = weblogicWebAppBean.createSessionDescriptor();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String TagName = reader.getLocalName();
            if ("session-manager".equals(TagName)) {
               this.parseSessionManager(reader, sessionDescriptor);
            } else if ("session-properties".equals(TagName)) {
               this.parseSessionProperties(reader, sessionDescriptor);
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "session-config"));

   }

   void parseSessionManager(XMLStreamReader reader, SessionDescriptorBean sessionDescriptor) throws XMLStreamException {
      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("manager-properties".equals(tagName)) {
               this.parseManagerProperties(reader, sessionDescriptor);
            } else if ("store-properties".equals(tagName)) {
               this.parseStoreProperties(reader, sessionDescriptor);
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "session-manager"));

   }

   private void parseSessionProperties(XMLStreamReader reader, SessionDescriptorBean sessionDescriptor) throws XMLStreamException {
      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("property".equals(tagName)) {
               BaseGlassfishTagParser.Property property = this.getProperty(reader);
               if ("timeoutSeconds".equals(property.getName())) {
                  sessionDescriptor.setTimeoutSecs(Integer.parseInt(property.getValue()));
                  HTTPLogger.logGlassfishDescriptorParsed("timeoutSeconds");
               }
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "session-properties"));

   }

   private void parseStoreProperties(XMLStreamReader reader, SessionDescriptorBean sessionDescriptor) throws XMLStreamException {
      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("property".equals(tagName)) {
               BaseGlassfishTagParser.Property property = this.getProperty(reader);
               if ("directory".equals(property.getName())) {
                  sessionDescriptor.setPersistentStoreDir(property.getValue());
                  HTTPLogger.logGlassfishDescriptorParsed("directory");
               }
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "store-properties"));

   }

   private void parseManagerProperties(XMLStreamReader reader, SessionDescriptorBean sessionDescriptor) throws XMLStreamException {
      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String tagName = reader.getLocalName();
            if ("property".equals(tagName)) {
               BaseGlassfishTagParser.Property property = this.getProperty(reader);
               if ("reapIntervalSeconds".equals(property.getName())) {
                  sessionDescriptor.setInvalidationIntervalSecs(Integer.parseInt(property.getValue()));
                  HTTPLogger.logGlassfishDescriptorParsed("reapIntervalSeconds");
               } else if ("maxSessions".equals(property.getName())) {
                  sessionDescriptor.setMaxInMemorySessions(Integer.parseInt(property.getValue()));
                  HTTPLogger.logGlassfishDescriptorParsed("maxSessions");
               }
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "manager-properties"));

   }
}
