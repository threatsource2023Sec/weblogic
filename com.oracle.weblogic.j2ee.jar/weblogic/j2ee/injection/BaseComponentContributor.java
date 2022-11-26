package weblogic.j2ee.injection;

import com.oracle.pitchfork.interfaces.MetadataParseException;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.EnvironmentException;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.utils.reflect.ReflectUtils;

public abstract class BaseComponentContributor implements ComponentContributor {
   protected final PitchforkContext pitchforkContext;
   protected ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

   public BaseComponentContributor(PitchforkContext pitchforkContext) {
      this.pitchforkContext = pitchforkContext;
   }

   public Jsr250MetadataI buildJsr250MetaData(EnricherI enricher, String componentName, String componentClassName) {
      Class componentClass = null;
      if (componentClassName != null) {
         componentClass = this.loadClass(componentClassName, this.classLoader);
      }

      return this.newJsr250Metadata(componentName, componentClass, enricher.getDeploymentUnitMetadata());
   }

   protected void buildInjectionMetadata(Jsr250MetadataI jsr250Metadata, J2eeClientEnvironmentBean envBean) {
      this.debug("Convert environmentGroupBean to jsr250Metadata for : " + envBean);
      ResourceEnvRefBean[] var3 = envBean.getResourceEnvRefs();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         ResourceEnvRefBean resourceEnvRef = var3[var5];
         this.addResourceEnvRef(resourceEnvRef, jsr250Metadata);
      }

