package edu.iastate.cs228.hw3;

/**
 *  A class that creates PrimeFactorization object and modifies it.
 * @author Sylvia Nguyen
 *
 */

import java.util.ListIterator;

//how to handle overflow for all of the methods
public class PrimeFactorization implements Iterable<PrimeFactor> {
	private static final long OVERFLOW = -1;
	private long value; // the factored integer
						// it is set to OVERFLOW when the number is greater than 2^63-1, the
						// largest number representable by the type long.

	/**
	 * Reference to dummy node at the head.
	 */
	private Node head;

	/**
	 * Reference to dummy node at the tail.
	 */
	private Node tail;

	private int size; // number of distinct prime factors

	// ------------
	// Constructors
	// ------------

	/**
	 * Default constructor constructs an empty list to represent the number 1.
	 * 
	 * Combined with the add() method, it can be used to create a prime
	 * factorization.
	 */
	public PrimeFactorization() {

		head = new Node();
		tail = new Node();

		head.next = tail;
		tail.previous = head;

		size = 0;
		value = 1;
		// TODO
	}

	/**
	 * Obtains the prime factorization of n and creates a doubly linked list to
	 * store the result. Follows the direct search factorization algorithm in
	 * Section 1.2 of the project description.
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization(long n) throws IllegalArgumentException {
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = 0;

		PrimeFactorizationIterator toAdd = this.iterator();

		if (n < 1) {
			throw new IllegalArgumentException();
		}

		value = n;
//The prime number variable 
		int pri = 2;
		// The multiplicity variable
		int mul = 0;
		while (pri * pri <= value) {
			while (value % pri == 0) {
				value = value / pri;
				mul++;

			}

			if (mul > 0) {

				toAdd.add(new PrimeFactor(pri, mul));

				pri += 1;
				mul = 0;

			} else {

				pri += 1;

			}

		}

		int i = (int) value;
		if (i != 1) {
			toAdd.add(new PrimeFactor(i, 1));
		}
		updateValue();
		// TODO
	}

	/**
	 * Copy constructor. It is unnecessary to verify the primality of the numbers in
	 * the list.
	 * 
	 * @param pf
	 */
	public PrimeFactorization(PrimeFactorization pf) {
		// TODO

		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = 0;

		PrimeFactorizationIterator copyIter = this.iterator();
		PrimeFactorizationIterator beingCopied = pf.iterator();

		while (beingCopied.hasNext()) {
			copyIter.add(beingCopied.next().clone());

		}
		updateValue();
	}

	/**
	 * Constructs a factorization from an array of prime factors. Useful when the
	 * number is too large to be represented even as a long integer.
	 * 
	 * @param pflist
	 */
	public PrimeFactorization(PrimeFactor[] pfList) {
		// TODO
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
//The iterator that will help add data in the pfList array to this linked-list
		PrimeFactorizationIterator guide = this.iterator();
		for (int i = 0; i < pfList.length; i++) {
			if (pfList[i] != null) {
				guide.add(pfList[i]);
			}
		}
		updateValue();
	}

	// --------------
	// Primality Test
	// --------------

