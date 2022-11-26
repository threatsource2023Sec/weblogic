package weblogic.management.mbeans.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.security.service.EJBResource;
import weblogic.utils.Debug;

public final class EJBComponent extends Component {
   private static final long serialVersionUID = 5432117546996543348L;
   private List excludedResources = null;
   private List uncheckedResources = null;
   private EJBComponentRuntimeMBean compRTMBean = null;
   private Map remoteMethods = new HashMap();
   private Map homeMethods = new HashMap();
   private Map localMethods = new HashMap();
   private Map localHomeMethods = new HashMap();
   private boolean methodsInitialized = false;

   public EJBComponent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public EJBComponentRuntimeMBean getEJBComponentRuntime() {
      return this.compRTMBean;
   }

   public void setEJBComponentRuntime(EJBComponentRuntimeMBean runtime) {
      this.compRTMBean = runtime;
   }

   public List getExcludedEJBResources() {
      if (this.excludedResources == null) {
         this.excludedResources = new ArrayList();
      }

      return this.excludedResources;
   }

   public List getUncheckedEJBResources() {
      if (this.uncheckedResources == null) {
         this.uncheckedResources = new ArrayList();
      }

      return this.uncheckedResources;
   }

   public List getExcludedEJBResources(String applicationName, String moduleName, String ejbName) {
      return !this.hasExcludedEJBResources() ? this.getExcludedEJBResources() : this.getQualifiedList(applicationName, moduleName, ejbName, this.excludedResources);
   }

   public List getUncheckedEJBResources(String applicationName, String moduleName, String ejbName) {
      return !this.hasUncheckedEJBResources() ? this.getUncheckedEJBResources() : this.getQualifiedList(applicationName, moduleName, ejbName, this.uncheckedResources);
   }

   public void refreshDDsIfNeeded(String[] changedFiles) {
      if (!this.isConfig()) {
         if ((changedFiles == null || this.containsDD(changedFiles, "ejb-jar.xml") || this.containsDD(changedFiles, "weblogic-ejb-jar.xml") || this.containsDD(changedFiles, "weblogic-rdbms-jar.xml")) && DEBUG) {
            Debug.say("Resetting Editor and Descriptor Tree");
         }

         this.methodsInitialized = false;
      }
   }

   private void addExcludedEJBResource(EJBResource r) {
      if (this.excludedResources == null) {
         this.excludedResources = new ArrayList();
      }

      this.excludedResources.add(r);
   }

   private void addUncheckedEJBResource(EJBResource r) {
      if (this.uncheckedResources == null) {
         this.uncheckedResources = new ArrayList();
      }

      this.uncheckedResources.add(r);
   }

   public boolean hasExcludedEJBResources() {
      if (this.excludedResources == null) {
         return false;
      } else {
         return this.excludedResources.size() > 0;
      }
   }

   public boolean hasUncheckedEJBResources() {
      if (this.uncheckedResources == null) {
         return false;
      } else {
         return this.uncheckedResources.size() > 0;
      }
   }

   private List getQualifiedList(String applicationName, String moduleName, String ejbName, List inputList) {
      List l = new ArrayList();
      if (inputList == null) {
         return l;
      } else {
         Iterator it = inputList.iterator();

         while(true) {
            EJBResource r;
            do {
               do {
                  do {
                     if (!it.hasNext()) {
                        return l;
                     }

                     r = (EJBResource)it.next();
                  } while(applicationName != null && !applicationName.equals(r.getApplicationName()));
               } while(moduleName != null && !moduleName.equals(r.getModuleName()));
            } while(ejbName != null && !ejbName.equals(r.getEJBName()));

            l.add(r);
         }
      }
   }
}
