package org.python.modules;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyFrozenSet;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

public class _hashlib implements ClassDictInit {
   private static final Map algorithmMap = new HashMap() {
      {
         this.put("sha1", "sha-1");
         this.put("sha224", "sha-224");
         this.put("sha256", "sha-256");
         this.put("sha384", "sha-384");
         this.put("sha512", "sha-512");
      }
   };
   public static final PyFrozenSet openssl_md_meth_names = new PyFrozenSet(new PyTuple(new PyObject[]{Py.newString("md5"), Py.newString("sha1"), Py.newString("sha224"), Py.newString("sha256"), Py.newString("sha384"), Py.newString("sha512")}));

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", Py.newString("_hashlib"));
      dict.__setitem__((String)"algorithmMap", (PyObject)null);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   public static PyObject new$(String name) {
      return new$(name, (PyObject)null);
   }

   public static PyObject new$(String name, PyObject obj) {
      name = name.toLowerCase();
      if (algorithmMap.containsKey(name)) {
         name = (String)algorithmMap.get(name);
      }

      Hash hash = new Hash(name);
      if (obj != null) {
         hash.update(obj);
      }

      return hash;
   }

   public static PyObject openssl_md5() {
      return openssl_md5((PyObject)null);
   }

   public static PyObject openssl_md5(PyObject obj) {
      return new$("md5", obj);
   }

   public static PyObject openssl_sha1() {
      return openssl_sha1((PyObject)null);
   }

   public static PyObject openssl_sha1(PyObject obj) {
      return new$("sha1", obj);
   }

   public static PyObject openssl_sha224() {
      return openssl_sha224((PyObject)null);
   }

   public static PyObject openssl_sha224(PyObject obj) {
      return new$("sha224", obj);
   }

   public static PyObject openssl_sha256() {
      return openssl_sha256((PyObject)null);
   }

   public static PyObject openssl_sha256(PyObject obj) {
      return new$("sha256", obj);
   }

   public static PyObject openssl_sha384() {
      return openssl_sha384((PyObject)null);
   }

   public static PyObject openssl_sha384(PyObject obj) {
      return new$("sha384", obj);
   }

   public static PyObject openssl_sha512() {
      return openssl_sha512((PyObject)null);
   }

   public static PyObject openssl_sha512(PyObject obj) {
      return new$("sha512", obj);
   }

   @Untraversable
   @ExposedType(
      name = "_hashlib.HASH"
   )
   public static class Hash extends PyObject {
      public static final PyType TYPE;
      public String name;
      private MessageDigest digest;
      private static final Map blockSizes;

      public Hash(String name) {
         this(name, getDigest(name));
      }

      private Hash(String name, MessageDigest digest) {
         super(TYPE);
         this.name = name;
         this.digest = digest;
      }

      private static final MessageDigest getDigest(String name) {
         try {
            return (MessageDigest)(name.equals("sha-224") ? new SHA224Digest() : MessageDigest.getInstance(name));
         } catch (NoSuchAlgorithmException var2) {
            throw Py.ValueError("unsupported hash type");
         }
      }

      private MessageDigest cloneDigest() {
         try {
            synchronized(this) {
               return (MessageDigest)this.digest.clone();
            }
         } catch (CloneNotSupportedException var4) {
            throw Py.RuntimeError(String.format("_hashlib.HASH (%s) internal error", this.name));
         }
      }

      private byte[] calculateDigest() {
         return this.cloneDigest().digest();
      }

      public void update(PyObject obj) {
         this.HASH_update(obj);
      }

      final void HASH_update(PyObject obj) {
         String string;
         if (obj instanceof PyUnicode) {
            string = ((PyUnicode)obj).encode();
         } else if (obj instanceof PyString) {
            string = obj.toString();
         } else {
            if (!(obj instanceof PyArray)) {
               throw Py.TypeError("update() argument 1 must be string or read-only buffer, not " + obj.getType().fastGetName());
            }

            string = ((PyArray)obj).tostring();
         }

         byte[] input = StringUtil.toBytes(string);
         synchronized(this) {
            this.digest.update(input);
         }
      }

      public PyObject digest() {
         return this.HASH_digest();
      }

      final PyObject HASH_digest() {
         return Py.newString(StringUtil.fromBytes(this.calculateDigest()));
      }

      public PyObject hexdigest() {
         return this.HASH_hexdigest();
      }

