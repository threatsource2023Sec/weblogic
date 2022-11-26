package weblogic.protocol;

public interface Protocol {
   byte QOS_ANY = 101;
   byte QOS_SECURE = 102;
   byte QOS_ADMIN = 103;
   byte T3 = 0;
   byte HTTP = 1;
   byte T3S = 2;
   byte HTTPS = 3;
   byte IIOP = 4;
   byte IIOPS = 5;
   byte ADMIN = 6;
   byte COM = 7;
   byte JRMP = 8;
   byte LDAP = 10;
   byte LDAPS = 11;
   byte CLUSTER = 12;
   byte CLUSTERS = 13;
   byte SNMP = 14;
   byte DYNAMIC_PROTOCOL = 16;
   String PROTOCOL_ADMIN_NAME = "ADMIN";

   ProtocolHandler getHandler();

   String getProtocolName();

   String getAsURLPrefix();

   byte toByte();

   byte getQOS();

   boolean isSatisfactoryQOS(byte var1);

   boolean isSecure();

   /** @deprecated */
   @Deprecated
   boolean isUnknown();

   boolean isEnabled();

   Protocol upgrade();
}
