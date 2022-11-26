package weblogic.ejb.container.ejbc.bytecodegen;

class MethInfo {
   private String methodName;
   private Class[] args = null;
   private Class retType;
   private Class[] exs;
   private String mdName;

   MethInfo() {
      this.retType = Void.TYPE;
      this.exs = null;
   }

   String getMethodName() {
      return this.methodName;
   }

   String getMdName() {
      return this.mdName;
   }

   Class[] getArgs() {
      return this.args;
   }

   Class getRetType() {
      return this.retType;
   }

   Class[] getExs() {
      return this.exs;
   }

   static Creator of(String methodName, String mdName) {
      Creator mib = new Creator();
      mib.mInfo.methodName = methodName;
      mib.mInfo.mdName = mdName;
      return mib;
   }

   static class Creator {
      private final MethInfo mInfo;

      private Creator() {
         this.mInfo = new MethInfo();
      }

      Creator args(Class... args) {
         this.mInfo.args = args;
         return this;
      }

      Creator exceps(Class... exs) {
         this.mInfo.exs = exs;
         return this;
      }

      Creator returns(Class retType) {
         this.mInfo.retType = retType;
         return this;
      }

      MethInfo create() {
         return this.mInfo;
      }

      // $FF: synthetic method
      Creator(Object x0) {
         this();
      }
   }
}
