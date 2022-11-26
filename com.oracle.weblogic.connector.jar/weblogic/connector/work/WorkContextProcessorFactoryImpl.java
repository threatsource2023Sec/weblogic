package weblogic.connector.work;

import java.util.ArrayList;
import java.util.List;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.work.CallbackHandlerFactoryImpl;
import weblogic.security.container.jca.jaspic.ConnectorCallbackHandler;

public class WorkContextProcessorFactoryImpl implements WorkContextProcessorFactory {
   private ConnectorCallbackHandler.EISPrincipalMapper mapper;
   private boolean virtual;
   private SubjectStack subjectStack;

   public WorkContextProcessorFactoryImpl(ConnectorCallbackHandler.EISPrincipalMapper mapper, boolean virtual, SubjectStack subjectStack) {
      this.mapper = mapper;
      this.virtual = virtual;
      this.subjectStack = subjectStack;
   }

   public List getWorkContextProcessors() {
      List processors = new ArrayList(3);
      processors.add(new TransactionContextProcessor());
      processors.add(new HintsContextProcessor());
      processors.add(new SecurityContextProcessor(this.subjectStack, new CallbackHandlerFactoryImpl(this.mapper, this.virtual)));
      return processors;
   }
}
