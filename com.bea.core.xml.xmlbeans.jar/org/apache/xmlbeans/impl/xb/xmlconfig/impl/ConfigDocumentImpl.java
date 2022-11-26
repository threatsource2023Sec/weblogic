package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Nsconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnameconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Usertypeconfig;

public class ConfigDocumentImpl extends XmlComplexContentImpl implements ConfigDocument {
   private static final QName CONFIG$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "config");

   public ConfigDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConfigDocument.Config getConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigDocument.Config target = null;
         target = (ConfigDocument.Config)this.get_store().find_element_user((QName)CONFIG$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConfig(ConfigDocument.Config config) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigDocument.Config target = null;
         target = (ConfigDocument.Config)this.get_store().find_element_user((QName)CONFIG$0, 0);
         if (target == null) {
            target = (ConfigDocument.Config)this.get_store().add_element_user(CONFIG$0);
         }

         target.set(config);
      }
   }

   public ConfigDocument.Config addNewConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigDocument.Config target = null;
         target = (ConfigDocument.Config)this.get_store().add_element_user(CONFIG$0);
         return target;
      }
   }

   public static class ConfigImpl extends XmlComplexContentImpl implements ConfigDocument.Config {
      private static final QName NAMESPACE$0 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "namespace");
      private static final QName QNAME$2 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "qname");
      private static final QName EXTENSION$4 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "extension");
      private static final QName USERTYPE$6 = new QName("http://xml.apache.org/xmlbeans/2004/02/xbean/config", "usertype");

      public ConfigImpl(SchemaType sType) {
         super(sType);
      }

      public Nsconfig[] getNamespaceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)NAMESPACE$0, targetList);
            Nsconfig[] result = new Nsconfig[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Nsconfig getNamespaceArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Nsconfig target = null;
            target = (Nsconfig)this.get_store().find_element_user(NAMESPACE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfNamespaceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(NAMESPACE$0);
         }
      }

      public void setNamespaceArray(Nsconfig[] namespaceArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(namespaceArray, NAMESPACE$0);
         }
      }

      public void setNamespaceArray(int i, Nsconfig namespace) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Nsconfig target = null;
            target = (Nsconfig)this.get_store().find_element_user(NAMESPACE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(namespace);
            }
         }
      }

      public Nsconfig insertNewNamespace(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Nsconfig target = null;
            target = (Nsconfig)this.get_store().insert_element_user(NAMESPACE$0, i);
            return target;
         }
      }

      public Nsconfig addNewNamespace() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Nsconfig target = null;
            target = (Nsconfig)this.get_store().add_element_user(NAMESPACE$0);
            return target;
         }
      }

      public void removeNamespace(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(NAMESPACE$0, i);
         }
      }

      public Qnameconfig[] getQnameArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)QNAME$2, targetList);
            Qnameconfig[] result = new Qnameconfig[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Qnameconfig getQnameArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Qnameconfig target = null;
            target = (Qnameconfig)this.get_store().find_element_user(QNAME$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfQnameArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(QNAME$2);
         }
      }

      public void setQnameArray(Qnameconfig[] qnameArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(qnameArray, QNAME$2);
         }
      }

      public void setQnameArray(int i, Qnameconfig qname) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Qnameconfig target = null;
            target = (Qnameconfig)this.get_store().find_element_user(QNAME$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(qname);
            }
         }
      }

      public Qnameconfig insertNewQname(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Qnameconfig target = null;
            target = (Qnameconfig)this.get_store().insert_element_user(QNAME$2, i);
            return target;
         }
      }

      public Qnameconfig addNewQname() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Qnameconfig target = null;
            target = (Qnameconfig)this.get_store().add_element_user(QNAME$2);
            return target;
         }
      }

      public void removeQname(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(QNAME$2, i);
         }
      }

      public Extensionconfig[] getExtensionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)EXTENSION$4, targetList);
            Extensionconfig[] result = new Extensionconfig[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Extensionconfig getExtensionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Extensionconfig target = null;
            target = (Extensionconfig)this.get_store().find_element_user(EXTENSION$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfExtensionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(EXTENSION$4);
         }
      }

      public void setExtensionArray(Extensionconfig[] extensionArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(extensionArray, EXTENSION$4);
         }
      }

      public void setExtensionArray(int i, Extensionconfig extension) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Extensionconfig target = null;
            target = (Extensionconfig)this.get_store().find_element_user(EXTENSION$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(extension);
            }
         }
      }

      public Extensionconfig insertNewExtension(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Extensionconfig target = null;
            target = (Extensionconfig)this.get_store().insert_element_user(EXTENSION$4, i);
            return target;
         }
      }

      public Extensionconfig addNewExtension() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Extensionconfig target = null;
            target = (Extensionconfig)this.get_store().add_element_user(EXTENSION$4);
            return target;
         }
      }

      public void removeExtension(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(EXTENSION$4, i);
         }
      }

      public Usertypeconfig[] getUsertypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)USERTYPE$6, targetList);
            Usertypeconfig[] result = new Usertypeconfig[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public Usertypeconfig getUsertypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Usertypeconfig target = null;
            target = (Usertypeconfig)this.get_store().find_element_user(USERTYPE$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfUsertypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(USERTYPE$6);
         }
      }

      public void setUsertypeArray(Usertypeconfig[] usertypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(usertypeArray, USERTYPE$6);
         }
      }

      public void setUsertypeArray(int i, Usertypeconfig usertype) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Usertypeconfig target = null;
            target = (Usertypeconfig)this.get_store().find_element_user(USERTYPE$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(usertype);
            }
         }
      }

      public Usertypeconfig insertNewUsertype(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Usertypeconfig target = null;
            target = (Usertypeconfig)this.get_store().insert_element_user(USERTYPE$6, i);
            return target;
         }
      }

      public Usertypeconfig addNewUsertype() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Usertypeconfig target = null;
            target = (Usertypeconfig)this.get_store().add_element_user(USERTYPE$6);
            return target;
         }
      }

      public void removeUsertype(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(USERTYPE$6, i);
         }
      }
   }
}
