package weblogic;

import weblogic.nodemanager.server.NMServer;

public final class NodeManager {
   public static void main(String[] argv) {
      if (argv.length > 0 && (argv[0].equalsIgnoreCase("-help") || argv[0].equalsIgnoreCase("help") || argv[0].equalsIgnoreCase("-h") || argv[0].equals("?") || argv[0].equals("-?"))) {
         System.out.println(getUsage());
      } else if (argv.length > 0 && argv[0].equalsIgnoreCase("-advanced")) {
         System.out.println(getAdvancedUsage());
      } else {
         NMServer.main(argv);
      }

   }

   public static String getUsage() {
      StringBuffer buff = new StringBuffer();
      buff.append("Usage: java [<Property>=<PropertyValue>] weblogic.NodeManager [OPTIONS]\n");
      buff.append("\t  where the following properties are specified as:\n");
      buff.append("\t  -DListenAddress \t\t[String, default 'localhost']\n");
      buff.append("\t  -DListenPort \t\t\t[int, default '5556']\n");
      buff.append("\t  -DPropertiesFile \t\t[String, default 'nodemanager.properties']\n");
      buff.append("\n");
      buff.append("\t  where options include:\n");
      buff.append("\t  -n <home>  Specify node manager home directory (default is PWD)\n");
      buff.append("\t  -f <file>  Specify node manager properties file\n");
      buff.append("\t             (default is NM_HOME/nodemanager.properties)\n");
      buff.append("\t  -v         Run in verbose mode\n");
      buff.append("\t  -d         Enable debug output to log file\n");
      buff.append("\t  -?, -h     Print this usage message\n");
      buff.append("\t  -advanced  Print advanced options\n");
      return buff.toString();
   }

