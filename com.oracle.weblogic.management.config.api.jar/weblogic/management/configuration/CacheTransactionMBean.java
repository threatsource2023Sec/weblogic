package weblogic.management.configuration;

public interface CacheTransactionMBean extends ConfigurationMBean {
   String NONE = "None";
   String PESSIMISTIC = "Pessimistic";
   String OPTIMISTIC = "Optimistic";
   String READ_UNCOMMITTED = "ReadUncommitted";
   String READ_COMMITTED = "ReadCommitted";
   String REPEATABLE_READ = "RepeatableRead";

   String getConcurrency();

   void setConcurrency(String var1);

   boolean isConcurrencySet();

   String getIsolationLevel();

   void setIsolationLevel(String var1);

   boolean isIsolationLevelSet();
}
