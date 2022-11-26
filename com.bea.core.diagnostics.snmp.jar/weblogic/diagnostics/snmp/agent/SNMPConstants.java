package weblogic.diagnostics.snmp.agent;

public interface SNMPConstants {
   String SNMP_WORK_MANAGER_NAME = "SnmpWorkManager";
   int SNMP_MIN_THREADS = 2;
   String V2_DISPLAY_STRING = "SNMPv2-TC.DisplayString";
   String TABLE_NAME_SUFFIX = "Table";
   String INDEX = "Index";
   String OBJECT_NAME = "ObjectName";
   int SNMP_V1 = 1;
   int SNMP_V2 = 2;
   int SNMP_V3 = 3;
   String NOAUTH_NOPRIV_STR = "noAuthNoPriv";
   String AUTH_NOPRIV_STR = "authNoPriv";
   String AUTH_PRIV_STR = "authPriv";
   int NOAUTH_NOPRIV = 0;
   int AUTH_NOPRIV = 1;
   int AUTH_PRIV = 3;
   int MD5 = 0;
   int SHA = 1;
   int DES = 2;
   int AES_128 = 3;
   String MD5_STR = "MD5";
   String SHA_STR = "SHA";
   String DES_STR = "DES";
   String AES_128_STR = "AES_128";
   int MAX_PORT_RETRY_COUNT_DEFAULT = 10;
   int SNMP_TRAP_DESTINATION_DEFAULT = 162;
   int SNMP_UDP_DEFAULT_PORT = 161;
}
