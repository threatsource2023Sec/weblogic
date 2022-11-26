package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.OutboundDocument;
import com.bea.connector.monitoring1Dot0.OutboundGroupDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OutboundDocumentImpl extends XmlComplexContentImpl implements OutboundDocument {
   private static final long serialVersionUID = 1L;
   private static final QName OUTBOUND$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "outbound");

   public OutboundDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public OutboundDocument.Outbound getOutbound() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundDocument.Outbound target = null;
         target = (OutboundDocument.Outbound)this.get_store().find_element_user(OUTBOUND$0, 0);
         return target == null ? null : target;
      }
   }

   public void setOutbound(OutboundDocument.Outbound outbound) {
      this.generatedSetterHelperImpl(outbound, OUTBOUND$0, 0, (short)1);
   }

   public OutboundDocument.Outbound addNewOutbound() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundDocument.Outbound target = null;
         target = (OutboundDocument.Outbound)this.get_store().add_element_user(OUTBOUND$0);
         return target;
      }
   }

   public static class OutboundImpl extends XmlComplexContentImpl implements OutboundDocument.Outbound {
      private static final long serialVersionUID = 1L;
      private static final QName OUTBOUNDGROUP$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "outbound-group");

      public OutboundImpl(SchemaType sType) {
         super(sType);
      }

      public OutboundGroupDocument.OutboundGroup[] getOutboundGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(OUTBOUNDGROUP$0, targetList);
            OutboundGroupDocument.OutboundGroup[] result = new OutboundGroupDocument.OutboundGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public OutboundGroupDocument.OutboundGroup getOutboundGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            OutboundGroupDocument.OutboundGroup target = null;
            target = (OutboundGroupDocument.OutboundGroup)this.get_store().find_element_user(OUTBOUNDGROUP$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfOutboundGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(OUTBOUNDGROUP$0);
         }
      }

      public void setOutboundGroupArray(OutboundGroupDocument.OutboundGroup[] outboundGroupArray) {
         this.check_orphaned();
         this.arraySetterHelper(outboundGroupArray, OUTBOUNDGROUP$0);
      }

      public void setOutboundGroupArray(int i, OutboundGroupDocument.OutboundGroup outboundGroup) {
         this.generatedSetterHelperImpl(outboundGroup, OUTBOUNDGROUP$0, i, (short)2);
      }

      public OutboundGroupDocument.OutboundGroup insertNewOutboundGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            OutboundGroupDocument.OutboundGroup target = null;
            target = (OutboundGroupDocument.OutboundGroup)this.get_store().insert_element_user(OUTBOUNDGROUP$0, i);
            return target;
         }
      }

      public OutboundGroupDocument.OutboundGroup addNewOutboundGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            OutboundGroupDocument.OutboundGroup target = null;
            target = (OutboundGroupDocument.OutboundGroup)this.get_store().add_element_user(OUTBOUNDGROUP$0);
            return target;
         }
      }

      public void removeOutboundGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(OUTBOUNDGROUP$0, i);
         }
      }
   }
}
