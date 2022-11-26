package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageContext;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.IMessageHolder;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageHandler;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.MessageWriter;
import com.bea.core.repackaged.aspectj.bridge.WeaveMessage;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.IClassFileProvider;
import com.bea.core.repackaged.aspectj.weaver.ICrossReferenceHandler;
import com.bea.core.repackaged.aspectj.weaver.IUnwovenClassFile;
import com.bea.core.repackaged.aspectj.weaver.IWeaveRequestor;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelObjectType;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWeaver;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWorld;
import com.bea.core.repackaged.aspectj.weaver.bcel.UnwovenClassFile;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.CachedClassEntry;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.CachedClassReference;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.SimpleCache;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.SimpleCacheFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.WeavedClassCache;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

public class WeavingAdaptor implements IMessageContext {
   public static final String WEAVING_ADAPTOR_VERBOSE = "aj.weaving.verbose";
   public static final String SHOW_WEAVE_INFO_PROPERTY = "com.bea.core.repackaged.aspectj.weaver.showWeaveInfo";
   public static final String TRACE_MESSAGES_PROPERTY = "com.bea.core.repackaged.aspectj.tracing.messages";
   private boolean enabled = false;
   protected boolean verbose = getVerbose();
   protected BcelWorld bcelWorld;
   protected BcelWeaver weaver;
   private IMessageHandler messageHandler;
   private WeavingAdaptorMessageHolder messageHolder;
   private boolean abortOnError = false;
   protected GeneratedClassHandler generatedClassHandler;
   protected Map generatedClasses = new HashMap();
   public BcelObjectType delegateForCurrentClass;
   protected ProtectionDomain activeProtectionDomain;
   private boolean haveWarnedOnJavax = false;
   protected WeavedClassCache cache;
   private int weavingSpecialTypes = 0;
   private static final int INITIALIZED = 1;
   private static final int WEAVE_JAVA_PACKAGE = 2;
   private static final int WEAVE_JAVAX_PACKAGE = 4;
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(WeavingAdaptor.class);
   private ThreadLocal weaverRunning = new ThreadLocal() {
      protected Boolean initialValue() {
         return Boolean.FALSE;
      }
   };

   protected WeavingAdaptor() {
   }

   public WeavingAdaptor(WeavingClassLoader loader) {
      this.generatedClassHandler = loader;
      this.init((ClassLoader)loader, this.getFullClassPath((ClassLoader)loader), this.getFullAspectPath((ClassLoader)loader));
   }

   public WeavingAdaptor(GeneratedClassHandler handler, URL[] classURLs, URL[] aspectURLs) {
      this.generatedClassHandler = handler;
      this.init((ClassLoader)null, FileUtil.makeClasspath(classURLs), FileUtil.makeClasspath(aspectURLs));
   }

   protected List getFullClassPath(ClassLoader loader) {
      LinkedList list;
      for(list = new LinkedList(); loader != null; loader = loader.getParent()) {
         if (loader instanceof URLClassLoader) {
            URL[] urls = ((URLClassLoader)loader).getURLs();
            list.addAll(0, FileUtil.makeClasspath(urls));
         } else {
            this.warn("cannot determine classpath");
         }
      }

      list.addAll(0, makeClasspath(System.getProperty("sun.boot.class.path")));
      return list;
   }

   private List getFullAspectPath(ClassLoader loader) {
      LinkedList list;
      for(list = new LinkedList(); loader != null; loader = loader.getParent()) {
         if (loader instanceof WeavingClassLoader) {
            URL[] urls = ((WeavingClassLoader)loader).getAspectURLs();
            list.addAll(0, FileUtil.makeClasspath(urls));
         }
      }

      return list;
   }

   private static boolean getVerbose() {
      try {
         return Boolean.getBoolean("aj.weaving.verbose");
      } catch (Throwable var1) {
         return false;
      }
   }

   private void init(ClassLoader loader, List classPath, List aspectPath) {
      this.abortOnError = true;
      this.createMessageHandler();
      this.info("using classpath: " + classPath);
      this.info("using aspectpath: " + aspectPath);
      this.bcelWorld = new BcelWorld(classPath, this.messageHandler, (ICrossReferenceHandler)null);
      this.bcelWorld.setXnoInline(false);
      this.bcelWorld.getLint().loadDefaultProperties();
      if (LangUtil.is15VMOrGreater()) {
         this.bcelWorld.setBehaveInJava5Way(true);
      }

      this.weaver = new BcelWeaver(this.bcelWorld);
      this.registerAspectLibraries(aspectPath);
      this.initializeCache(loader, aspectPath, (GeneratedClassHandler)null, this.getMessageHandler());
      this.enabled = true;
   }

