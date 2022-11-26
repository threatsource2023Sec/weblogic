package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Entity {
   String ejbName();

   String primKeyClass();

   String tableName() default "UNSPECIFIED";

   VerifyRows verifyRows() default Entity.VerifyRows.UNSPECIFIED;

   Constants.Bool clientsOnSameServer() default Constants.Bool.UNSPECIFIED;

   String invalidationTarget() default "UNSPECIFIED";

   Constants.Bool useCallerIdentity() default Constants.Bool.UNSPECIFIED;

   Constants.Bool cacheBetweenTransactions() default Constants.Bool.UNSPECIFIED;

   String defaultDbmsTablesDdl() default "UNSPECIFIED";

   Constants.TransactionAttribute defaultTransaction() default Constants.TransactionAttribute.UNSPECIFIED;

   Constants.Bool checkExistsOnMethod() default Constants.Bool.UNSPECIFIED;

   String optimisticColumn() default "UNSPECIFIED";

   Constants.Bool orderDatabaseOperations() default Constants.Bool.UNSPECIFIED;

   String runAsIdentityPrincipal() default "UNSPECIFIED";

   String runAsPrincipalName() default "UNSPECIFIED";

   DelayDatabaseInsertUntil delayDatabaseInsertUntil() default Entity.DelayDatabaseInsertUntil.UNSPECIFIED;

   Constants.Bool enableCallByReference() default Constants.Bool.UNSPECIFIED;

   ValidateDBSchemaWith validateDbSchemaWith() default Entity.ValidateDBSchemaWith.UNSPECIFIED;

   String dispatchPolicy() default "UNSPECIFIED";

   String lockOrder() default "UNSPECIFIED";

   Constants.Bool findersLoadBean() default Constants.Bool.UNSPECIFIED;

   Constants.ConcurrencyStrategy concurrencyStrategy() default Constants.ConcurrencyStrategy.UNSPECIFIED;

   PersistenceType persistenceType() default Entity.PersistenceType.UNSPECIFIED;

   String maxBeansInCache() default "UNSPECIFIED";

   Constants.Bool primKeyClassNogen() default Constants.Bool.UNSPECIFIED;

   String initialBeansInFreePool() default "UNSPECIFIED";

   VerifyColumns verifyColumns() default Entity.VerifyColumns.UNSPECIFIED;

   String idleTimeoutSeconds() default "UNSPECIFIED";

   String transTimeoutSeconds() default "UNSPECIFIED";

   Constants.Bool enableBatchOperations() default Constants.Bool.UNSPECIFIED;

   String maxBeansInFreePool() default "UNSPECIFIED";

   String disableWarning() default "UNSPECIFIED";

   String readTimeoutSeconds() default "UNSPECIFIED";

   DatabaseType databaseType() default Entity.DatabaseType.UNSPECIFIED;

   Constants.Bool reentrant() default Constants.Bool.UNSPECIFIED;

   Constants.HomeLoadAlgorithm homeLoadAlgorithm() default Constants.HomeLoadAlgorithm.UNSPECIFIED;

   Constants.Bool delayUpdatesUntilEndOfTx() default Constants.Bool.UNSPECIFIED;

   String runAs() default "UNSPECIFIED";

   String abstractSchemaName() default "UNSPECIFIED";

   Constants.Bool dbIsShared() default Constants.Bool.UNSPECIFIED;

   Constants.Bool enableDynamicQueries() default Constants.Bool.UNSPECIFIED;

   Constants.Bool homeIsClusterable() default Constants.Bool.UNSPECIFIED;

   String dataSourceName() default "UNSPECIFIED";

   Constants.Bool useSelectForUpdate() default Constants.Bool.UNSPECIFIED;

   InstanceLockOrder instanceLockOrder() default Entity.InstanceLockOrder.UNSPECIFIED;

   Constants.Bool clusterInvalidationDisabled() default Constants.Bool.UNSPECIFIED;

   String remoteClientTimeout() default "UNSPECIFIED";

   String homeCallRouterClassName() default "UNSPECIFIED";

   String networkAccessPoint() default "UNSPECIFIED";

   String unknownPrimaryKeyField() default "UNSPECIFIED";

   /** @deprecated */
   @Deprecated
   String timerPersistenceStore() default "UNSPECIFIED";

   String timerPersistentStore() default "UNSPECIFIED";

   public static enum InstanceLockOrder {
      UNSPECIFIED,
      ACCESS_ORDER,
      VALUE_ORDER;
   }

   public static enum DatabaseType {
      UNSPECIFIED,
      DB2,
      INFORMIX,
      ORACLE,
      SQL_SERVER,
      SYBASE,
      POINTBASE,
      SQL_SERVER2000;
   }

   public static enum VerifyColumns {
      UNSPECIFIED,
      READ,
      MODIFIED,
      VERSION,
      TIMESTAMP;
   }

   public static enum PersistenceType {
      UNSPECIFIED,
      CMP,
      BMP;
   }

   public static enum ValidateDBSchemaWith {
      UNSPECIFIED,
      META_DATA,
      TABLE_QUERY;
   }

   public static enum DelayDatabaseInsertUntil {
      UNSPECIFIED,
      EJB_CREATE,
      EJB_POST_CREATE;
   }

   public static enum VerifyRows {
      UNSPECIFIED,
      READ,
      MODIFIED;
   }
}
