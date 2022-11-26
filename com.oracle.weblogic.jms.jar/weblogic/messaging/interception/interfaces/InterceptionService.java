package weblogic.messaging.interception.interfaces;

import java.util.Iterator;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;

public interface InterceptionService {
   AssociationHandle addAssociation(String var1, String[] var2, String var3, String var4, boolean var5) throws InterceptionServiceException;

   AssociationHandle addAssociation(String var1, String[] var2, String var3, String var4, boolean var5, int var6) throws InterceptionServiceException;

   void removeAssociation(AssociationHandle var1) throws InterceptionServiceException;

   void registerInterceptionPointNameDescription(String var1, InterceptionPointNameDescriptor[] var2, AssociationListener var3) throws InterceptionServiceException;

   void registerInterceptionPointNameDescriptionListener(InterceptionPointNameDescriptionListener var1) throws InterceptionServiceException;

   InterceptionPointNameDescriptor[] getInterceptionPointNameDescription(String var1);

   InterceptionPointHandle registerInterceptionPoint(String var1, String[] var2) throws InterceptionServiceException;

   void unRegisterInterceptionPoint(InterceptionPointHandle var1) throws InterceptionServiceException;

   void registerProcessorType(String var1, String var2) throws InterceptionServiceException;

   ProcessorHandle addProcessor(String var1, String var2, String var3) throws InterceptionServiceException;

   void removeProcessor(ProcessorHandle var1) throws InterceptionServiceException;

   void removeProcessor(String var1, String var2) throws InterceptionServiceException;

   Iterator getAssociationHandles();

   AssociationHandle getAssociationHandle(String var1, String[] var2) throws InterceptionServiceException;

   Iterator getProcessorHandles(String var1) throws InterceptionServiceException;

   ProcessorHandle getProcessorHandle(String var1, String var2) throws InterceptionServiceException;
}
