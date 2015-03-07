package ca.etsmtl.log430.lab3.a;

import java.io.PipedWriter;

import ca.etsmtl.log430.lab3.filters.FileReaderFilter;
import ca.etsmtl.log430.lab3.filters.FileWriterFilter;
import ca.etsmtl.log430.lab3.filters.MergeFilter;
import ca.etsmtl.log430.lab3.filters.ProjectFormatFilter;
import ca.etsmtl.log430.lab3.filters.StateFilter;
import ca.etsmtl.log430.lab3.filters.StatusFilter;

/**
 * This class contains the main method for assignment 3. The program
 * consists of these files:<br><br>
 * 
 * 1) SystemInitialize: instantiates all filters and pipes, starts all filters.<br>
 * 2) FileReaderFilter: reads input file and sends each line to its output pipe.<br>
 * 3) StatusFilter: separates the input stream in two project types (REG, CRI) and writes
 *    lines to the appropriate output pipe.<br>
 * 4) StateFilter: determines if an entry contains a particular state specified
 *    at instantiation. If so, sends the whole line to its output pipe.<br>
 * 5) MergeFilter: accepts inputs from 2 input pipes and writes them to its output pipe.<br>
 * 6) FileWriterFilter: sends its input stream to a text file.<br><br>
 * 
 * Pseudo Code:
 * <pre>
 * instantiate all filters and pipes
 * start FileReaderFilter
 * start StatusFilter
 * start StateFilter for RIS
 * start StateFilter for DIF
 * start MergeFilter
 * start FileWriterFilter
 * </pre>
 * 
 * Running the program:
 * <pre>
 * java SystemInitialize InputFile OutputFile > DebugFile
 * 
 * SystemInitialize - Program name
 * InputFile - Text input file (see comments below)
 * OutputFile - Text output file with students
 * DebugFile - Optional file to direct debug statements
 * </pre>
 * 
 * @author A.J. Lattanze
 */

public class SystemInitialize {

	public static void main(String argv[]) {
		// Let's make sure that input and output files are provided on the
		// command line

		if (argv.length != 3) {

			System.out
					.println("\n\nNombre incorrect de parametres d'entree. Utilisation:");
			System.out
					.println("\njava SystemInitialize <fichier d'entree> <fichier de sortie 1> <fichier de sortie 2>");

		} else {
			String inputFile = argv[0];
			//String state = argv[1];
			String outputFile1 = argv[1];
			String outputFile2 = argv[2];
			
			// These are the declarations for the pipes.
			PipedWriter pipe01 = new PipedWriter();
			PipedWriter pipe02 = new PipedWriter();
			PipedWriter pipe03 = new PipedWriter();
			PipedWriter pipe04 = new PipedWriter();
			PipedWriter pipe05 = new PipedWriter();
			PipedWriter pipe06 = new PipedWriter();
			PipedWriter pipe07 = new PipedWriter();
			PipedWriter pipe08 = new PipedWriter();
			PipedWriter pipe09 = new PipedWriter();
			PipedWriter pipe10 = new PipedWriter();
			PipedWriter pipe11 = new PipedWriter();

			// Instantiate Filter Threads
			Thread fileReaderFilter = new FileReaderFilter(inputFile, pipe01);
			
			Thread statusFilter = new StatusFilter(pipe01, pipe02, pipe03);
			Thread stateFilter1 = new StateFilter("RIS", pipe02, pipe04, pipe08);
			Thread stateFilter2 = new StateFilter("DIF", pipe03, pipe05, pipe09);
			
			Thread mergeFilter = new MergeFilter(pipe04, pipe05, pipe06);
			Thread projectFormatFilter1 = new ProjectFormatFilter(pipe06, pipe07);
			Thread fileWriterFilter1 = new FileWriterFilter(outputFile1, pipe07);
			
			Thread mergeFilter2 = new MergeFilter(pipe08, pipe09, pipe10);
			Thread projectFormatFilter2 = new ProjectFormatFilter(pipe10, pipe11);
			Thread fileWriterFilter2 = new FileWriterFilter(outputFile2, pipe11);
			
			// Start the threads
			fileReaderFilter.start();
			
			statusFilter.start();
			stateFilter1.start();
			stateFilter2.start();
			
			mergeFilter.start();
			projectFormatFilter1.start();
			fileWriterFilter1.start();
			
			mergeFilter2.start();
			projectFormatFilter2.start();
			fileWriterFilter2.start();
			
		}  // if
		
	} // main
	
} // SystemInitialize