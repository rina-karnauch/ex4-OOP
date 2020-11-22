import java.util.Arrays;

/**
 * class of closed hash set which uses quadratic probing for hashing for no collisions.
 * implements SimpleSet interface.
 *
 * @author rina.karnauch
 */
public class ClosedHashSet extends SimpleHashSet implements SimpleSet {


    /*
    not contained value indicator
     */
    private static final int NOT_CONTAINED = -1;

    /*
     * deleted object.
     */
    private static final String DELETE_STRING = new String("deleted");

    /*
     * the array of strings for the hashTable.
     */
    private String[] hashTable;

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.hashTable = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        hashTable = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16)
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        super();
        hashTable = new String[INITIAL_CAPACITY];
        if (data != null) {
            for (String s : data) {
                this.add(s);
            }
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (newValue == null || contains(newValue)) {
            return false;
        }
        int currentFilledCapacity = super.getCurrentFilledCapacity();
        super.setCurrentFilledCapacity(currentFilledCapacity + 1);
        this.adjustCapacity(ADD);
        for (int i = 0; i < super.getCapacity(); i++) {
            int hashValue = probedHashValueCalculation(newValue, i);
            int hashIndex = super.clamp(hashValue);
            if (this.hashTable[hashIndex] == null || hashTable[hashIndex] == DELETE_STRING) { // we need a ref to it.
                this.hashTable[hashIndex] = newValue;
                return true;
            }
        }
        return false;
    }

    /*
    a method to look for the toSearch value inside the hash table
    we will receive it's index if contained, and -1 otherwise.
     */
    private int searchIndex(String toSearch) {
        if (toSearch == null) {
            return NOT_CONTAINED;
        }
        for (int i = 0; i < super.getCapacity(); i++) {
            int hashValue = probedHashValueCalculation(toSearch, i);
            int hashIndex = super.clamp(hashValue);
            if (hashTable[hashIndex] == null) {
                return NOT_CONTAINED;
            } else if (hashTable[hashIndex] != null && hashTable[hashIndex].equals(toSearch)) {
                return hashIndex;
            } else if (hashTable[hashIndex] == DELETE_STRING) {
                continue;
            }
        }
        return NOT_CONTAINED;
    }

    /**
     * check the input element is from the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        int hashIndex = searchIndex(searchVal);
        return (hashIndex != NOT_CONTAINED);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        int hashIndex = searchIndex(toDelete);
        if (hashIndex == NOT_CONTAINED) {
            return false;
        } else {
            hashTable[hashIndex] = DELETE_STRING;
            int currentFilledCapacity = super.getCurrentFilledCapacity();
            super.setCurrentFilledCapacity(currentFilledCapacity - 1);
            this.adjustCapacity(REMOVE);
            return true;
        }
    }


    /**
     * The current capacity (number of cells) of the table.
     *
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return super.getCapacity();
    }

    /**
     * a method to get the number of elements currently in the set
     *
     * @return The number of elements currently in the set
     */
    public int size() {
        return super.getCurrentFilledCapacity();
    }

    /*
    private method to calculate the probed hashing value of String e with quadratic probing
    for the ith try out.
     */
    private int probedHashValueCalculation(String e, int i) {
        int hashCode = e.hashCode();
        int probingIndex = (i + (i * i));
        return hashCode + (probingIndex / 2);
    }

    /*
    a method to adjust capacity according to the action we did
     */
    private void adjustCapacity(String action) {
        float newLoadFactor = super.loadFactorCalculator();
        float currentUpperLoadFactor = super.getUpperLoadFactor();
        float currentLowerLoadFactor = super.getLowerLoadFactor();
        if (newLoadFactor > currentUpperLoadFactor && action.equals(ADD)) {
            String[] previousHashTable = this.hashTable.clone();
            increaseSize(); // capacity is changed here
            super.setLoadFactor(newLoadFactor);
            this.rehash(previousHashTable);
        } else if (newLoadFactor < currentLowerLoadFactor && action.equals(REMOVE)) {
            String[] previousHashTable = this.hashTable.clone();
            decreaseSize(); // capacity is changed here
            super.setLoadFactor(newLoadFactor);
            this.rehash(previousHashTable);
        }
    }

    /*
    method to rehash all values to a new table
     */
    private void rehash(String[] previousHashTable) {
        for (String s : previousHashTable) {
            if (s != null) {
                loopTillInsertion(s);
            }
        }
    }

    /*
    method to loop through
     */
    private void loopTillInsertion(String str) {
        boolean flag = false;
        int i = 0;
        while (i < this.capacity() && !flag) {
            int newHashValue = probedHashValueCalculation(str, i);
            int newHashIndex = super.clamp(newHashValue);
            if (this.hashTable[newHashIndex] == null || this.hashTable[newHashIndex] == DELETE_STRING) {
                this.hashTable[newHashIndex] = str;
                flag = true;
            }
            i++;
        }
    }

    /*
     * a method to increase size of hashtable
     */
    private void increaseSize() {
        int newSize = this.getCapacity() * 2;
        this.hashTable = new String[newSize];
        this.setCapacity(newSize);
    }

    /*
     * a method to decrease size of hashtable
     */
    private void decreaseSize() {
        if (super.getCapacity() > 1) {
            int newSize = this.getCapacity() / 2;
            this.hashTable = new String[newSize];
            this.setCapacity(newSize);
        }
    }
}
