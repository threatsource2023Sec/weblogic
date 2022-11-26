package weblogic.diagnostics.image.descriptor;

public interface InstrumentationImageSourceBean {
   InstrumentationEventBean[] getInstrumentationEvents();

   InstrumentationEventBean createInstrumentationEvent();
}
