//author: Xianduo Zeng


package cs570;
import java.util.Arrays;

//big-endian
public class BinaryNumber {
	private int[] data;
	private boolean overflow;
	
//	A constructor BinaryNumber(int length) for creating a binary number of length length
//	and consisting only of zeros.
	public BinaryNumber(int length) {
		data = new int[length];
		for(int i = 0;i < length;i++) {
			data[i] = 0;
		}
	}
//	A constructor BinaryNumber(String str) for creating a binary number given a string.
	public BinaryNumber(String str) {
		int length = str.length();
		data = new int[length];
		for(int i = 0;i < str.length();i++) {
			int number = Character.getNumericValue(str.charAt(i));
			if (number == 0||number == 1) {
				data[i] = number;
			}
		}
	}
//	An operation int getLength() for determining the length of a binary number.
	public int getLength() {
		return data.length;
		
	}
//	An operation int getDigit(int index) for obtaining a digit of a binary number given an
//	index.
	public int getDigit(int index) {
		if(index >= 0&&index < data.length) {
			return data[index];
		}else {
			System.out.println(" the index is out of bounds");
			return 0;
		}
	}
//	An operation int toDecimal() for transforming a binary number to its decimal notation
	public int toDecimal() {
		int length = data.length;
		int decimal = 0;
		for(int i = 0;i < length;i++) {
			int index = length - 1 - i;
			int power = (int)Math.pow(2, index);
			decimal = decimal + data[i] * power;
		}
		return decimal;
	}
//	An operation void shiftR(int amount) for shifting all digits in a binary number any
//	number of places to the right
	void shiftR(int amount) {
		int newlength = data.length + amount;
		int newdata[] = Arrays.copyOf(data, newlength);
		for(int i = newdata.length - 1;i >= amount;i--) {
			newdata[i] = data[i - amount];
		}
		for(int i = 0;i < amount;i++) {
			newdata[i] = 0;
			
		}
		String str = "";
		for(int i = 0 ;i < newlength;i++) {
			str = str + newdata[i];
		}
		System.out.println("the result of shifting:" + str);
	}
//	void add(BinaryNumber aBinaryNumber) for adding two binary numbers, one is the binary
//	number that receives the message and the other is given as a parameter.
	public void add(BinaryNumber aBinaryNumber) {
		if(data.length != aBinaryNumber.getLength()) {
			System.out.println("the lengths of the binary numbers do not coincide");
			
		}else {
			int carried = 0;
			int sum = 0;
			for(int i = data.length - 1;i >= 0;i--) {
				sum = data[i] + aBinaryNumber.getDigit(i) + carried;
				carried = sum / 2;
				data[i] = sum % 2;
			}
			System.out.println(toString());
			if(carried == 1) {
				overflow = true;
				System.out.println("overflow");
			}else {
				overflow = false;
			}
		}
	}
//	An operation clearOverflow() that clears the overflow flag.
	public void clearOverflow() {
		overflow = false;
		
	}
//	An operation String toString() for transforming a binary number to a String.
	public String toString() {
		String str = "";
		for(int i = 0;i < data.length;i++) {
			str = str + data[i];
		}
		if(overflow) {
			return "Overflow";
		}else {
			return str;
		}
	}
	
//	big-endian
	
	public static void main(String[] args) {
		BinaryNumber number1 = new BinaryNumber(10);
		BinaryNumber number2 = new BinaryNumber("0111");
		BinaryNumber number3 = new BinaryNumber("1101");
		System.out.println(number1.toString());
		System.out.println(number2.toString());
		System.out.println(number3.toDecimal());
		number3.shiftR(3);
		number2.add(number3);
		BinaryNumber number4 = new BinaryNumber("1101");
		BinaryNumber number5 = new BinaryNumber("10111");
		number4.add(number5);
	}
	
}
