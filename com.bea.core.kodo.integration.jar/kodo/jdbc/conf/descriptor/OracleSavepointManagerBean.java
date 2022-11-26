package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.SavepointManagerBean;

public interface OracleSavepointManagerBean extends SavepointManagerBean {
   boolean getRestoreFieldState();

   void setRestoreFieldState(boolean var1);
}
