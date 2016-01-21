package org.ulco.introspection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mistermagikarp on 21/01/16.
 */
public class SousClass {

    Class lesClasses;

    public SousClass(String lesClasses) throws ClassNotFoundException {
        this.lesClasses = Class.forName(lesClasses);
    }



    public static List<String> listerFichier(File rep){
        List<Class> SousClasse = new ArrayList<>();

    String[] fichier = rep.list();
        List<String> listeFichier = new ArrayList<>();
      for(String f : fichier) {
          if (f.contains(".java")) {
              String str[] = f.split("\\.");
              listeFichier.add(str[0]);
              //System.out.println(str[0]);
          }
      }
        return listeFichier;
    }

    private static List<Class> creationListe(List<String> rep) throws ClassNotFoundException {
        List<Class> SousClasse = new ArrayList<>();

        for (String s : rep) {
            SousClass sc = new SousClass("org.ulco." + s);
            if(sc.lesClasses.getSuperclass().toString().equals("class org.ulco.GraphicsObject")){
                SousClasse.add(sc.lesClasses);

            }
        }
        return SousClasse;
    }

    public static void main(String[] args) throws ClassNotFoundException{
        List<String> rep = listerFichier(new File("./src/org/ulco"));
        List<Class> sousClasse = creationListe(rep);

        for (Class cl: sousClasse){
            System.out.println("GraphicsObject: " + cl.getName());
        }
    }


}
