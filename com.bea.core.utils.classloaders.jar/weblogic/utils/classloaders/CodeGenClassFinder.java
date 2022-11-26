package weblogic.utils.classloaders;

import java.util.Enumeration;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.enumerations.EmptyEnumerator;

final class CodeGenClassFinder extends MultiClassFinder {
   private final ConcurrentHashMap classNameToSource = new ConcurrentHashMap();

   public CodeGenClassFinder() {
      this.addFinder(new SourceClassFinder());
   }

   public CodeGenClassFinder(ClassFinder finder) {
      this.addFinder(new SourceClassFinder());
      this.addFinder(finder);
   }

   public void addCodeGenSource(String name, Source source) {
      this.classNameToSource.putIfAbsent(name, source);
   }

   protected int getFirstIndex() {
      return 1;
   }

   public void close() {
      super.close();
      this.classNameToSource.clear();
   }

   private class SourceClassFinder extends AbstractClassFinder {
      private SourceClassFinder() {
      }

      public Source getClassSource(String name) {
         return (Source)CodeGenClassFinder.this.classNameToSource.get(name);
      }

      public Source getSource(String name) {
         return name != null && name.endsWith(".class") ? (Source)CodeGenClassFinder.this.classNameToSource.get(name.substring(0, name.length() - 6).replace('/', '.')) : null;
      }

      public Enumeration getSources(String name) {
         return new EmptyEnumerator();
      }

      public String getClassPath() {
         return null;
      }

      // $FF: synthetic method
      SourceClassFinder(Object x1) {
         this();
      }
   }
}
