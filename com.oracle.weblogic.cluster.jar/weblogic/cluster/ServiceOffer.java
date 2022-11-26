package weblogic.cluster;

import java.io.Serializable;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.rmi.spi.HostID;

public interface ServiceOffer extends Serializable {
   int id();

   void setServer(HostID var1);

   HostID getServerID();

   String name();

   String appID();

   String serviceName();

   boolean isClusterable();

   void install(Context var1) throws NamingException;

   void retract(Context var1) throws NamingException;

   void update(Context var1) throws NamingException;

   long approximateAge();

   int getOldID();
}
