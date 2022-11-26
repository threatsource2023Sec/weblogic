package weblogic.entitlement.data.ldap;

import java.util.Properties;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;
import weblogic.entitlement.data.BaseResource;
import weblogic.security.shared.LoggerWrapper;

abstract class ECursorBase {
   private static int cursorId = 1;
   private String cursorName;
   private LDAPConnection conn;
   private LDAPSearchResults results;
   private LDAPEntry currEntry;
   private int currEntryNum;
   private int maximumToReturn;
   protected EData data;
   protected LoggerWrapper traceLogger;

   public ECursorBase(String cursorName, LDAPConnection conn, LDAPSearchResults results, int maximumToReturn, EData data, LoggerWrapper traceLogger) {
      this.traceLogger = traceLogger;
      this.data = data;
      this.conn = conn;
      this.results = results;
      this.maximumToReturn = maximumToReturn;
      this.currEntry = null;
      this.currEntryNum = 0;
      this.cursorName = cursorName + this.hashCode() + this.getNextCursorId();
      if (results != null) {
         if (traceLogger != null && traceLogger.isDebugEnabled()) {
            traceLogger.debug("ECursorBase has more elements: " + results.hasMoreElements());
         }

         if (!results.hasMoreElements()) {
            this.conn = null;
            if (conn != null) {
               EData.releaseConnection(conn);
            }
         } else {
            this.advance();
         }
      }

   }

   public abstract Properties getCurrentProperties();

   public void advance() {
      this.advance(true);
   }

   protected void advance(boolean addEntryNum) {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("advance(" + addEntryNum + ")");
      }

      this.currEntry = null;

      try {
         if (this.results == null || !this.results.hasMoreElements() || this.maximumToReturn != 0 && this.currEntryNum >= this.maximumToReturn) {
            this.close();
         } else {
            this.currEntry = this.results.next();
            if (addEntryNum) {
               ++this.currEntryNum;
            }
         }
      } catch (LDAPException var3) {
         if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
            this.traceLogger.debug("LDAPException while trying to advance cursor");
         }

         this.close();
         EData var10000 = this.data;
         EData.checkStorageException(var3);
      }

   }

   public void close() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("close");
      }

      boolean var6 = false;

      EData var10000;
      LDAPConnection c;
      label153: {
         try {
            try {
               var6 = true;
               if (this.results != null) {
                  if (this.conn != null) {
                     if (this.traceLogger != null) {
                        this.traceLogger.debug("ECursorBase abandoning search results");
                     }

                     LDAPSearchResults r = this.results;
                     this.results = null;
                     this.conn.abandon(r);
                     var6 = false;
                  } else {
                     var6 = false;
                  }
               } else {
                  var6 = false;
               }
               break label153;
            } catch (LDAPException var7) {
               if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
                  this.traceLogger.debug("LDAPException while trying to abandon results");
               }
            }

            var10000 = this.data;
            EData.checkStorageException(var7);
            var6 = false;
         } finally {
            if (var6) {
               if (this.conn != null) {
                  if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
                     this.traceLogger.debug("ECursorBase releasing connection");
                  }

                  LDAPConnection c = this.conn;
                  this.conn = null;
                  var10000 = this.data;
                  EData.releaseConnection(c);
               }

            }
         }

         if (this.conn != null) {
            if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
               this.traceLogger.debug("ECursorBase releasing connection");
            }

            c = this.conn;
            this.conn = null;
            var10000 = this.data;
            EData.releaseConnection(c);
         }

         return;
      }

      if (this.conn != null) {
         if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
            this.traceLogger.debug("ECursorBase releasing connection");
         }

         c = this.conn;
         this.conn = null;
         var10000 = this.data;
         EData.releaseConnection(c);
      }

   }

   public boolean haveCurrent() {
      return this.currEntry != null;
   }

   public String getCursorName() {
      return this.cursorName;
   }

   protected LDAPEntry getCurrentEntry() {
      return this.currEntry;
   }

   protected String getEntitlement(BaseResource res) {
      String eexpr = res.getEntitlement();
      return eexpr != null ? eexpr : "";
   }

   private synchronized int getNextCursorId() {
      return cursorId++;
   }
}
