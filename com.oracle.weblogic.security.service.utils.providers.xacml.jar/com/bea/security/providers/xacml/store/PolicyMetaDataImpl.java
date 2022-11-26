package com.bea.security.providers.xacml.store;

import com.bea.security.xacml.PolicyMetaData;
import java.util.Map;
import java.util.Set;

public class PolicyMetaDataImpl implements PolicyMetaData {
   private String name;
   private String value;
   private Map index;

   public PolicyMetaDataImpl(String name, String value, Map index) {
      this.name = name;
      this.value = value;
      this.index = index;
   }

   public String getValue() {
      return this.value;
   }

   public String getElementName() {
      return this.name;
   }

   public String[] getIndexKeys() {
      Set keys = this.index.keySet();
      return (String[])keys.toArray(new String[keys.size()]);
   }

   public String getIndexValue(String key) {
      return (String)this.index.get(key);
   }

   public String toString() {
      return this.value;
   }
}
