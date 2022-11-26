package weblogic.diagnostics.flightrecorder.event;

public interface ConnectorEndpointEventInfo {
   String getEjbName();

   void setEjbName(String var1);

   String getJndiName();

   void setJndiName(String var1);

   String getMessageListener();

   void setMessageListener(String var1);
}
