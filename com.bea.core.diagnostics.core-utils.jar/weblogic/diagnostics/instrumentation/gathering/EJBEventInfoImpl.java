package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.EJBEventInfo;

public class EJBEventInfoImpl implements EJBEventInfo {
   private static final String delimiter = ":";
   private String applicationName = null;
   private String componentName = null;
   private String ejbName = null;
   private String ejbMethodName = null;

   public EJBEventInfoImpl(String applicationName, String componentName, String ejbName, String ejbMethodName) {
      this.applicationName = applicationName;
      this.componentName = componentName;
      this.ejbName = ejbName;
      this.ejbMethodName = ejbMethodName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public void setComponentName(String componentName) {
      this.componentName = componentName;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public void setEjbName(String ejbName) {
      this.ejbName = ejbName;
   }

   public String getEjbMethodName() {
      return this.ejbMethodName;
   }

   public void setEjbMethodName(String ejbMethodName) {
      this.ejbMethodName = ejbMethodName;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.applicationName);
      sb.append(":");
      sb.append(this.componentName);
      sb.append(":");
      sb.append(this.ejbName);
      sb.append(":");
      sb.append(this.ejbMethodName);
      return sb.toString();
   }
}
