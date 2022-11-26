package weblogic.corba.repository;

import java.util.Map;
import java.util.WeakHashMap;
import javax.validation.constraints.NotNull;
import org.omg.CORBA.BAD_PARAM;
import weblogic.corba.utils.ClassInfo;

public class SentClassesRepository {
   private static Map repositoryIdToClassInfoMap = new WeakHashMap();

   public static @NotNull ClassInfo findClassInfo(Class aClass) {
      ClassInfo classInfo = ClassInfo.findClassInfo(aClass);
      if (lookup(getRepositoryIdString(classInfo)) == null) {
         repositoryIdToClassInfoMap.put(getRepositoryIdString(classInfo), classInfo);
      }

      return classInfo;
   }

   private static String getRepositoryIdString(ClassInfo classInfo) {
      return classInfo.getRepositoryId().toString();
   }

   private static ClassInfo lookup(String repositoryId) {
      return (ClassInfo)repositoryIdToClassInfoMap.get(repositoryId);
   }

   public static @NotNull ClassInfo findClassInfo(String repositoryId) {
      ClassInfo classInfo = lookup(repositoryId);
      if (classInfo == null) {
         throw new BAD_PARAM("Could not find FVD class for: " + repositoryId);
      } else {
         return classInfo;
      }
   }
}
