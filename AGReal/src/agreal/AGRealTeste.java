/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agreal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Carla
 */
public class AGRealTeste {
    
    public final static int REPETICOES = 30;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
               
        Problema problema = new Problema();
        
        //Parâmetros propostos
        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Integer geracoes = 300;
        
        //Parâmetros não modificados
        Double pCrossover = 0.013;
        Double pMutacao = 0.001;
        
        //Casos de teste
        //1 - Variar mutação; 2 - variar crossover
        ArrayList<String> nomes = new ArrayList<>(Arrays.asList("REAL1", "REAL2"));
        
        for(int i = 0; i < REPETICOES; i++){
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);
            
            for(int c = 0; c < casos.size(); c++){
                
                Integer tamanho = 300;
                
                AlgoritmoGenetico ag;
                
                ArrayList<Individuo> result;
                
                long startTime = System.currentTimeMillis();
                
                int teste = casos.get(c);
                
                switch(teste){
                    case 1:
                       tamanho = 450;
                    break;
                        
                    case 2: 
                        tamanho = 600;
                    break;
                }
                
                ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);
                result = ag.executar();
                                
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println(nomes.get(teste-1) + ';' + "RESULTADO" + ';' + result.get(0).getFuncaoObjetivo() + ';' + "TEMPO" 
                                + ';' + totalTime + ';' + "REPETICAO" + ';' + (i+1));
            }                         
        }
    }    
}
