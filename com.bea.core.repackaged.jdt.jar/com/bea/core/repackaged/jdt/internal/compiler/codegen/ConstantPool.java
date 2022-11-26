package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeIds;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfInteger;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfObject;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class ConstantPool implements ClassFileConstants, TypeIds {
   public static final int DOUBLE_INITIAL_SIZE = 5;
   public static final int FLOAT_INITIAL_SIZE = 3;
   public static final int INT_INITIAL_SIZE = 248;
   public static final int LONG_INITIAL_SIZE = 5;
   public static final int UTF8_INITIAL_SIZE = 778;
   public static final int STRING_INITIAL_SIZE = 761;
   public static final int METHODS_AND_FIELDS_INITIAL_SIZE = 450;
   public static final int CLASS_INITIAL_SIZE = 86;
   public static final int NAMEANDTYPE_INITIAL_SIZE = 272;
   public static final int CONSTANTPOOL_INITIAL_SIZE = 2000;
   public static final int CONSTANTPOOL_GROW_SIZE = 6000;
   public static final int DYNAMIC_INITIAL_SIZE = 10;
   protected DoubleCache doubleCache;
   protected FloatCache floatCache;
   protected IntegerCache intCache;
   protected LongCache longCache;
   public CharArrayCache UTF8Cache = new CharArrayCache(778);
   protected CharArrayCache stringCache = new CharArrayCache(761);
   protected HashtableOfObject methodsAndFieldsCache = new HashtableOfObject(450);
   protected CharArrayCache classCache = new CharArrayCache(86);
   protected CharArrayCache moduleCache = new CharArrayCache(5);
   protected CharArrayCache packageCache = new CharArrayCache(5);
   protected HashtableOfObject nameAndTypeCacheForFieldsAndMethods = new HashtableOfObject(272);
   protected HashtableOfInteger dynamicCache = new HashtableOfInteger(10);
   public byte[] poolContent;
   public int currentIndex = 1;
   public int currentOffset;
   public int[] offsets = new int[5];
   public ClassFile classFile;
   public static final char[] Append = "append".toCharArray();
   public static final char[] ARRAY_NEWINSTANCE_NAME = "newInstance".toCharArray();
   public static final char[] ARRAY_NEWINSTANCE_SIGNATURE = "(Ljava/lang/Class;[I)Ljava/lang/Object;".toCharArray();
   public static final char[] ArrayCopy = "arraycopy".toCharArray();
   public static final char[] ArrayCopySignature = "(Ljava/lang/Object;ILjava/lang/Object;II)V".toCharArray();
   public static final char[] ArrayJavaLangClassConstantPoolName = "[Ljava/lang/Class;".toCharArray();
   public static final char[] ArrayJavaLangObjectConstantPoolName = "[Ljava/lang/Object;".toCharArray();
   public static final char[] booleanBooleanSignature = "(Z)Ljava/lang/Boolean;".toCharArray();
   public static final char[] BooleanConstrSignature = "(Z)V".toCharArray();
   public static final char[] BOOLEANVALUE_BOOLEAN_METHOD_NAME = "booleanValue".toCharArray();
   public static final char[] BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE = "()Z".toCharArray();
   public static final char[] byteByteSignature = "(B)Ljava/lang/Byte;".toCharArray();
   public static final char[] ByteConstrSignature = "(B)V".toCharArray();
   public static final char[] BYTEVALUE_BYTE_METHOD_NAME = "byteValue".toCharArray();
   public static final char[] BYTEVALUE_BYTE_METHOD_SIGNATURE = "()B".toCharArray();
   public static final char[] charCharacterSignature = "(C)Ljava/lang/Character;".toCharArray();
   public static final char[] CharConstrSignature = "(C)V".toCharArray();
   public static final char[] CHARVALUE_CHARACTER_METHOD_NAME = "charValue".toCharArray();
   public static final char[] CHARVALUE_CHARACTER_METHOD_SIGNATURE = "()C".toCharArray();
   public static final char[] Clinit = "<clinit>".toCharArray();
   public static final char[] DefaultConstructorSignature = "()V".toCharArray();
   public static final char[] ClinitSignature;
   public static final char[] Close;
   public static final char[] CloseSignature;
   public static final char[] DesiredAssertionStatus;
   public static final char[] DesiredAssertionStatusSignature;
   public static final char[] DoubleConstrSignature;
   public static final char[] doubleDoubleSignature;
   public static final char[] DOUBLEVALUE_DOUBLE_METHOD_NAME;
   public static final char[] DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE;
   public static final char[] EnumName;
   public static final char[] EnumOrdinal;
   public static final char[] Exit;
   public static final char[] ExitIntSignature;
   public static final char[] FloatConstrSignature;
   public static final char[] floatFloatSignature;
   public static final char[] FLOATVALUE_FLOAT_METHOD_NAME;
   public static final char[] FLOATVALUE_FLOAT_METHOD_SIGNATURE;
   public static final char[] ForName;
   public static final char[] ForNameSignature;
   public static final char[] GET_BOOLEAN_METHOD_NAME;
   public static final char[] GET_BOOLEAN_METHOD_SIGNATURE;
   public static final char[] GET_BYTE_METHOD_NAME;
   public static final char[] GET_BYTE_METHOD_SIGNATURE;
   public static final char[] GET_CHAR_METHOD_NAME;
   public static final char[] GET_CHAR_METHOD_SIGNATURE;
   public static final char[] GET_DOUBLE_METHOD_NAME;
   public static final char[] GET_DOUBLE_METHOD_SIGNATURE;
   public static final char[] GET_FLOAT_METHOD_NAME;
   public static final char[] GET_FLOAT_METHOD_SIGNATURE;
   public static final char[] GET_INT_METHOD_NAME;
   public static final char[] GET_INT_METHOD_SIGNATURE;
   public static final char[] GET_LONG_METHOD_NAME;
   public static final char[] GET_LONG_METHOD_SIGNATURE;
   public static final char[] GET_OBJECT_METHOD_NAME;
   public static final char[] GET_OBJECT_METHOD_SIGNATURE;
   public static final char[] GET_SHORT_METHOD_NAME;
   public static final char[] GET_SHORT_METHOD_SIGNATURE;
   public static final char[] GetClass;
   public static final char[] GetClassSignature;
   public static final char[] GetComponentType;
   public static final char[] GetComponentTypeSignature;
   public static final char[] GetConstructor;
   public static final char[] GetConstructorSignature;
   public static final char[] GETDECLAREDCONSTRUCTOR_NAME;
   public static final char[] GETDECLAREDCONSTRUCTOR_SIGNATURE;
   public static final char[] GETDECLAREDFIELD_NAME;
   public static final char[] GETDECLAREDFIELD_SIGNATURE;
   public static final char[] GETDECLAREDMETHOD_NAME;
   public static final char[] GETDECLAREDMETHOD_SIGNATURE;
   public static final char[] GetMessage;
   public static final char[] GetMessageSignature;
   public static final char[] HasNext;
   public static final char[] HasNextSignature;
   public static final char[] Init;
   public static final char[] IntConstrSignature;
   public static final char[] ITERATOR_NAME;
   public static final char[] ITERATOR_SIGNATURE;
   public static final char[] Intern;
   public static final char[] InternSignature;
   public static final char[] IntIntegerSignature;
   public static final char[] INTVALUE_INTEGER_METHOD_NAME;
   public static final char[] INTVALUE_INTEGER_METHOD_SIGNATURE;
   public static final char[] INVOKE_METHOD_METHOD_NAME;
   public static final char[] INVOKE_METHOD_METHOD_SIGNATURE;
   public static final char[][] JAVA_LANG_REFLECT_ACCESSIBLEOBJECT;
   public static final char[][] JAVA_LANG_REFLECT_ARRAY;
   public static final char[] IllegalArgumentExceptionConstructorSignature;
   public static final char[] JavaIoPrintStreamSignature;
   public static final char[] JavaLangAssertionErrorConstantPoolName;
   public static final char[] JavaLangBooleanConstantPoolName;
   public static final char[] JavaLangByteConstantPoolName;
   public static final char[] JavaLangCharacterConstantPoolName;
   public static final char[] JavaLangClassConstantPoolName;
   public static final char[] JavaLangClassNotFoundExceptionConstantPoolName;
   public static final char[] JavaLangClassSignature;
   public static final char[] JavaLangDoubleConstantPoolName;
   public static final char[] JavaLangEnumConstantPoolName;
   public static final char[] JavaLangErrorConstantPoolName;
   public static final char[] JavaLangIncompatibleClassChangeErrorConstantPoolName;
   public static final char[] JavaLangExceptionConstantPoolName;
   public static final char[] JavaLangFloatConstantPoolName;
   public static final char[] JavaLangIntegerConstantPoolName;
   public static final char[] JavaLangLongConstantPoolName;
   public static final char[] JavaLangNoClassDefFoundErrorConstantPoolName;
   public static final char[] JavaLangNoSuchFieldErrorConstantPoolName;
   public static final char[] JavaLangObjectConstantPoolName;
   public static final char[] JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME;
   public static final char[] JAVALANGREFLECTARRAY_CONSTANTPOOLNAME;
   public static final char[] JavaLangReflectConstructorConstantPoolName;
   public static final char[] JavaLangReflectConstructorNewInstanceSignature;
   public static final char[] JAVALANGREFLECTFIELD_CONSTANTPOOLNAME;
   public static final char[] JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME;
   public static final char[] JavaLangShortConstantPoolName;
   public static final char[] JavaLangStringBufferConstantPoolName;
   public static final char[] JavaLangStringBuilderConstantPoolName;
   public static final char[] JavaLangStringConstantPoolName;
   public static final char[] JavaLangStringSignature;
   public static final char[] JavaLangObjectSignature;
   public static final char[] JavaLangSystemConstantPoolName;
   public static final char[] JavaLangThrowableConstantPoolName;
   public static final char[] JavaLangIllegalArgumentExceptionConstantPoolName;
   public static final char[] JavaLangVoidConstantPoolName;
   public static final char[] JavaUtilIteratorConstantPoolName;
   public static final char[] LongConstrSignature;
   public static final char[] longLongSignature;
   public static final char[] LONGVALUE_LONG_METHOD_NAME;
   public static final char[] LONGVALUE_LONG_METHOD_SIGNATURE;
   public static final char[] Name;
   public static final char[] NewInstance;
   public static final char[] NewInstanceSignature;
   public static final char[] Next;
   public static final char[] NextSignature;
   public static final char[] ObjectConstrSignature;
   public static final char[] ObjectSignature;
   public static final char[] Ordinal;
   public static final char[] OrdinalSignature;
   public static final char[] Out;
   public static final char[] SET_BOOLEAN_METHOD_NAME;
   public static final char[] SET_BOOLEAN_METHOD_SIGNATURE;
   public static final char[] SET_BYTE_METHOD_NAME;
   public static final char[] SET_BYTE_METHOD_SIGNATURE;
   public static final char[] SET_CHAR_METHOD_NAME;
   public static final char[] SET_CHAR_METHOD_SIGNATURE;
   public static final char[] SET_DOUBLE_METHOD_NAME;
   public static final char[] SET_DOUBLE_METHOD_SIGNATURE;
   public static final char[] SET_FLOAT_METHOD_NAME;
   public static final char[] SET_FLOAT_METHOD_SIGNATURE;
   public static final char[] SET_INT_METHOD_NAME;
   public static final char[] SET_INT_METHOD_SIGNATURE;
   public static final char[] SET_LONG_METHOD_NAME;
   public static final char[] SET_LONG_METHOD_SIGNATURE;
   public static final char[] SET_OBJECT_METHOD_NAME;
   public static final char[] SET_OBJECT_METHOD_SIGNATURE;
   public static final char[] SET_SHORT_METHOD_NAME;
   public static final char[] SET_SHORT_METHOD_SIGNATURE;
   public static final char[] SETACCESSIBLE_NAME;
   public static final char[] SETACCESSIBLE_SIGNATURE;
   public static final char[] ShortConstrSignature;
   public static final char[] shortShortSignature;
   public static final char[] SHORTVALUE_SHORT_METHOD_NAME;
   public static final char[] SHORTVALUE_SHORT_METHOD_SIGNATURE;
   public static final char[] StringBufferAppendBooleanSignature;
   public static final char[] StringBufferAppendCharSignature;
   public static final char[] StringBufferAppendDoubleSignature;
   public static final char[] StringBufferAppendFloatSignature;
   public static final char[] StringBufferAppendIntSignature;
   public static final char[] StringBufferAppendLongSignature;
   public static final char[] StringBufferAppendObjectSignature;
   public static final char[] StringBufferAppendStringSignature;
   public static final char[] StringBuilderAppendBooleanSignature;
   public static final char[] StringBuilderAppendCharSignature;
   public static final char[] StringBuilderAppendDoubleSignature;
   public static final char[] StringBuilderAppendFloatSignature;
   public static final char[] StringBuilderAppendIntSignature;
   public static final char[] StringBuilderAppendLongSignature;
   public static final char[] StringBuilderAppendObjectSignature;
   public static final char[] StringBuilderAppendStringSignature;
   public static final char[] StringConstructorSignature;
   public static final char[] This;
   public static final char[] ToString;
   public static final char[] ToStringSignature;
   public static final char[] TYPE;
   public static final char[] ValueOf;
   public static final char[] ValueOfBooleanSignature;
   public static final char[] ValueOfCharSignature;
   public static final char[] ValueOfDoubleSignature;
   public static final char[] ValueOfFloatSignature;
   public static final char[] ValueOfIntSignature;
   public static final char[] ValueOfLongSignature;
   public static final char[] ValueOfObjectSignature;
   public static final char[] ValueOfStringClassSignature;
   public static final char[] JAVA_LANG_ANNOTATION_DOCUMENTED;
   public static final char[] JAVA_LANG_ANNOTATION_ELEMENTTYPE;
   public static final char[] JAVA_LANG_ANNOTATION_RETENTION;
   public static final char[] JAVA_LANG_ANNOTATION_RETENTIONPOLICY;
   public static final char[] JAVA_LANG_ANNOTATION_TARGET;
   public static final char[] JAVA_LANG_DEPRECATED;
   public static final char[] JAVA_LANG_ANNOTATION_INHERITED;
   public static final char[] JAVA_LANG_SAFEVARARGS;
   public static final char[] JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE;
   public static final char[] METAFACTORY;
   public static final char[] JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE;
   public static final char[] ALTMETAFACTORY;
   public static final char[] JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE;
   public static final char[] JavaLangInvokeSerializedLambda;
   public static final char[] JavaLangInvokeSerializedLambdaConstantPoolName;
   public static final char[] GetImplMethodName;
   public static final char[] GetImplMethodNameSignature;
   public static final char[] GetImplMethodKind;
   public static final char[] GetImplMethodKindSignature;
   public static final char[] GetFunctionalInterfaceClass;
   public static final char[] GetFunctionalInterfaceClassSignature;
   public static final char[] GetFunctionalInterfaceMethodName;
   public static final char[] GetFunctionalInterfaceMethodNameSignature;
   public static final char[] GetFunctionalInterfaceMethodSignature;
   public static final char[] GetFunctionalInterfaceMethodSignatureSignature;
   public static final char[] GetImplClass;
   public static final char[] GetImplClassSignature;
   public static final char[] GetImplMethodSignature;
   public static final char[] GetImplMethodSignatureSignature;
   public static final char[] GetCapturedArg;
   public static final char[] GetCapturedArgSignature;
   public static final char[] JAVA_LANG_ANNOTATION_REPEATABLE;
   public static final char[] HashCode;
   public static final char[] HashCodeSignature;
   public static final char[] Equals;
   public static final char[] EqualsSignature;
   public static final char[] AddSuppressed;
   public static final char[] AddSuppressedSignature;
   public static final char[] Clone;
   public static final char[] CloneSignature;

   static {
      ClinitSignature = DefaultConstructorSignature;
      Close = "close".toCharArray();
      CloseSignature = "()V".toCharArray();
      DesiredAssertionStatus = "desiredAssertionStatus".toCharArray();
      DesiredAssertionStatusSignature = "()Z".toCharArray();
      DoubleConstrSignature = "(D)V".toCharArray();
      doubleDoubleSignature = "(D)Ljava/lang/Double;".toCharArray();
      DOUBLEVALUE_DOUBLE_METHOD_NAME = "doubleValue".toCharArray();
      DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE = "()D".toCharArray();
      EnumName = "$enum$name".toCharArray();
      EnumOrdinal = "$enum$ordinal".toCharArray();
      Exit = "exit".toCharArray();
      ExitIntSignature = "(I)V".toCharArray();
      FloatConstrSignature = "(F)V".toCharArray();
      floatFloatSignature = "(F)Ljava/lang/Float;".toCharArray();
      FLOATVALUE_FLOAT_METHOD_NAME = "floatValue".toCharArray();
      FLOATVALUE_FLOAT_METHOD_SIGNATURE = "()F".toCharArray();
      ForName = "forName".toCharArray();
      ForNameSignature = "(Ljava/lang/String;)Ljava/lang/Class;".toCharArray();
      GET_BOOLEAN_METHOD_NAME = "getBoolean".toCharArray();
      GET_BOOLEAN_METHOD_SIGNATURE = "(Ljava/lang/Object;)Z".toCharArray();
      GET_BYTE_METHOD_NAME = "getByte".toCharArray();
      GET_BYTE_METHOD_SIGNATURE = "(Ljava/lang/Object;)B".toCharArray();
      GET_CHAR_METHOD_NAME = "getChar".toCharArray();
      GET_CHAR_METHOD_SIGNATURE = "(Ljava/lang/Object;)C".toCharArray();
      GET_DOUBLE_METHOD_NAME = "getDouble".toCharArray();
      GET_DOUBLE_METHOD_SIGNATURE = "(Ljava/lang/Object;)D".toCharArray();
      GET_FLOAT_METHOD_NAME = "getFloat".toCharArray();
      GET_FLOAT_METHOD_SIGNATURE = "(Ljava/lang/Object;)F".toCharArray();
      GET_INT_METHOD_NAME = "getInt".toCharArray();
      GET_INT_METHOD_SIGNATURE = "(Ljava/lang/Object;)I".toCharArray();
      GET_LONG_METHOD_NAME = "getLong".toCharArray();
      GET_LONG_METHOD_SIGNATURE = "(Ljava/lang/Object;)J".toCharArray();
      GET_OBJECT_METHOD_NAME = "get".toCharArray();
      GET_OBJECT_METHOD_SIGNATURE = "(Ljava/lang/Object;)Ljava/lang/Object;".toCharArray();
      GET_SHORT_METHOD_NAME = "getShort".toCharArray();
      GET_SHORT_METHOD_SIGNATURE = "(Ljava/lang/Object;)S".toCharArray();
      GetClass = "getClass".toCharArray();
      GetClassSignature = "()Ljava/lang/Class;".toCharArray();
      GetComponentType = "getComponentType".toCharArray();
      GetComponentTypeSignature = GetClassSignature;
      GetConstructor = "getConstructor".toCharArray();
      GetConstructorSignature = "([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;".toCharArray();
      GETDECLAREDCONSTRUCTOR_NAME = "getDeclaredConstructor".toCharArray();
      GETDECLAREDCONSTRUCTOR_SIGNATURE = "([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;".toCharArray();
      GETDECLAREDFIELD_NAME = "getDeclaredField".toCharArray();
      GETDECLAREDFIELD_SIGNATURE = "(Ljava/lang/String;)Ljava/lang/reflect/Field;".toCharArray();
      GETDECLAREDMETHOD_NAME = "getDeclaredMethod".toCharArray();
      GETDECLAREDMETHOD_SIGNATURE = "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;".toCharArray();
      GetMessage = "getMessage".toCharArray();
      GetMessageSignature = "()Ljava/lang/String;".toCharArray();
      HasNext = "hasNext".toCharArray();
      HasNextSignature = "()Z".toCharArray();
      Init = "<init>".toCharArray();
      IntConstrSignature = "(I)V".toCharArray();
      ITERATOR_NAME = "iterator".toCharArray();
      ITERATOR_SIGNATURE = "()Ljava/util/Iterator;".toCharArray();
      Intern = "intern".toCharArray();
      InternSignature = GetMessageSignature;
      IntIntegerSignature = "(I)Ljava/lang/Integer;".toCharArray();
      INTVALUE_INTEGER_METHOD_NAME = "intValue".toCharArray();
      INTVALUE_INTEGER_METHOD_SIGNATURE = "()I".toCharArray();
      INVOKE_METHOD_METHOD_NAME = "invoke".toCharArray();
      INVOKE_METHOD_METHOD_SIGNATURE = "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".toCharArray();
      JAVA_LANG_REFLECT_ACCESSIBLEOBJECT = new char[][]{TypeConstants.JAVA, TypeConstants.LANG, TypeConstants.REFLECT, "AccessibleObject".toCharArray()};
      JAVA_LANG_REFLECT_ARRAY = new char[][]{TypeConstants.JAVA, TypeConstants.LANG, TypeConstants.REFLECT, "Array".toCharArray()};
      IllegalArgumentExceptionConstructorSignature = "(Ljava/lang/String;)V".toCharArray();
      JavaIoPrintStreamSignature = "Ljava/io/PrintStream;".toCharArray();
      JavaLangAssertionErrorConstantPoolName = "java/lang/AssertionError".toCharArray();
      JavaLangBooleanConstantPoolName = "java/lang/Boolean".toCharArray();
      JavaLangByteConstantPoolName = "java/lang/Byte".toCharArray();
      JavaLangCharacterConstantPoolName = "java/lang/Character".toCharArray();
      JavaLangClassConstantPoolName = "java/lang/Class".toCharArray();
      JavaLangClassNotFoundExceptionConstantPoolName = "java/lang/ClassNotFoundException".toCharArray();
      JavaLangClassSignature = "Ljava/lang/Class;".toCharArray();
      JavaLangDoubleConstantPoolName = "java/lang/Double".toCharArray();
      JavaLangEnumConstantPoolName = "java/lang/Enum".toCharArray();
      JavaLangErrorConstantPoolName = "java/lang/Error".toCharArray();
      JavaLangIncompatibleClassChangeErrorConstantPoolName = "java/lang/IncompatibleClassChangeError".toCharArray();
      JavaLangExceptionConstantPoolName = "java/lang/Exception".toCharArray();
      JavaLangFloatConstantPoolName = "java/lang/Float".toCharArray();
      JavaLangIntegerConstantPoolName = "java/lang/Integer".toCharArray();
      JavaLangLongConstantPoolName = "java/lang/Long".toCharArray();
      JavaLangNoClassDefFoundErrorConstantPoolName = "java/lang/NoClassDefFoundError".toCharArray();
      JavaLangNoSuchFieldErrorConstantPoolName = "java/lang/NoSuchFieldError".toCharArray();
      JavaLangObjectConstantPoolName = "java/lang/Object".toCharArray();
      JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME = "java/lang/reflect/AccessibleObject".toCharArray();
      JAVALANGREFLECTARRAY_CONSTANTPOOLNAME = "java/lang/reflect/Array".toCharArray();
      JavaLangReflectConstructorConstantPoolName = "java/lang/reflect/Constructor".toCharArray();
      JavaLangReflectConstructorNewInstanceSignature = "([Ljava/lang/Object;)Ljava/lang/Object;".toCharArray();
      JAVALANGREFLECTFIELD_CONSTANTPOOLNAME = "java/lang/reflect/Field".toCharArray();
      JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME = "java/lang/reflect/Method".toCharArray();
      JavaLangShortConstantPoolName = "java/lang/Short".toCharArray();
      JavaLangStringBufferConstantPoolName = "java/lang/StringBuffer".toCharArray();
      JavaLangStringBuilderConstantPoolName = "java/lang/StringBuilder".toCharArray();
      JavaLangStringConstantPoolName = "java/lang/String".toCharArray();
      JavaLangStringSignature = "Ljava/lang/String;".toCharArray();
      JavaLangObjectSignature = "Ljava/lang/Object;".toCharArray();
      JavaLangSystemConstantPoolName = "java/lang/System".toCharArray();
      JavaLangThrowableConstantPoolName = "java/lang/Throwable".toCharArray();
      JavaLangIllegalArgumentExceptionConstantPoolName = "java/lang/IllegalArgumentException".toCharArray();
      JavaLangVoidConstantPoolName = "java/lang/Void".toCharArray();
      JavaUtilIteratorConstantPoolName = "java/util/Iterator".toCharArray();
      LongConstrSignature = "(J)V".toCharArray();
      longLongSignature = "(J)Ljava/lang/Long;".toCharArray();
      LONGVALUE_LONG_METHOD_NAME = "longValue".toCharArray();
      LONGVALUE_LONG_METHOD_SIGNATURE = "()J".toCharArray();
      Name = "name".toCharArray();
      NewInstance = "newInstance".toCharArray();
      NewInstanceSignature = "(Ljava/lang/Class;[I)Ljava/lang/Object;".toCharArray();
      Next = "next".toCharArray();
      NextSignature = "()Ljava/lang/Object;".toCharArray();
      ObjectConstrSignature = "(Ljava/lang/Object;)V".toCharArray();
      ObjectSignature = "Ljava/lang/Object;".toCharArray();
      Ordinal = "ordinal".toCharArray();
      OrdinalSignature = "()I".toCharArray();
      Out = "out".toCharArray();
      SET_BOOLEAN_METHOD_NAME = "setBoolean".toCharArray();
      SET_BOOLEAN_METHOD_SIGNATURE = "(Ljava/lang/Object;Z)V".toCharArray();
      SET_BYTE_METHOD_NAME = "setByte".toCharArray();
      SET_BYTE_METHOD_SIGNATURE = "(Ljava/lang/Object;B)V".toCharArray();
      SET_CHAR_METHOD_NAME = "setChar".toCharArray();
      SET_CHAR_METHOD_SIGNATURE = "(Ljava/lang/Object;C)V".toCharArray();
      SET_DOUBLE_METHOD_NAME = "setDouble".toCharArray();
      SET_DOUBLE_METHOD_SIGNATURE = "(Ljava/lang/Object;D)V".toCharArray();
      SET_FLOAT_METHOD_NAME = "setFloat".toCharArray();
      SET_FLOAT_METHOD_SIGNATURE = "(Ljava/lang/Object;F)V".toCharArray();
      SET_INT_METHOD_NAME = "setInt".toCharArray();
      SET_INT_METHOD_SIGNATURE = "(Ljava/lang/Object;I)V".toCharArray();
      SET_LONG_METHOD_NAME = "setLong".toCharArray();
      SET_LONG_METHOD_SIGNATURE = "(Ljava/lang/Object;J)V".toCharArray();
      SET_OBJECT_METHOD_NAME = "set".toCharArray();
      SET_OBJECT_METHOD_SIGNATURE = "(Ljava/lang/Object;Ljava/lang/Object;)V".toCharArray();
      SET_SHORT_METHOD_NAME = "setShort".toCharArray();
      SET_SHORT_METHOD_SIGNATURE = "(Ljava/lang/Object;S)V".toCharArray();
      SETACCESSIBLE_NAME = "setAccessible".toCharArray();
      SETACCESSIBLE_SIGNATURE = "(Z)V".toCharArray();
      ShortConstrSignature = "(S)V".toCharArray();
      shortShortSignature = "(S)Ljava/lang/Short;".toCharArray();
      SHORTVALUE_SHORT_METHOD_NAME = "shortValue".toCharArray();
      SHORTVALUE_SHORT_METHOD_SIGNATURE = "()S".toCharArray();
      StringBufferAppendBooleanSignature = "(Z)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendCharSignature = "(C)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendDoubleSignature = "(D)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendFloatSignature = "(F)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendIntSignature = "(I)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendLongSignature = "(J)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendObjectSignature = "(Ljava/lang/Object;)Ljava/lang/StringBuffer;".toCharArray();
      StringBufferAppendStringSignature = "(Ljava/lang/String;)Ljava/lang/StringBuffer;".toCharArray();
      StringBuilderAppendBooleanSignature = "(Z)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendCharSignature = "(C)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendDoubleSignature = "(D)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendFloatSignature = "(F)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendIntSignature = "(I)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendLongSignature = "(J)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendObjectSignature = "(Ljava/lang/Object;)Ljava/lang/StringBuilder;".toCharArray();
      StringBuilderAppendStringSignature = "(Ljava/lang/String;)Ljava/lang/StringBuilder;".toCharArray();
      StringConstructorSignature = "(Ljava/lang/String;)V".toCharArray();
      This = "this".toCharArray();
      ToString = "toString".toCharArray();
      ToStringSignature = GetMessageSignature;
      TYPE = "TYPE".toCharArray();
      ValueOf = "valueOf".toCharArray();
      ValueOfBooleanSignature = "(Z)Ljava/lang/String;".toCharArray();
      ValueOfCharSignature = "(C)Ljava/lang/String;".toCharArray();
      ValueOfDoubleSignature = "(D)Ljava/lang/String;".toCharArray();
      ValueOfFloatSignature = "(F)Ljava/lang/String;".toCharArray();
      ValueOfIntSignature = "(I)Ljava/lang/String;".toCharArray();
      ValueOfLongSignature = "(J)Ljava/lang/String;".toCharArray();
      ValueOfObjectSignature = "(Ljava/lang/Object;)Ljava/lang/String;".toCharArray();
      ValueOfStringClassSignature = "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;".toCharArray();
      JAVA_LANG_ANNOTATION_DOCUMENTED = "Ljava/lang/annotation/Documented;".toCharArray();
      JAVA_LANG_ANNOTATION_ELEMENTTYPE = "Ljava/lang/annotation/ElementType;".toCharArray();
      JAVA_LANG_ANNOTATION_RETENTION = "Ljava/lang/annotation/Retention;".toCharArray();
      JAVA_LANG_ANNOTATION_RETENTIONPOLICY = "Ljava/lang/annotation/RetentionPolicy;".toCharArray();
      JAVA_LANG_ANNOTATION_TARGET = "Ljava/lang/annotation/Target;".toCharArray();
      JAVA_LANG_DEPRECATED = "Ljava/lang/Deprecated;".toCharArray();
      JAVA_LANG_ANNOTATION_INHERITED = "Ljava/lang/annotation/Inherited;".toCharArray();
      JAVA_LANG_SAFEVARARGS = "Ljava/lang/SafeVarargs;".toCharArray();
      JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE = "Ljava/lang/invoke/MethodHandle$PolymorphicSignature;".toCharArray();
      METAFACTORY = "metafactory".toCharArray();
      JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;".toCharArray();
      ALTMETAFACTORY = "altMetafactory".toCharArray();
      JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;".toCharArray();
      JavaLangInvokeSerializedLambda = "Ljava/lang/invoke/SerializedLambda;".toCharArray();
      JavaLangInvokeSerializedLambdaConstantPoolName = "java/lang/invoke/SerializedLambda".toCharArray();
      GetImplMethodName = "getImplMethodName".toCharArray();
      GetImplMethodNameSignature = "()Ljava/lang/String;".toCharArray();
      GetImplMethodKind = "getImplMethodKind".toCharArray();
      GetImplMethodKindSignature = "()I".toCharArray();
      GetFunctionalInterfaceClass = "getFunctionalInterfaceClass".toCharArray();
      GetFunctionalInterfaceClassSignature = "()Ljava/lang/String;".toCharArray();
      GetFunctionalInterfaceMethodName = "getFunctionalInterfaceMethodName".toCharArray();
      GetFunctionalInterfaceMethodNameSignature = "()Ljava/lang/String;".toCharArray();
      GetFunctionalInterfaceMethodSignature = "getFunctionalInterfaceMethodSignature".toCharArray();
      GetFunctionalInterfaceMethodSignatureSignature = "()Ljava/lang/String;".toCharArray();
      GetImplClass = "getImplClass".toCharArray();
      GetImplClassSignature = "()Ljava/lang/String;".toCharArray();
      GetImplMethodSignature = "getImplMethodSignature".toCharArray();
      GetImplMethodSignatureSignature = "()Ljava/lang/String;".toCharArray();
      GetCapturedArg = "getCapturedArg".toCharArray();
      GetCapturedArgSignature = "(I)Ljava/lang/Object;".toCharArray();
      JAVA_LANG_ANNOTATION_REPEATABLE = "Ljava/lang/annotation/Repeatable;".toCharArray();
      HashCode = "hashCode".toCharArray();
      HashCodeSignature = "()I".toCharArray();
      Equals = "equals".toCharArray();
      EqualsSignature = "(Ljava/lang/Object;)Z".toCharArray();
      AddSuppressed = "addSuppressed".toCharArray();
      AddSuppressedSignature = "(Ljava/lang/Throwable;)V".toCharArray();
      Clone = "clone".toCharArray();
      CloneSignature = "()Ljava/lang/Object;".toCharArray();
   }

   public ConstantPool(ClassFile classFile) {
      this.initialize(classFile);
   }

   public void initialize(ClassFile givenClassFile) {
      this.poolContent = givenClassFile.header;
      this.currentOffset = givenClassFile.headerOffset;
      this.currentIndex = 1;
      this.classFile = givenClassFile;
   }

   public byte[] dumpBytes() {
      System.arraycopy(this.poolContent, 0, this.poolContent = new byte[this.currentOffset], 0, this.currentOffset);
      return this.poolContent;
   }

   public int literalIndex(byte[] utf8encoding, char[] stringCharArray) {
      int index;
      if ((index = this.UTF8Cache.putIfAbsent(stringCharArray, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         ++this.currentIndex;
         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(1);
         int utf8encodingLength = utf8encoding.length;
         if (this.currentOffset + 2 + utf8encodingLength >= this.poolContent.length) {
            this.resizePoolContents(2 + utf8encodingLength);
         }

         this.poolContent[this.currentOffset++] = (byte)(utf8encodingLength >> 8);
         this.poolContent[this.currentOffset++] = (byte)utf8encodingLength;
         System.arraycopy(utf8encoding, 0, this.poolContent, this.currentOffset, utf8encodingLength);
         this.currentOffset += utf8encodingLength;
      }

      return index;
   }

   public int literalIndex(TypeBinding binding) {
      TypeBinding typeBinding = binding.leafComponentType();
      if ((typeBinding.tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, typeBinding);
      }

      return this.literalIndex(binding.signature());
   }

   public int literalIndex(char[] utf8Constant) {
      int index;
      if ((index = this.UTF8Cache.putIfAbsent(utf8Constant, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(1);
         int savedCurrentOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         length = 0;

         for(int i = 0; i < utf8Constant.length; ++i) {
            char current = utf8Constant[i];
            if (current >= 1 && current <= 127) {
               this.writeU1(current);
               ++length;
            } else if (current > 2047) {
               length += 3;
               this.writeU1(224 | current >> 12 & 15);
               this.writeU1(128 | current >> 6 & 63);
               this.writeU1(128 | current & 63);
            } else {
               length += 2;
               this.writeU1(192 | current >> 6 & 31);
               this.writeU1(128 | current & 63);
            }
         }

         if (length >= 65535) {
            this.currentOffset = savedCurrentOffset - 1;
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceForConstant(this.classFile.referenceBinding.scope.referenceType());
         }

         if (index > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         ++this.currentIndex;
         this.poolContent[savedCurrentOffset] = (byte)(length >> 8);
         this.poolContent[savedCurrentOffset + 1] = (byte)length;
      }

      return index;
   }

   public int literalIndex(char[] stringCharArray, byte[] utf8encoding) {
      int index;
      if ((index = this.stringCache.putIfAbsent(stringCharArray, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(8);
         int stringIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int stringIndex = this.literalIndex(utf8encoding, stringCharArray);
         this.poolContent[stringIndexOffset++] = (byte)(stringIndex >> 8);
         this.poolContent[stringIndexOffset] = (byte)stringIndex;
      }

      return index;
   }

   public int literalIndex(double key) {
      if (this.doubleCache == null) {
         this.doubleCache = new DoubleCache(5);
      }

      int index;
      if ((index = this.doubleCache.putIfAbsent(key, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         this.currentIndex += 2;
         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(6);
         long temp = Double.doubleToLongBits(key);
         length = this.poolContent.length;
         if (this.currentOffset + 8 >= length) {
            this.resizePoolContents(8);
         }

         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 56));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 48));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 40));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 32));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 24));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 16));
         this.poolContent[this.currentOffset++] = (byte)((int)(temp >>> 8));
         this.poolContent[this.currentOffset++] = (byte)((int)temp);
      }

      return index;
   }

   public int literalIndex(float key) {
      if (this.floatCache == null) {
         this.floatCache = new FloatCache(3);
      }

      int index;
      if ((index = this.floatCache.putIfAbsent(key, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         ++this.currentIndex;
         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(4);
         int temp = Float.floatToIntBits(key);
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.poolContent[this.currentOffset++] = (byte)(temp >>> 24);
         this.poolContent[this.currentOffset++] = (byte)(temp >>> 16);
         this.poolContent[this.currentOffset++] = (byte)(temp >>> 8);
         this.poolContent[this.currentOffset++] = (byte)temp;
      }

      return index;
   }

   public int literalIndex(int key) {
      if (this.intCache == null) {
         this.intCache = new IntegerCache(248);
      }

      int index;
      if ((index = this.intCache.putIfAbsent(key, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(3);
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.poolContent[this.currentOffset++] = (byte)(key >>> 24);
         this.poolContent[this.currentOffset++] = (byte)(key >>> 16);
         this.poolContent[this.currentOffset++] = (byte)(key >>> 8);
         this.poolContent[this.currentOffset++] = (byte)key;
      }

      return index;
   }

   public int literalIndex(long key) {
      if (this.longCache == null) {
         this.longCache = new LongCache(5);
      }

      int index;
      if ((index = this.longCache.putIfAbsent(key, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         this.currentIndex += 2;
         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(5);
         if (this.currentOffset + 8 >= this.poolContent.length) {
            this.resizePoolContents(8);
         }

         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 56));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 48));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 40));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 32));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 24));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 16));
         this.poolContent[this.currentOffset++] = (byte)((int)(key >>> 8));
         this.poolContent[this.currentOffset++] = (byte)((int)key);
      }

      return index;
   }

   public int literalIndex(String stringConstant) {
      char[] stringCharArray = stringConstant.toCharArray();
      int index;
      if ((index = this.stringCache.putIfAbsent(stringCharArray, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(8);
         int stringIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int stringIndex = this.literalIndex(stringCharArray);
         this.poolContent[stringIndexOffset++] = (byte)(stringIndex >> 8);
         this.poolContent[stringIndexOffset] = (byte)stringIndex;
      }

      return index;
   }

   public int literalIndexForModule(char[] moduleName) {
      int index;
      if ((index = this.moduleCache.putIfAbsent(moduleName, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(19);
         int stringIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int stringIndex = this.literalIndex(moduleName);
         this.poolContent[stringIndexOffset++] = (byte)(stringIndex >> 8);
         this.poolContent[stringIndexOffset] = (byte)stringIndex;
      }

      return index;
   }

   public int literalIndexForPackage(char[] packageName) {
      int index;
      if ((index = this.packageCache.putIfAbsent(packageName, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(20);
         int stringIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int stringIndex = this.literalIndex(packageName);
         this.poolContent[stringIndexOffset++] = (byte)(stringIndex >> 8);
         this.poolContent[stringIndexOffset] = (byte)stringIndex;
      }

      return index;
   }

   public int literalIndexForType(char[] constantPoolName) {
      int index;
      if ((index = this.classCache.putIfAbsent(constantPoolName, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(7);
         int nameIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int nameIndex = this.literalIndex(constantPoolName);
         this.poolContent[nameIndexOffset++] = (byte)(nameIndex >> 8);
         this.poolContent[nameIndexOffset] = (byte)nameIndex;
      }

      return index;
   }

   public int literalIndexForType(TypeBinding binding) {
      TypeBinding typeBinding = binding.leafComponentType();
      if ((typeBinding.tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, typeBinding);
      }

      return this.literalIndexForType(binding.constantPoolName());
   }

   public int literalIndexForMethod(char[] declaringClass, char[] selector, char[] signature, boolean isInterface) {
      int index;
      if ((index = this.putInCacheIfAbsent(declaringClass, selector, signature, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(isInterface ? 11 : 10);
         int classIndexOffset = this.currentOffset;
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.currentOffset += 4;
         int classIndex = this.literalIndexForType(declaringClass);
         int nameAndTypeIndex = this.literalIndexForNameAndType(selector, signature);
         this.poolContent[classIndexOffset++] = (byte)(classIndex >> 8);
         this.poolContent[classIndexOffset++] = (byte)classIndex;
         this.poolContent[classIndexOffset++] = (byte)(nameAndTypeIndex >> 8);
         this.poolContent[classIndexOffset] = (byte)nameAndTypeIndex;
      }

      return index;
   }

   public int literalIndexForMethod(TypeBinding declaringClass, char[] selector, char[] signature, boolean isInterface) {
      if ((declaringClass.tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, declaringClass);
      }

      return this.literalIndexForMethod(declaringClass.constantPoolName(), selector, signature, isInterface);
   }

   public int literalIndexForNameAndType(char[] name, char[] signature) {
      int index;
      if ((index = this.putInNameAndTypeCacheIfAbsent(name, signature, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(12);
         int nameIndexOffset = this.currentOffset;
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.currentOffset += 4;
         int nameIndex = this.literalIndex(name);
         int typeIndex = this.literalIndex(signature);
         this.poolContent[nameIndexOffset++] = (byte)(nameIndex >> 8);
         this.poolContent[nameIndexOffset++] = (byte)nameIndex;
         this.poolContent[nameIndexOffset++] = (byte)(typeIndex >> 8);
         this.poolContent[nameIndexOffset] = (byte)typeIndex;
      }

      return index;
   }

   public int literalIndexForMethodHandle(MethodBinding binding) {
      boolean isInterface = binding.declaringClass.isInterface();
      int referenceKind = isInterface ? (binding.isStatic() ? 6 : (binding.isPrivate() ? 7 : 9)) : (binding.isConstructor() ? 8 : (binding.isStatic() ? 6 : (binding.isPrivate() ? 7 : 5)));
      return this.literalIndexForMethodHandle(referenceKind, binding.declaringClass, binding.selector, binding.signature(), isInterface);
   }

   public int literalIndexForMethodHandle(int referenceKind, TypeBinding declaringClass, char[] selector, char[] signature, boolean isInterface) {
      int indexForMethod = this.literalIndexForMethod(declaringClass, selector, signature, isInterface);
      int index = this.currentIndex++;
      int length = this.offsets.length;
      if (length <= index) {
         System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
      }

      this.offsets[index] = this.currentOffset;
      this.writeU1(15);
      this.writeU1(referenceKind);
      this.writeU2(indexForMethod);
      return index;
   }

   public int literalIndexForMethodType(char[] descriptor) {
      int signatureIndex = this.literalIndex(descriptor);
      int index = this.currentIndex++;
      int length = this.offsets.length;
      if (length <= index) {
         System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
      }

      this.offsets[index] = this.currentOffset;
      this.writeU1(16);
      this.writeU2(signatureIndex);
      return index;
   }

   private int literalIndexForInvokeAndConstantDynamic(int bootStrapIndex, char[] selector, char[] descriptor, int tag) {
      int index;
      if ((index = this.putInDynamicCacheIfAbsent(bootStrapIndex, selector, descriptor, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(tag);
         int classIndexOffset = this.currentOffset;
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.currentOffset += 4;
         int nameAndTypeIndex = this.literalIndexForNameAndType(selector, descriptor);
         this.poolContent[classIndexOffset++] = (byte)(bootStrapIndex >> 8);
         this.poolContent[classIndexOffset++] = (byte)bootStrapIndex;
         this.poolContent[classIndexOffset++] = (byte)(nameAndTypeIndex >> 8);
         this.poolContent[classIndexOffset] = (byte)nameAndTypeIndex;
      }

      return index;
   }

   public int literalIndexForDynamic(int bootStrapIndex, char[] selector, char[] descriptor) {
      return this.literalIndexForInvokeAndConstantDynamic(bootStrapIndex, selector, descriptor, 17);
   }

   public int literalIndexForInvokeDynamic(int bootStrapIndex, char[] selector, char[] descriptor) {
      return this.literalIndexForInvokeAndConstantDynamic(bootStrapIndex, selector, descriptor, 18);
   }

   public int literalIndexForField(char[] declaringClass, char[] name, char[] signature) {
      int index;
      if ((index = this.putInCacheIfAbsent(declaringClass, name, signature, this.currentIndex)) < 0) {
         ++this.currentIndex;
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(9);
         int classIndexOffset = this.currentOffset;
         if (this.currentOffset + 4 >= this.poolContent.length) {
            this.resizePoolContents(4);
         }

         this.currentOffset += 4;
         int classIndex = this.literalIndexForType(declaringClass);
         int nameAndTypeIndex = this.literalIndexForNameAndType(name, signature);
         this.poolContent[classIndexOffset++] = (byte)(classIndex >> 8);
         this.poolContent[classIndexOffset++] = (byte)classIndex;
         this.poolContent[classIndexOffset++] = (byte)(nameAndTypeIndex >> 8);
         this.poolContent[classIndexOffset] = (byte)nameAndTypeIndex;
      }

      return index;
   }

   public int literalIndexForLdc(char[] stringCharArray) {
      int savedCurrentIndex = this.currentIndex;
      int savedCurrentOffset = this.currentOffset;
      int index;
      if ((index = this.stringCache.putIfAbsent(stringCharArray, this.currentIndex)) < 0) {
         if ((index = -index) > 65535) {
            this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
         }

         ++this.currentIndex;
         int length = this.offsets.length;
         if (length <= index) {
            System.arraycopy(this.offsets, 0, this.offsets = new int[index * 2], 0, length);
         }

         this.offsets[index] = this.currentOffset;
         this.writeU1(8);
         int stringIndexOffset = this.currentOffset;
         if (this.currentOffset + 2 >= this.poolContent.length) {
            this.resizePoolContents(2);
         }

         this.currentOffset += 2;
         int stringIndex;
         if ((stringIndex = this.UTF8Cache.putIfAbsent(stringCharArray, this.currentIndex)) < 0) {
            if ((stringIndex = -stringIndex) > 65535) {
               this.classFile.referenceBinding.scope.problemReporter().noMoreAvailableSpaceInConstantPool(this.classFile.referenceBinding.scope.referenceType());
            }

            ++this.currentIndex;
            length = this.offsets.length;
            if (length <= stringIndex) {
               System.arraycopy(this.offsets, 0, this.offsets = new int[stringIndex * 2], 0, length);
            }

            this.offsets[stringIndex] = this.currentOffset;
            this.writeU1(1);
            int lengthOffset = this.currentOffset;
            if (this.currentOffset + 2 >= this.poolContent.length) {
               this.resizePoolContents(2);
            }

            this.currentOffset += 2;
            length = 0;
            int i = 0;

            while(true) {
               if (i >= stringCharArray.length) {
                  if (length >= 65535) {
                     this.currentOffset = savedCurrentOffset;
                     this.currentIndex = savedCurrentIndex;
                     this.stringCache.remove(stringCharArray);
                     this.UTF8Cache.remove(stringCharArray);
                     return 0;
                  }

                  this.poolContent[lengthOffset++] = (byte)(length >> 8);
                  this.poolContent[lengthOffset] = (byte)length;
                  break;
               }

               char current = stringCharArray[i];
               if (current >= 1 && current <= 127) {
                  ++length;
                  if (this.currentOffset + 1 >= this.poolContent.length) {
                     this.resizePoolContents(1);
                  }

                  this.poolContent[this.currentOffset++] = (byte)current;
               } else if (current > 2047) {
                  length += 3;
                  if (this.currentOffset + 3 >= this.poolContent.length) {
                     this.resizePoolContents(3);
                  }

                  this.poolContent[this.currentOffset++] = (byte)(224 | current >> 12 & 15);
                  this.poolContent[this.currentOffset++] = (byte)(128 | current >> 6 & 63);
                  this.poolContent[this.currentOffset++] = (byte)(128 | current & 63);
               } else {
                  if (this.currentOffset + 2 >= this.poolContent.length) {
                     this.resizePoolContents(2);
                  }

                  length += 2;
                  this.poolContent[this.currentOffset++] = (byte)(192 | current >> 6 & 31);
                  this.poolContent[this.currentOffset++] = (byte)(128 | current & 63);
               }

               ++i;
            }
         }

         this.poolContent[stringIndexOffset++] = (byte)(stringIndex >> 8);
         this.poolContent[stringIndexOffset] = (byte)stringIndex;
      }

      return index;
   }

   private int putInNameAndTypeCacheIfAbsent(char[] key1, char[] key2, int value) {
      Object key1Value = this.nameAndTypeCacheForFieldsAndMethods.get(key1);
      int index;
      CachedIndexEntry entry;
      if (key1Value == null) {
         entry = new CachedIndexEntry(key2, value);
         index = -value;
         this.nameAndTypeCacheForFieldsAndMethods.put(key1, entry);
      } else if (key1Value instanceof CachedIndexEntry) {
         entry = (CachedIndexEntry)key1Value;
         if (CharOperation.equals(key2, entry.signature)) {
            index = entry.index;
         } else {
            CharArrayCache charArrayCache = new CharArrayCache();
            charArrayCache.putIfAbsent(entry.signature, entry.index);
            index = charArrayCache.putIfAbsent(key2, value);
            this.nameAndTypeCacheForFieldsAndMethods.put(key1, charArrayCache);
         }
      } else {
         CharArrayCache charArrayCache = (CharArrayCache)key1Value;
         index = charArrayCache.putIfAbsent(key2, value);
      }

      return index;
   }

   private int putInDynamicCacheIfAbsent(int bootstrapIndex, char[] selector, char[] descriptor, int value) {
      HashtableOfObject key1Value = (HashtableOfObject)this.dynamicCache.get(bootstrapIndex);
      int index;
      if (key1Value == null) {
         key1Value = new HashtableOfObject();
         this.dynamicCache.put(bootstrapIndex, key1Value);
         CachedIndexEntry cachedIndexEntry = new CachedIndexEntry(descriptor, value);
         index = -value;
         key1Value.put(selector, cachedIndexEntry);
      } else {
         Object key2Value = key1Value.get(selector);
         CachedIndexEntry entry;
         if (key2Value == null) {
            entry = new CachedIndexEntry(descriptor, value);
            index = -value;
            key1Value.put(selector, entry);
         } else if (key2Value instanceof CachedIndexEntry) {
            entry = (CachedIndexEntry)key2Value;
            if (CharOperation.equals(descriptor, entry.signature)) {
               index = entry.index;
            } else {
               CharArrayCache charArrayCache = new CharArrayCache();
               charArrayCache.putIfAbsent(entry.signature, entry.index);
               index = charArrayCache.putIfAbsent(descriptor, value);
               key1Value.put(selector, charArrayCache);
            }
         } else {
            CharArrayCache charArrayCache = (CharArrayCache)key2Value;
            index = charArrayCache.putIfAbsent(descriptor, value);
         }
      }

      return index;
   }

   private int putInCacheIfAbsent(char[] key1, char[] key2, char[] key3, int value) {
      HashtableOfObject key1Value = (HashtableOfObject)this.methodsAndFieldsCache.get(key1);
      int index;
      if (key1Value == null) {
         key1Value = new HashtableOfObject();
         this.methodsAndFieldsCache.put(key1, key1Value);
         CachedIndexEntry cachedIndexEntry = new CachedIndexEntry(key3, value);
         index = -value;
         key1Value.put(key2, cachedIndexEntry);
      } else {
         Object key2Value = key1Value.get(key2);
         CachedIndexEntry entry;
         if (key2Value == null) {
            entry = new CachedIndexEntry(key3, value);
            index = -value;
            key1Value.put(key2, entry);
         } else if (key2Value instanceof CachedIndexEntry) {
            entry = (CachedIndexEntry)key2Value;
            if (CharOperation.equals(key3, entry.signature)) {
               index = entry.index;
            } else {
               CharArrayCache charArrayCache = new CharArrayCache();
               charArrayCache.putIfAbsent(entry.signature, entry.index);
               index = charArrayCache.putIfAbsent(key3, value);
               key1Value.put(key2, charArrayCache);
            }
         } else {
            CharArrayCache charArrayCache = (CharArrayCache)key2Value;
            index = charArrayCache.putIfAbsent(key3, value);
         }
      }

      return index;
   }

   public void resetForClinit(int constantPoolIndex, int constantPoolOffset) {
      this.currentIndex = constantPoolIndex;
      this.currentOffset = constantPoolOffset;
      if (this.UTF8Cache.get(AttributeNamesConstants.CodeName) >= constantPoolIndex) {
         this.UTF8Cache.remove(AttributeNamesConstants.CodeName);
      }

      if (this.UTF8Cache.get(ClinitSignature) >= constantPoolIndex) {
         this.UTF8Cache.remove(ClinitSignature);
      }

      if (this.UTF8Cache.get(Clinit) >= constantPoolIndex) {
         this.UTF8Cache.remove(Clinit);
      }

   }

   private final void resizePoolContents(int minimalSize) {
      int length = this.poolContent.length;
      int toAdd = length;
      if (length < minimalSize) {
         toAdd = minimalSize;
      }

      System.arraycopy(this.poolContent, 0, this.poolContent = new byte[length + toAdd], 0, length);
   }

   protected final void writeU1(int value) {
      if (this.currentOffset + 1 >= this.poolContent.length) {
         this.resizePoolContents(1);
      }

      this.poolContent[this.currentOffset++] = (byte)value;
   }

   protected final void writeU2(int value) {
      if (this.currentOffset + 2 >= this.poolContent.length) {
         this.resizePoolContents(2);
      }

      this.poolContent[this.currentOffset++] = (byte)(value >>> 8);
      this.poolContent[this.currentOffset++] = (byte)value;
   }

   public void reset() {
      if (this.doubleCache != null) {
         this.doubleCache.clear();
      }

      if (this.floatCache != null) {
         this.floatCache.clear();
      }

      if (this.intCache != null) {
         this.intCache.clear();
      }

      if (this.longCache != null) {
         this.longCache.clear();
      }

      this.UTF8Cache.clear();
      this.stringCache.clear();
      this.methodsAndFieldsCache.clear();
      this.classCache.clear();
      this.packageCache.clear();
      this.moduleCache.clear();
      this.nameAndTypeCacheForFieldsAndMethods.clear();
      this.dynamicCache.clear();
      this.currentIndex = 1;
      this.currentOffset = 0;
   }
}
