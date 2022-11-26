package com.bea.common.ldap;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPUrl;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.log.Log;
import weblogic.ldap.EmbeddedLDAPConnection;
import weblogic.utils.collections.CircularQueue;
import weblogic.utils.net.InetAddressHelper;

public class LDAPConfiguration extends OpenJPAConfigurationImpl {
   public static final String PROP_LDAP_CONFIG_POOL_SIZE = "weblogic.security.ldap.poolsize";
   public static final int DEFAULT_POOL_SIZE = 2;
   public static final String EMBEDDED = "embedded";
   public static final String VDETIMESTAMP = "vdetimestamp";
   private static final String HEADER = "LDAPConfiguration: ";
   private static final int LDAP_VERSION_MINIMUM = 3;
   private static int maxldapConnsSize = 2;
   private final CircularQueue ldapConns = new CircularQueue();

   public LDAPConfiguration() {
      super(false);
   }

   protected LDAPConnection getConnection() throws LDAPException {
      Log log = this.getLog("openjpa.Runtime");
      LDAPConnection conn = null;
      synchronized(this.ldapConns) {
         conn = (LDAPConnection)this.ldapConns.remove();
      }

      if (conn == null) {
         String host = "localhost";
         int port = 389;
         String curl = this.getConnectionURL();
         if (curl != null) {
            if (log.isTraceEnabled()) {
               log.trace("LDAPConfiguration: using URL: " + curl);
            }

            try {
               LDAPUrl u = new LDAPUrl(curl);
               if (u != null) {
                  host = u.getHost();
                  port = u.getPort();
                  if (log.isTraceEnabled()) {
                     log.trace("LDAPConfiguration: host: " + host + " port: " + port);
                  }
               }
            } catch (MalformedURLException var7) {
            }
         }

         host = InetAddressHelper.convertHostIfIPV6(host);
         conn = this.isEmbedded() ? new EmbeddedLDAPConnection(false) : new LDAPConnection();
         ((LDAPConnection)conn).connect(host, port);
         ((LDAPConnection)conn).bind(3, this.getConnectionUserName(), this.getConnectionPassword());
      }

      return (LDAPConnection)conn;
   }

   protected void releaseConnection(LDAPConnection conn) throws LDAPException {
      boolean released = false;
      int sz = false;
      synchronized(this.ldapConns) {
         int sz = this.ldapConns.size();
         if (sz < maxldapConnsSize) {
            this.ldapConns.add(conn);
            released = true;
         }
      }

      if (!released) {
         conn.disconnect();
      }

   }

   public boolean isEmbedded() {
      String cp = this.getConnectionProperties();
      if (cp != null) {
         Map props = new HashMap();
         StringTokenizer ct = new StringTokenizer(cp, ",");

         String embedded;
         while(ct.hasMoreTokens()) {
            embedded = ct.nextToken().trim();
            int idx = embedded.indexOf(61);
            if (idx >= 0) {
               props.put(embedded.substring(0, idx), embedded.substring(idx + 1));
            } else {
               props.put(embedded, (Object)null);
            }
         }

         embedded = (String)props.get("embedded");
         if (embedded != null) {
            return Boolean.valueOf(embedded);
         }
      }

      return false;
   }

   public boolean isVDETimestamp() {
      String cp = this.getConnectionProperties();
      if (cp == null) {
         return false;
      } else {
         Map props = new HashMap();
         StringTokenizer ct = new StringTokenizer(cp, ",");

         while(ct.hasMoreTokens()) {
            String pair = ct.nextToken().trim();
            int idx = pair.indexOf(61);
            if (idx >= 0) {
               props.put(pair.substring(0, idx), pair.substring(idx + 1));
            } else {
               props.put(pair, (Object)null);
            }
         }

         boolean isEmbedded = false;
         String embedded = (String)props.get("embedded");
         if (embedded != null) {
            isEmbedded = Boolean.valueOf(embedded);
         }

         boolean isVDETimestamp = false;
         String vdeTimestamp = (String)props.get("vdetimestamp");
         if (vdeTimestamp != null) {
            isVDETimestamp = Boolean.valueOf(vdeTimestamp);
         }

         return isEmbedded || isVDETimestamp;
      }
   }

   static {
      maxldapConnsSize = Integer.getInteger("weblogic.security.ldap.poolsize", 2);
   }
}
