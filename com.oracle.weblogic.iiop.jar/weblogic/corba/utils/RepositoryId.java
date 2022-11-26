package weblogic.corba.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.Serializable;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.rmi.CORBA.ClassDesc;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.SendingContext.CodeBaseHelper;
import weblogic.corba.cos.naming.NamingContextAnyHelper;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.io.ObjectStreamClass;
import weblogic.utils.io.ObjectStreamField;

public final class RepositoryId extends MarshaledString {
   private String annotation;
   private static final String[] ILLEGAL_CHARS = new String[]{"\\U0024", "__"};
   private static final String[] LEGAL_CHARS = new String[]{"$", "$"};
   private static final String STRING_ID = "IDL:omg.org/CORBA/WStringValue:1.0";
   private static Function getFields = ObjectStreamClass::getFields;
   public static final RepositoryId STRING = new RepositoryId("IDL:omg.org/CORBA/WStringValue:1.0");
   public static final RepositoryId NULL = new RepositoryId("IDL:omg.org/CORBA/AbstractBase:1.0");
   public static final RepositoryId EMPTY = new RepositoryId("");
   public static final RepositoryId OBJECT = new RepositoryId("IDL:omg.org/CORBA/Object:1.0");
   static final RepositoryId NAMING = new RepositoryId(NamingContextHelper.id());
   private static final RepositoryId CLASS_DESC = new RepositoryId(ClassDesc.class);
   private static final RepositoryId NAMING_ANY = new RepositoryId(NamingContextAnyHelper.id());
   private static final RepositoryId CODEBASE = new RepositoryId(CodeBaseHelper.id());
   private static final RepositoryId INT_ID = new RepositoryId("RMI:int:0000000000000000");
   private static final RepositoryId BYTE_ID = new RepositoryId("RMI:byte:0000000000000000");
   private static final RepositoryId LONG_ID = new RepositoryId("RMI:long:0000000000000000");
   private static final RepositoryId FLOAT_ID = new RepositoryId("RMI:float:0000000000000000");
   private static final RepositoryId DOUBLE_ID = new RepositoryId("RMI:double:0000000000000000");
   private static final RepositoryId SHORT_ID = new RepositoryId("RMI:short:0000000000000000");
   private static final RepositoryId CHAR_ID = new RepositoryId("RMI:char:0000000000000000");
   private static final RepositoryId BOOLEAN_ID = new RepositoryId("RMI:boolean:0000000000000000");
   static final HashMap PRIMITIVE_MAP = new HashMap(31);
   private static ConcurrentHashMap classNameMap;

   public RepositoryId(InputStream in, int len) {
      super(in, len);
      this.initHash();
   }

   public RepositoryId(InputStream in) {
      super(in);
      this.initHash();
   }

   public RepositoryId(String repId) {
      super(repId);
      this.initHash();
   }

   RepositoryId(RepositoryId repid, String annotation) {
      super((MarshaledString)repid);
      this.annotation = annotation;
      this.hash = repid.hash;
   }

   public RepositoryId(Class theClass) {
      super(createRMIRepositoryID(theClass));
      this.setClassLoader(theClass.getClassLoader());
      this.initHash();
   }

   void setClassLoader(ClassLoader loader) {
      if (this.annotation == null) {
         this.annotation = CorbaUtils.getAnnotation(loader);
      }

   }

   public boolean isIDLType() {
      return this.encodedString.length > 3 && this.encodedString[0] == 73 && this.encodedString[1] == 68 && this.encodedString[2] == 76;
   }

   public static RepositoryId createFromRemote(Class c) {
      return new RepositoryId(createRMIRepositoryID(c));
   }

   public static Set getRemoteIDs(Class aClass) {
      HashSet results = new HashSet();

      for(Class c = aClass; Utilities.isARemote(c); c = c.getSuperclass()) {
         Class[] interfaces = c.getInterfaces();
         Class[] var4 = interfaces;
         int var5 = interfaces.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class anInterface = var4[var6];
            if (Utilities.isARemote(anInterface)) {
               results.add(CorbaUtils.isIDLInterface(anInterface) ? getIDFromIDLEntity(anInterface) : createRMIRepositoryID(anInterface));
            }
         }
      }

