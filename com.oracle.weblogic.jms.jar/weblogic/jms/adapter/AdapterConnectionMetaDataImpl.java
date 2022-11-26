package weblogic.jms.adapter;

import javax.resource.ResourceException;
import javax.resource.spi.EISSystemException;
import weblogic.jms.bridge.AdapterConnection;
import weblogic.jms.bridge.AdapterConnectionMetaData;

public class AdapterConnectionMetaDataImpl implements AdapterConnectionMetaData {
   private JMSManagedConnection mc;
   private JMSManagedConnectionFactory mcf;

   public AdapterConnectionMetaDataImpl(JMSManagedConnection mc, JMSManagedConnectionFactory mcf) {
      this.mc = mc;
      this.mcf = mcf;
   }

   public String getProductName() throws ResourceException {
      return new String("Java Messaging Service");
   }

   public String getProductVersion() throws ResourceException {
      return new String("1.0.2");
   }

   public String getUserName() throws ResourceException {
      return this.mc.getUserName();
   }

   public boolean implementsMDBTransaction() throws ResourceException {
      try {
         AdapterConnection con = this.mc.getJMSBaseConnection();
         return ((JMSBaseConnection)con).implementsMDBTransaction();
      } catch (Exception var3) {
         ResourceException re = new EISSystemException(var3.getMessage());
         re.setLinkedException(var3);
         throw re;
      }
   }

   public boolean isXAConnection() throws ResourceException {
      try {
         AdapterConnection con = this.mc.getJMSBaseConnection();
         return ((JMSBaseConnection)con).isXAConnection();
      } catch (Exception var3) {
         ResourceException re = new EISSystemException(var3.getMessage());
         re.setLinkedException(var3);
         throw re;
      }
   }
}
