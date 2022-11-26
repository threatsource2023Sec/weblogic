package weblogic.application;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ApplicationAccessService {
   String getCurrentModuleName();

   /** @deprecated */
   @Deprecated
   String getCurrentApplicationName();

   String getApplicationName(ClassLoader var1);

   String getModuleName(ClassLoader var1);

   String getApplicationVersion(ClassLoader var1);
}
