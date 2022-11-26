package weblogic.diagnostics.instrumentation;

import java.security.AccessController;
import java.security.Principal;
import java.util.Iterator;
import javax.security.auth.Subject;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.spi.WLSUser;
import weblogic.transaction.TxHelper;

public final class InstrumentationEvent {
   private static ComponentInvocationContextManager compInvCtxMgr = ComponentInvocationContextManager.getInstance();
   private String retValString;
   private String argsString;
   private DiagnosticMonitorControl monitor;
   private DiagnosticAction action;
   private String eventType;
   private JoinPoint jp;
   private long timestamp;
   private String contextId;
   private String threadName;
   private String contextPayload;
   private String txId;
   private String userId;
   private String partitionId;
   private String partitionName;
   private Object payload;
   private long dyeVector;
   private static String serverName;
   private static String domainName;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public InstrumentationEvent(DiagnosticAction action, JoinPoint jp, boolean showArgs) {
      this();
      this.action = action;
      this.jp = jp;
      if (showArgs) {
         this.captureArgReturnValues();
      }

   }

   public InstrumentationEvent(DiagnosticMonitorControl monitor, JoinPoint jp) {
      this();
      this.monitor = monitor;
      this.jp = jp;
   }

   public InstrumentationEvent(String eventType) {
      this();
      this.eventType = eventType;
   }

   public InstrumentationEvent(String eventType, Object payload) {
      this();
      this.eventType = eventType;
      this.payload = payload;
   }

   private InstrumentationEvent() {
      this.retValString = "";
      this.argsString = "";
      this.contextId = "";
      this.threadName = "";
      this.txId = "";
      this.userId = "";
      this.partitionId = "";
      this.partitionName = "";
      this.doInit();
   }

   private void doInit() {
      this.timestamp = System.currentTimeMillis();
      this.threadName = Thread.currentThread().getName();
      Correlation context = CorrelationFactory.findOrCreateCorrelation();
      if (context != null) {
         this.contextId = context.getECID();
         this.contextPayload = context.getPayload();
         this.dyeVector = context.getDyeVector();
      }

      if (compInvCtxMgr != null) {
         ComponentInvocationContext ctx = compInvCtxMgr.getCurrentComponentInvocationContext();
         if (ctx != null) {
            String tempId = ctx.getPartitionId();
            if (tempId != null) {
               this.partitionId = tempId;
            }

            tempId = ctx.getPartitionName();
            if (tempId != null) {
               this.partitionName = tempId;
            }
         }
      }

      if (Kernel.isInitialized()) {
         this.txId = TxHelper.getTransactionId();
         Subject currentSubject = Security.getCurrentSubject();
         this.userId = this.extractCurrentWLSSubject(currentSubject);
         if (this.userId == null) {
            this.userId = SubjectUtils.getUsername(currentSubject);
         }

         if (this.txId == null) {
            this.txId = "";
         }

         if (this.userId == null) {
            this.userId = "";
         }

         if (serverName == null) {
            RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
            if (runtimeAccess != null) {
               serverName = runtimeAccess.getServerName();
               domainName = runtimeAccess.getDomainName();
            }
         }
      }

   }

   private String extractCurrentWLSSubject(Subject currentSubject) {
      String username = null;
      int numPrincipals = currentSubject.getPrincipals().size();
      if (numPrincipals > 0) {
         Iterator principalsIt = currentSubject.getPrincipals().iterator();

         while(principalsIt.hasNext()) {
            Principal next = (Principal)principalsIt.next();
            if (next instanceof WLSUser) {
               username = next.getName();
               break;
            }
         }
      } else {
         username = WLSPrincipals.getAnonymousUsername();
      }

      return username;
   }

   public String getEventType() {
      return this.eventType;
   }

   public void setEventType(String eventType) {
      this.eventType = eventType;
   }

   public void setContextId(String contextId) {
      this.contextId = contextId;
   }

   public String getContextId() {
      return this.contextId;
   }

   public void setPayload(Object payload) {
      this.payload = payload;
   }

   public Object getPayload() {
      return this.payload;
   }

   public DataRecord getDataRecord() {
      String actionType = "";
      String monType = "";
      String scopeName = "";
      if (this.action != null) {
         DiagnosticMonitor mon = this.action.getDiagnosticMonitor();
         if (mon instanceof DiagnosticMonitorControl) {
            this.monitor = (DiagnosticMonitorControl)mon;
         }

         actionType = this.action.getType();
      }

      if (this.monitor != null) {
         monType = this.monitor.getType();
         InstrumentationScope scope = this.monitor.getInstrumentationScope();
         if (scope != null) {
            scopeName = scope.getName();
         }
      }

      int linenum = 0;
      String moduleName = "";
      String sourceFile = "";
      String className = "";
      String methodName = "";
      String methodDesc = "";
      if (this.jp != null) {
         moduleName = this.jp.getModuleName();
         linenum = this.jp.getLineNumber();
         sourceFile = this.jp.getSourceFile();
         className = this.jp.getClassName();
         methodName = this.jp.getMethodName();
         methodDesc = this.jp.getMethodDescriptor();
      }

      String server_name = serverName != null ? serverName : "";
      String domain_name = domainName != null ? domainName : "";
      String event_type = this.eventType != null ? this.eventType : actionType;
      this.contextPayload = this.contextPayload != null ? this.contextPayload : "";
      Object[] data = new Object[ArchiveConstants.EVENTS_ARCHIVE_COLUMNS_COUNT];
      int cnt = 0;
      data[cnt++] = null;
      data[cnt++] = new Long(this.timestamp);
      data[cnt++] = this.contextId;
      data[cnt++] = this.txId;
      data[cnt++] = this.userId;
      data[cnt++] = event_type;
      data[cnt++] = domain_name;
      data[cnt++] = server_name;
      data[cnt++] = scopeName;
      data[cnt++] = moduleName;
      data[cnt++] = monType;
      data[cnt++] = sourceFile;
      data[cnt++] = new Integer(linenum);
      data[cnt++] = className;
      data[cnt++] = methodName;
      data[cnt++] = methodDesc;
      data[cnt++] = this.argsString;
      data[cnt++] = this.retValString;
      data[cnt++] = this.payload;
      data[cnt++] = this.contextPayload;
      data[cnt++] = new Long(this.dyeVector);
      data[cnt++] = this.threadName;
      data[cnt++] = this.partitionId;
      data[cnt++] = this.partitionName;
      return new DataRecord(data);
   }

   private void captureArgReturnValues() {
      if (this.jp instanceof DynamicJoinPoint) {
         DynamicJoinPoint dynJp = (DynamicJoinPoint)this.jp;
         Object retVal = dynJp.getReturnValue();
         if (retVal != null) {
            this.retValString = this.getArgValue(retVal);
         }

         Object[] args = dynJp.getArguments();
         if (args != null) {
            int size = args.length;
            StringBuffer buf = new StringBuffer();

            for(int i = 0; i < size; ++i) {
               if (i > 0) {
                  buf.append(", ");
               }

               buf.append(this.getArgValue(args[i]));
            }

            this.argsString = buf.toString();
         }
      }

   }

   private String getArgValue(Object obj) {
      if (obj == null) {
         return "null";
      } else {
         String retVal = null;

         try {
            retVal = obj.toString();
         } catch (Throwable var4) {
            retVal = "??" + obj.getClass().getName() + "??";
         }

         return retVal;
      }
   }
}
