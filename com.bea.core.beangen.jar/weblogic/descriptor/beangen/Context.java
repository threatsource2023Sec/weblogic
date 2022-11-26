package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weblogic.descriptor.annotation.BeanAnnotations;

public class Context {
   public static final ThreadLocal THREAD_CONTEXT = new ThreadLocal();
   private String implSuffix;
   private String packageName;
   private boolean packageNameExplicit;
   private JClass baseClass;
   private JClass baseInterface;
   private JClass baseBeanClass;
   private JClass baseBeanInterface;
   private Logger log;
   private BeanGenOptions options;
   private List excludedClasses;
   private JClass beanInterface;

   public static void initialize(JClass ifc, BeanGenOptions options) {
      THREAD_CONTEXT.set(new Context(ifc, options));
   }

   public static Context get() {
      return (Context)THREAD_CONTEXT.get();
   }

   private Context(JClass ifc, BeanGenOptions options) throws BeanGenerationException {
      this.beanInterface = ifc;
      this.options = options;
      this.packageName = options.getPackage();
      if (this.packageName == null) {
         this.packageName = ifc.getContainingPackage().getQualifiedName();
         this.packageNameExplicit = false;
      } else {
         this.packageNameExplicit = true;
      }

      this.implSuffix = options.getSuffix();
      if (options.getSuffix() != null) {
         this.implSuffix = options.getSuffix();
      } else {
         this.implSuffix = "Impl";
      }

      if (options.getBaseInterfaceName() != null) {
         this.baseBeanInterface = this.loadClass(options.getBaseInterfaceName());
      } else {
         this.baseBeanInterface = JClasses.OBJECT;
      }

      this.baseInterface = this.baseBeanInterface;
      if (options.getBaseClassName() != null) {
         this.baseClass = this.loadClass(options.getBaseClassName());
         this.baseBeanClass = this.baseClass;
         this.log = new Logger();
         this.excludedClasses = new ArrayList();
         String[] excludeList = options.getExcludes();

         for(int count = 0; count < excludeList.length; ++count) {
            if (excludeList[count] != null) {
               int index = excludeList[count].lastIndexOf(".java");
               if (index > -1) {
                  this.excludedClasses.add(excludeList[count].substring(0, index).replace(File.separatorChar, '.'));
               }
            }
         }

      } else {
         throw new BeanGenerationException("must specify -baseClass");
      }
   }

   private JClass loadClass(String name) throws BeanGenerationException {
      try {
         JClass cls = this.beanInterface.getClassLoader().loadClass(name);
         if (cls == null || cls.isUnresolvedType()) {
            cls = JClasses.load(name);
         }

         if (cls.isUnresolvedType()) {
            throw new BeanGenerationException(name + " not found");
         } else {
            return cls;
         }
      } catch (BeanGenerationException var3) {
         var3.printStackTrace();
         throw var3;
      }
   }

   public BeanGenOptions getOptions() {
      return this.options;
   }

   public Logger getLog() {
      return this.log;
   }

   public String interfaceToClassName(JClass ifc, boolean abbreviate, boolean skipPackageSuffix) {
      String suffix = this.implSuffix;
      String name;
      if (ifc.isArrayType()) {
         name = this.interfaceToClassName(ifc.getArrayComponentType(), abbreviate) + "[]";
      } else if (!this.isBean(ifc)) {
         name = ifc.getQualifiedName();
      } else {
         name = this.options.getClassForInterface(ifc.getQualifiedName());
         if (name == null) {
            if (this.isPackageNameExplicit()) {
               name = this.getPackageName() + "." + ifc.getSimpleName() + suffix;
            } else if (this.isPackageSuffix() && !skipPackageSuffix) {
               name = ifc.getQualifiedName();
               int idx = name.lastIndexOf(46);
               if (idx != -1) {
                  name = name.substring(0, idx);
               }

               name = name + "." + this.options.getPackageSuffix() + "." + ifc.getSimpleName() + suffix;
            } else {
               name = ifc.getQualifiedName() + suffix;
            }
         }
      }

      return abbreviate ? this.abbreviateClass(name) : name;
   }

   public String interfaceToClassName(JClass ifc, boolean abbreviate) {
      return this.interfaceToClassName(ifc, abbreviate, false);
   }

   public String interfaceToClassName(JClass ifc) {
      return this.interfaceToClassName(ifc, true);
   }

   public boolean generateLocalValidators() {
      return !this.options.isNoLocalValidation();
   }

   public boolean getNoSynthetics() {
      return this.options.getNoSynthetics();
   }

   public String getBaseBeanInterfaceName() {
      return this.abbreviateClass(this.getBaseBeanInterface().getQualifiedName());
   }

   public JClass getBaseBeanInterface() {
      return this.baseBeanInterface;
   }

   public String getBaseInterfaceName() {
      return this.abbreviateClass(this.getBaseInterface().getQualifiedName());
   }

   public JClass getBaseInterface() {
      return this.baseInterface;
   }

   public String getBaseBeanClassName() {
      return this.abbreviateClass(this.baseBeanClass.getQualifiedName());
   }

   public JClass getBaseBeanClass() {
      return this.baseBeanClass;
   }

   public JClass getBaseClass() {
      return this.baseClass;
   }

   public String getBaseClassName() {
      return this.abbreviateClass(this.baseClass.getQualifiedName());
   }

   public String getPackageName() {
      return this.packageName;
   }

   public boolean isPackageNameExplicit() {
      return this.packageNameExplicit;
   }

   public boolean isPackageSuffix() {
      String suffix = this.options.getPackageSuffix();
      return suffix != null && suffix.trim().length() > 0;
   }

   public boolean isGenerateToCustom() {
      return this.options.isGenerateToCustom();
   }

   public String getSuffix() {
      return this.implSuffix;
   }

   public String getTemplateExtension() {
      return this.options.getTemplateExtension();
   }

   public boolean isBean(JClass clazz) {
      if (this.excludedClasses.contains(clazz.getQualifiedName())) {
         return false;
      } else if (BeanAnnotations.BEAN.isDefined(clazz)) {
         return true;
      } else if (!JClasses.OBJECT.equals(this.baseBeanInterface)) {
         return this.baseBeanInterface.isAssignableFrom(clazz);
      } else {
         return clazz.isInterface() ? this.isBean(clazz.getSimpleName()) : false;
      }
   }

   private boolean isBean(String className) {
      return className.endsWith("Bean");
   }

   public String abbreviateClass(String className) {
      if (this.options.isGenerateToCustom()) {
         return className;
      } else if (className == null) {
         return null;
      } else {
         String[] prefixList;
         if (this.packageName == null) {
            prefixList = new String[]{"java.lang"};
         } else {
            prefixList = new String[]{"java.lang", this.packageName};
         }

         for(int i = 0; i < prefixList.length; ++i) {
            int prefixLength = className.lastIndexOf(46);
            if (className.startsWith(prefixList[i]) && prefixList[i].length() == prefixLength) {
               return className.substring(prefixLength + 1);
            }
         }

         return className;
      }
   }
}
