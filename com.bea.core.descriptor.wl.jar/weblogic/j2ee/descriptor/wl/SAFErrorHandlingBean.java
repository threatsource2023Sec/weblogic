package weblogic.j2ee.descriptor.wl;

public interface SAFErrorHandlingBean extends NamedEntityBean {
   String getPolicy();

   void setPolicy(String var1) throws IllegalArgumentException;

   String getLogFormat();

   void setLogFormat(String var1) throws IllegalArgumentException;

   SAFDestinationBean getSAFErrorDestination();

   void setSAFErrorDestination(SAFDestinationBean var1) throws IllegalArgumentException;
}
