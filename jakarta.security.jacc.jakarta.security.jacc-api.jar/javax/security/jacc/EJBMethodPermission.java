package javax.security.jacc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.reflect.Method;
import java.security.Permission;
import java.util.HashMap;

public final class EJBMethodPermission extends Permission {
   private static final long serialVersionUID = 1L;
   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("actions", String.class)};
   private static final String[] interfaceKeys = new String[]{"Local", "LocalHome", "Remote", "Home", "ServiceEndpoint"};
   private static HashMap interfaceHash = new HashMap();
   private transient int methodInterface;
   private transient String otherMethodInterface;
   private transient String methodName;
   private transient String methodParams;
   private transient String actions;
   private transient int hashCodeValue;

   public EJBMethodPermission(String name, String actions) {
      super(name);
      this.setMethodSpec(actions);
   }

   public EJBMethodPermission(String EJBName, String methodName, String methodInterface, String[] methodParams) {
      super(EJBName);
      this.setMethodSpec(methodName, methodInterface, methodParams);
   }

   public EJBMethodPermission(String EJBName, String methodInterface, Method method) {
      super(EJBName);
      this.setMethodSpec(methodInterface, method);
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof EJBMethodPermission) {
         EJBMethodPermission that = (EJBMethodPermission)o;
         if (!this.getName().equals(that.getName())) {
            return false;
         } else {
            if (this.methodName != null) {
               if (that.methodName == null || !this.methodName.equals(that.methodName)) {
                  return false;
               }
            } else if (that.methodName != null) {
               return false;
            }

            if (this.methodInterface != that.methodInterface) {
               return false;
            } else if (this.methodInterface == -2 && !this.otherMethodInterface.equals(that.otherMethodInterface)) {
               return false;
            } else {
               if (this.methodParams != null) {
                  if (that.methodParams == null || !this.methodParams.equals(that.methodParams)) {
                     return false;
                  }
               } else if (that.methodParams != null) {
                  return false;
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public String getActions() {
      if (this.actions == null) {
         String iSpec = this.methodInterface == -1 ? null : (this.methodInterface < 0 ? this.otherMethodInterface : interfaceKeys[this.methodInterface]);
         if (this.methodName == null) {
            if (iSpec == null) {
               if (this.methodParams != null) {
                  this.actions = "," + this.methodParams;
               }
            } else if (this.methodParams == null) {
               this.actions = "," + iSpec;
            } else {
               this.actions = "," + iSpec + this.methodParams;
            }
         } else if (iSpec == null) {
            if (this.methodParams == null) {
               this.actions = this.methodName;
            } else {
               this.actions = this.methodName + "," + this.methodParams;
            }
         } else if (this.methodParams == null) {
            this.actions = this.methodName + "," + iSpec;
         } else {
            this.actions = this.methodName + "," + iSpec + this.methodParams;
         }
      }

      return this.actions;
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         String actions = this.getActions();
         String hashInput;
         if (actions == null) {
            hashInput = this.getName();
         } else {
            hashInput = this.getName() + " " + actions;
         }

         this.hashCodeValue = hashInput.hashCode();
      }

      return this.hashCodeValue;
   }

   public boolean implies(Permission permission) {
      if (permission != null && permission instanceof EJBMethodPermission) {
         EJBMethodPermission that = (EJBMethodPermission)permission;
         if (!this.getName().equals(that.getName())) {
            return false;
         } else if (this.methodName == null || that.methodName != null && this.methodName.equals(that.methodName)) {
            if (this.methodInterface != -1 && (that.methodInterface == -1 || this.methodInterface != that.methodInterface)) {
               return false;
            } else if (this.methodInterface == -2 && !this.otherMethodInterface.equals(that.otherMethodInterface)) {
               return false;
            } else {
               return this.methodParams == null || that.methodParams != null && this.methodParams.equals(that.methodParams);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      this.setMethodSpec((String)inputStream.readFields().get("actions", (Object)null));
   }

   private synchronized void writeObject(ObjectOutputStream outputStream) throws IOException {
      outputStream.putFields().put("actions", this.getActions());
      outputStream.writeFields();
   }

   private void setMethodSpec(String actions) {
      String mInterface = null;
      this.methodName = null;
      this.methodParams = null;
      if (actions != null) {
         if (actions.length() > 0) {
            int i = actions.indexOf(44);
            if (i < 0) {
               this.methodName = actions;
            } else if (i >= 0) {
               if (i != 0) {
                  this.methodName = actions.substring(0, i);
               }

               if (actions.length() == i + 1) {
                  throw new IllegalArgumentException("illegal actions spec");
               }

               int j = actions.substring(i + 1).indexOf(44);
               if (j < 0) {
                  mInterface = actions.substring(i + 1);
               } else {
                  if (j > 0) {
                     mInterface = actions.substring(i + 1, i + j + 1);
                  }

                  this.methodParams = actions.substring(i + j + 1);
                  if (this.methodParams.length() > 1 && this.methodParams.endsWith(",")) {
                     throw new IllegalArgumentException("illegal methodParam");
                  }
               }
            }
         } else {
            actions = null;
         }
      }

      this.methodInterface = validateInterface(mInterface);
      if (this.methodInterface < -1) {
         this.otherMethodInterface = mInterface;
      }

      this.actions = actions;
   }

   private void setMethodSpec(String methodName, String mInterface, String[] methodParams) {
      if (methodName != null && methodName.indexOf(44) >= 0) {
         throw new IllegalArgumentException("illegal methodName");
      } else {
         this.methodInterface = validateInterface(mInterface);
         if (this.methodInterface < -1) {
            this.otherMethodInterface = mInterface;
         }

         if (methodParams == null) {
            this.methodParams = null;
         } else {
            StringBuffer mParams = new StringBuffer(",");
            int i = 0;

            while(true) {
               if (i >= methodParams.length) {
                  this.methodParams = mParams.toString();
                  break;
               }

               if (methodParams[i] == null || methodParams[i].indexOf(44) >= 0) {
                  throw new IllegalArgumentException("illegal methodParam");
               }

               if (i == 0) {
                  mParams.append(methodParams[i]);
               } else {
                  mParams.append("," + methodParams[i]);
               }

               ++i;
            }
         }

         this.methodName = methodName;
      }
   }

   private void setMethodSpec(String mInterface, Method method) {
      this.methodInterface = validateInterface(mInterface);
      if (this.methodInterface < -1) {
         this.otherMethodInterface = mInterface;
      }

      this.methodName = method.getName();
      Class[] params = method.getParameterTypes();
      StringBuffer methodParameters = new StringBuffer(",");

      for(int i = 0; i < params.length; ++i) {
         String parameterName = params[i].getName();
         Class componentType = params[i].getComponentType();
         if (componentType != null) {
            String brackets;
            for(brackets = "[]"; componentType.getComponentType() != null; brackets = brackets + "[]") {
               componentType = componentType.getComponentType();
            }

            parameterName = componentType.getName() + brackets;
         }

         if (i == 0) {
            methodParameters.append(parameterName);
         } else {
            methodParameters.append("," + parameterName);
         }
      }

      this.methodParams = methodParameters.toString();
   }

   private static int validateInterface(String methodInterface) {
      int result = -1;
      if (methodInterface != null && methodInterface.length() > 0) {
         Integer i = (Integer)interfaceHash.get(methodInterface);
         if (i != null) {
            result = i;
         } else {
            result = -2;
         }
      }

      return result;
   }

   static {
      for(int i = 0; i < interfaceKeys.length; ++i) {
         interfaceHash.put(interfaceKeys[i], i);
      }

   }
}
