package com.oracle.jrockit.jfr.client;

import com.oracle.jrockit.jfr.management.FlightRecorderMBean;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import oracle.jrockit.jfr.JFRImpl;
import oracle.jrockit.jfr.events.EventDescriptor;
import oracle.jrockit.jfr.openmbean.EventDefaultType;
import oracle.jrockit.jfr.openmbean.EventDescriptorType;
import oracle.jrockit.jfr.openmbean.EventSettingType;
import oracle.jrockit.jfr.settings.EventDefault;
import oracle.jrockit.jfr.settings.EventDefaultSet;
import oracle.jrockit.jfr.settings.EventSetting;

public class EventSettingsBuilder {
   private final ArrayList eventDefaultSets = new ArrayList();
   private final ArrayList settings = new ArrayList();
   private final EventDescriptorType eventDescriptorType;
   private final EventSettingType eventSettingType;
   private final EventDefaultType eventDefaultType;

   public EventSettingsBuilder() {
      try {
         this.eventDescriptorType = new EventDescriptorType();
         this.eventSettingType = new EventSettingType((JFRImpl)null);
         this.eventDefaultType = new EventDefaultType();
      } catch (OpenDataException var2) {
         throw new InternalError();
      }
   }

   public void addSettings(File settingsFile) throws IOException, URISyntaxException, ParseException {
      this.eventDefaultSets.add(new EventDefaultSet(settingsFile));
   }

   public void addSettings(String settingsFile) throws IOException, URISyntaxException, ParseException {
      this.eventDefaultSets.add(new EventDefaultSet(settingsFile));
   }

   public void addSettings(Reader r) throws IOException, URISyntaxException, ParseException {
      this.eventDefaultSets.add(new EventDefaultSet(r));
   }

   public void addSettings(EventDefaultSet set) {
      this.eventDefaultSets.add(set);
   }

   public void createSetting(String uriExpr, boolean enable, boolean stacktrace, long threshold, long period) throws URISyntaxException {
      this.eventDefaultSets.add(new EventDefaultSet(new EventDefault[]{new EventDefault(new URI(uriExpr), new EventSetting(0, enable, stacktrace, threshold, period))}));
   }

   public void createSetting(int id, boolean enable, boolean stacktrace, long threshold, long period) throws URISyntaxException {
      this.settings.add(new EventSetting(id, enable, stacktrace, threshold, period));
   }

   public List createSettings(FlightRecorderMBean jfr) throws OpenDataException {
      ArrayList l = new ArrayList();
      if (!this.eventDefaultSets.isEmpty()) {
         Iterator i$ = jfr.getProducers().iterator();

         while(i$.hasNext()) {
            CompositeData d = (CompositeData)i$.next();
            CompositeData[] events = (CompositeData[])((CompositeData[])d.get("events"));
            Iterator i$ = this.eventDescriptorType.toJavaTypeData(Arrays.asList(events)).iterator();

            while(i$.hasNext()) {
               EventDescriptor e = (EventDescriptor)i$.next();
               EventSetting s = null;
               Iterator i$ = this.eventDefaultSets.iterator();

               while(i$.hasNext()) {
                  EventDefaultSet f = (EventDefaultSet)i$.next();
                  EventSetting o = f.get(e.getURI());
                  if (o != null) {
                     s = s != null ? new EventSetting(s, o) : new EventSetting(e, o);
                  }
               }

               if (s != null) {
                  l.add(this.eventSettingType.toCompositeTypeData(s));
               }
            }
         }
      }

      l.addAll(this.eventSettingType.toCompositeData(this.settings));
      return l;
   }

   public List createDefaultSettings() throws OpenDataException {
      ArrayList l = new ArrayList();
      Iterator i$ = this.eventDefaultSets.iterator();

      while(i$.hasNext()) {
         EventDefaultSet d = (EventDefaultSet)i$.next();
         l.addAll(d.getAll());
      }

      return this.eventDefaultType.toCompositeData(l);
   }
}
