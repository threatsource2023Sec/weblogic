package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.CharsetMappingType;
import com.oracle.xmlns.weblogic.weblogicWebApp.CharsetParamsType;
import com.oracle.xmlns.weblogic.weblogicWebApp.InputCharsetType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CharsetParamsTypeImpl extends XmlComplexContentImpl implements CharsetParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName INPUTCHARSET$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "input-charset");
   private static final QName CHARSETMAPPING$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "charset-mapping");
   private static final QName ID$4 = new QName("", "id");

   public CharsetParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public InputCharsetType[] getInputCharsetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INPUTCHARSET$0, targetList);
         InputCharsetType[] result = new InputCharsetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InputCharsetType getInputCharsetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InputCharsetType target = null;
         target = (InputCharsetType)this.get_store().find_element_user(INPUTCHARSET$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInputCharsetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INPUTCHARSET$0);
      }
   }

   public void setInputCharsetArray(InputCharsetType[] inputCharsetArray) {
      this.check_orphaned();
      this.arraySetterHelper(inputCharsetArray, INPUTCHARSET$0);
   }

   public void setInputCharsetArray(int i, InputCharsetType inputCharset) {
      this.generatedSetterHelperImpl(inputCharset, INPUTCHARSET$0, i, (short)2);
   }

   public InputCharsetType insertNewInputCharset(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InputCharsetType target = null;
         target = (InputCharsetType)this.get_store().insert_element_user(INPUTCHARSET$0, i);
         return target;
      }
   }

   public InputCharsetType addNewInputCharset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InputCharsetType target = null;
         target = (InputCharsetType)this.get_store().add_element_user(INPUTCHARSET$0);
         return target;
      }
   }

   public void removeInputCharset(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INPUTCHARSET$0, i);
      }
   }

   public CharsetMappingType[] getCharsetMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CHARSETMAPPING$2, targetList);
         CharsetMappingType[] result = new CharsetMappingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CharsetMappingType getCharsetMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetMappingType target = null;
         target = (CharsetMappingType)this.get_store().find_element_user(CHARSETMAPPING$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCharsetMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHARSETMAPPING$2);
      }
   }

   public void setCharsetMappingArray(CharsetMappingType[] charsetMappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(charsetMappingArray, CHARSETMAPPING$2);
   }

   public void setCharsetMappingArray(int i, CharsetMappingType charsetMapping) {
      this.generatedSetterHelperImpl(charsetMapping, CHARSETMAPPING$2, i, (short)2);
   }

   public CharsetMappingType insertNewCharsetMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetMappingType target = null;
         target = (CharsetMappingType)this.get_store().insert_element_user(CHARSETMAPPING$2, i);
         return target;
      }
   }

   public CharsetMappingType addNewCharsetMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharsetMappingType target = null;
         target = (CharsetMappingType)this.get_store().add_element_user(CHARSETMAPPING$2);
         return target;
      }
   }

   public void removeCharsetMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHARSETMAPPING$2, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
