package weblogic.connector.security.layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextProvider;
import weblogic.connector.security.SecurityHelperFactory;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.work.WorkContextManager;
import weblogic.connector.work.WorkRuntimeMetadata;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WorkImpl extends SecureBaseImpl implements Work {
   private static final long serialVersionUID = -2843016894938066669L;
   private ClassLoader rarClassLoader;
   private ClassLoader origClassLoader;
   private List wcList;
   private WorkRuntimeMetadata metadata;
   private AuthenticatedSubject callerSubject;

   public WorkImpl(String appId, String moduleName, Work work, SubjectStack adapterLayer, AuthenticatedSubject kernelId, ClassLoader rarClassLoader, WorkContextManager ctxManager) {
      super(work, adapterLayer, "Work", kernelId);
      this.rarClassLoader = rarClassLoader;
      this.initWorkContexts(ctxManager);
      this.metadata = new WorkRuntimeMetadata();
      this.metadata.setWork(this);
      this.metadata.setAppId(appId);
      this.metadata.setModuleName(moduleName);
      this.metadata.setWorkName(work.getClass().getName());
      this.callerSubject = SecurityHelperFactory.getInstance().getCurrentSubject(kernelId);
   }

   public void run() {
      if (this.metadata.getEstablishedSubject() != null) {
         this.runIt();
      } else {
         this.pushWorkSubject(this.callerSubject);

         try {
            this.runIt();
         } finally {
            this.pop();
         }
      }

   }

   public void release() {
      if (this.metadata.getEstablishedSubject() != null) {
         this.pushGivenSubject(this.metadata.getEstablishedSubject());
      } else {
         this.pushWorkSubject(this.callerSubject);
      }

      try {
         this.releaseIt();
      } finally {
         this.pop();
      }

   }

   private void runIt() {
      try {
         this.setClassLoader();
         ((Work)this.myObj).run();
      } finally {
         this.resetClassLoader();
      }

   }

   private void releaseIt() {
      try {
         this.setClassLoader();
         ((Work)this.myObj).release();
      } finally {
         this.resetClassLoader();
      }

   }

   private void setClassLoader() {
      if (this.rarClassLoader != null) {
         this.origClassLoader = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(this.rarClassLoader);
      }

   }

   private void resetClassLoader() {
      if (this.rarClassLoader != null) {
         Thread.currentThread().setContextClassLoader(this.origClassLoader);
      }

   }

   private void initWorkContexts(WorkContextManager ctxManager) {
      if (this.supportWorkContextProvider()) {
         this.push();

         try {
            this.wcList = new ArrayList();
            List originalWcList = this.getOriginalWorkContextProvider().getWorkContexts();
            if (originalWcList == null) {
               return;
            }

            Iterator var3 = originalWcList.iterator();

            while(var3.hasNext()) {
               WorkContext originalWc = (WorkContext)var3.next();
               WorkContextWrapper wc = ctxManager.createWrapper(originalWc, this.adapterLayer, this.kernelId);
               this.wcList.add(wc);
            }
         } finally {
            this.pop();
         }

      }
   }

   public final List getWorkContexts() {
      return this.wcList;
   }

   public final boolean supportWorkContextProvider() {
      return this.myObj instanceof WorkContextProvider;
   }

   public final Work getOriginalWork() {
      return (Work)this.getSourceObj();
   }

   public final WorkContextProvider getOriginalWorkContextProvider() {
      return (WorkContextProvider)this.getSourceObj();
   }

   public final WorkRuntimeMetadata getRuntimeMetadata() {
      return this.metadata;
   }
}
