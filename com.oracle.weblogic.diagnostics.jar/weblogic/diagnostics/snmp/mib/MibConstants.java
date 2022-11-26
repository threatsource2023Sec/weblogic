package weblogic.diagnostics.snmp.mib;

import weblogic.diagnostics.debug.DebugLogger;

public interface MibConstants {
   String MODULE_NAME = "BEA-WEBLOGIC-MIB";
   String[] SUBSYSTEM_PREFIXES = new String[]{"com", "ejb", "iiop", "jmx", "ldap", "http", "jdbc", "jms", "jta", "jvm", "man", "ons", "url", "nt", "ps", "rmc", "rdbms", "saf", "sca", "ssl", "snmp", "wan", "wldf", "wtc", "xml", "wsrm"};
   String METADATA_RESOURCE = "WLSMibMetadata.dat";
   DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPMib");
}
