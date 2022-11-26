package org.antlr.stringtemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.antlr.stringtemplate.language.GroupLexer;
import org.antlr.stringtemplate.language.GroupParser;

public class StringTemplateGroup {
   protected String name;
   protected Map templates;
   protected Map maps;
   protected Class templateLexerClass;
   protected static Class defaultTemplateLexerClass;
   protected String rootDir;
   protected static Map nameToGroupMap;
   protected static Map nameToInterfaceMap;
   protected StringTemplateGroup superGroup;
   protected List interfaces;
   protected boolean templatesDefinedInGroupFile;
   protected Class userSpecifiedWriter;
   protected boolean debugTemplateOutput;
   protected Set noDebugStartStopStrings;
   protected Map attributeRenderers;
   private static StringTemplateGroupLoader groupLoader;
   protected StringTemplateErrorListener listener;
   public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER;
   protected static final StringTemplate NOT_FOUND_ST;
   protected int refreshIntervalInSeconds;
   protected long lastCheckedDisk;
   String fileCharEncoding;

   public StringTemplateGroup(String name, String rootDir) {
      this(name, rootDir, DefaultTemplateLexer.class);
   }

   public StringTemplateGroup(String name, String rootDir, Class lexer) {
      this.templates = new HashMap();
      this.maps = new HashMap();
      this.templateLexerClass = null;
      this.rootDir = null;
      this.superGroup = null;
      this.interfaces = null;
      this.templatesDefinedInGroupFile = false;
      this.debugTemplateOutput = false;
      this.listener = DEFAULT_ERROR_LISTENER;
      this.refreshIntervalInSeconds = 2147483;
      this.lastCheckedDisk = 0L;
      this.fileCharEncoding = System.getProperty("file.encoding");
      this.name = name;
      this.rootDir = rootDir;
      this.lastCheckedDisk = System.currentTimeMillis();
      nameToGroupMap.put(name, this);
      this.templateLexerClass = lexer;
   }

   public StringTemplateGroup(String name) {
      this((String)name, (String)null, (Class)null);
   }

   public StringTemplateGroup(String name, Class lexer) {
      this((String)name, (String)null, (Class)lexer);
   }

   public StringTemplateGroup(Reader r) {
      this(r, AngleBracketTemplateLexer.class, DEFAULT_ERROR_LISTENER, (StringTemplateGroup)null);
   }

   public StringTemplateGroup(Reader r, StringTemplateErrorListener errors) {
      this(r, AngleBracketTemplateLexer.class, errors, (StringTemplateGroup)null);
   }

   public StringTemplateGroup(Reader r, Class lexer) {
      this(r, lexer, (StringTemplateErrorListener)null, (StringTemplateGroup)null);
   }

   public StringTemplateGroup(Reader r, Class lexer, StringTemplateErrorListener errors) {
      this(r, lexer, errors, (StringTemplateGroup)null);
   }

   public StringTemplateGroup(Reader r, Class lexer, StringTemplateErrorListener errors, StringTemplateGroup superGroup) {
      this.templates = new HashMap();
      this.maps = new HashMap();
      this.templateLexerClass = null;
      this.rootDir = null;
      this.superGroup = null;
      this.interfaces = null;
      this.templatesDefinedInGroupFile = false;
      this.debugTemplateOutput = false;
      this.listener = DEFAULT_ERROR_LISTENER;
      this.refreshIntervalInSeconds = 2147483;
      this.lastCheckedDisk = 0L;
      this.fileCharEncoding = System.getProperty("file.encoding");
      this.templatesDefinedInGroupFile = true;
      if (lexer == null) {
         lexer = AngleBracketTemplateLexer.class;
      }

      this.templateLexerClass = lexer;
      if (errors != null) {
         this.listener = errors;
      }

      this.setSuperGroup(superGroup);
      this.parseGroup(r);
      nameToGroupMap.put(this.name, this);
      this.verifyInterfaceImplementations();
   }

