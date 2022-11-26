package weblogic.ldap;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.vde.Attribute;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.DirectorySchemaViolation;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.ParseFilter;
import com.octetstring.vde.util.PasswordEncryptor;
import com.octetstring.vde.util.ServerConfig;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPCache;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPConstraints;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPExtendedOperation;
import netscape.ldap.LDAPModification;
import netscape.ldap.LDAPModificationSet;
import netscape.ldap.LDAPResponseListener;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.LDAPSearchListener;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.LDAPSocketFactory;
import netscape.ldap.LDAPUrl;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.SSL.CertPathTrustManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.EmbeddedLDAPConnectionService;
import weblogic.security.utils.EmbeddedLDAPConnectionServiceGenerator;
import weblogic.security.utils.SSLContextManager;

public class EmbeddedLDAPConnection extends LDAPConnection implements EmbeddedLDAPConnectionService {
   private static final long serialVersionUID = -932303772832631138L;
   Credentials creds;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean inMasterServer;
   private boolean useMasterFirst;
   private boolean writeFailover;
   private LDAPCache cache;
   private LDAPSearchConstraints defaultConstraints;
   private LDAPConnection delegate;
   private String savedHost;
   private int savedPort;
   private String savedDN;
   private String savedPasswd;
   private int protocolVersion;
   private boolean useSSL;
   private boolean ignoreCertPathValidators;
   private String masterHost;
   private int masterPort;
   private static final DebugLogger log = DebugLogger.getDebugLogger("DebugEmbeddedLDAP");
   private boolean debugEnabled;
   private static final DirectoryString EMPTY_DIRSTRING = new DirectoryString("");
   private static final DirectoryString USERPASSWORD = new DirectoryString("userPassword");
   private boolean msiMode;
   private final String MSI_NO_WRITE_FAILOVER;
   private final boolean MSI_LOCAL_LDAP_ONLY;
   private boolean keepAliveEnabled;

   public EmbeddedLDAPConnection(boolean masterFirst) {
      this(masterFirst, true);
   }

   public EmbeddedLDAPConnection(boolean masterFirst, boolean connWriteFailover) {
      this(masterFirst, connWriteFailover, false);
   }

   public EmbeddedLDAPConnection(boolean masterFirst, boolean connWriteFailover, boolean ignoreCertPathValidators) {
      this.creds = null;
      this.inMasterServer = false;
      this.useMasterFirst = false;
      this.writeFailover = true;
      this.cache = null;
      this.defaultConstraints = new LDAPSearchConstraints();
      this.delegate = null;
      this.savedHost = null;
      this.savedPort = -1;
      this.savedDN = null;
      this.savedPasswd = null;
      this.protocolVersion = 2;
      this.useSSL = false;
      this.ignoreCertPathValidators = false;
      this.masterHost = null;
      this.masterPort = -1;
      this.msiMode = false;
      this.MSI_NO_WRITE_FAILOVER = "Admin server unavailable and write failover is not enabled.";
      this.MSI_LOCAL_LDAP_ONLY = Boolean.valueOf(System.getProperty("weblogic.security.MSILocalLDAPOnly", "false"));
      this.keepAliveEnabled = false;
      this.debugEnabled = EmbeddedLDAP.getEmbeddedLDAP().isDebugEnabled();
      this.useSSL = EmbeddedLDAP.getEmbeddedLDAPUseSSL();
      this.keepAliveEnabled = EmbeddedLDAP.getEmbeddedLDAP().isKeepAliveEnabled();
      this.useMasterFirst = masterFirst;
      this.writeFailover = connWriteFailover;
      this.ignoreCertPathValidators = ignoreCertPathValidators;
      this.inMasterServer = ManagementService.getRuntimeAccess(kernelId).isAdminServer();
      if (this.debugEnabled) {
         log.debug("Initialize local ldap connection ");
      }

      if (EmbeddedLDAP.getEmbeddedLDAP().isMasterFirst()) {
         this.useMasterFirst = true;
      }

      if (!this.inMasterServer) {
         this.masterHost = EmbeddedLDAP.getEmbeddedLDAPHost();
         this.masterPort = EmbeddedLDAP.getEmbeddedLDAPPort();
      }

      if (this.MSI_LOCAL_LDAP_ONLY) {
         this.msiMode = !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();
         if (this.useMasterFirst && !this.inMasterServer && !this.msiMode) {
            this.delegate = this.getDelegate();
         }
      } else if (this.useMasterFirst && !this.inMasterServer) {
         this.delegate = this.getDelegate();
      }

      this.creds = new Credentials();
      this.creds.setUser(EMPTY_DIRSTRING);
   }

   public void finalize() throws LDAPException {
   }

   public void setCache(LDAPCache cache) {
      if (this.delegate != null) {
         this.delegate.setCache(cache);
      } else {
         this.cache = cache;
      }
   }

   public LDAPCache getCache() {
      return this.delegate != null ? this.delegate.getCache() : this.cache;
   }

