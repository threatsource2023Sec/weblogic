package org.apache.openjpa.lib.conf;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.lib.util.ParseException;
import org.apache.openjpa.lib.util.StringDistance;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;
import serp.util.Strings;

public class Configurations {
   private static final Localizer _loc = Localizer.forPackage(Configurations.class);
   private static final ConcurrentReferenceHashMap _loaders = new ConcurrentReferenceHashMap(1, 0);
   private static final Object NULL_LOADER = "null-loader";

   public static String getClassName(String plugin) {
      return getPluginComponent(plugin, true);
   }

   public static String getProperties(String plugin) {
      return getPluginComponent(plugin, false);
   }

   private static String getPluginComponent(String plugin, boolean clsName) {
      if (plugin != null) {
         plugin = plugin.trim();
      }

      if (StringUtils.isEmpty(plugin)) {
         return null;
      } else {
         int openParen = -1;
         if (plugin.charAt(plugin.length() - 1) == ')') {
            openParen = plugin.indexOf(40);
         }

         if (openParen == -1) {
            int eq = plugin.indexOf(61);
            if (eq == -1) {
               return clsName ? plugin : null;
            } else {
               return clsName ? null : plugin;
            }
         } else if (clsName) {
            return plugin.substring(0, openParen).trim();
         } else {
            String prop = plugin.substring(openParen + 1, plugin.length() - 1).trim();
            return prop.length() == 0 ? null : prop;
         }
      }
   }

   public static String getPlugin(String clsName, String props) {
      if (StringUtils.isEmpty(clsName)) {
         return props;
      } else {
         return StringUtils.isEmpty(props) ? clsName : clsName + "(" + props + ")";
      }
   }

   public static String combinePlugins(String orig, String override) {
      if (StringUtils.isEmpty(orig)) {
         return override;
      } else if (StringUtils.isEmpty(override)) {
         return orig;
      } else {
         String origCls = getClassName(orig);
         String overrideCls = getClassName(override);
         String cls;
         if (StringUtils.isEmpty(origCls)) {
            cls = overrideCls;
         } else if (StringUtils.isEmpty(overrideCls)) {
            cls = origCls;
         } else {
            if (!origCls.equals(overrideCls)) {
               return override;
            }

            cls = origCls;
         }

         String origProps = getProperties(orig);
         String overrideProps = getProperties(override);
         if (StringUtils.isEmpty(origProps)) {
            return getPlugin(cls, overrideProps);
         } else if (StringUtils.isEmpty(overrideProps)) {
            return getPlugin(cls, origProps);
         } else {
            Properties props = parseProperties(origProps);
            props.putAll(parseProperties(overrideProps));
            return getPlugin(cls, serializeProperties(props));
         }
      }
   }

   public static Object newInstance(String clsName, ClassLoader loader) {
      return newInstance(clsName, (Value)null, (Configuration)null, loader, true);
   }

   public static Object newInstance(String clsName, Configuration conf, String props, ClassLoader loader) {
      Object obj = newInstance(clsName, (Value)null, conf, loader, true);
      configureInstance(obj, conf, props);
      return obj;
   }

