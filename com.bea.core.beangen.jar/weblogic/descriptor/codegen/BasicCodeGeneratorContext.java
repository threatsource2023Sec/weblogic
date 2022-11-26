package weblogic.descriptor.codegen;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import java.util.HashMap;
import weblogic.apache.org.apache.velocity.context.AbstractContext;

public class BasicCodeGeneratorContext extends AbstractContext {
   protected JClass jClass;
   protected CodeGenerator generator;
   protected Production production;
   protected final String suffix;
   protected HashMap context = new HashMap();
   private static HashMap missingAnnotations = new HashMap();

   public BasicCodeGeneratorContext(JClass jClass, CodeGenerator generator) {
      this.jClass = jClass;
      this.generator = generator;
      this.suffix = generator.getOpts().getSuffix();
      this.production = generator.getProduction(jClass);
      this.context.put("ArrayUtils", new ArrayUtils());
   }

   public Production getProduction(JClass jClass) {
      return this.generator.getProduction(jClass);
   }

   public Object internalGet(String key) {
      if (key == null) {
         throw new IllegalArgumentException("no support for null keys");
      } else {
         Production var10001;
         switch (key.length()) {
            case 4:
               var10001 = this.production;
               if (key.equals("date")) {
                  return this.production.date();
               }
            case 5:
            case 8:
            default:
               break;
            case 6:
               var10001 = this.production;
               if (key.equals("target")) {
                  return this.production.getAnnotatableTarget();
               }

               var10001 = this.production;
               if (key.equals("author")) {
                  return this.production.author();
               }

               var10001 = this.production;
               if (key.equals("source")) {
                  return this.production.getAnnotatableSource();
               }

               if (key.equals("suffix")) {
                  return this.production.getSuffix();
               }
               break;
            case 7:
               var10001 = this.production;
               if (key.equals("version")) {
                  return this.production.version();
               }
               break;
            case 9:
               var10001 = this.production;
               if (key.equals("copyright")) {
                  return this.production.copyright();
               }
         }

         JAnnotation annotation = this.jClass.getAnnotation(key);
         if (annotation != null) {
            JAnnotationValue value = annotation.getValue("value");
            if (value != null) {
               return value.asString();
            }
         }

         Object o = this.context.get(key);
         if (o != null) {
            return o;
         } else {
            if (missingAnnotations.get(key) == null) {
               System.out.println("no annotation found for key [" + key + "]");
               missingAnnotations.put(key, key);
            }

            return null;
         }
      }
   }

   public Object internalPut(String key, Object value) {
      return this.context.put(key, value);
   }

   public boolean internalContainsKey(Object key) {
      return this.context.containsKey(key);
   }

   public Object[] internalGetKeys() {
      return this.context.keySet().toArray();
   }

   public Object internalRemove(Object key) {
      return this.context.remove(key);
   }

   public class ArrayUtils {
      public int getLength(Object arrayObject) {
         return ((Object[])((Object[])arrayObject)).length;
      }

      public Object getEntry(Object arrayObject, int i) {
         return ((Object[])((Object[])arrayObject))[i];
      }
   }
}
