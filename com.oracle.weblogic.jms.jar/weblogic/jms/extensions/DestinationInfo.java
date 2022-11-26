package weblogic.jms.extensions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import weblogic.jms.common.DestinationImpl;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.io.FilteringObjectInputStream;

public class DestinationInfo {
   private static final String OPEN_TYPE_NAME = "DestinationInfo";
   private static final String OPEN_DESCRIPTION = "This object represents a JMS Destination.";
   private static final String ITEM_VERSION_NUMBER = "VersionNumber";
   private static final String ITEM_NAME = "Name";
   private static final String ITEM_SERVER_NAME = "ServerName";
   private static final String ITEM_APPLICATION_NAME = "ApplicationName";
   private static final String ITEM_MODULE_NAME = "ModuleName";
   private static final String ITEM_TOPIC = "Topic";
   private static final String ITEM_QUEUE = "Queue";
   private static final String ITEM_SERIALIZED_DESTINATION = "SerializedDestination";
   private static String[] itemNames = new String[]{"VersionNumber", "Name", "ServerName", "ApplicationName", "ModuleName", "Topic", "Queue", "SerializedDestination"};
   private static String[] itemDescriptions = new String[]{"The version number.", "The name of the destination.", "The name of the JMS server hosting the destination.", "The name of the application that the destination is associated with.", "The name of the module that the destination is associated with.", "Indicates whether the destination is a topic.", "Indicates whether the destination is a queue.", "The serialized Destination instance."};
   private static OpenType[] itemTypes;
   private static final int VERSION = 1;
   private String name;
   private String jmsServerInstanceName;
   private String applicationName;
   private String moduleName;
   private boolean topic;
   private boolean queue;
   private WLDestination destination;

   public DestinationInfo(CompositeData cd) throws OpenDataException {
      this.readCompositeData(cd);
   }

   public DestinationInfo(WLDestination destination) {
      this.destination = destination;
      DestinationImpl dest = (DestinationImpl)destination;
      this.name = dest.getName();
      this.jmsServerInstanceName = dest.getServerName();
      this.applicationName = dest.getApplicationName();
      this.moduleName = dest.getModuleName();
      this.topic = dest.isTopic();
      this.queue = dest.isQueue();
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public WLDestination getDestination() {
      return this.destination;
   }

   public void setDestination(WLDestination destination) {
      this.destination = destination;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public boolean isQueue() {
      return this.queue;
   }

   public void setQueue(boolean queue) {
      this.queue = queue;
   }

   public String getServerName() {
      return this.jmsServerInstanceName;
   }

   public void setServerName(String jmsServerInstanceName) {
      this.jmsServerInstanceName = jmsServerInstanceName;
   }

   public boolean isTopic() {
      return this.topic;
   }

   public void setTopic(boolean topic) {
      this.topic = topic;
   }

   protected void readCompositeData(CompositeData cd) throws OpenDataException {
      String cdName = (String)cd.get("Name");
      if (cdName != null) {
         this.setName(cdName);
      }

      String cdServerName = (String)cd.get("ServerName");
      if (cdServerName != null) {
         this.setServerName(cdServerName);
      }

      String cdAppName = (String)cd.get("ApplicationName");
      if (cdAppName != null) {
         this.setApplicationName(cdAppName);
      }

      String cdModuleName = (String)cd.get("ModuleName");
      if (cdModuleName != null) {
         this.setModuleName(cdModuleName);
      }

      Boolean cdQueue = (Boolean)cd.get("Queue");
      if (cdQueue != null) {
         this.setQueue(cdQueue);
      }

      Boolean cdTopic = (Boolean)cd.get("Topic");
      if (cdTopic != null) {
         this.setTopic(cdTopic);
      }

      String cdDestination = (String)cd.get("SerializedDestination");
      if (cdDestination != null) {
         OpenDataException e;
         try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(cdDestination);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new FilteringObjectInputStream(bais);
            this.setDestination((WLDestination)ois.readObject());
         } catch (IOException var13) {
            e = new OpenDataException("Unable to deserialize destination.");
            e.initCause(var13);
            throw e;
         } catch (ClassNotFoundException var14) {
            e = new OpenDataException("Unable to deserialize destination.");
            e.initCause(var14);
            throw e;
         }
      }

   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();
      data.put("VersionNumber", new Integer(1));
      data.put("Name", this.name);
      data.put("ServerName", this.jmsServerInstanceName);
      data.put("ApplicationName", this.applicationName);
      data.put("ModuleName", this.moduleName);
      data.put("Topic", new Boolean(this.topic));
      data.put("Queue", new Boolean(this.queue));

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(this.destination);
         BASE64Encoder encoder = new BASE64Encoder();
         String value = encoder.encodeBuffer(baos.toByteArray());
         data.put("SerializedDestination", value);
         return data;
      } catch (IOException var6) {
         OpenDataException e = new OpenDataException("Unable to serialize destination.");
         e.initCause(var6);
         throw e;
      }
   }

   protected CompositeType getCompositeType() throws OpenDataException {
      CompositeType ct = new CompositeType("DestinationInfo", "This object represents a JMS Destination.", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   static {
      itemTypes = new OpenType[]{SimpleType.INTEGER, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.BOOLEAN, SimpleType.BOOLEAN, SimpleType.STRING};
   }
}
