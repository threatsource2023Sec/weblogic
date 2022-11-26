package org.python.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.apache.tools.ant.types.Resource;
import org.python.apache.tools.ant.types.ResourceCollection;
import org.python.apache.tools.ant.types.resources.BaseResourceCollectionContainer;

public class NameUnionAntType extends BaseResourceCollectionContainer {
   protected Collection getCollection() {
      List collections = this.getResourceCollections();
      Set seenNames = Generic.set();
      List union = new ArrayList();
      Iterator var4 = collections.iterator();

      while(var4.hasNext()) {
         ResourceCollection rc = (ResourceCollection)var4.next();
         Iterator resources = rc.iterator();

         while(resources.hasNext()) {
            Resource r = (Resource)resources.next();
            if (seenNames.add(r.getName())) {
               union.add(r);
            }
         }
      }

      return union;
   }
}
