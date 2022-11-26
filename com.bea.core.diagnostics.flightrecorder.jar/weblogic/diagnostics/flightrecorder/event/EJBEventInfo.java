package weblogic.diagnostics.flightrecorder.event;

public interface EJBEventInfo {
   String getApplicationName();

   void setApplicationName(String var1);

   String getComponentName();

   void setComponentName(String var1);

   String getEjbName();

   void setEjbName(String var1);

   String getEjbMethodName();

   void setEjbMethodName(String var1);
}
