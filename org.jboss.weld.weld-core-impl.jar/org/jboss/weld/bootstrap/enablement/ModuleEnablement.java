package org.jboss.weld.bootstrap.enablement;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.Sets;

public class ModuleEnablement {
   public static final ModuleEnablement EMPTY_ENABLEMENT = new ModuleEnablement(Collections.emptyList(), Collections.emptyList(), Collections.emptyMap(), Collections.emptySet(), Collections.emptySet());
   private final List interceptors;
   private final List decorators;
   private final Map interceptorMap;
   private final Map decoratorMap;
   private final Map globalAlternatives;
   private final Set localAlternativeClasses;
   private final Set localAlternativeStereotypes;
   private final Comparator decoratorComparator;
   private final Comparator interceptorComparator;

   public ModuleEnablement(List interceptors, List decorators, Map globalAlternatives, Set localAlternativeClasses, Set localAlternativeStereotypes) {
      this.interceptors = interceptors;
      this.decorators = decorators;
      this.interceptorMap = createLookupMap(interceptors);
      this.decoratorMap = createLookupMap(decorators);
      this.decoratorComparator = new EnablementComparator(this.decoratorMap);
      this.interceptorComparator = new EnablementComparator(this.interceptorMap);
      this.globalAlternatives = globalAlternatives;
      this.localAlternativeClasses = localAlternativeClasses;
      this.localAlternativeStereotypes = localAlternativeStereotypes;
   }

   private static Map createLookupMap(List list) {
      if (list.isEmpty()) {
         return Collections.emptyMap();
      } else {
         Map result = new HashMap();

         for(int i = 0; i < list.size(); ++i) {
            result.put(list.get(i), i);
         }

         return ImmutableMap.copyOf(result);
      }
   }

   public boolean isInterceptorEnabled(Class javaClass) {
      return this.interceptorMap.containsKey(javaClass);
   }

   public boolean isDecoratorEnabled(Class javaClass) {
      return this.decoratorMap.containsKey(javaClass);
   }

   public List getInterceptors() {
      return this.interceptors;
   }

   public List getDecorators() {
      return this.decorators;
   }

   public Comparator getDecoratorComparator() {
      return this.decoratorComparator;
   }

   public Comparator getInterceptorComparator() {
      return this.interceptorComparator;
   }

   public Integer getAlternativePriority(Class javaClass) {
      return (Integer)this.globalAlternatives.get(javaClass);
   }

   public boolean isEnabledAlternativeClass(Class alternativeClass) {
      return this.globalAlternatives.containsKey(alternativeClass) || this.localAlternativeClasses.contains(alternativeClass);
   }

   public boolean isEnabledAlternativeStereotype(Class alternativeClass) {
      return this.globalAlternatives.containsKey(alternativeClass) || this.localAlternativeStereotypes.contains(alternativeClass);
   }

   public Set getAlternativeClasses() {
      return this.localAlternativeClasses;
   }

   public Set getAlternativeStereotypes() {
      return this.localAlternativeStereotypes;
   }

   public Set getGlobalAlternatives() {
      return this.globalAlternatives.keySet();
   }

   public Set getAllAlternatives() {
      return Sets.union(Sets.union(this.localAlternativeClasses, this.localAlternativeStereotypes), this.getGlobalAlternatives());
   }

   public String toString() {
      return "ModuleEnablement [interceptors=" + this.interceptors + ", decorators=" + this.decorators + ", alternatives=" + this.getAllAlternatives() + "]";
   }

   private static class EnablementComparator implements Comparator, Serializable {
      private static final long serialVersionUID = -4757462262711016985L;
      private final Map enabledClasses;

      public EnablementComparator(Map enabledClasses) {
         this.enabledClasses = enabledClasses;
      }

      public int compare(Bean o1, Bean o2) {
         int p1 = (Integer)this.enabledClasses.get(o1.getBeanClass());
         int p2 = (Integer)this.enabledClasses.get(o2.getBeanClass());
         return p1 - p2;
      }
   }
}
