package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleAliasRegistry implements AliasRegistry {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Map aliasMap = new ConcurrentHashMap(16);

   public void registerAlias(String name, String alias) {
      Assert.hasText(name, "'name' must not be empty");
      Assert.hasText(alias, "'alias' must not be empty");
      synchronized(this.aliasMap) {
         if (alias.equals(name)) {
            this.aliasMap.remove(alias);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Alias definition '" + alias + "' ignored since it points to same name");
            }
         } else {
            String registeredName = (String)this.aliasMap.get(alias);
            if (registeredName != null) {
               if (registeredName.equals(name)) {
                  return;
               }

               if (!this.allowAliasOverriding()) {
                  throw new IllegalStateException("Cannot define alias '" + alias + "' for name '" + name + "': It is already registered for name '" + registeredName + "'.");
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Overriding alias '" + alias + "' definition for registered name '" + registeredName + "' with new target name '" + name + "'");
               }
            }

            this.checkForAliasCircle(name, alias);
            this.aliasMap.put(alias, name);
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Alias definition '" + alias + "' registered for name '" + name + "'");
            }
         }

      }
   }

   protected boolean allowAliasOverriding() {
      return true;
   }

   public boolean hasAlias(String name, String alias) {
      Iterator var3 = this.aliasMap.entrySet().iterator();

      String registeredAlias;
      do {
         Map.Entry entry;
         String registeredName;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            entry = (Map.Entry)var3.next();
            registeredName = (String)entry.getValue();
         } while(!registeredName.equals(name));

         registeredAlias = (String)entry.getKey();
      } while(!registeredAlias.equals(alias) && !this.hasAlias(registeredAlias, alias));

      return true;
   }

   public void removeAlias(String alias) {
      synchronized(this.aliasMap) {
         String name = (String)this.aliasMap.remove(alias);
         if (name == null) {
            throw new IllegalStateException("No alias '" + alias + "' registered");
         }
      }
   }

   public boolean isAlias(String name) {
      return this.aliasMap.containsKey(name);
   }

   public String[] getAliases(String name) {
      List result = new ArrayList();
      synchronized(this.aliasMap) {
         this.retrieveAliases(name, result);
      }

      return StringUtils.toStringArray((Collection)result);
   }

   private void retrieveAliases(String name, List result) {
      this.aliasMap.forEach((alias, registeredName) -> {
         if (registeredName.equals(name)) {
            result.add(alias);
            this.retrieveAliases(alias, result);
         }

      });
   }

   public void resolveAliases(StringValueResolver valueResolver) {
      Assert.notNull(valueResolver, (String)"StringValueResolver must not be null");
      synchronized(this.aliasMap) {
         Map aliasCopy = new HashMap(this.aliasMap);
         aliasCopy.forEach((alias, registeredName) -> {
            String resolvedAlias = valueResolver.resolveStringValue(alias);
            String resolvedName = valueResolver.resolveStringValue(registeredName);
            if (resolvedAlias != null && resolvedName != null && !resolvedAlias.equals(resolvedName)) {
               if (!resolvedAlias.equals(alias)) {
                  String existingName = (String)this.aliasMap.get(resolvedAlias);
                  if (existingName != null) {
                     if (existingName.equals(resolvedName)) {
                        this.aliasMap.remove(alias);
                        return;
                     }

                     throw new IllegalStateException("Cannot register resolved alias '" + resolvedAlias + "' (original: '" + alias + "') for name '" + resolvedName + "': It is already registered for name '" + registeredName + "'.");
                  }

                  this.checkForAliasCircle(resolvedName, resolvedAlias);
                  this.aliasMap.remove(alias);
                  this.aliasMap.put(resolvedAlias, resolvedName);
               } else if (!registeredName.equals(resolvedName)) {
                  this.aliasMap.put(alias, resolvedName);
               }
            } else {
               this.aliasMap.remove(alias);
            }

         });
      }
   }

   protected void checkForAliasCircle(String name, String alias) {
      if (this.hasAlias(alias, name)) {
         throw new IllegalStateException("Cannot register alias '" + alias + "' for name '" + name + "': Circular reference - '" + name + "' is a direct or indirect alias for '" + alias + "' already");
      }
   }

   public String canonicalName(String name) {
      String canonicalName = name;

      String resolvedName;
      do {
         resolvedName = (String)this.aliasMap.get(canonicalName);
         if (resolvedName != null) {
            canonicalName = resolvedName;
         }
      } while(resolvedName != null);

      return canonicalName;
   }
}
