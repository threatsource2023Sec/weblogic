package weblogic.management.patching.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtScriptEnvKeys {
   public static final String JAVA_HOME = "javaHome";
   public static final String MW_HOME = "mwHome";
   public static final String DOMAIN_DIR = "domainDir";
   public static final String DOMAIN_TMP = "domainTmp";
   public static final String PATCHED = "patched";
   public static final String BACKUP_DIR = "backupDir";
   public static final String NEW_JAVA_HOME = "newJavaHome";
   public static final String CURRENT_NODE_NAME = "currentNodeName";
   public static final String CURRENT_SERVER_NAMES = "currentServerNames";
   public static final String PARTITION_NAME = "partitionName";
   public static final String APPLICATION_INFO = "applicationInfo";
   protected List keyCollection = new ArrayList(Arrays.asList("javaHome", "mwHome", "domainDir", "domainTmp", "patched", "backupDir", "newJavaHome", "currentNodeName", "currentServerNames", "partitionName", "applicationInfo"));

   public List getKeyCollection() {
      return this.keyCollection;
   }
}