      EnvEntryBean[] var7 = envBean.getEnvEntries();
      var4 = var7.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EnvEntryBean envEntry = var7[var5];
         this.addEnvEntry(envEntry, jsr250Metadata);
      }

      EjbRefBean[] var8 = envBean.getEjbRefs();
      var4 = var8.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EjbRefBean ejbRef = var8[var5];
         this.addEjbRef(ejbRef, jsr250Metadata);
      }

      ServiceRefBean[] var9 = envBean.getServiceRefs();
      var4 = var9.length;

      for(var5 = 0; var5 < var4; ++var5) {
         ServiceRefBean serviceRef = var9[var5];
         this.addServiceRef(serviceRef, jsr250Metadata);
      }

      ResourceRefBean[] var10 = envBean.getResourceRefs();
      var4 = var10.length;

      for(var5 = 0; var5 < var4; ++var5) {
         ResourceRefBean resourceRef = var10[var5];
         this.addResourceRef(resourceRef, jsr250Metadata);
      }

      MessageDestinationRefBean[] var11 = envBean.getMessageDestinationRefs();
      var4 = var11.length;

      for(var5 = 0; var5 < var4; ++var5) {
         MessageDestinationRefBean mdRef = var11[var5];
         this.addMessageDestinationRef(mdRef, jsr250Metadata);
      }

      PersistenceUnitRefBean[] var12 = envBean.getPersistenceUnitRefs();
      var4 = var12.length;

      for(var5 = 0; var5 < var4; ++var5) {
         PersistenceUnitRefBean puRef = var12[var5];
         this.addPersistenceUnitRef(puRef, jsr250Metadata);
      }

   }

   protected Set getInjectableTargetClasses(J2eeClientEnvironmentBean envBean) {
      Set set = new HashSet();
      ResourceEnvRefBean[] var3 = envBean.getResourceEnvRefs();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         ResourceEnvRefBean resourceEnvRef = var3[var5];
         set.addAll(this.getInjectableTargetClasses(resourceEnvRef.getInjectionTargets()));
      }

      EnvEntryBean[] var7 = envBean.getEnvEntries();
      var4 = var7.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EnvEntryBean envEntry = var7[var5];
         set.addAll(this.getInjectableTargetClasses(envEntry.getInjectionTargets()));
      }

      EjbRefBean[] var8 = envBean.getEjbRefs();
      var4 = var8.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EjbRefBean ejbRef = var8[var5];
         set.addAll(this.getInjectableTargetClasses(ejbRef.getInjectionTargets()));
      }

      ServiceRefBean[] var9 = envBean.getServiceRefs();
      var4 = var9.length;

      for(var5 = 0; var5 < var4; ++var5) {
         ServiceRefBean serviceRef = var9[var5];
         set.addAll(this.getInjectableTargetClasses(serviceRef.getInjectionTargets()));
      }

      ResourceRefBean[] var10 = envBean.getResourceRefs();
      var4 = var10.length;

      for(var5 = 0; var5 < var4; ++var5) {
         ResourceRefBean resourceRef = var10[var5];
         set.addAll(this.getInjectableTargetClasses(resourceRef.getInjectionTargets()));
      }

      MessageDestinationRefBean[] var11 = envBean.getMessageDestinationRefs();
      var4 = var11.length;

      for(var5 = 0; var5 < var4; ++var5) {
         MessageDestinationRefBean mdRef = var11[var5];
         set.addAll(this.getInjectableTargetClasses(mdRef.getInjectionTargets()));
      }

      PersistenceUnitRefBean[] var12 = envBean.getPersistenceUnitRefs();
      var4 = var12.length;

      for(var5 = 0; var5 < var4; ++var5) {
         PersistenceUnitRefBean puRef = var12[var5];
         set.addAll(this.getInjectableTargetClasses(puRef.getInjectionTargets()));
      }

      return set;
   }

   protected Set getInjectableTargetClasses(InjectionTargetBean[] beans) {
      Set set = new HashSet();
      InjectionTargetBean[] var3 = beans;
      int var4 = beans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         InjectionTargetBean injectionTargetBean = var3[var5];
         set.add(injectionTargetBean.getInjectionTargetClass());
      }

      return set;
   }

   protected boolean createInjectionWhenNoLookupValueFound(EnvEntryBean envEntry, Jsr250MetadataI meta) {
      return false;
   }

   protected void addEnvEntry(EnvEntryBean envEntry, Jsr250MetadataI jsr250Metadata) {
      Object valueObject = null;
      if (envEntry.getEnvEntryValue() == null && envEntry.getLookupName() == null) {
         try {
            valueObject = InitialContext.doLookup(envEntry.getEnvEntryName());
         } catch (NamingException var8) {
         }
      }

      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, envEntry.getInjectionTargets(), envEntry.getEnvEntryType(), envEntry.getEnvEntryName(), true);
      if (envEntry.getEnvEntryType() == null) {
         envEntry.setEnvEntryType(ReflectUtils.getJavaLangType(pr.getType()));
      }

      if (envEntry.getEnvEntryValue() != null) {
         try {
            valueObject = EnvUtils.getValue(envEntry);
         } catch (EnvironmentException var7) {
            throw new AssertionError(var7);
         }
      }

      if (valueObject != null) {
         Iterator var5 = pr.getInjections().iterator();

         while(var5.hasNext()) {
            InjectionI injection = (InjectionI)var5.next();
            injection.setValue(valueObject);
         }
      }

      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addEjbRef(EjbRefBean ejbRef, Jsr250MetadataI jsr250Metadata) {
      String refClassName = ejbRef.getHome();
      if (refClassName == null) {
         refClassName = ejbRef.getRemote();
      }

      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, ejbRef.getInjectionTargets(), refClassName, ejbRef.getEjbRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addLocalEjbRef(EjbLocalRefBean ejbLocalRef, Jsr250MetadataI jsr250Metadata) {
      String refClassName = ejbLocalRef.getLocalHome();
      if (refClassName == null) {
         refClassName = ejbLocalRef.getLocal();
      }

      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, ejbLocalRef.getInjectionTargets(), refClassName, ejbLocalRef.getEjbRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addServiceRef(ServiceRefBean serviceRef, Jsr250MetadataI jsr250Metadata) {
      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, serviceRef.getInjectionTargets(), serviceRef.getServiceRefType(), serviceRef.getServiceRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addResourceRef(ResourceRefBean resourceRef, Jsr250MetadataI jsr250Metadata) {
      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, resourceRef.getInjectionTargets(), resourceRef.getResType(), resourceRef.getResRefName());
      if (resourceRef.getResType() == null) {
         resourceRef.setResType(ReflectUtils.getJavaLangType(pr.getType()));
      }

      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addResourceEnvRef(ResourceEnvRefBean reRef, Jsr250MetadataI jsr250Metadata) {
      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, reRef.getInjectionTargets(), reRef.getResourceEnvRefType(), reRef.getResourceEnvRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addMessageDestinationRef(MessageDestinationRefBean mdRef, Jsr250MetadataI jsr250Metadata) {
      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, mdRef.getInjectionTargets(), mdRef.getMessageDestinationType(), mdRef.getMessageDestinationRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addPersistenceUnitRef(PersistenceUnitRefBean puRef, Jsr250MetadataI jsr250Metadata) {
      ParseResults pr = this.parseInjectionTarget(jsr250Metadata, puRef.getInjectionTargets(), EntityManagerFactory.class.getName(), puRef.getPersistenceUnitRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected ParseResults parseInjectionTarget(Jsr250MetadataI jsr250Metadata, InjectionTargetBean[] injectionTargetBeans, String type, String resourceLocation) {
      return this.parseInjectionTarget(jsr250Metadata, injectionTargetBeans, type, resourceLocation, false);
   }

   protected ParseResults parseInjectionTarget(Jsr250MetadataI jsr250Metadata, InjectionTargetBean[] injectionTargetBeans, String type, String resourceLocation, boolean isOptional) {
      List injections = new ArrayList();
      if (injectionTargetBeans.length == 0) {
         return new ParseResults(injections, type);
      } else {
         String foundType = null;
         Class injectedType = null;
         if (type != null) {
            injectedType = this.loadClass(type, this.classLoader);
         }

         InjectionTargetBean[] var9 = injectionTargetBeans;
         int var10 = injectionTargetBeans.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            InjectionTargetBean injectionTargetBean = var9[var11];
            String targetClassName = injectionTargetBean.getInjectionTargetClass();
            String injectionTargetName = injectionTargetBean.getInjectionTargetName();
            Class targetClass = this.loadClass(targetClassName, this.classLoader);
            Class componentClass = jsr250Metadata.getComponentClass();
            if (!targetClass.isAssignableFrom(componentClass)) {
               this.debug("Skipping injection for: targetClassName " + targetClassName + "; componentName " + jsr250Metadata.getComponentName());
            } else {
               foundType = this.findTargetMethodOrField(injections, targetClass, injectionTargetName, injectedType, resourceLocation, isOptional);
            }
         }

         if (foundType == null) {
            foundType = type;
         }

         return new ParseResults(injections, foundType);
      }
   }

   private String findTargetMethodOrField(List injections, Class targetClass, String injectionTargetName, Class injectedType, String resourceLocation, boolean isOptional) {
      AccessibleObject methodOrField = ReflectUtils.getMethodOrFieldForSetter(targetClass, injectionTargetName, injectedType);
      String retVal = null;
      Class foundInjectedType = ReflectUtils.getTypeOfSetter(methodOrField);
      if (injectedType == null) {
         retVal = foundInjectedType.getName();
      }

      InjectionI injection;
      if (methodOrField instanceof Method) {
         injection = this.pitchforkContext.getPitchforkUtils().createMethodInjection((Method)methodOrField, resourceLocation, foundInjectedType, isOptional);
         this.debug("Adding method injection " + injection + " for " + injectionTargetName);
         injections.add(injection);
      } else {
         injection = this.pitchforkContext.getPitchforkUtils().createFieldInjection((Field)methodOrField, resourceLocation, foundInjectedType, isOptional);
         this.debug("Adding field injection " + injection + " for " + injectionTargetName);
         injections.add(injection);
      }

      return retVal;
   }

   protected void insertOrOverwriteInjectionStrategy(List injections, List newInjections) {
      Iterator var3 = newInjections.iterator();

      while(var3.hasNext()) {
         InjectionI injection = (InjectionI)var3.next();
         Member member = injection.getMember();
         Class type = injection.getType();
         boolean skip = false;
         Iterator var8 = injections.iterator();

         while(var8.hasNext()) {
            InjectionI tmpInjection = (InjectionI)var8.next();
            Member tmpMember = tmpInjection.getMember();
            Class tmpType = tmpInjection.getType();
            if (tmpInjection.getName().equals(injection.getName()) && tmpMember.equals(member)) {
               injections.remove(tmpInjection);
               break;
            }

            if (tmpType.getName().equals(type.getName()) && tmpMember.getName().equals(member.getName())) {
               if (!Modifier.isPrivate(tmpMember.getModifiers()) && tmpMember.getDeclaringClass().isAssignableFrom(member.getDeclaringClass())) {
                  if (injection.isOptional() && tmpInjection.getValue() != null && injection.getValue() == null) {
                     skip = true;
                     break;
                  }

                  injections.remove(tmpInjection);
                  break;
               }

               if (!Modifier.isPrivate(member.getModifiers()) && member.getDeclaringClass().isAssignableFrom(tmpMember.getDeclaringClass())) {
                  skip = true;
                  break;
               }
            }
         }

         if (!skip) {
            injections.add(injection);
         }
      }

   }

   protected Class loadClass(String className, ClassLoader cl) {
      if (className.indexOf(46) == -1) {
         if ("int".equals(className)) {
            return Integer.TYPE;
         }

         if ("float".equals(className)) {
            return Float.TYPE;
         }

         if ("double".equals(className)) {
            return Double.TYPE;
         }

         if ("char".equals(className)) {
            return Character.TYPE;
         }

         if ("boolean".equals(className)) {
            return Boolean.TYPE;
         }

         if ("byte".equals(className)) {
            return Byte.TYPE;
         }

         if ("long".equals(className)) {
            return Long.TYPE;
         }

         if ("short".equals(className)) {
            return Short.TYPE;
         }
      }

      try {
         return cl.loadClass(className);
      } catch (ClassNotFoundException var4) {
         throw new MetadataParseException("Cannot find class:" + className, var4);
      }
   }

   protected Class forName(String className, ClassLoader classLoader) {
      try {
         return this.pitchforkContext.getPitchforkUtils().forName(className, classLoader);
      } catch (ClassNotFoundException var4) {
         throw new MetadataParseException("Cannot find class: " + className, var4);
      }
   }

   protected abstract void debug(String var1);

   protected static final class ParseResults {
      private final List injections;
      private final String type;

      protected ParseResults(List injections, String type) {
         this.injections = injections;
         this.type = type;
      }

      public List getInjections() {
         return this.injections;
      }

      public String getType() {
         return this.type;
      }
   }
}
