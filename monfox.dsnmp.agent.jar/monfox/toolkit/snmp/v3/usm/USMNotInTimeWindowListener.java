package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public interface USMNotInTimeWindowListener {
   boolean handleNotInTimeWindow(boolean var1, SnmpEngineID var2, int var3, int var4, int var5, int var6, V3SnmpMessageParameters var7, byte[] var8);
}
