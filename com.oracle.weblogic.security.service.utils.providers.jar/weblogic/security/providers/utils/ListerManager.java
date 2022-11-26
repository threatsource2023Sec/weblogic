package weblogic.security.providers.utils;

import com.bea.common.security.SecurityLogger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.utils.InvalidCursorException;

public abstract class ListerManager {
   private static HashMap listers = new HashMap();
   private static int nextCursorIndex = 0;
   private static int listerTimeout = 0;
   private static int purgeInterval = 0;
   private static Date nextPurgeDate = null;
   private static int maxListers = 0;

   private static int getIntProperty(String propertyName, int defaultValue) {
      String prop = System.getProperty(propertyName);
      if (prop != null && prop.length() > 0) {
         try {
            return Integer.parseInt(prop);
         } catch (Exception var4) {
         }
      }

      return defaultValue;
   }

   private static void setNextPurgeDate() {
      if (purgeInterval > 0) {
         Date now = new Date();
         nextPurgeDate = new Date(now.getTime() + (long)purgeInterval);
      } else {
         nextPurgeDate = null;
      }

   }

   private static void purgeExpiredLists() {
      if (nextPurgeDate != null) {
         Date now = new Date();
         if (!now.before(nextPurgeDate)) {
            Set entries = listers.entrySet();
            Iterator i = listers.values().iterator();

            while(i.hasNext()) {
               ListerEntry entry = (ListerEntry)i.next();
               if (entry.isExpired()) {
                  i.remove();
               }
            }

            setNextPurgeDate();
         }
      }
   }

   public static synchronized String addLister(Lister lister) {
      purgeExpiredLists();
      if (listers.size() >= maxListers) {
         lister.close();
         throw new RuntimeException(SecurityLogger.getMaximumListersExceeded(maxListers, listerTimeout));
      } else {
         String cursor = "Cursor_" + nextCursorIndex;
         ++nextCursorIndex;
         listers.put(cursor, new ListerEntry(lister, listerTimeout));
         return cursor;
      }
   }

   protected static Lister _getLister(String cursor) throws InvalidCursorException {
      Object o = listers.get(cursor);
      if (o == null) {
         throw new InvalidCursorException(SecurityLogger.getCursorNotFound(cursor));
      } else {
         ListerEntry entry = (ListerEntry)o;
         if (entry.isExpired()) {
            listers.remove(cursor);
            throw new InvalidCursorException(SecurityLogger.getCursorNotFound(cursor));
         } else {
            return entry.getLister();
         }
      }
   }

   public static synchronized Lister getLister(String cursor) throws InvalidCursorException {
      return _getLister(cursor);
   }

   public static boolean haveCurrent(String cursor) throws InvalidCursorException {
      return getLister(cursor).haveCurrent();
   }

   public static void advance(String cursor) throws InvalidCursorException {
      getLister(cursor).advance();
   }

   public static void close(String cursor) throws InvalidCursorException {
      Lister lister = null;
      Class var2 = ListerManager.class;
      synchronized(ListerManager.class) {
         lister = _getLister(cursor);
         listers.remove(cursor);
      }

      if (lister != null) {
         lister.close();
      }

   }

   static {
      maxListers = getIntProperty("weblogic.security.providers.utils.MaxListers", 1000);
      listerTimeout = getIntProperty("weblogic.security.providers.utils.ListerTimeout", 3600000);
      purgeInterval = listerTimeout / 2;
      setNextPurgeDate();
   }

   public static class ListerEntry {
      private Lister lister;
      private Date expirationDate;

      private ListerEntry(Lister lister, int timeout) {
         this.lister = lister;
         if (timeout > 0) {
            Date now = new Date();
            this.expirationDate = new Date(now.getTime() + (long)timeout);
         } else {
            this.expirationDate = null;
         }

      }

      private Lister getLister() {
         return this.lister;
      }

      private boolean isExpired() {
         if (this.expirationDate == null) {
            return false;
         } else {
            Date now = new Date();
            if (now.before(this.expirationDate)) {
               return false;
            } else {
               this.lister.close();
               return true;
            }
         }
      }

      // $FF: synthetic method
      ListerEntry(Lister x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   public interface Lister {
      boolean haveCurrent();

      void advance();

      void close();
   }
}
