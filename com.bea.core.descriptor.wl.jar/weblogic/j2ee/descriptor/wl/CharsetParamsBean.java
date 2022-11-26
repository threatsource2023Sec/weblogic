package weblogic.j2ee.descriptor.wl;

public interface CharsetParamsBean {
   InputCharsetBean[] getInputCharsets();

   InputCharsetBean createInputCharset();

   void destroyInputCharset(InputCharsetBean var1);

   CharsetMappingBean[] getCharsetMappings();

   CharsetMappingBean createCharsetMapping();

   void destroyCharsetMapping(CharsetMappingBean var1);

   String getId();

   void setId(String var1);
}
