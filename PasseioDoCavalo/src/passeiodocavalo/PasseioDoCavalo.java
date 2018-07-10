/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passeiodocavalo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class PasseioDoCavalo {

    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) {
        // TODO code application logic here
                
        Problema problema = new Problema();

        Integer tamanho = 50; //quantidade de indivíduos de cada população
        Double pCrossover = 0.8;
        Double pMutacao = 0.03;
        Integer geracoes = 500;
        Integer precisao = 3;
        
        Integer minimo = 0;
        Integer maximo = 7;
        Integer nVariaveis = 64;
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, precisao, minimo, maximo, nVariaveis);
        ag.executar();               
    }    
}
