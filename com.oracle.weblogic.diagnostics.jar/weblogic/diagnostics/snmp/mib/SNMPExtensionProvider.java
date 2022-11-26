package weblogic.diagnostics.snmp.mib;

public interface SNMPExtensionProvider {
   String DEBUG_LOGGER_NAME = "DebugSNMPExtensionProvider";

   String getBasePath();

   String getMibModules();

   String[] getRuntimeMBeanPackageNames();

   WLSMibMetadata getMibMetaData() throws WLSMibMetadataException;
}
