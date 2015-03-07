Le projet java permettant d'importer les données est disponible dans le dossier "import_donnee". Simplement compiler et executé le fichier LectureBD.java.

#### Compiler
```
javac -d bin -classpath lib/jbcrypt-0.3m.jar:lib/ojdbc6.jar:lib/xmlpull_1_1_3_4c.jar:lib/xpp3-1.1.3.4.C.jar src/ca/etsmtl/log660/tp1/LectureBD.java
```

#### Exécuter
```
java -classpath lib/jbcrypt-0.3m.jar:lib/ojdbc6.jar:lib/xmlpull_1_1_3_4c.jar:lib/xpp3-1.1.3.4.C.jar:bin -Xmx512M ca.etsmtl.log660.tp1.LectureBD data/personnes_latin1.xml data/films_latin1.xml data/clients_latin1.xml
```
