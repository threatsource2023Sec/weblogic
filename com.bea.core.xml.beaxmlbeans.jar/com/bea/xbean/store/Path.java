package com.bea.xbean.store;

import com.bea.xbean.common.XPath;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlDate;
import com.bea.xml.XmlDecimal;
import com.bea.xml.XmlDouble;
import com.bea.xml.XmlFloat;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.w3c.dom.Node;

public abstract class Path {
   public static final String PATH_DELEGATE_INTERFACE = "PATH_DELEGATE_INTERFACE";
   public static String _useDelegateForXpath = "use delegate for xpath";
   public static String _useXdkForXpath = "use xdk for xpath";
   public static String _useXqrlForXpath = "use xqrl for xpath";
   public static String _useXbeanForXpath = "use xbean for xpath";
   public static String _forceXqrl2002ForXpathXQuery = "use xqrl-2002 for xpath";
   private static final int USE_XBEAN = 1;
   private static final int USE_XQRL = 2;
   private static final int USE_DELEGATE = 4;
   private static final int USE_XQRL2002 = 8;
   private static final int USE_XDK = 16;
   private static Map _xbeanPathCache = new WeakHashMap();
   private static Map _xdkPathCache = new WeakHashMap();
   private static Map _xqrlPathCache = new WeakHashMap();
   private static Map _xqrl2002PathCache = new WeakHashMap();
   private static Method _xdkCompilePath;
   private static Method _xqrlCompilePath;
   private static Method _xqrl2002CompilePath;
   private static boolean _xdkAvailable = true;
   private static boolean _xqrlAvailable = true;
   private static boolean _xqrl2002Available = true;
   private static String _delIntfName;
   private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
   protected final String _pathKey;

   Path(String key) {
      this._pathKey = key;
   }

   abstract PathEngine execute(Cur var1, XmlOptions var2);

   static String getCurrentNodeVar(XmlOptions options) {
      String currentNodeVar = "this";
      options = XmlOptions.maskNull(options);
      if (options.hasOption("XQUERY_CURRENT_NODE_VAR")) {
         currentNodeVar = (String)options.get("XQUERY_CURRENT_NODE_VAR");
         if (currentNodeVar.startsWith("$")) {
            throw new IllegalArgumentException("Omit the '$' prefix for the current node variable");
         }
      }

      return currentNodeVar;
   }

   public static Path getCompiledPath(String pathExpr, XmlOptions options) {
      options = XmlOptions.maskNull(options);
      int force = options.hasOption(_useDelegateForXpath) ? 4 : (options.hasOption(_useXqrlForXpath) ? 2 : (options.hasOption(_useXdkForXpath) ? 16 : (options.hasOption(_useXbeanForXpath) ? 1 : (options.hasOption(_forceXqrl2002ForXpathXQuery) ? 8 : 23))));
      String delIntfName = options.hasOption("PATH_DELEGATE_INTERFACE") ? (String)options.get("PATH_DELEGATE_INTERFACE") : _delIntfName;
      return getCompiledPath(pathExpr, force, getCurrentNodeVar(options), delIntfName);
   }

