package weblogic.connector.external.impl;

import java.util.ArrayList;
import java.util.List;
import weblogic.connector.external.ActivationSpecInfo;
import weblogic.connector.external.InboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.RequiredConfigPropertyBean;

public class InboundInfoImpl implements InboundInfo {
   private RAInfo raInfo;
   private MessageListenerBean msgListenerBean;

   public InboundInfoImpl(RAInfo raInfo, MessageListenerBean msgListener) {
      this.raInfo = raInfo;
      this.msgListenerBean = msgListener;
   }

   public String getRADescription() {
      return this.raInfo.getRADescription();
   }

   public String getDisplayName() {
      return this.raInfo.getDisplayName();
   }

   public String getVendorName() {
      return this.raInfo.getVendorName();
   }

   public String getEisType() {
      return this.raInfo.getEisType();
   }

   public List getActivationSpecProps() {
      RequiredConfigPropertyBean[] configPropArray = this.msgListenerBean.getActivationSpec().getRequiredConfigProperties();
      if (configPropArray == null) {
         return null;
      } else {
         List configPropList = new ArrayList();

         for(int idx = 0; idx < configPropArray.length; ++idx) {
            RequiredConfigPropInfoImpl configPropInfo = new RequiredConfigPropInfoImpl(configPropArray[idx]);
            configPropList.add(configPropInfo);
         }

         return configPropList;
      }
   }

   public String getMsgType() {
      return this.msgListenerBean.getMessageListenerType();
   }

   public ActivationSpecInfo getActivationSpec() {
      return new ActivationSpecInfoImpl(this.raInfo.getPropertyNameNormalizer(), this.msgListenerBean.getActivationSpec());
   }
}
