package org.apache.xmlbeans.impl.soap;

import java.util.Locale;

public interface SOAPFault extends SOAPBodyElement {
   void setFaultCode(String var1) throws SOAPException;

   String getFaultCode();

   void setFaultActor(String var1) throws SOAPException;

   String getFaultActor();

   void setFaultString(String var1) throws SOAPException;

   String getFaultString();

   Detail getDetail();

   Detail addDetail() throws SOAPException;

   void setFaultCode(Name var1) throws SOAPException;

   Name getFaultCodeAsName();

   void setFaultString(String var1, Locale var2) throws SOAPException;

   Locale getFaultStringLocale();
}
