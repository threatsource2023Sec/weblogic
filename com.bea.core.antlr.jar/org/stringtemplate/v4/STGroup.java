package org.stringtemplate.v4;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.Compiler;
import org.stringtemplate.v4.compiler.FormalArgument;
import org.stringtemplate.v4.compiler.GroupLexer;
import org.stringtemplate.v4.compiler.GroupParser;
import org.stringtemplate.v4.compiler.STException;
import org.stringtemplate.v4.misc.Aggregate;
import org.stringtemplate.v4.misc.AggregateModelAdaptor;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.MapModelAdaptor;
import org.stringtemplate.v4.misc.Misc;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STModelAdaptor;
import org.stringtemplate.v4.misc.TypeRegistry;

public class STGroup {
   public static final String GROUP_FILE_EXTENSION = ".stg";
   public static final String TEMPLATE_FILE_EXTENSION = ".st";
   public static final String DICT_KEY = "key";
   public static final String DEFAULT_KEY = "default";
   public String encoding = "UTF-8";
   protected final List imports = Collections.synchronizedList(new ArrayList());
   protected final List importsToClearOnUnload = Collections.synchronizedList(new ArrayList());
   public char delimiterStartChar = '<';
   public char delimiterStopChar = '>';
   protected Map templates = Collections.synchronizedMap(new LinkedHashMap());
   protected Map dictionaries = Collections.synchronizedMap(new HashMap());
   protected Map renderers;
   protected final Map adaptors;
   protected static final CompiledST NOT_FOUND_ST = new CompiledST();
   public static final ErrorManager DEFAULT_ERR_MGR = new ErrorManager();
   public static boolean verbose = false;
   public static boolean trackCreationEvents = false;
   public boolean iterateAcrossValues;
   public static STGroup defaultGroup = new STGroup();
   public ErrorManager errMgr;

   public STGroup() {
      TypeRegistry registry = new TypeRegistry();
      registry.put((Class)Object.class, new ObjectModelAdaptor());
      registry.put((Class)ST.class, new STModelAdaptor());
      registry.put((Class)Map.class, new MapModelAdaptor());
      registry.put((Class)Aggregate.class, new AggregateModelAdaptor());
      this.adaptors = Collections.synchronizedMap(registry);
      this.iterateAcrossValues = false;
      this.errMgr = DEFAULT_ERR_MGR;
   }

   public STGroup(char delimiterStartChar, char delimiterStopChar) {
      TypeRegistry registry = new TypeRegistry();
      registry.put((Class)Object.class, new ObjectModelAdaptor());
      registry.put((Class)ST.class, new STModelAdaptor());
      registry.put((Class)Map.class, new MapModelAdaptor());
      registry.put((Class)Aggregate.class, new AggregateModelAdaptor());
      this.adaptors = Collections.synchronizedMap(registry);
      this.iterateAcrossValues = false;
      this.errMgr = DEFAULT_ERR_MGR;
      this.delimiterStartChar = delimiterStartChar;
      this.delimiterStopChar = delimiterStopChar;
   }

   public ST getInstanceOf(String name) {
      if (name == null) {
         return null;
      } else {
         if (verbose) {
            System.out.println(this.getName() + ".getInstanceOf(" + name + ")");
         }

         if (name.charAt(0) != '/') {
            name = "/" + name;
         }

         CompiledST c = this.lookupTemplate(name);
         return c != null ? this.createStringTemplate(c) : null;
      }
   }

   protected ST getEmbeddedInstanceOf(Interpreter interp, InstanceScope scope, String name) {
      String fullyQualifiedName = name;
      if (name.charAt(0) != '/') {
         fullyQualifiedName = scope.st.impl.prefix + name;
      }

      if (verbose) {
         System.out.println("getEmbeddedInstanceOf(" + fullyQualifiedName + ")");
      }

      ST st = this.getInstanceOf(fullyQualifiedName);
      if (st == null) {
         this.errMgr.runTimeError(interp, scope, ErrorType.NO_SUCH_TEMPLATE, fullyQualifiedName);
         return this.createStringTemplateInternally(new CompiledST());
      } else {
         if (trackCreationEvents) {
            st.debugState.newSTEvent = null;
         }

         return st;
      }
   }

