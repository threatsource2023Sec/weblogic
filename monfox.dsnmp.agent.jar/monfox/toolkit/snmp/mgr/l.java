package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.mgr.usm.Usm;

interface l {
   void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3) throws SnmpException;

   void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException;

   void sendPollPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException;

   void setParameter(String var1, Object var2);

   void retryPDU(SnmpPendingRequest var1) throws SnmpException;

   void retryPDU(SnmpPendingRequest var1, boolean var2) throws SnmpException;

   void pausePolling(SnmpPendingRequest var1);

   void restartPolling(SnmpPendingRequest var1);

   void setReceiveBufferSize(int var1);

   int getReceiveBufferSize();

   void setMaxSize(int var1);

   int getMaxSize();

   void shutdown();

   Usm getUsm();

   SnmpEngine getSnmpEngine();

   void cancel(SnmpPendingRequest var1);

   void cancel(SnmpPendingRequest var1, boolean var2);
}
