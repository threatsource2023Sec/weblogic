package weblogic.rmi.utils;

import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLClassFinder;

final class RMIURLClassFinder extends URLClassFinder {
   public RMIURLClassFinder(String url) {
      super(url);
   }

   public Source getSource(String name) {
      return name.indexOf("_WLStub") > 0 ? null : super.getSource(name);
   }
}
