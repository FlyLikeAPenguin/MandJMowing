import java.util.regex.Pattern;

public class Customer { //FC2 User defined Classes/ Objects | FC3

	private String custName;   //Cannot change name, phonenumber, address or postcode
	private	String custPhoneNumber;
	private String custAddress;
	private String custPostCode;
	private String custInfo;
	private int[] custDays;
	boolean cusValid = true;

	Customer(String name, String address, String postCode, String phoneNumber, String additionalInfo, int[] days){

		if (ValidatePostCode(postCode) == false){
			
			cusValid = false;
			System.out.println(postCode);
			System.out.println("Invalid Post Code");
			
		}
		
		if (CheckLength(phoneNumber) == false){
			
			cusValid = false;
			
		}

		if (CheckNull(name) == true || CheckNull(phoneNumber) == true || CheckNull(address) == true){
			
			cusValid = false;
			System.out.println("Must enter a Name, Phone Number and Address");
			
		}

		if (cusValid == true){

			custName = name;   //Cannot change name
			System.out.println(custName);
			custAddress = address;
			System.out.println(custAddress);
			custPostCode = postCode;
			System.out.println(custPostCode);
			custPhoneNumber = phoneNumber;
			System.out.println(custPhoneNumber);
			CleanInfo(additionalInfo);
			System.out.println(custInfo);
			custDays = days;
			System.out.println(days);

		}
	}

	private boolean ValidatePostCode(String postCodeToCheck){
		
		//check for regex match
		//FC8 Pattern Matching (Regex).
		return (Pattern.matches("^([A-PR-UWYZ](([0-9](([0-9]|[A-HJKSTUW])?)?)|([A-HK-Y][0-9]([0-9]|[ABEHMNPRVWXY])?)) [0-9][ABD-HJLNP-UW-Z]{2})|GIR 0AA$", postCodeToCheck));
	}

	private boolean CheckNull(String data){
		
		if(data.equals("")){
			//No data entered
			return true;
		}else{
			//data entered
			return false;
			
		}
	}

	private void CleanInfo(String info){
		//FC8 Pattern Matching (Regex).
		custInfo = info.replaceAll("[^a-zA-Z0-9 ]", "");
		
	}
	
	private boolean CheckLength(String pn){
		if (pn.length() > 12){
			return false;
		} else { 
			return true;
		}
	}

	public String GetName(){
		return custName;
	}

	public String GetPhoneNumber(){
		return custPhoneNumber;
	}

	public String GetAddress(){
		return custAddress;
	}

	public String GetPostCode(){
		return custPostCode;
	}

	public String GetInfo(){
		return custInfo;
	}

	public void SetInfo(String newInfo){
		custInfo = newInfo;
	}

	public int[] GetDays(){
		return custDays;
	}

	public boolean GetValid(){
		return cusValid;
	}
}