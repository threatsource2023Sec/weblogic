package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Attribute;
import com.oracle.wls.shaded.org.apache.bcel.classfile.Field;
import com.oracle.wls.shaded.org.apache.bcel.classfile.Method;
import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.DLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.DSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.FLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.FSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.ICONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IfInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.IndexedInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionConstants;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionTargeter;
import com.oracle.wls.shaded.org.apache.bcel.generic.LLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.LSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.MethodGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.RET;
import com.oracle.wls.shaded.org.apache.bcel.generic.Select;
import com.oracle.wls.shaded.org.apache.bcel.generic.TargetLostException;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Constants;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.Pattern;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.XSLTC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class MethodGenerator extends MethodGen implements Constants {
   protected static final int INVALID_INDEX = -1;
   private static final String START_ELEMENT_SIG = "(Ljava/lang/String;)V";
   private static final String END_ELEMENT_SIG = "(Ljava/lang/String;)V";
   private InstructionList _mapTypeSub;
   private static final int DOM_INDEX = 1;
   private static final int ITERATOR_INDEX = 2;
   private static final int HANDLER_INDEX = 3;
   private static final int MAX_METHOD_SIZE = 65535;
   private static final int MAX_BRANCH_TARGET_OFFSET = 32767;
   private static final int MIN_BRANCH_TARGET_OFFSET = -32768;
   private static final int TARGET_METHOD_SIZE = 60000;
   private static final int MINIMUM_OUTLINEABLE_CHUNK_SIZE = 1000;
   private Instruction _iloadCurrent;
   private Instruction _istoreCurrent;
   private final Instruction _astoreHandler = new ASTORE(3);
   private final Instruction _aloadHandler = new ALOAD(3);
   private final Instruction _astoreIterator = new ASTORE(2);
   private final Instruction _aloadIterator = new ALOAD(2);
   private final Instruction _aloadDom = new ALOAD(1);
   private final Instruction _astoreDom = new ASTORE(1);
   private final Instruction _startElement;
   private final Instruction _endElement;
   private final Instruction _startDocument;
   private final Instruction _endDocument;
   private final Instruction _attribute;
   private final Instruction _uniqueAttribute;
   private final Instruction _namespace;
   private final Instruction _setStartNode;
   private final Instruction _reset;
   private final Instruction _nextNode;
   private SlotAllocator _slotAllocator;
   private boolean _allocatorInit = false;
   private LocalVariableRegistry _localVariableRegistry;
   private Hashtable _preCompiled = new Hashtable();
   private int m_totalChunks = 0;
   private int m_openChunks = 0;

   public MethodGenerator(int access_flags, com.oracle.wls.shaded.org.apache.bcel.generic.Type return_type, com.oracle.wls.shaded.org.apache.bcel.generic.Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cpg) {
      super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cpg);
      int startElement = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "startElement", "(Ljava/lang/String;)V");
      this._startElement = new INVOKEINTERFACE(startElement, 2);
      int endElement = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "endElement", "(Ljava/lang/String;)V");
      this._endElement = new INVOKEINTERFACE(endElement, 2);
      int attribute = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "addAttribute", "(Ljava/lang/String;Ljava/lang/String;)V");
      this._attribute = new INVOKEINTERFACE(attribute, 3);
      int uniqueAttribute = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "addUniqueAttribute", "(Ljava/lang/String;Ljava/lang/String;I)V");
      this._uniqueAttribute = new INVOKEINTERFACE(uniqueAttribute, 4);
      int namespace = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "namespaceAfterStartElement", "(Ljava/lang/String;Ljava/lang/String;)V");
      this._namespace = new INVOKEINTERFACE(namespace, 3);
      int index = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "startDocument", "()V");
      this._startDocument = new INVOKEINTERFACE(index, 1);
      index = cpg.addInterfaceMethodref(TRANSLET_OUTPUT_INTERFACE, "endDocument", "()V");
      this._endDocument = new INVOKEINTERFACE(index, 1);
      index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "setStartNode", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
      this._setStartNode = new INVOKEINTERFACE(index, 2);
      index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "reset", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
      this._reset = new INVOKEINTERFACE(index, 1);
      index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "next", "()I");
      this._nextNode = new INVOKEINTERFACE(index, 1);
      this._slotAllocator = new SlotAllocator();
      this._slotAllocator.initialize(this.getLocalVariableRegistry().getLocals(false));
      this._allocatorInit = true;
   }

   public LocalVariableGen addLocalVariable(String name, com.oracle.wls.shaded.org.apache.bcel.generic.Type type, InstructionHandle start, InstructionHandle end) {
      LocalVariableGen lvg;
      if (this._allocatorInit) {
         lvg = this.addLocalVariable2(name, type, start);
      } else {
         lvg = super.addLocalVariable(name, type, start, end);
         this.getLocalVariableRegistry().registerLocalVariable(lvg);
      }

      return lvg;
   }

   public LocalVariableGen addLocalVariable2(String name, com.oracle.wls.shaded.org.apache.bcel.generic.Type type, InstructionHandle start) {
      LocalVariableGen lvg = super.addLocalVariable(name, type, this._slotAllocator.allocateSlot(type), start, (InstructionHandle)null);
      this.getLocalVariableRegistry().registerLocalVariable(lvg);
      return lvg;
   }

   private LocalVariableRegistry getLocalVariableRegistry() {
      if (this._localVariableRegistry == null) {
         this._localVariableRegistry = new LocalVariableRegistry();
      }

      return this._localVariableRegistry;
   }

   boolean offsetInLocalVariableGenRange(LocalVariableGen lvg, int offset) {
      InstructionHandle lvgStart = lvg.getStart();
      InstructionHandle lvgEnd = lvg.getEnd();
      if (lvgStart == null) {
         lvgStart = this.getInstructionList().getStart();
      }

      if (lvgEnd == null) {
         lvgEnd = this.getInstructionList().getEnd();
      }

      return lvgStart.getPosition() <= offset && lvgEnd.getPosition() + lvgEnd.getInstruction().getLength() >= offset;
   }

   public void removeLocalVariable(LocalVariableGen lvg) {
      this._slotAllocator.releaseSlot(lvg);
      this.getLocalVariableRegistry().removeByNameTracking(lvg);
      super.removeLocalVariable(lvg);
   }

   public Instruction loadDOM() {
      return this._aloadDom;
   }

   public Instruction storeDOM() {
      return this._astoreDom;
   }

   public Instruction storeHandler() {
      return this._astoreHandler;
   }

   public Instruction loadHandler() {
      return this._aloadHandler;
   }

   public Instruction storeIterator() {
      return this._astoreIterator;
   }

   public Instruction loadIterator() {
      return this._aloadIterator;
   }

   public final Instruction setStartNode() {
      return this._setStartNode;
   }

   public final Instruction reset() {
      return this._reset;
   }

   public final Instruction nextNode() {
      return this._nextNode;
   }

   public final Instruction startElement() {
      return this._startElement;
   }

   public final Instruction endElement() {
      return this._endElement;
   }

   public final Instruction startDocument() {
      return this._startDocument;
   }

   public final Instruction endDocument() {
      return this._endDocument;
   }

   public final Instruction attribute() {
      return this._attribute;
   }

   public final Instruction uniqueAttribute() {
      return this._uniqueAttribute;
   }

   public final Instruction namespace() {
      return this._namespace;
   }

   public Instruction loadCurrentNode() {
      if (this._iloadCurrent == null) {
         int idx = this.getLocalIndex("current");
         if (idx > 0) {
            this._iloadCurrent = new ILOAD(idx);
         } else {
            this._iloadCurrent = new ICONST(0);
         }
      }

      return this._iloadCurrent;
   }

   public Instruction storeCurrentNode() {
      return this._istoreCurrent != null ? this._istoreCurrent : (this._istoreCurrent = new ISTORE(this.getLocalIndex("current")));
   }

   public Instruction loadContextNode() {
      return this.loadCurrentNode();
   }

   public Instruction storeContextNode() {
      return this.storeCurrentNode();
   }

   public int getLocalIndex(String name) {
      return this.getLocalVariable(name).getIndex();
   }

   public LocalVariableGen getLocalVariable(String name) {
      return this.getLocalVariableRegistry().lookUpByName(name);
   }

   public void setMaxLocals() {
      int maxLocals = super.getMaxLocals();
      LocalVariableGen[] localVars = super.getLocalVariables();
      if (localVars != null && localVars.length > maxLocals) {
         maxLocals = localVars.length;
      }

      if (maxLocals < 5) {
         maxLocals = 5;
      }

      super.setMaxLocals(maxLocals);
   }

   public void addInstructionList(Pattern pattern, InstructionList ilist) {
      this._preCompiled.put(pattern, ilist);
   }

   public InstructionList getInstructionList(Pattern pattern) {
      return (InstructionList)this._preCompiled.get(pattern);
   }

   private ArrayList getCandidateChunks(ClassGenerator classGen, int totalMethodSize) {
      Iterator instructions = this.getInstructionList().iterator();
      ArrayList candidateChunks = new ArrayList();
      ArrayList currLevelChunks = new ArrayList();
      Stack subChunkStack = new Stack();
      boolean openChunkAtCurrLevel = false;
      boolean firstInstruction = true;
      if (this.m_openChunks != 0) {
         String msg = (new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS")).toString();
         throw new InternalError(msg);
      } else {
         InstructionHandle currentHandle;
         do {
            currentHandle = instructions.hasNext() ? (InstructionHandle)instructions.next() : null;
            Instruction inst = currentHandle != null ? currentHandle.getInstruction() : null;
            if (firstInstruction) {
               openChunkAtCurrLevel = true;
               currLevelChunks.add(currentHandle);
               firstInstruction = false;
            }

            if (inst instanceof OutlineableChunkStart) {
               if (openChunkAtCurrLevel) {
                  subChunkStack.push(currLevelChunks);
                  currLevelChunks = new ArrayList();
               }

               openChunkAtCurrLevel = true;
               currLevelChunks.add(currentHandle);
            } else if (currentHandle == null || inst instanceof OutlineableChunkEnd) {
               ArrayList nestedSubChunks = null;
               if (!openChunkAtCurrLevel) {
                  nestedSubChunks = currLevelChunks;
                  currLevelChunks = (ArrayList)subChunkStack.pop();
               }

               InstructionHandle chunkStart = (InstructionHandle)currLevelChunks.get(currLevelChunks.size() - 1);
               int chunkEndPosition = currentHandle != null ? currentHandle.getPosition() : totalMethodSize;
               int chunkSize = chunkEndPosition - chunkStart.getPosition();
               if (chunkSize <= 60000) {
                  currLevelChunks.add(currentHandle);
               } else {
                  if (!openChunkAtCurrLevel) {
                     int childChunkCount = nestedSubChunks.size() / 2;
                     if (childChunkCount > 0) {
                        Chunk[] childChunks = new Chunk[childChunkCount];

                        for(int i = 0; i < childChunkCount; ++i) {
                           InstructionHandle start = (InstructionHandle)nestedSubChunks.get(i * 2);
                           InstructionHandle end = (InstructionHandle)nestedSubChunks.get(i * 2 + 1);
                           childChunks[i] = new Chunk(start, end);
                        }

                        ArrayList mergedChildChunks = this.mergeAdjacentChunks(childChunks);

                        for(int i = 0; i < mergedChildChunks.size(); ++i) {
                           Chunk mergedChunk = (Chunk)mergedChildChunks.get(i);
                           int mergedSize = mergedChunk.getChunkSize();
                           if (mergedSize >= 1000 && mergedSize <= 60000) {
                              candidateChunks.add(mergedChunk);
                           }
                        }
                     }
                  }

                  currLevelChunks.remove(currLevelChunks.size() - 1);
               }

               openChunkAtCurrLevel = (currLevelChunks.size() & 1) == 1;
            }
         } while(currentHandle != null);

         return candidateChunks;
      }
   }

   private ArrayList mergeAdjacentChunks(Chunk[] chunks) {
      int[] adjacencyRunStart = new int[chunks.length];
      int[] adjacencyRunLength = new int[chunks.length];
      boolean[] chunkWasMerged = new boolean[chunks.length];
      int maximumRunOfChunks = 0;
      int numAdjacentRuns = 0;
      ArrayList mergedChunks = new ArrayList();
      int startOfCurrentRun = 0;

      int numToMerge;
      int run;
      for(numToMerge = 1; numToMerge < chunks.length; ++numToMerge) {
         if (!chunks[numToMerge - 1].isAdjacentTo(chunks[numToMerge])) {
            run = numToMerge - startOfCurrentRun;
            if (maximumRunOfChunks < run) {
               maximumRunOfChunks = run;
            }

            if (run > 1) {
               adjacencyRunLength[numAdjacentRuns] = run;
               adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun;
               ++numAdjacentRuns;
            }

            startOfCurrentRun = numToMerge;
         }
      }

      if (chunks.length - startOfCurrentRun > 1) {
         numToMerge = chunks.length - startOfCurrentRun;
         if (maximumRunOfChunks < numToMerge) {
            maximumRunOfChunks = numToMerge;
         }

         adjacencyRunLength[numAdjacentRuns] = chunks.length - startOfCurrentRun;
         adjacencyRunStart[numAdjacentRuns] = startOfCurrentRun;
         ++numAdjacentRuns;
      }

      for(numToMerge = maximumRunOfChunks; numToMerge > 1; --numToMerge) {
         for(run = 0; run < numAdjacentRuns; ++run) {
            int runStart = adjacencyRunStart[run];
            int runEnd = runStart + adjacencyRunLength[run] - 1;
            boolean foundChunksToMerge = false;

            for(int mergeStart = runStart; mergeStart + numToMerge - 1 <= runEnd && !foundChunksToMerge; ++mergeStart) {
               int mergeEnd = mergeStart + numToMerge - 1;
               int mergeSize = 0;

               int trailingRunLength;
               for(trailingRunLength = mergeStart; trailingRunLength <= mergeEnd; ++trailingRunLength) {
                  mergeSize += chunks[trailingRunLength].getChunkSize();
               }

               if (mergeSize <= 60000) {
                  foundChunksToMerge = true;

                  for(trailingRunLength = mergeStart; trailingRunLength <= mergeEnd; ++trailingRunLength) {
                     chunkWasMerged[trailingRunLength] = true;
                  }

                  mergedChunks.add(new Chunk(chunks[mergeStart].getChunkStart(), chunks[mergeEnd].getChunkEnd()));
                  adjacencyRunLength[run] = adjacencyRunStart[run] - mergeStart;
                  trailingRunLength = runEnd - mergeEnd;
                  if (trailingRunLength >= 2) {
                     adjacencyRunStart[numAdjacentRuns] = mergeEnd + 1;
                     adjacencyRunLength[numAdjacentRuns] = trailingRunLength;
                     ++numAdjacentRuns;
                  }
               }
            }
         }
      }

      for(numToMerge = 0; numToMerge < chunks.length; ++numToMerge) {
         if (!chunkWasMerged[numToMerge]) {
            mergedChunks.add(chunks[numToMerge]);
         }
      }

      return mergedChunks;
   }

   public Method[] outlineChunks(ClassGenerator classGen, int originalMethodSize) {
      ArrayList methodsOutlined = new ArrayList();
      int currentMethodSize = originalMethodSize;
      int outlinedCount = 0;
      String originalMethodName = this.getName();
      if (originalMethodName.equals("<init>")) {
         originalMethodName = "$lt$init$gt$";
      } else if (originalMethodName.equals("<clinit>")) {
         originalMethodName = "$lt$clinit$gt$";
      }

      boolean moreMethodsOutlined;
      do {
         ArrayList candidateChunks = this.getCandidateChunks(classGen, currentMethodSize);
         Collections.sort(candidateChunks);
         moreMethodsOutlined = false;

         for(int i = candidateChunks.size() - 1; i >= 0 && currentMethodSize > 60000; --i) {
            Chunk chunkToOutline = (Chunk)candidateChunks.get(i);
            methodsOutlined.add(this.outline(chunkToOutline.getChunkStart(), chunkToOutline.getChunkEnd(), originalMethodName + "$outline$" + outlinedCount, classGen));
            ++outlinedCount;
            moreMethodsOutlined = true;
            InstructionList il = this.getInstructionList();
            InstructionHandle lastInst = il.getEnd();
            il.setPositions();
            currentMethodSize = lastInst.getPosition() + lastInst.getInstruction().getLength();
         }
      } while(moreMethodsOutlined && currentMethodSize > 60000);

      if (currentMethodSize > 65535) {
         String msg = (new ErrorMsg("OUTLINE_ERR_METHOD_TOO_BIG")).toString();
         throw new InternalError(msg);
      } else {
         Method[] methodsArr = new Method[methodsOutlined.size() + 1];
         methodsOutlined.toArray(methodsArr);
         methodsArr[methodsOutlined.size()] = this.getThisMethod();
         return methodsArr;
      }
   }

   private Method outline(InstructionHandle first, InstructionHandle last, String outlinedMethodName, ClassGenerator classGen) {
      if (this.getExceptionHandlers().length != 0) {
         String msg = (new ErrorMsg("OUTLINE_ERR_TRY_CATCH")).toString();
         throw new InternalError(msg);
      } else {
         int outlineChunkStartOffset = first.getPosition();
         int outlineChunkEndOffset = last.getPosition() + last.getInstruction().getLength();
         ConstantPoolGen cpg = this.getConstantPool();
         InstructionList newIL = new InstructionList();
         XSLTC xsltc = classGen.getParser().getXSLTC();
         String argTypeName = xsltc.getHelperClassName();
         com.oracle.wls.shaded.org.apache.bcel.generic.Type[] argTypes = new com.oracle.wls.shaded.org.apache.bcel.generic.Type[]{(new ObjectType(argTypeName)).toJCType()};
         String argName = "copyLocals";
         String[] argNames = new String[]{"copyLocals"};
         int methodAttributes = 18;
         boolean isStaticMethod = (this.getAccessFlags() & 8) != 0;
         if (isStaticMethod) {
            methodAttributes |= 8;
         }

         MethodGenerator outlinedMethodGen = new MethodGenerator(methodAttributes, com.oracle.wls.shaded.org.apache.bcel.generic.Type.VOID, argTypes, argNames, outlinedMethodName, this.getClassName(), newIL, cpg);
         ClassGenerator copyAreaCG = new ClassGenerator(argTypeName, "java.lang.Object", argTypeName + ".java", 49, (String[])null, classGen.getStylesheet()) {
            public boolean isExternal() {
               return true;
            }
         };
         ConstantPoolGen copyAreaCPG = copyAreaCG.getConstantPool();
         copyAreaCG.addEmptyConstructor(1);
         int copyAreaFieldCount = 0;
         InstructionHandle limit = last.getNext();
         InstructionList oldMethCopyInIL = new InstructionList();
         InstructionList oldMethCopyOutIL = new InstructionList();
         InstructionList newMethCopyInIL = new InstructionList();
         InstructionList newMethCopyOutIL = new InstructionList();
         InstructionHandle outlinedMethodCallSetup = oldMethCopyInIL.append((Instruction)(new NEW(cpg.addClass(argTypeName))));
         oldMethCopyInIL.append((Instruction)InstructionConstants.DUP);
         oldMethCopyInIL.append((Instruction)InstructionConstants.DUP);
         oldMethCopyInIL.append((Instruction)(new INVOKESPECIAL(cpg.addMethodref(argTypeName, "<init>", "()V"))));
         InstructionHandle outlinedMethodRef;
         if (isStaticMethod) {
            outlinedMethodRef = oldMethCopyOutIL.append((Instruction)(new INVOKESTATIC(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature()))));
         } else {
            oldMethCopyOutIL.append((Instruction)InstructionConstants.THIS);
            oldMethCopyOutIL.append((Instruction)InstructionConstants.SWAP);
            outlinedMethodRef = oldMethCopyOutIL.append((Instruction)(new INVOKEVIRTUAL(cpg.addMethodref(classGen.getClassName(), outlinedMethodName, outlinedMethodGen.getSignature()))));
         }

         boolean chunkStartTargetMappingsPending = false;
         InstructionHandle pendingTargetMappingHandle = null;
         InstructionHandle lastCopyHandle = null;
         HashMap targetMap = new HashMap();
         HashMap localVarMap = new HashMap();
         HashMap revisedLocalVarStart = new HashMap();
         HashMap revisedLocalVarEnd = new HashMap();

         InstructionHandle ih;
         Instruction i;
         LocalVariableGen oldLVG;
         LocalVariableGen newLVG;
         String varName;
         com.oracle.wls.shaded.org.apache.bcel.generic.Type varType;
         for(ih = first; ih != limit; ih = ih.getNext()) {
            Instruction inst = ih.getInstruction();
            if (inst instanceof MarkerInstruction) {
               if (ih.hasTargeters()) {
                  if (inst instanceof OutlineableChunkEnd) {
                     targetMap.put(ih, lastCopyHandle);
                  } else if (!chunkStartTargetMappingsPending) {
                     chunkStartTargetMappingsPending = true;
                     pendingTargetMappingHandle = ih;
                  }
               }
            } else {
               i = inst.copy();
               if (i instanceof BranchInstruction) {
                  lastCopyHandle = newIL.append((BranchInstruction)i);
               } else {
                  lastCopyHandle = newIL.append(i);
               }

               if (i instanceof LocalVariableInstruction || i instanceof RET) {
                  IndexedInstruction lvi = (IndexedInstruction)i;
                  int oldLocalVarIndex = lvi.getIndex();
                  oldLVG = this.getLocalVariableRegistry().lookupRegisteredLocalVariable(oldLocalVarIndex, ih.getPosition());
                  newLVG = (LocalVariableGen)localVarMap.get(oldLVG);
                  if (localVarMap.get(oldLVG) == null) {
                     boolean copyInLocalValue = this.offsetInLocalVariableGenRange(oldLVG, outlineChunkStartOffset != 0 ? outlineChunkStartOffset - 1 : 0);
                     boolean copyOutLocalValue = this.offsetInLocalVariableGenRange(oldLVG, outlineChunkEndOffset + 1);
                     if (copyInLocalValue || copyOutLocalValue) {
                        varName = oldLVG.getName();
                        varType = oldLVG.getType();
                        newLVG = outlinedMethodGen.addLocalVariable(varName, varType, (InstructionHandle)null, (InstructionHandle)null);
                        int newLocalVarIndex = newLVG.getIndex();
                        String varSignature = varType.getSignature();
                        localVarMap.put(oldLVG, newLVG);
                        ++copyAreaFieldCount;
                        String copyAreaFieldName = "field" + copyAreaFieldCount;
                        copyAreaCG.addField(new Field(1, copyAreaCPG.addUtf8(copyAreaFieldName), copyAreaCPG.addUtf8(varSignature), (Attribute[])null, copyAreaCPG.getConstantPool()));
                        int fieldRef = cpg.addFieldref(argTypeName, copyAreaFieldName, varSignature);
                        InstructionHandle copyOutStore;
                        if (copyInLocalValue) {
                           oldMethCopyInIL.append((Instruction)InstructionConstants.DUP);
                           copyOutStore = oldMethCopyInIL.append(loadLocal(oldLocalVarIndex, varType));
                           oldMethCopyInIL.append((Instruction)(new PUTFIELD(fieldRef)));
                           if (!copyOutLocalValue) {
                              revisedLocalVarEnd.put(oldLVG, copyOutStore);
                           }

                           newMethCopyInIL.append((Instruction)InstructionConstants.ALOAD_1);
                           newMethCopyInIL.append((Instruction)(new GETFIELD(fieldRef)));
                           newMethCopyInIL.append(storeLocal(newLocalVarIndex, varType));
                        }

                        if (copyOutLocalValue) {
                           newMethCopyOutIL.append((Instruction)InstructionConstants.ALOAD_1);
                           newMethCopyOutIL.append(loadLocal(newLocalVarIndex, varType));
                           newMethCopyOutIL.append((Instruction)(new PUTFIELD(fieldRef)));
                           oldMethCopyOutIL.append((Instruction)InstructionConstants.DUP);
                           oldMethCopyOutIL.append((Instruction)(new GETFIELD(fieldRef)));
                           copyOutStore = oldMethCopyOutIL.append(storeLocal(oldLocalVarIndex, varType));
                           if (!copyInLocalValue) {
                              revisedLocalVarStart.put(oldLVG, copyOutStore);
                           }
                        }
                     }
                  }
               }

               if (ih.hasTargeters()) {
                  targetMap.put(ih, lastCopyHandle);
               }

               if (chunkStartTargetMappingsPending) {
                  do {
                     targetMap.put(pendingTargetMappingHandle, lastCopyHandle);
                     pendingTargetMappingHandle = pendingTargetMappingHandle.getNext();
                  } while(pendingTargetMappingHandle != ih);

                  chunkStartTargetMappingsPending = false;
               }
            }
         }

         ih = first;

         InstructionHandle endInst;
         int j;
         for(InstructionHandle ch = newIL.getStart(); ch != null; ih = ih.getNext()) {
            i = ih.getInstruction();
            Instruction c = ch.getInstruction();
            int idx;
            if (i instanceof BranchInstruction) {
               BranchInstruction bc = (BranchInstruction)c;
               BranchInstruction bi = (BranchInstruction)i;
               endInst = bi.getTarget();
               InstructionHandle newTarget = (InstructionHandle)targetMap.get(endInst);
               bc.setTarget(newTarget);
               if (bi instanceof Select) {
                  InstructionHandle[] itargets = ((Select)bi).getTargets();
                  InstructionHandle[] ctargets = ((Select)bc).getTargets();

                  for(j = 0; j < itargets.length; ++j) {
                     ctargets[j] = (InstructionHandle)targetMap.get(itargets[j]);
                  }
               }
            } else if (i instanceof LocalVariableInstruction || i instanceof RET) {
               IndexedInstruction lvi = (IndexedInstruction)c;
               idx = lvi.getIndex();
               newLVG = this.getLocalVariableRegistry().lookupRegisteredLocalVariable(idx, ih.getPosition());
               LocalVariableGen newLVG = (LocalVariableGen)localVarMap.get(newLVG);
               int newLocalVarIndex;
               if (newLVG == null) {
                  varName = newLVG.getName();
                  varType = newLVG.getType();
                  newLVG = outlinedMethodGen.addLocalVariable(varName, varType, (InstructionHandle)null, (InstructionHandle)null);
                  newLocalVarIndex = newLVG.getIndex();
                  localVarMap.put(newLVG, newLVG);
                  revisedLocalVarStart.put(newLVG, outlinedMethodRef);
                  revisedLocalVarEnd.put(newLVG, outlinedMethodRef);
               } else {
                  newLocalVarIndex = newLVG.getIndex();
               }

               lvi.setIndex(newLocalVarIndex);
            }

            if (ih.hasTargeters()) {
               InstructionTargeter[] targeters = ih.getTargeters();

               for(idx = 0; idx < targeters.length; ++idx) {
                  InstructionTargeter targeter = targeters[idx];
                  if (targeter instanceof LocalVariableGen && ((LocalVariableGen)targeter).getEnd() == ih) {
                     Object newLVG = localVarMap.get(targeter);
                     if (newLVG != null) {
                        outlinedMethodGen.removeLocalVariable((LocalVariableGen)newLVG);
                     }
                  }
               }
            }

            if (!(i instanceof MarkerInstruction)) {
               ch = ch.getNext();
            }
         }

         oldMethCopyOutIL.append((Instruction)InstructionConstants.POP);
         Iterator revisedLocalVarStartPairIter = revisedLocalVarStart.entrySet().iterator();

         while(revisedLocalVarStartPairIter.hasNext()) {
            Map.Entry lvgRangeStartPair = (Map.Entry)revisedLocalVarStartPairIter.next();
            LocalVariableGen lvg = (LocalVariableGen)lvgRangeStartPair.getKey();
            InstructionHandle startInst = (InstructionHandle)lvgRangeStartPair.getValue();
            lvg.setStart(startInst);
         }

         Iterator revisedLocalVarEndPairIter = revisedLocalVarEnd.entrySet().iterator();

         while(revisedLocalVarEndPairIter.hasNext()) {
            Map.Entry lvgRangeEndPair = (Map.Entry)revisedLocalVarEndPairIter.next();
            oldLVG = (LocalVariableGen)lvgRangeEndPair.getKey();
            endInst = (InstructionHandle)lvgRangeEndPair.getValue();
            oldLVG.setEnd(endInst);
         }

         xsltc.dumpClass(copyAreaCG.getJavaClass());
         InstructionList oldMethodIL = this.getInstructionList();
         oldMethodIL.insert(first, oldMethCopyInIL);
         oldMethodIL.insert(first, oldMethCopyOutIL);
         newIL.insert(newMethCopyInIL);
         newIL.append(newMethCopyOutIL);
         newIL.append((Instruction)InstructionConstants.RETURN);

         try {
            oldMethodIL.delete(first, last);
         } catch (TargetLostException var50) {
            InstructionHandle[] targets = var50.getTargets();

            for(int i = 0; i < targets.length; ++i) {
               InstructionHandle lostTarget = targets[i];
               InstructionTargeter[] targeters = lostTarget.getTargeters();

               for(j = 0; j < targeters.length; ++j) {
                  if (targeters[j] instanceof LocalVariableGen) {
                     LocalVariableGen lvgTargeter = (LocalVariableGen)targeters[j];
                     if (lvgTargeter.getStart() == lostTarget) {
                        lvgTargeter.setStart(outlinedMethodRef);
                     }

                     if (lvgTargeter.getEnd() == lostTarget) {
                        lvgTargeter.setEnd(outlinedMethodRef);
                     }
                  } else {
                     targeters[j].updateTarget(lostTarget, outlinedMethodCallSetup);
                  }
               }
            }
         }

         String[] exceptions = this.getExceptions();

         for(int i = 0; i < exceptions.length; ++i) {
            outlinedMethodGen.addException(exceptions[i]);
         }

         return outlinedMethodGen.getThisMethod();
      }
   }

   private static Instruction loadLocal(int index, com.oracle.wls.shaded.org.apache.bcel.generic.Type type) {
      if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.BOOLEAN) {
         return new ILOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT) {
         return new ILOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.SHORT) {
         return new ILOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.LONG) {
         return new LLOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.BYTE) {
         return new ILOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.CHAR) {
         return new ILOAD(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.FLOAT) {
         return new FLOAD(index);
      } else {
         return (Instruction)(type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.DOUBLE ? new DLOAD(index) : new ALOAD(index));
      }
   }

   private static Instruction storeLocal(int index, com.oracle.wls.shaded.org.apache.bcel.generic.Type type) {
      if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.BOOLEAN) {
         return new ISTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.INT) {
         return new ISTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.SHORT) {
         return new ISTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.LONG) {
         return new LSTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.BYTE) {
         return new ISTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.CHAR) {
         return new ISTORE(index);
      } else if (type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.FLOAT) {
         return new FSTORE(index);
      } else {
         return (Instruction)(type == com.oracle.wls.shaded.org.apache.bcel.generic.Type.DOUBLE ? new DSTORE(index) : new ASTORE(index));
      }
   }

   public void markChunkStart() {
      this.getInstructionList().append(OutlineableChunkStart.OUTLINEABLECHUNKSTART);
      ++this.m_totalChunks;
      ++this.m_openChunks;
   }

   public void markChunkEnd() {
      this.getInstructionList().append(OutlineableChunkEnd.OUTLINEABLECHUNKEND);
      --this.m_openChunks;
      if (this.m_openChunks < 0) {
         String msg = (new ErrorMsg("OUTLINE_ERR_UNBALANCED_MARKERS")).toString();
         throw new InternalError(msg);
      }
   }

   Method[] getGeneratedMethods(ClassGenerator classGen) {
      InstructionList il = this.getInstructionList();
      InstructionHandle last = il.getEnd();
      il.setPositions();
      int instructionListSize = last.getPosition() + last.getInstruction().getLength();
      if (instructionListSize > 32767) {
         boolean ilChanged = this.widenConditionalBranchTargetOffsets();
         if (ilChanged) {
            il.setPositions();
            last = il.getEnd();
            instructionListSize = last.getPosition() + last.getInstruction().getLength();
         }
      }

      Method[] generatedMethods;
      if (instructionListSize > 65535) {
         generatedMethods = this.outlineChunks(classGen, instructionListSize);
      } else {
         generatedMethods = new Method[]{this.getThisMethod()};
      }

      return generatedMethods;
   }

   protected Method getThisMethod() {
      this.stripAttributes(true);
      this.setMaxLocals();
      this.setMaxStack();
      this.removeNOPs();
      return this.getMethod();
   }

   boolean widenConditionalBranchTargetOffsets() {
      boolean ilChanged = false;
      int maxOffsetChange = 0;
      InstructionList il = this.getInstructionList();

      Instruction inst;
      for(InstructionHandle ih = il.getStart(); ih != null; ih = ih.getNext()) {
         inst = ih.getInstruction();
         switch (inst.getOpcode()) {
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 198:
            case 199:
               maxOffsetChange += 5;
               break;
            case 167:
            case 168:
               maxOffsetChange += 2;
            case 169:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            default:
               break;
            case 170:
            case 171:
               maxOffsetChange += 3;
         }
      }

      for(InstructionHandle ih = il.getStart(); ih != null; ih = ((InstructionHandle)ih).getNext()) {
         inst = ((InstructionHandle)ih).getInstruction();
         if (inst instanceof IfInstruction) {
            IfInstruction oldIfInst = (IfInstruction)inst;
            BranchHandle oldIfHandle = (BranchHandle)ih;
            InstructionHandle target = oldIfInst.getTarget();
            int relativeTargetOffset = target.getPosition() - oldIfHandle.getPosition();
            if (relativeTargetOffset - maxOffsetChange < -32768 || relativeTargetOffset + maxOffsetChange > 32767) {
               InstructionHandle nextHandle = oldIfHandle.getNext();
               IfInstruction invertedIfInst = oldIfInst.negate();
               BranchHandle invertedIfHandle = il.append((InstructionHandle)oldIfHandle, (BranchInstruction)invertedIfInst);
               BranchHandle gotoHandle = il.append((InstructionHandle)invertedIfHandle, (BranchInstruction)(new GOTO(target)));
               if (nextHandle == null) {
                  nextHandle = il.append((InstructionHandle)gotoHandle, (Instruction)NOP);
               }

               invertedIfHandle.updateTarget(target, nextHandle);
               if (oldIfHandle.hasTargeters()) {
                  InstructionTargeter[] targeters = oldIfHandle.getTargeters();

                  for(int i = 0; i < targeters.length; ++i) {
                     InstructionTargeter targeter = targeters[i];
                     if (targeter instanceof LocalVariableGen) {
                        LocalVariableGen lvg = (LocalVariableGen)targeter;
                        if (lvg.getStart() == oldIfHandle) {
                           lvg.setStart(invertedIfHandle);
                        } else if (lvg.getEnd() == oldIfHandle) {
                           lvg.setEnd(gotoHandle);
                        }
                     } else {
                        targeter.updateTarget(oldIfHandle, invertedIfHandle);
                     }
                  }
               }

               try {
                  il.delete((InstructionHandle)oldIfHandle);
               } catch (TargetLostException var18) {
                  String msg = (new ErrorMsg("OUTLINE_ERR_DELETED_TARGET", var18.getMessage())).toString();
                  throw new InternalError(msg);
               }

               ih = gotoHandle;
               ilChanged = true;
            }
         }
      }

      return ilChanged;
   }

   private static class Chunk implements Comparable {
      private InstructionHandle m_start;
      private InstructionHandle m_end;
      private int m_size;

      Chunk(InstructionHandle start, InstructionHandle end) {
         this.m_start = start;
         this.m_end = end;
         this.m_size = end.getPosition() - start.getPosition();
      }

      boolean isAdjacentTo(Chunk neighbour) {
         return this.getChunkEnd().getNext() == neighbour.getChunkStart();
      }

      InstructionHandle getChunkStart() {
         return this.m_start;
      }

      InstructionHandle getChunkEnd() {
         return this.m_end;
      }

      int getChunkSize() {
         return this.m_size;
      }

      public int compareTo(Object comparand) {
         return this.getChunkSize() - ((Chunk)comparand).getChunkSize();
      }
   }

   protected class LocalVariableRegistry {
      protected ArrayList _variables = new ArrayList();
      protected HashMap _nameToLVGMap = new HashMap();

      protected void registerLocalVariable(LocalVariableGen lvg) {
         int slot = lvg.getIndex();
         int registrySize = this._variables.size();
         if (slot >= registrySize) {
            for(int i = registrySize; i < slot; ++i) {
               this._variables.add((Object)null);
            }

            this._variables.add(lvg);
         } else {
            Object localsInSlot = this._variables.get(slot);
            if (localsInSlot != null) {
               if (localsInSlot instanceof LocalVariableGen) {
                  ArrayList listOfLocalsInSlot = new ArrayList();
                  listOfLocalsInSlot.add(localsInSlot);
                  listOfLocalsInSlot.add(lvg);
                  this._variables.set(slot, listOfLocalsInSlot);
               } else {
                  ((ArrayList)localsInSlot).add(lvg);
               }
            } else {
               this._variables.set(slot, lvg);
            }
         }

         this.registerByName(lvg);
      }

      protected LocalVariableGen lookupRegisteredLocalVariable(int slot, int offset) {
         Object localsInSlot = this._variables != null ? this._variables.get(slot) : null;
         if (localsInSlot != null) {
            if (localsInSlot instanceof LocalVariableGen) {
               LocalVariableGen lvgx = (LocalVariableGen)localsInSlot;
               if (MethodGenerator.this.offsetInLocalVariableGenRange(lvgx, offset)) {
                  return lvgx;
               }
            } else {
               ArrayList listOfLocalsInSlot = (ArrayList)localsInSlot;
               int size = listOfLocalsInSlot.size();

               for(int i = 0; i < size; ++i) {
                  LocalVariableGen lvg = (LocalVariableGen)listOfLocalsInSlot.get(i);
                  if (MethodGenerator.this.offsetInLocalVariableGenRange(lvg, offset)) {
                     return lvg;
                  }
               }
            }
         }

         return null;
      }

      protected void registerByName(LocalVariableGen lvg) {
         Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName());
         if (duplicateNameEntry == null) {
            this._nameToLVGMap.put(lvg.getName(), lvg);
         } else {
            ArrayList sameNameList;
            if (duplicateNameEntry instanceof ArrayList) {
               sameNameList = (ArrayList)duplicateNameEntry;
               sameNameList.add(lvg);
            } else {
               sameNameList = new ArrayList();
               sameNameList.add(duplicateNameEntry);
               sameNameList.add(lvg);
            }

            this._nameToLVGMap.put(lvg.getName(), sameNameList);
         }

      }

      protected void removeByNameTracking(LocalVariableGen lvg) {
         Object duplicateNameEntry = this._nameToLVGMap.get(lvg.getName());
         if (duplicateNameEntry instanceof ArrayList) {
            ArrayList sameNameList = (ArrayList)duplicateNameEntry;

            for(int i = 0; i < sameNameList.size(); ++i) {
               if (sameNameList.get(i) == lvg) {
                  sameNameList.remove(i);
                  break;
               }
            }
         } else {
            this._nameToLVGMap.remove(lvg);
         }

      }

      protected LocalVariableGen lookUpByName(String name) {
         LocalVariableGen lvg = null;
         Object duplicateNameEntry = this._nameToLVGMap.get(name);
         if (duplicateNameEntry instanceof ArrayList) {
            ArrayList sameNameList = (ArrayList)duplicateNameEntry;

            for(int i = 0; i < sameNameList.size(); ++i) {
               lvg = (LocalVariableGen)sameNameList.get(i);
               if (lvg.getName() == name) {
                  break;
               }
            }
         } else {
            lvg = (LocalVariableGen)duplicateNameEntry;
         }

         return lvg;
      }

      protected LocalVariableGen[] getLocals(boolean includeRemoved) {
         LocalVariableGen[] locals = null;
         ArrayList allVarsEverDeclared = new ArrayList();
         Object slotEntries;
         ArrayList slotList;
         int i;
         if (includeRemoved) {
            int slotCount = allVarsEverDeclared.size();

            for(int ix = 0; ix < slotCount; ++ix) {
               slotEntries = this._variables.get(ix);
               if (slotEntries != null) {
                  if (slotEntries instanceof ArrayList) {
                     slotList = (ArrayList)slotEntries;

                     for(i = 0; i < slotList.size(); ++i) {
                        allVarsEverDeclared.add(slotList.get(ix));
                     }
                  } else {
                     allVarsEverDeclared.add(slotEntries);
                  }
               }
            }
         } else {
            Iterator nameVarsPairsIter = this._nameToLVGMap.entrySet().iterator();

            label44:
            while(true) {
               while(true) {
                  do {
                     if (!nameVarsPairsIter.hasNext()) {
                        break label44;
                     }

                     Map.Entry nameVarsPair = (Map.Entry)nameVarsPairsIter.next();
                     slotEntries = nameVarsPair.getValue();
                  } while(slotEntries == null);

                  if (slotEntries instanceof ArrayList) {
                     slotList = (ArrayList)slotEntries;

                     for(i = 0; i < slotList.size(); ++i) {
                        allVarsEverDeclared.add(slotList.get(i));
                     }
                  } else {
                     allVarsEverDeclared.add(slotEntries);
                  }
               }
            }
         }

         locals = new LocalVariableGen[allVarsEverDeclared.size()];
         allVarsEverDeclared.toArray(locals);
         return locals;
      }
   }
}
