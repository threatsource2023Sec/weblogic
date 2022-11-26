package weblogic.work.concurrent;

import java.security.AccessController;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.work.concurrent.context.CICContextProvider;
import weblogic.work.concurrent.context.ContextSetupProcessor;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.EmptyManagedTaskListener;
import weblogic.work.concurrent.utils.ManagedTaskUtils;

public class TaskWrapper {
   private static final ManagedTaskListener EmptyTaskListener = new EmptyManagedTaskListener();
   private final ContextProvider contextSetupProcessor;
   private final ContextProvider submittingCompStateChecker;
   private final ContextHandle contextHandleForSetup;
   private final ContextHandle contextHandleForSubmittingCompState;
   private final Object userTask;
   private final Callable target;
   private final String taskName;
   private final ManagedTaskListener taskListener;
   private final boolean checkUserObject;
   private final AuthenticatedSubject subject;
   private final String submittingPartition;

   private TaskWrapper(Object userTask, Callable target, ContextProvider contextSetupProcessor, ClassLoader taskClassloader) {
      if (userTask == null) {
         throw new NullPointerException();
      } else {
         this.target = target;
         this.userTask = userTask;
         this.contextSetupProcessor = contextSetupProcessor;
         this.submittingCompStateChecker = contextSetupProcessor instanceof ContextSetupProcessor ? ((ContextSetupProcessor)contextSetupProcessor).getSubmittingCompStateChecker() : null;
         Map props = null;
         if (userTask instanceof ManagedTask) {
            props = ((ManagedTask)userTask).getExecutionProperties();
         }

         this.contextHandleForSetup = contextSetupProcessor.save(props);
         this.contextHandleForSubmittingCompState = this.submittingCompStateChecker != null ? this.submittingCompStateChecker.save(props) : null;
         AbstractSubject currentSubject = TaskWrapper.ResourceProvider.INSTANCE.getSubjectManager().getCurrentSubject(TaskWrapper.ResourceProvider.INSTANCE.getKernelId());
         if (currentSubject instanceof AuthenticatedSubject) {
            this.subject = (AuthenticatedSubject)currentSubject;
            this.submittingPartition = TaskWrapper.ResourceProvider.INSTANCE.getCICManager().getCurrentComponentInvocationContext().getPartitionName();
         } else {
            this.subject = null;
            this.submittingPartition = null;
         }

         this.taskName = ManagedTaskUtils.getTaskName(userTask);
         this.taskListener = ManagedTaskUtils.getTaskListener(userTask);
         this.checkUserObject = ManagedTaskUtils.isCheckUserObject(userTask);
         if (taskClassloader != null && this.checkUserObject) {
            try {
               ConcurrentUtils.serializeByClassloader(userTask, taskClassloader);
               ConcurrentUtils.serializeByClassloader(this.taskListener, taskClassloader);
            } catch (Exception var8) {
               throw new RejectedExecutionException(var8);
            }
         }

      }
   }

   public TaskWrapper(Callable userTask, ContextProvider contextSetupProcessor, ClassLoader taskClassloader) {
      this((Object)userTask, (Callable)userTask, contextSetupProcessor, taskClassloader);
   }

   public TaskWrapper(Runnable userTask, Object result, ContextProvider contextSetupProcessor, ClassLoader taskClassloader) {
      this((Object)userTask, (Callable)Executors.callable(userTask, result), contextSetupProcessor, taskClassloader);
      if (taskClassloader != null && this.checkUserObject) {
         try {
            ConcurrentUtils.serializeByClassloader(result, taskClassloader);
         } catch (Exception var6) {
            throw new RejectedExecutionException(var6);
         }
      }

   }

   public void checkUserObject(Object o, ClassLoader taskClassloader) {
      if (taskClassloader != null && this.checkUserObject) {
         try {
            ConcurrentUtils.serializeByClassloader(o, taskClassloader);
         } catch (Exception var4) {
            throw new RejectedExecutionException(var4);
         }
      }

   }

   public ContextHandleWrapper setupContext() {
      return this.contextHandleForSetup != null && this.contextSetupProcessor != null ? new ContextHandleWrapper(this.contextSetupProcessor, this.contextHandleForSetup) : null;
   }

   public Object getUserTask() {
      return this.userTask;
   }

   public String getTaskName() {
      return this.taskName;
   }

   public Object call() throws Exception {
      ContextHandleWrapper context = this.setupContext();

      Object var2;
      try {
         var2 = this.target.call();
      } finally {
         if (context != null) {
            context.restore();
         }

      }

      return var2;
   }

   public boolean isLongRunning() {
      return ManagedTaskUtils.isLongRunning(this.userTask);
   }

   public ManagedTaskListener buildTaskListener() {
      return (ManagedTaskListener)(this.taskListener == null ? EmptyTaskListener : new ListenerWithContext(this.taskListener, this));
   }

   public boolean isCheckUserObject() {
      return this.checkUserObject;
   }

   public AuthenticatedSubject getAuthenticatedSubject() {
      return this.subject;
   }

   public boolean isAdminRequest() {
      if (this.subject == null) {
         return false;
      } else {
         try {
            return (Boolean)ComponentInvocationContextManager.runAs(TaskWrapper.ResourceProvider.INSTANCE.getKernelId(), TaskWrapper.ResourceProvider.INSTANCE.getCICManager().createComponentInvocationContext(this.submittingPartition), new Callable() {
               public Boolean call() throws Exception {
                  return SubjectUtils.doesUserHaveAnyAdminRoles(TaskWrapper.this.subject);
               }
            });
         } catch (ExecutionException var2) {
            return false;
         }
      }
   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return CICContextProvider.InvocationContextHandle.extractCIC(this.contextHandleForSubmittingCompState);
   }

   public void checkSubmittingCompState() {
      if (this.contextHandleForSubmittingCompState != null) {
         this.submittingCompStateChecker.setup(this.contextHandleForSubmittingCompState);
      }

   }

   private static enum ResourceProvider {
      INSTANCE;

      private final SubjectManager subjectManager = SubjectManager.getSubjectManager();
      private final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      private final ComponentInvocationContextManager cicManager;

      private ResourceProvider() {
         this.cicManager = ComponentInvocationContextManager.getInstance(this.kernelId);
      }

      private SubjectManager getSubjectManager() {
         return this.subjectManager;
      }

      private AuthenticatedSubject getKernelId() {
         return this.kernelId;
      }

      private ComponentInvocationContextManager getCICManager() {
         return this.cicManager;
      }
   }
}
