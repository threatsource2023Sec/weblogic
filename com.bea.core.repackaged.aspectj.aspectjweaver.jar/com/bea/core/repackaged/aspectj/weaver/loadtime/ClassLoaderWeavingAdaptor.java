package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.ICrossReferenceHandler;
import com.bea.core.repackaged.aspectj.weaver.Lint;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWeakClassLoaderReference;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWeaver;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWorld;
import com.bea.core.repackaged.aspectj.weaver.bcel.Utility;
import com.bea.core.repackaged.aspectj.weaver.loadtime.definition.Definition;
import com.bea.core.repackaged.aspectj.weaver.loadtime.definition.DocumentParser;
import com.bea.core.repackaged.aspectj.weaver.ltw.LTWWorld;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.aspectj.weaver.tools.GeneratedClassHandler;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.WeavingAdaptor;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.WeavedClassCache;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

public class ClassLoaderWeavingAdaptor extends WeavingAdaptor {
   private static final String AOP_XML = "META-INF/aop.xml;META-INF/aop-ajc.xml;org/aspectj/aop.xml";
   private boolean initialized;
   private List m_dumpTypePattern = new ArrayList();
   private boolean m_dumpBefore = false;
   private boolean dumpDirPerClassloader = false;
   private boolean hasExcludes = false;
   private List excludeTypePattern = new ArrayList();
   private List excludeStartsWith = new ArrayList();
   private List excludeStarDotDotStar = new ArrayList();
   private List excludeExactName = new ArrayList();
   private List excludeEndsWith = new ArrayList();
   private List excludeSpecial = new ArrayList();
   private boolean hasIncludes = false;
   private List includeTypePattern = new ArrayList();
   private List m_includeStartsWith = new ArrayList();
   private List includeExactName = new ArrayList();
   private boolean includeStar = false;
   private List m_aspectExcludeTypePattern = new ArrayList();
   private List m_aspectExcludeStartsWith = new ArrayList();
   private List m_aspectIncludeTypePattern = new ArrayList();
   private List m_aspectIncludeStartsWith = new ArrayList();
   private StringBuffer namespace;
   private IWeavingContext weavingContext;
   private List concreteAspects = new ArrayList();
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(ClassLoaderWeavingAdaptor.class);
   private Method defineClassMethod;
   private Method defineClassWithProtectionDomainMethod;

