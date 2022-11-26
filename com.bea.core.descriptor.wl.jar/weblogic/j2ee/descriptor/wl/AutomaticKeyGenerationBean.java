package weblogic.j2ee.descriptor.wl;

public interface AutomaticKeyGenerationBean {
   String getGeneratorType();

   void setGeneratorType(String var1);

   String getGeneratorName();

   void setGeneratorName(String var1);

   int getKeyCacheSize();

   void setKeyCacheSize(int var1);

   String getId();

   void setId(String var1);

   void setSelectFirstSequenceKeyBeforeUpdate(boolean var1);

   boolean getSelectFirstSequenceKeyBeforeUpdate();
}