   public static String getAdvancedUsage() {
      StringBuffer buff = new StringBuffer();
      buff.append("Usage: java [<Property>=<PropertyValue>] weblogic.NodeManager\n");
      buff.append("\t  where the following properties are specified as:\n");
      buff.append("\t  -DListenAddress \t\t[String, default 'localhost']\n");
      buff.append("\t  -DListenPort \t\t\t[int, default '5556']\n");
      buff.append("\t  -DWeblogicHome \t\t[String]\n");
      buff.append("\t    Specifies the root directory of the WebLogic\n");
      buff.append("\t    installation. This is used as the value for\n");
      buff.append("\t    -Dweblogic.RootDirectory\n");
      buff.append("\t  -DNativeVersionEnabled \t[boolean, default 'true']\n");
      buff.append("\t    Causes native libraries for the operating system to be used.\n");
      buff.append("\t    For UNIX systems other than Solaris, HP-UX, or Linux,\n");
      buff.append("\t    set this property to false to run Node Manager in \n");
      buff.append("\t    non-native mode. This will cause Node Manager to use the \n");
      buff.append("\t    start script specified by the StartScriptEnabled \n");
      buff.append("\t    property to start Managed Servers.\n");
      buff.append("\t  -DJavaHome \t\t\t[String]\n");
      buff.append("\t    Specifies the home directory of 'java'.\n");
      buff.append("\t    The managed servers will use the java (or 'java.exe')\n");
      buff.append("\t    from javaHome/bin directory when they are started.\n");
      buff.append("\n");
      buff.append("\t  -DDomainsFile \t\t[String, default 'NM_HOME/nodemanager.domains']\n");
      buff.append("\t    The name of the nodemanager.domains file.\n");
      buff.append("\t  -DDomainsFileEnabled \t\t[boolean, default 'true']\n");
      buff.append("\t    If set to true, use the file specified in DomainsFile. If false,\n");
      buff.append("\t    assumes the domain of the current directory or WL_HOME.\n");
      buff.append("\t  -Dweblogic.StartScriptName \t\t[String, default 'startWeblogic.sh/.cmd']\n");
      buff.append("\t    The name of the start script, located in the domain directory.\n");
      buff.append("\t  -Dweblogic.StartScriptEnabled \t\t[boolean, default 'true']\n");
      buff.append("\t    If true, use the start script specified by StartScriptName\n");
      buff.append("\t    to start a server. If false, invoke java directly to start a server.\n");
      buff.append("\n");
      buff.append("\t  -DLogFile \t\t\t[String, default 'NM_HOME/nodemanager.log']\n");
      buff.append("\t    Location of the NodeManager log file.\n");
      buff.append("\t  -DLogLimit \t\t\t[int, default unlimited]\n");
      buff.append("\t    Maximum size of the Node Manager log. When this limit is reached,\n");
      buff.append("\t    a new log file is started.\n");
      buff.append("\t  -DLogCount \t\t\t[int, default '1']\n");
      buff.append("\t    Maximum number of log files to create when LogLimit is reached.\n");
      buff.append("\t  -DLogAppend \t\t\t[boolean, default 'true']\n");
      buff.append("\t    If true, append to existing log files instead of creating a new\n");
      buff.append("\t    one when NodeManager is started.\n");
      buff.append("\t  -DLogToStderr \t\t[boolean, default 'true']\n");
      buff.append("\t    If true, log output is also sent to stderr. Setting this to false\n");
      buff.append("\t    will override the -v flag.\n");
      buff.append("\t  -DLogLevel \t\t\t[String, default 'WLLevel.INFO']\n");
      buff.append("\t    Severity level of logging used by NodeManager. Options: Trace\n");
      buff.append("\t    Debug, Info, Warning, Error, Notice, Critical, Alert, Emergency.\n");
      buff.append("\t  -DLogFormatter \t\t[String, default 'weblogic.nodemanager.server.LogFormatter']\n");
      buff.append("\t    Name of formatter class to use for NM log messages.\n");
      buff.append("\n");
      buff.append("\t  -DCrashRecoveryEnabled \t[boolean, default 'true']\n");
      buff.append("\t    Enables System Crash Recovery. If true, nodemanager will restart\n");
      buff.append("\t    crashed servers when it starts.\n");
      buff.append("\n");
      buff.append("\t  -DSecureListener \t\t[boolean, default 'true']\n");
      buff.append("\t    If true, use the secure listener. Otherwise use a plain socket.\n");
      buff.append("\t  -DCipherSuite \t\t[String, default value depends on JDK and installed JSSE providers]\n");
      buff.append("\t    Deprecated and replaced by CipherSuites since 12.1.3.\n");
      buff.append("\t    Name of the cipher suite to use with the secure listener.\n");
      buff.append("\t  -DCipherSuites \t\t[String, default value depends on JDK and installed JSSE providers]\n");
      buff.append("\t    Comma separated list of the cipher suite names to use with the secure listener.\n");
      buff.append("\n");
      buff.append("\t  -DKeyStores \t\t\t[String, default 'DemoIdentityAndDemoTrust']\n");
      buff.append("\t    Indicates the keystore configuration the nodemanager uses\n");
      buff.append("\t    to find its identity and trust.\n");
      buff.append("\t  -DJavaStandardTrustKeyStorePassPhrase \t[String, default none]\n");
      buff.append("\t    Specifies the password defined when creating the Trust keystore.\n");
      buff.append("\t    Weblogic only reads from the keystore, so whether or not you set.\n");
      buff.append("\t    This property depends on the requirements of the keystore.\n");
      buff.append("\t    This property is required when the KeyStores property is set as\n");
      buff.append("\t    CustomIdentityAndJavaStandardTrust or DemoIdentityAndDemoTrust.\n");
      buff.append("\t  -DCustomIdentityKeyStorePassPhrase \t\t[String, default none]\n");
      buff.append("\t    Specifies the password defined when creating the Identity keystore.\n");
      buff.append("\t    Weblogic only reads from the keystore, so whether or not you set.\n");
      buff.append("\t    This property depends on the requirements of the keystore.\n");
      buff.append("\t  -DCustomIdentityKeyStoreFileName \t\t[String, default none]\n");
      buff.append("\t    Specifies the name of the Identity keystore.\n");
      buff.append("\t    This property is required when the KeyStores property is set as\n");
      buff.append("\t    CustomIdentityAndCustomTrust or CustomIdentityAndJavaStandardTrust.\n");
      buff.append("\t  -DCustomIdentityAlias \t\t\t[String, default none]\n");
      buff.append("\t    Specifies the alias when loading the private key into\n");
      buff.append("\t    the keystore. This property is required when the KeyStores\n");
      buff.append("\t    property is set as CustomIdentityAndCustomTrust or\n");
      buff.append("\t    CustomIdentityAndJavaStandardTrust.\n");
      buff.append("\t  -DCustomIdentityPrivateKeyPassPhrase \t\t[String, default none]\n");
      buff.append("\t    Specifies the password used to retrieve the private key for\n");
      buff.append("\t    WebLogic Server from the Identity keystore. This property is\n");
      buff.append("\t    required when the KeyStores property is set as\n");
      buff.append("\t    CustomIdentityAndCustomTrust or CustomIdentityAndJavaStandardTrust.\n");
      buff.append("\t  -DCustomIdentityKeyStoreType \t\t\t[String, default keystore type from java.security ]\n");
      buff.append("\t    Specifies the type of the Identity Keystore. Generally,\n");
      buff.append("\t    this is JKS. This property is optional.\n");
      buff.append("\n");
      buff.append("\t  Additional properties that are used as defaults\n");
      buff.append("\t  when starting up Managed servers can be\n");
      buff.append("\t  specified. These are: \n");
      buff.append("\t  -Dbea.home \t\t\t[String]\n");
      buff.append("\t    Specifies the BEA_Home\n");
      buff.append("\t  -DWeblogicHome \t\t[String]\n");
      buff.append("\t    Specifies the root directory of the WebLogic\n");
      buff.append("\t    installation. This is used as the value for\n");
      buff.append("\t    -Dweblogic.RootDirectory\n");
      buff.append("\t  -Djava.security.policy \t[String]\n");
      buff.append("\t    Specifies the security policy to be used.\n");
      buff.append("\t    Note that in addition to being used as the\n");
      buff.append("\t    default security policy by the Managed servers\n");
      buff.append("\t    started by this NodeManager, this file also\n");
      buff.append("\t    specifies the policy file for this NodeManager.\n");
      buff.append("\n\n");
      buff.append("\t  You can specify all these properties in a properties file using\n");
      buff.append("\t  the following property.\n");
      buff.append("\t  -DPropertiesFile \t\t\t[String, default 'nodemanager.properties']\n");
      buff.append("\t  For more information see:\n");
      buff.append("\t  http://e-docs.bea.com/wls/docs103/nodemgr/intro.html");
      return buff.toString();
   }
}
