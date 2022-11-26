package weblogic.servlet.ejb2jsp;

import java.util.ArrayList;
import java.util.List;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;

public class EJBTaglibGenerator {
   private List methods = new ArrayList();
   EJBTaglibDescriptor dd;
   BeanGenerator[] bgs;

   public EJBTaglibGenerator(EJBTaglibDescriptor dd) {
      this.dd = dd;
   }

   public void setGenerators(BeanGenerator[] bgs) {
      this.bgs = (BeanGenerator[])((BeanGenerator[])bgs.clone());
   }

   public EJBTaglibDescriptor getDD() {
      return this.dd;
   }

   private void mangleConflictingClassNames() {
      EJBMethodGenerator[] gens = new EJBMethodGenerator[this.methods.size()];
      this.methods.toArray(gens);
      int lim = gens.length;

      for(int i = 0; i < lim - 1; ++i) {
         String mname = gens[i].generated_class_name();
         int ndups = 0;

         for(int j = i + 1; j < lim; ++j) {
            if (mname.equals(gens[j].generated_class_name())) {
               gens[i].setGeneratedClassName(mname + ndups++);
            }
         }
      }

   }

   public String[] generateSources() throws Exception {
      this.methods.clear();

      for(int i = 0; i < this.bgs.length; ++i) {
         List l = this.bgs[i].getMethods();
         this.methods.addAll(l);
      }

      this.mangleConflictingClassNames();
      List srcs = new ArrayList();

      for(int i = 0; i < this.bgs.length; ++i) {
         String[] x = this.bgs[i].generateSources();

         for(int j = 0; x != null && j < x.length; ++j) {
            srcs.add(x[j]);
         }
      }

      String[] s = new String[srcs.size()];
      srcs.toArray(s);
      return s;
   }

   static void p(String s) {
      System.err.println("[ejbtaggen]: " + s);
   }

   public String toXML() {
      return (new TLDOutputter(this.methods)).generate();
   }
}
