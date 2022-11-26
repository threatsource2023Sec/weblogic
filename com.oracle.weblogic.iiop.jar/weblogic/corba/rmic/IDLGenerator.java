package weblogic.corba.rmic;

import java.io.File;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.IDLUtils;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class IDLGenerator extends CodeGenerator implements IDLGeneratorOptions {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   static boolean m_idlOverwrite = false;
   static String m_idlDirectory = null;
   static boolean m_idlVerbose = false;
   static String m_idlFile = null;
   static String m_idlMethodSignatures = null;
   static Output m_currentOutput = null;

   public IDLGenerator(Getopt2 opts) {
      super(opts);
      addIDLGeneratorOptions(opts);
   }

   public static void addIDLGeneratorOptions(Getopt2 opts) {
      opts.addFlag("idl", "Generate idl for remote interfaces");
      opts.addFlag("idlOverwrite", "Always overwrite existing IDL files");
      opts.addFlag("idlVerbose", "Display verbose information for IDL generation");
      opts.addFlag("idlStrict", "Generate IDL according to the OMG standard");
      opts.addFlag("idlFactories", "Generate factory methods for valuetypes");
      opts.addFlag("idlNoValueTypes", "Do not generate valuetypes and methods/attributes that contain them.");
      opts.addFlag("idlNoAbstractInterfaces", "Do not generate abstract interfaces and methods/attributes that contain them.");
      opts.addFlag("idlCompatibility", "Substitute structs for value types to generate CORBA 2.2 compatible IDL.");
      opts.markPrivate("idlCompatibility");
      opts.addOption("idlMethodSignatures", "", "Specify the method signatures used to trigger idl code generation.");
      opts.addFlag("idlVisibroker", "Generate IDL somewhat compatible with Visibroker 4.5 C++");
      opts.addFlag("idlOrbix", "Generate IDL somewhat compatible with Orbix 2000 2.0 C++");
      opts.addOption("idlDirectory", "directory", "Specify the directory where IDL files will be created (default : current directory)");
   }

   public String impl_class_type() {
      return m_currentOutput.getIDLType().getClassName();
   }

   public String forward_references_iterator() {
      StringBuffer result = new StringBuffer();

      try {
         IDLType t = m_currentOutput.getIDLType();
         Hashtable ref = new Hashtable();
         t.getReferences(ref);
         Enumeration e = ref.elements();

         while(e.hasMoreElements()) {
            Object obj = e.nextElement();
            IDLType type = (IDLType)obj;
            Class refclass = type.getJavaClass();
            if (refclass.isAssignableFrom(t.getJavaClass())) {
               result.append(IDLUtils.generateInclude(type.getDirectory(), refclass));
            } else {
               result.append(type.getForwardDeclaration());
            }
         }
      } catch (CodeGenerationException var8) {
         result.append("/*\n" + var8.toString() + "\n*/\n");
      }

      if (this.getIDLVerbose()) {
         result.append("\n// Generated from " + m_currentOutput.getIDLType() + "\n");
      }

      return result.toString();
   }

   public String guard_name() {
      IDLType type = m_currentOutput.getIDLType();
      return type.getGuardName("");
   }

   public String main_declaration() {
      StringBuffer result = new StringBuffer("");
      IDLType type = m_currentOutput.getIDLType();
      Class c = type.getJavaClass();

      try {
         boolean required = type.isRequired();
         if (!required) {
            result.append("// valuetypes or abstract interfaces are excluded\n");
            result.append("/*\n");
         }

         result.append(IDLUtils.openModule(c));
         result.append(type.getOpeningDeclaration());
         result.append(type.getOpenBrace());
         result.append(type.generateAttributes());
         result.append(type.generateMethods());
         result.append(type.generateExtraLines());
         result.append("\n" + type.getPragmaID() + "\n");
         result.append(type.getCloseBrace());
         result.append(IDLUtils.closeModule(c));
         if (!required) {
            result.append("*/\n");
            result.append("// valuetypes or abstract interfaces are excluded\n");
         }
      } catch (CodeGenerationException var5) {
      }

      return result.toString();
   }

   public String include_iterator() {
      StringBuffer result = new StringBuffer();

      try {
         IDLType t = m_currentOutput.getIDLType();
         Hashtable ref = new Hashtable();
         t.getReferences(ref);
         Enumeration e = ref.elements();

         while(e.hasMoreElements()) {
            Object obj = e.nextElement();
            IDLType type = (IDLType)obj;
            if (type.isRequired() && !type.isAssignableFrom(t)) {
               result.append(type.getIncludeDeclaration());
            }
         }
      } catch (CodeGenerationException var7) {
         result.append("/*\n" + var7.toString() + "\n*/\n");
      }

      return result.toString();
   }

   public String before_main_declaration() throws CodeGenerationException {
      IDLType type = m_currentOutput.getIDLType();
      return type.beforeMainDeclaration();
   }

   public String after_main_declaration() throws CodeGenerationException {
      IDLType type = m_currentOutput.getIDLType();
      return type.afterMainDeclaration();
   }

   protected void extractOptionValues(Getopt2 opts) {
      m_idlOverwrite = opts.hasOption("idlOverwrite");
      m_idlDirectory = opts.getOption("idlDirectory", (String)null);
      if (null == m_idlDirectory) {
         m_idlDirectory = super.getRootDirectoryName();
      } else {
         this.setRootDirectoryName(m_idlDirectory);
      }

      m_idlVerbose = opts.hasOption("idlVerbose");
      m_idlFile = opts.getOption("idlFile", (String)null);
      m_idlMethodSignatures = opts.getOption("idlMethodSignatures", (String)null);
      IDLOptions.setIDLStrict(opts.hasOption("idlStrict"));
      IDLOptions.setFactories(opts.hasOption("idlFactories"));
      IDLOptions.setNoValueTypes(opts.hasOption("idlNoValueTypes"));
      IDLOptions.setNoAbstract(opts.hasOption("idlNoAbstractInterfaces"));
      IDLOptions.setCompatibility(opts.hasOption("idlCompatibility"));
      IDLOptions.setVisibroker(opts.hasOption("idlVisibroker"));
      IDLOptions.setOrbix(opts.hasOption("idlOrbix"));
   }

   public String getIDLFile() {
      return m_idlFile;
   }

   String getIDLDirectory() {
      return m_idlDirectory != null ? m_idlDirectory.replace('/', File.separatorChar).replace('\\', File.separatorChar) : null;
   }

   public String getRootDirectoryName() {
      return this.getIDLDirectory();
   }

   boolean getIDLOverwrite() {
      return m_idlOverwrite;
   }

   boolean getIDLVerbose() {
      return m_idlVerbose;
   }

   boolean getIDLStrict() {
      return IDLOptions.getIDLStrict();
   }

   String getIDLMethodSignatures() {
      return m_idlMethodSignatures;
   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      String[] classes = (String[])((String[])inputs);
      Hashtable idlFiles = new Hashtable();
      Hashtable outputs = new Hashtable();
      Hashtable processedFiles = new Hashtable();
      IDLType.resetUsedTypes();
      this.processOverrides();

      for(int i = 0; i < classes.length; ++i) {
         Class c = Utilities.classForName(classes[i]);
         Class remoteInterface = IDLUtils.getRemoteInterface(c);
         if (remoteInterface != null) {
            IDLType t = new IDLTypeRemote(remoteInterface, (Class)null);
            idlFiles.put(t.getFileName(), t);
            Class[] allClasses = IDLUtils.getClasses(remoteInterface, false, true);

            for(int j = 0; j < allClasses.length; ++j) {
               IDLType.createType(allClasses[j], remoteInterface);
            }
         } else {
            IDLType.createType(c, (Class)null);
         }

         Enumeration e = IDLType.getUsedTypes().elements();

         IDLType t;
         while(e.hasMoreElements()) {
            t = (IDLType)e.nextElement();
            if (t.isRequired()) {
               idlFiles.put(t.getFileName(), t);
            }
         }

         while(!idlFiles.isEmpty()) {
            this.generateIDLFiles(idlFiles);
            processedFiles.putAll(idlFiles);
            idlFiles.clear();
            e = IDLType.getUsedTypes().elements();

            while(e.hasMoreElements()) {
               t = (IDLType)e.nextElement();
               if (!processedFiles.containsKey(t.getFileName()) && t.isRequired()) {
                  idlFiles.put(t.getFileName(), t);
               }
            }
         }
      }

      return outputs.elements();
   }

   void processOverrides() throws Exception {
      IDLType.clearOverrides();
      if (this.getIDLMethodSignatures() != null) {
         StringTokenizer stkn = new StringTokenizer(this.getIDLMethodSignatures(), ";", false);
         StringBuffer text = new StringBuffer("(idlMethodSignatures");

         while(stkn.hasMoreTokens()) {
            text.append("(").append(stkn.nextToken()).append("generate true)");
         }

         text.append(")");
         String parseText = text.toString();
         StructureTokenizer st = new StructureTokenizer(new StringReader(text.toString()));
         this.createOverrides(st.parseStructure());
      }
   }

   void createOverrides(Structure s) {
      Hashtable elements = s.elements;
      Enumeration e = elements.keys();

      for(int i = 0; e.hasMoreElements(); ++i) {
         String methodName = (String)e.nextElement();
         String methodParams = null;
         Hashtable mh = (Hashtable)elements.get(methodName);
         Enumeration mhKeys = mh.keys();

         for(int j = 0; mhKeys.hasMoreElements(); ++j) {
            Object o = mhKeys.nextElement();
            if (o instanceof String) {
               String key = (String)o;
               if (key.compareToIgnoreCase("generate") == 0) {
                  String action = (String)mh.get(key);
                  if (action.compareToIgnoreCase("true") == 0) {
                  }
               }

               methodParams = key;
            }
         }

         IDLType.registerOverride(methodName, methodParams);
      }

   }

   public void generateIDLFiles(Hashtable idlFiles) {
      Enumeration e = idlFiles.elements();

      while(e.hasMoreElements()) {
         IDLType t = (IDLType)e.nextElement();
         String dir = this.getIDLDirectory();
         String fileName = t.getFileName();
         if (null != dir) {
            fileName = dir + File.separatorChar + fileName;
         }

         boolean create = CorbaUtils.isARemote(t.getJavaClass()) | this.getIDLOverwrite();
         if (!create) {
            File f = new File(fileName);
            if (!f.exists()) {
               create = true;
            }
         }

         if (create) {
            if (this.getIDLVerbose()) {
               System.out.println("Generating " + fileName + " for type " + t);
            }

            Output o = new Output(t.getFileName(), "idl.j", t.getPackageName(), t);

            try {
               this.prepare(o);
               this.generateCode(o);
               this.cleanup();
            } catch (Exception var9) {
            }
         } else if (this.getIDLVerbose()) {
            System.out.println("Skipping " + fileName);
         }
      }

   }

   protected void prepare(CodeGenerator.Output output) throws Exception {
      m_currentOutput = (Output)output;
      super.prepare(output);
   }

   public static IDLType getCurrentType() {
      return m_currentOutput.getIDLType();
   }

   private static class Output extends CodeGenerator.Output {
      private IDLType m_type;

      Output(String outputFile, String template, String pkg, IDLType type) {
         super(outputFile, template, pkg);
         this.m_type = type;
      }

      public IDLType getIDLType() {
         return this.m_type;
      }
   }
}
