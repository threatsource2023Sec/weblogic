package weblogic.corba.repository;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.corba.utils.ClassInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class ReceivedClassesRepository {
   private static final URLClassLoader DEFAULT_CLASSLOADER = new URLClassLoader(new URL[0]);
   private static Map repositories = new ConcurrentWeakHashMap();
   private Map repositoryIdMap = new ConcurrentHashMap();

   public static ReceivedClassesRepository getRepository() {
      ClassLoader classLoader = getCurrentClassLoader();
      return repositories.containsKey(classLoader) ? (ReceivedClassesRepository)repositories.get(classLoader) : createRepository(classLoader);
   }

   private static ClassLoader getCurrentClassLoader() {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      return (ClassLoader)(classLoader != null ? classLoader : DEFAULT_CLASSLOADER);
   }

   private static synchronized ReceivedClassesRepository createRepository(ClassLoader classLoader) {
      if (repositories.containsKey(classLoader)) {
         return (ReceivedClassesRepository)repositories.get(classLoader);
      } else {
         ReceivedClassesRepository repository = new ReceivedClassesRepository();
         repositories.put(classLoader, repository);
         return repository;
      }
   }

   public ClassInfo findClassInfo(RepositoryId repId, String codebase) {
      if (repId == null) {
         return null;
      } else {
         ClassInfo info = (ClassInfo)this.repositoryIdMap.get(repId.toString());
         if (info != null) {
            return info;
         } else {
            info = new ClassInfo(repId, codebase);
            this.repositoryIdMap.put(repId.toString(), info);
            return info;
         }
      }
   }

   public Class findClass(RepositoryId repid) {
      return this.findClassInfo(repid, (String)null).forClass();
   }
}
