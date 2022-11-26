package weblogic.servlet.internal.tld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.jsp.tagext.TagInfo;
import weblogic.jsp.internal.jsp.JspTagLibraryFeature;
import weblogic.jsp.internal.jsp.tag.TagLibInfoEx;
import weblogic.jsp.internal.jsp.tag.TagListenerInfo;
import weblogic.servlet.utils.WarUtils;

public class ExtensionTLD {
   public static final String JSTL = "org/apache/taglibs/standard/";
   public static final String JSF2 = "com/sun/faces/";
   private static final List NULL_TAG_CLASSES = new ArrayList(2);
   private List extraTagClasses;
   private String dataObjectname;

   public ExtensionTLD(String dataObjectName, ClassLoader classloader) {
      this.dataObjectname = dataObjectName;
      this.extraTagClasses = getExtraTagClasses(dataObjectName, classloader);
   }

   public Set getTagClass(String type) {
      if ("tag-class".equals(type)) {
         return (Set)this.extraTagClasses.get(0);
      } else {
         return "listener-class".equals(type) ? (Set)this.extraTagClasses.get(1) : Collections.EMPTY_SET;
      }
   }

   public String getName() {
      return this.dataObjectname;
   }

   private static List getExtraTagClasses(String dataObjectName, ClassLoader loader) {
      List result = new ArrayList(2);
      JspTagLibraryFeature.DataObject dataObject = JspTagLibraryFeature.loadDataObject(dataObjectName, loader);
      if (dataObject == null) {
         return NULL_TAG_CLASSES;
      } else {
         Set tagClasses = null;
         Set tagListenerClasses = null;
         Map map = dataObject.getTldResourceMap();
         Collection values = map.values();
         if (values == null) {
            return NULL_TAG_CLASSES;
         } else {
            TagLibInfoEx library;
            for(Iterator var8 = values.iterator(); var8.hasNext(); tagListenerClasses = addTagListenerClasses(tagListenerClasses, library)) {
               library = (TagLibInfoEx)var8.next();
               tagClasses = addTagClasses(tagClasses, library);
            }

            result.add(tagClasses == null ? Collections.EMPTY_SET : tagClasses);
            result.add(tagListenerClasses == null ? Collections.EMPTY_SET : tagListenerClasses);
            return result;
         }
      }
   }

   private static Set addTagListenerClasses(Set tagListenerClasses, TagLibInfoEx library) {
      List tagListeners = library.getTagListeners();
      Set listeners = new HashSet();
      Iterator var4 = tagListeners.iterator();

      while(var4.hasNext()) {
         TagListenerInfo listenerInfo = (TagListenerInfo)var4.next();
         listeners.add(listenerInfo.getClassName());
      }

      tagListenerClasses = WarUtils.addAllIfNotEmpty((Set)tagListenerClasses, listeners);
      return tagListenerClasses;
   }

   private static Set addTagClasses(Set tagClasses, TagLibInfoEx library) {
      TagInfo[] tagInfos = library.getTags();
      Set tagHandlers = new HashSet();

      for(int i = 0; i < tagInfos.length; ++i) {
         tagHandlers.add(tagInfos[i].getTagClassName());
      }

      tagClasses = WarUtils.addAllIfNotEmpty((Set)tagClasses, tagHandlers);
      return tagClasses;
   }

   static {
      NULL_TAG_CLASSES.add(Collections.EMPTY_SET);
      NULL_TAG_CLASSES.add(Collections.EMPTY_SET);
   }
}
