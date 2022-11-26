package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.CookieCommentType;
import org.jcp.xmlns.xml.ns.javaee.CookieConfigType;
import org.jcp.xmlns.xml.ns.javaee.CookieDomainType;
import org.jcp.xmlns.xml.ns.javaee.CookieNameType;
import org.jcp.xmlns.xml.ns.javaee.CookiePathType;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class CookieConfigTypeImpl extends XmlComplexContentImpl implements CookieConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName DOMAIN$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "domain");
   private static final QName PATH$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "path");
   private static final QName COMMENT$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "comment");
   private static final QName HTTPONLY$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "http-only");
   private static final QName SECURE$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "secure");
   private static final QName MAXAGE$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-age");
   private static final QName ID$14 = new QName("", "id");

   public CookieConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CookieNameType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieNameType target = null;
         target = (CookieNameType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAME$0) != 0;
      }
   }

   public void setName(CookieNameType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public CookieNameType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieNameType target = null;
         target = (CookieNameType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAME$0, 0);
      }
   }

   public CookieDomainType getDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieDomainType target = null;
         target = (CookieDomainType)this.get_store().find_element_user(DOMAIN$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DOMAIN$2) != 0;
      }
   }

   public void setDomain(CookieDomainType domain) {
      this.generatedSetterHelperImpl(domain, DOMAIN$2, 0, (short)1);
   }

   public CookieDomainType addNewDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieDomainType target = null;
         target = (CookieDomainType)this.get_store().add_element_user(DOMAIN$2);
         return target;
      }
   }

   public void unsetDomain() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DOMAIN$2, 0);
      }
   }

   public CookiePathType getPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookiePathType target = null;
         target = (CookiePathType)this.get_store().find_element_user(PATH$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PATH$4) != 0;
      }
   }

   public void setPath(CookiePathType path) {
      this.generatedSetterHelperImpl(path, PATH$4, 0, (short)1);
   }

   public CookiePathType addNewPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookiePathType target = null;
         target = (CookiePathType)this.get_store().add_element_user(PATH$4);
         return target;
      }
   }

   public void unsetPath() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PATH$4, 0);
      }
   }

   public CookieCommentType getComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieCommentType target = null;
         target = (CookieCommentType)this.get_store().find_element_user(COMMENT$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMMENT$6) != 0;
      }
   }

   public void setComment(CookieCommentType comment) {
      this.generatedSetterHelperImpl(comment, COMMENT$6, 0, (short)1);
   }

   public CookieCommentType addNewComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CookieCommentType target = null;
         target = (CookieCommentType)this.get_store().add_element_user(COMMENT$6);
         return target;
      }
   }

   public void unsetComment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMMENT$6, 0);
      }
   }

   public TrueFalseType getHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(HTTPONLY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPONLY$8) != 0;
      }
   }

   public void setHttpOnly(TrueFalseType httpOnly) {
      this.generatedSetterHelperImpl(httpOnly, HTTPONLY$8, 0, (short)1);
   }

   public TrueFalseType addNewHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(HTTPONLY$8);
         return target;
      }
   }

   public void unsetHttpOnly() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPONLY$8, 0);
      }
   }

   public TrueFalseType getSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SECURE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURE$10) != 0;
      }
   }

   public void setSecure(TrueFalseType secure) {
      this.generatedSetterHelperImpl(secure, SECURE$10, 0, (short)1);
   }

   public TrueFalseType addNewSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SECURE$10);
         return target;
      }
   }

   public void unsetSecure() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURE$10, 0);
      }
   }

   public XsdIntegerType getMaxAge() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXAGE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxAge() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXAGE$12) != 0;
      }
   }

   public void setMaxAge(XsdIntegerType maxAge) {
      this.generatedSetterHelperImpl(maxAge, MAXAGE$12, 0, (short)1);
   }

   public XsdIntegerType addNewMaxAge() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXAGE$12);
         return target;
      }
   }

   public void unsetMaxAge() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXAGE$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
