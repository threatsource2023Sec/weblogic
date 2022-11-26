package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.PoolParamsType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import javax.xml.namespace.QName;

public class PoolParamsTypeImpl extends ConnectionPoolParamsTypeImpl implements PoolParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName MATCHCONNECTIONSSUPPORTED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "match-connections-supported");
   private static final QName USEFIRSTAVAILABLE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "use-first-available");

   public PoolParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(MATCHCONNECTIONSSUPPORTED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MATCHCONNECTIONSSUPPORTED$0) != 0;
      }
   }

   public void setMatchConnectionsSupported(TrueFalseType matchConnectionsSupported) {
      this.generatedSetterHelperImpl(matchConnectionsSupported, MATCHCONNECTIONSSUPPORTED$0, 0, (short)1);
   }

   public TrueFalseType addNewMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(MATCHCONNECTIONSSUPPORTED$0);
         return target;
      }
   }

   public void unsetMatchConnectionsSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MATCHCONNECTIONSSUPPORTED$0, 0);
      }
   }

   public TrueFalseType getUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USEFIRSTAVAILABLE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEFIRSTAVAILABLE$2) != 0;
      }
   }

   public void setUseFirstAvailable(TrueFalseType useFirstAvailable) {
      this.generatedSetterHelperImpl(useFirstAvailable, USEFIRSTAVAILABLE$2, 0, (short)1);
   }

   public TrueFalseType addNewUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USEFIRSTAVAILABLE$2);
         return target;
      }
   }

   public void unsetUseFirstAvailable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEFIRSTAVAILABLE$2, 0);
      }
   }
}
