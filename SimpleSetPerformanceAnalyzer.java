import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * a class to test out the performances for our hash table with different kinds of simple sets
 *
 * @author rina.karnauch
 */
public class SimpleSetPerformanceAnalyzer {


    /*
   name of open hash objects to put into checked items list
    */
    private static final String OPEN_HASH_OBJECT = "OpenHashSet";

    /*
   name of close hash objects to put into checked items list
    */
    private static final String CLOSE_HASH_OBJECT = "CloseHashSet";

    /*
   name of hash set objects to put into checked items list
    */
    private static final String HASH_SET_OBJECT = "HashSet";

    /*
   name of tree set objects to put into checked items list
    */
    private static final String TREE_SET_OBJECT = "TreeSet";

    /*
   name of linked list objects to put into checked items list
    */
    private static final String LINKED_LIST_OBJECT = "LinkedList";

    /*
    warm up value to iterate to compare times.
     */
    private static final int WARM_UP_VALUE = 70000;

    /*
    no warm up value to iterate to compare times, for linked list
     */
    private static final int NO_WARM_UP_VALUE = 7000;

    /*
    string "hi" to look for in tests
     */
    private static final String HI_STRING_FOR_TEST = "hi";

    /*
    string value to look for in data2 file data structures
     */
    private static final String TWENTY_THREE_FOR_TEST = "23";

    /*
    string "-13170890158" which is the string with the same hashCode will all other words
    */
    private static final String LONG_NUMBER_FOR_TEST = "-13170890158";

    /*
    string value to divide by to get milliseconds.
     */
    private static final long DIVISION_FOR_NANO = 1000000;

    /*
    name path of first data file
     */
    private static final String DATA_1_FILE = "data1.txt";

    /*
    name path of second data file
     */
    private static final String DATA_2_FILE = "data2.txt";

    /*
    names of collections to test
     */
    private String[] collectionsToTest;

    /*
    array to keep all simple set objects for file checking
     */
    private SimpleSet[] objectsForTestFile;

    /*
    amount of objects to check
     */
    private int amountOfObjects;

    /*
    amount of filled objects
     */
    private int filledObjects;

    /**
     * constructor of class
     */
    public SimpleSetPerformanceAnalyzer() {
        this.collectionsToTest = new String[]{OPEN_HASH_OBJECT, CLOSE_HASH_OBJECT, TREE_SET_OBJECT,
                LINKED_LIST_OBJECT, HASH_SET_OBJECT};
        this.amountOfObjects = this.collectionsToTest.length;
        this.filledObjects = 0;
        this.objectsForTestFile = new SimpleSet[this.amountOfObjects];
    }

    /*
    function to init our structures which are not the ones we built
     */
    private void commonInit() {
        for (int i = 0; i < amountOfObjects; i++) {
            String dataName = this.collectionsToTest[i];
            switch (dataName) {
                case (TREE_SET_OBJECT): {
                    TreeSet<String> treeForTest = new TreeSet<String>();
                    CollectionFacadeSet wrappedTreeSetForTest = new CollectionFacadeSet(treeForTest);
                    this.objectsForTestFile[i] = wrappedTreeSetForTest;
                    this.filledObjects++;
                    break;
                }
                case (LINKED_LIST_OBJECT): {
                    LinkedList<String> linkedListForTest = new LinkedList<String>();
                    CollectionFacadeSet wrappedLinkedListForTest = new CollectionFacadeSet(linkedListForTest);
                    this.objectsForTestFile[i] = wrappedLinkedListForTest;
                    this.filledObjects++;
                    break;
                }
                case (HASH_SET_OBJECT): {
                    HashSet<String> hashSetForTest = new HashSet<String>();
                    CollectionFacadeSet wrappedHashSetForTest = new CollectionFacadeSet(hashSetForTest);
                    this.objectsForTestFile[i] = wrappedHashSetForTest;
                    this.filledObjects++;
                    break;
                }
            }
        }
    }

    /*
    method to add all elements to data structure.
     */
    private void addAll(String[] words, SimpleSet dataStructure) {
        for (String word : words) {
            dataStructure.add(word);
        }
    }

    /*
    init our object with default init methods
     */
    private void defaultInit() {
        for (int i = 0; i < amountOfObjects; i++) {
            String dataName = this.collectionsToTest[i];
            switch (dataName) {
                case (OPEN_HASH_OBJECT): {
                    OpenHashSet openHashSetForTest = new OpenHashSet();
                    this.objectsForTestFile[i] = openHashSetForTest;
                    filledObjects++;
                    break;
                }
                case (CLOSE_HASH_OBJECT): {
                    ClosedHashSet closeHashSetForTest = new ClosedHashSet();
                    this.objectsForTestFile[i] = closeHashSetForTest;
                    filledObjects++;
                    break;
                }
            }
        }
        commonInit();
    }

