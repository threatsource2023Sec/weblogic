package weblogic.jms.adapter;

import java.io.Serializable;
import javax.resource.spi.ManagedConnectionFactory;
import weblogic.jms.bridge.AdapterMetaData;

public class AdapterMetaDataImpl implements AdapterMetaData, Serializable {
   static final long serialVersionUID = -3699080937853855761L;
   private ManagedConnectionFactory mcf;

   AdapterMetaDataImpl(ManagedConnectionFactory mcf) {
      this.mcf = mcf;
   }

   public String getAdapterName() {
      return new String("WLS Messaging Bridge JMS Adapter");
   }

   public String getAdapterShortDescription() {
      return new String("This adapter can be used to plug a JMS implementation into the WLS Messaging Bridge framework");
   }

   public String getAdapterVendorName() {
      return new String("BEA Systems, Inc.");
   }

   public String getAdapterVersion() {
      return new String("1.0");
   }

   public String getSpecVersion() {
      return new String("1.0");
   }

   public String getNativeMessageFormat() {
      return new String("javax.jms.Message");
   }

   public String[] getMessageFormatsUnderstands() {
      String[] list = new String[]{"javax.jms.Message"};
      return list;
   }

   public boolean supportsLocalTransactionDemarcation() {
      return true;
   }

   public boolean supportsXAResource() {
      return true;
   }

   public boolean supportsAcknowledgement() {
      return true;
   }

   public boolean supportsAsynchronousMode() {
      return true;
   }

   public boolean supportsDurability() {
      return true;
   }

   public String[] getInteractionModeSupport() {
      String[] list = new String[]{"SYNC_SEND", "SYNC_RECEIVE", "SYNC_RECEIVE"};
      return list;
   }

   public boolean supportsMDBTransaction() {
      return ((JMSManagedConnectionFactory)this.mcf).isWLSAdapter();
   }

   public boolean understands(String className) {
      return className.equals("javax.jms.Message");
   }
}
