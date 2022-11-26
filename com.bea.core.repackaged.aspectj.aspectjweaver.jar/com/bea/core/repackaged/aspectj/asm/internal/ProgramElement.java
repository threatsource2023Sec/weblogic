package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.HierarchyWalker;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProgramElement implements IProgramElement {
   public transient AsmManager asm;
   private static final long serialVersionUID = 171673495267384449L;
   public static boolean shortITDNames = true;
   private static final String UNDEFINED = "<undefined>";
   private static final int AccPublic = 1;
   private static final int AccPrivate = 2;
   private static final int AccProtected = 4;
   private static final int AccPrivileged = 6;
   private static final int AccStatic = 8;
   private static final int AccFinal = 16;
   private static final int AccSynchronized = 32;
   private static final int AccVolatile = 64;
   private static final int AccTransient = 128;
   private static final int AccNative = 256;
   private static final int AccAbstract = 1024;
   protected String name;
   private IProgramElement.Kind kind;
   protected IProgramElement parent;
   protected List children;
   public Map kvpairs;
   protected ISourceLocation sourceLocation;
   public int modifiers;
   private String handle;

   public AsmManager getModel() {
      return this.asm;
   }

   public ProgramElement() {
      this.parent = null;
      this.children = Collections.emptyList();
      this.kvpairs = Collections.emptyMap();
      this.sourceLocation = null;
      this.handle = null;
   }

   public ProgramElement(AsmManager asm, String name, IProgramElement.Kind kind, List children) {
      this.parent = null;
      this.children = Collections.emptyList();
      this.kvpairs = Collections.emptyMap();
      this.sourceLocation = null;
      this.handle = null;
      this.asm = asm;
      if (asm == null && !name.equals("<build to view structure>")) {
         throw new RuntimeException();
      } else {
         this.name = name;
         this.kind = kind;
         if (children != null) {
            this.setChildren(children);
         }

      }
   }

   public ProgramElement(AsmManager asm, String name, IProgramElement.Kind kind, ISourceLocation sourceLocation, int modifiers, String comment, List children) {
      this(asm, name, kind, children);
      this.sourceLocation = sourceLocation;
      this.setFormalComment(comment);
      this.modifiers = modifiers;
   }

   public int getRawModifiers() {
      return this.modifiers;
   }

   public List getModifiers() {
      return genModifiers(this.modifiers);
   }

   public IProgramElement.Accessibility getAccessibility() {
      return genAccessibility(this.modifiers);
   }

   public void setDeclaringType(String t) {
      if (t != null && t.length() > 0) {
         this.fixMap();
         this.kvpairs.put("declaringType", t);
      }

   }

   public String getDeclaringType() {
      String dt = (String)this.kvpairs.get("declaringType");
      return dt == null ? "" : dt;
   }

   public String getPackageName() {
      if (this.kind == IProgramElement.Kind.PACKAGE) {
         return this.getName();
      } else {
         return this.getParent() == null ? "" : this.getParent().getPackageName();
      }
   }

   public IProgramElement.Kind getKind() {
      return this.kind;
   }

   public boolean isCode() {
      return this.kind.equals(IProgramElement.Kind.CODE);
   }

   public ISourceLocation getSourceLocation() {
      return this.sourceLocation;
   }

   public void setSourceLocation(ISourceLocation sourceLocation) {
   }

   public IMessage getMessage() {
      return (IMessage)this.kvpairs.get("message");
   }

   public void setMessage(IMessage message) {
      this.fixMap();
      this.kvpairs.put("message", message);
   }

   public IProgramElement getParent() {
      return this.parent;
   }

   public void setParent(IProgramElement parent) {
      this.parent = parent;
   }

   public boolean isMemberKind() {
      return this.kind.isMember();
   }

   public void setRunnable(boolean value) {
      this.fixMap();
      if (value) {
         this.kvpairs.put("isRunnable", "true");
      } else {
         this.kvpairs.remove("isRunnable");
      }

   }

   public boolean isRunnable() {
      return this.kvpairs.get("isRunnable") != null;
   }

   public boolean isImplementor() {
      return this.kvpairs.get("isImplementor") != null;
   }

   public void setImplementor(boolean value) {
      this.fixMap();
      if (value) {
         this.kvpairs.put("isImplementor", "true");
      } else {
         this.kvpairs.remove("isImplementor");
      }

   }

   public boolean isOverrider() {
      return this.kvpairs.get("isOverrider") != null;
   }

   public void setOverrider(boolean value) {
      this.fixMap();
      if (value) {
         this.kvpairs.put("isOverrider", "true");
      } else {
         this.kvpairs.remove("isOverrider");
      }

   }

   public String getFormalComment() {
      return (String)this.kvpairs.get("formalComment");
   }

   public String toString() {
      return this.toLabelString();
   }

   private static List genModifiers(int modifiers) {
      List modifiersList = new ArrayList();
      if ((modifiers & 8) != 0) {
         modifiersList.add(IProgramElement.Modifiers.STATIC);
      }

      if ((modifiers & 16) != 0) {
         modifiersList.add(IProgramElement.Modifiers.FINAL);
      }

      if ((modifiers & 32) != 0) {
         modifiersList.add(IProgramElement.Modifiers.SYNCHRONIZED);
      }

      if ((modifiers & 64) != 0) {
         modifiersList.add(IProgramElement.Modifiers.VOLATILE);
      }

      if ((modifiers & 128) != 0) {
         modifiersList.add(IProgramElement.Modifiers.TRANSIENT);
      }

      if ((modifiers & 256) != 0) {
         modifiersList.add(IProgramElement.Modifiers.NATIVE);
      }

      if ((modifiers & 1024) != 0) {
         modifiersList.add(IProgramElement.Modifiers.ABSTRACT);
      }

      return modifiersList;
   }

   public static IProgramElement.Accessibility genAccessibility(int modifiers) {
      if ((modifiers & 1) != 0) {
         return IProgramElement.Accessibility.PUBLIC;
      } else if ((modifiers & 2) != 0) {
         return IProgramElement.Accessibility.PRIVATE;
      } else if ((modifiers & 4) != 0) {
         return IProgramElement.Accessibility.PROTECTED;
      } else {
         return (modifiers & 6) != 0 ? IProgramElement.Accessibility.PRIVILEGED : IProgramElement.Accessibility.PACKAGE;
      }
   }

   public String getBytecodeName() {
      String s = (String)this.kvpairs.get("bytecodeName");
      return s == null ? "<undefined>" : s;
   }

   public void setBytecodeName(String s) {
      this.fixMap();
      this.kvpairs.put("bytecodeName", s);
   }

   public void setBytecodeSignature(String s) {
      this.fixMap();
      this.kvpairs.put("bytecodeSignature", s);
   }

   public String getBytecodeSignature() {
      String s = (String)this.kvpairs.get("bytecodeSignature");
      return s;
   }

   public String getSourceSignature() {
      return (String)this.kvpairs.get("sourceSignature");
   }

   public void setSourceSignature(String string) {
      this.fixMap();
      this.kvpairs.put("sourceSignature", string);
   }

   public void setKind(IProgramElement.Kind kind) {
      this.kind = kind;
   }

   public void setCorrespondingType(String s) {
      this.fixMap();
      this.kvpairs.put("returnType", s);
   }

   public void setParentTypes(List ps) {
      this.fixMap();
      this.kvpairs.put("parentTypes", ps);
   }

   public List getParentTypes() {
      return (List)((List)(this.kvpairs == null ? null : this.kvpairs.get("parentTypes")));
   }

   public void setAnnotationType(String fullyQualifiedAnnotationType) {
      this.fixMap();
      this.kvpairs.put("annotationType", fullyQualifiedAnnotationType);
   }

   public void setAnnotationRemover(boolean isRemover) {
      this.fixMap();
      this.kvpairs.put("annotationRemover", isRemover);
   }

   public String getAnnotationType() {
      return this.isAnnotationRemover() ? null : (String)((String)(this.kvpairs == null ? null : this.kvpairs.get("annotationType")));
   }

   public boolean isAnnotationRemover() {
      if (this.kvpairs == null) {
         return false;
      } else {
         Boolean b = (Boolean)this.kvpairs.get("annotationRemover");
         return b == null ? false : b;
      }
   }

   public String[] getRemovedAnnotationTypes() {
      if (!this.isAnnotationRemover()) {
         return null;
      } else {
         String annotype = (String)((String)(this.kvpairs == null ? null : this.kvpairs.get("annotationType")));
         return annotype == null ? null : new String[]{annotype};
      }
   }

   public String getCorrespondingType() {
      return this.getCorrespondingType(false);
   }

   public String getCorrespondingTypeSignature() {
      String typename = (String)this.kvpairs.get("returnType");
      return typename == null ? null : nameToSignature(typename);
   }

   public static String nameToSignature(String name) {
      int len = name.length();
      if (len < 8) {
         if (name.equals("byte")) {
            return "B";
         }

         if (name.equals("char")) {
            return "C";
         }

         if (name.equals("double")) {
            return "D";
         }

         if (name.equals("float")) {
            return "F";
         }

         if (name.equals("int")) {
            return "I";
         }

         if (name.equals("long")) {
            return "J";
         }

         if (name.equals("short")) {
            return "S";
         }

         if (name.equals("boolean")) {
            return "Z";
         }

         if (name.equals("void")) {
            return "V";
         }

         if (name.equals("?")) {
            return name;
         }
      }

      if (name.endsWith("[]")) {
         return "[" + nameToSignature(name.substring(0, name.length() - 2));
      } else if (len == 0) {
         throw new IllegalArgumentException("Bad type name: " + name);
      } else {
         assert name.charAt(0) != '[';

         if (name.indexOf("<") == -1) {
            return "L" + name.replace('.', '/') + ';';
         } else {
            StringBuffer nameBuff = new StringBuffer();
            int nestLevel = 0;
            nameBuff.append("L");

            label109:
            for(int i = 0; i < name.length(); ++i) {
               char c = name.charAt(i);
               switch (c) {
                  case ',':
                     throw new IllegalStateException("Should only happen inside <...>");
                  case '.':
                     nameBuff.append('/');
                     break;
                  case '<':
                     nameBuff.append("<");
                     ++nestLevel;
                     StringBuffer innerBuff = new StringBuffer();

                     while(true) {
                        while(nestLevel > 0) {
                           ++i;
                           c = name.charAt(i);
                           if (c == '<') {
                              ++nestLevel;
                           }

                           if (c == '>') {
                              --nestLevel;
                           }

                           if (c == ',' && nestLevel == 1) {
                              nameBuff.append(nameToSignature(innerBuff.toString()));
                              innerBuff = new StringBuffer();
                           } else if (nestLevel > 0) {
                              innerBuff.append(c);
                           }
                        }

                        nameBuff.append(nameToSignature(innerBuff.toString()));
                        nameBuff.append('>');
                        continue label109;
                     }
                  case '>':
                     throw new IllegalStateException("Should by matched by <");
                  default:
                     nameBuff.append(c);
               }
            }

            nameBuff.append(";");
            return nameBuff.toString();
         }
      }
   }

   public String getCorrespondingType(boolean getFullyQualifiedType) {
      String returnType = (String)this.kvpairs.get("returnType");
      if (returnType == null) {
         returnType = "";
      }

      return getFullyQualifiedType ? returnType : trim(returnType);
   }

   public static String trim(String fqname) {
      int i = fqname.indexOf("<");
      if (i == -1) {
         int lastdot = fqname.lastIndexOf(46);
         return lastdot == -1 ? fqname : fqname.substring(lastdot + 1);
      } else {
         char[] charArray = fqname.toCharArray();
         StringBuilder candidate = new StringBuilder(charArray.length);
         StringBuilder complete = new StringBuilder(charArray.length);
         char[] arr$ = charArray;
         int len$ = charArray.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            char c = arr$[i$];
            switch (c) {
               case ',':
               case '<':
               case '>':
                  complete.append(candidate).append(c);
                  candidate.setLength(0);
                  break;
               case '.':
                  candidate.setLength(0);
                  break;
               default:
                  candidate.append(c);
            }
         }

         complete.append(candidate);
         return complete.toString();
      }
   }

   public String getName() {
      return this.name;
   }

   public List getChildren() {
      return this.children;
   }

   public void setChildren(List children) {
      this.children = children;
      if (children != null) {
         Iterator it = children.iterator();

         while(it.hasNext()) {
            ((IProgramElement)it.next()).setParent(this);
         }

      }
   }

   public void addChild(IProgramElement child) {
      if (this.children == null || this.children == Collections.EMPTY_LIST) {
         this.children = new ArrayList();
      }

      this.children.add(child);
      child.setParent(this);
   }

   public void addChild(int position, IProgramElement child) {
      if (this.children == null || this.children == Collections.EMPTY_LIST) {
         this.children = new ArrayList();
      }

      this.children.add(position, child);
      child.setParent(this);
   }

   public boolean removeChild(IProgramElement child) {
      child.setParent((IProgramElement)null);
      return this.children.remove(child);
   }

   public void setName(String string) {
      this.name = string;
   }

   public IProgramElement walk(HierarchyWalker walker) {
      if (this.children != null) {
         Iterator i$ = this.children.iterator();

         while(i$.hasNext()) {
            IProgramElement child = (IProgramElement)i$.next();
            walker.process(child);
         }
      }

      return this;
   }

   public String toLongString() {
      final StringBuffer buffer = new StringBuffer();
      HierarchyWalker walker = new HierarchyWalker() {
         private int depth = 0;

         public void preProcess(IProgramElement node) {
            for(int i = 0; i < this.depth; ++i) {
               buffer.append(' ');
            }

            buffer.append(node.toString());
            buffer.append('\n');
            this.depth += 2;
         }

         public void postProcess(IProgramElement node) {
            this.depth -= 2;
         }
      };
      walker.process(this);
      return buffer.toString();
   }

   public void setModifiers(int i) {
      this.modifiers = i;
   }

   public void addModifiers(IProgramElement.Modifiers newModifier) {
      this.modifiers |= newModifier.getBit();
   }

   public String toSignatureString() {
      return this.toSignatureString(true);
   }

   public String toSignatureString(boolean getFullyQualifiedArgTypes) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.name);
      List ptypes = this.getParameterTypes();
      if (ptypes != null && (!ptypes.isEmpty() || this.kind.equals(IProgramElement.Kind.METHOD)) || this.kind.equals(IProgramElement.Kind.CONSTRUCTOR) || this.kind.equals(IProgramElement.Kind.ADVICE) || this.kind.equals(IProgramElement.Kind.POINTCUT) || this.kind.equals(IProgramElement.Kind.INTER_TYPE_METHOD) || this.kind.equals(IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR)) {
         sb.append('(');
         Iterator it = ptypes.iterator();

         while(it.hasNext()) {
            char[] arg = (char[])it.next();
            if (getFullyQualifiedArgTypes) {
               sb.append(arg);
            } else {
               int index = CharOperation.lastIndexOf('.', arg);
               if (index != -1) {
                  sb.append(CharOperation.subarray(arg, index + 1, arg.length));
               } else {
                  sb.append(arg);
               }
            }

            if (it.hasNext()) {
               sb.append(",");
            }
         }

         sb.append(')');
      }

      return sb.toString();
   }

   public String toLinkLabelString() {
      return this.toLinkLabelString(true);
   }

   public String toLinkLabelString(boolean getFullyQualifiedArgTypes) {
      String label;
      if (this.kind != IProgramElement.Kind.CODE && this.kind != IProgramElement.Kind.INITIALIZER) {
         if (this.kind.isInterTypeMember()) {
            if (shortITDNames) {
               label = "";
            } else {
               int dotIndex = this.name.indexOf(46);
               if (dotIndex != -1) {
                  return this.parent.getName() + ": " + this.toLabelString().substring(dotIndex + 1);
               }

               label = this.parent.getName() + '.';
            }
         } else if (this.kind != IProgramElement.Kind.CLASS && this.kind != IProgramElement.Kind.ASPECT && this.kind != IProgramElement.Kind.INTERFACE) {
            if (this.kind.equals(IProgramElement.Kind.DECLARE_PARENTS)) {
               label = "";
            } else if (this.parent != null) {
               label = this.parent.getName() + '.';
            } else {
               label = "injar aspect: ";
            }
         } else {
            label = "";
         }
      } else {
         label = this.parent.getParent().getName() + ": ";
      }

      label = label + this.toLabelString(getFullyQualifiedArgTypes);
      return label;
   }

   public String toLabelString() {
      return this.toLabelString(true);
   }

   public String toLabelString(boolean getFullyQualifiedArgTypes) {
      String label = this.toSignatureString(getFullyQualifiedArgTypes);
      String details = this.getDetails();
      if (details != null) {
         label = label + ": " + details;
      }

      return label;
   }

   public String getHandleIdentifier() {
      return this.getHandleIdentifier(true);
   }

   public String getHandleIdentifier(boolean create) {
      String h = this.handle;
      if (null == this.handle && create) {
         if (this.asm == null && this.name.equals("<build to view structure>")) {
            h = "<build to view structure>";
         } else {
            try {
               h = this.asm.getHandleProvider().createHandleIdentifier((IProgramElement)this);
            } catch (ArrayIndexOutOfBoundsException var4) {
               throw new RuntimeException("AIOOBE whilst building handle for " + this, var4);
            }
         }
      }

      this.setHandleIdentifier(h);
      return h;
   }

   public void setHandleIdentifier(String handle) {
      this.handle = handle;
   }

   public List getParameterNames() {
      List parameterNames = (List)this.kvpairs.get("parameterNames");
      return parameterNames;
   }

   public void setParameterNames(List list) {
      if (list != null && list.size() != 0) {
         this.fixMap();
         this.kvpairs.put("parameterNames", list);
      }
   }

   public List getParameterTypes() {
      List l = this.getParameterSignatures();
      if (l != null && !l.isEmpty()) {
         List params = new ArrayList();
         Iterator iter = l.iterator();

         while(iter.hasNext()) {
            char[] param = (char[])iter.next();
            params.add(NameConvertor.convertFromSignature(param));
         }

         return params;
      } else {
         return Collections.emptyList();
      }
   }

   public List getParameterSignatures() {
      List parameters = (List)this.kvpairs.get("parameterSigs");
      return parameters;
   }

   public List getParameterSignaturesSourceRefs() {
      List parameters = (List)this.kvpairs.get("parameterSigsSourceRefs");
      return parameters;
   }

   public void setParameterSignatures(List list, List sourceRefs) {
      this.fixMap();
      if (list != null && list.size() != 0) {
         this.kvpairs.put("parameterSigs", list);
      } else {
         this.kvpairs.put("parameterSigs", Collections.EMPTY_LIST);
      }

      if (sourceRefs != null && sourceRefs.size() != 0) {
         this.kvpairs.put("parameterSigsSourceRefs", sourceRefs);
      }

   }

   public String getDetails() {
      String details = (String)this.kvpairs.get("details");
      return details;
   }

   public void setDetails(String string) {
      this.fixMap();
      this.kvpairs.put("details", string);
   }

   public void setFormalComment(String txt) {
      if (txt != null && txt.length() > 0) {
         this.fixMap();
         this.kvpairs.put("formalComment", txt);
      }

   }

   private void fixMap() {
      if (this.kvpairs == Collections.EMPTY_MAP) {
         this.kvpairs = new HashMap();
      }

   }

   public void setExtraInfo(IProgramElement.ExtraInformation info) {
      this.fixMap();
      this.kvpairs.put("ExtraInformation", info);
   }

   public IProgramElement.ExtraInformation getExtraInfo() {
      return (IProgramElement.ExtraInformation)this.kvpairs.get("ExtraInformation");
   }

   public boolean isAnnotationStyleDeclaration() {
      return this.kvpairs.get("annotationStyleDeclaration") != null;
   }

   public void setAnnotationStyleDeclaration(boolean b) {
      if (b) {
         this.fixMap();
         this.kvpairs.put("annotationStyleDeclaration", "true");
      }

   }

   public Map getDeclareParentsMap() {
      Map s = (Map)this.kvpairs.get("declareparentsmap");
      return s;
   }

   public void setDeclareParentsMap(Map newmap) {
      this.fixMap();
      this.kvpairs.put("declareparentsmap", newmap);
   }

   public void addFullyQualifiedName(String fqname) {
      this.fixMap();
      this.kvpairs.put("itdfqname", fqname);
   }

   public String getFullyQualifiedName() {
      return (String)this.kvpairs.get("itdfqname");
   }
}
