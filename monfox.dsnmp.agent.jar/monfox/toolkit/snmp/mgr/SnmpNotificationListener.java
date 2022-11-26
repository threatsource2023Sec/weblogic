package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;

/** @deprecated */
public interface SnmpNotificationListener {
   void handleTrapV1(SnmpNotificationDispatcher var1, SnmpTrapPDU var2, TransportEntity var3);

   void handleTrapV2(SnmpNotificationDispatcher var1, SnmpRequestPDU var2, TransportEntity var3);

   void handleInform(SnmpNotificationDispatcher var1, SnmpRequestPDU var2, TransportEntity var3);
}
