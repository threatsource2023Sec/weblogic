package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPStoreManager;
import org.apache.openjpa.kernel.exps.Constant;

public abstract class Const extends Val implements Constant {
   private LDAPStoreManager manager;

   public Const(Class type, LDAPStoreManager manager) {
      super(type);
      this.manager = manager;
   }

   protected LDAPStoreManager getManager() {
      return this.manager;
   }

   public boolean isVariable() {
      return false;
   }

   public abstract Object getValue();

   public Object getValue(Object[] parameters) {
      return this.getValue();
   }
}
