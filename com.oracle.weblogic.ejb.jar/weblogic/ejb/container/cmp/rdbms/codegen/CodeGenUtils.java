package weblogic.ejb.container.cmp.rdbms.codegen;

import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.utils.MethodUtils;

public final class CodeGenUtils {
   public static final String VAR_PREFIX = "__WL_";
   public static final String SUPER_PREFIX = "__WL_super_";
   public static final String INTERNAL_PREFIX = "__WL_internal_";

   private CodeGenUtils() {
   }

   static Class getSnapshotClass(RDBMSBean bean, Class varClass) {
      return !bean.isValidSQLType(varClass) ? byte[].class : varClass;
   }

   public static String snapshotNameForVar(String varName) {
      return "__WL_snapshot_" + varName;
   }

   static String fieldVarName(String fieldName) {
      return "__WL_" + fieldName + "_field_";
   }

   static String fieldRemovedVarName(String fieldName) {
      return "__WL_removed_" + fieldName + "_field_";
   }

   public static String cacheRelationshipMethodName(String cmrFieldName) {
      return "__WL_cache" + fieldVarName(cmrFieldName);
   }

   public static String getCMRFieldFinderMethodName(RDBMSBean rbean, String cmrFieldName) {
      String methodName = rbean.finderMethodName(rbean.getCMPBeanDescriptor(), cmrFieldName);
      return rbean.isManyToManyRelation(cmrFieldName) ? convertToEjbSelectInternalName(methodName, new Class[0], (String)null) : MethodUtils.convertToFinderName(methodName);
   }

   static String convertToEjbSelectInternalName(String name, Class[] paramTypes, String beanName) {
      StringBuilder sb = new StringBuilder();
      sb.append("__WL_").append(signature2identifier(name, paramTypes));
      if (beanName != null) {
         sb.append(beanName);
      }

      return sb.toString();
   }

   private static String signature2identifier(String name, Class[] paramTypes) {
      if (name == null) {
         return "";
      } else {
         if (paramTypes == null) {
            paramTypes = new Class[0];
         }

         String methodSig = DDUtils.getMethodSignature(name, paramTypes);
         methodSig = methodSig.replace('.', '_');
         methodSig = methodSig.replace(',', '_');
         methodSig = methodSig.replace('[', 'A');
         methodSig = methodSig.replace('(', '_');
         methodSig = methodSig.replace(')', '_');
         return methodSig;
      }
   }
}
