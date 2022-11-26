package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.util.GenericSignatureParser;
import com.bea.core.repackaged.aspectj.weaver.tools.Traceable;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class UnresolvedType implements Traceable, TypeVariableDeclaringElement {
   public static final UnresolvedType[] NONE = new UnresolvedType[0];
   public static final UnresolvedType OBJECT = forSignature("Ljava/lang/Object;");
   public static final UnresolvedType OBJECTARRAY = forSignature("[Ljava/lang/Object;");
   public static final UnresolvedType CLONEABLE = forSignature("Ljava/lang/Cloneable;");
   public static final UnresolvedType SERIALIZABLE = forSignature("Ljava/io/Serializable;");
   public static final UnresolvedType THROWABLE = forSignature("Ljava/lang/Throwable;");
   public static final UnresolvedType RUNTIME_EXCEPTION = forSignature("Ljava/lang/RuntimeException;");
   public static final UnresolvedType ERROR = forSignature("Ljava/lang/Error;");
   public static final UnresolvedType AT_INHERITED = forSignature("Ljava/lang/annotation/Inherited;");
   public static final UnresolvedType AT_RETENTION = forSignature("Ljava/lang/annotation/Retention;");
   public static final UnresolvedType ENUM = forSignature("Ljava/lang/Enum;");
   public static final UnresolvedType ANNOTATION = forSignature("Ljava/lang/annotation/Annotation;");
   public static final UnresolvedType JL_CLASS = forSignature("Ljava/lang/Class;");
   public static final UnresolvedType JAVA_LANG_CLASS_ARRAY = forSignature("[Ljava/lang/Class;");
   public static final UnresolvedType JL_STRING = forSignature("Ljava/lang/String;");
   public static final UnresolvedType JL_EXCEPTION = forSignature("Ljava/lang/Exception;");
   public static final UnresolvedType JAVA_LANG_REFLECT_METHOD = forSignature("Ljava/lang/reflect/Method;");
   public static final UnresolvedType JAVA_LANG_REFLECT_FIELD = forSignature("Ljava/lang/reflect/Field;");
   public static final UnresolvedType JAVA_LANG_REFLECT_CONSTRUCTOR = forSignature("Ljava/lang/reflect/Constructor;");
   public static final UnresolvedType JAVA_LANG_ANNOTATION = forSignature("Ljava/lang/annotation/Annotation;");
   public static final UnresolvedType SUPPRESS_AJ_WARNINGS = forSignature("Lorg/aspectj/lang/annotation/SuppressAjWarnings;");
   public static final UnresolvedType AT_TARGET = forSignature("Ljava/lang/annotation/Target;");
   public static final UnresolvedType SOMETHING = new UnresolvedType("?");
   public static final UnresolvedType[] ARRAY_WITH_JUST_OBJECT;
   public static final UnresolvedType JOINPOINT_STATICPART;
   public static final UnresolvedType JOINPOINT_ENCLOSINGSTATICPART;
   public static final UnresolvedType BOOLEAN;
   public static final UnresolvedType BYTE;
   public static final UnresolvedType CHAR;
   public static final UnresolvedType DOUBLE;
   public static final UnresolvedType FLOAT;
   public static final UnresolvedType INT;
   public static final UnresolvedType LONG;
   public static final UnresolvedType SHORT;
   public static final UnresolvedType VOID;
   public static final String MISSING_NAME = "@missing@";
   protected TypeKind typeKind;
   protected String signature;
   protected String signatureErasure;
   private String packageName;
   private String className;
   protected UnresolvedType[] typeParameters;
   protected TypeVariable[] typeVariables;
   private int size;
   private boolean needsModifiableDelegate;

   public boolean isPrimitiveType() {
      return this.typeKind == UnresolvedType.TypeKind.PRIMITIVE;
   }

   public boolean isVoid() {
      return this.signature.equals("V");
   }

   public boolean isSimpleType() {
      return this.typeKind == UnresolvedType.TypeKind.SIMPLE;
   }

   public boolean isRawType() {
      return this.typeKind == UnresolvedType.TypeKind.RAW;
   }

   public boolean isGenericType() {
      return this.typeKind == UnresolvedType.TypeKind.GENERIC;
   }

   public boolean isParameterizedType() {
      return this.typeKind == UnresolvedType.TypeKind.PARAMETERIZED;
   }

   public boolean isParameterizedOrGenericType() {
      return this.typeKind == UnresolvedType.TypeKind.GENERIC || this.typeKind == UnresolvedType.TypeKind.PARAMETERIZED;
   }

   public boolean isParameterizedOrRawType() {
      return this.typeKind == UnresolvedType.TypeKind.PARAMETERIZED || this.typeKind == UnresolvedType.TypeKind.RAW;
   }

   public boolean isTypeVariableReference() {
      return this.typeKind == UnresolvedType.TypeKind.TYPE_VARIABLE;
   }

   public boolean isGenericWildcard() {
      return this.typeKind == UnresolvedType.TypeKind.WILDCARD;
   }

   public TypeKind getTypekind() {
      return this.typeKind;
   }

   public final boolean isArray() {
      return this.signature.length() > 0 && this.signature.charAt(0) == '[';
   }

   public boolean equals(Object other) {
      return !(other instanceof UnresolvedType) ? false : this.signature.equals(((UnresolvedType)other).signature);
   }

   public int hashCode() {
      return this.signature.hashCode();
   }

   protected UnresolvedType(String signature) {
      this.typeKind = UnresolvedType.TypeKind.SIMPLE;
      this.size = 1;
      this.needsModifiableDelegate = false;
      this.signature = signature;
      this.signatureErasure = signature;
   }

   protected UnresolvedType(String signature, String signatureErasure) {
      this.typeKind = UnresolvedType.TypeKind.SIMPLE;
      this.size = 1;
      this.needsModifiableDelegate = false;
      this.signature = signature;
      this.signatureErasure = signatureErasure;
   }

   public UnresolvedType(String signature, String signatureErasure, UnresolvedType[] typeParams) {
      this.typeKind = UnresolvedType.TypeKind.SIMPLE;
      this.size = 1;
      this.needsModifiableDelegate = false;
      this.signature = signature;
      this.signatureErasure = signatureErasure;
      this.typeParameters = typeParams;
      if (typeParams != null) {
         this.typeKind = UnresolvedType.TypeKind.PARAMETERIZED;
      }

   }

   public int getSize() {
      return this.size;
   }

   public static UnresolvedType forName(String name) {
      return forSignature(nameToSignature(name));
   }

   public static UnresolvedType[] forNames(String[] names) {
      UnresolvedType[] ret = new UnresolvedType[names.length];
      int i = 0;

      for(int len = names.length; i < len; ++i) {
         ret[i] = forName(names[i]);
      }

      return ret;
   }

   public static UnresolvedType forGenericType(String name, TypeVariable[] tvbs, String genericSig) {
      String sig = nameToSignature(name);
      UnresolvedType ret = forSignature(sig);
      ret.typeKind = UnresolvedType.TypeKind.GENERIC;
      ret.typeVariables = tvbs;
      ret.signatureErasure = sig;
      return ret;
   }

   public static UnresolvedType forGenericTypeSignature(String sig, String declaredGenericSig) {
      UnresolvedType ret = forSignature(sig);
      ret.typeKind = UnresolvedType.TypeKind.GENERIC;
      GenericSignature.ClassSignature csig = (new GenericSignatureParser()).parseAsClassSignature(declaredGenericSig);
      GenericSignature.FormalTypeParameter[] ftps = csig.formalTypeParameters;
      ret.typeVariables = new TypeVariable[ftps.length];

      for(int i = 0; i < ftps.length; ++i) {
         GenericSignature.FormalTypeParameter parameter = ftps[i];
         if (parameter.classBound instanceof GenericSignature.ClassTypeSignature) {
            GenericSignature.ClassTypeSignature cts = (GenericSignature.ClassTypeSignature)parameter.classBound;
            ret.typeVariables[i] = new TypeVariable(ftps[i].identifier, forSignature(cts.outerType.identifier + ";"));
         } else {
            if (!(parameter.classBound instanceof GenericSignature.TypeVariableSignature)) {
               throw new BCException("UnresolvedType.forGenericTypeSignature(): Do not know how to process type variable bound of type '" + parameter.classBound.getClass() + "'.  Full signature is '" + sig + "'");
            }

            GenericSignature.TypeVariableSignature tvs = (GenericSignature.TypeVariableSignature)parameter.classBound;
            UnresolvedTypeVariableReferenceType utvrt = new UnresolvedTypeVariableReferenceType(new TypeVariable(tvs.typeVariableName));
            ret.typeVariables[i] = new TypeVariable(ftps[i].identifier, utvrt);
         }
      }

      ret.signatureErasure = sig;
      ret.signature = ret.signatureErasure;
      return ret;
   }

   public static UnresolvedType forGenericTypeVariables(String sig, TypeVariable[] tVars) {
      UnresolvedType ret = forSignature(sig);
      ret.typeKind = UnresolvedType.TypeKind.GENERIC;
      ret.typeVariables = tVars;
      ret.signatureErasure = sig;
      ret.signature = ret.signatureErasure;
      return ret;
   }

   public static UnresolvedType forRawTypeName(String name) {
      UnresolvedType ret = forName(name);
      ret.typeKind = UnresolvedType.TypeKind.RAW;
      return ret;
   }

   public static UnresolvedType forPrimitiveType(String signature) {
      UnresolvedType ret = new UnresolvedType(signature);
      ret.typeKind = UnresolvedType.TypeKind.PRIMITIVE;
      if (!signature.equals("J") && !signature.equals("D")) {
         if (signature.equals("V")) {
            ret.size = 0;
         }
      } else {
         ret.size = 2;
      }

      return ret;
   }

   public static UnresolvedType[] add(UnresolvedType[] types, UnresolvedType end) {
      int len = types.length;
      UnresolvedType[] ret = new UnresolvedType[len + 1];
      System.arraycopy(types, 0, ret, 0, len);
      ret[len] = end;
      return ret;
   }

   public static UnresolvedType[] insert(UnresolvedType start, UnresolvedType[] types) {
      int len = types.length;
      UnresolvedType[] ret = new UnresolvedType[len + 1];
      ret[0] = start;
      System.arraycopy(types, 0, ret, 1, len);
      return ret;
   }

   public static UnresolvedType forSignature(String signature) {
      assert !signature.startsWith("L") || signature.indexOf("<") == -1;

      switch (signature.charAt(0)) {
         case '+':
            return TypeFactory.createTypeFromSignature(signature);
         case ',':
         case '.':
         case '/':
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '@':
         case 'A':
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'Q':
         case 'R':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new BCException("Bad type signature " + signature);
         case '-':
            return TypeFactory.createTypeFromSignature(signature);
         case '?':
            return TypeFactory.createTypeFromSignature(signature);
         case 'B':
            return BYTE;
         case 'C':
            return CHAR;
         case 'D':
            return DOUBLE;
         case 'F':
            return FLOAT;
         case 'I':
            return INT;
         case 'J':
            return LONG;
         case 'L':
            return TypeFactory.createTypeFromSignature(signature);
         case 'P':
            return TypeFactory.createTypeFromSignature(signature);
         case 'S':
            return SHORT;
         case 'T':
            return TypeFactory.createTypeFromSignature(signature);
         case 'V':
            return VOID;
         case 'Z':
            return BOOLEAN;
         case '[':
            return TypeFactory.createTypeFromSignature(signature);
      }
   }

   public static UnresolvedType[] forSignatures(String[] sigs) {
      UnresolvedType[] ret = new UnresolvedType[sigs.length];
      int i = 0;

      for(int len = sigs.length; i < len; ++i) {
         ret[i] = forSignature(sigs[i]);
      }

      return ret;
   }

   public String getName() {
      return signatureToName(this.signature);
   }

   public String getSimpleName() {
      String name = this.getRawName();
      int lastDot = name.lastIndexOf(46);
      if (lastDot != -1) {
         name = name.substring(lastDot + 1);
      }

      if (this.isParameterizedType()) {
         StringBuffer sb = new StringBuffer(name);
         sb.append("<");

         for(int i = 0; i < this.typeParameters.length - 1; ++i) {
            sb.append(this.typeParameters[i].getSimpleName());
            sb.append(",");
         }

         sb.append(this.typeParameters[this.typeParameters.length - 1].getSimpleName());
         sb.append(">");
         name = sb.toString();
      }

      return name;
   }

   public String getRawName() {
      return signatureToName(this.signatureErasure == null ? this.signature : this.signatureErasure);
   }

   public String getBaseName() {
      String name = this.getName();
      if (!this.isParameterizedType() && !this.isGenericType()) {
         return name;
      } else {
         return this.typeParameters == null ? name : name.substring(0, name.indexOf("<"));
      }
   }

   public String getSimpleBaseName() {
      String name = this.getBaseName();
      int lastDot = name.lastIndexOf(46);
      if (lastDot != -1) {
         name = name.substring(lastDot + 1);
      }

      return name;
   }

   public static String[] getNames(UnresolvedType[] types) {
      String[] ret = new String[types.length];
      int i = 0;

      for(int len = types.length; i < len; ++i) {
         ret[i] = types[i].getName();
      }

      return ret;
   }

   public String getSignature() {
      return this.signature;
   }

   public String getErasureSignature() {
      return this.signatureErasure == null ? this.signature : this.signatureErasure;
   }

   public boolean needsModifiableDelegate() {
      return this.needsModifiableDelegate;
   }

   public void setNeedsModifiableDelegate(boolean b) {
      this.needsModifiableDelegate = b;
   }

   public UnresolvedType getRawType() {
      return forSignature(this.getErasureSignature());
   }

   public UnresolvedType getOutermostType() {
      if (!this.isArray() && !this.isPrimitiveType()) {
         String sig = this.getErasureSignature();
         int dollar = sig.indexOf(36);
         return dollar != -1 ? forSignature(sig.substring(0, dollar) + ';') : this;
      } else {
         return this;
      }
   }

   public UnresolvedType getComponentType() {
      return this.isArray() ? forSignature(this.signature.substring(1)) : null;
   }

   public String toString() {
      return this.getName();
   }

   public String toDebugString() {
      return this.getName();
   }

   public ResolvedType resolve(World world) {
      return world.resolve(this);
   }

   private static String signatureToName(String signature) {
      switch (signature.charAt(0)) {
         case '*':
            return "?";
         case '+':
            return "? extends " + signatureToName(signature.substring(1, signature.length()));
         case ',':
         case '.':
         case '/':
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'A':
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'Q':
         case 'R':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new BCException("Bad type signature: " + signature);
         case '-':
            return "? super " + signatureToName(signature.substring(1, signature.length()));
         case 'B':
            return "byte";
         case 'C':
            return "char";
         case 'D':
            return "double";
         case 'F':
            return "float";
         case 'I':
            return "int";
         case 'J':
            return "long";
         case 'L':
            String name = signature.substring(1, signature.length() - 1).replace('/', '.');
            return name;
         case 'P':
            StringBuffer nameBuff = new StringBuffer();
            int paramNestLevel = 0;
            int i = 1;

            for(; i < signature.length(); ++i) {
               char c = signature.charAt(i);
               switch (c) {
                  case '/':
                     nameBuff.append('.');
                  case ';':
                     break;
                  case '<':
                     nameBuff.append("<");
                     ++paramNestLevel;
                     StringBuffer innerBuff = new StringBuffer();

                     while(paramNestLevel > 0) {
                        ++i;
                        c = signature.charAt(i);
                        if (c == '<') {
                           ++paramNestLevel;
                        }

                        if (c == '>') {
                           --paramNestLevel;
                        }

                        if (paramNestLevel > 0) {
                           innerBuff.append(c);
                        }

                        if (c == ';' && paramNestLevel == 1) {
                           nameBuff.append(signatureToName(innerBuff.toString()));
                           if (signature.charAt(i + 1) != '>') {
                              nameBuff.append(',');
                           }

                           innerBuff = new StringBuffer();
                        }
                     }

                     nameBuff.append(">");
                     break;
                  default:
                     nameBuff.append(c);
               }
            }

            return nameBuff.toString();
         case 'S':
            return "short";
         case 'T':
            StringBuffer nameBuff2 = new StringBuffer();
            int colon = signature.indexOf(";");
            String tvarName = signature.substring(1, colon);
            nameBuff2.append(tvarName);
            return nameBuff2.toString();
         case 'V':
            return "void";
         case 'Z':
            return "boolean";
         case '[':
            return signatureToName(signature.substring(1, signature.length())) + "[]";
      }
   }

   private static String nameToSignature(String name) {
      int len = name.length();
      if (len < 8) {
         if (name.equals("int")) {
            return "I";
         }

         if (name.equals("void")) {
            return "V";
         }

         if (name.equals("long")) {
            return "J";
         }

         if (name.equals("boolean")) {
            return "Z";
         }

         if (name.equals("double")) {
            return "D";
         }

         if (name.equals("float")) {
            return "F";
         }

         if (name.equals("byte")) {
            return "B";
         }

         if (name.equals("short")) {
            return "S";
         }

         if (name.equals("char")) {
            return "C";
         }

         if (name.equals("?")) {
            return name;
         }
      }

      if (len == 0) {
         throw new BCException("Bad type name: " + name);
      } else if (name.endsWith("[]")) {
         return "[" + nameToSignature(name.substring(0, name.length() - 2));
      } else if (name.charAt(0) == '[') {
         return name.replace('.', '/');
      } else if (name.indexOf("<") == -1) {
         return "L" + name.replace('.', '/') + ';';
      } else {
         StringBuffer nameBuff = new StringBuffer();
         int nestLevel = 0;
         nameBuff.append("P");

         for(int i = 0; i < len; ++i) {
            char c = name.charAt(i);
            switch (c) {
               case '.':
                  nameBuff.append('/');
                  break;
               case '<':
                  nameBuff.append("<");
                  ++nestLevel;
                  StringBuffer innerBuff = new StringBuffer();

                  while(nestLevel > 0) {
                     ++i;
                     c = name.charAt(i);
                     if (c == '<') {
                        ++nestLevel;
                     } else if (c == '>') {
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
                  break;
               default:
                  nameBuff.append(c);
            }
         }

         nameBuff.append(";");
         return nameBuff.toString();
      }
   }

   public final void write(CompressingDataOutputStream s) throws IOException {
      s.writeUTF(this.getSignature());
   }

   public static UnresolvedType read(DataInputStream s) throws IOException {
      String sig = s.readUTF();
      return (UnresolvedType)(sig.equals("@missing@") ? ResolvedType.MISSING : forSignature(sig));
   }

   public String getNameAsIdentifier() {
      return this.getName().replace('.', '_');
   }

   public String getPackageNameAsIdentifier() {
      String name = this.getName();
      int index = name.lastIndexOf(46);
      return index == -1 ? "" : name.substring(0, index).replace('.', '_');
   }

   public UnresolvedType[] getTypeParameters() {
      return this.typeParameters == null ? NONE : this.typeParameters;
   }

   public TypeVariable[] getTypeVariables() {
      return this.typeVariables;
   }

   public TypeVariable getTypeVariableNamed(String name) {
      TypeVariable[] vars = this.getTypeVariables();
      if (vars != null && vars.length != 0) {
         for(int i = 0; i < vars.length; ++i) {
            TypeVariable aVar = vars[i];
            if (aVar.getName().equals(name)) {
               return aVar;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public String toTraceString() {
      return this.getClass().getName() + "[" + this.getName() + "]";
   }

   public UnresolvedType parameterize(Map typeBindings) {
      throw new UnsupportedOperationException("unable to parameterize unresolved type: " + this.signature);
   }

   public String getClassName() {
      if (this.className == null) {
         String name = this.getName();
         if (name.indexOf("<") != -1) {
            name = name.substring(0, name.indexOf("<"));
         }

         int index = name.lastIndexOf(46);
         if (index == -1) {
            this.className = name;
         } else {
            this.className = name.substring(index + 1);
         }
      }

      return this.className;
   }

   public String getPackageName() {
      if (this.packageName == null) {
         String name = this.getName();
         int angly = name.indexOf(60);
         if (angly != -1) {
            name = name.substring(0, angly);
         }

         int index = name.lastIndexOf(46);
         if (index == -1) {
            this.packageName = "";
         } else {
            this.packageName = name.substring(0, index);
         }
      }

      return this.packageName;
   }

   public static void writeArray(UnresolvedType[] types, CompressingDataOutputStream stream) throws IOException {
      int len = types.length;
      stream.writeShort(len);
      UnresolvedType[] arr$ = types;
      int len$ = types.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         UnresolvedType type = arr$[i$];
         type.write(stream);
      }

   }

   public static UnresolvedType[] readArray(DataInputStream s) throws IOException {
      int len = s.readShort();
      if (len == 0) {
         return NONE;
      } else {
         UnresolvedType[] types = new UnresolvedType[len];

         for(int i = 0; i < len; ++i) {
            types[i] = read(s);
         }

         return types;
      }
   }

   public static UnresolvedType makeArray(UnresolvedType base, int dims) {
      StringBuffer sig = new StringBuffer();

      for(int i = 0; i < dims; ++i) {
         sig.append("[");
      }

      sig.append(base.getSignature());
      return forSignature(sig.toString());
   }

   static {
      ARRAY_WITH_JUST_OBJECT = new UnresolvedType[]{OBJECT};
      JOINPOINT_STATICPART = forSignature("Lorg/aspectj/lang/JoinPoint$StaticPart;");
      JOINPOINT_ENCLOSINGSTATICPART = forSignature("Lorg/aspectj/lang/JoinPoint$EnclosingStaticPart;");
      BOOLEAN = forPrimitiveType("Z");
      BYTE = forPrimitiveType("B");
      CHAR = forPrimitiveType("C");
      DOUBLE = forPrimitiveType("D");
      FLOAT = forPrimitiveType("F");
      INT = forPrimitiveType("I");
      LONG = forPrimitiveType("J");
      SHORT = forPrimitiveType("S");
      VOID = forPrimitiveType("V");
   }

   public static class TypeKind {
      public static final TypeKind PRIMITIVE = new TypeKind("primitive");
      public static final TypeKind SIMPLE = new TypeKind("simple");
      public static final TypeKind RAW = new TypeKind("raw");
      public static final TypeKind GENERIC = new TypeKind("generic");
      public static final TypeKind PARAMETERIZED = new TypeKind("parameterized");
      public static final TypeKind TYPE_VARIABLE = new TypeKind("type_variable");
      public static final TypeKind WILDCARD = new TypeKind("wildcard");
      private final String type;

      public String toString() {
         return this.type;
      }

      private TypeKind(String type) {
         this.type = type;
      }
   }
}
