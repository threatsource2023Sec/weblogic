package weblogic.security.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPCache;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import weblogic.security.shared.LoggerWrapper;

public final class LDAPConnectionHelper {
   private LDAPConnection conn;
   private Pool pool;
   private boolean isWrite;
   private static final String OBJECTCLASS_ATTR = "objectclass";
   private static LoggerWrapper log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");

   private boolean isDebug() {
      return log.isDebugEnabled();
   }

   private void _debug(String msg) {
      if (log.isDebugEnabled()) {
         log.debug(msg);
      }

   }

   private void debug(String method, String info) {
      this._debug("LDAPConnectionHelper." + method + ": " + info);
   }

   public LDAPConnectionHelper(Pool pool, boolean isWrite, LoggerWrapper log) {
      String method = "constructor";
      if (log != null) {
         setLog(log);
      }

      this.pool = pool;
      this.isWrite = isWrite;

      try {
         this.conn = (LDAPConnection)pool.getInstance();
         if (this.isDebug()) {
            this.debug(method, "conn:" + this.conn);
         }

      } catch (InvocationTargetException var6) {
         throw new RuntimeException(var6);
      }
   }

   private static void setLog(LoggerWrapper logger) {
      log = logger;
   }

   public void setReadOnly() {
      this.isWrite = false;
   }

   public LDAPConnection getConnection() {
      return this.conn;
   }

   public void done() {
      String method = "done";
      if (this.conn != null) {
         if (this.isDebug()) {
            this.debug(method, "conn:" + this.conn);
         }

         if (this.isWrite) {
            LDAPCache cache = this.conn.getCache();
            if (cache != null) {
               cache.flushEntries((String)null, 0);
            }
         }

         this.pool.returnInstance(this.conn);
         this.conn = null;
      }
   }

   public void error() {
      String method = "error";
      if (this.conn != null) {
         if (this.isDebug()) {
            this.debug(method, "conn:" + this.conn);
         }

         try {
            this.conn.disconnect();
         } catch (Exception var3) {
         }

         this.conn = null;
      }
   }

   public boolean ensureDirectoryExists(String dn, String objectClass, String leafAttr, String leafValue) throws LDAPException {
      String method = "ensureDirectoryExists";
      if (this.isDebug()) {
         this.debug(method, "dn=\"" + dn + "\", objectClass=" + objectClass + ", leafAttr=" + leafAttr + ", leafValue=" + leafValue);
      }

      try {
         String[] noAttrs = new String[]{"1.1"};
         this.conn.read(dn, noAttrs);
         if (this.isDebug()) {
            this.debug(method, "directory already exists");
         }

         return false;
      } catch (LDAPException var12) {
         if (var12.getLDAPResultCode() == 32) {
            String[] objectClassValues = new String[]{"top", objectClass};
            LDAPAttributeSet attribSet = new LDAPAttributeSet();
            attribSet.add(new LDAPAttribute("objectclass", objectClassValues));
            attribSet.add(new LDAPAttribute(leafAttr, leafValue));
            LDAPEntry entry = new LDAPEntry(dn, attribSet);

            try {
               this.conn.add(entry);
            } catch (LDAPException var11) {
               if (var11.getLDAPResultCode() == 68) {
                  if (this.isDebug()) {
                     this.debug(method, "directory already exists or just created by other component");
                  }

                  return false;
               }

               throw var11;
            }

            if (this.isDebug()) {
               this.debug(method, "created directory");
            }

            return true;
         } else {
            throw var12;
         }
      }
   }

   private static String escapeSearchFilterStringAttr(String value, boolean escapeAsterisk) {
      int len = value.length();
      StringBuffer buf = new StringBuffer(len);

      for(int i = 0; i < len; ++i) {
         char ch = value.charAt(i);
         switch (ch) {
            case '\u0000':
               buf.append("\\00");
               break;
            case '(':
               buf.append("\\28");
               break;
            case ')':
               buf.append("\\29");
               break;
            case '*':
               if (escapeAsterisk) {
                  buf.append("\\2a");
               } else {
                  buf.append(ch);
               }
               break;
            case '\\':
               buf.append("\\5c");
               break;
            default:
               buf.append(ch);
         }
      }

      return buf.toString();
   }

   public static String escapeSearchFilterLiteralStringAttr(String value) {
      return escapeSearchFilterStringAttr(value, true);
   }

   public static String escapeSearchFilterWildcardStringAttr(String value) {
      return escapeSearchFilterStringAttr(value, false);
   }

   public static String escapeDNAttr(String value) {
      return value;
   }

   public static byte[] getSingletonByteArrayValue(LDAPEntry entry, String attrName) {
      LDAPAttribute attr = entry.getAttribute(attrName);
      if (attr == null) {
         return null;
      } else {
         Enumeration enum_ = attr.getByteValues();
         return enum_ != null && enum_.hasMoreElements() ? (byte[])((byte[])enum_.nextElement()) : null;
      }
   }

   public static String getSingletonStringValue(LDAPEntry entry, String attrName) {
      LDAPAttribute attr = entry.getAttribute(attrName);
      if (attr == null) {
         return null;
      } else {
         Enumeration enum_ = attr.getStringValues();
         return enum_ != null && enum_.hasMoreElements() ? (String)enum_.nextElement() : null;
      }
   }
}
