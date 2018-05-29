/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import metodo.EsTSP;
import problema.Problema;
import problema.ProblemaTSP;
import solucao.Individuo;

/**
 *
 * @author Carla
 */
public class EsTSPMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Problema problema = new ProblemaTSP("D:/Usuarios/Carlos/Documents/Carla/UFOP/7º Período/Computação Evolucionária/Nova pasta/instances/tsplib/berlin52.tsp");
    
        //Parametros = ES
        Integer mu = 20;
        Integer lambda = 100;
        Integer geracoes = 100;
        Double pMutacao = 0.3;
        Double pBuscaLocal = 0.5;

        EsTSP esTsp = new EsTSP(problema, mu, lambda, geracoes, pMutacao, pBuscaLocal);

        Individuo melhor = esTsp.executar();
        System.out.println(melhor);   
    }
    
}
