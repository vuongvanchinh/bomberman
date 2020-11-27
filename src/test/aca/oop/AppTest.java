package aca.oop;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    static int count(int k, Map<Integer, Integer> hashTable) {
        int result = 0;
        Set<Integer> keys = hashTable.keySet();
        for (int i : keys) {
            if (hashTable.get(i + k) != null) result++;
        }
        return result;
    }
    public static void main(String[] args) {
    //     // Map<Integer, Integer> hashTable = new Hashtable<Integer,Integer>();
        
    //     // int[] arr = {1, 2, 3, 4};

    //     // for (int i : arr) {
    //     //     hashTable.put(i, i);
    //     // }

    //     // System.out.println(count(1, hashTable));
    //     //System.out.println(hashTable.toString());

    //     int[] arr = {0, 2, 1 , 5, 1};
    //     int[] brr = {0, 2, 1 , 5, 1, 6};
    //     Map<Integer, Integer> hashA = new Hashtable<Integer,Integer>();
    //     Map<Integer, Integer> hashB = new Hashtable<Integer,Integer>();
    //     Map<Integer, Integer> freA = new Hashtable<Integer,Integer>();
    //     Map<Integer, Integer> freB = new Hashtable<Integer,Integer>();
        
    //     for (int i : arr) {
    //         if (hashA.get(i) != null) {
    //             hashA.put(i, i);
    //             freA.put(i, 1);
    //         } else {
    //             freA.put(i,freA.get(i) + 1);
    //         }
    //     }

    //     for (int i : brr) {
    //         if (hashB.get(i) != null) {
    //             hashB.put(i, i);
    //             freB.put(i, 1);
    //         } else {
    //             freB.put(i,freB.get(i) + 1);
    //         }
    //     }

        int a = 10;
        a-= 10 + 5;
        System.out.println(a);
    }

}
