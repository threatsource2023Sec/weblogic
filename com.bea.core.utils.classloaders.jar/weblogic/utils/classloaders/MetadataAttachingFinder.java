package weblogic.utils.classloaders;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;

public class MetadataAttachingFinder extends DelegateFinder {
   private Object metadata;

   public MetadataAttachingFinder(ClassFinder finder, Object metadata) {
      super(finder);
      this.metadata = metadata;
   }

   public Object getMetadata() {
      return this.metadata;
   }

   public Source getSource(String name) {
      Source s = super.getSource(name);
      return s == null ? null : new SourceWithMetadata(s, this.metadata);
   }

   public Enumeration getSources(String name) {
      Enumeration e = super.getSources(name);
      List l = null;
      if (e != null) {
         l = new ArrayList();

         while(e.hasMoreElements()) {
            Source s = (Source)e.nextElement();
            l.add(new SourceWithMetadata(s, this.metadata));
         }
      }

      return (Enumeration)(l == null ? new EmptyEnumerator() : new IteratorEnumerator(l.iterator()));
   }
}