   public ClassLoaderWeavingAdaptor() {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   /** @deprecated */
   public ClassLoaderWeavingAdaptor(ClassLoader deprecatedLoader, IWeavingContext deprecatedContext) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object[])(new Object[]{deprecatedLoader, deprecatedContext}));
      }

      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   public void initialize(ClassLoader classLoader, IWeavingContext context) {
      if (!this.initialized) {
         boolean success = true;
         this.weavingContext = context;
         if (this.weavingContext == null) {
            this.weavingContext = new DefaultWeavingContext(classLoader);
         }

         this.createMessageHandler();
         this.generatedClassHandler = new SimpleGeneratedClassHandler(classLoader);
         List definitions = this.weavingContext.getDefinitions(classLoader, this);
         if (definitions.isEmpty()) {
            this.disable();
            if (trace.isTraceEnabled()) {
               trace.exit("initialize", (Object)definitions);
            }

         } else {
            this.bcelWorld = new LTWWorld(classLoader, this.weavingContext, this.getMessageHandler(), (ICrossReferenceHandler)null);
            this.weaver = new BcelWeaver(this.bcelWorld);
            success = this.registerDefinitions(this.weaver, classLoader, definitions);
            if (success) {
               this.weaver.prepareForWeave();
               this.enable();
               success = this.weaveAndDefineConceteAspects();
            }

            if (success) {
               this.enable();
            } else {
               this.disable();
               this.bcelWorld = null;
               this.weaver = null;
            }

            if (WeavedClassCache.isEnabled()) {
               this.initializeCache(classLoader, this.getAspectClassNames(definitions), this.generatedClassHandler, this.getMessageHandler());
            }

            this.initialized = true;
            if (trace.isTraceEnabled()) {
               trace.exit("initialize", this.isEnabled());
            }

         }
      }
   }

   List getAspectClassNames(List definitions) {
      List aspects = new LinkedList();
      Iterator it = definitions.iterator();

      while(it.hasNext()) {
         Definition def = (Definition)it.next();
         List defAspects = def.getAspectClassNames();
         if (defAspects != null) {
            aspects.addAll(defAspects);
         }
      }

      return aspects;
   }

   List parseDefinitions(ClassLoader loader) {
      if (trace.isTraceEnabled()) {
         trace.enter("parseDefinitions", this);
      }

      List definitions = new ArrayList();

      try {
         this.info("register classloader " + this.getClassLoaderName(loader));
         String resourcePath;
         if (loader.equals(ClassLoader.getSystemClassLoader())) {
            resourcePath = System.getProperty("aj5.def", (String)null);
            if (resourcePath != null) {
               this.info("using (-Daj5.def) " + resourcePath);
               definitions.add(DocumentParser.parse((new File(resourcePath)).toURL()));
            }
         }

         resourcePath = System.getProperty("com.bea.core.repackaged.aspectj.weaver.loadtime.configuration", "META-INF/aop.xml;META-INF/aop-ajc.xml;org/aspectj/aop.xml");
         if (trace.isTraceEnabled()) {
            trace.event("parseDefinitions", this, (Object)resourcePath);
         }

         StringTokenizer st = new StringTokenizer(resourcePath, ";");

         while(true) {
            while(st.hasMoreTokens()) {
               String nextDefinition = st.nextToken();
               if (nextDefinition.startsWith("file:")) {
                  try {
                     String fpath = (new URL(nextDefinition)).getFile();
                     File configFile = new File(fpath);
                     if (!configFile.exists()) {
                        this.warn("configuration does not exist: " + nextDefinition);
                     } else {
                        definitions.add(DocumentParser.parse(configFile.toURL()));
                     }
                  } catch (MalformedURLException var9) {
                     this.error("malformed definition url: " + nextDefinition);
                  }
               } else {
                  Enumeration xmls = this.weavingContext.getResources(nextDefinition);
                  Set seenBefore = new HashSet();

                  while(xmls.hasMoreElements()) {
                     URL xml = (URL)xmls.nextElement();
                     if (trace.isTraceEnabled()) {
                        trace.event("parseDefinitions", this, (Object)xml);
                     }

                     if (!seenBefore.contains(xml)) {
                        this.info("using configuration " + this.weavingContext.getFile(xml));
                        definitions.add(DocumentParser.parse(xml));
                        seenBefore.add(xml);
                     } else {
                        this.debug("ignoring duplicate definition: " + xml);
                     }
                  }
               }
            }

            if (definitions.isEmpty()) {
               this.info("no configuration found. Disabling weaver for class loader " + this.getClassLoaderName(loader));
            }
            break;
         }
      } catch (Exception var10) {
         definitions.clear();
         this.warn("parse definitions failed", var10);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("parseDefinitions", (Object)definitions);
      }

      return definitions;
   }

   private boolean registerDefinitions(BcelWeaver weaver, ClassLoader loader, List definitions) {
      if (trace.isTraceEnabled()) {
         trace.enter("registerDefinitions", this, (Object)definitions);
      }

      boolean success = true;

      try {
         this.registerOptions(weaver, loader, definitions);
         this.registerAspectExclude(weaver, loader, definitions);
         this.registerAspectInclude(weaver, loader, definitions);
         success = this.registerAspects(weaver, loader, definitions);
         this.registerIncludeExclude(weaver, loader, definitions);
         this.registerDump(weaver, loader, definitions);
      } catch (Exception var6) {
         trace.error("register definition failed", var6);
         success = false;
         this.warn("register definition failed", var6 instanceof AbortException ? null : var6);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("registerDefinitions", success);
      }

      return success;
   }

   private String getClassLoaderName(ClassLoader loader) {
      return this.weavingContext.getClassLoaderName();
   }

   private void registerOptions(BcelWeaver weaver, ClassLoader loader, List definitions) {
      StringBuffer allOptions = new StringBuffer();
      Iterator i$ = definitions.iterator();

      while(i$.hasNext()) {
         Definition definition = (Definition)i$.next();
         allOptions.append(definition.getWeaverOptions()).append(' ');
      }

      Options.WeaverOption weaverOption = Options.parse(allOptions.toString(), loader, this.getMessageHandler());
      World world = weaver.getWorld();
      this.setMessageHandler(weaverOption.messageHandler);
      world.setXlazyTjp(weaverOption.lazyTjp);
      world.setXHasMemberSupportEnabled(weaverOption.hasMember);
      world.setTiming(weaverOption.timers, true);
      world.setOptionalJoinpoints(weaverOption.optionalJoinpoints);
      world.setPinpointMode(weaverOption.pinpoint);
      weaver.setReweavableMode(weaverOption.notReWeavable);
      if (weaverOption.loadersToSkip != null && weaverOption.loadersToSkip.length() > 0) {
         Aj.loadersToSkip = LangUtil.anySplit(weaverOption.loadersToSkip, ",");
      }

      if (Aj.loadersToSkip != null) {
         MessageUtil.info(world.getMessageHandler(), "no longer creating weavers for these classloaders: " + Aj.loadersToSkip);
      }

      world.performExtraConfiguration(weaverOption.xSet);
      world.setXnoInline(weaverOption.noInline);
      world.setBehaveInJava5Way(LangUtil.is15VMOrGreater());
      world.setAddSerialVerUID(weaverOption.addSerialVersionUID);
      this.bcelWorld.getLint().loadDefaultProperties();
      this.bcelWorld.getLint().adviceDidNotMatch.setKind((IMessage.Kind)null);
      if (weaverOption.lintFile != null) {
         InputStream resource = null;

         try {
            resource = loader.getResourceAsStream(weaverOption.lintFile);
            Exception failure = null;
            if (resource != null) {
               try {
                  Properties properties = new Properties();
                  properties.load(resource);
                  world.getLint().setFromProperties(properties);
               } catch (IOException var17) {
                  failure = var17;
               }
            }

            if (failure != null || resource == null) {
               this.warn("Cannot access resource for -Xlintfile:" + weaverOption.lintFile, failure);
            }
         } finally {
            try {
               resource.close();
            } catch (Throwable var16) {
            }

         }
      }

      if (weaverOption.lint != null) {
         if (weaverOption.lint.equals("default")) {
            this.bcelWorld.getLint().loadDefaultProperties();
         } else {
            this.bcelWorld.getLint().setAll(weaverOption.lint);
            if (weaverOption.lint.equals("ignore")) {
               this.bcelWorld.setAllLintIgnored();
            }
         }
      }

   }

   private void registerAspectExclude(BcelWeaver weaver, ClassLoader loader, List definitions) {
      String fastMatchInfo = null;
      Iterator i$ = definitions.iterator();

      while(i$.hasNext()) {
         Definition definition = (Definition)i$.next();
         Iterator i$ = definition.getAspectExcludePatterns().iterator();

         while(i$.hasNext()) {
            String exclude = (String)i$.next();
            TypePattern excludePattern = (new PatternParser(exclude)).parseTypePattern();
            this.m_aspectExcludeTypePattern.add(excludePattern);
            fastMatchInfo = this.looksLikeStartsWith(exclude);
            if (fastMatchInfo != null) {
               this.m_aspectExcludeStartsWith.add(fastMatchInfo);
            }
         }
      }

   }

   private void registerAspectInclude(BcelWeaver weaver, ClassLoader loader, List definitions) {
      String fastMatchInfo = null;
      Iterator i$ = definitions.iterator();

      while(i$.hasNext()) {
         Definition definition = (Definition)i$.next();
         Iterator i$ = definition.getAspectIncludePatterns().iterator();

         while(i$.hasNext()) {
            String include = (String)i$.next();
            TypePattern includePattern = (new PatternParser(include)).parseTypePattern();
            this.m_aspectIncludeTypePattern.add(includePattern);
            fastMatchInfo = this.looksLikeStartsWith(include);
            if (fastMatchInfo != null) {
               this.m_aspectIncludeStartsWith.add(fastMatchInfo);
            }
         }
      }

   }

   protected void lint(String name, String[] infos) {
      Lint lint = this.bcelWorld.getLint();
      Lint.Kind kind = lint.getLintKind(name);
      kind.signal(infos, (ISourceLocation)null, (ISourceLocation[])null);
   }

   public String getContextId() {
      return this.weavingContext.getId();
   }

   private boolean registerAspects(BcelWeaver weaver, ClassLoader loader, List definitions) {
      if (trace.isTraceEnabled()) {
         trace.enter("registerAspects", this, (Object[])(new Object[]{weaver, loader, definitions}));
      }

      boolean success = true;
      Iterator i$ = definitions.iterator();

      Definition definition;
      Iterator i$;
      while(i$.hasNext()) {
         definition = (Definition)i$.next();
         i$ = definition.getAspectClassNames().iterator();

         while(i$.hasNext()) {
            String aspectClassName = (String)i$.next();
            if (this.acceptAspect(aspectClassName)) {
               this.info("register aspect " + aspectClassName);
               String requiredType = definition.getAspectRequires(aspectClassName);
               if (requiredType != null) {
                  ((BcelWorld)weaver.getWorld()).addAspectRequires(aspectClassName, requiredType);
               }

               String definedScope = definition.getScopeForAspect(aspectClassName);
               if (definedScope != null) {
                  ((BcelWorld)weaver.getWorld()).addScopedAspect(aspectClassName, definedScope);
               }

               weaver.addLibraryAspect(aspectClassName);
               if (this.namespace == null) {
                  this.namespace = new StringBuffer(aspectClassName);
               } else {
                  this.namespace = this.namespace.append(";").append(aspectClassName);
               }
            } else {
               this.lint("aspectExcludedByConfiguration", new String[]{aspectClassName, this.getClassLoaderName(loader)});
            }
         }
      }

      i$ = definitions.iterator();

      while(true) {
         while(i$.hasNext()) {
            definition = (Definition)i$.next();
            i$ = definition.getConcreteAspects().iterator();

            while(i$.hasNext()) {
               Definition.ConcreteAspect concreteAspect = (Definition.ConcreteAspect)i$.next();
               if (this.acceptAspect(concreteAspect.name)) {
                  this.info("define aspect " + concreteAspect.name);
                  ConcreteAspectCodeGen gen = new ConcreteAspectCodeGen(concreteAspect, weaver.getWorld());
                  if (!gen.validate()) {
                     this.error("Concrete-aspect '" + concreteAspect.name + "' could not be registered");
                     success = false;
                     break;
                  }

                  ((BcelWorld)weaver.getWorld()).addSourceObjectType(Utility.makeJavaClass(concreteAspect.name, gen.getBytes()), true);
                  this.concreteAspects.add(gen);
                  weaver.addLibraryAspect(concreteAspect.name);
                  if (this.namespace == null) {
                     this.namespace = new StringBuffer(concreteAspect.name);
                  } else {
                     this.namespace = this.namespace.append(";" + concreteAspect.name);
                  }
               }
            }
         }

         if (!success) {
            this.warn("failure(s) registering aspects. Disabling weaver for class loader " + this.getClassLoaderName(loader));
         } else if (this.namespace == null) {
            success = false;
            this.info("no aspects registered. Disabling weaver for class loader " + this.getClassLoaderName(loader));
         }

         if (trace.isTraceEnabled()) {
            trace.exit("registerAspects", success);
         }

         return success;
      }
   }

   private boolean weaveAndDefineConceteAspects() {
      if (trace.isTraceEnabled()) {
         trace.enter("weaveAndDefineConceteAspects", this, (Object)this.concreteAspects);
      }

      boolean success = true;
      Iterator i$ = this.concreteAspects.iterator();

      while(i$.hasNext()) {
         ConcreteAspectCodeGen gen = (ConcreteAspectCodeGen)i$.next();
         String name = gen.getClassName();
         byte[] bytes = gen.getBytes();

         try {
            byte[] newBytes = this.weaveClass(name, bytes, true);
            this.generatedClassHandler.acceptClass(name, bytes, newBytes);
         } catch (IOException var7) {
            trace.error("weaveAndDefineConceteAspects", var7);
            this.error("exception weaving aspect '" + name + "'", var7);
         }
      }

      if (trace.isTraceEnabled()) {
         trace.exit("weaveAndDefineConceteAspects", success);
      }

      return success;
   }

   private void registerIncludeExclude(BcelWeaver weaver, ClassLoader loader, List definitions) {
      String fastMatchInfo = null;
      Iterator i$ = definitions.iterator();

      while(i$.hasNext()) {
         Definition definition = (Definition)i$.next();
         Iterator iterator1 = definition.getIncludePatterns().iterator();

         String exclude;
         TypePattern excludePattern;
         while(iterator1.hasNext()) {
            this.hasIncludes = true;
            exclude = (String)iterator1.next();
            fastMatchInfo = this.looksLikeStartsWith(exclude);
            if (fastMatchInfo != null) {
               this.m_includeStartsWith.add(fastMatchInfo);
            } else if (exclude.equals("*")) {
               this.includeStar = true;
            } else if ((fastMatchInfo = this.looksLikeExactName(exclude)) != null) {
               this.includeExactName.add(fastMatchInfo);
            } else {
               excludePattern = (new PatternParser(exclude)).parseTypePattern();
               this.includeTypePattern.add(excludePattern);
            }
         }

         iterator1 = definition.getExcludePatterns().iterator();

         while(iterator1.hasNext()) {
            this.hasExcludes = true;
            exclude = (String)iterator1.next();
            fastMatchInfo = this.looksLikeStartsWith(exclude);
            if (fastMatchInfo != null) {
               this.excludeStartsWith.add(fastMatchInfo);
            } else if ((fastMatchInfo = this.looksLikeStarDotDotStarExclude(exclude)) != null) {
               this.excludeStarDotDotStar.add(fastMatchInfo);
            } else if (this.looksLikeExactName(exclude) != null) {
               this.excludeExactName.add(exclude);
            } else if ((fastMatchInfo = this.looksLikeEndsWith(exclude)) != null) {
               this.excludeEndsWith.add(fastMatchInfo);
            } else if (exclude.equals("org.codehaus.groovy..* && !org.codehaus.groovy.grails.web.servlet.mvc.SimpleGrailsController*")) {
               this.excludeSpecial.add(new String[]{"org.codehaus.groovy.", "org.codehaus.groovy.grails.web.servlet.mvc.SimpleGrailsController"});
            } else {
               excludePattern = (new PatternParser(exclude)).parseTypePattern();
               this.excludeTypePattern.add(excludePattern);
            }
         }
      }

   }

   private String looksLikeStarDotDotStarExclude(String typePattern) {
      if (!typePattern.startsWith("*..*")) {
         return null;
      } else if (!typePattern.endsWith("*")) {
         return null;
      } else {
         String subPattern = typePattern.substring(4, typePattern.length() - 1);
         return this.hasStarDot(subPattern, 0) ? null : subPattern.replace('$', '.');
      }
   }

   private String looksLikeExactName(String typePattern) {
      return !this.hasSpaceAnnotationPlus(typePattern, 0) && typePattern.indexOf("*") == -1 ? typePattern.replace('$', '.') : null;
   }

   private String looksLikeEndsWith(String typePattern) {
      if (typePattern.charAt(0) != '*') {
         return null;
      } else {
         return !this.hasSpaceAnnotationPlus(typePattern, 1) && !this.hasStarDot(typePattern, 1) ? typePattern.substring(1).replace('$', '.') : null;
      }
   }

   private boolean hasSpaceAnnotationPlus(String string, int pos) {
      int i = pos;

      for(int max = string.length(); i < max; ++i) {
         char ch = string.charAt(i);
         if (ch == ' ' || ch == '@' || ch == '+') {
            return true;
         }
      }

      return false;
   }

   private boolean hasStarDot(String string, int pos) {
      int i = pos;

      for(int max = string.length(); i < max; ++i) {
         char ch = string.charAt(i);
         if (ch == '*' || ch == '.') {
            return true;
         }
      }

      return false;
   }

   private String looksLikeStartsWith(String typePattern) {
      if (!this.hasSpaceAnnotationPlus(typePattern, 0) && typePattern.charAt(typePattern.length() - 1) == '*') {
         int length = typePattern.length();
         return typePattern.endsWith("..*") && length > 3 && typePattern.indexOf("..") == length - 3 && typePattern.indexOf(42) == length - 1 ? typePattern.substring(0, length - 2).replace('$', '.') : null;
      } else {
         return null;
      }
   }

   private void registerDump(BcelWeaver weaver, ClassLoader loader, List definitions) {
      Iterator i$ = definitions.iterator();

      while(i$.hasNext()) {
         Definition definition = (Definition)i$.next();
         Iterator iterator1 = definition.getDumpPatterns().iterator();

         while(iterator1.hasNext()) {
            String dump = (String)iterator1.next();
            TypePattern pattern = (new PatternParser(dump)).parseTypePattern();
            this.m_dumpTypePattern.add(pattern);
         }

         if (definition.shouldDumpBefore()) {
            this.m_dumpBefore = true;
         }

         if (definition.createDumpDirPerClassloader()) {
            this.dumpDirPerClassloader = true;
         }
      }

   }

   protected boolean accept(String className, byte[] bytes) {
      if (!this.hasExcludes && !this.hasIncludes) {
         return true;
      } else {
         String fastClassName = className.replace('/', '.');
         Iterator i$ = this.excludeStartsWith.iterator();

         String name;
         while(i$.hasNext()) {
            name = (String)i$.next();
            if (fastClassName.startsWith(name)) {
               return false;
            }
         }

         int i;
         if (!this.excludeStarDotDotStar.isEmpty()) {
            i$ = this.excludeStarDotDotStar.iterator();

            while(i$.hasNext()) {
               name = (String)i$.next();
               i = fastClassName.lastIndexOf(46);
               if (fastClassName.indexOf(name, i + 1) != -1) {
                  return false;
               }
            }
         }

         fastClassName = fastClassName.replace('$', '.');
         if (!this.excludeEndsWith.isEmpty()) {
            i$ = this.excludeEndsWith.iterator();

            while(i$.hasNext()) {
               name = (String)i$.next();
               if (fastClassName.endsWith(name)) {
                  return false;
               }
            }
         }

         if (!this.excludeExactName.isEmpty()) {
            i$ = this.excludeExactName.iterator();

            while(i$.hasNext()) {
               name = (String)i$.next();
               if (fastClassName.equals(name)) {
                  return false;
               }
            }
         }

         String exactname;
         if (!this.excludeSpecial.isEmpty()) {
            i$ = this.excludeSpecial.iterator();

            while(i$.hasNext()) {
               String[] entry = (String[])i$.next();
               exactname = entry[0];
               String exceptThese = entry[1];
               if (fastClassName.startsWith(exactname) && !fastClassName.startsWith(exceptThese)) {
                  return false;
               }
            }
         }

         boolean didSomeIncludeMatching = false;
         boolean accept;
         if (this.excludeTypePattern.isEmpty()) {
            if (this.includeStar) {
               return true;
            }

            if (!this.includeExactName.isEmpty()) {
               didSomeIncludeMatching = true;
               Iterator i$ = this.includeExactName.iterator();

               while(i$.hasNext()) {
                  exactname = (String)i$.next();
                  if (fastClassName.equals(exactname)) {
                     return true;
                  }
               }
            }

            accept = false;

            for(i = 0; i < this.m_includeStartsWith.size(); ++i) {
               didSomeIncludeMatching = true;
               accept = fastClassName.startsWith((String)this.m_includeStartsWith.get(i));
               if (accept) {
                  return true;
               }
            }

            if (this.includeTypePattern.isEmpty()) {
               return !didSomeIncludeMatching;
            }
         }

         try {
            this.ensureDelegateInitialized(className, bytes);
            ResolvedType classInfo = this.delegateForCurrentClass.getResolvedTypeX();
            Iterator i$ = this.excludeTypePattern.iterator();

            TypePattern typePattern;
            boolean var9;
            while(i$.hasNext()) {
               typePattern = (TypePattern)i$.next();
               if (typePattern.matchesStatically(classInfo)) {
                  var9 = false;
                  return var9;
               }
            }

            if (this.includeStar) {
               boolean var23 = true;
               return var23;
            }

            if (!this.includeExactName.isEmpty()) {
               didSomeIncludeMatching = true;
               i$ = this.includeExactName.iterator();

               while(i$.hasNext()) {
                  String exactname = (String)i$.next();
                  if (fastClassName.equals(exactname)) {
                     var9 = true;
                     return var9;
                  }
               }
            }

            for(int i = 0; i < this.m_includeStartsWith.size(); ++i) {
               didSomeIncludeMatching = true;
               boolean fastaccept = fastClassName.startsWith((String)this.m_includeStartsWith.get(i));
               if (fastaccept) {
                  var9 = true;
                  return var9;
               }
            }

            accept = !didSomeIncludeMatching;
            i$ = this.includeTypePattern.iterator();

            while(i$.hasNext()) {
               typePattern = (TypePattern)i$.next();
               accept = typePattern.matchesStatically(classInfo);
               if (accept) {
                  break;
               }
            }
         } finally {
            this.bcelWorld.demote();
         }

         return accept;
      }
   }

   private boolean acceptAspect(String aspectClassName) {
      if (this.m_aspectExcludeTypePattern.isEmpty() && this.m_aspectIncludeTypePattern.isEmpty()) {
         return true;
      } else {
         String fastClassName = aspectClassName.replace('/', '.').replace('.', '$');

         int i;
         for(i = 0; i < this.m_aspectExcludeStartsWith.size(); ++i) {
            if (fastClassName.startsWith((String)this.m_aspectExcludeStartsWith.get(i))) {
               return false;
            }
         }

         for(i = 0; i < this.m_aspectIncludeStartsWith.size(); ++i) {
            if (fastClassName.startsWith((String)this.m_aspectIncludeStartsWith.get(i))) {
               return true;
            }
         }

         ResolvedType classInfo = this.weaver.getWorld().resolve(UnresolvedType.forName(aspectClassName), true);
         Iterator iterator = this.m_aspectExcludeTypePattern.iterator();

         while(iterator.hasNext()) {
            TypePattern typePattern = (TypePattern)iterator.next();
            if (typePattern.matchesStatically(classInfo)) {
               return false;
            }
         }

         boolean accept = true;
         Iterator iterator = this.m_aspectIncludeTypePattern.iterator();

         while(iterator.hasNext()) {
            TypePattern typePattern = (TypePattern)iterator.next();
            accept = typePattern.matchesStatically(classInfo);
            if (accept) {
               break;
            }
         }

         return accept;
      }
   }

   protected boolean shouldDump(String className, boolean before) {
      if (before && !this.m_dumpBefore) {
         return false;
      } else if (this.m_dumpTypePattern.isEmpty()) {
         return false;
      } else {
         ResolvedType classInfo = this.weaver.getWorld().resolve(UnresolvedType.forName(className), true);
         Iterator iterator = this.m_dumpTypePattern.iterator();

         TypePattern typePattern;
         do {
            if (!iterator.hasNext()) {
               return false;
            }

            typePattern = (TypePattern)iterator.next();
         } while(!typePattern.matchesStatically(classInfo));

         return true;
      }
   }

   protected String getDumpDir() {
      if (this.dumpDirPerClassloader) {
         StringBuffer dir = new StringBuffer();
         dir.append("_ajdump").append(File.separator).append(this.weavingContext.getId());
         return dir.toString();
      } else {
         return super.getDumpDir();
      }
   }

   public String getNamespace() {
      return this.namespace == null ? "" : new String(this.namespace);
   }

   public boolean generatedClassesExistFor(String className) {
      if (className == null) {
         return !this.generatedClasses.isEmpty();
      } else {
         return this.generatedClasses.containsKey(className);
      }
   }

   public void flushGeneratedClasses() {
      this.generatedClasses = new HashMap();
   }

   private void defineClass(ClassLoader loader, String name, byte[] bytes) {
      if (trace.isTraceEnabled()) {
         trace.enter("defineClass", this, (Object[])(new Object[]{loader, name, bytes}));
      }

      Object clazz = null;
      this.debug("generating class '" + name + "'");

      try {
         if (this.defineClassMethod == null) {
            this.defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, bytes.getClass(), Integer.TYPE, Integer.TYPE);
         }

         this.defineClassMethod.setAccessible(true);
         clazz = this.defineClassMethod.invoke(loader, name, bytes, new Integer(0), new Integer(bytes.length));
      } catch (InvocationTargetException var6) {
         if (var6.getTargetException() instanceof LinkageError) {
            this.warn("define generated class failed", var6.getTargetException());
         } else {
            this.warn("define generated class failed", var6.getTargetException());
         }
      } catch (Exception var7) {
         this.warn("define generated class failed", var7);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("defineClass", clazz);
      }

   }

   private void defineClass(ClassLoader loader, String name, byte[] bytes, ProtectionDomain protectionDomain) {
      if (trace.isTraceEnabled()) {
         trace.enter("defineClass", this, (Object[])(new Object[]{loader, name, bytes, protectionDomain}));
      }

      Object clazz = null;
      this.debug("generating class '" + name + "'");

      try {
         if (this.defineClassWithProtectionDomainMethod == null) {
            this.defineClassWithProtectionDomainMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, bytes.getClass(), Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
         }

         this.defineClassWithProtectionDomainMethod.setAccessible(true);
         clazz = this.defineClassWithProtectionDomainMethod.invoke(loader, name, bytes, 0, new Integer(bytes.length), protectionDomain);
      } catch (InvocationTargetException var7) {
         if (var7.getTargetException() instanceof LinkageError) {
            this.warn("define generated class failed", var7.getTargetException());
         } else {
            this.warn("define generated class failed", var7.getTargetException());
         }
      } catch (Exception var8) {
         this.warn("define generated class failed", var8);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("defineClass", clazz);
      }

   }

   class SimpleGeneratedClassHandler implements GeneratedClassHandler {
      private BcelWeakClassLoaderReference loaderRef;

      SimpleGeneratedClassHandler(ClassLoader loader) {
         this.loaderRef = new BcelWeakClassLoaderReference(loader);
      }

      public void acceptClass(String name, byte[] originalBytes, byte[] wovenBytes) {
         try {
            if (ClassLoaderWeavingAdaptor.this.shouldDump(name.replace('/', '.'), false)) {
               ClassLoaderWeavingAdaptor.this.dump(name, wovenBytes, false);
            }
         } catch (Throwable var5) {
            var5.printStackTrace();
         }

         if (ClassLoaderWeavingAdaptor.this.activeProtectionDomain != null) {
            ClassLoaderWeavingAdaptor.this.defineClass(this.loaderRef.getClassLoader(), name, wovenBytes, ClassLoaderWeavingAdaptor.this.activeProtectionDomain);
         } else {
            ClassLoaderWeavingAdaptor.this.defineClass(this.loaderRef.getClassLoader(), name, wovenBytes);
         }

      }
   }
}
