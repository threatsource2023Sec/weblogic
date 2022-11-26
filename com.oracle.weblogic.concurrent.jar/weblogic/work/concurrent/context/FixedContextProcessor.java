package weblogic.work.concurrent.context;

import java.util.Map;
import javax.naming.Context;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.subject.AbstractSubject;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public class FixedContextProcessor extends ContextSetupProcessor {
   private static final long serialVersionUID = -6154118891870095616L;

   public FixedContextProcessor(String appId, String moduleId, String compName, int concurrentType, ClassLoader classLoader, Context jndi) {
      super(concurrentType, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), new FixedCICProvider(ComponentInvocationContextManager.getInstance().createComponentInvocationContext(appId, moduleId, compName)), new SubmittingCompStateCheckerProvider(appId), new FixedClassLoaderProvider(classLoader), new FixedJndiProvider(jndi), FixedContextProcessor.FixedSecurityProvider.getAnonSubjectInstance(), FixedContextProcessor.FixedWorkAreaProvider.getEmptyMapInstance());
   }

   protected FixedContextProcessor(int concurrentType, ContextProvider... contextProviders) {
      super(concurrentType, contextProviders);
   }

   public int getConcurrentObjectType() {
      return -1;
   }

   public static class FixedWorkAreaProvider extends WorkAreaContextProvider {
      private static final long serialVersionUID = 6407940658319823943L;
      private static final FixedWorkAreaProvider EmptyMapProvider = new FixedWorkAreaProvider((WorkContextMapInterceptor)null);
      private final WorkAreaContextProvider.WorkAreaContextHandle waHandle;

      protected FixedWorkAreaProvider(WorkContextMapInterceptor map) {
         this.waHandle = new WorkAreaContextProvider.WorkAreaContextHandle(map);
      }

      public static FixedWorkAreaProvider getEmptyMapInstance() {
         return EmptyMapProvider;
      }

      public ContextHandle save(Map executionProperties) {
         return this.waHandle;
      }

      public int getConcurrentObjectType() {
         return -1;
      }
   }

   public static class FixedSecurityProvider extends SecurityContextProvider {
      private static final long serialVersionUID = -8969298043869527074L;
      private static final FixedSecurityProvider anonSubjectProvider;
      private final SecurityContextProvider.SecurityContextHandle securityHandle;

      protected FixedSecurityProvider(AbstractSubject subject) {
         this.securityHandle = new SecurityContextProvider.SecurityContextHandle(subject);
      }

      public static FixedSecurityProvider getAnonSubjectInstance() {
         return anonSubjectProvider;
      }

      public ContextHandle save(Map executionProperties) {
         return this.securityHandle;
      }

      public int getConcurrentObjectType() {
         return -1;
      }

      static {
         anonSubjectProvider = new FixedSecurityProvider(subjectManager.getAnonymousSubject());
      }
   }

   public static class FixedJndiProvider extends JndiContextProvider {
      private static final long serialVersionUID = 3042618630811785668L;
      private static final FixedJndiProvider NullContextInstance = new FixedJndiProvider((Context)null);
      private final JndiContextProvider.JndiContextHandle jndiHandle;

      protected FixedJndiProvider(Context context) {
         this.jndiHandle = new JndiContextProvider.JndiContextHandle(context);
      }

      public static FixedJndiProvider getNullContextInstance() {
         return NullContextInstance;
      }

      public ContextHandle save(Map executionProperties) {
         return this.jndiHandle;
      }

      public int getConcurrentObjectType() {
         return -1;
      }
   }

   public static class FixedClassLoaderProvider extends ClassLoaderContextProvider {
      private static final long serialVersionUID = -1766674487382031676L;
      private final ClassLoaderContextProvider.ClassLoaderHandle clHandle;

      public FixedClassLoaderProvider(ClassLoader cl) {
         this.clHandle = new ClassLoaderContextProvider.ClassLoaderHandle(cl);
      }

      public ContextHandle save(Map executionProperties) {
         return this.clHandle;
      }

      public int getConcurrentObjectType() {
         return -1;
      }
   }

   public static class FixedCICProvider extends CICContextProvider {
      private static final long serialVersionUID = 7111008022944658925L;
      private final CICContextProvider.InvocationContextHandle cicHandle;

      public FixedCICProvider(ComponentInvocationContext cic) {
         this.cicHandle = new CICContextProvider.InvocationContextHandle(cic, (ManagedInvocationContext)null);
      }

      public ContextHandle save(Map executionProperties) {
         return this.cicHandle;
      }

      public int getConcurrentObjectType() {
         return -1;
      }
   }
}