      return results;
   }

   static RepositoryId[] getRepositoryIdList(Class c) {
      if (c != null && c.getSuperclass() != null && !c.getSuperclass().isInterface() && Serializable.class.isAssignableFrom(c.getSuperclass())) {
         ArrayList a;
         for(a = new ArrayList(); c != null && !c.isInterface() && Serializable.class.isAssignableFrom(c); c = c.getSuperclass()) {
            a.add(new RepositoryId(c));
         }

         return (RepositoryId[])a.toArray(new RepositoryId[0]);
      } else {
         return null;
      }
   }

   private static String getIDFromIDLEntity(Class c) {
      String name = c.getName();
      String typeid;
      if (name.startsWith("org.omg.")) {
         typeid = "IDL:omg.org/" + name.substring("org.omg.".length()).replace('.', '/') + ":1.0";
      } else {
         typeid = "IDL:" + c.getName().replace('.', '/') + ":1.0";
      }

      return typeid;
   }

   private static String unconvertIllegalCharacters(String name) {
      for(int j = 0; j < ILLEGAL_CHARS.length; ++j) {
         int idx = name.indexOf(ILLEGAL_CHARS[j]);
         int startidx = 0;
         if (idx >= 0) {
            StringBuilder result;
            for(result = new StringBuilder(); idx >= 0; idx = name.indexOf(ILLEGAL_CHARS[j], startidx)) {
               result.append(name, startidx, idx).append(LEGAL_CHARS[j]);
               startidx = idx + ILLEGAL_CHARS[j].length();
            }

            name = result.append(name, startidx, name.length() - 1).toString();
         }
      }

      return name;
   }

   public String getClassName() {
      String cname = (String)classNameMap.get(this.toString());
      if (cname == null) {
         String typeid = this.toString();
         int end;
         if (typeid.startsWith("IDL:omg.org/")) {
            typeid = "org.omg." + typeid.substring("IDL:omg.org/".length());
            end = typeid.indexOf(58);
            if (end <= 0) {
               return null;
            }

            cname = typeid.substring(0, end).replace('/', '.');
         } else {
            typeid = unconvertIllegalCharacters(typeid);
            end = typeid.indexOf(58);
            int end = typeid.indexOf(58, end + 1);
            if (end <= 0 || end <= 0) {
               return null;
            }

            cname = typeid.substring(end + 1, end).replace('/', '.');
         }

         addToMap(this, cname);
      }

      return cname;
   }

   private void initHash() {
      int h = 0;
      int len = this.encodedString.length;
      if (len > 21 && this.encodedString[0] == 82) {
         len -= 17;
      }

      for(int i = 4; i < len; ++i) {
         h = 31 * h + this.encodedString[i];
      }

      this.hash = h;
   }

   public final int hashCode() {
      return this.hash;
   }

   public final boolean equals(Object other) {
      return other instanceof RepositoryId && this.equals((RepositoryId)other);
   }

   private boolean equals(RepositoryId other) {
      return this.compareStrings(other) && Objects.equals(this.annotation, other.annotation);
   }

   private static String createRMIRepositoryID(Class cl) {
      if (isIDLEntity(cl)) {
         return CorbaUtils.createIDFromIDLEntity(cl);
      } else {
         String ret;
         if (cl == String.class) {
            ret = "IDL:omg.org/CORBA/WStringValue:1.0";
         } else if (cl == Class.class) {
            ret = createRMIRepositoryID(ClassDesc.class);
         } else {
            StringBuilder repid = new StringBuilder("RMI:");
            repid.append(convertIllegalCharacters(cl.getName()));
            repid.append(":");
            Class clazz = cl;
            if (cl.isArray()) {
               while(clazz.getComponentType() != null) {
                  clazz = clazz.getComponentType();
               }
            }

            ObjectStreamClass osc = ObjectStreamClass.lookup(clazz);
            if (osc != null && !clazz.isInterface() && (!osc.isArray() || !osc.forClass().getComponentType().isPrimitive())) {
               repid.append(toHexString(computeHashCode(osc, clazz)));
               repid.append(":");
               repid.append(toHexString(osc.getObjectStreamClass().getSerialVersionUID()));
               ret = repid.toString();
            } else {
               repid.append(toHexString(0L));
               ret = repid.toString();
            }
         }

         return ret;
      }
   }

   private static boolean isIDLEntity(Class c) {
      return !IDLEntity.class.equals(c) && IDLEntity.class.isAssignableFrom(c);
   }

   public static String toHexString(long l) {
      StringBuilder b = new StringBuilder();
      String hex = Long.toHexString(l).toUpperCase();
      int i = 16 - hex.length();

      while(i-- > 0) {
         b.append('0');
      }

      b.append(hex);
      return b.toString();
   }

   private static String convertIllegalCharacters(String name) {
      StringBuilder result = new StringBuilder();

      for(int i = 0; i < name.length(); ++i) {
         char c = name.charAt(i);
         switch (c) {
            case '$':
               result.append("\\U0024");
               break;
            case '\\':
               if ('u' != name.charAt(i + 1)) {
                  result.append(c);
                  break;
               }

               result.append("U");

               for(int k = i + 2; k < i + 5; ++k) {
                  result.append(Character.toUpperCase(name.charAt(k)));
               }

               i += 4;
               break;
            default:
               result.append(c);
         }
      }

      return result.toString();
   }

   private static long computeHashCode(ObjectStreamClass osc, Class cl) {
      try {
         if (Serializable.class.isAssignableFrom(cl) && !cl.isInterface()) {
            if (Externalizable.class.isAssignableFrom(cl)) {
               return 1L;
            } else {
               ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
               MessageDigest digest = MessageDigest.getInstance("SHA");
               DigestOutputStream dout = new DigestOutputStream(bout, digest);
               DataOutputStream data = new DataOutputStream(dout);
               Class parent = cl.getSuperclass();
               if (parent != null) {
                  data.writeLong(computeHashCode(osc.getSuperclass(), parent));
               }

               (new RepositoryHashBuilder(osc, data)).invoke();
               data.flush();
               long hcode = 0L;
               byte[] hash = digest.digest();

               for(int i = 0; i < Math.min(8, hash.length); ++i) {
                  hcode += (long)(hash[i] & 255) << i * 8;
               }

               return hcode;
            }
         } else {
            return 0L;
         }
      } catch (NoSuchAlgorithmException | IOException var11) {
         return -1L;
      }
   }

   private static void addToMap(RepositoryId id, String className) {
      classNameMap.put(id.toString(), className);
   }

   private static void initialize() {
      addToMap(NAMING, "org.omg.CosNaming.NamingContext");
      addToMap(STRING, "java.lang.String");
      addToMap(NAMING_ANY, "weblogic.corba.cos.naming.NamingContextAny");

      try {
         Class.forName("com.sun.org.omg.SendingContext.CodeBase");
         addToMap(CODEBASE, "com.sun.org.omg.SendingContext.CodeBase");
      } catch (ClassNotFoundException var3) {
         try {
            Class.forName("com.ibm.org.omg.SendingContext.CodeBase");
            addToMap(CODEBASE, "com.ibm.org.omg.SendingContext.CodeBase");
         } catch (ClassNotFoundException var2) {
         }
      }

   }

   public boolean isClassDesc() {
      return this.compareStrings(CLASS_DESC);
   }

   String toPrettyString() {
      return this.toString() + "@" + this.getAnnotation();
   }

   public String getAnnotation() {
      return this.annotation;
   }

   public void setAnnotation(String annotation) {
      this.annotation = annotation;
   }

   static {
      PRIMITIVE_MAP.put(INT_ID, Integer.TYPE);
      PRIMITIVE_MAP.put(BYTE_ID, Byte.TYPE);
      PRIMITIVE_MAP.put(LONG_ID, Long.TYPE);
      PRIMITIVE_MAP.put(FLOAT_ID, Float.TYPE);
      PRIMITIVE_MAP.put(DOUBLE_ID, Double.TYPE);
      PRIMITIVE_MAP.put(SHORT_ID, Short.TYPE);
      PRIMITIVE_MAP.put(CHAR_ID, Character.TYPE);
      PRIMITIVE_MAP.put(BOOLEAN_ID, Boolean.TYPE);
      classNameMap = new ConcurrentHashMap();
      initialize();
   }

   private static class RepositoryHashBuilder {
      private DataOutputStream data;
      private List toProcess;
      private final List processed = new ArrayList();

      RepositoryHashBuilder(ObjectStreamClass osc, DataOutputStream data) {
         this.data = data;
         this.toProcess = new ArrayList(Collections.singletonList(osc));
      }

      public void invoke() throws IOException {
         label21:
         while(true) {
            if (!this.toProcess.isEmpty()) {
               ObjectStreamClass streamClass = (ObjectStreamClass)this.toProcess.remove(0);
               this.processed.add(streamClass);
               this.data.writeInt(streamClass.hasWriteObject() ? 2 : 1);
               ObjectStreamField[] fields = (ObjectStreamField[])RepositoryId.getFields.apply(streamClass);
               Arrays.sort(fields, Comparator.comparing(ObjectStreamField::getName));
               ObjectStreamField[] var3 = fields;
               int var4 = fields.length;
               int var5 = 0;

               while(true) {
                  if (var5 >= var4) {
                     continue label21;
                  }

                  ObjectStreamField field = var3[var5];
                  this.data.writeUTF(field.getName());
                  this.data.writeUTF(field.getSignature());
                  this.processFieldType(this.getUnderlyingType(field.getType()));
                  ++var5;
               }
            }

            return;
         }
      }

      private void processFieldType(Class fieldType) {
         if (fieldType != String.class) {
            ObjectStreamClass fieldClass = ObjectStreamClass.lookup(fieldType);
            if (fieldClass != null && !fieldClass.isExternalizable() && this.isNewClass(fieldClass)) {
               this.toProcess.add(fieldClass);
            }

         }
      }

      private Class getUnderlyingType(Class type) {
         return type.getComponentType() == null ? type : this.getUnderlyingType(type.getComponentType());
      }

      private boolean isNewClass(ObjectStreamClass fieldClass) {
         return !this.processed.contains(fieldClass) && !this.toProcess.contains(fieldClass);
      }
   }
}
