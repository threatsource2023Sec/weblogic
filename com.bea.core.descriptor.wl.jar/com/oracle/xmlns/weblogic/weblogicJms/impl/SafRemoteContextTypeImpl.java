package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.SafLoginContextType;
import com.oracle.xmlns.weblogic.weblogicJms.SafRemoteContextType;
import javax.xml.namespace.QName;

public class SafRemoteContextTypeImpl extends NamedEntityTypeImpl implements SafRemoteContextType {
   private static final long serialVersionUID = 1L;
   private static final QName SAFLOGINCONTEXT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-login-context");
   private static final QName COMPRESSIONTHRESHOLD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "compression-threshold");
   private static final QName REPLYTOSAFREMOTECONTEXTNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "reply-to-saf-remote-context-name");

   public SafRemoteContextTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SafLoginContextType getSafLoginContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafLoginContextType target = null;
         target = (SafLoginContextType)this.get_store().find_element_user(SAFLOGINCONTEXT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSafLoginContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFLOGINCONTEXT$0) != 0;
      }
   }

   public void setSafLoginContext(SafLoginContextType safLoginContext) {
      this.generatedSetterHelperImpl(safLoginContext, SAFLOGINCONTEXT$0, 0, (short)1);
   }

   public SafLoginContextType addNewSafLoginContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafLoginContextType target = null;
         target = (SafLoginContextType)this.get_store().add_element_user(SAFLOGINCONTEXT$0);
         return target;
      }
   }

   public void unsetSafLoginContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFLOGINCONTEXT$0, 0);
      }
   }

   public int getCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPRESSIONTHRESHOLD$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(COMPRESSIONTHRESHOLD$2, 0);
         return target;
      }
   }

   public boolean isSetCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPRESSIONTHRESHOLD$2) != 0;
      }
   }

   public void setCompressionThreshold(int compressionThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COMPRESSIONTHRESHOLD$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COMPRESSIONTHRESHOLD$2);
         }

         target.setIntValue(compressionThreshold);
      }
   }

   public void xsetCompressionThreshold(XmlInt compressionThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(COMPRESSIONTHRESHOLD$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(COMPRESSIONTHRESHOLD$2);
         }

         target.set(compressionThreshold);
      }
   }

   public void unsetCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPRESSIONTHRESHOLD$2, 0);
      }
   }

   public String getReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         return target;
      }
   }

   public boolean isNilReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REPLYTOSAFREMOTECONTEXTNAME$4) != 0;
      }
   }

   public void setReplyToSafRemoteContextName(String replyToSafRemoteContextName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REPLYTOSAFREMOTECONTEXTNAME$4);
         }

         target.setStringValue(replyToSafRemoteContextName);
      }
   }

   public void xsetReplyToSafRemoteContextName(XmlString replyToSafRemoteContextName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REPLYTOSAFREMOTECONTEXTNAME$4);
         }

         target.set(replyToSafRemoteContextName);
      }
   }

   public void setNilReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REPLYTOSAFREMOTECONTEXTNAME$4);
         }

         target.setNil();
      }
   }

   public void unsetReplyToSafRemoteContextName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REPLYTOSAFREMOTECONTEXTNAME$4, 0);
      }
   }
}
