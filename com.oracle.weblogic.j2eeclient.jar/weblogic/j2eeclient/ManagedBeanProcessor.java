package weblogic.j2eeclient;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.ManagedBean;
import javax.interceptor.Interceptor;
import javax.naming.Context;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ModuleException;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.ManagedBeanBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;
import weblogic.managedbean.EnvironmentManager;
import weblogic.managedbean.ManagedBeanAnnotationProcessor;
import weblogic.managedbean.ManagedBeanUtils;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.GenericClassLoader;

public class ManagedBeanProcessor {
   private final GenericClassLoader gcl;
   private final String moduleName;
   private final String appClientName;
   private final Context appNamingRootCtx;
   private final Context moduleNamingRootCtx;
   private ManagedBeansBean managedBeansBean;
   private EnvironmentManager environmentManager;
   private Set beanClasses;
   private Set interceptorClasses;

   public ManagedBeanProcessor(String appClientName, String moduleName, Context appNamingRootCtx, Context moduleNamingRootCtx, GenericClassLoader gcl) {
      this.gcl = gcl;
      this.moduleName = moduleName;
      this.appClientName = appClientName;
      this.appNamingRootCtx = appNamingRootCtx;
      this.moduleNamingRootCtx = moduleNamingRootCtx;
   }

   public void process() throws AnnotationProcessingException, ClassNotFoundException, ModuleException {
      Class[] annotationClasses = new Class[]{ManagedBean.class, Interceptor.class};
      String[] annotationClassNames = new String[]{ManagedBean.class.getName(), Interceptor.class.getName()};
      Map map = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(this.gcl.getClassFinder()).setComponentAnnotations(annotationClasses)).getAnnotatedClasses(annotationClassNames);
      if (!((Set)map.get(ManagedBean.class.getName())).isEmpty()) {
         this.beanClasses = new HashSet();
         Iterator var4 = ((Set)map.get(ManagedBean.class.getName())).iterator();

         String className;
         while(var4.hasNext()) {
            className = (String)var4.next();
            this.beanClasses.add(this.gcl.loadClass(className));
         }

         this.interceptorClasses = new HashSet();
         var4 = ((Set)map.get(Interceptor.class.getName())).iterator();

         while(var4.hasNext()) {
            className = (String)var4.next();
            this.interceptorClasses.add(this.gcl.loadClass(className));
         }

         this.managedBeansBean = (ManagedBeansBean)(new DescriptorManager()).createDescriptorRoot(ManagedBeansBean.class).getRootBean();
         ErrorCollectionException errors = new ErrorCollectionException();
         Set duplicates = new HashSet();
         Iterator var6 = this.beanClasses.iterator();

         while(var6.hasNext()) {
            Class beanClass = (Class)var6.next();
            ManagedBeanUtils.validateManagedBeanClass(beanClass, errors);
            String beanName = ManagedBeanUtils.calculateManagedBeanName(beanClass);
            ManagedBeanUtils.validateManagedBeanName(beanName, duplicates, errors);
            ManagedBeanBean bean = this.managedBeansBean.createManagedBean();
            bean.setManagedBeanClass(beanClass.getName());
            bean.setManagedBeanName(beanName);
         }

         if (!errors.isEmpty()) {
            throw new ModuleException(errors);
         } else {
            try {
               ManagedBeanAnnotationProcessor mbap = new ManagedBeanAnnotationProcessor(this.gcl);
               mbap.processAnnotations(this.interceptorClasses, this.beanClasses, this.managedBeansBean);
            } catch (ClassNotFoundException var10) {
               throw new ModuleException(var10);
            }

            this.environmentManager = new EnvironmentManager(this.managedBeansBean, this.appClientName, this.moduleName, this.appNamingRootCtx, this.moduleNamingRootCtx, this.gcl);
            this.environmentManager.activate();
         }
      }
   }

   public void cleanup() {
      if (this.environmentManager != null) {
         this.environmentManager.destroy();
      }

   }
}
