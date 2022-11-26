package weblogic.connector.configuration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.configuration.meta.ConfigPropertyProcessor;
import weblogic.connector.configuration.meta.ConnectorBeanNavigator;
import weblogic.connector.configuration.meta.JCAAnnotationProcessor;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.exception.RAException;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class VJAnnotationScanner {
   private ConnectorBean connectorBean;
   private JCAAnnotationProcessor processor;
   private boolean processed;
   private ConfigPropertyProcessor configPropertyProcessor;

   public VJAnnotationScanner(ConnectorBean connectorBean) {
      this.connectorBean = connectorBean;
      ConnectorBeanNavigator connectorBeanNavigator = new ConnectorBeanNavigator(connectorBean);
      this.configPropertyProcessor = new ConfigPropertyProcessor(connectorBeanNavigator);
      this.processor = new JCAAnnotationProcessor(connectorBeanNavigator);
   }

   public void process(RarArchive rar, ClassLoader cl) throws RAException {
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Start to process annotations for adapter " + rar + "...");
      }

      Set classSet = this.getAnnotatedClass(rar, cl, this.processor.getIdentityAnnotations());
      this.process(classSet);
   }

   public void process(Set classSet) throws RAException {
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Start to process annotations for classes set: " + classSet + "...");
      }

      Iterator classes = classSet.iterator();
      if (classes.hasNext()) {
         this.processed = true;
         this.processor.process(classes);
      }

      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("End process annotations for classes set: result:" + this.getResult());
      }

   }

   public ConnectorAPContext getResult() {
      return this.processed ? this.processor.getAPContext() : ConnectorAPContext.NullContext;
   }

   public boolean needToProcessAnnotation() {
      String version = this.connectorBean.getVersion();
      if (version.compareTo("1.6") < 0) {
         return false;
      } else {
         DescriptorBean descriptorBean = (DescriptorBean)this.connectorBean;
         return !descriptorBean.isSet("MetadataComplete") || !this.connectorBean.isMetadataComplete();
      }
   }

   private Set getAnnotatedClass(RarArchive rar, ClassLoader cl, Class[] annotationClazz) {
      Set classes = new HashSet();
      ClassInfoFinder cif = rar.getAnnotatedClassFinder();
      String[] annotationNames = new String[annotationClazz.length];

      for(int i = 0; i < annotationClazz.length; ++i) {
         annotationNames[i] = annotationClazz[i].getName();
      }

      Set classNames = cif.getClassNamesWithAnnotations(annotationNames);
      Iterator var8 = classNames.iterator();

      while(var8.hasNext()) {
         String className = (String)var8.next();

         try {
            Class clazz = cl.loadClass(className);
            classes.add(clazz);
         } catch (Throwable var11) {
            ConnectorLogger.logIgnoredErrorWhenProcessAnnotation("class " + className, "loading using ClassLoader [" + cl + "] for adapter " + rar, var11 == null ? "" : var11.toString(), var11);
         }
      }

      return classes;
   }

   public void processConfigProperty(GenericClassLoader cl) throws RAException {
      this.configPropertyProcessor.readConfigProperties(cl);
   }
}
