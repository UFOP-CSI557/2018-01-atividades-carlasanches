/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import metodo.DEReal;
import problema.Problema;
import problema.ProblemaDeJong;
import solucao.Individuo;

/**
 *
 * @author Carla
 */
public class DERealPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Double minimo = -100.0;
        Double maximo = 100.0;
        
        int D = 100;
        Problema problema = new ProblemaDeJong(D);
        
        int gmax = 100;
        int Np = 3;
        
        double F = 0.001;
        double Cr = 0.9;
        
        DEReal deReal = new DEReal(minimo, maximo, problema, gmax, D, Np, F, Cr);
        
        Individuo resultado = deReal.executar();
        System.out.println(resultado);
    }
    
}
