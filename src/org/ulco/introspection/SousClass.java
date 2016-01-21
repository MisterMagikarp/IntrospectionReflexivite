package org.ulco.introspection;

import org.ulco.GraphicsObject;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mistermagikarp on 21/01/16.
 */
public class SousClass {

    static String pack = "org.ulco.";
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

    public static List<Method> classeAbtraite(String nom) throws ClassNotFoundException {

        String nom2 = pack + nom;
        Class classe = Class.forName(nom2);
        List<Method> methode = new ArrayList<>();

        if(Modifier.isAbstract(classe.getModifiers())){
            Method[] champs = classe.getMethods();
            for(Method m : champs){
                if(Modifier.isAbstract((m.getModifiers()))) {
                   // System.out.println("\t" + m.getName());
                    methode.add(m);
                }
            }

            }
        else {
            System.out.println("La classe n'est pas abstraite");
        }
        return methode;
    }

    public static boolean complet(String nomClasse) throws ClassNotFoundException{

        List<Method> methodesClassesAbstraites = classeAbtraite(nomClasse);

        Class classeAVerifier = Class.forName(pack + "Triangle");
        Method[] methodes = classeAVerifier.getMethods();

        List<String> nomsMethodes = new ArrayList<>();
        for(Method m : methodesClassesAbstraites){
            nomsMethodes.add(m.getName());
        }
        System.out.println("---");

        List<String> methodeAutreFonction = new ArrayList<>();
        for(Method m : methodes){
            methodeAutreFonction.add(m.getName());
        }

        boolean complet = true;
        for(String s : methodeAutreFonction){
            if (methodeAutreFonction.indexOf(s) == -1){
                complet = false;
            }
        }

        return complet;
    }

    public static void exo4(List<String> nomClasse) throws ClassNotFoundException {
        List<Class> sousClasses = SousClass.creationListe(nomClasse);

        for(Class c : sousClasses) {
            Constructor[] constructors = c.getConstructors();
            for (Constructor cons : constructors) {
                System.out.println(cons);

                Parameter[] parameters = cons.getParameters();
                for (Parameter p : parameters) {
                    System.out.println(p);

                }
            }


        }



    }

    public static void main(String[] args) throws ClassNotFoundException{
        List<String> rep = listerFichier(new File("./src/org/ulco"));
        List<Class> sousClasse = creationListe(rep);

        for (Class cl: sousClasse){
            System.out.println("GraphicsObject: " + cl.getName());
        }

        List<Method> Methode = classeAbtraite("GraphicsObject");
        for (Method m : Methode){
            System.out.println(m.getName());
        }

        boolean res = complet("GraphicsObject");

                if(res){
                    System.out.println("Toutes les méthodes de la classe mère sont implémentées dans la classe fille");
                }

        exo4(rep);

    }


}
