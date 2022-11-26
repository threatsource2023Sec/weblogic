package weblogic.management.scripting;

import javax.management.MBeanServerConnection;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;

public class WLSTEditVariables {
   final String name;
   MBeanServerConnection msc;
   EditServiceMBean serviceMBean;
   DomainMBean domainMBean;
   ConfigurationManagerMBean configurationManager;
   String lastPlaceInEdit;
   boolean addedEditChangeListener;
   boolean isEditSessionInProgress;
   boolean isEditSessionExclusive;

   WLSTEditVariables(String name) {
      this.msc = null;
      this.serviceMBean = null;
      this.domainMBean = null;
      this.configurationManager = null;
      this.lastPlaceInEdit = "";
      this.addedEditChangeListener = false;
      this.isEditSessionInProgress = false;
      this.isEditSessionExclusive = false;
      this.name = name == null ? "default" : name;
   }

   WLSTEditVariables() {
      this((String)null);
   }

   boolean equalsName(String str) {
      if (str != null) {
         return str.equals(this.name);
      } else {
         return this.name == null || "default".equals(this.name);
      }
   }

   boolean isGlobalSession() {
      return this.name == null || "default".equals(this.name);
   }
}
