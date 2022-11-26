package monfox.toolkit.snmp.v3;

import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;

public interface SnmpSecurityCoder {
   int UNSUPPORTED_SECURITY_MODEL = 1;
   int UNSUPPORTED_SECURITY_LEVEL = 2;
   int UNKNOWN_ENGINE_ID = 3;
   int UNKNOWN_USER_NAME = 4;
   int NOT_IN_TIME_WINDOW = 5;
   int AUTH_FAILURE = 6;
   int DECRYPTION_ERROR = 7;
   int ENCRYPTION_ERROR = 8;

   byte[] encryptScopedPDU(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException;

   BERBuffer decryptScopedPDU(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException;

   void authenticateOutgoing(BERBuffer var1, SnmpMessage var2, int var3) throws SnmpCoderException, BERException;

   int encodeSecurityParameters(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException;

   SnmpSecurityParameters decodeSecurityParameters(BERBuffer var1, SnmpMessage var2) throws SnmpCoderException, BERException;
}
