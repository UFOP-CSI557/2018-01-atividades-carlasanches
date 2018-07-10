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
    
    public final static int REPETICOES = 30;
    
    public static void main(String[] args) {
        // TODO code application logic here
                
        Problema problema = new Problema();

        Integer tamanho = 50; //quantidade de indivíduos de cada população
        Double pCrossover = 0.8;
        Double pMutacao = 0.03;
        Integer geracoes = 100;
        Integer precisao = 3;
        
        Integer minimo = 0;
        Integer maximo = 7;
        Integer nVariaveis = 64;
        
         //Casos de teste
        //1 - Variar mutação; 2 - variar crossover
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("TESTE1", "TESTE2"));
        
        for(int i = 0; i < REPETICOES; i++){
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);
            
            for(int c = 0; c < casos.size(); c++){
                
                AlgoritmoGenetico ag;
                Individuo result;
                long startTime = System.currentTimeMillis();
                int teste = casos.get(c);
                
                switch(teste){
                    case 1:
                      // tamanho = 100;
                    break;
                        
                    case 2: 
                       // tamanho = 100;
                    break;
                }
                
                ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, precisao, minimo, maximo, nVariaveis);
                result = ag.executar();
                
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println(nomes.get(teste-1) + ';' + "RESULTADO" + ';' + result.getFuncaoObjetivo() + ';' + "TEMPO" 
                                + ';' + totalTime + ';' + "REPETICAO" + ';' + (i+1));
            }
        }
        
        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, precisao, minimo, maximo, nVariaveis);
        ag.executar();               
    }    
}