   protected void initializeCache(ClassLoader loader, List aspects, GeneratedClassHandler existingClassHandler, IMessageHandler myMessageHandler) {
      if (WeavedClassCache.isEnabled()) {
         this.cache = WeavedClassCache.createCache(loader, aspects, existingClassHandler, myMessageHandler);
         if (this.cache != null) {
            this.generatedClassHandler = this.cache.getCachingClassHandler();
         }
      }

   }

   protected void createMessageHandler() {
      this.messageHolder = new WeavingAdaptorMessageHolder(new PrintWriter(System.err));
      this.messageHandler = this.messageHolder;
      if (this.verbose) {
         this.messageHandler.dontIgnore(IMessage.INFO);
      }

      if (Boolean.getBoolean("com.bea.core.repackaged.aspectj.weaver.showWeaveInfo")) {
         this.messageHandler.dontIgnore(IMessage.WEAVEINFO);
      }

      this.info("AspectJ Weaver Version 1.8.9 built on Monday Mar 14, 2016 at 21:18:16 GMT");
   }

   protected IMessageHandler getMessageHandler() {
      return this.messageHandler;
   }

   public IMessageHolder getMessageHolder() {
      return this.messageHolder;
   }

   protected void setMessageHandler(IMessageHandler mh) {
      if (mh instanceof ISupportsMessageContext) {
         ISupportsMessageContext smc = (ISupportsMessageContext)mh;
         smc.setMessageContext(this);
      }

      if (mh != this.messageHolder) {
         this.messageHolder.setDelegate(mh);
      }

      this.messageHolder.flushMessages();
   }

   protected void disable() {
      if (trace.isTraceEnabled()) {
         trace.enter("disable", this);
      }

      this.enabled = false;
      this.messageHolder.flushMessages();
      if (trace.isTraceEnabled()) {
         trace.exit("disable");
      }

   }

   protected void enable() {
      this.enabled = true;
      this.messageHolder.flushMessages();
   }

   protected boolean isEnabled() {
      return this.enabled;
   }

   public void addURL(URL url) {
      File libFile = new File(url.getPath());

      try {
         this.weaver.addLibraryJarFile(libFile);
      } catch (IOException var4) {
         this.warn("bad library: '" + libFile + "'");
      }

   }

   public byte[] weaveClass(String name, byte[] bytes) throws IOException {
      return this.weaveClass(name, bytes, false);
   }

   public byte[] weaveClass(String name, byte[] bytes, boolean mustWeave) throws IOException {
      if (trace == null) {
         System.err.println("AspectJ Weaver cannot continue to weave, static state has been cleared.  Are you under Tomcat? In order to weave '" + name + "' during shutdown, 'org.apache.catalina.loader.WebappClassLoader.ENABLE_CLEAR_REFERENCES=false' must be set (see https://bugs.eclipse.org/bugs/show_bug.cgi?id=231945).");
         return bytes;
      } else if ((Boolean)this.weaverRunning.get()) {
         return bytes;
      } else {
         byte[] var4;
         try {
            this.weaverRunning.set(true);
            if (trace.isTraceEnabled()) {
               trace.enter("weaveClass", this, (Object[])(new Object[]{name, bytes}));
            }

            if (this.enabled) {
               boolean debugOn = !this.messageHandler.isIgnoring(Message.DEBUG);

               try {
                  this.delegateForCurrentClass = null;
                  name = name.replace('/', '.');
                  if (this.couldWeave(name, bytes)) {
                     if (this.accept(name, bytes)) {
                        CachedClassReference cacheKey = null;
                        byte[] original_bytes = bytes;
                        if (this.cache != null && !mustWeave) {
                           cacheKey = this.cache.createCacheKey(name, bytes);
                           CachedClassEntry entry = this.cache.get(cacheKey, bytes);
                           if (entry != null) {
                              byte[] var8;
                              if (entry.isIgnored()) {
                                 var8 = bytes;
                                 return var8;
                              }

                              var8 = entry.getBytes();
                              return var8;
                           }
                        }

                        if (debugOn) {
                           this.debug("weaving '" + name + "'");
                        }

                        bytes = this.getWovenBytes(name, bytes);
                        if (cacheKey != null) {
                           if (Arrays.equals(original_bytes, bytes)) {
                              this.cache.ignore(cacheKey, original_bytes);
                           } else {
                              this.cache.put(cacheKey, original_bytes, bytes);
                           }
                        }
                     } else if (debugOn) {
                        this.debug("not weaving '" + name + "'");
                     }
                  } else if (debugOn) {
                     this.debug("cannot weave '" + name + "'");
                  }
               } finally {
                  this.delegateForCurrentClass = null;
               }

               if (trace.isTraceEnabled()) {
                  trace.exit("weaveClass", (Object)bytes);
               }

               byte[] var18 = bytes;
               return var18;
            }

            if (trace.isTraceEnabled()) {
               trace.exit("weaveClass", false);
            }

            var4 = bytes;
         } finally {
            this.weaverRunning.set(false);
         }

         return var4;
      }
   }

