package com.bea.wls.redef;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import serp.bytecode.lowlevel.ConstantPoolTable;
import weblogic.diagnostics.debug.DebugLogger;

final class SerialVersionUID {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugClassRedef");

   static Long getExplicitSVUIDValue(ConstantPoolTable table, int idx) {
      Long ret = null;
      idx += 6;
      int attrCount = table.readUnsignedShort(idx);
      idx += 2;

      for(int i = 0; i < attrCount; ++i) {
         int utfEntry = table.readUnsignedShort(idx);
         String name = table.readString(table.get(utfEntry));
         int len;
         if (name.equals("ConstantValue")) {
            len = table.readUnsignedShort(idx + 6);
            long val = readLong(table, table.get(len));
            ret = new Long(val);
         }

         len = table.readInt(idx + 2);
         idx += 6 + len;
      }

      return ret;
   }

   private static long readLong(ConstantPoolTable table, int idx) {
      long hi = ((long)table.readInt(idx) & 4294967295L) << 32;
      long lo = (long)table.readInt(idx + 4) & 4294967295L;
      return hi | lo;
   }

   static long computeSerialVersionUID(ClassMetaData meta) {
      ByteArrayOutputStream bos = null;
      DataOutputStream dos = null;

      try {
         bos = new ByteArrayOutputStream();
         dos = new DataOutputStream(bos);
         dos.writeUTF(meta.getName());
         int classModifiers = meta.getAccess() & 1553;
         MethodMetaData[] methods = meta.getDeclaredMethods();
         if ((classModifiers & 512) != 0) {
            classModifiers = methods.length > 0 ? classModifiers | 1024 : classModifiers & -1025;
         }

         dos.writeInt(classModifiers);
         String[] interfaces = meta.getInterfaces();
         String[] tmp = interfaces != null ? (String[])((String[])interfaces.clone()) : new String[0];
         Arrays.sort(tmp);

         for(int i = 0; i < tmp.length; ++i) {
            dos.writeUTF(tmp[i]);
         }

         writeSVUIDMembers(dos, getSVUIDFields(meta));
         if (meta.hasStaticInitializer()) {
            dos.writeUTF("<clinit>");
            dos.writeInt(8);
            dos.writeUTF("()V");
         }

         ConstructorMetaData[] cons = meta.getDeclaredConstructors();
         writeSVUIDMembers(dos, getSVUIDMethods(cons));
         writeSVUIDMembers(dos, getSVUIDMethods(methods));
         dos.flush();
         MessageDigest md = MessageDigest.getInstance("SHA");
         byte[] hashBytes = md.digest(bos.toByteArray());
         long hash = 0L;

         for(int i = Math.min(hashBytes.length, 8) - 1; i >= 0; --i) {
            hash = hash << 8 | (long)(hashBytes[i] & 255);
         }

         long var29 = hash;
         return var29;
      } catch (NoSuchAlgorithmException var25) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Error computing SerialVersionUID", var25);
         }
      } catch (IOException var26) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Error computing SerialVersionUID", var26);
         }
      } finally {
         if (dos != null) {
            try {
               dos.close();
            } catch (IOException var24) {
            }
         }

      }

      return 0L;
   }

   private static void writeSVUIDMembers(DataOutputStream dos, List memberSignatures) throws IOException {
      MemberSignature[] sigs = new MemberSignature[memberSignatures.size()];
      sigs = (MemberSignature[])memberSignatures.toArray(sigs);
      Arrays.sort(sigs);
      MemberSignature[] var3 = sigs;
      int var4 = sigs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MemberSignature member = var3[var5];
         member.write(dos);
      }

   }

   private static List getSVUIDFields(ClassMetaData meta) {
      List list = new ArrayList();
      FieldMetaData[] var2 = meta.getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         FieldMetaData field = var2[var4];
         int acc = field.getAccess() & 223;
         if ((acc & 2) == 0 || (acc & 136) == 0) {
            list.add(new MemberSignature(field.getName(), field.getDescriptor(), acc, false));
         }
      }

      return list;
   }

   private static List getSVUIDMethods(MemberMetaData[] members) {
      List list = new ArrayList();
      MemberMetaData[] var2 = members;
      int var3 = members.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MemberMetaData member = var2[var4];
         if (member instanceof MethodMetaData) {
            MethodMetaData method = (MethodMetaData)member;
            if (method.getOverride() != OverrideType.NONE) {
               continue;
            }
         }

         int acc = member.getAccess() & 3391;
         if ((acc & 2) == 0) {
            list.add(new MemberSignature(member.getName(), member.getDescriptor(), acc, true));
         }
      }

      return list;
   }

   private static class MemberSignature implements Comparable {
      private String _name;
      private String _descriptor;
      private int _access;

      MemberSignature(String name, String descriptor, int access, boolean dotted) {
         this._name = name;
         this._descriptor = descriptor;
         if (dotted) {
            this._descriptor = descriptor.replace('/', '.');
         }

         this._access = access;
      }

      public int compareTo(Object o) {
         MemberSignature other = (MemberSignature)o;
         int retVal = this._name.compareTo(other._name);
         if (retVal == 0) {
            retVal = this._descriptor.compareTo(other._descriptor);
         }

         return retVal;
      }

      public void write(DataOutputStream dos) throws IOException {
         dos.writeUTF(this._name);
         dos.writeInt(this._access);
         dos.writeUTF(this._descriptor);
      }
   }
}
