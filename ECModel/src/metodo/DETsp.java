/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import problema.Problema;
import solucao.Individuo;
import solucao.IndividuoDouble;
import solucao.IndividuoInteger;
import solucao.PopulacaoDouble;
import solucao.PopulacaoInteger;

/**
 *
 * @author Carla
 */
public class DETsp implements Metodo{
    
    private Double minimo;
    private Double maximo;
    private Problema problema;
    
    //Criterio de parada
    private Integer gmax;
    //Numero de variaveis
    private Integer D;
    //Tamanho da população 
    private Integer Np;
    //Coeficiente de mutação
    private Double F;
    //Coeficiente de Crossover
    private Double Cr;

    public DETsp(Double minimo, Double maximo, Problema problema, Integer gmax, Integer D, Integer Np, Double F, Double Cr) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.problema = problema;
        this.gmax = gmax;
        this.D = D;
        this.Np = Np;
        this.F = F;
        this.Cr = Cr;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public Integer getGmax() {
        return gmax;
    }

    public void setGmax(Integer gmax) {
        this.gmax = gmax;
    }

    public Integer getD() {
        return D;
    }

    public void setD(Integer D) {
        this.D = D;
    }

    public Integer getNp() {
        return Np;
    }

    public void setNp(Integer Np) {
        this.Np = Np;
    }

    public Double getF() {
        return F;
    }

    public void setF(Double F) {
        this.F = F;
    }

    public Double getCr() {
        return Cr;
    }

    public void setCr(Double Cr) {
        this.Cr = Cr;
    }

    @Override
    public Individuo executar() {
        
        //Criação da população inicial - X
        PopulacaoDouble populacao = new PopulacaoDouble(this.problema, this.minimo, this.maximo, this.D, this.Np);
        populacao.criar();
        
        //População para representar o contexo cominatório
        PopulacaoInteger popTSP = new PopulacaoInteger(Np, problema);
        
        //Converter - representação real para inteiro
        this.converterRealParaInteiro(populacao,popTSP);
        
        //Avaliar a população inicial
        populacao.avaliar();
        
        //Nova população
        PopulacaoDouble novaPopulacao = new PopulacaoDouble();
        
        IndividuoDouble melhorSolucao = (IndividuoDouble) ((IndividuoDouble) populacao.getMelhorIndividuo()).clone();
        
        //Enquanto o criterio de parada não for atingido
        for(int g = 1; g <= this.gmax; g++){
            //Para cada vetor da população
            for(int i = 0; i < this.Np; i++){
                
                //Selecionar r0, r1, r2
                Random rnd = new Random();
                //int r0 = rnd.nextInt(this.Np);
                int r0, r1, r2;
                
                do{
                    r0 = rnd.nextInt(this.Np);
                }while(r0 == i);
                do{
                    r1 = rnd.nextInt(this.Np);
                }while(r1 == 0);
                
                do{
                    r2 = rnd.nextInt(this.Np);
                }while(r2 == r1 || r2 == r0);
                
                IndividuoDouble trial = new IndividuoDouble(minimo, maximo, this.D);
                
                IndividuoDouble xr0 = (IndividuoDouble) populacao.getIndividuos().get(r0);
                IndividuoDouble xr1 = (IndividuoDouble) populacao.getIndividuos().get(r1);
                IndividuoDouble xr2 = (IndividuoDouble) populacao.getIndividuos().get(r2);
                
                //Gerar perturbação - diferença
                gerarPerturbacao(trial, xr1, xr2);
                //Mutação - r0 + F * perturbação
                mutacao(trial, xr0);
                //Target
                IndividuoDouble target = (IndividuoDouble) populacao.getIndividuos().get(i);
                //Crossover
                crossover(trial, target);
                //Seleção
                IndividuoInteger trialTSP = this.converterRealParaInteiro(trial);
                problema.calcularFuncaoObjetivo(trial);
                IndividuoInteger targetTSP = this.converterRealParaInteiro(target);
                problema.calcularFuncaoObjetivo(targetTSP);
                
                //Busca local
                //BL(trial)
                
                trial.setFuncaoObjetivo(trialTSP.getFuncaoObjetivo());
                target.setFuncaoObjetivo(targetTSP.getFuncaoObjetivo());
                
                if(trial.getFuncaoObjetivo() <= target.getFuncaoObjetivo()){
                    novaPopulacao.getIndividuos().add(trial);
                }
                else{
                    novaPopulacao.getIndividuos().add(target);
                }
            }
            
            //População para a geração seguinte
            populacao.getIndividuos().clear();
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            
            IndividuoDouble melhorDaPopulacao = (IndividuoDouble) populacao.getMelhorIndividuo();
            
            if(melhorDaPopulacao.getFuncaoObjetivo() <= melhorSolucao.getFuncaoObjetivo()){
                melhorSolucao = (IndividuoDouble) melhorDaPopulacao.clone();
            }
            
            System.out.println("G = " + g + "\t" + melhorSolucao.getFuncaoObjetivo());
        }
        return melhorSolucao;
    }

