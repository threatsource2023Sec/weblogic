package org.apache.openjpa.conf;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;

public class MetaDataCacheMaintenance {
   private final BrokerFactory factory;
   private final OpenJPAConfiguration conf;
   private final boolean devpath;
   private Log log;

   public static void main(String[] args) {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      boolean devpath = opts.getBooleanProperty("scanDevPath", "ScanDevPath", true);
      ConfigurationProvider cp = new MapConfigurationProvider(opts);
      BrokerFactory factory = Bootstrap.newBrokerFactory(cp, (ClassLoader)null);

      try {
         MetaDataCacheMaintenance maint = new MetaDataCacheMaintenance(factory, devpath);
         if (args.length == 1) {
            if ("store".equals(args[0])) {
               maint.store();
               return;
            } else {
               if ("dump".equals(args[0])) {
                  maint.dump();
               } else {
                  usage();
               }

               return;
            }
         }

         usage();
      } finally {
         factory.close();
      }

   }

   /** @deprecated */
   public MetaDataCacheMaintenance(BrokerFactory factory, boolean devpath, boolean verbose) {
      this(factory, devpath);
   }

   public MetaDataCacheMaintenance(BrokerFactory factory, boolean devpath) {
      this.factory = factory;
      this.conf = factory.getConfiguration();
      this.devpath = devpath;
      this.log = this.conf.getLog("openjpa.Tool");
   }

   public void setLog(Log log) {
      this.log = log;
   }

   private static int usage() {
      System.err.println("Usage: java MetaDataCacheMaintenance [-scanDevPath t|f] [-<openjpa.PropertyName> value] store | dump");
      return -1;
   }

   public void store() {
      MetaDataRepository repos = this.conf.getMetaDataRepositoryInstance();
      repos.setSourceMode(31);
      Collection types = repos.loadPersistentTypes(this.devpath, (ClassLoader)null);
      Iterator iter = types.iterator();

      while(iter.hasNext()) {
         repos.getMetaData((Class)((Class)iter.next()), (ClassLoader)null, true);
      }

      this.loadQueries();
      this.log.info("The following data will be stored: ");
      this.log(repos, this.conf.getQueryCompilationCacheInstance());
      CacheMarshallersValue.getMarshallerById(this.conf, this.getClass().getName()).store(new Object[]{repos, this.conf.getQueryCompilationCacheInstance()});
   }

   private void loadQueries() {
      Broker broker = this.factory.newBroker();

      try {
         QueryMetaData[] qmds = this.conf.getMetaDataRepositoryInstance().getQueryMetaDatas();

         for(int i = 0; i < qmds.length; ++i) {
            this.loadQuery(broker, qmds[i]);
         }
      } finally {
         broker.close();
      }

   }

   private void loadQuery(Broker broker, QueryMetaData qmd) {
      try {
         Query q = broker.newQuery(qmd.getLanguage(), (Object)null);
         qmd.setInto(q);
         q.compile();
      } catch (Exception var4) {
         if (this.log.isTraceEnabled()) {
            this.log.warn("Skipping named query " + qmd.getName() + ": " + var4.getMessage(), var4);
         } else {
            this.log.warn("Skipping named query " + qmd.getName() + ": " + var4.getMessage());
         }
      }

   }

   public void dump() {
      Object[] os = (Object[])((Object[])CacheMarshallersValue.getMarshallerById(this.conf, this.getClass().getName()).load());
      if (os == null) {
         this.log.info("No cached data was found");
      } else {
         MetaDataRepository repos = (MetaDataRepository)os[0];
         Map qcc = (Map)os[1];
         this.log.info("The following data was found: ");
         this.log(repos, qcc);
      }
   }

   private void log(MetaDataRepository repos, Map qcc) {
      ClassMetaData[] metas = repos.getMetaDatas();
      this.log.info("  Types: " + metas.length);
      if (this.log.isTraceEnabled()) {
         for(int i = 0; i < metas.length; ++i) {
            this.log.trace("    " + metas[i].getDescribedType().getName());
         }
      }

      QueryMetaData[] qmds = repos.getQueryMetaDatas();
      this.log.info("  Queries: " + qmds.length);
      if (this.log.isTraceEnabled()) {
         for(int i = 0; i < qmds.length; ++i) {
            this.log.trace("    " + qmds[i].getName() + ": " + qmds[i].getQueryString());
         }
      }

      SequenceMetaData[] smds = repos.getSequenceMetaDatas();
      this.log.info("  Sequences: " + smds.length);
      if (this.log.isTraceEnabled()) {
         for(int i = 0; i < smds.length; ++i) {
            this.log.trace("    " + smds[i].getName());
         }
      }

      this.log.info("  Compiled queries: " + (qcc == null ? "0" : "" + qcc.size()));
      if (this.log.isTraceEnabled() && qcc != null) {
         Iterator iter = qcc.keySet().iterator();

         while(iter.hasNext()) {
            this.log.trace("    " + iter.next());
         }
      }

   }
}
