import java.util.LinkedList;

/**
 * class of open hash set which uses linked lists for hashing for no collisions.
 * implements SimpleSet interface.
 *
 * @author rina.karnauch
 */
public class OpenHashSet extends SimpleHashSet implements SimpleSet {

    /**
     * nested class of linked list wrapper to create an array
     */
    public class LinkedListWrapper extends LinkedList<String> {
        /*
        wrapped linked list.
         */
        private LinkedList<String> linkedListWrapper;
    }

    /*
    represents the hastTable itself, an array of linked list of strings,
    wrapped by our nested wrapper class, due to java instructions.
     */
    private LinkedListWrapper[] hashTable;

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        setWithEmptyStrings();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        setWithEmptyStrings();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16)
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
        super();
        this.hashTable = new LinkedListWrapper[INITIAL_CAPACITY];
        setWithEmptyStrings();
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
        // add a new linked list in place.
        int currentFilledCapacity = super.getCurrentFilledCapacity();
        super.setCurrentFilledCapacity(currentFilledCapacity + 1);
        this.adjustCapacity(ADD);
        int hashingIndex = super.clamp(newValue.hashCode());
        if (hashTable[hashingIndex] == null) {
            this.hashTable[hashingIndex] = new LinkedListWrapper();
        }
        return this.hashTable[hashingIndex].add(newValue);
    }

    /**
     * Remove the input element from the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        if (searchVal == null) {
            return false;
        }
        int hashCodeIndex = super.clamp(searchVal.hashCode());
        if (hashTable[hashCodeIndex] != null) {
            return hashTable[hashCodeIndex].contains(searchVal);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (toDelete == null || !contains(toDelete)) {
            return false;
        }
        int hashingIndex = super.clamp(toDelete.hashCode());
        boolean deleteCheck = false;
        if (hashTable[hashingIndex] != null) {
            deleteCheck = hashTable[hashingIndex].remove(toDelete);
        }
        if (deleteCheck) {
            int currentFilledCapacity = super.getCurrentFilledCapacity();
            super.setCurrentFilledCapacity(currentFilledCapacity - 1);
            this.adjustCapacity(REMOVE);
        }
        return deleteCheck;
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
    a function to adjust new capacity to the table
     */
    private void adjustCapacity(String action) {
        float newLoadFactor = loadFactorCalculator();
        float currentLowerLoadFactor = super.getLowerLoadFactor();
        float currentUpperLoadFactor = super.getUpperLoadFactor();
        if (newLoadFactor < currentLowerLoadFactor && action.equals(REMOVE)) {
            LinkedListWrapper[] previousHashTable = this.hashTable.clone();
            decreaseSize(); // capacity is changed here
            super.setLoadFactor(newLoadFactor);
            this.rehash(previousHashTable);
        } else if (newLoadFactor > currentUpperLoadFactor && action.equals(ADD)) {
            LinkedListWrapper[] previousHashTable = this.hashTable.clone();
            increaseSize(); // capacity is changed here
            super.setLoadFactor(newLoadFactor);
            this.rehash(previousHashTable);
        }
    }

    /*
    rehash helper method
     */
    private void rehashLinkedList(LinkedListWrapper l) {
        for (String s : l) {
            if (s != null) {
                int newHashIndex = super.clamp(s.hashCode());
                if (this.hashTable[newHashIndex] == null) {
                    this.hashTable[newHashIndex] = new LinkedListWrapper();
                }
                this.hashTable[newHashIndex].add(s);
            }
        }
    }

    /*
    method to rehash all values to a new table
     */
    private void rehash(LinkedListWrapper[] previousHashTable) {
        if (previousHashTable == null) {
            return;
        }
        for (LinkedListWrapper l : previousHashTable) {
            // if l is null it is returned.
            if (l != null) {
                rehashLinkedList(l);
            }
        }
    }

    /*
     * a method to increase size of hashtable
     */
    private void increaseSize() {
        int newSize = this.getCapacity() * 2;
        this.hashTable = new LinkedListWrapper[newSize];
        this.setCapacity(newSize);
    }

    /*
     * a method to decrease size of hashtable
     */
    private void decreaseSize() {
        if (super.getCapacity() > 1) {
            int newSize = this.getCapacity() / 2;
            this.hashTable = new LinkedListWrapper[newSize];
            this.setCapacity(newSize);
        }
    }

    /*
    a method to init the linked lists inside the array of them
     */
    private void setWithEmptyStrings() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new LinkedListWrapper();
        }
    }

}
