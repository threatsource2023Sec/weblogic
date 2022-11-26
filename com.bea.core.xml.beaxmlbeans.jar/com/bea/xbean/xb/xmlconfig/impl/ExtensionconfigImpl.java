package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xmlconfig.Extensionconfig;
import com.bea.xbean.xb.xmlconfig.JavaNameList;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ExtensionconfigImpl extends XmlComplexContentImpl implements Extensionconfig {
   private static final QName INTERFACE$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "interface");
   private static final QName PREPOSTSET$2 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "prePostSet");
   private static final QName FOR$4 = new QName("", "for");

   public ExtensionconfigImpl(SchemaType sType) {
      super(sType);
   }

   public Extensionconfig.Interface[] getInterfaceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)INTERFACE$0, targetList);
         Extensionconfig.Interface[] result = new Extensionconfig.Interface[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Extensionconfig.Interface getInterfaceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.Interface target = null;
         target = (Extensionconfig.Interface)this.get_store().find_element_user(INTERFACE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInterfaceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERFACE$0);
      }
   }

   public void setInterfaceArray(Extensionconfig.Interface[] xinterfaceArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xinterfaceArray, INTERFACE$0);
      }
   }

   public void setInterfaceArray(int i, Extensionconfig.Interface xinterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.Interface target = null;
         target = (Extensionconfig.Interface)this.get_store().find_element_user(INTERFACE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xinterface);
         }
      }
   }

   public Extensionconfig.Interface insertNewInterface(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.Interface target = null;
         target = (Extensionconfig.Interface)this.get_store().insert_element_user(INTERFACE$0, i);
         return target;
      }
   }

   public Extensionconfig.Interface addNewInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.Interface target = null;
         target = (Extensionconfig.Interface)this.get_store().add_element_user(INTERFACE$0);
         return target;
      }
   }

   public void removeInterface(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERFACE$0, i);
      }
   }

   public Extensionconfig.PrePostSet getPrePostSet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.PrePostSet target = null;
         target = (Extensionconfig.PrePostSet)this.get_store().find_element_user((QName)PREPOSTSET$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrePostSet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREPOSTSET$2) != 0;
      }
   }

   public void setPrePostSet(Extensionconfig.PrePostSet prePostSet) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.PrePostSet target = null;
         target = (Extensionconfig.PrePostSet)this.get_store().find_element_user((QName)PREPOSTSET$2, 0);
         if (target == null) {
            target = (Extensionconfig.PrePostSet)this.get_store().add_element_user(PREPOSTSET$2);
         }

         target.set(prePostSet);
      }
   }

   public Extensionconfig.PrePostSet addNewPrePostSet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Extensionconfig.PrePostSet target = null;
         target = (Extensionconfig.PrePostSet)this.get_store().add_element_user(PREPOSTSET$2);
         return target;
      }
   }

   public void unsetPrePostSet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)PREPOSTSET$2, 0);
      }
   }

   public Object getFor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FOR$4);
         return target == null ? null : target.getObjectValue();
      }
   }

   public JavaNameList xgetFor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaNameList target = null;
         target = (JavaNameList)this.get_store().find_attribute_user(FOR$4);
         return target;
      }
   }

   public boolean isSetFor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FOR$4) != null;
      }
   }

   public void setFor(Object xfor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FOR$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FOR$4);
         }

         target.setObjectValue(xfor);
      }
   }

   public void xsetFor(JavaNameList xfor) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaNameList target = null;
         target = (JavaNameList)this.get_store().find_attribute_user(FOR$4);
         if (target == null) {
            target = (JavaNameList)this.get_store().add_attribute_user(FOR$4);
         }

         target.set(xfor);
      }
   }

   public void unsetFor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FOR$4);
      }
   }

   public static class PrePostSetImpl extends XmlComplexContentImpl implements Extensionconfig.PrePostSet {
      private static final QName STATICHANDLER$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "staticHandler");

      public PrePostSetImpl(SchemaType sType) {
         super(sType);
      }

      public String getStaticHandler() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetStaticHandler() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            return target;
         }
      }

      public void setStaticHandler(String staticHandler) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(STATICHANDLER$0);
            }

            target.setStringValue(staticHandler);
         }
      }

      public void xsetStaticHandler(XmlString staticHandler) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(STATICHANDLER$0);
            }

            target.set(staticHandler);
         }
      }
   }

   public static class InterfaceImpl extends XmlComplexContentImpl implements Extensionconfig.Interface {
      private static final QName STATICHANDLER$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "staticHandler");
      private static final QName NAME$2 = new QName("", "name");

      public InterfaceImpl(SchemaType sType) {
         super(sType);
      }

      public String getStaticHandler() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetStaticHandler() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            return target;
         }
      }

      public void setStaticHandler(String staticHandler) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(STATICHANDLER$0);
            }

            target.setStringValue(staticHandler);
         }
      }

      public void xsetStaticHandler(XmlString staticHandler) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user((QName)STATICHANDLER$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(STATICHANDLER$0);
            }

            target.set(staticHandler);
         }
      }

      public String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$2);
            return target;
         }
      }

      public boolean isSetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(NAME$2) != null;
         }
      }

      public void setName(String name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAME$2);
            }

            target.setStringValue(name);
         }
      }

      public void xsetName(XmlString name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$2);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(NAME$2);
            }

            target.set(name);
         }
      }

      public void unsetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(NAME$2);
         }
      }
   }
}
