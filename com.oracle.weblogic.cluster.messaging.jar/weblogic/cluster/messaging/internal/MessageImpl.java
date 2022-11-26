package weblogic.cluster.messaging.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

final class MessageImpl implements Message, Externalizable {
   private byte[] data;
   private String serverName;
   private long startTime;
   private long id;
   private String version;
   private ServerConfigurationInformation configuration;
   private transient ServerConfigurationInformation forwardConfiguration;

   MessageImpl(byte[] data, String serverName, long startTime, long id) {
      this.data = data;
      this.serverName = serverName;
      this.startTime = startTime;
      this.id = id;
      this.version = "9.5";
      this.configuration = Environment.getConfiguredServersMonitor().getConfiguration(serverName);
   }

   public String getVersion() {
      return this.version;
   }

   public String getServerName() {
      return this.serverName;
   }

   public long getServerStartTime() {
      return this.startTime;
   }

   public byte[] getData() {
      return this.data;
   }

   public ServerConfigurationInformation getSenderConfiguration() {
      return this.configuration;
   }

   public ServerConfigurationInformation getForwardingServer() {
      return this.forwardConfiguration;
   }

   public String toString() {
      return " server " + this.serverName + ", id=" + this.id;
   }

   public MessageImpl() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF("9.5");
      out.writeInt(this.data.length);
      out.write(this.data);
      out.writeUTF(this.serverName);
      out.writeObject(this.configuration);
      out.writeObject(Environment.getGroupManager().getServerConfigForWire());
      out.writeLong(this.startTime);
      out.writeLong(this.id);
   }

   public void readExternal(ObjectInput in) throws IOException {
      this.version = in.readUTF();
      int length = in.readInt();
      this.data = new byte[length];
      in.readFully(this.data);
      this.serverName = in.readUTF();

      try {
         this.configuration = (ServerConfigurationInformation)in.readObject();
         this.forwardConfiguration = (ServerConfigurationInformation)in.readObject();
      } catch (ClassNotFoundException var4) {
         throw new AssertionError(var4);
      }

      this.startTime = in.readLong();
      this.id = in.readLong();
   }
}
