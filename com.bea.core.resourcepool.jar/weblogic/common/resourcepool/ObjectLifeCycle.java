package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public interface ObjectLifeCycle {
   void start(Object var1) throws ResourceException;

   void resume() throws ResourceException;

   void suspend(boolean var1) throws ResourceException;

   void forceSuspend(boolean var1) throws ResourceException;

   void shutdown() throws ResourceException;
}
