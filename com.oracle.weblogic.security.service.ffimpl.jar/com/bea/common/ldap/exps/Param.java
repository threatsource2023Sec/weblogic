package com.bea.common.ldap.exps;

import com.bea.common.ldap.DateUtil;
import com.bea.common.ldap.LDAPStoreManager;
import java.util.Date;
import java.util.Map;
import org.apache.openjpa.kernel.exps.Parameter;

public class Param extends Const implements Parameter {
   private final String _name;
   private int _idx = -1;

   public Param(String name, Class type, LDAPStoreManager manager) {
      super(type, manager);
      this._name = name;
   }

   public String getParameterName() {
      return this._name;
   }

   public int getIndex() {
      return this._idx;
   }

   public void setIndex(int idx) {
      this._idx = idx;
   }

   public Object getValue(Object[] parameters) {
      if (this._idx >= 0 && parameters != null && parameters.length > this._idx) {
         Object o = parameters[this._idx];
         if (o instanceof Map) {
            o = ((Map)o).get(this._name);
         }

         String val;
         if (o instanceof Date) {
            val = DateUtil.toString((Date)o, this.getManager().getConfiguration().isVDETimestamp());
         } else {
            val = o.toString();
         }

         return val;
      } else {
         return "";
      }
   }

   public Object getValue() {
      return "";
   }
}
