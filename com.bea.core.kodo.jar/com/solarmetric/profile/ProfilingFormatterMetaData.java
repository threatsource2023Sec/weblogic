package com.solarmetric.profile;

public class ProfilingFormatterMetaData {
   String _className;
   String _methodName;

   public ProfilingFormatterMetaData(String className, String methodName) {
      this._className = className;
      this._methodName = methodName;
   }

   public String getClassName() {
      return this._className;
   }

   public String getMethodName() {
      return this._methodName;
   }
}
