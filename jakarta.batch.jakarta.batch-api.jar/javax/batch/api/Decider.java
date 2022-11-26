package javax.batch.api;

import javax.batch.runtime.StepExecution;

public interface Decider {
   String decide(StepExecution[] var1) throws Exception;
}
