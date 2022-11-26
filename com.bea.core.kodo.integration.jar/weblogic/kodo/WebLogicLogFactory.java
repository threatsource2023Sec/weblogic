package weblogic.kodo;

import kodo.bea.BEALogFactory;
import kodo.bea.BEALogImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLogger;

public class WebLogicLogFactory extends BEALogFactory {
   private final BEALogImpl.TraceEnabledHook UNKNOWN = new BEALogImpl.TraceEnabledHook() {
      public boolean isTraceEnabled() {
         return false;
      }
   };
   private final BEALogImpl.TraceEnabledHook METADATA = new TraceEnabledHookImpl("DebugJpaMetaData");
   private final BEALogImpl.TraceEnabledHook ENHANCE = new TraceEnabledHookImpl("DebugJpaEnhance");
   private final BEALogImpl.TraceEnabledHook RUNTIME = new TraceEnabledHookImpl("DebugJpaRuntime");
   private final BEALogImpl.TraceEnabledHook QUERY = new TraceEnabledHookImpl("DebugJpaQuery");
   private final BEALogImpl.TraceEnabledHook DATACACHE = new TraceEnabledHookImpl("DebugJpaDataCache");
   private final BEALogImpl.TraceEnabledHook TOOL = new TraceEnabledHookImpl("DebugJpaTool");
   private final BEALogImpl.TraceEnabledHook MANAGE = new TraceEnabledHookImpl("DebugJpaManage");
   private final BEALogImpl.TraceEnabledHook PROFILE = new TraceEnabledHookImpl("DebugJpaProfile");
   private final BEALogImpl.TraceEnabledHook SQL = new TraceEnabledHookImpl("DebugJpaJdbcSql");
   private final BEALogImpl.TraceEnabledHook JDBC = new TraceEnabledHookImpl("DebugJpaJdbcJdbc");
   private final BEALogImpl.TraceEnabledHook SCHEMA = new TraceEnabledHookImpl("DebugJpaJdbcSchema");

   protected BEALogImpl newBEALogImpl(String channel, MessageLogger ml, MessageDispatcher md, String subsystem, String bundleBaseName) {
      BEALogImpl log = super.newBEALogImpl(channel, ml, md, subsystem, bundleBaseName);
      BEALogImpl.TraceEnabledHook hook;
      if (channel.endsWith("MetaData")) {
         hook = this.METADATA;
      } else if (channel.endsWith("Enhance")) {
         hook = this.ENHANCE;
      } else if (channel.endsWith("Runtime")) {
         hook = this.RUNTIME;
      } else if (channel.endsWith("Query")) {
         hook = this.QUERY;
      } else if (channel.endsWith("DataCache")) {
         hook = this.DATACACHE;
      } else if (channel.endsWith("Tool")) {
         hook = this.TOOL;
      } else if (channel.endsWith("Manage")) {
         hook = this.MANAGE;
      } else if (channel.endsWith("Profile")) {
         hook = this.PROFILE;
      } else if (channel.endsWith("SQL")) {
         hook = this.SQL;
      } else if (channel.endsWith("JDBC")) {
         hook = this.JDBC;
      } else if (channel.endsWith("Schema")) {
         hook = this.SCHEMA;
      } else {
         hook = this.UNKNOWN;
      }

      log.setTraceEnabledHook(hook);
      return log;
   }

   public class TraceEnabledHookImpl implements BEALogImpl.TraceEnabledHook {
      private final DebugLogger logger;

      public TraceEnabledHookImpl(String debugScope) {
         this.logger = DebugLogger.getDebugLogger(debugScope);
      }

      public boolean isTraceEnabled() {
         return this.logger.isDebugEnabled();
      }
   }
}
