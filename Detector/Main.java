
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("******************************************************************");
		System.out.println("\t\t              KEYLOGGER DETECTOR!!\t\t\t\t");
		System.out.println("\t\tPROJECT BY: AENISH PRASAIN, SUSHOVAN K C and ISHAN KHADKA ");
		System.out.println("******************************************************************\n\n");

		System.out.println("Enter the path for detecting keyloggers: ");
		String path = scanner.nextLine();
		scanner.close();
		
		System.out.println("\nScanning all the files in the path specified......");
		Detector detector = new Detector(path);
		detector.detect();
		System.out.println("Scan Completed!!!\n");
		System.out.println("SCAN RESULTS IN D:\\ISAA J COMPONENT\\Detector\\");
		System.out.println("\t\t Result of the scan : result.txt");
		System.out.println("\t\t Errors while performing scan : errors.txt");
		System.out.println("\t\t List of affected files : affected.txt");
	}



}
