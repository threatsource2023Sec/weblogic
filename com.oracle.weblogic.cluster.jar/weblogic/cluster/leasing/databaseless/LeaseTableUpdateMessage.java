package weblogic.cluster.leasing.databaseless;

import java.io.Serializable;
import java.util.Map;
import weblogic.cluster.messaging.internal.BaseClusterMessage;
import weblogic.cluster.messaging.internal.ServerInformation;

public final class LeaseTableUpdateMessage extends BaseClusterMessage {
   static final int PUT = 1;
   static final int REMOVE = 2;
   static final int PUT_ALL = 3;
   private final long version;
   private final int operation;
   private final Serializable key;
   private final Serializable value;
   private final Serializable map;
   private static final long serialVersionUID = -4700234497899619454L;

   public LeaseTableUpdateMessage(ServerInformation senderInformation, long version, int type, Serializable key, Serializable value) {
      super(senderInformation, 5);
      this.version = version;
      this.operation = type;
      this.key = key;
      this.value = value;
      this.map = null;
   }

   public LeaseTableUpdateMessage(ServerInformation senderInformation, long version, int type, Serializable map) {
      super(senderInformation, 5);
      this.version = version;
      this.operation = type;

      assert map instanceof Serializable;

      this.map = map;
      this.key = null;
      this.value = null;
   }

   public static LeaseTableUpdateMessage createPutMessage(ServerInformation leaderInformation, long version, Serializable key, Serializable value) {
      return new LeaseTableUpdateMessage(leaderInformation, version, 1, key, value);
   }

   public static LeaseTableUpdateMessage createRemoveMessage(ServerInformation leaderInformation, long version, Serializable key) {
      return new LeaseTableUpdateMessage(leaderInformation, version, 2, key, (Serializable)null);
   }

   public static LeaseTableUpdateMessage createPutAllMessage(ServerInformation leaderInformation, long version, Serializable map) {
      return new LeaseTableUpdateMessage(leaderInformation, version, 3, map);
   }

   public long getVersion() {
      return this.version;
   }

   public int getOperation() {
      return this.operation;
   }

   public Serializable getKey() {
      return this.key;
   }

   public Serializable getValue() {
      return this.value;
   }

   public Map getMap() {
      return (Map)this.map;
   }

   public String toString() {
      return "[LeaseTableUpdateMessage] operation " + this.operation + ", version " + this.version + ", key " + this.key + ", value " + this.value + ", map " + this.map + "]";
   }
}
