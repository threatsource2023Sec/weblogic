package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Listener;
import org.jcp.xmlns.xml.ns.javaee.Listeners;

public class ListenersImpl extends XmlComplexContentImpl implements Listeners {
   private static final long serialVersionUID = 1L;
   private static final QName LISTENER$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "listener");

   public ListenersImpl(SchemaType sType) {
      super(sType);
   }

   public Listener[] getListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LISTENER$0, targetList);
         Listener[] result = new Listener[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Listener getListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Listener target = null;
         target = (Listener)this.get_store().find_element_user(LISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENER$0);
      }
   }

   public void setListenerArray(Listener[] listenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(listenerArray, LISTENER$0);
   }

   public void setListenerArray(int i, Listener listener) {
      this.generatedSetterHelperImpl(listener, LISTENER$0, i, (short)2);
   }

   public Listener insertNewListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Listener target = null;
         target = (Listener)this.get_store().insert_element_user(LISTENER$0, i);
         return target;
      }
   }

   public Listener addNewListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Listener target = null;
         target = (Listener)this.get_store().add_element_user(LISTENER$0);
         return target;
      }
   }

   public void removeListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENER$0, i);
      }
   }
}
