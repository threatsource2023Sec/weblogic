package weblogic.management.utils.fileobserver;

import java.util.Collection;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.ServiceFailureException;

@Contract
public interface FileChangeObserverMonitor {
   void addFileChangeObserver(FileChangeObserver var1);

   void removeFileChangeObserver(FileChangeObserver var1);

   Collection getFileChangeObservers();

   long getPollInterval();

   void setPollInterval(long var1) throws Exception;

   void start() throws ServiceFailureException;

   void stop();

   void destroy() throws Exception;

   void observeChanges();
}
