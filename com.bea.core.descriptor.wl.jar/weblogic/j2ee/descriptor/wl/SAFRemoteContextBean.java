package weblogic.j2ee.descriptor.wl;

public interface SAFRemoteContextBean extends NamedEntityBean {
   SAFLoginContextBean getSAFLoginContext();

   int getCompressionThreshold();

   void setCompressionThreshold(int var1) throws IllegalArgumentException;

   String getReplyToSAFRemoteContextName();

   void setReplyToSAFRemoteContextName(String var1);
}
