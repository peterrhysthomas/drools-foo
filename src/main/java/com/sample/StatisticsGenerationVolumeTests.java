package com.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.junit.Test;
import org.springframework.util.StopWatch;

public class StatisticsGenerationVolumeTests {
	
	private static final int VALUES_PER_KEY = 10;
	private static final int KEY_COUNT = 10000;

	@Test
	public void generateLargeStatisticsSet(){
		System.out.println("Generating " + KEY_COUNT * VALUES_PER_KEY + " values");
		StatefulKnowledgeSession ksession = generateStatistics(KEY_COUNT, VALUES_PER_KEY);
		
		StopWatch fireAllRules = new StopWatch();
		fireAllRules.start();
		ksession.fireAllRules();
		fireAllRules.stop();
		System.out.println("Fire all rules took " + fireAllRules.getTotalTimeSeconds());
		
		StopWatch query = new StopWatch();
		query.start();
		QueryResults results = ksession.getQueryResults("trade statistics");
		query.stop();
		System.out.println("Query took " + query.getTotalTimeSeconds());
		
		assertEquals(KEY_COUNT, results.size());
	}

	private StatefulKnowledgeSession generateStatistics(int keys, int values) {
    	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    	kbuilder.add( ResourceFactory.newClassPathResource( "trades.drl"), ResourceType.DRL );
    	if ( kbuilder.hasErrors() ) {
    	    System.err.println( kbuilder.getErrors().toString() );
    	}
    	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    	kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
    	StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

       	ArrayList<Trade> tradeArray = new ArrayList<Trade>();
		StopWatch dataGenerationStopwatch = new StopWatch();
		dataGenerationStopwatch.start();
		for (int i=0;i<keys;i++){
			String stock = "STOCK_" + i;
			String countryOfExecution = "COE_" + i;
			String counterparty = "CPY_" + i;
			for (int n=0;n<values;n++){
				String reference = "REF_" + i + "_" + n;
				Trade trade = new Trade(reference, stock, counterparty, countryOfExecution, n, n);
				tradeArray.add(trade);
			}
		}

		dataGenerationStopwatch.stop();
		System.out.println("Data generation took " + dataGenerationStopwatch.getTotalTimeSeconds());
		
		StopWatch processorStopwatch = new StopWatch();
		processorStopwatch.start();
		for (Trade trade : tradeArray){
			ksession.insert(trade);
		}
		processorStopwatch.stop();
		System.out.println("Insert took " + processorStopwatch.getTotalTimeSeconds());
		
		return ksession;
	}
}
