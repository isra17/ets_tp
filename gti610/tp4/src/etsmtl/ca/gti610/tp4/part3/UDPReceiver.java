/******************************************************
 Laboratoire #3 : Programmation d'un serveur DNS
 
 Cours :             LOG610
 Session :           Hiver 2007
 Groupe :            01
 Projet :            Laboratoire #3
 �tudiant(e)(s) :    Maxime Bouchard
 Code(s) perm. :     BOUM24028309
 
 Professeur :        Michel Lavoie 
 Nom du fichier :    UDPReceiver.java
 Date cr�e :         2007-03-10
 Date dern. modif.   X
 *******************************************************/

package etsmtl.ca.gti610.tp4.part3;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;


/**
 * Cette classe permet la r�ception d'un paquet UDP sur le port de r�ception
 * UDP/DNS. Elle analyse le paquet et extrait le hostname
 * 
 * Il s'agit d'un Thread qui �coute en permanance 
 * pour ne pas affecter le d�roulement du programme
 * @author Max
 *
 */


public class UDPReceiver extends Thread {
	/**
	 * Les champs d'un Packet UDP
	 * --------------------------
	 * En-tête (12 octects)
	 * Question : l'adresse demandé
	 * Réponse : l'adresse IP
	 * Autorité : info sur le serveur d'autorité
	 * Additionnel : information supplémentaire
	 */
	
	/**
	 * Définition de l'En-tête d'un Packet UDP
	 * ---------------------------------------
	 * Identifiant Paramètres
	 * QDcount Ancount
	 * NScount ARcount
	 * 
	 *- identifiant est un entier permettant d'identifier la requete.
	 *- parametres contient les champs suivant :
	 *	- QR (1 bit) : indique si le message est une question (0) ou une reponse (1).
	 *	- OPCODE (4 bits) : type de la requete (0000 pour une requete simple).
	 *	- AA (1 bit) : le serveur qui a fourni la reponse a-t'il autorite sur le domaine?
	 *	- TC (1 bit) : indique si le message est tronque.
	 *	- RD (1 bit) : demande d'une requete recursive.
	 *	- RA (1 bit) : indique que le serveur peut faire une demande recursive.
	 *	- UNUSED, AD, CD (1 bit chacun) : non utilises.
	 *	- RCODE (4 bits) : code de retour. 0 : OK, 1 : erreur sur le format de la requete, 2: probleme du serveur,
	 *    3 : nom de domaine non trouve (valide seulement si AA), 4 : requete non supportee, 5 : le serveur refuse
	 *    de repondre (raisons de sécurite ou autres).
	 * - QDCount : nombre de questions.
	 * - ANCount, NSCount, ARCount : nombre d'entrees dans les champs "Reponse", "Autorite", "Additionnel".
	 */
	
	/**
	 * Les champs Reponse, Autorite, Additionnel sont tous representes de la meme maniere :
	 *
	 * � Nom (16 bits) : Pour eviter de recopier la totalite du nom, on utilise des offsets. Par exemple si ce champ
	 *   vaut C0 0C, cela signifie qu�on a un offset (C0) de 12 (0C) octets. C�est-a-dire que le nom en clair se trouve
	 *   au 12eme octet du message.
	 * � Type (16 bits) : idem que pour le champ Question.
	 * � Class (16 bits) : idem que pour le champ Question.
	 * � TTL (32 bits) : dur�ee de vie de l�entr�ee.
	 * � RDLength (16 bits): nombre d�octets de la zone RDData.
	 * � RDData (RDLength octets) : reponse
	 */
	
	private DataInputStream d = null;
	protected final static int BUF_SIZE = 1024;
	protected String SERVER_DNS = null;
	protected int port = 5353;  // port de r�ception
	private String DomainName = "none";
	private AnswerRecorder cacheRecorder = null;
	private QueryFinder cacheFinder = null;
	private String adrIP = null;
	private boolean RedirectionSeulement = false;
	private String adresseIP = null;
	
	public void setport(int p) {
		this.port = p;
	}
	
	public void setRedirectionSeulement(boolean b){
		this.RedirectionSeulement = b;
	}
	
	public String gethostNameFromPacket(){
		return DomainName;
	}
	
	public String getAdrIP(){
		return adrIP;
	}
	
	private void setAdrIP(String ip){
		adrIP = ip;
	}
	
	public void sethostNameFromPacket(String hostname){
		this.DomainName = hostname;
	}
	
	public String getSERVER_DNS(){
		return SERVER_DNS;
	}
	
	public void setSERVER_DNS(String server_dns){
		this.SERVER_DNS = server_dns;
	}
	
