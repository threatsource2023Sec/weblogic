package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.ConfigHelper;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.internal.TCTransactionHelper;
import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.SocketFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CodeSet;
import weblogic.jdbc.common.internal.AffinityContextHelper;
import weblogic.jdbc.common.internal.AffinityContextHelperFactory;
import weblogic.management.ManagementException;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.JSSESocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.Debug;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.gwt.GwtUtil;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.gwt.TDMImport;
import weblogic.wtc.gwt.TDMRemoteTDomain;
import weblogic.wtc.gwt.TimerEventManager;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoCorbaConnection;
import weblogic.wtc.gwt.WTCService;
import weblogic.wtc.gwt.WTCStatisticsRuntimeMBeanImpl;

public class dsession implements CorbaAtmi {
   protected String lpwd;
   protected String rpwd;
   protected String desired_name;
   protected String dom_target_name = null;
   protected atn gssatn = null;
   protected int dom_protocol;
   protected int security_type;
   protected tplle myLLE;
   protected boolean interoperate = false;
   protected TCAppKey myAppKey = null;
   protected String myAppKeySel = null;
   protected boolean myAllowAnon;
   protected int myDfltAppKey;
   protected String myUidKw = null;
   protected String myGidKw = null;
   protected String myCustomAppKeyClass = null;
   protected String myCustomAppKeyClassParam = null;
   private int acl_policy = 0;
   private int cred_policy = 0;
   private String tpusrfile;
   private int eflags;
   private int elevel;
   private InetAddress[] domaddr_ip;
   private int[] domaddr_port;
   private String remote_domain_id;
   private String local_domain_name;
   private Socket dom_socket;
   private DataOutputStream dom_ostream;
   private DataInputStream dom_istream;
   private TuxedoMuxableSocket ein;
   private JSSESocket jsseSock;
   private TpeOut eout;
   private TpeIn rein;
   private int auth_type = -1;
   private boolean is_term = false;
   private boolean is_connected = false;
   private int reqid = 0;
   private int convid = 0;
   private int cmplimit = Integer.MAX_VALUE;
   private rdsession rcv_place;
   private InvokeSvc invoker;
   private boolean connector = true;
   private int uid;
   private Timer myTimeService;
   private Timer asyncTimeService;
   private TuxRply myRplyObj;
   private long myBlockTime;
   private TuxXidRply myXidRplyObj;
   private OnTerm myTerminationHandler;
   private ClientInfo myCltInfo = null;
   private BetaFeatures useBetaFeatures = null;
   private HashMap rmiCallList = null;
   private Object lockObject = new Object();
   private boolean cachedUR = false;
   private int tmsndprio = 50;
   private int kas = -2;
   private int kaws = -1;
   private int ka = -2;
   private int kaw = -1;
   protected int rdom_features = 0;
   private long lastRecvTime = 0L;
   private int kaState = -1;
   private Lock myLock = new ReentrantLock();
   private KeepAliveTask kaTask = null;
   private long kaExpTime = 0L;
   private long kawExpTime = 0L;
   private boolean useSSL = false;
   private String identityKeyStoreType;
   private String identityKeyStore;
   private String identityKeyStorePassphrase;
   private String identityKeyAlias;
   private String identityKeyPassphrase;
   private String trustKeyStoreType;
   private String trustKeyStore;
   private String trustKeyStorePassphrase;
   private int minEncryptBits = 0;
   private int maxEncryptBits = 256;
   private boolean[] useSDP;
   private WTCService myWTCSvc = null;
   private WTCStatisticsRuntimeMBeanImpl myWTCStat = null;
   private ConcurrentHashMap cdToImpSvc = null;
   public static final int KA_NONE = -1;
   public static final int KA_INIT = 0;
   public static final int KA_ACTIVE = 1;
   public static final int KA_WAIT = 2;
   public static final int KA_SCHEDULED = 3;
   public static final int KA_SHUTDOWN = 4;
   public static final int KA_DISABLED = 5;
   public static final int KA_PENDING = 6;
   public static final int DMACL_LOCAL = 0;
   public static final int DMACL_GLOBAL = 1;
   public static final int TM_PRIORANGE = 100;
   public static final int TM_SENDBASE = 536870912;
   private static final String ANONYMOUS_USER = "anonymous";
   public static final String SEL_TPUSRFILE = "TpUsrFile";
   public static final String SEL_LDAP = "LDAP";
   private static boolean useCORBATimeout = Boolean.getBoolean("weblogic.wtc.corba.Timeout");
   private OatmialServices tos = null;
   private static boolean enable64BitsLong = Boolean.getBoolean("weblogic.wtc.use64BitsLong");
   private int flags = 0;
   private int char_codeset;
   private int wchar_codeset;
   private IOR remoteCodeBase;
   private int minorVersion;

