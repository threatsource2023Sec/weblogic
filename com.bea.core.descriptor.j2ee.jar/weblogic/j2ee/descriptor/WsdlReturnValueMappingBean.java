package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface WsdlReturnValueMappingBean {
   String getMethodReturnValue();

   void setMethodReturnValue(String var1);

   QName getWsdlMessage();

   void setWsdlMessage(QName var1);

   String getWsdlMessagePartName();

   void setWsdlMessagePartName(String var1);

   String getId();

   void setId(String var1);
}
