package weblogic.servlet.internal;

import oracle.jsp.provider.JspResourceProvider;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.Source;

public class MDSClassFinder extends AbstractClassFinder {
   private final JspResourceProvider provider;

   public MDSClassFinder(JspResourceProvider jrp) {
      this.provider = jrp;
   }

   public String getClassPath() {
      return "";
   }

   public Source getSource(String name) {
      MDSSource source = new MDSSource(name, this.provider);
      return source.exists() ? source : null;
   }
}
