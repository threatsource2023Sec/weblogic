package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public interface TypeConstants {
   char[] JAVA = "java".toCharArray();
   char[] JAVAX = "javax".toCharArray();
   char[] LANG = "lang".toCharArray();
   char[] IO = "io".toCharArray();
   char[] UTIL = "util".toCharArray();
   char[] ZIP = "zip".toCharArray();
   char[] ANNOTATION = "annotation".toCharArray();
   char[] REFLECT = "reflect".toCharArray();
   char[] LENGTH = "length".toCharArray();
   char[] CLONE = "clone".toCharArray();
   char[] EQUALS = "equals".toCharArray();
   char[] GETCLASS = "getClass".toCharArray();
   char[] HASHCODE = "hashCode".toCharArray();
   char[] OBJECT = "Object".toCharArray();
   char[] MAIN = "main".toCharArray();
   char[] SERIALVERSIONUID = "serialVersionUID".toCharArray();
   char[] SERIALPERSISTENTFIELDS = "serialPersistentFields".toCharArray();
   char[] READRESOLVE = "readResolve".toCharArray();
   char[] WRITEREPLACE = "writeReplace".toCharArray();
   char[] READOBJECT = "readObject".toCharArray();
   char[] WRITEOBJECT = "writeObject".toCharArray();
   char[] CharArray_JAVA_LANG_OBJECT = "java.lang.Object".toCharArray();
   char[] CharArray_JAVA_LANG_ENUM = "java.lang.Enum".toCharArray();
   char[] CharArray_JAVA_LANG_ANNOTATION_ANNOTATION = "java.lang.annotation.Annotation".toCharArray();
   char[] CharArray_JAVA_IO_OBJECTINPUTSTREAM = "java.io.ObjectInputStream".toCharArray();
   char[] CharArray_JAVA_IO_OBJECTOUTPUTSTREAM = "java.io.ObjectOutputStream".toCharArray();
   char[] CharArray_JAVA_IO_OBJECTSTREAMFIELD = "java.io.ObjectStreamField".toCharArray();
   char[] ANONYM_PREFIX = "new ".toCharArray();
   char[] ANONYM_SUFFIX = "(){}".toCharArray();
   char[] WILDCARD_NAME = new char[]{'?'};
   char[] WILDCARD_SUPER = " super ".toCharArray();
   char[] WILDCARD_EXTENDS = " extends ".toCharArray();
   char[] WILDCARD_MINUS = new char[]{'-'};
   char[] WILDCARD_STAR = new char[]{'*'};
   char[] WILDCARD_PLUS = new char[]{'+'};
   char[] WILDCARD_CAPTURE_NAME_PREFIX = "capture#".toCharArray();
   char[] WILDCARD_CAPTURE_NAME_SUFFIX = "-of ".toCharArray();
   char[] WILDCARD_CAPTURE_SIGNABLE_NAME_SUFFIX = "capture-of ".toCharArray();
   char[] WILDCARD_CAPTURE = new char[]{'!'};
   char[] CAPTURE18 = new char[]{'^'};
   char[] BYTE = "byte".toCharArray();
   char[] SHORT = "short".toCharArray();
   char[] INT = "int".toCharArray();
   char[] LONG = "long".toCharArray();
   char[] FLOAT = "float".toCharArray();
   char[] DOUBLE = "double".toCharArray();
   char[] CHAR = "char".toCharArray();
   char[] BOOLEAN = "boolean".toCharArray();
   char[] NULL = "null".toCharArray();
   char[] VOID = "void".toCharArray();
   char[] VALUE = "value".toCharArray();
   char[] VALUES = "values".toCharArray();
   char[] VALUEOF = "valueOf".toCharArray();
   char[] UPPER_SOURCE = "SOURCE".toCharArray();
   char[] UPPER_CLASS = "CLASS".toCharArray();
   char[] UPPER_RUNTIME = "RUNTIME".toCharArray();
   char[] ANNOTATION_PREFIX = "@".toCharArray();
   char[] ANNOTATION_SUFFIX = "()".toCharArray();
   char[] TYPE = "TYPE".toCharArray();
   char[] UPPER_FIELD = "FIELD".toCharArray();
   char[] UPPER_METHOD = "METHOD".toCharArray();
   char[] UPPER_PARAMETER = "PARAMETER".toCharArray();
   char[] UPPER_CONSTRUCTOR = "CONSTRUCTOR".toCharArray();
   char[] UPPER_LOCAL_VARIABLE = "LOCAL_VARIABLE".toCharArray();
   char[] UPPER_ANNOTATION_TYPE = "ANNOTATION_TYPE".toCharArray();
   char[] UPPER_PACKAGE = "PACKAGE".toCharArray();
   char[] ANONYMOUS_METHOD = "lambda$".toCharArray();
   char[] DESERIALIZE_LAMBDA = "$deserializeLambda$".toCharArray();
   char[] LAMBDA_TYPE = "<lambda>".toCharArray();
   char[] UPPER_MODULE = "MODULE".toCharArray();
   char[] VAR = "var".toCharArray();
   char[] TYPE_USE_TARGET = "TYPE_USE".toCharArray();
   char[] TYPE_PARAMETER_TARGET = "TYPE_PARAMETER".toCharArray();
   char[] ORG = "org".toCharArray();
   char[] ECLIPSE = "eclipse".toCharArray();
   char[] CORE = "core".toCharArray();
   char[] RUNTIME = "runtime".toCharArray();
   char[] APACHE = "apache".toCharArray();
   char[] COMMONS = "commons".toCharArray();
   char[] LANG3 = "lang3".toCharArray();
   char[] COM = "com".toCharArray();
   char[] GOOGLE = "google".toCharArray();
   char[] JDT = "jdt".toCharArray();
   char[] INTERNAL = "internal".toCharArray();
   char[] COMPILER = "compiler".toCharArray();
   char[] LOOKUP = "lookup".toCharArray();
   char[] TYPEBINDING = "TypeBinding".toCharArray();
   char[] DOM = "dom".toCharArray();
   char[] ITYPEBINDING = "ITypeBinding".toCharArray();
   char[] SPRING = "springframework".toCharArray();
   char[][] JAVA_LANG = new char[][]{JAVA, LANG};
   char[][] JAVA_IO = new char[][]{JAVA, IO};
   char[][] JAVA_LANG_ANNOTATION = new char[][]{JAVA, LANG, ANNOTATION};
   char[][] JAVA_LANG_ANNOTATION_ANNOTATION = new char[][]{JAVA, LANG, ANNOTATION, "Annotation".toCharArray()};
   char[][] JAVA_LANG_ASSERTIONERROR = new char[][]{JAVA, LANG, "AssertionError".toCharArray()};
   char[][] JAVA_LANG_CLASS = new char[][]{JAVA, LANG, "Class".toCharArray()};
   char[][] JAVA_LANG_CLASSNOTFOUNDEXCEPTION = new char[][]{JAVA, LANG, "ClassNotFoundException".toCharArray()};
   char[][] JAVA_LANG_CLONEABLE = new char[][]{JAVA, LANG, "Cloneable".toCharArray()};
   char[][] JAVA_LANG_ENUM = new char[][]{JAVA, LANG, "Enum".toCharArray()};
   char[][] JAVA_LANG_EXCEPTION = new char[][]{JAVA, LANG, "Exception".toCharArray()};
   char[][] JAVA_LANG_ERROR = new char[][]{JAVA, LANG, "Error".toCharArray()};
   char[][] JAVA_LANG_ILLEGALARGUMENTEXCEPTION = new char[][]{JAVA, LANG, "IllegalArgumentException".toCharArray()};
   char[][] JAVA_LANG_ITERABLE = new char[][]{JAVA, LANG, "Iterable".toCharArray()};
   char[][] JAVA_LANG_NOCLASSDEFERROR = new char[][]{JAVA, LANG, "NoClassDefError".toCharArray()};
   char[][] JAVA_LANG_OBJECT = new char[][]{JAVA, LANG, OBJECT};
   char[][] JAVA_LANG_STRING = new char[][]{JAVA, LANG, "String".toCharArray()};
   char[][] JAVA_LANG_STRINGBUFFER = new char[][]{JAVA, LANG, "StringBuffer".toCharArray()};
   char[][] JAVA_LANG_STRINGBUILDER = new char[][]{JAVA, LANG, "StringBuilder".toCharArray()};
   char[][] JAVA_LANG_SYSTEM = new char[][]{JAVA, LANG, "System".toCharArray()};
   char[][] JAVA_LANG_RUNTIMEEXCEPTION = new char[][]{JAVA, LANG, "RuntimeException".toCharArray()};
   char[][] JAVA_LANG_THROWABLE = new char[][]{JAVA, LANG, "Throwable".toCharArray()};
   char[][] JAVA_LANG_REFLECT_CONSTRUCTOR = new char[][]{JAVA, LANG, REFLECT, "Constructor".toCharArray()};
   char[][] JAVA_IO_PRINTSTREAM = new char[][]{JAVA, IO, "PrintStream".toCharArray()};
   char[][] JAVA_IO_SERIALIZABLE = new char[][]{JAVA, IO, "Serializable".toCharArray()};
   char[][] JAVA_LANG_BYTE = new char[][]{JAVA, LANG, "Byte".toCharArray()};
   char[][] JAVA_LANG_SHORT = new char[][]{JAVA, LANG, "Short".toCharArray()};
   char[][] JAVA_LANG_CHARACTER = new char[][]{JAVA, LANG, "Character".toCharArray()};
   char[][] JAVA_LANG_INTEGER = new char[][]{JAVA, LANG, "Integer".toCharArray()};
   char[][] JAVA_LANG_LONG = new char[][]{JAVA, LANG, "Long".toCharArray()};
   char[][] JAVA_LANG_FLOAT = new char[][]{JAVA, LANG, "Float".toCharArray()};
   char[][] JAVA_LANG_DOUBLE = new char[][]{JAVA, LANG, "Double".toCharArray()};
   char[][] JAVA_LANG_BOOLEAN = new char[][]{JAVA, LANG, "Boolean".toCharArray()};
   char[][] JAVA_LANG_VOID = new char[][]{JAVA, LANG, "Void".toCharArray()};
   char[][] JAVA_UTIL_COLLECTION = new char[][]{JAVA, UTIL, "Collection".toCharArray()};
   char[][] JAVA_UTIL_ITERATOR = new char[][]{JAVA, UTIL, "Iterator".toCharArray()};
   char[][] JAVA_UTIL_OBJECTS = new char[][]{JAVA, UTIL, "Objects".toCharArray()};
   char[][] JAVA_UTIL_LIST = new char[][]{JAVA, UTIL, "List".toCharArray()};
   char[][] JAVA_LANG_DEPRECATED = new char[][]{JAVA, LANG, "Deprecated".toCharArray()};
   char[] FOR_REMOVAL = "forRemoval".toCharArray();
   char[] SINCE = "since".toCharArray();
   char[][] JAVA_LANG_ANNOTATION_DOCUMENTED = new char[][]{JAVA, LANG, ANNOTATION, "Documented".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_INHERITED = new char[][]{JAVA, LANG, ANNOTATION, "Inherited".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_REPEATABLE = new char[][]{JAVA, LANG, ANNOTATION, "Repeatable".toCharArray()};
   char[][] JAVA_LANG_OVERRIDE = new char[][]{JAVA, LANG, "Override".toCharArray()};
   char[][] JAVA_LANG_FUNCTIONAL_INTERFACE = new char[][]{JAVA, LANG, "FunctionalInterface".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_RETENTION = new char[][]{JAVA, LANG, ANNOTATION, "Retention".toCharArray()};
   char[][] JAVA_LANG_SUPPRESSWARNINGS = new char[][]{JAVA, LANG, "SuppressWarnings".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_TARGET = new char[][]{JAVA, LANG, ANNOTATION, "Target".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_RETENTIONPOLICY = new char[][]{JAVA, LANG, ANNOTATION, "RetentionPolicy".toCharArray()};
   char[][] JAVA_LANG_ANNOTATION_ELEMENTTYPE = new char[][]{JAVA, LANG, ANNOTATION, "ElementType".toCharArray()};
   char[][] JAVA_LANG_REFLECT_FIELD = new char[][]{JAVA, LANG, REFLECT, "Field".toCharArray()};
   char[][] JAVA_LANG_REFLECT_METHOD = new char[][]{JAVA, LANG, REFLECT, "Method".toCharArray()};
   char[][] JAVA_IO_CLOSEABLE = new char[][]{JAVA, IO, "Closeable".toCharArray()};
   char[][] JAVA_IO_OBJECTSTREAMEXCEPTION = new char[][]{JAVA, IO, "ObjectStreamException".toCharArray()};
   char[][] JAVA_IO_EXTERNALIZABLE = new char[][]{JAVA, IO, "Externalizable".toCharArray()};
   char[][] JAVA_IO_IOEXCEPTION = new char[][]{JAVA, IO, "IOException".toCharArray()};
   char[][] JAVA_IO_OBJECTOUTPUTSTREAM = new char[][]{JAVA, IO, "ObjectOutputStream".toCharArray()};
   char[][] JAVA_IO_OBJECTINPUTSTREAM = new char[][]{JAVA, IO, "ObjectInputStream".toCharArray()};
   char[][] JAVA_NIO_FILE_FILES = new char[][]{JAVA, "nio".toCharArray(), "file".toCharArray(), "Files".toCharArray()};
   char[][] JAVAX_RMI_CORBA_STUB = new char[][]{JAVAX, "rmi".toCharArray(), "CORBA".toCharArray(), "Stub".toCharArray()};
   char[][] JAVA_LANG_SAFEVARARGS = new char[][]{JAVA, LANG, "SafeVarargs".toCharArray()};
   char[] INVOKE = "invoke".toCharArray();
   char[][] JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE = new char[][]{JAVA, LANG, INVOKE, "MethodHandle".toCharArray(), "PolymorphicSignature".toCharArray()};
   char[][] JAVA_LANG_INVOKE_METHODHANDLE_$_POLYMORPHICSIGNATURE = new char[][]{JAVA, LANG, INVOKE, "MethodHandle$PolymorphicSignature".toCharArray()};
   char[][] JAVA_LANG_INVOKE_LAMBDAMETAFACTORY = new char[][]{JAVA, LANG, INVOKE, "LambdaMetafactory".toCharArray()};
   char[][] JAVA_LANG_INVOKE_SERIALIZEDLAMBDA = new char[][]{JAVA, LANG, INVOKE, "SerializedLambda".toCharArray()};
   char[][] JAVA_LANG_INVOKE_METHODHANDLES = new char[][]{JAVA, LANG, INVOKE, "MethodHandles".toCharArray()};
   char[][] JAVA_LANG_AUTOCLOSEABLE = new char[][]{JAVA, LANG, "AutoCloseable".toCharArray()};
   char[] CLOSE = "close".toCharArray();
   char[][] GUAVA_CLOSEABLES = new char[][]{COM, GOOGLE, "common".toCharArray(), IO, "Closeables".toCharArray()};
   char[][] APACHE_IOUTILS = new char[][]{ORG, APACHE, COMMONS, IO, "IOUtils".toCharArray()};
   char[][] APACHE_DBUTILS = new char[][]{ORG, APACHE, COMMONS, "dbutils".toCharArray(), "DbUtils".toCharArray()};
   char[] CLOSE_QUIETLY = "closeQuietly".toCharArray();
   CloseMethodRecord[] closeMethods = new CloseMethodRecord[]{new CloseMethodRecord(GUAVA_CLOSEABLES, CLOSE_QUIETLY, 1), new CloseMethodRecord(GUAVA_CLOSEABLES, CLOSE, 1), new CloseMethodRecord(APACHE_IOUTILS, CLOSE_QUIETLY, 1), new CloseMethodRecord(APACHE_DBUTILS, CLOSE, 1), new CloseMethodRecord(APACHE_DBUTILS, CLOSE_QUIETLY, 3), new CloseMethodRecord(APACHE_DBUTILS, "commitAndClose".toCharArray(), 1), new CloseMethodRecord(APACHE_DBUTILS, "commitAndCloseQuietly".toCharArray(), 1), new CloseMethodRecord(APACHE_DBUTILS, "rollbackAndClose".toCharArray(), 1), new CloseMethodRecord(APACHE_DBUTILS, "rollbackAndCloseQuietly".toCharArray(), 1)};
   char[][] JAVA_IO_WRAPPER_CLOSEABLES = new char[][]{"BufferedInputStream".toCharArray(), "BufferedOutputStream".toCharArray(), "BufferedReader".toCharArray(), "BufferedWriter".toCharArray(), "InputStreamReader".toCharArray(), "PrintWriter".toCharArray(), "LineNumberReader".toCharArray(), "DataInputStream".toCharArray(), "DataOutputStream".toCharArray(), "ObjectInputStream".toCharArray(), "ObjectOutputStream".toCharArray(), "FilterInputStream".toCharArray(), "FilterOutputStream".toCharArray(), "DataInputStream".toCharArray(), "DataOutputStream".toCharArray(), "PushbackInputStream".toCharArray(), "SequenceInputStream".toCharArray(), "PrintStream".toCharArray(), "PushbackReader".toCharArray(), "OutputStreamWriter".toCharArray()};
   char[][] JAVA_UTIL_ZIP_WRAPPER_CLOSEABLES = new char[][]{"GZIPInputStream".toCharArray(), "InflaterInputStream".toCharArray(), "DeflaterInputStream".toCharArray(), "CheckedInputStream".toCharArray(), "ZipInputStream".toCharArray(), "JarInputStream".toCharArray(), "GZIPOutputStream".toCharArray(), "InflaterOutputStream".toCharArray(), "DeflaterOutputStream".toCharArray(), "CheckedOutputStream".toCharArray(), "ZipOutputStream".toCharArray(), "JarOutputStream".toCharArray()};
   char[][][] OTHER_WRAPPER_CLOSEABLES = new char[][][]{{JAVA, "security".toCharArray(), "DigestInputStream".toCharArray()}, {JAVA, "security".toCharArray(), "DigestOutputStream".toCharArray()}, {JAVA, "beans".toCharArray(), "XMLEncoder".toCharArray()}, {JAVA, "beans".toCharArray(), "XMLDecoder".toCharArray()}, {JAVAX, "sound".toCharArray(), "sampled".toCharArray(), "AudioInputStream".toCharArray()}};
   char[][] JAVA_IO_RESOURCE_FREE_CLOSEABLES = new char[][]{"StringReader".toCharArray(), "StringWriter".toCharArray(), "ByteArrayInputStream".toCharArray(), "ByteArrayOutputStream".toCharArray(), "CharArrayReader".toCharArray(), "CharArrayWriter".toCharArray(), "StringBufferInputStream".toCharArray()};
   char[][] JAVA_UTIL_STREAM = new char[][]{JAVA, UTIL, "stream".toCharArray()};
   char[][] RESOURCE_FREE_CLOSEABLE_J_U_STREAMS = new char[][]{"Stream".toCharArray(), "DoubleStream".toCharArray(), "LongStream".toCharArray(), "IntStream".toCharArray()};
   char[] ASSERT_CLASS = "Assert".toCharArray();
   char[][] ORG_ECLIPSE_CORE_RUNTIME_ASSERT = new char[][]{ORG, ECLIPSE, CORE, RUNTIME, ASSERT_CLASS};
   char[] IS_NOTNULL = "isNotNull".toCharArray();
   char[] JUNIT = "junit".toCharArray();
   char[] FRAMEWORK = "framework".toCharArray();
   char[] JUPITER = "jupiter".toCharArray();
   char[] PARAMS = "params".toCharArray();
   char[] PROVIDER = "provider".toCharArray();
   char[][] JUNIT_FRAMEWORK_ASSERT = new char[][]{JUNIT, FRAMEWORK, ASSERT_CLASS};
   char[][] ORG_JUNIT_ASSERT = new char[][]{ORG, JUNIT, ASSERT_CLASS};
   char[] ASSERT_NULL = "assertNull".toCharArray();
   char[] ASSERT_NOTNULL = "assertNotNull".toCharArray();
   char[] ASSERT_TRUE = "assertTrue".toCharArray();
   char[] ASSERT_FALSE = "assertFalse".toCharArray();
   char[] METHOD_SOURCE = "MethodSource".toCharArray();
   char[][] ORG_JUNIT_METHOD_SOURCE = new char[][]{ORG, JUNIT, JUPITER, PARAMS, PROVIDER, METHOD_SOURCE};
   char[] VALIDATE_CLASS = "Validate".toCharArray();
   char[][] ORG_APACHE_COMMONS_LANG_VALIDATE = new char[][]{ORG, APACHE, COMMONS, LANG, VALIDATE_CLASS};
   char[][] ORG_APACHE_COMMONS_LANG3_VALIDATE = new char[][]{ORG, APACHE, COMMONS, LANG3, VALIDATE_CLASS};
   char[][] ORG_ECLIPSE_JDT_INTERNAL_COMPILER_LOOKUP_TYPEBINDING = new char[][]{ORG, ECLIPSE, JDT, INTERNAL, COMPILER, LOOKUP, TYPEBINDING};
   char[][] ORG_ECLIPSE_JDT_CORE_DOM_ITYPEBINDING = new char[][]{ORG, ECLIPSE, JDT, CORE, DOM, ITYPEBINDING};
   char[] IS_TRUE = "isTrue".toCharArray();
   char[] NOT_NULL = "notNull".toCharArray();
   char[][] COM_GOOGLE_COMMON_BASE_PRECONDITIONS = new char[][]{COM, GOOGLE, "common".toCharArray(), "base".toCharArray(), "Preconditions".toCharArray()};
   char[] CHECK_NOT_NULL = "checkNotNull".toCharArray();
   char[] CHECK_ARGUMENT = "checkArgument".toCharArray();
   char[] CHECK_STATE = "checkState".toCharArray();
   char[] REQUIRE_NON_NULL = "requireNonNull".toCharArray();
   char[] INJECT_PACKAGE = "inject".toCharArray();
   char[] INJECT_TYPE = "Inject".toCharArray();
   char[][] JAVAX_ANNOTATION_INJECT_INJECT = new char[][]{JAVAX, INJECT_PACKAGE, INJECT_TYPE};
   char[][] COM_GOOGLE_INJECT_INJECT = new char[][]{COM, GOOGLE, INJECT_PACKAGE, INJECT_TYPE};
   char[] OPTIONAL = "optional".toCharArray();
   char[][] JAVA_UTIL_MAP = new char[][]{JAVA, UTIL, "Map".toCharArray()};
   char[] GET = "get".toCharArray();
   char[] REMOVE = "remove".toCharArray();
   char[] REMOVE_ALL = "removeAll".toCharArray();
   char[] CONTAINS_ALL = "containsAll".toCharArray();
   char[] RETAIN_ALL = "retainAll".toCharArray();
   char[] CONTAINS_KEY = "containsKey".toCharArray();
   char[] CONTAINS_VALUE = "containsValue".toCharArray();
   char[] CONTAINS = "contains".toCharArray();
   char[] INDEX_OF = "indexOf".toCharArray();
   char[] LAST_INDEX_OF = "lastIndexOf".toCharArray();
   char[] AUTOWIRED = "Autowired".toCharArray();
   char[] BEANS = "beans".toCharArray();
   char[] FACTORY = "factory".toCharArray();
   char[][] ORG_SPRING_AUTOWIRED = new char[][]{ORG, SPRING, BEANS, FACTORY, ANNOTATION, AUTOWIRED};
   char[] REQUIRED = "required".toCharArray();
   int CONSTRAINT_EQUAL = 0;
   int CONSTRAINT_EXTENDS = 1;
   int CONSTRAINT_SUPER = 2;
   char[] INIT = "<init>".toCharArray();
   char[] CLINIT = "<clinit>".toCharArray();
   char[] SYNTHETIC_SWITCH_ENUM_TABLE = "$SWITCH_TABLE$".toCharArray();
   char[] SYNTHETIC_ENUM_VALUES = "ENUM$VALUES".toCharArray();
   char[] SYNTHETIC_ASSERT_DISABLED = "$assertionsDisabled".toCharArray();
   char[] SYNTHETIC_CLASS = "class$".toCharArray();
   char[] SYNTHETIC_OUTER_LOCAL_PREFIX = "val$".toCharArray();
   char[] SYNTHETIC_ENCLOSING_INSTANCE_PREFIX = "this$".toCharArray();
   char[] SYNTHETIC_ACCESS_METHOD_PREFIX = "access$".toCharArray();
   char[] SYNTHETIC_ENUM_CONSTANT_INITIALIZATION_METHOD_PREFIX = " enum constant initialization$".toCharArray();
   char[] SYNTHETIC_STATIC_FACTORY = "<factory>".toCharArray();
   char[] DEFAULT_LOCATION__PARAMETER = "PARAMETER".toCharArray();
   char[] DEFAULT_LOCATION__RETURN_TYPE = "RETURN_TYPE".toCharArray();
   char[] DEFAULT_LOCATION__FIELD = "FIELD".toCharArray();
   char[] DEFAULT_LOCATION__TYPE_ARGUMENT = "TYPE_ARGUMENT".toCharArray();
   char[] DEFAULT_LOCATION__TYPE_PARAMETER = "TYPE_PARAMETER".toCharArray();
   char[] DEFAULT_LOCATION__TYPE_BOUND = "TYPE_BOUND".toCharArray();
   char[] DEFAULT_LOCATION__ARRAY_CONTENTS = "ARRAY_CONTENTS".toCharArray();
   char[] PACKAGE_INFO_NAME = "package-info".toCharArray();
   char[] MODULE_INFO_NAME = "module-info".toCharArray();
   String MODULE_INFO_NAME_STRING = "module-info";
   char[] MODULE_INFO_FILE_NAME = "module-info.java".toCharArray();
   char[] MODULE_INFO_CLASS_NAME = "module-info.class".toCharArray();
   String MODULE_INFO_FILE_NAME_STRING = "module-info.java";
   String MODULE_INFO_CLASS_NAME_STRING = "module-info.class";
   char[] JAVA_BASE = "java.base".toCharArray();
   String META_INF_MANIFEST_MF = "META-INF/MANIFEST.MF";
   String AUTOMATIC_MODULE_NAME = "Automatic-Module-Name";

   public static enum BoundCheckStatus {
      OK,
      NULL_PROBLEM,
      UNCHECKED,
      MISMATCH;

      // $FF: synthetic field
      private static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;

      boolean isOKbyJLS() {
         switch (this) {
            case OK:
            case NULL_PROBLEM:
               return true;
            default:
               return false;
         }
      }

      public BoundCheckStatus betterOf(BoundCheckStatus other) {
         return this.ordinal() < other.ordinal() ? this : other;
      }

      // $FF: synthetic method
      static int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus() {
         int[] var10000 = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus;
         if (var10000 != null) {
            return var10000;
         } else {
            int[] var0 = new int[values().length];

            try {
               var0[MISMATCH.ordinal()] = 4;
            } catch (NoSuchFieldError var4) {
            }

            try {
               var0[NULL_PROBLEM.ordinal()] = 2;
            } catch (NoSuchFieldError var3) {
            }

            try {
               var0[OK.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
            }

            try {
               var0[UNCHECKED.ordinal()] = 3;
            } catch (NoSuchFieldError var1) {
            }

            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$TypeConstants$BoundCheckStatus = var0;
            return var0;
         }
      }
   }

   public static class CloseMethodRecord {
      public char[][] typeName;
      public char[] selector;
      public int numCloseableArgs;

      public CloseMethodRecord(char[][] typeName, char[] selector, int num) {
         this.typeName = typeName;
         this.selector = selector;
         this.numCloseableArgs = num;
      }
   }

   public static enum DangerousMethod {
      Contains,
      Remove,
      RemoveAll,
      ContainsAll,
      RetainAll,
      Get,
      ContainsKey,
      ContainsValue,
      IndexOf,
      LastIndexOf,
      Equals;

      public static DangerousMethod detectSelector(char[] selector) {
         switch (selector[0]) {
            case 'c':
               if (CharOperation.prefixEquals(TypeConstants.CONTAINS, selector)) {
                  if (CharOperation.equals(selector, TypeConstants.CONTAINS)) {
                     return Contains;
                  }

                  if (CharOperation.equals(selector, TypeConstants.CONTAINS_ALL)) {
                     return ContainsAll;
                  }

                  if (CharOperation.equals(selector, TypeConstants.CONTAINS_KEY)) {
                     return ContainsKey;
                  }

                  if (CharOperation.equals(selector, TypeConstants.CONTAINS_VALUE)) {
                     return ContainsValue;
                  }
               }
               break;
            case 'e':
               if (CharOperation.equals(selector, TypeConstants.EQUALS)) {
                  return Equals;
               }
               break;
            case 'g':
               if (CharOperation.equals(selector, TypeConstants.GET)) {
                  return Get;
               }
               break;
            case 'i':
               if (CharOperation.equals(selector, TypeConstants.INDEX_OF)) {
                  return IndexOf;
               }
               break;
            case 'l':
               if (CharOperation.equals(selector, TypeConstants.LAST_INDEX_OF)) {
                  return LastIndexOf;
               }
               break;
            case 'r':
               if (CharOperation.prefixEquals(TypeConstants.REMOVE, selector)) {
                  if (CharOperation.equals(selector, TypeConstants.REMOVE)) {
                     return Remove;
                  }

                  if (CharOperation.equals(selector, TypeConstants.REMOVE_ALL)) {
                     return RemoveAll;
                  }
               } else if (CharOperation.equals(selector, TypeConstants.RETAIN_ALL)) {
                  return RetainAll;
               }
         }

         return null;
      }
   }
}
