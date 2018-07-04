/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agbinario;

import java.util.Random;

/**
 *
 * @author Carla
 */
public class AGBinario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Individuo ind = new Individuo(3, -100.0, 100.0, 3);
//        
//        ind.criar();
//                
//        System.out.println(ind.getCromossomos());
//        
//        ind.decodificar();
//        System.out.println(ind.getDecodificacao());
//        System.out.println(ind.getVariaveis());
        
        Problema problema = new Problema();
//        problema.calcularFuncaoObjetivo(ind);
//        System.out.println(ind.getFuncaoObjetivo());

        Integer tamanho = 50; //quantidade de indivíduos de cada população
        Double pCrossover = 0.8;
        Double pMutacao = 0.05;
        Integer geracoes = 500;
        //1000 e 100
        Integer precisao = 3;
        //5, 10, 25, 50, 100
        
        Double minimo = 1.0;
        Double maximo = 64.0;
        Integer nVariaveis = 64;
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, precisao, minimo, maximo, nVariaveis);
       // Random rnd = new Random();
       // System.out.println(rnd.nextInt(65));
        ag.executar();               
    }    
}
