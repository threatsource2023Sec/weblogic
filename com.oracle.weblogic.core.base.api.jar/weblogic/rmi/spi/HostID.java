package weblogic.rmi.spi;

public interface HostID extends Comparable {
   boolean isLocal();

   String objectToString();

   String getServerName();

   String getHostURI();
}