	public void UDPReceiver(String server_dns,int Port) {
		this.SERVER_DNS = server_dns;
		this.port = Port;
	}
	
	public void setDNSFile(String filename){
		cacheRecorder = new AnswerRecorder(filename);
		cacheFinder = new QueryFinder(filename);
	}
	
	public void run(){
		
		try{
			
			//*Creation d'un socket UDP
			@SuppressWarnings("resource")
			DatagramSocket socket = new DatagramSocket(port);
			
			HashMap<Short, SocketAddress> requestMapping = new HashMap<Short, SocketAddress>();
			
			//*Boucle infinie de recpetion
			while(true){
				
				//*Reception d'un paquet UDP via le socket
				byte[] buf = new byte[BUF_SIZE];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				//*Creation d'un DataInputStream ou ByteArrayInputStream pour manipuler les bytes du paquet
				d = new DataInputStream(new ByteArrayInputStream(buf));

				//*Lecture et sauvegarde des deux premier bytes, qui specifie l'identifiant
				short id = d.readShort();
				
				//*Lecture et sauvegarde du huitieme byte, qui specifie le nombre de reponse dans le message 
				d.skip(4);
				short aNCount = d.readShort();
				
				//*Lecture du Query Domain name, a partir du 13 byte
				d.skip(4);
				StringBuilder domainBuilder = new StringBuilder();
				int n = 0;
				while((n = d.read()) > 0) {
					byte[] section = new byte[n];
					d.read(section);
					domainBuilder.append(new String(section));
					domainBuilder.append('.');						
				}
				
				domainBuilder.deleteCharAt(domainBuilder.length()-1);					 
									
				//*Sauvegarde du Query Domain name
				String name = domainBuilder.toString();
				
				//*Dans le cas d'une reponse
				if(aNCount == 1) {
					//*Passe par dessus Query Type et Query Class
					//*Passe par dessus les premiers champs du ressource record pour arriver au ressource data
					//*qui contient l'adresse IP associe au hostname (dans le fond saut de 16 bytes)
					d.skip(16);
					
					//*Capture de l'adresse IP
					byte[] ipBin = new byte[4];
					d.read(ipBin);
					InetAddress ip = InetAddress.getByAddress(ipBin);
					
					//*Ajouter la correspondance dans le fichier seulement si une seule
					//*reponse dans le message DNS (cette apllication ne traite que ce cas)
					cacheRecorder.StartRecord(name, ip.getHostAddress());
					
					//*Faire parvenir le paquet reponse au demandeur original, ayant emis une requete 
					//*avec cet identifiant
					SocketAddress src = requestMapping.get(id);
					requestMapping.remove(id);
					if(src != null) {
						packet.setSocketAddress(src);
						socket.send(packet);
					}
				}
				//*Dans le cas d'une requete
				else {
					//*Sauvegarde de l'adresse, du port et de l'identifiant de la requete
					SocketAddress src = packet.getSocketAddress();
					

					//*Si le mode est redirection seulement
					if(RedirectionSeulement) {
						requestMapping.put(id, src);
						
						//*Rediriger le paquet vers le serveur DNS
						InetSocketAddress dnsAddr = new InetSocketAddress(SERVER_DNS, 53);
						packet.setSocketAddress(dnsAddr);
						socket.send(packet);
					}
					
					//*Sinon
					else {
						//*Rechercher l'adresse IP associe au Query Domain name dans le fichier de 
						//*correspondance de ce serveur
						String ip = cacheFinder.StartResearch(name);
						
						//*Si la correspondance n'est pas trouvee
						if(ip == "none") {
							//*Rediriger le paquet vers le serveur DNS
							requestMapping.put(id, src);
							
							//*Rediriger le paquet vers le serveur DNS
							InetSocketAddress dnsAddr = new InetSocketAddress(SERVER_DNS, 53);
							packet.setSocketAddress(dnsAddr);
							socket.send(packet);
						}
					
						//*Sinon
						else {							
							//*Creer le paquet de reponse a l'aide du UDPAnswerPaquetCreator
							UDPAnswerPacketCreator answer = new UDPAnswerPacketCreator();
							byte[] data = answer.CreateAnswerPacket(buf, ip);							
							
							//*Placer ce paquet dans le socket
							DatagramPacket answerPacket = new DatagramPacket(data, data.length, src);							
							
							//*Envoyer le paquet
							socket.send(answerPacket);
						}
					}
				}
			}
		}catch(Exception e){
			System.err.println("Probl�me � l'ex�cution :");
			e.printStackTrace(System.err);
		}	
	}
}
