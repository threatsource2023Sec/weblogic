package weblogic.j2ee.descriptor.wl;

public interface SAFDestinationBean extends NamedEntityBean {
   String getRemoteJNDIName();

   void setRemoteJNDIName(String var1) throws IllegalArgumentException;

   String getLocalJNDIName();

   void setLocalJNDIName(String var1) throws IllegalArgumentException;

   String getPersistentQos();

   void setPersistentQos(String var1) throws IllegalArgumentException;

   String getNonPersistentQos();

   void setNonPersistentQos(String var1) throws IllegalArgumentException;

   SAFErrorHandlingBean getSAFErrorHandling();

   void setSAFErrorHandling(SAFErrorHandlingBean var1) throws IllegalArgumentException;

   long getTimeToLiveDefault();

   void setTimeToLiveDefault(long var1) throws IllegalArgumentException;

   boolean isUseSAFTimeToLiveDefault();

   void setUseSAFTimeToLiveDefault(boolean var1) throws IllegalArgumentException;

   String getUnitOfOrderRouting();

   void setUnitOfOrderRouting(String var1) throws IllegalArgumentException;

   MessageLoggingParamsBean getMessageLoggingParams();
}
