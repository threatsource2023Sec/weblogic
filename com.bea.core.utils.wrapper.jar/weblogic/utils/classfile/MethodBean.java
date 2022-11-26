package weblogic.utils.classfile;

import java.util.List;
import weblogic.utils.classfile.cp.CPClass;

public class MethodBean extends BaseClassBean {
   private String name;
   private String returnType;
   private String[] params;
   private String[] exceptions;

   public MethodBean() {
      this.setModifiers(1);
      this.name = "";
      this.returnType = "void";
      this.exceptions = this.params = new String[0];
   }

   public MethodBean(String name, String returnType, String[] params) {
      this();
      this.name = name;
      this.returnType = returnType;
      this.params = params;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      this.name = s;
   }

   public String getReturnType() {
      return this.returnType;
   }

   public void setReturnType(String s) {
      this.returnType = s;
   }

   public String[] getParams() {
      return this.params;
   }

   public void setParams(String[] s) {
      this.params = s;
   }

   public String[] getExceptions() {
      return this.exceptions;
   }

   public void setExceptions(String[] s) {
      this.exceptions = s;
   }

   public boolean isSynchronized() {
      return this.hasBit(1);
   }

   public boolean isNative() {
      return this.hasBit(256);
   }

   public boolean isStatic() {
      return this.hasBit(8);
   }

   public boolean isConstructor() {
      return "<init>".equals(this.getName());
   }

   MethodBean(MethodInfo mi) {
      this.name = mi.getName();
      this.modifiers = mi.getAccessFlags();
      CPClass[] clazzes = mi.getExceptions();
      if (clazzes == null) {
         clazzes = new CPClass[0];
      }

      this.exceptions = new String[clazzes.length];

      for(int i = 0; i < clazzes.length; ++i) {
         this.exceptions[i] = clazzes[i].fullName();
      }

      Descriptor d = new Descriptor(mi.getDescriptor());
      this.returnType = d.getReturnType();
      List l = d.getArgumentTypes();
      this.params = new String[l.size()];
      l.toArray(this.params);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(ClassFileBean.shortClass(this.getReturnType()));
      sb.append(' ');
      sb.append(this.getName());
      sb.append('(');

      for(int i = 0; i < this.params.length; ++i) {
         sb.append(ClassFileBean.shortClass(this.params[i]));
         if (i != this.params.length - 1) {
            sb.append(',');
         }
      }

      sb.append(')');
      return sb.toString();
   }

   public int hashCode() {
      int retVal = this.name.hashCode();
      retVal ^= this.returnType.hashCode();
      String[] var2 = this.params;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String param = var2[var4];
         retVal ^= param.hashCode();
      }

      return retVal;
   }

   public boolean equals(Object o) {
      if (!(o instanceof MethodBean)) {
         return false;
      } else {
         MethodBean m = (MethodBean)o;
         if (!this.name.equals(m.getName())) {
            return false;
         } else if (!this.returnType.equals(m.returnType)) {
            return false;
         } else if (this.params.length != m.params.length) {
            return false;
         } else {
            for(int i = 0; i < this.params.length; ++i) {
               if (!this.params[i].equals(m.params[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static void p(String s) {
      System.err.println("[MethodBean]: " + s);
   }
}