   static Path getCompiledPath(String pathExpr, int force, String currentVar, String delIntfName) {
      Path path = null;
      WeakReference pathWeakRef = null;
      Map namespaces = (force & 4) != 0 ? new HashMap() : null;
      lock.readLock().lock();

      try {
         if ((force & 1) != 0) {
            pathWeakRef = (WeakReference)_xbeanPathCache.get(pathExpr);
         }

         if (pathWeakRef == null && (force & 2) != 0) {
            pathWeakRef = (WeakReference)_xqrlPathCache.get(pathExpr);
         }

         if (pathWeakRef == null && (force & 16) != 0) {
            pathWeakRef = (WeakReference)_xdkPathCache.get(pathExpr);
         }

         if (pathWeakRef == null && (force & 8) != 0) {
            pathWeakRef = (WeakReference)_xqrl2002PathCache.get(pathExpr);
         }

         if (pathWeakRef != null) {
            path = (Path)pathWeakRef.get();
         }

         if (path != null) {
            Path var7 = path;
            return var7;
         }
      } finally {
         lock.readLock().unlock();
      }

      lock.writeLock().lock();

      try {
         if ((force & 1) != 0) {
            pathWeakRef = (WeakReference)_xbeanPathCache.get(pathExpr);
            if (pathWeakRef != null) {
               path = (Path)pathWeakRef.get();
            }

            if (path == null) {
               path = getCompiledPathXbean(pathExpr, currentVar, namespaces);
            }
         }

         if (path == null && (force & 2) != 0) {
            pathWeakRef = (WeakReference)_xqrlPathCache.get(pathExpr);
            if (pathWeakRef != null) {
               path = (Path)pathWeakRef.get();
            }

            if (path == null) {
               path = getCompiledPathXqrl(pathExpr, currentVar);
            }
         }

         if (path == null && (force & 16) != 0) {
            pathWeakRef = (WeakReference)_xdkPathCache.get(pathExpr);
            if (pathWeakRef != null) {
               path = (Path)pathWeakRef.get();
            }

            if (path == null) {
               path = getCompiledPathXdk(pathExpr, currentVar);
            }
         }

         if (path == null && (force & 4) != 0) {
            path = getCompiledPathDelegate(pathExpr, currentVar, namespaces, delIntfName);
         }

         if (path == null && (force & 8) != 0) {
            pathWeakRef = (WeakReference)_xqrl2002PathCache.get(pathExpr);
            if (pathWeakRef != null) {
               path = (Path)pathWeakRef.get();
            }

            if (path == null) {
               path = getCompiledPathXqrl2002(pathExpr, currentVar);
            }
         }

         if (path == null) {
            StringBuffer errMessage = new StringBuffer();
            if ((force & 1) != 0) {
               errMessage.append(" Trying XBeans path engine...");
            }

            if ((force & 2) != 0) {
               errMessage.append(" Trying XQRL...");
            }

            if ((force & 16) != 0) {
               errMessage.append(" Trying XDK...");
            }

            if ((force & 4) != 0) {
               errMessage.append(" Trying delegated path engine...");
            }

            if ((force & 8) != 0) {
               errMessage.append(" Trying XQRL2002...");
            }

            throw new RuntimeException(errMessage.toString() + " FAILED on " + pathExpr);
         }
      } finally {
         lock.writeLock().unlock();
      }

      return path;
   }

   private static Path getCompiledPathXdk(String pathExpr, String currentVar) {
      Path path = createXdkCompiledPath(pathExpr, currentVar);
      if (path != null) {
         _xdkPathCache.put(path._pathKey, new WeakReference(path));
      }

      return path;
   }

   private static Path getCompiledPathXqrl(String pathExpr, String currentVar) {
      Path path = createXqrlCompiledPath(pathExpr, currentVar);
      if (path != null) {
         _xqrlPathCache.put(path._pathKey, new WeakReference(path));
      }

      return path;
   }

   private static Path getCompiledPathXqrl2002(String pathExpr, String currentVar) {
      Path path = createXqrl2002CompiledPath(pathExpr, currentVar);
      if (path != null) {
         _xqrl2002PathCache.put(path._pathKey, new WeakReference(path));
      }

      return path;
   }

   private static Path getCompiledPathXbean(String pathExpr, String currentVar, Map namespaces) {
      Path path = Path.XbeanPath.create(pathExpr, currentVar, namespaces);
      if (path != null) {
         _xbeanPathCache.put(path._pathKey, new WeakReference(path));
      }

      return path;
   }

   private static Path getCompiledPathDelegate(String pathExpr, String currentVar, Map namespaces, String delIntfName) {
      Path path = null;
      if (namespaces == null) {
         namespaces = new HashMap();
      }

      try {
         XPath.compileXPath(pathExpr, currentVar, (Map)namespaces);
      } catch (XPath.XPathCompileException var6) {
      }

      int offset = ((Map)namespaces).get("$xmlbeans!ns_boundary") == null ? 0 : (Integer)((Map)namespaces).get("$xmlbeans!ns_boundary");
      ((Map)namespaces).remove("$xmlbeans!ns_boundary");
      path = Path.DelegatePathImpl.create(delIntfName, pathExpr.substring(offset), currentVar, (Map)namespaces);
      return path;
   }

   public static String compilePath(String pathExpr, XmlOptions options) {
      return getCompiledPath(pathExpr, options)._pathKey;
   }

