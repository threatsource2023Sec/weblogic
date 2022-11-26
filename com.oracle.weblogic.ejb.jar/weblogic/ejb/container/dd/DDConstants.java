package weblogic.ejb.container.dd;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DDConstants implements weblogic.ejb.spi.DDConstants {
   public static final short TX_NOT_SET = -1;
   public static final short TX_NOT_SUPPORTED = 0;
   public static final short TX_REQUIRED = 1;
   public static final short TX_SUPPORTS = 2;
   public static final short TX_REQUIRES_NEW = 3;
   public static final short TX_MANDATORY = 4;
   public static final short TX_NEVER = 5;
   public static final Map VALID_TX_ATTRIBUTES = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("NotSupported", Short.valueOf((short)0));
         this.put("Required", Short.valueOf((short)1));
         this.put("Supports", Short.valueOf((short)2));
         this.put("RequiresNew", Short.valueOf((short)3));
         this.put("Mandatory", Short.valueOf((short)4));
         this.put("Never", Short.valueOf((short)5));
      }
   });
   public static final List TX_ATTRIBUTE_STRINGS = Collections.unmodifiableList(Arrays.asList("NotSupported", "Required", "Supports", "RequiresNew", "Mandatory", "Never"));
   public static final String TEMPCOLUMNNAME = "WLS_TEMP";
   public static final int SELECT_FOR_UPDATE_DISABLED = 0;
   public static final int SELECT_FOR_UPDATE = 1;
   public static final int SELECT_FOR_UPDATE_NO_WAIT = 2;
   public static final int UPDATE_LOCK_NONE = 4;
   public static final int UPDATE_LOCK_AS_GENERATED = 5;
   public static final int UPDATE_LOCK_TX_LEVEL = 6;
   public static final int UPDATE_LOCK_TX_LEVEL_NO_WAIT = 7;
   public static final int DB_UNKNOWN = 0;
   public static final int DB_ORACLE = 1;
   public static final int DB_SQLSERVER = 2;
   public static final int DB_INFORMIX = 3;
   public static final int DB_DB2 = 4;
   public static final int DB_SYBASE = 5;
   public static final int DB_POINTBASE = 6;
   public static final int DB_SQLSERVER_2000 = 7;
   public static final int DB_MYSQL = 8;
   public static final int DB_DERBY = 9;
   public static final int DB_TIMESTEN = 10;
   public static final Map DBTYPE_MAP = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("ORACLE", 1);
         this.put("SQL_SERVER", 2);
         this.put("SQLSERVER", 2);
         this.put("INFORMIX", 3);
         this.put("DB2", 4);
         this.put("SYBASE", 5);
         this.put("POINTBASE", 6);
         this.put("SQLSERVER2000", 7);
         this.put("MYSQL", 8);
         this.put("DERBY", 9);
         this.put("TIMESTEN", 10);
      }
   });
   private static final String[] DBTYPE_NAMES = new String[]{"UNKNOWN", "ORACLE", "SQLSERVER", "INFORMIX", "DB2", "SYBASE", "POINTBASE", "SQLSERVER2000", "MYSQL", "DERBY", "TIMESTEN"};
   public static final int INSERT = 0;
   public static final int UPDATE = 1;
   public static final int DELETE = 2;
   public static final List DEST_CONN_MODES = Collections.unmodifiableList(Arrays.asList("LocalOnly", "EveryMember"));
   public static final int DEST_CONN_MODE_EVERY_MEMBER = 1;
   public static final int DEST_CONN_MODE_LOCAL_ONLY = 0;
   public static final List TOPIC_MESSAGE_DISTRIBUTION_MODES = Collections.unmodifiableList(Arrays.asList("Compatibility", "One-Copy-Per-Server", "One-Copy-Per-Application"));
   public static final int COMPATIBILITY = 0;
   public static final int ONE_COPY_PER_SERVER = 1;
   public static final int ONE_COPY_PER_APPLICATION = 2;
   public static final int MDB_STATUS_INITIALIZING = 0;
   public static final int MDB_STATUS_ACTIVE = 1;
   public static final int MDB_STATUS_RUNNING = 2;
   public static final int MDB_STATUS_SUSPENDED = 3;
   public static final int MDB_STATUS_ERROR = 4;
   public static final int MDB_STATUS_INACTIVE = 5;
   public static final int MDB_STATUS_SUSPENDING = 6;
   public static final int MDB_STATUS_UNDEPLOYING = 7;
   public static final int MDB_STATUS_UNDEPLOYED = 8;
   public static final List ACP_KEY_TOPIC_MESSAGE_PARTITION_MODES = Collections.unmodifiableList(Arrays.asList("Isolated", "Shared"));
   public static final int TOPIC_SUBSCRIPTION_IN_PARTITIONS_ISOLATED = 0;
   public static final int TOPIC_SUBSCRIPTION_IN_PARTITIONS_SHARED = 1;
   public static final String MESSAGE_DRIVEN_TYPE_NAME = "MessageDriven";
   public static final String ENTITY_TYPE_NAME = "Entity";
   public static final String MDB_CONNECTION_SUSPENDED_ON_START = "weblogic.mdbs.suspendConnectionOnStart";

   public static String getDBNameForType(int type) {
      return type < DBTYPE_NAMES.length && type >= 0 ? DBTYPE_NAMES[type] : "UNKNOWN";
   }
}
