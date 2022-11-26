package weblogic.management.runtime;

import javax.xml.namespace.QName;

public interface WseeHandlerRuntimeMBean extends RuntimeMBean {
   Class getHandlerClass();

   QName[] getHeaders();
}
