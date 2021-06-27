/*
 * Description: Sorting functions
 * License: Apache-2.0
 * Copyright: x-file.xyz
 */
package utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Date: 2020-07-16
 * Place: Zwingenberg, Germany
 * @author brito
 */
public class sort {

    @SuppressWarnings("Convert2Lambda")
    public static LinkedHashMap<String, Long> sortByComparator(
        HashMap<String, Long> unsortMap) {

        List<Map.Entry<String, Long>> list = new LinkedList<>(
            unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Long>> () {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        @SuppressWarnings("Convert2Diamond")
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> entry : list) {
          sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static LinkedHashMap<String, Integer> sortByComparatorInteger(
        HashMap<String, Integer> unsortMap) {

        List<Map.Entry<String, Integer>> list = new LinkedList<>(
            unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>> () {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        @SuppressWarnings("Convert2Diamond")
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
          sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
}
