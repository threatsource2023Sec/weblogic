package weblogic.j2ee.descriptor.wl;

public interface GzipCompressionBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isEnabledSet();

   long getMinContentLength();

   void setMinContentLength(long var1);

   boolean isMinContentLengthSet();

   String[] getContentType();

   void setContentType(String[] var1);

   boolean isContentTypeSet();
}
