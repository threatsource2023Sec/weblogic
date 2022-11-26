package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface IsReferenceParameterBearing {
   String WSA_IS_REFERENCE_PARAMETER_ATTR_LOCAL_NAME = "IsReferenceParameter";
   QName WSA_IS_REFERENCE_PARAMETER_ATTR_NAME = new QName("http://www.w3.org/2005/08/addressing", "IsReferenceParameter", "wsa");

   Boolean isWSAIsReferenceParameter();

   XSBooleanValue isWSAIsReferenceParameterXSBoolean();

   void setWSAIsReferenceParameter(Boolean var1);

   void setWSAIsReferenceParameter(XSBooleanValue var1);
}
