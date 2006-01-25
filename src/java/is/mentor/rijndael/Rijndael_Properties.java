/**
 * 
 */
package is.mentor.rijndael;
/*jadclipse*/// Decompiled by Jad v1.5.8c. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/kpdus/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Rijndael_Properties.java


import java.io.*;
import java.util.*;

public class Rijndael_Properties
{

 public static String getProperty(String s)
 {
     return properties.getProperty(s);
 }

 public static String getProperty(String s, String s1)
 {
     return properties.getProperty(s, s1);
 }

 public static void list(PrintStream printstream)
 {
     list(new PrintWriter(printstream, true));
 }

 public static void list(PrintWriter printwriter)
 {
     printwriter.println("#");
     printwriter.println("# ----- Begin Rijndael properties -----");
     printwriter.println("#");
     String s;
     String s1;
     for(Enumeration enumeration = properties.propertyNames(); enumeration.hasMoreElements(); printwriter.println(s + " = " + s1))
     {
         s = (String)enumeration.nextElement();
         s1 = properties.getProperty(s);
     }

     printwriter.println("#");
     printwriter.println("# ----- End Rijndael properties -----");
 }

 public static Enumeration propertyNames()
 {
     return properties.propertyNames();
 }

 static boolean isTraceable(String s)
 {
     String s1 = "Trace." + s;
     s1 = properties.getProperty(s1);
     if(s1 == null)
         return false;
     else
         return (new Boolean(s1)).booleanValue();
 }

 static int getLevel(String s)
 {
     String s1 = "Debug.Level." + s;
     s1 = properties.getProperty(s1);
     if(s1 == null)
     {
         s1 = properties.getProperty("Debug.Level.*");
         if(s1 == null)
             return 0;
     }
     try
     {
         return Integer.parseInt(s1);
     }
     catch(NumberFormatException _ex)
     {
         return 0;
     }
 }

 static PrintWriter getOutput()
 {
     String s = properties.getProperty("Output");
     PrintWriter printwriter;
     if(s != null && s.equals("out"))
         printwriter = new PrintWriter(System.out, true);
     else
         printwriter = new PrintWriter(System.err, true);
     return printwriter;
 }

 public Rijndael_Properties()
 {
 }

 static final boolean GLOBAL_DEBUG = false;
 static final String ALGORITHM = "Rijndael";
 static final double VERSION = 0.10000000000000001D;
 static final String FULL_NAME = "Rijndael ver. " + 0.10000000000000001D;
 static final String NAME = "Rijndael_Properties";
 static final Properties properties;
 private static final String DEFAULT_PROPERTIES[][] = {
     {
         "Trace.Rijndael_Algorithm", "true"
     }, {
         "Debug.Level.*", "1"
     }, {
         "Debug.Level.Rijndael_Algorithm", "9"
     }
 };

 static 
 {
     properties = new Properties();
     InputStream inputstream = (Rijndael.Rijndael_Properties.class).getResourceAsStream("Rijndael.properties");
     boolean flag = inputstream != null;
     if(flag)
         try
         {
             properties.load(inputstream);
             inputstream.close();
         }
         catch(Exception _ex)
         {
             flag = false;
         }
     if(!flag)
     {
         int i = DEFAULT_PROPERTIES.length;
         for(int j = 0; j < i; j++)
             properties.put(DEFAULT_PROPERTIES[j][0], DEFAULT_PROPERTIES[j][1]);

     }
 }
}