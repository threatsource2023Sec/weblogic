package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.soap.common.SOAPObject;

public interface Fault extends SOAPObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Fault";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault", "soap11");
   String TYPE_LOCAL_NAME = "Fault";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault", "soap11");

   @Nullable
   FaultCode getCode();

   void setCode(@Nullable FaultCode var1);

   @Nullable
   FaultString getMessage();

   void setMessage(@Nullable FaultString var1);

   @Nullable
   FaultActor getActor();

   void setActor(@Nullable FaultActor var1);

   @Nullable
   Detail getDetail();

   void setDetail(@Nullable Detail var1);
}
