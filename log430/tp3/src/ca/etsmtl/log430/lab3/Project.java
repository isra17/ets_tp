package ca.etsmtl.log430.lab3;


public class Project implements Comparable<Project> {
		/**
		 * Project ID
		 */
		private String id;

		private String status;
		
		private String system;
		
		private String version;
		
		private String rate;
		
		private String state;
		
		private String description = "";
	
		public void setID(String id) {
			this.id = id;
		}

		public String getID() {
			return id;
		}
		
		public void setStatus(String status){
			this.status = status;
		}
		
		public String getStatus(){
			return status;
		}

		public void setSystem(String system) {
			this.system = system;
		}

		public String getSystem() {
			return system;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getVersion() {
			return version;
		}
		
		public void setRate(String rate) {
			this.rate = rate;
		}

		public String getRate() {
			return rate;
		}
		
		public void setState(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
		
		//Statut, �tat, taux, no de projet
		public String GetProjectFormatString()
		{
			return this.status + " " + this.state + " " + this.rate + " " + this.id + "\n";
		}
		
		@Override
		public int compareTo(Project obj) {
			Project project = (Project)obj;
			return this.state.compareTo(project.state);
		}

	} // Project class
