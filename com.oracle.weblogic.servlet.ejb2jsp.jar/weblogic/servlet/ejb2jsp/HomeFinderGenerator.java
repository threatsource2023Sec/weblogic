package weblogic.servlet.ejb2jsp;

import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.utils.Getopt2;

public class HomeFinderGenerator extends EJBMethodGenerator {
   public HomeFinderGenerator(Getopt2 opts, BeanGenerator bg, EJBMethodDescriptor md) {
      super(opts, bg, md);
   }

   protected String getTemplatePath() {
      return "/weblogic/servlet/ejb2jsp/homefindertag.j";
   }

   public String retSet() {
      return "if (_return != null) pageContext.setAttribute(_return, theEJB);\n";
   }

   public String generated_class_name() {
      if (this.generatedClassName == null) {
         String hometype = this.homeType();
         int ind = hometype.lastIndexOf(46);
         if (ind > 0) {
            hometype = hometype.substring(ind + 1);
         }

         this.generatedClassName = this.bg.getPackage() + "._" + hometype + "_" + this.getMethodName() + "Tag";
      }

      return this.generatedClassName;
   }

   public boolean equals(Object o) {
      return !(o instanceof HomeFinderGenerator) ? false : super.equals(o);
   }

   public String homeTagType() {
      return this.bg.getHomeTagInterfaceName();
   }
}
