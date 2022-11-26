package com.solarmetric.profile;

public class ProfilingPropertyMetaData implements ProfilingFormattableMetaData {
   String _name;
   String _profileName;
   ProfilingFormatterMetaData _formatter = null;

   public ProfilingPropertyMetaData(String name, String profileName) {
      this._name = name;
      this._profileName = profileName;
   }

   public String getName() {
      return this._name;
   }

   public String getProfileName() {
      return this._profileName;
   }

   public ProfilingFormatterMetaData setFormatter(String className, String methodName) {
      this._formatter = new ProfilingFormatterMetaData(className, methodName);
      return this._formatter;
   }

   public ProfilingFormatterMetaData getFormatter() {
      return this._formatter;
   }
}
