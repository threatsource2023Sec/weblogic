package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import javax.enterprise.inject.spi.Annotated;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WseeInjectionService {
   Object resolveWebServiceReference(InjectionArchive var1, Annotated var2, String var3);

   boolean hasWebServiceReference(Annotated var1);

   String getWebServiceReferenceName(Annotated var1);
}
