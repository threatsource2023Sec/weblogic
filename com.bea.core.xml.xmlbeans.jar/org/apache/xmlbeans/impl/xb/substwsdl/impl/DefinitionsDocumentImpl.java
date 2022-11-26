package org.apache.xmlbeans.impl.xb.substwsdl.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.substwsdl.DefinitionsDocument;
import org.apache.xmlbeans.impl.xb.substwsdl.TImport;

public class DefinitionsDocumentImpl extends XmlComplexContentImpl implements DefinitionsDocument {
   private static final QName DEFINITIONS$0 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "definitions");

   public DefinitionsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DefinitionsDocument.Definitions getDefinitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefinitionsDocument.Definitions target = null;
         target = (DefinitionsDocument.Definitions)this.get_store().find_element_user((QName)DEFINITIONS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDefinitions(DefinitionsDocument.Definitions definitions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefinitionsDocument.Definitions target = null;
         target = (DefinitionsDocument.Definitions)this.get_store().find_element_user((QName)DEFINITIONS$0, 0);
         if (target == null) {
            target = (DefinitionsDocument.Definitions)this.get_store().add_element_user(DEFINITIONS$0);
         }

         target.set(definitions);
      }
   }

   public DefinitionsDocument.Definitions addNewDefinitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefinitionsDocument.Definitions target = null;
         target = (DefinitionsDocument.Definitions)this.get_store().add_element_user(DEFINITIONS$0);
         return target;
      }
   }

   public static class DefinitionsImpl extends XmlComplexContentImpl implements DefinitionsDocument.Definitions {
      private static final QName IMPORT$0 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "import");
      private static final QName TYPES$2 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "types");
      private static final QName MESSAGE$4 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "message");
      private static final QName BINDING$6 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "binding");
      private static final QName PORTTYPE$8 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "portType");
      private static final QName SERVICE$10 = new QName("http://www.apache.org/internal/xmlbeans/wsdlsubst", "service");

      public DefinitionsImpl(SchemaType sType) {
         super(sType);
      }

      public TImport[] getImportArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)IMPORT$0, targetList);
            TImport[] result = new TImport[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TImport getImportArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TImport target = null;
            target = (TImport)this.get_store().find_element_user(IMPORT$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfImportArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(IMPORT$0);
         }
      }

      public void setImportArray(TImport[] ximportArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(ximportArray, IMPORT$0);
         }
      }

      public void setImportArray(int i, TImport ximport) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TImport target = null;
            target = (TImport)this.get_store().find_element_user(IMPORT$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(ximport);
            }
         }
      }

      public TImport insertNewImport(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TImport target = null;
            target = (TImport)this.get_store().insert_element_user(IMPORT$0, i);
            return target;
         }
      }

      public TImport addNewImport() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TImport target = null;
            target = (TImport)this.get_store().add_element_user(IMPORT$0);
            return target;
         }
      }

      public void removeImport(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(IMPORT$0, i);
         }
      }

      public XmlObject[] getTypesArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)TYPES$2, targetList);
            XmlObject[] result = new XmlObject[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlObject getTypesArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(TYPES$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfTypesArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(TYPES$2);
         }
      }

      public void setTypesArray(XmlObject[] typesArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(typesArray, TYPES$2);
         }
      }

      public void setTypesArray(int i, XmlObject types) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(TYPES$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(types);
            }
         }
      }

      public XmlObject insertNewTypes(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().insert_element_user(TYPES$2, i);
            return target;
         }
      }

      public XmlObject addNewTypes() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().add_element_user(TYPES$2);
            return target;
         }
      }

      public void removeTypes(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(TYPES$2, i);
         }
      }

      public XmlObject[] getMessageArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)MESSAGE$4, targetList);
            XmlObject[] result = new XmlObject[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlObject getMessageArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(MESSAGE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfMessageArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(MESSAGE$4);
         }
      }

      public void setMessageArray(XmlObject[] messageArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(messageArray, MESSAGE$4);
         }
      }

      public void setMessageArray(int i, XmlObject message) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(MESSAGE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(message);
            }
         }
      }

      public XmlObject insertNewMessage(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().insert_element_user(MESSAGE$4, i);
            return target;
         }
      }

      public XmlObject addNewMessage() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().add_element_user(MESSAGE$4);
            return target;
         }
      }

      public void removeMessage(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MESSAGE$4, i);
         }
      }

      public XmlObject[] getBindingArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)BINDING$6, targetList);
            XmlObject[] result = new XmlObject[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlObject getBindingArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(BINDING$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfBindingArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(BINDING$6);
         }
      }

      public void setBindingArray(XmlObject[] bindingArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(bindingArray, BINDING$6);
         }
      }

      public void setBindingArray(int i, XmlObject binding) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(BINDING$6, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(binding);
            }
         }
      }

      public XmlObject insertNewBinding(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().insert_element_user(BINDING$6, i);
            return target;
         }
      }

      public XmlObject addNewBinding() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().add_element_user(BINDING$6);
            return target;
         }
      }

      public void removeBinding(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(BINDING$6, i);
         }
      }

      public XmlObject[] getPortTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)PORTTYPE$8, targetList);
            XmlObject[] result = new XmlObject[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlObject getPortTypeArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(PORTTYPE$8, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfPortTypeArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(PORTTYPE$8);
         }
      }

      public void setPortTypeArray(XmlObject[] portTypeArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(portTypeArray, PORTTYPE$8);
         }
      }

      public void setPortTypeArray(int i, XmlObject portType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(PORTTYPE$8, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(portType);
            }
         }
      }

      public XmlObject insertNewPortType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().insert_element_user(PORTTYPE$8, i);
            return target;
         }
      }

      public XmlObject addNewPortType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().add_element_user(PORTTYPE$8);
            return target;
         }
      }

      public void removePortType(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(PORTTYPE$8, i);
         }
      }

      public XmlObject[] getServiceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)SERVICE$10, targetList);
            XmlObject[] result = new XmlObject[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlObject getServiceArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(SERVICE$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfServiceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SERVICE$10);
         }
      }

      public void setServiceArray(XmlObject[] serviceArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(serviceArray, SERVICE$10);
         }
      }

      public void setServiceArray(int i, XmlObject service) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().find_element_user(SERVICE$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(service);
            }
         }
      }

      public XmlObject insertNewService(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().insert_element_user(SERVICE$10, i);
            return target;
         }
      }

      public XmlObject addNewService() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlObject target = null;
            target = (XmlObject)this.get_store().add_element_user(SERVICE$10);
            return target;
         }
      }

      public void removeService(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SERVICE$10, i);
         }
      }
   }
}
