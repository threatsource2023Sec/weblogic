package weblogic.servlet.ejb2jsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarFile;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.JndiBindingBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;
import weblogic.servlet.ejb2jsp.dd.FilesystemInfoDescriptor;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;
import weblogic.servlet.jsp.JspConfig;
import weblogic.utils.ArrayUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.io.XMLWriter;
import weblogic.utils.jars.JarFileObject;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.reflect.ReflectUtils;

public class Utils {
   public static final String RESOLVE_ERRORS_MESSAGE = "You may resolve errors in the following ways using the GUI ejb2jsp tool:\n - for duplicate tag names, you can rename tag(s) so that all tag\n   names are unique.\n - for duplicate tag names, individual tag(s) can be disabled, so\n   that their name does not cause a conflict.  The JSP tag for that\n   EJB method will not be generated.\n - for duplicate tag names in projects containing more than one EJB,\n   entire beans may be disabled.  JSP tags are not generated for\n   disabled beans, and their tag names will not cause conflicts.\n - for \"meaningless\" parameter names (arg0,arg1,...) each parameter\n   should be given a usable name.  This is done in the GUI tool by\n   editing the tag's attributes.  Reasonable parameters can also be\n   inferred by the tool in most cases, if the \"Source Path\" of the\n   project is set correctly, so that the tool can parse the parameter\n   names out of the .java source files for the EJB interface(s).\n";

   static void p(String s) {
      System.err.println("[Utils]: " + s);
   }

   public static String[] union(String[] s1, String[] s2) {
      List l = new ArrayList();

      int i;
      for(i = 0; i < s1.length; ++i) {
         l.add(s1[i]);
      }

      for(i = 0; i < s2.length; ++i) {
         if (!l.contains(s2[i])) {
            l.add(s2[i]);
         }
      }

      String[] ret = new String[l.size()];
      l.toArray(ret);
      return ret;
   }

   public static String[] delta(String[] s1, String[] s2) {
      List l = new ArrayList();

      for(int i = 0; i < s1.length; ++i) {
         l.add(s1[i]);
      }

      List ret = new ArrayList();

      for(int i = 0; i < s2.length; ++i) {
         if (!l.contains(s2[i])) {
            ret.add(s2[i]);
         }
      }

      String[] s = new String[ret.size()];
      ret.toArray(s);
      return s;
   }

   public static String[] splitPath(String s) {
      s = s.replace('/', File.separatorChar);
      String[] ret = StringUtils.splitCompletely(s, File.pathSeparator);

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = abs(ret[i]);
      }