   public Object getProperty(String name) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public void setProperty(String name, Object val) throws LDAPException {
      if (this.delegate != null) {
         try {
            this.delegate.setProperty(name, val);
            return;
         } catch (LDAPException var4) {
            this.determineFailover(var4);
         }
      }

      if (this.debugEnabled) {
         log.debug("Setting property " + name + " to " + val);
      }

   }

   public String getHost() {
      return this.savedHost;
   }

   public int getPort() {
      return this.savedPort;
   }

   public String getAuthenticationDN() {
      return null;
   }

   public String getAuthenticationPassword() {
      return null;
   }

   public int getConnectTimeout() {
      return 0;
   }

   public void setConnectTimeout(int timeout) {
   }

   public int getConnSetupDelay() {
      return 0;
   }

   public void setConnSetupDelay(int delay) {
   }

   public LDAPSocketFactory getSocketFactory() {
      return null;
   }

   public void setSocketFactory(LDAPSocketFactory factory) {
   }

   public boolean isConnected() {
      return this.delegate != null ? this.delegate.isConnected() : true;
   }

   public boolean isAuthenticated() {
      return true;
   }

   public void connect(String host, int port) throws LDAPException {
      this.savedHost = host;
      this.savedPort = port;
      if (this.delegate != null) {
         try {
            this.connect(this.delegate, host, port);
            return;
         } catch (LDAPException var4) {
            this.determineFailover(var4);
         }
      }

   }

   public void connect(String host, int port, String dn, String passwd) throws LDAPException {
      throwUnsupported();
   }

