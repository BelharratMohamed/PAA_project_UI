package factory;

import reseau.Reseau;
import reseau.Maison;
import reseau.Generateur;

import java.io.*;
import java.util.Map;

public class ReseauFactory {

    private ReseauFactory(){}

    /**
     * Permet de parser un reseau depuis un fichier .txt
     * @param penalite du reseau
     * @param fichier chemin du fichier
     * @return le reseau r
     * @throws Exception
     */
    public static Reseau parserReseau(double penalite, String fichier) throws Exception {
        Reseau r;
        boolean m = false; // passage au maison
        boolean c = false; // passage au connexion
        r = new Reseau("r",penalite);
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int compteur = 0;

            while((ligne = br.readLine()) != null) {
                compteur++;
                switch (ligne.split("\\(")[0]) {
                    case "generateur": {
                        if (m || c) {
                            throw new Exception("Erreur ligne " + compteur + " : mauvais ordre.");
                        }
                        try {
                            String[] data = recupererDonnees(ligne);
                            if (Integer.parseInt(data[1]) > 0) {
                                r.addGenerateur(data[0], Integer.parseInt(data[1]));
                            } else {
                                throw new Exception("Erreur ligne " + compteur + " : la charge du générateur doit être un entier supérieur à 0");
                            }
                        } catch (Exception e) {
                            System.out.println("Erreur ligne " + compteur + " : " + e.getMessage());
                            System.exit(1);
                        }
                        break;
                    }
                    case "maison": {
                        if (c) {
                            throw new Exception("Erreur ligne " + compteur + " : mauvais ordre.");
                        }
                        m = true;
                        try {
                            String[] data = recupererDonnees(ligne);
                            if (consoValide(data[1].toUpperCase())) {
                                try {
                                    r.addMaison(data[0], data[1].toUpperCase());
                                } catch (Exception e) {
                                    System.out.println("Erreur ligne " + compteur + " : " + e.getMessage());
                                    System.exit(1);
                                }
                            } else {
                                throw new Exception("Erreur ligne " + compteur + " : la consommation doit être basse, normal ou forte.");
                            }
                        }catch (Exception e) {
                            System.out.println("Erreur ligne " + compteur + " : " + e.getMessage());
                            System.exit(1);
                        }
                        break;
                    }
                    case "connexion": {
                        c = true;
                        try {
                            String[] data = recupererDonnees(ligne);
                            if (r.maisonDansReseau(data[0]) && r.generateurDansReseau(data[1])) {
                                r.addConnexion(data[0], data[1]);
                            } else if (r.maisonDansReseau(data[1]) && r.generateurDansReseau(data[0])) {
                                r.addConnexion(data[1], data[0]);
                            } else {
                                throw new Exception("Erreur ligne " + compteur + " : Erreur connexion.");
                            }
                        }catch (Exception e) {
                            System.out.println("Erreur ligne " + compteur + " : " + e.getMessage());
                            System.exit(1);
                        }
                        break;
                    }
                    default: {
                        throw new Exception("Erreur ligne " + compteur + " : la ligne doit commencer par generateur, maison ou connexion.");
                    }
                }
            }
        }catch (FileNotFoundException e) {
            throw e;
        }
        return r;
    }

    private static String[] recupererDonnees(String s) throws Exception {
        String[] donnees = new String[2];
        if(s.charAt(s.length()-1) != '.') {
            throw new Exception("Format incorrect");
        }
        s = s.split("\\(")[1];
        s = s.split("\\)")[0];
        donnees = s.split(",");
        return donnees;
    }

    private static boolean consoValide(String s){
        String[] conso = {"BASSE","NORMAL","FORTE"};
        for (String c : conso){
            if (c.equals(s)){
                return true;
            }
        }
        return false;
    }

    public static void reseauToFile(String fichier, Reseau r) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichier))){
            for (Generateur g : r.getGenerateurs()){
                bw.write(g.toString());
                bw.newLine();
            }
            for (Maison m : r.getMaisons()){
                bw.write(m.toString());
                bw.newLine();
            }
            for(Map.Entry<Maison,Generateur> connexion : r.getConnexions().entrySet()){
                bw.write("connexion(" +connexion.getKey().getNom()+","+connexion.getValue().getNom()+").");
                bw.newLine();
            }
            bw.flush();
        }
    }

}
