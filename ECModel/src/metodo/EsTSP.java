/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo;

import java.util.Collections;
import java.util.Random;
import operacoes.BuscaLocalCombinatorio;
import problema.Problema;
import solucao.Individuo;
import solucao.IndividuoBinario;
import solucao.IndividuoInteger;
import solucao.PopulacaoInteger;

/**
 *
 * @author Carla
 */
public class EsTSP implements Metodo{
    
    //Parametros - TSP
    private Problema problema;
    
    //Parametros - ES
    private Integer mu;
    private Integer lambda;
    private Integer geracoes;
    private Double pMutacao;
    private Double pBuscaLocal; // individuos/descendentes que serão submetidos ao processo de busca local

    public EsTSP(Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao, Double pBuscaLocal) {
        this.problema = problema;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
        this.pBuscaLocal = pBuscaLocal;
    }

    @Override
    public Individuo executar() {
        
        PopulacaoInteger populacao = new PopulacaoInteger((this.mu), this.problema);
        populacao.criar();
        
        //Avaliar
        populacao.avaliar();
        
        PopulacaoInteger descendentes = new PopulacaoInteger(this.mu, this.problema);
        
        Random rnd = new Random();
        
        //Geracoes - criterio de parada
        for(int g = 1; g <= this.geracoes; g++){
            
            //Para cada individuo
            for(int i = 0; i < populacao.getIndividuos().size(); i++){
                
                //Gerar descendentes - lambda/mu
                for(int d = 0; d < lambda/mu; d++){
                    
                    //Clonar individuo
                    IndividuoInteger filho = (IndividuoInteger) populacao.getIndividuos().get(i).clone();
                    
                    //Mutação
                    mutacaoSWAP(filho);
                    
                    //Avaliar
                    problema.calcularFuncaoObjetivo(filho);
                    
                    //Busca Local
                    if(rnd.nextDouble() <= this.pBuscaLocal){
                        //Filho
                        BuscaLocalCombinatorio bl = new BuscaLocalCombinatorio(problema);
                        bl.executar(filho);
                        
                        //Inserir em descendentes
                        descendentes.getIndividuos().add(filho);
                        
                        //ES(mu, lambda)?  
                        //Eliminar a população corrente
                        populacao.getIndividuos().clear();

                        //ES(mu + lambda)?
                        populacao.getIndividuos().addAll(descendentes.getIndividuos());
                        //Ordenar Mu+Lambda
                        Collections.sort(populacao.getIndividuos());
                        //Definir sobreviventes
                        populacao.getIndividuos().subList(this.mu, populacao.getIndividuos().size()).clear();

                        //Limpar descendentes
                        descendentes.getIndividuos().clear();

                        System.out.println("G = " + g + "\t" + populacao.getMelhorIndividuo().getFuncaoObjetivo());
                    }
                }
            }
        }
        return populacao.getMelhorIndividuo();
    }    
    
     private void mutacaoSWAP(Individuo individuo){

        Random rnd = new Random();
        
        //Verificar a troca para cada cidade - posição
        for(int i = 0; i < individuo.getCromossomos().size(); i++){
            if (rnd.nextDouble() <= this.pMutacao){
               
                //Mutação SWAP - troca entre duas cidades
                int j;
                do{
                    j = rnd.nextInt(this.problema.getDimensao());
                }while(i == j);
                
                Collections.swap(individuo.getCromossomos(), i, j);
            }
        }
    }    
}
