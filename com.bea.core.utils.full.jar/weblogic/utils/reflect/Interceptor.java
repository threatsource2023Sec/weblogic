package weblogic.utils.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Interceptor implements InvocationHandler {
   Object delegate;

   public static Object intercept(Object o) {
      Interceptor interceptor = new Interceptor(o);
      return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), interceptor);
   }

   private Interceptor(Object o) {
      this.delegate = o;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      StringBuffer sb = new StringBuffer(200);
      sb.append(method.getName()).append("(");
      stringify(sb, args);
      sb.append(")");

      Object var6;
      try {
         Object retval = method.invoke(this.delegate, args);
         if (retval != null) {
            sb.append(" ---> ");
            stringify(sb, retval);
         }

         var6 = retval;
      } catch (Throwable var10) {
         sb.append(" ---> ");
         sb.append(var10.toString());
         throw var10;
      } finally {
         System.out.println(sb);
      }

      return var6;
   }

   public static void stringify(StringBuffer sb, Object arg) {
      if (arg != null) {
         Class clazz = arg.getClass();
         if (clazz.isArray()) {
            sb.append("[");
            if (clazz.getComponentType().isPrimitive()) {
               clazz = clazz.getComponentType();
               if (clazz.equals(Boolean.TYPE)) {
                  stringify(sb, (boolean[])((boolean[])arg));
               } else if (clazz.equals(Byte.TYPE)) {
                  stringify(sb, (byte[])((byte[])arg));
               } else if (clazz.equals(Character.TYPE)) {
                  stringify(sb, (char[])((char[])arg));
               } else if (clazz.equals(Short.TYPE)) {
                  stringify(sb, (short[])((short[])arg));
               } else if (clazz.equals(Integer.TYPE)) {
                  stringify(sb, (int[])((int[])arg));
               } else if (clazz.equals(Long.TYPE)) {
                  stringify(sb, (long[])((long[])arg));
               } else if (clazz.equals(Float.TYPE)) {
                  stringify(sb, (float[])((float[])arg));
               } else if (clazz.equals(Double.TYPE)) {
                  stringify(sb, (double[])((double[])arg));
               } else {
                  sb.append(arg);
               }
            } else {
               stringify(sb, (Object[])((Object[])arg));
            }

            sb.append("]");
         } else {
            sb.append(arg.toString());
         }

      }
   }

   public static void stringify(StringBuffer sb, Object[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            stringify(sb, args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, boolean[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, byte[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, char[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, short[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, int[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, long[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, float[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }

   public static void stringify(StringBuffer sb, double[] args) {
      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(args[i]);
         }

      }
   }
}
