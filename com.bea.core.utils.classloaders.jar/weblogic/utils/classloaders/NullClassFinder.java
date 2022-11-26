package weblogic.utils.classloaders;

public final class NullClassFinder extends AbstractClassFinder {
   public static final ClassFinder NULL_FINDER = new NullClassFinder();

   public Source getSource(String name) {
      return null;
   }

   public String getClassPath() {
      return "";
   }
}
