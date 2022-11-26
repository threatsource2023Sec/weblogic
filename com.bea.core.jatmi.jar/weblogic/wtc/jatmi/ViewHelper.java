package weblogic.wtc.jatmi;

import java.util.Hashtable;

public class ViewHelper {
   private static ViewHelper instance = new ViewHelper();
   private static Hashtable viewMap;

   public static ViewHelper getInstance() {
      return instance;
   }

   public TypedBuffer newViewInstance(String viewName) throws InstantiationException, IllegalAccessException {
      Class cl;
      return (cl = (Class)viewMap.get(viewName)) != null ? (TypedBuffer)cl.newInstance() : null;
   }

   public void setViewClass(String viewName, String className) throws ClassNotFoundException {
      viewMap.put(viewName, Class.forName(className));
   }

   public void setViewClass(String viewName, Class viewclass) {
      if (!viewMap.containsKey(viewName)) {
         viewMap.put(viewName, viewclass);
      }

   }

   static {
      ViewHelper var10000 = instance;
      viewMap = new Hashtable();
   }
}
