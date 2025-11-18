package Menu;

import Reseau.Reseau;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reseau r = new Reseau("r",10);
        Menu1.menu(sc,r);
        Menu2.menu(sc,r);
        sc.close();
    }
}
