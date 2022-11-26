package weblogic.j2ee.descriptor;

public interface LocaleEncodingMappingListBean {
   LocaleEncodingMappingBean[] getLocaleEncodingMappings();

   LocaleEncodingMappingBean createLocaleEncodingMapping();

   void destroyLocaleEncodingMapping(LocaleEncodingMappingBean var1);

   String getId();

   void setId(String var1);
}
