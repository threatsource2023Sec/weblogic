package weblogic.servlet.internal;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InternalWebAppListenerRegistration {
   String getListenerClassName();
}
