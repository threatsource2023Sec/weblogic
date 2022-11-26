package weblogic.management.provider.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.server.ServerService;

@Service
@Singleton
public class ServerServiceDependencyAnalyzer {
   @Inject
   private ServiceLocator serviceLocator;
   private DiGraph serviceGraph;
   private final String root = "Root";
   private List runLevels = new ArrayList(Arrays.asList(5, 10, 15, 20));

   @PostConstruct
   private void init() {
      try {
         this.serviceGraph = new DiGraph();
         this.serviceGraph.addVertex("Root");
         this.loadRunLevelNodes();
         this.loadServerServiceNodes();
      } catch (Exception var2) {
         PartitionLifecycleDebugger.debug("Error initializing " + this.getClass().getName() + var2.getMessage());
         throw var2;
      }
   }

   public List orderedServerServices() {
      final List result = new ArrayList();
      this.serviceGraph.breadthFirst((Object)"Root", (Object)null, new DiGraph.Visitor() {
         public void visit(Object data) {
            if (data instanceof Class) {
               Class c = (Class)data;
               if (ServerService.class.isAssignableFrom(c)) {
                  result.add(c);
               }
            }

         }
      });
      return result;
   }

   private void loadRunLevelNodes() {
      Integer previousRunLevel = null;

      Integer runLevel;
      for(Iterator var2 = this.runLevels.iterator(); var2.hasNext(); previousRunLevel = runLevel) {
         runLevel = (Integer)var2.next();
         this.serviceGraph.addVertex(runLevel);
         if (previousRunLevel != null) {
            this.serviceGraph.addEdge(previousRunLevel, runLevel);
         } else {
            this.serviceGraph.addEdge("Root", runLevel);
         }
      }

   }

   private void loadServerServiceNodes() {
      Iterator var1 = this.serviceLocator.getAllServiceHandles(ServerService.class, new Annotation[0]).iterator();

      while(var1.hasNext()) {
         ServiceHandle serviceHandle = (ServiceHandle)var1.next();
         ActiveDescriptor ad = serviceHandle.getActiveDescriptor();
         Class ssClass = ad.getImplementationClass();
         this.serviceGraph.ensureVertex(ssClass);
         Integer runLevelForService = this.runLevelForService(ssClass);
         if (runLevelForService == null) {
            this.serviceGraph.ensureEdge("Root", ssClass);
         } else {
            this.serviceGraph.ensureEdge(runLevelForService, ssClass);
            int runLevelIndex = this.runLevels.indexOf(runLevelForService);
            if (runLevelIndex < this.runLevels.size() - 1) {
               this.serviceGraph.ensureEdge(ssClass, this.runLevels.get(runLevelIndex + 1));
            }
         }

         Iterator var11 = ad.getInjectees().iterator();

         while(var11.hasNext()) {
            Injectee injectee = (Injectee)var11.next();
            Set reqdQualifiers = injectee.getRequiredQualifiers();
            ServiceHandle injectedServiceHandle = this.serviceLocator.getServiceHandle(injectee.getRequiredType(), (Annotation[])reqdQualifiers.toArray(new Annotation[reqdQualifiers.size()]));
            if (injectedServiceHandle != null) {
               Class injectedClass = injectedServiceHandle.getActiveDescriptor().getImplementationClass();
               if (ServerService.class.isAssignableFrom(injectedClass)) {
                  this.serviceGraph.ensureVertex(injectedClass);
                  this.serviceGraph.ensureEdge(injectedClass, ssClass);
               }
            }
         }
      }

   }

   private Integer runLevelForService(Class ssClass) {
      RunLevel runLevelAnno = (RunLevel)ssClass.getAnnotation(RunLevel.class);
      return runLevelAnno == null ? null : new Integer(runLevelAnno.value());
   }
}