   public void connect(String host, int port, String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void connect(String host, int port, String dn, String passwd, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void connect(int version, String host, int port, String dn, String passwd) throws LDAPException {
      throwUnsupported();
   }

   public void connect(int version, String host, int port, String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void connect(int version, String host, int port, String dn, String passwd, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void abandon(LDAPSearchResults searchResults) throws LDAPException {
      if (this.delegate != null) {
         try {
            this.delegate.abandon(searchResults);
            return;
         } catch (LDAPException var3) {
            this.determineFailover(var3);
         }
      }

      if (searchResults != null) {
         ;
      }
   }

   public void authenticate(String dn, String passwd) throws LDAPException {
      this.bind(this.protocolVersion, dn, passwd);
   }

   public void authenticate(String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(String dn, String passwd, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(int version, String dn, String passwd) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(int version, String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(int version, String dn, String passwd, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(String dn, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(String dn, String[] mechanisms, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(String dn, String mechanism, String packageName, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public void authenticate(String dn, String[] mechanisms, String packageName, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public LDAPResponseListener authenticate(int version, String dn, String passwd, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener authenticate(int version, String dn, String passwd, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public void bind(String dn, String passwd) throws LDAPException {
      throwUnsupported();
   }

   public void bind(String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void bind(int version, String dn, String passwd) throws LDAPException {
      this.savedDN = dn;
      this.savedPasswd = passwd;
      if (this.delegate != null) {
         try {
            this.delegate.bind(version, dn, passwd);
         } catch (LDAPException var8) {
            this.determineFailover(var8);
         }
      }

      if (this.debugEnabled) {
         log.debug("bind (version = " + version + ", dn = " + dn + ")");
      }

      DirectoryString subject = null;
      if (dn != null) {
         subject = new DirectoryString(dn);
      } else {
         subject = EMPTY_DIRSTRING;
      }

      if (subject.equals(EMPTY_DIRSTRING)) {
         if (!EmbeddedLDAP.getEmbeddedLDAP().getEmbeddedLDAPMBean().isAnonymousBindAllowed()) {
            if (this.debugEnabled) {
               log.debug("Anonymous user, but anonymous bind is not allowed");
            }

            throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
         }
      } else if (passwd != null && !passwd.equals("")) {
         if ((new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser"))).equals(subject)) {
            if (PasswordEncryptor.compare(passwd, (String)ServerConfig.getInstance().get("vde.rootpw"))) {
               this.creds.setUser(subject);
               this.creds.setRoot(true);
               if (this.debugEnabled) {
                  log.debug("binding as root user: " + subject);
               }

            } else {
               if (this.debugEnabled) {
                  log.debug("Invalid password for root user: " + subject);
               }

               throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
            }
         } else if (BackendHandler.getInstance().doBind(subject)) {
            boolean res = BackendHandler.getInstance().bind(subject, new BinarySyntax(passwd.getBytes()));
            if (res) {
               this.creds = new Credentials();
               this.creds.setUser(subject);
               if (this.debugEnabled) {
                  log.debug("binding as user: " + subject);
               }

            } else {
               if (this.debugEnabled) {
                  log.debug("Invalid password for user " + subject);
               }

               throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
            }
         } else {
            Entry bindEnt = null;

            try {
               bindEnt = BackendHandler.getInstance().getByDN((DirectoryString)null, subject);
               bindEnt = BackendHandler.getInstance().map(bindEnt);
            } catch (DirectoryException var7) {
            }

            if (bindEnt != null && bindEnt.containsKey(USERPASSWORD)) {
               String entryPassword = new String(((Syntax)bindEnt.get(USERPASSWORD).elementAt(0)).getValue());
               if (PasswordEncryptor.compare(passwd, entryPassword)) {
                  if (this.debugEnabled) {
                     log.debug("Binding as user: " + subject);
                  }

                  this.creds.setUser(subject);
               } else {
                  if (this.debugEnabled) {
                     log.debug("Invalid password for user: " + subject);
                  }

                  throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
               }
            } else {
               if (this.debugEnabled) {
                  log.debug("Invalid password for user: " + subject);
               }

               throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
            }
         }
      } else {
         this.creds.setUser(EMPTY_DIRSTRING);
         if (EmbeddedLDAP.getEmbeddedLDAP().getEmbeddedLDAPMBean().isAnonymousBindAllowed()) {
            if (this.debugEnabled) {
               log.debug("binding as anonymous user");
            }

         } else {
            if (this.debugEnabled) {
               log.debug("binding as anonymous user is not allowed");
            }

            throw new LDAPException("error result", LDAPResult.INVALID_CREDENTIALS.intValue());
         }
      }
   }

   public void bind(int version, String dn, String passwd, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void bind(String dn, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public void bind(String dn, String[] mechanisms, Hashtable props, Object cbh) throws LDAPException {
      throwUnsupported();
   }

   public String getAuthenticationMethod() {
      return null;
   }

   public void reconnect() throws LDAPException {
      throwUnsupported();
   }

   public synchronized void disconnect() throws LDAPException {
      if (this.delegate != null) {
         try {
            this.delegate.disconnect();
            return;
         } catch (LDAPException var2) {
            this.determineFailover(var2);
         }
      }

   }

   public LDAPEntry read(String DN) throws LDAPException {
      if (this.useMasterFirst && this.delegate != null) {
         try {
            return this.delegate.read(DN);
         } catch (LDAPException var3) {
            this.determineFailover(var3);
         }
      }

      if (this.debugEnabled) {
         log.debug("read (" + DN + ")");
      }

      return this.read(DN, (String[])null, this.defaultConstraints);
   }

   public LDAPEntry read(String DN, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPEntry read(String DN, String[] attrs) throws LDAPException {
      if (this.useMasterFirst && this.delegate != null) {
         try {
            return this.delegate.read(DN, attrs);
         } catch (LDAPException var4) {
            this.determineFailover(var4);
         }
      }

      if (this.debugEnabled) {
         log.debug("read (" + DN + "," + attrs + ")");
      }

      return this.read(DN, attrs, this.defaultConstraints);
   }

   public LDAPEntry read(String DN, String[] attrs, LDAPSearchConstraints cons) throws LDAPException {
      if (this.useMasterFirst && this.delegate != null) {
         try {
            return this.delegate.read(DN, attrs, cons);
         } catch (LDAPException var5) {
            this.determineFailover(var5);
         }
      }

      if (this.debugEnabled) {
         log.debug("read (" + DN + "," + attrs + "," + cons + ")");
      }

      LDAPSearchResults results = this.search(DN, 0, "(|(objectclass=*)(objectclass=ldapsubentry))", attrs, false, (LDAPSearchConstraints)cons);
      return results == null ? null : results.next();
   }

   public static LDAPEntry read(LDAPUrl toGet) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public static LDAPSearchResults search(LDAPUrl toGet) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public static LDAPSearchResults search(LDAPUrl toGet, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPSearchResults search(String base, int scope, String filter, String[] attrs, boolean attrsOnly) throws LDAPException {
      this.defaultConstraints.setMaxResults(0);
      return this.search(base, scope, filter, attrs, attrsOnly, this.defaultConstraints);
   }

   public LDAPSearchResults search(String searchBase, int scope, String searchFilter, String[] attrs, boolean attrsOnly, LDAPSearchConstraints cons) throws LDAPException {
      if (this.useMasterFirst && this.delegate != null) {
         try {
            return this.delegate.search(searchBase, scope, searchFilter, attrs, attrsOnly, cons);
         } catch (LDAPException var24) {
            this.determineFailover(var24);
         }
      }

      int entrycount = 0;
      int mysearchlimit = false;
      DirectoryString base = null;
      Vector entries = null;
      EntrySet currentEntrySet = null;
      if (this.debugEnabled) {
         log.debug("search (base=" + searchBase + ", scope=" + scope + ", searchFilter=, attrs=" + attrs + ", attrsOnly=" + attrsOnly + ", cons=" + cons);
      }

      if (cons == null) {
         cons = this.defaultConstraints;
      }

      EmbeddedLDAPSearchResults returnValue = new EmbeddedLDAPSearchResults();
      Filter filter = null;

      try {
         base = DNUtility.getInstance().normalize(new DirectoryString(searchBase));
         if (this.debugEnabled) {
            log.debug("search normalized base = " + base);
         }

         filter = ParseFilter.parse(searchFilter);
      } catch (InvalidDNException var22) {
         throw new LDAPException("error result", var22.getLDAPErrorCode(), var22.getMessage());
      } catch (DirectoryException var23) {
         throw new LDAPException("error result", var23.getLDAPErrorCode(), var23.getMessage());
      }

      boolean typesOnly = attrsOnly;
      Vector attributes = new Vector();

      DirectoryString currentEntry;
      for(int i = 0; attrs != null && i < attrs.length; ++i) {
         if (attrs[i] != null) {
            if (this.debugEnabled) {
               log.debug("search attr " + i + " = " + attrs[i]);
            }

            currentEntry = new DirectoryString(attrs[i]);
            attributes.addElement(currentEntry);
         }
      }

      int mysearchlimit = cons.getMaxResults();
      if (this.debugEnabled) {
         log.debug("search search limit = " + mysearchlimit);
      }

      try {
         if (this.creds == null) {
            this.creds = new Credentials();
         }

         entries = BackendHandler.getInstance().get(this.creds.getUser(), base, scope, filter, typesOnly, attributes);
      } catch (DirectoryException var21) {
         throw new LDAPException("error result", var21.getLDAPErrorCode(), var21.getMessage());
      }

      Enumeration backEnum = entries.elements();
      if (backEnum.hasMoreElements()) {
         currentEntrySet = (EntrySet)backEnum.nextElement();
      }

      while(currentEntrySet != null && (currentEntrySet.hasMore() || backEnum.hasMoreElements())) {
         if (mysearchlimit != 0 && entrycount >= mysearchlimit) {
            if (this.debugEnabled) {
               log.debug("search exceeded limit of " + mysearchlimit + ", num entries = " + entrycount);
            }

            return returnValue;
         }

         if (!currentEntrySet.hasMore()) {
            currentEntrySet = (EntrySet)backEnum.nextElement();
         }

         currentEntry = null;

         Entry currentEntry;
         try {
            currentEntry = currentEntrySet.getNext();
         } catch (DirectoryException var20) {
            throw new LDAPException("error result", var20.getLDAPErrorCode());
         }

         if (currentEntry != null) {
            currentEntry = BackendHandler.getInstance().postSearch(this.creds, currentEntry, attributes, filter, scope, base);
            if (currentEntry != null) {
               ++entrycount;
               LDAPAttributeSet attrSet = this.attributeSetFromEntry(currentEntry, typesOnly, attributes);
               LDAPEntry entry = new LDAPEntry(currentEntry.getName().getDirectoryString(), attrSet);
               if (this.debugEnabled) {
                  log.debug("search adding entry " + currentEntry.getName());
               }

               returnValue.add(new EmbeddedLDAPSearchResult(entry));
            }
         }
      }

      if (this.debugEnabled) {
         log.debug("search returning " + returnValue);
      }

      return returnValue;
   }

   public boolean compare(String DN, LDAPAttribute attr) throws LDAPException {
      throwUnsupported();
      return false;
   }

   public boolean compare(String DN, LDAPAttribute attr, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return false;
   }

   public boolean compare(String DN, LDAPAttribute attr, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
      return false;
   }

   public void add(LDAPEntry entry) throws LDAPException {
      LDAPException delegateExc = null;
      if (this.MSI_LOCAL_LDAP_ONLY) {
         if (this.msiMode) {
            this.msiMode = !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();
         }

         if (this.msiMode && !this.writeFailover) {
            throw new LDAPException("Admin server unavailable and write failover is not enabled.");
         }

         if (!this.inMasterServer && this.delegate == null && !this.msiMode) {
            try {
               this.delegate = this.getAndInitDelegate();
            } catch (LDAPException var15) {
               this.determineWriteFailover(var15);
            }
         }
      } else if (!this.inMasterServer && this.delegate == null) {
         try {
            this.delegate = this.getAndInitDelegate();
         } catch (LDAPException var14) {
            this.determineWriteFailover(var14);
         }
      }

      Credentials localCreds = this.creds;
      boolean delegateWrite = false;
      if (this.delegate != null) {
         try {
            this.delegate.add(entry);
            delegateWrite = true;
         } catch (LDAPException var19) {
            if (var19.getLDAPResultCode() == 68) {
               delegateExc = var19;
            } else {
               this.determineWriteFailover(var19);
            }
         }

         localCreds = new Credentials();
         localCreds.setUser(new DirectoryString(this.savedDN));
         if (((String)ServerConfig.getInstance().get("vde.rootuser")).equalsIgnoreCase(this.savedDN)) {
            localCreds.setRoot(true);
         }
      }

      Int8 res = LDAPResult.SUCCESS;

      try {
         if (this.debugEnabled) {
            log.debug("add entry " + entry);
         }

         Entry newEntry = this.LDAPEntryToEntry(entry);
         res = BackendHandler.getInstance().add(localCreds, newEntry);
      } catch (DirectorySchemaViolation var16) {
         res = LDAPResult.OBJECT_CLASS_VIOLATION;
         if (var16.getMessage() != null) {
            throw new LDAPException("error result", res.intValue(), var16.getMessage());
         }

         throw new LDAPException("error result", res.intValue());
      } catch (InvalidDNException var17) {
         res = LDAPResult.INVALID_DN_SYNTAX;
         if (var17.getMessage() != null) {
            throw new LDAPException("error result", res.intValue(), var17.getMessage());
         }

         throw new LDAPException("error result", res.intValue());
      } finally {
         localCreds = null;
      }

      if (res != LDAPResult.SUCCESS) {
         if (!delegateWrite || res != LDAPResult.ENTRY_ALREADY_EXISTS) {
            throw new LDAPException("error result", res.intValue());
         }

         if (this.debugEnabled) {
            log.debug("delegate success and local already exists");
         }
      }

      if (delegateExc != null) {
         throw delegateExc;
      } else {
         if (this.debugEnabled) {
            log.debug("added entry successfully");
         }

      }
   }

   public void add(LDAPEntry entry, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void add(LDAPEntry entry, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation op) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation op, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation op, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public void modify(String DN, LDAPModification mod) throws LDAPException {
      this.modify(DN, new LDAPModification[]{mod});
   }

   public void modify(String DN, LDAPModification mod, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void modify(String DN, LDAPModification mod, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void modify(String DN, LDAPModificationSet mods) throws LDAPException {
      if (this.MSI_LOCAL_LDAP_ONLY) {
         if (this.msiMode) {
            this.msiMode = !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();
         }

         if (this.msiMode && !this.writeFailover) {
            throw new LDAPException("Admin server unavailable and write failover is not enabled.");
         }

         if (!this.inMasterServer && this.delegate == null && !this.msiMode) {
            try {
               this.delegate = this.getAndInitDelegate();
            } catch (LDAPException var7) {
               this.determineWriteFailover(var7);
            }
         }
      } else if (!this.inMasterServer && this.delegate == null) {
         try {
            this.delegate = this.getAndInitDelegate();
         } catch (LDAPException var6) {
            this.determineWriteFailover(var6);
         }
      }

      if (this.delegate != null) {
         try {
            this.delegate.modify(DN, mods);
         } catch (LDAPException var5) {
            this.determineWriteFailover(var5);
         }
      }

      LDAPModification[] modArray = new LDAPModification[mods.size()];

      for(int i = 0; i < mods.size(); ++i) {
         modArray[i] = mods.elementAt(i);
      }

      this.modify(DN, modArray);
   }

   public void modify(String DN, LDAPModificationSet mods, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void modify(String DN, LDAPModificationSet mods, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void modify(String DN, LDAPModification[] mods) throws LDAPException {
      if (this.MSI_LOCAL_LDAP_ONLY) {
         if (this.msiMode) {
            this.msiMode = !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();
         }

         if (this.msiMode && !this.writeFailover) {
            throw new LDAPException("Admin server unavailable and write failover is not enabled.");
         }

         if (!this.inMasterServer && this.delegate == null && !this.msiMode) {
            try {
               this.delegate = this.getAndInitDelegate();
            } catch (LDAPException var26) {
               this.determineWriteFailover(var26);
            }
         }
      } else if (!this.inMasterServer && this.delegate == null) {
         try {
            this.delegate = this.getAndInitDelegate();
         } catch (LDAPException var25) {
            this.determineWriteFailover(var25);
         }
      }

      if (this.delegate != null) {
         try {
            this.delegate.modify(DN, mods);
         } catch (LDAPException var24) {
            this.determineWriteFailover(var24);
         }
      }

      if (this.debugEnabled) {
         log.debug("modify entry " + DN + " mods" + mods);
      }

      Vector changeVector = new Vector();

      for(int i = 0; i < mods.length; ++i) {
         int modType = mods[i].getOp();
         LDAPAttribute attr = mods[i].getAttribute();
         DirectoryString modAttr = new DirectoryString(attr.getName());
         AttributeType at = SchemaChecker.getInstance().getAttributeType(modAttr);
         Class valClass = null;
         if (at != null) {
            valClass = at.getSyntaxClass();
         } else {
            valClass = DirectoryString.class;
         }

         if (this.debugEnabled) {
            log.debug("modify attribute " + attr.getName() + " class " + valClass);
         }

         Vector modValues = new Vector();
         Enumeration byteValuesEnum = mods[i].getAttribute().getByteValues();

         while(byteValuesEnum.hasMoreElements()) {
            byte[] thisVal = (byte[])((byte[])byteValuesEnum.nextElement());
            if (thisVal.length > 0) {
               try {
                  Syntax modValue = (Syntax)valClass.newInstance();
                  modValue.setValue(thisVal);
                  modValues.addElement(modValue);
                  if (this.debugEnabled) {
                     log.debug("modify value " + modValue);
                  }
               } catch (InstantiationException var22) {
                  throw new LDAPException("conversion error", LDAPResult.OTHER.intValue(), var22.getMessage());
               } catch (IllegalAccessException var23) {
                  throw new LDAPException("conversion error", LDAPResult.OTHER.intValue(), var23.getMessage());
               }
            }
         }

         EntryChange oneChange = new EntryChange(modType, modAttr, modValues);
         changeVector.addElement(oneChange);
      }

      DirectoryString ds = new DirectoryString(DN);
      Credentials localCreds = new Credentials();
      localCreds.setUser(new DirectoryString(this.savedDN));
      if (((String)ServerConfig.getInstance().get("vde.rootuser")).equalsIgnoreCase(this.savedDN)) {
         localCreds.setRoot(true);
      }

      try {
         BackendHandler.getInstance().modify(localCreds, ds, changeVector);
      } catch (DirectoryException var27) {
         if (var27.getMessage() != null) {
            throw new LDAPException("error result", var27.getLDAPErrorCode(), var27.getMessage());
         }

         throw new LDAPException("error result", var27.getLDAPErrorCode());
      } finally {
         localCreds = null;
      }

   }

   public void modify(String DN, LDAPModification[] mods, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void modify(String DN, LDAPModification[] mods, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void delete(String DN) throws LDAPException {
      LDAPException delegateExc = null;
      if (this.MSI_LOCAL_LDAP_ONLY) {
         if (this.msiMode) {
            this.msiMode = !ManagementService.getRuntimeAccess(kernelId).isAdminServerAvailable();
         }

         if (this.msiMode && !this.writeFailover) {
            throw new LDAPException("Admin server unavailable and write failover is not enabled.");
         }

         if (!this.inMasterServer && this.delegate == null && !this.msiMode) {
            try {
               this.delegate = this.getAndInitDelegate();
            } catch (LDAPException var8) {
               this.determineWriteFailover(var8);
            }
         }
      } else if (!this.inMasterServer && this.delegate == null) {
         try {
            this.delegate = this.getAndInitDelegate();
         } catch (LDAPException var7) {
            this.determineWriteFailover(var7);
         }
      }

      boolean delegateWrite = false;
      if (this.delegate != null) {
         try {
            this.delegate.delete(DN);
            delegateWrite = true;
         } catch (LDAPException var9) {
            if (var9.getLDAPResultCode() == 32) {
               delegateExc = var9;
            } else {
               this.determineWriteFailover(var9);
            }
         }
      }

      if (this.debugEnabled) {
         log.debug("delete entry " + DN);
      }

      DirectoryString ds = new DirectoryString(DN);
      Credentials localCreds = new Credentials();
      localCreds.setUser(new DirectoryString(this.savedDN));
      if (((String)ServerConfig.getInstance().get("vde.rootuser")).equalsIgnoreCase(this.savedDN)) {
         localCreds.setRoot(true);
      }

      Int8 res = BackendHandler.getInstance().delete(localCreds, ds);
      localCreds = null;
      if (res != LDAPResult.SUCCESS) {
         if (!delegateWrite || res != LDAPResult.NO_SUCH_OBJECT) {
            throw new LDAPException("error result", res.intValue());
         }

         if (this.debugEnabled) {
            log.debug("delegate success and local no such object");
         }
      }

      if (delegateExc != null) {
         throw delegateExc;
      }
   }

   public void delete(String DN, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void delete(String DN, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String DN, String newRDN, boolean deleteOldRDN) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String DN, String newRDN, boolean deleteOldRDN, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String DN, String newRDN, boolean deleteOldRDN, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String dn, String newRDN, String newParentDN, boolean deleteOldRDN) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String DN, String newRDN, String newParentDN, boolean deleteOldRDN, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public void rename(String DN, String newRDN, String newParentDN, boolean deleteOldRDN, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
   }

   public LDAPResponseListener add(LDAPEntry entry, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener add(LDAPEntry entry, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener bind(int version, String dn, String passwd, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener bind(String dn, String passwd, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener bind(String dn, String passwd, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener bind(int version, String dn, String passwd, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener delete(String dn, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener delete(String dn, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener modify(String dn, LDAPModification mod, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener modify(String dn, LDAPModification mod, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener modify(String dn, LDAPModificationSet mods, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener modify(String dn, LDAPModificationSet mods, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener rename(String dn, String newRdn, boolean deleteOldRdn, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener rename(String dn, String newRdn, boolean deleteOldRdn, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPSearchListener search(String base, int scope, String filter, String[] attrs, boolean typesOnly, LDAPSearchListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPSearchListener search(String base, int scope, String filter, String[] attrs, boolean typesOnly, LDAPSearchListener listener, LDAPSearchConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener compare(String dn, LDAPAttribute attr, LDAPResponseListener listener) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public LDAPResponseListener compare(String dn, LDAPAttribute attr, LDAPResponseListener listener, LDAPConstraints cons) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public void abandon(int id) throws LDAPException {
      throwUnsupported();
   }

   public void abandon(LDAPSearchListener searchlistener) throws LDAPException {
      throwUnsupported();
   }

   public Object getOption(int option) throws LDAPException {
      throwUnsupported();
      return null;
   }

   public void setOption(int option, Object value) throws LDAPException {
   }

   public LDAPControl[] getResponseControls() {
      return null;
   }

   public LDAPConstraints getConstraints() {
      return null;
   }

   public LDAPSearchConstraints getSearchConstraints() {
      return null;
   }

   public void setConstraints(LDAPConstraints cons) {
   }

   public void setSearchConstraints(LDAPSearchConstraints cons) {
   }

   public InputStream getInputStream() {
      return null;
   }

   public void setInputStream(InputStream is) {
   }

   public OutputStream getOutputStream() {
      return null;
   }

   public void setOutputStream(OutputStream os) {
   }

   public synchronized Object clone() {
      return null;
   }

   public static boolean isNetscape() {
      return false;
   }

   private LDAPAttributeSet attributeSetFromEntry(Entry entry, boolean returnValues, Vector attrs) {
      LDAPAttributeSet attrSet = new LDAPAttributeSet();
      Attribute curAttr = null;
      Vector values = null;
      Syntax curval = null;
      Vector attributes = entry.getAttributes();
      int size = attributes.size();

      for(int i = 0; i < size; ++i) {
         curAttr = (Attribute)attributes.elementAt(i);
         LDAPAttribute attr = null;
         if (!returnValues) {
            attr = new LDAPAttribute(curAttr.type.getDirectoryString());
            int vsize = curAttr.values.size();

            for(int j = 0; j < vsize; ++j) {
               curval = (Syntax)curAttr.values.elementAt(j);
               if (curval != null) {
                  attr.addValue(curval.getValue());
               }
            }
         } else {
            attr = new LDAPAttribute(curAttr.type.getDirectoryString());
         }

         attrSet.add(attr);
      }

      return attrSet;
   }

   private Entry LDAPEntryToEntry(LDAPEntry ldapEntry) throws InvalidDNException {
      Entry entry = new Entry(new DirectoryString(ldapEntry.getDN()));
      LDAPAttributeSet attrSet = ldapEntry.getAttributeSet();
      Enumeration enumAttr = attrSet.getAttributes();

      while(true) {
         LDAPAttribute attr;
         do {
            if (!enumAttr.hasMoreElements()) {
               return entry;
            }

            attr = (LDAPAttribute)enumAttr.nextElement();
         } while(attr.getName() == null);

         DirectoryString type = new DirectoryString(attr.getName());
         AttributeType at = SchemaChecker.getInstance().getAttributeType(type);
         Class valClass = null;
         if (at != null) {
            valClass = at.getSyntaxClass();
         } else {
            valClass = DirectoryString.class;
         }

         Vector values = new Vector();
         Enumeration enumVal = attr.getByteValues();

         while(enumVal.hasMoreElements()) {
            byte[] thisVal = (byte[])((byte[])enumVal.nextElement());
            if (thisVal.length > 0) {
               try {
                  Syntax synVal = (Syntax)valClass.newInstance();
                  synVal.setValue(thisVal);
                  values.addElement(synVal);
               } catch (InstantiationException var13) {
                  EmbeddedLDAPLogger.logStackTrace(var13);
               } catch (IllegalAccessException var14) {
                  EmbeddedLDAPLogger.logStackTrace(var14);
               }
            }
         }

         if (values.size() > 0) {
            entry.put(type, values, false);
         }
      }
   }

   private LDAPConnection getDelegate() {
      LDAPConnection conn = null;
      if (this.debugEnabled) {
         log.debug("Creating LDAP Connection delegate, useSSL is " + this.useSSL + ", keep alive is " + (this.keepAliveEnabled ? " enabled." : " disabled."));
      }

      LDAPSocketFactory factory = null;
      if (this.useSSL) {
         factory = new EmbeddedLDAPSSLSocketFactory(this.ignoreCertPathValidators, this.keepAliveEnabled);
      } else {
         factory = new EmbeddedLDAPBaseSocketFactory(this.keepAliveEnabled);
      }

      conn = new LDAPConnection((LDAPSocketFactory)factory);
      Integer connectTimeout = Integer.getInteger("weblogic.security.embeddedLDAPConnectTimeout");
      if (null != connectTimeout) {
         conn.setConnectTimeout(connectTimeout);
      }

      int timeout = EmbeddedLDAP.getEmbeddedLDAP().getTimeout();
      if (timeout > 0) {
         Integer newLimit = new Integer(timeout * 1000);

         try {
            conn.setOption(4, newLimit);
         } catch (LDAPException var7) {
            if (this.debugEnabled) {
               log.debug("Error setting timeout " + var7);
            }
         }
      }

      return conn;
   }

   private LDAPConnection getAndInitDelegate() throws LDAPException {
      LDAPConnection newDelegate = this.getDelegate();
      if (this.debugEnabled) {
         log.debug("Initializing LDAP Connection delegate");
      }

      if (this.masterHost != null) {
         if (this.debugEnabled) {
            log.debug("Connecting write delegate to " + this.masterHost + ":" + this.masterPort);
         }

         this.connect(newDelegate, this.masterHost, this.masterPort);
      }

      if (this.savedDN != null) {
         if (this.debugEnabled) {
            log.debug("Binding delegate to " + this.savedDN);
         }

         newDelegate.bind(this.protocolVersion, this.savedDN, this.savedPasswd);
      }

      return newDelegate;
   }

   private static void throwUnsupported() throws LDAPException {
      Thread.dumpStack();
      throw new LDAPException("EmbeddedLDAPConnection does not support this method", 92);
   }

   private void determineFailover(LDAPException e) throws LDAPException {
      boolean failover = true;
      switch (e.getLDAPResultCode()) {
         default:
            failover = false;
         case 3:
         case 11:
         case 51:
         case 52:
         case 80:
         case 81:
         case 85:
         case 91:
            if (failover) {
               if (this.debugEnabled) {
                  log.debug("Failing over to local replicated server");
               }

               try {
                  this.delegate.disconnect();
               } catch (Exception var4) {
               }

               this.delegate = null;
               this.bind(this.protocolVersion, this.savedDN, this.savedPasswd);
            } else {
               throw e;
            }
      }
   }

   private void determineWriteFailover(LDAPException e) throws LDAPException {
      if (!this.writeFailover) {
         throw e;
      } else {
         this.determineFailover(e);
         EmbeddedLDAP.getEmbeddedLDAP().setReplicaInvalid();
      }
   }

   private void connect(LDAPConnection conn, String host, int port) throws LDAPException {
      ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(EmbeddedLDAPConnection.class.getClassLoader());

      try {
         conn.connect(host, port);
      } finally {
         Thread.currentThread().setContextClassLoader(oldContextClassLoader);
      }

   }

   @Service
   private static class EmbeddedLDAPConnectionServiceGeneratorImpl implements EmbeddedLDAPConnectionServiceGenerator {
      public EmbeddedLDAPConnectionService createEmbeddedLDAPConnectionService(boolean masterFirst, boolean connWriteFailover, boolean ignoreCertPathValidators) {
         return new EmbeddedLDAPConnection(masterFirst, connWriteFailover, ignoreCertPathValidators);
      }
   }

   private static class EmbeddedLDAPBaseSocketFactory implements LDAPSocketFactory {
      protected boolean keepAliveEnabled;

      private EmbeddedLDAPBaseSocketFactory(boolean keepAliveEnabled) {
         this.keepAliveEnabled = true;
         this.keepAliveEnabled = keepAliveEnabled;
      }

      public Socket makeSocket(String host, int port) throws LDAPException {
         try {
            Socket socket = new Socket(host, port);
            socket.setKeepAlive(this.keepAliveEnabled);
            return socket;
         } catch (Exception var5) {
            LDAPException ex = new LDAPException(var5.getMessage(), 91);
            ex.initCause(var5);
            throw ex;
         }
      }

      // $FF: synthetic method
      EmbeddedLDAPBaseSocketFactory(boolean x0, Object x1) {
         this(x0);
      }
   }

   private static class EmbeddedLDAPSSLSocketFactory extends EmbeddedLDAPBaseSocketFactory {
      private boolean ignoreCertPathValidators;
      private SSLSocketFactory socketFactory;

      private EmbeddedLDAPSSLSocketFactory(boolean ignoreCertPathValidators, boolean keepAliveEnabled) {
         super(keepAliveEnabled, null);
         this.ignoreCertPathValidators = false;
         this.socketFactory = null;
         this.ignoreCertPathValidators = ignoreCertPathValidators;
      }

      public Socket makeSocket(String host, int port) throws LDAPException {
         try {
            SSLSocket socket = (SSLSocket)this.getFactory().createSocket(host, port);
            socket.setKeepAlive(this.keepAliveEnabled);
            socket.startHandshake();
            return socket;
         } catch (Exception var5) {
            LDAPException ex = new LDAPException(var5.getMessage(), 91);
            ex.initCause(var5);
            throw ex;
         }
      }

      private synchronized SSLSocketFactory getFactory() throws Exception {
         if (this.socketFactory == null) {
            if (this.ignoreCertPathValidators) {
               CertPathTrustManager trustManager = new CertPathTrustManager();
               trustManager.setBuiltinSSLValidationOnly();
               this.socketFactory = SSLContextManager.getSSLSocketFactory(EmbeddedLDAPConnection.kernelId, trustManager);
            } else {
               this.socketFactory = SSLContextManager.getDefaultClientSSLSocketFactory("ldaps", EmbeddedLDAPConnection.kernelId);
            }
         }

         return this.socketFactory;
      }

      // $FF: synthetic method
      EmbeddedLDAPSSLSocketFactory(boolean x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }
}
