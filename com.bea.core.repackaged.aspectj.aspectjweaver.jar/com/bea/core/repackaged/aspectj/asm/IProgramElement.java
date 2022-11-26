package com.bea.core.repackaged.aspectj.asm;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IProgramElement extends Serializable {
   List getChildren();

   void setChildren(List var1);

   void addChild(IProgramElement var1);

   boolean removeChild(IProgramElement var1);

   void setExtraInfo(ExtraInformation var1);

   ExtraInformation getExtraInfo();

   IProgramElement getParent();

   void setParent(IProgramElement var1);

   void setParentTypes(List var1);

   List getParentTypes();

   String getName();

   void setName(String var1);

   String getDetails();

   void setDetails(String var1);

   Kind getKind();

   void setKind(Kind var1);

   List getModifiers();

   void setModifiers(int var1);

   Accessibility getAccessibility();

   String getDeclaringType();

   String getPackageName();

   void setCorrespondingType(String var1);

   String getCorrespondingType();

   String getCorrespondingType(boolean var1);

   String toSignatureString();

   String toSignatureString(boolean var1);

   void setRunnable(boolean var1);

   boolean isRunnable();

   boolean isImplementor();

   void setImplementor(boolean var1);

   boolean isOverrider();

   void setOverrider(boolean var1);

   IMessage getMessage();

   void setMessage(IMessage var1);

   ISourceLocation getSourceLocation();

   void setSourceLocation(ISourceLocation var1);

   String toString();

   String getFormalComment();

   void setFormalComment(String var1);

   String toLinkLabelString();

   String toLinkLabelString(boolean var1);

   String toLabelString();

   String toLabelString(boolean var1);

   List getParameterNames();

   void setParameterNames(List var1);

   List getParameterSignatures();

   List getParameterSignaturesSourceRefs();

   void setParameterSignatures(List var1, List var2);

   List getParameterTypes();

   String getHandleIdentifier();

   String getHandleIdentifier(boolean var1);

   void setHandleIdentifier(String var1);

   String toLongString();

   String getBytecodeName();

   String getBytecodeSignature();

   void setBytecodeName(String var1);

   void setBytecodeSignature(String var1);

   String getSourceSignature();

   void setSourceSignature(String var1);

   IProgramElement walk(HierarchyWalker var1);

   AsmManager getModel();

   int getRawModifiers();

   void setAnnotationStyleDeclaration(boolean var1);

   boolean isAnnotationStyleDeclaration();

   void setAnnotationType(String var1);

   String getAnnotationType();

   String[] getRemovedAnnotationTypes();

   Map getDeclareParentsMap();

   void setDeclareParentsMap(Map var1);

   void addFullyQualifiedName(String var1);

   String getFullyQualifiedName();

   void setAnnotationRemover(boolean var1);

   boolean isAnnotationRemover();

   String getCorrespondingTypeSignature();

   public static class Kind implements Serializable {
      private static final long serialVersionUID = -1963553877479266124L;
      public static final Kind PROJECT = new Kind("project");
      public static final Kind PACKAGE = new Kind("package");
      public static final Kind FILE = new Kind("file");
      public static final Kind FILE_JAVA = new Kind("java source file");
      public static final Kind FILE_ASPECTJ = new Kind("aspect source file");
      public static final Kind FILE_LST = new Kind("build configuration file");
      public static final Kind IMPORT_REFERENCE = new Kind("import reference");
      public static final Kind CLASS = new Kind("class");
      public static final Kind INTERFACE = new Kind("interface");
      public static final Kind ASPECT = new Kind("aspect");
      public static final Kind ENUM = new Kind("enum");
      public static final Kind ENUM_VALUE = new Kind("enumvalue");
      public static final Kind ANNOTATION = new Kind("annotation");
      public static final Kind INITIALIZER = new Kind("initializer");
      public static final Kind INTER_TYPE_FIELD = new Kind("inter-type field");
      public static final Kind INTER_TYPE_METHOD = new Kind("inter-type method");
      public static final Kind INTER_TYPE_CONSTRUCTOR = new Kind("inter-type constructor");
      public static final Kind INTER_TYPE_PARENT = new Kind("inter-type parent");
      public static final Kind CONSTRUCTOR = new Kind("constructor");
      public static final Kind METHOD = new Kind("method");
      public static final Kind FIELD = new Kind("field");
      public static final Kind POINTCUT = new Kind("pointcut");
      public static final Kind ADVICE = new Kind("advice");
      public static final Kind DECLARE_PARENTS = new Kind("declare parents");
      public static final Kind DECLARE_WARNING = new Kind("declare warning");
      public static final Kind DECLARE_ERROR = new Kind("declare error");
      public static final Kind DECLARE_SOFT = new Kind("declare soft");
      public static final Kind DECLARE_PRECEDENCE = new Kind("declare precedence");
      public static final Kind CODE = new Kind("code");
      public static final Kind ERROR = new Kind("error");
      public static final Kind DECLARE_ANNOTATION_AT_CONSTRUCTOR = new Kind("declare @constructor");
      public static final Kind DECLARE_ANNOTATION_AT_FIELD = new Kind("declare @field");
      public static final Kind DECLARE_ANNOTATION_AT_METHOD = new Kind("declare @method");
      public static final Kind DECLARE_ANNOTATION_AT_TYPE = new Kind("declare @type");
      public static final Kind SOURCE_FOLDER = new Kind("source folder");
      public static final Kind PACKAGE_DECLARATION = new Kind("package declaration");
      public static final Kind[] ALL;
      private final String name;
      private static int nextOrdinal;
      private final int ordinal;

      public static Kind getKindForString(String kindString) {
         for(int i = 0; i < ALL.length; ++i) {
            if (ALL[i].toString().equals(kindString)) {
               return ALL[i];
            }
         }

         return ERROR;
      }

      private Kind(String name) {
         this.ordinal = nextOrdinal++;
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      public static List getNonAJMemberKinds() {
         List list = new ArrayList();
         list.add(METHOD);
         list.add(ENUM_VALUE);
         list.add(FIELD);
         list.add(CONSTRUCTOR);
         return list;
      }

      public boolean isMember() {
         return this == FIELD || this == METHOD || this == CONSTRUCTOR || this == POINTCUT || this == ADVICE || this == ENUM_VALUE;
      }

      public boolean isInterTypeMember() {
         return this == INTER_TYPE_CONSTRUCTOR || this == INTER_TYPE_FIELD || this == INTER_TYPE_METHOD;
      }

      public boolean isType() {
         return this == CLASS || this == INTERFACE || this == ASPECT || this == ANNOTATION || this == ENUM;
      }

      public boolean isSourceFile() {
         return this == FILE_ASPECTJ || this == FILE_JAVA;
      }

      public boolean isFile() {
         return this == FILE;
      }

      public boolean isDeclare() {
         return this.name.startsWith("declare");
      }

      public boolean isDeclareAnnotation() {
         return this.name.startsWith("declare @");
      }

      public boolean isDeclareParents() {
         return this.name.startsWith("declare parents");
      }

      public boolean isDeclareSoft() {
         return this.name.startsWith("declare soft");
      }

      public boolean isDeclareWarning() {
         return this.name.startsWith("declare warning");
      }

      public boolean isDeclareError() {
         return this.name.startsWith("declare error");
      }

      public boolean isDeclarePrecedence() {
         return this.name.startsWith("declare precedence");
      }

      private Object readResolve() throws ObjectStreamException {
         return ALL[this.ordinal];
      }

      public boolean isPackageDeclaration() {
         return this == PACKAGE_DECLARATION;
      }

      static {
         ALL = new Kind[]{PROJECT, PACKAGE, FILE, FILE_JAVA, FILE_ASPECTJ, FILE_LST, IMPORT_REFERENCE, CLASS, INTERFACE, ASPECT, ENUM, ENUM_VALUE, ANNOTATION, INITIALIZER, INTER_TYPE_FIELD, INTER_TYPE_METHOD, INTER_TYPE_CONSTRUCTOR, INTER_TYPE_PARENT, CONSTRUCTOR, METHOD, FIELD, POINTCUT, ADVICE, DECLARE_PARENTS, DECLARE_WARNING, DECLARE_ERROR, DECLARE_SOFT, DECLARE_PRECEDENCE, CODE, ERROR, DECLARE_ANNOTATION_AT_CONSTRUCTOR, DECLARE_ANNOTATION_AT_FIELD, DECLARE_ANNOTATION_AT_METHOD, DECLARE_ANNOTATION_AT_TYPE, SOURCE_FOLDER, PACKAGE_DECLARATION};
         nextOrdinal = 0;
      }
   }

   public static class Accessibility implements Serializable {
      private static final long serialVersionUID = 5371838588180918519L;
      public static final Accessibility PUBLIC = new Accessibility("public");
      public static final Accessibility PACKAGE = new Accessibility("package");
      public static final Accessibility PROTECTED = new Accessibility("protected");
      public static final Accessibility PRIVATE = new Accessibility("private");
      public static final Accessibility PRIVILEGED = new Accessibility("privileged");
      public static final Accessibility[] ALL;
      private final String name;
      private static int nextOrdinal;
      private final int ordinal;

      private Accessibility(String name) {
         this.ordinal = nextOrdinal++;
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      private Object readResolve() throws ObjectStreamException {
         return ALL[this.ordinal];
      }

      static {
         ALL = new Accessibility[]{PUBLIC, PACKAGE, PROTECTED, PRIVATE, PRIVILEGED};
         nextOrdinal = 0;
      }
   }

   public static class Modifiers implements Serializable {
      private static final long serialVersionUID = -8279300899976607927L;
      public static final Modifiers STATIC = new Modifiers("static", 8);
      public static final Modifiers FINAL = new Modifiers("final", 16);
      public static final Modifiers ABSTRACT = new Modifiers("abstract", 1024);
      public static final Modifiers SYNCHRONIZED = new Modifiers("synchronized", 32);
      public static final Modifiers VOLATILE = new Modifiers("volatile", 64);
      public static final Modifiers STRICTFP = new Modifiers("strictfp", 2048);
      public static final Modifiers TRANSIENT = new Modifiers("transient", 128);
      public static final Modifiers NATIVE = new Modifiers("native", 256);
      public static final Modifiers[] ALL;
      private final String name;
      private final int bit;
      private static int nextOrdinal;
      private final int ordinal;

      private Modifiers(String name, int bit) {
         this.ordinal = nextOrdinal++;
         this.name = name;
         this.bit = bit;
      }

      public String toString() {
         return this.name;
      }

      public int getBit() {
         return this.bit;
      }

      private Object readResolve() throws ObjectStreamException {
         return ALL[this.ordinal];
      }

      static {
         ALL = new Modifiers[]{STATIC, FINAL, ABSTRACT, SYNCHRONIZED, VOLATILE, STRICTFP, TRANSIENT, NATIVE};
         nextOrdinal = 0;
      }
   }

   public static class ExtraInformation implements Serializable {
      private static final long serialVersionUID = -3880735494840820638L;
      private String extraInfo = "";

      public void setExtraAdviceInformation(String string) {
         this.extraInfo = string;
      }

      public String getExtraAdviceInformation() {
         return this.extraInfo;
      }

      public String toString() {
         return "ExtraInformation: [" + this.extraInfo + "]";
      }
   }
}
