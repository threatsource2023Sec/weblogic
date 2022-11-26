package weblogic.servlet.internal;

import weblogic.management.DeploymentException;

/** @deprecated */
@Deprecated
public interface OnDemandListener {
   void OnDemandURIAccessed(String var1, String var2, boolean var3) throws DeploymentException;
}
