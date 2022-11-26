package weblogic.servlet.ejb2jsp;

import java.util.List;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;

public class SourceMethodInfo {
   private String methodName;
   private String retType;
   private String[][] params;

   private String[][] doParams(List types, List names) {
      String[][] ret = new String[][]{new String[types.size()], null};
      types.toArray(ret[0]);
      ret[1] = new String[names.size()];
      names.toArray(ret[1]);
      return ret;
   }

   public String toString() {
      return this.retType + ' ' + this.methodName + '(' + this.getArgListString() + ')';
   }

   public String getArgListString() {
      StringBuffer sb = new StringBuffer();
      int sz = this.params[0].length;

      for(int i = 0; i < sz; ++i) {
         sb.append(this.params[0][i]);
         sb.append(' ');
         sb.append(this.params[1][i]);
         if (i + 1 != sz) {
            sb.append(',');
         }
      }

      return sb.toString();
   }

   public String[][] getParams() {
      return this.params;
   }

   public SourceMethodInfo(String methodName, String returnType, List argTypes, List argNames) {
      this.methodName = methodName;
      this.retType = returnType;
      if (argTypes.size() != argNames.size()) {
         throw new IllegalArgumentException("type list size " + argTypes.size() + "!=name list size " + argNames.size());
      } else {
         this.params = this.doParams(argTypes, argNames);
      }
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String getReturnType() {
      return this.retType;
   }

   public static boolean equalClassNames(String x, String y) {
      int ind = x.lastIndexOf(46);
      if (ind >= 0) {
         x = x.substring(ind + 1);
      }

      ind = y.lastIndexOf(46);
      if (ind >= 0) {
         y = y.substring(ind + 1);
      }

      return x.equals(y);
   }

   static void p(String s) {
      System.err.println("[SMI]: " + s);
   }

   public boolean equalsMethod(EJBMethodDescriptor md) {
      if (!this.methodName.equals(md.getName())) {
         return false;
      } else {
         String rtype = this.getReturnType();
         String mRType = md.getReturnType();
         if (Utils.isVoid(rtype)) {
            if (!Utils.isVoid(mRType)) {
               return false;
            }
         } else if (!equalClassNames(mRType, rtype)) {
            return false;
         }

         String[] args = this.getParams()[0];
         MethodParamDescriptor[] mArgs = md.getParams();
         if (mArgs != null && mArgs.length != 0) {
            if (mArgs.length != args.length) {
               return false;
            } else {
               for(int i = 0; i < mArgs.length; ++i) {
                  if (!equalClassNames(args[i], mArgs[i].getType())) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return args == null || args.length == 0;
         }
      }
   }
}
