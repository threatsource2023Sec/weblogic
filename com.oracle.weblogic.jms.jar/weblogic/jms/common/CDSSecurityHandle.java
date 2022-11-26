package weblogic.jms.common;

import weblogic.security.subject.AbstractSubject;

public interface CDSSecurityHandle {
   boolean isReady();

   boolean isRemoteDomain();

   boolean isForeignJMSServer();

   AbstractSubject getForeignSubject();

   void close();
}
