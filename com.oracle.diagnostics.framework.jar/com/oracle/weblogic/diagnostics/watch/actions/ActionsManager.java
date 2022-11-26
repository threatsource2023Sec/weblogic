package com.oracle.weblogic.diagnostics.watch.actions;

import java.beans.BeanInfo;
import java.lang.annotation.Annotation;
import java.util.Locale;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ActionsManager {
   String[] getKnownActionTypes(Annotation... var1);

   ScopedActionsFactory createScopedActionsFactory(Annotation... var1);

   BeanInfo getActionInfo(String var1, Locale var2);

   Action getAction(String var1, Annotation... var2);

   ServiceHandle getActionServiceHandle(String var1, Annotation... var2);

   ActionConfigBean getActionConfig(String var1);

   void releaseAction(Action var1);
}