   private boolean couldWeave(String name, byte[] bytes) {
      return !this.generatedClasses.containsKey(name) && this.shouldWeaveName(name);
   }

   protected boolean accept(String name, byte[] bytes) {
      return true;
   }

   protected boolean shouldDump(String name, boolean before) {
      return false;
   }

   private boolean shouldWeaveName(String name) {
      if ("osj".indexOf(name.charAt(0)) != -1) {
         if ((this.weavingSpecialTypes & 1) == 0) {
            this.weavingSpecialTypes |= 1;
            Properties p = this.weaver.getWorld().getExtraConfiguration();
            if (p != null) {
               boolean b = p.getProperty("weaveJavaPackages", "false").equalsIgnoreCase("true");
               if (b) {
                  this.weavingSpecialTypes |= 2;
               }

               b = p.getProperty("weaveJavaxPackages", "false").equalsIgnoreCase("true");
               if (b) {
                  this.weavingSpecialTypes |= 4;
               }
            }
         }

         if (name.startsWith("com.bea.core.repackaged.aspectj.")) {
            return false;
         }

         if (name.startsWith("sun.reflect.")) {
            return false;
         }

         if (name.startsWith("javax.")) {
            if ((this.weavingSpecialTypes & 4) != 0) {
               return true;
            }

            if (!this.haveWarnedOnJavax) {
               this.haveWarnedOnJavax = true;
               this.warn("javax.* types are not being woven because the weaver option '-Xset:weaveJavaxPackages=true' has not been specified");
            }

            return false;
         }

         if (name.startsWith("java.")) {
            if ((this.weavingSpecialTypes & 2) != 0) {
               return true;
            }

            return false;
         }
      }

      return true;
   }

   private boolean shouldWeaveAnnotationStyleAspect(String name, byte[] bytes) {
      if (this.delegateForCurrentClass == null) {
         this.ensureDelegateInitialized(name, bytes);
      }

      return this.delegateForCurrentClass.isAnnotationStyleAspect();
   }

   protected void ensureDelegateInitialized(String name, byte[] bytes) {
      if (this.delegateForCurrentClass == null) {
         BcelWorld world = (BcelWorld)this.weaver.getWorld();
         this.delegateForCurrentClass = world.addSourceObjectType(name, bytes, false);
      }

   }

   private byte[] getWovenBytes(String name, byte[] bytes) throws IOException {
      WeavingClassFileProvider wcp = new WeavingClassFileProvider(name, bytes);
      this.weaver.weave((IClassFileProvider)wcp);
      return wcp.getBytes();
   }

   private byte[] getAtAspectJAspectBytes(String name, byte[] bytes) throws IOException {
      WeavingClassFileProvider wcp = new WeavingClassFileProvider(name, bytes);
      wcp.setApplyAtAspectJMungersOnly();
      this.weaver.weave((IClassFileProvider)wcp);
      return wcp.getBytes();
   }

   private void registerAspectLibraries(List aspectPath) {
      Iterator i = aspectPath.iterator();

      while(i.hasNext()) {
         String libName = (String)i.next();
         this.addAspectLibrary(libName);
      }

      this.weaver.prepareForWeave();
   }

   private void addAspectLibrary(String aspectLibraryName) {
      File aspectLibrary = new File(aspectLibraryName);
      if (!aspectLibrary.isDirectory() && !FileUtil.isZipFile(aspectLibrary)) {
         this.error("bad aspect library: '" + aspectLibrary + "'");
      } else {
         try {
            this.info("adding aspect library: '" + aspectLibrary + "'");
            this.weaver.addLibraryJarFile(aspectLibrary);
         } catch (IOException var4) {
            this.error("exception adding aspect library: '" + var4 + "'");
         }
      }

   }

   private static List makeClasspath(String cp) {
      List ret = new ArrayList();
      if (cp != null) {
         StringTokenizer tok = new StringTokenizer(cp, File.pathSeparator);

         while(tok.hasMoreTokens()) {
            ret.add(tok.nextToken());
         }
      }

      return ret;
   }

