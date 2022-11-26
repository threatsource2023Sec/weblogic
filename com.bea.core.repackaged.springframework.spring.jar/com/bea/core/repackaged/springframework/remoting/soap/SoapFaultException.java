package com.bea.core.repackaged.springframework.remoting.soap;

import com.bea.core.repackaged.springframework.remoting.RemoteInvocationFailureException;
import javax.xml.namespace.QName;

public abstract class SoapFaultException extends RemoteInvocationFailureException {
   protected SoapFaultException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public abstract String getFaultCode();

   public abstract QName getFaultCodeAsQName();

   public abstract String getFaultString();

   public abstract String getFaultActor();
}
