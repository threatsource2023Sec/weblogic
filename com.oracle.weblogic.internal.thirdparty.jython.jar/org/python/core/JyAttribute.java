package org.python.core;

import java.io.PrintStream;
import java.io.Serializable;

public abstract class JyAttribute implements Serializable {
   public static final byte JAVA_PROXY_ATTR = -128;
   public static final byte WEAK_REF_ATTR = 0;
   public static final byte JYNI_HANDLE_ATTR = 1;
   public static final byte PY_ID_ATTR = 2;
   public static final byte WEAKREF_PENDING_GET_ATTR = 3;
   public static final byte PYCLASS_PY2JY_CACHE_ATTR = 4;
   public static final byte GC_CYCLE_MARK_ATTR = 5;
   public static final byte GC_DELAYED_FINALIZE_CRITICAL_MARK_ATTR = 6;
   public static final byte FINALIZE_TRIGGER_ATTR = 127;
   private static byte nonBuiltinAttrTypeOffset = -127;
   private static byte nonBuiltinTransientAttrTypeOffset = 7;
   byte attr_type;

   public static byte reserveCustomAttrType() {
      if (nonBuiltinAttrTypeOffset == 0) {
         throw new IllegalStateException("No more attr types available.");
      } else {
         byte var10000 = nonBuiltinAttrTypeOffset;
         nonBuiltinAttrTypeOffset = (byte)(var10000 + 1);
         return var10000;
      }
   }

   public static byte reserveTransientCustomAttrType() {
      if (nonBuiltinTransientAttrTypeOffset == 127) {
         throw new IllegalStateException("No more transient attr types available.");
      } else {
         byte var10000 = nonBuiltinTransientAttrTypeOffset;
         nonBuiltinTransientAttrTypeOffset = (byte)(var10000 + 1);
         return var10000;
      }
   }

   protected JyAttribute(byte attr_type) {
      this.attr_type = attr_type;
   }

   protected abstract JyAttribute getNext();

   protected abstract void setNext(JyAttribute var1);

   protected abstract Object getValue();

   protected abstract void setValue(Object var1);

   public static boolean hasAttr(PyObject ob, byte attr_type) {
      return getAttr(ob, attr_type) != null;
   }

   public static Object getAttr(PyObject ob, byte attr_type) {
      synchronized(ob) {
         if (!(ob.attributes instanceof JyAttribute)) {
            return attr_type == -128 ? ob.attributes : null;
         } else {
            JyAttribute att;
            for(att = (JyAttribute)ob.attributes; att != null && att.attr_type < attr_type; att = att.getNext()) {
            }

            return att != null && att.attr_type == attr_type ? att.getValue() : null;
         }
      }
   }

   public static void debugPrintAttributes(PyObject o, PrintStream out) {
      synchronized(o) {
         out.println("debugPrintAttributes of " + System.identityHashCode(o) + ":");
         if (o.attributes == null) {
            out.println("null");
         } else if (!(o.attributes instanceof JyAttribute)) {
            out.println("only javaProxy");
         } else {
            for(JyAttribute att = (JyAttribute)o.attributes; att != null; att = att.getNext()) {
               out.println("att type: " + att.attr_type + " value: " + att.getValue());
            }
         }

         out.println("debugPrintAttributes done");
      }
   }

   public static void setAttr(PyObject ob, byte attr_type, Object value) {
      synchronized(ob) {
         if (value == null) {
            delAttr(ob, attr_type);
         } else if (ob.attributes == null) {
            if (attr_type == -128) {
               ob.attributes = value;
            } else {
               ob.attributes = attr_type < 0 ? new AttributeLink(attr_type, value) : new TransientAttributeLink(attr_type, value);
            }
         } else if (!(ob.attributes instanceof JyAttribute)) {
            if (attr_type == -128) {
               ob.attributes = value;
            } else {
               ob.attributes = new AttributeLink((byte)-128, ob.attributes);
               ((JyAttribute)ob.attributes).setNext((JyAttribute)(attr_type < 0 ? new AttributeLink(attr_type, value) : new TransientAttributeLink(attr_type, value)));
            }
         } else {
            JyAttribute att = (JyAttribute)ob.attributes;
            Object newAtt;
            if (att.attr_type > attr_type) {
               newAtt = attr_type < 0 ? new AttributeLink(attr_type, value) : new TransientAttributeLink(attr_type, value);
               ((JyAttribute)newAtt).setNext(att);
               ob.attributes = newAtt;
            } else {
               while(att.getNext() != null && att.getNext().attr_type <= attr_type) {
                  att = att.getNext();
               }

               if (att.attr_type == attr_type) {
                  att.setValue(value);
               } else if (att.getNext() == null) {
                  att.setNext((JyAttribute)(attr_type < 0 ? new AttributeLink(attr_type, value) : new TransientAttributeLink(attr_type, value)));
               } else {
                  newAtt = attr_type < 0 ? new AttributeLink(attr_type, value) : new TransientAttributeLink(attr_type, value);
                  ((JyAttribute)newAtt).setNext(att.getNext());
                  att.setNext((JyAttribute)newAtt);
               }
            }
         }

      }
   }

   public static void delAttr(PyObject ob, byte attr_type) {
      synchronized(ob) {
         if (ob.attributes != null) {
            if (attr_type == -128 && !(ob.attributes instanceof JyAttribute)) {
               ob.attributes = null;
            }

            JyAttribute att = (JyAttribute)ob.attributes;
            if (att.attr_type == attr_type) {
               ob.attributes = att.getNext();
            } else {
               while(att.getNext() != null && att.getNext().attr_type < attr_type) {
                  att = att.getNext();
               }

               if (att.getNext() != null && att.getNext().attr_type == attr_type) {
                  att.setNext(att.getNext().getNext());
               }
            }

            if (ob.attributes != null) {
               att = (JyAttribute)ob.attributes;
               if (att.getNext() == null && att.attr_type == -128) {
                  ob.attributes = att.getValue();
               }
            }

         }
      }
   }

   static class TransientAttributeLink extends JyAttribute {
      transient JyAttribute next;
      transient Object value;

      protected TransientAttributeLink(byte attr_type, Object value) {
         super(attr_type);
         this.value = value;
      }

      protected JyAttribute getNext() {
         return this.next;
      }

      protected void setNext(JyAttribute next) {
         this.next = next;
      }

      protected Object getValue() {
         return this.value;
      }

      protected void setValue(Object value) {
         this.value = value;
      }
   }

   static class AttributeLink extends JyAttribute {
      JyAttribute next;
      Object value;

      protected AttributeLink(byte attr_type, Object value) {
         super(attr_type);
         this.value = value;
      }

      protected JyAttribute getNext() {
         return this.next;
      }

      protected void setNext(JyAttribute next) {
         this.next = next;
      }

      protected Object getValue() {
         return this.value;
      }

      protected void setValue(Object value) {
         this.value = value;
      }
   }
}