   public ST createSingleton(Token templateToken) {
      String template;
      if (templateToken.getType() == 5) {
         template = Misc.strip(templateToken.getText(), 2);
      } else {
         template = Misc.strip(templateToken.getText(), 1);
      }

      CompiledST impl = this.compile(this.getFileName(), (String)null, (List)null, template, templateToken);
      ST st = this.createStringTemplateInternally(impl);
      st.groupThatCreatedThisInstance = this;
      st.impl.hasFormalArgs = false;
      st.impl.name = "anonymous";
      st.impl.defineImplicitlyDefinedTemplates(this);
      return st;
   }

   public boolean isDefined(String name) {
      return this.lookupTemplate(name) != null;
   }

   public CompiledST lookupTemplate(String name) {
      if (name.charAt(0) != '/') {
         name = "/" + name;
      }

      if (verbose) {
         System.out.println(this.getName() + ".lookupTemplate(" + name + ")");
      }

      CompiledST code = this.rawGetTemplate(name);
      if (code == NOT_FOUND_ST) {
         if (verbose) {
            System.out.println(name + " previously seen as not found");
         }

         return null;
      } else {
         if (code == null) {
            code = this.load(name);
         }

         if (code == null) {
            code = this.lookupImportedTemplate(name);
         }

         if (code == null) {
            if (verbose) {
               System.out.println(name + " recorded not found");
            }

            this.templates.put(name, NOT_FOUND_ST);
         }

         if (verbose && code != null) {
            System.out.println(this.getName() + ".lookupTemplate(" + name + ") found");
         }

         return code;
      }
   }

   public synchronized void unload() {
      this.templates.clear();
      this.dictionaries.clear();
      Iterator var1 = this.imports.iterator();

      STGroup imp;
      while(var1.hasNext()) {
         imp = (STGroup)var1.next();
         imp.unload();
      }

      var1 = this.importsToClearOnUnload.iterator();

      while(var1.hasNext()) {
         imp = (STGroup)var1.next();
         this.imports.remove(imp);
      }

      this.importsToClearOnUnload.clear();
   }

   protected CompiledST load(String name) {
      return null;
   }

   public void load() {
   }

   protected CompiledST lookupImportedTemplate(String name) {
      if (this.imports.size() == 0) {
         return null;
      } else {
         Iterator var2 = this.imports.iterator();

         STGroup g;
         CompiledST code;
         do {
            if (!var2.hasNext()) {
               if (verbose) {
                  System.out.println(name + " not found in " + this.getName() + " imports");
               }

               return null;
            }

            g = (STGroup)var2.next();
            if (verbose) {
               System.out.println("checking " + g.getName() + " for imported " + name);
            }

            code = g.lookupTemplate(name);
         } while(code == null);

         if (verbose) {
            System.out.println(g.getName() + ".lookupImportedTemplate(" + name + ") found");
         }

         return code;
      }
   }

   public CompiledST rawGetTemplate(String name) {
      return (CompiledST)this.templates.get(name);
   }

   public Map rawGetDictionary(String name) {
      return (Map)this.dictionaries.get(name);
   }

   public boolean isDictionary(String name) {
      return this.dictionaries.get(name) != null;
   }

   public CompiledST defineTemplate(String templateName, String template) {
      if (templateName.charAt(0) != '/') {
         templateName = "/" + templateName;
      }

      try {
         CompiledST impl = this.defineTemplate(templateName, new CommonToken(9, templateName), (List)null, template, (Token)null);
         return impl;
      } catch (STException var4) {
         return null;
      }
   }

   public CompiledST defineTemplate(String name, String argsS, String template) {
      if (name.charAt(0) != '/') {
         name = "/" + name;
      }

      String[] args = argsS.split(",");
      List a = new ArrayList();
      String[] var6 = args;
      int var7 = args.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String arg = var6[var8];
         a.add(new FormalArgument(arg));
      }

