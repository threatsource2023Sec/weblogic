package com.bea.common.ldap;

import com.bea.common.ldap.exps.LDAPExpression;
import java.util.BitSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.rop.MergedResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.StoreException;

public class LDAPStoreQuery extends ExpressionStoreQuery {
   private static final String HEADER = "LDAPStoreQuery: ";
   private LDAPStoreManager manager;
   private Log log;
   private ParserFactory pFac;

   public LDAPStoreQuery(ExpressionParser parser, LDAPStoreManager manager, Log log, ParserFactory pFac) {
      super(parser);
      this.manager = manager;
      this.log = log;
      this.pFac = pFac;
   }

   protected Number executeDelete(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: executeDelete(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + ")");
      }

      return null;
   }

   protected ResultObjectProvider executeQuery(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, boolean lrs, long startIdx, long endIdx) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: executeQuery(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + "," + lrs + "," + startIdx + "," + endIdx + ")");
      }

      ResultObjectProvider[] rops = new ResultObjectProvider[parsed.length];

      for(int i = 0; i < rops.length; ++i) {
         String loc = types[i].getStringExtension("com.bea.common.security", "ldap-objectclass");
         String filter;
         if (loc != null) {
            int idx = loc.indexOf(44);
            filter = "(objectclass=" + (idx >= 0 ? loc.substring(0, idx) : loc) + ")";
         } else {
            filter = "(objectclass=" + types[i].getDescribedType().getSimpleName() + ")";
         }

         FetchConfiguration fc = this.getContext().getFetchConfiguration();
         DataLoader dl = this.pFac.getDataLoader(types[i]);
         LDAPExpressionFactory fact = (LDAPExpressionFactory)facts[i];
         String x = ((LDAPExpression)parsed[i].filter).getFilter(fact, params);
         if (x != null && x.length() > 0) {
            if (x.startsWith("(&")) {
               x = x.substring(2, x.length() - 1);
            }

            filter = "(&" + filter + x + ")";
         }

         LDAPSearchResults results = null;
         LDAPConfiguration conf = this.manager.getConfiguration();

         try {
            LDAPConnection con = conf.getConnection();

            try {
               String b = dl.createSearchBase(types[i], ((LDAPExpression)parsed[i].filter).getScopingExpressions(), params);
               String[] lfetch = dl.createFetchList((BitSet)null, fc);
               if (this.log.isTraceEnabled()) {
                  this.log.trace("LDAPStoreQuery: Performing LDAP search... base: " + b + " filter: " + filter);
               }

               results = con.search(b, 2, filter, lfetch, false);
               if (this.log.isTraceEnabled()) {
                  if (results != null) {
                     this.log.trace("LDAPStoreQuery: result count: " + results.getCount());
                  } else {
                     this.log.trace("LDAPStoreQuery: no results found");
                  }
               }
            } finally {
               conf.releaseConnection(con);
            }
         } catch (LDAPException var30) {
            if (var30.getLDAPResultCode() != 32 && var30.getLDAPResultCode() != 94) {
               if (this.log.isTraceEnabled()) {
                  this.log.trace("LDAPStoreQuery: LDAP error code: " + var30.getLDAPResultCode());
               }

               throw (new StoreException(var30)).setFatal(true);
            }

            if (this.log.isTraceEnabled()) {
               this.log.trace("LDAPStoreQuery: no results found");
            }
         }

         rops[i] = this.manager.getSearchResults(types[i], fc, results);
      }

      return new MergedResultObjectProvider(rops);
   }

   protected ResultObjectProvider executeQuery(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, StoreQuery.Range range) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: executeQuery(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + "," + range.lrs + "," + range.start + "," + range.end + ")");
      }

      ResultObjectProvider[] rops = new ResultObjectProvider[parsed.length];

      for(int i = 0; i < rops.length; ++i) {
         String loc = types[i].getStringExtension("com.bea.common.security", "ldap-objectclass");
         String filter;
         if (loc != null) {
            int idx = loc.indexOf(44);
            filter = "(objectclass=" + (idx >= 0 ? loc.substring(0, idx) : loc) + ")";
         } else {
            filter = "(objectclass=" + types[i].getDescribedType().getSimpleName() + ")";
         }

         FetchConfiguration fc = this.getContext().getFetchConfiguration();
         DataLoader dl = this.pFac.getDataLoader(types[i]);
         LDAPExpressionFactory fact = (LDAPExpressionFactory)facts[i];
         String x = ((LDAPExpression)parsed[i].filter).getFilter(fact, params);
         if (x != null && x.length() > 0) {
            if (x.startsWith("(&")) {
               x = x.substring(2, x.length() - 1);
            }

            filter = "(&" + filter + x + ")";
         }

         LDAPSearchResults results = null;
         LDAPConfiguration conf = this.manager.getConfiguration();

         try {
            LDAPConnection con = conf.getConnection();

            try {
               String b = dl.createSearchBase(types[i], ((LDAPExpression)parsed[i].filter).getScopingExpressions(), params);
               String[] lfetch = dl.createFetchList((BitSet)null, fc);
               if (this.log.isTraceEnabled()) {
                  this.log.trace("LDAPStoreQuery: Performing LDAP search... base: " + b + " filter: " + filter);
               }

               results = con.search(b, 2, filter, lfetch, false);
               if (this.log.isTraceEnabled()) {
                  if (results != null) {
                     this.log.trace("LDAPStoreQuery: result count: " + results.getCount());
                  } else {
                     this.log.trace("LDAPStoreQuery: no results found");
                  }
               }
            } finally {
               conf.releaseConnection(con);
            }
         } catch (LDAPException var26) {
            if (var26.getLDAPResultCode() != 32 && var26.getLDAPResultCode() != 94) {
               if (this.log.isTraceEnabled()) {
                  this.log.trace("LDAPStoreQuery: LDAP error code: " + var26.getLDAPResultCode());
               }

               throw (new StoreException(var26)).setFatal(true);
            }

            if (this.log.isTraceEnabled()) {
               this.log.trace("LDAPStoreQuery: no results found");
            }
         }

         rops[i] = this.manager.getSearchResults(types[i], fc, results);
      }

      return new MergedResultObjectProvider(rops);
   }

   protected Number executeUpdate(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: executeUpdate(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + ")");
      }

      return null;
   }

   protected ExpressionFactory getExpressionFactory(ClassMetaData type) {
      return new LDAPExpressionFactory(type, this.manager, this.log);
   }

   public boolean supportsDataStoreExecution() {
      return true;
   }

   protected String[] getDataStoreActions(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, long startIdx, long endIdx) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: getDataStoreActions(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + "," + startIdx + "," + endIdx + ")");
      }

      return null;
   }

   protected String[] getDataStoreActions(StoreQuery.Executor ex, ClassMetaData base, ClassMetaData[] types, boolean subclasses, ExpressionFactory[] facts, QueryExpressions[] parsed, Object[] params, StoreQuery.Range range) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPStoreQuery: getDataStoreActions(" + ex + "," + base + "," + this.printArray(types) + "," + subclasses + "," + this.printArray(facts) + "," + this.printArray(parsed) + "," + this.printArray(params) + "," + range.lrs + "," + range.start + "," + range.end + ")");
      }

      return null;
   }

   private String printArray(Object[] arg) {
      if (arg != null) {
         StringBuilder sb = new StringBuilder();
         sb.append('[');

         for(int i = 0; i < arg.length; ++i) {
            if (i > 0) {
               sb.append(',');
            }

            sb.append(arg[i]);
         }

         sb.append(']');
         return sb.toString();
      } else {
         return "null";
      }
   }
}
