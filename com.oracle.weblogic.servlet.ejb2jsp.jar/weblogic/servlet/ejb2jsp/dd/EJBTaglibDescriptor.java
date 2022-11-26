package weblogic.servlet.ejb2jsp.dd;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import weblogic.servlet.ejb2jsp.BeanGenerator;
import weblogic.servlet.ejb2jsp.EJBMethodGenerator;
import weblogic.servlet.ejb2jsp.EJBTaglibGenerator;
import weblogic.servlet.ejb2jsp.HomeCollectionGenerator;
import weblogic.servlet.ejb2jsp.HomeFinderGenerator;
import weblogic.servlet.ejb2jsp.HomeMethodGenerator;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.Getopt2;
import weblogic.utils.XXEUtils;
import weblogic.utils.classloaders.ClasspathClassLoader;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;
import weblogic.xml.jaxp.WebLogicDocumentBuilderFactory;

public class EJBTaglibDescriptor implements ToXML, Externalizable {
   private static final long serialVersionUID = -9016538269900747655L;
   private FilesystemInfoDescriptor fileInfo;
   private BeanDescriptor[] beans;
   private transient ClassLoader jarLoader;
   private static final String PREAMBLE = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n<!DOCTYPE ejb2jsp-taglib PUBLIC \"-//BEA Systems, Inc.//DTD EJB2JSP Taglib 1.0//EN\" \"http://www.bea.com/servers/wls600/dtd/weblogic-ejb2jsp.dtd\">";

   static void p(String s) {
      System.err.println("[EJBTagDesc]: " + s);
   }

   public EJBTaglibDescriptor() {
      this.fileInfo = new FilesystemInfoDescriptor();
      this.beans = new BeanDescriptor[0];
   }

   public EJBTaglibDescriptor(Element parent) throws DOMProcessingException {
      this.initFromRoot(parent);
   }

   private void initFromRoot(Element parent) throws DOMProcessingException {
      Element e = null;
      e = DOMUtils.getElementByTagName(parent, "filesystem-info");
      this.fileInfo = new FilesystemInfoDescriptor(e);
      List l = DOMUtils.getElementsByTagName(parent, "ejb");
      List parsed = new ArrayList();
      Iterator I = l.iterator();

      while(I.hasNext()) {
         parsed.add(new BeanDescriptor((Element)I.next()));
      }

      this.beans = new BeanDescriptor[parsed.size()];
      parsed.toArray(this.beans);
   }

   public String toString() {
      String path = this.getFileInfo().getEJBJarFile();
      int ind1 = path.lastIndexOf(47);
      int ind2 = path.lastIndexOf(File.separatorChar);
      int ind = Math.max(ind1, ind2);
      return ind < 0 ? path : path.substring(ind + 1);
   }

   public void writeExternal(ObjectOutput o) throws IOException {
      o.writeUTF(toString(this));
   }

   public void readExternal(ObjectInput s) throws IOException, ClassNotFoundException {
      StringReader rdr = new StringReader(s.readUTF());

      try {
         load(rdr, this);
      } catch (RuntimeException var4) {
         throw var4;
      } catch (IOException var5) {
         throw var5;
      } catch (Exception var6) {
         throw new IOException("error reading XML: " + var6);
      }
   }

   public ClassLoader getClassLoader() {
      if (this.jarLoader == null) {
         this.jarLoader = new ClasspathClassLoader(this.getFileInfo().getEJBJarFile());
      }

      return this.jarLoader;
   }

   public FilesystemInfoDescriptor getFileInfo() {
      return this.fileInfo;
   }

   public void setFileInfo(FilesystemInfoDescriptor fi) {
      this.fileInfo = fi;
   }

   public BeanDescriptor[] getBeans() {
      if (this.beans == null) {
         this.beans = new BeanDescriptor[0];
      }

      return (BeanDescriptor[])((BeanDescriptor[])this.beans.clone());
   }

   public void setBeans(BeanDescriptor[] b) {
      if (b == null) {
         this.beans = new BeanDescriptor[0];
      } else {
         this.beans = (BeanDescriptor[])((BeanDescriptor[])b.clone());
      }

   }

   public void setEnableBaseEJB(boolean b) {
      BeanDescriptor[] bs = this.getBeans();

      for(int i = 0; i < bs.length; ++i) {
         bs[i].setEnableBaseEJB(b);
      }

   }

