package weblogic.management.runtime;

import java.util.Iterator;
import java.util.List;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;

public interface SessionHelperRuntimeMBean extends RuntimeMBean {
   WeblogicApplicationBean getWeblogicApplicationBean();

   ApplicationBean getApplicationBean();

   List getWebAppBeans();

   List getEjbJarBeans();

   List getConnectorBeans();

   List getGarBeans();

   List getModuleBeans();

   void savePlan();

   void activateChanges();

   Iterator getChanges();

   Iterator getUnactivatedChanges();

   boolean isModified();

   void updateApplication();

   void undoUnsavedChanges();

   void undoUnactivatedChanges();
}
