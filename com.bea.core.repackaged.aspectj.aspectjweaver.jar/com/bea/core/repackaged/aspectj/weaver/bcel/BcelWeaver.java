package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassParser;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.asm.internal.AspectJElementHierarchy;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.bridge.WeaveMessage;
import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import com.bea.core.repackaged.aspectj.bridge.context.ContextToken;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AdviceKind;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.AnnotationOnTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.CrosscuttingMembersSet;
import com.bea.core.repackaged.aspectj.weaver.CustomMungerFactory;
import com.bea.core.repackaged.aspectj.weaver.IClassFileProvider;
import com.bea.core.repackaged.aspectj.weaver.IUnwovenClassFile;
import com.bea.core.repackaged.aspectj.weaver.IWeaveRequestor;
import com.bea.core.repackaged.aspectj.weaver.NewParentTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.model.AsmRelationshipProvider;
import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.BindingPattern;
import com.bea.core.repackaged.aspectj.weaver.patterns.BindingTypePattern;
import com.bea.core.repackaged.aspectj.weaver.patterns.ConcreteCflowPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareTypeErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.FastMatchInfo;
import com.bea.core.repackaged.aspectj.weaver.patterns.IfPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.KindedPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NameBindingPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.OrPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.PointcutRewriter;
import com.bea.core.repackaged.aspectj.weaver.patterns.WithinPointcut;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BcelWeaver {
   public static final String CLOSURE_CLASS_PREFIX = "$Ajc";
   public static final String SYNTHETIC_CLASS_POSTFIX = "$ajc";
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(BcelWeaver.class);
   private final transient BcelWorld world;
   private final CrosscuttingMembersSet xcutSet;
   private boolean inReweavableMode = false;
   private transient List addedClasses = new ArrayList();
   private transient List deletedTypenames = new ArrayList();
   private transient List shadowMungerList = null;
   private transient List typeMungerList = null;
   private transient List lateTypeMungerList = null;
   private transient List declareParentsList = null;
   private Manifest manifest = null;
   private boolean needToReweaveWorld = false;
   private boolean isBatchWeave = true;
   private ZipOutputStream zipOutputStream;
   private CustomMungerFactory customMungerFactory;
   private Set candidatesForRemoval = null;

   public BcelWeaver(BcelWorld world) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object)world);
      }

      this.world = world;
      this.xcutSet = world.getCrosscuttingMembersSet();
      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   public ResolvedType addLibraryAspect(String aspectName) {
      if (trace.isTraceEnabled()) {
         trace.enter("addLibraryAspect", this, (Object)aspectName);
      }

      UnresolvedType unresolvedT = UnresolvedType.forName(aspectName);
      unresolvedT.setNeedsModifiableDelegate(true);
      ResolvedType type = this.world.resolve(unresolvedT, true);
      if (type.isMissing()) {
         String fixedName = aspectName;
         int hasDot = aspectName.lastIndexOf(46);

         while(hasDot > 0) {
            char[] fixedNameChars = fixedName.toCharArray();
            fixedNameChars[hasDot] = '$';
            fixedName = new String(fixedNameChars);
            hasDot = fixedName.lastIndexOf(46);
            UnresolvedType ut = UnresolvedType.forName(fixedName);
            ut.setNeedsModifiableDelegate(true);
            type = this.world.resolve(ut, true);
            if (!type.isMissing()) {
               break;
            }
         }
      }

      if (type.isAspect()) {
         WeaverStateInfo wsi = type.getWeaverState();
         if (wsi != null && wsi.isReweavable()) {
            BcelObjectType classType = this.getClassType(type.getName());
            JavaClass wovenJavaClass = classType.getJavaClass();
            byte[] bytes = wsi.getUnwovenClassFileData(wovenJavaClass.getBytes());
            JavaClass unwovenJavaClass = Utility.makeJavaClass(wovenJavaClass.getFileName(), bytes);
            this.world.storeClass(unwovenJavaClass);
            classType.setJavaClass(unwovenJavaClass, true);
         }

         this.xcutSet.addOrReplaceAspect(type);
         if (trace.isTraceEnabled()) {
            trace.exit("addLibraryAspect", (Object)type);
         }

         if (type.getSuperclass().isAspect()) {
            this.addLibraryAspect(type.getSuperclass().getName());
         }

         return type;
      } else {
         RuntimeException ex = new RuntimeException("Cannot register non aspect: " + type.getName() + " , " + aspectName);
         if (trace.isTraceEnabled()) {
            trace.exit("addLibraryAspect", (Throwable)ex);
         }

         throw ex;
      }
   }

   public void addLibraryJarFile(File inFile) throws IOException {
      List addedAspects = null;
      if (inFile.isDirectory()) {
         addedAspects = this.addAspectsFromDirectory(inFile);
      } else {
         addedAspects = this.addAspectsFromJarFile(inFile);
      }

      Iterator i$ = addedAspects.iterator();

      while(i$.hasNext()) {
         ResolvedType addedAspect = (ResolvedType)i$.next();
         this.xcutSet.addOrReplaceAspect(addedAspect);
      }

   }

   private List addAspectsFromJarFile(File inFile) throws FileNotFoundException, IOException {
      ZipInputStream inStream = new ZipInputStream(new FileInputStream(inFile));
      List addedAspects = new ArrayList();

      try {
         while(true) {
            ZipEntry entry = inStream.getNextEntry();
            if (entry == null) {
               return addedAspects;
            }

            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
               int size = (int)entry.getSize();
               ClassParser parser = new ClassParser(new ByteArrayInputStream(FileUtil.readAsByteArray((InputStream)inStream)), entry.getName());
               JavaClass jc = parser.parse();
               inStream.closeEntry();
               ResolvedType type = this.world.addSourceObjectType(jc, false).getResolvedTypeX();
               type.setBinaryPath(inFile.getAbsolutePath());
               if (type.isAspect()) {
                  addedAspects.add(type);
               } else {
                  this.world.demote(type);
               }
            }
         }
      } finally {
         inStream.close();
      }
   }

   private List addAspectsFromDirectory(File directory) throws FileNotFoundException, IOException {
      List addedAspects = new ArrayList();
      File[] classFiles = FileUtil.listFiles(directory, new FileFilter() {
         public boolean accept(File pathname) {
            return pathname.getName().endsWith(".class");
         }
      });
      File[] arr$ = classFiles;
      int len$ = classFiles.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         File classFile = arr$[i$];
         FileInputStream fis = new FileInputStream(classFile);
         byte[] classBytes = FileUtil.readAsByteArray((InputStream)fis);
         ResolvedType aspectType = this.isAspect(classBytes, classFile.getAbsolutePath(), directory);
         if (aspectType != null) {
            addedAspects.add(aspectType);
         }

         fis.close();
      }

      return addedAspects;
   }

   private ResolvedType isAspect(byte[] classbytes, String name, File dir) throws IOException {
      ClassParser parser = new ClassParser(new ByteArrayInputStream(classbytes), name);
      JavaClass jc = parser.parse();
      ResolvedType type = this.world.addSourceObjectType(jc, false).getResolvedTypeX();
      String typeName = type.getName().replace('.', File.separatorChar);
      int end = name.lastIndexOf(typeName + ".class");
      String binaryPath = null;
      if (end == -1) {
         binaryPath = dir.getAbsolutePath();
      } else {
         binaryPath = name.substring(0, end - 1);
      }

      type.setBinaryPath(binaryPath);
      if (type.isAspect()) {
         return type;
      } else {
         this.world.demote(type);
         return null;
      }
   }

   public List addDirectoryContents(File inFile, File outDir) throws IOException {
      List addedClassFiles = new ArrayList();
      File[] files = FileUtil.listFiles(inFile, new FileFilter() {
         public boolean accept(File f) {
            boolean accept = !f.isDirectory();
            return accept;
         }
      });

      for(int i = 0; i < files.length; ++i) {
         addedClassFiles.add(this.addClassFile(files[i], inFile, outDir));
      }

      return addedClassFiles;
   }

   public List addJarFile(File inFile, File outDir, boolean canBeDirectory) {
      List addedClassFiles = new ArrayList();
      this.needToReweaveWorld = true;
      JarFile inJar = null;
      boolean var26 = false;

      Message message;
      label237: {
         label238: {
            try {
               var26 = true;
               if (inFile.isDirectory() && canBeDirectory) {
                  addedClassFiles.addAll(this.addDirectoryContents(inFile, outDir));
                  var26 = false;
               } else {
                  inJar = new JarFile(inFile);

                  try {
                     this.addManifest(inJar.getManifest());

                     InputStream inStream;
                     for(Enumeration entries = inJar.entries(); entries.hasMoreElements(); inStream.close()) {
                        JarEntry entry = (JarEntry)entries.nextElement();
                        inStream = inJar.getInputStream(entry);
                        byte[] bytes = FileUtil.readAsByteArray(inStream);
                        String filename = entry.getName();
                        UnwovenClassFile classFile = new UnwovenClassFile((new File(outDir, filename)).getAbsolutePath(), bytes);
                        if (filename.endsWith(".class")) {
                           ReferenceType type = this.addClassFile(classFile, false);
                           StringBuffer sb = new StringBuffer();
                           sb.append(inFile.getAbsolutePath());
                           sb.append("!");
                           sb.append(entry.getName());
                           type.setBinaryPath(sb.toString());
                           addedClassFiles.add(classFile);
                        }
                     }
                  } finally {
                     inJar.close();
                  }

                  inJar.close();
                  var26 = false;
               }
               break label237;
            } catch (FileNotFoundException var40) {
               message = new Message("Could not find input jar file " + inFile.getPath() + ", ignoring", new SourceLocation(inFile, 0), false);
               this.world.getMessageHandler().handleMessage(message);
               var26 = false;
            } catch (IOException var41) {
               message = new Message("Could not read input jar file " + inFile.getPath() + "(" + var41.getMessage() + ")", new SourceLocation(inFile, 0), true);
               this.world.getMessageHandler().handleMessage(message);
               var26 = false;
               break label238;
            } finally {
               if (var26) {
                  if (inJar != null) {
                     try {
                        inJar.close();
                     } catch (IOException var35) {
                        IMessage message = new Message("Could not close input jar file " + inFile.getPath() + "(" + var35.getMessage() + ")", new SourceLocation(inFile, 0), true);
                        this.world.getMessageHandler().handleMessage(message);
                     }
                  }

               }
            }

            if (inJar != null) {
               try {
                  inJar.close();
               } catch (IOException var37) {
                  message = new Message("Could not close input jar file " + inFile.getPath() + "(" + var37.getMessage() + ")", new SourceLocation(inFile, 0), true);
                  this.world.getMessageHandler().handleMessage(message);
               }

               return addedClassFiles;
            }

            return addedClassFiles;
         }

         if (inJar != null) {
            try {
               inJar.close();
            } catch (IOException var36) {
               message = new Message("Could not close input jar file " + inFile.getPath() + "(" + var36.getMessage() + ")", new SourceLocation(inFile, 0), true);
               this.world.getMessageHandler().handleMessage(message);
            }

            return addedClassFiles;
         }

         return addedClassFiles;
      }

      if (inJar != null) {
         try {
            inJar.close();
         } catch (IOException var38) {
            message = new Message("Could not close input jar file " + inFile.getPath() + "(" + var38.getMessage() + ")", new SourceLocation(inFile, 0), true);
            this.world.getMessageHandler().handleMessage(message);
         }
      }

      return addedClassFiles;
   }

   public boolean needToReweaveWorld() {
      return this.needToReweaveWorld;
   }

   public ReferenceType addClassFile(UnwovenClassFile classFile, boolean fromInpath) {
      this.addedClasses.add(classFile);
      ReferenceType type = this.world.addSourceObjectType(classFile.getJavaClass(), false).getResolvedTypeX();
      if (fromInpath) {
         type.setBinaryPath(classFile.getFilename());
      }

      return type;
   }

   public UnwovenClassFile addClassFile(File classFile, File inPathDir, File outDir) throws IOException {
      FileInputStream fis = new FileInputStream(classFile);
      byte[] bytes = FileUtil.readAsByteArray((InputStream)fis);
      String filename = classFile.getAbsolutePath().substring(inPathDir.getAbsolutePath().length() + 1);
      UnwovenClassFile ucf = new UnwovenClassFile((new File(outDir, filename)).getAbsolutePath(), bytes);
      if (filename.endsWith(".class")) {
         StringBuffer sb = new StringBuffer();
         sb.append(inPathDir.getAbsolutePath());
         sb.append("!");
         sb.append(filename);
         ReferenceType type = this.addClassFile(ucf, false);
         type.setBinaryPath(sb.toString());
      }

      fis.close();
      return ucf;
   }

   public void deleteClassFile(String typename) {
      this.deletedTypenames.add(typename);
      this.world.deleteSourceObjectType(UnresolvedType.forName(typename));
   }

   public void setIsBatchWeave(boolean b) {
      this.isBatchWeave = b;
   }

   public void prepareForWeave() {
      if (trace.isTraceEnabled()) {
         trace.enter("prepareForWeave", this);
      }

      this.needToReweaveWorld = this.xcutSet.hasChangedSinceLastReset();
      Iterator i = this.addedClasses.iterator();

      while(i.hasNext()) {
         UnwovenClassFile jc = (UnwovenClassFile)i.next();
         String name = jc.getClassName();
         ResolvedType type = this.world.resolve(name);
         if (type.isAspect()) {
            this.needToReweaveWorld |= this.xcutSet.addOrReplaceAspect(type);
         }
      }

      i = this.deletedTypenames.iterator();

      while(i.hasNext()) {
         String name = (String)i.next();
         if (this.xcutSet.deleteAspect(UnresolvedType.forName(name))) {
            this.needToReweaveWorld = true;
         }
      }

      this.shadowMungerList = this.xcutSet.getShadowMungers();
      this.rewritePointcuts(this.shadowMungerList);
      this.typeMungerList = this.xcutSet.getTypeMungers();
      this.lateTypeMungerList = this.xcutSet.getLateTypeMungers();
      this.declareParentsList = this.xcutSet.getDeclareParents();
      this.addCustomMungers();
      Collections.sort(this.shadowMungerList, new Comparator() {
         public int compare(ShadowMunger sm1, ShadowMunger sm2) {
            if (sm1.getSourceLocation() == null) {
               return sm2.getSourceLocation() == null ? 0 : 1;
            } else {
               return sm2.getSourceLocation() == null ? -1 : sm2.getSourceLocation().getOffset() - sm1.getSourceLocation().getOffset();
            }
         }
      });
      if (this.inReweavableMode) {
         this.world.showMessage(IMessage.INFO, WeaverMessages.format("reweavableMode"), (ISourceLocation)null, (ISourceLocation)null);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("prepareForWeave");
      }

   }

   private void addCustomMungers() {
      if (this.customMungerFactory != null) {
         Iterator i = this.addedClasses.iterator();

         while(i.hasNext()) {
            UnwovenClassFile jc = (UnwovenClassFile)i.next();
            String name = jc.getClassName();
            ResolvedType type = this.world.resolve(name);
            if (type.isAspect()) {
               Collection shadowMungers = this.customMungerFactory.createCustomShadowMungers(type);
               if (shadowMungers != null) {
                  this.shadowMungerList.addAll(shadowMungers);
               }

               Collection typeMungers = this.customMungerFactory.createCustomTypeMungers(type);
               if (typeMungers != null) {
                  this.typeMungerList.addAll(typeMungers);
               }
            }
         }
      }

   }

   public void setCustomMungerFactory(CustomMungerFactory factory) {
      this.customMungerFactory = factory;
   }

   private void rewritePointcuts(List shadowMungers) {
      PointcutRewriter rewriter = new PointcutRewriter();
      Iterator i$ = shadowMungers.iterator();

      Pointcut newP;
      while(i$.hasNext()) {
         ShadowMunger munger = (ShadowMunger)i$.next();
         Pointcut p = munger.getPointcut();
         newP = rewriter.rewrite(p);
         if (munger instanceof Advice) {
            Advice advice = (Advice)munger;
            if (advice.getSignature() != null) {
               int numFormals;
               String[] names;
               if ((!advice.getConcreteAspect().isAnnotationStyleAspect() || advice.getDeclaringAspect() == null || !advice.getDeclaringAspect().resolve(this.world).isAnnotationStyleAspect()) && !advice.isAnnotationStyle()) {
                  numFormals = advice.getBaseParameterCount();
                  if (numFormals > 0) {
                     names = advice.getBaseParameterNames(this.world);
                     this.validateBindings(newP, p, numFormals, names);
                  }
               } else {
                  numFormals = advice.getBaseParameterCount();
                  int numArgs = advice.getSignature().getParameterTypes().length;
                  if (numFormals > 0) {
                     names = advice.getSignature().getParameterNames(this.world);
                     this.validateBindings(newP, p, numArgs, names);
                  }
               }
            }
         }

         newP.m_ignoreUnboundBindingForNames = p.m_ignoreUnboundBindingForNames;
         munger.setPointcut(newP);
      }

      Map pcMap = new HashMap();
      Iterator i$ = shadowMungers.iterator();

      while(i$.hasNext()) {
         ShadowMunger munger = (ShadowMunger)i$.next();
         newP = munger.getPointcut();
         Pointcut newP = this.shareEntriesFromMap(newP, pcMap);
         newP.m_ignoreUnboundBindingForNames = newP.m_ignoreUnboundBindingForNames;
         munger.setPointcut(newP);
      }

   }

   private Pointcut shareEntriesFromMap(Pointcut p, Map pcMap) {
      if (p instanceof NameBindingPointcut) {
         return p;
      } else if (p instanceof IfPointcut) {
         return p;
      } else if (p instanceof ConcreteCflowPointcut) {
         return p;
      } else {
         Pointcut not;
         Pointcut right;
         if (p instanceof AndPointcut) {
            AndPointcut apc = (AndPointcut)p;
            not = this.shareEntriesFromMap(apc.getLeft(), pcMap);
            right = this.shareEntriesFromMap(apc.getRight(), pcMap);
            return new AndPointcut(not, right);
         } else if (p instanceof OrPointcut) {
            OrPointcut opc = (OrPointcut)p;
            not = this.shareEntriesFromMap(opc.getLeft(), pcMap);
            right = this.shareEntriesFromMap(opc.getRight(), pcMap);
            return new OrPointcut(not, right);
         } else if (p instanceof NotPointcut) {
            NotPointcut npc = (NotPointcut)p;
            not = this.shareEntriesFromMap(npc.getNegatedPointcut(), pcMap);
            return new NotPointcut(not);
         } else if (pcMap.containsKey(p)) {
            return (Pointcut)pcMap.get(p);
         } else {
            pcMap.put(p, p);
            return p;
         }
      }
   }

   private void validateBindings(Pointcut dnfPointcut, Pointcut userPointcut, int numFormals, String[] names) {
      if (numFormals != 0) {
         if (dnfPointcut.couldMatchKinds() != Shadow.NO_SHADOW_KINDS_BITS) {
            if (dnfPointcut instanceof OrPointcut) {
               OrPointcut orBasedDNFPointcut = (OrPointcut)dnfPointcut;
               Pointcut[] leftBindings = new Pointcut[numFormals];
               Pointcut[] rightBindings = new Pointcut[numFormals];
               this.validateOrBranch(orBasedDNFPointcut, userPointcut, numFormals, names, leftBindings, rightBindings);
            } else {
               Pointcut[] bindings = new Pointcut[numFormals];
               this.validateSingleBranch(dnfPointcut, userPointcut, numFormals, names, bindings);
            }

         }
      }
   }

   private void validateOrBranch(OrPointcut pc, Pointcut userPointcut, int numFormals, String[] names, Pointcut[] leftBindings, Pointcut[] rightBindings) {
      Pointcut left = pc.getLeft();
      Pointcut right = pc.getRight();
      Pointcut[] newLeftBindings;
      if (left instanceof OrPointcut) {
         newLeftBindings = new Pointcut[numFormals];
         this.validateOrBranch((OrPointcut)left, userPointcut, numFormals, names, leftBindings, newLeftBindings);
      } else if (left.couldMatchKinds() != Shadow.NO_SHADOW_KINDS_BITS) {
         this.validateSingleBranch(left, userPointcut, numFormals, names, leftBindings);
      }

      if (right instanceof OrPointcut) {
         newLeftBindings = new Pointcut[numFormals];
         this.validateOrBranch((OrPointcut)right, userPointcut, numFormals, names, newLeftBindings, rightBindings);
      } else if (right.couldMatchKinds() != Shadow.NO_SHADOW_KINDS_BITS) {
         this.validateSingleBranch(right, userPointcut, numFormals, names, rightBindings);
      }

      int kindsInCommon = left.couldMatchKinds() & right.couldMatchKinds();
      if (kindsInCommon != Shadow.NO_SHADOW_KINDS_BITS && this.couldEverMatchSameJoinPoints(left, right)) {
         List ambiguousNames = new ArrayList();

         for(int i = 0; i < numFormals; ++i) {
            if (leftBindings[i] == null) {
               if (rightBindings[i] != null) {
                  ambiguousNames.add(names[i]);
               }
            } else if (!leftBindings[i].equals(rightBindings[i])) {
               ambiguousNames.add(names[i]);
            }
         }

         if (!ambiguousNames.isEmpty()) {
            this.raiseAmbiguityInDisjunctionError(userPointcut, ambiguousNames);
         }
      }

   }

   private void validateSingleBranch(Pointcut pc, Pointcut userPointcut, int numFormals, String[] names, Pointcut[] bindings) {
      boolean[] foundFormals = new boolean[numFormals];

      int i;
      for(i = 0; i < foundFormals.length; ++i) {
         foundFormals[i] = false;
      }

      this.validateSingleBranchRecursion(pc, userPointcut, foundFormals, names, bindings);

      for(i = 0; i < foundFormals.length; ++i) {
         if (!foundFormals[i]) {
            boolean ignore = false;

            for(int j = 0; j < userPointcut.m_ignoreUnboundBindingForNames.length; ++j) {
               if (names[i] != null && names[i].equals(userPointcut.m_ignoreUnboundBindingForNames[j])) {
                  ignore = true;
                  break;
               }
            }

            if (!ignore) {
               this.raiseUnboundFormalError(names[i], userPointcut);
            }
         }
      }

   }

   private void validateSingleBranchRecursion(Pointcut pc, Pointcut userPointcut, boolean[] foundFormals, String[] names, Pointcut[] bindings) {
      if (pc instanceof NotPointcut) {
         NotPointcut not = (NotPointcut)pc;
         if (not.getNegatedPointcut() instanceof NameBindingPointcut) {
            NameBindingPointcut nnbp = (NameBindingPointcut)not.getNegatedPointcut();
            if (!nnbp.getBindingAnnotationTypePatterns().isEmpty() && !nnbp.getBindingTypePatterns().isEmpty()) {
               this.raiseNegationBindingError(userPointcut);
            }
         }
      } else if (pc instanceof AndPointcut) {
         AndPointcut and = (AndPointcut)pc;
         this.validateSingleBranchRecursion(and.getLeft(), userPointcut, foundFormals, names, bindings);
         this.validateSingleBranchRecursion(and.getRight(), userPointcut, foundFormals, names, bindings);
      } else if (pc instanceof NameBindingPointcut) {
         List btps = ((NameBindingPointcut)pc).getBindingTypePatterns();
         Iterator iter = btps.iterator();

         while(iter.hasNext()) {
            BindingTypePattern btp = (BindingTypePattern)iter.next();
            int index = btp.getFormalIndex();
            bindings[index] = pc;
            if (foundFormals[index]) {
               this.raiseAmbiguousBindingError(names[index], userPointcut);
            } else {
               foundFormals[index] = true;
            }
         }

         List baps = ((NameBindingPointcut)pc).getBindingAnnotationTypePatterns();
         Iterator iter = baps.iterator();

         while(iter.hasNext()) {
            BindingPattern bap = (BindingPattern)iter.next();
            int index = bap.getFormalIndex();
            bindings[index] = pc;
            if (foundFormals[index]) {
               this.raiseAmbiguousBindingError(names[index], userPointcut);
            } else {
               foundFormals[index] = true;
            }
         }
      } else if (pc instanceof ConcreteCflowPointcut) {
         ConcreteCflowPointcut cfp = (ConcreteCflowPointcut)pc;
         int[] slots = cfp.getUsedFormalSlots();

         for(int i = 0; i < slots.length; ++i) {
            bindings[slots[i]] = cfp;
            if (foundFormals[slots[i]]) {
               this.raiseAmbiguousBindingError(names[slots[i]], userPointcut);
            } else {
               foundFormals[slots[i]] = true;
            }
         }
      }

   }

   private boolean couldEverMatchSameJoinPoints(Pointcut left, Pointcut right) {
      OrPointcut rightOrPointcut;
      if (left instanceof OrPointcut) {
         rightOrPointcut = (OrPointcut)left;
         if (this.couldEverMatchSameJoinPoints(rightOrPointcut.getLeft(), right)) {
            return true;
         } else {
            return this.couldEverMatchSameJoinPoints(rightOrPointcut.getRight(), right);
         }
      } else if (right instanceof OrPointcut) {
         rightOrPointcut = (OrPointcut)right;
         if (this.couldEverMatchSameJoinPoints(left, rightOrPointcut.getLeft())) {
            return true;
         } else {
            return this.couldEverMatchSameJoinPoints(left, rightOrPointcut.getRight());
         }
      } else {
         WithinPointcut leftWithin = (WithinPointcut)this.findFirstPointcutIn(left, WithinPointcut.class);
         WithinPointcut rightWithin = (WithinPointcut)this.findFirstPointcutIn(right, WithinPointcut.class);
         if (leftWithin != null && rightWithin != null && !leftWithin.couldEverMatchSameJoinPointsAs(rightWithin)) {
            return false;
         } else {
            KindedPointcut leftKind = (KindedPointcut)this.findFirstPointcutIn(left, KindedPointcut.class);
            KindedPointcut rightKind = (KindedPointcut)this.findFirstPointcutIn(right, KindedPointcut.class);
            return leftKind == null || rightKind == null || leftKind.couldEverMatchSameJoinPointsAs(rightKind);
         }
      }
   }

   private Pointcut findFirstPointcutIn(Pointcut toSearch, Class toLookFor) {
      if (toSearch instanceof NotPointcut) {
         return null;
      } else if (toLookFor.isInstance(toSearch)) {
         return toSearch;
      } else if (toSearch instanceof AndPointcut) {
         AndPointcut apc = (AndPointcut)toSearch;
         Pointcut left = this.findFirstPointcutIn(apc.getLeft(), toLookFor);
         return left != null ? left : this.findFirstPointcutIn(apc.getRight(), toLookFor);
      } else {
         return null;
      }
   }

   private void raiseNegationBindingError(Pointcut userPointcut) {
      this.world.showMessage(IMessage.ERROR, WeaverMessages.format("negationDoesntAllowBinding"), userPointcut.getSourceContext().makeSourceLocation(userPointcut), (ISourceLocation)null);
   }

   private void raiseAmbiguousBindingError(String name, Pointcut userPointcut) {
      this.world.showMessage(IMessage.ERROR, WeaverMessages.format("ambiguousBindingInPC", name), userPointcut.getSourceContext().makeSourceLocation(userPointcut), (ISourceLocation)null);
   }

   private void raiseAmbiguityInDisjunctionError(Pointcut userPointcut, List names) {
      StringBuffer formalNames = new StringBuffer(((String)names.get(0)).toString());

      for(int i = 1; i < names.size(); ++i) {
         formalNames.append(", ");
         formalNames.append((String)names.get(i));
      }

      this.world.showMessage(IMessage.ERROR, WeaverMessages.format("ambiguousBindingInOrPC", formalNames), userPointcut.getSourceContext().makeSourceLocation(userPointcut), (ISourceLocation)null);
   }

   private void raiseUnboundFormalError(String name, Pointcut userPointcut) {
      this.world.showMessage(IMessage.ERROR, WeaverMessages.format("unboundFormalInPC", name), userPointcut.getSourceLocation(), (ISourceLocation)null);
   }

   public void addManifest(Manifest newManifest) {
      if (this.manifest == null) {
         this.manifest = newManifest;
      }

   }

   public Manifest getManifest(boolean shouldCreate) {
      if (this.manifest == null && shouldCreate) {
         String WEAVER_MANIFEST_VERSION = "1.0";
         Attributes.Name CREATED_BY = new Attributes.Name("Created-By");
         String WEAVER_CREATED_BY = "AspectJ Compiler";
         this.manifest = new Manifest();
         Attributes attributes = this.manifest.getMainAttributes();
         attributes.put(Name.MANIFEST_VERSION, WEAVER_MANIFEST_VERSION);
         attributes.put(CREATED_BY, WEAVER_CREATED_BY);
      }

      return this.manifest;
   }

   public Collection weave(File file) throws IOException {
      OutputStream os = FileUtil.makeOutputStream(file);
      this.zipOutputStream = new ZipOutputStream(os);
      this.prepareForWeave();
      Collection c = this.weave(new IClassFileProvider() {
         public boolean isApplyAtAspectJMungersOnly() {
            return false;
         }

         public Iterator getClassFileIterator() {
            return BcelWeaver.this.addedClasses.iterator();
         }

         public IWeaveRequestor getRequestor() {
            return new IWeaveRequestor() {
               public void acceptResult(IUnwovenClassFile result) {
                  try {
                     BcelWeaver.this.writeZipEntry(result.getFilename(), result.getBytes());
                  } catch (IOException var3) {
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
               }
            };
         }
      });
      this.zipOutputStream.close();
      return c;
   }

   public Collection weave(IClassFileProvider input) throws IOException {
      if (trace.isTraceEnabled()) {
         trace.enter("weave", this, (Object)input);
      }

      ContextToken weaveToken = CompilationAndWeavingContext.enteringPhase(22, "");
      Collection wovenClassNames = new ArrayList();
      IWeaveRequestor requestor = input.getRequestor();
      if (this.world.getModel() != null && this.world.isMinimalModel()) {
         this.candidatesForRemoval = new HashSet();
      }

      Iterator i;
      UnwovenClassFile classFile;
      if (this.world.getModel() != null && !this.isBatchWeave) {
         AsmManager manager = this.world.getModelAsAsmManager();
         i = input.getClassFileIterator();

         while(i.hasNext()) {
            classFile = (UnwovenClassFile)i.next();
            manager.removeRelationshipsTargettingThisType(classFile.getClassName());
         }
      }

      Iterator i = input.getClassFileIterator();

      while(i.hasNext()) {
         UnwovenClassFile classFile = (UnwovenClassFile)i.next();
         String className = classFile.getClassName();
         ResolvedType theType = this.world.resolve(className);
         if (theType != null) {
            theType.ensureConsistent();
         }
      }

      ContextToken atAspectJMungersOnly;
      String className;
      if (input.isApplyAtAspectJMungersOnly()) {
         atAspectJMungersOnly = CompilationAndWeavingContext.enteringPhase(32, "");
         requestor.weavingAspects();
         CompilationAndWeavingContext.enteringPhase(25, "");
         i = input.getClassFileIterator();

         while(true) {
            ResolvedType theType;
            do {
               if (!i.hasNext()) {
                  requestor.weaveCompleted();
                  CompilationAndWeavingContext.leavingPhase(atAspectJMungersOnly);
                  return wovenClassNames;
               }

               classFile = (UnwovenClassFile)i.next();
               className = classFile.getClassName();
               theType = this.world.resolve(className);
            } while(!theType.isAnnotationStyleAspect());

            BcelObjectType classType = BcelWorld.getBcelObjectType(theType);
            if (classType == null) {
               throw new BCException("Can't find bcel delegate for " + className + " type=" + theType.getClass());
            }

            LazyClassGen clazz = classType.getLazyClassGen();
            BcelPerClauseAspectAdder selfMunger = new BcelPerClauseAspectAdder(theType, theType.getPerClause().getKind());
            selfMunger.forceMunge(clazz, true);
            classType.finishedWith();
            UnwovenClassFile[] newClasses = this.getClassFilesFor(clazz);

            for(int news = 0; news < newClasses.length; ++news) {
               requestor.acceptResult(newClasses[news]);
            }

            wovenClassNames.add(classFile.getClassName());
         }
      } else {
         requestor.processingReweavableState();
         atAspectJMungersOnly = CompilationAndWeavingContext.enteringPhase(23, "");
         this.prepareToProcessReweavableState();
         i = input.getClassFileIterator();

         while(i.hasNext()) {
            classFile = (UnwovenClassFile)i.next();
            className = classFile.getClassName();
            BcelObjectType classType = this.getClassType(className);
            if (classType != null) {
               ContextToken tok = CompilationAndWeavingContext.enteringPhase(23, className);
               this.processReweavableStateIfPresent(className, classType);
               CompilationAndWeavingContext.leavingPhase(tok);
            }
         }

         CompilationAndWeavingContext.leavingPhase(atAspectJMungersOnly);
         ContextToken typeMungingToken = CompilationAndWeavingContext.enteringPhase(24, "");
         requestor.addingTypeMungers();
         List typesToProcess = new ArrayList();
         Iterator i = input.getClassFileIterator();

         UnwovenClassFile classFile;
         while(i.hasNext()) {
            classFile = (UnwovenClassFile)i.next();
            typesToProcess.add(classFile.getClassName());
         }

         while(typesToProcess.size() > 0) {
            this.weaveParentsFor(typesToProcess, (String)typesToProcess.get(0), (ResolvedType)null);
         }

         i = input.getClassFileIterator();

         while(i.hasNext()) {
            classFile = (UnwovenClassFile)i.next();
            String className = classFile.getClassName();
            this.addNormalTypeMungers(className);
         }

         CompilationAndWeavingContext.leavingPhase(typeMungingToken);
         requestor.weavingAspects();
         ContextToken aspectToken = CompilationAndWeavingContext.enteringPhase(25, "");
         Iterator i = input.getClassFileIterator();

         while(i.hasNext()) {
            UnwovenClassFile classFile = (UnwovenClassFile)i.next();
            String className = classFile.getClassName();
            ResolvedType theType = this.world.resolve(className);
            if (theType.isAspect()) {
               BcelObjectType classType = BcelWorld.getBcelObjectType(theType);
               if (classType == null) {
                  ReferenceTypeDelegate theDelegate = ((ReferenceType)theType).getDelegate();
                  if (!theDelegate.getClass().getName().endsWith("EclipseSourceType")) {
                     throw new BCException("Can't find bcel delegate for " + className + " type=" + theType.getClass());
                  }
               } else {
                  this.weaveAndNotify(classFile, classType, requestor);
                  wovenClassNames.add(className);
               }
            }
         }

         CompilationAndWeavingContext.leavingPhase(aspectToken);
         requestor.weavingClasses();
         ContextToken classToken = CompilationAndWeavingContext.enteringPhase(26, "");
         Iterator i = input.getClassFileIterator();

         while(i.hasNext()) {
            UnwovenClassFile classFile = (UnwovenClassFile)i.next();
            String className = classFile.getClassName();
            ResolvedType theType = this.world.resolve(className);
            if (!theType.isAspect()) {
               BcelObjectType classType = BcelWorld.getBcelObjectType(theType);
               if (classType == null) {
                  ReferenceTypeDelegate theDelegate = ((ReferenceType)theType).getDelegate();
                  if (!theDelegate.getClass().getName().endsWith("EclipseSourceType")) {
                     throw new BCException("Can't find bcel delegate for " + className + " type=" + theType.getClass());
                  }
               } else {
                  this.weaveAndNotify(classFile, classType, requestor);
                  wovenClassNames.add(className);
               }
            }
         }

         CompilationAndWeavingContext.leavingPhase(classToken);
         this.addedClasses.clear();
         this.deletedTypenames.clear();
         requestor.weaveCompleted();
         CompilationAndWeavingContext.leavingPhase(weaveToken);
         if (trace.isTraceEnabled()) {
            trace.exit("weave", (Object)wovenClassNames);
         }

         if (this.world.getModel() != null && this.world.isMinimalModel()) {
            this.candidatesForRemoval.clear();
         }

         return wovenClassNames;
      }
   }

   public void allWeavingComplete() {
      this.warnOnUnmatchedAdvice();
   }

   private void warnOnUnmatchedAdvice() {
      if (this.world.isInJava5Mode() && this.world.getLint().adviceDidNotMatch.isEnabled()) {
         List l = this.world.getCrosscuttingMembersSet().getShadowMungers();
         Set alreadyWarnedLocations = new HashSet();
         Iterator iter = l.iterator();

         while(true) {
            ShadowMunger element;
            BcelAdvice ba;
            do {
               AdviceLocation loc;
               do {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 if (!iter.hasNext()) {
                                    return;
                                 }

                                 element = (ShadowMunger)iter.next();
                              } while(!(element instanceof BcelAdvice));

                              ba = (BcelAdvice)element;
                           } while(ba.getKind() == AdviceKind.CflowEntry);
                        } while(ba.getKind() == AdviceKind.CflowBelowEntry);
                     } while(ba.hasMatchedSomething());
                  } while(ba.getSignature() == null);

                  class AdviceLocation {
                     private final int lineNo;
                     private final UnresolvedType inAspect;

                     public AdviceLocation(BcelAdvice advice) {
                        this.lineNo = advice.getSourceLocation().getLine();
                        this.inAspect = advice.getDeclaringAspect();
                     }

                     public boolean equals(Object obj) {
                        if (!(obj instanceof AdviceLocation)) {
                           return false;
                        } else {
                           AdviceLocation other = (AdviceLocation)obj;
                           if (this.lineNo != other.lineNo) {
                              return false;
                           } else {
                              return this.inAspect.equals(other.inAspect);
                           }
                        }
                     }

                     public int hashCode() {
                        return 37 + 17 * this.lineNo + 17 * this.inAspect.hashCode();
                     }
                  }

                  loc = new AdviceLocation(ba);
               } while(alreadyWarnedLocations.contains(loc));

               alreadyWarnedLocations.add(loc);
            } while(ba.getSignature() instanceof BcelMethod && Utility.isSuppressing(ba.getSignature(), "adviceDidNotMatch"));

            this.world.getLint().adviceDidNotMatch.signal(ba.getDeclaringAspect().toString(), new SourceLocation(element.getSourceLocation().getSourceFile(), element.getSourceLocation().getLine()));
         }
      }
   }

   private void weaveParentsFor(List typesForWeaving, String typeToWeave, ResolvedType resolvedTypeToWeave) {
      if (resolvedTypeToWeave == null) {
         resolvedTypeToWeave = this.world.resolve(typeToWeave);
      }

      ResolvedType superclassType = resolvedTypeToWeave.getSuperclass();
      String superclassTypename = superclassType == null ? null : superclassType.getName();
      if (superclassType != null && !superclassType.isTypeHierarchyComplete() && superclassType.isExposedToWeaver() && typesForWeaving.contains(superclassTypename)) {
         this.weaveParentsFor(typesForWeaving, superclassTypename, superclassType);
      }

      ResolvedType[] interfaceTypes = resolvedTypeToWeave.getDeclaredInterfaces();
      ResolvedType[] arr$ = interfaceTypes;
      int len$ = interfaceTypes.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedType resolvedSuperInterface = arr$[i$];
         if (!resolvedSuperInterface.isTypeHierarchyComplete()) {
            String interfaceTypename = resolvedSuperInterface.getName();
            if (resolvedSuperInterface.isExposedToWeaver()) {
               this.weaveParentsFor(typesForWeaving, interfaceTypename, resolvedSuperInterface);
            }
         }
      }

      ContextToken tok = CompilationAndWeavingContext.enteringPhase(7, resolvedTypeToWeave.getName());
      if (!resolvedTypeToWeave.isTypeHierarchyComplete()) {
         this.weaveParentTypeMungers(resolvedTypeToWeave);
      }

      CompilationAndWeavingContext.leavingPhase(tok);
      typesForWeaving.remove(typeToWeave);
      resolvedTypeToWeave.tagAsTypeHierarchyComplete();
   }

   public void prepareToProcessReweavableState() {
   }

   public void processReweavableStateIfPresent(String className, BcelObjectType classType) {
      WeaverStateInfo wsi = classType.getWeaverState();
      if (wsi != null && wsi.isReweavable()) {
         this.world.showMessage(IMessage.INFO, WeaverMessages.format("processingReweavable", className, classType.getSourceLocation().getSourceFile()), (ISourceLocation)null, (ISourceLocation)null);
         Set aspectsPreviouslyInWorld = wsi.getAspectsAffectingType();
         Set alreadyConfirmedReweavableState = new HashSet();
         Iterator i$ = aspectsPreviouslyInWorld.iterator();

         while(i$.hasNext()) {
            String requiredTypeSignature = (String)i$.next();
            if (!alreadyConfirmedReweavableState.contains(requiredTypeSignature)) {
               ResolvedType rtx = this.world.resolve(UnresolvedType.forSignature(requiredTypeSignature), true);
               boolean exists = !rtx.isMissing();
               if (!exists) {
                  this.world.getLint().missingAspectForReweaving.signal(new String[]{rtx.getName(), className}, classType.getSourceLocation(), (ISourceLocation[])null);
               } else {
                  if (!this.world.isOverWeaving()) {
                     if (!this.xcutSet.containsAspect(rtx)) {
                        this.world.showMessage(IMessage.ERROR, WeaverMessages.format("reweavableAspectNotRegistered", rtx.getName(), className), (ISourceLocation)null, (ISourceLocation)null);
                     } else if (!this.world.getMessageHandler().isIgnoring(IMessage.INFO)) {
                        this.world.showMessage(IMessage.INFO, WeaverMessages.format("verifiedReweavableType", rtx.getName(), rtx.getSourceLocation().getSourceFile()), (ISourceLocation)null, (ISourceLocation)null);
                     }
                  }

                  alreadyConfirmedReweavableState.add(requiredTypeSignature);
               }
            }
         }

         if (!this.world.isOverWeaving()) {
            byte[] bytes = wsi.getUnwovenClassFileData(classType.getJavaClass().getBytes());
            AjAttribute.WeaverVersionInfo wvi = classType.getWeaverVersionAttribute();
            JavaClass newJavaClass = Utility.makeJavaClass(classType.getJavaClass().getFileName(), bytes);
            classType.setJavaClass(newJavaClass, true);
            classType.getResolvedTypeX().ensureConsistent();
         }
      }

   }

   private void weaveAndNotify(UnwovenClassFile classFile, BcelObjectType classType, IWeaveRequestor requestor) throws IOException {
      trace.enter("weaveAndNotify", this, (Object[])(new Object[]{classFile, classType, requestor}));
      ContextToken tok = CompilationAndWeavingContext.enteringPhase(27, classType.getResolvedTypeX().getName());
      LazyClassGen clazz = this.weaveWithoutDump(classFile, classType);
      classType.finishedWith();
      if (clazz != null) {
         UnwovenClassFile[] newClasses = this.getClassFilesFor(clazz);
         if (newClasses[0].getClassName().equals(classFile.getClassName())) {
            newClasses[0].setClassNameAsChars(classFile.getClassNameAsChars());
         }

         for(int i = 0; i < newClasses.length; ++i) {
            requestor.acceptResult(newClasses[i]);
         }
      } else {
         requestor.acceptResult(classFile);
      }

      classType.weavingCompleted();
      CompilationAndWeavingContext.leavingPhase(tok);
      trace.exit("weaveAndNotify");
   }

   public BcelObjectType getClassType(String forClass) {
      return BcelWorld.getBcelObjectType(this.world.resolve(forClass));
   }

   public void addParentTypeMungers(String typeName) {
      this.weaveParentTypeMungers(this.world.resolve(typeName));
   }

   public void addNormalTypeMungers(String typeName) {
      this.weaveNormalTypeMungers(this.world.resolve(typeName));
   }

   public UnwovenClassFile[] getClassFilesFor(LazyClassGen clazz) {
      List childClasses = clazz.getChildClasses(this.world);
      UnwovenClassFile[] ret = new UnwovenClassFile[1 + childClasses.size()];
      ret[0] = new UnwovenClassFile(clazz.getFileName(), clazz.getClassName(), clazz.getJavaClassBytesIncludingReweavable(this.world));
      int index = 1;

      UnwovenClassFile childClass;
      for(Iterator iter = childClasses.iterator(); iter.hasNext(); ret[index++] = childClass) {
         UnwovenClassFile.ChildClass element = (UnwovenClassFile.ChildClass)iter.next();
         childClass = new UnwovenClassFile(clazz.getFileName() + "$" + element.name, element.bytes);
      }

      return ret;
   }

   public void weaveParentTypeMungers(ResolvedType onType) {
      if (((ResolvedType)onType).isRawType() || ((ResolvedType)onType).isParameterizedType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      ((ResolvedType)onType).clearInterTypeMungers();
      List decpToRepeat = new ArrayList();
      boolean aParentChangeOccurred = false;
      boolean anAnnotationChangeOccurred = false;
      Iterator i$ = this.declareParentsList.iterator();

      boolean typeChanged;
      while(i$.hasNext()) {
         DeclareParents decp = (DeclareParents)i$.next();
         typeChanged = this.applyDeclareParents(decp, (ResolvedType)onType);
         if (typeChanged) {
            aParentChangeOccurred = true;
         } else {
            decpToRepeat.add(decp);
         }
      }

      i$ = this.xcutSet.getDeclareAnnotationOnTypes().iterator();

      while(i$.hasNext()) {
         DeclareAnnotation decA = (DeclareAnnotation)i$.next();
         typeChanged = this.applyDeclareAtType(decA, (ResolvedType)onType, true);
         if (typeChanged) {
            anAnnotationChangeOccurred = true;
         }
      }

      while((aParentChangeOccurred || anAnnotationChangeOccurred) && !decpToRepeat.isEmpty()) {
         aParentChangeOccurred = false;
         anAnnotationChangeOccurred = false;
         List decpToRepeatNextTime = new ArrayList();
         Iterator i$ = decpToRepeat.iterator();

         boolean typeChanged;
         while(i$.hasNext()) {
            DeclareParents decp = (DeclareParents)i$.next();
            typeChanged = this.applyDeclareParents(decp, (ResolvedType)onType);
            if (typeChanged) {
               aParentChangeOccurred = true;
            } else {
               decpToRepeatNextTime.add(decp);
            }
         }

         i$ = this.xcutSet.getDeclareAnnotationOnTypes().iterator();

         while(i$.hasNext()) {
            DeclareAnnotation decA = (DeclareAnnotation)i$.next();
            typeChanged = this.applyDeclareAtType(decA, (ResolvedType)onType, false);
            if (typeChanged) {
               anAnnotationChangeOccurred = true;
            }
         }

         decpToRepeat = decpToRepeatNextTime;
      }

   }

   private boolean applyDeclareAtType(DeclareAnnotation decA, ResolvedType onType, boolean reportProblems) {
      boolean didSomething = false;
      if (decA.matches(onType)) {
         AnnotationAJ theAnnotation = decA.getAnnotation();
         if (theAnnotation == null) {
            return false;
         }

         if (onType.hasAnnotation(theAnnotation.getType())) {
            return false;
         }

         AnnotationAJ annoX = decA.getAnnotation();
         boolean problemReported = this.verifyTargetIsOK(decA, onType, annoX, reportProblems);
         if (!problemReported) {
            AsmRelationshipProvider.addDeclareAnnotationRelationship(this.world.getModelAsAsmManager(), decA.getSourceLocation(), onType.getSourceLocation(), false);
            if (!this.getWorld().getMessageHandler().isIgnoring(IMessage.WEAVEINFO)) {
               this.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_ANNOTATES, new String[]{onType.toString(), Utility.beautifyLocation(onType.getSourceLocation()), decA.getAnnotationString(), "type", decA.getAspect().toString(), Utility.beautifyLocation(decA.getSourceLocation())}));
            }

            didSomething = true;
            ResolvedTypeMunger newAnnotationTM = new AnnotationOnTypeMunger(annoX);
            newAnnotationTM.setSourceLocation(decA.getSourceLocation());
            onType.addInterTypeMunger(new BcelTypeMunger(newAnnotationTM, decA.getAspect().resolve(this.world)), false);
            decA.copyAnnotationTo(onType);
         }
      }

      return didSomething;
   }

   private boolean verifyTargetIsOK(DeclareAnnotation decA, ResolvedType onType, AnnotationAJ annoX, boolean outputProblems) {
      boolean problemReported = false;
      if (annoX.specifiesTarget() && (onType.isAnnotation() && !annoX.allowedOnAnnotationType() || !annoX.allowedOnRegularType())) {
         if (outputProblems) {
            if (decA.isExactPattern()) {
               this.world.getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("incorrectTargetForDeclareAnnotation", onType.getName(), annoX.getTypeName(), annoX.getValidTargets()), decA.getSourceLocation()));
            } else if (this.world.getLint().invalidTargetForAnnotation.isEnabled()) {
               this.world.getLint().invalidTargetForAnnotation.signal(new String[]{onType.getName(), annoX.getTypeName(), annoX.getValidTargets()}, decA.getSourceLocation(), new ISourceLocation[]{onType.getSourceLocation()});
            }
         }

         problemReported = true;
      }

      return problemReported;
   }

   private boolean applyDeclareParents(DeclareParents p, ResolvedType onType) {
      boolean didSomething = false;
      List newParents = p.findMatchingNewParents(onType, true);
      if (!newParents.isEmpty()) {
         didSomething = true;
         BcelWorld.getBcelObjectType(onType);
         Iterator i$ = newParents.iterator();

         while(i$.hasNext()) {
            ResolvedType newParent = (ResolvedType)i$.next();
            onType.addParent(newParent);
            NewParentTypeMunger newParentMunger = new NewParentTypeMunger(newParent, p.getDeclaringType());
            if (p.isMixin()) {
               newParentMunger.setIsMixin(true);
            }

            newParentMunger.setSourceLocation(p.getSourceLocation());
            onType.addInterTypeMunger(new BcelTypeMunger(newParentMunger, this.xcutSet.findAspectDeclaringParents(p)), false);
         }
      }

      return didSomething;
   }

   public void weaveNormalTypeMungers(ResolvedType onType) {
      ContextToken tok = CompilationAndWeavingContext.enteringPhase(24, ((ResolvedType)onType).getName());
      if (((ResolvedType)onType).isRawType() || ((ResolvedType)onType).isParameterizedType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      Iterator i$ = this.typeMungerList.iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger m = (ConcreteTypeMunger)i$.next();
         if (!m.isLateMunger() && m.matches((ResolvedType)onType)) {
            ((ResolvedType)onType).addInterTypeMunger(m, false);
         }
      }

      CompilationAndWeavingContext.leavingPhase(tok);
   }

   public LazyClassGen weaveWithoutDump(UnwovenClassFile classFile, BcelObjectType classType) throws IOException {
      return this.weave(classFile, classType, false);
   }

   LazyClassGen weave(UnwovenClassFile classFile, BcelObjectType classType) throws IOException {
      LazyClassGen ret = this.weave(classFile, classType, true);
      return ret;
   }

   private LazyClassGen weave(UnwovenClassFile classFile, BcelObjectType classType, boolean dump) throws IOException {
      LazyClassGen var30;
      try {
         ReferenceType resolvedClassType;
         if (classType.isSynthetic()) {
            if (dump) {
               this.dumpUnchanged(classFile);
            }

            resolvedClassType = null;
            return resolvedClassType;
         }

         resolvedClassType = classType.getResolvedTypeX();
         List shadowMungers;
         if (this.world.isXmlConfigured() && this.world.getXmlConfiguration().excludesType(resolvedClassType)) {
            if (!this.world.getMessageHandler().isIgnoring(IMessage.INFO)) {
               this.world.getMessageHandler().handleMessage(MessageUtil.info("Type '" + resolvedClassType.getName() + "' not woven due to exclusion via XML weaver exclude section"));
            }

            if (dump) {
               this.dumpUnchanged(classFile);
            }

            shadowMungers = null;
            return shadowMungers;
         }

         shadowMungers = this.fastMatch(this.shadowMungerList, resolvedClassType);
         List typeMungers = classType.getResolvedTypeX().getInterTypeMungers();
         resolvedClassType.checkInterTypeMungers();
         boolean mightNeedToWeave = shadowMungers.size() > 0 || typeMungers.size() > 0 || classType.isAspect() || this.world.getDeclareAnnotationOnMethods().size() > 0 || this.world.getDeclareAnnotationOnFields().size() > 0;
         boolean mightNeedBridgeMethods = this.world.isInJava5Mode() && !classType.isInterface() && resolvedClassType.getInterTypeMungersIncludingSupers().size() > 0;
         LazyClassGen clazz = null;
         String classDebugInfo;
         String messageText;
         if (!mightNeedToWeave && !mightNeedBridgeMethods) {
            this.checkDeclareTypeErrorOrWarning(this.world, classType);
         } else {
            clazz = classType.getLazyClassGen();

            try {
               boolean isChanged = false;
               if (mightNeedToWeave) {
                  isChanged = BcelClassWeaver.weave(this.world, clazz, shadowMungers, typeMungers, this.lateTypeMungerList, this.inReweavableMode);
               }

               this.checkDeclareTypeErrorOrWarning(this.world, classType);
               if (mightNeedBridgeMethods) {
                  isChanged = BcelClassWeaver.calculateAnyRequiredBridgeMethods(this.world, clazz) || isChanged;
               }

               if (isChanged) {
                  if (dump) {
                     this.dump(classFile, clazz);
                  }

                  var30 = clazz;
                  return var30;
               }
            } catch (RuntimeException var26) {
               classDebugInfo = null;

               try {
                  classDebugInfo = clazz.toLongString();
               } catch (Throwable var25) {
                  (new RuntimeException("Crashed whilst crashing with this exception: " + var25, var25)).printStackTrace();
                  classDebugInfo = clazz.getClassName();
               }

               messageText = "trouble in: \n" + classDebugInfo;
               this.getWorld().getMessageHandler().handleMessage(new Message(messageText, IMessage.ABORT, var26, (ISourceLocation)null));
            } catch (Error var27) {
               classDebugInfo = null;

               try {
                  classDebugInfo = clazz.toLongString();
               } catch (OutOfMemoryError var23) {
                  System.err.println("Ran out of memory creating debug info for an error");
                  var27.printStackTrace(System.err);
                  classDebugInfo = clazz.getClassName();
               } catch (Throwable var24) {
                  classDebugInfo = clazz.getClassName();
               }

               messageText = "trouble in: \n" + classDebugInfo;
               this.getWorld().getMessageHandler().handleMessage(new Message(messageText, IMessage.ABORT, var27, (ISourceLocation)null));
            }
         }

         AsmManager model = this.world.getModelAsAsmManager();
         if (this.world.isMinimalModel() && model != null && !classType.isAspect()) {
            AspectJElementHierarchy hierarchy = (AspectJElementHierarchy)model.getHierarchy();
            messageText = classType.getResolvedTypeX().getPackageName();
            String tname = classType.getResolvedTypeX().getSimpleBaseName();
            IProgramElement typeElement = hierarchy.findElementForType(messageText, tname);
            if (typeElement != null && this.hasInnerType(typeElement)) {
               this.candidatesForRemoval.add(typeElement);
            }

            if (typeElement != null && !this.hasInnerType(typeElement)) {
               IProgramElement parent = typeElement.getParent();
               if (parent != null) {
                  parent.removeChild(typeElement);
                  if (parent.getKind().isSourceFile()) {
                     this.removeSourceFileIfNoMoreTypeDeclarationsInside(hierarchy, typeElement, parent);
                  } else {
                     hierarchy.forget((IProgramElement)null, typeElement);
                     this.walkUpRemovingEmptyTypesAndPossiblyEmptySourceFile(hierarchy, tname, parent);
                  }
               }
            }
         }

         if (!dump) {
            if (clazz != null && !clazz.getChildClasses(this.world).isEmpty()) {
               var30 = clazz;
               return var30;
            }

            classDebugInfo = null;
            return classDebugInfo;
         }

         this.dumpUnchanged(classFile);
         var30 = clazz;
      } finally {
         this.world.demote();
      }

      return var30;
   }

   private void walkUpRemovingEmptyTypesAndPossiblyEmptySourceFile(AspectJElementHierarchy hierarchy, String tname, IProgramElement typeThatHasChildRemoved) {
      while(typeThatHasChildRemoved != null && !typeThatHasChildRemoved.getKind().isType() && !typeThatHasChildRemoved.getKind().isSourceFile()) {
         typeThatHasChildRemoved = typeThatHasChildRemoved.getParent();
      }

      if (this.candidatesForRemoval.contains(typeThatHasChildRemoved) && !this.hasInnerType(typeThatHasChildRemoved)) {
         IProgramElement parent = typeThatHasChildRemoved.getParent();
         if (parent != null) {
            parent.removeChild(typeThatHasChildRemoved);
            this.candidatesForRemoval.remove(typeThatHasChildRemoved);
            if (parent.getKind().isSourceFile()) {
               this.removeSourceFileIfNoMoreTypeDeclarationsInside(hierarchy, typeThatHasChildRemoved, parent);
            } else {
               this.walkUpRemovingEmptyTypesAndPossiblyEmptySourceFile(hierarchy, tname, parent);
            }
         }
      }

   }

   private void removeSourceFileIfNoMoreTypeDeclarationsInside(AspectJElementHierarchy hierarchy, IProgramElement typeElement, IProgramElement sourceFileNode) {
      boolean anyOtherTypeDeclarations = false;
      Iterator i$ = sourceFileNode.getChildren().iterator();

      while(i$.hasNext()) {
         IProgramElement child = (IProgramElement)i$.next();
         IProgramElement.Kind k = child.getKind();
         if (k.isType()) {
            anyOtherTypeDeclarations = true;
            break;
         }
      }

      if (!anyOtherTypeDeclarations) {
         IProgramElement cuParent = sourceFileNode.getParent();
         if (cuParent != null) {
            sourceFileNode.setParent((IProgramElement)null);
            cuParent.removeChild(sourceFileNode);
         }

         hierarchy.forget(sourceFileNode, typeElement);
      } else {
         hierarchy.forget((IProgramElement)null, typeElement);
      }

   }

   private boolean hasInnerType(IProgramElement typeNode) {
      Iterator i$ = typeNode.getChildren().iterator();

      boolean b;
      do {
         IProgramElement child;
         IProgramElement.Kind kind;
         do {
            if (!i$.hasNext()) {
               return false;
            }

            child = (IProgramElement)i$.next();
            kind = child.getKind();
            if (kind.isType()) {
               return true;
            }
         } while(!kind.isType() && kind != IProgramElement.Kind.METHOD && kind != IProgramElement.Kind.CONSTRUCTOR);

         b = this.hasInnerType(child);
      } while(!b);

      return b;
   }

   private void checkDeclareTypeErrorOrWarning(BcelWorld world2, BcelObjectType classType) {
      List dteows = this.world.getDeclareTypeEows();
      Iterator i$ = dteows.iterator();

      while(i$.hasNext()) {
         DeclareTypeErrorOrWarning dteow = (DeclareTypeErrorOrWarning)i$.next();
         if (dteow.getTypePattern().matchesStatically(classType.getResolvedTypeX())) {
            if (dteow.isError()) {
               this.world.getMessageHandler().handleMessage(MessageUtil.error(dteow.getMessage(), classType.getResolvedTypeX().getSourceLocation()));
            } else {
               this.world.getMessageHandler().handleMessage(MessageUtil.warn(dteow.getMessage(), classType.getResolvedTypeX().getSourceLocation()));
            }
         }
      }

   }

   private void dumpUnchanged(UnwovenClassFile classFile) throws IOException {
      if (this.zipOutputStream != null) {
         this.writeZipEntry(this.getEntryName(classFile.getJavaClass().getClassName()), classFile.getBytes());
      } else {
         classFile.writeUnchangedBytes();
      }

   }

   private String getEntryName(String className) {
      return className.replace('.', '/') + ".class";
   }

   private void dump(UnwovenClassFile classFile, LazyClassGen clazz) throws IOException {
      if (this.zipOutputStream != null) {
         String mainClassName = classFile.getJavaClass().getClassName();
         this.writeZipEntry(this.getEntryName(mainClassName), clazz.getJavaClass(this.world).getBytes());
         List childClasses = clazz.getChildClasses(this.world);
         if (!childClasses.isEmpty()) {
            Iterator i = childClasses.iterator();

            while(i.hasNext()) {
               UnwovenClassFile.ChildClass c = (UnwovenClassFile.ChildClass)i.next();
               this.writeZipEntry(this.getEntryName(mainClassName + "$" + c.name), c.bytes);
            }
         }
      } else {
         classFile.writeWovenBytes(clazz.getJavaClass(this.world).getBytes(), clazz.getChildClasses(this.world));
      }

   }

   private void writeZipEntry(String name, byte[] bytes) throws IOException {
      ZipEntry newEntry = new ZipEntry(name);
      this.zipOutputStream.putNextEntry(newEntry);
      this.zipOutputStream.write(bytes);
      this.zipOutputStream.closeEntry();
   }

   private List fastMatch(List list, ResolvedType type) {
      if (list == null) {
         return Collections.emptyList();
      } else {
         boolean isOverweaving = this.world.isOverWeaving();
         WeaverStateInfo typeWeaverState = isOverweaving ? type.getWeaverState() : null;
         FastMatchInfo info = new FastMatchInfo(type, (Shadow.Kind)null, this.world);
         List result = new ArrayList();
         Iterator i$;
         ShadowMunger munger;
         ResolvedType declaringAspect;
         Pointcut pointcut;
         if (this.world.areInfoMessagesEnabled() && this.world.isTimingEnabled()) {
            i$ = list.iterator();

            while(true) {
               do {
                  if (!i$.hasNext()) {
                     return result;
                  }

                  munger = (ShadowMunger)i$.next();
                  if (typeWeaverState == null) {
                     break;
                  }

                  declaringAspect = munger.getDeclaringType();
               } while(typeWeaverState.isAspectAlreadyApplied(declaringAspect));

               pointcut = munger.getPointcut();
               long starttime = System.nanoTime();
               FuzzyBoolean fb = pointcut.fastMatch(info);
               long endtime = System.nanoTime();
               this.world.recordFastMatch(pointcut, endtime - starttime);
               if (fb.maybeTrue()) {
                  result.add(munger);
               }
            }
         } else {
            i$ = list.iterator();

            while(true) {
               do {
                  if (!i$.hasNext()) {
                     return result;
                  }

                  munger = (ShadowMunger)i$.next();
                  if (typeWeaverState == null) {
                     break;
                  }

                  declaringAspect = munger.getConcreteAspect();
               } while(typeWeaverState.isAspectAlreadyApplied(declaringAspect));

               pointcut = munger.getPointcut();
               FuzzyBoolean fb = pointcut.fastMatch(info);
               if (fb.maybeTrue()) {
                  result.add(munger);
               }
            }
         }
      }
   }

   public void setReweavableMode(boolean xNotReweavable) {
      this.inReweavableMode = !xNotReweavable;
      WeaverStateInfo.setReweavableModeDefaults(!xNotReweavable, false, true);
   }

   public boolean isReweavable() {
      return this.inReweavableMode;
   }

   public World getWorld() {
      return this.world;
   }

   public void tidyUp() {
      if (trace.isTraceEnabled()) {
         trace.enter("tidyUp", this);
      }

      this.shadowMungerList = null;
      this.typeMungerList = null;
      this.lateTypeMungerList = null;
      this.declareParentsList = null;
      if (trace.isTraceEnabled()) {
         trace.exit("tidyUp");
      }

   }

   public void write(CompressingDataOutputStream dos) throws IOException {
      this.xcutSet.write(dos);
   }

   public void setShadowMungers(List shadowMungers) {
      this.shadowMungerList = shadowMungers;
   }
}
