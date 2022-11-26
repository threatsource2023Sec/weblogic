package weblogic.cluster.migration;

public interface Migratable {
   int INACTIVE = 0;
   int ACTIVE = 1;
   int ACTIVATING = 2;
   int DEFAULT_ORDER = 100;
   int EARLY_ORDER = -900;
   int FILESTORE_ORDER = -1900;
   int LATE_ORDER = 1100;
   int MDB_ORDER = Integer.MAX_VALUE;

   String getName();

   void migratableInitialize() throws MigrationException;

   void migratableActivate() throws MigrationException;

   void migratableDeactivate() throws MigrationException;

   int getOrder();
}
