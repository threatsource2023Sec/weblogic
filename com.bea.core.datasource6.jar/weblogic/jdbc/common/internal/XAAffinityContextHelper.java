package weblogic.jdbc.common.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.SystemException;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.utils.StackTraceUtils;

public class XAAffinityContextHelper extends AbstractAffinityContextHelper {
   TransactionManager tm = null;

   XAAffinityContextHelper() {
      this.tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   public String[] getKeys() {
      try {
         Transaction tx = (Transaction)this.tm.getTransaction();
         if (tx == null) {
            return null;
         } else {
            Map properties = tx.getProperties();
            if (properties != null && properties.size() != 0) {
               List keylist = new ArrayList();
               Set keys = properties.keySet();
               Iterator var5 = keys.iterator();

               while(var5.hasNext()) {
                  String key = (String)var5.next();
                  if (key.startsWith("weblogic.jdbc.affinity.")) {
                     keylist.add(key);
                  }
               }

               return (String[])keylist.toArray(new String[keylist.size()]);
            } else {
               return null;
            }
         }
      } catch (SystemException var7) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var7));
         }

         return null;
      }
   }

   public boolean isApplicationContextAvailable() {
      try {
         Transaction tx = (Transaction)this.tm.getTransaction();
         if (tx != null) {
            return true;
         }
      } catch (SystemException var2) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var2));
         }
      }

      return false;
   }

   public Object getAffinityContext(String key) {
      try {
         Transaction tx = (Transaction)this.tm.getTransaction();
         Object wlContext = this.getAffinityContext(tx, key);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getAffinityContext() returns " + wlContext);
         }

         return wlContext;
      } catch (SystemException var4) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug(StackTraceUtils.throwable2StackTrace(var4));
         }

         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getAffinityContext() returns null");
         }

         return null;
      }
   }

   public Object getAffinityContext(Transaction tx, String key) {
      if (tx == null) {
         return null;
      } else {
         Object wlContext = tx.getProperty(key);
         return wlContext;
      }
   }

   public boolean setAffinityContext(String key, Object context) {
      if (context == null) {
         return false;
      } else {
         Transaction tx = null;

         try {
            tx = (Transaction)this.tm.getTransaction();
            if (tx != null) {
               Object wlContext = tx.getProperty(key);
               if (wlContext == null) {
                  tx.setProperty(key, (Serializable)context);
                  if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
                     this.debug("setAffinityContext() key= " + key + ", context=" + context);
                  }

                  return true;
               }
            }
         } catch (SystemException var5) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug(StackTraceUtils.throwable2StackTrace(var5));
            }
         }

         return false;
      }
   }

   public boolean setAffinityContext(Transaction tx, String key, Object context) {
      if (tx == null) {
         return false;
      } else {
         tx.setProperty(key, (Serializable)context);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("setAffinityContext() tx= " + tx.getXid() + ", key= " + key + ", context=" + context);
         }

         return true;
      }
   }
}
