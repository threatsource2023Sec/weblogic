package weblogic.management.runtime;

import java.util.Collection;

public interface BatchJobRepositoryRuntimeMBean extends RuntimeMBean {
   Collection getJobDetails() throws BatchJobRepositoryException;

   Collection getJobExecutions(long var1) throws BatchJobRepositoryException;

   Collection getStepExecutions(long var1) throws BatchJobRepositoryException;
}
