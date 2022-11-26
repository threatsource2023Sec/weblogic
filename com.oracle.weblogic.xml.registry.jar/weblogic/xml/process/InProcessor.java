package weblogic.xml.process;

public interface InProcessor {
   void preProc(ProcessingContext var1) throws SAXProcessorException;

   void postProc(ProcessingContext var1) throws SAXProcessorException;
}
