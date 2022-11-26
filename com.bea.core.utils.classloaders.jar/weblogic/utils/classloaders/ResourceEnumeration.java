package weblogic.utils.classloaders;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.SequencingEnumerator;

public class ResourceEnumeration implements Enumeration {
   private final List enums = Collections.synchronizedList(new LinkedList());
   private Enumeration seqEnumerator;
   private boolean inited;

   public ResourceEnumeration(Enumeration e) {
      this.addEnumeration(e);
   }

   protected ResourceEnumeration() {
   }

   private synchronized void init() {
      if (!this.inited) {
         List l = this.getEnumerations();
         if (l.isEmpty()) {
            this.seqEnumerator = new EmptyEnumerator();
         } else {
            this.seqEnumerator = new SequencingEnumerator((Enumeration[])((Enumeration[])l.toArray(new Enumeration[l.size()])));
         }

         this.inited = true;
      }
   }

   protected List getEnumerations() {
      return this.enums;
   }

   public void addEnumeration(Enumeration e) {
      if (e != null) {
         this.enums.add(e);
      }

   }

   public boolean hasMoreElements() {
      if (!this.inited) {
         this.init();
      }

      return this.seqEnumerator.hasMoreElements();
   }

   public Object nextElement() {
      if (!this.inited) {
         this.init();
      }

      return this.seqEnumerator.nextElement();
   }
}
