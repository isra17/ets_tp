package ca.etsmtl.log430.lab3.filters;

import java.io.PipedReader;
import java.io.PipedWriter;

import ca.etsmtl.log430.lab3.predicate.IPredicate;

public class EntryFilter extends Thread {
	boolean done;
	
	IPredicate<String> predicate;
	PipedReader inputPipe = new PipedReader();
	PipedWriter acceptedPipe;
	PipedWriter rejectedPipe;

	public EntryFilter(IPredicate<String> predicate, PipedWriter inputPipe,
			PipedWriter acceptedPipe, PipedWriter rejectedPipe) {

		this.predicate = predicate;

		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("EntryFilter :: connected to upstream filter.");

			// Connect outputPipe
			this.acceptedPipe = acceptedPipe;
			this.rejectedPipe = rejectedPipe;
			System.out.println("EntryFilter :: connected to downstream filters.");

		} catch (Exception Error) {

			System.out.println("EntryFilter :: Error connecting to other filters.");

		} // try/catch

	} // Constructor
	
	// This is the method that is called when the thread is started
	public void run() {

		// Declarations

		char[] characterValue = new char[1];
		// char array is required to turn char into a string
		String lineOfText = "";
		// string is required to look for the keyword
		int integerCharacter; // the integer value read from the pipe

		try {

			done = false;

			while (!done) {

				integerCharacter = inputPipe.read();
				characterValue[0] = (char) integerCharacter;

				if (integerCharacter == -1) { // pipe is closed

					done = true;

				} else {

					if (integerCharacter == '\n') { // end of line

						System.out.println("EntryFilter :: received: " + lineOfText + ".");
						lineOfText += new String(characterValue);
						boolean isAccepted = predicate.apply(lineOfText);
						if (isAccepted && acceptedPipe != null) {

							System.out.println("EntryFilter :: sending: "
									+ lineOfText + " to output accepted pipe");
							
							acceptedPipe.write(lineOfText, 0, lineOfText.length());
							acceptedPipe.flush();
						} else if(!isAccepted && rejectedPipe != null) {
							System.out.println("EntryFilter :: sending: "
									+ lineOfText + " to output accepted pipe");
							
							rejectedPipe.write(lineOfText, 0, lineOfText.length());
							rejectedPipe.flush();
						}

						lineOfText = "";

					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while

		} catch (Exception error) {

			System.out.println("EntryFilter:: Interrupted.");

		} // try/catch

		try {

			inputPipe.close();
			System.out.println("EntryFilter :: input pipe closed.");

			if(acceptedPipe != null) {
				acceptedPipe.close();
			}
			if(rejectedPipe != null) {
				rejectedPipe.close();
			}
			System.out.println("EntryFilter :: output pipe closed.");

		} catch (Exception error) {

			System.out.println("EntryFilter :: Error closing pipes.");

		} // try/catch

	} // run
}
