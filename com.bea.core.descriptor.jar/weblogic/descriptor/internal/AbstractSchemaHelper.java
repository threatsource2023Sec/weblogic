package weblogic.descriptor.internal;

public abstract class AbstractSchemaHelper implements Munger.SchemaHelper {
   public int getPropertyIndex(Munger.ReaderEventInfo info) {
      return this.getPropertyIndex(info.getElementName());
   }

   public int getPropertyIndex(String propName) {
      return -1;
   }

   public boolean isArray(int propIndex) {
      return false;
   }

   public boolean isBean(int propIndex) {
      return false;
   }

   public boolean isAnAttribute(int propIndex) {
      return false;
   }

   public Munger.SchemaHelper getSchemaHelper(int propIndex) {
      return this;
   }
}
