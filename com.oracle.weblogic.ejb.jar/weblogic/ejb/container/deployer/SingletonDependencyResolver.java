package weblogic.ejb.container.deployer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.GenericApplicationContext;
import weblogic.application.ModuleContext;
import weblogic.application.Registry;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.ReferenceResolutionException;
import weblogic.application.naming.ReferenceResolver;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.utils.graph.CyclicDependencyException;
import weblogic.ejb.container.utils.graph.DirectedGraph;
import weblogic.ejb.container.utils.graph.DirectedGraphImpl;
import weblogic.ejb.spi.ClientDrivenBeanInfo;
import weblogic.ejb.spi.DeploymentInfo;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.logging.Loggable;

public class SingletonDependencyResolver implements ReferenceResolver {
   private final String applicationId;
   private final String moduleId;
   private final String moduleURI;
   private final Set ejbLinks;
   private final SingletonSessionBeanInfo currentSingletonInfo;
   private Registry appRegistry;
   private Map dependencyMap;
   private SingletonDependencyInfo resolvedInfo;

   public SingletonDependencyResolver(String applicationId, String moduleId, String moduleURI, Set ejbLinks, SingletonSessionBeanInfo currentSingletonInfo) {
      this.applicationId = applicationId;
      this.moduleId = moduleId;
      this.moduleURI = moduleURI;
      this.ejbLinks = ejbLinks;
      this.currentSingletonInfo = currentSingletonInfo;
   }

   public SingletonDependencyInfo get() {
      if (this.resolvedInfo != null) {
         return this.resolvedInfo;
      } else {
         DirectedGraph graph;
         if (null != this.appRegistry && null != (graph = (DirectedGraph)this.appRegistry.get(DirectedGraph.class.getName()))) {
            try {
               this.resolvedInfo = new SingletonDependencyInfo(graph.getVerticesInPathTo(this.currentSingletonInfo), graph.getVerticesReachableFrom(this.currentSingletonInfo));
            } catch (CyclicDependencyException var3) {
               throw new IllegalStateException("Singleton dependencies have not been resolved yet!");
            }

            return this.resolvedInfo;
         } else {
            throw new IllegalStateException("Singleton dependencies have not been resolved yet!");
         }
      }
   }

   public void resolve(ApplicationContextInternal appCtx) throws ReferenceResolutionException {
      this.resolveInternal(EnvUtils.getAppModuleContexts(appCtx, new String[]{WebLogicModuleType.MODULETYPE_EJB, WebLogicModuleType.MODULETYPE_WAR}), appCtx);
   }

   public void resolve(ToolsContext appCtx) throws ReferenceResolutionException {
      this.resolveInternal(EnvUtils.getAppModuleContexts(appCtx, new ModuleType[]{ModuleType.EJB, ModuleType.WAR}), appCtx);
   }

   private void resolveInternal(Collection moduleContexts, GenericApplicationContext appCtx) throws ReferenceResolutionException {
      this.appRegistry = appCtx.getApplicationRegistry();
      DirectedGraph graph = (DirectedGraph)this.appRegistry.get(DirectedGraph.class.getName());
      if (null == graph) {
         int maxNodes = this.getMaxNodes(moduleContexts);
         graph = new DirectedGraphImpl(maxNodes);
         this.appRegistry.put(DirectedGraph.class.getName(), graph);
      }

      if (null != this.ejbLinks && !this.ejbLinks.isEmpty()) {
         this.resolveDependencyLinks(moduleContexts);
         if (((DirectedGraph)graph).addVertex(this.currentSingletonInfo) >= 0) {
            this.populateDependencyGraph((DirectedGraph)graph, moduleContexts);
         }

         try {
            ((DirectedGraph)graph).verify();
         } catch (CyclicDependencyException var5) {
            throw new ReferenceResolutionException(var5.getMessage());
         }
      }
   }

   private void resolveDependencyLinks(Collection moduleContexts) throws ReferenceResolutionException {
      if (this.dependencyMap == null) {
         this.dependencyMap = new HashMap();
         Iterator var2 = this.ejbLinks.iterator();

         while(var2.hasNext()) {
            String ejbLink = (String)var2.next();
            ClientDrivenBeanInfo cdbi = EnvUtils.findInfoByEjbLink(moduleContexts, this.applicationId, this.moduleId, this.moduleURI, (String)null, ejbLink);
            if (!(cdbi instanceof SingletonSessionBeanInfo)) {
               Loggable l = EJBLogger.logIllegalDependencyOfSingletonSessionBeanLoggable(this.currentSingletonInfo.getDisplayName(), ejbLink);
               throw new ReferenceResolutionException(l.getMessage());
            }

            this.dependencyMap.put(ejbLink, (SingletonSessionBeanInfo)cdbi);
         }
      }

   }

   private void populateDependencyGraph(DirectedGraph graph, Collection moduleContexts) throws ReferenceResolutionException {
      if (null != this.ejbLinks && !this.ejbLinks.isEmpty()) {
         this.resolveDependencyLinks(moduleContexts);
         Iterator var3 = this.dependencyMap.keySet().iterator();

         while(var3.hasNext()) {
            String ejbLink = (String)var3.next();
            SingletonSessionBeanInfo dependencySingletonInfo = (SingletonSessionBeanInfo)this.dependencyMap.get(ejbLink);
            int vid = graph.addVertex(dependencySingletonInfo);
            graph.addEdge(dependencySingletonInfo, this.currentSingletonInfo);
            if (vid >= 0) {
               SingletonDependencyResolver dResolver = dependencySingletonInfo.getSingletonDependencyResolver();
               if (dResolver != null) {
                  dResolver.populateDependencyGraph(graph, moduleContexts);
               }
            }
         }

      }
   }

   private int getMaxNodes(Collection moduleContexts) {
      int count = 0;
      Iterator var3 = moduleContexts.iterator();

      while(true) {
         DeploymentInfo di;
         do {
            if (!var3.hasNext()) {
               return count;
            }

            ModuleContext moduleContext = (ModuleContext)var3.next();
            di = EnvUtils.getDeploymentInfo(moduleContext);
         } while(di == null);

         Iterator var6 = di.getSessionBeanInfos().iterator();

         while(var6.hasNext()) {
            SessionBeanInfo bi = (SessionBeanInfo)var6.next();
            if (bi.isSingleton()) {
               ++count;
            }
         }
      }
   }

   public static final class SingletonDependencyInfo {
      private final List dependencies;
      private final List dependents;

      SingletonDependencyInfo(List dependencies, List dependents) {
         this.dependencies = dependencies;
         this.dependents = dependents;
      }

      public List getDependencies() {
         return this.dependencies;
      }

      public List getDependents() {
         return this.dependents;
      }
   }
}
