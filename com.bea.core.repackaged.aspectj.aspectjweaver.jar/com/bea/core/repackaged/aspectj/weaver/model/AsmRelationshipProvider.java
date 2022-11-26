package com.bea.core.repackaged.aspectj.weaver.model;

import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IHierarchy;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.asm.IRelationship;
import com.bea.core.repackaged.aspectj.asm.IRelationshipMap;
import com.bea.core.repackaged.aspectj.asm.internal.HandleProviderDelimiter;
import com.bea.core.repackaged.aspectj.asm.internal.ProgramElement;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AdviceKind;
import com.bea.core.repackaged.aspectj.weaver.Checker;
import com.bea.core.repackaged.aspectj.weaver.Lint;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.NewParentTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelShadow;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePatternList;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AsmRelationshipProvider {
   public static final String ADVISES = "advises";
   public static final String ADVISED_BY = "advised by";
   public static final String DECLARES_ON = "declares on";
   public static final String DECLAREDY_BY = "declared by";
   public static final String SOFTENS = "softens";
   public static final String SOFTENED_BY = "softened by";
   public static final String MATCHED_BY = "matched by";
   public static final String MATCHES_DECLARE = "matches declare";
   public static final String INTER_TYPE_DECLARES = "declared on";
   public static final String INTER_TYPE_DECLARED_BY = "aspect declarations";
   public static final String ANNOTATES = "annotates";
   public static final String ANNOTATED_BY = "annotated by";
   private static final String NO_COMMENT = null;

   public static void addDeclareErrorOrWarningRelationship(AsmManager model, Shadow affectedShadow, Checker deow) {
      if (model != null) {
         if (affectedShadow.getSourceLocation() != null && deow.getSourceLocation() != null) {
            if (World.createInjarHierarchy) {
               createHierarchyForBinaryAspect(model, deow);
            }

            IProgramElement targetNode = getNode(model, affectedShadow);
            if (targetNode != null) {
               String targetHandle = targetNode.getHandleIdentifier();
               if (targetHandle != null) {
                  IProgramElement sourceNode = model.getHierarchy().findElementForSourceLine(deow.getSourceLocation());
                  String sourceHandle = sourceNode.getHandleIdentifier();
                  if (sourceHandle != null) {
                     IRelationshipMap relmap = model.getRelationshipMap();
                     IRelationship foreward = relmap.get(sourceHandle, IRelationship.Kind.DECLARE, "matched by", false, true);
                     foreward.addTarget(targetHandle);
                     IRelationship back = relmap.get(targetHandle, IRelationship.Kind.DECLARE, "matches declare", false, true);
                     if (back != null && back.getTargets() != null) {
                        back.addTarget(sourceHandle);
                     }

                     if (sourceNode.getSourceLocation() != null) {
                        model.addAspectInEffectThisBuild(sourceNode.getSourceLocation().getSourceFile());
                     }

                  }
               }
            }
         }
      }
   }

   private static boolean isMixinRelated(ResolvedTypeMunger typeTransformer) {
      ResolvedTypeMunger.Kind kind = typeTransformer.getKind();
      return kind == ResolvedTypeMunger.MethodDelegate2 || kind == ResolvedTypeMunger.FieldHost || kind == ResolvedTypeMunger.Parent && ((NewParentTypeMunger)typeTransformer).isMixin();
   }

   public static void addRelationship(AsmManager model, ResolvedType onType, ResolvedTypeMunger typeTransformer, ResolvedType originatingAspect) {
      if (model != null) {
         if (World.createInjarHierarchy && isBinaryAspect(originatingAspect)) {
            createHierarchy(model, typeTransformer, originatingAspect);
         }

         if (originatingAspect.getSourceLocation() != null) {
            String sourceHandle = "";
            IProgramElement sourceNode = null;
            if (typeTransformer.getSourceLocation() != null && typeTransformer.getSourceLocation().getOffset() != -1 && !isMixinRelated(typeTransformer)) {
               sourceNode = model.getHierarchy().findElementForType(originatingAspect.getPackageName(), originatingAspect.getClassName());
               IProgramElement closer = model.getHierarchy().findCloserMatchForLineNumber(sourceNode, typeTransformer.getSourceLocation().getLine());
               if (closer != null) {
                  sourceNode = closer;
               }

               if (sourceNode == null && World.createInjarHierarchy) {
                  createHierarchy(model, typeTransformer, originatingAspect);
                  if (typeTransformer.getSourceLocation() != null && typeTransformer.getSourceLocation().getOffset() != -1 && !isMixinRelated(typeTransformer)) {
                     sourceNode = model.getHierarchy().findElementForType(originatingAspect.getPackageName(), originatingAspect.getClassName());
                     IProgramElement closer2 = model.getHierarchy().findCloserMatchForLineNumber(sourceNode, typeTransformer.getSourceLocation().getLine());
                     if (closer2 != null) {
                        sourceNode = closer2;
                     }
                  } else {
                     sourceNode = model.getHierarchy().findElementForType(originatingAspect.getPackageName(), originatingAspect.getClassName());
                  }
               }

               sourceHandle = sourceNode.getHandleIdentifier();
            } else {
               sourceNode = model.getHierarchy().findElementForType(originatingAspect.getPackageName(), originatingAspect.getClassName());
               sourceHandle = sourceNode.getHandleIdentifier();
            }

            if (sourceHandle == null) {
               return;
            }

            String targetHandle = findOrFakeUpNode(model, onType);
            if (targetHandle == null) {
               return;
            }

            IRelationshipMap mapper = model.getRelationshipMap();
            IRelationship foreward = mapper.get(sourceHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "declared on", false, true);
            foreward.addTarget(targetHandle);
            IRelationship back = mapper.get(targetHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "aspect declarations", false, true);
            back.addTarget(sourceHandle);
            if (sourceNode != null && sourceNode.getSourceLocation() != null) {
               model.addAspectInEffectThisBuild(sourceNode.getSourceLocation().getSourceFile());
            }
         }

      }
   }

   private static String findOrFakeUpNode(AsmManager model, ResolvedType onType) {
      IHierarchy hierarchy = model.getHierarchy();
      ISourceLocation sourceLocation = onType.getSourceLocation();
      String canonicalFilePath = model.getCanonicalFilePath(sourceLocation.getSourceFile());
      int lineNumber = sourceLocation.getLine();
      IProgramElement node = hierarchy.findNodeForSourceFile(hierarchy.getRoot(), canonicalFilePath);
      if (node != null) {
         IProgramElement closernode = hierarchy.findCloserMatchForLineNumber(node, lineNumber);
         return closernode == null ? node.getHandleIdentifier() : closernode.getHandleIdentifier();
      } else {
         String bpath = onType.getBinaryPath();
         if (bpath == null) {
            return model.getHandleProvider().createHandleIdentifier(createFileStructureNode(model, canonicalFilePath));
         } else {
            IProgramElement programElement = model.getHierarchy().getRoot();
            StringBuffer phantomHandle = new StringBuffer();
            phantomHandle.append(programElement.getHandleIdentifier());
            phantomHandle.append(HandleProviderDelimiter.PACKAGEFRAGMENTROOT.getDelimiter()).append(HandleProviderDelimiter.PHANTOM.getDelimiter());
            int pos = bpath.indexOf(33);
            String packageName;
            if (pos != -1) {
               packageName = bpath.substring(0, pos);
               String element = model.getHandleElementForInpath(packageName);
               if (element != null) {
                  phantomHandle.append(element);
               }
            }

            packageName = onType.getPackageName();
            phantomHandle.append(HandleProviderDelimiter.PACKAGEFRAGMENT.getDelimiter()).append(packageName);
            int dotClassPosition = bpath.lastIndexOf(".class");
            if (dotClassPosition == -1) {
               phantomHandle.append(HandleProviderDelimiter.CLASSFILE.getDelimiter()).append("UNKNOWN.class");
            } else {
               int startPosition;
               char ch;
               for(startPosition = dotClassPosition; startPosition > 0 && (ch = bpath.charAt(startPosition)) != '/' && ch != '\\' && ch != '!'; --startPosition) {
               }

               String classFile = bpath.substring(startPosition + 1, dotClassPosition + 6);
               phantomHandle.append(HandleProviderDelimiter.CLASSFILE.getDelimiter()).append(classFile);
            }

            phantomHandle.append(HandleProviderDelimiter.TYPE.getDelimiter()).append(onType.getClassName());
            return phantomHandle.toString();
         }
      }
   }

   public static IProgramElement createFileStructureNode(AsmManager asm, String sourceFilePath) {
      int lastSlash = sourceFilePath.lastIndexOf(92);
      if (lastSlash == -1) {
         lastSlash = sourceFilePath.lastIndexOf(47);
      }

      int i = sourceFilePath.lastIndexOf(33);
      int j = sourceFilePath.indexOf(".class");
      if (i > lastSlash && i != -1 && j != -1) {
         lastSlash = i;
      }

      String fileName = sourceFilePath.substring(lastSlash + 1);
      IProgramElement fileNode = new ProgramElement(asm, fileName, IProgramElement.Kind.FILE_JAVA, new SourceLocation(new File(sourceFilePath), 1, 1), 0, (String)null, (List)null);
      fileNode.addChild(IHierarchy.NO_STRUCTURE);
      return fileNode;
   }

   private static boolean isBinaryAspect(ResolvedType aspect) {
      return aspect.getBinaryPath() != null;
   }

   private static ISourceLocation getBinarySourceLocation(ResolvedType aspect, ISourceLocation sl) {
      if (sl == null) {
         return null;
      } else {
         String sourceFileName = null;
         if (aspect instanceof ReferenceType) {
            String s = ((ReferenceType)aspect).getDelegate().getSourcefilename();
            int i = s.lastIndexOf(47);
            if (i != -1) {
               sourceFileName = s.substring(i + 1);
            } else {
               sourceFileName = s;
            }
         }

         ISourceLocation sLoc = new SourceLocation(getBinaryFile(aspect), sl.getLine(), sl.getEndLine(), sl.getColumn() == 0 ? -2147483647 : sl.getColumn(), sl.getContext(), sourceFileName);
         return sLoc;
      }
   }

   private static ISourceLocation createSourceLocation(String sourcefilename, ResolvedType aspect, ISourceLocation sl) {
      ISourceLocation sLoc = new SourceLocation(getBinaryFile(aspect), sl.getLine(), sl.getEndLine(), sl.getColumn() == 0 ? -2147483647 : sl.getColumn(), sl.getContext(), sourcefilename);
      return sLoc;
   }

   private static String getSourceFileName(ResolvedType aspect) {
      String sourceFileName = null;
      if (aspect instanceof ReferenceType) {
         String s = ((ReferenceType)aspect).getDelegate().getSourcefilename();
         int i = s.lastIndexOf(47);
         if (i != -1) {
            sourceFileName = s.substring(i + 1);
         } else {
            sourceFileName = s;
         }
      }

      return sourceFileName;
   }

   private static File getBinaryFile(ResolvedType aspect) {
      String s = aspect.getBinaryPath();
      File f = aspect.getSourceLocation().getSourceFile();
      int i = f.getPath().lastIndexOf(46);
      String path = null;
      if (i != -1) {
         path = f.getPath().substring(0, i) + ".class";
      } else {
         path = f.getPath() + ".class";
      }

      return new File(s + "!" + path);
   }

   private static void createHierarchy(AsmManager model, ResolvedTypeMunger typeTransformer, ResolvedType aspect) {
      IProgramElement filenode = model.getHierarchy().findElementForSourceLine(typeTransformer.getSourceLocation());
      if (filenode != null || typeTransformer.getKind() != ResolvedTypeMunger.MethodDelegate2 && typeTransformer.getKind() != ResolvedTypeMunger.FieldHost) {
         if (filenode.getKind().equals(IProgramElement.Kind.FILE_JAVA)) {
            ISourceLocation binLocation = getBinarySourceLocation(aspect, aspect.getSourceLocation());
            String f = getBinaryFile(aspect).getName();
            IProgramElement classFileNode = new ProgramElement(model, f, IProgramElement.Kind.FILE, binLocation, 0, (String)null, (List)null);
            IProgramElement root = model.getHierarchy().getRoot();
            IProgramElement binaries = model.getHierarchy().findElementForLabel(root, IProgramElement.Kind.SOURCE_FOLDER, "binaries");
            if (binaries == null) {
               binaries = new ProgramElement(model, "binaries", IProgramElement.Kind.SOURCE_FOLDER, new ArrayList());
               root.addChild((IProgramElement)binaries);
            }

            String packagename = aspect.getPackageName() == null ? "" : aspect.getPackageName();
            IProgramElement pkgNode = model.getHierarchy().findElementForLabel((IProgramElement)binaries, IProgramElement.Kind.PACKAGE, packagename);
            if (pkgNode == null) {
               IProgramElement pkgNode = new ProgramElement(model, packagename, IProgramElement.Kind.PACKAGE, new ArrayList());
               ((IProgramElement)binaries).addChild(pkgNode);
               pkgNode.addChild(classFileNode);
            } else {
               pkgNode.addChild(classFileNode);
               Iterator i$ = pkgNode.getChildren().iterator();

               while(i$.hasNext()) {
                  IProgramElement element = (IProgramElement)i$.next();
                  if (!element.equals(classFileNode) && element.getHandleIdentifier().equals(classFileNode.getHandleIdentifier())) {
                     pkgNode.removeChild(classFileNode);
                     return;
                  }
               }
            }

            IProgramElement aspectNode = new ProgramElement(model, aspect.getSimpleName(), IProgramElement.Kind.ASPECT, getBinarySourceLocation(aspect, aspect.getSourceLocation()), aspect.getModifiers(), (String)null, (List)null);
            classFileNode.addChild(aspectNode);
            addChildNodes(model, aspect, aspectNode, (ResolvedMember[])aspect.getDeclaredPointcuts());
            addChildNodes(model, aspect, aspectNode, (Collection)aspect.getDeclaredAdvice());
            addChildNodes(model, aspect, aspectNode, (Collection)aspect.getDeclares());
            addChildNodes(model, aspect, aspectNode, (Collection)aspect.getTypeMungers());
         }
      }
   }

   public static void addDeclareAnnotationRelationship(AsmManager model, ISourceLocation declareAnnotationLocation, ISourceLocation annotatedLocation, boolean isRemove) {
      if (model != null) {
         IProgramElement sourceNode = model.getHierarchy().findElementForSourceLine(declareAnnotationLocation);
         String sourceHandle = sourceNode.getHandleIdentifier();
         if (sourceHandle != null) {
            IProgramElement targetNode = model.getHierarchy().findElementForSourceLine(annotatedLocation);
            String targetHandle = targetNode.getHandleIdentifier();
            if (targetHandle != null) {
               IRelationshipMap mapper = model.getRelationshipMap();
               IRelationship foreward = mapper.get(sourceHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotates", false, true);
               foreward.addTarget(targetHandle);
               IRelationship back = mapper.get(targetHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotated by", false, true);
               back.addTarget(sourceHandle);
               if (sourceNode.getSourceLocation() != null) {
                  model.addAspectInEffectThisBuild(sourceNode.getSourceLocation().getSourceFile());
               }

            }
         }
      }
   }

   public static void createHierarchyForBinaryAspect(AsmManager asm, ShadowMunger munger) {
      if (munger.isBinary()) {
         IProgramElement sourceFileNode = asm.getHierarchy().findElementForSourceLine(munger.getSourceLocation());
         if (sourceFileNode.getKind().equals(IProgramElement.Kind.FILE_JAVA)) {
            ResolvedType aspect = munger.getDeclaringType();
            IProgramElement classFileNode = new ProgramElement(asm, sourceFileNode.getName(), IProgramElement.Kind.FILE, munger.getBinarySourceLocation(aspect.getSourceLocation()), 0, (String)null, (List)null);
            IProgramElement root = asm.getHierarchy().getRoot();
            IProgramElement binaries = asm.getHierarchy().findElementForLabel(root, IProgramElement.Kind.SOURCE_FOLDER, "binaries");
            if (binaries == null) {
               binaries = new ProgramElement(asm, "binaries", IProgramElement.Kind.SOURCE_FOLDER, new ArrayList());
               root.addChild((IProgramElement)binaries);
            }

            String packagename = aspect.getPackageName() == null ? "" : aspect.getPackageName();
            IProgramElement pkgNode = asm.getHierarchy().findElementForLabel((IProgramElement)binaries, IProgramElement.Kind.PACKAGE, packagename);
            if (pkgNode == null) {
               IProgramElement pkgNode = new ProgramElement(asm, packagename, IProgramElement.Kind.PACKAGE, new ArrayList());
               ((IProgramElement)binaries).addChild(pkgNode);
               pkgNode.addChild(classFileNode);
            } else {
               pkgNode.addChild(classFileNode);
               Iterator i$ = pkgNode.getChildren().iterator();

               while(i$.hasNext()) {
                  IProgramElement element = (IProgramElement)i$.next();
                  if (!element.equals(classFileNode) && element.getHandleIdentifier().equals(classFileNode.getHandleIdentifier())) {
                     pkgNode.removeChild(classFileNode);
                     return;
                  }
               }
            }

            IProgramElement aspectNode = new ProgramElement(asm, aspect.getSimpleName(), IProgramElement.Kind.ASPECT, munger.getBinarySourceLocation(aspect.getSourceLocation()), aspect.getModifiers(), (String)null, (List)null);
            classFileNode.addChild(aspectNode);
            String sourcefilename = getSourceFileName(aspect);
            addPointcuts(asm, sourcefilename, aspect, aspectNode, aspect.getDeclaredPointcuts());
            addChildNodes(asm, aspect, aspectNode, (Collection)aspect.getDeclaredAdvice());
            addChildNodes(asm, aspect, aspectNode, (Collection)aspect.getDeclares());
            addChildNodes(asm, aspect, aspectNode, (Collection)aspect.getTypeMungers());
         }
      }
   }

   private static void addPointcuts(AsmManager model, String sourcefilename, ResolvedType aspect, IProgramElement containingAspect, ResolvedMember[] pointcuts) {
      for(int i = 0; i < pointcuts.length; ++i) {
         ResolvedMember pointcut = pointcuts[i];
         if (pointcut instanceof ResolvedPointcutDefinition) {
            ResolvedPointcutDefinition rpcd = (ResolvedPointcutDefinition)pointcut;
            Pointcut p = rpcd.getPointcut();
            ISourceLocation sLoc = p == null ? null : p.getSourceLocation();
            if (sLoc == null) {
               sLoc = rpcd.getSourceLocation();
            }

            ISourceLocation pointcutLocation = sLoc == null ? null : createSourceLocation(sourcefilename, aspect, sLoc);
            ProgramElement pointcutElement = new ProgramElement(model, pointcut.getName(), IProgramElement.Kind.POINTCUT, pointcutLocation, pointcut.getModifiers(), NO_COMMENT, Collections.emptyList());
            containingAspect.addChild(pointcutElement);
         }
      }

   }

   private static void addChildNodes(AsmManager asm, ResolvedType aspect, IProgramElement parent, ResolvedMember[] children) {
      for(int i = 0; i < children.length; ++i) {
         ResolvedMember pcd = children[i];
         if (pcd instanceof ResolvedPointcutDefinition) {
            ResolvedPointcutDefinition rpcd = (ResolvedPointcutDefinition)pcd;
            Pointcut p = rpcd.getPointcut();
            ISourceLocation sLoc = p == null ? null : p.getSourceLocation();
            if (sLoc == null) {
               sLoc = rpcd.getSourceLocation();
            }

            parent.addChild(new ProgramElement(asm, pcd.getName(), IProgramElement.Kind.POINTCUT, getBinarySourceLocation(aspect, sLoc), pcd.getModifiers(), (String)null, Collections.emptyList()));
         }
      }

   }

   private static void addChildNodes(AsmManager asm, ResolvedType aspect, IProgramElement parent, Collection children) {
      int deCtr = 1;
      int dwCtr = 1;
      Iterator i$ = children.iterator();

      while(i$.hasNext()) {
         Object element = i$.next();
         if (element instanceof DeclareErrorOrWarning) {
            DeclareErrorOrWarning decl = (DeclareErrorOrWarning)element;
            int counter = false;
            int counter;
            if (decl.isError()) {
               counter = deCtr++;
            } else {
               counter = dwCtr++;
            }

            parent.addChild(createDeclareErrorOrWarningChild(asm, aspect, decl, counter));
         } else if (element instanceof Advice) {
            Advice advice = (Advice)element;
            parent.addChild(createAdviceChild(asm, advice));
         } else if (element instanceof DeclareParents) {
            parent.addChild(createDeclareParentsChild(asm, (DeclareParents)element));
         } else if (element instanceof BcelTypeMunger) {
            IProgramElement newChild = createIntertypeDeclaredChild(asm, aspect, (BcelTypeMunger)element);
            if (newChild != null) {
               parent.addChild(newChild);
            }
         }
      }

   }

   private static IProgramElement createDeclareErrorOrWarningChild(AsmManager model, ResolvedType aspect, DeclareErrorOrWarning decl, int count) {
      IProgramElement deowNode = new ProgramElement(model, decl.getName(), decl.isError() ? IProgramElement.Kind.DECLARE_ERROR : IProgramElement.Kind.DECLARE_WARNING, getBinarySourceLocation(aspect, decl.getSourceLocation()), decl.getDeclaringType().getModifiers(), (String)null, (List)null);
      deowNode.setDetails("\"" + AsmRelationshipUtils.genDeclareMessage(decl.getMessage()) + "\"");
      if (count != -1) {
         deowNode.setBytecodeName(decl.getName() + "_" + count);
      }

      return deowNode;
   }

   private static IProgramElement createAdviceChild(AsmManager model, Advice advice) {
      IProgramElement adviceNode = new ProgramElement(model, advice.getKind().getName(), IProgramElement.Kind.ADVICE, advice.getBinarySourceLocation(advice.getSourceLocation()), advice.getSignature().getModifiers(), (String)null, Collections.emptyList());
      adviceNode.setDetails(AsmRelationshipUtils.genPointcutDetails(advice.getPointcut()));
      adviceNode.setBytecodeName(advice.getSignature().getName());
      return adviceNode;
   }

   private static IProgramElement createIntertypeDeclaredChild(AsmManager model, ResolvedType aspect, BcelTypeMunger itd) {
      ResolvedTypeMunger rtMunger = itd.getMunger();
      ResolvedMember sig = rtMunger.getSignature();
      ResolvedTypeMunger.Kind kind = rtMunger.getKind();
      String name;
      ProgramElement pe;
      if (kind == ResolvedTypeMunger.Field) {
         name = sig.getDeclaringType().getClassName() + "." + sig.getName();
         if (name.indexOf("$") != -1) {
            name = name.substring(name.indexOf("$") + 1);
         }

         pe = new ProgramElement(model, name, IProgramElement.Kind.INTER_TYPE_FIELD, getBinarySourceLocation(aspect, itd.getSourceLocation()), rtMunger.getSignature().getModifiers(), (String)null, Collections.emptyList());
         pe.setCorrespondingType(sig.getReturnType().getName());
         return pe;
      } else if (kind == ResolvedTypeMunger.Method) {
         name = sig.getDeclaringType().getClassName() + "." + sig.getName();
         if (name.indexOf("$") != -1) {
            name = name.substring(name.indexOf("$") + 1);
         }

         pe = new ProgramElement(model, name, IProgramElement.Kind.INTER_TYPE_METHOD, getBinarySourceLocation(aspect, itd.getSourceLocation()), rtMunger.getSignature().getModifiers(), (String)null, Collections.emptyList());
         setParams(pe, sig);
         return pe;
      } else if (kind == ResolvedTypeMunger.Constructor) {
         name = sig.getDeclaringType().getClassName() + "." + sig.getDeclaringType().getClassName();
         if (name.indexOf("$") != -1) {
            name = name.substring(name.indexOf("$") + 1);
         }

         pe = new ProgramElement(model, name, IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR, getBinarySourceLocation(aspect, itd.getSourceLocation()), rtMunger.getSignature().getModifiers(), (String)null, Collections.emptyList());
         setParams(pe, sig);
         return pe;
      } else {
         return null;
      }
   }

   private static void setParams(IProgramElement pe, ResolvedMember sig) {
      UnresolvedType[] ts = sig.getParameterTypes();
      pe.setParameterNames(Collections.emptyList());
      if (ts == null) {
         pe.setParameterSignatures(Collections.emptyList(), Collections.emptyList());
      } else {
         List paramSigs = new ArrayList();

         for(int i = 0; i < ts.length; ++i) {
            paramSigs.add(ts[i].getSignature().toCharArray());
         }

         pe.setParameterSignatures(paramSigs, Collections.emptyList());
      }

      pe.setCorrespondingType(sig.getReturnType().getName());
   }

   private static IProgramElement createDeclareParentsChild(AsmManager model, DeclareParents decp) {
      IProgramElement decpElement = new ProgramElement(model, "declare parents", IProgramElement.Kind.DECLARE_PARENTS, getBinarySourceLocation(decp.getDeclaringType(), decp.getSourceLocation()), 1, (String)null, Collections.emptyList());
      setParentTypesOnDeclareParentsNode(decp, decpElement);
      return decpElement;
   }

   private static void setParentTypesOnDeclareParentsNode(DeclareParents decp, IProgramElement decpElement) {
      TypePatternList tpl = decp.getParents();
      List parents = new ArrayList();

      for(int i = 0; i < tpl.size(); ++i) {
         parents.add(tpl.get(i).getExactType().getName().replaceAll("\\$", "."));
      }

      decpElement.setParentTypes(parents);
   }

   public static String getHandle(AsmManager asm, Advice advice) {
      if (null == advice.handle) {
         ISourceLocation sl = advice.getSourceLocation();
         if (sl != null) {
            IProgramElement ipe = asm.getHierarchy().findElementForSourceLine(sl);
            advice.handle = ipe.getHandleIdentifier();
         }
      }

      return advice.handle;
   }

   public static void addAdvisedRelationship(AsmManager model, Shadow matchedShadow, ShadowMunger munger) {
      if (model != null) {
         if (munger instanceof Advice) {
            Advice advice = (Advice)munger;
            if (advice.getKind().isPerEntry() || advice.getKind().isCflow()) {
               return;
            }

            if (World.createInjarHierarchy) {
               createHierarchyForBinaryAspect(model, advice);
            }

            IRelationshipMap mapper = model.getRelationshipMap();
            IProgramElement targetNode = getNode(model, matchedShadow);
            if (targetNode == null) {
               return;
            }

            boolean runtimeTest = advice.hasDynamicTests();
            IProgramElement.ExtraInformation extra = new IProgramElement.ExtraInformation();
            String adviceHandle = getHandle(model, advice);
            if (adviceHandle == null) {
               return;
            }

            extra.setExtraAdviceInformation(advice.getKind().getName());
            IProgramElement adviceElement = model.getHierarchy().findElementForHandle(adviceHandle);
            if (adviceElement != null) {
               adviceElement.setExtraInfo(extra);
            }

            String targetHandle = targetNode.getHandleIdentifier();
            IRelationship foreward;
            IRelationship back;
            if (advice.getKind().equals(AdviceKind.Softener)) {
               foreward = mapper.get(adviceHandle, IRelationship.Kind.DECLARE_SOFT, "softens", runtimeTest, true);
               if (foreward != null) {
                  foreward.addTarget(targetHandle);
               }

               back = mapper.get(targetHandle, IRelationship.Kind.DECLARE, "softened by", runtimeTest, true);
               if (back != null) {
                  back.addTarget(adviceHandle);
               }
            } else {
               foreward = mapper.get(adviceHandle, IRelationship.Kind.ADVICE, "advises", runtimeTest, true);
               if (foreward != null) {
                  foreward.addTarget(targetHandle);
               }

               back = mapper.get(targetHandle, IRelationship.Kind.ADVICE, "advised by", runtimeTest, true);
               if (back != null) {
                  back.addTarget(adviceHandle);
               }
            }

            if (adviceElement.getSourceLocation() != null) {
               model.addAspectInEffectThisBuild(adviceElement.getSourceLocation().getSourceFile());
            }
         }

      }
   }

   protected static IProgramElement getNode(AsmManager model, Shadow shadow) {
      Member enclosingMember = shadow.getEnclosingCodeSignature();
      IProgramElement enclosingNode = null;
      Member shadowSig;
      if (shadow instanceof BcelShadow) {
         shadowSig = ((BcelShadow)shadow).getRealEnclosingCodeSignature();
         if (shadowSig == null) {
            enclosingNode = lookupMember(model.getHierarchy(), shadow.getEnclosingType(), enclosingMember);
         } else {
            UnresolvedType type = enclosingMember.getDeclaringType();
            UnresolvedType actualType = shadowSig.getDeclaringType();
            if (type.equals(actualType)) {
               enclosingNode = lookupMember(model.getHierarchy(), shadow.getEnclosingType(), enclosingMember);
            } else {
               enclosingNode = lookupMember(model.getHierarchy(), shadow.getEnclosingType(), shadowSig);
            }
         }
      } else {
         enclosingNode = lookupMember(model.getHierarchy(), shadow.getEnclosingType(), enclosingMember);
      }

      if (enclosingNode == null) {
         Lint.Kind err = shadow.getIWorld().getLint().shadowNotInStructure;
         if (err.isEnabled()) {
            err.signal(shadow.toString(), shadow.getSourceLocation());
         }

         return null;
      } else {
         shadowSig = shadow.getSignature();
         if (shadow.getKind() != Shadow.MethodCall && shadow.getKind() != Shadow.ConstructorCall && shadowSig.equals(enclosingMember)) {
            return enclosingNode;
         } else {
            IProgramElement bodyNode = findOrCreateCodeNode(model, enclosingNode, shadowSig, shadow);
            return bodyNode;
         }
      }
   }

   private static boolean sourceLinesMatch(ISourceLocation location1, ISourceLocation location2) {
      return location1.getLine() == location2.getLine();
   }

   private static IProgramElement findOrCreateCodeNode(AsmManager asm, IProgramElement enclosingNode, Member shadowSig, Shadow shadow) {
      Iterator it = enclosingNode.getChildren().iterator();

      IProgramElement node;
      int excl;
      do {
         do {
            if (!it.hasNext()) {
               ISourceLocation sl = shadow.getSourceLocation();
               SourceLocation peLoc = new SourceLocation(enclosingNode.getSourceLocation().getSourceFile(), sl.getLine());
               peLoc.setOffset(sl.getOffset());
               IProgramElement peNode = new ProgramElement(asm, shadow.toString(), IProgramElement.Kind.CODE, peLoc, 0, (String)null, (List)null);
               int numberOfChildrenWithThisName = 0;
               Iterator i$ = enclosingNode.getChildren().iterator();

               while(i$.hasNext()) {
                  IProgramElement child = (IProgramElement)i$.next();
                  if (child.getName().equals(shadow.toString())) {
                     ++numberOfChildrenWithThisName;
                  }
               }

               peNode.setBytecodeName(shadowSig.getName() + "!" + (numberOfChildrenWithThisName + 1));
               peNode.setBytecodeSignature(shadowSig.getSignature());
               enclosingNode.addChild(peNode);
               return peNode;
            }

            node = (IProgramElement)it.next();
            excl = node.getBytecodeName().lastIndexOf(33);
         } while((excl == -1 || !shadowSig.getName().equals(node.getBytecodeName().substring(0, excl))) && !shadowSig.getName().equals(node.getBytecodeName()));
      } while(!shadowSig.getSignature().equals(node.getBytecodeSignature()) || !sourceLinesMatch(node.getSourceLocation(), shadow.getSourceLocation()));

      return node;
   }

   private static IProgramElement lookupMember(IHierarchy model, UnresolvedType declaringType, Member member) {
      IProgramElement typeElement = model.findElementForType(declaringType.getPackageName(), declaringType.getClassName());
      if (typeElement == null) {
         return null;
      } else {
         Iterator it = typeElement.getChildren().iterator();

         IProgramElement element;
         do {
            if (!it.hasNext()) {
               return typeElement;
            }

            element = (IProgramElement)it.next();
         } while(!member.getName().equals(element.getBytecodeName()) || !member.getSignature().equals(element.getBytecodeSignature()));

         return element;
      }
   }

   public static void addDeclareAnnotationMethodRelationship(ISourceLocation sourceLocation, String affectedTypeName, ResolvedMember affectedMethod, AsmManager model) {
      if (model != null) {
         String pkg = null;
         String type = affectedTypeName;
         int packageSeparator = affectedTypeName.lastIndexOf(".");
         if (packageSeparator != -1) {
            pkg = affectedTypeName.substring(0, packageSeparator);
            type = affectedTypeName.substring(packageSeparator + 1);
         }

         IHierarchy hierarchy = model.getHierarchy();
         IProgramElement typeElem = hierarchy.findElementForType(pkg, type);
         if (typeElem != null) {
            if (!typeElem.getKind().isType()) {
               throw new IllegalStateException("Did not find a type element, found a " + typeElem.getKind() + " element");
            } else {
               StringBuilder parmString = new StringBuilder("(");
               UnresolvedType[] args = affectedMethod.getParameterTypes();

               for(int i = 0; i < args.length; ++i) {
                  parmString.append(args[i].getName());
                  if (i + 1 < args.length) {
                     parmString.append(",");
                  }
               }

               parmString.append(")");
               IProgramElement methodElem = null;
               if (affectedMethod.getName().startsWith("<init>")) {
                  methodElem = hierarchy.findElementForSignature(typeElem, IProgramElement.Kind.CONSTRUCTOR, type + parmString);
                  if (methodElem == null && args.length == 0) {
                     methodElem = typeElem;
                  }
               } else {
                  methodElem = hierarchy.findElementForSignature(typeElem, IProgramElement.Kind.METHOD, affectedMethod.getName() + parmString);
               }

               if (methodElem != null) {
                  try {
                     String targetHandle = methodElem.getHandleIdentifier();
                     if (targetHandle == null) {
                        return;
                     }

                     IProgramElement sourceNode = hierarchy.findElementForSourceLine(sourceLocation);
                     String sourceHandle = sourceNode.getHandleIdentifier();
                     if (sourceHandle == null) {
                        return;
                     }

                     IRelationshipMap mapper = model.getRelationshipMap();
                     IRelationship foreward = mapper.get(sourceHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotates", false, true);
                     foreward.addTarget(targetHandle);
                     IRelationship back = mapper.get(targetHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotated by", false, true);
                     back.addTarget(sourceHandle);
                  } catch (Throwable var18) {
                     var18.printStackTrace();
                  }

               }
            }
         }
      }
   }

   public static void addDeclareAnnotationFieldRelationship(AsmManager model, ISourceLocation declareLocation, String affectedTypeName, ResolvedMember affectedFieldName, boolean isRemove) {
      if (model != null) {
         String pkg = null;
         String type = affectedTypeName;
         int packageSeparator = affectedTypeName.lastIndexOf(".");
         if (packageSeparator != -1) {
            pkg = affectedTypeName.substring(0, packageSeparator);
            type = affectedTypeName.substring(packageSeparator + 1);
         }

         IHierarchy hierarchy = model.getHierarchy();
         IProgramElement typeElem = hierarchy.findElementForType(pkg, type);
         if (typeElem != null) {
            IProgramElement fieldElem = hierarchy.findElementForSignature(typeElem, IProgramElement.Kind.FIELD, affectedFieldName.getName());
            if (fieldElem != null) {
               String targetHandle = fieldElem.getHandleIdentifier();
               if (targetHandle != null) {
                  IProgramElement sourceNode = hierarchy.findElementForSourceLine(declareLocation);
                  String sourceHandle = sourceNode.getHandleIdentifier();
                  if (sourceHandle != null) {
                     IRelationshipMap relmap = model.getRelationshipMap();
                     IRelationship foreward = relmap.get(sourceHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotates", false, true);
                     foreward.addTarget(targetHandle);
                     IRelationship back = relmap.get(targetHandle, IRelationship.Kind.DECLARE_INTER_TYPE, "annotated by", false, true);
                     back.addTarget(sourceHandle);
                  }
               }
            }
         }
      }
   }
}
