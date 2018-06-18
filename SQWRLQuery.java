import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.Set;
import java.io.File;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.*;
import org.swrlapi.literal.*;
import org.swrlapi.sqwrl.values.*;
import java.io.*;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.builtins.strings.*;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;




public class SQWRLQuery
{
  public static void main(String[] args)
  { 
    try {
		
     
      OWLOntologyManager m = OWLManager.createOWLOntologyManager();

		
   File owlFile = new File("C:\\TRABAJO\\limpia\\jars\\tmp_10.owl");
    	OWLOntology ontology=m.loadOntologyFromOntologyDocument(owlFile);
    	
	
      SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
		String ficheroKML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		ficheroKML=ficheroKML + "<kml xmlns=\"http://earth.google.com/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/kml/2.2 https://developers.google.com/kml/schema/kml22gx.xsd\">";
ficheroKML=ficheroKML + "<Document>";
ficheroKML=ficheroKML + "<name><![CDATA[Alarms]]></name>";

		
		
	 SQWRLResult result4 = queryEngine
        .runSQWRLQuery("q1", "   sosa:Observation(?x)  alarms:hasTemporalInfluenceInHours(?x, ?z)  sosa:resultTime(?x, ?t) geo:hasInfluenceNow(?bool, ?t, ?z)  swrlb:equal(?bool, true) alarms:hasLocation(?x, ?l1)  geosparql:asWKT(?l1, ?wkt1)  alarms:triggersAlarm(?x, ?alarm1) alarms:hasPriority(?alarm1, ?prior1) alarms:NotCombinedAlarm(?alarm1) swrlb:greaterThan(?prior1, 0) -> sqwrl:selectDistinct(?x, ?prior1, ?wkt1)");

		

		while (result4.next()) {
  SQWRLNamedIndividualResultValue nameValue = result4.getNamedIndividual("x");
  SQWRLLiteralResultValue wkt1 = result4.getLiteral("wkt1");
  SQWRLLiteralResultValue prioridad = result4.getLiteral("prior1");
  System.out.println("x " + nameValue.getShortName());
   System.out.println("wkt1 " + wkt1.getString());
   System.out.println("prioridad " + prioridad.getInteger());
ficheroKML=ficheroKML + "\n" + "<Placemark>";
ficheroKML=ficheroKML + "\n" + "<name><![CDATA[" + nameValue.getShortName() + "</br><h4>Priority: " + prioridad.getInteger() + "</h4>]]></name>";
//ficheroKML=ficheroKML + "\n" + "<Style><IconStyle><scale>1</scale><Icon><href>03.png</href></Icon><hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/></IconStyle><LabelStyle><color>fffff</color><colorMode>normal</colorMode><scale>1</scale></LabelStyle></Style>";
String coordinates = wkt1.getString();

coordinates = coordinates.substring(coordinates.lastIndexOf("(")+1,coordinates.lastIndexOf(")"));
coordinates = coordinates.replace(" ",",") + ",0";
ficheroKML=ficheroKML + "\n" + "<Point><coordinates>" + coordinates +"</coordinates></Point>";
ficheroKML=ficheroKML + "\n" + "</Placemark>";   
   //Formar el KLM
   
   }
queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
     result4 = queryEngine
        .runSQWRLQuery("q1", "   sosa:Observation(?x)  alarms:hasTemporalInfluenceInHours(?x, ?z)  sosa:resultTime(?x, ?t) geo:hasInfluenceNow(?bool, ?t, ?z)  swrlb:equal(?bool, true) alarms:hasLocation(?x, ?l1)  geosparql:asWKT(?l1, ?wkt1)  alarms:triggersAlarm(?x, ?alarm1) alarms:CombinedAlarm(?alarm1) -> sqwrl:selectDistinct(?x, ?wkt1)");

		

		while (result4.next()) {
  SQWRLNamedIndividualResultValue nameValue = result4.getNamedIndividual("x");
  SQWRLLiteralResultValue wkt1 = result4.getLiteral("wkt1");
 System.out.println("-----------------------------------------");
  System.out.println("x " + nameValue.getShortName());
   System.out.println("wkt1 " + wkt1.getString());
   ficheroKML=ficheroKML + "\n" + "<Placemark>";
ficheroKML=ficheroKML + "\n" + "<name><![CDATA[" + nameValue.getShortName() + "]]></name>";
String coordinates = wkt1.getString();

coordinates = coordinates.substring(coordinates.lastIndexOf("(")+1,coordinates.lastIndexOf(")"));
coordinates = coordinates.replace(" ",",") + ",0";
ficheroKML=ficheroKML + "\n" + "<Point><coordinates>" + coordinates +"</coordinates></Point>";
ficheroKML=ficheroKML + "\n" + "</Placemark>";  
   }
   ficheroKML=ficheroKML + "\n" + "</Document>";
 ficheroKML=ficheroKML + "\n" + "</kml>";
 System.out.println(ficheroKML);
   writeFile(ficheroKML);
   
    } catch (OWLOntologyCreationException e) {
      System.err.println("Error creating OWL ontology: " + e.getMessage());
      System.exit(-1);
    } catch (SWRLParseException e) {
      System.err.println("Error parsing SQWRL query: " + e.getMessage());
      System.exit(-1);
    } catch (SQWRLException e) {
      System.err.println("Error running SQWRL query: " + e.getMessage());
      System.exit(-1);
    } catch (RuntimeException e) {
      System.err.println("Error starting application: " + e.getMessage());
      System.exit(-1);
    } 
	
	
  }
  
  
 public static void writeFile(String data)
    {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("alarms.kml");
            pw = new PrintWriter(fichero);
                pw.println(data);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    } 
  
  
}