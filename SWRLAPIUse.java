import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.core.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import java.lang.reflect.*;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.util.Collections;
import java.util.Set;
import java.io.File;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.core.*;
import org.swrlapi.literal.*;
import org.swrlapi.sqwrl.values.*;

import java.util.Random;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class SWRLAPIExample
{
  public static void main(String[] args)
  { String resultados = "---------RESULTADOS---------";
    if (args.length > 1)
      Usage();
  for (int j=10; j<15; j= j+10)
  {System.out.println("Empezando---" + j);
  String temporal = "tmp_" + j + ".owl";
  String owlFileName = args[0];
	  //copy to a tmp ontology for test purposes
	  Path source = Paths.get(owlFileName);
		Path destination = Paths.get(temporal);
 
		try {
			Files.copy(source, destination,  StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
  
  owlFileName = temporal;
   
   File owlFile = (new File(owlFileName)) ;
      Optional.<@NonNull File>empty();
	  
	  
    
    try {
      // Create an OWL ontology using the OWLAPI
      OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
      OWLOntology ontology =   ontologyManager.loadOntologyFromOntologyDocument(owlFile) ;
      OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();
//incluir nueva Observation
String ns = "http://www.semanticweb.org/evelio/ontologies/2018/5/Alarms";
String sosa = "http://www.w3.org/ns/sosa/";
String sf = "http://www.opengis.net/ont/sf";
String geosparql = "http://www.opengis.net/ont/geosparql";
 IRI IOR = IRI.create (ns + "#Observation2");
 IRI IRISOSA = IRI.create (sosa + "Observation");
 
 Random rand = new Random(System.currentTimeMillis());
 for(int i=0; i<j; i++)
 {OWLNamedIndividual obs1 = df.getOWLNamedIndividual(IRI.create(ns + "#NewObservation" + i));
	  OWLClass observationClass = df.getOWLClass(IRISOSA);
	  OWLDeclarationAxiom da1 = df.getOWLDeclarationAxiom(obs1);
	  OWLClassAssertionAxiom caa = df.getOWLClassAssertionAxiom(observationClass, obs1);
	  //paso1
	  OWLObjectProperty observedProperty = df.getOWLObjectProperty(IRI.create(sosa + "observedProperty"));
	  OWLNamedIndividual waterLevel = df.getOWLNamedIndividual(IRI.create(ns + "#WaterLevelInZaragoza"));
	   OWLObjectPropertyAssertionAxiom opa3 = df.getOWLObjectPropertyAssertionAxiom(observedProperty, obs1, waterLevel);
	  //paso2
	  OWLNamedIndividual result1 = df.getOWLNamedIndividual(IRI.create(ns + "#NewResult" + i));
	   OWLDeclarationAxiom da2 = df.getOWLDeclarationAxiom(result1);
	 OWLClass resultClass = df.getOWLClass(IRI.create(sosa + "Result"));  
	 OWLClassAssertionAxiom caa2 = df.getOWLClassAssertionAxiom(resultClass, result1);
	  //paSO 3
	  OWLObjectProperty hasResult = df.getOWLObjectProperty(IRI.create(sosa + "hasResult"));
	  OWLObjectPropertyAssertionAxiom opa4 = df.getOWLObjectPropertyAssertionAxiom(hasResult, obs1, result1);
//paso 4
	  
	
	  double numericValue = 5*Math.random();
	   OWLDataProperty hasNumericValue = df.getOWLDataProperty(IRI.create(ns + "#hasNumericValue"));
	  OWLDataPropertyAssertionAxiom dpa1 = df.getOWLDataPropertyAssertionAxiom(hasNumericValue, result1, numericValue);
	  //paso 5
	  
	  int hora =    rand.nextInt(24);
	  int min =  rand.nextInt(60);
	  int sec = rand.nextInt(60);
	  String horaS ="";
	  String minS ="";
	  String secS ="";
	  
	  if (hora<10)
	  {horaS = "0" + hora;}
	  else {horaS=""+hora;}
	  if (min<10)
	  {minS = "0" + min;}
	  else {minS=""+min;}
	  if (sec<10)
	  {secS = "0" + sec;}
       else {secS=""+sec;}
	  
	  
	  
	  String time = "2018-06-09T" + horaS + ":" + minS	  + ":"+ secS + "Z";
	   OWLDataProperty resultTime = df.getOWLDataProperty(IRI.create(sosa + "resultTime"));
	  OWLDataPropertyAssertionAxiom dpa2 = df.getOWLDataPropertyAssertionAxiom(resultTime, obs1, time);
	  //paso 6
	  
	  OWLNamedIndividual location1 = df.getOWLNamedIndividual(IRI.create(ns + "#NewLocation" + i));
	  OWLClass locationClass = df.getOWLClass(IRI.create(sf + "#Point"));
	  OWLDeclarationAxiom da4 = df.getOWLDeclarationAxiom(location1);
	  OWLClassAssertionAxiom caa3 = df.getOWLClassAssertionAxiom(locationClass, location1);
	  //paso 7
	  
	    OWLObjectProperty hasLocation = df.getOWLObjectProperty(IRI.create(ns + "#hasLocation"));
	  OWLObjectPropertyAssertionAxiom opa1 = df.getOWLObjectPropertyAssertionAxiom(hasLocation, obs1, location1);
      //paso 8	

	
	  OWLDataProperty hasInfluence = df.getOWLDataProperty(IRI.create(ns + "#hasTemporalInfluenceInHours"));
	  OWLDataPropertyAssertionAxiom opa2 = df.getOWLDataPropertyAssertionAxiom(hasInfluence, obs1, 500);
	  //paso 9
	  
	  //POINT(-0.8810755751947226,41.65856367042024)
	  double lat = 41.65856367042024 + 0.2 * (-1 + 2* Math.random());
	  double lon = -0.8810755751947226 + 0.2 * (-1 + 2* Math.random());
	  String point = "POINT(" + lon + " " + lat + ")";
	  
	  OWLDataProperty asWKT = df.getOWLDataProperty(IRI.create(geosparql + "#asWKT"));
	  OWLDataPropertyAssertionAxiom opa5 = df.getOWLDataPropertyAssertionAxiom(asWKT, location1, point);
	  
	  
	long antes2 = System.currentTimeMillis();
 ontologyManager.addAxiom(ontology, da1);
 long despues2 = System.currentTimeMillis();  
	long dif2 = despues2-antes2;

 System.out.println("Tiempo de axioma:" + dif2);
  
	   ontologyManager.addAxiom(ontology, da2);
	   ontologyManager.addAxiom(ontology, caa);
	    ontologyManager.addAxiom(ontology, caa2);
       ontologyManager.addAxiom(ontology, caa2);
            
	  ontologyManager.addAxiom(ontology, opa1);
	   ontologyManager.addAxiom(ontology, opa2); 
	   ontologyManager.addAxiom(ontology, opa3); 
	   ontologyManager.addAxiom(ontology, opa4);
	      ontologyManager.addAxiom(ontology, opa5);
	   ontologyManager.addAxiom(ontology, dpa1);
	     ontologyManager.addAxiom(ontology, dpa2);
		for(int k=0; k<i; k++) 
		{  OWLNamedIndividual obsx = df.getOWLNamedIndividual(IRI.create(ns + "#NewObservation" + k));
			OWLDifferentIndividualsAxiom dif1 =  df.getOWLDifferentIndividualsAxiom(obs1,obsx);
			ontologyManager.addAxiom(ontology, dif1);
		}
	}
	
	OWLDocumentFormat format = ontologyManager.getOntologyFormat(ontology);
       PrefixDocumentFormat prefix = format.asPrefixOWLOntologyFormat();
        prefix.setPrefix("_", "http://www.w3.org/2003/11/strings");
 
 // Create a SWRL rule engine using the SWRLAPI  
 SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
 
 // Run the rule engine
long antes = System.currentTimeMillis();
 ruleEngine.infer();
 long despues = System.currentTimeMillis();
 long dif = despues-antes;
 resultados = resultados + "\n" + j + " " + dif;
 System.out.println(resultados);
		ontologyManager.saveOntology(ontology);
 SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
 SQWRLResult result4 = queryEngine
        .runSQWRLQuery("q1", "   sosa:Observation(?x) ^ sosa:Result(?y) ^ sosa:hasResult(?x,?y) alarms:hasNumericValue(?y, ?z) ^ sosa:observedProperty(?x, ?a) ^ alarms:hasAlarmRange(?a, ?b) ^ alarms:hasUpperLimit(?b, ?c) ^ alarms:hasLowerLimit(?b, ?d) ^ alarms:triggersAlarm(?b, ?e) ^ swrlb:lessThan(?z, ?c) ^ swrlb:greaterThanOrEqual(?z, ?d) -> sqwrl:select(?x, ?z, ?d, ?c)");
	while (result4.next()) {
  SQWRLNamedIndividualResultValue nameValue = result4.getNamedIndividual("x");
  SQWRLLiteralResultValue lower = result4.getLiteral("d");
  SQWRLLiteralResultValue upper = result4.getLiteral("c");
  SQWRLLiteralResultValue valor = result4.getLiteral("z");
 System.out.println("-----------------------------------------");
  System.out.println("sosa:Observation x " + nameValue.getShortName());
   System.out.println("upper " + upper.getDouble());
   System.out.println("lower " + lower.getDouble());
   System.out.println("valor " + valor.getDouble());
   }
 
 
    } catch (OWLOntologyCreationException e) {
      System.err.println("Error creating OWL ontology: " + e.getMessage());
      System.exit(-1);
    } catch (RuntimeException e) {
      System.err.println("Error starting application: " + e.getMessage());
      System.exit(-1);
    } catch (OWLOntologyStorageException e) {
		System.err.println("Error almacenando la ontologia: " + e.getMessage());
      System.exit(-1);
	} catch (SWRLParseException e) {
      System.err.println("Error parsing SQWRL query: " + e.getMessage());
      System.exit(-1);
    } catch (SQWRLException e) {
      System.err.println("Error running SQWRL query: " + e.getMessage());
      System.exit(-1);
    } 
  }
  }

  private static void Usage()
  {
    System.err.println("Usage: " + SWRLAPIExample.class.getName() + " [ <owlFileName> ]");
    System.exit(1);
  }
}
