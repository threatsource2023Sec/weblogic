package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.LinkRefType;
import com.oracle.xmlns.weblogic.weblogicConnector.ProxyType;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorExtensionType;
import javax.xml.namespace.QName;

public class WeblogicConnectorExtensionTypeImpl extends WeblogicConnectorTypeImpl implements WeblogicConnectorExtensionType {
   private static final long serialVersionUID = 1L;
   private static final QName LINKREF$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "link-ref");
   private static final QName PROXY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "proxy");

   public WeblogicConnectorExtensionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public LinkRefType getLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LinkRefType target = null;
         target = (LinkRefType)this.get_store().find_element_user(LINKREF$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LINKREF$0) != 0;
      }
   }

   public void setLinkRef(LinkRefType linkRef) {
      this.generatedSetterHelperImpl(linkRef, LINKREF$0, 0, (short)1);
   }

   public LinkRefType addNewLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LinkRefType target = null;
         target = (LinkRefType)this.get_store().add_element_user(LINKREF$0);
         return target;
      }
   }

   public void unsetLinkRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LINKREF$0, 0);
      }
   }

   public ProxyType getProxy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyType target = null;
         target = (ProxyType)this.get_store().find_element_user(PROXY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProxy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROXY$2) != 0;
      }
   }

   public void setProxy(ProxyType proxy) {
      this.generatedSetterHelperImpl(proxy, PROXY$2, 0, (short)1);
   }

   public ProxyType addNewProxy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyType target = null;
         target = (ProxyType)this.get_store().add_element_user(PROXY$2);
         return target;
      }
   }

   public void unsetProxy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROXY$2, 0);
      }
   }
}
