package com.bea.core.repackaged.aspectj.asm;

import com.bea.core.repackaged.aspectj.asm.internal.AspectJElementHierarchy;
import com.bea.core.repackaged.aspectj.asm.internal.HandleProviderDelimiter;
import com.bea.core.repackaged.aspectj.asm.internal.JDTLikeHandleProvider;
import com.bea.core.repackaged.aspectj.asm.internal.RelationshipMap;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.IStructureModel;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class AsmManager implements IStructureModel {
   public static boolean recordingLastActiveStructureModel = true;
   public static AsmManager lastActiveStructureModel;
   public static boolean forceSingletonBehaviour = false;
   public static boolean attemptIncrementalModelRepairs = false;
   public static boolean dumpModelPostBuild = false;
   private static boolean dumpModel = false;
   private static boolean dumpRelationships = false;
   private static boolean dumpDeltaProcessing = false;
   private static IModelFilter modelFilter = null;
   private static String dumpFilename = "";
   private static boolean reporting = false;
   private static boolean completingTypeBindings = false;
   private final List structureListeners = new ArrayList();
   protected IHierarchy hierarchy;
   protected Map inpathMap;
   private IRelationshipMap mapper;
   private IElementHandleProvider handleProvider;
   private final CanonicalFilePathMap canonicalFilePathMap = new CanonicalFilePathMap();
   private final Set lastBuildChanges = new HashSet();
   final Set aspectsWeavingInLastBuild = new HashSet();

   private AsmManager() {
   }

   public static AsmManager createNewStructureModel(Map inpathMap) {
      if (forceSingletonBehaviour && lastActiveStructureModel != null) {
         return lastActiveStructureModel;
      } else {
         AsmManager asm = new AsmManager();
         asm.inpathMap = inpathMap;
         asm.hierarchy = new AspectJElementHierarchy(asm);
         asm.mapper = new RelationshipMap();
         asm.handleProvider = new JDTLikeHandleProvider(asm);
         asm.handleProvider.initialize();
         asm.resetDeltaProcessing();
         setLastActiveStructureModel(asm);
         return asm;
      }
   }

   public IHierarchy getHierarchy() {
      return this.hierarchy;
   }

   public IRelationshipMap getRelationshipMap() {
      return this.mapper;
   }

   public void fireModelUpdated() {
      this.notifyListeners();
      if (dumpModelPostBuild && this.hierarchy.getConfigFile() != null) {
         this.writeStructureModel(this.hierarchy.getConfigFile());
      }

   }

   public HashMap getInlineAnnotations(String sourceFile, boolean showSubMember, boolean showMemberAndType) {
      if (!this.hierarchy.isValid()) {
         return null;
      } else {
         HashMap annotations = new HashMap();
         IProgramElement node = this.hierarchy.findElementForSourceFile(sourceFile);
         if (node == IHierarchy.NO_STRUCTURE) {
            return null;
         } else {
            ArrayList peNodes = new ArrayList();
            this.getAllStructureChildren(node, peNodes, showSubMember, showMemberAndType);
            Iterator it = peNodes.iterator();

            while(it.hasNext()) {
               IProgramElement peNode = (IProgramElement)it.next();
               List entries = new ArrayList();
               entries.add(peNode);
               ISourceLocation sourceLoc = peNode.getSourceLocation();
               if (null != sourceLoc) {
                  Integer hash = new Integer(sourceLoc.getLine());
                  List existingEntry = (List)annotations.get(hash);
                  if (existingEntry != null) {
                     entries.addAll(existingEntry);
                  }

                  annotations.put(hash, entries);
               }
            }

            return annotations;
         }
      }
   }

   private void getAllStructureChildren(IProgramElement node, List result, boolean showSubMember, boolean showMemberAndType) {
      List children = node.getChildren();
      if (node.getChildren() != null) {
         IProgramElement next;
         for(Iterator i$ = children.iterator(); i$.hasNext(); this.getAllStructureChildren(next, result, showSubMember, showMemberAndType)) {
            next = (IProgramElement)i$.next();
            List rels = this.mapper.get(next);
            if (next != null && (next.getKind() == IProgramElement.Kind.CODE && showSubMember || next.getKind() != IProgramElement.Kind.CODE && showMemberAndType) && rels != null && rels.size() > 0) {
               result.add(next);
            }
         }

      }
   }

   public void addListener(IHierarchyListener listener) {
      this.structureListeners.add(listener);
   }

   public void removeStructureListener(IHierarchyListener listener) {
      this.structureListeners.remove(listener);
   }

   public void removeAllListeners() {
      this.structureListeners.clear();
   }

   private void notifyListeners() {
      Iterator i$ = this.structureListeners.iterator();

      while(i$.hasNext()) {
         IHierarchyListener listener = (IHierarchyListener)i$.next();
         listener.elementsUpdated(this.hierarchy);
      }

   }

   public IElementHandleProvider getHandleProvider() {
      return this.handleProvider;
   }

   public void setHandleProvider(IElementHandleProvider handleProvider) {
      this.handleProvider = handleProvider;
   }

   public void writeStructureModel(String configFilePath) {
      try {
         String filePath = this.genExternFilePath(configFilePath);
         FileOutputStream fos = new FileOutputStream(filePath);
         ObjectOutputStream s = new ObjectOutputStream(fos);
         s.writeObject(this.hierarchy);
         s.writeObject(this.mapper);
         s.flush();
         fos.flush();
         fos.close();
         s.close();
      } catch (IOException var5) {
      }

   }

   public void readStructureModel(String configFilePath) {
      boolean hierarchyReadOK = false;

      try {
         if (configFilePath == null) {
            this.hierarchy.setRoot(IHierarchy.NO_STRUCTURE);
         } else {
            String filePath = this.genExternFilePath(configFilePath);
            FileInputStream in = new FileInputStream(filePath);
            ObjectInputStream s = new ObjectInputStream(in);
            this.hierarchy = (AspectJElementHierarchy)s.readObject();
            ((AspectJElementHierarchy)this.hierarchy).setAsmManager(this);
            hierarchyReadOK = true;
            this.mapper = (RelationshipMap)s.readObject();
            s.close();
         }
      } catch (FileNotFoundException var11) {
         this.hierarchy.setRoot(IHierarchy.NO_STRUCTURE);
      } catch (EOFException var12) {
         if (!hierarchyReadOK) {
            System.err.println("AsmManager: Unable to read structure model: " + configFilePath + " because of:");
            var12.printStackTrace();
            this.hierarchy.setRoot(IHierarchy.NO_STRUCTURE);
         }
      } catch (Exception var13) {
         this.hierarchy.setRoot(IHierarchy.NO_STRUCTURE);
      } finally {
         this.notifyListeners();
      }

   }

   private String genExternFilePath(String configFilePath) {
      if (configFilePath.lastIndexOf(".lst") != -1) {
         configFilePath = configFilePath.substring(0, configFilePath.lastIndexOf(".lst"));
      }

      return configFilePath + ".ajsym";
   }

   public String getCanonicalFilePath(File f) {
      return this.canonicalFilePathMap.get(f);
   }

   public CanonicalFilePathMap getCanonicalFilePathMap() {
      return this.canonicalFilePathMap;
   }

   public static void setReporting(String filename, boolean dModel, boolean dRels, boolean dDeltaProcessing, boolean deletefile) {
      reporting = true;
      dumpModel = dModel;
      dumpRelationships = dRels;
      dumpDeltaProcessing = dDeltaProcessing;
      if (deletefile) {
         (new File(filename)).delete();
      }

      dumpFilename = filename;
   }

   public static void setReporting(String filename, boolean dModel, boolean dRels, boolean dDeltaProcessing, boolean deletefile, IModelFilter aFilter) {
      setReporting(filename, dModel, dRels, dDeltaProcessing, deletefile);
      modelFilter = aFilter;
   }

   public static boolean isReporting() {
      return reporting;
   }

   public static void setDontReport() {
      reporting = false;
      dumpDeltaProcessing = false;
      dumpModel = false;
      dumpRelationships = false;
   }

   public void reportModelInfo(String reasonForReport) {
      if (dumpModel || dumpRelationships) {
         try {
            FileWriter fw = new FileWriter(dumpFilename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (dumpModel) {
               bw.write("=== MODEL STATUS REPORT ========= " + reasonForReport + "\n");
               dumptree(bw, this.hierarchy.getRoot(), 0);
               bw.write("=== END OF MODEL REPORT =========\n");
            }

            if (dumpRelationships) {
               bw.write("=== RELATIONSHIPS REPORT ========= " + reasonForReport + "\n");
               this.dumprels(bw);
               bw.write("=== END OF RELATIONSHIPS REPORT ==\n");
            }

            Properties p = this.summarizeModel().getProperties();
            Enumeration pkeyenum = p.keys();
            bw.write("=== Properties of the model and relationships map =====\n");

            while(pkeyenum.hasMoreElements()) {
               String pkey = (String)pkeyenum.nextElement();
               bw.write(pkey + "=" + p.getProperty(pkey) + "\n");
            }

            bw.flush();
            fw.close();
         } catch (IOException var7) {
            System.err.println("InternalError: Unable to report model information:");
            var7.printStackTrace();
         }

      }
   }

   public static void dumptree(Writer w, IProgramElement node, int indent) throws IOException {
      for(int i = 0; i < indent; ++i) {
         w.write(" ");
      }

      String loc = "";
      if (node != null && node.getSourceLocation() != null) {
         loc = node.getSourceLocation().toString();
         if (modelFilter != null) {
            loc = modelFilter.processFilelocation(loc);
         }
      }

      w.write(node + "  [" + (node == null ? "null" : node.getKind().toString()) + "] " + loc + "\n");
      if (node != null) {
         Iterator i$ = node.getChildren().iterator();

         while(i$.hasNext()) {
            IProgramElement child = (IProgramElement)i$.next();
            dumptree(w, child, indent + 2);
         }
      }

   }

   public static void dumptree(IProgramElement node, int indent) throws IOException {
      for(int i = 0; i < indent; ++i) {
         System.out.print(" ");
      }

      String loc = "";
      if (node != null && node.getSourceLocation() != null) {
         loc = node.getSourceLocation().toString();
      }

      System.out.println(node + "  [" + (node == null ? "null" : node.getKind().toString()) + "] " + loc);
      if (node != null) {
         Iterator i$ = node.getChildren().iterator();

         while(i$.hasNext()) {
            IProgramElement child = (IProgramElement)i$.next();
            dumptree(child, indent + 2);
         }
      }

   }

   public void dumprels(Writer w) throws IOException {
      int ctr = 1;
      Set entries = this.mapper.getEntries();
      Iterator i$ = entries.iterator();

      while(i$.hasNext()) {
         String hid = (String)i$.next();
         List rels = this.mapper.get(hid);
         Iterator i$ = rels.iterator();

         while(i$.hasNext()) {
            IRelationship ir = (IRelationship)i$.next();
            List targets = ir.getTargets();
            Iterator i$ = targets.iterator();

            while(i$.hasNext()) {
               String thid = (String)i$.next();
               StringBuffer sb = new StringBuffer();
               if (modelFilter == null || modelFilter.wantsHandleIds()) {
                  sb.append("Hid:" + ctr++ + ":");
               }

               sb.append("(targets=" + targets.size() + ") " + hid + " (" + ir.getName() + ") " + thid + "\n");
               w.write(sb.toString());
            }
         }
      }

   }

   private void dumprelsStderr(String key) {
      System.err.println("Relationships dump follows: " + key);
      int ctr = 1;
      Set entries = this.mapper.getEntries();
      Iterator i$ = entries.iterator();

      while(i$.hasNext()) {
         String hid = (String)i$.next();
         Iterator i$ = this.mapper.get(hid).iterator();

         while(i$.hasNext()) {
            IRelationship ir = (IRelationship)i$.next();
            List targets = ir.getTargets();
            Iterator i$ = targets.iterator();

            while(i$.hasNext()) {
               String thid = (String)i$.next();
               System.err.println("Hid:" + ctr++ + ":(targets=" + targets.size() + ") " + hid + " (" + ir.getName() + ") " + thid);
            }
         }
      }

      System.err.println("End of relationships dump for: " + key);
   }

   public boolean removeStructureModelForFiles(Writer fw, Collection files) throws IOException {
      boolean modelModified = false;
      Set deletedNodes = new HashSet();
      Iterator i$ = files.iterator();

      while(i$.hasNext()) {
         File fileForCompilation = (File)i$.next();
         String correctedPath = this.getCanonicalFilePath(fileForCompilation);
         IProgramElement progElem = (IProgramElement)this.hierarchy.findInFileMap(correctedPath);
         if (progElem != null) {
            if (dumpDeltaProcessing) {
               fw.write("Deleting " + progElem + " node for file " + fileForCompilation + "\n");
            }

            this.removeNode(progElem);
            this.lastBuildChanges.add(fileForCompilation);
            deletedNodes.add(this.getCanonicalFilePath(progElem.getSourceLocation().getSourceFile()));
            if (!this.hierarchy.removeFromFileMap(correctedPath)) {
               throw new RuntimeException("Whilst repairing model, couldn't remove entry for file: " + correctedPath + " from the filemap");
            }

            modelModified = true;
         }
      }

      if (modelModified) {
         this.hierarchy.updateHandleMap(deletedNodes);
      }

      return modelModified;
   }

   public void processDelta(Collection files_tobecompiled, Set files_added, Set files_deleted) {
      try {
         Writer fw = null;
         if (dumpDeltaProcessing) {
            FileWriter filew = new FileWriter(dumpFilename, true);
            fw = new BufferedWriter(filew);
            fw.write("=== Processing delta changes for the model ===\n");
            fw.write("Files for compilation:#" + files_tobecompiled.size() + ":" + files_tobecompiled + "\n");
            fw.write("Files added          :#" + files_added.size() + ":" + files_added + "\n");
            fw.write("Files deleted        :#" + files_deleted.size() + ":" + files_deleted + "\n");
         }

         long stime = System.currentTimeMillis();
         this.removeStructureModelForFiles(fw, files_deleted);
         long etime1 = System.currentTimeMillis();
         this.repairRelationships(fw);
         long etime2 = System.currentTimeMillis();
         this.removeStructureModelForFiles(fw, files_tobecompiled);
         if (dumpDeltaProcessing) {
            fw.write("===== Delta Processing timing ==========\n");
            fw.write("Hierarchy=" + (etime1 - stime) + "ms   Relationshipmap=" + (etime2 - etime1) + "ms\n");
            fw.write("===== Traversal ========================\n");
            fw.write("========================================\n");
            fw.flush();
            fw.close();
         }

         this.reportModelInfo("After delta processing");
      } catch (IOException var12) {
         var12.printStackTrace();
      }

   }

   private String getTypeNameFromHandle(String handle, Map cache) {
      try {
         String typename = (String)cache.get(handle);
         if (typename != null) {
            return typename;
         } else {
            int hasPackage = handle.indexOf(HandleProviderDelimiter.PACKAGEFRAGMENT.getDelimiter());
            int typeLocation = handle.indexOf(HandleProviderDelimiter.TYPE.getDelimiter());
            if (typeLocation == -1) {
               typeLocation = handle.indexOf(HandleProviderDelimiter.ASPECT_TYPE.getDelimiter());
            }

            if (typeLocation == -1) {
               return "";
            } else {
               StringBuffer qualifiedTypeNameFromHandle = new StringBuffer();
               if (hasPackage != -1) {
                  int classfileLoc = handle.indexOf(HandleProviderDelimiter.CLASSFILE.getDelimiter(), hasPackage);
                  qualifiedTypeNameFromHandle.append(handle.substring(hasPackage + 1, classfileLoc));
                  qualifiedTypeNameFromHandle.append('.');
               }

               qualifiedTypeNameFromHandle.append(handle.substring(typeLocation + 1));
               typename = qualifiedTypeNameFromHandle.toString();
               cache.put(handle, typename);
               return typename;
            }
         }
      } catch (StringIndexOutOfBoundsException var8) {
         System.err.println("Handle processing problem, the handle is: " + handle);
         var8.printStackTrace(System.err);
         return "";
      }
   }

   public void removeRelationshipsTargettingThisType(String typename) {
      boolean debug = false;
      if (debug) {
         System.err.println(">>removeRelationshipsTargettingThisType " + typename);
      }

      String pkg = null;
      String type = typename;
      int lastSep = typename.lastIndexOf(46);
      if (lastSep != -1) {
         pkg = typename.substring(0, lastSep);
         type = typename.substring(lastSep + 1);
      }

      boolean didsomething = false;
      IProgramElement typeNode = this.hierarchy.findElementForType(pkg, type);
      if (typeNode != null) {
         Set sourcesToRemove = new HashSet();
         Map handleToTypenameCache = new HashMap();
         Set sourcehandlesSet = this.mapper.getEntries();
         List relationshipsToRemove = new ArrayList();
         Iterator i$ = sourcehandlesSet.iterator();

         while(true) {
            while(true) {
               String hid;
               List relationships;
               do {
                  IProgramElement ipe;
                  do {
                     do {
                        if (!i$.hasNext()) {
                           i$ = sourcesToRemove.iterator();

                           while(i$.hasNext()) {
                              hid = (String)i$.next();
                              this.mapper.removeAll(hid);
                              ipe = this.hierarchy.getElement(hid);
                              if (ipe != null && ipe.getKind().equals(IProgramElement.Kind.CODE)) {
                                 if (debug) {
                                    System.err.println("  source handle: it was code node, removing that as well... code=" + ipe + " parent=" + ipe.getParent());
                                 }

                                 this.removeSingleNode(ipe);
                              }
                           }

                           if (debug) {
                              this.dumprelsStderr("after processing 'affectedby'");
                           }

                           if (didsomething) {
                              sourcesToRemove.clear();
                              if (debug) {
                                 this.dumprelsStderr("before processing 'affects'");
                              }

                              sourcehandlesSet = this.mapper.getEntries();
                              i$ = sourcehandlesSet.iterator();

                              label177:
                              while(true) {
                                 while(true) {
                                    List relationships;
                                    label166:
                                    do {
                                       if (!i$.hasNext()) {
                                          i$ = sourcesToRemove.iterator();

                                          while(i$.hasNext()) {
                                             hid = (String)i$.next();
                                             this.mapper.removeAll(hid);
                                             ipe = this.hierarchy.getElement(hid);
                                             if (ipe != null && ipe.getKind().equals(IProgramElement.Kind.CODE)) {
                                                if (debug) {
                                                   System.err.println("  source handle: it was code node, removing that as well... code=" + ipe + " parent=" + ipe.getParent());
                                                }

                                                this.removeSingleNode(ipe);
                                             }
                                          }

                                          if (debug) {
                                             this.dumprelsStderr("after processing 'affects'");
                                          }
                                          break label177;
                                       }

                                       hid = (String)i$.next();
                                       relationshipsToRemove.clear();
                                       relationships = this.mapper.get(hid);
                                       Iterator i$ = relationships.iterator();

                                       while(true) {
                                          while(true) {
                                             ArrayList targetsToRemove;
                                             Iterator i$;
                                             String togo;
                                             IRelationship rel;
                                             List targets;
                                             label150:
                                             do {
                                                do {
                                                   do {
                                                      if (!i$.hasNext()) {
                                                         continue label166;
                                                      }

                                                      rel = (IRelationship)i$.next();
                                                   } while(rel.getKind() == IRelationship.Kind.USES_POINTCUT);
                                                } while(!rel.isAffects());

                                                targets = rel.getTargets();
                                                targetsToRemove = new ArrayList();
                                                i$ = targets.iterator();

                                                while(true) {
                                                   IProgramElement existingTarget;
                                                   do {
                                                      do {
                                                         if (!i$.hasNext()) {
                                                            continue label150;
                                                         }

                                                         togo = (String)i$.next();
                                                      } while(this.isPhantomHandle(hid) && !this.getTypeNameFromHandle(hid, handleToTypenameCache).equals(typename));

                                                      existingTarget = this.hierarchy.getElement(togo);
                                                   } while(existingTarget != null && !this.sameType(togo, existingTarget, typeNode));

                                                   targetsToRemove.add(togo);
                                                }
                                             } while(targetsToRemove.size() == 0);

                                             if (targetsToRemove.size() == targets.size()) {
                                                relationshipsToRemove.add(rel);
                                             } else {
                                                i$ = targetsToRemove.iterator();

                                                while(i$.hasNext()) {
                                                   togo = (String)i$.next();
                                                   targets.remove(togo);
                                                }
                                             }
                                          }
                                       }
                                    } while(relationshipsToRemove.size() <= 0);

                                    if (relationshipsToRemove.size() == relationships.size()) {
                                       sourcesToRemove.add(hid);
                                    } else {
                                       for(int i = 0; i < relationshipsToRemove.size(); ++i) {
                                          relationships.remove(relationshipsToRemove.get(i));
                                       }
                                    }
                                 }
                              }
                           }

                           if (debug) {
                              System.err.println("<<removeRelationshipsTargettingThisFile");
                           }

                           return;
                        }

                        hid = (String)i$.next();
                     } while(this.isPhantomHandle(hid) && !this.getTypeNameFromHandle(hid, handleToTypenameCache).equals(typename));

                     ipe = this.hierarchy.getElement(hid);
                  } while(ipe != null && !this.sameType(hid, ipe, typeNode));

                  relationshipsToRemove.clear();
                  relationships = this.mapper.get(hid);
                  Iterator i$ = relationships.iterator();

                  while(i$.hasNext()) {
                     IRelationship relationship = (IRelationship)i$.next();
                     if (relationship.getKind() != IRelationship.Kind.USES_POINTCUT && !relationship.isAffects()) {
                        relationshipsToRemove.add(relationship);
                     }
                  }
               } while(relationshipsToRemove.size() <= 0);

               didsomething = true;
               if (relationshipsToRemove.size() == relationships.size()) {
                  sourcesToRemove.add(hid);
               } else {
                  for(int i = 0; i < relationshipsToRemove.size(); ++i) {
                     relationships.remove(relationshipsToRemove.get(i));
                  }
               }
            }
         }
      }
   }

   private boolean sameType(String hid, IProgramElement target, IProgramElement type) {
      IProgramElement containingType = target;
      if (target == null) {
         throw new RuntimeException("target can't be null!");
      } else if (type == null) {
         throw new RuntimeException("type can't be null!");
      } else if (!target.getKind().isSourceFile() && !target.getKind().isFile()) {
         try {
            while(!containingType.getKind().isType()) {
               containingType = containingType.getParent();
            }
         } catch (Throwable var6) {
            throw new RuntimeException("Exception whilst walking up from target " + target.toLabelString() + " kind=(" + target.getKind() + ") hid=(" + target.getHandleIdentifier() + ")", var6);
         }

         return type.equals(containingType);
      } else if (target.getSourceLocation() == null) {
         return false;
      } else if (type.getSourceLocation() == null) {
         return false;
      } else if (target.getSourceLocation().getSourceFile() == null) {
         return false;
      } else {
         return type.getSourceLocation().getSourceFile() == null ? false : target.getSourceLocation().getSourceFile().equals(type.getSourceLocation().getSourceFile());
      }
   }

   private boolean isPhantomHandle(String handle) {
      int phantomMarker = handle.indexOf(HandleProviderDelimiter.PHANTOM.getDelimiter());
      return phantomMarker != -1 && handle.charAt(phantomMarker - 1) == HandleProviderDelimiter.PACKAGEFRAGMENTROOT.getDelimiter();
   }

   private void repairRelationships(Writer fw) {
      try {
         if (dumpDeltaProcessing) {
            fw.write("Repairing relationships map:\n");
         }

         Set sourcesToRemove = new HashSet();
         Set nonExistingHandles = new HashSet();
         Set keyset = this.mapper.getEntries();
         Iterator srciter = keyset.iterator();

         while(true) {
            String hid;
            IProgramElement existingElement;
            label134:
            while(srciter.hasNext()) {
               hid = (String)srciter.next();
               if (nonExistingHandles.contains(hid)) {
                  sourcesToRemove.add(hid);
               } else if (!this.isPhantomHandle(hid)) {
                  existingElement = this.hierarchy.getElement(hid);
                  if (dumpDeltaProcessing) {
                     fw.write("Looking for handle [" + hid + "] in model, found: " + existingElement + "\n");
                  }

                  if (existingElement == null) {
                     sourcesToRemove.add(hid);
                     nonExistingHandles.add(hid);
                  } else {
                     List relationships = this.mapper.get(hid);
                     List relationshipsToRemove = new ArrayList();
                     Iterator reliter = relationships.iterator();

                     while(true) {
                        while(true) {
                           IRelationship rel;
                           List targets;
                           ArrayList targetsToRemove;
                           Iterator i$;
                           String targethid;
                           do {
                              if (!reliter.hasNext()) {
                                 if (relationshipsToRemove.size() <= 0) {
                                    continue label134;
                                 }

                                 if (relationshipsToRemove.size() == relationships.size()) {
                                    sourcesToRemove.add(hid);
                                    continue label134;
                                 }

                                 for(int i = 0; i < relationshipsToRemove.size(); ++i) {
                                    rel = (IRelationship)relationshipsToRemove.get(i);
                                    verifyAssumption(this.mapper.remove(hid, rel), "Failed to remove relationship " + rel.getName() + " for shid " + hid);
                                 }

                                 List rels = this.mapper.get(hid);
                                 if (rels == null || rels.size() == 0) {
                                    sourcesToRemove.add(hid);
                                 }
                                 continue label134;
                              }

                              rel = (IRelationship)reliter.next();
                              targets = rel.getTargets();
                              targetsToRemove = new ArrayList();
                              i$ = targets.iterator();

                              while(i$.hasNext()) {
                                 targethid = (String)i$.next();
                                 if (nonExistingHandles.contains(targethid)) {
                                    if (dumpDeltaProcessing) {
                                       fw.write("Target handle [" + targethid + "] for srchid[" + hid + "]rel[" + rel.getName() + "] does not exist\n");
                                    }

                                    targetsToRemove.add(targethid);
                                 } else if (!this.isPhantomHandle(targethid)) {
                                    IProgramElement existingTarget = this.hierarchy.getElement(targethid);
                                    if (existingTarget == null) {
                                       if (dumpDeltaProcessing) {
                                          fw.write("Target handle [" + targethid + "] for srchid[" + hid + "]rel[" + rel.getName() + "] does not exist\n");
                                       }

                                       targetsToRemove.add(targethid);
                                       nonExistingHandles.add(targethid);
                                    }
                                 }
                              }
                           } while(targetsToRemove.size() == 0);

                           if (targetsToRemove.size() == targets.size()) {
                              if (dumpDeltaProcessing) {
                                 fw.write("No targets remain for srchid[" + hid + "] rel[" + rel.getName() + "]: removing it\n");
                              }

                              relationshipsToRemove.add(rel);
                           } else {
                              i$ = targetsToRemove.iterator();

                              while(i$.hasNext()) {
                                 targethid = (String)i$.next();
                                 targets.remove(targethid);
                              }

                              if (targets.size() == 0) {
                                 if (dumpDeltaProcessing) {
                                    fw.write("No targets remain for srchid[" + hid + "] rel[" + rel.getName() + "]: removing it\n");
                                 }

                                 relationshipsToRemove.add(rel);
                              }
                           }
                        }
                     }
                  }
               }
            }

            srciter = sourcesToRemove.iterator();

            while(srciter.hasNext()) {
               hid = (String)srciter.next();
               this.mapper.removeAll(hid);
               existingElement = this.hierarchy.getElement(hid);
               if (existingElement != null && existingElement.getKind().equals(IProgramElement.Kind.CODE)) {
                  this.removeSingleNode(existingElement);
               }
            }
            break;
         }
      } catch (IOException var17) {
         System.err.println("Failed to repair relationships:");
         var17.printStackTrace();
      }

   }

   private void removeSingleNode(IProgramElement progElem) {
      if (progElem == null) {
         throw new IllegalStateException("AsmManager.removeNode(): programElement unexpectedly null");
      } else {
         boolean deleteOK = false;
         IProgramElement parent = progElem.getParent();
         List kids = parent.getChildren();
         int i = 0;

         for(int max = kids.size(); i < max; ++i) {
            if (((IProgramElement)kids.get(i)).equals(progElem)) {
               kids.remove(i);
               deleteOK = true;
               break;
            }
         }

         if (!deleteOK) {
            System.err.println("unexpectedly failed to delete node from model.  hid=" + progElem.getHandleIdentifier());
         }

      }
   }

   private void removeNode(IProgramElement progElem) {
      try {
         if (progElem == null) {
            throw new IllegalStateException("AsmManager.removeNode(): programElement unexpectedly null");
         }

         IProgramElement parent = progElem.getParent();
         List kids = parent.getChildren();

         for(int i = 0; i < kids.size(); ++i) {
            if (((IProgramElement)kids.get(i)).equals(progElem)) {
               kids.remove(i);
               break;
            }
         }

         if (parent.getChildren().size() == 0 && parent.getParent() != null && (parent.getKind().equals(IProgramElement.Kind.CODE) || parent.getKind().equals(IProgramElement.Kind.PACKAGE))) {
            this.removeNode(parent);
         }
      } catch (NullPointerException var5) {
         var5.printStackTrace();
      }

   }

   public static void verifyAssumption(boolean b, String info) {
      if (!b) {
         System.err.println("=========== ASSERTION IS NOT TRUE =========v");
         System.err.println(info);
         Thread.dumpStack();
         System.err.println("=========== ASSERTION IS NOT TRUE =========^");
         throw new RuntimeException("Assertion is false");
      }
   }

   public static void verifyAssumption(boolean b) {
      if (!b) {
         Thread.dumpStack();
         throw new RuntimeException("Assertion is false");
      }
   }

   public ModelInfo summarizeModel() {
      return new ModelInfo(this.getHierarchy(), this.getRelationshipMap());
   }

   public static void setCompletingTypeBindings(boolean b) {
      completingTypeBindings = b;
   }

   public static boolean isCompletingTypeBindings() {
      return completingTypeBindings;
   }

   public void resetDeltaProcessing() {
      this.lastBuildChanges.clear();
      this.aspectsWeavingInLastBuild.clear();
   }

   public Set getModelChangesOnLastBuild() {
      return this.lastBuildChanges;
   }

   public Set getAspectsWeavingFilesOnLastBuild() {
      return this.aspectsWeavingInLastBuild;
   }

   public void addAspectInEffectThisBuild(File f) {
      this.aspectsWeavingInLastBuild.add(f);
   }

   public static void setLastActiveStructureModel(AsmManager structureModel) {
      if (recordingLastActiveStructureModel) {
         lastActiveStructureModel = structureModel;
      }

   }

   public String getHandleElementForInpath(String binaryPath) {
      return (String)this.inpathMap.get(new File(binaryPath));
   }

   public static class ModelInfo {
      private final Hashtable nodeTypeCount;
      private final Properties extraProperties;

      private ModelInfo(IHierarchy hierarchy, IRelationshipMap relationshipMap) {
         this.nodeTypeCount = new Hashtable();
         this.extraProperties = new Properties();
         IProgramElement ipe = hierarchy.getRoot();
         this.walkModel(ipe);
         this.recordStat("FileMapSize", (new Integer(hierarchy.getFileMapEntrySet().size())).toString());
         this.recordStat("RelationshipMapSize", (new Integer(relationshipMap.getEntries().size())).toString());
      }

      private void walkModel(IProgramElement ipe) {
         this.countNode(ipe);
         Iterator i$ = ipe.getChildren().iterator();

         while(i$.hasNext()) {
            IProgramElement child = (IProgramElement)i$.next();
            this.walkModel(child);
         }

      }

      private void countNode(IProgramElement ipe) {
         String node = ipe.getKind().toString();
         Integer ctr = (Integer)this.nodeTypeCount.get(node);
         if (ctr == null) {
            this.nodeTypeCount.put(node, new Integer(1));
         } else {
            ctr = new Integer(ctr + 1);
            this.nodeTypeCount.put(node, ctr);
         }

      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("Model node summary:\n");
         Enumeration nodeKeys = this.nodeTypeCount.keys();

         while(nodeKeys.hasMoreElements()) {
            String key = (String)nodeKeys.nextElement();
            Integer ct = (Integer)this.nodeTypeCount.get(key);
            sb.append(key + "=" + ct + "\n");
         }

         sb.append("Model stats:\n");
         Enumeration ks = this.extraProperties.keys();

         while(ks.hasMoreElements()) {
            String k = (String)ks.nextElement();
            String v = this.extraProperties.getProperty(k);
            sb.append(k + "=" + v + "\n");
         }

         return sb.toString();
      }

      public Properties getProperties() {
         Properties p = new Properties();
         Iterator i$ = this.nodeTypeCount.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            p.setProperty((String)entry.getKey(), ((Integer)entry.getValue()).toString());
         }

         p.putAll(this.extraProperties);
         return p;
      }

      public void recordStat(String string, String string2) {
         this.extraProperties.setProperty(string, string2);
      }

      // $FF: synthetic method
      ModelInfo(IHierarchy x0, IRelationshipMap x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class CanonicalFilePathMap {
      private static final int MAX_SIZE = 4000;
      private final Map pathMap;

      private CanonicalFilePathMap() {
         this.pathMap = new HashMap(20);
      }

      public String get(File f) {
         String ret = (String)this.pathMap.get(f.getPath());
         if (ret == null) {
            try {
               ret = f.getCanonicalPath();
            } catch (IOException var4) {
               ret = f.getPath();
            }

            this.pathMap.put(f.getPath(), ret);
            if (this.pathMap.size() > 4000) {
               this.pathMap.clear();
            }
         }

         return ret;
      }

      // $FF: synthetic method
      CanonicalFilePathMap(Object x0) {
         this();
      }
   }
}
