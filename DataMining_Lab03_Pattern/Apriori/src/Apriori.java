import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    static final int[][] TRANSACTIONS = new int[][] { { 1, 2, 3, 4, 5 }, { 1, 3, 5 }, { 2, 3, 5 }, { 1, 5 }, { 1, 3, 4 }, { 2, 3, 5 }, { 2, 3, 5 },
                    { 3, 4, 5 }, { 4, 5 }, { 2 }, { 2, 3 }, { 2, 3, 4 }, { 3, 4, 5 } };

    public static void main( String[] args ) throws Exception {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        
    	
    	System.out.println("TRANSACTIONS Count: " + TRANSACTIONS.length);
    	
    	final int supportThreshold = 3;
        apriori( TRANSACTIONS, supportThreshold );
    }

    public static List<ItemSet> apriori( int[][] transactions, int supportThreshold ) throws Exception {
        int k;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1( transactions, supportThreshold );
        
        //frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
        Hashtable<ItemSet, Integer> result = frequentItemSets;
        
        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.println( "Finding frequent itemsets of length " + (k + 1) + ":" );
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
            result.putAll(frequentItemSets);
            // TODO: add to list
            System.out.println( " found " + frequentItemSets.size() );
        }
        
        System.out.println("RESULT: " + result);
        
        // TODO: create association rules from the frequent itemsets
       for (int i = 0; i < transactions.length; i++){
    	   System.out.println("Transaction: " + new ItemSet(transactions[i]));
       }
        
        // TODO: return something useful
        return null;
    }

    private static Hashtable<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, int[][] transactions,
                    Hashtable<ItemSet, Integer> lowerLevelItemSets ) throws Exception {
        // TODO: first generate candidate itemsets from the lower level itemsets
    	
    	int k = lowerLevelItemSets.keySet().size();
    	Hashtable<ItemSet, Integer> candidates = new Hashtable<ItemSet, Integer>();
    	for (ItemSet itemset: lowerLevelItemSets.keySet()){
    		for (ItemSet itemset2: lowerLevelItemSets.keySet()){
    			//System.out.println("Compare" + itemset + " and " + itemset2);
    			ItemSet joined = joinSets(itemset,itemset2);
    			if (joined != null){
    				if (!candidates.containsKey(joined)){
    					int support = countSupport(joined.set,transactions);
    		    		if (support >= supportThreshold)
    		    			candidates.put(joined, support);
    				}
    			}
    		}
    	}
    	
    	//Print
    	/*for(ItemSet itemset: candidates.keySet()){
    		System.out.println(itemset + ": " + candidates.get(itemset));
    	}*/
    	
        /*
         * TODO: now check the support for all candidates and add only those
         * that have enough support to the set
         */
    	
    	//Print
    	for(ItemSet itemset: candidates.keySet()){
    		System.out.println(itemset + ": " + candidates.get(itemset));
    	}
    	
        // TODO: return something useful
        return candidates;
    }

    private static ItemSet joinSets( ItemSet first, ItemSet second ) throws Exception {
        if (first.set.length != second.set.length)
        	throw new Exception("Sets must be from the same size.");
        if (!first.equals(second)){
	        int k = first.set.length;
	        int[] set = new int[k+1];
	        boolean skip = false;
	        for (int i = 0; i < k-1; i++){
	        	if (first.set[i] == second.set[i]){
	        		set[i] = first.set[i];
	        	}
	        	else {
	        		skip = true;
	        		break;
	        	}
	        }
	        if (second.set[k-1] < first.set[k-1]){
	    		set[k-1] = second.set[k-1];
	    		set[k] = first.set[k-1];
	        }
	    	else{
	    		set[k-1] = first.set[k-1];
	    		set[k] = second.set[k-1];
	    	}
	        if (skip)
	        	return null;
	        else
	        	return new ItemSet(set);
        
        }
        return null;
    }

    private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int supportThreshold ) {
        // TODO: return something useful
    	Hashtable<ItemSet, Integer> tempFrequentItemSetL1 = new Hashtable<ItemSet, Integer>();
    	Hashtable<ItemSet, Integer> frequentItemSetL1 = new Hashtable<ItemSet, Integer>();
    	
    	for(int[] itemset: transactions){
    		for(int item: itemset){
    			ItemSet newItemSet = new ItemSet(new int[]{item});
    			if (frequentItemSetL1.containsKey(newItemSet)){
    				int count = frequentItemSetL1.get(newItemSet) + 1;
    				frequentItemSetL1.put(newItemSet, count);
    			}
    			else
    			{
    				if (tempFrequentItemSetL1.containsKey(newItemSet)){
        				int count = tempFrequentItemSetL1.get(newItemSet) + 1;
        				if (count >= supportThreshold)
        					frequentItemSetL1.put(newItemSet, count);
        				else
        					tempFrequentItemSetL1.put(newItemSet, count);
    				}
    				else
    				{
    					tempFrequentItemSetL1.put(newItemSet, 1);
    				}
    			}
    		}
    	}
    	
    	//Print
    	for(ItemSet itemset: frequentItemSetL1.keySet()){
    		System.out.println(itemset + ": " + frequentItemSetL1.get(itemset));
    	}
    	
        return frequentItemSetL1;
    }

    private static int countSupport( int[] itemSet, int[][] transactions ) {
        // Assumes that items in ItemSets and transactions are both unique
    	HashSet<Integer> mySet1 = new HashSet<Integer>();
		for (int i: itemSet){
			mySet1.add(i);
    	}
    	
    	int result = 0;
    	for (int[] itemSet2: transactions){
    		HashSet<Integer> mySet2 = new HashSet<Integer>();
    		for (int i: itemSet2){
    			mySet2.add(i);
    		}
    		if (mySet2.containsAll(mySet1))
    			result++;
    	}

        // TODO: return something useful
        return result;
    }

}
