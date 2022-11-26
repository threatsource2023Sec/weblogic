package weblogic.application.io;

import java.util.Enumeration;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.EmptyEnumerator;

public class DescriptorFinder extends AbstractClassFinder {
   private final String prefix;
   private final ClassFinder delegate;

   public DescriptorFinder(String uri, ClassFinder delegate) {
      this.prefix = uri + "#";
      this.delegate = delegate;
   }

   private String removePrefix(String name) {
      return name.substring(this.prefix.length(), name.length());
   }

   public Source getSource(String name) {
      return name != null && name.startsWith(this.prefix) ? this.delegate.getSource(this.removePrefix(name)) : null;
   }

   public Enumeration getSources(String name) {
      return (Enumeration)(name != null && name.startsWith(this.prefix) ? this.delegate.getSources(this.removePrefix(name)) : new EmptyEnumerator());
   }

   public Source getClassSource(String name) {
      return null;
   }

   public String getClassPath() {
      return "";
   }

   public ClassFinder getManifestFinder() {
      return new ClasspathClassFinder2("");
   }

   public Enumeration entries() {
      return EmptyEnumerator.EMPTY;
   }

   public void close() {
      this.delegate.close();
   }
}