   static Object newInstance(String clsName, Value val, Configuration conf, ClassLoader loader, boolean fatal) {
      if (StringUtils.isEmpty(clsName)) {
         return null;
      } else {
         Class cls = null;

         while(cls == null) {
            Object key = loader == null ? NULL_LOADER : loader;
            Map loaderCache = (Map)_loaders.get(key);
            if (loaderCache == null) {
               loaderCache = new ConcurrentHashMap();
               _loaders.put(key, loaderCache);
            } else {
               cls = (Class)((Map)loaderCache).get(clsName);
            }

            if (cls == null) {
               try {
                  cls = Strings.toClass(clsName, findDerivedLoader(conf, loader));
                  ((Map)loaderCache).put(clsName, cls);
               } catch (RuntimeException var11) {
                  RuntimeException re = var11;
                  if (loader == null) {
                     if (val != null) {
                        re = getCreateException(clsName, val, var11);
                     }

                     if (fatal) {
                        throw re;
                     }

                     Log log = conf == null ? null : conf.getConfigurationLog();
                     if (log != null && log.isErrorEnabled()) {
                        log.error(_loc.get("plugin-creation-exception", (Object)val), re);
                     }

                     return null;
                  }

                  loader = null;
               }
            }
         }

         try {
            return AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(cls));
         } catch (Exception var10) {
            Exception e = var10;
            if (var10 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var10).getException();
            }

            RuntimeException re = new NestableRuntimeException(_loc.get("obj-create", (Object)cls).getMessage(), e);
            if (fatal) {
               throw re;
            } else {
               Log log = conf == null ? null : conf.getConfigurationLog();
               if (log != null && log.isErrorEnabled()) {
                  log.error(_loc.get("plugin-creation-exception", (Object)val), re);
               }

               return null;
            }
         }
      }
   }

   private static ClassLoader findDerivedLoader(Configuration conf, ClassLoader loader) {
      ClassLoader ctxLoader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      if (loader == null) {
         if (ctxLoader != null) {
            return ctxLoader;
         } else {
            return conf != null ? (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(conf.getClass())) : Configurations.class.getClassLoader();
         }
      } else {
         ClassLoader parent;
         for(parent = ctxLoader; parent != null; parent = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getParentAction(parent))) {
            if (parent == loader) {
               return ctxLoader;
            }
         }

         if (conf != null) {
            for(parent = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(conf.getClass())); parent != null; parent = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getParentAction(parent))) {
               if (parent == loader) {
                  return (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(conf.getClass()));
               }
            }
         }

         return loader;
      }
   }

   public static List getFullyQualifiedAnchorsInPropertiesLocation(Options opts) {
      String props = opts.getProperty("properties", "p", (String)null);
      if (props != null) {
         int anchorPosition = props.indexOf("#");
         if (anchorPosition > -1) {
            return Arrays.asList(props);
         }
      }

      return ProductDerivations.getFullyQualifiedAnchorsInPropertiesLocation(props);
   }

   public static void populateConfiguration(Configuration conf, Options opts) {
      String props = opts.removeProperty("properties", "p", (String)null);
      ConfigurationProvider provider;
      if (!StringUtils.isEmpty(props)) {
         String path = props;
         String anchor = null;
         int idx = props.lastIndexOf(35);
         if (idx != -1) {
            if (idx < props.length() - 1) {
               anchor = props.substring(idx + 1);
            }

            path = props.substring(0, idx);
            if (path.length() == 0) {
               throw new MissingResourceException(_loc.get("anchor-only", (Object)props).getMessage(), Configurations.class.getName(), props);
            }
         }

         File file = new File(path);
         if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.isFileAction(file))) {
            provider = ProductDerivations.load((File)file, anchor, (ClassLoader)null);
         } else {
            file = new File("META-INF" + File.separatorChar + path);
            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.isFileAction(file))) {
               provider = ProductDerivations.load((File)file, anchor, (ClassLoader)null);
            } else {
               provider = ProductDerivations.load((String)path, anchor, (ClassLoader)null);
            }
         }

         if (provider == null) {
            throw new MissingResourceException(_loc.get("no-provider", (Object)props).getMessage(), Configurations.class.getName(), props);
         }

         provider.setInto(conf);
      } else {
         provider = ProductDerivations.loadDefaults((ClassLoader)null);
         if (provider != null) {
            provider.setInto(conf);
         }
      }

      opts.setInto(conf);
   }

   private static RuntimeException getCreateException(String clsName, Value val, Exception e) {
      String alias = val.alias(clsName);
      String[] aliases = val.getAliases();
      String[] keys;
      if (aliases.length == 0) {
         keys = aliases;
      } else {
         keys = new String[aliases.length / 2];

         for(int i = 0; i < aliases.length; i += 2) {
            keys[i / 2] = aliases[i];
         }
      }

      String msg;
      Object[] params;
      if (keys.length == 0) {
         msg = "invalid-plugin";
         params = new Object[]{val.getProperty(), alias, e.toString()};
      } else {
         String closest;
         if ((closest = StringDistance.getClosestLevenshteinDistance(alias, keys, 0.5F)) == null) {
            msg = "invalid-plugin-aliases";
            params = new Object[]{val.getProperty(), alias, e.toString(), new TreeSet(Arrays.asList(keys))};
         } else {
            msg = "invalid-plugin-aliases-hint";
            params = new Object[]{val.getProperty(), alias, e.toString(), new TreeSet(Arrays.asList(keys)), closest};
         }
      }

      return new ParseException(_loc.get(msg, params), e);
   }

   public static void configureInstance(Object obj, Configuration conf, String properties) {
      configureInstance(obj, conf, (String)properties, (String)null);
   }

   public static void configureInstance(Object obj, Configuration conf, String properties, String configurationName) {
      if (obj != null) {
         Properties props = null;
         if (!StringUtils.isEmpty(properties)) {
            props = parseProperties(properties);
         }

         configureInstance(obj, conf, (Properties)props, configurationName);
      }
   }

   public static void configureInstance(Object obj, Configuration conf, Properties properties) {
      configureInstance(obj, conf, (Properties)properties, (String)null);
   }

   public static void configureInstance(Object obj, Configuration conf, Properties properties, String configurationName) {
      if (obj != null) {
         Options opts;
         if (properties instanceof Options) {
            opts = (Options)properties;
         } else {
            opts = new Options();
            if (properties != null) {
               opts.putAll(properties);
            }
         }

         Configurable configurable = null;
         if (conf != null && obj instanceof Configurable) {
            configurable = (Configurable)obj;
         }

         if (configurable != null) {
            configurable.setConfiguration(conf);
            configurable.startConfiguration();
         }

         Options invalidEntries = opts.setInto(obj);
         if (obj instanceof GenericConfigurable) {
            ((GenericConfigurable)obj).setInto(invalidEntries);
         }

         if (!invalidEntries.isEmpty() && configurationName != null) {
            Localizer.Message msg = null;
            String first = (String)invalidEntries.keySet().iterator().next();
            if (invalidEntries.keySet().size() == 1 && first.indexOf(46) == -1) {
               Collection options = findOptionsFor(obj.getClass());
               String close = StringDistance.getClosestLevenshteinDistance(first, options, 0.75F);
               if (close != null) {
                  msg = _loc.get("invalid-config-param-hint", new Object[]{configurationName, obj.getClass(), first, close, options});
               }
            }

            if (msg == null) {
               msg = _loc.get("invalid-config-params", (Object[])(new String[]{configurationName, obj.getClass().getName(), invalidEntries.keySet().toString(), findOptionsFor(obj.getClass()).toString()}));
            }

            throw new ParseException(msg);
         } else {
            if (configurable != null) {
               configurable.endConfiguration();
            }

         }
      }
   }

   private static Collection findOptionsFor(Class cls) {
      Collection c = Options.findOptionsFor(cls);
      if (Configurable.class.isAssignableFrom(cls)) {
         c.remove("Configuration");
      }

      if (GenericConfigurable.class.isAssignableFrom(cls)) {
         c.remove("Into");
      }

      return c;
   }

   public static String serializeProperties(Map map) {
      if (map != null && !map.isEmpty()) {
         StringBuffer buf = new StringBuffer();
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            if (buf.length() > 0) {
               buf.append(", ");
            }

            buf.append(entry.getKey()).append('=');
            String val = String.valueOf(entry.getValue());
            if (val.indexOf(44) != -1) {
               buf.append('"').append(val).append('"');
            } else {
               buf.append(val);
            }
         }

         return buf.toString();
      } else {
         return null;
      }
   }

   public static Options parseProperties(String properties) {
      Options opts = new Options();
      properties = StringUtils.trimToNull(properties);
      if (properties == null) {
         return opts;
      } else {
         try {
            String[] props = Strings.split(properties, ",", 0);

            for(int i = 0; i < props.length; ++i) {
               int idx = props[i].indexOf(61);
               String prop;
               String val;
               if (idx == -1) {
                  prop = props[i];
                  val = prop;
               } else {
                  prop = props[i].substring(0, idx).trim();
                  val = props[i].substring(idx + 1).trim();
               }

               if ((val.startsWith("\"") && val.endsWith("\"") || val.startsWith("'") && val.endsWith("'")) && val.length() > 1) {
                  val = val.substring(1, val.length() - 1);
               } else if (val.startsWith("\"") || val.startsWith("'")) {
                  char quote = val.charAt(0);
                  StringBuffer buf = new StringBuffer(val.substring(1));

                  while(true) {
                     ++i;
                     if (i >= props.length) {
                        break;
                     }

                     buf.append(",");
                     int quotIdx = props[i].indexOf(quote);
                     if (quotIdx != -1) {
                        buf.append(props[i].substring(0, quotIdx));
                        if (quotIdx + 1 < props[i].length()) {
                           buf.append(props[i].substring(quotIdx + 1));
                        }
                        break;
                     }

                     buf.append(props[i]);
                  }

                  val = buf.toString();
               }

               opts.put(prop, val);
            }

            return opts;
         } catch (RuntimeException var10) {
            throw new ParseException(_loc.get("prop-parse", (Object)properties), var10);
         }
      }
   }

   public static Object lookup(String name) {
      if (StringUtils.isEmpty(name)) {
         return null;
      } else {
         Context ctx = null;

         Object var2;
         try {
            ctx = new InitialContext();
            var2 = ctx.lookup(name);
         } catch (NamingException var11) {
            throw new NestableRuntimeException(_loc.get("naming-err", (Object)name).getMessage(), var11);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (Exception var10) {
               }
            }

         }

         return var2;
      }
   }

   public static boolean containsProperty(String partialKey, Map props) {
      return partialKey != null && props != null && !props.isEmpty() ? props.containsKey(ProductDerivations.getConfigurationKey(partialKey, props)) : false;
   }

   public static Object getProperty(String partialKey, Map m) {
      return partialKey != null && m != null && !m.isEmpty() ? m.get(ProductDerivations.getConfigurationKey(partialKey, m)) : null;
   }

   public static Object removeProperty(String partialKey, Map props) {
      if (partialKey != null && props != null && !props.isEmpty()) {
         return containsProperty(partialKey, props) ? props.remove(ProductDerivations.getConfigurationKey(partialKey, props)) : null;
      } else {
         return null;
      }
   }

   public static boolean runAgainstAllAnchors(Options opts, Runnable runnable) {
      if (!opts.containsKey("help") && !opts.containsKey("-help")) {
         List anchors = getFullyQualifiedAnchorsInPropertiesLocation(opts);
         if (opts.containsKey("p")) {
            opts.remove("p");
         }

         boolean ret = true;
         Options clonedOptions;
         if (anchors.size() == 0) {
            ret = launchRunnable(opts, runnable);
         } else {
            for(Iterator iter = anchors.iterator(); iter.hasNext(); ret &= launchRunnable(clonedOptions, runnable)) {
               clonedOptions = (Options)opts.clone();
               clonedOptions.setProperty("properties", iter.next().toString());
            }
         }

         return ret;
      } else {
         return false;
      }
   }

   private static boolean launchRunnable(Options opts, Runnable runnable) {
      boolean ret = true;

      try {
         ret = runnable.run(opts);
         return ret;
      } catch (Exception var4) {
         if (!(var4 instanceof RuntimeException)) {
            throw new RuntimeException(var4);
         } else {
            throw (RuntimeException)var4;
         }
      }
   }

   public interface Runnable {
      boolean run(Options var1) throws Exception;
   }
}
