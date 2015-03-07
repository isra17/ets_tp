package ca.etsmtl.log660.tp1;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class LectureBD {
    public class Role {
        public Role(int i, String n, String p) {
            id = i;
            nom = n;
            personnage = p;
        }
        protected int id;
        protected String nom;
        protected String personnage;
    }

    public LectureBD() {
        connectionBD();
        try {
            // Prepare les requetes d'insertions
            participantStmt = db_connection.prepareStatement("INSERT INTO Participants(" +
                    "idParticipant, nom, prenom, dateNaissance, lieuNaissance) VALUES(?,?,?,?,?)");
            bioStmt = db_connection.prepareStatement("INSERT INTO Biographies(idParticipant, biographie)" +
                    " VALUES(?,?)");
            filmStmt = db_connection.prepareStatement("INSERT INTO Films(" +
                    "idFilm, titre, anneeSortie, langue, duree, resume)" +
                    " VALUES(?,?,?,?,?,?)");
            filmPaysStmt = db_connection.prepareStatement("INSERT INTO FilmPays(idFilm, idPays) VALUES(?,?)");
            filmGenreStmt = db_connection.prepareStatement("INSERT INTO FilmGenres(idFilm, idGenre) VALUES(?,?)");
            filmScenaristeStmt = db_connection.prepareStatement("INSERT INTO FilmScenaristes(idFilm, idScenariste) VALUES(?,?)");
            filmParticipantStmt = db_connection.prepareStatement("INSERT INTO FilmParticipants(idFilm, idParticipant) VALUES(?,?)");
            roleStmt = db_connection.prepareStatement("INSERT INTO Roles(idFilm, idParticipant, personnage) VALUES(?,?,?)");
            copieStmt = db_connection.prepareStatement("INSERT INTO CopieFilms(code, idFilm) VALUES(?,?)");

            personneStmt = db_connection.prepareStatement("INSERT INTO Personnes(idPersonne, nom, prenom, telephone, dateNaissance," +
                    "courriel, motDePasse, adresse, ville, province, codePostal)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            clientStmt = db_connection.prepareStatement("INSERT INTO Clients(idClient, ccType, ccNumero, ccExpiration, ccCVV)" +
                    " VALUES(?,?,?,?,?)");
            abonnementStmt = db_connection.prepareStatement("INSERT INTO Abonnements(idForfait, idClient, dateDebut) VALUES(?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Commit les batch de requetes
    public void commit() {
        try {
            participantStmt.executeBatch();
            bioStmt.executeBatch();
            filmStmt.executeBatch();
            filmPaysStmt.executeBatch();
            filmGenreStmt.executeBatch();
            filmScenaristeStmt.executeBatch();
            filmParticipantStmt.executeBatch();
            roleStmt.executeBatch();
            copieStmt.executeBatch();
            personneStmt.executeBatch();
            clientStmt.executeBatch();
            abonnementStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ferme les connections a la DB
    public void close() {
        try {
            participantStmt.close();
            bioStmt.close();
            filmStmt.close();
            filmPaysStmt.close();
            filmGenreStmt.close();
            filmScenaristeStmt.close();
            filmParticipantStmt.close();
            roleStmt.close();
            copieStmt.close();
            personneStmt.close();
            clientStmt.close();
            abonnementStmt.close();

            db_connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void lecturePersonnes(String nomFichier){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            InputStream is = new FileInputStream(nomFichier);
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            String tag = null,
                    nom = null,
                    anniversaire = null,
                    lieu = null,
                    photo = null,
                    bio = null;

            int id = -1;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    tag = parser.getName();

                    if (tag.equals("personne") && parser.getAttributeCount() == 1)
                        id = Integer.parseInt(parser.getAttributeValue(0));
                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    tag = null;

                    if (parser.getName().equals("personne") && id >= 0)
                    {
                        insertionPersonne(id,nom,anniversaire,lieu,photo,bio);

                        id = -1;
                        nom = null;
                        anniversaire = null;
                        lieu = null;
                        photo = null;
                        bio = null;
                    }
                }
                else if (eventType == XmlPullParser.TEXT && id >= 0)
                {
                    if (tag != null)
                    {
                        if (tag.equals("nom"))
                            nom = parser.getText();
                        else if (tag.equals("anniversaire"))
                            anniversaire = parser.getText();
                        else if (tag.equals("lieu"))
                            lieu = parser.getText();
                        else if (tag.equals("photo"))
                            photo = parser.getText();
                        else if (tag.equals("bio"))
                            bio = parser.getText();
                    }
                }

                eventType = parser.next();
            }
        }
        catch (XmlPullParserException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println("IOException while parsing " + nomFichier);
        }
    }

    public void lectureFilms(String nomFichier){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            InputStream is = new FileInputStream(nomFichier);
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            String tag = null,
                    titre = null,
                    langue = null,
                    poster = null,
                    roleNom = null,
                    rolePersonnage = null,
                    realisateurNom = null,
                    resume = null;

            ArrayList<String> pays = new ArrayList<String>();
            ArrayList<String> genres = new ArrayList<String>();
            ArrayList<String> scenaristes = new ArrayList<String>();
            ArrayList<Role> roles = new ArrayList<Role>();
            ArrayList<String> annonces = new ArrayList<String>();

            int id = -1,
                    annee = -1,
                    duree = -1,
                    roleId = -1,
                    realisateurId = -1;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    tag = parser.getName();

                    if (tag.equals("film") && parser.getAttributeCount() == 1)
                        id = Integer.parseInt(parser.getAttributeValue(0));
                    else if (tag.equals("realisateur") && parser.getAttributeCount() == 1)
                        realisateurId = Integer.parseInt(parser.getAttributeValue(0));
                    else if (tag.equals("acteur") && parser.getAttributeCount() == 1)
                        roleId = Integer.parseInt(parser.getAttributeValue(0));
                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    tag = null;

                    if (parser.getName().equals("film") && id >= 0)
                    {
                        insertionFilm(id,titre,annee,pays,langue,
                                duree,resume,genres,realisateurNom,
                                realisateurId, scenaristes,
                                roles,poster,annonces);

                        id = -1;
                        annee = -1;
                        duree = -1;
                        titre = null;
                        langue = null;
                        poster = null;
                        resume = null;
                        realisateurNom = null;
                        roleNom = null;
                        rolePersonnage = null;
                        realisateurId = -1;
                        roleId = -1;

                        genres.clear();
                        scenaristes.clear();
                        roles.clear();
                        annonces.clear();
                        pays.clear();
                    }
                    if (parser.getName().equals("role") && roleId >= 0)
                    {
                        roles.add(new Role(roleId, roleNom, rolePersonnage));
                        roleId = -1;
                        roleNom = null;
                        rolePersonnage = null;
                    }
                }
                else if (eventType == XmlPullParser.TEXT && id >= 0)
                {
                    if (tag != null)
                    {
                        if (tag.equals("titre"))
                            titre = parser.getText();
                        else if (tag.equals("annee"))
                            annee = Integer.parseInt(parser.getText());
                        else if (tag.equals("pays"))
                            pays.add(parser.getText());
                        else if (tag.equals("langue"))
                            langue = parser.getText();
                        else if (tag.equals("duree"))
                            duree = Integer.parseInt(parser.getText());
                        else if (tag.equals("resume"))
                            resume = parser.getText();
                        else if (tag.equals("genre"))
                            genres.add(parser.getText());
                        else if (tag.equals("realisateur"))
                            realisateurNom = parser.getText();
                        else if (tag.equals("scenariste"))
                            scenaristes.add(parser.getText());
                        else if (tag.equals("acteur"))
                            roleNom = parser.getText();
                        else if (tag.equals("personnage"))
                            rolePersonnage = parser.getText();
                        else if (tag.equals("poster"))
                            poster = parser.getText();
                        else if (tag.equals("annonce"))
                            annonces.add(parser.getText());
                    }
                }

                eventType = parser.next();
            }
        }
        catch (XmlPullParserException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println("IOException while parsing " + nomFichier);
        }
    }

    public void lectureClients(String nomFichier){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            InputStream is = new FileInputStream(nomFichier);
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            String tag = null,
                    nomFamille = null,
                    prenom = null,
                    courriel = null,
                    tel = null,
                    anniv = null,
                    adresse = null,
                    ville = null,
                    province = null,
                    codePostal = null,
                    carte = null,
                    noCarte = null,
                    motDePasse = null,
                    forfait = null;

            int id = -1,
                    expMois = -1,
                    expAnnee = -1;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    tag = parser.getName();

                    if (tag.equals("client") && parser.getAttributeCount() == 1)
                        id = Integer.parseInt(parser.getAttributeValue(0));
                }
                else if (eventType == XmlPullParser.END_TAG)
                {
                    tag = null;

                    if (parser.getName().equals("client") && id >= 0)
                    {
                        insertionClient(id,nomFamille,prenom,courriel,tel,
                                anniv,adresse,ville,province,
                                codePostal,carte,noCarte,
                                expMois,expAnnee,motDePasse,forfait);

                        nomFamille = null;
                        prenom = null;
                        courriel = null;
                        tel = null;
                        anniv = null;
                        adresse = null;
                        ville = null;
                        province = null;
                        codePostal = null;
                        carte = null;
                        noCarte = null;
                        motDePasse = null;
                        forfait = null;

                        id = -1;
                        expMois = -1;
                        expAnnee = -1;
                    }
                }
                else if (eventType == XmlPullParser.TEXT && id >= 0)
                {
                    if (tag != null)
                    {
                        if (tag.equals("nom-famille"))
                            nomFamille = parser.getText();
                        else if (tag.equals("prenom"))
                            prenom = parser.getText();
                        else if (tag.equals("courriel"))
                            courriel = parser.getText();
                        else if (tag.equals("tel"))
                            tel = parser.getText();
                        else if (tag.equals("anniversaire"))
                            anniv = parser.getText();
                        else if (tag.equals("adresse"))
                            adresse = parser.getText();
                        else if (tag.equals("ville"))
                            ville = parser.getText();
                        else if (tag.equals("province"))
                            province = parser.getText();
                        else if (tag.equals("code-postal"))
                            codePostal = parser.getText();
                        else if (tag.equals("carte"))
                            carte = parser.getText();
                        else if (tag.equals("no"))
                            noCarte = parser.getText();
                        else if (tag.equals("exp-mois"))
                            expMois = Integer.parseInt(parser.getText());
                        else if (tag.equals("exp-annee"))
                            expAnnee = Integer.parseInt(parser.getText());
                        else if (tag.equals("mot-de-passe"))
                            motDePasse = parser.getText();
                        else if (tag.equals("forfait"))
                            forfait = parser.getText();
                    }
                }

                eventType = parser.next();
            }
        }
        catch (XmlPullParserException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println("IOException while parsing " + nomFichier);
        }
    }

    private void insertionPersonne(int id, String nom, String anniv, String lieu, String photo, String bio) {

        try {

            // Insere l'entrée Participants
            participantStmt.setInt(1, id);

            String[] splittedNom = nom.split(" ", 2);
            String prenom = splittedNom[0];
            String nomFamille = "";
            if(splittedNom.length > 1) {
                nomFamille = splittedNom[1];
            }
            participantStmt.setNString(2, nomFamille);
            participantStmt.setNString(3, prenom);
            java.sql.Date annivDate = null;
            if(anniv != null) {
                annivDate = new java.sql.Date(date_formatter.parse(anniv).getTime());
            }
            participantStmt.setDate(4, annivDate);
            participantStmt.setNString(5, lieu);

            participantStmt.addBatch();

            // Insère la biographie
            if(bio != null) {

                bioStmt.setInt(1, id);
                bioStmt.setString(2, bio);
                bioStmt.addBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void insertionFilm(int id, String titre, int annee,
                               ArrayList<String> pays, String langue, int duree, String resume,
                               ArrayList<String> genres, String realisateurNom, int realisateurId,
                               ArrayList<String> scenaristes,
                               ArrayList<Role> roles, String poster,
                               ArrayList<String> annonces) {
        try {
            // Insère le film
            filmStmt.setInt(1, id);
            filmStmt.setNString(2, titre);
            filmStmt.setInt(3, annee);
            filmStmt.setNString(4, langue);
            filmStmt.setInt(5, duree);
            filmStmt.setNString(6, resume);
            filmStmt.addBatch();

            // Insère les pays du film
            for(String filmPays : pays) {
                int paysId = getPaysId(filmPays);
                filmPaysStmt.setInt(1, id);
                filmPaysStmt.setInt(2, paysId);
                filmPaysStmt.addBatch();
            }

            // Insère les genre du film
            for(String filmGenre : genres) {
                int genreId = getGenreId(filmGenre);
                filmGenreStmt.setInt(1, id);
                filmGenreStmt.setInt(2, genreId);
                filmGenreStmt.addBatch();
            }

            // Insère les scénariste du film
            for(String filmScenariste : scenaristes) {
                int scenaristeId = getScenaristeId(filmScenariste);
                filmScenaristeStmt.setInt(1, id);
                filmScenaristeStmt.setInt(2, scenaristeId);
                filmScenaristeStmt.addBatch();
            }

            // Assigne le réalisateur du film
            if(realisateurId > 0) {
                filmParticipantStmt.setInt(1, id);
                filmParticipantStmt.setInt(2, realisateurId);
                filmParticipantStmt.addBatch();
            }

            // Assigne les rôles du film
            for(Role role : roles) {
                roleStmt.setInt(1, id);
                roleStmt.setInt(2, role.id);
                roleStmt.setNString(3, role.personnage);
                roleStmt.addBatch();
            }

            // Crée les copies du film
            int copieCount = random.nextInt(100) + 1;
            for(int i=0; i < copieCount; i++) {
                copieStmt.setNString(1, id + " " + i);
                copieStmt.setInt(2, id);
                copieStmt.addBatch();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertionClient(int id, String nomFamille, String prenom,
                                 String courriel, String tel, String anniv,
                                 String adresse, String ville, String province,
                                 String codePostal, String carte, String noCarte,
                                 int expMois, int expAnnee, String motDePasse,
                                 String forfait) {

        try {
            // Crée l'enregistrement Personne
            personneStmt.setInt(1, id);
            personneStmt.setNString(2, nomFamille);
            personneStmt.setNString(3, prenom);
            personneStmt.setNString(4, tel);
            personneStmt.setDate(5, new java.sql.Date(date_formatter.parse(anniv).getTime()));
            personneStmt.setNString(6, courriel);
            //Hashing is cool, but sloooooow...
            //personneStmt.setString(7, BCrypt.hashpw(motDePasse, BCrypt.gensalt(12)));
            personneStmt.setString(7, motDePasse);
            personneStmt.setNString(8, adresse);
            personneStmt.setNString(9, ville);
            personneStmt.setNString(10, province);
            personneStmt.setNString(11, codePostal.replaceAll("\\s", ""));
            personneStmt.addBatch();

            // Ajoute les informations du client
            clientStmt.setInt(1, id);
            clientStmt.setString(2, carte);
            clientStmt.setString(3, noCarte.replaceAll("\\s",""));
            clientStmt.setDate(4, new java.sql.Date(card_formatter.parse(expAnnee + "-" + expMois).getTime()));
            clientStmt.setInt(5, 111);
            clientStmt.addBatch();

            // Assigne l'abonnement
            abonnementStmt.setInt(1, getForfaitId(forfait));
            abonnementStmt.setInt(2, id);
            abonnementStmt.setDate(3, new java.sql.Date(new Date().getTime()));
            abonnementStmt.addBatch();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Crée ou retourne l'id d'un forfait à partir de son nom
    private int getForfaitId(String nom) {
        if(forfaits_cache.containsKey(nom)) return forfaits_cache.get(nom);
        PreparedStatement stmt = null;
        try {
            stmt = db_connection.prepareStatement("INSERT INTO Forfaits(locationMax, dureeMax, nom, cout) VALUES(?,?,?,?)", new String[]{"idForfait"});
            stmt.setInt(1, nom.charAt(0));
            stmt.setInt(2, 14);
            stmt.setNString(3, nom);
            stmt.setInt(4, nom.charAt(0)+20);
            stmt.execute();
            ResultSet key = stmt.getGeneratedKeys();
            key.next();
            int id = key.getInt(1);
            forfaits_cache.put(nom, id);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    // Crée ou retourne l'id d'un pays à partir de son nom
    private int getPaysId(String pays) {
        if(pays_cache.containsKey(pays)) return pays_cache.get(pays);
        PreparedStatement stmt = null;
        try {
            stmt = db_connection.prepareStatement("INSERT INTO Pays(nom) VALUES(?)", new String[]{"idPays"});
            stmt.setNString(1, pays);
            stmt.execute();
            ResultSet key = stmt.getGeneratedKeys();
            key.next();
            int id = key.getInt(1);
            pays_cache.put(pays, id);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    // Crée ou retourne l'id d'un scénariste à partir de son nom
    private int getScenaristeId(String scenariste) {
        if(scenaristes_cache.containsKey(scenariste)) return scenaristes_cache.get(scenariste);
        PreparedStatement stmt = null;
        try {
            stmt = db_connection.prepareStatement("INSERT INTO Scenaristes(nom) VALUES(?)", new String[]{"idScenariste"});
            stmt.setNString(1, scenariste);
            stmt.execute();
            ResultSet key = stmt.getGeneratedKeys();
            key.next();
            int id = key.getInt(1);
            scenaristes_cache.put(scenariste, id);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    // Crée ou retourne l'id d'un genre à partir de son nom
    private int getGenreId(String genre) {
        if(genres_cache.containsKey(genre)) return genres_cache.get(genre);
        PreparedStatement stmt = null;
        try {
            stmt = db_connection.prepareStatement("INSERT INTO Genres(titre) VALUES(?)", new String[]{"idGenre"});
            stmt.setNString(1, genre);
            stmt.execute();
            ResultSet key = stmt.getGeneratedKeys();
            key.next();
            int id = key.getInt(1);
            genres_cache.put(genre, id);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    // Connection à la BDD
    private void connectionBD() {
        try {
            this.db_connection = DriverManager.getConnection("jdbc:oracle:thin:@big-data-3.logti.etsmtl.ca:1521:LOG660", "equipe27", "vfF1SIbJ");
        } catch (SQLException e) {
            System.err.println("Cannot connect to database!: " + e.getMessage());
            System.exit(1);
        }
    }

    private Connection db_connection;
    // Format de date pour les anniversaires
    private SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
    // Format de date pour les expiration de carte de crédit
    private SimpleDateFormat card_formatter = new SimpleDateFormat("yyyy-MM");

    Random random = new Random();

    // Requêtes préparées
    PreparedStatement participantStmt = null;
    PreparedStatement bioStmt = null;
    PreparedStatement filmStmt = null;
    PreparedStatement filmPaysStmt = null;
    PreparedStatement filmGenreStmt = null;
    PreparedStatement filmScenaristeStmt = null;
    PreparedStatement filmParticipantStmt = null;
    PreparedStatement roleStmt = null;
    PreparedStatement copieStmt = null;
    PreparedStatement personneStmt = null;
    PreparedStatement clientStmt = null;
    PreparedStatement abonnementStmt = null;

    // Caches des enregistrements créée
    Map<String, Integer> pays_cache = new HashMap<String, Integer>();
    Map<String, Integer> genres_cache = new HashMap<String, Integer>();
    Map<String, Integer> scenaristes_cache = new HashMap<String, Integer>();
    Map<String, Integer> forfaits_cache = new HashMap<String, Integer>();

    public static void main(String[] args) {
        LectureBD lecture = new LectureBD();

        // Reset la BDD
        lecture.clearAll();

        // Importe les données
        lecture.lecturePersonnes(args[0]);
        lecture.lectureFilms(args[1]);
        lecture.lectureClients(args[2]);

        // Commit le tout
        lecture.commit();
        // Ferme la connection
        lecture.close();

        System.out.println("Done...");
    }

    // Vide la base de données
    private void clearAll() {
        try {
            Statement statement = db_connection.createStatement();
            statement.execute("DELETE FROM Abonnements");
            statement.execute("DELETE FROM Forfaits");
            statement.execute("DELETE FROM Clients");
            statement.execute("DELETE FROM Personnes");

            statement.execute("DELETE FROM FilmPays");
            statement.execute("DELETE FROM FilmGenres");
            statement.execute("DELETE FROM FilmScenaristes");
            statement.execute("DELETE FROM Pays");
            statement.execute("DELETE FROM Genres");
            statement.execute("DELETE FROM Scenaristes");
            statement.execute("DELETE FROM FilmParticipants");
            statement.execute("DELETE FROM Roles");
            statement.execute("DELETE FROM CopieFilms");
            statement.execute("DELETE FROM Biographies");
            statement.execute("DELETE FROM Participants");
            statement.execute("DELETE FROM Films");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