   public Getopt2 getOpts() throws Exception {
      Getopt2 opts = new Getopt2();
      opts.addOption("d", "destination directory", "working dir of codegen");
      String dir = null;
      if (this.getFileInfo().saveAsDirectory()) {
         dir = this.getFileInfo().getSaveDirClassDir();
      } else {
         dir = this.getFileInfo().getSaveJarTmpdir();
      }

      opts.setOption("d", dir);
      return opts;
   }

   public EJBTaglibGenerator createGenerator() throws Exception {
      if (!this.getFileInfo().saveAsDirectory()) {
         String d = this.getFileInfo().getSaveJarTmpdir();
         File dir = new File(d.replace('/', File.separatorChar));
         if (!dir.mkdirs() && !dir.isDirectory()) {
            throw new Exception("cannot make tmp directory '" + dir.getAbsolutePath() + "'");
         }
      }

      EJBTaglibGenerator ret = new EJBTaglibGenerator(this);
      int nEnabledBeans = 0;

      for(int i = 0; i < this.beans.length; ++i) {
         if (this.beans[i].isEnabled()) {
            ++nEnabledBeans;
         }
      }

      BeanGenerator[] bgs = new BeanGenerator[nEnabledBeans];
      int j = 0;

      for(int i = 0; i < this.beans.length; ++i) {
         if (this.beans[i].isEnabled()) {
            bgs[j++] = this.addMethodGenerators(this.beans[i], ret);
         }
      }

      ret.setGenerators(bgs);
      return ret;
   }

   private BeanGenerator addMethodGenerators(BeanDescriptor bd, EJBTaglibGenerator ret) throws Exception {
      EJBMethodDescriptor[] ms = bd.getEJBMethods();
      BeanGenerator bg = new BeanGenerator(this, bd);
      Getopt2 opts = this.getOpts();

      for(int i = 0; i < ms.length; ++i) {
         if (ms[i].isEnabled()) {
            EJBMethodGenerator em = new EJBMethodGenerator(opts, bg, ms[i]);
            bg.addMethod(em);
         }
      }

      if (bd.isStatefulBean()) {
         ms = bd.getHomeMethods();
         EJBMethodGenerator em = null;

         for(int i = 0; i < ms.length; ++i) {
            if (ms[i].isEnabled()) {
               String mname = ms[i].getName();
               if (!mname.startsWith("find") && !mname.startsWith("create")) {
                  em = new HomeMethodGenerator(opts, bg, ms[i]);
               } else {
                  Class rt = this.getClassLoader().loadClass(ms[i].getReturnType());
                  if (rt.getName().equals(bd.getRemoteType())) {
                     em = new HomeFinderGenerator(opts, bg, ms[i]);
                  } else {
                     Class collClass = Collection.class;
                     Class enumClass = Enumeration.class;
                     if (collClass.isAssignableFrom(rt)) {
                        em = new HomeCollectionGenerator(opts, bg, ms[i]);
                     } else {
                        if (!enumClass.isAssignableFrom(rt)) {
                           throw new IllegalArgumentException("illegal finder on home: " + ms[i].getSignature());
                        }

                        em = new HomeCollectionGenerator(opts, bg, ms[i]);
                        ((HomeCollectionGenerator)em).setIsEnumeration(true);
                     }
                  }
               }

               if (em == null) {
                  throw new Error("no generator type for " + ms[i].getSignature());
               }

               bg.addMethod((EJBMethodGenerator)em);
            }
         }
      }

      return bg;
   }

   public String[] getErrors() {
      List errors = new ArrayList();
      String[] s = this.getFileInfo().getErrors();

      for(int i = 0; s != null && i < s.length; ++i) {
         errors.add(s[i]);
      }

      this.getDuplicateTagNames(errors);
      this.getDuplicateAttributeNames(errors);
      this.getUnresolvedMethods(errors);
      String[] ret = new String[errors.size()];
      errors.toArray(ret);
      return ret;
   }

   private void getDuplicateAttributeNames(List l) {
      for(int i = 0; this.beans != null && i < this.beans.length; ++i) {
         this.beans[i].getDuplicateAttributeNames(l);
      }

   }

