package weblogic.diagnostics.instrumentation.engine.base;

import java.util.List;

public class MethodInfo {
   private int methodAccess;
   private String className;
   private String methodName;
   private String methodDesc;
   private int maxStack;
   private int maxLocals;
   private List annotations;
   private List caughtExceptions;

   public List getCaughtExceptions() {
      return this.caughtExceptions;
   }

   public void setCaughtExceptions(List caughtExceptions) {
      this.caughtExceptions = caughtExceptions;
   }

   public List getAnnotations() {
      return this.annotations;
   }

   public void setAnnotations(List annotations) {
      this.annotations = annotations;
   }

   public int getMaxLocals() {
      return this.maxLocals;
   }

   public void setMaxLocals(int maxLocals) {
      this.maxLocals = maxLocals;
   }

   public int getMaxStack() {
      return this.maxStack;
   }

   public void setMaxStack(int maxStack) {
      this.maxStack = maxStack;
   }

   public int getMethodAccess() {
      return this.methodAccess;
   }

   public void setMethodAccess(int methodAccess) {
      this.methodAccess = methodAccess;
   }

   public String getMethodDesc() {
      return this.methodDesc;
   }

   public void setMethodDesc(String methodDesc) {
      this.methodDesc = methodDesc;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String className) {
      this.className = className;
   }
}
