package com.bea.common.ldap.exps;

import com.bea.common.ldap.LDAPStoreManager;

public class Null extends Const {
   public Null(LDAPStoreManager manager) {
      super(Void.TYPE, manager);
   }

   public Object getValue() {
      return null;
   }
}
