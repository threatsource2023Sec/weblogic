package weblogic.j2ee.descriptor.wl;

public interface InterceptionBean {
   AssociationBean[] getAssociations();

   AssociationBean createAssociation();

   void destroyAssociation(AssociationBean var1);

   ProcessorBean[] getProcessors();

   ProcessorBean createProcessor();

   void destroyProcessor(ProcessorBean var1);

   ProcessorTypeBean[] getProcessorTypes();

   ProcessorTypeBean createProcessorType();

   void destroyProcessorType(ProcessorTypeBean var1);

   String getVersion();

   void setVersion(String var1);
}
