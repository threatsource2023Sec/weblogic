package weblogic.j2ee.descriptor.wl;

public interface RelationshipCachingBean {
   String getCachingName();

   void setCachingName(String var1);

   CachingElementBean[] getCachingElements();

   CachingElementBean createCachingElement();

   void destroyCachingElement(CachingElementBean var1);

   String getId();

   void setId(String var1);
}