      final PyObject HASH_hexdigest() {
         byte[] result = this.calculateDigest();
         char[] hexDigest = new char[result.length * 2];
         int i = 0;

         for(int j = 0; i < result.length; ++i) {
            int c = result[i] >> 4 & 15;
            c = c > 9 ? c + 97 - 10 : c + 48;
            hexDigest[j++] = (char)c;
            c = result[i] & 15;
            c = c > 9 ? c + 97 - 10 : c + 48;
            hexDigest[j++] = (char)c;
         }

         return Py.newString(new String(hexDigest));
      }

      public PyObject copy() {
         return this.HASH_copy();
      }

      final PyObject HASH_copy() {
         return new Hash(this.name, this.cloneDigest());
      }

      public synchronized int getDigestSize() {
         return this.digest.getDigestLength();
      }

      public int getDigest_size() {
         return this.getDigestSize();
      }

      public PyObject getBlockSize() {
         Integer size = (Integer)blockSizes.get(this.name);
         return (PyObject)(size == null ? Py.None : Py.newInteger(size));
      }

      public String toString() {
         return String.format("<%s HASH object @ %s>", this.name, Py.idstr(this));
      }

      static {
         PyType.addBuilder(Hash.class, new PyExposer());
         TYPE = PyType.fromClass(Hash.class);
         blockSizes = new HashMap() {
            {
               this.put("md5", 64);
               this.put("sha-1", 64);
               this.put("sha-224", 64);
               this.put("sha-256", 64);
               this.put("sha-384", 128);
               this.put("sha-512", 128);
            }
         };
      }

      private static class HASH_update_exposer extends PyBuiltinMethodNarrow {
         public HASH_update_exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "";
         }

         public HASH_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new HASH_update_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            ((Hash)this.self).HASH_update(var1);
            return Py.None;
         }
      }

      private static class HASH_digest_exposer extends PyBuiltinMethodNarrow {
         public HASH_digest_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "";
         }

         public HASH_digest_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new HASH_digest_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((Hash)this.self).HASH_digest();
         }
      }

      private static class HASH_hexdigest_exposer extends PyBuiltinMethodNarrow {
         public HASH_hexdigest_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "";
         }

         public HASH_hexdigest_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new HASH_hexdigest_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((Hash)this.self).HASH_hexdigest();
         }
      }

      private static class HASH_copy_exposer extends PyBuiltinMethodNarrow {
         public HASH_copy_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "";
         }

         public HASH_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new HASH_copy_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((Hash)this.self).HASH_copy();
         }
      }

      private static class digestsize_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public digestsize_descriptor() {
            super("digestsize", Integer.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return Py.newInteger(((Hash)var1).getDigestSize());
         }

         public boolean implementsDescrGet() {
            return true;
         }

         public boolean implementsDescrSet() {
            return false;
         }

         public boolean implementsDescrDelete() {
            return false;
         }
      }

      private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public name_descriptor() {
            super("name", String.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            String var10000 = ((Hash)var1).name;
            return var10000 == null ? Py.None : Py.newString(var10000);
         }

         public boolean implementsDescrGet() {
            return true;
         }

         public boolean implementsDescrSet() {
            return false;
         }

         public boolean implementsDescrDelete() {
            return false;
         }
      }

      private static class block_size_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public block_size_descriptor() {
            super("block_size", PyObject.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return ((Hash)var1).getBlockSize();
         }

         public boolean implementsDescrGet() {
            return true;
         }

         public boolean implementsDescrSet() {
            return false;
         }

         public boolean implementsDescrDelete() {
            return false;
         }
      }

      private static class digest_size_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public digest_size_descriptor() {
            super("digest_size", Integer.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return Py.newInteger(((Hash)var1).getDigest_size());
         }

         public boolean implementsDescrGet() {
            return true;
         }

         public boolean implementsDescrSet() {
            return false;
         }

         public boolean implementsDescrDelete() {
            return false;
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new HASH_update_exposer("update"), new HASH_digest_exposer("digest"), new HASH_hexdigest_exposer("hexdigest"), new HASH_copy_exposer("copy")};
            PyDataDescr[] var2 = new PyDataDescr[]{new digestsize_descriptor(), new name_descriptor(), new block_size_descriptor(), new digest_size_descriptor()};
            super("_hashlib.HASH", Hash.class, Object.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }
}