    private void gerarPerturbacao(IndividuoDouble trial, IndividuoDouble xr1 ,IndividuoDouble xr2){
        //trial <- Diferença entre r1 e r2
        for(int i = 0; i < this.D; i++){
            Double diferenca = xr1.getCromossomos().get(i) - xr2.getCromossomos().get(i);
            
            trial.getCromossomos().add(diferenca);
        }
    }
    
    private void mutacao(IndividuoDouble trial, IndividuoDouble xr0){
        //trial <- r0 + F * perturbação (trial)
        for(int i = 0; i < this.D; i++){
            Double valor = this.F * xr0.getCromossomos().get(i) + this.F * (trial.getCromossomos().get(i));
            
            while(valor < this.minimo || valor > this.maximo){
                valor *= this.F;
            }
            
            trial.getCromossomos().set(i, valor);
        }
    }
    
    private void crossover(IndividuoDouble trial, IndividuoDouble target){
        Random rnd = new Random();
        int j = rnd.nextInt(this.D);
        
        for(int i = 0; i < this.D; i++){
            if(!(rnd.nextDouble() <= this.Cr || i == j)){
                //Target
                trial.getCromossomos().set(i, target.getCromossomos().get(i));
            }
        }
    }
    
    private void converterRealParaInteiro(PopulacaoDouble populacao, PopulacaoInteger popTSP){
        popTSP.getIndividuos().clear();
        
        for(Individuo ind : populacao.getIndividuos()){
            
            IndividuoInteger indTSP = this.converterRealParaInteiro((IndividuoDouble) ind);
            popTSP.getIndividuos().add(indTSP);
        }
        
    }
    
    private IndividuoInteger converterRealParaInteiro(IndividuoDouble individuo){
        
        IndividuoInteger indTSP = new IndividuoInteger(this.D);
        indTSP.setCromossomos(new ArrayList<>(Arrays.asList(new Integer[this.D])));
        
        HashMap<Integer, Double> valores = new HashMap<>();
        
        for(int i = 0; i < this.D; i++){
            valores.put(i, individuo.getCromossomos().get(i));
        }
        
        IndividuoDouble copiaValores = (IndividuoDouble) individuo.clone();
        
        IndividuoDouble copiaPosicoes = (IndividuoDouble) individuo.clone();
        
        //Ordenar o cromossomo
        Collections.sort(copiaValores.getCromossomos());
        
        int cliente = 1;
        
        for(int i = 0; i < this.D; i++){
            //Recuperar a posição em relação ao valor
            int posicao = copiaPosicoes.getCromossomos().indexOf(copiaValores.getCromossomos().get(i));
            indTSP.getCromossomos().set(posicao, cliente);            
            copiaValores.getCromossomos().set(posicao, Double.NaN);
            cliente++;
        }
        
        indTSP.setFuncaoObjetivo(individuo.getFuncaoObjetivo());
        
        return indTSP;
        
    }
}
