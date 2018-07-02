/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class AlgoritmoAGDE {
    
    Populacao populacao;     
    Integer geracoes; //Critério de parada - numero de gerações    
    Double pCrossover; //Taxa de crossover - operador principal    
    Double pMutacao; //Taxa de mutação - operador secundário
   
    Integer tamanho; //Tamanho da população    
    Double minimo; //Minimo    
    Double maximo; //Maximo
    Integer nVariaveis;  //Variaveis     
    Problema problema; //Problema Schwefel

    public AlgoritmoAGDE(Integer geracoes, Double pCrossover, Double pMutacao, Integer tamanho, Double minimo, Double maximo, Integer nVariaveis, Problema problema) {
        this.geracoes = geracoes;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.tamanho = tamanho;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.problema = problema;
    }
    
    public Individuo getMelhorSolucao(Populacao populacao) {
        
        Individuo melhorSolucao = populacao.getMelhorIndividuo().clone();
        
        return melhorSolucao;
    }
    
    public Individuo executar(){
        
        Random rnd = new Random();
        
        this.populacao = new Populacao(this.minimo, this.maximo, this.nVariaveis, this.tamanho, this.problema);
        
        //Criar a população
        populacao.criar();
                
        //Avaliar a população
        populacao.avaliar();
        
        //Nova população
        Populacao novaPopulacao = new Populacao();
        
        Individuo melhorSolucao = getMelhorSolucao(this.populacao);
        
        for(int g = 1; g <= this.geracoes; g++){
            for(int i = 0; i < this.tamanho; i++){
                //Crossover
                if(rnd.nextDouble() <= this.pCrossover){
                    
                    int r0, r1;
                    
                    do{
                        r0 = rnd.nextInt(this.tamanho);
                    }while(r0 == i);
                    do{
                        r1 = rnd.nextInt(this.tamanho);
                    }while(r1 == r0);                    
                    
                    Individuo progenitor1 = populacao.getIndividuos().get(r0);
                    Individuo progenitor2 = populacao.getIndividuos().get(r1);
                    Individuo descendente = new Individuo(this.minimo, this.maximo, this.nVariaveis);
                    
                     //ponto de corte
                    int corte = rnd.nextInt(progenitor1.getCromossomos().size());
                    
                    crossoverUmPonto(progenitor1, progenitor2, descendente, corte);
                    
                    ArrayList<Individuo> progenitores = new ArrayList<>();
                    progenitores.add(progenitor1);
                    progenitores.add(progenitor2);
                    
                    Individuo melhorProgenitor = Collections.min(progenitores);
                    
                    mutacao(melhorProgenitor, descendente);
                    
                    problema.calcularFuncaoObjetivo(descendente);
                    
                    novaPopulacao.getIndividuos().add(descendente);
                }
            }
            
            //populacao para a geração seguinte            
            this.populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());    
            
            Individuo melhorDaPopulacao = this.populacao.getMelhorIndividuo();
            
            if(melhorDaPopulacao.getFuncaoObjetivo() <= melhorSolucao.getFuncaoObjetivo()){
                melhorSolucao = melhorDaPopulacao.clone();
            }
            
            this.populacao.getIndividuos().add(melhorSolucao);
            
            //Ordenar população
            Collections.sort(populacao.getIndividuos());
            
            //Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos().subList(this.tamanho, populacao.getIndividuos().size()).clear();
            novaPopulacao.getIndividuos().clear();
                        
            //System.out.println("G = " + g + "\t" + melhorSolucao.getFuncaoObjetivo());
        }
        
        return melhorSolucao;
    }    
        
    private void crossoverUmPonto(Individuo progenitor1, Individuo progenitor2, Individuo descendente, int corte){
        
        //Crossover aritmetico - 1 ponto de corte
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();
        
        //Ind_1
        //alpha * P1
        for(int i = 0; i < corte; i ++){
            Double valor = alpha * progenitor1.getCromossomos().get(i);
            
            descendente.getCromossomos().add(valor);
        }        

        //Ind1_2
        //(1 - alpha) * P2        
        for(int i = corte; i < this.nVariaveis; i ++){
            Double valor = (1 - alpha) * progenitor2.getCromossomos().get(i);      
            
            descendente.getCromossomos().add(valor);
        }
    }
    
    public void mutacao(Individuo melhorProgenitor, Individuo descendente){
        
        for(int i = 0; i < this.nVariaveis; i++){
            Double valor = this.pMutacao * melhorProgenitor.getCromossomos().get(i) + this.pMutacao * 
                            descendente.getCromossomos().get(i);
            
            while(valor < this.minimo || valor > this.maximo){
                valor *= this.pMutacao;
            }
            
            descendente.getCromossomos().set(i,valor);
        }
    }
}
