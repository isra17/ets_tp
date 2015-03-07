package ca.etsmtl.log430.lab3.filters;

//import ca.etsmtl.log430.lab3
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.*;

import ca.etsmtl.log430.lab3.Project;

public class ProjectFormatFilter extends Thread {

	// Declarations

	boolean done;

	List<Project> projects;
	PipedReader inputPipe = new PipedReader();
	PipedWriter outputPipe = new PipedWriter();

	public ProjectFormatFilter(PipedWriter inputPipe, PipedWriter outputPipe) {

		this.projects = new ArrayList<Project>();

		try {

			// Connect inputPipe
			this.inputPipe.connect(inputPipe);
			System.out.println("ProjectFormatFilter :: connected to upstream filter.");

			// Connect outputPipe
			this.outputPipe = outputPipe;
			System.out.println("ProjectFormatFilter :: connected to downstream filter.");

		} catch (Exception Error) {

			System.out.println("ProjectFormatFilter :: Error connecting to other filters.");

		} // try/catch

	} // Constructor

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

						System.out.println("ProjectFormatFilter :: received: " + lineOfText + ".");
						
						String tempArr[] = lineOfText.split("\\s+"); // any whitespace
						Project project = new Project();
						
						if (tempArr.length >= 7)
						{
							for (int x = 0 ; x < tempArr.length ; x++)
							{
								switch (x)
								{
									case 0 : project.setID(tempArr[x]);
										break;
									case 1 : project.setStatus(tempArr[x]);
										break;
									case 2 : project.setSystem(tempArr[x]);
										break;
									case 3: project.setVersion(tempArr[x]);
										break;
									case 4: project.setRate(tempArr[x]);
										break;
									case 5: project.setState(tempArr[x]);
										break;
									case 6: project.setDescription(project.getDescription() + tempArr[x] + " ");
										break;
								}
							}
							projects.add(project);
							lineOfText = "";
							
						}
						else
						{
							System.out.println("Error : Project file not in a good format.");
						}
						
					} else {

						lineOfText += new String(characterValue);

					} // if //

				} // if

			} // while
			
			Collections.sort(projects);
			
			if (!projects.isEmpty()) {
				for(int x = 0; x < projects.size(); x++)
				{
					Project project = projects.get(x);
					String text = project.GetProjectFormatString();
					//projects.
					System.out.println("ProjectFormatFilter:: sending: "
							+ text + " to output pipe.");
					//lineOfText += new String(characterValue);
					outputPipe.write(text, 0, text.length());
					outputPipe.flush();
				}
			} // if
			else
			{
				System.out.println("ProjectFormatFilter:: no projects.");
			}

			lineOfText = "";

		} catch (Exception error) {

			System.out.println("ProjectFormatFilter:: Interrupted.");

		} // try/catch

		try {

			inputPipe.close();
			System.out.println("ProjectFormatFilter :: input pipe closed.");

			outputPipe.close();
			System.out.println("ProjectFormatFilter :: output pipe closed.");

		} catch (Exception error) {

			System.out.println("ProjectFormatFilter :: Error closing pipes.");

		} // try/catch

	} // run

} // class