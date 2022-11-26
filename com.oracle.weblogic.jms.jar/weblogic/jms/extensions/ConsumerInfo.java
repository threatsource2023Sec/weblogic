package weblogic.jms.extensions;

import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class ConsumerInfo {
   private static final String OPEN_TYPE_NAME = "ConsumerInfo";
   private static final String OPEN_DESCRIPTION = "This object represents a JMS Consumer.";
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_NAME = "Name";
   private static final String ITEM_DURABLE = "Durable";
   private static final String ITEM_SELECTOR = "Selector";
   private static final String ITEM_CLIENT_ID = "ClientID";
   private static final String ITEM_NO_LOCAL = "NoLocal";
   private static final String ITEM_CONNECTION_ADDRESS = "ConnectionAddress";
   private static String[] itemNames = new String[]{"VersionNumber", "Name", "Durable", "Selector", "ClientID", "NoLocal", "ConnectionAddress"};
   private static String[] itemDescriptions = new String[]{"The version number.", "The name of the consumer/subscription.", "Indicates whether this consumer is a durable subscriber.", "The JMS message selector associated with this consumer.", "The clientID of this consumer's connection.", "The NoLocal attribute of this subscriber.", "Addressing information about the consumer's connection that consists of the client's host address"};
   private static OpenType[] itemTypes;
   private static final int VERSION = 1;
   private String name;
   private boolean durable;
   private String selector;
   private String clientID;
   private boolean noLocal;
   private String connectionAddress;

   public ConsumerInfo(CompositeData cd) {
      this.readCompositeData(cd);
   }

   public ConsumerInfo(String name, boolean durable, String selector, String clientID, boolean noLocal, String connectionAddress) {
      this.name = name;
      this.durable = durable;
      this.selector = selector;
      this.clientID = clientID;
      this.noLocal = noLocal;
      this.connectionAddress = connectionAddress;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   public String getClientID() {
      return this.clientID;
   }

   public void setClientID(String clientID) {
      this.clientID = clientID;
   }

   public String getConnectionAddress() {
      return this.connectionAddress;
   }

   public void setConnectionAddress(String connectionAddress) {
      this.connectionAddress = connectionAddress;
   }

   public boolean isDurable() {
      return this.durable;
   }

   public void setDurable(boolean durable) {
      this.durable = durable;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isNoLocal() {
      return this.noLocal;
   }

   public void setNoLocal(boolean noLocal) {
      this.noLocal = noLocal;
   }

   public String getSelector() {
      return this.selector;
   }

   public void setSelector(String selector) {
      this.selector = selector;
   }

   protected void readCompositeData(CompositeData cd) {
      String cdName = (String)cd.get("Name");
      if (cdName != null) {
         this.setName(cdName);
      }

      Boolean cdDurable = (Boolean)cd.get("Durable");
      if (cdDurable != null) {
         this.setDurable(cdDurable);
      }

      String cdSelector = (String)cd.get("Selector");
      if (cdSelector != null) {
         this.setSelector(cdSelector);
      }

      String cdClientID = (String)cd.get("ClientID");
      if (cdClientID != null) {
         this.setClientID(cdClientID);
      }

      Boolean cdNoLocal = (Boolean)cd.get("NoLocal");
      if (cdNoLocal != null) {
         this.setNoLocal(cdNoLocal);
      }

      String cdConnectionAddress = (String)cd.get("ConnectionAddress");
      if (cdConnectionAddress != null) {
         this.setConnectionAddress(cdConnectionAddress);
      }

   }

   protected Map getCompositeDataMap() {
      Map data = new HashMap();
      data.put("VersionNumber", new Integer(1));
      data.put("Name", this.name);
      data.put("Durable", new Boolean(this.durable));
      data.put("Selector", this.selector);
      data.put("ClientID", this.clientID);
      data.put("NoLocal", new Boolean(this.noLocal));
      data.put("ConnectionAddress", this.connectionAddress);
      return data;
   }

   protected CompositeType getCompositeType() throws OpenDataException {
      CompositeType ct = new CompositeType("ConsumerInfo", "This object represents a JMS Consumer.", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   static {
      itemTypes = new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.BOOLEAN, SimpleType.STRING, SimpleType.STRING, SimpleType.BOOLEAN, SimpleType.STRING};
   }
}
