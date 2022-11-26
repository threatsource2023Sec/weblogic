package weblogic.descriptor.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.utils.XXEUtils;

public class DescriptorUtils {
   private static final EditableDescriptorManager edm = new EditableDescriptorManager();

   private DescriptorUtils() {
   }

   public static void writeAsXML(DescriptorBean db) {
      Descriptor desc = db.getDescriptor();

      try {
         edm.writeDescriptorAsXML(desc, new BufferedOutputStream(System.out) {
            public void close() {
            }
         });
      } catch (IOException var3) {
      }

   }

   public static String toString(DescriptorBean db) {
      Descriptor desc = db.getDescriptor();

      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         edm.writeDescriptorAsXML(desc, baos);
         return new String(baos.toString());
      } catch (IOException var3) {
         return null;
      }
   }

   public static String toString(DescriptorManager descriptorManager, DescriptorBean db) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      boolean includeMetadata = false;
      descriptorManager.writeDescriptorBeanAsXML(db, baos, (String)null, includeMetadata);
      return baos.toString();
   }

   public static void writeDescriptor(DescriptorManager descriptorManager, DescriptorBean descriptorBean, File f) throws IOException {
      OutputStream os = null;

      try {
         if (f != null) {
            File parent = f.getParentFile();
            if (!parent.exists()) {
               parent.mkdirs();
            }
         }

         os = new BufferedOutputStream(new FileOutputStream(f));
         descriptorManager.writeDescriptorAsXML(descriptorBean.getDescriptor(), os);
         os.flush();
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (IOException var10) {
            }
         }

      }

   }

   public static Object getOrCreateFirstChild(Object parentBean, Object childArray, String propComponentName) {
      Object obj = getFirstChild(childArray);
      if (obj != null) {
         return obj;
      } else if (parentBean == null) {
         throw new NullPointerException("Parent Bean object null");
      } else {
         Method method = getMethod(parentBean, "create" + propComponentName, new Class[0]);
         return invokeMethod(parentBean, method, new Object[0]);
      }
   }

   public static Object getFirstChildOrDefaultBean(Object parentBean, Object childArray, String propComponentName) {
      Object obj = getFirstChild(childArray);
      if (obj != null) {
         return obj;
      } else if (parentBean == null) {
         throw new NullPointerException("Parent Bean object null");
      } else {
         Method method = getMethod(parentBean, "create" + propComponentName, new Class[0]);
         obj = invokeMethod(parentBean, method, new Object[0]);
         if (obj == null) {
            throw new IllegalArgumentException("Error creating bean " + propComponentName);
         } else {
            method = getMethod(parentBean, "destroy" + propComponentName, new Class[]{method.getReturnType()});
            invokeMethod(parentBean, method, new Object[]{obj});
            return obj;
         }
      }
   }

   private static Object getFirstChild(Object childArray) {
      if (childArray == null) {
         throw new NullPointerException("Array of children is null");
      } else if (!childArray.getClass().isArray()) {
         throw new IllegalArgumentException("childArray is not of type array");
      } else {
         return Array.getLength(childArray) > 0 ? Array.get(childArray, 0) : null;
      }
   }

   private static Method getMethod(Object parent, String methodName, Class[] argTypes) {
      try {
         return parent.getClass().getMethod(methodName, argTypes);
      } catch (NoSuchMethodException var4) {
         throw new IllegalArgumentException(methodName + "() method does not exist on " + parent.getClass());
      }
   }

   private static Object invokeMethod(Object parent, Method method, Object[] args) {
      try {
         return method.invoke(parent, args);
      } catch (IllegalAccessException var4) {
         throw new IllegalArgumentException(method + " method  not accessible on " + parent.getClass());
      } catch (InvocationTargetException var5) {
         throw new IllegalArgumentException(method + " method on " + parent.getClass() + " threw an exception: " + var5.getTargetException().getMessage());
      }
   }

   public static void main(String[] args) throws IOException, XMLStreamException {
      if (args.length > 0) {
         if (args[0].equals("load") && args.length > 1) {
            DescriptorManager manager = new DescriptorManager();
            Descriptor descriptor = manager.createDescriptor(XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(new FileInputStream(args[1])));
            manager.writeDescriptorAsXML(descriptor, System.out);
         }
      } else {
         System.out.println("Arguments: [load <descriptor_file>]");
      }

   }
}
