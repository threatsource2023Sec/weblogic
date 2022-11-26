package kodo.jdo;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import javax.jdo.JDOUserException;
import org.apache.openjpa.meta.AbstractMetaDataDefaults;
import org.apache.openjpa.meta.ClassMetaData;

class JDOMetaDataDefaults extends AbstractMetaDataDefaults {
   private static Set _persistentContainers = new HashSet();

   public JDOMetaDataDefaults() {
      this.setDeclaredInterfacePersistent(false);
      this.setDefaultIdentityType(1);
   }

   protected boolean isDefaultPersistent(ClassMetaData meta, Member member, String name) {
      int mods = member.getModifiers();
      if (Modifier.isTransient(mods)) {
         return false;
      } else {
         Class type = member instanceof Field ? ((Field)member).getType() : ((Method)member).getReturnType();
         if (type.isArray()) {
            type = type.getComponentType();
         }

         return isDefaultPersistent(type) || isUserDefined(type);
      }
   }

   private static boolean isDefaultPersistent(Class var0) {
      // $FF: Couldn't be decompiled
   }

   public boolean getCallbacksBeforeListeners(int type) {
      switch (type) {
         case 2:
         case 14:
         case 16:
            return true;
         default:
            return false;
      }
   }

   public Class getUnimplementedExceptionType() {
      return JDOUserException.class;
   }

   static {
      _persistentContainers.add(Collection.class);
      _persistentContainers.add(List.class);
      _persistentContainers.add(Set.class);
      _persistentContainers.add(SortedSet.class);
      _persistentContainers.add(ArrayList.class);
      _persistentContainers.add(LinkedList.class);
      _persistentContainers.add(HashSet.class);
      _persistentContainers.add(TreeSet.class);
      _persistentContainers.add(Vector.class);
      _persistentContainers.add(Map.class);
      _persistentContainers.add(SortedMap.class);
      _persistentContainers.add(HashMap.class);
      _persistentContainers.add(Hashtable.class);
      _persistentContainers.add(TreeMap.class);
   }
}
