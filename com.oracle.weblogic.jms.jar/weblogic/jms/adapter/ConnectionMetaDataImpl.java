package weblogic.jms.adapter;

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;
import weblogic.jms.bridge.AdapterConnection;

public class ConnectionMetaDataImpl implements ManagedConnectionMetaData {
   private JMSManagedConnection mc;

   public ConnectionMetaDataImpl(JMSManagedConnection mc) {
      this.mc = mc;
   }

   public String getEISProductName() throws ResourceException {
      AdapterConnection con = this.mc.getJMSBaseConnection();
      return con.getMetaData().getProductName();
   }

   public String getEISProductVersion() throws ResourceException {
      AdapterConnection con = this.mc.getJMSBaseConnection();
      return con.getMetaData().getProductVersion();
   }

   public int getMaxConnections() throws ResourceException {
      return this.mc.getMaxConnections();
   }

   public String getUserName() throws ResourceException {
      return this.mc.getUserName();
   }
}
