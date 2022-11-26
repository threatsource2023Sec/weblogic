package weblogic.wtc.jatmi;

public class TypedBufferFactory {
   private static TypedBufferPool g_buffers = null;
   private static int by_reference_enabled = -1;

   public static TypedBufferPool getBufferPool() {
      if (-1 == by_reference_enabled) {
         String by_ref = System.getProperty("weblogic.wtc.ByReference");
         if ("yes".equalsIgnoreCase(by_ref)) {
            by_reference_enabled = 1;
            g_buffers = new TypedBufferPool();
         }
      }

      return g_buffers;
   }

   public static TypedBuffer createTypedBuffer(String type, String subtype) {
      if (type == null) {
         return null;
      } else if (type.equals("STRING")) {
         return new TypedString();
      } else if (type.equals("CARRAY")) {
         return new TypedCArray();
      } else if (type.equals("FML")) {
         return new TypedFML();
      } else if (type.equals("FML32")) {
         return new TypedFML32();
      } else if (!type.equals("VIEW") && !type.equals("VIEW32")) {
         if (type.equals("TPINIT")) {
            return new TPINIT();
         } else if (type.equals("wsrpcrq")) {
            return new WSRPCRQ();
         } else if (type.equals("XML")) {
            return new TypedXML();
         } else if (type.equals("TGIOP")) {
            return new TypedTGIOP();
         } else if (type.equals("X_OCTET")) {
            return new TypedXOctet();
         } else if (!type.equals("X_COMMON") && !type.equals("X_C_TYPE")) {
            return type.equals("MBSTRING") ? new TypedMBString() : null;
         } else {
            return createViewInstance(subtype);
         }
      } else {
         return createViewInstance(subtype);
      }
   }

   public static TypedBuffer createTypedBuffer(int hint_index, String type, String subtype) {
      if (hint_index < 0) {
         return null;
      } else if (type == null) {
         return null;
      } else {
         switch (hint_index) {
            case 10:
               if (!type.equals("TGIOP")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedTGIOP();
            case 11:
               if (!type.equals("wsrpcrq")) {
                  return createTypedBuffer(type, subtype);
               }

               return new WSRPCRQ();
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return createTypedBuffer(type, subtype);
            case 16:
               if (!type.equals("CARRAY")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedCArray();
            case 17:
               if (!type.equals("STRING")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedString();
            case 18:
               if (!type.equals("FML")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedFML();
            case 19:
               if (!type.equals("VIEW")) {
                  return createTypedBuffer(type, subtype);
               }

               return createViewInstance(subtype);
            case 20:
               if (!type.equals("X_OCTET")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedXOctet();
            case 21:
               if (!type.equals("X_C_TYPE")) {
                  return createTypedBuffer(type, subtype);
               }

               return createViewInstance(subtype);
            case 22:
               if (!type.equals("X_COMMON")) {
                  return createTypedBuffer(type, subtype);
               }

               return createViewInstance(subtype);
            case 23:
               if (!type.equals("FML32")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedFML32();
            case 24:
               if (!type.equals("VIEW32")) {
                  return createTypedBuffer(type, subtype);
               }

               return createViewInstance(subtype);
            case 25:
               if (!type.equals("XML")) {
                  return createTypedBuffer(type, subtype);
               }

               return new TypedXML();
            case 26:
               return (TypedBuffer)(!type.equals("MBSTRING") ? createTypedBuffer(type, subtype) : new TypedMBString());
         }
      }
   }

   private static TypedBuffer createViewInstance(String subtype) {
      if (subtype == null) {
         return null;
      } else {
         try {
            TypedBuffer viewinst = (new ViewHelper()).newViewInstance(subtype);
            return viewinst;
         } catch (InstantiationException var2) {
            return null;
         } catch (IllegalAccessException var3) {
            return null;
         }
      }
   }
}
