package com.bea.xbean.soap;

public abstract class SOAPConnection {
   public abstract SOAPMessage call(SOAPMessage var1, Object var2) throws SOAPException;

   public abstract void close() throws SOAPException;
}
