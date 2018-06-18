package org.swrlapi.builtins.geo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.builtins.AbstractSWRLBuiltInLibrary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.exceptions.InvalidSWRLBuiltInArgumentException;
import org.swrlapi.exceptions.InvalidSWRLBuiltInNameException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLBuiltInNotImplementedException;
import org.swrlapi.literal.OWLLiteralComparator;
import org.swrlapi.literal.XSDDate;
import org.swrlapi.literal.XSDDateTime;
import org.swrlapi.literal.XSDDuration;
import org.swrlapi.literal.XSDTime;
import org.swrlapi.literal.XSDTimeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Implementations library for the core SWRL built-in methods. These built-ins are defined <a
 * href="http://www.daml.org/2004/04/swrl/builtins.html">here</a>.
 * <p>
 * Built-ins for lists and URIs not yet implemented.
 */
public class SWRLBuiltInLibraryImpl extends AbstractSWRLBuiltInLibrary
{
  private static final String PREFIX = "geo";

  private static final String NAMESPACE = "http://www.w3.org/2003/11/geo#";

  private static final String[] BUILT_IN_NAMES = { "haversine", "hasInfluenceNow" };

  
  public SWRLBuiltInLibraryImpl()
  {
    super(PREFIX, NAMESPACE, new HashSet<>(Arrays.asList(BUILT_IN_NAMES)));
  }

  @Override public void reset()
  {
  }

  
  public static double distance(double lat1, double lat2, double lon1, double lon2) {
    int R = 6371; // Radius of the earth
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c * 1000; // convert to meters
    
    return distance;
}


public double procesar(String s1, String s2)
{String lon1 = s1.substring(s1.lastIndexOf("(")+1,s1.lastIndexOf(" "));
 String lat1 = s1.substring(s1.lastIndexOf(" ")+1,s1.lastIndexOf(")"));
 String lon2 = s2.substring(s2.lastIndexOf("(")+1,s2.lastIndexOf(" "));
 String lat2 = s2.substring(s2.lastIndexOf(" ")+1,s2.lastIndexOf(")"));
 double distancia = distance(Double.parseDouble(lat1),Double.parseDouble(lat2), Double.parseDouble(lon1),Double.parseDouble(lon2));
 return distancia;
} 
 
 
  public boolean haversine(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsAtLeast(3, arguments.size());
    String argument2 = getArgumentAsAString(1, arguments);
    String argument3 = getArgumentAsAString(2, arguments);
    @Nullable Double operationResult = new Double(procesar(argument2, argument3));
    if (operationResult != null)
      return processResultArgument(arguments, 0, operationResult);
    else
      return false;
  }
 
public boolean procesarDuration(String dt, int horas)
{
dt = dt.replace("T", " ");
dt = dt.replace("Z", "");
	boolean dentro = false;
try{	
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = sdf.parse(dt);
long millisMedida = date.getTime();
	Date ahora = new Date();
	long milisAhora = ahora.getTime();
	long plazo = millisMedida + horas*3600*1000;
	if(milisAhora <= plazo)
	{dentro = true;}
} catch (ParseException e) {}

return dentro;
}


  /**
   * @param arguments The built-in arguments
   * @return The result of the built-in
   * @throws SWRLBuiltInException If an error occurs during processing
   */
  public boolean startsWith(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(2, arguments.size());

    String argument1 = getArgumentAsAString(0, arguments);
    String argument2 = getArgumentAsAString(1, arguments);

    return argument1.startsWith(argument2);
  }


public boolean hasInfluenceNow(@NonNull List<@NonNull SWRLBuiltInArgument> arguments) throws SWRLBuiltInException
  {
    checkNumberOfArgumentsEqualTo(3, arguments.size());

    String argument1 = getArgumentAsAString(1, arguments);
    BigInteger ip = getArgumentAsAnInteger(2, arguments);
    int ip2 = ip.intValue();
    boolean operationResult = procesarDuration(argument1,ip2);
    return processResultArgument(arguments, 0, operationResult);
    
  }
 

}