   private void getUnresolvedMethods(List errors) {
      for(int i = 0; this.beans != null && i < this.beans.length; ++i) {
         if (this.beans[i].isEnabled()) {
            EJBMethodDescriptor[] unr = this.beans[i].getUnresolvedMethods();

            for(int j = 0; unr != null && j < unr.length; ++j) {
               String s = "tag " + unr[j].getTagName() + " for method " + unr[j].getName() + " on " + unr[j].getTargetType() + " appears to have meaningless parameter names (arg0,arg1,....)";
               errors.add(s);
            }
         }
      }

   }

   private void getDuplicateTagNames(List l) {
      List meths = new ArrayList();

      for(int i = 0; this.beans != null && i < this.beans.length; ++i) {
         EJBMethodDescriptor[] m = this.beans[i].getEJBMethods();

         int j;
         for(j = 0; m != null && j < m.length; ++j) {
            if (m[j].isEnabled()) {
               meths.add(m[j]);
            }
         }

         m = this.beans[i].getHomeMethods();

         for(j = 0; m != null && j < m.length; ++j) {
            if (m[j].isEnabled()) {
               meths.add(m[j]);
            }
         }
      }

      EJBMethodDescriptor[] m = new EJBMethodDescriptor[meths.size()];
      meths.toArray(m);
      this.getDuplicateTagNames(l, m);
   }

   private void getDuplicateTagNames(List l, EJBMethodDescriptor[] m) {
      if (m != null) {
         int lim = m.length;

         for(int i = 0; i < lim - 1; ++i) {
            String tname = m[i].getTagName();

            for(int j = i + 1; j < lim; ++j) {
               if (tname != null && tname.equals(m[j].getTagName())) {
                  String errMsg = "duplicate tag names \"" + tname + "\" refer to methods \"" + m[i].getSignature() + "\" and \"" + m[j].getSignature() + "\"";
                  l.add(errMsg);
               }
            }
         }

      }
   }

   public static EJBTaglibDescriptor load(File f) throws Exception {
      Reader rdr = new InputStreamReader(new FileInputStream(f));

      EJBTaglibDescriptor var2;
      try {
         var2 = load((Reader)rdr);
      } finally {
         rdr.close();
      }

      return var2;
   }

   public static String toString(EJBTaglibDescriptor td) {
      StringWriter w = new StringWriter();
      XMLWriter x = new XMLWriter(w);
      td.toXML(x);
      x.flush();
      return w.toString();
   }

   public static EJBTaglibDescriptor load(String s) throws Exception {
      return load((Reader)(new StringReader(s)));
   }

   public static EJBTaglibDescriptor load(Reader rdr) throws Exception {
      return load(rdr, new EJBTaglibDescriptor());
   }

   private static EJBTaglibDescriptor load(Reader rdr, EJBTaglibDescriptor ret) throws Exception {
      DocumentBuilderFactory fact = new WebLogicDocumentBuilderFactory();
      XXEUtils.disableXXEOnDocumentBuilderFactory(fact);
      fact.setValidating(true);
      DocumentBuilder domParser = fact.newDocumentBuilder();
      domParser.setEntityResolver(new EJBTaglibEntityResolver());
      InputSource src = new InputSource(rdr);
      Element root = null;
      root = domParser.parse(src).getDocumentElement();
      ret.initFromRoot(root);
      return ret;
   }

   public static void main(String[] a) throws Exception {
      File f = new File(a[0]);
      EJBTaglibDescriptor d = load(f);
      XMLWriter x = new XMLWriter(System.out);
      d.toXML(x);
      x.flush();
   }

   public void toXML(XMLWriter x) {
      x.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n<!DOCTYPE ejb2jsp-taglib PUBLIC \"-//BEA Systems, Inc.//DTD EJB2JSP Taglib 1.0//EN\" \"http://www.bea.com/servers/wls600/dtd/weblogic-ejb2jsp.dtd\">");
      x.println("<ejb2jsp-taglib>");
      x.incrIndent();
      this.fileInfo.toXML(x);
      if (this.beans != null && this.beans.length != 0) {
         for(int i = 0; this.beans != null && i < this.beans.length; ++i) {
            this.beans[i].toXML(x);
         }

         x.decrIndent();
         x.println("</ejb2jsp-taglib>");
      } else {
         throw new IllegalStateException("cannot save xml descriptor file with no bean entries");
      }
   }
}