   private static Path createXdkCompiledPath(String pathExpr, String currentVar) {
      if (!_xdkAvailable) {
         return null;
      } else {
         if (_xdkCompilePath == null) {
            try {
               Class xdkImpl = Class.forName("com.bea.xbean.store.OXQXBXqrlImpl");
               _xdkCompilePath = xdkImpl.getDeclaredMethod("compilePath", String.class, String.class, Boolean.class);
            } catch (ClassNotFoundException var7) {
               _xdkAvailable = false;
               return null;
            } catch (Exception var8) {
               _xdkAvailable = false;
               throw new RuntimeException(var8.getMessage(), var8);
            }
         }

         Object[] args = new Object[]{pathExpr, currentVar, new Boolean(true)};

         try {
            return (Path)_xdkCompilePath.invoke((Object)null, args);
         } catch (InvocationTargetException var5) {
            Throwable t = var5.getCause();
            throw new RuntimeException(t.getMessage(), t);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6.getMessage(), var6);
         }
      }
   }

   private static Path createXqrlCompiledPath(String pathExpr, String currentVar) {
      if (!_xqrlAvailable) {
         return null;
      } else {
         if (_xqrlCompilePath == null) {
            try {
               Class xqrlImpl = Class.forName("com.bea.xbean.store.XqrlImpl");
               _xqrlCompilePath = xqrlImpl.getDeclaredMethod("compilePath", String.class, String.class, Boolean.class);
            } catch (ClassNotFoundException var7) {
               _xqrlAvailable = false;
               return null;
            } catch (Exception var8) {
               _xqrlAvailable = false;
               throw new RuntimeException(var8.getMessage(), var8);
            }
         }

         Object[] args = new Object[]{pathExpr, currentVar, new Boolean(true)};

         try {
            return (Path)_xqrlCompilePath.invoke((Object)null, args);
         } catch (InvocationTargetException var5) {
            Throwable t = var5.getCause();
            throw new RuntimeException(t.getMessage(), t);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6.getMessage(), var6);
         }
      }
   }

   private static Path createXqrl2002CompiledPath(String pathExpr, String currentVar) {
      if (!_xqrl2002Available) {
         return null;
      } else {
         if (_xqrl2002CompilePath == null) {
            try {
               Class xqrlImpl = Class.forName("com.bea.xbean.store.Xqrl2002Impl");
               _xqrl2002CompilePath = xqrlImpl.getDeclaredMethod("compilePath", String.class, String.class, Boolean.class);
            } catch (ClassNotFoundException var7) {
               _xqrl2002Available = false;
               return null;
            } catch (Exception var8) {
               _xqrl2002Available = false;
               throw new RuntimeException(var8.getMessage(), var8);
            }
         }

         Object[] args = new Object[]{pathExpr, currentVar, new Boolean(true)};

         try {
            return (Path)_xqrl2002CompilePath.invoke((Object)null, args);
         } catch (InvocationTargetException var5) {
            Throwable t = var5.getCause();
            throw new RuntimeException(t.getMessage(), t);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6.getMessage(), var6);
         }
      }
   }

   static {
      ClassLoader cl = Path.class.getClassLoader();
      String id = "META-INF/services/com.bea.xbean.store.PathDelegate.SelectPathInterface";
      InputStream in = cl.getResourceAsStream(id);

      try {
         BufferedReader br = new BufferedReader(new InputStreamReader(in));
         _delIntfName = br.readLine().trim();
         br.close();
      } catch (Exception var4) {
         _delIntfName = null;
      }

   }

   private static final class DelegatePathImpl extends Path {
      private PathDelegate.SelectPathInterface _xpathImpl;

      static Path create(String implClassName, String pathExpr, String currentNodeVar, Map namespaceMap) {
         assert !currentNodeVar.startsWith("$");

         PathDelegate.SelectPathInterface impl = PathDelegate.createInstance(implClassName, pathExpr, currentNodeVar, namespaceMap);
         return impl == null ? null : new DelegatePathImpl(impl, pathExpr);
      }

      private DelegatePathImpl(PathDelegate.SelectPathInterface xpathImpl, String pathExpr) {
         super(pathExpr);
         this._xpathImpl = xpathImpl;
      }

      protected PathEngine execute(Cur c, XmlOptions options) {
         return new DelegatePathEngine(this._xpathImpl, c);
      }

      private static class DelegatePathEngine extends XPath.ExecutionContext implements PathEngine {
         private Cur _cur;
         private PathDelegate.SelectPathInterface _engine;
         private boolean _firstCall = true;
         private long _version;

         DelegatePathEngine(PathDelegate.SelectPathInterface xpathImpl, Cur c) {
            this._engine = xpathImpl;
            this._version = c._locale.version();
            this._cur = c.weakCur(this);
         }

         public boolean next(Cur c) {
            if (!this._firstCall) {
               return false;
            } else {
               this._firstCall = false;
               if (this._cur != null && this._version != this._cur._locale.version()) {
                  throw new ConcurrentModificationException("Document changed during select");
               } else {
                  Object context_node = this._cur.getDom();
                  List resultsList = this._engine.selectPath(context_node);

                  for(int i = 0; i < resultsList.size(); ++i) {
                     Object node = resultsList.get(i);
                     Cur pos = null;
                     if (!(node instanceof Node)) {
                        String value = resultsList.get(i).toString();
                        Locale l = c._locale;

                        try {
                           pos = l.load("<xml-fragment/>").tempCur();
                           pos.setValue(value);
                           SchemaType type = this.getType(node);
                           Locale.autoTypeDocument(pos, type, (XmlOptions)null);
                           pos.next();
                        } catch (Exception var10) {
                           throw new RuntimeException(var10);
                        }
                     } else {
                        assert node instanceof DomImpl.Dom : "New object created in XPATH!";

                        pos = ((DomImpl.Dom)node).tempCur();
                     }

                     c.addToSelection(pos);
                     pos.release();
                  }

                  this.release();
                  this._engine = null;
                  return true;
               }
            }
         }

         private SchemaType getType(Object node) {
            SchemaType type;
            if (node instanceof Integer) {
               type = XmlInteger.type;
            } else if (node instanceof Double) {
               type = XmlDouble.type;
            } else if (node instanceof Long) {
               type = XmlLong.type;
            } else if (node instanceof Float) {
               type = XmlFloat.type;
            } else if (node instanceof BigDecimal) {
               type = XmlDecimal.type;
            } else if (node instanceof Boolean) {
               type = XmlBoolean.type;
            } else if (node instanceof String) {
               type = XmlString.type;
            } else if (node instanceof Date) {
               type = XmlDate.type;
            } else {
               type = XmlAnySimpleType.type;
            }

            return type;
         }

         public void release() {
            if (this._cur != null) {
               this._cur.release();
               this._cur = null;
            }

         }
      }
   }

   private static final class XbeanPathEngine extends XPath.ExecutionContext implements PathEngine {
      private final long _version;
      private Cur _cur;

      XbeanPathEngine(XPath xpath, Cur c) {
         assert c.isContainer();

         this._version = c._locale.version();
         this._cur = c.weakCur(this);
         this._cur.push();
         this.init(xpath);
         int ret = this.start();
         if ((ret & 1) != 0) {
            c.addToSelection();
         }

         this.doAttrs(ret, c);
         if ((ret & 2) == 0 || !Locale.toFirstChildElement(this._cur)) {
            this.release();
         }

      }

      private void advance(Cur c) {
         assert this._cur != null;

         if (this._cur.isFinish()) {
            if (this._cur.isAtEndOfLastPush()) {
               this.release();
            } else {
               this.end();
               this._cur.next();
            }
         } else if (this._cur.isElem()) {
            int ret = this.element(this._cur.getName());
            if ((ret & 1) != 0) {
               c.addToSelection(this._cur);
            }

            this.doAttrs(ret, c);
            if ((ret & 2) == 0 || !Locale.toFirstChildElement(this._cur)) {
               this.end();
               this._cur.skip();
            }
         } else {
            do {
               this._cur.next();
            } while(!this._cur.isContainerOrFinish());
         }

      }

      private void doAttrs(int ret, Cur c) {
         assert this._cur.isContainer();

         if ((ret & 4) != 0 && this._cur.toFirstAttr()) {
            do {
               if (this.attr(this._cur.getName())) {
                  c.addToSelection(this._cur);
               }
            } while(this._cur.toNextAttr());

            this._cur.toParent();
         }

      }

      public boolean next(Cur c) {
         if (this._cur != null && this._version != this._cur._locale.version()) {
            throw new ConcurrentModificationException("Document changed during select");
         } else {
            int startCount = c.selectionCount();

            do {
               if (this._cur == null) {
                  return false;
               }

               this.advance(c);
            } while(startCount == c.selectionCount());

            return true;
         }
      }

      public void release() {
         if (this._cur != null) {
            this._cur.release();
            this._cur = null;
         }

      }
   }

   private static final class XbeanPath extends Path {
      private final String _currentVar;
      private final XPath _compiledPath;
      public Map namespaces;

      static Path create(String pathExpr, String currentVar, Map namespaces) {
         try {
            return new XbeanPath(pathExpr, currentVar, XPath.compileXPath(pathExpr, currentVar, namespaces));
         } catch (XPath.XPathCompileException var4) {
            return null;
         }
      }

      private XbeanPath(String pathExpr, String currentVar, XPath xpath) {
         super(pathExpr);
         this._currentVar = currentVar;
         this._compiledPath = xpath;
      }

      PathEngine execute(Cur c, XmlOptions options) {
         options = XmlOptions.maskNull(options);
         String delIntfName = options.hasOption("PATH_DELEGATE_INTERFACE") ? (String)options.get("PATH_DELEGATE_INTERFACE") : Path._delIntfName;
         if (c.isContainer() && !this._compiledPath.sawDeepDot()) {
            return new XbeanPathEngine(this._compiledPath, c);
         } else {
            int force = 22;
            return getCompiledPath(this._pathKey, force, this._currentVar, delIntfName).execute(c, options);
         }
      }
   }

   interface PathEngine {
      void release();

      boolean next(Cur var1);
   }
}
