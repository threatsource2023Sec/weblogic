package com.solarmetric.profile;

import java.util.ArrayList;

public class ProfilingMethodMetaData {
   String _name;
   String _profileName;
   String _description;
   String _category;
   ArrayList _params = new ArrayList();
   ArrayList _props = new ArrayList();
   ProfilingArgMetaData _envArg = null;
   ProfilingArgMetaData _agentArg = null;

   public ProfilingMethodMetaData(String name, String profileName, String description, String category) {
      this._name = name;
      this._profileName = profileName;
      this._description = description;
      this._category = category;
   }

   public String getName() {
      return this._name;
   }

   public String getProfileName() {
      return this._profileName;
   }

   public String getDescription() {
      return this._description;
   }

   public String getCategory() {
      return this._category;
   }

   public ProfilingPropertyMetaData addPropertyElement(String name, String profileName) {
      ProfilingPropertyMetaData prop = new ProfilingPropertyMetaData(name, profileName);
      this._props.add(prop);
      return prop;
   }

   public ProfilingPropertyMetaData[] getProperties() {
      ProfilingPropertyMetaData[] props = new ProfilingPropertyMetaData[this._props.size()];

      for(int i = 0; i < this._props.size(); ++i) {
         props[i] = (ProfilingPropertyMetaData)this._props.get(i);
      }

      return props;
   }

   public ProfilingArgMetaData addSignatureElement(String typeName, boolean isDescriber, String name, boolean isAgentProvider, boolean isEnv) {
      int index = this._params.size();
      ProfilingArgMetaData arg = new ProfilingArgMetaData(typeName, isDescriber, name, index, isAgentProvider, isEnv);
      if (isAgentProvider) {
         this._agentArg = arg;
      }

      if (isEnv) {
         this._envArg = arg;
      }

      this._params.add(arg);
      return arg;
   }

   public String[] getSignatureElements() {
      String[] signature = new String[this._params.size()];

      for(int i = 0; i < this._params.size(); ++i) {
         signature[i] = ((ProfilingArgMetaData)this._params.get(i)).getType();
      }

      return signature;
   }

   public ProfilingArgMetaData getArgMetaData(int i) {
      return (ProfilingArgMetaData)this._params.get(i);
   }

   public ProfilingArgMetaData[] getDescriberArgs() {
      ArrayList list = new ArrayList();

      for(int i = 0; i < this._params.size(); ++i) {
         ProfilingArgMetaData argmd = (ProfilingArgMetaData)this._params.get(i);
         if (argmd.getIsDescriber()) {
            list.add(argmd);
         }
      }

      ProfilingArgMetaData[] args = (ProfilingArgMetaData[])((ProfilingArgMetaData[])list.toArray(new ProfilingArgMetaData[list.size()]));
      return args;
   }

   public ProfilingArgMetaData getEnvArg() {
      return this._envArg;
   }

   public ProfilingArgMetaData getAgentArg() {
      return this._agentArg;
   }
}
