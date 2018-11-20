package cz.martlin.tinyexcel.core.sources;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class _CommonsCSVExample {
	/**
	 * @author ashraf
	 *
	 */
	public static class Student {
		
		private long id;
		private String firstName;
		private String lastName;
		private String gender;
		private int age;
		/**
		 * @param id
		 * @param firstName
		 * @param lastName
		 * @param gender
		 * @param age
		 */
		public Student(long id, String firstName, String lastName, String gender,
				int age) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.age = age;
		}
		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}
		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}
		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}
		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		/**
		 * @return the gender
		 */
		public String getGender() {
			return gender;
		}
		/**
		 * @param gender the gender to set
		 */
		public void setGender(String gender) {
			this.gender = gender;
		}
		/**
		 * @return the age
		 */
		public int getAge() {
			return age;
		}
		/**
		 * @param age the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}
		
		@Override
		public String toString() {
			return "Student [id=" + id + ", firstName=" + firstName
					+ ", lastName=" + lastName + ", gender=" + gender + ", age="
					+ age + "]";
		}
	}
	
	/**
	 * @author ashraf
	 * 
	 */
	public static class CsvFileWriter {
		
		//Delimiter used in CSV file
		private static final String NEW_LINE_SEPARATOR = "\n";
		
		//CSV file header
		private static final Object [] FILE_HEADER = {"id","firstName","lastName","gender","age"};

		public static void writeCsvFile(String fileName) {
			
			//Create new students objects
			Student student1 = new Student(1, "Ahmed", "Mohamed", "M", 25);
			Student student2 = new Student(2, "Sara", "Said", "F", 23);
			Student student3 = new Student(3, "Ali", "Hassan", "M", 24);
			Student student4 = new Student(4, "Sama", "Karim", "F", 20);
			Student student5 = new Student(5, "Khaled", "Mohamed", "M", 22);
			Student student6 = new Student(6, "Ghada", "Sarhan", "F", 21);
			
			//Create a new list of student objects
			List<Student> students = new ArrayList<>();
			students.add(student1);
			students.add(student2);
			students.add(student3);
			students.add(student4);
			students.add(student5);
			students.add(student6);
			
			FileWriter fileWriter = null;
			
			CSVPrinter csvFilePrinter = null;
			
			//Create the CSVFormat object with "\n" as a record delimiter
	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
					
			try {
				
				//initialize FileWriter object
				fileWriter = new FileWriter(fileName);
				
				//initialize CSVPrinter object 
		        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		        
		        //Create CSV file header
		        csvFilePrinter.printRecord(FILE_HEADER);
				
				//Write a new student object list to the CSV file
				for (Student student : students) {
					List<String> studentDataRecord = new ArrayList<>();
		            studentDataRecord.add(String.valueOf(student.getId()));
		            studentDataRecord.add(student.getFirstName());
		            studentDataRecord.add(student.getLastName());
		            studentDataRecord.add(student.getGender());
		            studentDataRecord.add(String.valueOf(student.getAge()));
		            csvFilePrinter.printRecord(studentDataRecord);
				}

				System.out.println("CSV file was created successfully !!!");
				
			} catch (Exception e) {
				System.out.println("Error in CsvFileWriter !!!");
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
					csvFilePrinter.close();
				} catch (IOException e) {
					System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
	                e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @author ashraf_sarhan
	 *
	 */
	public static class CsvFileReader {
		
		//CSV file header
	    private static final String [] FILE_HEADER_MAPPING = {"id","firstName","lastName","gender","age"};
		
		//Student attributes
		private static final String STUDENT_ID = "id";
		private static final String STUDENT_FNAME = "firstName";
		private static final String STUDENT_LNAME = "lastName";
		private static final String STUDENT_GENDER = "gender"; 
		private static final String STUDENT_AGE = "age";
		
		public static void readCsvFile(String fileName) {

			FileReader fileReader = null;
			
			CSVParser csvFileParser = null;
			
			//Create the CSVFormat object with the header mapping
	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
	     
	        try {
	        	
	        	//Create a new list of student to be filled by CSV file data 
	        	List<Student> students = new ArrayList<>();
	            
	            //initialize FileReader object
	            fileReader = new FileReader(fileName);
	            
	            //initialize CSVParser object
	            csvFileParser = new CSVParser(fileReader, csvFileFormat);
	            
	            //Get a list of CSV file records
	            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
	            
	            //Read the CSV file records starting from the second record to skip the header
	            for (int i = 1; i < csvRecords.size(); i++) {
	            	CSVRecord record = csvRecords.get(i);
	            	//Create a new student object and fill his data
	            	Student student = new Student(Long.parseLong(record.get(STUDENT_ID)), record.get(STUDENT_FNAME), record.get(STUDENT_LNAME), record.get(STUDENT_GENDER), Integer.parseInt(record.get(STUDENT_AGE)));
	                students.add(student);	
				}
	            
	            //Print the new student list
	            for (Student student : students) {
					System.out.println(student.toString());
				}
	        } 
	        catch (Exception e) {
	        	System.out.println("Error in CsvFileReader !!!");
	            e.printStackTrace();
	        } finally {
	            try {
	                fileReader.close();
	                csvFileParser.close();
	            } catch (IOException e) {
	            	System.out.println("Error while closing fileReader/csvFileParser !!!");
	                e.printStackTrace();
	            }
	        }

		}

	}
}
