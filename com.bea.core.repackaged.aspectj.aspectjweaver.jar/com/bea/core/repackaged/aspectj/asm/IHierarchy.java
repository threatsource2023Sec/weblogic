package com.bea.core.repackaged.aspectj.asm;

import com.bea.core.repackaged.aspectj.asm.internal.ProgramElement;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IHierarchy extends Serializable {
   IProgramElement NO_STRUCTURE = new ProgramElement((AsmManager)null, "<build to view structure>", IProgramElement.Kind.ERROR, (List)null);

   IProgramElement getElement(String var1);

   IProgramElement getRoot();

   void setRoot(IProgramElement var1);

   void addToFileMap(String var1, IProgramElement var2);

   boolean removeFromFileMap(String var1);

   void setFileMap(HashMap var1);

   Object findInFileMap(Object var1);

   Set getFileMapEntrySet();

   boolean isValid();

   IProgramElement findElementForHandle(String var1);

   IProgramElement findElementForHandleOrCreate(String var1, boolean var2);

   IProgramElement findElementForSignature(IProgramElement var1, IProgramElement.Kind var2, String var3);

   IProgramElement findElementForLabel(IProgramElement var1, IProgramElement.Kind var2, String var3);

   IProgramElement findElementForType(String var1, String var2);

   IProgramElement findElementForSourceFile(String var1);

   IProgramElement findElementForSourceLine(ISourceLocation var1);

   IProgramElement findElementForSourceLine(String var1, int var2);

   IProgramElement findElementForOffSet(String var1, int var2, int var3);

   String getConfigFile();

   void setConfigFile(String var1);

   void flushTypeMap();

   void flushHandleMap();

   void updateHandleMap(Set var1);

   IProgramElement findCloserMatchForLineNumber(IProgramElement var1, int var2);

   IProgramElement findNodeForSourceFile(IProgramElement var1, String var2);
}
