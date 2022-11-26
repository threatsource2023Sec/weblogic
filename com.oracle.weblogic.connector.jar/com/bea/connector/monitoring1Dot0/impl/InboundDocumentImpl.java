package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.InboundDocument;
import com.bea.connector.monitoring1Dot0.MessagelistenerDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class InboundDocumentImpl extends XmlComplexContentImpl implements InboundDocument {
   private static final long serialVersionUID = 1L;
   private static final QName INBOUND$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "inbound");

   public InboundDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public InboundDocument.Inbound getInbound() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundDocument.Inbound target = null;
         target = (InboundDocument.Inbound)this.get_store().find_element_user(INBOUND$0, 0);
         return target == null ? null : target;
      }
   }

   public void setInbound(InboundDocument.Inbound inbound) {
      this.generatedSetterHelperImpl(inbound, INBOUND$0, 0, (short)1);
   }

   public InboundDocument.Inbound addNewInbound() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundDocument.Inbound target = null;
         target = (InboundDocument.Inbound)this.get_store().add_element_user(INBOUND$0);
         return target;
      }
   }

   public static class InboundImpl extends XmlComplexContentImpl implements InboundDocument.Inbound {
      private static final long serialVersionUID = 1L;
      private static final QName MESSAGELISTENER$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "messagelistener");

      public InboundImpl(SchemaType sType) {
         super(sType);
      }

      public MessagelistenerDocument.Messagelistener[] getMessagelistenerArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(MESSAGELISTENER$0, targetList);
            MessagelistenerDocument.Messagelistener[] result = new MessagelistenerDocument.Messagelistener[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public MessagelistenerDocument.Messagelistener getMessagelistenerArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MessagelistenerDocument.Messagelistener target = null;
            target = (MessagelistenerDocument.Messagelistener)this.get_store().find_element_user(MESSAGELISTENER$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfMessagelistenerArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(MESSAGELISTENER$0);
         }
      }

      public void setMessagelistenerArray(MessagelistenerDocument.Messagelistener[] messagelistenerArray) {
         this.check_orphaned();
         this.arraySetterHelper(messagelistenerArray, MESSAGELISTENER$0);
      }

      public void setMessagelistenerArray(int i, MessagelistenerDocument.Messagelistener messagelistener) {
         this.generatedSetterHelperImpl(messagelistener, MESSAGELISTENER$0, i, (short)2);
      }

      public MessagelistenerDocument.Messagelistener insertNewMessagelistener(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MessagelistenerDocument.Messagelistener target = null;
            target = (MessagelistenerDocument.Messagelistener)this.get_store().insert_element_user(MESSAGELISTENER$0, i);
            return target;
         }
      }

      public MessagelistenerDocument.Messagelistener addNewMessagelistener() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            MessagelistenerDocument.Messagelistener target = null;
            target = (MessagelistenerDocument.Messagelistener)this.get_store().add_element_user(MESSAGELISTENER$0);
            return target;
         }
      }

      public void removeMessagelistener(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(MESSAGELISTENER$0, i);
         }
      }
   }
}
