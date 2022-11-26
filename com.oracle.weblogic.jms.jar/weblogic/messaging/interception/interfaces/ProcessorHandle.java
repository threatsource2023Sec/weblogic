package weblogic.messaging.interception.interfaces;

import java.util.Iterator;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;

public interface ProcessorHandle {
   Iterator getAssociationInfos() throws InterceptionServiceException;

   String getType() throws InterceptionServiceException;

   String getName() throws InterceptionServiceException;
}
