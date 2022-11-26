package weblogic.ejb.container.cmp.rdbms.codegen;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.utils.Getopt2;

public abstract class BaseCodeGenerator extends CMPCodeGenerator {
   protected static final DebugLogger debugLogger;

   protected BaseCodeGenerator(Getopt2 opts) {
      super(opts);
   }

   public String varPrefix() {
      return "__WL_";
   }

   protected String lookupConvert(Class clazz) {
      if (clazz.equals(Boolean.class)) {
         return ".booleanValue()";
      } else if (clazz.equals(Integer.class)) {
         return ".intValue()";
      } else if (clazz.equals(Float.class)) {
         return ".floatValue()";
      } else if (clazz.equals(Double.class)) {
         return ".doubleValue()";
      } else if (clazz.equals(Long.class)) {
         return ".longValue()";
      } else if (clazz.equals(Short.class)) {
         return ".shortValue()";
      } else if (clazz.equals(Character.class)) {
         return ".charValue()";
      } else {
         return clazz.equals(Byte.class) ? ".byteValue()" : "";
      }
   }

   protected String perhapsConvert(Class toClass, Class fromClass, String fromOperand) {
      String result = null;
      if (toClass.equals(fromClass)) {
         result = fromOperand;
      } else if (toClass.isPrimitive()) {
         assert ClassUtils.getObjectClass(toClass).equals(fromClass);

         result = fromOperand + this.lookupConvert(fromClass);
      } else {
         assert fromClass.isPrimitive();

         assert toClass.equals(ClassUtils.getObjectClass(fromClass));

         result = "new " + this.javaCodeForType(ClassUtils.getObjectClass(fromClass)) + "(" + fromOperand + ")";
      }

      return result;
   }

   protected String perhapsConvertPrimitive(Class fromClass, String fromOperand) {
      return fromClass.isPrimitive() ? "new " + this.javaCodeForType(ClassUtils.getObjectClass(fromClass)) + "(" + fromOperand + ")" : fromOperand;
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
