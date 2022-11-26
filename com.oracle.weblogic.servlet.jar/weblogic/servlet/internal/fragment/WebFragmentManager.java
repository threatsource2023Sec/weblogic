package weblogic.servlet.internal.fragment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.j2ee.descriptor.AbsoluteOrderingBean;
import weblogic.j2ee.descriptor.NameOrOrderingOthersBean;
import weblogic.j2ee.descriptor.OrderingBean;
import weblogic.j2ee.descriptor.OrderingOrderingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebFragmentBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.WebAppHelper;

public class WebFragmentManager {
   private static final String SYMBOL_FOR_OTHERS = "__OTHERS__";
   private static final String SYMBOL_FOR_NONAME = "__NONAME__";
   private int seedForGeneratedNames;
   private Collection loaderSet;
   private List sortedList;
   private List orderedLibs;
   private WebAppBean workingWebAppBean;
   private WebFragmentMergeDispatcher dispatcher;

   public WebFragmentManager(WebAppHelper war, WebAppBean webBean) throws CycleFoundInGraphException, XMLStreamException, DeploymentException, IOException {
      this(war.getWebFragments(), webBean);
   }

   WebFragmentManager(Collection loaderSet, WebAppBean webBean) throws CycleFoundInGraphException, DeploymentException, XMLStreamException, IOException {
      this.seedForGeneratedNames = 100;
      this.loaderSet = null;
      this.sortedList = null;
      this.orderedLibs = null;
      this.workingWebAppBean = null;
      this.loaderSet = (Collection)(loaderSet == null ? Collections.emptySet() : loaderSet);
      this.workingWebAppBean = webBean;
      this.dispatcher = new WebFragmentMergeDispatcher(this.workingWebAppBean);
      this.sortedList = this.sortFragments();
   }