   protected boolean debug(String message) {
      return MessageUtil.debug(this.messageHandler, message);
   }

   protected boolean info(String message) {
      return MessageUtil.info(this.messageHandler, message);
   }

   protected boolean warn(String message) {
      return MessageUtil.warn(this.messageHandler, message);
   }

   protected boolean warn(String message, Throwable th) {
      return this.messageHandler.handleMessage(new Message(message, IMessage.WARNING, th, (ISourceLocation)null));
   }

   protected boolean error(String message) {
      return MessageUtil.error(this.messageHandler, message);
   }

   protected boolean error(String message, Throwable th) {
      return this.messageHandler.handleMessage(new Message(message, IMessage.ERROR, th, (ISourceLocation)null));
   }

   public String getContextId() {
      return "WeavingAdaptor";
   }

   protected void dump(String name, byte[] b, boolean before) {
      String dirName = this.getDumpDir();
      if (before) {
         dirName = dirName + File.separator + "_before";
      }

      String className = name.replace('.', '/');
      File dir;
      if (className.indexOf(47) > 0) {
         dir = new File(dirName + File.separator + className.substring(0, className.lastIndexOf(47)));
      } else {
         dir = new File(dirName);
      }

      dir.mkdirs();
      String fileName = dirName + File.separator + className + ".class";

      try {
         FileOutputStream os = new FileOutputStream(fileName);
         os.write(b);
         os.close();
      } catch (IOException var9) {
         this.warn("unable to dump class " + name + " in directory " + dirName, var9);
      }

   }

   protected String getDumpDir() {
      return "_ajdump";
   }

   public void setActiveProtectionDomain(ProtectionDomain protectionDomain) {
      this.activeProtectionDomain = protectionDomain;
   }

   private class WeavingClassFileProvider implements IClassFileProvider {
      private final UnwovenClassFile unwovenClass;
      private final List unwovenClasses = new ArrayList();
      private IUnwovenClassFile wovenClass;
      private boolean isApplyAtAspectJMungersOnly = false;

      public WeavingClassFileProvider(String name, byte[] bytes) {
         WeavingAdaptor.this.ensureDelegateInitialized(name, bytes);
         this.unwovenClass = new UnwovenClassFile(name, WeavingAdaptor.this.delegateForCurrentClass.getResolvedTypeX().getName(), bytes);
         this.unwovenClasses.add(this.unwovenClass);
         if (WeavingAdaptor.this.shouldDump(name.replace('/', '.'), true)) {
            WeavingAdaptor.this.dump(name, bytes, true);
         }

      }

      public void setApplyAtAspectJMungersOnly() {
         this.isApplyAtAspectJMungersOnly = true;
      }

      public boolean isApplyAtAspectJMungersOnly() {
         return this.isApplyAtAspectJMungersOnly;
      }

      public byte[] getBytes() {
         return this.wovenClass != null ? this.wovenClass.getBytes() : this.unwovenClass.getBytes();
      }

      public Iterator getClassFileIterator() {
         return this.unwovenClasses.iterator();
      }

      public IWeaveRequestor getRequestor() {
         return new IWeaveRequestor() {
            public void acceptResult(IUnwovenClassFile result) {
               String name;
               if (WeavingClassFileProvider.this.wovenClass == null) {
                  WeavingClassFileProvider.this.wovenClass = result;
                  name = result.getClassName();
                  if (WeavingAdaptor.this.shouldDump(name.replace('/', '.'), false)) {
                     WeavingAdaptor.this.dump(name, result.getBytes(), false);
                  }
               } else {
                  name = result.getClassName();
                  byte[] resultBytes = result.getBytes();
                  if (SimpleCacheFactory.isEnabled()) {
                     SimpleCache lacache = SimpleCacheFactory.createSimpleCache();
                     lacache.put(result.getClassName(), WeavingClassFileProvider.this.wovenClass.getBytes(), result.getBytes());
                     lacache.addGeneratedClassesNames(WeavingClassFileProvider.this.wovenClass.getClassName(), WeavingClassFileProvider.this.wovenClass.getBytes(), result.getClassName());
                  }

                  WeavingAdaptor.this.generatedClasses.put(name, result);
                  WeavingAdaptor.this.generatedClasses.put(WeavingClassFileProvider.this.wovenClass.getClassName(), result);
                  WeavingAdaptor.this.generatedClassHandler.acceptClass(name, (byte[])null, resultBytes);
               }

            }

            public void processingReweavableState() {
            }

            public void addingTypeMungers() {
            }

            public void weavingAspects() {
            }

            public void weavingClasses() {
            }

            public void weaveCompleted() {
               if (WeavingAdaptor.this.delegateForCurrentClass != null) {
                  WeavingAdaptor.this.delegateForCurrentClass.weavingCompleted();
               }

            }
         };
      }
   }

