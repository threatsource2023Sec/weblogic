package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.xml.stream.XMLName;

public interface TypeMappingEntryMBean extends XMLElementMBean {
   String getClassName();

   void setClassName(String var1);

   XMLName getElementName();

   void setElementName(XMLName var1);

   XMLName getXSDTypeName();

   void setXSDTypeName(XMLName var1);

   String getSerializerName();

   void setSerializerName(String var1);

   String getDeserializerName();

   void setDeserializerName(String var1);
}
