package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface ProcessingInstruction extends XMLEvent {
   String getTarget();

   String getData();
}