      return ret;
   }

   public static String flattenPath(String[] s) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < s.length; ++i) {
         sb.append(s[i].replace('/', File.separatorChar));
         if (i != s.length - 1) {
            sb.append(File.pathSeparator);
         }
      }

      return sb.toString();
   }

   public static void compile(EJBTaglibDescriptor desc, PrintStream ps) throws Exception {
      String[] errors = desc.getErrors();
      if (errors != null && errors.length > 0) {
         ps.println("[ejb2jsp] cannot compile taglib, it has " + errors.length + " error(s):");

         for(int i = 0; i < errors.length; ++i) {
            ps.println(' ' + errors[i]);
         }

         throw new RuntimeException("taglib descriptor had errors");
      } else {
         EJBTaglibGenerator gen = desc.createGenerator();
         String[] sources = gen.generateSources();
         ps.println("[ejb2jsp]: generated the following sources: ");

         for(int i = 0; i < sources.length; ++i) {
            ps.println(" " + sources[i]);
         }

         CompilerInvoker inv = getCompiler(desc);
         inv.setWantCompilerErrors(true);
         inv.setNoExit(true);

         try {
            inv.compile(sources);
         } catch (Exception var7) {
            ps.println("compilation failed:\n---");
            ps.println("" + inv.getCompilerErrors());
            ps.println("---\n" + var7);
            throw new IOException("compilation failed: " + inv.getCompilerErrors());
         }

         doPackaging(desc, gen);
         FilesystemInfoDescriptor fs = desc.getFileInfo();
         if (fs.saveAsDirectory()) {
            ps.println("TLD file written to " + fs.getSaveDirTldFile());
            ps.println("classes compiled to directory " + fs.getSaveDirClassDir());
         } else {
            ps.println("Taglib jar file written to " + fs.getSaveJarFile());
         }

         ps.println("[ejb2jsp]: compile successful.");
      }
   }

   public static void doPackaging(EJBTaglibDescriptor desc, EJBTaglibGenerator gen) throws Exception {
      if (desc.getFileInfo().saveAsDirectory()) {
         String tld = desc.getFileInfo().getSaveDirTldFile();
         tld = tld.replace('/', File.separatorChar);
         PrintStream ps = new PrintStream(new FileOutputStream(tld));

         try {
            ps.print(gen.toXML());
            ps.flush();
         } finally {
            ps.close();
         }
      } else {
         File tmpdir = new File(desc.getFileInfo().getSaveJarTmpdir().replace('/', File.separatorChar));
         File metaInf = new File(tmpdir, "META-INF");
         metaInf.mkdirs();
         if (!metaInf.isDirectory()) {
            throw new Exception("cannot make directory: " + metaInf.getAbsolutePath());
         }

         File tld = new File(metaInf, "taglib.tld");
         PrintStream ps = new PrintStream(new FileOutputStream(tld));

         try {
            ps.print(gen.toXML());
            ps.flush();
         } finally {
            ps.close();
         }

         String var6 = desc.getFileInfo().getSaveJarFile();
         JarFileObject var7 = JarFileObject.makeJar(var6, tmpdir);
         var7.save();
      }

   }

   public static CompilerInvoker getCompiler(EJBTaglibDescriptor desc) throws Exception {
      FilesystemInfoDescriptor fi = desc.getFileInfo();
      String[] tmp = fi.getBuiltinClasspath();
      String compileCp = flattenPath(tmp);
      tmp = fi.getCompileClasspath();
      if (tmp != null && tmp.length > 0) {
         compileCp = compileCp + File.pathSeparator + flattenPath(tmp);
      }

      String target = null;
      if (fi.saveAsDirectory()) {
         target = fi.getSaveDirClassDir();
      } else {
         target = fi.getSaveJarTmpdir();
      }

      compileCp = compileCp + File.pathSeparator + target;
      String[] extraCompileFlags = JspConfig.parseFlags(fi.getJavacFlags());
      Getopt2 compilerOpts = new Getopt2();
      CompilerInvoker inv = new CompilerInvoker(compilerOpts);
      compilerOpts.setOption("classpath", compileCp);
      compilerOpts.setOption("compiler", fi.getJavacPath());
      compilerOpts.grok(desc.getFileInfo().getCompileCommand());
      inv.setExtraCompileFlags(extraCompileFlags);
      return inv;
   }

   public static List getAllEJBeans(EjbDescriptorBean ejbm) {
      List ret = new ArrayList();
      EjbJarBean jarMBean = ejbm.getEjbJarBean();
      WeblogicEjbJarBean wlMBean = ejbm.getWeblogicEjbJarBean();
      WeblogicEnterpriseBeanBean[] wlbs = wlMBean.getWeblogicEnterpriseBeans();
      int j = 0;
      EnterpriseBeansBean ent = jarMBean.getEnterpriseBeans();
      EntityBeanBean[] embs = ent.getEntities();

      String ht;
      String rt;
      String jndi;
      JndiBindingBean name;
      for(int i = 0; embs != null && i < embs.length; ++i) {
         String ht = null;
         ht = null;
         rt = null;
         jndi = null;
         if (embs[i].getLocal() != null) {
            ht = embs[i].getLocalHome();
            ht = embs[i].getLocal();
            name = wlbs[j++].lookupJndiBinding(embs[i].getLocalHome());
            rt = name == null ? null : name.getJndiName();
         } else {
            ht = embs[i].getHome();
            ht = embs[i].getRemote();
            name = wlbs[j++].lookupJndiBinding(embs[i].getHome());
            rt = name == null ? null : name.getJndiName();
         }

         jndi = embs[i].getEjbName();
         EJBean x = new EJBean(jndi, ht, ht, rt, true, false, false);
         ret.add(x);
      }

      SessionBeanBean[] sbs = ent.getSessions();

      for(int i = 0; sbs != null && i < sbs.length; ++i) {
         ht = null;
         rt = null;
         jndi = null;
         name = null;
         JndiBindingBean jbean;
         if (sbs[i].getLocal() != null) {
            ht = sbs[i].getLocalHome();
            rt = sbs[i].getLocal();
            jbean = wlbs[j++].lookupJndiBinding(sbs[i].getLocalHome());
            jndi = jbean == null ? null : jbean.getJndiName();
         } else {
            ht = sbs[i].getHome();
            rt = sbs[i].getRemote();
            jbean = wlbs[j++].lookupJndiBinding(sbs[i].getHome());
            jndi = jbean == null ? null : jbean.getJndiName();
         }

         String name = sbs[i].getEjbName();
         boolean isStateful = "Stateful".equalsIgnoreCase(sbs[i].getSessionType());
         EJBean x = new EJBean(name, rt, ht, jndi, false, true, isStateful);
         ret.add(x);
      }

      return ret;
   }

   public static void main(String[] a) throws Exception {
      System.setProperty("javax.xml.parsers.SAXParserFactory", "weblogic.apache.xerces.jaxp.SAXParserFactoryImpl");
      System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "weblogic.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
      JarFile jf = new JarFile(a[0]);
      EjbDescriptorBean ejbm = EjbDescriptorFactory.createDescriptorFromJarFile(VirtualJarFactory.createVirtualJar(jf));
      jf.close();
      File ejbjar = new File(a[0]);
      EJBTaglibDescriptor ejbt = createDefaultDescriptor(ejbjar, getAllEJBeans(ejbm), "C:/tmp");
      XMLWriter x = new XMLWriter(System.out);
      ejbt.toXML(x);
      x.flush();
   }

   public static String abs(String s) {
      File f = new File(s.replace('/', File.separatorChar));
      return f.getAbsolutePath();
   }

   public static EJBTaglibDescriptor createDefaultDescriptor(String jarfile, String sourcePath, String webappDir) throws Exception {
      JarFile jf = new JarFile(jarfile);
      EjbDescriptorBean ejbm = EjbDescriptorFactory.createDescriptorFromJarFile(VirtualJarFactory.createVirtualJar(jf));
      List beans = getAllEJBeans(ejbm);
      jf.close();
      EJBTaglibDescriptor ret = null;
      ret = createDefaultDescriptor(new File(jarfile), beans, webappDir);
      String[] srcs = StringUtils.splitCompletely(sourcePath, File.pathSeparator);
      ret.getFileInfo().setSourcePath(srcs);
      resolveSources(ret);
      return ret;
   }

   static boolean isWin32() {
      try {
         String os = System.getProperty("os.name");
         if (os == null) {
            return false;
         } else {
            os = os.toLowerCase(Locale.ENGLISH);
            return os.indexOf("windows") >= 0;
         }
      } catch (Exception var1) {
         var1.printStackTrace();
         return false;
      }
   }

   public static EJBTaglibDescriptor createDefaultDescriptor(File ejbjar, List l, String webappDir) throws Exception {
      EJBTaglibDescriptor ret = new EJBTaglibDescriptor();
      FilesystemInfoDescriptor fs = ret.getFileInfo();
      fs.setEJBJarFile(ejbjar.getAbsolutePath());
      fs.setJavacPath("javac");
      fs.setSourcePath(new String[0]);
      fs.setSaveAs("DIRECTORY");
      String ejbjarName = ret.toString();
      int ind = ejbjarName.lastIndexOf(46);
      if (ind > 0) {
         ejbjarName = ejbjarName.substring(0, ind);
      }

      String tmp = "/tmp/" + ejbjarName + "_tags";
      if (isWin32()) {
         tmp = "C:\\TEMP\\" + ejbjarName + "_tags";
      }

      fs.setSaveJarTmpdir(tmp + File.separatorChar + "jar_tmp");
      if (!webappDir.toUpperCase(Locale.ENGLISH).endsWith("WEB-INF")) {
         webappDir = webappDir + File.separatorChar + "WEB-INF";
      }

      String saveJarFile = webappDir + File.separatorChar + "lib" + File.separatorChar + ejbjarName + "-tags.jar";
      String classDir = webappDir + File.separatorChar + "classes";
      String tld = webappDir + File.separatorChar + ejbjarName + "-tags.tld";
      fs.setSaveJarFile(saveJarFile);
      fs.setSaveDirClassDir(classDir);
      fs.setSaveDirTldFile(tld);
      BeanDescriptor[] beans = new BeanDescriptor[l.size()];
      Iterator I = l.iterator();
      ClassLoader cl = ret.getClassLoader();

      for(int i = 0; i < beans.length; ++i) {
         beans[i] = createBeanDescriptor((EJBean)I.next(), cl);
      }

      ret.setBeans(beans);
      if (beans.length > 0) {
         String ejbpkg = beans[0].getRemoteType();
         ind = ejbpkg.lastIndexOf(46);
         if (ind > 0) {
            ejbpkg = ejbpkg.substring(0, ind);
         }

         ejbpkg = ejbpkg + ".jsp_tags";
         fs.setPackage(ejbpkg);
      }

      ret.setEnableBaseEJB(false);
      return ret;
   }

   private static BeanDescriptor createBeanDescriptor(EJBean ejbean, ClassLoader cl) throws Exception {
      BeanDescriptor ret = new BeanDescriptor();
      ret.setRemoteType(ejbean.getRemoteInterfaceName());
      ret.setHomeType(ejbean.getHomeInterfaceName());
      ret.setJNDIName(ejbean.getJNDIName());
      ret.setEJBName(ejbean.getEJBName());
      String ejbtype = null;
      if (ejbean.isEntityBean()) {
         ejbtype = "ENTITY";
      } else if (ejbean.isStatefulSessionBean()) {
         ejbtype = "STATEFUL";
      } else {
         ejbtype = "STATELESS";
      }

      ret.setEJBType(ejbtype);
      Class remoteType = cl.loadClass(ejbean.getRemoteInterfaceName());
      Class homeType = cl.loadClass(ejbean.getHomeInterfaceName());
      Enumeration e = ReflectUtils.distinctInterfaceMethods(remoteType);
      List mds = new ArrayList();

      while(e.hasMoreElements()) {
         Method m = (Method)e.nextElement();
         EJBMethodDescriptor emd = method2descriptor(m);
         emd.setTargetType(ejbean.getRemoteInterfaceName());
         mds.add(emd);
      }

      EJBMethodDescriptor[] methodArray = new EJBMethodDescriptor[mds.size()];
      mds.toArray(methodArray);
      ret.setEJBMethods(methodArray);
      if (ret.isStatefulBean()) {
         mds.clear();
         e = ReflectUtils.distinctInterfaceMethods(homeType);

         while(e.hasMoreElements()) {
            Method m = (Method)e.nextElement();
            EJBMethodDescriptor emd = method2descriptor(m);
            emd.setTargetType(ejbean.getHomeInterfaceName());
            emd.setTagName("home-" + emd.getTagName());
            mds.add(emd);
         }

         methodArray = new EJBMethodDescriptor[mds.size()];
         mds.toArray(methodArray);
         ret.setHomeMethods(methodArray);
      }

      ret.resolveBaseMethods();
      return ret;
   }

   public static String unArray(Class c) {
      int depth;
      for(depth = 0; c.isArray(); c = c.getComponentType()) {
         ++depth;
      }

      String name;
      for(name = c.getName(); depth > 0; --depth) {
         name = name + "[]";
      }

      return name;
   }

   static EJBMethodDescriptor method2descriptor(Method m) {
      EJBMethodDescriptor ret = new EJBMethodDescriptor();
      ret.setName(m.getName());
      ret.setTagName(m.getName());
      ret.setInfo("");
      String rtype = unArray(m.getReturnType());
      ret.setReturnType(rtype);
      ret.setParams(method2params(m));
      return ret;
   }

   static MethodParamDescriptor[] method2params(Method m) {
      Class[] args = m.getParameterTypes();
      if (args == null) {
         return new MethodParamDescriptor[0];
      } else {
         MethodParamDescriptor[] ret = new MethodParamDescriptor[args.length];

         for(int i = 0; i < args.length; ++i) {
            MethodParamDescriptor mpd = ret[i] = new MethodParamDescriptor();
            mpd.setType(unArray(args[i]));
            mpd.setName("arg" + i);
            mpd.setDefault("NONE");
            mpd.setDefaultValue("");
            mpd.setDefaultMethod("");
         }

         return ret;
      }
   }

   public static void resolveSources(EJBTaglibDescriptor ejbt) throws Exception {
      ClassLoader cl = ejbt.getClassLoader();
      String[] sourcePath = ejbt.getFileInfo().getSourcePath();
      BeanDescriptor[] beans = ejbt.getBeans();

      for(int i = 0; beans != null && i < beans.length; ++i) {
         BeanDescriptor desc = beans[i];
         System.out.println("[ejb2jsp] resolving sources for bean " + desc.getEJBName() + ":");
         Class type = cl.loadClass(desc.getRemoteType());
         resolveSources(desc, type, sourcePath);
         type = cl.loadClass(desc.getHomeType());
         resolveSources(desc, type, sourcePath);
         EJBMethodDescriptor[] unr = desc.getUnresolvedMethods();
         if (unr != null && unr.length > 0) {
            System.err.println("[ejb2jsp] WARNING: the following methods are unresolved: ");

            for(int j = 0; j < unr.length; ++j) {
               System.err.println(" " + unr[j].getSignature() + " on type " + unr[j].getTargetType());
            }
         }
      }

   }

   private static void resolveSources(BeanDescriptor desc, Class root, String[] sourcePath) throws Exception {
      Enumeration e = ReflectUtils.allInterfaces(root);

      do {
         if (!isBaseEJBClass(root)) {
            try {
               resolveSingleSource(desc, root, sourcePath);
            } catch (Exception var5) {
               System.err.println("[ejb2jsp] WARNING: cannot resolve source file(s): " + var5);
            }
         }
      } while(e.hasMoreElements() && (root = (Class)e.nextElement()) != null);

   }

   private static void resolveSingleSource(BeanDescriptor desc, Class c, String[] sourcePath) throws Exception {
      InputStream is = findSource(c, sourcePath);

      try {
         EJB2JSPLexer lex = new EJB2JSPLexer(is);
         lex.setDescriptor(desc);
         lex.parse();
      } finally {
         is.close();
      }

   }

   private static InputStream findSource(Class c, String[] sp) throws FileNotFoundException {
      String cname = c.getName().replace('.', File.separatorChar) + ".java";
      String simpleName = cname;
      int ind = cname.lastIndexOf(File.separatorChar);
      if (ind > 0) {
         simpleName = cname.substring(ind + 1);
      }

      for(int i = 0; i < sp.length; ++i) {
         sp[i] = sp[i].replace('/', File.separatorChar);
         File f = new File(sp[i], cname);
         if (f.exists()) {
            return new FileInputStream(f);
         }

         f = new File(sp[i], simpleName);
         if (f.exists()) {
            return new FileInputStream(f);
         }
      }

      throw new FileNotFoundException("cannot resolve " + cname + " or " + simpleName + " in path " + ArrayUtils.toString(sp));
   }

   static boolean isBaseEJBClass(Class c) {
      return isBaseEJBClass(c.getName());
   }

   static boolean isBaseEJBClass(String c) {
      return "javax.ejb.EJBObject".equals(c) || "javax.ejb.EJBHome".equals(c) || "java.rmi.Remote".equals(c);
   }

   static boolean isBaseEJBMethod(Method m) {
      return isBaseEJBClass(m.getDeclaringClass());
   }

   public static boolean isPrimitive(String type) {
      return "boolean".equals(type) || "int".equals(type) || "short".equals(type) || "long".equals(type) || "byte".equals(type) || "char".equals(type) || "float".equals(type) || "double".equals(type);
   }

   public static boolean isPrimitive(Class c) {
      return isPrimitive(c.getName());
   }

   public static boolean isVoid(Class c) {
      return c == Void.class || c == Void.TYPE;
   }

   public static boolean isVoid(String s) {
      return s.equals("void") || s.equals("Void") || s.equals("java.lang.Void");
   }

   public static String primitive2Object(Class propertyType) {
      if (propertyType == Boolean.TYPE) {
         return "Boolean";
      } else if (propertyType == Byte.TYPE) {
         return "Byte";
      } else if (propertyType == Double.TYPE) {
         return "Double";
      } else if (propertyType == Integer.TYPE) {
         return "Integer";
      } else if (propertyType == Float.TYPE) {
         return "Float";
      } else if (propertyType == Long.TYPE) {
         return "Long";
      } else if (propertyType == Character.TYPE) {
         return "Character";
      } else if (propertyType == Short.TYPE) {
         return "Short";
      } else {
         throw new IllegalArgumentException("type " + propertyType.getName() + " not primitive");
      }
   }

   public static String primitive2Object(String p) {
      if (p.equals("boolean")) {
         return "Boolean";
      } else if (p.equals("byte")) {
         return "Byte";
      } else if (p.equals("double")) {
         return "Double";
      } else if (p.equals("int")) {
         return "Integer";
      } else if (p.equals("float")) {
         return "Float";
      } else if (p.equals("long")) {
         return "Long";
      } else if (p.equals("char")) {
         return "Character";
      } else if (p.equals("short")) {
         return "Short";
      } else {
         throw new IllegalArgumentException("type " + p + " not primitive");
      }
   }

   public static String primitive2Object(String propertyType, String v) {
      return "new " + primitive2Object(propertyType) + "(" + v + ")";
   }

   public static String primitive2Object(Class propertyType, String v) {
      return "new " + primitive2Object(propertyType) + "(" + v + ")";
   }
}
