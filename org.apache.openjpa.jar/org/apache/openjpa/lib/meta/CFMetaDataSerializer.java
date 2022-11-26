package org.apache.openjpa.lib.meta;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.xml.sax.SAXException;

public abstract class CFMetaDataSerializer extends XMLMetaDataSerializer {
   private String _package = null;

   protected String getPackage() {
      return this._package;
   }

   protected void setPackage(String pkg) {
      this._package = pkg;
   }

   protected Map groupByPackage(Collection objs) throws SAXException {
      Map packages = new LinkedHashMap();

      Object packageObjs;
      Object obj;
      for(Iterator itr = objs.iterator(); itr.hasNext(); ((Collection)packageObjs).add(obj)) {
         obj = itr.next();
         String packageName = this.getPackage(obj);
         packageObjs = (Collection)packages.get(packageName);
         if (packageObjs == null) {
            packageObjs = new LinkedList();
            packages.put(packageName, packageObjs);
         }
      }

      return packages;
   }

   protected String getPackage(Object obj) {
      return null;
   }

   protected String getClassName(String name) {
      if (this._package != null && name.lastIndexOf(46) == this._package.length() && name.startsWith(this._package)) {
         return name.substring(this._package.length() + 1);
      } else {
         String[] packages = CFMetaDataParser.PACKAGES;

         for(int i = 0; i < packages.length; ++i) {
            if (name.startsWith(packages[i]) && name.lastIndexOf(46) == packages[i].length() - 1) {
               return name.substring(packages[i].length());
            }
         }

         return name;
      }
   }
}
