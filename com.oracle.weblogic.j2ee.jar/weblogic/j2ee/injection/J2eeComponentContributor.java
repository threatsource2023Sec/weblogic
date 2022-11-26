package weblogic.j2ee.injection;

import com.oracle.pitchfork.interfaces.MetadataParseException;
import com.oracle.pitchfork.interfaces.PitchforkUtils;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.extensions.ExtensionManager;
import weblogic.j2ee.extensions.InjectionExtension;

public abstract class J2eeComponentContributor extends BaseComponentContributor {
   public J2eeComponentContributor(PitchforkContext pitchforkContext) {
      super(pitchforkContext);
   }

   public void contribute(EnricherI enricher, String componentName, String componentClassName, J2eeEnvironmentBean environmentGroupBean) {
      Jsr250MetadataI jsr250Metadata = this.buildJsr250MetaData(enricher, componentName, componentClassName);
      this.buildInjectionMetadata(jsr250Metadata, environmentGroupBean);
      this.contribute(jsr250Metadata, environmentGroupBean);
      this.addLifecycleMethods(jsr250Metadata, environmentGroupBean);
      enricher.attach(jsr250Metadata, false);
      this.updateMetadataUsingExtensions(jsr250Metadata);
   }

   private void updateMetadataUsingExtensions(Jsr250MetadataI metadata) {
      ArrayList newInjectionList = new ArrayList();
      Iterator iter = metadata.getInjections().iterator();

      while(true) {
         InjectionI injection;
         String name;
         String type;
         InjectionExtension extension;
         do {
            if (!iter.hasNext()) {
               if (newInjectionList.size() > 0) {
                  metadata.getInjections().addAll(newInjectionList);
               }

               return;
            }

            injection = (InjectionI)iter.next();
            name = injection.getInfo().getName();
            type = injection.getInfo().getType().getName();
            extension = ExtensionManager.instance.getFirstMatchingExtension(type, name);
         } while(extension == null);

         if (name != null && name.length() != 0) {
            name = extension.getName(type, name);
         } else {
            name = extension.getName(type);
         }

         InjectionI newInjection = null;
         PitchforkUtils utils = this.pitchforkContext.getPitchforkUtils();
         Member m = injection.getMember();
         if (m instanceof Field) {
            newInjection = utils.createFieldInjection((Field)m, name, injection.getInfo().getType(), injection.isOptional());
         } else if (m instanceof Method) {
            newInjection = utils.createMethodInjection((Method)m, name, injection.getInfo().getType(), injection.isOptional());
         }

         if (newInjection != null) {
            newInjectionList.add(newInjection);
            iter.remove();
         }
      }
   }

   protected abstract void contribute(Jsr250MetadataI var1, J2eeEnvironmentBean var2);

   protected void addLifecycleMethods(Jsr250MetadataI metadata, J2eeEnvironmentBean envBean) {
      this.addLifecycleMethods(metadata, envBean.getPostConstructs(), LifecycleEvent.POST_CONSTRUCT);
      this.addLifecycleMethods(metadata, envBean.getPreDestroys(), LifecycleEvent.PRE_DESTROY);
      if (envBean instanceof InterceptorBean) {
         this.addLifecycleMethods(metadata, ((InterceptorBean)envBean).getAroundConstructs(), LifecycleEvent.AROUND_CONSTRUCT);
      }

   }

   private void addLifecycleMethods(Jsr250MetadataI metadata, LifecycleCallbackBean[] lcbs, LifecycleEvent le) {
      List bubbledUp = bubbleUpSuperClasses(lcbs, metadata.getComponentClass());
      Iterator var5 = bubbledUp.iterator();

      while(var5.hasNext()) {
         LifecycleCallbackBean lifecycleCallbackBean = (LifecycleCallbackBean)var5.next();
         this.addLifecycleMethods(metadata, lifecycleCallbackBean, le);
      }

   }

