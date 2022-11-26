package com.solarmetric.profile;

import java.util.ArrayList;

public class ProfilingClassMetaData {
   public static final int SOURCE_PROVIDER = 0;
   public static final int SOURCE_FIELD = 1;
   public static final int SOURCE_ARG = 2;
   private Class _type;
   private ArrayList _methods = new ArrayList();
   private int _agentSource;
   private int _envSource;
   private String _agentSourceSymbol;
   private String _envSourceSymbol;

   public ProfilingClassMetaData(Class type) {
      this._type = type;
   }

   public Class getType() {
      return this._type;
   }

   public void add(ProfilingMethodMetaData method) {
      this._methods.add(method);
   }

   public int getAgentSource() {
      return this._agentSource;
   }

   public void setAgentSource(int source) {
      this._agentSource = source;
   }

   public String getAgentSourceSymbol() {
      return this._agentSourceSymbol;
   }

   public void setAgentSourceSymbol(String symbol) {
      this._agentSourceSymbol = symbol;
   }

   public int getEnvSource() {
      return this._envSource;
   }

   public void setEnvSource(int source) {
      this._envSource = source;
   }

   public String getEnvSourceSymbol() {
      return this._envSourceSymbol;
   }

   public void setEnvSourceSymbol(String symbol) {
      this._envSourceSymbol = symbol;
   }

   public int methodCount() {
      return this._methods.size();
   }

   public ProfilingMethodMetaData getMethodMetaData(int idx) {
      return (ProfilingMethodMetaData)this._methods.get(idx);
   }
}