      return this.defineTemplate(name, new CommonToken(9, name), a, template, (Token)null);
   }

   public CompiledST defineTemplate(String fullyQualifiedTemplateName, Token nameT, List args, String template, Token templateToken) {
      if (verbose) {
         System.out.println("defineTemplate(" + fullyQualifiedTemplateName + ")");
      }

      if (fullyQualifiedTemplateName != null && fullyQualifiedTemplateName.length() != 0) {
         if (fullyQualifiedTemplateName.indexOf(46) >= 0) {
            throw new IllegalArgumentException("cannot have '.' in template names");
         } else {
            template = Misc.trimOneStartingNewline(template);
            template = Misc.trimOneTrailingNewline(template);
            CompiledST code = this.compile(this.getFileName(), fullyQualifiedTemplateName, args, template, templateToken);
            code.name = fullyQualifiedTemplateName;
            this.rawDefineTemplate(fullyQualifiedTemplateName, code, nameT);
            code.defineArgDefaultValueTemplates(this);
            code.defineImplicitlyDefinedTemplates(this);
            return code;
         }
      } else {
         throw new IllegalArgumentException("empty template name");
      }
   }

   public CompiledST defineTemplateAlias(Token aliasT, Token targetT) {
      String alias = aliasT.getText();
      String target = targetT.getText();
      CompiledST targetCode = this.rawGetTemplate("/" + target);
      if (targetCode == null) {
         this.errMgr.compileTimeError(ErrorType.ALIAS_TARGET_UNDEFINED, (Token)null, aliasT, alias, target);
         return null;
      } else {
         this.rawDefineTemplate("/" + alias, targetCode, aliasT);
         return targetCode;
      }
   }

   public CompiledST defineRegion(String enclosingTemplateName, Token regionT, String template, Token templateToken) {
      String name = regionT.getText();
      template = Misc.trimOneStartingNewline(template);
      template = Misc.trimOneTrailingNewline(template);
      CompiledST code = this.compile(this.getFileName(), enclosingTemplateName, (List)null, template, templateToken);
      String mangled = getMangledRegionName(enclosingTemplateName, name);
      if (this.lookupTemplate(mangled) == null) {
         this.errMgr.compileTimeError(ErrorType.NO_SUCH_REGION, templateToken, regionT, enclosingTemplateName, name);
         return new CompiledST();
      } else {
         code.name = mangled;
         code.isRegion = true;
         code.regionDefType = ST.RegionType.EXPLICIT;
         code.templateDefStartToken = regionT;
         this.rawDefineTemplate(mangled, code, regionT);
         code.defineArgDefaultValueTemplates(this);
         code.defineImplicitlyDefinedTemplates(this);
         return code;
      }
   }

   public void defineTemplateOrRegion(String fullyQualifiedTemplateName, String regionSurroundingTemplateName, Token templateToken, String template, Token nameToken, List args) {
      try {
         if (regionSurroundingTemplateName != null) {
            this.defineRegion(regionSurroundingTemplateName, nameToken, template, templateToken);
         } else {
            this.defineTemplate(fullyQualifiedTemplateName, nameToken, args, template, templateToken);
         }
      } catch (STException var8) {
      }

   }

   public void rawDefineTemplate(String name, CompiledST code, Token defT) {
      CompiledST prev = this.rawGetTemplate(name);
      if (prev != null) {
         if (!prev.isRegion) {
            this.errMgr.compileTimeError(ErrorType.TEMPLATE_REDEFINITION, (Token)null, defT);
            return;
         }

         if (prev.isRegion) {
            if (code.regionDefType != ST.RegionType.IMPLICIT && prev.regionDefType == ST.RegionType.EMBEDDED) {
               this.errMgr.compileTimeError(ErrorType.EMBEDDED_REGION_REDEFINITION, (Token)null, defT, getUnMangledTemplateName(name));
               return;
            }

            if (code.regionDefType == ST.RegionType.IMPLICIT || prev.regionDefType == ST.RegionType.EXPLICIT) {
               this.errMgr.compileTimeError(ErrorType.REGION_REDEFINITION, (Token)null, defT, getUnMangledTemplateName(name));
               return;
            }
         }
      }

      code.nativeGroup = this;
      code.templateDefStartToken = defT;
      this.templates.put(name, code);
   }

   public void undefineTemplate(String name) {
      this.templates.remove(name);
   }

   public CompiledST compile(String srcName, String name, List args, String template, Token templateToken) {
      Compiler c = new Compiler(this);
      return c.compile(srcName, name, args, template, templateToken);
   }

   public static String getMangledRegionName(String enclosingTemplateName, String name) {
      if (enclosingTemplateName.charAt(0) != '/') {
         enclosingTemplateName = '/' + enclosingTemplateName;
      }

      return "/region__" + enclosingTemplateName + "__" + name;
   }

   public static String getUnMangledTemplateName(String mangledName) {
      String t = mangledName.substring("/region__".length(), mangledName.lastIndexOf("__"));
      String r = mangledName.substring(mangledName.lastIndexOf("__") + 2, mangledName.length());
      return t + '.' + r;
   }

   public void defineDictionary(String name, Map mapping) {
      this.dictionaries.put(name, mapping);
   }

   public void importTemplates(STGroup g) {
      this.importTemplates(g, false);
   }

   public void importTemplates(Token fileNameToken) {
      if (verbose) {
         System.out.println("importTemplates(" + fileNameToken.getText() + ")");
      }

      String fileName = fileNameToken.getText();
      if (fileName != null && !fileName.equals("<missing STRING>")) {
         fileName = Misc.strip(fileName, 1);
         boolean isGroupFile = fileName.endsWith(GROUP_FILE_EXTENSION);
         boolean isTemplateFile = fileName.endsWith(TEMPLATE_FILE_EXTENSION);
         boolean isGroupDir = !isGroupFile && !isTemplateFile;
         STGroup g = null;
         URL thisRoot = this.getRootDirURL();

         URL fileUnderRoot;
         try {
            fileUnderRoot = new URL(thisRoot + "/" + fileName);
         } catch (MalformedURLException var14) {
            this.errMgr.internalError((ST)null, "can't build URL for " + thisRoot + "/" + fileName, var14);
            return;
         }

         if (isTemplateFile) {
            g = new STGroup(this.delimiterStartChar, this.delimiterStopChar);
            ((STGroup)g).setListener(this.getListener());
            URL fileURL;
            if (Misc.urlExists(fileUnderRoot)) {
               fileURL = fileUnderRoot;
            } else {
               fileURL = this.getURL(fileName);
            }

            if (fileURL != null) {
               try {
                  InputStream s = fileURL.openStream();
                  ANTLRInputStream templateStream = new ANTLRInputStream(s);
                  templateStream.name = fileName;
                  CompiledST code = ((STGroup)g).loadTemplateFile("/", fileName, templateStream);
                  if (code == null) {
                     g = null;
                  }
               } catch (IOException var13) {
                  this.errMgr.internalError((ST)null, "can't read from " + fileURL, var13);
                  g = null;
               }
            } else {
               g = null;
            }
         } else if (isGroupFile) {
            if (Misc.urlExists(fileUnderRoot)) {
               g = new STGroupFile(fileUnderRoot, this.encoding, this.delimiterStartChar, this.delimiterStopChar);
               ((STGroup)g).setListener(this.getListener());
            } else {
               g = new STGroupFile(fileName, this.delimiterStartChar, this.delimiterStopChar);
               ((STGroup)g).setListener(this.getListener());
            }
         } else if (isGroupDir) {
            if (Misc.urlExists(fileUnderRoot)) {
               g = new STGroupDir(fileUnderRoot, this.encoding, this.delimiterStartChar, this.delimiterStopChar);
               ((STGroup)g).setListener(this.getListener());
            } else {
               g = new STGroupDir(fileName, this.delimiterStartChar, this.delimiterStopChar);
               ((STGroup)g).setListener(this.getListener());
            }
         }

         if (g == null) {
            this.errMgr.compileTimeError(ErrorType.CANT_IMPORT, (Token)null, fileNameToken, fileName);
         } else {
            this.importTemplates((STGroup)g, true);
         }

      }
   }

   protected void importTemplates(STGroup g, boolean clearOnUnload) {
      if (g != null) {
         this.imports.add(g);
         if (clearOnUnload) {
            this.importsToClearOnUnload.add(g);
         }

      }
   }

   public List getImportedGroups() {
      return this.imports;
   }

   public void loadGroupFile(String prefix, String fileName) {
      if (verbose) {
         System.out.println(this.getClass().getSimpleName() + ".loadGroupFile(group-file-prefix=" + prefix + ", fileName=" + fileName + ")");
      }

      try {
         URL f = new URL(fileName);
         ANTLRInputStream fs = new ANTLRInputStream(f.openStream(), this.encoding);
         GroupLexer lexer = new GroupLexer(fs);
         fs.name = fileName;
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         GroupParser parser = new GroupParser(tokens);
         parser.group(this, prefix);
      } catch (Exception var8) {
         this.errMgr.IOError((ST)null, ErrorType.CANT_LOAD_GROUP_FILE, var8, fileName);
      }

   }

   public CompiledST loadAbsoluteTemplateFile(String fileName) {
      ANTLRFileStream fs;
      try {
         fs = new ANTLRFileStream(fileName, this.encoding);
         fs.name = fileName;
      } catch (IOException var4) {
         return null;
      }

      return this.loadTemplateFile("", fileName, fs);
   }

   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName, CharStream templateStream) {
      GroupLexer lexer = new GroupLexer(templateStream);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      GroupParser parser = new GroupParser(tokens);
      parser.group = this;
      lexer.group = this;

      try {
         parser.templateDef(prefix);
      } catch (RecognitionException var9) {
         this.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, unqualifiedFileName, var9, var9.getMessage());
      }

      String templateName = Misc.getFileNameNoSuffix(unqualifiedFileName);
      if (prefix != null && prefix.length() > 0) {
         templateName = prefix + templateName;
      }

      CompiledST impl = this.rawGetTemplate(templateName);
      impl.prefix = prefix;
      return impl;
   }

   public void registerModelAdaptor(Class attributeType, ModelAdaptor adaptor) {
      if (attributeType.isPrimitive()) {
         throw new IllegalArgumentException("can't register ModelAdaptor for primitive type " + attributeType.getSimpleName());
      } else {
         this.adaptors.put(attributeType, adaptor);
      }
   }

   public ModelAdaptor getModelAdaptor(Class attributeType) {
      return (ModelAdaptor)this.adaptors.get(attributeType);
   }

   public void registerRenderer(Class attributeType, AttributeRenderer r) {
      this.registerRenderer(attributeType, r, true);
   }

   public void registerRenderer(Class attributeType, AttributeRenderer r, boolean recursive) {
      if (attributeType.isPrimitive()) {
         throw new IllegalArgumentException("can't register renderer for primitive type " + attributeType.getSimpleName());
      } else {
         if (this.renderers == null) {
            this.renderers = Collections.synchronizedMap(new TypeRegistry());
         }

         this.renderers.put(attributeType, r);
         if (recursive) {
            this.load();
            Iterator var4 = this.imports.iterator();

            while(var4.hasNext()) {
               STGroup g = (STGroup)var4.next();
               g.registerRenderer(attributeType, r, true);
            }
         }

      }
   }

   public AttributeRenderer getAttributeRenderer(Class attributeType) {
      return this.renderers == null ? null : (AttributeRenderer)this.renderers.get(attributeType);
   }

   public ST createStringTemplate(CompiledST impl) {
      ST st = new ST();
      st.impl = impl;
      st.groupThatCreatedThisInstance = this;
      if (impl.formalArguments != null) {
         st.locals = new Object[impl.formalArguments.size()];
         Arrays.fill(st.locals, ST.EMPTY_ATTR);
      }

      return st;
   }

   public ST createStringTemplateInternally(CompiledST impl) {
      ST st = this.createStringTemplate(impl);
      if (trackCreationEvents && st.debugState != null) {
         st.debugState.newSTEvent = null;
      }

      return st;
   }

   public ST createStringTemplateInternally(ST proto) {
      return new ST(proto);
   }

   public String getName() {
      return "<no name>;";
   }

   public String getFileName() {
      return null;
   }

   public URL getRootDirURL() {
      return null;
   }

   public URL getURL(String fileName) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      URL url = cl.getResource(fileName);
      if (url == null) {
         cl = this.getClass().getClassLoader();
         url = cl.getResource(fileName);
      }

      return url;
   }

   public String toString() {
      return this.getName();
   }

   public String show() {
      StringBuilder buf = new StringBuilder();
      if (this.imports.size() != 0) {
         buf.append(" : " + this.imports);
      }

      Iterator var2 = this.templates.keySet().iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         CompiledST c = this.rawGetTemplate(name);
         if (!c.isAnonSubtemplate && c != NOT_FOUND_ST) {
            int slash = name.lastIndexOf(47);
            name = name.substring(slash + 1, name.length());
            buf.append(name);
            buf.append('(');
            if (c.formalArguments != null) {
               buf.append(Misc.join(c.formalArguments.values().iterator(), ","));
            }

            buf.append(')');
            buf.append(" ::= <<" + Misc.newline);
            buf.append(c.template + Misc.newline);
            buf.append(">>" + Misc.newline);
         }
      }

      return buf.toString();
   }

   public STErrorListener getListener() {
      return this.errMgr.listener;
   }

   public void setListener(STErrorListener listener) {
      this.errMgr = new ErrorManager(listener);
   }

   public Set getTemplateNames() {
      this.load();
      HashSet result = new HashSet();
      Iterator var2 = this.templates.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         if (e.getValue() != NOT_FOUND_ST) {
            result.add(e.getKey());
         }
      }

      return result;
   }
}