   protected void addLifecycleMethods(Jsr250MetadataI jsr250, LifecycleCallbackBean lcb, LifecycleEvent le) {
      Class clazz = this.loadClass(lcb.getLifecycleCallbackClass(), this.classLoader);
      if (clazz.isAssignableFrom(jsr250.getComponentClass())) {
         Method lifecycleMethod;
         if (jsr250 instanceof InterceptorMetadataI) {
            lifecycleMethod = this.getDeclaredMethod(clazz, lcb.getLifecycleCallbackMethod(), InvocationContext.class);
            ((InterceptorMetadataI)jsr250).registerLifecycleEventListenerMethod(le, lifecycleMethod);
         } else {
            lifecycleMethod = this.getDeclaredMethod(clazz, lcb.getLifecycleCallbackMethod());
            jsr250.registerLifecycleEventCallbackMethod(le, lifecycleMethod);
         }

         this.debug("Adding lifecycleMethod=" + lifecycleMethod.getName() + " for annotation " + le);
      }
   }

   protected void buildInjectionMetadata(Jsr250MetadataI metadata, J2eeEnvironmentBean envBean) {
      super.buildInjectionMetadata(metadata, envBean);
      PersistenceContextRefBean[] var3 = envBean.getPersistenceContextRefs();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         PersistenceContextRefBean pcRef = var3[var5];
         this.addPersistenceContextRef(pcRef, metadata);
      }

