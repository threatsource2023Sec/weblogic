package com.bea.common.ldap.exps;

import com.bea.common.ldap.DateUtil;
import com.bea.common.ldap.LDAPStoreManager;
import java.util.Date;
import org.apache.openjpa.kernel.exps.Literal;

public class Lit extends Const implements Literal {
   private Object _val;
   private int _ptype;
   private Class _cast = null;

   public Lit(Object val, LDAPStoreManager manager, int ptype) {
      super(val != null ? val.getClass() : Object.class, manager);
      this._val = val;
      this._ptype = ptype;
   }

   public Class getType() {
      if (this._cast != null) {
         return this._cast;
      } else {
         return this._val == null ? Object.class : this._val.getClass();
      }
   }

   public void setImplicitType(Class type) {
      this._cast = type;
   }

   public int getParseType() {
      return this._ptype;
   }

   public Object getValue() {
      return this._val instanceof Date ? DateUtil.toString((Date)this._val, this.getManager().getConfiguration().isVDETimestamp()) : this._val;
   }

   public void setValue(Object val) {
      this._val = val;
   }
}
