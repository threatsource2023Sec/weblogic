package weblogic.servlet.ejb2jsp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;

public class BeanGenerator {
   private String homeTagInterfaceName;
   private List imports = new ArrayList();
   private List methods = new ArrayList();
   private BeanDescriptor dd;
   private EJBTaglibDescriptor parentDD;

   public BeanGenerator(EJBTaglibDescriptor parentDD, BeanDescriptor dd) {
      this.parentDD = parentDD;
      this.dd = dd;
      this.addImport(dd.getRemoteType());
      this.addImport(dd.getHomeType());
      if (dd.isStatefulBean()) {
         this.calculateHomeTagInterfaceName();
      }

   }

   public BeanDescriptor getDD() {
      return this.dd;
   }

   public EJBTaglibDescriptor getParentDD() {
      return this.parentDD;
   }

   public String getHomeTagInterfaceName() {
      return this.homeTagInterfaceName;
   }

   private void calculateHomeTagInterfaceName() {
      String homeType = this.dd.getHomeType();
      int ind = homeType.lastIndexOf(46);
      if (ind > 0) {
         homeType = homeType.substring(ind + 1);
      }

      this.homeTagInterfaceName = this.parentDD.getFileInfo().getPackage() + ".__Base_" + homeType + "_homeTag";
   }

   public void addMethodImports(Method m) {
      this.addImport(m.getReturnType());
      Class[] c = m.getParameterTypes();

      for(int i = 0; c != null && i < c.length; ++i) {
         this.addImport(c[i]);
      }

   }

   public String getImportString() {
      StringBuffer sb = new StringBuffer();
      Iterator I = this.getImports().iterator();

      while(I.hasNext()) {
         sb.append("import " + I.next() + ";\n");
      }

      return sb.toString();
   }

   public String[] generateSources() throws Exception {
      List ret = new ArrayList();
      if (this.dd.isStatefulBean()) {
         HomeInterfaceGenerator hg = new HomeInterfaceGenerator(this.parentDD.getOpts(), this.getImportString(), this.getHomeTagInterfaceName(), this.dd.getRemoteType());
         ret.add(hg.generate()[0]);
      }

      Iterator I = this.methods.iterator();

      while(I.hasNext()) {
         EJBMethodGenerator md = (EJBMethodGenerator)I.next();
         String[] sourceNames = md.generate();
         int t = sourceNames.length;
         ret.add(sourceNames[0]);
         if (t > 1) {
            ret.add(sourceNames[1]);
         }
      }

      String[] s = new String[ret.size()];
      ret.toArray(s);
      return s;
   }

   static void p(String s) {
      System.err.println("[beangen]: " + s);
   }

   public void addImport(Class c) {
      if (c != Void.class && c != Void.TYPE) {
         this.addImport(c.getName());
      }

   }

   private void addImport(String s) {
      if (!Utils.isPrimitive(s) && !this.imports.contains(s)) {
         this.imports.add(s);
      }

   }

   public String getPackage() {
      return this.parentDD.getFileInfo().getPackage();
   }

   public List getImports() {
      return this.imports;
   }

   public void addMethod(EJBMethodGenerator em) {
      if (!this.methods.contains(em)) {
         this.methods.add(em);
      }

   }

   public List getMethods() {
      return this.methods;
   }
}
