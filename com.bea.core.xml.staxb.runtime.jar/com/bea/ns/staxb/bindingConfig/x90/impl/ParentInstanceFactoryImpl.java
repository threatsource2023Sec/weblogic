package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.ns.staxb.bindingConfig.x90.ParentInstanceFactory;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class ParentInstanceFactoryImpl extends JavaInstanceFactoryImpl implements ParentInstanceFactory {
   private static final long serialVersionUID = 1L;
   private static final QName CREATEOBJECTMETHOD$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "create-object-method");

   public ParentInstanceFactoryImpl(SchemaType sType) {
      super(sType);
   }

   public JavaMethodName getCreateObjectMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(CREATEOBJECTMETHOD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCreateObjectMethod(JavaMethodName createObjectMethod) {
      this.generatedSetterHelperImpl(createObjectMethod, CREATEOBJECTMETHOD$0, 0, (short)1);
   }

   public JavaMethodName addNewCreateObjectMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(CREATEOBJECTMETHOD$0);
         return target;
      }
   }
}