    /*
    init our object with wanted data file
     */
    private void dataInit(String[] wordsFile) {
        for (int i = 0; i < amountOfObjects; i++) {
            String dataName = this.collectionsToTest[i];
            switch (dataName) {
                case (OPEN_HASH_OBJECT): {
                    OpenHashSet openHashSetForTest = new OpenHashSet(wordsFile);
                    this.objectsForTestFile[i] = openHashSetForTest;
                    filledObjects++;
                    break;
                }
                case (CLOSE_HASH_OBJECT): {
                    ClosedHashSet closeHashSetForTest = new ClosedHashSet(wordsFile);
                    this.objectsForTestFile[i] = closeHashSetForTest;
                    filledObjects++;
                    break;
                }
                case (HASH_SET_OBJECT): {
                    HashSet<String> hashSetForTest = new HashSet<String>();
                    CollectionFacadeSet wrappedHashSetForTest = new CollectionFacadeSet(hashSetForTest);
                    addAll(wordsFile, wrappedHashSetForTest);
                    this.objectsForTestFile[i] = wrappedHashSetForTest;
                    filledObjects++;
                    break;
                }
                case (TREE_SET_OBJECT): {
                    TreeSet<String> treeSetForTest = new TreeSet<String>();
                    CollectionFacadeSet wrappedTreeSetForTest = new CollectionFacadeSet(treeSetForTest);
                    addAll(wordsFile, wrappedTreeSetForTest);
                    this.objectsForTestFile[i] = wrappedTreeSetForTest;
                    filledObjects++;
                    break;
                }
                case (LINKED_LIST_OBJECT): {
                    LinkedList<String> linkedListForTest = new LinkedList<String>();
                    CollectionFacadeSet wrappedLinkedListForTest = new CollectionFacadeSet(linkedListForTest);
                    addAll(wordsFile, wrappedLinkedListForTest);
                    this.objectsForTestFile[i] = wrappedLinkedListForTest;
                    filledObjects++;
                    break;
                }

            }
        }
    }

    /*
    a method to loop the contains as much as we need a warm up
     */
    private long timeWarmUpLoop(SimpleSet dataStructure, String lookUpString) {
        for (int j = 0; j < WARM_UP_VALUE; j++) {
            dataStructure.contains(lookUpString);
        }
        long sumTime = 0;
        for (int j = 0; j < WARM_UP_VALUE; j++) {
            long timeBefore = System.nanoTime();
            dataStructure.contains(lookUpString);
            long difference = System.nanoTime() - timeBefore;
            sumTime = sumTime + difference;
        }
        return sumTime;
    }

    /*
    a method to calculate difference in time after contains operation
     */
    private long timeNoWarmUp(SimpleSet dataStructure, String lookUpString) {
        long sumTime = 0;
        for (int j = 0; j < NO_WARM_UP_VALUE; j++) {
            long timeBefore = System.nanoTime();
            dataStructure.contains(lookUpString);
            long difference = System.nanoTime() - timeBefore;
            sumTime = sumTime + difference;
        }
        return sumTime;
    }

    /*
    method to test adding from words array to each object in simpleSetArray of simple sets
     */
    private void addTest(String[] wordsArray) {
        int j = 0;
        for (SimpleSet dataStructure : this.objectsForTestFile) {
            long timeBefore = System.nanoTime();
            addAll(wordsArray, dataStructure);
            long difference = System.nanoTime() - timeBefore;
            System.out.println("Time taken: " + difference / DIVISION_FOR_NANO + " for:" + this.collectionsToTest[j]);
            j++;
        }
    }

    /*
   method to test contains for a string word to each object in simpleSetArray of simple sets
    */
    private void containTest(String toLookUp) {
        int i = 0;
        for (SimpleSet dataStructure : this.objectsForTestFile) {
            if (this.collectionsToTest[i].equals(LINKED_LIST_OBJECT)) {
                long difference = timeNoWarmUp(dataStructure, toLookUp);
                System.out.println("Time taken: " + difference / NO_WARM_UP_VALUE + " for: " + this.collectionsToTest[i]);
            } else {
                long sumTime = timeWarmUpLoop(dataStructure, toLookUp);
                System.out.println("Time taken: " + (sumTime / WARM_UP_VALUE) + " for: " + this.collectionsToTest[i]);
            }
            i++;
        }
    }

    /*
    private method to print out test number
     */
    private static void testNum(int i) {
        System.out.println("-------------------- TEST NUMBER " + i + " --------------------");
    }

    /**
     * a main method to run all tests to analyze the data structures
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer testDefault = new SimpleSetPerformanceAnalyzer();
        SimpleSetPerformanceAnalyzer testInit = new SimpleSetPerformanceAnalyzer();

        // ----- TEST 1 & 2 -----
        // init
        testNum(1);
        testDefault.defaultInit();
        // adding all one by one from data1.
        testDefault.addTest(Ex4Utils.file2array(DATA_1_FILE));

        testNum(2);
        // init
        testDefault.defaultInit();
        // adding all one by one from data2.
        testDefault.addTest(Ex4Utils.file2array(DATA_2_FILE));

        // ----- TEST 3 -----
        testNum(3);
        // init
        testInit.dataInit(Ex4Utils.file2array(DATA_1_FILE));
        // contains "hi" test
        testInit.containTest(HI_STRING_FOR_TEST);

        // ----- TEST 4 -----
        testNum(4);
        // contains number -13170890158 collisions hashing test in data 1
        testInit.containTest(LONG_NUMBER_FOR_TEST);

        // ----- TEST 5 -----
        testNum(5);
        //init
        testInit.dataInit(Ex4Utils.file2array(DATA_2_FILE));
        // contains number 23  collisions hashing test in data 2
        testInit.containTest(TWENTY_THREE_FOR_TEST);

        // ----- TEST 6 -----
        testNum(6);
        //init
        testInit.dataInit(Ex4Utils.file2array(DATA_2_FILE));
        // contains "hi" in data 2
        testInit.containTest(HI_STRING_FOR_TEST);
    }
}


