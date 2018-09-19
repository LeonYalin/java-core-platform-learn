package com.leony.home;

import jdk.nashorn.api.tree.Tree;

import java.util.*;

public class WorkingWithCollections {
    public void createSimpleArrayList() {
        ArrayList list = new ArrayList();
        list.add("one");
        list.add("two");
        list.add(123456);
        list.add(new Object());

        for (Object item : list) {
            System.out.println(item.toString());
        }
        System.out.printf("total %d items in list\n", list.size());
    }

    public void useCommonCollectionsMethods() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        System.out.printf("the list of total %d elements is: %s\n", list.size(), list.toString());

        List<String> anotherList = new LinkedList<>();
        anotherList.add("three");
        anotherList.add("four");
        System.out.printf("another list of total %d elements is: %s\n", anotherList.size(), anotherList.toString());

        list.addAll(anotherList);
        System.out.printf("the list of total %d elements after addAll is: %s\n", list.size(), list.toString());

        list.remove(0);
        System.out.printf("Is the list empty? %s\n", list.isEmpty());

        list.clear();
        System.out.printf("The list of total %d elements after clear is: %s\n", list.size(), list);

        System.out.println("Comparing instances of custom classes");
        WorkingWithCollectionInner inner = new WorkingWithCollectionInner("one", "two");
        WorkingWithCollectionInner inner2 = new WorkingWithCollectionInner("three", "four");
        List<WorkingWithCollectionInner> innerList = new ArrayList<>();
        innerList.add(inner);
        innerList.add(inner2);
        System.out.printf("List contains its first member: %b\n", innerList.contains(inner));
        System.out.printf(
                "List contains its first and second members as another collection: %b\n",
                innerList.containsAll(Arrays.asList(inner, inner2))
        );
        System.out.printf(
                "Remove list variables that are not present in another list: %s\n",
                innerList.retainAll(Arrays.asList(new WorkingWithCollectionInner("five", "six")))
        );
    }

    public void useJava8CollectionsMethods() {
        List<WorkingWithCollectionInner> list = new ArrayList<>();
        list.add(new WorkingWithCollectionInner("one", "two"));
        list.add(new WorkingWithCollectionInner("three", "four"));
        list.add(new WorkingWithCollectionInner("five", "six"));

        System.out.println("Print collection elements using forEach()");
        list.forEach(i -> System.out.println(i.toString()));

        System.out.println("Remove collection element 'five' using removeIf()");
        list.removeIf(i -> i.word.equals("five"));
    }

    public class WorkingWithCollectionInner implements Comparable<WorkingWithCollectionInner> {
        private String word;
        private String anotherWord;

        WorkingWithCollectionInner(String word, String anotherWord) {
            this.word = word;
            this.anotherWord = anotherWord;
        }

        @Override
        public boolean equals(Object o) {
            WorkingWithCollectionInner obj = (WorkingWithCollectionInner) o;
            return this.word.equals(obj.word) && this.anotherWord.equals(obj.anotherWord);
        }

        @Override
        public String toString() {
            return String.format("words:[%s %s]", word, anotherWord);
        }

        @Override
        public int compareTo(WorkingWithCollectionInner o) {
            return this.word.compareToIgnoreCase(o.word);
        }
    }

    public void convertBetweenCollectionAndArray() {
        List<WorkingWithCollectionInner> list = new ArrayList<>();
        list.add(new WorkingWithCollectionInner("one", "two"));
        list.add(new WorkingWithCollectionInner("three", "four"));
        list.add(new WorkingWithCollectionInner("five", "six"));

        System.out.printf("Convert collection %s to array\n", list);
        System.out.println("Convert collection to array of objects: " + list.toArray());
        System.out.println("Convert collection to array of same class: " + list.toArray(new WorkingWithCollectionInner[0]));
        System.out.println("Convert array to collection: " + Arrays.asList(list.toArray(new WorkingWithCollectionInner[0])));
    }

    public void playWithCommonCollections() {
        System.out.println("Playing with ArrayList:");
        ArrayList<String> list = new ArrayList();
        list.add("one");
        list.add("two");
        System.out.printf("Created an ArrayList %s of size %d\n", list, list.size());
        System.out.println("List contains 'one'? " + list.contains("one"));
        list.remove(0);
        System.out.println("After removing the first member, the list is :" + list);
        System.out.println("List is empty? " + list.isEmpty());
        System.out.println("Iterate over the ArrayList using Java 8 forEach method:");
        list.forEach(i -> System.out.println(i));

        System.out.println("Playing with LinkedList:");
        LinkedList<String> linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList("one", "two", "three")); // add to the end
        System.out.printf("Created an LinkedList %s of size %d\n", linkedList, linkedList.size());
        linkedList.addFirst("before one");
        linkedList.addLast("after three");
        linkedList.add(3, "between two and three");
        System.out.println("LinkedList after inserting first, last and intermediate elements: " + linkedList);
        System.out.printf("Get first, last and 3rd element of the LinkedList: '%s', '%s', '%s'\n", linkedList.getFirst(), linkedList.getLast(), linkedList.get(3));
        linkedList.removeFirst();
        linkedList.removeLast();
        linkedList.remove(3);
        System.out.println("LinkedList after removing first, last and 3rd elements: " + linkedList);

        System.out.println("Playing with HashSet");
        HashSet<String> set = new HashSet();
        set.addAll(Set.of("b", "a", "d", "c"));
        System.out.printf("Created a HashSet %s of size %d\n", set, set.size());
        set.addAll(Set.of("a", "d"));
        System.out.println("HashSet after adding duplicate 'a' and 'd' values: " + set);

        System.out.println("Playing with TreeSet");
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(Set.of("b1", "a2", "d3", "c4"));
        System.out.printf("Created a TreeSet %s of size %d\n", treeSet, treeSet.size());
        TreeSet<String> treeSet2 = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(1) - o2.charAt(1);
            }
        });
        treeSet2.addAll(Set.of("b1", "a2", "d3", "c4"));
        System.out.printf("Created another TreeSet %d of size with custom order by digits: %s\n", treeSet2.size(), treeSet2);
    }

    public void playWithMapCollections() {
        System.out.println("Playing with HashMap");
        Map<String, String> map = new HashMap<>();
        map.putAll(Map.of("a", "aaa", "b", "bbb", "c", "ccc"));
        map.putIfAbsent("a", "ddd"); // will not be added
        map.putIfAbsent("a", null); // will not be added
        System.out.printf("Created a HashMap %s of size %d\n", map, map.size());
        System.out.println("Get map entry for 'a' key: " + map.get("a"));
        System.out.println("Get map default entry for non-existing 'z' key: " + map.getOrDefault("z", "default value"));
        System.out.println("Get map keys: " + map.keySet());
        System.out.println("Get map values: " + map.values());
        System.out.println("Get map entries: " + map.entrySet());
        System.out.println("Use map forEach method:");
        map.forEach((k, v) -> System.out.printf("key: %s, value: %s\n", k, v));
        map.replaceAll((k, v) -> String.format("%s:updated", v));
        System.out.println("Get map after using replaceAll: " + map);

        System.out.println("Playing with TreeMap");
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.putAll(Map.of("b", "b1", "a", "a2", "c", "c4", "d", "d3"));
        System.out.printf("Created a TreeMap %s of size %d\n", treeMap, treeMap.size());
        TreeMap<String, String> treeMap2 = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(1) - o2.charAt(1);
            }
        });
        treeMap2.putAll(Map.of("b1", "b1", "a2", "a2", "c4", "c4", "d3", "d3"));
        System.out.printf("Created another TreeMap %s of size %d with custom order by key digits\n", treeMap2, treeMap2.size());
        System.out.println("Use the firstKey() method: " + treeMap2.firstKey());
        System.out.println("Use the lastKey() method: " + treeMap2.lastKey());
        System.out.println("Use the firstEntry() method: " + treeMap2.firstEntry());
        System.out.println("Use the lastEntry() method: " + treeMap2.lastEntry());
        System.out.println("Use the headMap('a2') method: " + treeMap2.headMap("a2"));
        System.out.println("Use the tailMap('a2') method: " + treeMap2.tailMap("a2"));
        System.out.println("Use the tailMap('a2') method: " + treeMap2.tailMap("a2"));
        System.out.println("Use the subMap('a2', 'c4') method: " + treeMap2.subMap("a2", "c4"));
    }
}