      EjbLocalRefBean[] var7 = envBean.getEjbLocalRefs();
      var4 = var7.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EjbLocalRefBean ejbLocalRef = var7[var5];
         this.addLocalEjbRef(ejbLocalRef, metadata);
      }

   }

   protected Set getInjectableTargetClasses(J2eeEnvironmentBean envBean) {
      Set set = new HashSet();
      set.addAll(super.getInjectableTargetClasses((J2eeClientEnvironmentBean)envBean));
      LifecycleCallbackBean[] var3 = envBean.getPostConstructs();
      int var4 = var3.length;

      int var5;
      LifecycleCallbackBean preDestroyBean;
      for(var5 = 0; var5 < var4; ++var5) {
         preDestroyBean = var3[var5];
         set.add(preDestroyBean.getLifecycleCallbackClass());
      }

      var3 = envBean.getPreDestroys();
      var4 = var3.length;

      for(var5 = 0; var5 < var4; ++var5) {
         preDestroyBean = var3[var5];
         set.add(preDestroyBean.getLifecycleCallbackClass());
      }

      PersistenceContextRefBean[] var7 = envBean.getPersistenceContextRefs();
      var4 = var7.length;

      for(var5 = 0; var5 < var4; ++var5) {
         PersistenceContextRefBean pcRef = var7[var5];
         set.addAll(this.getInjectableTargetClasses(pcRef.getInjectionTargets()));
      }

      EjbLocalRefBean[] var8 = envBean.getEjbLocalRefs();
      var4 = var8.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EjbLocalRefBean ejbLocalRef = var8[var5];
         set.addAll(this.getInjectableTargetClasses(ejbLocalRef.getInjectionTargets()));
      }

      return set;
   }

   protected void addPersistenceContextRef(PersistenceContextRefBean pcRef, Jsr250MetadataI jsr250Metadata) {
      BaseComponentContributor.ParseResults pr = this.parseInjectionTarget(jsr250Metadata, pcRef.getInjectionTargets(), EntityManager.class.getName(), pcRef.getPersistenceContextRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected void addLocalEjbRef(EjbLocalRefBean ejbLocalRef, Jsr250MetadataI jsr250Metadata) {
      String refClassName = ejbLocalRef.getLocalHome();
      if (refClassName == null) {
         refClassName = ejbLocalRef.getLocal();
      }

      BaseComponentContributor.ParseResults pr = this.parseInjectionTarget(jsr250Metadata, ejbLocalRef.getInjectionTargets(), refClassName, ejbLocalRef.getEjbRefName());
      this.insertOrOverwriteInjectionStrategy(jsr250Metadata.getInjections(), pr.getInjections());
   }

   protected Method getDeclaredMethod(Class clazz, String name, Class... paramTypes) {
      try {
         return clazz.getDeclaredMethod(name, paramTypes);
      } catch (NoSuchMethodException var5) {
         throw new MetadataParseException("Cannot get the method " + name + " on class " + clazz.getName(), var5);
      }
   }

   private static Set getSuperClasses(Class clazz) {
      HashSet retVal = new HashSet();

      for(Class supes = clazz.getSuperclass(); supes != null; supes = supes.getSuperclass()) {
         retVal.add(supes.getName());
      }

      return retVal;
   }

   private static ListWithSupers findMyParent(LifecycleCallbackBean bean, List list, Class componentClass) {
      String amIASuperclassOfSomeone = bean.getLifecycleCallbackClass();
      if (amIASuperclassOfSomeone == null) {
         amIASuperclassOfSomeone = componentClass.getName();
      }

      Iterator var4 = list.iterator();

      ListWithSupers item;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         item = (ListWithSupers)var4.next();
      } while(!item.isASubclass(amIASuperclassOfSomeone));

      return item;
   }

   private static List bubbleUpSuperClasses(LifecycleCallbackBean[] beans, Class componentClass) {
      ClassLoader componentClassLoader = componentClass.getClassLoader();
      Set componentClassSupers = getSuperClasses(componentClass);
      LinkedList listOfElements = new LinkedList();
      LifecycleCallbackBean[] var5 = beans;
      int var6 = beans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         LifecycleCallbackBean bean = var5[var7];
         ListWithSupers descriptorItem;
         if (bean.getBeanSource() == 0) {
            descriptorItem = new ListWithSupers(bean);
            listOfElements.add(descriptorItem);
            if (bean.getLifecycleCallbackClass() == null) {
               descriptorItem.setSubClasses(componentClassSupers);
            } else {
               Class lifecycleClass = null;

               try {
                  lifecycleClass = Class.forName(bean.getLifecycleCallbackClass(), false, componentClassLoader);
               } catch (Throwable var12) {
               }

               if (lifecycleClass != null) {
                  descriptorItem.setSubClasses(getSuperClasses(lifecycleClass));
               }
            }
         } else {
            descriptorItem = findMyParent(bean, listOfElements, componentClass);
            if (descriptorItem == null) {
               listOfElements.add(new ListWithSupers(bean));
            } else {
               descriptorItem.addSuperChild(bean);
            }
         }
      }

      LinkedList retVal = new LinkedList();
      Iterator var14 = listOfElements.iterator();

      while(var14.hasNext()) {
         ListWithSupers lws = (ListWithSupers)var14.next();
         Iterator var16 = lws.getChildren().iterator();

         while(var16.hasNext()) {
            LifecycleCallbackBean child = (LifecycleCallbackBean)var16.next();
            retVal.add(child);
         }

         retVal.add(lws.getMe());
      }

      return retVal;
   }

   private static final class ListWithSupers {
      private final LifecycleCallbackBean deploymentDescriptorParent;
      private final LinkedList superClassChildren = new LinkedList();
      private Set subClasses = new HashSet();

      ListWithSupers(LifecycleCallbackBean parent) {
         this.deploymentDescriptorParent = parent;
      }

      LifecycleCallbackBean getMe() {
         return this.deploymentDescriptorParent;
      }

      void addSuperChild(LifecycleCallbackBean child) {
         this.superClassChildren.add(child);
      }

      LinkedList getChildren() {
         return this.superClassChildren;
      }

      void setSubClasses(Set param) {
         this.subClasses = param;
      }

      boolean isASubclass(String clazzName) {
         return this.subClasses.contains(clazzName);
      }
   }
}