   public dsession(Timer tsd, InetAddress ip, int port, int myUid, TuxXidRply anXidRply, BetaFeatures betaFeatures) {
      this.char_codeset = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.wchar_codeset = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.remoteCodeBase = null;
      this.minorVersion = 2;
      this.domaddr_ip = new InetAddress[1];
      this.domaddr_port = new int[1];
      this.domaddr_ip[0] = ip;
      this.domaddr_port[0] = port;
      this.uid = myUid;
      this.myTimeService = tsd;
      this.myRplyObj = new TuxRply();
      this.myXidRplyObj = anXidRply;
      this.useBetaFeatures = betaFeatures;
      this.myWTCSvc = WTCService.getWTCService();
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)this.myWTCSvc.getWTCStatisticsRuntimeMBean();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tos = ConfigHelper.getTuxedoServices();
   }

   public dsession(Timer tsd, InetAddress ip, int port, atn gssimpl, int myUid, TuxXidRply anXidRply, BetaFeatures betaFeatures) {
      this.char_codeset = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.wchar_codeset = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.remoteCodeBase = null;
      this.minorVersion = 2;
      this.domaddr_ip = new InetAddress[1];
      this.domaddr_port = new int[1];
      this.domaddr_ip[0] = ip;
      this.domaddr_port[0] = port;
      this.gssatn = gssimpl;
      this.uid = myUid;
      this.myTimeService = tsd;
      this.myRplyObj = new TuxRply();
      this.myXidRplyObj = anXidRply;
      this.useBetaFeatures = betaFeatures;
      this.myWTCSvc = WTCService.getWTCService();
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)this.myWTCSvc.getWTCStatisticsRuntimeMBean();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tos = ConfigHelper.getTuxedoServices();
   }

   public dsession(Timer tsd, InetAddress[] ip, int[] port, atn gssimpl, InvokeSvc invoke, int myUid, TuxXidRply anXidRply, BetaFeatures betaFeatures) {
      this.char_codeset = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.wchar_codeset = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.remoteCodeBase = null;
      this.minorVersion = 2;
      this.domaddr_ip = ip;
      this.domaddr_port = port;
      this.gssatn = gssimpl;
      this.invoker = invoke;
      this.uid = myUid;
      this.myTimeService = tsd;
      this.myRplyObj = new TuxRply();
      this.myXidRplyObj = anXidRply;
      this.useBetaFeatures = betaFeatures;
      this.myWTCSvc = WTCService.getWTCService();
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)this.myWTCSvc.getWTCStatisticsRuntimeMBean();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tos = ConfigHelper.getTuxedoServices();
   }

   public dsession(Timer tsd, InetAddress ip, int port, atn gssimpl, InvokeSvc invoke, int myUid, TuxXidRply anXidRply, BetaFeatures betaFeatures) {
      this.char_codeset = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.wchar_codeset = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.remoteCodeBase = null;
      this.minorVersion = 2;
      this.domaddr_ip = new InetAddress[1];
      this.domaddr_port = new int[1];
      this.domaddr_ip[0] = ip;
      this.domaddr_port[0] = port;
      this.gssatn = gssimpl;
      this.invoker = invoke;
      this.uid = myUid;
      this.myTimeService = tsd;
      this.myRplyObj = new TuxRply();
      this.myXidRplyObj = anXidRply;
      this.useBetaFeatures = betaFeatures;
      this.myWTCSvc = WTCService.getWTCService();
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)this.myWTCSvc.getWTCStatisticsRuntimeMBean();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tos = ConfigHelper.getTuxedoServices();
   }

   public dsession(Timer tsd, Socket tdom_socket, atn gssimpl, InvokeSvc invoke, int myUid, TuxXidRply anXidRply, boolean useSSL, BetaFeatures betaFeatures) throws IOException {
      this.char_codeset = CodeSet.DEFAULT_CHAR_NATIVE_CODE_SET;
      this.wchar_codeset = CodeSet.DEFAULT_WCHAR_NATIVE_CODE_SET;
      this.remoteCodeBase = null;
      this.minorVersion = 2;
      this.dom_socket = tdom_socket;

      try {
         this.ein = new TuxedoMuxableSocket(this.dom_socket, useSSL);
      } catch (SocketException var10) {
         throw new IOException("ERROR: Could not create Tuxedo Muxable Socket: " + var10);
      }

      this.useSSL = useSSL;
      this.eout = new TpeOut(this.dom_socket.getOutputStream());
      this.dom_ostream = new DataOutputStream(this.eout);
      this.rein = new TpeIn(this.dom_socket.getInputStream());
      this.dom_istream = new DataInputStream(this.rein);
      this.gssatn = gssimpl;
      this.invoker = invoke;
      this.connector = false;
      this.uid = myUid;
      this.myTimeService = tsd;
      this.myRplyObj = new TuxRply();
      this.myXidRplyObj = anXidRply;
      this.useBetaFeatures = betaFeatures;
      this.myWTCSvc = WTCService.getWTCService();
      this.myWTCStat = (WTCStatisticsRuntimeMBeanImpl)this.myWTCSvc.getWTCStatisticsRuntimeMBean();
      this.cdToImpSvc = new ConcurrentHashMap();
      this.tos = ConfigHelper.getTuxedoServices();
   }

   public InetAddress getIAddress() {
      return this.dom_socket.getInetAddress();
   }

   public TuxXidRply getUnknownRplyObj() {
      return this.myXidRplyObj;
   }

   public void set_BlockTime(long blocktime) {
      this.myBlockTime = blocktime;
      if (this.rcv_place != null) {
         this.rcv_place.set_BlockTime(blocktime);
      }

   }

   public long get_BlockTime() {
      return this.myBlockTime;
   }

   public Timer get_TimeService() {
      return this.myTimeService;
   }

   public void set_invoker(InvokeSvc invoke) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/set_invoker/" + this.invoker);
      }

      if (this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/set_invoker/30/");
         }

         throw new TPException(9, "Can not set invoker once connected");
      } else {
         this.invoker = invoke;
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/set_invoker/10");
         }

      }
   }

   public InvokeSvc get_invoker() {
      return this.invoker;
   }

   public void set_dom_target_name(String domname) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/set_dom_target_name/" + domname);
      }

      this.dom_target_name = new String(domname);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/set_dom_target_name/10/");
      }

   }

   public String getTargetName() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getTargetName/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getTargetName/10/" + this.dom_target_name);
      }

      return this.dom_target_name;
   }

   public void set_compression_threshold(int cmpl) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/set_compression_threshold/" + cmpl);
      }

      if (this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/set_compression_threshold/10/");
         }

         throw new TPException(9, "Can not set compression threshold once connected");
      } else {
         this.cmplimit = cmpl;
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/set_compression_threshold/20/");
         }

      }
   }

   public int getCompressionThreshold() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getCompressionThreshold/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getCompressionThreshold/10/" + this.cmplimit);
      }

      return this.cmplimit;
   }

   public void set_sess_sec(String sectype) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/set_sess_sec/" + sectype);
      }

      if (this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/set_sess_sec/10/");
         }

         throw new TPException(9, "Can not set security type once connected");
      } else {
         if (sectype.equals("NONE")) {
            this.security_type = 0;
         } else if (sectype.equals("DM_PW")) {
            this.security_type = 2;
         } else {
            if (!sectype.equals("APP_PW")) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/set_sess_sec/20/");
               }

               throw new TPException(9, "Invalid security type(" + sectype + ") specified");
            }

            this.security_type = 1;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/set_sess_sec/30/");
         }

      }
   }

   public int get_sess_sec() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/get_sess_sec/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/get_sess_sec/10/" + this.security_type);
      }

      return this.security_type;
   }

   public void setKeepAlive(int keepalive) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setKeepAlive/" + keepalive);
      }

      if (keepalive >= -1) {
         this.myLock.lock();
         if ((this.ka = keepalive) != 0) {
            if (this.ka % 1000 != 0) {
               this.kas = keepalive / 1000 + 1;
            } else {
               this.kas = keepalive / 1000;
            }

            if (this.kaw == -1) {
               this.kaws = 0;
            }

            if (this.is_connected && this.kaState == -1) {
               this.startKACountDown();
            }
         } else {
            this.kas = 0;
            if (this.kaState == 3) {
               this.kaTask.cancel();
            }

            this.kaState = -1;
         }

         this.myLock.unlock();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setKeepAlive/10/");
      }

   }

   public int getKeepAlive() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getKeepAlive/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/getKeepAlive/10/" + this.ka);
      }

      return this.ka;
   }

   public void setKeepAliveWait(int keepalivewait) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setKeepAliveWait/" + keepalivewait);
      }

      if (keepalivewait >= 0) {
         this.kaw = keepalivewait;
         if (this.kaw % 1000 != 0) {
            this.kaws = keepalivewait / 1000 + 1;
         } else {
            this.kaws = keepalivewait / 1000;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setKeepAliveWait/10/");
      }

   }

   public int getKeepAliveWait() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getKeepAliveWait/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/getKeepAliveWait/10/" + this.kaw);
      }

      return this.kaw;
   }

   public void setLocalPassword(String passwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setLocalPasswd/" + passwd);
      }

      this.lpwd = new String(passwd);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setLocalPasswd/10/");
      }

   }

   public void setSessionFeatures(int f) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setSessionFeatures/" + f);
      }

      if (enable64BitsLong) {
         TdomTcb.setRuntimeFeatureSupported(TdomTcb.getRuntimeFeatureSupported() | '耀');
      }

      this.rdom_features = f & TdomTcb.getRuntimeFeatureSupported();
      if ((this.rdom_features & '耀') != 0 && traceEnabled) {
         ntrace.doTrace("/dsession(" + this.uid + ")/setSessionFeatures/turn on GWT_FEATURE_XDR64_COMPAT");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setSessionFeatures/10");
      }

   }

   public int getSessionFeatures() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getSessionFeatures/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getSessionFeatures/10/" + this.rdom_features);
      }

      return this.rdom_features;
   }

   public void dmqDecision() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/dmqDecision/");
      }

      if ((this.rdom_features & 1) == 0) {
         if (this.ka > 0) {
            WTCLogger.logWarnDisableKeepAlive(this.local_domain_name, this.remote_domain_id);
         }

         this.myLock.lock();
         this.kaState = 5;
         this.myLock.unlock();
      } else {
         this.myLock.lock();
         if (this.kaState == 5) {
            this.kaState = -1;
         }

         this.myLock.unlock();
         if (this.ka > 0) {
            this.startKACountDown();
         }
      }

      this.asyncTimeService = WTCService.getAsyncTimerService();
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/dmqDecision/10");
      }

   }

   public void setRemotePassword(String passwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setRemotePasswd/" + passwd);
      }

      this.rpwd = new String(passwd);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setRemotePasswd/10/");
      }

   }

   public void setApplicationPassword(String passwd) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setApplicationPassword/" + passwd);
      }

      this.lpwd = new String(passwd);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setApplicationPassword/10/");
      }

   }

   public String getLocalPassword() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getLocalPassword/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getLocalPassword/10/" + this.lpwd);
      }

      return this.lpwd;
   }

   public String getRemotePassword() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getRemotePassword/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getRemotePassword/10/" + this.rpwd);
      }

      return this.rpwd;
   }

   public String getApplicationPassword() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getApplicationPassword/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getApplicationPassword/10/" + this.lpwd);
      }

      return this.lpwd;
   }

   public void setDesiredName(String desired) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setDesiredName/" + desired);
      }

      if (desired != null) {
         this.desired_name = new String(desired);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setDesiredName/10/");
      }

   }

   public String getDesiredName() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getDesiredName/");
         ntrace.doTrace("]/dsession(" + this.uid + ")/getDesiredName/10/" + this.desired_name);
      }

      return this.desired_name;
   }

   public void setEncryptionFlags(int ef) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setEncryptionFlags/" + ef);
      }

      this.eflags = ef;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setEncryptionFlags/10");
      }

   }

   public void setELevel(int l) {
      this.elevel = l;
   }

   public int getELevel() {
      return this.elevel;
   }

   public void setLLE() {
      if (this.elevel != 0 && this.elevel != 1) {
         this.rein.setElevel(this.elevel);
         this.rein.setLLE(this.myLLE);
         this.ein.setElevel(this.elevel);
         this.ein.setLLE(this.myLLE);
         this.eout.setElevel(this.elevel);
         this.eout.setLLE(this.myLLE);
      } else if (this.myLLE != null) {
         this.myLLE = null;
      }

   }

   public void setAclPolicy(String acl) {
      if (acl != null && acl.equals("GLOBAL")) {
         this.acl_policy = 1;
      } else {
         this.acl_policy = 0;
      }

   }

   public void setCredentialPolicy(String cred) {
      if (cred != null && cred.equals("GLOBAL")) {
         this.cred_policy = 1;
      } else {
         this.cred_policy = 0;
      }

   }

   public int getAclPolicy() {
      return this.acl_policy;
   }

   public int getCredentialPolicy() {
      return this.cred_policy;
   }

   public void setTpUserFile(String ufile) {
      if (ufile != null) {
         this.tpusrfile = new String(ufile);
      } else {
         this.tpusrfile = null;
      }

   }

   public String getTpUserFile() {
      return this.tpusrfile;
   }

   public int setUpTuxedoAAA() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setUpTuxedoAAA/");
      }

      if (this.myAppKey == null && this.cred_policy == 1 && this.dom_protocol >= 15) {
         String p1 = null;
         String p2 = null;
         if (this.myAppKeySel != null && this.myAppKeySel.compareToIgnoreCase("TpUsrFile") != 0) {
            if (this.myAppKeySel.compareToIgnoreCase("LDAP") == 0) {
               p1 = this.myUidKw;
               p2 = this.myGidKw;
            } else {
               p1 = this.myCustomAppKeyClass;
               p2 = this.myCustomAppKeyClassParam;
            }
         } else {
            p1 = this.tpusrfile;
            if (p1 == null) {
               p1 = WTCService.getGlobalTpUsrFile();
            }
         }

         this.myAppKey = TCSecurityManager.getSecurityService().getAppKeyGenerator(this.myAppKeySel, p1, p2, this.myAllowAnon, this.myDfltAppKey);
         if (this.myAppKey != null) {
            this.cachedUR = this.myAppKey.isCached();
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setUpTuxedoAAA/10/return 0");
      }

      return 0;
   }

   public void setInteroperate(boolean allowed) {
      this.interoperate = allowed;
   }

   public boolean getInteroperate() {
      return this.interoperate;
   }

   public boolean getIsTerminated() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/getIsTerminated/ldom=" + this.local_domain_name + " rdom=" + this.remote_domain_id);
         ntrace.doTrace("]/dsession(" + this.uid + ")/getIsTerminated/" + this.is_term);
      }

      return this.is_term;
   }

   public void setIsTerminated() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.local_domain_name + ", " + this.remote_domain_id + ")/setIsTerminated");
      }

      if (!this.is_term) {
         this.terminateTDomainSession(false);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setIsTerminated(10)/done");
      }

   }

   public void doLocalTerminate() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.local_domain_name + ", " + this.remote_domain_id + ")/doLocalTerminate");
      }

      if (!this.is_term) {
         this.terminateTDomainSession(true);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession/doLocalTerminate(10)/done");
      }

   }

   public boolean get_is_connected() {
      return this.is_connected;
   }

   public void set_is_connected(boolean isc) {
      this.is_connected = isc;
   }

   public String get_local_domain_name() {
      return this.local_domain_name;
   }

   public void set_local_domain_name(String ldn) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/set_local_domain_name/" + ldn);
      }

      this.local_domain_name = ldn;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/set_local_domain_name/10");
      }

   }

   public String getRemoteDomainId() {
      return this.remote_domain_id;
   }

   public void setRemoteDomainId(String rdn) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/setRemoteDomainId/" + rdn);
      }

      this.remote_domain_id = rdn;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/setRemoteDomainId/10");
      }

   }

   public void setAppKey(String appkeyType) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setAppKey/" + appkeyType);
      }

      this.myAppKeySel = appkeyType;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setAppKey/10/");
      }

   }

   public void setDfltAppKey(int val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setDfltAppKey/" + val);
      }

      this.myDfltAppKey = val;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setDfltAppKey/10/");
      }

   }

   public void setAllowAnonymous(boolean val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setAllowAnonymous/" + val);
      }

      this.myAllowAnon = val;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setAllowAnonymous/10/");
      }

   }

   public void setUidKw(String kw) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setUidKw/" + kw);
      }

      this.myUidKw = kw.trim();
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setUidKw/10/");
      }

   }

   public void setGidKw(String kw) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setGidKw/" + kw);
      }

      this.myGidKw = kw.trim();
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setGidKw/10/");
      }

   }

   public void setCustomAppKeyClass(String val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setCustomAppKeyClass/" + val);
      }

      this.myCustomAppKeyClass = val;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setCustomAppKeyClass/10/");
      }

   }

   public void setCustomAppKeyClassParam(String val) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setCustomAppKeyClassParam/" + val);
      }

      this.myCustomAppKeyClassParam = val;
      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setCustomAppKeyClassParam/10/");
      }

   }

   public int tpchkauth() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpchkauth/");
      }

      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpchkauth/10/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/tpchkauth/20/" + this.auth_type);
         }

         return this.auth_type;
      }
   }

   public void set_authtype(int authtype) {
      this.auth_type = authtype;
   }

   public boolean get_is_connector() {
      return this.connector;
   }

   public DataOutputStream get_output_stream() {
      return this.dom_ostream;
   }

   public DataInputStream get_input_stream() {
      return this.dom_istream;
   }

   public void setInProtocol(int protocol) {
      this.dom_protocol = protocol;
      this.ein.setProtocol(protocol);
      this.rein.setProtocol(protocol);
   }

   public void setOutProtocol(int protocol) {
      this.eout.setProtocol(protocol);
   }

   public void set_rcv_place(rdsession rp) {
      this.rcv_place = rp;
      if (this.useSSL) {
         try {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext((SSLSocket)this.dom_socket);
            SSLFilter sslf = null;
            if (sslIOCtx != null) {
               sslf = (SSLFilter)sslIOCtx.getFilter();
               this.ein.setSocketFilter(sslf);
            }

            if (sslf != null) {
               sslf.setDelegate(this.ein);
               sslf.activate();
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      this.ein.setRecvSession(this.rcv_place);
   }

   public rdsession get_rcv_place() {
      return this.rcv_place;
   }

   public void setUseSSL(boolean useSSL) {
      this.useSSL = useSSL;
   }

   public boolean getUseSSL() {
      return this.useSSL;
   }

   public void setIdentityKeyStoreType(String ikst) {
      this.identityKeyStoreType = ikst;
   }

   public void setIdentityKeyStore(String iks) {
      this.identityKeyStore = iks;
   }

   public String getIdentityKeyStore() {
      return this.identityKeyStore;
   }

   public void setIdentityKeyStorePassphrase(String iksp) {
      this.identityKeyStorePassphrase = iksp;
   }

   public void setIdentityKeyAlias(String alias) {
      this.identityKeyAlias = alias;
   }

   public void setIdentityKeyPassphrase(String ap) {
      this.identityKeyPassphrase = ap;
   }

   public void setTrustKeyStoreType(String tkst) {
      this.trustKeyStoreType = tkst;
   }

   public void setTrustKeyStore(String tks) {
      this.trustKeyStore = tks;
   }

   public void setTrustKeyStorePassphrase(String tksp) {
      this.trustKeyStorePassphrase = tksp;
   }

   public void setMinEncryptBits(int bits) {
      this.minEncryptBits = bits;
   }

   public void setMaxEncryptBits(int bits) {
      this.maxEncryptBits = bits;
   }

   public final int getMinorVersion() {
      return this.minorVersion;
   }

   public final void setMinorVersion(int minorVersion) {
      this.minorVersion = minorVersion;
   }

   public final void setCodeSets(int cs, int ws) {
      this.char_codeset = cs;
      this.wchar_codeset = ws;
   }

   public final int getWcharCodeSet() {
      return this.wchar_codeset;
   }

   public final int getCharCodeSet() {
      return this.char_codeset;
   }

   public void setFlag(int f) {
      this.flags |= f;
   }

   public boolean getFlag(int f) {
      return (this.flags & f) != 0;
   }

   public final IOR getRemoteCodeBase() {
      return this.remoteCodeBase;
   }

   public final void setRemoteCodeBase(IOR ior) {
      this.remoteCodeBase = ior;
   }

   public void setUseSDP(boolean[] useSDP) {
      this.useSDP = useSDP;
   }

   private void setUse64BitsLong(tfmh tmmsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setUse64BitsLong");
      }

      if ((this.rdom_features & '耀') != 0 && enable64BitsLong) {
         if (tmmsg == null) {
            return;
         }

         tcm user_tcm = tmmsg.user;
         if (user_tcm == null) {
            return;
         }

         tcb user_tcb = user_tcm.body;
         if (user_tcb == null) {
            return;
         }

         if (user_tcb.getType() == 0) {
            user_tcm.setUse64BitsLong(true);
            ((UserTcb)user_tcb).setUse64BitsLong(true);
            tmmsg.getMetahdr().setFlags(tmmsg.getMetahdr().getFlags() | 67108864);
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/setUse64BitsLong/true/set TMXDR64COMPAT in metahdr");
            }

            return;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession/setUse64BitsLong/false");
      }

   }

   private void do_connect(String domainid) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/do_connect/" + domainid);
      }

      int opcode = 0;
      int more_work = true;
      byte[] send_buf = null;
      byte[] recv_buf = null;
      tcm user = null;
      atncredtdom mygsscred = null;
      atnctxtdom mygssctx = null;
      TypedCArray myuserdata = null;
      int lcv;
      char[] domid_char;
      if (domainid != null) {
         if (domainid.length() > 30) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/10/");
            }

            throw new TPException(4, "domainid must be less than 30 characters");
         }

         domid_char = domainid.toCharArray();
      } else {
         domid_char = new char[32];

         for(lcv = 0; lcv < 32; ++lcv) {
            domid_char[lcv] = 0;
         }
      }

      if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if (this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/30/");
         }

         throw new TPException(9, "Domain session is already connected");
      } else {
         tfmh initmsg = new tfmh(16, (tcm)null, 1);
         TdomTcb inittdom = this.alloc_TDOM(14, 0, (String)null);
         initmsg.tdom = new tcm((short)7, inittdom);
         boolean done = false;

         for(lcv = 0; lcv < this.domaddr_ip.length && !done; ++lcv) {
            if (this.domaddr_ip[lcv] == null) {
               if (lcv >= this.domaddr_ip.length - 1) {
                  WTCLogger.logWarnNoMoreValidRemoteAddress(this.local_domain_name, this.remote_domain_id);
                  break;
               }
            } else {
               try {
                  if (this.useSDP[lcv] && this.useSSL) {
                     this.useSSL = false;
                     WTCLogger.logWarnIgnoreSSLwithSDP(domainid);
                  }

                  if (this.useSSL) {
                     String principalName = this.identityKeyAlias;
                     if (principalName == null && (principalName = this.desired_name) == null) {
                        principalName = this.local_domain_name;
                     }

                     SocketFactory factory = new TuxedoSSLSocketFactory(this.identityKeyStoreType, this.identityKeyStore, this.identityKeyStorePassphrase, principalName, this.identityKeyPassphrase, this.trustKeyStoreType, this.trustKeyStore, this.trustKeyStorePassphrase);
                     this.dom_socket = factory.createSocket(this.domaddr_ip[lcv], this.domaddr_port[lcv]);
                     String[] supportedProtocols = new String[]{"TLSv1"};
                     ((SSLSocket)this.dom_socket).setEnabledProtocols(supportedProtocols);
                     ((SSLSocket)this.dom_socket).setEnabledCipherSuites(TuxedoSSLSocketFactory.getCiphers(this.minEncryptBits, this.maxEncryptBits));
                  } else {
                     InetSocketAddress isa;
                     if (!this.useSDP[lcv]) {
                        isa = new InetSocketAddress(this.domaddr_ip[lcv], this.domaddr_port[lcv]);
                        this.dom_socket = SocketMuxer.getMuxer().newSocket(isa.getAddress(), isa.getPort(), (int)this.myBlockTime);
                     } else {
                        isa = new InetSocketAddress(this.domaddr_ip[lcv], this.domaddr_port[lcv]);
                        this.dom_socket = SocketMuxer.getMuxer().newSDPSocket(isa.getAddress(), isa.getPort(), (InetAddress)null, 0, (int)this.myBlockTime);
                     }
                  }

                  done = true;
               } catch (Exception var33) {
                  if (lcv < this.domaddr_ip.length - 1) {
                     WTCLogger.logInfoTryNextAddress(this.domaddr_ip[lcv].getHostName(), this.domaddr_port[lcv]);
                  } else {
                     WTCLogger.logWarnNoMoreAddressToTry(this.domaddr_ip[lcv].getHostName(), this.domaddr_port[lcv]);
                  }
               }
            }
         }

         if (!done) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/35/exception");
            }

            throw new TPException(12, "Unable to create connection from local domain <" + this.local_domain_name + "> to remote TDomain <" + this.remote_domain_id + ">!");
         } else {
            try {
               this.ein = new TuxedoMuxableSocket(this.dom_socket, this.useSSL);
               this.ein.setSoTimeout((int)this.myBlockTime);
               if (this.useSSL) {
                  this.jsseSock = JSSEUtils.getJSSESocket((SSLSocket)this.dom_socket);
                  if (this.jsseSock != null) {
                     JSSEUtils.registerJSSEFilter(this.jsseSock, this.ein);
                     this.jsseSock.addHandshakeCompletedListener(new MyListener());
                     this.ein.setUseJSSE(true);
                     this.ein.setSocketFilter(this.jsseSock.getFilter());
                     JSSEUtils.activate(this.jsseSock, this.ein);
                     this.eout = new TpeOut(this.jsseSock.getOutputStream());
                     this.dom_ostream = new DataOutputStream(this.eout);
                     this.rein = new TpeIn(this.ein.getInputStream());
                     this.dom_istream = new DataInputStream(this.rein);
                     this.rein.setMuxableSocket(this.ein);
                  } else {
                     SSLIOContext sslIOCtx = SSLIOContextTable.findContext((SSLSocket)this.dom_socket);
                     SSLFilter sslf = null;
                     if (sslIOCtx != null) {
                        sslf = (SSLFilter)sslIOCtx.getFilter();
                        this.ein.setSocketFilter(sslf);
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/Performing SSL Handshake");
                     }

                     ((SSLSocket)this.dom_socket).addHandshakeCompletedListener(new MyListener());
                     ((SSLSocket)this.dom_socket).startHandshake();
                     if (sslf != null) {
                        sslf.setDelegate(this.ein);
                        sslf.activate();
                        this.eout = new TpeOut(this.dom_socket.getOutputStream());
                        this.dom_ostream = new DataOutputStream(this.eout);
                        this.rein = new TpeIn(this.ein.getInputStream());
                        this.dom_istream = new DataInputStream(this.rein);
                     }
                  }
               } else {
                  this.eout = new TpeOut(this.dom_socket.getOutputStream());
                  this.dom_ostream = new DataOutputStream(this.eout);
                  this.rein = new TpeIn(this.dom_socket.getInputStream());
                  this.dom_istream = new DataInputStream(this.rein);
               }

               this.local_domain_name = domainid;
               inittdom.set_lle_flags(this.eflags);
               int o_release;
               if (enable64BitsLong) {
                  o_release = TdomTcb.getRuntimeFeatureSupported();
                  TdomTcb.setRuntimeFeatureSupported(o_release | '耀');
               }

               if (initmsg.write_dom_65_tfmh(this.dom_ostream, domainid, 10, 134217727) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/40/");
                  }

                  throw new TPException(9, "Could not get authorization parameters from remote domain");
               }

               if (this.useSSL) {
                  if (traceEnabled) {
                     ntrace.doTrace("/dsession/(" + this.uid + ")/do_connect/SSL connection - waiting to receive data");
                  }

                  synchronized(this.ein) {
                     if (this.jsseSock == null) {
                        while(this.ein.getBufferOffset() == 0) {
                           try {
                              this.ein.wait();
                           } catch (InterruptedException var25) {
                           }
                        }
                     }
                  }
               }

               if (initmsg.read_dom_65_tfmh(this.dom_istream, 10) != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/50/");
                  }

                  throw new TPException(4, "Could not read message from remote domain");
               }

               if (inittdom.get_opcode() != 15) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/50/");
                  }

                  throw new TPException(4, "Invalid opcode");
               }

               if (inittdom.get_security_type() != this.security_type) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/60/");
                  }

                  throw new TPException(4, "Remote Security level(" + inittdom.get_security_type() + ") does not match local security level(" + this.security_type + ")");
               }

               this.rdom_features = inittdom.getFeaturesSupported() & TdomTcb.getRuntimeFeatureSupported();
               if (traceEnabled) {
                  ntrace.doTrace("Remote gateway support features = " + this.rdom_features);
               }

               if ((this.rdom_features & '耀') != 0 && traceEnabled) {
                  ntrace.doTrace("*/dsession(" + this.uid + ")/do_connect/turn on GWT_FEATURE_XDR64_COMPAT");
               }

               this.dom_protocol = inittdom.get_dom_protocol();
               this.ein.setProtocol(this.dom_protocol);
               this.rein.setProtocol(this.dom_protocol);
               this.eout.setProtocol(this.dom_protocol);
               if (traceEnabled) {
                  ntrace.doTrace(" /dsession(" + this.uid + ")/do_connect/dom_protocol " + this.dom_protocol);
               }

               o_release = this.dom_protocol & 31;
               if ((o_release < 13 || o_release == 14) && (this.dom_protocol & 2147483616 & 20) == 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/70/");
                  }

                  throw new TPException(4, "ERROR: Protocol level " + this.dom_protocol + " is not supported!");
               }

               if (o_release == 13 && !this.interoperate) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/65/");
                  }

                  throw new TPException(12, "Use Interoperate option to interoperate with sites older than 7.1");
               }

               int r_eflags = inittdom.get_lle_flags();
               if (this.dom_protocol >= 13) {
                  this.eflags &= r_eflags;
               } else {
                  this.eflags &= 1;
               }

               int send_size;
               int recv_size;
               TypedCArray tmp_ca;
               tcm rcv_user;
               UserTcb rcv_tcb;
               if (this.eflags == 1) {
                  if (traceEnabled) {
                     ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/no LLE protocol");
                  }

                  this.elevel = 0;
               } else {
                  if (this.eflags == 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/80/");
                     }

                     throw new TPException(4, "Link level encryption negotiation failure" + r_eflags);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/do LLE protocol");
                  }

                  this.myLLE = new tplle();
                  lcv = -1;
                  send_size = 2048;

                  while(lcv < 0) {
                     if (traceEnabled) {
                        ntrace.doTrace(" /dsession(" + this.uid + ")/do_connect/lle buffer " + send_size);
                     }

                     send_buf = new byte[send_size];
                     lcv = this.myLLE.crypKeyeOne(this.eflags, send_buf, 0);
                     if (lcv < 0) {
                        send_size = -lcv;
                     }
                  }

                  if (lcv == 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/80/");
                     }

                     throw new TPException(12, "Unable to generate first diffie-hellman packet" + r_eflags);
                  }

                  inittdom.setLLELength(send_size);
                  inittdom.setSendSecPDU(send_buf, send_size);
                  myuserdata = new TypedCArray();
                  user = new tcm((short)0, new UserTcb(myuserdata));
                  myuserdata.carray = send_buf;
                  initmsg.user = user;
                  myuserdata.setSendSize(lcv);
                  inittdom.set_opcode(20);
                  if (this.dom_protocol <= 13) {
                     if (initmsg.write_dom_65_tfmh(this.dom_ostream, domainid, 10, this.cmplimit) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/81/");
                        }

                        throw new TPException(4, "Could not send LLE message to remote domain");
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/...send LLE");
                     }

                     if (initmsg.read_dom_65_tfmh(this.dom_istream, 10) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/82/");
                        }

                        throw new TPException(4, "Could not read message from remote domain");
                     }
                  } else {
                     if (initmsg.write_tfmh(this.dom_ostream, this.cmplimit) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/83/");
                        }

                        throw new TPException(4, "Could not send LLE message to remote domain");
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/...send LLE");
                     }

                     if (initmsg.read_tfmh(this.dom_istream) != 0) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/84/");
                        }

                        throw new TPException(4, "Could not read message from remote domain");
                     }

                     inittdom = (TdomTcb)initmsg.tdom.body;
                  }

                  if (inittdom.get_opcode() != 21) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/85/");
                     }

                     throw new TPException(4, "Invalid opcode");
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/...recv LLE_RPLY");
                  }

                  rcv_user = initmsg.user;
                  rcv_tcb = (UserTcb)rcv_user.body;
                  tmp_ca = (TypedCArray)rcv_tcb.user_data;
                  recv_buf = tmp_ca.carray;
                  recv_size = recv_buf.length;
                  if (traceEnabled) {
                     ntrace.doTrace("recv size = " + recv_size);
                  }

                  switch (this.myLLE.crypFinishOne(recv_buf)) {
                     case 3:
                        this.elevel = 0;
                        this.myLLE = null;
                        break;
                     case 4:
                        this.elevel = 2;
                        break;
                     case 5:
                        this.elevel = 32;
                        break;
                     case 6:
                        this.elevel = 4;
                        break;
                     default:
                        this.myLLE = null;
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/83/");
                        }

                        throw new TPException(12, "ERROR: unexpected link level encryption failure");
                  }

                  if (this.elevel != 0) {
                     this.rein.setElevel(this.elevel);
                     this.rein.setLLE(this.myLLE);
                     this.ein.setElevel(this.elevel);
                     this.ein.setLLE(this.myLLE);
                     this.eout.setElevel(this.elevel);
                     this.eout.setLLE(this.myLLE);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/ready to use LLE");
                  }
               }

               if (this.security_type == 0) {
                  this.auth_type = 0;
               } else {
                  Object mygss;
                  if (this.dom_protocol <= 13) {
                     mygss = new atntdom65(this.desired_name);
                  } else {
                     if (this.gssatn == null) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/82/");
                        }

                        throw new TPException(12, "Missing appropriate GSSATN object");
                     }

                     mygss = this.gssatn;
                  }

                  ((atn)mygss).setSecurityType(this.security_type);
                  ((atn)mygss).setSrcName(this.desired_name);
                  ((atn)mygss).setTargetName(this.dom_target_name);
                  if (this.security_type == 1) {
                     ((atn)mygss).setApplicationPasswd(this.lpwd);
                  } else {
                     ((atn)mygss).setLocalPasswd(this.lpwd);
                     ((atn)mygss).setRemotePasswd(this.rpwd);
                  }

                  if (this.dom_protocol >= 15) {
                     ((atn)mygss).setInitiatorAddr(this.dom_socket.getLocalAddress().getAddress());
                     ((atn)mygss).setAcceptorAddr(this.dom_socket.getInetAddress().getAddress());
                     if (this.myLLE != null && this.elevel > 0 && this.elevel != 1) {
                        ((atn)mygss).setApplicationData(this.myLLE.getFingerprint());
                     }

                     myuserdata = new TypedCArray();
                     user = new tcm((short)0, new UserTcb(myuserdata));
                  }

                  try {
                     mygsscred = (atncredtdom)((atn)mygss).gssAcquireCred(this.desired_name, this.desired_name);
                  } catch (EngineSecError var27) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/90/");
                     }

                     throw new TPException(8, "Unable to acquire credentials (" + var27.errno + ")");
                  }

                  try {
                     mygssctx = (atnctxtdom)((atn)mygss).gssGetContext(mygsscred, this.dom_target_name);
                  } catch (EngineSecError var26) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/100/");
                     }

                     throw new TPException(8, "Unable to get security context (" + var26.errno + ")");
                  }

                  int more_work = 1;

                  while(more_work > 0) {
                     send_size = ((atn)mygss).getEstimatedPDUSendSize(mygssctx);
                     recv_size = ((atn)mygss).getEstimatedPDURecvSize(mygssctx);
                     if (send_size > 0) {
                        if (send_buf == null || send_buf.length < send_size) {
                           send_buf = new byte[send_size];
                           if (traceEnabled) {
                              ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/send_size " + send_size);
                           }
                        }

                        if (this.dom_protocol <= 13) {
                           switch (mygssctx.context_state) {
                              case 0:
                                 opcode = 16;
                                 break;
                              case 2:
                                 opcode = 18;
                           }
                        }
                     }

                     if (recv_size > 0) {
                        if (this.dom_protocol > 13) {
                           if (initmsg.read_tfmh(this.dom_istream) != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/120/");
                              }

                              throw new TPException(4, "Could not receive security exchange from remote domain");
                           }

                           rcv_user = initmsg.user;
                           rcv_tcb = (UserTcb)rcv_user.body;
                           tmp_ca = (TypedCArray)rcv_tcb.user_data;
                           recv_buf = tmp_ca.carray;
                           recv_size = recv_buf.length;
                           inittdom = (TdomTcb)initmsg.tdom.body;
                           if (traceEnabled) {
                              ntrace.doTrace("recv size = " + recv_size);
                           }
                        } else {
                           if (recv_buf == null || recv_buf.length < recv_size) {
                              recv_buf = new byte[recv_size];
                              if (traceEnabled) {
                                 ntrace.doTrace("/dsession(" + this.uid + ")/do_connect/recv size " + recv_size);
                              }
                           }

                           inittdom.setRecvSecPDU(recv_buf, recv_size);
                           if (initmsg.read_dom_65_tfmh(this.dom_istream, 10) != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/110/");
                              }

                              throw new TPException(4, "Could not receive security exchange from remote domain");
                           }
                        }
                     }

                     try {
                        more_work = ((atn)mygss).gssInitSecContext(mygssctx, recv_buf, recv_size, send_buf);
                     } catch (EngineSecError var30) {
                        if (var30.errno != -3005) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/140/");
                           }

                           throw new TPException(8, "Security violation (" + var30.errno + ")");
                        }

                        send_buf = new byte[var30.needspace];

                        try {
                           more_work = ((atn)mygss).gssInitSecContext(mygssctx, recv_buf, recv_size, send_buf);
                        } catch (EngineSecError var28) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/130/");
                           }

                           throw new TPException(8, "Security violation (" + var28.errno + ")");
                        }
                     }

                     if (more_work == -1) {
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/145/");
                        }

                        throw new TPException(8, "Security violation");
                     }

                     if (send_size > 0) {
                        int actual_send_size = ((atn)mygss).getActualPDUSendSize();
                        inittdom.setSendSecPDU(send_buf, actual_send_size);
                        if (this.dom_protocol <= 13) {
                           inittdom.set_opcode(opcode);
                           if (initmsg.write_dom_65_tfmh(this.dom_ostream, domainid, 10, this.cmplimit) != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/140/");
                              }

                              throw new TPException(4, "Could not send message to remote domain");
                           }
                        } else {
                           myuserdata.carray = send_buf;
                           initmsg.user = user;
                           myuserdata.setSendSize(actual_send_size);
                           inittdom.set_opcode(22);
                           if (initmsg.write_tfmh(this.dom_ostream, this.cmplimit) != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/150/");
                              }

                              throw new TPException(4, "Could not send message to remote domain");
                           }
                        }
                     }
                  }
               }
            } catch (SocketTimeoutException var31) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/155/Exception:" + var31);
               }

               throw new TPException(13, "Connection establishment timed out");
            } catch (IOException var32) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/160/Exception:" + var32);
               }

               if (DomainRegistry.getDuplicatedConnection(this) != -1) {
                  this.terminateTDomainSession(false);
                  return;
               }

               throw new TPException(12, "Unable to get authentication level");
            }

            if (this.setUpTuxedoAAA() < 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/do_connect/170/Error");
               }

               throw new TPException(12, "Unable to setup security authentication and auditing");
            } else {
               if (this.useSSL) {
                  this.ein.setBufferOffset(0);
               }

               this.rcv_place = new rdsession(this.dom_ostream, this, this.invoker, this.dom_protocol, this.local_domain_name, this.myTimeService, this.myXidRplyObj, this.useBetaFeatures);
               this.rcv_place.set_BlockTime(this.myBlockTime);
               this.rcv_place.setSessionReference(this);
               this.ein.setRecvSession(this.rcv_place);
               this.dmqDecision();
               WTCLogger.logInfoConnectedToRemoteDomain(this.remote_domain_id);
               this.is_connected = true;
               if (traceEnabled) {
                  ntrace.doTrace("]/dsession(" + this.uid + ")/do_connect/170/");
               }

            }
         }
      }
   }

   public synchronized void _dom_drop() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/_dom_drop/");
      }

      this.is_term = true;
      if (this.myTerminationHandler != null) {
         this.myTerminationHandler.onTerm(0);
         this.myTerminationHandler = null;
      }

      DomainRegistry.removeDomainSession(this);
      if (this.dom_socket == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/_dom_drop/10/");
         }

      } else {
         try {
            this.dom_ostream = null;
            this.dom_istream = null;
            if (this.ein != null) {
               this.ein.close();
               this.ein = null;
            }

            this.dom_socket.close();
            this.dom_socket = null;
         } catch (IOException var3) {
            WTCLogger.logIOEbadDomSocketClose(var3.getMessage());
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/_dom_drop/20/");
         }

      }
   }

   public void terminateTDomainSession(boolean localTermination) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      int uid = true;
      dsession rdses = null;
      boolean isDuplicate = false;
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.local_domain_name + ", " + this.remote_domain_id + ")/termimateTDomainSession/" + localTermination);
      }

      this.myLock.lock();
      this.is_term = true;
      int uid = DomainRegistry.getDuplicatedConnection(this);
      if (uid != -1) {
         rdses = (dsession)DomainRegistry.getDomainSession(uid);
         isDuplicate = true;
      }

      DomainRegistry.removeDomainSession(this);
      if (this.myAppKey != null) {
         try {
            this.myAppKey.uninit();
         } catch (TPException var7) {
            this.myLock.unlock();
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/terminateTDomainSession(10)/" + var7.getMessage());
            }

            return;
         }

         this.myAppKey = null;
      }

      if (this.kaTask != null && this.kaState == 3) {
         this.kaTask.cancel();
      }

      this.kaState = 4;
      this.kaTask = null;
      this.myLock.unlock();
      if (this.dom_socket != null) {
         try {
            this.dom_ostream = null;
            this.dom_istream = null;
            if (this.ein != null) {
               if (!this.ein.isClosed()) {
                  this.ein.close();
               }

               this.ein = null;
            }

            if (!this.dom_socket.isClosed()) {
               this.dom_socket.close();
            }

            this.dom_socket = null;
         } catch (InterruptedIOException var9) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/terminateTDomainSession(15) " + var9.getMessage());
            }
         } catch (IOException var10) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/terminateTDomainSession(20)" + var10.getMessage());
            }

            return;
         }
      }

      if (this.myTerminationHandler != null) {
         if (localTermination) {
            this.myTerminationHandler.onTerm(0);
         } else if (isDuplicate) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/terminateTDomainSession(25)" + rdses.getUid() + "/" + this.getUid() + "/" + rdses.getRemoteDomainId());
            }

            try {
               TDMRemoteTDomain rdomain = WTCService.getService().getRemoteTDomain(rdses.getRemoteDomainId());
               this.myTerminationHandler.onTerm(4);
               rdomain.setTsession(rdses);
            } catch (ManagementException var8) {
               if (traceEnabled) {
                  var8.printStackTrace();
                  ntrace.doTrace("]/dsession/terminateTDomainSession(26)" + var8.getMessage());
               }
            }
         } else {
            this.myTerminationHandler.onTerm(1);
         }

         this.myTerminationHandler = null;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession/terminateTDomainSession(30)");
      }

   }

   public synchronized void tpinit(TPINIT tpinfo) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpinit/" + tpinfo);
      }

      if (this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpinit/10/");
         }

         throw new TPException(9, "Can not init object more than once");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpinit/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if (!this.connector) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpinit/30/");
         }

         throw new TPException(9, "We are accepting, not connecting");
      } else {
         this.do_connect(tpinfo.usrname);
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/tpinit/30/");
         }

      }
   }

   public synchronized void tpterm() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpterm/");
      }

      if (!this.is_term) {
         this.terminateTDomainSession(true);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/tpterm/10/");
      }

   }

   private synchronized int allocReqid() {
      ++this.reqid;
      return this.reqid;
   }

   public TdomTcb alloc_TDOM(int opcode, int flags, String svc) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/alloc_TDOM/" + opcode + "/" + svc);
      }

      TdomTcb tmmsg_tdom = new TdomTcb(opcode, this.allocReqid(), flags, svc);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/alloc_TDOM/10/" + tmmsg_tdom);
      }

      return tmmsg_tdom;
   }

   private CallDescriptor _tpacall_internal(ReplyQueue rplyObj, String svc, tfmh tmmsg, int flags, Xid tid, int trantime, MethodParameters methodParms, boolean conversational, TuxedoCorbaConnection aCorbaConn, GatewayTpacallAsyncReply callBack, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      boolean dumpEnabled = ntrace.isTraceEnabled(64);
      AffinityContextHelper tranaffinityctxhelper = null;
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/_tpacall_internal/" + svc + "/" + tmmsg + "/" + flags + "/" + tid + "/" + trantime);
      }

      boolean isRMICall = false;
      int reqId = 0;
      Object[] reqInfo = new Object[4];
      Txid xid = null;
      String username = null;
      UserRec user = null;
      if (traceEnabled) {
         ntrace.doTrace("myAppKey = " + this.myAppKey);
      }

      if (this.myAppKey != null && (user = this.getCurrentUser(tuxUser)) == null) {
         throw new TPException(8, "User does not have permission to access Tuxedo");
      } else {
         TdomTcb tmmsg_tdom;
         int appkey;
         if (conversational) {
            flags &= -17;
            if (tmmsg.tdom == null) {
               tmmsg_tdom = this.alloc_TDOM(4, flags, svc);
            } else {
               tmmsg_tdom = (TdomTcb)tmmsg.tdom.body;
               tmmsg_tdom.set_opcode(4);
               tmmsg_tdom.set_reqid(this.allocReqid());
               tmmsg_tdom.set_flag(flags);
               tmmsg_tdom.set_service(svc);
            }

            tmmsg_tdom.setConvId(this.convid);
            tmmsg_tdom.set_seqnum(1);
            appkey = tmmsg_tdom.get_info();
            tmmsg_tdom.set_info(appkey | 1);
         } else {
            if ((flags & 16384) != 0) {
               flags &= -16385;
               isRMICall = true;
            }

            if (tmmsg.tdom == null) {
               tmmsg_tdom = this.alloc_TDOM(1, flags, svc);
            } else {
               tmmsg_tdom = (TdomTcb)tmmsg.tdom.body;
               tmmsg_tdom.set_opcode(1);
               tmmsg_tdom.set_reqid(this.allocReqid());
               tmmsg_tdom.set_flag(flags);
               tmmsg_tdom.set_service(svc);
               tmmsg_tdom.set_msgprio(this.tmsndprio);
            }
         }

         if (tmmsg.tdom == null) {
            tcm tdom = new tcm((short)7, tmmsg_tdom);
            tmmsg.tdom = tdom;
         }

         int useBlocktime;
         if (tid != null) {
            useBlocktime = trantime;
         } else if ((flags & 32) != 0) {
            useBlocktime = -1;
         } else {
            useBlocktime = 0;
         }

         SessionAcallDescriptor cd;
         if ((flags & 4) == 0) {
            if (conversational) {
               cd = new SessionAcallDescriptor(tmmsg_tdom.getConvId(), conversational);
            } else {
               cd = new SessionAcallDescriptor(tmmsg_tdom.get_reqid(), conversational);
            }

            if (callBack != null) {
               cd.setHasCallback(true);
            }

            if (rplyObj != null) {
               this.rcv_place.add_rplyObj(cd, rplyObj, useBlocktime, callBack);
            }
         } else {
            cd = new SessionAcallDescriptor(-1, false);
         }

         if (cd.getCd() != -1 && tuxUser != null) {
            ConcurrentHashMap map = tuxUser.getCurrImpSvc2();
            TDMImport imp = null;
            String[] info = (String[])((String[])map.get(svc));
            if (map != null && info != null && info.length == 3) {
               String rsname = null;
               String[] imp_keys = new String[2];
               rsname = info[0];
               imp_keys[0] = info[1];
               imp_keys[1] = info[2];
               imp = WTCService.getWTCService().getImport(rsname, imp_keys);
               this.cdToImpSvc.put(cd, imp);
            }
         }

         if (tid != null) {
            if (this.tos == null) {
               this.tos = ConfigHelper.getTuxedoServices();
               if (this.tos == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("/dsession(" + this.uid + ")/_tpacall_internal/cannot get OatmialServices");
                  }

                  throw new TPException(12, "Cannot get services");
               }
            }

            Xid rxid = this.tos.getOutboundXidAssociatedWithFXid(tid);
            if (rxid == null) {
               rxid = tid;
            }

            TdomTranTcb tmmsg_tdom_tran = new TdomTranTcb(rxid, trantime, this.local_domain_name);
            xid = new Txid(tmmsg_tdom_tran.getGlobalTransactionId());
            tcm tdom_tran = new tcm((short)10, tmmsg_tdom_tran);
            tmmsg.tdomtran = tdom_tran;
            this.setOutboundImportedXid(tmmsg, tid, 0);
            tranaffinityctxhelper = AffinityContextHelperFactory.createXAAffinityContextHelper();
            GwtUtil.addOutboundAffinityCtxToMetaTCM(tmmsg, tranaffinityctxhelper, true);
         } else {
            tmmsg.tdomtran = null;
         }

         if (user != null) {
            appkey = user.getAppKey();
            username = user.getRemoteUserName();
            if (traceEnabled) {
               ntrace.doTrace("/dsession/_tpacall_internal/" + username + "," + appkey);
            }

            AaaTcb tmmsg_tdom_sec = new AaaTcb();
            tmmsg_tdom_sec.setATZUserName(username);
            tmmsg_tdom_sec.setATZAppKey(appkey);
            tmmsg_tdom_sec.setAUDUserName(username);
            tmmsg_tdom_sec.setAUDAppKey(appkey);
            tcm tdom_sec = new tcm((short)15, tmmsg_tdom_sec);
            tmmsg.AAA = tdom_sec;
         } else {
            tmmsg.AAA = null;
         }

         if (this.dom_protocol >= 20) {
            DmsReflect dms = null;

            try {
               dms = DmsReflect.getInstance();
            } catch (Exception var43) {
               if (traceEnabled) {
                  ntrace.doTrace("]/dsession/_tpacall_internal/cannot get DMS instance for ECID");
               }
            }

            if (dms != null) {
               String ecid = dms.getECID();
               if (ecid != null && ecid.length() != 0) {
                  if (traceEnabled) {
                     ntrace.doTrace("/dsession/_tpacall_internal/ecid," + ecid);
                  }

                  MetaTcb tmmsg_tdom_meta = new MetaTcb();
                  tmmsg_tdom_meta.setECID(ecid);
                  tcm tdom_meta = new tcm((short)19, tmmsg_tdom_meta);
                  tmmsg.meta = tdom_meta;
               }
            } else {
               tmmsg.meta = null;
            }
         } else {
            tmmsg.meta = null;
         }

         if (callBack != null) {
            callBack.setTargetSubject(TCSecurityManager.getCurrentUser());
         }

         try {
            if (isRMICall) {
               if (this.rmiCallList == null) {
                  synchronized(this.lockObject) {
                     if (this.rmiCallList == null) {
                        this.rmiCallList = new HashMap(100);
                        if (traceEnabled) {
                           ntrace.doTrace("/dsession/_tpacall_internal/4/" + this + "/" + this.rmiCallList);
                        }
                     }
                  }
               }

               reqId = tmmsg_tdom.get_reqid();
               if (traceEnabled) {
                  ntrace.doTrace("/dsession/_tpacall_internal/5:RMI/IIOP call: reqId =" + reqId);
               }

               reqInfo[0] = aCorbaConn;
               reqInfo[1] = username;
               Debug.assertion(methodParms != null, "RMI/IIOP call made with null MethodParameters");
               reqInfo[2] = new Integer(methodParms.getGIOPRequestID());
               reqInfo[3] = xid;
               synchronized(this.rmiCallList) {
                  if (traceEnabled) {
                     ntrace.doTrace("/dsession/_tpacall_internal/7: RMI/IIOP call: reqId =" + reqId + ", xid =" + xid);
                  }

                  this.rmiCallList.put(new Integer(reqId), reqInfo);
               }
            }

            if (useCORBATimeout && (flags & 36) == 0 && tid == null && this.myBlockTime > 0L) {
               this.rcv_place.addTimeoutRequest(methodParms.getGIOPRequestID(), reqId, this.myBlockTime);
            }

            synchronized(this.dom_ostream) {
               if (dumpEnabled) {
                  tmmsg.dumpUData(true);
               }

               if (this.dom_protocol >= 15) {
                  tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
               } else {
                  tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
               }

               if (dumpEnabled) {
                  tmmsg.dumpUData(false);
               }
            }
         } catch (IOException var42) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/_tpacall_internal/30/");
            }

            if (isRMICall) {
               synchronized(this.rmiCallList) {
                  Object ri = this.rmiCallList.remove(new Integer(reqId));
                  if (ri == null && traceEnabled) {
                     ntrace.doTrace("*/dsession(" + this.uid + ")/_tpacall_internal/35/");
                  }
               }
            }

            this.setIsTerminated();
            throw new TPException(12, "tpacall network send error: " + var42);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/_tpacall_internal/40/" + cd);
         }

         return cd;
      }
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags, Xid tid, int trantime, GatewayTpacallAsyncReply callBack, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpacall/" + svc + "/" + data + "/" + flags + "/" + tid + "/" + trantime);
      }

      tfmh tmmsg = null;
      CallDescriptor cd = null;
      StandardTypes sData = null;
      ConcurrentHashMap map = tuxUser.getCurrImpSvc2();
      TDMImport imp = null;
      String[] info = (String[])((String[])map.get(svc));
      if (map != null && info != null && info.length == 3) {
         String rsname = null;
         String[] imp_keys = new String[2];
         rsname = info[0];
         imp_keys[0] = info[1];
         imp_keys[1] = info[2];
         imp = WTCService.getWTCService().getImport(rsname, imp_keys);
      }

      this.collect_stat_begin(imp);
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpacall/10/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(9, "Must init before tpacall");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpacall/20/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(9, "Domain session has been terminated");
      } else if ((flags & -46) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpacall/30/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(4);
      } else if (svc != null && !svc.equals("")) {
         if (data == null) {
            tmmsg = new tfmh(1);
         } else {
            if (data instanceof StandardTypes) {
               sData = (StandardTypes)data;
               tmmsg = (tfmh)sData.getTfmhCache();
            }

            if (tmmsg == null) {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 1);
            }

            this.setUse64BitsLong(tmmsg);
         }

         try {
            cd = this._tpacall_internal(this.myRplyObj, svc, tmmsg, flags, tid, trantime, (MethodParameters)null, false, (TuxedoCorbaConnection)null, callBack, tuxUser);
         } catch (TPException var18) {
            this.collect_stat_end(imp, 0L, false, true);
            throw var18;
         }

         this.collect_stat_end(imp, (long)tmmsg.getUserDataSize(), true, false);
         if (sData != null) {
            sData.setTfmhCache(tmmsg);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/tpacall/100/" + cd);
         }

         return cd;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpacall/40/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(4);
      }
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags) throws TPException {
      return this.tpacall(svc, data, flags, (Xid)null, 0, (GatewayTpacallAsyncReply)null, (TuxedoConnection)null);
   }

   public CallDescriptor tpacall(String svc, TypedBuffer data, int flags, TpacallAsyncReply callBack) throws TPException {
      if (!(callBack instanceof GatewayTpacallAsyncReply)) {
         throw new TPException(4, "ERROR: internal tpacall called without gateway tpacall async reply");
      } else {
         return this.tpacall(svc, data, flags, (Xid)null, 0, (GatewayTpacallAsyncReply)callBack, (TuxedoConnection)null);
      }
   }

   public void tpcancel(CallDescriptor cd, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpcancel/" + cd + "/" + flags);
      }

      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcancel/10/");
         }

         throw new TPException(9, "Must init before tpcancel");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcancel/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if (cd != null && flags == 0 && cd instanceof SessionAcallDescriptor) {
            SessionAcallDescriptor inCd = (SessionAcallDescriptor)cd;
            if (!this.rcv_place.remove_rplyObj(inCd)) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcancel/40/");
               }

               throw new TPException(2, "This descriptor (" + inCd + ") has already received a reply");
            } else {
               if (this.myWTCStat != null) {
                  TDMImport imp = (TDMImport)this.cdToImpSvc.get(inCd);
                  if (imp != null) {
                     this.myWTCStat.updOutstandingNWReqCount(this, -1L);
                     this.myWTCStat.updOutstandingNWReqCount(imp, -1L);
                     this.cdToImpSvc.remove(inCd);
                  }
               }

            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcancel/30/");
            }

            throw new TPException(4, "Invalid parameter passed to tpcancel");
         }
      }
   }

   private tfmh _tpgetrply_internal(SessionAcallDescriptor cd, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/_tpgetrply_internal/" + cd + "/" + flags);
      }

      tfmh tmmsg_in = null;
      int realcd = cd.getCd();
      AffinityContextHelper tranaffinityctxhelper = null;
      boolean block = (flags & 1) == 0;
      if (realcd != -1) {
         ReqOid myOid = new ReqOid(cd, this);
         tmmsg_in = this.myRplyObj.get_specific_reply(myOid, block);
         tranaffinityctxhelper = AffinityContextHelperFactory.createXAAffinityContextHelper();
         if (tranaffinityctxhelper != null && tranaffinityctxhelper.isApplicationContextAvailable()) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/_tpgetrply_internal/getInboundAffinityCtxFromMetaTCM:" + tranaffinityctxhelper.isApplicationContextAvailable());
            }

            GwtUtil.getInboundAffinityCtxFromMetaTCM(tmmsg_in, tranaffinityctxhelper, true);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/_tpgetrply_internal/20/" + tmmsg_in);
         }

         return tmmsg_in;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/_tpgetrply_internal/10/");
         }

         throw new TPException(12, "TPGETANY not implemented yet");
      }
   }

   public Reply tpgetrply(CallDescriptor cd, int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpgetrply/" + cd + "/" + flags);
      }

      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/10/");
         }

         throw new TPException(9, "Must init before tpgetrply");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if (cd != null && cd instanceof SessionAcallDescriptor) {
            SessionAcallDescriptor inCd;
            if ((inCd = (SessionAcallDescriptor)cd) != null && inCd.hasCallBack()) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/27/");
               }

               throw new TPException(4, "CallDescriptor given to tpgetrply has an asynchronous call-back function associated with it");
            } else {
               int realcd = inCd.getCd();
               if (realcd >= -1 && (flags & -162) == 0) {
                  if (realcd == -1 && (flags & 128) == 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/40/");
                     }

                     throw new TPException(4);
                  } else {
                     boolean block = (flags & 1) == 0;
                     TDMImport imp = (TDMImport)this.cdToImpSvc.get(inCd);
                     tfmh tmmsg_in;
                     if ((tmmsg_in = this._tpgetrply_internal(inCd, flags)) == null) {
                        if (block) {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/70/");
                           }

                           this.collect_stat_end(imp, 0L, false, true);
                           this.cdToImpSvc.remove(inCd);
                           throw new TPException(12, "Connection dropped");
                        } else {
                           if (traceEnabled) {
                              ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/80/");
                           }

                           this.collect_stat_end(imp, 0L, false, true);
                           this.cdToImpSvc.remove(inCd);
                           throw new TPException(3);
                        }
                     } else {
                        TdomTcb tdom = (TdomTcb)tmmsg_in.tdom.body;
                        int myTPException = tdom.get_diagnostic();
                        int mytpurcode = tdom.getTpurcode();
                        int mytperrordetail = tdom.get_errdetail();
                        int opcode = tdom.get_opcode();
                        if (traceEnabled) {
                           ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/85/opcode=" + opcode + "/diagnostic=" + myTPException);
                        }

                        if (opcode == 3 && myTPException != 11 && myTPException != 10) {
                           if (myTPException == 7) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/90/");
                              }

                              this.collect_stat_end(imp, 0L, false, true);
                              this.cdToImpSvc.remove(inCd);
                              throw new TPException(myTPException, mytpurcode, 0, mytperrordetail);
                           } else {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/95/");
                              }

                              this.collect_stat_end(imp, 0L, false, true);
                              this.cdToImpSvc.remove(inCd);
                              throw new TPException(myTPException, 0, mytpurcode, mytperrordetail);
                           }
                        } else {
                           if (myTPException != 11 && myTPException != 10) {
                              myTPException = 0;
                           }

                           TypedBuffer tb;
                           if (tmmsg_in.user == null) {
                              tb = null;
                           } else {
                              UserTcb utcb = (UserTcb)tmmsg_in.user.body;
                              tb = utcb.user_data;
                           }

                           TuxedoReply ret = new TuxedoReply(tb, mytpurcode, cd);
                           if (myTPException != 0) {
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/90/" + ret);
                              }

                              this.collect_stat_end(imp, 0L, false, true);
                              this.cdToImpSvc.remove(inCd);
                              throw new TPReplyException(myTPException, 0, mytpurcode, mytperrordetail, ret);
                           } else {
                              if (this.rcv_place != null) {
                                 this.rcv_place.restoreTfmhToCache(tmmsg_in);
                              }

                              this.collect_stat_end(imp, 0L, true, true);
                              this.cdToImpSvc.remove(inCd);
                              if (traceEnabled) {
                                 ntrace.doTrace("]/dsession(" + this.uid + ")/tpgetrply/100/" + ret);
                              }

                              return ret;
                           }
                        }
                     }
                  }
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/30/");
                  }

                  throw new TPException(4);
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpgetrply/25/");
            }

            throw new TPException(4, "Invalid object (cd) passed to tpgetrply");
         }
      }
   }

   private tfmh _tpcall_internal(String svc, tfmh tmmsg, int flags, Xid tid, int trantime, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/_tpcall_internal/" + svc + "/" + tmmsg + "/" + flags);
      }

      SessionAcallDescriptor cd = (SessionAcallDescriptor)this._tpacall_internal(this.myRplyObj, svc, tmmsg, flags, tid, trantime, (MethodParameters)null, false, (TuxedoCorbaConnection)null, (GatewayTpacallAsyncReply)null, tuxUser);
      tfmh tmmsg_out = this._tpgetrply_internal(cd, flags);
      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/_tpcall_internal/10/" + tmmsg_out);
      }

      return tmmsg_out;
   }

   public Reply tpcall(String svc, TypedBuffer data, int flags, Xid tid, int trantime, TuxedoConnection user) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpcall/" + svc + "/" + data + "/" + flags + "/" + tid);
      }

      CallDescriptor cd;
      try {
         cd = this.tpacall(svc, data, flags, tid, trantime, (GatewayTpacallAsyncReply)null, user);
      } catch (TPException var13) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcall/10/" + var13);
         }

         throw var13;
      }

      Reply ret;
      try {
         ret = this.tpgetrply(cd, flags & -10);
      } catch (TPReplyException var11) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcall/20/" + var11);
         }

         throw var11;
      } catch (TPException var12) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcall/30/" + var12);
         }

         throw var12;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/dsession(" + this.uid + ")/tpcall/20/" + ret);
      }

      return ret;
   }

   public Reply tpcall(String svc, TypedBuffer data, int flags) throws TPException, TPReplyException {
      return this.tpcall(svc, data, flags, (Xid)null, 0, (TuxedoConnection)null);
   }

   public synchronized void tpbegin(long timeout, int flags) throws TPException {
      throw new TPException(9, "tpbegin not implemented");
   }

   public synchronized void tpabort(int flags) throws TPException {
      throw new TPException(9, "tpabort not implemented");
   }

   public synchronized void tpcommit(int flags) throws TPException {
      throw new TPException(9, "tpcommit not implemented");
   }

   public byte[] tpenqueue(String qspace, String qname, EnqueueRequest ctl, TypedBuffer data, int flags, Xid tid, int trantime, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpenqueue/" + qspace + "/" + qname + "/" + ctl + "/" + data + "/" + flags + "/" + tid + "/" + trantime);
      }

      ComposFmlTcb tmmsg_compos_fml_in = null;
      Integer diagnostic = null;
      int retDiagnostic = 0;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/10/");
         }

         throw new TPException(9, "Must init before tpenqueue");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -42) == 0 && qspace != null && qname != null && qspace.length() != 0 && qname.length() != 0 && ctl != null) {
            int opcode = 1;
            if (ctl.getexp_time() != null) {
               opcode |= 65536;
            } else if (ctl.getdelivery_qos() == 4) {
               opcode |= 65536;
            } else if (ctl.getreply_qos() == 4) {
               opcode |= 65536;
            }

            ComposHdrTcb tmmsg_compos_hdr = new ComposHdrTcb(opcode, ctl.geturcode());
            tcm compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
            ComposFmlTcb tmmsg_compos_fml = new ComposFmlTcb(qname, ctl);
            tcm compos_fml = new tcm((short)6, tmmsg_compos_fml);
            tfmh tmmsg;
            if (data == null) {
               tmmsg = new tfmh(1);
            } else {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 1);
               this.setUse64BitsLong(tmmsg);
            }

            tmmsg.set_TPENQUEUE(true);
            tmmsg.compos_hdr = compos_hdr;
            tmmsg.compos_fml = compos_fml;
            tfmh tmmsg_in;
            if ((tmmsg_in = this._tpcall_internal(qspace, tmmsg, flags & -2, tid, trantime, tuxUser)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/40/");
               }

               throw new TPException(12, "tpenqueue got invalid return from _tpcall_internal");
            } else {
               TdomTcb tdom = (TdomTcb)tmmsg_in.tdom.body;
               opcode = tdom.get_opcode();
               int tperrno = tdom.get_diagnostic();
               if (opcode == 3 || tmmsg_in.compos_hdr != null && tmmsg_in.compos_fml != null) {
                  if (tmmsg_in.compos_fml != null) {
                     tmmsg_compos_fml_in = (ComposFmlTcb)tmmsg_in.compos_fml.body;
                     diagnostic = tmmsg_compos_fml_in.getDiagnostic();
                  }

                  if (opcode == 3) {
                     if (tperrno == 24) {
                        if (diagnostic == null) {
                           retDiagnostic = 7;
                        } else {
                           retDiagnostic = diagnostic;
                        }
                     }

                     int tpurcode = tdom.getTpurcode();
                     int tperrordetail = tdom.get_errdetail();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/90/" + opcode + "/" + tperrno);
                     }

                     throw new TPException(tperrno, 0, tpurcode, tperrordetail, retDiagnostic);
                  } else if (diagnostic != null && (retDiagnostic = diagnostic) < 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/100/" + retDiagnostic);
                     }

                     throw new TPException(24, 0, 0, 0, retDiagnostic);
                  } else {
                     byte[] ret = tmmsg_compos_fml_in.getMsgid();
                     if (this.rcv_place != null) {
                        this.rcv_place.restoreTfmhToCache(tmmsg_in);
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/dsession(" + this.uid + ")/tpenqueue/110/" + ret.length);
                     }

                     return ret;
                  }
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/50/");
                  }

                  throw new TPException(12, "tpenqueue could not get queue information");
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/30/");
            }

            throw new TPException(4);
         }
      }
   }

   public byte[] tpenqueue(String qspace, String qname, EnqueueRequest ctl, TypedBuffer data, int flags) throws TPException {
      return this.tpenqueue(qspace, qname, ctl, data, flags, (Xid)null, 0, (TuxedoConnection)null);
   }

   public DequeueReply tpdequeue(String qspace, String qname, byte[] msgid, byte[] corrid, boolean doWait, boolean doPeek, int flags, Xid tid, int trantime, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpdequeue/" + qspace + "/" + qname + "/" + Arrays.toString(msgid) + "/" + Arrays.toString(corrid) + "/" + doWait + "/" + doPeek + "/" + flags + "/" + tid + "/" + trantime);
      }

      ComposFmlTcb tmmsg_compos_fml_in = null;
      ComposHdrTcb tmmsg_compos_hdr_in = null;
      TypedBuffer tb = null;
      Integer diagnostic = null;
      int retDiagnostic = 0;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/10/");
         }

         throw new TPException(9, "Must init before tpenqueue");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpenqueue/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if ((flags & -42) == 0 && qspace != null && qname != null && qspace.length() != 0 && qname.length() != 0) {
            if (doPeek) {
               flags |= 8;
            }

            ComposHdrTcb tmmsg_compos_hdr = new ComposHdrTcb(2, 0);
            tcm compos_hdr = new tcm((short)5, tmmsg_compos_hdr);
            ComposFmlTcb tmmsg_compos_fml = new ComposFmlTcb(qname, msgid, corrid, doWait, doPeek);
            tcm compos_fml = new tcm((short)6, tmmsg_compos_fml);
            tfmh tmmsg = new tfmh(1);
            tmmsg.compos_hdr = compos_hdr;
            tmmsg.compos_fml = compos_fml;
            tfmh tmmsg_in;
            if ((tmmsg_in = this._tpcall_internal(qspace, tmmsg, flags & -2, tid, trantime, tuxUser)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/40/");
               }

               throw new TPException(12, "tpdequeue got invalid return from _tpcall_internal");
            } else {
               TdomTcb tdom = (TdomTcb)tmmsg_in.tdom.body;
               int opcode = tdom.get_opcode();
               int tpurcode = tdom.getTpurcode();
               int tperrno = tdom.get_diagnostic();
               if (opcode == 3 || tmmsg_in.compos_hdr != null && tmmsg_in.compos_fml != null) {
                  if (tmmsg_in.compos_fml != null) {
                     tmmsg_compos_fml_in = (ComposFmlTcb)tmmsg_in.compos_fml.body;
                     diagnostic = tmmsg_compos_fml_in.getDiagnostic();
                  }

                  if (tmmsg_in.compos_hdr != null) {
                     tmmsg_compos_hdr_in = (ComposHdrTcb)tmmsg_in.compos_hdr.body;
                  }

                  if (opcode == 3) {
                     if (tperrno == 24) {
                        if (diagnostic == null) {
                           retDiagnostic = 7;
                        } else {
                           retDiagnostic = diagnostic;
                        }
                     }

                     int tperrordetail = tdom.get_errdetail();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/90/");
                     }

                     throw new TPException(tperrno, 0, tpurcode, tperrordetail, retDiagnostic);
                  } else if (diagnostic != null && (retDiagnostic = diagnostic) < 0) {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/100/" + retDiagnostic);
                     }

                     throw new TPException(24, 0, 0, 0, retDiagnostic);
                  } else {
                     if (tmmsg_in.user != null) {
                        tb = ((UserTcb)tmmsg_in.user.body).user_data;
                     }

                     DequeueReply ret = new DequeueReply(tb, tpurcode, (CallDescriptor)null, tmmsg_compos_fml_in.getMsgid(), tmmsg_compos_fml_in.getCoorid(), tmmsg_compos_fml_in.getReplyQueue(), tmmsg_compos_fml_in.getFailureQueue(), new Integer(tmmsg_compos_hdr_in.getAppkey()), tmmsg_compos_fml_in.getPriority(), tmmsg_compos_fml_in.getDeliveryQualityOfService(), tmmsg_compos_fml_in.getReplyQualityOfService(), tmmsg_compos_hdr_in.getUrcode());
                     if (this.rcv_place != null) {
                        this.rcv_place.restoreTfmhToCache(tmmsg_in);
                     }

                     if (traceEnabled) {
                        ntrace.doTrace("]/dsession(" + this.uid + ")/tpdequeue/80/" + ret);
                     }

                     return ret;
                  }
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/50/");
                  }

                  throw new TPException(12, "tpdequeue could not get queue information");
               }
            }
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpdequeue/30/");
            }

            throw new TPException(4);
         }
      }
   }

   public DequeueReply tpdequeue(String qspace, String qname, byte[] msgid, byte[] corrid, boolean doWait, boolean doPeek, int flags) throws TPException {
      return this.tpdequeue(qspace, qname, msgid, corrid, doWait, doPeek, flags, (Xid)null, 0, (TuxedoConnection)null);
   }

   public DequeueReply tpdequeue(String qspace, String qname, int flags) throws TPException {
      return this.tpdequeue(qspace, qname, (byte[])null, (byte[])null, false, false, flags, (Xid)null, 0, (TuxedoConnection)null);
   }

   public void send_success_return(Serializable rd, tfmh tmmsg, int the_TPException, int the_tpurcode, int conversationIdentifier) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      boolean dumpEnabled = ntrace.isTraceEnabled(64);
      AffinityContextHelper tranaffinityctxhelper = null;
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/send_success_return/" + this.reqid + "/" + tmmsg + "/" + the_tpurcode + "/" + conversationIdentifier);
      }

      dreqid myReqid = (dreqid)rd;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/send_success_return/10/");
         }

         throw new TPException(9, "How could this have happened?");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/send_success_return/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else if (the_TPException != 0 && the_TPException != 10 && the_TPException != 11) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/send_success_return/25/");
            }

            throw new TPException(4, "Invalid TPException value:" + the_TPException);
         } else {
            tmmsg.setTMREPLY(true);
            TdomTcb tmmsg_tdom;
            if (the_TPException == 0) {
               tmmsg_tdom = new TdomTcb(2, myReqid.reqid, 4194304, (String)null);
            } else {
               tmmsg_tdom = new TdomTcb(3, myReqid.reqid, 4194304, (String)null);
            }

            tmmsg_tdom.set_tpurcode(the_tpurcode);
            tmmsg_tdom.set_convid(conversationIdentifier);
            if (conversationIdentifier != -1) {
               tmmsg_tdom.set_info(2);
               switch (the_TPException) {
                  case 0:
                     tmmsg_tdom.set_tpevent(8);
                     tmmsg_tdom.set_diagnostic(22);
                     break;
                  case 10:
                     tmmsg_tdom.set_tpevent(2);
                     tmmsg_tdom.set_diagnostic(22);
                     break;
                  case 11:
                     tmmsg_tdom.set_tpevent(4);
                     tmmsg_tdom.set_diagnostic(22);
                     break;
                  default:
                     tmmsg_tdom.set_diagnostic(the_TPException);
               }
            } else {
               tmmsg_tdom.set_diagnostic(the_TPException);
            }

            tmmsg.tdom = new tcm((short)7, tmmsg_tdom);
            this.setUse64BitsLong(tmmsg);

            try {
               synchronized(this.dom_ostream) {
                  if (dumpEnabled) {
                     tmmsg.dumpUData(true);
                  }

                  if (this.dom_protocol >= 15) {
                     tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
                  } else {
                     tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
                  }

                  if (dumpEnabled) {
                     tmmsg.dumpUData(false);
                  }
               }
            } catch (IOException var14) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/send_success_return/30/");
               }

               throw new TPException(12, "Unable to send success reply network error: " + var14);
            }

            tranaffinityctxhelper = AffinityContextHelperFactory.createXAAffinityContextHelper();
            if (tranaffinityctxhelper != null && tranaffinityctxhelper.isApplicationContextAvailable()) {
               if (traceEnabled) {
                  ntrace.doTrace("]/dsession/send_success_return/addOutboundAffinityCtxToMetaTCM:" + tranaffinityctxhelper.isApplicationContextAvailable());
               }

               GwtUtil.addOutboundAffinityCtxToMetaTCM(tmmsg, tranaffinityctxhelper, true);
            }

            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/send_success_return/40/");
            }

         }
      }
   }

   public void send_transaction_reply(tfmh tmmsg) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/send_transaction_reply/" + tmmsg);
      }

      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/send_transaction_reply/10/");
         }

         throw new TPException(9, "How could this have happened?");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/send_transaction_reply/20/");
            }

            throw new TPException(9, "Tuxedo session has been terminated");
         } else {
            this.setUse64BitsLong(tmmsg);

            try {
               synchronized(this.dom_ostream) {
                  if (this.dom_protocol >= 15) {
                     tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
                  } else {
                     tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
                  }
               }
            } catch (IOException var6) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/send_transaction_reply/30/");
               }

               throw new TPException(12, "Unable to send transaction reply network error: " + var6);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/send_transaction_reply/40/");
            }

         }
      }
   }

   public void send_failure_return(Serializable rd, TPException te, int conversationIdentifier) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (ntrace.getTraceLevel() >= 50) {
         ntrace.doTrace("Some error happened! " + te);
      }

      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/send_failure_return/" + rd + "/" + te);
      }

      dreqid myReqid = (dreqid)rd;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/send_failure_return/10/");
         }

      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/send_failure_return/20/");
            }

         } else {
            tfmh fail_tmmsg = new tfmh(1);
            TdomTcb fail_tmmsg_tdom = new TdomTcb(3, myReqid.reqid, 4194304, (String)null);
            fail_tmmsg_tdom.set_errdetail(te.gettperrordetail());
            fail_tmmsg_tdom.set_tpurcode(te.gettpurcode());
            fail_tmmsg_tdom.set_diagnostic(te.gettperrno());
            fail_tmmsg_tdom.set_convid(conversationIdentifier);
            fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);

            try {
               synchronized(this.dom_ostream) {
                  if (this.dom_protocol >= 15) {
                     fail_tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
                  } else {
                     fail_tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
                  }
               }
            } catch (IOException var11) {
               if (traceEnabled) {
                  ntrace.doTrace("]/dsession(" + this.uid + ")/send_failure_return/20/");
               }

               return;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/send_failure_return/30/");
            }

         }
      }
   }

   public CallDescriptor tprplycall(TuxRply rplyObj, String svc, TypedBuffer data, int flags, Xid tid, int trantime, GatewayTpacallAsyncReply callBack, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tprplycall/" + svc + "/" + flags);
      }

      CallDescriptor cd = null;
      ConcurrentHashMap map = tuxUser.getCurrImpSvc2();
      TDMImport imp = null;
      String[] info = (String[])((String[])map.get(svc));
      if (map != null && info != null && info.length == 3) {
         String rsname = null;
         String[] imp_keys = new String[2];
         rsname = info[0];
         imp_keys[0] = info[1];
         imp_keys[1] = info[2];
         imp = WTCService.getWTCService().getImport(rsname, imp_keys);
      }

      this.collect_stat_begin(imp);
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tprplycall/10/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(9, "Must init before tprplycall");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tprplycall/20/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(9, "Domain session has been terminated");
      } else if ((flags & -46) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tprplycall/30/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(4);
      } else if (svc != null && !svc.equals("")) {
         if ((flags & 4) != 0 && rplyObj != null) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tprplycall/50/");
            }

            this.collect_stat_end(imp, 0L, false, true);
            throw new TPException(4, "Cannot have a reply object if TPNOREPLY is set");
         } else {
            tfmh tmmsg;
            if (data == null) {
               tmmsg = new tfmh(1);
            } else {
               tcm user = new tcm((short)0, new UserTcb(data));
               tmmsg = new tfmh(data.getHintIndex(), user, 1);
               this.setUse64BitsLong(tmmsg);
            }

            try {
               cd = this._tpacall_internal(rplyObj, svc, tmmsg, flags, tid, trantime, (MethodParameters)null, false, (TuxedoCorbaConnection)null, callBack, tuxUser);
            } catch (TPException var18) {
               this.collect_stat_end(imp, 0L, false, true);
               throw var18;
            }

            if ((flags & 4) == 0) {
               this.collect_stat_end(imp, (long)tmmsg.getUserDataSize(), true, false);
            } else {
               this.collect_stat_end(imp, (long)tmmsg.getUserDataSize(), true, true);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/tprplycall/60/" + cd);
            }

            return cd;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tprplycall/40/");
         }

         this.collect_stat_end(imp, 0L, false, true);
         throw new TPException(4);
      }
   }

   public Txid tpprepare(TuxXidRply rplyObj, Xid tid, int trantime) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpprepare/" + rplyObj + "/" + tid + "/" + trantime);
      }

      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpprepare/10/");
         }

         throw new TPException(9, "Must init before tprplycall");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpprepare/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if (rplyObj != null && tid != null) {
         tfmh tmmsg = new tfmh(1);
         TdomTcb tmmsg_tdom = this.alloc_TDOM(7, 0, (String)null);
         tcm tdom = new tcm((short)7, tmmsg_tdom);
         tmmsg.tdom = tdom;
         tmmsg_tdom.set_info(32);
         if (this.tos == null) {
            this.tos = ConfigHelper.getTuxedoServices();
            if (this.tos == null) {
               if (traceEnabled) {
                  ntrace.doTrace("/dsession(" + this.uid + ")/tpprepare/cannot get OatmialServices");
               }

               throw new TPException(12, "Cannot get services");
            }
         }

         Xid rxid = this.tos.getOutboundXidAssociatedWithFXid(tid);
         if (rxid == null) {
            rxid = tid;
         } else {
            Transaction tran = TCTransactionHelper.getTransaction(rxid);

            try {
               if (tran != null && tran.getStatus() == 7) {
                  if (traceEnabled) {
                     ntrace.doTrace(" /dsession/tpprepare/use FXid");
                  }
               } else {
                  rxid = tid;
                  if (traceEnabled) {
                     ntrace.doTrace(" /dsession/tpprepare/ignore FXid");
                  }
               }
            } catch (SystemException var17) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpprepare/60/");
               }

               throw new TPException(9);
            }
         }

         TdomTranTcb tmmsg_tdomtran = new TdomTranTcb(rxid, 0, this.local_domain_name);
         tcm tdomtran = new tcm((short)10, tmmsg_tdomtran);
         tmmsg.tdomtran = tdomtran;
         this.setOutboundImportedXid(tmmsg, tid, 0);
         Txid cd = new Txid(rxid.getGlobalTransactionId());
         this.rcv_place.add_rplyXidObj(cd, rplyObj, trantime);

         try {
            synchronized(this.dom_ostream) {
               if (this.dom_protocol >= 15) {
                  tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
               } else {
                  tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
               }
            }
         } catch (IOException var16) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpprepare/40/");
            }

            throw new TPException(12, "Could not send prepare message" + var16);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/tpprepare/50/" + cd);
         }

         return cd;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpprepare/30/");
         }

         throw new TPException(4);
      }
   }

   public Txid tpcommit(TuxXidRply rplyObj, Xid tid, int trantime, boolean doCommit) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpcommit/" + rplyObj + "/" + tid + "/" + trantime + "/" + doCommit);
      }

      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcommit/10/");
         }

         throw new TPException(9, "Must init before tprplycall");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcommit/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if (rplyObj != null && tid != null) {
         tfmh tmmsg = new tfmh(1);
         TdomTcb tmmsg_tdom;
         if (doCommit) {
            tmmsg_tdom = this.alloc_TDOM(9, 0, (String)null);
         } else {
            tmmsg_tdom = this.alloc_TDOM(12, 0, (String)null);
         }

         tcm tdom = new tcm((short)7, tmmsg_tdom);
         tmmsg.tdom = tdom;
         tmmsg_tdom.set_info(32);
         if (this.tos == null) {
            this.tos = ConfigHelper.getTuxedoServices();
            if (this.tos == null) {
               if (traceEnabled) {
                  ntrace.doTrace("/dsession(" + this.uid + ")/tpcommit/cannot get OatmialServices");
               }

               throw new TPException(12, "Cannot get services");
            }
         }

         Xid rxid = this.tos.getOutboundXidAssociatedWithFXid(tid);
         if (rxid == null) {
            rxid = tid;
         } else {
            try {
               Transaction tran = TCTransactionHelper.getTransaction(rxid);
               if (tran != null && tran.getStatus() == 8) {
                  if (traceEnabled) {
                     ntrace.doTrace(" /dsession/tpcommit/ignore FXid");
                  }
               } else {
                  rxid = tid;
                  if (traceEnabled) {
                     ntrace.doTrace(" /dsession/tpcommit/ignore FXid");
                  }
               }
            } catch (SystemException var17) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcommit/60/");
               }

               throw new TPException(9);
            }
         }

         TdomTranTcb tmmsg_tdomtran = new TdomTranTcb(rxid, 0, this.local_domain_name);
         tcm tdomtran = new tcm((short)10, tmmsg_tdomtran);
         tmmsg.tdomtran = tdomtran;
         this.setOutboundImportedXid(tmmsg, tid, 1);
         Txid cd = new Txid(rxid.getGlobalTransactionId());
         this.rcv_place.add_rplyXidObj(cd, rplyObj, trantime);

         try {
            synchronized(this.dom_ostream) {
               if (this.dom_protocol >= 15) {
                  tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
               } else {
                  tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
               }
            }

            if (this.myWTCStat != null) {
               if (traceEnabled) {
                  ntrace.doTrace("*/dsession(" + this.uid + ")/tpcommit/collecting metrics for WTC connection (" + this.local_domain_name + ":" + this.remote_domain_id + ")");
               }

               if (doCommit) {
                  this.myWTCStat.updOutTransactionCommittedTotalCount(this, 1L);
               } else {
                  this.myWTCStat.updOutTransactionRolledBackTotalCount(this, 1L);
               }
            }
         } catch (IOException var16) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/tpcommit/40/null");
            }

            return null;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/tpcommit/50/" + cd);
         }

         return cd;
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpcommit/30/");
         }

         throw new TPException(4);
      }
   }

   public int getUid() {
      return this.uid;
   }

   public int hashCode() {
      return this.uid & '\uffff';
   }

   public boolean equals(Object obj) {
      dsession myObj = (dsession)obj;
      if (myObj == null) {
         return false;
      } else {
         return myObj.getUid() == this.uid;
      }
   }

   public synchronized Conversation tpconnect(String svc, TypedBuffer data, int flags, Xid xid, int timeout, TuxedoConnection tuxUser) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/tpconnect/" + svc + "/" + data + "/" + flags + "/" + xid + "/" + timeout);
      }

      tfmh tmmsg = null;
      StandardTypes sData = null;
      DomainOutboundConversation ret = null;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/10/");
         }

         throw new TPException(9, "Must init before tpacall");
      } else if (this.is_term) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/20/");
         }

         throw new TPException(9, "Domain session has been terminated");
      } else if ((flags & -6186) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/30/");
         }

         throw new TPException(4);
      } else if ((flags & 6144) == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/40/");
         }

         throw new TPException(4, "Must specify a flag TPSENDONLY or TPRECVONLY");
      } else if ((flags & 2048) != 0 & (flags & 4096) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/50/");
         }

         throw new TPException(4, "Only one of TPSENDONLY or TPRECVONLY should be set");
      } else {
         boolean sendOnly = (flags & 2048) != 0;
         boolean inTransaction = xid != null;
         if (svc != null && !svc.equals("")) {
            if (data == null) {
               tmmsg = new tfmh(1);
            } else {
               if (data instanceof StandardTypes) {
                  sData = (StandardTypes)data;
                  tmmsg = (tfmh)sData.getTfmhCache();
               }

               if (tmmsg == null) {
                  tcm user = new tcm((short)0, new UserTcb(data));
                  tmmsg = new tfmh(data.getHintIndex(), user, 1);
               }

               this.setUse64BitsLong(tmmsg);
            }

            ++this.convid;
            ConversationReply replyObject = new ConversationReply();

            SessionAcallDescriptor cd;
            try {
               cd = (SessionAcallDescriptor)this._tpacall_internal(replyObject, svc, tmmsg, flags, xid, timeout, (MethodParameters)null, true, (TuxedoCorbaConnection)null, (GatewayTpacallAsyncReply)null, tuxUser);
            } catch (TPException var17) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/70/" + var17);
               }

               throw var17;
            }

            if (sData != null) {
               sData.setTfmhCache(tmmsg);
            }

            ret = new DomainOutboundConversation(this, replyObject, this.convid, sendOnly, cd, inTransaction);
            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/tpconnect/85/" + ret);
            }

            return ret;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/tpconnect/60/");
            }

            throw new TPException(4);
         }
      }
   }

   public Conversation tpconnect(String svc, TypedBuffer data, int flags) throws TPException {
      return this.tpconnect(svc, data, flags, (Xid)null, 0, (TuxedoConnection)null);
   }

   public void setTerminationHandler(OnTerm terminationHandler) {
      this.myTerminationHandler = terminationHandler;
   }

   public void restoreTfmhToCache(tfmh msg) {
      if (this.rcv_place != null) {
         this.rcv_place.restoreTfmhToCache(msg);
      }

   }

   public CallDescriptor tpMethodReq(TypedBuffer data, Objinfo objinfo, MethodParameters methodParms, TuxedoCorbaConnection aCorbaConn, int flags, TuxRply trp, Xid tid, int trantime, TuxedoConnection tuxUser) throws TPException {
      Objrecv currObjrecv = null;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("/dsession(" + this.uid + ")/tpMethodReq/0/" + flags + ":" + trp + ":" + tid + ":" + methodParms);
      }

      if (objinfo == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpMethodReq/10");
         }

         throw new TPException(4);
      } else if (data != null && data.getHintIndex() != 10) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/tpMethodReq/20");
         }

         throw new TPException(4);
      } else {
         if (methodParms != null) {
            currObjrecv = methodParms.getObjrecv();
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession/tpMethodReq/21,currObjrecv = " + currObjrecv);
            }
         }

         tfmh tmmsg;
         if (data != null) {
            tcm user = new tcm((short)0, new UserTcb(data));
            tmmsg = new tfmh(data.getHintIndex(), user, 1);
            this.setUse64BitsLong(tmmsg);
         } else {
            tmmsg = new tfmh(1);
         }

         if (this.myCltInfo == null) {
            this.myCltInfo = new ClientInfo(currObjrecv, 1, this.local_domain_name);
         }

         if (currObjrecv == null) {
            objinfo.setSendSrcCltinfo(this.myCltInfo);
         } else {
            objinfo.setSendSrcCltinfo(currObjrecv.getObjinfo().getRecvSrcCltinfo());
         }

         TdomValsTcb currTdomValsTcb;
         if (objinfo.getIsMyDomain() == 0 && tmmsg.tdom_vals == null) {
            tmmsg.tdom_vals = new tcm((short)17, new TdomValsTcb());
            currTdomValsTcb = (TdomValsTcb)tmmsg.tdom_vals.body;
            currTdomValsTcb.setDescrim(2);
         }

         String val;
         if (tmmsg.tdom_vals != null) {
            currTdomValsTcb = (TdomValsTcb)tmmsg.tdom_vals.body;
            if ((flags & 1) == 0) {
               currTdomValsTcb.setOrigDomain(new String(this.local_domain_name));
            }

            ClientInfo cltInfo;
            if ((cltInfo = objinfo.getSendSrcCltinfo()) == null) {
               currTdomValsTcb.setSrcDomain(new String(this.local_domain_name));
            } else if (cltInfo.getDomain() != null && !cltInfo.getDomain().equals("")) {
               currTdomValsTcb.setSrcDomain(new String(cltInfo.getDomain()));
            } else {
               currTdomValsTcb.setSrcDomain(new String(this.local_domain_name));
            }

            if (currTdomValsTcb.getDestDomain() == null || currTdomValsTcb.getDestDomain().equals("")) {
               if ((val = objinfo.getDomainId()) == null) {
                  currTdomValsTcb.setDestDomain(new String(this.local_domain_name));
               } else {
                  currTdomValsTcb.setDestDomain(new String(val));
               }
            }
         }

         if (objinfo.getIsACallout() == 1) {
            TGIOPUtil.calloutSet(tmmsg, objinfo, currObjrecv, 0);
            if (objinfo.getIsMyDomain() == 1) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession/tpMethodReq/25");
               }

               throw new TPException(12);
            }
         } else if (currObjrecv != null) {
            TGIOPUtil.routeSetHost(tmmsg, currObjrecv.getHost(), currObjrecv.getHost().length(), (short)currObjrecv.getPort(), 0);
         } else {
            TGIOPUtil.routeSetHost(tmmsg, (String)null, 0, (short)0, 0);
         }

         if (tmmsg.route != null) {
            RouteTcb currRouteTcb = (RouteTcb)tmmsg.route.body;
            currRouteTcb.setObjinfo(objinfo);
         }

         if (objinfo.getIsMyDomain() == 0) {
            tmmsg.getMetahdr().setFlags(tmmsg.getMetahdr().getFlags() & -8193);
            val = new String("//" + objinfo.getDomainId());
            flags &= -2;
            CallDescriptor cd = this._tpacall_internal(trp, val, tmmsg, flags, tid, trantime, methodParms, false, aCorbaConn, (GatewayTpacallAsyncReply)null, tuxUser);
            tmmsg.tdom_vals = tmmsg.route = tmmsg.callout = null;
            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/tpMethodReq/40");
            }

            return cd;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession/tpMethodReq/30");
            }

            throw new TPException(12);
         }
      }
   }

   protected TuxRply getRplyObj() {
      return this.myRplyObj;
   }

   public void _tpsend_internal(tfmh tmmsg, int sequenceNumber, int conversationIdentifier, boolean isInitiator, boolean switchDirection, boolean doDisconnect) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession(" + this.uid + ")/_tpsend_internal/" + sequenceNumber + "/" + conversationIdentifier + "/" + isInitiator + "/" + switchDirection + "/" + doDisconnect);
      }

      UserRec user = null;
      if (!this.is_connected) {
         if (traceEnabled) {
            ntrace.doTrace("*]/dsession(" + this.uid + ")/_tpsend_internal/10/");
         }

         throw new TPException(9, "ERROR: Connection dropped");
      } else {
         if (this.rcv_place.isTerm()) {
            this._dom_drop();
         }

         if (this.is_term) {
            if (traceEnabled) {
               ntrace.doTrace("*]/dsession(" + this.uid + ")/_tpsend_internal/20/");
            }

            throw new TPException(9, "ERROR: Tuxedo session has been terminated");
         } else {
            TdomTcb tmmsg_tdom;
            if (doDisconnect) {
               tmmsg_tdom = new TdomTcb(6, this.allocReqid(), 0, (String)null);
            } else {
               tmmsg_tdom = new TdomTcb(5, this.allocReqid(), 0, (String)null);
            }

            tmmsg_tdom.setConvId(conversationIdentifier);
            tmmsg_tdom.set_seqnum(sequenceNumber);
            int dom_info = tmmsg_tdom.get_info();
            if (isInitiator) {
               tmmsg_tdom.set_info(dom_info | 1);
            } else {
               tmmsg_tdom.set_info(dom_info | 2);
            }

            if (switchDirection) {
               tmmsg_tdom.set_flag(4096);
               tmmsg_tdom.set_diagnostic(22);
            }

            if (doDisconnect) {
               tmmsg_tdom.set_diagnostic(22);
               tmmsg_tdom.set_tpevent(1);
            }

            tmmsg.tdom = new tcm((short)7, tmmsg_tdom);
            tmmsg.AAA = null;
            this.setUse64BitsLong(tmmsg);

            try {
               synchronized(this.dom_ostream) {
                  if (this.dom_protocol >= 15) {
                     tmmsg.write_tfmh(this.dom_ostream, this.cmplimit);
                  } else {
                     tmmsg.write_dom_65_tfmh(this.dom_ostream, this.local_domain_name, this.dom_protocol, this.cmplimit);
                  }
               }
            } catch (IOException var16) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/dsession(" + this.uid + ")/_tpsend_internal/30/");
               }

               throw new TPException(12, "ERROR: tpsend failed with network error: " + var16);
            }

            if (traceEnabled) {
               ntrace.doTrace("]/dsession(" + this.uid + ")/_tpsend_internal/40/");
            }

         }
      }
   }

   public HashMap getRMICallList() {
      return this.rmiCallList;
   }

   public UserRec getCurrentUser(TuxedoConnection tuxUser) {
      boolean need_set = false;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         if (tuxUser != null) {
            ntrace.doTrace("[/dsession/getCurrentUser/" + tuxUser.toString());
         } else {
            ntrace.doTrace("[/dsession/getCurrentUser/null tuxUser");
         }

         ntrace.doTrace("cachedUR = " + this.cachedUR);
      }

      UserRec u;
      if (this.cachedUR && tuxUser != null) {
         if ((u = tuxUser.getUserRecord()) != null) {
            if (traceEnabled) {
               ntrace.doTrace("]/dsession/getCurrentUser(10) return " + u.getRemoteUserName());
            }

            return u;
         }

         need_set = true;
      }

      TCAuthenticatedUser c_subj = TCSecurityManager.getCurrentUser();
      u = this.myAppKey.getTuxedoUserRecord(c_subj);
      if (need_set && u != null) {
         tuxUser.setUserRecord(u);
      }

      if (traceEnabled) {
         if (u != null) {
            ntrace.doTrace("]/dsession/getCurrentUser(20) return" + u.getRemoteUserName());
         } else {
            ntrace.doTrace("]/dsession/getCurrentUser(30) return null");
         }
      }

      return u;
   }

   public void tpsprio(int prio, int flags) throws TPException {
      if ((flags & -65) != 0) {
         throw new TPException(4, "Bad flags value");
      } else {
         if ((flags & 64) != 0) {
            if (prio >= 1 && prio <= 100) {
               this.tmsndprio = prio;
            } else {
               this.tmsndprio = 50;
            }
         } else if (prio > 100) {
            this.tmsndprio = 100;
         } else if (prio < 1) {
            this.tmsndprio = 1;
         } else {
            this.tmsndprio = prio;
         }

      }
   }

   public void sendKeepAliveRequest() {
      this.myLock.lock();
      int old_state = this.kaState;
      this.kaState = 6;
      this.myLock.unlock();
      TdomTcb dmq_tdom = new TdomTcb(23, this.allocReqid(), 0, (String)null);
      dmq_tdom.set_diagnostic(0);
      tfmh dmq = new tfmh(1);
      dmq.tdom = new tcm((short)7, dmq_tdom);
      this.kawExpTime = TimerEventManager.getClockTick() + (long)this.kaws;

      try {
         synchronized(this.dom_ostream) {
            dmq.write_tfmh(this.dom_ostream, this.cmplimit);
         }
      } catch (IOException var7) {
         this.kaState = old_state;
      }

      this.myLock.lock();
      this.kaExpTime = TimerEventManager.getClockTick() + (long)this.kas;
      if (this.kaState == 6) {
         if (this.kaws > 0) {
            this.kaState = 2;
         } else {
            this.kaState = 1;
         }
      }

      this.myLock.unlock();
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("...Send DMQUERY(KEEPALIVE) from " + this.local_domain_name + " to " + this.remote_domain_id);
      }

   }

   public void sendKeepAliveAcknowledge() {
      TdomTcb dmq_tdom = new TdomTcb(23, this.allocReqid(), 0, (String)null);
      dmq_tdom.set_diagnostic(1);
      tfmh dmq = new tfmh(1);
      dmq.tdom = new tcm((short)7, dmq_tdom);

      try {
         synchronized(this.dom_ostream) {
            dmq.write_tfmh(this.dom_ostream, this.cmplimit);
         }
      } catch (IOException var6) {
      }

      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("...Send DMQUERY(KEEPALIVE_RPLY) from " + this.local_domain_name + " to " + this.remote_domain_id);
      }

   }

   public void startKACountDown() {
      long tick = TimerEventManager.getClockTick();
      this.myLock.lock();
      this.kaState = 1;
      this.kaExpTime = tick + (long)this.kas;
      this.myLock.unlock();
   }

   public void updateLastReceiveTime() {
      if (this.kaState != -1 && this.kaState != 5 && this.kaState != 4) {
         long tick = TimerEventManager.getClockTick();
         this.myLock.lock();
         if (this.kaState != 1) {
            if (this.kaTask != null && this.kaState == 3) {
               this.kaTask.cancel();
            }

            this.kaState = 1;
            this.kawExpTime = 0L;
         }

         if (tick != this.lastRecvTime) {
            this.lastRecvTime = tick;
            this.kaExpTime = tick + (long)this.kas;
         }

         this.myLock.unlock();
      }
   }

   public boolean isKATimersExpired(long tick) {
      if (this.kaState != 5 && this.kaState != -1 && this.kaState != 4) {
         this.myLock.lock();
         if (this.kawExpTime > 0L && tick >= this.kawExpTime) {
            this.kawExpTime = 0L;
            this.myLock.unlock();

            try {
               WTCLogger.logInfoDisconnectNoKeepAliveAck(this.local_domain_name, this.remote_domain_id);
               this.tpterm();
            } catch (TPException var4) {
            }

            return true;
         } else {
            if (this.kaExpTime > 0L && tick >= this.kaExpTime) {
               if (this.kaState == 1) {
                  this.kaExpTime = tick + (long)this.kas;
                  this.kaState = 3;
                  this.kaTask = new KeepAliveTask(this);
                  this.asyncTimeService.schedule(this.kaTask, 0L);
               } else if (this.kaState == 2) {
                  this.kaState = 3;
                  this.kaExpTime = tick + (long)this.kas;
                  this.kaTask = new KeepAliveTask(this);
                  this.asyncTimeService.schedule(this.kaTask, 0L);
               }
            }

            this.myLock.unlock();
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isKeepAliveAvailable() {
      return this.is_connected && this.ka > 0 && (this.kaState == 1 || this.kaState == 2 || this.kaState == 6 || this.kaState == 3);
   }

   private void collect_stat_begin(TDMImport imp) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (this.myWTCStat != null && imp != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/dsession(" + this.uid + ")/collect_stat_begin/collecting metrics for import service " + imp.getResourceName() + "(" + this.local_domain_name + ":" + this.remote_domain_id + ")");
         }

         this.myWTCStat.updOutboundMessageTotalCount(this, 1L);
         this.myWTCStat.updOutstandingNWReqCount(this, 1L);
         this.myWTCStat.updOutboundMessageTotalCount(imp, 1L);
         this.myWTCStat.updOutstandingNWReqCount(imp, 1L);
         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/collect_stat_begin/done)");
         }
      }

   }

   private void collect_stat_end(TDMImport imp, long len, boolean result, boolean end) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (this.myWTCStat != null && imp != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/dsession(" + this.uid + ")/collect_stat_end/collecting metrics for import service " + imp.getResourceName() + "(" + this.local_domain_name + ":" + this.remote_domain_id + ")/msg_len=" + len + ", result=" + result + ", end=" + end);
         }

         if (len > 0L) {
            this.myWTCStat.updOutboundNWMessageTotalSize(this, len);
         }

         if (end) {
            this.myWTCStat.updOutstandingNWReqCount(this, -1L);
         }

         if (len > 0L) {
            this.myWTCStat.updOutboundNWMessageTotalSize(imp, len);
         }

         if (end) {
            this.myWTCStat.updOutstandingNWReqCount(imp, -1L);
            if (result) {
               this.myWTCStat.updOutboundSuccessReqTotalCount(imp, 1L);
            } else {
               this.myWTCStat.updOutboundFailReqTotalCount(imp, 1L);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/collect_stat_end/done)");
         }
      }

   }

   public void collect_stat_end(CallDescriptor cd, long len, boolean result, boolean end) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      TDMImport imp = (TDMImport)this.cdToImpSvc.get(cd);
      if (this.myWTCStat != null && imp != null) {
         if (traceEnabled) {
            ntrace.doTrace("[/dsession(" + this.uid + ")/collect_stat_end/collecting metrics for import service " + imp.getResourceName() + "(" + this.local_domain_name + ":" + this.remote_domain_id + ")/msg_len=" + len + ", result=" + result + ", end=" + end);
         }

         if (len > 0L) {
            this.myWTCStat.updOutboundNWMessageTotalSize(this, len);
         }

         if (end) {
            this.myWTCStat.updOutstandingNWReqCount(this, -1L);
         }

         if (len > 0L) {
            this.myWTCStat.updOutboundNWMessageTotalSize(imp, len);
         }

         if (end) {
            this.myWTCStat.updOutstandingNWReqCount(imp, -1L);
            if (result) {
               this.myWTCStat.updOutboundSuccessReqTotalCount(imp, 1L);
            } else {
               this.myWTCStat.updOutboundFailReqTotalCount(imp, 1L);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/dsession(" + this.uid + ")/collect_stat_end/done)");
         }
      }

   }

   private void setOutboundImportedXid(tfmh tmmsg, Xid tid, int mode) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/dsession/setOutboundImportedXid/" + tid + "/" + mode);
      }

      if (tmmsg != null && tid != null) {
         MetaTcb tmmsg_metatcm;
         if (tmmsg.meta == null) {
            tmmsg_metatcm = new MetaTcb();
            tmmsg.meta = new tcm((short)19, tmmsg_metatcm);
         } else if (tmmsg.meta.body == null) {
            tmmsg_metatcm = new MetaTcb();
            tmmsg.meta.body = tmmsg_metatcm;
         } else {
            tmmsg_metatcm = (MetaTcb)tmmsg.meta.body;
         }

         MetaTcmHelper.setImportedXid(tmmsg_metatcm, tid.getFormatId(), tid.getGlobalTransactionId(), (byte[])null);
         if (traceEnabled) {
            ntrace.doTrace("]/dsession/setOutboundImportedXid/");
         }

      }
   }

   private class MyListener implements HandshakeCompletedListener {
      private MyListener() {
      }

      public void handshakeCompleted(HandshakeCompletedEvent event) {
         boolean traceEnabled = ntrace.isTraceEnabled(4);
         if (traceEnabled) {
            ntrace.doTrace("Client handshake done. Cipher used: " + event.getCipherSuite());
         }

      }

      // $FF: synthetic method
      MyListener(Object x1) {
         this();
      }
   }
}
