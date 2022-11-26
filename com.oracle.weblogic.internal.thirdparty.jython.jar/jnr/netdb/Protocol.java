package jnr.netdb;

import java.util.Collection;

public final class Protocol {
   private final String protocolName;
   private final int proto;
   private final Collection aliases;

   Protocol(String name, int proto, Collection aliases) {
      this.protocolName = name;
      this.proto = proto;
      this.aliases = aliases;
   }

   public static final Protocol getProtocolByName(String name) {
      return getProtocolDB().getProtocolByName(name);
   }

   public static final Protocol getProtocolByNumber(int proto) {
      return getProtocolDB().getProtocolByNumber(proto);
   }

   public final String getName() {
      return this.protocolName;
   }

   public final int getProto() {
      return this.proto;
   }

   public final Collection getAliases() {
      return this.aliases;
   }

   private static final ProtocolsDB getProtocolDB() {
      return Protocol.ProtocolDBSingletonHolder.INSTANCE;
   }

   public String toString() {
      return String.format("<Protocol: Name: %s, Proto: %d, Aliases: %s>", this.protocolName, this.proto, this.aliases);
   }

   private static final class ProtocolDBSingletonHolder {
      static final ProtocolsDB INSTANCE = load();

      private static final ProtocolsDB load() {
         ProtocolsDB db = NativeProtocolsDB.getInstance();
         if (db == null) {
            db = FileProtocolsDB.getInstance();
         }

         return (ProtocolsDB)(db != null ? db : IANAProtocolsDB.getInstance());
      }
   }
}
