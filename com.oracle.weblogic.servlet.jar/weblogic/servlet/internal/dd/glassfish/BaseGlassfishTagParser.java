package weblogic.servlet.internal.dd.glassfish;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.lang.StringUtils;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;

public abstract class BaseGlassfishTagParser {
   abstract void parse(XMLStreamReader var1, WeblogicWebAppBean var2) throws XMLStreamException;

   protected String parseTagData(XMLStreamReader reader) throws XMLStreamException {
      String cData = null;

      for(int event = reader.next(); event != 4; event = reader.next()) {
      }

      if (reader.getText() != null) {
         cData = reader.getText().trim();
      }

      return cData;
   }

   protected boolean isEndTag(int event, XMLStreamReader reader, String tagName) {
      return event == 2 && StringUtils.isNotEmpty(tagName) && tagName.equals(reader.getLocalName());
   }

   protected boolean convertToBoolean(String flag) {
      String[] var2 = DescriptorConstants.BOOLEAN_FALSE;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String booleanStr = var2[var4];
         if (booleanStr.equals(flag)) {
            return false;
         }
      }

      return true;
   }

   protected Property getProperty(XMLStreamReader reader) {
      return new Property(reader.getAttributeValue((String)null, "name"), reader.getAttributeValue((String)null, "value"));
   }

   class Property {
      String name;
      String value;

      public Property(String name, String value) {
         this.name = name;
         this.value = value;
      }

      public String getName() {
         return this.name;
      }

      public String getValue() {
         return this.value;
      }
   }
}
