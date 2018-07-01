/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

import java.util.ArrayList;

/**
 *
 * @author Carla
 */
public class Populacao {
    
    //Valor minimo
    Double minimo;
    //Valor máximo
    Double maximo;
    
    //Numero de variaveis
    Integer nVar;
    
    //tamanho da população
    Integer tamanho;
    
    //Conjunto de individuos
    ArrayList<Individuo> individuos;
    
    //Problema
    Problema problema;

    public Populacao(Double minimo, Double maximo, Integer nVar, Integer tamanho, Problema problema) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.tamanho = tamanho;        
        this.problema = problema;
        this.individuos = new ArrayList<>();
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

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }
    
    //Criar a população
    public void criar(){
        
        this.individuos = new ArrayList<>();
        
        for(int i = 0; i < this.getTamanho(); i++){
            Individuo individuo = new Individuo(minimo, maximo, nVar);
            individuo.criar();
            
            this.getIndividuos().add(individuo);
        }
    }        
}
