package weblogic.servlet.ejb2jsp;

import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.utils.Getopt2;

public class HomeCollectionGenerator extends HomeFinderGenerator {
   boolean isEnumeration;

   public void setIsEnumeration(boolean b) {
      this.isEnumeration = b;
   }

   public HomeCollectionGenerator(Getopt2 opts, BeanGenerator bg, EJBMethodDescriptor md) {
      super(opts, bg, md);
   }

   protected String getTemplatePath() {
      return "/weblogic/servlet/ejb2jsp/homecollectiontag.j";
   }
}