   protected class WeavingAdaptorMessageWriter extends MessageWriter {
      private final Set ignoring = new HashSet();
      private final IMessage.Kind failKind;

      public WeavingAdaptorMessageWriter(PrintWriter writer) {
         super(writer, true);
         this.ignore(IMessage.WEAVEINFO);
         this.ignore(IMessage.DEBUG);
         this.ignore(IMessage.INFO);
         this.failKind = IMessage.ERROR;
      }

      public boolean handleMessage(IMessage message) throws AbortException {
         super.handleMessage(message);
         if (WeavingAdaptor.this.abortOnError && 0 <= message.getKind().compareTo(this.failKind)) {
            throw new AbortException(message);
         } else {
            return true;
         }
      }

      public boolean isIgnoring(IMessage.Kind kind) {
         return null != kind && this.ignoring.contains(kind);
      }

      public void ignore(IMessage.Kind kind) {
         if (null != kind && !this.ignoring.contains(kind)) {
            this.ignoring.add(kind);
         }

      }

      public void dontIgnore(IMessage.Kind kind) {
         if (null != kind) {
            this.ignoring.remove(kind);
         }

      }

      protected String render(IMessage message) {
         return "[" + WeavingAdaptor.this.getContextId() + "] " + super.render(message);
      }
   }

   protected class WeavingAdaptorMessageHolder extends MessageHandler {
      private IMessageHandler delegate;
      private List savedMessages;
      protected boolean traceMessages = Boolean.getBoolean("com.bea.core.repackaged.aspectj.tracing.messages");

      public WeavingAdaptorMessageHolder(PrintWriter writer) {
         this.delegate = WeavingAdaptor.this.new WeavingAdaptorMessageWriter(writer);
         super.dontIgnore(IMessage.WEAVEINFO);
      }

      private void traceMessage(IMessage message) {
         if (message instanceof WeaveMessage) {
            WeavingAdaptor.trace.debug(this.render(message));
         } else if (message.isDebug()) {
            WeavingAdaptor.trace.debug(this.render(message));
         } else if (message.isInfo()) {
            WeavingAdaptor.trace.info(this.render(message));
         } else if (message.isWarning()) {
            WeavingAdaptor.trace.warn(this.render(message), message.getThrown());
         } else if (message.isError()) {
            WeavingAdaptor.trace.error(this.render(message), message.getThrown());
         } else if (message.isFailed()) {
            WeavingAdaptor.trace.fatal(this.render(message), message.getThrown());
         } else if (message.isAbort()) {
            WeavingAdaptor.trace.fatal(this.render(message), message.getThrown());
         } else {
            WeavingAdaptor.trace.error(this.render(message), message.getThrown());
         }

      }

      protected String render(IMessage message) {
         return "[" + WeavingAdaptor.this.getContextId() + "] " + message.toString();
      }

      public void flushMessages() {
         if (this.savedMessages == null) {
            this.savedMessages = new ArrayList();
            this.savedMessages.addAll(super.getUnmodifiableListView());
            this.clearMessages();
            Iterator i$ = this.savedMessages.iterator();

            while(i$.hasNext()) {
               IMessage message = (IMessage)i$.next();
               this.delegate.handleMessage(message);
            }
         }

      }

      public void setDelegate(IMessageHandler messageHandler) {
         this.delegate = messageHandler;
      }

      public boolean handleMessage(IMessage message) throws AbortException {
         if (this.traceMessages) {
            this.traceMessage(message);
         }

         super.handleMessage(message);
         if (WeavingAdaptor.this.abortOnError && 0 <= message.getKind().compareTo(IMessage.ERROR)) {
            throw new AbortException(message);
         } else {
            if (this.savedMessages != null) {
               this.delegate.handleMessage(message);
            }

            return true;
         }
      }

      public boolean isIgnoring(IMessage.Kind kind) {
         return this.delegate.isIgnoring(kind);
      }

      public void dontIgnore(IMessage.Kind kind) {
         if (null != kind && this.delegate != null) {
            this.delegate.dontIgnore(kind);
         }

      }

      public void ignore(IMessage.Kind kind) {
         if (null != kind && this.delegate != null) {
            this.delegate.ignore(kind);
         }

      }

      public List getUnmodifiableListView() {
         List allMessages = new ArrayList();
         allMessages.addAll(this.savedMessages);
         allMessages.addAll(super.getUnmodifiableListView());
         return allMessages;
      }
   }
}
