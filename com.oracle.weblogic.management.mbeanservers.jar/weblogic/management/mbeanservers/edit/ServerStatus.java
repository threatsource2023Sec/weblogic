package weblogic.management.mbeanservers.edit;

public interface ServerStatus {
   String getServerName();

   int getServerState();

   Exception getServerException();
}
