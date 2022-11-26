package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.DynamicIntroductionAdvice;
import com.bea.core.repackaged.springframework.aop.IntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.IntroductionInfo;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.support.DefaultIntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.target.EmptyTargetSource;
import com.bea.core.repackaged.springframework.aop.target.SingletonTargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdvisedSupport extends ProxyConfig implements Advised {
   private static final long serialVersionUID = 2651364800145442165L;
   public static final TargetSource EMPTY_TARGET_SOURCE;
   TargetSource targetSource;
   private boolean preFiltered;
   AdvisorChainFactory advisorChainFactory;
   private transient Map methodCache;
   private List interfaces;
   private List advisors;
   private Advisor[] advisorArray;

   public AdvisedSupport() {
      this.targetSource = EMPTY_TARGET_SOURCE;
      this.preFiltered = false;
      this.advisorChainFactory = new DefaultAdvisorChainFactory();
      this.interfaces = new ArrayList();
      this.advisors = new ArrayList();
      this.advisorArray = new Advisor[0];
      this.methodCache = new ConcurrentHashMap(32);
   }

   public AdvisedSupport(Class... interfaces) {
      this();
      this.setInterfaces(interfaces);
   }

   public void setTarget(Object target) {
      this.setTargetSource(new SingletonTargetSource(target));
   }

   public void setTargetSource(@Nullable TargetSource targetSource) {
      this.targetSource = targetSource != null ? targetSource : EMPTY_TARGET_SOURCE;
   }

   public TargetSource getTargetSource() {
      return this.targetSource;
   }

   public void setTargetClass(@Nullable Class targetClass) {
      this.targetSource = EmptyTargetSource.forClass(targetClass);
   }

   @Nullable
   public Class getTargetClass() {
      return this.targetSource.getTargetClass();
   }

   public void setPreFiltered(boolean preFiltered) {
      this.preFiltered = preFiltered;
   }

   public boolean isPreFiltered() {
      return this.preFiltered;
   }

   public void setAdvisorChainFactory(AdvisorChainFactory advisorChainFactory) {
      Assert.notNull(advisorChainFactory, (String)"AdvisorChainFactory must not be null");
      this.advisorChainFactory = advisorChainFactory;
   }

   public AdvisorChainFactory getAdvisorChainFactory() {
      return this.advisorChainFactory;
   }

   public void setInterfaces(Class... interfaces) {
      Assert.notNull(interfaces, (String)"Interfaces must not be null");
      this.interfaces.clear();
      Class[] var2 = interfaces;
      int var3 = interfaces.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class ifc = var2[var4];
         this.addInterface(ifc);
      }

   }

   public void addInterface(Class intf) {
      Assert.notNull(intf, (String)"Interface must not be null");
      if (!intf.isInterface()) {
         throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
      } else {
         if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
            this.adviceChanged();
         }

      }
   }

   public boolean removeInterface(Class intf) {
      return this.interfaces.remove(intf);
   }

   public Class[] getProxiedInterfaces() {
      return ClassUtils.toClassArray(this.interfaces);
   }

   public boolean isInterfaceProxied(Class intf) {
      Iterator var2 = this.interfaces.iterator();

      Class proxyIntf;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         proxyIntf = (Class)var2.next();
      } while(!intf.isAssignableFrom(proxyIntf));

      return true;
   }

   public final Advisor[] getAdvisors() {
      return this.advisorArray;
   }

   public void addAdvisor(Advisor advisor) {
      int pos = this.advisors.size();
      this.addAdvisor(pos, advisor);
   }

   public void addAdvisor(int pos, Advisor advisor) throws AopConfigException {
      if (advisor instanceof IntroductionAdvisor) {
         this.validateIntroductionAdvisor((IntroductionAdvisor)advisor);
      }

      this.addAdvisorInternal(pos, advisor);
   }

   public boolean removeAdvisor(Advisor advisor) {
      int index = this.indexOf(advisor);
      if (index == -1) {
         return false;
      } else {
         this.removeAdvisor(index);
         return true;
      }
   }

   public void removeAdvisor(int index) throws AopConfigException {
      if (this.isFrozen()) {
         throw new AopConfigException("Cannot remove Advisor: Configuration is frozen.");
      } else if (index >= 0 && index <= this.advisors.size() - 1) {
         Advisor advisor = (Advisor)this.advisors.get(index);
         if (advisor instanceof IntroductionAdvisor) {
            IntroductionAdvisor ia = (IntroductionAdvisor)advisor;

            for(int j = 0; j < ia.getInterfaces().length; ++j) {
               this.removeInterface(ia.getInterfaces()[j]);
            }
         }

         this.advisors.remove(index);
         this.updateAdvisorArray();
         this.adviceChanged();
      } else {
         throw new AopConfigException("Advisor index " + index + " is out of bounds: This configuration only has " + this.advisors.size() + " advisors.");
      }
   }

   public int indexOf(Advisor advisor) {
      Assert.notNull(advisor, (String)"Advisor must not be null");
      return this.advisors.indexOf(advisor);
   }

   public boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException {
      Assert.notNull(a, (String)"Advisor a must not be null");
      Assert.notNull(b, (String)"Advisor b must not be null");
      int index = this.indexOf(a);
      if (index == -1) {
         return false;
      } else {
         this.removeAdvisor(index);
         this.addAdvisor(index, b);
         return true;
      }
   }

   public void addAdvisors(Advisor... advisors) {
      this.addAdvisors((Collection)Arrays.asList(advisors));
   }

   public void addAdvisors(Collection advisors) {
      if (this.isFrozen()) {
         throw new AopConfigException("Cannot add advisor: Configuration is frozen.");
      } else {
         if (!CollectionUtils.isEmpty(advisors)) {
            Iterator var2 = advisors.iterator();

            while(var2.hasNext()) {
               Advisor advisor = (Advisor)var2.next();
               if (advisor instanceof IntroductionAdvisor) {
                  this.validateIntroductionAdvisor((IntroductionAdvisor)advisor);
               }

               Assert.notNull(advisor, (String)"Advisor must not be null");
               this.advisors.add(advisor);
            }

            this.updateAdvisorArray();
            this.adviceChanged();
         }

      }
   }

   private void validateIntroductionAdvisor(IntroductionAdvisor advisor) {
      advisor.validateInterfaces();
      Class[] ifcs = advisor.getInterfaces();
      Class[] var3 = ifcs;
      int var4 = ifcs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class ifc = var3[var5];
         this.addInterface(ifc);
      }

   }

   private void addAdvisorInternal(int pos, Advisor advisor) throws AopConfigException {
      Assert.notNull(advisor, (String)"Advisor must not be null");
      if (this.isFrozen()) {
         throw new AopConfigException("Cannot add advisor: Configuration is frozen.");
      } else if (pos > this.advisors.size()) {
         throw new IllegalArgumentException("Illegal position " + pos + " in advisor list with size " + this.advisors.size());
      } else {
         this.advisors.add(pos, advisor);
         this.updateAdvisorArray();
         this.adviceChanged();
      }
   }

   protected final void updateAdvisorArray() {
      this.advisorArray = (Advisor[])this.advisors.toArray(new Advisor[0]);
   }

   protected final List getAdvisorsInternal() {
      return this.advisors;
   }

   public void addAdvice(Advice advice) throws AopConfigException {
      int pos = this.advisors.size();
      this.addAdvice(pos, advice);
   }

   public void addAdvice(int pos, Advice advice) throws AopConfigException {
      Assert.notNull(advice, (String)"Advice must not be null");
      if (advice instanceof IntroductionInfo) {
         this.addAdvisor(pos, new DefaultIntroductionAdvisor(advice, (IntroductionInfo)advice));
      } else {
         if (advice instanceof DynamicIntroductionAdvice) {
            throw new AopConfigException("DynamicIntroductionAdvice may only be added as part of IntroductionAdvisor");
         }

         this.addAdvisor(pos, new DefaultPointcutAdvisor(advice));
      }

   }

   public boolean removeAdvice(Advice advice) throws AopConfigException {
      int index = this.indexOf(advice);
      if (index == -1) {
         return false;
      } else {
         this.removeAdvisor(index);
         return true;
      }
   }

   public int indexOf(Advice advice) {
      Assert.notNull(advice, (String)"Advice must not be null");

      for(int i = 0; i < this.advisors.size(); ++i) {
         Advisor advisor = (Advisor)this.advisors.get(i);
         if (advisor.getAdvice() == advice) {
            return i;
         }
      }

      return -1;
   }

   public boolean adviceIncluded(@Nullable Advice advice) {
      if (advice != null) {
         Iterator var2 = this.advisors.iterator();

         while(var2.hasNext()) {
            Advisor advisor = (Advisor)var2.next();
            if (advisor.getAdvice() == advice) {
               return true;
            }
         }
      }

      return false;
   }

   public int countAdvicesOfType(@Nullable Class adviceClass) {
      int count = 0;
      if (adviceClass != null) {
         Iterator var3 = this.advisors.iterator();

         while(var3.hasNext()) {
            Advisor advisor = (Advisor)var3.next();
            if (adviceClass.isInstance(advisor.getAdvice())) {
               ++count;
            }
         }
      }

      return count;
   }

   public List getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class targetClass) {
      MethodCacheKey cacheKey = new MethodCacheKey(method);
      List cached = (List)this.methodCache.get(cacheKey);
      if (cached == null) {
         cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
         this.methodCache.put(cacheKey, cached);
      }

      return cached;
   }

   protected void adviceChanged() {
      this.methodCache.clear();
   }

   protected void copyConfigurationFrom(AdvisedSupport other) {
      this.copyConfigurationFrom(other, other.targetSource, new ArrayList(other.advisors));
   }

   protected void copyConfigurationFrom(AdvisedSupport other, TargetSource targetSource, List advisors) {
      this.copyFrom(other);
      this.targetSource = targetSource;
      this.advisorChainFactory = other.advisorChainFactory;
      this.interfaces = new ArrayList(other.interfaces);
      Iterator var4 = advisors.iterator();

      while(var4.hasNext()) {
         Advisor advisor = (Advisor)var4.next();
         if (advisor instanceof IntroductionAdvisor) {
            this.validateIntroductionAdvisor((IntroductionAdvisor)advisor);
         }

         Assert.notNull(advisor, (String)"Advisor must not be null");
         this.advisors.add(advisor);
      }

      this.updateAdvisorArray();
      this.adviceChanged();
   }

   AdvisedSupport getConfigurationOnlyCopy() {
      AdvisedSupport copy = new AdvisedSupport();
      copy.copyFrom(this);
      copy.targetSource = EmptyTargetSource.forClass(this.getTargetClass(), this.getTargetSource().isStatic());
      copy.advisorChainFactory = this.advisorChainFactory;
      copy.interfaces = this.interfaces;
      copy.advisors = this.advisors;
      copy.updateAdvisorArray();
      return copy;
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.methodCache = new ConcurrentHashMap(32);
   }

   public String toProxyConfigString() {
      return this.toString();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getName());
      sb.append(": ").append(this.interfaces.size()).append(" interfaces ");
      sb.append(ClassUtils.classNamesToString((Collection)this.interfaces)).append("; ");
      sb.append(this.advisors.size()).append(" advisors ");
      sb.append(this.advisors).append("; ");
      sb.append("targetSource [").append(this.targetSource).append("]; ");
      sb.append(super.toString());
      return sb.toString();
   }

   static {
      EMPTY_TARGET_SOURCE = EmptyTargetSource.INSTANCE;
   }

   private static final class MethodCacheKey implements Comparable {
      private final Method method;
      private final int hashCode;

      public MethodCacheKey(Method method) {
         this.method = method;
         this.hashCode = method.hashCode();
      }

      public boolean equals(Object other) {
         return this == other || other instanceof MethodCacheKey && this.method == ((MethodCacheKey)other).method;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public String toString() {
         return this.method.toString();
      }

      public int compareTo(MethodCacheKey other) {
         int result = this.method.getName().compareTo(other.method.getName());
         if (result == 0) {
            result = this.method.toString().compareTo(other.method.toString());
         }

         return result;
      }
   }
}
