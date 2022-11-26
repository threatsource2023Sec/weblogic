package com.bea.core.repackaged.aspectj.weaver.model;

import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.OrPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ReferencePointcut;

public class AsmRelationshipUtils {
   public static final String DECLARE_PRECEDENCE = "precedence";
   public static final String DECLARE_SOFT = "soft";
   public static final String DECLARE_PARENTS = "parents";
   public static final String DECLARE_WARNING = "warning";
   public static final String DECLARE_ERROR = "error";
   public static final String DECLARE_UNKNONWN = "<unknown declare>";
   public static final String POINTCUT_ABSTRACT = "<abstract pointcut>";
   public static final String POINTCUT_ANONYMOUS = "<anonymous pointcut>";
   public static final String DOUBLE_DOTS = "..";
   public static final int MAX_MESSAGE_LENGTH = 18;
   public static final String DEC_LABEL = "declare";

   public static String genDeclareMessage(String message) {
      int length = message.length();
      return length < 18 ? message : message.substring(0, 17) + "..";
   }

   public static String genPointcutDetails(Pointcut pcd) {
      StringBuffer details = new StringBuffer();
      if (pcd instanceof ReferencePointcut) {
         ReferencePointcut rp = (ReferencePointcut)pcd;
         details.append(rp.name).append("..");
      } else if (pcd instanceof AndPointcut) {
         AndPointcut ap = (AndPointcut)pcd;
         if (ap.getLeft() instanceof ReferencePointcut) {
            details.append(ap.getLeft().toString()).append("..");
         } else {
            details.append("<anonymous pointcut>").append("..");
         }
      } else if (pcd instanceof OrPointcut) {
         OrPointcut op = (OrPointcut)pcd;
         if (op.getLeft() instanceof ReferencePointcut) {
            details.append(op.getLeft().toString()).append("..");
         } else {
            details.append("<anonymous pointcut>").append("..");
         }
      } else {
         details.append("<anonymous pointcut>");
      }

      return details.toString();
   }
}
