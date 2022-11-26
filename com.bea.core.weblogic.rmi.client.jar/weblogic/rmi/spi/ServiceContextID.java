package weblogic.rmi.spi;

public interface ServiceContextID {
   int TRANSACTION_CONTEXT_ID = 0;
   int ACTIVATION_CONTEXT_ID = 2;
   int REPLICATION_CONTEXT_ID = 3;
   int TRACE_CONTEXT_ID = 4;
   int WORK_CONTEXT_ID = 5;
   int METHOD_DESCRIPTOR_CONTEXT_ID = 6;
   int TransactionService = 0;
   int CodeSets = 1;
   int BI_DIR_IIOP = 5;
   int SendingContextRunTime = 6;
   int INVOCATION_POLICIES = 7;
   int UnknownExceptionInfo = 9;
   int SecurityAttributeService = 15;
   int RMICustomMaxStreamFormat = 17;
   int FUTURE_OBJECT_ID = 25;
}
