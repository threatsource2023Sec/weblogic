package weblogic.entitlement.data.ldap;

import java.util.Properties;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchConstraints;
import weblogic.entitlement.data.EnCredentialException;
import weblogic.entitlement.data.EnDomainException;
import weblogic.entitlement.data.EnStorageException;
import weblogic.ldap.EmbeddedLDAPConnection;
import weblogic.security.shared.LoggerWrapper;

public class EnLDAP {
   public static final String LDAP_DOMAIN = "weblogic.entitlement.data.ldap.domain";
   public static final String LDAP_REALM = "weblogic.entitlement.data.ldap.realm";
   public static final String LDAP_HOST = "weblogic.entitlement.data.ldap.hostname";
   public static final String LDAP_PORT = "weblogic.entitlement.data.ldap.port";
   public static final String LDAP_PASSWORD = "weblogic.entitlement.data.ldap.password";
   public static final String LDAP_USELOCAL = "weblogic.entitlement.data.ldap.uselocal";
   private static String host = "localhost";
   private static int port = 7003;
   private static String password;
   private static String domainName = "mydomain";
   private static boolean useLocal = false;
   protected String realmName = "myrealm";
   private static final String admin = "cn=admin";
   private static LDAPConnection[] ldPool = null;
   private static int ldPoolCount = 0;
   private static LDAPSearchConstraints constraint = null;
   protected static String domainDN = null;
   protected String realmDN = null;
   protected static final String nameAttribute = "cn";
   protected static final int LDAP_VERSION = 2;
   protected static final String[] noAttrs = new String[]{"1.1"};
   protected static final String[] nameAttributeList = new String[]{"cn"};
   protected static final String NAME_DELIMITER = "::";
   protected static final LoggerWrapper traceLogger = LoggerWrapper.getInstance("SecurityEEngine");

   public EnLDAP(Properties env) {
      if (traceLogger.isDebugEnabled()) {
         traceLogger.debug("Initializing EnLDAP.");
      }

      if (env != null) {
         String p;
         if ((p = env.getProperty("weblogic.entitlement.data.ldap.port")) != null) {
            port = Integer.parseInt(p);
         }

         if ((p = env.getProperty("weblogic.entitlement.data.ldap.hostname")) != null) {
            host = p;
         }

         if ((p = env.getProperty("weblogic.entitlement.data.ldap.password")) != null && !p.isEmpty()) {
            password = p;
         }

         if ((p = env.getProperty("weblogic.entitlement.data.ldap.domain")) != null) {
            domainName = p;
         }

         if ((p = env.getProperty("weblogic.entitlement.data.ldap.realm")) != null) {
            this.realmName = p;
         }

         if ((p = env.getProperty("weblogic.entitlement.data.ldap.uselocal")) != null && "true".equals(p)) {
            useLocal = true;
         }
      }

      domainDN = "dc=" + domainName;
      this.realmDN = "ou=" + this.realmName + "," + domainDN;
      if (ldPool == null) {
         Class var18 = EnLDAP.class;
         synchronized(EnLDAP.class) {
            if (ldPool == null) {
               ldPool = new LDAPConnection[16];
               int reTry = 3;

               while(reTry != 0) {
                  LDAPConnection ld = null;

                  try {
                     ld = getConnection();
                     constraint = ld.getSearchConstraints();
                     addStructuralEntry(ld, domainDN, true, domainName);
                     addStructuralEntry(ld, this.realmDN, false, this.realmName);
                     reTry = 0;
                  } catch (LDAPException var15) {
                     ld = null;
                     EnStorageException sEn = null;
                     if (traceLogger.isDebugEnabled()) {
                        traceLogger.debug("EnLDAP(), LDAPException while trying to initialize LDAP", var15);
                     }

                     switch (var15.getLDAPResultCode()) {
                        case 49:
                           sEn = new EnCredentialException(var15.toString());
                           break;
                        case 53:
                           sEn = new EnDomainException(var15.toString());
                           break;
                        case 91:
                           if (reTry > 1) {
                              --reTry;

                              try {
                                 Thread.sleep(1000L);
                              } catch (Exception var14) {
                              }
                              continue;
                           }
                        case 52:
                        case 81:
                        default:
                           sEn = new EnStorageException(var15.toString());
                     }

                     ldPool = null;
                     throw sEn;
                  } finally {
                     if (ld != null) {
                        releaseConnection(ld);
                     }

                  }
               }
            }
         }
      }

   }

   protected static LDAPConnection getConnection() throws LDAPException {
      LDAPConnection conn = null;
      if (ldPoolCount > 0) {
         Class var1 = LDAPConnection.class;
         synchronized(LDAPConnection.class) {
            if (ldPoolCount > 0) {
               conn = ldPool[--ldPoolCount];
               ldPool[ldPoolCount] = null;
            }
         }
      }

      if (conn == null) {
         conn = useLocal ? new EmbeddedLDAPConnection(false) : new LDAPConnection();
         ((LDAPConnection)conn).connect(host, port);
         ((LDAPConnection)conn).bind(2, "cn=admin", password);
      }

      return (LDAPConnection)conn;
   }

   protected static synchronized void releaseConnection(LDAPConnection conn) {
      if (ldPoolCount == ldPool.length) {
         LDAPConnection[] pool = new LDAPConnection[ldPoolCount + 16];
         System.arraycopy(ldPool, 0, pool, 0, ldPoolCount);
         ldPool = pool;
      }

      ldPool[ldPoolCount++] = conn;
   }

   protected static void addEntry(LDAPConnection conn, String entry, boolean domain, String attrValue) throws LDAPException {
      String[] objectClassValues = new String[]{"top", domain ? "domain" : "organizationalUnit"};
      LDAPAttributeSet attribSet = new LDAPAttributeSet();
      attribSet.add(new LDAPAttribute("objectclass", objectClassValues));
      attribSet.add(new LDAPAttribute(domain ? "dc" : "ou", attrValue));
      LDAPEntry ldEntry = new LDAPEntry(entry, attribSet);
      conn.add(ldEntry);
   }

   protected static void addStructuralEntry(LDAPConnection conn, String entry, boolean domain, String attrValue) throws LDAPException {
      try {
         addEntry(conn, entry, domain, attrValue);
      } catch (LDAPException var5) {
         if (68 != var5.getLDAPResultCode()) {
            throw var5;
         }
      }

   }

   protected static void checkStorageException(LDAPException e) {
      if (traceLogger.isDebugEnabled()) {
         traceLogger.debug("LDAPException: ", e);
      }

      switch (e.getLDAPResultCode()) {
         case 52:
         case 81:
         case 91:
            throw new EnStorageException(e.getMessage());
         default:
      }
   }

   protected static String PK2Name(String escapedRes, String escapedRoleName) {
      if (escapedRes == null) {
         escapedRes = "*";
      }

      return escapedRes + "::" + escapedRoleName;
   }
}
