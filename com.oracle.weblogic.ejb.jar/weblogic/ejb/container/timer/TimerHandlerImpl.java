package weblogic.ejb.container.timer;

import java.lang.reflect.InvocationTargetException;
import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchEntityException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.TimerHandler;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.EJBContextHandler;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationContextStack;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.TimerDrivenLocalObject;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.manager.MessageDrivenManager;

public final class TimerHandlerImpl implements TimerHandler {
   private final BeanManager beanManager;
   private final BeanInfo beanInfo;

   public TimerHandlerImpl(BeanManager beanManager, BeanInfo beanInfo) {
      this.beanManager = beanManager;
      this.beanInfo = beanInfo;
   }

   public void executeTimer(ClusteredTimerImpl timer) {
      MethodDescriptor md;
      if (timer.isAutoCreated()) {
         md = this.beanInfo.getAutomaticTimerMethodDescriptor(timer.getCallbackMethodSignature());
      } else {
         md = this.beanInfo.getEjbTimeoutMethodDescriptor();
      }

      (new TimerObject(this.beanManager, this.beanInfo, md)).execute(timer);
   }

   private static final class TimerObject extends TimerDrivenLocalObject {
      private final MethodDescriptor md;

      TimerObject(BeanManager beanManager, BeanInfo beanInfo, MethodDescriptor md) {
         super.setBeanManager(beanManager);
         super.setBeanInfo(beanInfo);
         this.md = md;
      }

      public void execute(ClusteredTimerImpl timer) {
         InvocationWrapper wrap;
         try {
            wrap = this.preInvoke(timer.getPrimaryKey(), this.md, new EJBContextHandler(this.md, new Object[]{timer}));
         } catch (Throwable var16) {
            if (var16 instanceof EJBException) {
               Exception cb = ((EJBException)var16).getCausedByException();
               if (cb instanceof NoSuchEntityException) {
                  timer.cancel();
                  return;
               }
            }

            if (EJBRuntimeUtils.isCausedBy(var16, NoSuchEJBException.class)) {
               EJBLogger.logInvokeEJBTimeoutCallbackOnUndeployedBeanInstance(this.beanInfo.getDisplayName());
            } else {
               EJBLogger.logExceptionBeforeInvokingEJBTimeout(this.beanInfo.getDisplayName(), var16);
            }

            if (timer.isTransactional()) {
               try {
                  TransactionService.getTransaction().setRollbackOnly();
               } catch (Exception var15) {
                  EJBLogger.logErrorMarkingRollback(var15);
               }
            }

            return;
         }

         try {
            Object bean = wrap.getBean();
            WLEnterpriseBean wlBean = this.beanInfo.isClientDriven() ? (WLEnterpriseBean)bean : null;
            int oldState = wlBean != null ? wlBean.__WL_getMethodState() : 0;
            Throwable ee = null;
            boolean contextPushed = false;

            try {
               if (wlBean != null) {
                  wlBean.__WL_setMethodState(65536);
               }

               if (this.beanInfo.isSessionBean() && wlBean != null) {
                  EJBContextManager.pushEjbContext(wlBean.__WL_getEJBContext());
                  InvocationContextStack.push(wrap);
                  contextPushed = true;
               } else if (!this.beanInfo.isClientDriven()) {
                  EJBContextManager.pushEjbContext(((MessageDrivenManager)this.beanInfo.getBeanManager()).getMessageDrivenContext());
                  InvocationContextStack.push(wrap);
                  contextPushed = true;
               }

               this.getBeanManager().invokeTimeoutMethod(bean, new TimerWrapper(timer), this.md.getMethod());
            } catch (Throwable var17) {
               Throwable t = var17;
               if (var17 instanceof InvocationTargetException) {
                  t = var17.getCause();
               }

               ee = t;
            } finally {
               if (wlBean != null) {
                  wlBean.__WL_setMethodState(oldState);
               }

               if (contextPushed) {
                  InvocationContextStack.pop();
                  EJBContextManager.popEjbContext();
               }

            }

            this.postInvoke(wrap, ee);
         } catch (Throwable var19) {
            EJBLogger.logExceptionInvokingEJBTimeout(this.beanInfo.getDisplayName(), var19);
         }

      }
   }
}
