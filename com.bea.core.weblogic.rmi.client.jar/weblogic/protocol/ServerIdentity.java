package weblogic.protocol;

import weblogic.rmi.spi.HostID;

public interface ServerIdentity extends HostID {
   Identity getTransientIdentity();

   Identity getPersistentIdentity();

   boolean isClient();

   boolean equals(Object var1);

   int hashCode();

   String getDomainName();
}
