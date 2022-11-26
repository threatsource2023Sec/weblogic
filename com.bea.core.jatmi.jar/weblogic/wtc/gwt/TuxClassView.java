package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Set;
import weblogic.wtc.jatmi.TypedView32;

public class TuxClassView {
   private HashMap _nameToType;
   private HashMap _nameToArray;
   private HashMap _nameToTypeName;
   private Class _viewTable;

   public TuxClassView(Class viewTable) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxClassView/TuxClassView/");
      }

      Class mySuper = null;
      Class mySelf = viewTable;
      String superName = null;
      boolean doSrch = true;

      while(true) {
         while(doSrch) {
            mySuper = mySelf.getSuperclass();
            if (mySuper == null) {
               doSrch = false;
               this._viewTable = viewTable;
            } else {
               superName = mySuper.getName();
               if (!superName.equals("weblogic.wtc.jatmi.TypedView") && !superName.equals("weblogic.wtc.jatmi.TypedView32") && !superName.equals("weblogic.wtc.jatmi.TypedXCommon") && !superName.equals("weblogic.wtc.jatmi.TypedXCType")) {
                  mySelf = mySuper;
               } else {
                  doSrch = false;
                  this._viewTable = mySelf;
               }
            }
         }

         this._nameToType = new HashMap(64);
         this._nameToArray = new HashMap(64);
         this._nameToTypeName = new HashMap(64);
         boolean match = false;
         boolean isArray = false;
         Class returnType = null;
         Method[] methods = viewTable.getDeclaredMethods();
         Field[] fields = viewTable.getDeclaredFields();

         for(int i = 0; i < fields.length; ++i) {
            String fname = fields[i].getName();
            int fmodifier = fields[i].getModifiers();
            match = false;
            isArray = false;
            if (Modifier.isPrivate(fmodifier) && !fname.equals("_associatedFieldHandling")) {
               for(int j = 0; j < methods.length; ++j) {
                  String mname = methods[j].getName();
                  int mmodifier = methods[j].getModifiers();
                  Class[] params = methods[j].getParameterTypes();
                  returnType = methods[j].getReturnType();
                  if (!returnType.getName().equals(Void.TYPE.getName()) && mname.startsWith("get") && params.length == 0) {
                     String name = mname.substring(3);
                     if (fname.equals(name)) {
                        match = true;
                        break;
                     }

                     if (fname.equalsIgnoreCase(name)) {
                        char[] nameArray = name.toCharArray();
                        char[] fnameArray = fname.toCharArray();
                        if (Character.isLetter(nameArray[0]) && Character.isUpperCase(nameArray[0])) {
                           match = true;
                           break;
                        }
                     }
                  }
               }
            }

            if (match) {
               if (returnType != null && returnType.isArray()) {
                  Class baseType = returnType.getComponentType();
                  if (baseType.getSigners() != null || !baseType.equals(Byte.TYPE)) {
                     returnType = baseType;
                     isArray = true;
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("/TuxClassView/TuxClassView - field Name: " + fname);
                  ntrace.doTrace("/TuxClassView/TuxClassView - returnType: " + returnType);
                  ntrace.doTrace("/TuxClassView/TuxClassView - isArray?: " + isArray);
               }

               this.addName(fname, returnType);
               this._nameToArray.put(fname, new Boolean(isArray));
               this._nameToTypeName.put(fname, returnType.getName());
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxClassView/TuxClassView");
         }

         return;
      }
   }

   public int getType(String name) {
      Integer type = (Integer)this._nameToType.get(name);
      return type == null ? -1 : type;
   }

   public String getTypeName(String name) {
      String typeName = (String)this._nameToTypeName.get(name);
      return typeName;
   }

   public boolean getIsArray(String name) {
      Boolean isArray = (Boolean)this._nameToArray.get(name);
      return isArray == null ? false : isArray;
   }

   public Set getKeySet() {
      return this._nameToType.keySet();
   }

   public Class getInternalClass() {
      return this._viewTable;
   }

   private void addName(String fldName, Class fieldType) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxClassView/addName/");
      }

      int typeCode = TuxedoPrimitives.getPrimitiveTypeCode(fieldType);
      byte tuxType;
      if (typeCode >= 10) {
         if (fieldType.isArray()) {
            tuxType = 6;
         } else {
            try {
               if (fieldType.newInstance() instanceof TypedView32) {
                  tuxType = 11;
               } else {
                  tuxType = -1;
               }
            } catch (Exception var7) {
               tuxType = -1;
            }
         }
      } else {
         switch (typeCode) {
            case 0:
               tuxType = 14;
               break;
            case 1:
            default:
               tuxType = -1;
               break;
            case 2:
               tuxType = 0;
               break;
            case 3:
               tuxType = 1;
               break;
            case 4:
               tuxType = 19;
               break;
            case 5:
               tuxType = 2;
               break;
            case 6:
               tuxType = 3;
               break;
            case 7:
               tuxType = 4;
               break;
            case 8:
               tuxType = 5;
               break;
            case 9:
               tuxType = 8;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("/TuxClassView/addName " + fldName + " " + tuxType);
      }

      this._nameToType.put(fldName, new Integer(tuxType));
      if (traceEnabled) {
         ntrace.doTrace("]/TuxClassView/addName");
      }

   }
}
