package weblogic.j2ee.descriptor.wl;

public interface QuotaBean extends NamedEntityBean {
   long getBytesMaximum();

   void setBytesMaximum(long var1) throws IllegalArgumentException;

   long getMessagesMaximum();

   void setMessagesMaximum(long var1) throws IllegalArgumentException;

   String getPolicy();

   void setPolicy(String var1) throws IllegalArgumentException;

   boolean isShared();

   void setShared(boolean var1) throws IllegalArgumentException;
}
