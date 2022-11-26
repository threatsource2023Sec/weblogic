package weblogic.jms.client;

public interface ClientRuntimeInfo {
   String getWLSServerName();

   String getRuntimeMBeanName();

   ClientRuntimeInfo getParentInfo();
}
