package weblogic.descriptor.descriptorgen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamService;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import com.bea.util.jam.visitor.MVisitor;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import weblogic.descriptor.codegen.AnnotatableClass;
import weblogic.descriptor.codegen.AnnotatableMethod;
import weblogic.utils.StringUtils;

public class XSDContextCollection extends AbstractCollection {
   XSD2Java codeGenerator;
   XSD2JavaOptions options;
   JClass[] classes;
   int index = 0;
   JClass[] ignored;
   JClass baseInterface;

   public XSDContextCollection(XSD2Java codeGenerator) throws IOException {
      this.codeGenerator = codeGenerator;
      this.options = (XSD2JavaOptions)codeGenerator.getOpts();
      this.initClasses();
      this.initOriginals();
      this.initMBeans();
   }

   void initClasses() throws IOException {
      JamServiceFactory factory = JamServiceFactory.getInstance();
      System.out.println("Using sourceCodeDirectory = " + this.options.getSourceDir());
      JamClassLoader cl = XSD2Java.systemCL;
      this.baseInterface = cl.loadClass("com.bea.xml.XmlObject");
      String[] inames = new String[]{"com.sun.java.xml.ns.j2Ee.XsdStringType", "com.bea.xml.XmlAnyURI", "com.bea.xml.XmlBoolean", "com.bea.xml.XmlDecimal", "com.bea.xml.XmlInteger", "com.bea.xml.XmlNMTOKEN", "com.bea.xml.XmlString", "com.bea.xml.XmlQName"};
      this.ignored = new JClass[inames.length];

      for(int i = 0; i < this.ignored.length; ++i) {
         this.ignored[i] = cl.loadClass(inames[i]);
      }

      String[] sources = this.options.getSources();
      ArrayList classList = new ArrayList();

      for(int i = 0; i < sources.length; ++i) {
         if (sources[i].endsWith(".jar")) {
            this.addSourcesFormJar(sources[i], cl, classList);
         } else {
            this.addSources(sources[i], this.options.getSourceDir(), factory, classList);
         }
      }

      ArrayList cList = new ArrayList();
      Iterator iterator = classList.iterator();

      while(iterator.hasNext()) {
         JClass c = (JClass)iterator.next();
         if (c.isInterface() && this.baseInterface.isAssignableFrom(c) && !this.isIgnored(c)) {
            cList.add(c);
         }
      }

      this.classes = new JClass[cList.size()];
      cList.toArray(this.classes);
   }

   void initOriginals() throws IOException {
      JamServiceFactory factory = JamServiceFactory.getInstance();
      System.out.println("extracting originals from = " + this.options.getOriginalsPath());
      JamClassLoader cl = factory.createSystemJamClassLoader();
      String sources = this.options.getOriginalsPackages();
      ArrayList classList = new ArrayList();
      this.addSources(sources, this.options.getOriginalsPath(), factory, classList);
      JClass[] originals = new JClass[classList.size()];
      classList.toArray(originals);
      this.codeGenerator.setOriginals(originals);
   }

   void initMBeans() throws IOException {
      String specName = this.options.getMBeanSpec();
      if (specName != null) {
         String sources = this.options.getMBeanPackages();
         System.out.println("extracting mbeans from = " + this.options.getOriginalsPath() + "/" + sources);
         JamServiceFactory factory = JamServiceFactory.getInstance();
         ArrayList classList = new ArrayList();
         this.addSources(sources, this.options.getOriginalsPath(), factory, classList);
         HashMap tmpMBean = new HashMap();
         Iterator i = classList.iterator();

         while(i.hasNext()) {
            JClass mb = (JClass)i.next();
            tmpMBean.put(mb.getQualifiedName(), mb);
         }

         File fSpec = new File(specName);
         BufferedReader spec = null;

         try {
            spec = new BufferedReader(new FileReader(fSpec));
            File dir = new File(this.options.getOriginalsPath());
            HashMap mBeanMap = new HashMap();
            int lineNo = 0;

            while(true) {
               while(true) {
                  String line;
                  do {
                     if (!spec.ready()) {
                        XSD2Java var10000 = this.codeGenerator;
                        XSD2Java.setMBeans(mBeanMap);
                        return;
                     }

                     line = spec.readLine();
                     ++lineNo;
                  } while(line.startsWith("#"));

                  String[] tokens = StringUtils.splitCompletely(line, "=", false);
                  String jName = tokens[1].trim().replace('.', '/') + ".java";
                  File fd = new File(dir, jName);
                  String mBeanName;
                  JClass jMBean;
                  if (fd.exists()) {
                     mBeanName = tokens[1].trim();
                     jMBean = (JClass)tmpMBean.get(mBeanName);
                     if (jMBean == null) {
                        throw new AssertionError("MBean class (line # " + lineNo + ") not found: " + mBeanName);
                     }

                     mBeanMap.put(tokens[0], new AnnotatableClass(jMBean));
                  } else {
                     mBeanName = tokens[1];
                     mBeanName = mBeanName.substring(0, mBeanName.lastIndexOf(46));
                     jMBean = (JClass)tmpMBean.get(mBeanName);
                     if (jMBean == null) {
                        throw new AssertionError("MBean class not found: " + mBeanName);
                     }

                     JMethod[] methods = jMBean.getMethods();
                     boolean foundMatch = false;

                     for(int i = 0; i < methods.length; ++i) {
                        String mName = methods[i].getQualifiedName();
                        if (mName.equals(tokens[1])) {
                           foundMatch = true;
                           AnnotatableMethod m = new AnnotatableMethod(methods[i]);
                           mBeanMap.put(tokens[0], m);
                        }
                     }

                     if (!foundMatch) {
                        throw new AssertionError("MBean method (line # " + lineNo + ")\n " + tokens[1] + "\n not found on class name:\n " + mBeanName);
                     }
                  }
               }
            }
         } finally {
            if (spec != null) {
               try {
                  spec.close();
               } catch (IOException var27) {
               }
            }

         }
      }
   }

