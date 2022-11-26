package weblogic.management.mbeanservers.partition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import javax.management.MBeanServer;
import javax.naming.BinaryRefAddr;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import weblogic.rjvm.JVMID;
import weblogic.utils.io.FilteringObjectInputStream;

public abstract class PartitionedMbsRefObjFactory implements ObjectFactory {
   private static final String PARTITION_ADDRESS_TYPE = "partitionName";
   private static final String JVMID_ADDRESS_TYPE = "jvmId";

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      Reference ref = (Reference)Reference.class.cast(obj);
      String pName = (String)ref.get("partitionName").getContent();
      JVMID jvmId = (JVMID)this.deserialize((byte[])((byte[])ref.get("jvmId").getContent()));
      if (!jvmId.isLocal()) {
         throw new Exception(name + " cannot be looked up using a remote JNDI context. Use JSR160 interface to use JMX remotely or use a local JNDI context to look up the local MBeanServer.");
      } else {
         MBeanServer delegateMbs = this.getDelegateMbs();

         assert delegateMbs != null;

         return (new PartitionedMbsFactory()).create(pName, delegateMbs);
      }
   }

   Reference createReference(MBeanServer mbs, String pName) {
      StringRefAddr addr = new StringRefAddr("partitionName", pName);
      Reference reference = new Reference(MBeanServer.class.getName(), addr, this.getClass().getName(), (String)null);
      RefAddr jvmIdAddr = new BinaryRefAddr("jvmId", this.serialize(JVMID.localID()));
      reference.add(jvmIdAddr);
      return reference;
   }

   private byte[] serialize(Object o) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
         ObjectOutputStream os = new ObjectOutputStream(bos);
         Throwable var4 = null;

         byte[] var5;
         try {
            os.writeObject(o);
            var5 = bos.toByteArray();
         } catch (Throwable var15) {
            var4 = var15;
            throw var15;
         } finally {
            if (os != null) {
               if (var4 != null) {
                  try {
                     os.close();
                  } catch (Throwable var14) {
                     var4.addSuppressed(var14);
                  }
               } else {
                  os.close();
               }
            }

         }

         return var5;
      } catch (IOException var17) {
         throw new RuntimeException(var17);
      }
   }

   private Object deserialize(byte[] content) {
      ByteArrayInputStream bos = new ByteArrayInputStream(content);

      try {
         ObjectInputStream os = new FilteringObjectInputStream(bos);
         Throwable var4 = null;

         Object var5;
         try {
            var5 = os.readObject();
         } catch (Throwable var16) {
            var4 = var16;
            throw var16;
         } finally {
            if (os != null) {
               if (var4 != null) {
                  try {
                     os.close();
                  } catch (Throwable var15) {
                     var4.addSuppressed(var15);
                  }
               } else {
                  os.close();
               }
            }

         }

         return var5;
      } catch (IOException var18) {
         throw new RuntimeException(var18);
      } catch (ClassNotFoundException var19) {
         throw new RuntimeException(var19);
      }
   }

   protected abstract MBeanServer getDelegateMbs();
}
