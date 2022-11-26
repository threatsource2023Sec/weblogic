package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

public interface ClassFileConstants {
   int AccDefault = 0;
   int AccPublic = 1;
   int AccPrivate = 2;
   int AccProtected = 4;
   int AccStatic = 8;
   int AccFinal = 16;
   int AccSynchronized = 32;
   int AccVolatile = 64;
   int AccBridge = 64;
   int AccTransient = 128;
   int AccVarargs = 128;
   int AccNative = 256;
   int AccInterface = 512;
   int AccAbstract = 1024;
   int AccStrictfp = 2048;
   int AccSynthetic = 4096;
   int AccAnnotation = 8192;
   int AccEnum = 16384;
   int AccModule = 32768;
   int AccMandated = 32768;
   int ACC_OPEN = 32;
   int ACC_TRANSITIVE = 32;
   int ACC_STATIC_PHASE = 64;
   int ACC_SYNTHETIC = 4096;
   int AccSuper = 32;
   int AccAnnotationDefault = 131072;
   int AccDeprecated = 1048576;
   int Utf8Tag = 1;
   int IntegerTag = 3;
   int FloatTag = 4;
   int LongTag = 5;
   int DoubleTag = 6;
   int ClassTag = 7;
   int StringTag = 8;
   int FieldRefTag = 9;
   int MethodRefTag = 10;
   int InterfaceMethodRefTag = 11;
   int NameAndTypeTag = 12;
   int MethodHandleTag = 15;
   int MethodTypeTag = 16;
   int DynamicTag = 17;
   int InvokeDynamicTag = 18;
   int ModuleTag = 19;
   int PackageTag = 20;
   int ConstantMethodRefFixedSize = 5;
   int ConstantClassFixedSize = 3;
   int ConstantDoubleFixedSize = 9;
   int ConstantFieldRefFixedSize = 5;
   int ConstantFloatFixedSize = 5;
   int ConstantIntegerFixedSize = 5;
   int ConstantInterfaceMethodRefFixedSize = 5;
   int ConstantLongFixedSize = 9;
   int ConstantStringFixedSize = 3;
   int ConstantUtf8FixedSize = 3;
   int ConstantNameAndTypeFixedSize = 5;
   int ConstantMethodHandleFixedSize = 4;
   int ConstantMethodTypeFixedSize = 3;
   int ConstantDynamicFixedSize = 5;
   int ConstantInvokeDynamicFixedSize = 5;
   int ConstantModuleFixedSize = 3;
   int ConstantPackageFixedSize = 3;
   int MethodHandleRefKindGetField = 1;
   int MethodHandleRefKindGetStatic = 2;
   int MethodHandleRefKindPutField = 3;
   int MethodHandleRefKindPutStatic = 4;
   int MethodHandleRefKindInvokeVirtual = 5;
   int MethodHandleRefKindInvokeStatic = 6;
   int MethodHandleRefKindInvokeSpecial = 7;
   int MethodHandleRefKindNewInvokeSpecial = 8;
   int MethodHandleRefKindInvokeInterface = 9;
   int MAJOR_VERSION_1_1 = 45;
   int MAJOR_VERSION_1_2 = 46;
   int MAJOR_VERSION_1_3 = 47;
   int MAJOR_VERSION_1_4 = 48;
   int MAJOR_VERSION_1_5 = 49;
   int MAJOR_VERSION_1_6 = 50;
   int MAJOR_VERSION_1_7 = 51;
   int MAJOR_VERSION_1_8 = 52;
   int MAJOR_VERSION_9 = 53;
   int MAJOR_VERSION_10 = 54;
   int MAJOR_VERSION_11 = 55;
   int MAJOR_VERSION_12 = 56;
   int MAJOR_VERSION_0 = 44;
   int MAJOR_LATEST_VERSION = 56;
   int MINOR_VERSION_0 = 0;
   int MINOR_VERSION_1 = 1;
   int MINOR_VERSION_2 = 2;
   int MINOR_VERSION_3 = 3;
   int MINOR_VERSION_4 = 4;
   int MINOR_VERSION_PREVIEW = 65535;
   long JDK1_1 = 2949123L;
   long JDK1_2 = 3014656L;
   long JDK1_3 = 3080192L;
   long JDK1_4 = 3145728L;
   long JDK1_5 = 3211264L;
   long JDK1_6 = 3276800L;
   long JDK1_7 = 3342336L;
   long JDK1_8 = 3407872L;
   long JDK9 = 3473408L;
   long JDK10 = 3538944L;
   long JDK11 = 3604480L;
   long JDK12 = 3670016L;
   long CLDC_1_1 = 2949124L;
   long JDK_DEFERRED = Long.MAX_VALUE;
   int INT_ARRAY = 10;
   int BYTE_ARRAY = 8;
   int BOOLEAN_ARRAY = 4;
   int SHORT_ARRAY = 9;
   int CHAR_ARRAY = 5;
   int LONG_ARRAY = 11;
   int FLOAT_ARRAY = 6;
   int DOUBLE_ARRAY = 7;
   int ATTR_SOURCE = 1;
   int ATTR_LINES = 2;
   int ATTR_VARS = 4;
   int ATTR_STACK_MAP_TABLE = 8;
   int ATTR_STACK_MAP = 16;
   int ATTR_TYPE_ANNOTATION = 32;
   int ATTR_METHOD_PARAMETERS = 64;
   int FLAG_SERIALIZABLE = 1;
   int FLAG_MARKERS = 2;
   int FLAG_BRIDGES = 4;

   static long getLatestJDKLevel() {
      return 3670016L;
   }

   static long getComplianceLevelForJavaVersion(int major) {
      switch (major) {
         case 45:
            return 2949123L;
         default:
            major = Math.min(major, 56);
            return ((long)major << 16) + 0L;
      }
   }
}