   public static boolean isClassFromWebFragments(URL classSourceUrl, WebFragmentLoader... webFragments) {
      if (classSourceUrl == null) {
         return false;
      } else if (webFragments != null && webFragments.length != 0) {
         WebFragmentLoader[] var2 = webFragments;
         int var3 = webFragments.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WebFragmentLoader webFragment = var2[var4];
            if (webFragment.isFrom(classSourceUrl)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean isClassFromExcludedWebFragments(URL classSourceUrl) {
      Collection excludes = this.getExcludedWebFragments();
      if (excludes != null && !excludes.isEmpty()) {
         WebFragmentLoader[] webFragments = (WebFragmentLoader[])excludes.toArray(new WebFragmentLoader[0]);
         return isClassFromWebFragments(classSourceUrl, webFragments);
      } else {
         return false;
      }
   }

   public boolean isClassFromWebFragments(URL classSourceUrl) {
      WebFragmentLoader[] webFragments = (WebFragmentLoader[])this.loaderSet.toArray(new WebFragmentLoader[0]);
      return isClassFromWebFragments(classSourceUrl, webFragments);
   }

   public List getSortedFragments() {
      return this.sortedList;
   }

   public boolean shouldProcessAnnotation(URL classSourceUrl) {
      Iterator var2 = this.sortedList.iterator();

      WebFragmentLoader webFragment;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         webFragment = (WebFragmentLoader)var2.next();
         if (webFragment.shouldProcessAnnotation(classSourceUrl)) {
            return true;
         }
      } while(!isClassFromWebFragments(classSourceUrl, webFragment));

      return false;
   }

   private List sortFragments() throws IOException, XMLStreamException, CycleFoundInGraphException, DeploymentException {
      assert this.workingWebAppBean != null;

      AbsoluteOrderingBean[] absoluteOrderings = this.workingWebAppBean.getAbsoluteOrderings();
      return absoluteOrderings != null && absoluteOrderings.length != 0 ? this.sortAbsolutely(absoluteOrderings) : this.sortRelatively();
   }

   private List sortAbsolutely(AbsoluteOrderingBean[] absoluteOrderings) throws IOException, XMLStreamException {
      AbsoluteOrderingBean ordering = absoluteOrderings[0];
      Map namedLoaders = new HashMap();
      List nonameLoaders = new ArrayList();
      Iterator var5 = this.loaderSet.iterator();

      while(var5.hasNext()) {
         WebFragmentLoader loader = (WebFragmentLoader)var5.next();
         String[] names = loader.getWebFragmentBean().getNames();
         if (names.length > 0) {
            namedLoaders.put(names[0], loader);
         } else {
            nonameLoaders.add(loader);
         }
      }

      List result = new LinkedList();
      int indexOfOthers = -1;
      NameOrOrderingOthersBean[] var15 = ordering.getNameOrOrderingOther();
      int var8 = var15.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         NameOrOrderingOthersBean nameOrOthers = var15[var9];
         if (nameOrOthers.getOther() == null) {
            String name = nameOrOthers.getName();
            WebFragmentLoader loader = (WebFragmentLoader)namedLoaders.remove(name);
            if (loader != null) {
               result.add(loader);
            }
         } else {
            indexOfOthers = result.size();
         }
      }

      if (indexOfOthers >= 0) {
         result.addAll(indexOfOthers, nonameLoaders);
         result.addAll(indexOfOthers, namedLoaders.values());
      }

      return result;
   }

   private List sortRelatively() throws IOException, XMLStreamException, CycleFoundInGraphException, DeploymentException {
      Map loadersMap = new HashMap();
      List edgeList = new ArrayList();
      this.prepareForTopologicalSort(loadersMap, edgeList);
      return this.doTopologicalSort(loadersMap, edgeList);
   }

   private String generateName() {
      return "__NONAME__" + this.seedForGeneratedNames++;
   }

   private void prepareForTopologicalSort(Map loadersMap, List edgeList) throws IOException, XMLStreamException, DeploymentException {
      Iterator var3 = this.loaderSet.iterator();

      while(true) {
         String name;
         OrderingOrderingBean afterBean;
         String[] var14;
         int var15;
         int var16;
         String afterName;
         do {
            OrderingBean[] orderings;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               WebFragmentLoader loader = (WebFragmentLoader)var3.next();
               WebFragmentBean wfb = loader.getWebFragmentBean();
               String[] names = wfb.getNames();
               name = null;
               if (names != null && names.length != 0) {
                  name = names[0];
               } else {
                  name = this.generateName();
               }

               if (loadersMap.containsKey(name)) {
                  Loggable l = HTTPLogger.logFragmentNamesConflictLoggable(name);
                  l.log();
                  throw new DeploymentException(l.getMessage());
               }

               loadersMap.put(name, loader);
               orderings = wfb.getOrderings();
            } while(orderings.length == 0);

            OrderingBean ordering = orderings[0];
            OrderingOrderingBean beforeBean = ordering.getBefore();
            afterBean = ordering.getAfter();
            boolean hasBeforeOthers = beforeBean != null && beforeBean.getOthers() != null;
            boolean hasAfterOthers = afterBean != null && afterBean.getOthers() != null;
            if (hasBeforeOthers && hasAfterOthers) {
               Loggable l = HTTPLogger.logFragmentNamesConflictLoggable(name);
               l.log();
               throw new DeploymentException(l.getMessage());
            }

            if (hasBeforeOthers) {
               edgeList.add(new TopologicalSortingGraph.Edge(name, "__OTHERS__"));
            } else if (hasAfterOthers) {
               edgeList.add(new TopologicalSortingGraph.Edge("__OTHERS__", name));
            }

            if (beforeBean != null) {
               var14 = beforeBean.getNames();
               var15 = var14.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  afterName = var14[var16];
                  edgeList.add(new TopologicalSortingGraph.Edge(name, afterName));
               }
            }
         } while(afterBean == null);

         var14 = afterBean.getNames();
         var15 = var14.length;

         for(var16 = 0; var16 < var15; ++var16) {
            afterName = var14[var16];
            edgeList.add(new TopologicalSortingGraph.Edge(afterName, name));
         }
      }
   }

   private List doTopologicalSort(Map loadersMap, List edgeList) throws CycleFoundInGraphException {
      Set nodeSet = new HashSet(loadersMap.keySet());
      nodeSet.add("__OTHERS__");
      Set graphSet = TopologicalSortingGraph.createGraphs(nodeSet, edgeList);
      LinkedList sortedListOfOthers = new LinkedList();
      TopologicalSortingGraph mainGraph = null;
      Iterator var7 = graphSet.iterator();

      while(true) {
         while(var7.hasNext()) {
            TopologicalSortingGraph graph = (TopologicalSortingGraph)var7.next();
            if (graph.contains("__OTHERS__")) {
               mainGraph = graph;
            } else {
               Iterator var9 = graph.sort().iterator();

               while(var9.hasNext()) {
                  String name = (String)var9.next();
                  sortedListOfOthers.add(loadersMap.get(name));
               }
            }
         }

         LinkedList sortedList = new LinkedList();
         Iterator var12 = mainGraph.sort().iterator();

         while(var12.hasNext()) {
            String name = (String)var12.next();
            if ("__OTHERS__".equals(name)) {
               sortedList.addAll(sortedListOfOthers);
            } else {
               sortedList.addLast(loadersMap.get(name));
            }
         }

         return sortedList;
      }
   }

   public void mergeFragment(WebFragmentLoader webFragment) throws MergeException {
      try {
         this.dispatcher.merge(webFragment.getWebFragmentBean());
      } catch (IOException var3) {
         throw new MergeException(var3.getMessage(), var3);
      } catch (XMLStreamException var4) {
         throw new MergeException(var4.getMessage(), var4);
      }
   }

   private Collection getExcludedWebFragments() {
      if (this.loaderSet != null && !this.loaderSet.isEmpty()) {
         if (this.sortedList != null && !this.sortedList.isEmpty()) {
            List excludes = new ArrayList(this.loaderSet);
            excludes.removeAll(this.sortedList);
            return excludes;
         } else {
            return Collections.emptyList();
         }
      } else {
         return Collections.emptySet();
      }
   }

   public List getOrderedLibs() {
      if (this.orderedLibs == null) {
         this.orderedLibs = new ArrayList();
         Iterator var1 = this.sortedList.iterator();

         while(var1.hasNext()) {
            WebFragmentLoader loader = (WebFragmentLoader)var1.next();
            this.orderedLibs.add(loader.getJarFileName());
         }
      }

      return this.orderedLibs;
   }
}