   void addSources(String sources, String dirName, JamServiceFactory factory, ArrayList classList) throws IOException {
      File dir = new File(dirName);
      JamServiceParams params = factory.createServiceParams();
      params.setPropertyInitializer((MVisitor)null);
      File fd = new File(dir, sources);
      if (fd.isDirectory()) {
         this.addSubdirs(params, fd, dirName);
      } else {
         if (!sources.endsWith(".java")) {
            return;
         }

         boolean addIt = true;
         String[] excludes = this.options.getExcludes();

         for(int j = 0; j < excludes.length; ++j) {
            if (excludes[j].equals(sources)) {
               addIt = false;
               break;
            }
         }

         if (addIt) {
            params.includeSourceFile(fd);
         }
      }

      params.setLoggerWriter(new PrintWriter(new ByteArrayOutputStream(), true));
      JamService service = factory.createService(params);
      classList.addAll(Arrays.asList((Object[])service.getAllClasses()));
   }

   void addSubdirs(JamServiceParams params, File dir, String sourceDir) {
      File[] files = dir.listFiles(new FileFilter() {
         public boolean accept(File f) {
            if (f.isDirectory()) {
               return true;
            } else {
               return f.getName().endsWith(".java");
            }
         }
      });

      for(int i = 0; i < files.length; ++i) {
         if (files[i].isDirectory()) {
            this.addSubdirs(params, files[i], sourceDir);
         } else {
            boolean addIt = true;
            String[] excludes = this.options.getExcludes();

            for(int j = 0; j < excludes.length; ++j) {
               if (excludes[j].equals(this.getPath(sourceDir, files[i]))) {
                  addIt = false;
                  break;
               }
            }

            if (addIt) {
               params.includeSourceFile(files[i]);
            }
         }
      }

   }

   String getPath(String dir, File file) {
      String dp = (new File(dir)).getPath();
      String fp = file.getPath();
      return fp.substring(dp.length() + 1);
   }

   void addSourcesFormJar(String xsdFileName, JamClassLoader cl, ArrayList classList) throws IOException {
      JarFile xsdFile = new JarFile(xsdFileName);
      Enumeration e = xsdFile.entries();

      while(true) {
         ZipEntry ze;
         do {
            do {
               if (!e.hasMoreElements()) {
                  return;
               }

               ze = (ZipEntry)e.nextElement();
            } while(ze.getName().endsWith("/"));
         } while(!ze.getName().startsWith("com/sun/java/xml/ns/j2Ee/") && !ze.getName().startsWith("com/bea/ns/weblogic/x90/") && !ze.getName().startsWith("com/bea/ns/weblogic/x60/"));

         JClass c = cl.loadClass(ze.getName().substring(0, ze.getName().lastIndexOf(46)).replace('/', '.'));
         if (c.isInterface() && this.baseInterface.isAssignableFrom(c) && !this.isIgnored(c)) {
            classList.add(c);
         }
      }
   }

   boolean isIgnored(JClass c) {
      for(int i = 0; i < this.ignored.length; ++i) {
         if (this.ignored[i].isAssignableFrom(c)) {
            return true;
         }
      }

      return false;
   }

   public Iterator iterator() {
      return new ContextIterator(this.classes);
   }

   public int size() {
      return this.classes.length;
   }

   class ContextIterator implements Iterator {
      JClass[] classes;
      int index = 0;

      ContextIterator(JClass[] classes) {
         this.classes = classes;
      }

      public boolean hasNext() {
         return this.index < this.classes.length;
      }

      public Object next() {
         return XSDContextCollection.this.codeGenerator.getCodeGeneratorContext(this.classes[this.index++]);
      }

      public void remove() {
         this.classes[this.index] = null;
      }
   }
}
