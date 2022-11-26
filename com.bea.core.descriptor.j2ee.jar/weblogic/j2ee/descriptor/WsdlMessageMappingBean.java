package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface WsdlMessageMappingBean {
   QName getWsdlMessage();

   void setWsdlMessage(QName var1);

   String getWsdlMessagePartName();

   void setWsdlMessagePartName(String var1);

   String getParameterMode();

   void setParameterMode(String var1);

   EmptyBean getSoapHeader();

   EmptyBean createSoapHeader();

   void destroySoapHeader(EmptyBean var1);

   String getId();

   void setId(String var1);
}
