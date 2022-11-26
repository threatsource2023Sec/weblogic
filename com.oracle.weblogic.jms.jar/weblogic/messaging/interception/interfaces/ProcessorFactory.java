package weblogic.messaging.interception.interfaces;

import weblogic.messaging.interception.exceptions.InterceptionServiceException;

public interface ProcessorFactory {
   Processor create(String var1, String var2) throws InterceptionServiceException;
}
