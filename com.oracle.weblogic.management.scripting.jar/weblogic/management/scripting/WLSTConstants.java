package weblogic.management.scripting;

public interface WLSTConstants {
   String JNDI = "/jndi/";
   String PROTOCOL_T3 = "t3";
   String PROTOCOL_IIOP = "iiop";
   String RUNTIME_SERVER_JNDI_NAME = "weblogic.management.mbeanservers.runtime";
   String DOMAIN_RUNTIME_SERVER_JNDI_NAME = "weblogic.management.mbeanservers.domainruntime";
   String EDIT_SERVER_JNDI_NAME = "weblogic.management.mbeanservers.edit";
   String CONFIG_RUNTIME_TREE = "RuntimeConfigServerDomain";
   String RUNTIME_RUNTIME_TREE = "RuntimeRuntimeServerDomain";
   String CONFIG_DOMAINRUNTIME_TREE = "ConfigDomainRuntime";
   String RUNTIME_DOMAINRUNTIME_TREE = "RuntimeDomainRuntime";
   String CUSTOM_TREE = "Custom_Domain";
   String DOMAIN_CUSTOM_TREE = "DomainCustom_Domain";
   String EDIT_CUSTOM_TREE = "EditCustom_Domain";
   String DEPRECATED_ADMIN_TREE = "Domain";
   String DEPRECATED_CONFIG_TREE = "DomainConfig";
   String DEPRECATED_RUNTIME_TREE = "DomainRuntime";
   String EDIT_TREE = "ConfigEdit";
   String JNDI_TREE = "JNDI";
   String JSR77_TREE = "JSR77";
   String DEPRECATED_CONFIG_PROMPT = "config";
   String DEPRECATED_RUNTIME_PROMPT = "runtime";
   String DEPRECATED_ADMINCONFIG_PROMPT = "adminConfig";
   String CUSTOM_PROMPT = "custom";
   String DOMAIN_CUSTOM_PROMPT = "domainCustom";
   String EDIT_CUSTOM_PROMPT = "editCustom";
   String JNDI_PROMPT = "jndi";
   String CONFIG_RUNTIME_PROMPT = "serverConfig";
   String RUNTIME_RUNTIME_PROMPT = "serverRuntime";
   String CONFIG_DOMAINRUNTIME_PROMPT = "domainConfig";
   String RUNTIME_DOMAINRUNTIME_PROMPT = "domainRuntime";
   String EDIT_PROMPT = "edit";
   String JSR77_PROMPT = "jsr77";
   String PARTITIONS_PROMPT = "Partitions";
   String PARTITION_RUNTIMES_PROMPT = "PartitionRuntimes";
   String DOMAIN_PARTITION_RUNTIMES_PROMPT = "DomainPartitionRuntimes";
   String UNKNOWN_ERROR = "Unexpected Error: ";
   String THREAD_DUMP_FILE_NAME = "Thread_Dump";
   String userConfigFile = "userConfigFile";
   String userKeyFile = "userKeyFile";
   String timeout = "timeout";
   String _retryAttempts = "retryAttempts";
   String _retryDelay = "retryDelay";
   String IDD = "idd";
   String DEFAULT_URL = "t3://localhost:7001";
   String storeUserConfig = "storeUserConfig";
   String GLOBAL_SESSION_NAME = "default";
}
