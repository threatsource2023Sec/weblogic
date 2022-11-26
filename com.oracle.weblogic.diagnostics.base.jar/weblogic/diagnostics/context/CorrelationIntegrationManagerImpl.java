package weblogic.diagnostics.context;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;
import weblogic.workarea.utils.WorkContextInputAdapter;

class CorrelationIntegrationManagerImpl implements CorrelationIntegrationManagerForTest {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static CorrelationIntegrationManager SINGLETON = null;

   private CorrelationIntegrationManagerImpl() {
   }

   static synchronized CorrelationIntegrationManager getInstance() {
      if (!SecurityServiceManager.isKernelIdentity(SecurityServiceManager.getCurrentSubject(KERNEL_ID))) {
         return null;
      } else {
         if (SINGLETON == null) {
            SINGLETON = new CorrelationIntegrationManagerImpl();
         }

         return SINGLETON;
      }
   }

   public Correlation newCorrelation() {
      return new CorrelationImpl();
   }

   public void clearCorrelation() {
      CorrelationFactory.setCorrelation((Correlation)null);
   }

   public Correlation findCorrelation() {
      return CorrelationFactory.findCorrelation();
   }

   public Correlation findOrCreateCorrelation() {
      return CorrelationFactory.findOrCreateCorrelation(true);
   }

   public void setCorrelationEnabled(boolean enabled) {
      CorrelationManager.getCorrelationManagerInternal().setEnabled(enabled);
   }

   public void setDMSCorrelationCallback(CorrelationCallback callback) {
      CorrelationManager.getCorrelationManagerInternal().setDMSCorrelationCallback(callback);
   }

   public Correlation newCorrelation(String ecid, int[] ridComponents, int kidCount, Map values, long dyeVector, boolean inheritable) {
      return CorrelationManager.getCorrelationManagerInternal().newCorrelation(ecid, ridComponents, kidCount, values, dyeVector, inheritable);
   }

   public void activateCorrelation(Correlation correlation) {
      CorrelationManager.getCorrelationManagerInternal().activateCorrelation(correlation);
   }

   public Correlation getCorrelationFromMap() {
      try {
         return (Correlation)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
               return CorrelationImpl.getCorrelationFromMap(map);
            }
         });
      } catch (Throwable var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public Correlation newCorrelation(DiagnosticContextImpl dcImpl) {
      return new CorrelationImpl(dcImpl);
   }

   public Correlation newCorrelationFromBinaryDC(byte[] dcBytes) {
      return dcBytes == null ? null : new CorrelationImpl(this.newDiagnosticContext(dcBytes));
   }

   public DiagnosticContextImpl newDiagnosticContext(byte[] dcBytes) {
      if (dcBytes == null) {
         return null;
      } else {
         ByteArrayInputStream bis = new ByteArrayInputStream(dcBytes);
         ObjectInputStream ois = null;

         DiagnosticContextImpl newCtx;
         try {
            ois = new ObjectInputStream(bis);
            WorkContextInputAdapter wcia = new WorkContextInputAdapter(ois);
            newCtx = new DiagnosticContextImpl(false, (String)null);
            newCtx.readContext(wcia);
            DiagnosticContextImpl var6 = newCtx;
            return var6;
         } catch (Exception var16) {
            newCtx = null;
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var15) {
               }
            }

         }

         return newCtx;
      }
   }

   public DiagnosticContextImpl newDiagnosticContext(CorrelationImpl correlation) {
      return new DiagnosticContextImpl(correlation);
   }

   public void setIncomingDCImplsSeen(boolean value) {
      DiagnosticContextImpl.forTestSetIncomingDCImplsSeen(value);
   }

   public void correlationPropagatedIn(CorrelationImpl ctx) {
      CorrelationManager.getCorrelationManagerInternal().correlationPropagatedIn(ctx);
   }

   public void setIsUnmarshalled(CorrelationImpl ctx, boolean value) {
      if (ctx != null) {
         ctx.forTestSetIsUnmarshalled(value);
      }

   }

   public void setIsUnmarshalled(DiagnosticContextImpl ctx, boolean value) {
      if (ctx != null) {
         ctx.forTestSetIsUnmarshalled(value);
      }

   }

   public String wrap(Correlation ctx) {
      return WrapUtils.wrap(ctx);
   }

   public Correlation unwrap(String wrapString) {
      return WrapUtils.unwrap(wrapString);
   }
}
