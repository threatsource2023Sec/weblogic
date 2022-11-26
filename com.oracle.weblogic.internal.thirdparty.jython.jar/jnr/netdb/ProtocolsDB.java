package jnr.netdb;

import java.util.Collection;

interface ProtocolsDB {
   Protocol getProtocolByName(String var1);

   Protocol getProtocolByNumber(Integer var1);

   Collection getAllProtocols();
}
