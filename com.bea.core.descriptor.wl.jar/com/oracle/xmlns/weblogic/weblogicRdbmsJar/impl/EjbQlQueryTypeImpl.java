package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CachingNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.EjbQlQueryType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GroupNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicQlType;
import javax.xml.namespace.QName;

public class EjbQlQueryTypeImpl extends XmlComplexContentImpl implements EjbQlQueryType {
   private static final long serialVersionUID = 1L;
   private static final QName WEBLOGICQL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "weblogic-ql");
   private static final QName GROUPNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "group-name");
   private static final QName CACHINGNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "caching-name");

   public EjbQlQueryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public WeblogicQlType getWeblogicQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicQlType target = null;
         target = (WeblogicQlType)this.get_store().find_element_user(WEBLOGICQL$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWeblogicQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WEBLOGICQL$0) != 0;
      }
   }

   public void setWeblogicQl(WeblogicQlType weblogicQl) {
      this.generatedSetterHelperImpl(weblogicQl, WEBLOGICQL$0, 0, (short)1);
   }

   public WeblogicQlType addNewWeblogicQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WeblogicQlType target = null;
         target = (WeblogicQlType)this.get_store().add_element_user(WEBLOGICQL$0);
         return target;
      }
   }

   public void unsetWeblogicQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WEBLOGICQL$0, 0);
      }
   }

   public GroupNameType getGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().find_element_user(GROUPNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPNAME$2) != 0;
      }
   }

   public void setGroupName(GroupNameType groupName) {
      this.generatedSetterHelperImpl(groupName, GROUPNAME$2, 0, (short)1);
   }

   public GroupNameType addNewGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupNameType target = null;
         target = (GroupNameType)this.get_store().add_element_user(GROUPNAME$2);
         return target;
      }
   }

   public void unsetGroupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPNAME$2, 0);
      }
   }

   public CachingNameType getCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingNameType target = null;
         target = (CachingNameType)this.get_store().find_element_user(CACHINGNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHINGNAME$4) != 0;
      }
   }

   public void setCachingName(CachingNameType cachingName) {
      this.generatedSetterHelperImpl(cachingName, CACHINGNAME$4, 0, (short)1);
   }

   public CachingNameType addNewCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CachingNameType target = null;
         target = (CachingNameType)this.get_store().add_element_user(CACHINGNAME$4);
         return target;
      }
   }

   public void unsetCachingName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHINGNAME$4, 0);
      }
   }
}
