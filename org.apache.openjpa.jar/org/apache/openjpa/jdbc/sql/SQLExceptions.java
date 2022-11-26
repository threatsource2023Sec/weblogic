package org.apache.openjpa.jdbc.sql;

import java.sql.SQLException;
import java.util.LinkedList;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.StoreException;

public class SQLExceptions {
   private static final SQLException[] EMPTY_EXCEPS = new SQLException[0];

   public static OpenJPAException getStore(SQLException se) {
      return getStore((SQLException)se, (Object)null, (DBDictionary)null);
   }

   public static OpenJPAException getStore(SQLException se, Object failed) {
      return getStore((SQLException)se, (Object)failed, (DBDictionary)null);
   }

   public static OpenJPAException getStore(SQLException se, DBDictionary dict) {
      return getStore(se.getMessage(), se, dict);
   }

   public static OpenJPAException getStore(SQLException se, Object failed, DBDictionary dict) {
      return getStore(se.getMessage(), se, failed, dict);
   }

   public static OpenJPAException getStore(Localizer.Message msg, SQLException se, DBDictionary dict) {
      return getStore(msg.getMessage(), se, (Object)null, dict);
   }

   public static OpenJPAException getStore(String msg, SQLException se, DBDictionary dict) {
      return getStore(msg, se, (Object)null, dict);
   }

   public static OpenJPAException getStore(String msg, SQLException se, Object failed, DBDictionary dict) {
      if (msg == null) {
         msg = se.getClass().getName();
      }

      SQLException[] ses = getSQLExceptions(se);
      return dict == null ? (new StoreException(msg)).setFailedObject(failed).setNestedThrowables(ses) : dict.newStoreException(msg, ses, failed);
   }

   private static SQLException[] getSQLExceptions(SQLException se) {
      if (se == null) {
         return EMPTY_EXCEPS;
      } else {
         LinkedList errs;
         for(errs = new LinkedList(); se != null && !errs.contains(se); se = se.getNextException()) {
            errs.add(se);
         }

         return (SQLException[])((SQLException[])errs.toArray(new SQLException[errs.size()]));
      }
   }
}
