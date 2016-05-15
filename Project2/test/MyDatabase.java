import java.io.*;
import java.util.Scanner;

public class MyDatabase {

	public static void main(String[] args) {

		try {
			System.out.println("Available Functions: ");
			System.out.println("1.Parse and Create files");
			System.out.println("2.Query");
			System.out.println("3.Insert");
			System.out.println("4.Delete");
			Scanner sc = new Scanner(System.in);
			System.out.println("Please select your choice: ");
			int input = sc.nextInt();
			if (input == 1) {
				CreateDataBase();

			}
			if(input == 3) {
				System.out.println("Input your insert data: ");
				InsertFunction();
			}
			if ((input == 2) || (input == 4)) {
				if(input == 2) {
					System.out.println("Select which schema you want to make a query: ");
				} else {
					System.out.println("Select which schema you want to delete: ");
				}

				System.out.println("5. ID ");
				System.out.println("6. Company");
				System.out.println("7. Drug_ID");
				System.out.println("8. Trials");
				System.out.println("9. Patients");
				System.out.println("10. Dosage_mg");
				System.out.println("11. Reading");
				System.out.println("12. Double_Blind");
				System.out.println("13. Controlled_Study");
				System.out.println("14. Govt_Funded");
				System.out.println("15. Fda_Approved");
				System.out.println(" Please select your choice: ");
				int input2 = sc.nextInt();
				sc.nextLine();
				if (input2 == 5) {
					System.out
							.println("Please input the ID you would like to check: ");
					String input3 = sc.nextLine();
					if(input == 2)
						SearchFunction("id", input3);
					else if(input == 4)
						DeleteFunction("id", input3);
				}
				if (input2 == 6) {
					System.out
							.println("Please input the Company you would like to check: ");
					String input4 = sc.nextLine();
					if(input == 2)
						SearchFunction("company", input4);
					else if(input == 4)
						DeleteFunction("company", input4);
				}
				if (input2 == 7) {
					System.out
							.println("Please input the Drug_ID you would like to check: ");
					String input5 = sc.nextLine();
					if(input == 2)
						SearchFunction("drug_id", input5);
					else if(input == 4)
						DeleteFunction("drug_id", input5);
				}
				if (input2 == 8) {
					System.out
							.println("Please input the Trials you would like to check: ");
					String input6 = sc.nextLine();
					if(input == 2)
						SearchFunction("trials", input6);
					else if(input == 4)
						DeleteFunction("trials", input6);
				}
				if (input2 == 9) {
					System.out
							.println("Please input the Patients you would like to check: ");
					String input7 = sc.nextLine();
					if(input == 2)
						SearchFunction("Patients", input7);
					else if(input == 4)
						DeleteFunction("Patients", input7);
				}
				if (input2 == 10) {
					System.out
							.println("Please input the Dosage_MG you would like to check: ");
					String input8 = sc.nextLine();
					if(input == 2)
						SearchFunction("dosage_mg", input8);
					else if(input == 4)
						DeleteFunction("dosage_mg", input8);
				}
				if (input2 == 11) {
					System.out
							.println("Please input the Reading you would like to check: ");
					String input9 = sc.nextLine();
					if(input == 2)
						SearchFunction("reading", input9);
					else if(input == 4)
						DeleteFunction("reading", input9);
				}
				if (input2 == 12) {
					System.out
							.println("Please input the Double_Blind you would like to check: ");
					String input10 = sc.nextLine();
					if(input == 2)
						SearchFunction("double_blind", input10);
					else if(input == 4)
						DeleteFunction("double_blind", input10);
				}
				if (input2 == 13) {
					System.out
							.println("Please input the Controlled_Study you would like to check: ");
					String input11 = sc.nextLine();
					if(input == 2)
						SearchFunction("controlled_study", input11);
					else if(input == 4)
						DeleteFunction("controlled_study", input11);
				}
				if (input2 == 14) {
					System.out
							.println("Please input the Govt_Funded you would like to check: ");
					String input12 = sc.nextLine();
					if(input == 2)
						SearchFunction("govt_funded", input12);
					else if(input == 4)
						DeleteFunction("govt_funded", input12);
				}
				if (input2 == 15) {
					System.out
							.println("Please input the Fda_Approved you would like to check: ");
					String input13 = sc.nextLine();
					if(input == 2)
						SearchFunction("fda_approved", input13);
					else if(input == 4)
						DeleteFunction("fda_approved", input13);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void CreateIndex(String n1, String n2, String n3, String n4,
			String n5, String n6, String n7, String n8, String n9, String n10,
			long os) {
		try {

			RandomAccessFile rafn1 = new RandomAccessFile("company.ndx", "rw");
			RandomAccessFile rafn2 = new RandomAccessFile("drug_id.ndx", "rw");
			RandomAccessFile rafn3 = new RandomAccessFile("trials.ndx", "rw");
			RandomAccessFile rafn4 = new RandomAccessFile("patients.ndx", "rw");
			RandomAccessFile rafn5 = new RandomAccessFile("dosage_mg.ndx", "rw");
			RandomAccessFile rafn6 = new RandomAccessFile("reading.ndx", "rw");
			RandomAccessFile rafn7 = new RandomAccessFile("double_blind.ndx", "rw");
			RandomAccessFile rafn8 = new RandomAccessFile("controlled_study.ndx", "rw");
			RandomAccessFile rafn9 = new RandomAccessFile("govt_funded.ndx", "rw");
			RandomAccessFile rafn10 = new RandomAccessFile("fda_approved.ndx", "rw");

			String line;
			String[] item = new String[2];
			String str;
			Long offset = (long) 0;
			// n1
			if (n1 != null) {
				while ((line = rafn1.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n1)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn1.setLength(rafn1.length() + b.length);
						for (long i = rafn1.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn1.seek(i - b.length);
							byte temp = rafn1.readByte();
							rafn1.seek(i);
							rafn1.writeByte(temp);
						}
						rafn1.seek(offset);
						rafn1.write(b);
						break;
					}

					if (item[1].compareTo(n1) > 0) {
						str = Long.toString(os) + " " + n1 + "\r\n";
						byte[] b = str.getBytes();
						rafn1.setLength(rafn1.length() + b.length);
						for (long i = rafn1.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn1.seek(i - b.length);
							byte temp = rafn1.readByte();
							rafn1.seek(i);
							rafn1.writeByte(temp);
						}
						rafn1.seek(offset);
						rafn1.write(b);

						break;
					}
					offset = rafn1.getFilePointer();
				}
				if (line == null) {
					rafn1.writeBytes(Long.toString(os) + " " + n1 + "\r\n");
				}
			}
			// n2
			if (n2 != null) {
				offset = (long) 0;
				while ((line = rafn2.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n2)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn2.setLength(rafn2.length() + b.length);
						for (long i = rafn2.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn2.seek(i - b.length);
							byte temp = rafn2.readByte();
							rafn2.seek(i);
							rafn2.writeByte(temp);
						}
						rafn2.seek(offset);
						rafn2.write(b);
						break;
					}

					if (item[1].compareTo(n2) > 0) {
						str = Long.toString(os) + " " + n2 + "\r\n";
						byte[] b = str.getBytes();
						rafn2.setLength(rafn2.length() + b.length);
						for (long i = rafn2.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn2.seek(i - b.length);
							byte temp = rafn2.readByte();
							rafn2.seek(i);
							rafn2.writeByte(temp);
						}
						rafn2.seek(offset);
						rafn2.write(b);

						break;
					}
					offset = rafn2.getFilePointer();
				}
				if (line == null)
					rafn2.writeBytes(Long.toString(os) + " " + n2 + "\r\n");
			}

			// n3
			if (n3 != null) {
				offset = (long) 0;
				while ((line = rafn3.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n3)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn3.setLength(rafn3.length() + b.length);
						for (long i = rafn3.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn3.seek(i - b.length);
							byte temp = rafn3.readByte();
							rafn3.seek(i);
							rafn3.writeByte(temp);
						}
						rafn3.seek(offset);
						rafn3.write(b);
						break;
					}

					if (item[1].compareTo(n3) > 0) {
						str = Long.toString(os) + " " + n3 + "\r\n";
						byte[] b = str.getBytes();
						rafn3.setLength(rafn3.length() + b.length);
						for (long i = rafn3.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn3.seek(i - b.length);
							byte temp = rafn3.readByte();
							rafn3.seek(i);
							rafn3.writeByte(temp);
						}
						rafn3.seek(offset);
						rafn3.write(b);

						break;
					}
					offset = rafn3.getFilePointer();
				}
				if (line == null)
					rafn3.writeBytes(Long.toString(os) + " " + n3 + "\r\n");
			}
			// n4
			if (n4 != null) {
				offset = (long) 0;
				while ((line = rafn4.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n4)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn4.setLength(rafn4.length() + b.length);
						for (long i = rafn4.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn4.seek(i - b.length);
							byte temp = rafn4.readByte();
							rafn4.seek(i);
							rafn4.writeByte(temp);
						}
						rafn4.seek(offset);
						rafn4.write(b);
						break;
					}

					if (item[1].compareTo(n4) > 0) {
						str = Long.toString(os) + " " + n4 + "\r\n";
						byte[] b = str.getBytes();
						rafn4.setLength(rafn4.length() + b.length);
						for (long i = rafn4.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn4.seek(i - b.length);
							byte temp = rafn4.readByte();
							rafn4.seek(i);
							rafn4.writeByte(temp);
						}
						rafn4.seek(offset);
						rafn4.write(b);

						break;
					}
					offset = rafn4.getFilePointer();
				}
				if (line == null)
					rafn4.writeBytes(Long.toString(os) + " " + n4 + "\r\n");
			}
			// n5
			if (n5 != null) {
				offset = (long) 0;
				while ((line = rafn5.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n5)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn5.setLength(rafn5.length() + b.length);
						for (long i = rafn5.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn5.seek(i - b.length);
							byte temp = rafn5.readByte();
							rafn5.seek(i);
							rafn5.writeByte(temp);
						}
						rafn5.seek(offset);
						rafn5.write(b);
						break;
					}

					if (item[1].compareTo(n5) > 0) {
						str = Long.toString(os) + " " + n5 + "\r\n";
						byte[] b = str.getBytes();
						rafn5.setLength(rafn5.length() + b.length);
						for (long i = rafn5.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn5.seek(i - b.length);
							byte temp = rafn5.readByte();
							rafn5.seek(i);
							rafn5.writeByte(temp);
						}
						rafn5.seek(offset);
						rafn5.write(b);

						break;
					}
					offset = rafn5.getFilePointer();
				}
				if (line == null)
					rafn5.writeBytes(Long.toString(os) + " " + n5 + "\r\n");
			}
			// n6
			if (n6 != null) {
				offset = (long) 0;
				while ((line = rafn6.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n6)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn6.setLength(rafn6.length() + b.length);
						for (long i = rafn6.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn6.seek(i - b.length);
							byte temp = rafn6.readByte();
							rafn6.seek(i);
							rafn6.writeByte(temp);
						}
						rafn6.seek(offset);
						rafn6.write(b);
						break;
					}

					if (item[1].compareTo(n6) > 0) {
						str = Long.toString(os) + " " + n6 + "\r\n";
						byte[] b = str.getBytes();
						rafn6.setLength(rafn6.length() + b.length);
						for (long i = rafn6.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn6.seek(i - b.length);
							byte temp = rafn6.readByte();
							rafn6.seek(i);
							rafn6.writeByte(temp);
						}
						rafn6.seek(offset);
						rafn6.write(b);

						break;
					}
					offset = rafn6.getFilePointer();
				}
				if (line == null)
					rafn6.writeBytes(Long.toString(os) + " " + n6 + "\r\n");
			}
			// n7
			if (n7 != null) {
				offset = (long) 0;
				while ((line = rafn7.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n7)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn7.setLength(rafn7.length() + b.length);
						for (long i = rafn7.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn7.seek(i - b.length);
							byte temp = rafn7.readByte();
							rafn7.seek(i);
							rafn7.writeByte(temp);
						}
						rafn7.seek(offset);
						rafn7.write(b);
						break;
					}

					if (item[1].compareTo(n7) > 0) {
						str = Long.toString(os) + " " + n7 + "\r\n";
						byte[] b = str.getBytes();
						rafn7.setLength(rafn7.length() + b.length);
						for (long i = rafn7.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn7.seek(i - b.length);
							byte temp = rafn7.readByte();
							rafn7.seek(i);
							rafn7.writeByte(temp);
						}
						rafn7.seek(offset);
						rafn7.write(b);

						break;
					}
					offset = rafn7.getFilePointer();
				}
				if (line == null)
					rafn7.writeBytes(Long.toString(os) + " " + n7 + "\r\n");
			}

			// n8
			if (n8 != null) {
				offset = (long) 0;
				while ((line = rafn8.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n8)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn8.setLength(rafn8.length() + b.length);
						for (long i = rafn8.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn8.seek(i - b.length);
							byte temp = rafn8.readByte();
							rafn8.seek(i);
							rafn8.writeByte(temp);
						}
						rafn8.seek(offset);
						rafn8.write(b);
						break;
					}

					if (item[1].compareTo(n8) > 0) {
						str = Long.toString(os) + " " + n8 + "\r\n";
						byte[] b = str.getBytes();
						rafn8.setLength(rafn8.length() + b.length);
						for (long i = rafn8.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn8.seek(i - b.length);
							byte temp = rafn8.readByte();
							rafn8.seek(i);
							rafn8.writeByte(temp);
						}
						rafn8.seek(offset);
						rafn8.write(b);

						break;
					}
					offset = rafn8.getFilePointer();
				}
				if (line == null)
					rafn8.writeBytes(Long.toString(os) + " " + n8 + "\r\n");
			}
			// n9
			if (n9 != null) {
				offset = (long) 0;
				while ((line = rafn9.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n9)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn9.setLength(rafn9.length() + b.length);
						for (long i = rafn9.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn9.seek(i - b.length);
							byte temp = rafn9.readByte();
							rafn9.seek(i);
							rafn9.writeByte(temp);
						}
						rafn9.seek(offset);
						rafn9.write(b);
						break;
					}

					if (item[1].compareTo(n9) > 0) {
						str = Long.toString(os) + " " + n9 + "\r\n";
						byte[] b = str.getBytes();
						rafn9.setLength(rafn9.length() + b.length);
						for (long i = rafn9.length() - 1; i > b.length + offset
								- 1; i--) {
							rafn9.seek(i - b.length);
							byte temp = rafn9.readByte();
							rafn9.seek(i);
							rafn9.writeByte(temp);
						}
						rafn9.seek(offset);
						rafn9.write(b);

						break;
					}
					offset = rafn9.getFilePointer();
				}
				if (line == null)
					rafn9.writeBytes(Long.toString(os) + " " + n9 + "\r\n");
			}
			// n10
			if (n10 != null) {
				offset = (long) 0;
				while ((line = rafn10.readLine()) != null) {
					item = line.split(" ");
					if (item[1].equals(n10)) {
						str = Long.toString(os) + ",";
						byte[] b = str.getBytes();
						rafn10.setLength(rafn10.length() + b.length);
						for (long i = rafn10.length() - 1; i > b.length
								+ offset - 1; i--) {
							rafn10.seek(i - b.length);
							byte temp = rafn10.readByte();
							rafn10.seek(i);
							rafn10.writeByte(temp);
						}
						rafn10.seek(offset);
						rafn10.write(b);
						break;
					}

					if (item[1].compareTo(n10) > 0) {
						str = Long.toString(os) + " " + n10 + "\r\n";
						byte[] b = str.getBytes();
						rafn10.setLength(rafn10.length() + b.length);
						for (long i = rafn10.length() - 1; i > b.length
								+ offset - 1; i--) {
							rafn10.seek(i - b.length);
							byte temp = rafn10.readByte();
							rafn10.seek(i);
							rafn10.writeByte(temp);
						}
						rafn10.seek(offset);
						rafn10.write(b);

						break;
					}
					offset = rafn10.getFilePointer();
				}
				if (line == null)
					rafn10.writeBytes(Long.toString(os) + " " + n10 + "\r\n");
			}
			rafn1.close();
			rafn2.close();
			rafn3.close();
			rafn4.close();
			rafn5.close();
			rafn6.close();
			rafn7.close();
			rafn8.close();
			rafn9.close();
			rafn10.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int CreateDataBase() {
		try {

			BufferedReader reader = new BufferedReader(
					new FileReader("PHARMA_TRIALS_1000B.csv"));
			RandomAccessFile raf = new RandomAccessFile("data.db", "rw");
			RandomAccessFile rafid = new RandomAccessFile("id.ndx", "rw");

			reader.readLine();
			String line = null;
			long offset = 0;
			// int i = 0;
			// String items[] = new String[10];

			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				if (items[0].trim().equalsIgnoreCase("id"))
					continue;
				offset = raf.getFilePointer();
				items[1] = items[1].replace("\"", "");

				raf.writeInt(Integer.parseInt(items[0]));
				raf.writeByte(items[1].length());
				raf.writeBytes(items[1]);
				raf.writeBytes(items[2]);
				raf.writeShort(Short.parseShort(items[3]));
				raf.writeShort(Short.parseShort(items[4]));
				raf.writeShort(Short.parseShort(items[5]));
				raf.writeFloat(Float.parseFloat(items[6]));
				Byte byt = (byte) 00000000;
				if (Boolean.parseBoolean(items[7]))
					byt = (byte) (byt | (1 << 3));
				if (Boolean.parseBoolean(items[8]))
					byt = (byte) (byt | (1 << 2));
				if (Boolean.parseBoolean(items[9]))
					byt = (byte) (byt | (1 << 1));
				if (Boolean.parseBoolean(items[10]))
					byt = (byte) (byt | (1 << 0));
				raf.write(byt);

//				byt = (byte) 00000001;  // not delete
//				raf.write(byt);

				/*
				 * for(i = 0; i < 11; i ++ ){ raf.writeBytes(items[i] + "\t");
				 * //System.out.println(items[i]); }
				 */

				raf.writeBytes("\n");
				rafid.writeBytes(Long.toString(offset) + " " + items[0]
						+ "\r\n");
				CreateIndex(items[1], items[2], items[3], items[4], items[5],
						items[6], items[7], items[8], items[9], items[10],
						offset);
			}
			reader.close();
			raf.close();
			rafid.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void SearchFunction(String index, String value) {
		try {

			RandomAccessFile raf = new RandomAccessFile(index + ".ndx", "r");
			RandomAccessFile rafdb = new RandomAccessFile("data.db", "r");

			String line;
			String item[] = new String[2];
			int i = 0;
			long os;

			long count = 0;

			while ((line = raf.readLine()) != null) {
				item = line.split(" ");

//				if(0 == count)
//				{
//					count++;
//					System.out.println(item[1]);
//				}
				// System.out.println(item[0]);
				if (item[1].equals(value)) {

					String offset[] = item[0].split(",");

//					System.out.println(offset);

					// long offsetvalue = Long.parseLong(item[0]);

					// System.out.println(item[0]);
					for (i = offset.length - 1; i >= 0; i--)
					// for(int f=0; f <offsetvalue;f++)
					{
						os = Long.parseLong(offset[i]);
						rafdb.seek(os);
						int id = rafdb.readInt();
						int leth = rafdb.read();

						byte[] comp = new byte[leth];
						rafdb.read(comp);
						String company = new String(comp);
						byte[] drugid = new byte[6];
						rafdb.read(drugid);
						String drug_id = new String(drugid);
						short trials = rafdb.readShort();
						short patients = rafdb.readShort();
						short dosage_mg = rafdb.readShort();
						float reading = rafdb.readFloat();
						String double_blind, controlled_study, govt_funded, fda_approved, delete_flag;
						int by = rafdb.read();
						if ((by & 0x08) == 0x08)
							double_blind = "true";
						else
							double_blind = "false";
						if ((by & 0x04) == 0x04)
							controlled_study = "true";
						else
							controlled_study = "false";
						if ((by & 0x02) == 0x02)
							govt_funded = "true";
						else
							govt_funded = "false";
						if ((by & 0x01) == 0x01)
							fda_approved = "true";
						else
							fda_approved = "false";

						if ((by & 0x80) == 0x80)
							delete_flag = "true";
						else
							delete_flag = "false";

						if("false" == delete_flag)
							System.out.println(id + "\t" + company + "\t" + drug_id
									+ "\t" + trials + "\t" + patients + "\t"
									+ dosage_mg + "\t" + reading + "\t"
									+ double_blind + "\t" + controlled_study + "\t"
									+ govt_funded + "\t" + fda_approved + "\t");
					}
				}
			}
			rafdb.close();
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void DeleteFunction(String index, String value) {
		try {

			RandomAccessFile raf = new RandomAccessFile(index + ".ndx", "r");
			RandomAccessFile rafdb = new RandomAccessFile("data.db", "rw");

			String line;
			String item[] = new String[2];
			int i = 0;
			long os;

			while ((line = raf.readLine()) != null) {
				item = line.split(" ");
				// System.out.println(item[0]);
				if (item[1].equals(value)) {

					String offset[] = item[0].split(",");
					// long offsetvalue = Long.parseLong(item[0]);

					// System.out.println(item[0]);
					for (i = offset.length - 1; i >= 0; i--)
					// for(int f=0; f <offsetvalue;f++)
					{
						os = Long.parseLong(offset[i]);
						rafdb.seek(os);
						int id = rafdb.readInt();
						int leth = rafdb.read();

						byte[] comp = new byte[leth];
						rafdb.read(comp);
						String company = new String(comp);
						byte[] drugid = new byte[6];
						rafdb.read(drugid);
						String drug_id = new String(drugid);
						short trials = rafdb.readShort();
						short patients = rafdb.readShort();
						short dosage_mg = rafdb.readShort();
						float reading = rafdb.readFloat();
						String double_blind, controlled_study, govt_funded, fda_approved;

						long bytePos = rafdb.getFilePointer();  // get file offset

						int by = rafdb.read();
						if ((by & 0x08) == 0x08)
							double_blind = "true";
						else
							double_blind = "false";
						if ((by & 0x04) == 0x04)
							controlled_study = "true";
						else
							controlled_study = "false";
						if ((by & 0x02) == 0x02)
							govt_funded = "true";
						else
							govt_funded = "false";
						if ((by & 0x01) == 0x01)
							fda_approved = "true";
						else
							fda_approved = "false";

						by |= 0x80;  // delete flag
						rafdb.seek(bytePos);
						rafdb.write(by);

						System.out.println("delete: " + id + "\t" + company + "\t" + drug_id
								+ "\t" + trials + "\t" + patients + "\t"
								+ dosage_mg + "\t" + reading + "\t"
								+ double_blind + "\t" + controlled_study + "\t"
								+ govt_funded + "\t" + fda_approved + "\t");
					}
				}
			}
			rafdb.close();
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int InsertFunction() {
		try {

			RandomAccessFile raf = new RandomAccessFile("data.db", "rw");
			RandomAccessFile rafid = new RandomAccessFile("id.ndx", "rw");

//			reader.readLine();
			Scanner sc = new Scanner(System.in);
			String line = sc.nextLine();
			System.out.println(line);

			long offset = 0;
//			// int i = 0;
//			// String items[] = new String[10];
//
//			while ((line = reader.readLine()) != null) {

			long rafPos=raf.length();    //取文件长度。
			raf.seek(rafPos);

//			String[] items = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			String[] items = line.split(",");
			if (items[0].trim().equalsIgnoreCase("id"))
				return 1;
			offset = raf.getFilePointer();
			items[1] = items[1].replace("\"", "");

			raf.writeInt(Integer.parseInt(items[0]));
			raf.writeByte(items[1].length());
			raf.writeBytes(items[1]);
			raf.writeBytes(items[2]);
			raf.writeShort(Short.parseShort(items[3]));
			raf.writeShort(Short.parseShort(items[4]));
			raf.writeShort(Short.parseShort(items[5]));
			raf.writeFloat(Float.parseFloat(items[6]));
			Byte byt = (byte) 00000000;
			if (Boolean.parseBoolean(items[7]))
				byt = (byte) (byt | (1 << 3));
			if (Boolean.parseBoolean(items[8]))
				byt = (byte) (byt | (1 << 2));
			if (Boolean.parseBoolean(items[9]))
				byt = (byte) (byt | (1 << 1));
			if (Boolean.parseBoolean(items[10]))
				byt = (byte) (byt | (1 << 0));
			raf.write(byt);

//			byt = (byte) 00000001;  // not delete
//			raf.write(byt);

			/*
			 * for(i = 0; i < 11; i ++ ){ raf.writeBytes(items[i] + "\t");
			 * //System.out.println(items[i]); }
			 */

			raf.writeBytes("\n");

			long rafidPos=rafid.length();    //取文件长度。
			rafid.seek(rafidPos);
			rafid.writeBytes(Long.toString(offset) + " " + items[0]
					+ "\r\n");
			CreateIndex(items[1], items[2], items[3], items[4], items[5],
					items[6], items[7], items[8], items[9], items[10],
					offset);

			raf.close();
			rafid.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}