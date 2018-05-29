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
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class AlgoritmoGenetico {
    
    //Tamanho da população
    Integer tamanho;
    //Taxa de crossover - operador principal
    Double pCrossover;
    //Taxa de mutação - operador secundário
    Double pMutacao;
    //Critério de parada - numero de gerações
    Integer geracoes;
    
    //Dados do problema
    //Problema - DeJong
    Problema problema;
    //Precisão
    Integer precisao;
    //Minimo
    Double minimo;
    //Maximo
    Double maximo;
    //Variaveis
    Integer nVariaveis;

    public AlgoritmoGenetico(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema, Double minimo, Double maximo, Integer nVariaveis) {
        this.tamanho = tamanho;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
    }
    
    Populacao populacao;
    Populacao novaPopulacao;
    Individuo melhorSolucao;

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }
    
    public ArrayList<Individuo> executar(){
        populacao = new Populacao(minimo, maximo, nVariaveis, tamanho, problema);
        novaPopulacao = new Populacao(minimo, maximo, nVariaveis, tamanho, problema);
        
        //Criar a população
        populacao.criar();
        
        //Avaliar
        populacao.avaliar();
        
        //Recuperar o melhor individuo
        
        Random rnd = new Random();
        int ind1, ind2;
        
        //Enquanto o critério de parada não for satisfeito - Gerações
        for(int g = 1; g <= geracoes; g++){
            
            for(int i = 0; i < this.tamanho; i++){
            
                //Crossover
                if(rnd.nextDouble() <= this.pCrossover){
                    //Realizar a operação

                    ind1 = rnd.nextInt(this.tamanho);

                    do{
                        ind2 = rnd.nextInt(this.tamanho);
                    }while (ind2 == ind1);

                    Individuo desc1 = new Individuo(minimo, maximo, nVariaveis);
                    Individuo desc2 = new Individuo(minimo, maximo, nVariaveis);

                    //progenitores
                    Individuo p1 = populacao.getIndividuos().get(ind1);
                    Individuo p2 = populacao.getIndividuos().get(ind2);

                    //ponto de corte
                    int corte = rnd.nextInt(p1.getVariaveis().size());
                    //int corte = rnd.nextInt(p1.nVar);

                    //Descendente 1 => ind1_1 + ind2_2
                    crossoverUmPonto(p1, p2, desc1, corte);

                    //Descendente 2 => ind2_1 + ind1_2    
                    crossoverUmPonto(p2, p1, desc2, corte);

                    //Mutação
                    //Descendente 1
                    mutacaoPorVariavel(desc1);

                    //Descendente2
                    mutacaoPorVariavel(desc2);

                    //Avaliar as novas soluções 
                    problema.calcularFuncaoObjetivo(desc1);
                    problema.calcularFuncaoObjetivo(desc2);

                    //Inserir na nova população
                    novaPopulacao.getIndividuos().add(desc1);
                    novaPopulacao.getIndividuos().add(desc2);
                
                }                
            }      
            
            //Definir sobreviventes => população + descendentes
            //Merge: combinar pop+desc
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            
            //Ordenar população
            Collections.sort(populacao.getIndividuos());
            
            //Eliminar os demais individuos - criterio: tamanho da população
            populacao.getIndividuos().subList(this.tamanho, populacao.getIndividuos().size()).clear();
            
            novaPopulacao.getIndividuos().clear();
            
            //Imprimir a situação atual
            //System.out.println("Gen = " + g + "\tCusto = " + populacao.getIndividuos().get(0).getFuncaoObjetivo());
        }
        //System.out.println("Melhor resultado: ");
       // System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        return populacao.getIndividuos();                
    }
    
    private void crossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte){
        
        //Crossover aritmetico - 1 ponto de corte
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();
        
        //Ind_1
        //alpha * P1
        for(int i = 0; i < corte; i ++){
            Double valor = alpha * ind1.getVariaveis().get(i);
            
            descendente.getVariaveis().add(valor);
        }        

        //Ind1_2
        //(1 - alpha) * P2        
        for(int i = corte; i < this.nVariaveis; i ++){
            Double valor = (1 - alpha) * ind2.getVariaveis().get(i);      
            
            descendente.getVariaveis().add(valor);
        }
        
    }
    
     private void mutacaoPorVariavel(Individuo individuo){

        Random rnd = new Random();

        for(int i = 0; i < individuo.getVariaveis().size(); i++){
            if (rnd.nextDouble() <= this.pMutacao){
               
                //Mutação aritmetica
                //Multiplicar rnd e inverter ou não o sinal
                
                Double valor = individuo.getVariaveis().get(i);
                
                //Multiplica por rnd
                valor *= rnd.nextDouble();
                
                //Inverter o sinal
                if(!rnd.nextBoolean()){
                    valor = -valor;
                }
                
                if(valor >= this.minimo && valor <= this.maximo){
                    individuo.getVariaveis().set(i, valor);
                }
            }
        }
    }    
}