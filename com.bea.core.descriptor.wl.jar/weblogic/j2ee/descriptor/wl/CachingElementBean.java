package weblogic.j2ee.descriptor.wl;

public interface CachingElementBean {
   String getCmrField();

   void setCmrField(String var1);

   String getGroupName();

   void setGroupName(String var1);

   CachingElementBean[] getCachingElements();

   CachingElementBean createCachingElement();

   void destroyCachingElement(CachingElementBean var1);

   String getId();

   void setId(String var1);
}
