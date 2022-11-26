package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IHierarchy;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AspectJElementHierarchy implements IHierarchy {
   private static final long serialVersionUID = 6462734311117048620L;
   private transient AsmManager asm;
   protected IProgramElement root = null;
   protected String configFile = null;
   private Map fileMap = null;
   private Map handleMap = new HashMap();
   private Map typeMap = null;

   public AspectJElementHierarchy(AsmManager asm) {
      this.asm = asm;
   }

   public IProgramElement getElement(String handle) {
      return this.findElementForHandleOrCreate(handle, false);
   }

   public void setAsmManager(AsmManager asm) {
      this.asm = asm;
   }

   public IProgramElement getRoot() {
      return this.root;
   }

   public String toSummaryString() {
      StringBuilder s = new StringBuilder();
      s.append("FileMap has " + this.fileMap.size() + " entries\n");
      s.append("HandleMap has " + this.handleMap.size() + " entries\n");
      s.append("TypeMap has " + this.handleMap.size() + " entries\n");
      s.append("FileMap:\n");
      Iterator i$ = this.fileMap.entrySet().iterator();

      Map.Entry handleMapEntry;
      while(i$.hasNext()) {
         handleMapEntry = (Map.Entry)i$.next();
         s.append(handleMapEntry).append("\n");
      }

      s.append("TypeMap:\n");
      i$ = this.typeMap.entrySet().iterator();

      while(i$.hasNext()) {
         handleMapEntry = (Map.Entry)i$.next();
         s.append(handleMapEntry).append("\n");
      }

      s.append("HandleMap:\n");
      i$ = this.handleMap.entrySet().iterator();

      while(i$.hasNext()) {
         handleMapEntry = (Map.Entry)i$.next();
         s.append(handleMapEntry).append("\n");
      }

      return s.toString();
   }

   public void setRoot(IProgramElement root) {
      this.root = root;
      this.handleMap = new HashMap();
      this.typeMap = new HashMap();
   }

   public void addToFileMap(String key, IProgramElement value) {
      this.fileMap.put(key, value);
   }

   public boolean removeFromFileMap(String canonicalFilePath) {
      return this.fileMap.remove(canonicalFilePath) != null;
   }

   public void setFileMap(HashMap fileMap) {
      this.fileMap = fileMap;
   }

   public Object findInFileMap(Object key) {
      return this.fileMap.get(key);
   }

   public Set getFileMapEntrySet() {
      return this.fileMap.entrySet();
   }

   public boolean isValid() {
      return this.root != null && this.fileMap != null;
   }

   public IProgramElement findElementForSignature(IProgramElement parent, IProgramElement.Kind kind, String signature) {
      Iterator i$ = parent.getChildren().iterator();

      IProgramElement childSearch;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         IProgramElement node = (IProgramElement)i$.next();
         if (node.getKind() == kind && signature.equals(node.toSignatureString())) {
            return node;
         }

         childSearch = this.findElementForSignature(node, kind, signature);
      } while(childSearch == null);

      return childSearch;
   }

   public IProgramElement findElementForLabel(IProgramElement parent, IProgramElement.Kind kind, String label) {
      Iterator i$ = parent.getChildren().iterator();

      IProgramElement childSearch;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         IProgramElement node = (IProgramElement)i$.next();
         if (node.getKind() == kind && label.equals(node.toLabelString())) {
            return node;
         }

         childSearch = this.findElementForLabel(node, kind, label);
      } while(childSearch == null);

      return childSearch;
   }

   public IProgramElement findElementForType(String packageName, String typeName) {
      synchronized(this) {
         StringBuilder keyb = packageName == null ? new StringBuilder() : new StringBuilder(packageName);
         keyb.append(".").append(typeName);
         String key = keyb.toString();
         IProgramElement cachedValue = (IProgramElement)this.typeMap.get(key);
         if (cachedValue != null) {
            return cachedValue;
         } else {
            List packageNodes = this.findMatchingPackages(packageName);
            Iterator i$ = packageNodes.iterator();

            while(i$.hasNext()) {
               IProgramElement pkg = (IProgramElement)i$.next();
               Iterator i$ = pkg.getChildren().iterator();

               while(i$.hasNext()) {
                  IProgramElement fileNode = (IProgramElement)i$.next();
                  IProgramElement cNode = this.findClassInNodes(fileNode.getChildren(), typeName, typeName);
                  if (cNode != null) {
                     this.typeMap.put(key, cNode);
                     return cNode;
                  }
               }
            }

            return null;
         }
      }
   }

   public List findMatchingPackages(String packagename) {
      List children = this.root.getChildren();
      if (children.size() == 0) {
         return Collections.emptyList();
      } else if (((IProgramElement)children.get(0)).getKind() == IProgramElement.Kind.SOURCE_FOLDER) {
         String searchPackageName = packagename == null ? "" : packagename;
         List matchingPackageNodes = new ArrayList();
         Iterator i$ = children.iterator();

         while(i$.hasNext()) {
            IProgramElement sourceFolder = (IProgramElement)i$.next();
            List possiblePackageNodes = sourceFolder.getChildren();
            Iterator i$ = possiblePackageNodes.iterator();

            while(i$.hasNext()) {
               IProgramElement possiblePackageNode = (IProgramElement)i$.next();
               if (possiblePackageNode.getKind() == IProgramElement.Kind.PACKAGE && possiblePackageNode.getName().equals(searchPackageName)) {
                  matchingPackageNodes.add(possiblePackageNode);
               }
            }
         }

         return matchingPackageNodes;
      } else {
         ArrayList result;
         if (packagename == null) {
            result = new ArrayList();
            result.add(this.root);
            return result;
         } else {
            result = new ArrayList();
            Iterator i$ = children.iterator();

            while(true) {
               while(true) {
                  IProgramElement possiblePackage;
                  do {
                     do {
                        if (!i$.hasNext()) {
                           if (result.isEmpty()) {
                              return Collections.emptyList();
                           }

                           return result;
                        }

                        possiblePackage = (IProgramElement)i$.next();
                        if (possiblePackage.getKind() == IProgramElement.Kind.PACKAGE && possiblePackage.getName().equals(packagename)) {
                           result.add(possiblePackage);
                        }
                     } while(possiblePackage.getKind() != IProgramElement.Kind.SOURCE_FOLDER);
                  } while(!possiblePackage.getName().equals("binaries"));

                  Iterator i$ = possiblePackage.getChildren().iterator();

                  while(i$.hasNext()) {
                     IProgramElement possiblePackage2 = (IProgramElement)i$.next();
                     if (possiblePackage2.getKind() == IProgramElement.Kind.PACKAGE && possiblePackage2.getName().equals(packagename)) {
                        result.add(possiblePackage2);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private IProgramElement findClassInNodes(Collection nodes, String name, String typeName) {
      int dollar = name.indexOf(36);
      String baseName;
      String innerName;
      if (dollar == -1) {
         baseName = name;
         innerName = null;
      } else {
         baseName = name.substring(0, dollar);
         innerName = name.substring(dollar + 1);
      }

      Iterator i$ = nodes.iterator();

      while(i$.hasNext()) {
         IProgramElement classNode = (IProgramElement)i$.next();
         if (!classNode.getKind().isType()) {
            List kids = classNode.getChildren();
            if (kids != null && !kids.isEmpty()) {
               IProgramElement node = this.findClassInNodes(kids, name, typeName);
               if (node != null) {
                  return node;
               }
            }
         } else {
            if (baseName.equals(classNode.getName())) {
               if (innerName == null) {
                  return classNode;
               }

               return this.findClassInNodes(classNode.getChildren(), innerName, typeName);
            }

            if (name.equals(classNode.getName())) {
               return classNode;
            }

            if (typeName.equals(classNode.getBytecodeSignature())) {
               return classNode;
            }

            if (classNode.getChildren() != null && !classNode.getChildren().isEmpty()) {
               IProgramElement node = this.findClassInNodes(classNode.getChildren(), name, typeName);
               if (node != null) {
                  return node;
               }
            }
         }
      }

      return null;
   }

   public IProgramElement findElementForSourceFile(String sourceFile) {
      try {
         if (this.isValid() && sourceFile != null) {
            String correctedPath = this.asm.getCanonicalFilePath(new File(sourceFile));
            IProgramElement node = (IProgramElement)this.findInFileMap(correctedPath);
            return node != null ? node : this.createFileStructureNode(correctedPath);
         } else {
            return IHierarchy.NO_STRUCTURE;
         }
      } catch (Exception var4) {
         return IHierarchy.NO_STRUCTURE;
      }
   }

   public IProgramElement findElementForSourceLine(ISourceLocation location) {
      try {
         return this.findElementForSourceLine(this.asm.getCanonicalFilePath(location.getSourceFile()), location.getLine());
      } catch (Exception var3) {
         return null;
      }
   }

   public IProgramElement findElementForSourceLine(String sourceFilePath, int lineNumber) {
      String canonicalSFP = this.asm.getCanonicalFilePath(new File(sourceFilePath));
      IProgramElement node = this.findNodeForSourceFile(this.root, canonicalSFP);
      if (node == null) {
         return this.createFileStructureNode(sourceFilePath);
      } else {
         IProgramElement closernode = this.findCloserMatchForLineNumber(node, lineNumber);
         return closernode == null ? node : closernode;
      }
   }

   public IProgramElement findNodeForSourceFile(IProgramElement node, String sourcefilePath) {
      if ((!node.getKind().isSourceFile() || node.getName().equals("<root>")) && !node.getKind().isFile()) {
         Iterator i$ = node.getChildren().iterator();

         IProgramElement foundit;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            IProgramElement child = (IProgramElement)i$.next();
            foundit = this.findNodeForSourceFile(child, sourcefilePath);
         } while(foundit == null);

         return foundit;
      } else {
         ISourceLocation nodeLoc = node.getSourceLocation();
         return nodeLoc != null && this.asm.getCanonicalFilePath(nodeLoc.getSourceFile()).equals(sourcefilePath) ? node : null;
      }
   }

   public IProgramElement findElementForOffSet(String sourceFilePath, int lineNumber, int offSet) {
      String canonicalSFP = this.asm.getCanonicalFilePath(new File(sourceFilePath));
      IProgramElement node = this.findNodeForSourceLineHelper(this.root, canonicalSFP, lineNumber, offSet);
      return node != null ? node : this.createFileStructureNode(sourceFilePath);
   }

   private IProgramElement createFileStructureNode(String sourceFilePath) {
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
      IProgramElement fileNode = new ProgramElement(this.asm, fileName, IProgramElement.Kind.FILE_JAVA, new SourceLocation(new File(sourceFilePath), 1, 1), 0, (String)null, (List)null);
      fileNode.addChild(NO_STRUCTURE);
      return fileNode;
   }

   public IProgramElement findCloserMatchForLineNumber(IProgramElement node, int lineno) {
      if (node != null && node.getChildren() != null) {
         Iterator i$ = node.getChildren().iterator();

         while(i$.hasNext()) {
            IProgramElement child = (IProgramElement)i$.next();
            ISourceLocation childLoc = child.getSourceLocation();
            if (childLoc != null) {
               IProgramElement evenCloserMatch;
               if (childLoc.getLine() <= lineno && childLoc.getEndLine() >= lineno) {
                  evenCloserMatch = this.findCloserMatchForLineNumber(child, lineno);
                  if (evenCloserMatch == null) {
                     return child;
                  }

                  return evenCloserMatch;
               }

               if (child.getKind().isType()) {
                  evenCloserMatch = this.findCloserMatchForLineNumber(child, lineno);
                  if (evenCloserMatch != null) {
                     return evenCloserMatch;
                  }
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private IProgramElement findNodeForSourceLineHelper(IProgramElement node, String sourceFilePath, int lineno, int offset) {
      if (this.matches(node, sourceFilePath, lineno, offset) && !this.hasMoreSpecificChild(node, sourceFilePath, lineno, offset)) {
         return node;
      } else {
         if (node != null) {
            Iterator i$ = node.getChildren().iterator();

            while(i$.hasNext()) {
               IProgramElement child = (IProgramElement)i$.next();
               IProgramElement foundNode = this.findNodeForSourceLineHelper(child, sourceFilePath, lineno, offset);
               if (foundNode != null) {
                  return foundNode;
               }
            }
         }

         return null;
      }
   }

   private boolean matches(IProgramElement node, String sourceFilePath, int lineNumber, int offSet) {
      ISourceLocation nodeSourceLocation = node != null ? node.getSourceLocation() : null;
      return node != null && nodeSourceLocation != null && nodeSourceLocation.getSourceFile().getAbsolutePath().equals(sourceFilePath) && (offSet != -1 && nodeSourceLocation.getOffset() == offSet || offSet == -1) && (nodeSourceLocation.getLine() <= lineNumber && nodeSourceLocation.getEndLine() >= lineNumber || lineNumber <= 1 && node.getKind().isSourceFile());
   }

   private boolean hasMoreSpecificChild(IProgramElement node, String sourceFilePath, int lineNumber, int offSet) {
      Iterator i$ = node.getChildren().iterator();

      IProgramElement child;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         child = (IProgramElement)i$.next();
      } while(!this.matches(child, sourceFilePath, lineNumber, offSet));

      return true;
   }

   public String getConfigFile() {
      return this.configFile;
   }

   public void setConfigFile(String configFile) {
      this.configFile = configFile;
   }

   public IProgramElement findElementForHandle(String handle) {
      return this.findElementForHandleOrCreate(handle, true);
   }

   public IProgramElement findElementForHandleOrCreate(String handle, boolean create) {
      IProgramElement ipe = null;
      synchronized(this) {
         ipe = (IProgramElement)this.handleMap.get(handle);
         if (ipe != null) {
            return ipe;
         } else {
            ipe = this.findElementForHandle(this.root, handle);
            if (ipe == null && create) {
               ipe = this.createFileStructureNode(this.getFilename(handle));
            }

            if (ipe != null) {
               this.cache(handle, ipe);
            }

            return ipe;
         }
      }
   }

   private IProgramElement findElementForHandle(IProgramElement parent, String handle) {
      Iterator i$ = parent.getChildren().iterator();

      while(i$.hasNext()) {
         IProgramElement node = (IProgramElement)i$.next();
         String nodeHid = node.getHandleIdentifier();
         if (handle.equals(nodeHid)) {
            return node;
         }

         if (handle.startsWith(nodeHid)) {
            IProgramElement childSearch = this.findElementForHandle(node, handle);
            if (childSearch != null) {
               return childSearch;
            }
         }
      }

      return null;
   }

   protected void cache(String handle, IProgramElement pe) {
      if (!AsmManager.isCompletingTypeBindings()) {
         this.handleMap.put(handle, pe);
      }

   }

   public void flushTypeMap() {
      this.typeMap.clear();
   }

   public void flushHandleMap() {
      this.handleMap.clear();
   }

   public void flushFileMap() {
      this.fileMap.clear();
   }

   public void forget(IProgramElement compilationUnitNode, IProgramElement typeNode) {
      String k = null;
      synchronized(this) {
         Iterator i$ = this.typeMap.entrySet().iterator();

         while(true) {
            if (i$.hasNext()) {
               Map.Entry typeMapEntry = (Map.Entry)i$.next();
               if (typeMapEntry.getValue() != typeNode) {
                  continue;
               }

               k = (String)typeMapEntry.getKey();
            }

            if (k != null) {
               this.typeMap.remove(k);
            }
            break;
         }
      }

      if (compilationUnitNode != null) {
         k = null;
         Iterator i$ = this.fileMap.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            if (entry.getValue() == compilationUnitNode) {
               k = (String)entry.getKey();
               break;
            }
         }

         if (k != null) {
            this.fileMap.remove(k);
         }
      }

   }

   public void updateHandleMap(Set deletedFiles) {
      List forRemoval = new ArrayList();
      Set k = null;
      String filePath;
      synchronized(this) {
         k = this.handleMap.keySet();
         Iterator i$ = k.iterator();

         label79:
         while(true) {
            IProgramElement ipe;
            do {
               if (!i$.hasNext()) {
                  i$ = forRemoval.iterator();

                  while(i$.hasNext()) {
                     filePath = (String)i$.next();
                     this.handleMap.remove(filePath);
                  }

                  forRemoval.clear();
                  k = this.typeMap.keySet();
                  i$ = k.iterator();

                  while(i$.hasNext()) {
                     filePath = (String)i$.next();
                     ipe = (IProgramElement)this.typeMap.get(filePath);
                     if (deletedFiles.contains(this.getCanonicalFilePath(ipe))) {
                        forRemoval.add(filePath);
                     }
                  }

                  i$ = forRemoval.iterator();

                  while(i$.hasNext()) {
                     filePath = (String)i$.next();
                     this.typeMap.remove(filePath);
                  }

                  forRemoval.clear();
                  break label79;
               }

               filePath = (String)i$.next();
               ipe = (IProgramElement)this.handleMap.get(filePath);
               if (ipe == null) {
                  System.err.println("handleMap expectation not met, where is the IPE for " + filePath);
               }
            } while(ipe != null && !deletedFiles.contains(this.getCanonicalFilePath(ipe)));

            forRemoval.add(filePath);
         }
      }

      Iterator i$ = this.fileMap.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         filePath = (String)entry.getKey();
         if (deletedFiles.contains(this.getCanonicalFilePath((IProgramElement)entry.getValue()))) {
            forRemoval.add(filePath);
         }
      }

      i$ = forRemoval.iterator();

      while(i$.hasNext()) {
         String filePath = (String)i$.next();
         this.fileMap.remove(filePath);
      }

   }

   private String getFilename(String hid) {
      return this.asm.getHandleProvider().getFileForHandle(hid);
   }

   private String getCanonicalFilePath(IProgramElement ipe) {
      return ipe.getSourceLocation() != null ? this.asm.getCanonicalFilePath(ipe.getSourceLocation().getSourceFile()) : "";
   }
}
