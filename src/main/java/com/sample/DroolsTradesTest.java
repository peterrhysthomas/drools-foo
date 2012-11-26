package com.sample;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import org.junit.Test;


public class DroolsTradesTest {

	@Test
    public void blah(){
    	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    	kbuilder.add( ResourceFactory.newClassPathResource( "trades.drl"), ResourceType.DRL );
    	if ( kbuilder.hasErrors() ) {
    	    System.err.println( kbuilder.getErrors().toString() );
    	}
    	KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    	kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );
    	StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    	
		Trade trade1 = new Trade("REF1", "IBM", "CPY1", "GB", 100.0, 999.99);
		Trade trade2 = new Trade("REF2", "IBM", "CPY1", "GB", 200.0, 2000.01);
		Trade trade3 = new Trade("REF3", "IBM", "CPY1", "FRA", 200.0, 2000.01);
		Trade trade4 = new Trade("REF4", "APL", "CPY2", "GB", 400.0, 3000.01);
		Trade trade5 = new Trade("REF5", "IBM", "CPY2", "GB", 500.0, 4999.99);
		Trade trade6 = new Trade("REF6", "MCS", "CPY1", "GB", 600.0, 5000.01);
		
		ksession.insert(trade1);
		ksession.insert(trade2);
		ksession.insert(trade3);
		ksession.insert(trade4);
		ksession.insert(trade5);
		ksession.insert(trade6);
		
		ksession.fireAllRules();
		
		QueryResults results = ksession.getQueryResults("trade statistics");
		assertEquals(5, results.size());
		
		Map<TradeKey, TradeStatistic> statistics = new HashMap<TradeKey, TradeStatistic>();
		
		for (QueryResultsRow row : results ) {
			TradeStatistic statistic = (TradeStatistic) row.get("tradeStatistic");
			statistics.put(statistic.getTradeKey(), statistic);
		}
		
		assertEquals(5, statistics.size());
		assertTrue(statistics.containsKey(trade1.getKey()));
		assertTrue(statistics.containsKey(trade2.getKey()));
		assertTrue(statistics.containsKey(trade3.getKey()));
		assertTrue(statistics.containsKey(trade4.getKey()));
		assertTrue(statistics.containsKey(trade5.getKey()));
		assertTrue(statistics.containsKey(trade6.getKey()));
		
		assertEquals(2, statistics.get(trade1.getKey()).getCount());
		assertEquals(2, statistics.get(trade2.getKey()).getCount());
		assertEquals(1, statistics.get(trade3.getKey()).getCount());
		assertEquals(1, statistics.get(trade4.getKey()).getCount());
		assertEquals(1, statistics.get(trade5.getKey()).getCount());
		assertEquals(1, statistics.get(trade6.getKey()).getCount());
    }
}
