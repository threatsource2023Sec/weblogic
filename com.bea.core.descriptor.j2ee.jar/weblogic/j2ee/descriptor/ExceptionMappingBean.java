package weblogic.j2ee.descriptor;

import javax.xml.namespace.QName;

public interface ExceptionMappingBean {
   String getExceptionType();

   void setExceptionType(String var1);

   QName getWsdlMessage();

   void setWsdlMessage(QName var1);

   String getWsdlMessagePartName();

   void setWsdlMessagePartName(String var1);

   ConstructorParameterOrderBean getConstructorParameterOrder();

   ConstructorParameterOrderBean createConstructorParameterOrder();

   void destroyConstructorParameterOrder(ConstructorParameterOrderBean var1);

   String getId();

   void setId(String var1);
}
