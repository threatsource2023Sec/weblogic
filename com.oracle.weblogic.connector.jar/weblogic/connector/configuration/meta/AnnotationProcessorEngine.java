package weblogic.connector.configuration.meta;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAException;

public class AnnotationProcessorEngine {
   private Map typeProcessorRegistry = new HashMap();

   public void registerProcessor(TypeAnnotationProcessor processor) {
      AnnotationProcessorDescription desc = (AnnotationProcessorDescription)processor.getClass().getAnnotation(AnnotationProcessorDescription.class);
      this.typeProcessorRegistry.put(desc.targetAnnotation(), processor);
   }

   public void process(Iterator classes) throws RAException {
      label24:
      while(true) {
         if (classes.hasNext()) {
            Class clz = (Class)classes.next();
            Annotation[] annotations = clz.getAnnotations();
            Annotation[] var4 = annotations;
            int var5 = annotations.length;
            int var6 = 0;

            while(true) {
               if (var6 >= var5) {
                  continue label24;
               }

               Annotation annotation = var4[var6];
               TypeAnnotationProcessor processor = (TypeAnnotationProcessor)this.typeProcessorRegistry.get(annotation.annotationType());
               if (processor != null) {
                  if (Debug.isDeploymentEnabled()) {
                     Debug.deployment("Processing [" + clz + "]: annotation: [" + annotation.toString() + "]");
                  }

                  processor.processClass(clz, annotation);
               }

               ++var6;
            }
         }

         return;
      }
   }

   public Class[] getIdentityAnnotations() {
      return (Class[])this.typeProcessorRegistry.keySet().toArray(new Class[0]);
   }
}
