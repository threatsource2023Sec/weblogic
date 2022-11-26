package weblogic.messaging.interception.interfaces;

import javax.xml.rpc.handler.MessageContext;
import weblogic.messaging.interception.exceptions.InterceptionException;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.exceptions.MessageContextException;

public interface InterceptionPointHandle {
   String getType() throws InterceptionServiceException;

   String[] getName() throws InterceptionServiceException;

   AssociationInfo getAssociationInfo() throws InterceptionServiceException;

   boolean hasAssociation() throws InterceptionServiceException;

   void processOnly(MessageContext var1) throws InterceptionException, MessageContextException, InterceptionServiceException;

   boolean process(MessageContext var1) throws InterceptionException, MessageContextException, InterceptionServiceException;

   void processAsync(MessageContext var1, CarrierCallBack var2) throws InterceptionException, MessageContextException, InterceptionServiceException;

   void processOnlyAsync(MessageContext var1, CarrierCallBack var2) throws InterceptionException, MessageContextException, InterceptionServiceException;
}
