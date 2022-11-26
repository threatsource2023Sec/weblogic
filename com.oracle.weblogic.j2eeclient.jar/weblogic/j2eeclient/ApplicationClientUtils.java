package weblogic.j2eeclient;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.AnnotationProcessor;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.GenericClassLoader;

public class ApplicationClientUtils {
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.debug.DebugJ2EEClient");

   public static ApplicationClientBean getAnnotationProcessedDescriptor(GenericClassLoader gcl, ApplicationClientDescriptor acd, Class mainClass) throws Exception {
      ApplicationClientBean stdDD = acd.getApplicationClientBean();
      if (!stdDD.isMetadataComplete()) {
         if (DEBUG) {
            Debug.say(" The Descriptor is not full. Processing annotations");
         }

         if (DEBUG) {
            Debug.say("Before annotations the client bean is ");
            ((DescriptorBean)stdDD).getDescriptor().toXML(System.out);
         }

         AnnotationProcessor ap = getAnnotationProcessor();
         Thread currentThread = Thread.currentThread();
         ClassLoader currClassLoader = currentThread.getContextClassLoader();

         try {
            currentThread.setContextClassLoader(gcl);
            ap.processJ2eeAnnotations(mainClass, stdDD, true);
         } finally {
            currentThread.setContextClassLoader(currClassLoader);
         }

         ap.validate(gcl, (DescriptorBean)stdDD, true);
         if (DEBUG) {
            Debug.say("After annotations the client bean is ");
            ((DescriptorBean)stdDD).getDescriptor().toXML(System.out);
         }
      }

      return stdDD;
   }

   private static AnnotationProcessor getAnnotationProcessor() throws Exception {
      Class cl = Class.forName("weblogic.j2ee.dd.xml.BaseJ2eeAnnotationProcessor");
      return (AnnotationProcessor)cl.newInstance();
   }
}
