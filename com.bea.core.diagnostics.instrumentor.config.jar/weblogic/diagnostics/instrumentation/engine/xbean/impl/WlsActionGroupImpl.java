package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroup;

public class WlsActionGroupImpl extends XmlComplexContentImpl implements WlsActionGroup {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("", "name");
   private static final QName ACTIONTYPES$2 = new QName("", "action-types");

   public WlsActionGroupImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getActionTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ACTIONTYPES$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetActionTypes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ACTIONTYPES$2);
         return target;
      }
   }

   public void setActionTypes(String actionTypes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ACTIONTYPES$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ACTIONTYPES$2);
         }

         target.setStringValue(actionTypes);
      }
   }

   public void xsetActionTypes(XmlString actionTypes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ACTIONTYPES$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ACTIONTYPES$2);
         }

         target.set(actionTypes);
      }
   }
}
