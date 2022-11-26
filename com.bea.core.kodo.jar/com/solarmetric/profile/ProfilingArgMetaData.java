package com.solarmetric.profile;

public class ProfilingArgMetaData implements ProfilingFormattableMetaData {
   String _type;
   boolean _isDescriber;
   boolean _isAgentProvider;
   boolean _isEnv;
   String _name;
   int _index;
   ProfilingFormatterMetaData _formatter = null;

   public ProfilingArgMetaData(String type, boolean isDescriber, String name, int index, boolean isAgentProvider, boolean isEnv) {
      this._type = type;
      this._isDescriber = isDescriber;
      this._name = name;
      this._index = index;
      this._isAgentProvider = isAgentProvider;
      this._isEnv = isEnv;
   }

   public String getName() {
      return this._name;
   }

   public String getType() {
      return this._type;
   }

   public boolean getIsDescriber() {
      return this._isDescriber;
   }

   public boolean getIsAgentProvider() {
      return this._isAgentProvider;
   }

   public boolean getIsEnv() {
      return this._isEnv;
   }

   public int getIndex() {
      return this._index;
   }

   public ProfilingFormatterMetaData setFormatter(String className, String methodName) {
      this._formatter = new ProfilingFormatterMetaData(className, methodName);
      return this._formatter;
   }

   public ProfilingFormatterMetaData getFormatter() {
      return this._formatter;
   }
}
