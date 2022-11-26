package org.apache.velocity.anakia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.jdom.Element;

public class TreeWalker {
   public NodeList allElements(Element e) {
      ArrayList theElements = new ArrayList();
      this.treeWalk(e, theElements);
      return new NodeList(theElements, false);
   }

   private final void treeWalk(Element e, Collection theElements) {
      Iterator i = e.getChildren().iterator();

      while(i.hasNext()) {
         Element child = (Element)i.next();
         theElements.add(child);
         this.treeWalk(child, theElements);
      }

   }
}
