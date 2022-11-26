package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicWebservices.DeploymentListenerListType;
import com.sun.java.xml.ns.j2Ee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DeploymentListenerListTypeImpl extends XmlComplexContentImpl implements DeploymentListenerListType {
   private static final long serialVersionUID = 1L;
   private static final QName DEPLOYMENTLISTENER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "deployment-listener");

   public DeploymentListenerListTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getDeploymentListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DEPLOYMENTLISTENER$0, targetList);
         String[] result = new String[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public String getDeploymentListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DEPLOYMENTLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDeploymentListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPLOYMENTLISTENER$0);
      }
   }

   public void setDeploymentListenerArray(String[] deploymentListenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(deploymentListenerArray, DEPLOYMENTLISTENER$0);
   }

   public void setDeploymentListenerArray(int i, String deploymentListener) {
      this.generatedSetterHelperImpl(deploymentListener, DEPLOYMENTLISTENER$0, i, (short)2);
   }

   public String insertNewDeploymentListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().insert_element_user(DEPLOYMENTLISTENER$0, i);
         return target;
      }
   }

   public String addNewDeploymentListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DEPLOYMENTLISTENER$0);
         return target;
      }
   }

   public void removeDeploymentListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPLOYMENTLISTENER$0, i);
      }
   }
}
