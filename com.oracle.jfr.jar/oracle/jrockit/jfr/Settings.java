package oracle.jrockit.jfr;

import com.oracle.jrockit.jfr.EventInfo;
import com.oracle.jrockit.jfr.NoSuchEventException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import oracle.jrockit.jfr.events.EventControl;
import oracle.jrockit.jfr.settings.EventDefaultSet;
import oracle.jrockit.jfr.settings.EventSetting;
import oracle.jrockit.jfr.settings.EventSettings;

final class Settings implements EventSettings {
   private final Object lock;
   private final Map eventControls;
   private final Collection recordings;
   private final MetaProducer metaProducer;
   private final HashMap eventSettings = new HashMap();
   private final HashMap disabledSettings = new HashMap();
   private final Aggregator globalAggregator = new Aggregator();

   Settings(Object eventLock, Map eventControls, Collection recordings, MetaProducer metaProducer) {
      this.lock = eventLock;
      this.eventControls = eventControls;
      this.recordings = recordings;
      this.metaProducer = metaProducer;
   }

   void addEvents(Collection events) {
      synchronized(this.lock) {
         HashMap map = new HashMap();
         Iterator i$ = events.iterator();

         while(i$.hasNext()) {
            EventInfo e = (EventInfo)i$.next();
            int id = e.getId();
            map.put(id, new EventSetting(id));
         }

         this.disabledSettings.putAll(map);
         this.globalAggregator.updateDefaults(events);
         i$ = this.recordings.iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            r.settingsAggregator.updateDefaults(events);
         }

         this.update(map);
      }
   }

   private void update(HashMap settings) {
      assert Thread.holdsLock(this.lock);

      Iterator i$ = this.recordings.iterator();

      while(i$.hasNext()) {
         Recording r = (Recording)i$.next();
         if (r.isRunning()) {
            r.settingsAggregator.mergeAll(settings);
         }
      }

      i$ = settings.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry e = (Map.Entry)i$.next();
         EventControl c = (EventControl)this.eventControls.get(e.getKey());

         assert c != null;

         c.apply((EventSetting)e.getValue());
      }

      this.metaProducer.settingsChanged(settings.values(), this.eventSettings);
      this.eventSettings.putAll(settings);
   }

   void update() {
      synchronized(this.lock) {
         HashMap map = new HashMap(this.disabledSettings);
         this.update(map);
      }
   }

   void removeEvents(Collection events) {
      synchronized(this.lock) {
         Iterator i$ = events.iterator();

         while(i$.hasNext()) {
            EventInfo e = (EventInfo)i$.next();
            int id = e.getId();
            this.disabledSettings.remove(id);
            this.eventSettings.remove(id);
         }

         this.globalAggregator.remove(events);
         i$ = this.recordings.iterator();

         while(i$.hasNext()) {
            Recording r = (Recording)i$.next();
            r.settingsAggregator.remove(events);
         }

      }
   }

   Aggregator subAggregator() {
      return new Aggregator(this.globalAggregator);
   }

   public void addEventDefaultSet(EventDefaultSet d) {
      this.globalAggregator.addEventDefaultSet(d);
   }

   public Collection getEventDefaults() {
      return this.globalAggregator.getEventDefaults();
   }

   public List getEventDefaultSets() {
      return this.globalAggregator.getEventDefaultSets();
   }

   public EventSetting getSetting(int id) throws NoSuchEventException {
      synchronized(this.lock) {
         EventSetting s = (EventSetting)this.eventSettings.get(id);
         if (s == null) {
            throw new NoSuchEventException(String.valueOf(id));
         } else {
            return s;
         }
      }
   }

   public Collection getSettings() {
      synchronized(this.lock) {
         return new ArrayList(this.eventSettings.values());
      }
   }

   public void putSettings(Collection settings) {
      throw new IllegalStateException("No allowed");
   }

   public void replaceEventDefaultSets(Collection l) {
      this.globalAggregator.replaceEventDefaultSets(l);
   }

   final class Aggregator implements EventSettings {
      private final CopyOnWriteArrayList defaultSets;
      private final HashMap defaults;
      private final HashMap settings;
      private final Aggregator parent;

      Aggregator() {
         this((Aggregator)null);
      }

      Aggregator(Aggregator parent) {
         this.defaultSets = new CopyOnWriteArrayList();
         this.defaults = new HashMap();
         this.settings = new HashMap();
         this.parent = parent;
      }

      private void updateDefaults() {
         this.updateDefaults(Settings.this.eventControls.values());
         Settings.this.update();
      }

      void updateDefaults(Collection events) {
         assert Thread.holdsLock(Settings.this.lock);

         this.mergeDefaults(events, this.defaults);
      }

      private void remove(Collection events) {
         synchronized(Settings.this.lock) {
            Iterator i$ = events.iterator();

            while(i$.hasNext()) {
               EventInfo e = (EventInfo)i$.next();
               int id = e.getId();
               this.defaults.remove(id);
               this.settings.remove(id);
            }

         }
      }

      private void putAll(Collection events, Map result) {
         synchronized(Settings.this.lock) {
            Iterator i$ = events.iterator();

            while(i$.hasNext()) {
               EventInfo e = (EventInfo)i$.next();
               int id = e.getId();
               result.put(id, this.get(id));
            }

         }
      }

      void mergeAll(Map result) {
         assert Thread.holdsLock(Settings.this.lock);

         Iterator i$ = result.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry e = (Map.Entry)i$.next();
            int id = (Integer)e.getKey();
            EventSetting o = (EventSetting)e.getValue();
            EventSetting s = this.get0(id);
            if (s != null && s != o) {
               e.setValue(new EventSetting(s, o));
            }
         }

      }

      public Collection getSettings(Collection events) {
         HashMap result = new HashMap();
         this.putAll(events, result);
         return result.values();
      }

      public Collection getSettings() {
         return this.getSettings(Settings.this.eventControls.values());
      }

      private EventSetting get0(int id) {
         EventSetting s = null;
         if (!this.settings.isEmpty()) {
            s = (EventSetting)this.settings.get(id);
         }

         if (s == null && !this.defaults.isEmpty()) {
            s = (EventSetting)this.defaults.get(id);
         }

         if (s == null && this.parent != null) {
            s = this.parent.get0(id);
         }

         return s;
      }

      private EventSetting get(int id) {
         EventSetting s = this.get0(id);
         if (s == null) {
            s = (EventSetting)Settings.this.disabledSettings.get(id);
         }

         return s;
      }

      private boolean exists(int id) {
         return Settings.this.disabledSettings.containsKey(id);
      }

      public EventSetting getSetting(int id) throws NoSuchEventException {
         synchronized(Settings.this.lock) {
            EventSetting s = this.get(id);
            if (s == null) {
               throw new NoSuchEventException(String.valueOf(id));
            } else {
               return s;
            }
         }
      }

      public void putSettings(Collection settings) {
         HashMap result = new HashMap();
         synchronized(Settings.this.lock) {
            Iterator i$ = settings.iterator();

            while(i$.hasNext()) {
               EventSetting s = (EventSetting)i$.next();
               int id = s.getId();
               if (this.exists(id)) {
                  result.put(id, s);
               }
            }

            this.settings.putAll(result);
            Settings.this.update(result);
         }
      }

      public void putSetting(EventSetting s) {
         this.putSettings(Collections.singletonList(s));
      }

      public Collection getEventDefaults() {
         ArrayList l = new ArrayList();
         Iterator i$ = this.defaultSets.iterator();

         while(i$.hasNext()) {
            EventDefaultSet d = (EventDefaultSet)i$.next();
            l.addAll(d.getAll());
         }

         return l;
      }

      public List getEventDefaultSets() {
         return this.defaultSets;
      }

      public void addEventDefaultSet(EventDefaultSet d) {
         this.defaultSets.add(d);
         this.updateDefaults();
      }

      public void replaceEventDefaultSets(Collection l) {
         this.defaultSets.clear();
         this.defaultSets.addAll(l);
         this.updateDefaults();
      }

      private void mergeDefaults(Collection input, Map dest) {
         if (!this.defaultSets.isEmpty()) {
            Iterator i$ = input.iterator();

            while(i$.hasNext()) {
               EventInfo e = (EventInfo)i$.next();
               EventSetting s = this.getDefault(e);
               if (e != null) {
                  dest.put(e.getId(), s);
               }
            }

         }
      }

      private EventSetting getDefault(EventInfo e) {
         EventSetting s = null;
         Iterator i$ = this.defaultSets.iterator();

         while(i$.hasNext()) {
            EventDefaultSet f = (EventDefaultSet)i$.next();
            EventSetting s2 = f.get(e.getURI());
            if (s2 != null) {
               s = new EventSetting(e, s2);
            }
         }

         return s;
      }
   }
}
