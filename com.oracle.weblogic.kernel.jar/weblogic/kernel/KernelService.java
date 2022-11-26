package weblogic.kernel;

import weblogic.server.ServiceFailureException;

public interface KernelService {
   void initialize() throws ServiceFailureException;

   void shutdown();
}