	/**
	 * Test if a number is a prime or not. Check iteratively from 2 to the largest
	 * integer not exceeding the square root of n to see if it divides n.
	 * 
	 * @param n
	 * @return true if n is a prime false otherwise
	 */
	public static boolean isPrime(long n) {
		if (n <= 1) {
			return false;
		}
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				return false;
			}

		}

		// TODO
		return true;
	}

	// ---------------------------
	// Multiplication and Division
	// ---------------------------

	/**
	 * Multiplies the integer v represented by this object with another number n.
	 * Note that v may be too large (in which case this.value == OVERFLOW). You can
	 * do this in one loop: Factor n and traverse the doubly linked list
	 * simultaneously. For details refer to Section 3.1 in the project description.
	 * Store the prime factorization of the product. Update value and size.
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public void multiply(long n) throws IllegalArgumentException {
		// TODO

		if (n < 1) {
			throw new IllegalArgumentException();
		}
//Prime factorization of n 
		PrimeFactorization factorN = new PrimeFactorization(n);
		// Iterator of factorN
		PrimeFactorizationIterator iterN = factorN.iterator();
		// Iterator of this iterator
		PrimeFactorizationIterator thisIter = this.iterator();
		// variable that holds the value returned by next for this list
		PrimeFactor thisF = null;
		// variable that holds the value returned by next for factorN list
		PrimeFactor nF = null;

		while (thisIter.hasNext() || iterN.hasNext()) {

			if (iterN.hasNext()) {
				nF = iterN.next();
			}

			else if (iterN.hasNext() == false && thisIter.hasNext()) {

				break;
			}

			if (thisIter.hasNext()) {
				thisF = thisIter.next();
			}
//when prime is equal
			if (nF.prime == thisF.prime) {
				thisF.multiplicity += nF.multiplicity;
				updateValue();

			}
			// when the current position of n prime is less than the position of this prime,
			// add n before the position of this
			if (nF.prime < thisF.prime) {
				thisIter.previous();

				thisIter.add(nF);

				updateValue();

			}
			// add what left of iterN at the end of this linked-list
			if (nF.prime > thisF.prime && thisIter.hasNext() == false) {
				thisIter.add(nF);
				updateValue();

			}
			// this iter will move forward, iterN will stay in place
			else if (nF.prime > thisF.prime) {
				iterN.previous();
			}

		}

	}

	/**
	 * Multiplies the represented integer v with another number in the factorization
	 * form. Traverse both linked lists and store the result in this list object.
	 * See Section 3.1 in the project description for details of algorithm.
	 * 
	 * @param pf
	 */
	public void multiply(PrimeFactorization pf) {

		PrimeFactorizationIterator iterN = pf.iterator();
		PrimeFactorizationIterator thisIter = this.iterator();
		PrimeFactor thisF = null;
		PrimeFactor nF = null;
		while (thisIter.hasNext() || iterN.hasNext()) {

			if (iterN.hasNext()) {
				nF = iterN.next();

			}
			// reached the end of the pF list
			else if (iterN.hasNext() == false && thisIter.hasNext()) {

				break;
			}

			if (thisIter.hasNext()) {
				thisF = thisIter.next();
			}
//when primes are equal to each other 
			if (nF.prime == thisF.prime) {
				thisF.multiplicity += nF.multiplicity;
				updateValue();

			}
			// when current position of pF is less than this position, add pF (before this
			// position) to this linked list
			if (nF.prime < thisF.prime) {
				thisIter.previous();

				thisIter.add(nF);

				updateValue();

			}
			// add what's left of pF once thisIter has reached the end of the list
			if (nF.prime > thisF.prime && thisIter.hasNext() == false) {
				thisIter.add(nF);
				updateValue();

			}
			// if pF position is less than this position, progress this position and keep pF
			// position the same
			else if (nF.prime > thisF.prime) {
				iterN.previous();
			}

		}

		// TODO
	}

	/**
	 * Multiplies the integers represented by two PrimeFactorization objects.
	 * 
	 * @param pf1
	 * @param pf2
	 * @return object of PrimeFactorization to represent the product
	 */
	public static PrimeFactorization multiply(PrimeFactorization pf1, PrimeFactorization pf2) {
		// TODO

		pf1.multiply(pf2);
		PrimeFactorization returnValue = new PrimeFactorization(pf1);

		return returnValue;
	}

	/**
	 * Divides the represented integer v by n. Make updates to the list, value, size
	 * if divisible. No update otherwise. Refer to Section 3.2 in the project
	 * description for details.
	 * 
	 * @param n
	 * @return true if divisible false if not divisible
	 * @throws IllegalArgumentException if n <= 0
	 */
	public boolean dividedBy(long n) throws IllegalArgumentException {
		// TODO
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		if (this.value != -1 && this.value < n) {
			return false;
		}
		PrimeFactorization factorN = new PrimeFactorization(n);
		if (this.dividedBy(factorN) == false) {
			return false;
		}

		return true;
	}

	/**
	 * Division where the divisor is represented in the factorization form. Update
	 * the linked list of this object accordingly by removing those nodes housing
	 * prime factors that disappear after the division. No update if this number is
	 * not divisible by pf. Algorithm details are given in Section 3.2.
	 * 
	 * @param pf
	 * @return true if divisible by pf false otherwise
	 */
	public boolean dividedBy(PrimeFactorization pf) {
		// TODO

		if (this.value != -1 && pf.value != -1 && this.value < pf.value) {
			return false;
		}
		if (this.value != -1 && pf.value == -1) {
			return false;
		}
		if (this.value == pf.value) {
			this.clearList();
			this.updateValue();
			return true;
		}
		PrimeFactorization copy = new PrimeFactorization(this);
		PrimeFactorizationIterator iterCopy = copy.iterator();
		PrimeFactorizationIterator iterPf = pf.iterator();

		while (iterCopy.hasNext() && iterPf.cursor != pf.tail) {

			if (!iterCopy.hasNext() && iterPf.hasNext()) {
				return false;
			}

			if (iterCopy.cursor.pFactor.prime >= iterPf.cursor.pFactor.prime) {
				if (iterCopy.cursor.pFactor.prime > iterPf.cursor.pFactor.prime) {
					return false;
				}
				if (iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime) {
					if (iterCopy.cursor.pFactor.multiplicity < iterPf.cursor.pFactor.multiplicity) {
						return false;
					} else if (iterCopy.cursor.pFactor.multiplicity == iterPf.cursor.pFactor.multiplicity) {
						iterCopy.next();
						iterCopy.remove();
						// iterCopy.next();

						iterPf.next();
					} else if (iterCopy.cursor.pFactor.multiplicity > iterPf.cursor.pFactor.multiplicity) {
						iterCopy.cursor.pFactor.multiplicity -= iterPf.cursor.pFactor.multiplicity;
						iterCopy.next();
						iterPf.next();
					}

				}

			} else {
				iterCopy.next();

			}
		}
		this.head = copy.head;
		this.tail = copy.tail;
		this.size = copy.size;
		copy.updateValue();
		this.value = copy.value;
		return true;
	}

	/**
	 * Divide the integer represented by the object pf1 by that represented by the
	 * object pf2. Return a new object representing the quotient if divisible. Do
	 * not make changes to pf1 and pf2. No update if the first number is not
	 * divisible by the second one.
	 * 
	 * @param pf1
	 * @param pf2
	 * @return quotient as a new PrimeFactorization object if divisible null
	 *         otherwise
	 */
	public static PrimeFactorization dividedBy(PrimeFactorization pf1, PrimeFactorization pf2) {
		// TODO

		if (pf1.dividedBy(pf2) == false) {
			return null;
		}

		return pf1;
	}

	// -----------------------
	// Greatest Common Divisor
	// -----------------------

	/**
	 * Computes the greatest common divisor (gcd) of the represented integer v and
	 * an input integer n. Returns the result as a PrimeFactor object. Calls the
	 * method Euclidean() if this.value != OVERFLOW.
	 * 
	 * It is more efficient to factorize the gcd than n, which can be much greater.
	 * 
	 * @param n
	 * @return prime factorization of gcd
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization gcd(long n) throws IllegalArgumentException {
		// TODO
		if (n < 1) {
			throw new IllegalArgumentException();
		}

		if (this.value != OVERFLOW) {
			long holder = Euclidean(this.value, n);
			PrimeFactorization gcd = new PrimeFactorization(holder);
			return gcd;
		} else {
			PrimeFactorization gcdOfN = new PrimeFactorization(n);
			PrimeFactorization newGCD = this.gcd(gcdOfN);
			newGCD.updateValue();
			if (newGCD.value != -1) {
				return newGCD;
			}
			return new PrimeFactorization();
		}

	}

	/**
	 * Implements the Euclidean algorithm to compute the gcd of two natural numbers
	 * m and n. The algorithm is described in Section 4.1 of the project
	 * description.
	 * 
	 * @param m
	 * @param n
	 * @return gcd of m and n.
	 * @throws IllegalArgumentException if m < 1 or n < 1
	 */
	public static long Euclidean(long m, long n) throws IllegalArgumentException {
		// TODO

		if (m < 1 || n < 1) {
			throw new IllegalArgumentException();
		}

		if (m > n) {
			long largeValue = m;
			long smallValue = n;

			long valueAfter = largeValue % smallValue;

			while (valueAfter != 0) {

				largeValue = smallValue;
				smallValue = valueAfter;
				valueAfter = largeValue % smallValue;

			}

			return smallValue;

		}
		// otherwise if n is more than m or if they are =
		long largeValue = n;
		long smallValue = m;
		long valueAfter = largeValue % smallValue;

		while (valueAfter != 0) {
			largeValue = smallValue;
			smallValue = valueAfter;
			valueAfter = largeValue % smallValue;

		}

		return smallValue;
	}

	/**
	 * Computes the gcd of the values represented by this object and pf by
	 * traversing the two lists. No direct computation involving value and pf.value.
	 * Refer to Section 4.2 in the project description on how to proceed.
	 * 
	 * @param pf
	 * @return prime factorization of the gcd
	 */
	public PrimeFactorization gcd(PrimeFactorization pf) {
		// TODO
		PrimeFactorization gcd = new PrimeFactorization();
		if (this.value == -1 || pf.value == -1) {

			gcd.updateValue();
			if (gcd.value == -1) {
				return new PrimeFactorization();
			}

		}
		PrimeFactorizationIterator thisIter = this.iterator();
		PrimeFactorizationIterator pfIter = pf.iterator();

		PrimeFactorizationIterator aa = gcd.iterator();
		PrimeFactor t = null;
		PrimeFactor p = null;

		if (thisIter.hasNext()) {
			t = thisIter.next();

		}
		if (pfIter.hasNext()) {
			p = pfIter.next();
		}
		if (this.value == pf.value) {
			return this;

		}

		while (thisIter.hasNext() || pfIter.hasNext()) {

			if (t.prime == p.prime) {
				if (t.multiplicity == p.multiplicity) {
					PrimeFactor temp = new PrimeFactor(t.prime, t.multiplicity);
					aa.add(temp);

				} else {
					PrimeFactor temp = new PrimeFactor(t.prime, Math.min(p.multiplicity, t.multiplicity));
					aa.add(temp);
				}

			}
			if (thisIter.hasNext() && t.prime <= p.prime) {
				t = thisIter.next();

			}
			if (pfIter.hasNext() && p.prime <= t.prime) {
				p = pfIter.next();
			}
			if (pfIter.hasNext() == false && p.prime < t.prime) {
				thisIter.next();
			}
		}
		if (t.prime == p.prime) {
			if (t.multiplicity == p.multiplicity) {
				PrimeFactor temp = new PrimeFactor(t.prime, t.multiplicity);
				aa.add(temp);

			} else {
				PrimeFactor temp = new PrimeFactor(t.prime, Math.min(p.multiplicity, t.multiplicity));
				aa.add(temp);
			}
		}
		gcd.updateValue();
		return gcd;
	}

	/**
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the gcd of two numbers represented by pf1 and
	 *         pf2
	 */
	public static PrimeFactorization gcd(PrimeFactorization pf1, PrimeFactorization pf2) {
		// TODO

		return pf1.gcd(pf2);
	}

	// ------------
	// List Methods
	// ------------

	/**
	 * Traverses the list to determine if p is a prime factor.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @return true if p is a prime factor of the number v represented by this
	 *         linked list false otherwise
	 * @throws IllegalArgumentException if p is not a prime
	 */
	public boolean containsPrimeFactor(int p) throws IllegalArgumentException {
		// TODO
		if (isPrime(p) == false) {
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = this.iterator();
		while (iter.hasNext()) {
			if (p == iter.next().prime) {
				return true;
			}

		}

		return false;
	}

	// The next two methods ought to be private but are made public for testing
	// purpose. Keep
	// them public

	/**
	 * Adds a prime factor p of multiplicity m. Search for p in the linked list. If
	 * p is found at a node N, add m to N.multiplicity. Otherwise, create a new node
	 * to store p and m.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p prime
	 * @param m multiplicity
	 * @return true if m >= 1 false if m < 1
	 */
	public boolean add(int p, int m) {
		// TODO
		PrimeFactorizationIterator iter = this.iterator();
		if (m >= 1) {

			while (iter.hasNext()) {

				if (p == iter.cursor.pFactor.prime) {
					iter.cursor.pFactor.multiplicity += m;
					updateValue();
					return true;
				}
				if (p < iter.cursor.pFactor.prime) {

					Node a = new Node(p, m);
					link(iter.cursor.previous, a);
					size++;
					updateValue();
					return true;
				}

				iter.next();

			}
			Node a = new Node(p, m);
			link(iter.cursor.previous, a);
			size++;
			updateValue();
			return true;

		}

		return false;
	}

	/**
	 * Removes m from the multiplicity of a prime p on the linked list. It starts by
	 * searching for p. Returns false if p is not found, and true if p is found. In
	 * the latter case, let N be the node that stores p. If N.multiplicity > m,
	 * subtracts m from N.multiplicity. If N.multiplicity <= m, removes the node N.
	 * 
	 * Precondition: p is a prime.
	 * 
	 * @param p
	 * @param m
	 * @return true when p is found. false when p is not found.
	 * @throws IllegalArgumentException if m < 1
	 */
	public boolean remove(int p, int m) throws IllegalArgumentException {
		// TODO
		if (m < 1) {
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = this.iterator();
		while (iter.hasNext()) {
			PrimeFactor compare = iter.next();
			if (p == compare.prime) {
				if (compare.multiplicity > m) {
					compare.multiplicity -= m;
					updateValue();
				} else {
					iter.remove();
					updateValue();

				}
				return true;
			}

		}

		return false;
	}

	/**
	 * 
	 * @return size of the list
	 */
	public int size() {
		// TODO
		return size;
	}

	/**
	 * Writes out the list as a factorization in the form of a product. Represents
	 * exponentiation by a caret. For example, if the number is 5814, the returned
	 * string would be printed out as "2 * 3^2 * 17 * 19".
	 */
	@Override
	public String toString() {
		// TODO
		String a = "";
		if (size == 0) {
			return "1";
		}
		PrimeFactorizationIterator b = this.iterator();
		while (b.hasNext()) {
			a += b.next().toString();
			if (b.hasNext()) {
				a += " * ";
			}

		}
		return a;
	}

	// The next three methods are for testing, but you may use them as you like.

	/**
	 * @return true if this PrimeFactorization is representing a value that is too
	 *         large to be within long's range. e.g. 999^999. false otherwise.
	 */
	public boolean valueOverflow() {
		return value == OVERFLOW;
	}

	/**
	 * @return value represented by this PrimeFactorization, or -1 if
	 *         valueOverflow()
	 */
	public long value() {
		return value;
	}

	public PrimeFactor[] toArray() {
		PrimeFactor[] arr = new PrimeFactor[size];
		int i = 0;
		for (PrimeFactor pf : this)
			arr[i++] = pf;
		return arr;
	}

	@Override
	public PrimeFactorizationIterator iterator() {
		return new PrimeFactorizationIterator();
	}

	/**
	 * Doubly-linked node type for this class.
	 */
	private class Node {
		public PrimeFactor pFactor;
		public Node next;
		public Node previous;

		/**
		 * Default constructor for creating a dummy node.
		 */
		public Node() {

			pFactor = null;
			next = null;
			previous = null;
			// TODO
		}

		/**
		 * Precondition: p is a prime
		 * 
		 * @param p prime number
		 * @param m multiplicity
		 * @throws IllegalArgumentException if m < 1
		 */
		public Node(int p, int m) throws IllegalArgumentException {
			if (m < 1) {
				throw new IllegalArgumentException();
			}

			pFactor = new PrimeFactor(p, m);
			next = null;
			previous = null;

			// TODO
		}

		/**
		 * Constructs a node over a provided PrimeFactor object.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 */
		public Node(PrimeFactor pf) {
			pFactor = pf;
			next = null;
			previous = null;

			// TODO
		}

		/**
		 * Printed out in the form: prime + "^" + multiplicity. For instance "2^3".
		 * Also, deal with the case pFactor == null in which a string "dummy" is
		 * returned instead.
		 */
		@Override
		public String toString() {
			if (pFactor == null) {
				return "dummy";
			}
			return pFactor.toString();
			// TODO

		}
	}

	public class PrimeFactorizationIterator implements ListIterator<PrimeFactor> {
		// Class invariants:
		// 1) logical cursor position is always between cursor.previous and cursor
		// 2) after a call to next(), cursor.previous refers to the node just returned
		// 3) after a call to previous() cursor refers to the node just returned
		// 4) index is always the logical index of node pointed to by cursor

		private Node cursor = head.next;
		private Node pending = null; // node pending for removal
		private int index = 0;

		// other instance variables ...

		/**
		 * Default constructor positions the cursor before the smallest prime factor.
		 */
		public PrimeFactorizationIterator() {
			cursor = head.next;
			pending = null;
			index = 0;
			// TODO
		}

		@Override
		public boolean hasNext() {
			// TODO
			if (cursor == tail) {
				return false;
			}
			return cursor != null;
		}

		@Override
		public boolean hasPrevious() {
			// TODO
			if (cursor == head) {
				return false;
			}
			return cursor.previous != null;
		}

		@Override
		public PrimeFactor next() {
			// TODO
			this.pending = cursor;
			this.cursor = cursor.next;
			this.index += 1;
			return pending.pFactor;
		}

		@Override
		public PrimeFactor previous() {
			// TODO

			pending = cursor.previous;
			cursor = cursor.previous;
			index -= 1;
			return pending.pFactor;

		}

		/**
		 * Removes the prime factor returned by next() or previous()
		 * 
		 * @throws IllegalStateException if pending == null
		 */
		@Override
		public void remove() throws IllegalStateException {
			if (pending == null) {
				throw new IllegalStateException();
			}
			if (pending.previous != null) {
				pending.previous.next = pending.next;
			}
			if (pending.next != null) {
				pending.next.previous = pending.previous;
			}
			unlink(pending);
			pending = null;
			index--;
			size--;

			// TODO
		}

		/**
		 * Adds a prime factor at the cursor position. The cursor is at a wrong position
		 * in either of the two situations below:
		 * 
		 * a) pf.prime < cursor.previous.pFactor.prime if cursor.previous != head. b)
		 * pf.prime > cursor.pFactor.prime if cursor != tail.
		 * 
		 * Take into account the possibility that pf.prime == cursor.pFactor.prime.
		 * 
		 * Precondition: pf.prime is a prime.
		 * 
		 * @param pf
		 * @throws IllegalArgumentException if the cursor is at a wrong position.
		 */
		@Override
		public void add(PrimeFactor pf) throws IllegalArgumentException {
			// TODO

			if (cursor.previous != head && pf.prime < cursor.previous.pFactor.prime) {
				throw new IllegalArgumentException();
			}
			if (cursor != tail && pf.prime > cursor.pFactor.prime) {
				throw new IllegalArgumentException();
			}

			Node temp = new Node(pf);
			link(cursor.previous, temp);
			size++;
			index++;

		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Deprecated
		@Override
		public void set(PrimeFactor pf) {
			throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support set method");
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	// --------------
	// Helper methods
	// --------------

	/**
	 * Inserts toAdd into the list after current without updating size.
	 * 
	 * Precondition: current != null, toAdd != null
	 */
	private void link(Node current, Node toAdd) {
		if (current != null && toAdd != null) {
			toAdd.next = current.next;
			current.next.previous = toAdd;
			toAdd.previous = current;

			current.next = toAdd;

		}

		// TODO
	}

	/**
	 * Removes toRemove from the list without updating size.
	 */
	private void unlink(Node toRemove) {
		if (toRemove != null) {
			toRemove.previous.next = toRemove.next;
			toRemove.next.previous = toRemove.previous;
		}
		// TODO
	}

	/**
	 * Remove all the nodes in the linked list except the two dummy nodes.
	 * 
	 * Made public for testing purpose. Ought to be private otherwise.
	 */
	public void clearList() {
		head.next = tail;
		tail.previous = head;
		size = 0;

		// TODO
	}

	/**
	 * Multiply the prime factors (with multiplicities) out to obtain the
	 * represented integer. Use Math.multiply(). If an exception is throw, assign
	 * OVERFLOW to the instance variable value. Otherwise, assign the multiplication
	 * result to the variable.
	 * 
	 */
	private void updateValue() {
		try {
			long newValue = 1;

			PrimeFactorizationIterator a = this.iterator();
			while (a.hasNext()) {
				PrimeFactor b = a.next();

				newValue = Math.multiplyExact((long) Math.pow(b.prime, b.multiplicity), (long) newValue);

			}
			value = newValue;
			// TODO
		}

		catch (ArithmeticException e) {
			value = OVERFLOW;
		}

	}
}
