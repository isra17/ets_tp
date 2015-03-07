package ca.etsmtl.log430.lab3.b;

import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.List;

import ca.etsmtl.log430.lab3.filters.EntryFilter;
import ca.etsmtl.log430.lab3.filters.FileReaderFilter;
import ca.etsmtl.log430.lab3.filters.FileWriterFilter;
import ca.etsmtl.log430.lab3.filters.MergeFilter;
import ca.etsmtl.log430.lab3.filters.ProjectFormatFilter;
import ca.etsmtl.log430.lab3.filters.StatusFilter;
import ca.etsmtl.log430.lab3.predicate.ProgressPredicates;
import ca.etsmtl.log430.lab3.predicate.StatePredicate;

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
					.println("\njava SystemInitialize <fichier d'entree> <fichier de sortie REG> <fichier de sortie CRI>");

		} else {
			// These are the declarations for the pipes.
			PipedWriter pipe01 = new PipedWriter();
			PipedWriter regPipe = new PipedWriter();
			PipedWriter regStatePipe = new PipedWriter();
			PipedWriter criRisStatePipe = new PipedWriter();
			PipedWriter criOtherStatePipe = new PipedWriter();
			PipedWriter criPipe = new PipedWriter();
			PipedWriter pipe04 = new PipedWriter();
			PipedWriter pipe05 = new PipedWriter();
			PipedWriter pipe06 = new PipedWriter();
			PipedWriter pipe07 = new PipedWriter();
			PipedWriter pipe08 = new PipedWriter();
			PipedWriter pipe09 = new PipedWriter();
			
			List<String> state = new ArrayList<String>();
			state.add("RIS");
			state.add("DIF");
			
			
			// Instantiate Filter Threads
			Thread fileReaderFilter = new FileReaderFilter(argv[0], pipe01);
			//Thread statusFilter = new StatusFilter(pipe01, regPipe, criPipe);
			Thread statusFilter = new StatusFilter(pipe01, regPipe, criPipe);
			
			Thread regStateFilter = new EntryFilter(new StatePredicate(state), regPipe, regStatePipe, null);
			Thread regProgressFilter = new EntryFilter(new ProgressPredicates.Below(50), regStatePipe, pipe04, null);
			
			Thread regProjectFormatFilter = new ProjectFormatFilter(pipe04, pipe06);
			Thread regFileWriterFilter = new FileWriterFilter(argv[1], pipe06);
			
			Thread criStateFilter = new EntryFilter(new StatePredicate("RIS"), criPipe, criRisStatePipe, criOtherStatePipe);
			Thread criRisProgressFilter = new EntryFilter(new ProgressPredicates.Equal(25), criRisStatePipe, pipe07, null);
			Thread criDifProgressFilter = new EntryFilter(new ProgressPredicates.Above(75), criOtherStatePipe, pipe08, null);

			Thread criMergeFilter = new MergeFilter(pipe07, pipe08, pipe05);
			Thread criProjectFormatFilter = new ProjectFormatFilter(pipe05, pipe09);
			Thread criFileWriterFilter = new FileWriterFilter(argv[2], pipe09);

			// Start the threads
			fileReaderFilter.start();
			statusFilter.start();
			
			regStateFilter.start();
			regProgressFilter.start();
			regProjectFormatFilter.start();
			regFileWriterFilter.start();
			
			criStateFilter.start();
			criRisProgressFilter.start();
			criDifProgressFilter.start();
			criMergeFilter.start();
			criProjectFormatFilter.start();
			criFileWriterFilter.start();
			
		}  // if
		
	} // main
	
} // SystemInitialize