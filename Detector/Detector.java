/**
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;


public class Detector {

	private File file;
	private File signatures;
	private File affectedFiles;
	private File resultFile;
	private File errorFile;

	byte[][] sigContent = new byte[1024][1024];
	Scanner scanner;


	public Detector(String path) {
		this.file = new File(path);
		if (this.file == null) {

			try {
				writeResult("Invalid file path " + path, errorFile);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		this.affectedFiles = new File("D:\\ISAA J COMPONENT\\Detector\\affected.txt");
		this.resultFile = new File("D:\\ISAA J COMPONENT\\Detector\\result.txt");
		this.signatures = new File("D:\\ISAA J COMPONENT\\Detector\\signatures.txt");
		this.errorFile = new File("D:\\ISAA J COMPONENT\\Detector\\errors.txt");
		try {

			this.scanner = new Scanner(signatures);
			int i = 0;
			while (scanner.hasNext()) {
				sigContent[i] = scanner.nextLine().getBytes();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}


	public void detect() {
		detect(file);
	}


	private void detect(File file) {
		if (file != null) {
			FileInputStream mFileInputStream = null;
			String chk = null;
			try {
				writeResult("scanning file " + file.getName() + " in " + file.getAbsolutePath(), resultFile);

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			if (file.isDirectory()) {
				File[] contents = file.listFiles();
				for (File _file : contents) {
					detect(_file);
				}
			} else {

				try {
					mFileInputStream = new FileInputStream(file);
					chk = DigestUtils.sha1Hex(IOUtils.toByteArray(mFileInputStream));
				} catch (IOException e1) {
					try {
						writeResult(
								"=============================================================================================================================",
								errorFile);
						writeResult("The following error occurred while scanning the file " + file.getAbsolutePath()
								+ " the error is shown below", errorFile);
						writeResult(e1.getMessage(), file);
					} catch (IOException ioe) {

						ioe.printStackTrace();
					}
				} finally {
					try {
						if (mFileInputStream != null)
							mFileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				for (byte[] bs : sigContent) {
					String signature = new String(bs);
					if (chk != null && (chk.equalsIgnoreCase(signature) || chk.contains(signature))) {
						try {
							writeResult(file.getAbsolutePath(), affectedFiles);
							break;
						} catch (IOException e) {
							try {
								writeResult(
										"=============================================================================================================================",
										errorFile);
								writeResult("The following error occured while scanning the file "
										+ file.getAbsolutePath() + " The error is shown below", errorFile);
								writeResult(e.getMessage(), file);
							} catch (IOException e1) {

								e1.printStackTrace();
							}
						}
					}

				}

			}
		} else {
			try {
				writeResult("Invlid file entry ", errorFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private void writeResult(String result, File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.append(result);
		writer.newLine();
		writer.flush();
		writer.close();
	}

}