   public Class getTemplateLexerClass() {
      return this.templateLexerClass != null ? this.templateLexerClass : defaultTemplateLexerClass;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setSuperGroup(StringTemplateGroup superGroup) {
      this.superGroup = superGroup;
   }

   public void setSuperGroup(String superGroupName) {
      StringTemplateGroup superGroup = (StringTemplateGroup)nameToGroupMap.get(superGroupName);
      if (superGroup != null) {
         this.setSuperGroup(superGroup);
      } else {
         superGroup = loadGroup(superGroupName, this.templateLexerClass, (StringTemplateGroup)null);
         if (superGroup != null) {
            nameToGroupMap.put(superGroupName, superGroup);
            this.setSuperGroup(superGroup);
         } else if (groupLoader == null) {
            this.listener.error("no group loader registered", (Throwable)null);
         }

      }
   }

   public void implementInterface(StringTemplateGroupInterface I) {
      if (this.interfaces == null) {
         this.interfaces = new ArrayList();
      }

      this.interfaces.add(I);
   }

   public void implementInterface(String interfaceName) {
      StringTemplateGroupInterface I = (StringTemplateGroupInterface)nameToInterfaceMap.get(interfaceName);
      if (I != null) {
         this.implementInterface(I);
      } else {
         I = loadInterface(interfaceName);
         if (I != null) {
            nameToInterfaceMap.put(interfaceName, I);
            this.implementInterface(I);
         } else if (groupLoader == null) {
            this.listener.error("no group loader registered", (Throwable)null);
         }

      }
   }

   public StringTemplateGroup getSuperGroup() {
      return this.superGroup;
   }

   public String getGroupHierarchyStackString() {
      List groupNames = new LinkedList();

      for(StringTemplateGroup p = this; p != null; p = p.superGroup) {
         groupNames.add(0, p.name);
      }

      return groupNames.toString().replaceAll(",", "");
   }

   public String getRootDir() {
      return this.rootDir;
   }

   public void setRootDir(String rootDir) {
      this.rootDir = rootDir;
   }

   public StringTemplate createStringTemplate() {
      StringTemplate st = new StringTemplate();
      return st;
   }

   protected StringTemplate getInstanceOf(StringTemplate enclosingInstance, String name) throws IllegalArgumentException {
      StringTemplate st = this.lookupTemplate(enclosingInstance, name);
      if (st != null) {
         StringTemplate instanceST = st.getInstanceOf();
         return instanceST;
      } else {
         return null;
      }
   }

   public StringTemplate getInstanceOf(String name) {
      return this.getInstanceOf((StringTemplate)null, (String)name);
   }

   public StringTemplate getInstanceOf(String name, Map attributes) {
      StringTemplate st = this.getInstanceOf(name);
      st.attributes = attributes;
      return st;
   }

   public StringTemplate getEmbeddedInstanceOf(StringTemplate enclosingInstance, String name) throws IllegalArgumentException {
      StringTemplate st = null;
      if (name.startsWith("super.")) {
         st = enclosingInstance.getNativeGroup().getInstanceOf(enclosingInstance, name);
      } else {
         st = this.getInstanceOf(enclosingInstance, name);
      }

      st.setGroup(this);
      st.setEnclosingInstance(enclosingInstance);
      return st;
   }

   public synchronized StringTemplate lookupTemplate(StringTemplate enclosingInstance, String name) throws IllegalArgumentException {
      if (name.startsWith("super.")) {
         if (this.superGroup != null) {
            int dot = name.indexOf(46);
            name = name.substring(dot + 1, name.length());
            StringTemplate superScopeST = this.superGroup.lookupTemplate(enclosingInstance, name);
            return superScopeST;
         } else {
            throw new IllegalArgumentException(this.getName() + " has no super group; invalid template: " + name);
         }
      } else {
         this.checkRefreshInterval();
         StringTemplate st = (StringTemplate)this.templates.get(name);
         if (st == null) {
            if (!this.templatesDefinedInGroupFile) {
               st = this.loadTemplateFromBeneathRootDirOrCLASSPATH(this.getFileNameFromTemplateName(name));
            }

            if (st == null && this.superGroup != null) {
               st = this.superGroup.getInstanceOf(name);
               if (st != null) {
                  st.setGroup(this);
               }
            }

            if (st == null) {
               this.templates.put(name, NOT_FOUND_ST);
               String context = "";
               if (enclosingInstance != null) {
                  context = "; context is " + enclosingInstance.getEnclosingInstanceStackString();
               }

               String hier = this.getGroupHierarchyStackString();
               context = context + "; group hierarchy is " + hier;
               throw new IllegalArgumentException("Can't find template " + this.getFileNameFromTemplateName(name) + context);
            }

            this.templates.put(name, st);
         } else if (st == NOT_FOUND_ST) {
            return null;
         }

         return st;
      }
   }

   public StringTemplate lookupTemplate(String name) {
      return this.lookupTemplate((StringTemplate)null, name);
   }

   protected void checkRefreshInterval() {
      if (!this.templatesDefinedInGroupFile) {
         boolean timeToFlush = this.refreshIntervalInSeconds == 0 || System.currentTimeMillis() - this.lastCheckedDisk >= (long)(this.refreshIntervalInSeconds * 1000);
         if (timeToFlush) {
            this.templates.clear();
            this.lastCheckedDisk = System.currentTimeMillis();
         }

      }
   }

   protected StringTemplate loadTemplate(String name, BufferedReader r) throws IOException {
      String nl = System.getProperty("line.separator");
      StringBuffer buf = new StringBuffer(300);

      String line;
      while((line = r.readLine()) != null) {
         buf.append(line);
         buf.append(nl);
      }

      String pattern = buf.toString().trim();
      if (pattern.length() == 0) {
         this.error("no text in template '" + name + "'");
         return null;
      } else {
         return this.defineTemplate(name, pattern);
      }
   }

   protected StringTemplate loadTemplateFromBeneathRootDirOrCLASSPATH(String fileName) {
      StringTemplate template = null;
      String name = this.getTemplateNameFromFileName(fileName);
      if (this.rootDir == null) {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         InputStream is = cl.getResourceAsStream(fileName);
         if (is == null) {
            cl = this.getClass().getClassLoader();
            is = cl.getResourceAsStream(fileName);
         }

         if (is == null) {
            return null;
         } else {
            BufferedReader br = null;

            try {
               br = new BufferedReader(this.getInputStreamReader(is));
               template = this.loadTemplate(name, br);
            } catch (IOException var16) {
               this.error("Problem reading template file: " + fileName, var16);
            } finally {
               if (br != null) {
                  try {
                     br.close();
                  } catch (IOException var15) {
                     this.error("Cannot close template file: " + fileName, var15);
                  }
               }

            }

            return template;
         }
      } else {
         template = this.loadTemplate(name, this.rootDir + "/" + fileName);
         return template;
      }
   }

   public String getFileNameFromTemplateName(String templateName) {
      return templateName + ".st";
   }

   public String getTemplateNameFromFileName(String fileName) {
      String name = fileName;
      int suffix = fileName.lastIndexOf(".st");
      if (suffix >= 0) {
         name = fileName.substring(0, suffix);
      }

      return name;
   }

   protected StringTemplate loadTemplate(String name, String fileName) {
      BufferedReader br = null;
      StringTemplate template = null;

      try {
         InputStream fin = new FileInputStream(fileName);
         InputStreamReader isr = this.getInputStreamReader(fin);
         br = new BufferedReader(isr);
         template = this.loadTemplate(name, br);
         br.close();
         br = null;
      } catch (IOException var8) {
         if (br != null) {
            try {
               br.close();
            } catch (IOException var7) {
               this.error("Cannot close template file: " + fileName);
            }
         }
      }

      return template;
   }

   protected InputStreamReader getInputStreamReader(InputStream in) {
      InputStreamReader isr = null;

      try {
         isr = new InputStreamReader(in, this.fileCharEncoding);
      } catch (UnsupportedEncodingException var4) {
         this.error("Invalid file character encoding: " + this.fileCharEncoding);
      }

      return isr;
   }

   public String getFileCharEncoding() {
      return this.fileCharEncoding;
   }

   public void setFileCharEncoding(String fileCharEncoding) {
      this.fileCharEncoding = fileCharEncoding;
   }

   public synchronized StringTemplate defineTemplate(String name, String template) {
      if (name != null && name.indexOf(46) >= 0) {
         throw new IllegalArgumentException("cannot have '.' in template names");
      } else {
         StringTemplate st = this.createStringTemplate();
         st.setName(name);
         st.setGroup(this);
         st.setNativeGroup(this);
         st.setTemplate(template);
         st.setErrorListener(this.listener);
         this.templates.put(name, st);
         return st;
      }
   }

   public StringTemplate defineRegionTemplate(String enclosingTemplateName, String regionName, String template, int type) {
      String mangledName = this.getMangledRegionName(enclosingTemplateName, regionName);
      StringTemplate regionST = this.defineTemplate(mangledName, template);
      regionST.setIsRegion(true);
      regionST.setRegionDefType(type);
      return regionST;
   }

   public StringTemplate defineRegionTemplate(StringTemplate enclosingTemplate, String regionName, String template, int type) {
      StringTemplate regionST = this.defineRegionTemplate(enclosingTemplate.getOutermostName(), regionName, template, type);
      enclosingTemplate.getOutermostEnclosingInstance().addRegionName(regionName);
      return regionST;
   }

   public StringTemplate defineImplicitRegionTemplate(StringTemplate enclosingTemplate, String name) {
      return this.defineRegionTemplate((StringTemplate)enclosingTemplate, name, "", 1);
   }

   public String getMangledRegionName(String enclosingTemplateName, String name) {
      return "region__" + enclosingTemplateName + "__" + name;
   }

   public String getUnMangledTemplateName(String mangledName) {
      return mangledName.substring("region__".length(), mangledName.lastIndexOf("__"));
   }

   public synchronized StringTemplate defineTemplateAlias(String name, String target) {
      StringTemplate targetST = this.getTemplateDefinition(target);
      if (targetST == null) {
         this.error("cannot alias " + name + " to undefined template: " + target);
         return null;
      } else {
         this.templates.put(name, targetST);
         return targetST;
      }
   }

   public synchronized boolean isDefinedInThisGroup(String name) {
      StringTemplate st = (StringTemplate)this.templates.get(name);
      if (st != null) {
         return !st.isRegion() || st.getRegionDefType() != 1;
      } else {
         return false;
      }
   }

   public synchronized StringTemplate getTemplateDefinition(String name) {
      return (StringTemplate)this.templates.get(name);
   }

   public boolean isDefined(String name) {
      try {
         return this.lookupTemplate(name) != null;
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   protected void parseGroup(Reader r) {
      try {
         GroupLexer lexer = new GroupLexer(r);
         GroupParser parser = new GroupParser(lexer);
         parser.group(this);
      } catch (Exception var4) {
         String name = "<unknown>";
         if (this.getName() != null) {
            name = this.getName();
         }

         this.error("problem parsing group " + name + ": " + var4, var4);
      }

   }

   protected void verifyInterfaceImplementations() {
      for(int i = 0; this.interfaces != null && i < this.interfaces.size(); ++i) {
         StringTemplateGroupInterface I = (StringTemplateGroupInterface)this.interfaces.get(i);
         List missing = I.getMissingTemplates(this);
         List mismatched = I.getMismatchedTemplates(this);
         if (missing != null) {
            this.error("group " + this.getName() + " does not satisfy interface " + I.getName() + ": missing templates " + missing);
         }

         if (mismatched != null) {
            this.error("group " + this.getName() + " does not satisfy interface " + I.getName() + ": mismatched arguments on these templates " + mismatched);
         }
      }

   }

   public int getRefreshInterval() {
      return this.refreshIntervalInSeconds;
   }

   public void setRefreshInterval(int refreshInterval) {
      this.refreshIntervalInSeconds = refreshInterval;
   }

   public void setErrorListener(StringTemplateErrorListener listener) {
      this.listener = listener;
   }

   public StringTemplateErrorListener getErrorListener() {
      return this.listener;
   }

   public void setStringTemplateWriter(Class c) {
      this.userSpecifiedWriter = c;
   }

   public StringTemplateWriter getStringTemplateWriter(Writer w) {
      StringTemplateWriter stw = null;
      if (this.userSpecifiedWriter != null) {
         try {
            Constructor ctor = this.userSpecifiedWriter.getConstructor(Writer.class);
            stw = (StringTemplateWriter)ctor.newInstance(w);
         } catch (Exception var4) {
            this.error("problems getting StringTemplateWriter", var4);
         }
      }

      if (stw == null) {
         stw = new AutoIndentWriter(w);
      }

      return (StringTemplateWriter)stw;
   }

   public void setAttributeRenderers(Map renderers) {
      this.attributeRenderers = renderers;
   }

   public void registerRenderer(Class attributeClassType, Object renderer) {
      if (this.attributeRenderers == null) {
         this.attributeRenderers = Collections.synchronizedMap(new HashMap());
      }

      this.attributeRenderers.put(attributeClassType, renderer);
   }

   public AttributeRenderer getAttributeRenderer(Class attributeClassType) {
      if (this.attributeRenderers == null) {
         return this.superGroup == null ? null : this.superGroup.getAttributeRenderer(attributeClassType);
      } else {
         AttributeRenderer renderer = (AttributeRenderer)this.attributeRenderers.get(attributeClassType);
         if (renderer == null && this.superGroup != null) {
            renderer = this.superGroup.getAttributeRenderer(attributeClassType);
         }

         return renderer;
      }
   }

   public Map getMap(String name) {
      if (this.maps == null) {
         return this.superGroup == null ? null : this.superGroup.getMap(name);
      } else {
         Map m = (Map)this.maps.get(name);
         if (m == null && this.superGroup != null) {
            m = this.superGroup.getMap(name);
         }

         return m;
      }
   }

   public void defineMap(String name, Map mapping) {
      this.maps.put(name, mapping);
   }

   public static void registerDefaultLexer(Class lexerClass) {
      defaultTemplateLexerClass = lexerClass;
   }

   public static void registerGroupLoader(StringTemplateGroupLoader loader) {
      groupLoader = loader;
   }

   public static StringTemplateGroup loadGroup(String name) {
      return loadGroup(name, (Class)null, (StringTemplateGroup)null);
   }

   public static StringTemplateGroup loadGroup(String name, StringTemplateGroup superGroup) {
      return loadGroup(name, (Class)null, superGroup);
   }

   public static StringTemplateGroup loadGroup(String name, Class lexer, StringTemplateGroup superGroup) {
      return groupLoader != null ? groupLoader.loadGroup(name, lexer, superGroup) : null;
   }

   public static StringTemplateGroupInterface loadInterface(String name) {
      return groupLoader != null ? groupLoader.loadInterface(name) : null;
   }

   public void error(String msg) {
      this.error(msg, (Exception)null);
   }

   public void error(String msg, Exception e) {
      if (this.listener != null) {
         this.listener.error(msg, e);
      } else {
         System.err.println("StringTemplate: " + msg);
         if (e != null) {
            e.printStackTrace();
         }
      }

   }

   public synchronized Set getTemplateNames() {
      return this.templates.keySet();
   }

   public void emitDebugStartStopStrings(boolean emit) {
      this.debugTemplateOutput = emit;
   }

   public void doNotEmitDebugStringsForTemplate(String templateName) {
      if (this.noDebugStartStopStrings == null) {
         this.noDebugStartStopStrings = new HashSet();
      }

      this.noDebugStartStopStrings.add(templateName);
   }

   public void emitTemplateStartDebugString(StringTemplate st, StringTemplateWriter out) throws IOException {
      if (this.noDebugStartStopStrings == null || !this.noDebugStartStopStrings.contains(st.getName())) {
         String groupPrefix = "";
         if (!st.getName().startsWith("if") && !st.getName().startsWith("else")) {
            if (st.getNativeGroup() != null) {
               groupPrefix = st.getNativeGroup().getName() + ".";
            } else {
               groupPrefix = st.getGroup().getName() + ".";
            }
         }

         out.write("<" + groupPrefix + st.getName() + ">");
      }

   }

   public void emitTemplateStopDebugString(StringTemplate st, StringTemplateWriter out) throws IOException {
      if (this.noDebugStartStopStrings == null || !this.noDebugStartStopStrings.contains(st.getName())) {
         String groupPrefix = "";
         if (!st.getName().startsWith("if") && !st.getName().startsWith("else")) {
            if (st.getNativeGroup() != null) {
               groupPrefix = st.getNativeGroup().getName() + ".";
            } else {
               groupPrefix = st.getGroup().getName() + ".";
            }
         }

         out.write("</" + groupPrefix + st.getName() + ">");
      }

   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean showTemplatePatterns) {
      StringBuffer buf = new StringBuffer();
      Set templateNameSet = this.templates.keySet();
      List sortedNames = new ArrayList(templateNameSet);
      Collections.sort(sortedNames);
      Iterator iter = sortedNames.iterator();
      buf.append("group " + this.getName() + ";\n");
      StringTemplate formalArgs = new StringTemplate("$args;separator=\",\"$");

      while(iter.hasNext()) {
         String tname = (String)iter.next();
         StringTemplate st = (StringTemplate)this.templates.get(tname);
         if (st != NOT_FOUND_ST) {
            formalArgs = formalArgs.getInstanceOf();
            formalArgs.setAttribute("args", (Object)st.getFormalArguments());
            buf.append(tname + "(" + formalArgs + ")");
            if (showTemplatePatterns) {
               buf.append(" ::= <<");
               buf.append(st.getTemplate());
               buf.append(">>\n");
            } else {
               buf.append('\n');
            }
         }
      }

      return buf.toString();
   }

   static {
      defaultTemplateLexerClass = DefaultTemplateLexer.class;
      nameToGroupMap = Collections.synchronizedMap(new HashMap());
      nameToInterfaceMap = Collections.synchronizedMap(new HashMap());
      groupLoader = null;
      DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener() {
         public void error(String s, Throwable e) {
            System.err.println(s);
            if (e != null) {
               e.printStackTrace(System.err);
            }

         }

         public void warning(String s) {
            System.out.println(s);
         }
      };
      NOT_FOUND_ST = new StringTemplate();
   }
}
