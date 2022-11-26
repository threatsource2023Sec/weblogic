package weblogic.wtc.wls;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.security.AccessController;
import java.security.Principal;
import java.util.Enumeration;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;
import weblogic.ldap.EmbeddedLDAP;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.wtc.jatmi.DefaultUserRec;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.UserRec;

public final class ldapAppKey implements TCAppKey {
   public static final String DFLT_UID_KW = "TUXEDO_UID";
   public static final String DFLT_GID_KW = "TUXEDO_GID";
   private int dfltAppKey;
   private boolean allowAnon;
   private String uid_key = "TUXEDO_UID=";
   private String gid_key = "TUXEDO_GID=";
   private String anon_user = null;
   private String domain;
   private String realm;
   private String passwd;
   private String host;
   private String base;
   private int port;
   private DefaultUserRec anonUserRec = null;
   private LDAPConnection ld;
   private boolean _cached = false;
   private static final int scope = 2;
   private static final boolean attrsonly = false;
   private static final String base_prefix = "ou=people";
   private static final String ou = ",ou=";
   private static final String dc = ",dc=";
   private static final String dflt_filter = "(objectclass=*)";
   private static final String filter_prefix = "uid=";
   private static final String[] attr_query = new String[]{"uid", "description", null};
   private static final String ANONAPPKEY_KW = "DefaultAppKey=";
   private static final String admin = "cn=Admin";
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void init(String param, boolean anonAllowed, int defaultAppKey) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/init(param " + param + ", anonAllowed " + anonAllowed + ", dfltAppKey " + defaultAppKey + ")");
      }

      if (param != null) {
         this.parseParam(param);
      }

      this.do_init();
      this.dfltAppKey = defaultAppKey;
      this.allowAnon = anonAllowed;
      if (this.allowAnon) {
         this.anonUserRec = new DefaultUserRec(this.anon_user, this.dfltAppKey);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ldapAppKey/init(10) return");
      }

   }

   private void do_init() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/do_init()");
      }

      this.realm = SecurityServiceManager.getDefaultRealmName();
      this.domain = EmbeddedLDAP.getEmbeddedLDAPDomain();
      this.passwd = EmbeddedLDAP.getEmbeddedLDAPCredential(KERNELID);
      this.host = EmbeddedLDAP.getEmbeddedLDAPHost();
      this.port = EmbeddedLDAP.getEmbeddedLDAPPort();
      if (ntrace.getTraceLevel() == 1000373) {
         ntrace.doTrace("domain=" + this.domain + ", realm=" + this.realm + ", host=" + this.host + ", port=" + this.port);
      }

      StringBuffer tmp = (new StringBuffer("ou=people")).append(",ou=").append(this.realm);
      tmp = tmp.append(",dc=").append(this.domain);
      this.base = tmp.toString();
      if (traceEnabled) {
         ntrace.doTrace("search base: " + this.base);
      }

      try {
         this.ld = new LDAPConnection();
         this.ld.connect(3, this.host, this.port, "cn=Admin", this.passwd);
      } catch (LDAPException var7) {
         this.ld = null;
         if (traceEnabled) {
            ntrace.doTrace("*]/ldapAppKey/do_init(10) return TPESYSTEM");
         }

         throw new TPException(12, "Failed to create LDAP connection object");
      } finally {
         if (this.passwd != null) {
            this.passwd = null;
         }

      }

      this.anon_user = WLSPrincipals.getAnonymousUsername();
      if (traceEnabled) {
         ntrace.doTrace("]/ldapAppKey/do_init(20) return");
      }

   }

   public void uninit() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/uninit()");
      }

      if (this.ld != null) {
         try {
            this.ld.disconnect();
         } catch (LDAPException var3) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ldapAppKey/uninit(10) return TPESYSTEM");
            }

            throw new TPException(12, "Failed to close LDAP connection");
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ldapAppKey/uninit(20) return");
      }

   }

   public UserRec getTuxedoUserRecord(TCAuthenticatedUser subj) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/getTuxedoUserRecord(subj " + subj + ")");
      }

      Object[] obj = subj.getPrincipals();
      if (obj != null && obj.length != 0) {
         for(int i = 0; i < obj.length; ++i) {
            Principal user = (Principal)obj[i];
            String username = user.getName();
            if (username.equals(this.anon_user)) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ldapAppKey/getTuxedoUserRecord(30) return anonymous user: " + this.anonUserRec);
               }

               return this.anonUserRec;
            }

            UserRec rec;
            if ((rec = this.getUserRec(username)) != null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ldapAppKey/getTuxedoUserRecord(40) return user: " + rec);
               }

               return rec;
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ldapAppKey/getTuxedoUserRecord(50) return null");
         }

         return null;
      } else if (this.allowAnon) {
         if (traceEnabled) {
            ntrace.doTrace("]/ldapAppKey/getTuxedoUserRecord(10) return anonymous user: " + this.anonUserRec);
         }

         return this.anonUserRec;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/ldapAppKey/uninit(20) return null");
         }

         return null;
      }
   }

   private void parseParam(String param) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/parseParam(param " + param + ")");
      }

      String str = param.trim();
      StringBuffer sb;
      int i;
      if ((i = str.indexOf(32)) != -1) {
         sb = new StringBuffer(str.substring(0, i));
         sb.append('=');
         this.uid_key = new String(sb);
         String kw = str.substring(i + 1).trim();
         if ((i = kw.indexOf(32)) != -1) {
            str = kw.substring(0, i);
         } else if (kw.length() != 0) {
            str = kw;
         }

         sb = new StringBuffer(str);
         sb.append('=');
         this.gid_key = new String(sb);
      } else if (str.length() != 0) {
         sb = new StringBuffer(str);
         sb.append('=');
         this.uid_key = new String(sb);
      }

      if (traceEnabled) {
         ntrace.doTrace("/ldapAppKey/parseParam/(uid_key " + this.uid_key + ", gid_key " + this.gid_key);
         ntrace.doTrace("]/ldapAppKey/parseParam(10) return");
      }

   }

   private UserRec getUserRec(String username) {
      int uid = false;
      int gid = false;
      StringBuffer sb = (new StringBuffer("uid=")).append(username);
      String filter = sb.toString();
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ldapAppKey/getUserRec(username " + username + ")");
      }

      if (this.ld == null) {
         try {
            this.do_init();
         } catch (TPException var23) {
            if (traceEnabled) {
               ntrace.doTrace("]/ldapAppKey/getUserRec(5) return null");
            }

            return null;
         }
      }

      boolean do_again = true;

      while(do_again) {
         do_again = false;

         try {
            LDAPSearchResults res = this.ld.search(this.base, 2, filter, attr_query, false);

            while(res.hasMoreElements()) {
               LDAPEntry findEntry = null;
               findEntry = res.next();
               if (ntrace.getTraceLevel() == 1000373) {
                  ntrace.doTrace("DN: " + findEntry.getDN());
               }

               LDAPAttributeSet attributeSet = findEntry.getAttributeSet();

               for(int i = 0; i < attributeSet.size(); ++i) {
                  LDAPAttribute attr = attributeSet.elementAt(i);
                  String attrName = attr.getName();
                  if (ntrace.getTraceLevel() == 1000373) {
                     ntrace.doTrace(attrName + ':');
                  }

                  Enumeration enumVals = attr.getStringValues();
                  if (enumVals != null) {
                     while(enumVals.hasMoreElements()) {
                        String nextValue = (String)enumVals.nextElement();
                        if (ntrace.getTraceLevel() == 1000373) {
                           ntrace.doTrace(nextValue);
                        }

                        if (attrName.equals("description")) {
                           int j;
                           if ((j = nextValue.indexOf(this.uid_key)) != -1) {
                              j = nextValue.indexOf(61, j) + 1;
                              int k;
                              String t;
                              if ((k = nextValue.indexOf(32, j)) != -1) {
                                 t = nextValue.substring(j, k);
                              } else {
                                 t = nextValue.substring(j);
                              }

                              String kw;
                              if ((k = t.indexOf(44)) == -1 && (k = t.indexOf(59)) == -1 && (k = t.indexOf(58)) == -1) {
                                 kw = t;
                              } else {
                                 kw = t.substring(0, k);
                              }

                              Integer val;
                              int uid;
                              try {
                                 val = new Integer(kw);
                                 uid = val;
                                 if (ntrace.getTraceLevel() == 1000373) {
                                    ntrace.doTrace("uid = " + uid);
                                 }
                              } catch (NumberFormatException var24) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/ldapAppKey/getUserRec(10) return null");
                                 }

                                 return null;
                              }

                              if ((j = nextValue.indexOf(this.gid_key)) != -1) {
                                 j = nextValue.indexOf(61, j) + 1;
                                 if ((k = nextValue.indexOf(32, j)) != -1) {
                                    t = nextValue.substring(j, k);
                                 } else {
                                    t = nextValue.substring(j);
                                 }

                                 if ((k = t.indexOf(44)) == -1 && (k = t.indexOf(59)) == -1 && (k = t.indexOf(58)) == -1) {
                                    kw = t;
                                 } else {
                                    kw = t.substring(k);
                                 }

                                 int gid;
                                 try {
                                    val = new Integer(kw);
                                    gid = val;
                                    if (ntrace.getTraceLevel() == 1000373) {
                                       ntrace.doTrace("gid = " + gid);
                                    }
                                 } catch (NumberFormatException var25) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("]/ldapAppKey/getUserRec(30) return null");
                                    }

                                    return null;
                                 }

                                 uid &= 131071;
                                 gid &= 16383;
                                 int key = uid | gid << 17;
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/ldapAppKey/getUserRec(50) return user: " + username + ", appkey " + key);
                                 }

                                 return new DefaultUserRec(username, key);
                              }

                              if (traceEnabled) {
                                 ntrace.doTrace("]/ldapAppKey/getUserRec(40) return null");
                              }

                              return null;
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("]/ldapAppKey/getUserRec(20) return null");
                           }

                           return null;
                        }
                     }
                  }
               }
            }
         } catch (LDAPException var26) {
            int errcode = var26.getLDAPResultCode();
            if (errcode == 81) {
               this.ld = null;

               try {
                  this.do_init();
                  do_again = true;
               } catch (TPException var22) {
               }
            }
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ldapAppKey/getUserRec(60) return null");
      }

      return null;
   }

   public void doCache(boolean b) {
      this._cached = b;
   }

   public boolean isCached() {
      return this._cached;
   }
}
